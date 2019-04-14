package com.ziyata.kotlincrud.database

import com.ziyata.kotlincrud.model.User
import io.reactivex.Flowable

interface IUserDataSource {
    val allUsers: Flowable<List<User>>
    fun insertUser(vararg users: User)
    fun updateUser(nama : String, id : Int)
    fun delete(id : Int)
}