package com.example.zooshop.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.zooshop.R
import com.example.zooshop.model.ZoobazarProduct
import com.example.zooshop.parser.ZoobazarParser
import com.example.zooshop.parser.ZoobazarParserCallback
import com.example.zooshop.parser.ZoobazarParserCategory
import com.example.zooshop.storage.ZoobazarStorage

private const val ARG_PARAM1 = "param1"

class ProductParserFragment(val category: ZoobazarParserCategory) : Fragment(), ZoobazarParserCallback {
    // TODO: Rename and change types of parameters
    private var categoryName: String? = null

    private val zoobazarParser = ZoobazarParser()
    private val zoobazarProducts = ZoobazarStorage

    private var fragmentContainer: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryName = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return when(category){
            ZoobazarParserCategory.DOG-> {
                fragmentContainer = R.id.product_fragment_container
                inflater.inflate(R.layout.fragment_product_parser, container, false)
            }
            ZoobazarParserCategory.CAT-> {
                fragmentContainer = R.id.product_fragment_container2
                inflater.inflate(R.layout.fragment_product_parser2, container, false)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateFragment()
    }

    override fun onZoobazarParserStart(category: ZoobazarParserCategory) {
        zoobazarProducts.removeAllProducts(category)
        activity?.runOnUiThread {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                fragmentContainer,
                LoadingFragment()
            )?.commit()
        }
    }

    override fun onZoobazarParserUpdate(category: ZoobazarParserCategory, product: ZoobazarProduct) {
        zoobazarProducts.addProduct(category, product)
    }

    override fun onZoobazarParserEnd(category: ZoobazarParserCategory) {
        activity?.runOnUiThread {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                fragmentContainer,
                ProductListFragment(zoobazarProducts.getProducts(category))
            )?.commit()
        }
    }

    override fun onZoobazarParserFailed(category: ZoobazarParserCategory) {
        activity?.runOnUiThread {
            activity?.supportFragmentManager?.beginTransaction()?.replace(
                fragmentContainer,
                NoInternetFragment()
            )?.commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(categoryName: String) =
            ProductParserFragment(ZoobazarParserCategory.valueOf(categoryName)).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, categoryName)
                }
            }
    }

    private fun updateFragment() {
        zoobazarParser.start(category, this)
    }
}