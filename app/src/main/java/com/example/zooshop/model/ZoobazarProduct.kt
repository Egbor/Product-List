package com.example.zooshop.model

import java.io.Serializable

class ZoobazarProduct(title: String, subtitle: String, price: String, image: String): Serializable {
    val title = title
    val subtitle = subtitle
    val price = price
    val image: String = image
}