package com.example.zooshop.parser

import com.example.zooshop.model.ZoobazarProduct

interface ZoobazarParserCallback {
    fun onZoobazarParserStart(category: ZoobazarParserCategory)
    fun onZoobazarParserUpdate(category: ZoobazarParserCategory, product: ZoobazarProduct)
    fun onZoobazarParserEnd(category: ZoobazarParserCategory)
    fun onZoobazarParserFailed(category: ZoobazarParserCategory)
}