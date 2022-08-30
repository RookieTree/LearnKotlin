package com.tree.learnkotlin

import kotlinx.coroutines.*
import kotlin.properties.Delegates


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn14
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/29 20:00
 *  @描述：    启动协程
 */
fun main() {
    GlobalScope.launch {
        delay(1000L)
        println("Hello world")
    }
    Thread.sleep(2000L)

    runBlocking {
        println("First:${Thread.currentThread().name}")
        delay (1000L)
        println ("Hello First!")
    }
    runBlocking {
        println("Second:${Thread.currentThread().name}")
        delay (1000L)
        println ("Hello Second!")
    }
    runBlocking {
        println("Third:${Thread.currentThread().name}")
        delay (1000L)
        println ("Hello Third!")
    }
    // 删掉了 Thread.sleep
    println("Process end!")

}
//runBlocking 跟GlobalScope.launch一样也可以启动协程
//但是runBlocking 回阻塞当前线程的执行。只推荐用于连接线程与协程
// 大部分情况下，都应该只用于编写demo或者是测试代码
// 千万不要在生产环境中使用runBlocking

/**
 * 三种启动协程的方式，分别是launch、runBlocking、async
 * launch，是典型的“Fire-and-forget”场景，它不会阻塞当前程序的执行流程，使用这种方式的时候，我们无法直接获取协程的执行结果。它有点像是生活中的射箭。
 * runBlocking，我们可以获取协程的执行结果，但这种方式会阻塞代码的执行流程，因为它一般用于测试用途，生产环境当中是不推荐使用的。
 * async，则是很多编程语言当中普遍存在的协程模式。它像是结合了 launch 和 runBlocking 两者的优点。它既不会阻塞当前的执行流程，还可以直接获取协程的执行结果。它有点像是生活中的钓鱼。
 */


