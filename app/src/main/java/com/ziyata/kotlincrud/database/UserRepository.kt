package com.ziyata.kotlincrud.database

import com.ziyata.kotlincrud.model.User
import io.reactivex.Flowable

class UserRepository(private val mLocationDataSource: IUserDataSource ) : IUserDataSource {
    override fun updateUser(nama: String, id: Int) {
        mLocationDataSource.updateUser(nama, id)
    }

    override fun delete(id: Int) {
        mLocationDataSource.delete(id)
    }

    override val allUsers: Flowable<List<User>>
        get() = mLocationDataSource.allUsers

    override fun insertUser(vararg users: User) {
        mLocationDataSource.insertUser(*users)
    }

    companion object {
        private var mInstance: UserRepository? = null
        fun gerInstance(mLocationDataSource: IUserDataSource): UserRepository{
            if (mInstance == null){
                mInstance = UserRepository(mLocationDataSource)
            }
            return mInstance!!
        }
    }

}