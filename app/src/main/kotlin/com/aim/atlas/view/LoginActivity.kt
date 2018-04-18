package com.aim.atlas.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.aim.atlas.R
import com.aim.atlas.domain.User
import com.aim.atlas.viewModel.LoginViewModel
import android.databinding.DataBindingUtil
import com.aim.atlas.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private var viewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding : ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        viewModel?.init("edgardo.flores.osben@gmail.com", "111111")

        viewModel?.getUser()?.observe(this, Observer<User> {
            binding.setUser(it)
        })
    }
}
