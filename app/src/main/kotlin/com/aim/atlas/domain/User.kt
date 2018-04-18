package com.aim.atlas.domain


import java.io.Serializable
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


@Entity
class User : Serializable {

    @PrimaryKey
    private var id: Int = 0
    private var email: String? = null
    private var password: String? = null

    fun User(id : Int, email: String, password: String) {
        this.id = id
        this.email = email
        this.password = password
    }
}

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM user WHERE id = :userId")
    fun load(userId: String): LiveData<User>
}