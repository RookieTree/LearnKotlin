package com.tree.learnkotlin

import kotlinx.coroutines.*
import java.util.concurrent.Executors


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn23
 *  @创建者:   rookietree
 *  @创建时间:  2022/9/20 17:07
 *  @描述：    异常
 */
class Learn23 {
    fun main() = runBlocking {
        val job = launch(Dispatchers.Default) {
            var i = 0
            while (isActive) {
                Thread.sleep(500L)
                i++
                println("i = $i")
            }
        }
        delay(2000L)
        job.cancel()
        job.join()
        println("end")
    }

    val fixedDispatchers = Executors.newFixedThreadPool(2) {
        Thread(it, "MyFixedThread").apply { isDaemon = false }
    }.asCoroutineDispatcher()

    fun main2() = runBlocking {
        val parentJob = launch(fixedDispatchers) {
            launch {
                var i = 0
                while (isActive) {
                    Thread.sleep(500L)
                    i++
                    println("First i = $i")
                }
            }

            launch {
                var i = 0
                while (isActive) {
                    Thread.sleep(500L)
                    i++
                    println("Second i = $i")
                }
            }
        }
        delay(2000L)
        parentJob.cancel()
        parentJob.join()
        println("End")
    }
}

fun main() {
    Learn23().main2()
}