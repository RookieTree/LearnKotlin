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

    interface A{
        fun funA()
    }
    interface B{
        fun funB()
    }
    abstract class Man{
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
    object UserManager{
        fun login(){}
    }

}