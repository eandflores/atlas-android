package com.aim.atlas.ui.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.aim.atlas.vo.User
import com.aim.atlas.repository.UserRepository
import com.aim.atlas.vo.Resource
import javax.inject.Inject
import android.text.TextUtils
import android.arch.lifecycle.Transformations
import com.aim.atlas.util.AbsentLiveData


class LoginViewModel() : ViewModel() {

    private lateinit var userRepository: UserRepository
    var user: LiveData<Resource<User>>

    private var email : String
    private var password : String
    private var failedLoginIntents : Int

    val blockedAccountActivity = 1
    val maxFailedLoginIntents = 3

    init {
        user = AbsentLiveData.create()
        email = ""
        password = ""
        failedLoginIntents = 0
    }

    @Inject
    constructor(userRepository: UserRepository) : this() {
        this.userRepository = userRepository
    }

    fun login() {
        increaseFailedLoginIntents()

        this.user = Transformations.switchMap(user, {
            userRepository.login(email, password)
        })
    }

    fun isEmptyEmail() : Boolean {
        return TextUtils.isEmpty(email)
    }

    fun isValidEmail() : Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun setEmail(email: String){
        this.email = email
    }

    fun isEmptyPassword() : Boolean {
        return TextUtils.isEmpty(password)
    }

    fun setPassword(password: String){
        this.password = password
    }

    fun getFailedLoginIntents() : Int{
        return failedLoginIntents
    }

    private fun increaseFailedLoginIntents() {
        failedLoginIntents += 1
    }

    fun resetFailedLoginIntents() {
        failedLoginIntents = 0
    }

    /*fun retry() {
        if (this.login.value != null)
            this.login.value = this.login.value
    }*/


}