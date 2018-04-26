package com.aim.atlas.ui.login

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.LiveData
import com.aim.atlas.vo.User
import com.aim.atlas.repository.UserRepository
import com.aim.atlas.vo.Resource
import javax.inject.Inject
import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import android.arch.lifecycle.Transformations
import com.aim.atlas.util.AbsentLiveData
import android.text.Editable
import android.text.TextWatcher
import android.view.View


class LoginViewModel() : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    private var user: LiveData<Resource<User>>? = null
    private var userRepository: UserRepository? = null

    @Inject
    constructor(userRepository: UserRepository) : this() {
        this.userRepository = userRepository
    }

    fun login(email: String, password : String) : String? {
        val user = MutableLiveData<User>()
        user.value = User(email, password)

        this.user = Transformations.switchMap(user, {
            if (it == null)
                AbsentLiveData.create()
            else
                userRepository?.login(it.email!!, it.password!!)
        })

        return null
    }


    fun emailOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            if(login(email.value!!, password.value!!) != null) {

            } else {

            }
        }
    }

    fun passwordWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                password.value = charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        }
    }

    fun isEmptyEmail() : Boolean {
        return TextUtils.isEmpty(email.value)
    }

    fun isValidEmail() : Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    fun isEmptyPassword() : Boolean {
        return TextUtils.isEmpty(password.value)
    }

    fun getUser() : LiveData<Resource<User>>? {
        return this.user
    }

    /*fun retry() {
        if (this.login.value != null)
            this.login.value = this.login.value
    }*/


}