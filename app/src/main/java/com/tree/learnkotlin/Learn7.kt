package com.tree.learnkotlin

import android.view.View
import android.widget.ImageView


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn7
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/10 17:00
 *  @描述：    TODO
 */
class Learn7 {
    //高阶函数
//         (Int,  Int) ->Float 这就是 add 函数的类型
//           ↑     ↑      ↑
    fun add(a: Int, b: Int): Float {
        return (a + b).toFloat()
    }

    fun add1(a: Int): Float {
        return (a).toFloat()
    }

    //函数类型的规律：将函数的"参数类型"和"返回值类型"抽象出来后，就得到了"函数类型"。
    //(Int,Int)->Float就代表了参数类型是两个Int，返回值类型是Float的函数类型
    //函数引用：函数也可以被引用赋值给变量
    var function1: (Int, Int) -> Float = ::add
    var function2: (Int) -> Float = ::add1

    //高阶函数是将函数用作参数或返回值的函数

    fun test(function: (Int, Int) -> Int): Float {
        return function1(1, 2)
    }
    //Lambda表达式就是作为函数参数的，可以理解为函数的简写
    //SAM，意思是只有一个抽象方法的类或者接口。
    //SAM代表着只有一个抽象方法的接口，只要是符合SAM要求的接口，编译器就能进行SAM转换。
    //也就是可以用Lambda表达式。
    //SAM叫做函数式接口，限制有两点
    //1.必须是接口，抽象类不行
    //2.该接口有且只有一个抽象的方法，抽象方法必须是1，默认实现的方法可以有多个

    //当一个函数的参数是SAM时，同样也可以使用Lambda作为参数。
    //既可以用匿名内部类的方式传参，也可以使用Lambda的方式传参。
    //lambda表达式的8种写法
    var image = ImageView(MainActivity())

    //1.利用object的匿名内部类
    fun test1() {
        image.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                TODO("Not yet implemented")
            }
        })
    }

    //2.可以省略object
    fun test2() {
        image.setOnClickListener(View.OnClickListener { v: View? ->
            TODO()
        })
    }

    //3.因为kotlin的Lambda表达式是不需要SAM Constructor，所以也可以被删掉
    fun test3() {
        image.setOnClickListener({ v: View? ->
            TODO()
        })
    }

    //4.Kotlin支持类型推导，所以View可以被删掉
    fun test4() {
        image.setOnClickListener({ v ->
            TODO()
        })
    }

    //5.Kotlin Lambda表达式只有一个参数的时候，可以被写成it
    fun test5() {
        image.setOnClickListener({ it ->
            TODO()
        })
    }

    //6.Kotlin Lambda的it可以被省略
    fun test6() {
        image.setOnClickListener({
            TODO()
        })
    }

    //7.Kotlin Lambda作为函数的最后一个参数时，Lambda可以被挪到外面：
    fun test7() {
        image.setOnClickListener() {
            TODO()
        }
    }

    //8.当Kotlin只有一个Lambda作为函数参数时，()可以被省略
    fun test8() {
        image.setOnClickListener {
            TODO()
        }
    }

    //带接收者的函数类型，从外表上看等价于成员方法。
    //但从本质上说，仍然是通过编译器注入this来实现的。

}

abstract class BaseSingleton<P, T> {
    @Volatile
    private var instance: T? = null
    protected abstract var creator: (P) -> T
    fun getInstance(param: P): T =
        instance ?: synchronized(this) {
            instance ?: creator(param).also {
                instance = it
            }
        }
}

class PersonManager private constructor(name: String) {
    companion object : BaseSingleton<String, PersonManager>() {
        override var creator: (String) -> PersonManager = ::PersonManager
    }
}

var whs1: (Int, Int) -> Int = ::plus

fun whs(w: Int, h: Int, s: (Int, Int) -> Int): Int {
    return s(w, h)
}

fun plus(a: Int, b: Int): Int {
    return a + b
}

val list = listOf(1, 2, 3, 4, 5, 6, 7, 8)
var whs3 = list.map {
    it * 2
}
val list2 = listOf(1..5, 13 downTo 9)
val whs4 = list2.flatMap { it ->
    it.map { num ->
        "whs$num"
    }
}


fun main() {
    val instance = Learn7()
    var c = instance.test { a, b -> a + b }
    var d = whs(10, 8, ::plus)
    var e = whs(10, 8, whs1)
    var f = whs(8, 8) { w, h ->
        w + h
    }
    var g = whs(8, 8) { w, h ->
        w + h
    }
    println("$f")

    whs3.forEach(::println)
    whs4.forEach(::println)
    val whs5 = whs3.filter { it < 10 }
    whs5.forEach(::println)
    val whs6 = whs3.takeWhile { it < 15 }
    whs6.forEach(::println)

}