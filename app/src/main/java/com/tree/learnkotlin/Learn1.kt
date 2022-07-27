package com.tree.learnkotlin


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   LearnOne
 *  @创建者:   rookietree
 *  @创建时间:  2022/7/27 10:32
 *  @描述：    TODO
 */
class Learn1 {

    var price: Int = 100

    //kotlin支持类型推导
    var price2 = 100

    val i = 0
    fun test1() {
        //i=2; i会报错，val声明的变量是不可变变量，初始化后不可修改
    }

    //在kotlin里，一切都是对象，完全面向对象
    val j = 1.toDouble() //1 被看做对象了，所以可以调用它的成员方法toDouble，而java是无法实现的

    //空安全
    // 因为kotlin中一切都是对象，那么对象就可能为空
//    val k:Double=null 这样会报错
    //因为kotlin强制要求开发者在定义变量时，指定这个变量是否可能为null
    //对于可以能为null的变量，需要在声明的时候，在变量类型后面加一个 ？
    val k: Double? = null //不会报错

    //这样可为空的变量就不能赋值给不可空的变量
    //如果有这样的需求，只需要加个判断即可
    fun test2() {
        var i1: Double = 1.0
        var j1: Double? = null
        if (j1 != null) {
            i1 = j1
        }
    }

    /**
     * kotlin数字类型
     * 整数类型默认会被退到为Int类型
     * Long类型，则需要使用"L"后缀
     * 小数默认会被推导为"Double"，不需要使用"D"后缀
     * FLoat类型，需要使用"F"后缀
     * 使用"0x"表示十六进制字面量
     * 使用"0b"表示二进制字面量
     */

    //关于数字类型的转换
    // java可以隐式转换数字类型，而Kotlin推崇显示转换
    /**
     * eg:
     * int i = 100;
     * long j = i;
     * java可以编译通过
     */
    val i1: Int = 100

    //    val j1:Long=i1 会编译报错
    val j1: Long = i1.toLong()
    //类型直接互相转换存在精度问题，出现bug不好排查
    //kotlin这样做就可以避免该情况，而且代码可读性更强，也更容易维护

    //字符串方面
    // kotlin可以使用模板，java却没有，只能用 + 拼接起来
    fun test3() {
        val name = "kotlin"
        print("Hello $name")
        //也可以引用更复杂的变量 使用{ }括起来
        val array = arrayOf("1", "2", "3")
        print("Hello ${array.get(1)}")
        //kotlin新增原始字符串，用三个引号来表示，可以用于存放复杂的多行文本
        //并且定义的时候是什么格式，最终打印也会是对应的格式。
        //java写复杂多行文本就需要写一堆加号和换行符
        val s = """
            这是一个复杂的文本
            有多行
            kotlin很方便"""
        print(s)
    }

    //数组方面
    // kotlin一般会使用arrayOf来创建数组，括号当中
    //可以用传递数组元素进行初始化，同时，kotlin编译器也会根据传入的参数进行类型推导
    fun test4() {
        val array = arrayOf("apple", "pear")
        //Kotlin中，虽然数组仍然不属于集合，但它的一些操作是跟集合统一的
        //比如获取长度，数组也是调用size，而java里是调用length
        println("Size is ${array.size}")
        println("First element is ${array[0]}")
    }

    //函数声明
    /*
    关键字    函数名          参数类型   返回值类型
    ↓        ↓                ↓       ↓      */
    fun helloFunction(name: String): String {
        return "Hello $name !"
    }/*   ↑
    花括号内为：函数体
    */

    //当函数体实际上只有一行代码时，可以直接使用 = 来连接
    fun helloFunction2(name: String): String = "Hello $name !"

    //这种写法叫单一表达式函数，去掉return
    //因为kotlin的类型推导，返回类型也可以去掉
    fun helloFunction3(name: String) = "Hello $name !"

    //函数调用
    fun createUser(
        name: String,
        age: Int,
        gender: Int,
        friendCount: Int,
        feedCount: Int,
        likeCount: Long,
        commentCount: Int
    ) {
        //..
    }

    fun createUser2(
        name: String,
        age: Int,
        gender: Int = 1,
        friendCount: Int = 0,
        feedCount: Int = 0,
        likeCount: Long = 0L,
        commentCount: Int = 0
    ) {
        //..
    }

    fun test5() {
        helloFunction("kotlon")
        //kotlin提供了新特性，叫命名参数，允许在调用函数的时候传入 形参的名字
        helloFunction(name = "kotlon")
        //当出现很多参数函数时，可以利用改特性，提升了代码可读性和维护性
        createUser(
            name = "Tom",
            age = 30,
            gender = 1,
            friendCount = 78,
            feedCount = 2093,
            likeCount = 10937,
            commentCount = 3285
        )
        //kotlin还支持参数默认值，提供默认值后，我们就可以按需传参数，没传的参数，
        //kotlin会自动帮我们填上默认值
        createUser2(
            name = "Tom",
            age = 30,
            commentCount = 3285
        )
    }

    //流程控制
    /**
     * kotlin流程控制主要有if、when、for、while，这些语句可以控制代码的执行流程
     *
     */
    //if  kotlin与java使用基本一致
    fun test6() {
        val i = 1
        if (i > 0) {
            print("Big")
        } else {
            print("Small")
        }
        //还可以用作表达式来使用
        val j = 1
        val message = if (j > 0) "Big" else "Small"
        print(message)
    }

    //在kotlin中有明确规定了类型分为“可空类型”“不可空类型”，因此，我们会经常遇到可空的变量，并且要判断它们是否为空
    fun getLength(text: String?): Int {
        return if (text != null) text.length else 0
    }

    //kotlin针对这种情况提供了一种简写,叫Elvis表达式
    fun getLength2(text: String?): Int {
        return text?.length ?: 0
    }

    //when语句
    //在代码逻辑只有两个分支的时候，一般会使用if/else，而在大于两个逻辑分支时，会使用when
    fun test7() {
        val i: Int = 1
        when (i) {
            1 -> print("一")
            2 -> print("二")
            else -> print("i 不是一也不是二")
        }
    }

    //when语句
    // 有点像java switch case语句，不过when更强大，它同时也可以作为表达式，为变量赋值
    fun test8() {
        val i: Int = 1
        val message = when (i) {
            1 -> "一"
            2 -> "二"
            else -> "i 不是一也不是二"
        }
    }
    //与switch不同的是，when表达式要求里面的逻辑分支必须是完整的。
    //如果去掉else分支，编译器将报错

    //for while语句
    //while使用上与java区别不大
    //kotlin的for语句更多的是用于迭代
    fun test9() {
        val array = arrayOf(1, 2, 3)
        for (i in array) {
            println(i)
        }
    }

    //除了迭代数组和集合意外，kotlin还支持迭代一个区间
    fun test10() {
        //定义一个区间，用..来连接数值区间的两端，左闭右闭
        val oneToThree = 1..3 // 代表 [1, 3]
        for (i in oneToThree) {
            println(i)
        }
    }

    //还可以逆序迭代一个区间
    fun test11() {
        //downTo 代表逆序，step代表步长2
        for (i in 6 downTo 0 step 2) {
            println(i)
        }
//        输出结果：6 4 2 0
        val oneToThree = 1..3 // 代表 [1, 3]
        //reversed代表倒序
        for (i in oneToThree.reversed()) {
            println(i)
        }
        //withIndex代表带下标，index num这名字是随便起即可
        for ((index,num) in oneToThree.reversed().withIndex()) {
            println("$index:$num")
        }
    }

    //kotlin中return、break、continue这些关键字基本用法与java完全相同
    fun test12() {
       for (i in 1..6) {
            if (i==2){
                print("break loop")
                break
            }
        }
        print("break finish")
    }
    //kotlin中，新引入了标签概念，让中断循环变得更加灵活多样
    //标签基本用法：xxx@
    fun test123() {
        loop@ for (i in 1..6) {
            for (j in 1..3){
                if (i==2){
                    print("break loop")
                    //这里break就会跳出标签指定所在的循环
                    //continue跟break一样的使用方法
                    break@loop
                }
            }
        }
        print("break finish")
    }
    //返回到标签
    fun foo() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return // 非局部直接返回到 foo() 的调用者
            print(it)
        }
        println("this point is unreachable")
    }
    // 输出结果 12
    fun foo2() {
        listOf(1, 2, 3, 4, 5).forEach lit@{
            if (it == 3) return@lit // 局部返回到该 lambda 表达式的调用者，即 forEach 循环
            print(it)
        }
        print(" done with explicit label")
    }
    // 输出结果 1245 done with explicit label
    //通常情况下使用隐式标签更方便，该标签名与接受该lambda的函数同名
    fun foo3() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach // 局部返回到该 lambda 表达式的调用者，即 forEach 循环
            print(it)
        }
        print(" done with implicit label")
    }
    // 输出结果 1245 done with implicit label
    //或者也可以用一个匿名函数替代lambda表达式。匿名函数内部的return语句将从该匿名函数自身返回
    fun foo4() {
        listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
            if (value == 3) return  // 局部返回到匿名函数的调用者，即 forEach 循环
            print(value)
        })
        print(" done with anonymous function")
    }
    // 输出结果 1245 done with anonymous function
    //前面几个示例中使用的局部返回类似于在常规循环中使用continue。
    //也可以通过增加另一层嵌套lambda表达式并从其中非局部返回来模拟：
    fun foo5() {
        run loop@{
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@loop // 从传入 run 的 lambda 表达式非局部返回
                print(it)
            }
        }
        print(" done with nested loop")
    }
    // 输出结果 12 done with nested loop
}