package com.aim.atlas.vo


import java.io.Serializable
import android.arch.persistence.room.*


@Entity
class User() : Serializable {

    @PrimaryKey
    private var id: Int = 0
    private var email: String? = null
    private var password: String? = null

    constructor(id : Int, email: String, password: String) : this() {
        this.id = id
        this.email = email
        this.password = password
    }
}