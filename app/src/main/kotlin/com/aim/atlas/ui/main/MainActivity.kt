package com.aim.atlas.ui.main

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import com.aim.atlas.R
import com.aim.atlas.ui.login.LoginActivity
import com.aim.atlas.ui.splash.SplashActivity
import com.aim.atlas.util.Constants


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        goToSplashActivity()
    }

    fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra(Constants().expired_session, true)
        startActivity(intent)
        finish()
    }

    private fun goToSplashActivity() {
        if(viewModel.getSplash()) {
            viewModel.setShowSplash(false)
            startActivityForResult(Intent(this, SplashActivity::class.java), viewModel.splashActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == viewModel.splashActivity && resultCode == Activity.RESULT_OK)
            goToLoginActivity()
    }
}
