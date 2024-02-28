package com.etsiramua.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.etsiramua.weatherapp.service.Forecast
import java.text.SimpleDateFormat
import java.util.*

class HourlyItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val date: TextView = itemView.findViewById(R.id.date)
    val icon: ImageView = itemView.findViewById(R.id.hourly_icon)
    var temp: TextView = itemView.findViewById(R.id.hourly_temperature)
    val desc: TextView = itemView.findViewById(R.id.hourly_description)
}

class HourlyWeatherAdapter(private val items: List<Forecast>, private val context: Context) :
    RecyclerView.Adapter<HourlyItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyItemHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_item, parent, false)
        return HourlyItemHolder(view)
    }

    override fun onBindViewHolder(holder: HourlyItemHolder, position: Int) {
        val item = items[position]
        holder.temp.text = item.main.temp.toInt().toString() + context.getString(R.string.deg)
        holder.desc.text = item.weather[0].description
        holder.date.text

        val dateFormat = SimpleDateFormat("hh a dd MMM", Locale.getDefault())
        val date = Date(item.dt * 1000L)
        val formattedDate = dateFormat.format(date)

        holder.date.text = formattedDate

        val iconUrl =
            context.getString(R.string.icon_url) + item.weather[0].icon + context.getString(
                R.string.png
            )
        Glide.with(context)
            .load(iconUrl)
            .into(holder.icon)

    }

    override fun getItemCount() = items.size
}