package com.tree.learnkotlin


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn10
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/19 16:54
 *  @描述：    泛型与逆变
 */
open class TV {
    open fun turnOn() {}
}

class XiaoMiTV1 : TV() {
    override fun turnOn() {}
}

class Controller<T> {
    fun turnOn(tv: T) {}
    fun turnOff(tv: T) {}
}

fun buy(controller: Controller<XiaoMiTV1>) {
    val xiaoMiTV1 = XiaoMiTV1()
    controller.turnOn(xiaoMiTV1)
}

//使用处型变，在修改泛型参数的使用处代码
fun buy2(controller: Controller<in XiaoMiTV1>) {
    val xiaoMiTV1 = XiaoMiTV1()
    controller.turnOn(xiaoMiTV1)
}

//声明处型变，修改泛型源头
class Controller2<in T> {
    fun turnOn(tv: T) {}
}

fun foo(tv: TV) {}

//型变，是为了解决泛型的不变性问题。
//泛型是不变的
//型变会导致父子关系颠倒

//而泛型的协变，则会让父子关系和原来一致
open class Food {}
class KFC: Food(){

}
class Restaurant<T>{
   /* fun orderFood():T{

    }*/
}

////////////////////////////////
/*
泛型，是对程序的一种抽象。
从型变的位置来分类的话，分为使用处型变和声明处型变。
从型变的父子关系来分类的话，分为逆变和协变。逆变表示父子关系颠倒了，而协变表示父子关系和原来一致。
型变的口诀：泛型作为参数，用 in；泛型作为返回值，用 out。在特殊场景下，同时作为参数和返回值的泛型参数，我们可以用 @UnsafeVariance 来解决型变冲突。
星投影，就是当我们对泛型的具体类型不感兴趣的时候，直接传入一个“星号”作为泛型的实参。
Java中的协变：<? extends T>
Java中的逆变：<? super Object>
Java中的“星投影”：<?>
 */


fun main() {
    foo(XiaoMiTV1())
    //使用型变后，以下代码就不会报错
    val controller = Controller<TV>()
    buy2(controller)
    val controller2 = Controller2<XiaoMiTV1>()
    controller2.turnOn(XiaoMiTV1())
}

