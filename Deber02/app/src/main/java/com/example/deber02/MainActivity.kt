package com.example.deber02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.deber02.adapter.ViewPagerAdapter
import com.example.deber02.databinding.ActivityMainBinding
import com.example.deber02.ui.CallFragment
import com.example.deber02.ui.ChatFragment
import com.example.deber02.ui.StatusFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())

        val adapter = ViewPagerAdapter(this, supportFragmentManager, fragmentArrayList)

        binding!!.viewPager.adapter = adapter

        binding!!.tabs.setupWithViewPager(binding!!.viewPager)

    }
}