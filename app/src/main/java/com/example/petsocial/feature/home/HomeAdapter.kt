package com.example.petsocial.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petsocial.databinding.HomePostRawBinding

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeAdapterVH>() {
    class HomeAdapterVH(binding:HomePostRawBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterVH {
        val view= HomePostRawBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeAdapterVH(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: HomeAdapterVH, position: Int) {
        TODO("Not yet implemented")
    }
}