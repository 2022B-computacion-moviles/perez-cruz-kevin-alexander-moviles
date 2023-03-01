package com.example.deber02.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deber02.adapter.RecyclerAdapter
import com.example.deber02.data.Item
import com.example.deber02.databinding.FragmentChatBinding


class FragmentChat : Fragment() {

    lateinit var binding : FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data : ArrayList<Item> = arrayListOf(
            Item("https://www.blogads.de/account/themes/account/assets/pages/media/profile/profile_user.jpg", "Luis", "Quieres ser tu propio jefe?", "15:35", 10),
            Item("https://expertphotography.b-cdn.net/wp-content/uploads/2020/08/social-media-profile-photos-3.jpg", "Eduardo", "Sale un Amogus?", "Ayer", 2),
        )
        val recyclerAdapter = RecyclerAdapter(data)
        binding.chatRecyclerView.adapter = recyclerAdapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(parentFragment?.context, RecyclerView.VERTICAL, false)

    }
}