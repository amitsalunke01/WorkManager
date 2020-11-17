package com.amitsalunke.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val KEY_COUNT_VALUE = "Key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            setOneTimeWorkRequest()
        }
    }

    private fun setOneTimeWorkRequest() {
        val workManager = WorkManager.getInstance(applicationContext)

        val constraints = Constraints.Builder()
            //.setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val data: Data = Data.Builder()
            .putInt(KEY_COUNT_VALUE,1000)
            .build()

        val uploadWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        workManager.enqueue(uploadWorkRequest)

        workManager.getWorkInfoByIdLiveData(uploadWorkRequest.id).observe(this, Observer {
            text.text = it.state.name
            Log.e(
                "MainAct",
                " Current state name " + text
            )
        }
        )
    }
}
