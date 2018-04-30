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
import com.aim.atlas.util.Constants
import android.widget.LinearLayout
import com.aim.atlas.ui.blockedAccount.BlockedAccountActivity
import com.aim.atlas.ui.forgottenPassword.ForgottenPasswordActivity
import com.aim.atlas.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.editTextEmail
import kotlinx.android.synthetic.main.activity_login.editTextPassword
import kotlinx.android.synthetic.main.activity_login.textViewHeaderLogin
import kotlinx.android.synthetic.main.activity_login.buttonLogin
import kotlinx.android.synthetic.main.activity_login.textViewForgottenPassword
import kotlinx.android.synthetic.main.activity_login.coordinatorLayout
import kotlinx.android.synthetic.main.activity_login.textInputLayoutEmail
import kotlinx.android.synthetic.main.activity_login.textInputLayoutPassword
import android.support.design.widget.Snackbar


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel.user.observe(this, Observer {
            //update ui
        })

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

    private fun submitValidations() {
        if(viewModel.isEmptyEmail()) {
            disableForgottenPasswordTextView()
            disableLoginButton()
            hideEmailError()
        } else if(!viewModel.isValidEmail()) {
            disableForgottenPasswordTextView()
            disableLoginButton()
            showEmailError(resources.getString(R.string.login_email_validation))
        } else {
            enableForgottenPasswordTextView()
            hideEmailError()

            if(viewModel.isEmptyPassword())
                disableLoginButton()
            else
                enableLoginButton()
        }
    }

    private fun showEmailError(error: String) {
        textInputLayoutEmail.isErrorEnabled = true
        textInputLayoutEmail.error = error
        editTextEmail.requestFocus()
    }

    private fun hideEmailError() {
        textInputLayoutEmail.isErrorEnabled = false
        textInputLayoutEmail.error = null
    }

    private fun setListeners() {
        editTextEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel.setEmail(charSequence.toString().trim())
            }

            override fun afterTextChanged(editable: Editable) {
                submitValidations()
            }
        })

        editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel.setPassword(charSequence.toString().trim())
            }

            override fun afterTextChanged(editable: Editable) {
                submitValidations()
            }
        })

        buttonLogin.setOnClickListener {
            if(viewModel.getFailedLoginIntents() < viewModel.maxFailedLoginIntents) {
                showSnackBarLoginIntents()
                viewModel.login()
            } else
                goToBlockedAccountActivity()
        }

        textViewForgottenPassword.setOnClickListener {
            //Todo: Call forgotten password service
            goToForgottenPasswordActivity()
        }
    }

    private fun showSnackBarLoginIntents() {
        val intents = (viewModel.maxFailedLoginIntents - viewModel.getFailedLoginIntents()).toString()
        val snackBar = Snackbar.make(coordinatorLayout, getString(R.string.blocked_account_intents, intents), Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun goToForgottenPasswordActivity() {
        startActivity(Intent(this, ForgottenPasswordActivity::class.java))
    }

    private fun goToBlockedAccountActivity() {
        startActivityForResult(Intent(this, BlockedAccountActivity::class.java), viewModel.blockedAccountActivity)
    }

    //Keep first activity
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == viewModel.blockedAccountActivity && resultCode == RESULT_OK) {
            viewModel.resetFailedLoginIntents()
            editTextEmail.setText("")
            editTextPassword.setText("")
            showGenericWelcome()
        }
    }
}
