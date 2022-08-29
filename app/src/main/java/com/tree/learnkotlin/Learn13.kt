package com.tree.learnkotlin

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn13
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/29 16:59
 *  @描述：    协程
 */

fun main() {
    val list = getList()
    printList(list)

    runBlocking {
        launch {
            repeat(3) {
                delay(1000L)
                println("Print-1:${Thread.currentThread().name}")
            }
        }
        launch {
            repeat(3) {
                delay(900L)
                println("Print-2:${Thread.currentThread().name}")
            }
        }
        delay(3000L)
    }
}

fun getList(): List<Int> {
    val list = mutableListOf<Int>()
    println("Add 1")
    list.add(1)
    println("Add 2")
    list.add(2)
    println("Add 3")
    list.add(3)
    println("Add 4")
    list.add(4)

    return list
}

fun printList(list: List<Int>) {
    val i = list[0]
    println("Get$i")
    val j = list[1]
    println("Get$j")
    val k = list[2]
    println("Get$k")
    val m = list[3]
    println("Get$m")
}

/* 运行结果：
Add 1
Add 2
Add 3
Add 4
Get1
Get2
Get3
Get4
*/


// 看不懂代码没关系，目前咱们只需要关心代码的执行结果
fun main2() = runBlocking {
    val sequence = getSequence()
    printSequence(sequence)
}

fun getSequence() = sequence {
    println("Add 1")
    yield(1)
    println("Add 2")
    yield(2)
    println("Add 3")
    yield(3)
    println("Add 4")
    yield(4)
}

fun printSequence(sequence: Sequence<Int>) {
    val iterator = sequence.iterator()
    val i = iterator.next()
    println("Get$i")
    val j = iterator.next()
    println("Get$j")
    val k = iterator.next()
    println("Get$k")
    val m = iterator.next()
    println("Get$m")
}

/*
输出结果：
Add 1
Get1
Add 2
Get2
Add 3
Get3
Add 4
Get4
*/
/**
 * 广义的协程，可以理解为“互相协作的程序”，也就是“Cooperative-routine”。
 * 协程框架，是独立于 Kotlin 标准库的一套框架，它封装了 Java 的线程，对开发者暴露了协程的 API。
 * 程序当中运行的“协程”，可以理解为轻量的线程；
 * 一个线程当中，可以运行成千上万个协程；
 * 协程，也可以理解为运行在线程当中的非阻塞的 Task；
 * 协程，通过挂起和恢复的能力，实现了“非阻塞”；
 * 协程不会与特定的线程绑定，它可以在不同的线程之间灵活切换，而这其实也是通过“挂起和恢复”来实现的。
 *
 */