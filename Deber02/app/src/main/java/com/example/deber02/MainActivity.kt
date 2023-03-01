package com.example.deber02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import com.example.deber02.databinding.ActivityMainBinding
import com.example.deber02.interf.OnBackPress
import com.example.deber02.ui.FragmentSearchSheet
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), OnBackPress {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val adapter = ViewPagerAdapter(this)
        binding.viewPagerMainActivity.adapter = adapter
        val mediator = TabLayoutMediator(
            binding.tabLayoutActivityMain,
            binding.viewPagerMainActivity,
            object : TabLayoutMediator.TabConfigurationStrategy {

                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

                    when(position) {
                        0 -> {
                            tab.text = "Chats"
                        }

                        1 -> {
                            tab.text = "Estados"
                        }

                        2 -> {
                            tab.text = "Llamadas"
                        }
                    }
                }
            }
        )
        mediator.attach()

        // show search section fragment
        binding.appHeaderSearchIcon.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.searchDialogSheet, FragmentSearchSheet(this))
            transaction.addToBackStack("tag")
            transaction.commit()
        }

        // show popup menus
        binding.appHeaderMenuIcon.setOnClickListener { item ->
            showMenu(item, R.menu.menu_popup)
        }
    }

    private fun showMenu(v : View, @MenuRes menuRes : Int) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.
            true
        }

        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    override fun onBackPress() {
        onBackPressed()
    }
}