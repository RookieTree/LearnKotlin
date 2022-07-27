package com.tree.learnkotlin

import kotlin.system.exitProcess


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn4
 *  @创建者:   rookietree
 *  @创建时间:  2022/7/27 17:14
 *  @描述：    计算器
 *  @需求：    交互式界面，输入算式，按下回车，
 *            程序就会帮我们计算出结果；数字与字符之间要求有空格，
 *            “1 + 1”是可以的，“1+1”则不行；输入 exit，按下回车，程序就会退出；
 *            支持“加减乘除”，四种运算，仅支持两个数的运算
 */



fun main() {
    val cal=CalculatorV2()
    cal.start()
}
