package com.tree.learnkotlin


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn3
 *  @创建者:   rookietree
 *  @创建时间:  2022/7/27 16:47
 *  @描述：    TODO
 */
class Learn3 {

    //关于kotlin编译的
    //kotlin对基础类型的转换成Java规则：
    //1.只要基础类型的变量可能为空，那么这个变量就会被转换成Java的包装类型
    //2.反之，只要基础类型的变量不可能为空，那么这个变量就会被转换成Java的原始类型

    //kotlin接口语法局限性
    //kotlin接口的方法默认实现，本质上也没有直接提供实现的代码
    // 只是在接口当中定义了一个静态内部类"DefaultImpls"
    // 然后将默认实现的代码放到了静态内部类中了
    //kotlin接口的数据，本质上转换成java后，变成了一个普通的接口方法

    /**
     * 结论：
     * 写的kotlin代码，最终都会被kotlin编译器进行一次统一的翻译，变成java能理解的格式。
     * kotlin的每一个语法，最终都会被翻译成对应的java字节码。
     * kotlin编译器就相当于一个翻译官。
     */

}