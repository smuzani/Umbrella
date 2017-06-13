package com.syedmuzani.umbrella.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.syedmuzani.umbrella.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class DslActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DslActivityUI().setContentView(this)
        val btCancel: Button = find(DslActivityUI.ID_CANCEL)
        btCancel.setOnClickListener { finish() }
    }


    class DslActivityUI : AnkoComponent<DslActivity> {
        companion object {
            val ID_CANCEL = 11
            val ID_FB_LOGIN = 50
        }

        override fun createView(ui: AnkoContext<DslActivity>) = with(ui) {
            verticalLayout {
                horizontalPadding = dip (16)
                verticalPadding = dip(32)
                val name = editText{
                    hint = "Name"
                }
                linearLayout {
                    themedButton("Say Hello", theme = R.style.BtColored) {
                        onClick { ctx.toast("Hello, ${name.text}!") }
                    }
                    button("Cancel") {
                        id = ID_CANCEL
                    }
                }
                themedButton("This does nothing", theme = R.style.BtFlat)
            }
        }
    }
}
