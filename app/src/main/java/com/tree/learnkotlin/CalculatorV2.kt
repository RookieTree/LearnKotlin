package com.tree.learnkotlin

import kotlin.system.exitProcess


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   CalculatorV2
 *  @创建者:   rookietree
 *  @创建时间:  2022/7/27 17:37
 *  @描述：    TODO
 */
@Deprecated(
    message = "use CalculatorV3 instead",
    replaceWith = ReplaceWith("CalculatorV3"),
    level = DeprecationLevel.ERROR
)
class CalculatorV2 {
    val help = """
-----------------------------------
使用说明
请输入标准的算式，并且按回车；
比如：1+1，注意符合与数字之间要有空格
输入exit，退出程序。
-----------------------------------""".trimIndent()

    fun start() {
        while (true) {
            println(help)
            val input = readLine() ?: continue
            val res = calculate(input)
            if (res == null) {
                println("输入格式不对")
            } else {
                println("$input = $res")
            }
        }
    }

    fun calculate(input: String): String? {
        if (input == "exit") {
            exitProcess(0)
        }
        val exp = parseExpression(input) ?: return null
        val left = exp.left
        val opt = exp.opt
        val right = exp.right
        return when (opt) {
            Operation.ADD -> addString(left, right)
            Operation.MINUS -> minusString(left, right)
            Operation.MULTI -> multiString(left, right)
            Operation.DIVI -> diviString(left, right)
        }
    }

    fun parseExpression(input: String): Expression? {
        val opt = parseOperation(input) ?: return null
        val inputList = input.split(opt.value)
        if (inputList.size != 2) {
            return null
        }
        return Expression(inputList[0].trim(), opt, inputList[1].trim())
    }

    fun parseOperation(input: String): Operation? {
        Operation.values().forEach {
            if (input.contains(it.value)) {
                return it
            }
        }
        return null
    }

    fun addString(num1: String, num2: String): String {
        val result = num1.toInt() + num2.toInt()
        return result.toString()
    }

    fun minusString(num1: String, num2: String): String {
        val result = num1.toInt() - num2.toInt()
        return result.toString()
    }

    fun multiString(num1: String, num2: String): String {
        val result = num1.toInt() * num2.toInt()
        return result.toString()
    }

    fun diviString(num1: String, num2: String): String {
        val result = num1.toInt() / num2.toInt()
        return result.toString()
    }
}