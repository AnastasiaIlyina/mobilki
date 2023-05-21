package com.example.mobilki.data.dao

import androidx.room.*
import com.example.mobilki.data.enity.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

//Data Access Objects, DAO), которые определяют методы для взаимодействия с базой данных.

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
     fun getAll(): Maybe<List<User>>

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
     fun loadAllByIds(userIds: IntArray): Maybe<List<User>>

    @Query("SELECT * FROM users WHERE phoneNumber  = :phoneNumber")
     fun findByNumber(phoneNumber: String): Single<User>

    @Query("SELECT * FROM users WHERE uid = :uid")
     fun findById(uid: String): Single<User>



    @Update
     fun updateUser(user: User): Completable

    @Insert
     fun insertAll(vararg users: User): Completable

    @Delete
     fun delete(user: User): Completable
}
