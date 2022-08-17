package com.tree.learnkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       /* for (i in 1..3) {
            if (i==2){
                Log.d("whs"," break loop")
                return
            }
            Log.d("whs"," loop $i")
        }
        Log.d("whs"," break finish")*/
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach // 局部返回到该 lambda 表达式的调用者，即 forEach 循环
            Log.d("whs"," loop $it")
        }
        val p= Person("dat",20)
        p.age=10
        Log.d("whs"," log adult:{${p.isAdult}}")

        val l="laoliu".lastElement()
        var name="whs"
        var r=name.lastElement
        Log.d("whs","1r:{$r}")
        name = "whswh"
        r=name.lastElement
        Log.d("whs","2r:{$r}")
        var tv=TextView(this)
        tv.setOnClickListener {v:View ->
                print("aa")
        }
    }


    class Person(val name: String, var age: Int) {
        var isAdult = age >= 18
    }
}