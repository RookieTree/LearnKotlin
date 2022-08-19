package com.tree.learnkotlin

import java.security.acl.Owner
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn9
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/18 11:42
 *  @描述：    委托
 */
class Learn9 {

    //委托类
    interface DB {
        fun save()
    }

    class SqlDB() : DB {
        override fun save() {
            println("save to sql")
        }
    }

    class GreenDaoDB() : DB {
        override fun save() {
            println("save to greendao")
        }
    }

    //通过 by 将接口实现委托给 db
    class UniversalDB(db: DB) : DB by db

    fun main() {
        //UniversalDB 就相当于一个壳，虽然实现了DB接口，但并不关心怎么实现
        //传不同的委托对象进去，就会有不同的行为
        UniversalDB(SqlDB()).save()
        UniversalDB(GreenDaoDB()).save()
    }

    //委托属性
    //kotlin 委托类 委托的是接口方法，而 委托属性 委托的，则是属性的getter、setter
    //kotlin提供了好几种标准委托，包括两个属性之间的直接委托、by lazy懒加载委托、
    //Delegates.observable观察者委托，以及by map映射委托。
    //前两个使用频率比较高，后面两个频率比较低

    //将属性A委托给属性B
    class Item {
        var count: Int = 0
        var total: Int by ::count
        //将total属性的getter和setter都委托给了count
        //这种类型的委托，对软件版本之间的兼容很有效，比如服务端更换字段，通过这个可以兼容旧版本字段

    }

    //懒加载委托
    val data: String by lazy {
        request()
    }

    fun request():String{
        println("执行网络请求")
        return "网络数据"
    }

    fun main2() {
        println("开始")
        println(data)
    }
    //通过 by lazy{} ，就可以实现属性的懒加载了。使用到才去加载数据

    //Delegates.observable观察者委托，观察旧值新值
    //它具有三个参数：分配给的属性、旧值和新值
    class User {
        var name:String by Delegates.observable("null"){
            property, oldValue, newValue ->
            println("$property:$oldValue->$newValue")
        }
    }
    fun main3(){
        val user=User()
        user.name="first"
        user.name="second"
    }

    //by map 映射委托
    class User2(val map:Map<String,Any?>){
        val name: String by map
        val age: Int by map
    }

    val user = User2(
        mapOf(
            "name" to "john",
            "age" to 20
        )
    )

    //自定义委托
    //可以根据需求实现自己的属性委托
    class StringDelegate(private var s:String="hello"){
        operator fun getValue(thisRef: Owner, property: KProperty<*>): String {
            return s
        }

        operator fun setValue(thisRef: Owner, property: KProperty<*>, value: String) {
            s = value
        }
    }

    class Owner{
        var text:String by StringDelegate()
    }

    //提供委托
    class StringDelegate2(private var s: String = "Hello") : ReadWriteProperty<Owner2, String> {
        override fun getValue(thisRef: Owner2, property: KProperty<*>): String {
            return s
        }

        override fun setValue(thisRef: Owner2, property: KProperty<*>, value: String) {
            s = value
        }
    }
    class SmartDelegator {
        operator fun provideDelegate(
            thisRef: Owner2,
            prop: KProperty<*>
        ): ReadWriteProperty<Owner2, String> {
            return if (prop.name.contains("log")) {
                StringDelegate2("log")
            } else {
                StringDelegate2("normal")
            }
        }
    }

    class Owner2 {
        var normalText: String by SmartDelegator()
        var logText: String by SmartDelegator()
    }
}

//属性可见性封装
class Model{
    //私有化set方法，不允许外部改变data值
    var data:String =""
        private set
    private fun load(){
        data="请求结果"
    }
    //通过委托语法，将data的getter委托给了_data属性
    //而_data这个属性的类型是MutableList,
    //由于是private修饰，类的外部无法直接访问，达到了类的类外只能访问数据的目的
    //这个就利用到了委托属性
    val data2: List<String> by ::_data
    private val _data: MutableList<String> = mutableListOf()
    fun load2(){
        _data.add("hello")
    }
}

//数据与view的绑定，一般是用databinding，但是也可以用委托来实现

fun main() {
    Learn9().main3()
    println()
    println(Learn9().user.age)
    println(Learn9().user.name)
    val owner2= Learn9.Owner2()
    println(owner2.normalText)
    println(owner2.logText)
    val model=Model()

}

