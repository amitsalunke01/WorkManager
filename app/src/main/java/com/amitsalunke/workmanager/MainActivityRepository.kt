package com.amitsalunke.workmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amitsalunke.workmanager.model.User

class MainActivityRepository {
    val repUsers: MutableLiveData<List<User>> = MutableLiveData()

    fun getUsers(): LiveData<List<User>> {
        val users: List<User> = listOf(
            User(0, "Amit"),
            User(1, "Rima"),
            User(2, "abc"),
            User(3, "PQR")
        )
        repUsers.value = users
        return repUsers
    }
}