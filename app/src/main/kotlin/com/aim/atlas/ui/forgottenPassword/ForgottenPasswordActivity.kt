package com.aim.atlas.ui.forgottenPassword

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import com.aim.atlas.R
import kotlinx.android.synthetic.main.activity_forgotten_password.textViewForgottenPassword
import kotlinx.android.synthetic.main.activity_forgotten_password.buttonForgottenPassword

class ForgottenPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotten_password)

        setView()
        setEvents()
    }

    private fun setView() {
        /*setSupportActionBar(toolbar as Toolbar?)

        if(supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true);*/

        textViewForgottenPassword.text = Html.fromHtml(getString(
                R.string.forgotten_password_body,
                ContextCompat.getColor(this, R.color.forgotten_password_email).toString(),
                "edgardo.flores.osben@gmail.com"
        ))
    }

    private fun setEvents() {
        buttonForgottenPassword.setOnClickListener {
            finish()
        }
    }
}