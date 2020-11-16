package com.amitsalunke.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class UploadWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        try {
            for (i in 0..600) {
                Log.e("UploadWorker", " current status of the task $i")
            }
            return Result.success()
        } catch (ex: Exception) {
            Log.e("UploadWorker", "Something went wrong : $ex")
            return Result.failure()
        }

    }
}