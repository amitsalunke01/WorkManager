package com.amitsalunke.workmanager

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amitsalunke.workmanager.model.User

class MainActivityViewModel : ViewModel() {
    var users: LiveData<List<User>> = MutableLiveData()
    private var mainActivityRepository = MainActivityRepository();

    fun getUser(): LiveData<List<User>> {
        users = mainActivityRepository.getUsers()
        return users
    }

    fun getWorkerUser(
        applicationContext: Context,
        lifecycleOwner: LifecycleOwner
    ): LiveData<List<User>> {
        users = mainActivityRepository.getUserDataFromWorker(applicationContext, lifecycleOwner)
        return users
    }

}