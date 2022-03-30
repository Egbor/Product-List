package com.example.zooshop.parser

import org.jsoup.Jsoup

import com.example.zooshop.model.ZoobazarProduct
import java.io.IOException

class ZoobazarParser {
    companion object {
        private const val url = "https://zoobazar.by"
        private val urlExtension = arrayOf("/catalog/sobaki/sukhie_korma/", "/catalog/koshki/sukhie_korma/")
    }

    fun start(category: ZoobazarParserCategory, callback: ZoobazarParserCallback) {
        Thread(Runnable {
            try {
                callback.onZoobazarParserStart(category)

                var document = Jsoup.connect(url + urlExtension[category.value]).get()
                var elementsList = document.getElementsByClass("product__inner")

                elementsList.forEach {
                    var image = it.getElementsByClass("product__image")[0].attr("src")
                    var price = it.getElementsByClass("product__price")[0].text()
                    var title = it.getElementsByClass("product__brand")[0].text()
                    var subtitle = it.getElementsByClass("product__name")[0].text()

                    callback.onZoobazarParserUpdate(
                        category,
                        ZoobazarProduct(title, subtitle, price, url + image)
                    )
                }

                callback.onZoobazarParserEnd(category)
            } catch(e: IOException) {
                callback.onZoobazarParserFailed(category)
            }
        }).start()
    }
}