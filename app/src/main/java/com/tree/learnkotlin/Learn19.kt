package com.tree.learnkotlin

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn19
 *  @创建者:   rookietree
 *  @创建时间:  2022/9/7 18:52
 *  @描述：    Channel 管道 热的
 */
class Learn19 {


    fun main2222() = runBlocking {
//    val channel = Channel<Int>()
        /* launch {
             (1..3).forEach {
                 channel.send(it)
                 logX("send: $it")
             }
             //channel用完及时关闭
             channel.close()
         }*/
        /* launch {
             for (i in channel){
                 logX("receive: $i")
             }
         }*/

        //使用produce{}后，不用手动调用close()方法，produce会自动帮忙调用close方法
        val channel: ReceiveChannel<Int> = produce {
            (1..3).forEach {
                send(it)
                logX("send: $it")
            }
        }
        channel.receive()//1
        channel.receive()//2
        channel.receive()//3
        channel.receive()//异常，当channel被关闭时，不能再接收了
        logX("end")
        //如果不及时关闭channel,后面再继续receive的话，会导致后面的程序被永久挂起

        //注意：最好不用channel.receive()。即使配合isClosedForReceive这个判断条件。
        //可以使用channel.consumeEach{}
        val channel2: ReceiveChannel<Int> = produce(capacity = 3) {
            (1..300).forEach {
                send(it)
                println("send $it")
            }
        }
        channel2.consumeEach {
            println("receive $it")
        }
        logX("end2")
        //如果要读取channel中的数据时，
        //一定要使用for循环，或者是channel.consumeEach{}
        //千万不要直接调用channel.receive()
        /**
         * 补充：在某些特殊场景下，如果我们必须要自己来调用 channel.receive ()，
         * 那么可以考虑使用 receiveCatching ()，它可以防止异常发生。
         */

    }
    /**
     * 总结：
     * Channel 是一个管道，当我们想要用协程传递多个数据组成的流的话，就没办法通过挂起函数、async 来实现了。这时候，Channel 是一个不错的选择。
     * 我们可以通过 Channel () 这个顶层函数来创建 Channel 管道。在创建 Channel 的时候，有三个重要参数：capacity 代表了容量；onBufferOverflow 代表容量满了以后的应对策略；onUndeliveredElement 则是一个异常回调。
     * 在某些场景下，比如 “发送方对于数据是否被接收方十分关心” 的情况下，可以注册这个回调。
     * Channel 有两个关键的方法：send ()、receive ()，前者用于发送管道数据，后者用于接收管道数据。
     * 但是，由于 Channel 是存在关闭状态的，如果我们直接使用 receive ()，就会导致各种问题。因此，对于管道数据的接收方来说，我们应该尽可能地使用 for 循环、consumeEach {}。
     * Channel 是 “热” 的。这是因为 “不管有没有接收方，发送方都会工作”。最后，我们也分析了 Channel 的源码定义，发现它其实是 SendChannel、ReceiveChannel 这两个接口的组合。而我们也可以借助它的这个特点，实现 “对读取开放，对写入封闭” 的设计。
     */
}