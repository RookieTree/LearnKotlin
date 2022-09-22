package com.tree.learnkotlin

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.lang.Exception
import java.util.concurrent.TimeUnit


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   ResultX
 *  @创建者:   rookietree
 *  @创建时间:  2022/9/21 16:15
 *  @描述：    密封类
 */
data class RepoList2(
    val count: Int = 0,
    val items: List<Repo2> = listOf(),
    val msg: String = "数据为空"
)

data class Repo2(
    val added_stars: String = "",
    val avatars: List<String> = listOf(),
    val desc: String = "",
    val forks: String = "",
    val lang: String = "",
    val repo: String = "",
    val repo_link: String = "",
    val stars: String = ""
)

sealed class ResultX<out R : Any> {
    data class Success<out T : Any>(val data: T) : ResultX<T>()
    data class Error(val exception: Exception) : ResultX<Nothing>()
    object Loading : ResultX<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

interface RepoDataSource {
    suspend fun getRepos(): ResultX<RepoList2>
}

interface IRepository {
    suspend fun getRepoList(): ResultX<RepoList2>
}

object RetrofitClient {
    private const val TAG = "OkHttp"
    private const val BASE_URL = "https://baseUrl.com/"
    private const val TIME_OUT = 10

    val moshi: Moshi by lazy {
        Moshi.Builder().add(KotlinJsonAdapterFactory())
            .build()
    }

    val service by lazy {
        getService(RepoService::class.java, BASE_URL)
    }

    private val client: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
        builder.build()

    }

    private fun <S> getService(
        serviceClass: Class<S>,
        baseUrl: String,
        client: OkHttpClient = this.client
    ): S {
        return Retrofit.Builder()
            .client(client)
//            .addConverterFactory(KotlinJsonAdapterFactory.create())
            .baseUrl(baseUrl)
            .build().create(serviceClass)
    }

}

interface RepoService {

}

