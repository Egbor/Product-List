package com.example.zooshop.storage

import com.example.zooshop.model.ZoobazarProduct
import com.example.zooshop.parser.ZoobazarParserCategory

object ZoobazarStorage {
    private val zoobazarProductTable = Array<MutableList<ZoobazarProduct>>(
        ZoobazarParserCategory.values().size
    ) { _ -> mutableListOf() }

    fun addProduct(category: ZoobazarParserCategory, product: ZoobazarProduct) {
        zoobazarProductTable[category.value].add(product)
    }

    fun removeAllProducts(category: ZoobazarParserCategory) {
        zoobazarProductTable[category.value].clear()
    }

    fun getProducts(category: ZoobazarParserCategory): Array<ZoobazarProduct> {
        return zoobazarProductTable[category.value].toTypedArray()
    }
}