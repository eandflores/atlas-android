package com.aim.atlas.ui.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.aim.atlas.vo.User
import com.aim.atlas.repository.UserRepository
import com.aim.atlas.vo.Resource
import javax.inject.Inject
import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import java.util.*


//class LoginViewModel @Inject constructor(var userRepo: UserRepository?) : ViewModel() {
class LoginViewModel() : ViewModel() {

    val login = MutableLiveData<String>()
    private var user: LiveData<Resource<User>>? = null
    private var userRepo: UserRepository? = null


    @Inject // UserRepository parameter is provided by Dagger 2
    constructor(userRepo: UserRepository) : this() {
        this.userRepo = userRepo
    }

    fun setLogin(login: String) {
        if (Objects.equals(this.login.value, login))
            return

        this.login.value = login
    }

    fun emailValidation(email : String?) : Boolean {
        if (TextUtils.isEmpty(email))
            return false
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false

        return true
    }

    fun passwordValidation(password : String?) : Boolean {
        if (TextUtils.isEmpty(password))
            return false

        return true
    }

    fun getUser() : LiveData<Resource<User>>? {
        return this.user
    }

    fun retry() {
        if (this.login.value != null)
            this.login.value = this.login.value
    }


}