package com.ziyata.kotlincrud.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ziyata.kotlincrud.local.UserDatabase.Companion.DATABASE_VERSION
import com.ziyata.kotlincrud.model.User

@Database(entities = arrayOf(User::class),version = DATABASE_VERSION)
abstract class UserDatabase: RoomDatabase(){
    abstract fun userDAO(): UserDAO

    companion object {
        const val DATABASE_VERSION = 1
        val DATABASE_NAME = "CodingTalk_Room"

        private var mInstant: UserDatabase? = null

        fun getInstance(context: Context) : UserDatabase{
            if(mInstant == null){
                mInstant = Room.databaseBuilder(context, UserDatabase::class.java,
                        DATABASE_NAME).fallbackToDestructiveMigration()
                        .build()
            }
            return mInstant!!
        }
    }
}