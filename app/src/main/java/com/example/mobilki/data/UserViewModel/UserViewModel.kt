package com.example.mobilki.data.UserViewModel

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.mobilki.data.enity.User
import com.example.mobilki.data.repository.UserRepository

import com.example.mobilki.data.AppDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

// Центр связи между репозиторием и пользовательским интерфейс
// LiveData как Observer
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _user



    private var userRepository: UserRepository =
        UserRepository(AppDatabase.getDatabase(application).userDao())

    fun getAllUsers() {
        userRepository.getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _user.postValue(it)
            }, {
                _user.postValue(emptyList())
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun loadAllUsers(): Maybe<List<User>> {
        return userRepository.getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { _user.value = it }
            .onErrorReturn { emptyList() }
    }


    fun getUserById(id: String): Single<User> {
        return userRepository.getUserById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun getUserByNumber(phoneNumber: String): Single<User> {
        return userRepository.getUserByNumber(phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun updateUser(user: User) {
        userRepository.updateUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getAllUsers()
            }, {
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun insertUsers(vararg users: User) {
        userRepository.insertUsers(*users)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getAllUsers()
            }, {
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun deleteUser(user: User) {
        userRepository.deleteUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getAllUsers()
            }, {
            }).let {
                compositeDisposable.add(it)
            }
    }

    fun checkIfUserExistsByNumber(phoneNumber: String): Boolean {
        var userExists = false
        userRepository.getUserByNumber(phoneNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userExists = true
            }, {
                userExists = false
            }).let {
                compositeDisposable.add(it)
            }
        return userExists
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}







