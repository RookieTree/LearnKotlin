package com.tree.learnkotlin

import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn11
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/25 10:47
 *  @描述：    注解和反射
 */
//注解和反射 存在的意义是为了提高代码的灵活性
//注解，就是 程序代码的一种补充
@Target(
    AnnotationTarget.CLASS,//类
    AnnotationTarget.FUNCTION,//函数
    AnnotationTarget.PROPERTY,//属性
    AnnotationTarget.CONSTRUCTOR,//构造
    AnnotationTarget.PROPERTY_SETTER,//setter
    AnnotationTarget.PROPERTY_GETTER,//getter
    AnnotationTarget.TYPEALIAS//类型别名
)
@MustBeDocumented
public annotation class Deprecated(
    val message: String,//代表了废弃的提示信息
    val replaceWith: ReplaceWith = ReplaceWith(""),//代表了应该用什么来替代废弃的部分
    val level: DeprecationLevel = DeprecationLevel.WARNING//代表警告的程度，分别是 WARNING、ERROR、HIDDEN
)
/**
 * kotlin 常见的元注解有四个：
 * @Target,这个注解是指定了被修饰的注解都可以用在什么地方，也就是目标
 * @Retention,这个注解是指定了被修饰的注解是不是编译后可见、是不是运行时可见，也就是保留位置
 * @Repeatable,这个注解是允许我们在同一个地方，多次使用相同的被修饰的注解，使用场景比较少
 * @MustBeDocumented,指定被修饰的注解应该包含在生成的API文档中显示，这个注解一般用于SDK当中
 * 平常使用中，需要注意的是Target和Retention的取值
 *
 * @Retention.SOURCE:注解只存在于源代码，编译后不可见
 * @Retention.BINARY:注解编译后可见，运行时不可见
 * @Retention.SOURCE:编译后可见，运行时可见
 */

/**
 * Kotlin 反射具备三个特质
 * 感知程序的状态，包括程序的运行状态，还有源代码结构；
 * 修改程序的状态；
 * 根据程序的状态，调整自身的决策行为
 */

/**
 * 要使用反射的话，需要额外添加kotlin反射依赖
 *  implementation "org.jetbrains.kotlin:kotlin-reflect"
 */
data class Student(val name: String, val score: Double, val height: Int)
data class School(val name: String, var address: String)
fun readMembers(obj:Any){
    //读取obj的所有成员属性的名称和值
}

fun readMembers2(obj:Any){
    //利用反射读取obj所有成员属性
    obj::class.memberProperties.forEach {
        println("${obj::class.simpleName}.${it.name}=${it.getter.call(obj)}")
    }
}

fun main() {
    val student = Student("Tom", 99.0,170)
    val school=School("HSLG","HS")
//    readMembers(student)
//    readMembers(school)
    readMembers2(student)
    readMembers2(school)
    modifyAddressMember(school)
    readMembers2(student)
    readMembers2(school)
}

fun modifyAddressMember(obj: Any) {
    obj::class.memberProperties.forEach{
        /**
         * 注释①，判断属性的名称是否为 address，如果不是，则跳过；
         * 注释②，判断属性是否可变，在我们的例子当中 address 是用 var 修饰的，因此它的类型是 KMutableProperty1；
         * 注释③，我们在后面要调用属性的 setter，所以我们要先判断 setter 的参数是否符合预期，这里 setter 的参数个数应该是 2，第一个参数是 obj 自身，第二个是实际的值；
         * 注释④，根据属性的 getter 的返回值类型 returnType，来判断属性的类型是不是 String 类型；
         * 注释⑤，调用属性的 setter 方法，传入 obj，还有“China”，来完成属性的赋值。
         */
        if (it.name=="address"&&
                it is KMutableProperty1&&
                it.setter.parameters.size==2&&
                it.getter.returnType.classifier== String::class){
            it.setter.call(obj,"China")
            println("----address changed----")
        }else{
            println("====wrong type====")
        }
    }
}









 