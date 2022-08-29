package com.tree.learnkotlin

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn12
 *  @创建者:   rookietree
 *  @创建时间:  2022/8/25 18:52
 *  @描述：    用kotlin实现网络请求
 */
data class RepoList(
    var count: Int?,
    var items: List<Repo>?,
    var msg: String?
)

data class Repo(
    var added_stars: String?,
    var avatars: List<String>?,
    var desc: String?,
    var forks: String?,
    var lang: String?,
    var repo: String?,
    var repo_link: String?,
    var stars: String?
)

@Target(AnnotationTarget.FUNCTION)//只用于函数
@Retention(AnnotationRetention.RUNTIME)//运行时可访问
annotation class GET(val value: String)

@Target(AnnotationTarget.VALUE_PARAMETER)//只用于参数
@Retention(AnnotationRetention.RUNTIME)//运行时可访问
annotation class Field(val value: String)

interface ApiService {
    @GET("/repo")
    fun repos(@Field("lang") lang: String, @Field("since") since: String): RepoList
    @GET("/list/0/json")
    fun getCid(@Field("cid") pageNo: Int): CidBean
}

interface GithubService {
    @GET("/search")
    fun search(@Field("id") id: String): String
}

object KHttpV1 {
    private var okHttpClient: OkHttpClient = OkHttpClient()
    private var gson: Gson = Gson()
    var baseUrl = "https://baseurl.com"
    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            val annotations = method.annotations
            for (annotation in annotations) {
                if (annotation is GET) {
                    val url = baseUrl + annotation.value
                    return@newProxyInstance invoke(url, method, args!!)
                }
            }
            return@newProxyInstance null
        } as T
    }

    private fun invoke(path: String, method: Method, args: Array<Any>): Any? {
        //条件判断：如果方法参数中所有注解不等于参数个数，就返回
        if (method.parameterAnnotations.size != args.size) return null
        //1.根据url拼接参数
        var url = path
        val parameterAnnotations = method.parameterAnnotations
        for (i in parameterAnnotations.indices) {
            for (parameterAnnotation in parameterAnnotations[i]) {
                if (parameterAnnotation is Field) {
                    val key = parameterAnnotation.value
                    val value = args[i].toString()
                    if (!url.contains("?")) {
                        url += "?$key=$value"
                    } else {
                        url += "&$key=$value"
                    }
                }
            }
        }

        //2.使用okhttpclient进行网络请求
        val request = Request.Builder().url(url).build()
        val response = okHttpClient.newCall(request).execute()
        //3.使用gson进行JSON解析
        val genericReturnType = method.genericReturnType
        val body = response.body
        val json = body?.string()
        val result = gson.fromJson<Any?>(json, genericReturnType)
        //4.返回结果
        return result
    }
}

object KHttpV2 {
    private val okHttpClient by lazy { OkHttpClient() }
    private val gson by lazy { Gson() }
    var baseUrl = "https://www.wanandroid.com"
    inline fun <reified T> create(): T {
        return Proxy.newProxyInstance(
            T::class.java.classLoader,
            arrayOf(T::class.java)
        ) { proxy, method, args ->
            return@newProxyInstance method.annotations
                .filterIsInstance<GET>()
                .takeIf { it.size == 1 }
                ?.let { invoke("$baseUrl${it[0].value}", method, args) }
        } as T
    }

    fun invoke(url: String, method: Method, args: Array<Any>): Any? =
        method.parameterAnnotations.takeIf { method.parameterAnnotations.size == args.size }
            ?.mapIndexed { index, it -> Pair(it, args[index]) }
            ?.fold(url, ::parseUrl)
            ?.let { Request.Builder().url(it).build() }
            ?.let { okHttpClient.newCall(it).execute().body?.string() }
            ?.let { gson.fromJson(it, method.genericReturnType) }

    private fun parseUrl(acc: String, pair: Pair<Array<Annotation>, Any>) =
        pair.first.filterIsInstance<Field>()
            .first()
            .let { field ->
                if (acc.contains("?")) {
                    "$acc&${field.value}=${pair.second}"
                } else {
                    "$acc?${field.value}=${pair.second}"
                }
            }
}

fun main() {
    KHttpV2.baseUrl="https://www.wanandroid.com/article"

    val api: ApiService = KHttpV2.create()
    val data: CidBean = api.getCid(pageNo = 10)
    print(data.data.curPage.toString())
   /* val api2: GithubService = KHttpV1.create(GithubService::class.java)
    val data2: String = api2.search(id = "JetBrains")
    print(data2)*/
}

