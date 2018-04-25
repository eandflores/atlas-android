package com.aim.atlas.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import com.aim.atlas.R
import com.aim.atlas.ui.login.LoginActivity
import com.aim.atlas.util.Constants


class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel?.init()

        goToLoginActivity()
    }

    fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(Constants().expired_session, true)
        startActivity(intent)
        finish()
    }
}
