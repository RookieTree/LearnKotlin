package com.tree.learnkotlin

import android.widget.ImageView


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn5
 *  @创建者:   rookietree
 *  @创建时间:  2022/7/28 15:16
 *  @描述：    TODO
 */
class Learn5 {

    //object关键字
    /**
     * object关键字有三种用法
     * 可以分别定义为
     * 匿名内部类
     * 单例模式
     * 伴生对象
     */

    //object：匿名内部类
    //在kotlin中，使用object关键字来创建匿名内部类。同样，在它的内部也必须要实现它内部未实现的方法。
    //这样不仅可以用于创建接口的匿名内部类，也可以创建抽象类的匿名内部类。
    /**
     *
    image.setOnClickListener(object: View.OnClickListener {
    override fun onClick(v: View?) {
    gotoPreview()
    }
    })
     */

    interface A {
        fun funA()
    }

    interface B {
        fun funB()
    }

    abstract class Man {
        abstract fun findMan()
    }

    fun test() {
        //匿名内部类的特别之处：可以在继承一个抽象类的同时，实现多个接口
        val item = object : Man(), A, B {
            override fun funA() {
                TODO("Not yet implemented")
            }

            override fun funB() {
                TODO("Not yet implemented")
            }

            override fun findMan() {
                TODO("Not yet implemented")
            }
        }
    }

    //object：单例模式
    //直接用object修饰类即可，不需要写class关键字。
    object UserManager {
        fun login() {}
    }

    //object伴生对象
    class Person {
        object InnerSingleton {
            fun foo() {}
        }
    }

    //调用方式：Person.InnerSingleton.foo()
    //通过转成java代码，能看出这种写法中，foo()并不是静态方法
    //如果想让foo()方法变成静态，只需要加上@JvmStatic注解
    class Person2 {
        object InnerSingleton {
            @JvmStatic
            fun foo() {
            }
        }
    }

    //如果想让调用方式简化，少掉InnerSingleton这个层级，在object前加上companion关键字即可
    class Person3 {
        companion object InnerSingleton {
            @JvmStatic
            fun foo() {
            }
        }
    }

    //companion object，即伴生对象，是嵌套单例的特殊情况。
    //在伴生对象的内部，如果存在@JvmStatic修饰的方法或属性，会被挪到伴生对象外部的类当中，变成静态成员
    //object的缺点就是不支持懒加载，不支持单例时传递参数。
    //工厂模式
    class User private constructor(name: String) {
        companion object {
            @JvmStatic
            fun create(name: String): User {
                return User(name)
            }
        }
    }

    //kotlin中四种单例模式
    //懒加载委托单例模式、Double Check单例模式、抽象类模板单例以及接口单例模板
    //第一种：借助懒加载委托-----------
    object UserManager1 {
        val user by lazy {
            loadUser()
        }

        private fun loadUser(): User {
            return User.create("tom")
        }

        fun login() {}
    }

    //by lazy是kotlin中的"懒加载委托"，可以保证懒加载的同时，还能保证线程安全
    //第二种写法：伴生对象Double Check-----------
    class UserManager2 private constructor(name: String) {
        companion object {
            @Volatile
            private var INSTANCE: UserManager2? = null
            fun getInstance(name: String): UserManager2 =
                //第一次判空
                INSTANCE ?: synchronized(this) {
                    //第二次判空
                    INSTANCE ?: UserManager2(name).also { INSTANCE = it }
                }

        }
    }

    //本质上就是Java的double check
    //这种double check写法的缺点就是容易出错，而且模板一致，每个单例类都需要这样写
    //第三种写法：抽象类模板
    //类抽象模板
    abstract class BaseSingleton<in P, out T> {
        @Volatile
        private var instance: T? = null
        protected abstract fun creator(param: P): T
        fun getInstance(param: P): T = instance ?: synchronized(this) {
            instance ?: creator(param).also {
                instance = it
            }
        }
    }

    //in P,out T 是kotlin中的泛型，P和T分别代表了getInstance()的参数类型和返回类型
    class PersonManager private constructor(name: String) {
        companion object : BaseSingleton<String, PersonManager>() {
            override fun creator(param: String): PersonManager = PersonManager(param)
        }
    }

    class AnimalManager private constructor(name: String) {
        companion object : BaseSingleton<String, AnimalManager>() {
            override fun creator(param: String): AnimalManager = AnimalManager(param)
        }
    }

    //有了抽象单例模板，就可以不用重复写double check模板代码
    //第四种写法:接口模板（不被推荐）
    //这样写只是为了熟悉kotlin接口的特性
    interface ISingleton<P, T> {
        var instance: T?
        fun creator(param: P): T
        fun getInstance(param: P): T = instance ?: synchronized(this) {
            instance ?: creator(param).also { instance = it }
        }
    }
    //因为接口中，instance无法用private修饰，这样不符合单例的规范，容易被外部修改
    //instance无法使用volatile修饰，这样会引发多线程同步的问题

    /**
     * 总结
     * 如果我们的单例占用内存很小，并且对内存不敏感，不需要传参，直接使用 object 定义的单例即可。
     * 如果我们的单例占用内存很小，不需要传参，但它内部的属性会触发消耗资源的网络请求和数据库查询，我们可以使用 object 搭配 by lazy 懒加载。
     * 如果我们的工程很简单，只有一两个单例场景，同时我们有懒加载需求，并且 getInstance() 需要传参，我们可以直接手写 Double Check。
     * 如果我们的工程规模大，对内存敏感，单例场景比较多，那我们就很有必要使用抽象类模板 BaseSingleton 了。
     */


}