package com.amitsalunke.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var mainActivityViewModel: MainActivityViewModel

    companion object {
        const val KEY_COUNT_VALUE = "Key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val button = findViewById<Button>(R.id.button)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getUser().observe(this, Observer {

        })
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
            .putInt(KEY_COUNT_VALUE, 1000)
            .build()

        val uploadWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        workManager.enqueue(uploadWorkRequest)

        //incase of work chaining
        //workManager.beginWith(name of the worker).then(name of the worker).then(name of the worker).enqueue()

        //parallel workers


        workManager.getWorkInfoByIdLiveData(uploadWorkRequest.id).observe(this, Observer {
            text.text = it.state.name
            Log.e(
                "MainAct",
                " Current state name " + text
            )
            if (it.state.isFinished) {
                val data = it.outputData
                val msg: String? = data.getString(UploadWorker.KEY_WORKER)
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

            }
        }
        )
    }

    private fun setPeriodicWorkRequest(){

        val workManager1 = WorkManager.getInstance(applicationContext)
        val periodicWorkRequest = PeriodicWorkRequest
            .Builder(UploadWorker::class.java,16,TimeUnit.MINUTES)
            .build()

        workManager1.enqueue(periodicWorkRequest)


    }
}
