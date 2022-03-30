package com.example.zooshop.adapter

import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.zooshop.R
import com.example.zooshop.model.ZoobazarProduct
import java.lang.Exception

class ProductListAdapter(context: Context, productListArray: Array<ZoobazarProduct>) : ArrayAdapter<ZoobazarProduct>(context, R.layout.list_item, productListArray) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var food = getItem(position)
        var convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        var imageView = convertView.findViewById<ImageView>(R.id.food_image)
        var priceView = convertView.findViewById<TextView>(R.id.food_price)
        var titleView = convertView.findViewById<TextView>(R.id.food_title)
        var subtitleView = convertView.findViewById<TextView>(R.id.food_subtitle)

        if (food != null) {
            loadImage(food.image, imageView)
            priceView.text = food.price
            titleView.text = food.title
            subtitleView.text = food.subtitle
        }

        return convertView
    }

    private fun loadImage(url: String, imageView: ImageView) {
        var image: Bitmap? = null
        Thread(Runnable {
            try {
                val `in` = java.net.URL(url).openStream()
                image = BitmapFactory.decodeStream(`in`)
            } catch(e: Exception) {
                image = BitmapFactory.decodeResource(context.resources, R.drawable.ic_image)
            } finally {
                imageView.post {
                    if (image != null) {
                        imageView.setImageBitmap(image)
                    }
                }
            }
        }).start()
    }
}