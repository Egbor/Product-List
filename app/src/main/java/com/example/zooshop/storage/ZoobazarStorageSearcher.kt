package com.example.zooshop.storage

import com.example.zooshop.model.ZoobazarProduct
import com.example.zooshop.parser.ZoobazarParserCategory
import java.util.ArrayList

class ZoobazarStorageSearcher {
    fun searchByTitle(category: ZoobazarParserCategory, title: String): Array<ZoobazarProduct> {
        val products = ZoobazarStorage.getProducts(category)
        val result = ArrayList<ZoobazarProduct>()

        if (title.isNotEmpty()) {
            products.forEach {
                val srcTitle = title.lowercase().trim()
                val dstTitle = it.title.lowercase().trim()
                if (Regex(srcTitle).containsMatchIn(dstTitle)) {
                    result.add(it)
                }
            }
        }

        return result.toTypedArray()
    }

    fun searchAllByTitle(title: String): Array<ZoobazarProduct> {
        val product = ArrayList<ZoobazarProduct>()

        ZoobazarParserCategory.values().forEach {
            product.addAll(searchByTitle(it, title))
        }

        return product.toTypedArray()
    }
}