package com.etsiramua.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.etsiramua.weatherapp.databinding.FragmentTodayWeatherBinding
import com.etsiramua.weatherapp.service.WeatherApiClient
import java.util.*


class TodayWeather : Fragment() {

    private lateinit var currentCity: String

    private val REQUEST_KEY_1 = "request_key_1"

    private val REQUEST_KEY_2 = "request_key_2"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        currentCity = resources.getString(R.string.tbilisi)
//        val binding = FragmentTodayWeatherBinding.inflate(inflater, container, false)
//        return binding.root
        return inflater.inflate(R.layout.fragment_today_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // listener
        setFragmentResultListener(REQUEST_KEY_2) { requestKey, bundle ->
            val data = bundle.getString("city")
            if (data != null) {
                currentCity = data
            }
            loadData(view)

        }

        loadData(view)

        view.findViewById<ImageView>(R.id.georgia_flag).setOnClickListener {
            currentCity = resources.getString(R.string.tbilisi)
            sendDataToFragmentB()
            loadData(view)
        }

        view.findViewById<ImageView>(R.id.uk_flag).setOnClickListener {
            currentCity = resources.getString(R.string.london)
            sendDataToFragmentB()
            loadData(view)
        }

        view.findViewById<ImageView>(R.id.jamaica_flag).setOnClickListener {
            currentCity = resources.getString(R.string.kingston)
            sendDataToFragmentB()
            loadData(view)
        }

    }

    private fun sendDataToFragmentB() {
        val bundle = Bundle().apply {
            putString("city", currentCity)
        }
        setFragmentResult(REQUEST_KEY_1, bundle)
    }


    private fun loadData(view: View) {
        val weatherApiClient = WeatherApiClient(
            getString(R.string.api_key),
            getString(R.string.metric),
            getString(R.string.base_url)
        )

        weatherApiClient.getCurrentWeather(currentCity) { response ->
            if (response != null && response.cod == 200) {
                view.findViewById<TextView>(R.id.city_name).text = response.name

                view.findViewById<TextView>(R.id.temperature).text =
                    response.main.temp.toInt().toString() + resources.getString(R.string.deg)
                view.findViewById<TextView>(R.id.weather_description).text =
                    response.weather[0].description

                val iconUrl =
                    resources.getString(R.string.icon_url) + response.weather[0].icon + resources.getString(
                        R.string.png
                    )
                Glide.with(this)
                    .load(iconUrl)
                    .into(view.findViewById<ImageView>(R.id.weather_icon))

                // details
                view.findViewById<TextView>(R.id.details_temperature_val).text =
                    response.main.temp.toInt().toString() + resources.getString(R.string.deg)
                view.findViewById<TextView>(R.id.feels_like_val).text =
                    response.main.feels_like.toInt().toString() + resources.getString(R.string.deg)
                view.findViewById<TextView>(R.id.humidity_val).text =
                    response.main.humidity.toString() + resources.getString(R.string.per)
                view.findViewById<TextView>(R.id.pressure_val).text =
                    response.main.pressure.toString()

                val date = Date(response.dt * 1000)
                val calendar = Calendar.getInstance()
                calendar.time = date
                var hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

                if (hourOfDay in 6..17) {
                    view.setBackgroundColor(resources.getColor(R.color.day))
                } else {
                    view.setBackgroundColor(resources.getColor(R.color.night))
                }

            }
            if (response != null && response.cod != 200) {
                Toast.makeText(context, "Server Failure", Toast.LENGTH_SHORT).show()
                println("Server Failure")
            }

        }
    }


}