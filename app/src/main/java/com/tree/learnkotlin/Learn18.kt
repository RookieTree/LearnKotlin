package com.tree.learnkotlin

import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Type


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn18
 *  @创建者:   rookietree
 *  @创建时间:  2022/9/7 14:13
 *  @描述：    TODO
 */
interface Callback<T : Any> {
    fun onSuccess(data: T)
    fun onFailure(throwable: Throwable)
}

class KtCall<T : Any>(
    private val call: Call,
    private val gson: Gson,
    private val type: Type
) {
    fun call(callback: Callback<T>): Call {
        //使用call请求API
        //根据请求结果，调用callback.onSuccess()或者是callback.onFailure
        //返回Okhttp的call对象
        call.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val t = gson.fromJson<T>(response.body?.string(), type)
                    callback.onSuccess(t)
                } catch (e: Exception) {
                    callback.onFailure(e)
                }

            }

        })
        return call
    }
}