package com.tree.learnkotlin

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn16
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/30 14:44
 *  @描述：    协程生命周期
 */

/**
 * launch 和 async 启动协程，返回值类型分别是Job和Deferred
 * 而Deferred 也是继承于Job的。
 */

fun main22()= runBlocking {
    val job=launch{
        delay(1000L)
    }
    job.log()
    job.cancel()
    job.log()
    delay(1500L)
}

fun Job.log(){
    logX("""
        isActive=$isActive
        isCancelled=$isCancelled
        isCompleted=$isCompleted
    """.trimIndent())
}

fun logX(any:Any?){
    print("""
==================================
$any
Thread:${Thread.currentThread().name}
==================================""".trimIndent())
}

//结构化并发
//就是带有结构和层级的开发。
//协程之间存在父子关系

/**
 * 协程是有生命周期的，是结构化的
 * Job，相当于协程的句柄，相当于协程的控制器
 * Job，在它的内部，维护了一系列的生命周期状态，也对应着协程的生命周期状态。
 * 通过 Job，我们可以监测协程的状态，比如 isActive、isCancelled、isCompleted；
 * 另外，我们也可以一定程度地操控协程的状态，比如 start()、cancel()。
 * 还可以通过Job.invokeOnCompletion {}来监听协程执行完毕的事件；
 * 通过 Job.join() 这个挂起函数，我们可以挂起当前协程的执行流程，等到协程执行完毕以后，再恢复执行后面的代码。
 * 而对于 Deferred.await()，它的行为模式和 Job.join() 类似，只是它还会返回协程的执行结果。
 * 一个 Job 可以拥有多个 ChildJob；对应的，协程也可拥有多个“子协程”。
 * 那么结构化并发带来的最大优势就在于，我们可以实现只控制“父协程”，从而达到控制一堆子协程的目的。
 * parentJob.join() 不仅仅只会等待它自身执行完毕，还会等待它内部的 job1、job2、job3 执行完毕。parentJob.cancel() 同理。
 *
 */

// 代码段15

fun main() = runBlocking {
    val job = launch {
        logX("First coroutine start!")
        delay(1000L)
        logX("First coroutine end!")
    }

    job.join()
    print("job join after")
    val job2 = launch(job) {
        logX("Second coroutine start!")
        delay(1000L)
        logX("Second coroutine end!")
    }
    job2.join()
    logX("Process end!")
}

