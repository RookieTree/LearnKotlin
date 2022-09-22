package com.tree.learnkotlin

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn24
 *  @创建者:   rookietree
 *  @创建时间:  2022/9/21 10:38
 *  @描述：    TODO
 */
fun <T : Any> KtCall<T>.asFlow(): Flow<T> = callbackFlow {
    val call = call(object : Callback<T> {
        override fun onSuccess(data: T) {
            //传递成功数据
            trySendBlocking(data).onSuccess {
                close()
            }.onFailure {
                close(it)
            }
        }

        override fun onFailure(throwable: Throwable) {
            //传递失败数据
            close(throwable)
        }

    })
    //响应协程取消
    awaitClose {
        call.cancel()
    }
}

data class Student2(val name: String = "", val score: Int = 0)

val class1 = listOf(
    Student2("小明", 83),
    Student2("小红", 92),
    Student2("小李", 50),
    Student2("小白", 67),
    Student2("小琳", 72),
    Student2("小刚", 97),
    Student2("小强", 57),
    Student2("小林", 86)
)

class Learn24 {
    fun groupStudent() {
        val result = class1.groupBy {
            "${it.score / 10}0 分组"
        }
        println(result)
    }

    fun sumSocre() {
        val sum1 = class1.sumOf { it.score }
        val sum2 = class1.map { it.score }.reduce { acc, score ->
            println("acc: $acc, score: $score,acc+score:${acc + score}")
            acc + score
        }
        val sum3 = class1.map { it.score }.fold(0) { acc, score ->
            acc + score
        }
        println(sum1)
        println(sum2)
        println(sum3)
    }
}

fun main() {
//    Learn24().groupStudent()
    Learn24().sumSocre()
}