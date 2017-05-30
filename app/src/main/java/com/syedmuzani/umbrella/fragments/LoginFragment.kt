package com.syedmuzani.umbrella.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.syedmuzani.umbrella.R
import org.jetbrains.anko.toast

/**
 * Code from https://github.com/hananideen/Login
 */
class LoginFragment : Fragment() {

    private var callbackManager: CallbackManager? = null

    private var accessTokenTracker: AccessTokenTracker? = null
    private var profileTracker: ProfileTracker? = null

    private val callback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            val accessToken = loginResult.accessToken
            val profile: Profile? = Profile.getCurrentProfile()
            displayMessage(profile)
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
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        }

        profileTracker = object : ProfileTracker() {
            override fun onCurrentProfileChanged(oldProfile: Profile?, newProfile: Profile?) {
                displayMessage(newProfile)
            }
        }

        accessTokenTracker?.startTracking()
        profileTracker?.startTracking()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginButton = view!!.findViewById(R.id.login_button) as LoginButton

        loginButton.setReadPermissions("public_profile", "email")
        loginButton.fragment = this
        loginButton.registerCallback(callbackManager, callback)

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
}