package com.example.mobilki.data.repository

import com.example.mobilki.data.dao.UserDao
import com.example.mobilki.data.enity.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

class UserRepository(private val userDao: UserDao) {

     fun getAllUsers(): Maybe<List<User>> { //пользователи в виде списка, Maybe может быть пустым
        return userDao.getAll()
    }

    fun getUserById(uid: String): Single<User> {
        return userDao.findById(uid)
    }

     fun getUserByNumber(phoneNumber: String): Single<User> {
        return userDao.findByNumber(phoneNumber)
    }

    fun updateUser(user: User): Completable { //ничего не возвращает
        return userDao.updateUser(user)
    }

    fun insertUsers(vararg users: User): Completable {
        return userDao.insertAll(*users)
    }

    fun deleteUser(user: User): Completable {
        return userDao.delete(user)
    }

    fun checkIfUserExistsByNumber(phoneNumber: String): Boolean {
        return userDao.findByNumber(phoneNumber) != null
    }



}

