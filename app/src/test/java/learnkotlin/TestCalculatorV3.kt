package learnkotlin

import com.tree.learnkotlin.CalculatorV3
import kotlin.test.Test
import kotlin.test.assertEquals


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    learnkotlin
 *  @文件名:   TestCalculatorV3
 *  @创建者:   rookietree
 *  @创建时间:  2022/7/27 19:05
 *  @描述：    TODO
 */
class TestCalculatorV3 {
    @Test
    fun testCalculate(){
        val cal=CalculatorV3()
        val res1 = cal.calculate("2333333333333332+1")
        assertEquals("2333333333333333", res1)
    }
}