package com.aim.atlas.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import com.aim.atlas.R
import com.aim.atlas.vo.User
import com.aim.atlas.util.Constants
import com.aim.atlas.vo.Resource
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_login.editTextEmail
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import kotlinx.android.synthetic.main.activity_login.textViewHeaderLogin
import kotlinx.android.synthetic.main.activity_login.buttonLogin


class LoginActivity : AppCompatActivity() {

    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setView()
        setEvents()
    }

    private fun setView() {
        if(!intent.getBooleanExtra(Constants().expired_session, false)) {
            textViewHeaderLogin.text = getString(R.string.login_header)
            textViewHeaderLogin.gravity = Gravity.CENTER
            textViewHeaderLogin.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
        } else {
            textViewHeaderLogin.text = getString(R.string.login_header_expired_session, "Edgardo")
            textViewHeaderLogin.gravity = Gravity.START
            textViewHeaderLogin.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.75f)
            editTextEmail.setText("edgardo.flores.osben@gmail.com")
        }
    }

    private fun setEvents() {
        buttonLogin?.setOnClickListener {
            viewModel?.getUser()?.observe(this, Observer<Resource<User>> {
                // update UI
            })
        }
    }
}
