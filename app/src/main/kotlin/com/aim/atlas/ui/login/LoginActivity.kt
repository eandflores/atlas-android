package com.aim.atlas.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import com.aim.atlas.R
import com.aim.atlas.vo.User
import com.aim.atlas.util.Constants
import com.aim.atlas.vo.Resource
import android.widget.LinearLayout
import com.aim.atlas.ui.forgottenPassword.ForgottenPasswordActivity
import com.aim.atlas.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.editTextEmail
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import kotlinx.android.synthetic.main.activity_login.textViewHeaderLogin
import kotlinx.android.synthetic.main.activity_login.buttonLogin
import kotlinx.android.synthetic.main.activity_login.textViewForgottenPassword


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setListeners()
        setView()
    }

    private fun setView() {
        if(!intent.getBooleanExtra(Constants().expired_session, false))
            showGenericWelcome()
        else
            showLoggedWelcome()
    }

    private fun showGenericWelcome() {
        textViewHeaderLogin.text = getString(R.string.login_header)
        textViewHeaderLogin.gravity = Gravity.CENTER
        textViewHeaderLogin.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
        editTextEmail.setText("")
        editTextPassword.setText("")
    }

    private fun showLoggedWelcome() {
        textViewHeaderLogin.text = getString(R.string.login_header_expired_session, "Edgardo")
        textViewHeaderLogin.gravity = Gravity.START
        textViewHeaderLogin.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.75f)
        editTextEmail.setText("edgardo.flores.osben@gmail.com")
        editTextPassword.setText("")
    }

    private fun enableLoginButton() {
        buttonLogin.isEnabled = true
        buttonLogin.alpha = 1f
    }

    private fun enableForgottenPasswordTextView() {
        textViewForgottenPassword.isEnabled = true
        textViewForgottenPassword.alpha = 1f
    }

    private fun disableLoginButton() {
        buttonLogin.isEnabled = false
        buttonLogin.alpha = 0.5f
    }

    private fun disableForgottenPasswordTextView() {
        textViewForgottenPassword.isEnabled = false
        textViewForgottenPassword.alpha = 0.5f
    }

    private fun showOrHideButtons() {
        if(viewModel.isEmptyEmail() || !viewModel.isValidEmail()) {
            disableForgottenPasswordTextView()
            disableLoginButton()
        } else {
            enableForgottenPasswordTextView()

            if(viewModel.isEmptyPassword())
                disableLoginButton()
            else
                enableLoginButton()
        }
    }

    private fun setListeners() {
        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel.email.value = charSequence.toString().trim()
            }

            override fun afterTextChanged(editable: Editable) {
                showOrHideButtons()
            }
        })

        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel.password.value = charSequence.toString().trim()
            }

            override fun afterTextChanged(editable: Editable) {
                showOrHideButtons()
            }
        })

        buttonLogin.setOnClickListener {
            viewModel.getUser()?.observe(this, Observer<Resource<User>> {
                //Todo: Update UI
            })
        }

        textViewForgottenPassword.setOnClickListener {
            //Todo: Call forgotten password device
            goToForgottenPasswordActivity()
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun goToForgottenPasswordActivity() {
        startActivity(Intent(this, ForgottenPasswordActivity::class.java))
    }
}
