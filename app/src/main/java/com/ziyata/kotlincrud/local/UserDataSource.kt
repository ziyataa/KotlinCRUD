package com.ziyata.kotlincrud.local

import com.ziyata.kotlincrud.database.IUserDataSource
import com.ziyata.kotlincrud.model.User
import io.reactivex.Flowable

class UserDataSource(private val userDAO: UserDAO): IUserDataSource {
    override fun updateUser(nama: String, id: Int) {
        userDAO.updateUser(nama, id)
    }

    override fun delete(id: Int) {
        userDAO.deleteUser(id)
    }

    override val allUsers: Flowable<List<User>>
        get() = userDAO.allUsers

    override fun insertUser(vararg users: User) {
        userDAO.insertUser(*users)
    }

    companion object {
        private var mInstance: UserDataSource? = null
        fun getInstance(userDAO: UserDAO): UserDataSource{
            if (mInstance == null){
                mInstance = UserDataSource(userDAO)
            }
            return mInstance!!
        }
    }

}