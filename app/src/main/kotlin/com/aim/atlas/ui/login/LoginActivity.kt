package com.aim.atlas.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.aim.atlas.R
import com.aim.atlas.vo.User
import android.widget.EditText
import butterknife.BindView
import com.aim.atlas.vo.Resource


class LoginActivity : AppCompatActivity() {

    private var viewModel: LoginViewModel? = null

    @BindView(R.id.editTextEmail)
    private val editTextEmail: EditText? = null

    @BindView(R.id.editTextPassword)
    private val editTextPassword: EditText? = null

    @BindView(R.id.buttonLogin)
    private val buttonLogin: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setEvents()
    }

    private fun setEvents() {
        buttonLogin?.setOnClickListener {
            viewModel?.getUser()?.observe(this, Observer<Resource<User>> {
                // update UI
            })
        }
    }
}
