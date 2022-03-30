package com.example.zooshop.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zooshop.fragment.NoInternetFragment
import com.example.zooshop.fragment.ProductParserFragment
import com.example.zooshop.parser.ZoobazarParserCategory

class HomeFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return ZoobazarParserCategory.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return ProductParserFragment(ZoobazarParserCategory.values()[position])
    }
}