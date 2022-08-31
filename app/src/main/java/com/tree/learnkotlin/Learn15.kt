package com.tree.learnkotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn15
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/30 11:33
 *  @描述：    挂起函数
 */
suspend fun getUserInfo():String {
    withContext(Dispatchers.IO){
        delay(1000L)
    }
    return "London Boy"
}

suspend fun getFrendList(user:String):String{
    withContext(Dispatchers.IO){
        delay(1000L)
    }
    return "New Boy"
}

suspend fun getFeedList(friend:String):String{
    withContext(Dispatchers.IO){
        delay(1000L)
    }
    return "Boy List"
}

suspend fun main(){
    val user= getUserInfo()
    val friend= getFrendList(user)
    val feed= getFeedList(friend)
    //协程的魅力：以同步的方式完成异步任务
}
// suspend 挂起函数，只能在协程当中被调用，或者是被其他挂起函数调用
// 挂起和恢复，是协程的一种底层能力，而挂起函数，是这种底层能力的一种表现形式。
// 通过暴露出来的suspend关键字，开发者可以在上层，非常方便地使用这种底层能力。

//挂起函数写出来的代码可读性更好、扩展性更好、维护性更好，也更难出错。
//要定义挂起函数，只需要在普通函数的基础上，增加一个suspend关键字
//挂起函数，由于拥有挂起和恢复的能力，因此对于同一行代码来说，"="左右两边的代码分别可以执行在不同的线程之上。
//挂起函数的本质，就是callback。只是说Kotlin底层用了一个更高大上的名字，叫Continuation,