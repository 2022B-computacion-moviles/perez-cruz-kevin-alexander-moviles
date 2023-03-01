package com.example.deber02.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deber02.adapter.RecyclerAdapterCalls
import com.example.deber02.data.Calls
import com.example.deber02.databinding.FragmentCallsBinding

class FragmentCalls : Fragment() {

    lateinit var binding : FragmentCallsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCallsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data : ArrayList<Calls> = arrayListOf(
            Calls("https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg", "Irina", "Hoy, 14:44"),
            Calls("https://images.pexels.com/photos/733872/pexels-photo-733872.jpeg?cs=srgb&dl=pexels-andrea-piacquadio-733872.jpg&fm=jpg", "Prima Ali", "Ayer, 18:35"),
            Calls("https://www.blogads.de/account/themes/account/assets/pages/media/profile/people19.png", "David", "27 de febrero, 19:21"),
            Calls("https://preview.redd.it/2pm4mgw45qx11.jpg?width=640&crop=smart&auto=webp&s=b1659833767350c90c8ef4fa774307be5afbe2a7", "Julio", "30 de enero, 14:35"),
            Calls("https://preview.redd.it/l9zm6t42peu41.jpg?auto=webp&s=09073c88e5771a510b9055f46747fa2fc37a48d6", "Jonathan", "28/12/2022, 19:43"),
        )
        val recyclerAdapterCalls = RecyclerAdapterCalls(data)
        binding.callsFragmentRecyclerView.adapter = recyclerAdapterCalls
        binding.callsFragmentRecyclerView.layoutManager = LinearLayoutManager(parentFragment?.context, RecyclerView.VERTICAL, false)
    }
}