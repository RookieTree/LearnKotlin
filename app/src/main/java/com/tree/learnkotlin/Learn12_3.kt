package com.tree.learnkotlin


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn12_3
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/29 14:32
 *  @描述：    空安全思维
 */
/**
 * String   不可为空的字符串
 * String?  可能为空的字符串
 * Sting！  不知道是不是可能为空
 */

/**
 * Kotlin空安全思维，四大准则：
 * 第一个准则：警惕 Kotlin 与外界的交互。这里主要分为两大类，第一种是：Kotlin 与其他语言的交互，比如和 Java 交互；第二种是：Kotlin 与外界环境的交互，比如 JSON 解析。
 * 第二个准则：绝不使用非空断言“!!.”。
 * 这里主要是两个场景需要注意，一个是：IDE 的“Convert Java File To Kotlin File”功能转换的 Kotlin 代码，一定要 review，消灭其中的非空断言；
 * 另一个是：当 Smart Cast 失效的时候，我们要用其他办法来解决，而不是使用非空断言。
 * 第三个准则：尽可能使用非空类型。借助 lateinit、懒加载，我们可以做到灵活初始化的同时，还能消灭可空类型。
 * 第四个准则：明确泛型的可空性。我们不能被泛型 T 的外表所迷惑，当我们定义 的时候，一定要记住，它是可空的。在非空的场景下，我们一定要明确它的可空性，这一点，通过增加泛型的边界就能做到 。
 */