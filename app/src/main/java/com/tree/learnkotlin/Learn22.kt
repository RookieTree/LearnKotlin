package com.tree.learnkotlin

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Executors


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn22
 *  @创建者:   rookietree
 *  @创建时间:  2022/9/8 19:16
 *  @描述：    协程的并发
 */
class Learn22 {

    // 代码段1
    fun main() = runBlocking {
        val mySingleDispatcher = Executors.newSingleThreadExecutor {
            Thread(it, "MySingleThread").apply { isDaemon = true }
        }.asCoroutineDispatcher()
        var i = 0
        val jobs = mutableListOf<Job>()
        repeat(10) {
            val job = launch(mySingleDispatcher) {
                repeat(1000) {
                    i++
                }
            }
            jobs.add(job)
        }
        jobs.joinAll()
        println("i = $i")
    }

    fun main2() = runBlocking {
        val mutex = Mutex()
        var i = 0
        val jobs = mutableListOf<Job>()
        repeat(10) {
            val job = launch(Dispatchers.Default) {
                repeat(1000) {
                    mutex.withLock {
                        i++
                    }
                }
            }
            jobs.add(job)
        }
        jobs.joinAll()
        println("i = $i")
    }
}

fun main() {
    Learn22().main()
    Learn22().main2()
}