package com.amitsalunke.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.amitsalunke.workmanager.model.User
import com.google.gson.Gson

class DownloadUsersWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    companion object {
        val KEY_USER: String = "KEY_USER"
    }

    override fun doWork(): Result {
        try {
            val users: List<User> = listOf(
                User(0, "Amit"),
                User(1, "Rima"),
                User(2, "abc"),
                User(3, "PQR")
            )

            val userString: String = convertToString(users)
            val outPutData = Data.Builder()
                .putString(KEY_USER, userString)
                .build()

            return Result.success(outPutData)
        } catch (ex: Exception) {
            return Result.failure()
        }
    }

    fun convertToString(user: List<User>): String {
        var gson = Gson()
        var jsonString = gson.toJson(user)
        return jsonString
    }
}