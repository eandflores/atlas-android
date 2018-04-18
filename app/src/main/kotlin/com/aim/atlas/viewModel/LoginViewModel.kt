package com.aim.atlas.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.aim.atlas.domain.User
import com.aim.atlas.repository.UserRepository
import javax.inject.Inject


class LoginViewModel : ViewModel() {

    private var user: LiveData<User>? = null
    private var userRepo: UserRepository? = null

    @Inject // UserRepository parameter is provided by Dagger 2
    fun LoginViewModel(userRepo: UserRepository) {
        this.userRepo = userRepo
    }

    fun init(userEmail : String, userPassword : String) {
        if (this.user != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        user = userRepo?.getUser(userEmail, userPassword);
    }

    fun getUser() : LiveData<User>? {
        return this.user
    }



}