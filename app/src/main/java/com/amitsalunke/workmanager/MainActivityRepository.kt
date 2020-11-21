package com.amitsalunke.workmanager

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.amitsalunke.workmanager.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


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


    fun getUserDataFromWorker(
        applicationContext: Context,
        lifecycleOwner: LifecycleOwner
    ): LiveData<List<User>> {
        val workManager: WorkManager = WorkManager.getInstance(applicationContext)
        val downloadWorkRequest = OneTimeWorkRequest.Builder(DownloadUsersWorker::class.java)
            .build()

        workManager.enqueue(downloadWorkRequest)
        workManager.getWorkInfoByIdLiveData(downloadWorkRequest.id).observe(
            lifecycleOwner,
            Observer {
                if (it.state.isFinished) {
                    val data = it.outputData
                    val userString = data.getString(DownloadUsersWorker.KEY_USER)
                    Log.e("Repo", " value of the string:  $userString")
                    var gson = Gson()
                    var list: List<User> =
                        gson.fromJson(userString, object : TypeToken<List<User>>() {}.type)
                    repUsers.value = list
                }
            })
        return repUsers
    }


}