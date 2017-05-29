package com.syedmuzani.umbrella.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.syedmuzani.umbrella.R
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


/**
 * A placeholder fragment containing a simple view.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater!!.inflate(R.layout.fragment_login, container, false)
        var loginButton: LoginButton = v.find(R.id.login_button);
        loginButton.setReadPermissions("email")
        // If using in a fragment
        loginButton.fragment = this
        // Other app specific specialization

        // Callback registration
        val callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                context.toast("Success! " + loginResult.toString())
            }

            override fun onCancel() {
                context.toast("Canceled")
            }

            override fun onError(exception: FacebookException) {
                context.toast("Error: " + exception.toString())
            }
        })
        return v
    }
}
