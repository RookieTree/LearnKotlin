package com.tree.learnkotlin

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select


/*
 *  @项目名：  LearnKotlin 
 *  @包名：    com.tree.learnkotlin
 *  @文件名:   Learn21
 *  @创建者:   rookietree
 *  @创建时间:  2022/9/8 16:57
 *  @描述：    select
 */
data class Product(
    val productId: String,
    val price: Double,
    val isCache: Boolean = false
)

class Learn21 {

    fun main() = runBlocking {
        suspend fun getCacheInfo(productId: String): Product? {
            delay(100L)
            return Product(productId, 9.9)
        }

        suspend fun getNetInfo(productId: String): Product? {
            delay(200L)
            return Product(productId, 9.8)
        }

        fun updateUI(product: Product) {
            println("${product.productId}==${product.price}")
        }

        val startTime = System.currentTimeMillis()
        val productId = "xxxId"
        /* //查询缓存
         val cacheInfo=getCacheInfo(productId)
         if (cacheInfo!=null) {
             updateUI(cacheInfo)
             println("cache time cost: ${System.currentTimeMillis() - startTime}")
         }
         //查询网络
         val lastInfo=getNetInfo(productId)
         if (lastInfo!=null) {
             updateUI(lastInfo)
             println("net time cost: ${System.currentTimeMillis() - startTime}")
         }*/
        val cacheDeferred = async { getCacheInfo(productId) }
        val netDeferred = async { getNetInfo(productId) }

        val product = select<Product?> {
            cacheDeferred
                .onAwait {
                    it?.copy(isCache = true)
                }
            netDeferred
                .onAwait {
                    it?.copy(isCache = false)
                }
        }
        if (product != null) {
            updateUI(product)
            println("time cost: ${System.currentTimeMillis() - startTime}")
        }
        //如果当前结果是缓存，那么再取最新的网络服务结果
        if (product != null && product.isCache) {
            val latest = netDeferred.await() ?: return@runBlocking
            updateUI(latest)
            println("time cost: ${System.currentTimeMillis() - startTime}")
        }

    }
}

fun main() {
    Learn21().main()
}