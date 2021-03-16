package com.bangkit.faniabdullah_bfaa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.faniabdullah_bfaa.databinding.WheatherItemsBinding

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private val mData = ArrayList<WeatherItems>()
    fun setData(items: ArrayList<WeatherItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): WeatherViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.wheather_items, viewGroup, false)
        return WeatherViewHolder(mView)
    }

    override fun onBindViewHolder(weatherViewHolder: WeatherViewHolder, position: Int) {
        weatherViewHolder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = WheatherItemsBinding.bind(itemView)
        fun bind(weatherItems: WeatherItems) {
            with(itemView){
                binding.textCity.text = weatherItems.name
                binding.textTemp.text = weatherItems.temperature
                binding.textDesc.text = weatherItems.description
            }
        }
    }
}

