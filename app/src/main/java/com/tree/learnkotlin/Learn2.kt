package com.tree.learnkotlin


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn2
 *  @创建者:   rookietree
 *  @创建时间:  2022/7/27 12:09
 *  @描述：    TODO
 */
class Learn2 {
    //kotlin编译器会根据实际情况，自动生成getter和setter
    //name是val，就意味着初始化后无法被修改
    //对应到java代码里，就是只有getter没有setter
    class Person(val name: String, var age: Int) {
        fun isAdult() = age >= 18
    }

    //自定义属性getter
    //可以将isAdult()定义成Person的属性。
    class Person2(val name: String, var age: Int) {
        val isAdult
            get() = age >= 18

        //如果get()方法内部的逻辑比较复杂，仍然可以像正常函数那样，带上花括号
        val isAdult2: Boolean
            get() {
                //do something
                return age >= 18
            }

        val isAdult3 = age >= 18
    }

    //自定义属性setter
    class Person3(val name: String) {
        var age: Int = 0
            //  这就是age属性的setter，可以加上private修饰符，限制它仅可以在类的内部访问
            //       ↓
            /*private*/ set(value: Int) {
                print(value)
                //field代表了age
                field = value
            }
        // 省略
    }

    //抽象类与继承
    //与java几乎一样
    abstract class Person4(val name: String) {
        abstract fun walk()
        // 省略
    }
    //kotlin的继承与java并没有区别，只是语法上有点不一样
    //kotlin中使用冒号来表示继承
    class ZhangSan : Person4(name = "zhangsan") {
        //方法的重写表达方式也不太一样
        //java是使用注解，kotlin是用override关键字
        override fun walk() {

        }
    }
    //如果某类想被继承，必须使用open关键字修饰，否则无法继承
    //这个类里的方法也是，若想被重写，都需要添加open关键字
    open class Person5() {
        open val canWalk: Boolean = false
        open fun walk() {}
    }
    class Boy : Person5() {
        // 报错
        override val canWalk: Boolean = true

        // 报错
        override fun walk() {
        }
    }
    //java的继承是默认开放的，kotlin的继承是默认封闭的

    //接口
    //kotlin的接口跟继承实现语法是一样的
    //1、不能设置初始值；2、val可以重写get，var的get和set不能重写
    interface Behavior {
        val canWalk: Boolean

        //kotlin的接口跟java最大的差异在于，接口的方法可以有默认实现
        //如果接口方法有默认实现，就可以在子类里不用实现该方法
        fun walk() {
            if (canWalk) {
                //do something
            }
        }
    }
    class Person6(val name: String) : Behavior {
        override val canWalk: Boolean
            get() = true
    }

    //嵌套
    //java中，最常见的嵌套类分为两种：非静态内部类、静态内部类
    //kotlin也是如此
    class A {
        val name: String = ""
        fun foo() = 1

        class B {
//            val a = name   // 报错，无法访问A属性和方法
//            val b = foo()  // 报错，无法访问A属性和方法
        }
    }

    //kotlin中的普通嵌套类，本质是静态的。
    //如果想在kotlin当中定义一个普通的内部类，需要在嵌套类的前面加上inner关键字
    class A1 {
        val name: String = ""
        fun foo() = 1

        inner class B1 {
            val a = name
            val b = foo()
        }
    }
    //inner关键字，代表B1是A1类的内部类，这样就可以访问A1类的属性与方法
    //kotlin这样的设计，将默认犯错（导致内存泄漏）的风险抹掉了

    //kotlin中的特殊类
    //数据类
    //用于存放数据的类，定义一个数据类，只需要在普通的类前面加上一个关键字data即可

    // 数据类当中，构造方法最少要有一个属性
    //                  ↓
    data class Person7(val name: String, val age: Int)
    //在kotlin当中，编译器会为数据类自动生成一些有用的方法：
    /**
     * equals()；
     * hashCode()；
     * toString()；
     * componentN() 函数；
     * copy()。
     */
    fun test() {

        val tom = Person7("Tom", 18)
        val jack = Person7("Jack", 19)

        println(tom.equals(jack)) // 输出：false
        println(tom.hashCode())   // 输出：对应的hash code
        println(tom.toString())   // 输出：Person(name=Tom, age=18)
        //“val (name, age) = tom”这行代码，其实是使用了数据类的解构声明。
        //这种方式，可以让我们快速通过数据类来创建一连串的变量。
        val (name, age) = tom     // name=Tom, age=18
        println("name is $name, age is $age .")
        //copy 方法可以让我们非常方便地在创建一份拷贝的同时，修改某个属性
        val mike = tom.copy(name = "Mike")
        println(mike)             // 输出：Person(name=Mike, age=18)
    }

    //密封类
    //密封类常用来标识某种收到限制的继承结构
    //密封类是更强大的枚举类
    //想要定义枚举类需要使用sealed关键字，中文含义也代表密封
    //Android开发中，会经常使用密封类对数据进行封装。
    sealed class Result<out R> {
        data class Success<out T>(val data: T, val message: String = "") : Result<T>()
        data class Error(val exception: Exception) : Result<Nothing>()
        data class Loading(val time: Long = System.currentTimeMillis()) : Result<Nothing>()
    }

    //这个密封类，是专门用于封装网络请求结果的。在Result类中，有三个数据类，Success、Error、Loading。
    //我们讲一个网络请求结果也分为了三大类，分别代表请求成功、失败、正在进行。
    //网络请求有结果有，UI展示逻辑变得非常简单
    fun display(data: Result<String>) = when (data) {
        is Result.Success<*> -> displaySuccessUI(data)
        is Result.Error -> showErrorMsg(data)
        is Result.Loading -> showLoading()
    }

    fun displaySuccessUI(data: Result<String>) {}
    fun showErrorMsg(data: Result<String>) {}
    fun showLoading() {}
}