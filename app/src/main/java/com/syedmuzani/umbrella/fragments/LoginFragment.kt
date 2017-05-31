package com.syedmuzani.umbrella.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.facebook.*
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.syedmuzani.umbrella.R
import org.jetbrains.anko.toast
import org.json.JSONException
import java.util.*


/**
 * Code partially from https://github.com/hananideen/Login
 */
class LoginFragment : Fragment() {

    val TAG = "LoginFragment"

    private var callbackManager: CallbackManager? = null

    private var accessTokenTracker: AccessTokenTracker? = null
    private var accessToken: AccessToken? = null
    private var profileTracker: ProfileTracker? = null
    lateinit private var btLogin: Button

    val permissions: List<String> = Arrays.asList("public_profile", "user_friends")

    private val callback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            accessToken = loginResult.accessToken
            context.toast("Success!")
            if (accessToken != null) {
                getUserDetailsFromFB(accessToken!!)
            }
            setButton()
        }

        override fun onCancel() {
            context.toast("Canceled")
        }

        override fun onError(e: FacebookException) {
            context.toast("Error: " + e.toString())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callbackManager = CallbackManager.Factory.create()

        accessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(oldToken: AccessToken?, newToken: AccessToken?) {
                updateWithToken(newToken);
                setButton()
            }
        }

        profileTracker = object : ProfileTracker() {
            override fun onCurrentProfileChanged(oldProfile: Profile?, newProfile: Profile?) {
                displayMessage(newProfile)
                setButton()
            }
        }

        accessTokenTracker?.startTracking()
        profileTracker?.startTracking()
    }

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null
    }

    // TODO Bug: Is not being called upon logout
    fun setButton() {
        if (!isLoggedIn()) {
            btLogin.text = "Facebook Login"
            btLogin.setOnClickListener {
                LoginManager.getInstance().logInWithReadPermissions(this, permissions)
            }
        } else {
            btLogin.text = "Facebook Log Out"
            btLogin.setOnClickListener {
                LoginManager.getInstance().logOut()
            }
        }
    }

    private fun updateWithToken(currentAccessToken: AccessToken?) {
        val TIMEOUT = 20000L
        if (currentAccessToken != null) {
            Handler().postDelayed({
                displayMessage(Profile.getCurrentProfile())
            }, TIMEOUT)
        } else {
            Handler().postDelayed({
                context.toast("Not logged in")
            }, TIMEOUT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_login, container, false)
        val loginButton = v.findViewById(R.id.login_button) as LoginButton
        btLogin = v.findViewById(R.id.bt_login) as Button
        setButton()
        loginButton.setReadPermissions(permissions)
        loginButton.fragment = this
        loginButton.registerCallback(callbackManager, callback)
        LoginManager.getInstance().registerCallback(callbackManager, callback)
        return v;
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    private fun displayMessage(profile: Profile?) {
        if (profile != null) {
            Toast.makeText(activity, "User: " + profile.name, Toast.LENGTH_LONG).show()
        }
    }

    override fun onStop() {
        super.onStop()
        accessTokenTracker?.stopTracking()
        profileTracker?.stopTracking()
    }

    override fun onResume() {
        super.onResume()
        val profile = Profile.getCurrentProfile()
        displayMessage(profile)
    }

    private fun getUserDetailsFromFB(accessToken: AccessToken) {
        val request = GraphRequest.newMeRequest(accessToken) { user, _ ->
            try {
                val id = user.getString("id")
                val name = user.getString("name")
                val email = user.getString("email")
                val gender = user.getString("gender")
                Log.d(TAG, "id: $id, name: $name, email: $email, gender: $gender")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,email,gender")
        request.parameters = parameters
        request.executeAsync()
    }
}