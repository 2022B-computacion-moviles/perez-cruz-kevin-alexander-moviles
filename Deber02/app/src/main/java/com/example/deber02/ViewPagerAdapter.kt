package com.example.deber02

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.deber02.ui.FragmentCalls
import com.example.deber02.ui.FragmentChat
import com.example.deber02.ui.FragmentStatus

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() : Int {
        return 3
    }

    override fun createFragment(position: Int) : Fragment {
        when(position) {
            0 -> {
                return FragmentChat()
            }

            1 -> {
                return FragmentStatus()
            }

            2 -> {
                return FragmentCalls()
            }

            else -> {
                return Fragment()
            }
        }
    }
}