package com.tree.learnkotlin

import java.lang.Exception


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn13
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/26 19:16
 *  @描述：    表达式思维
 */
fun calculate(): Nothing = throw NotImplementedError()
fun calculate2(): Nothing = throw Exception()
/**
 * 所谓的表达式思维，就是要时刻记住：Kotlin 大部分的语句都是表达式，它是可以产生返回值的。利用这种思维，往往可以大大简化代码逻辑。
 * Any 是所有非空类型的根类型，而“Any?”才是所有类型的根类型
 * 。Unit 与 Java 的 void 类型，代表一个函数不需要返回值；而“Unit?”这个类型则没有太多实际的意义。
 * 当 Nothing 作为函数返回值的时候，意味着这个函数永远不会返回结果，而且还会截断程序的后续流程。Kotlin 编译器也会根据这一点，进行流程分析。
 * 当 Nothing 作为函数参数的时候，就意味着这个函数永远无法被正常调用。这在泛型星投影的时候是有一定应用的。
 * 另外，Nothing 可以看作是“Nothing?”子类型，因此，Nothing 可以看作是 Kotlin 所有类型的底类型。
 * 正是因为 Kotlin 在类型系统当中，加入了 Unit、Nothing 这两个类型，才让大部分无法产生值的语句摇身一变，成为了表达式。这也是“Kotlin 大部分的语句都是表达式”的根本原因。
 */

 