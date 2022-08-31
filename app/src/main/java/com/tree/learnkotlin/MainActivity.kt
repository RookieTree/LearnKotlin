package com.tree.learnkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        System.setProperty("kotlinx.coroutines.debug", "on")
        findViewById<TextView>(R.id.textview).setOnClickListener(){
            Toast.makeText(baseContext, "Debug", Toast.LENGTH_SHORT).show()
        }
        runBlocking {
            startJob()
        }
    }

    suspend fun startJob() = runBlocking {
        val job = launch {
            logX("First coroutine start!")
            delay(10000L)
            logX("First coroutine end!")
        }
        job.join()
    }

}