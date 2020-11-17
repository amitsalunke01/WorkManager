package com.amitsalunke.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    companion object {
        val KEY_WORKER: String = "Key_Worker"
    }

    override fun doWork(): Result {
        try {
            val count: Int = inputData.getInt(MainActivity.KEY_COUNT_VALUE, 0)
            for (i in 0..count) {
                Log.e("UploadWorker", " current status of the task $i")
            }
            val time = SimpleDateFormat("dd/M/yyyy h:mm:ss")
            val currentDate = time.format(Date())

            val outPutData = Data.Builder()
                .putString(KEY_WORKER, currentDate)
                .build()

            return Result.success(outPutData)
        } catch (ex: Exception) {
            Log.e("UploadWorker", "Something went wrong : $ex")
            return Result.failure()
        }

    }
}