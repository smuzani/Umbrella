package com.syedmuzani.umbrella.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.syedmuzani.umbrella.R
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 * Login to Facebook
 */
class FacebookLoginActivity : AppCompatActivity() {

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar: Toolbar = find(R.id.toolbar)
        setSupportActionBar(toolbar)

        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        toast("Success! " + loginResult.toString())
                    }

                    override fun onCancel() {
                        toast("Canceled")
                    }

                    override fun onError(exception: FacebookException) {
                        toast("Error: " + exception.toString())
                    }
                })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
