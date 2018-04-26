package com.aim.atlas.vo


import java.io.Serializable
import android.arch.persistence.room.*


@Entity
class User() : Serializable {

    @PrimaryKey
    private var id: Int = 0
    var email: String? = null
    var password: String? = null

    constructor(email: String, password: String) : this() {
        this.email = email
        this.password = password
    }

    constructor(id : Int, email: String, password: String) : this() {
        this.id = id
        this.email = email
        this.password = password
    }
}