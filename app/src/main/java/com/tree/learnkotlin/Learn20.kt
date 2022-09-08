package com.tree.learnkotlin

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn20
 *  @创建者:   rookietree
 *  @创建时间:  2022/9/8 15:11
 *  @描述：    Flow 冷的
 */
class Learn20 {
    fun main() = runBlocking {
        flow {
            emit(1)
            emit(2)
            emit(3)
            emit(4)
            emit(5)
            emit(6)
        }.filter { it > 2 }
            .map { it * 2 }
            .take(2)
            .collect {
                println(it)
            }
        //flow里 emit{} 就是发射、发送数据的意思
        //collect{}，终止操作符或者叫末端操作符，用来终止Flow数据流，并且接受这些数据
        //除了用flow{}创建Flow以外，还可以使用flowOf()这个函数
        flowOf(1, 2, 3, 4, 5).filter { it > 2 }
            .map { it * 2 }.take(2).collect { println(it) }

        //中间操作符（一些专门为Flow设计的）
        //onStart、onCompletion 监听生命周期
        flowOf(1, 2, 3, 4, 5).take(2)
            .filter {
                println("filter $it")
                it > 2
            }
            .map {
                println("map $it")
                it * 2
            }.onStart {
                println("onStart")
            }.onCompletion {
                println("oncomplete")
            }.collect {
                println("collect $it")
            }
        //onStart和onCompletion的执行顺序，跟他在Flow当中的位置无关。
        //但其他操作符，take、filter、map等跟位置有关系，位置不同结果不同

        /* flow {
             emit(1)
             emit(2)
             emit(3)
         }.map { it * 2 }
             .catch { println("catch $it") }
             .filter { it / 0 > 1 }
             .collect {
                 println(it)
             }*/
        //catch操作符可以捕获flow中异常，只管catch操作符上面的异常
        //如果想处理下游的异常，可以在collect里try catch
        flow {
            emit(1)
            emit(2)
            emit(3)
        }.map { it * 2 }
            .onCompletion { println("compele") }
            .collect {
                try {
                    println("coll $it")
                    throw IllegalStateException()
                } catch (e: Exception) {
                    println("cat $e")
                }
            }
        //所以针对Flow当中的异常处理，有两种手段，一个是用catch操作符捕获上游的异常
        //另外就是try catch 来处理下游的异常

        //切换Context: flowOn、launchIn
    }

}

fun main() = Learn20().main()

