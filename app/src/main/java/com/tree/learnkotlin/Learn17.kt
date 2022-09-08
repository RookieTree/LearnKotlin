package com.tree.learnkotlin

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.coroutineContext
import kotlin.math.log


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn17
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/31 15:02
 *  @描述：    context
 */
/**
 * Kotlin 官方提供了如下内置Dispatchers
 * Dispatchers.IO：IO密集型任务的线程池。内部的线程数量一般会更多一些
 * Dispatchers.MAIN：代表UI线程
 * Dispatchers.Unconfined：代表无所谓，当前协程可以运行在任意线程之上
 * Dispatchers.Default：CPU密集型任务的线程池。内部的线程个数与CPU核心数量保持一致，最小限制为2
 * 注意：Dispatchers.IO底层是可以复用Dispatchers.Default当中的线程的。
 */
fun main2() = runBlocking {
    //仅用于测试
    val scope= CoroutineScope(Job())
    CoroutineScope(Job() + mySingleDispatcher)
    scope.launch {
        logX("First start")
        delay(1000L)
        logX("First end")
    }
    scope.launch {
        logX("Second start")
        delay(1000L)
        logX("Second end")
    }
    scope.launch {
        logX("Third start")
        delay(1000L)
        logX("Third end")//不会执行
    }
    logX("scope start")
    delay(500L)
    scope.cancel()
    logX("scope cancel")
    delay(1000L)

}

val mySingleDispatcher = Executors.newSingleThreadExecutor {
    Thread(it, "MySingleThread").apply { isDaemon = true }
}.asCoroutineDispatcher()

suspend fun testContext()= coroutineContext
fun main()= runBlocking {
    print(testContext())
}



