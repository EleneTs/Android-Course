package com.etsiramua.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.etsiramua.weatherapp.service.WeatherApiClient
import java.util.*


class HourlyWeather : Fragment() {

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

        return inflater.inflate(R.layout.fragment_hourly_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add divider
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        val recyclerView = view.findViewById<RecyclerView>(R.id.hourly_weather_list)
        dividerItemDecoration.setDrawable(context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.divider
            )
        }!!)
        recyclerView.addItemDecoration(dividerItemDecoration)

        setFragmentResultListener(REQUEST_KEY_1) { requestKey, bundle ->
            val data = bundle.getString("city")
            if (data != null) {
                currentCity = data
            }

            loadData(view)

        }

        loadData(view)

        view.findViewById<ImageView>(R.id.georgia_flag).setOnClickListener {
            currentCity = resources.getString(R.string.tbilisi)
            sendDataToFragmentA()
            loadData(view)
        }

        view.findViewById<ImageView>(R.id.uk_flag).setOnClickListener {
            currentCity = resources.getString(R.string.london)
            sendDataToFragmentA()
            loadData(view)
        }

        view.findViewById<ImageView>(R.id.jamaica_flag).setOnClickListener {
            currentCity = resources.getString(R.string.kingston)
            sendDataToFragmentA()
            loadData(view)
        }

    }

    private fun sendDataToFragmentA() {
        val bundle = Bundle().apply {
            putString("city", currentCity)
        }

        setFragmentResult(REQUEST_KEY_2, bundle)
    }

    private fun loadData(view: View) {
        val weatherApiClient = WeatherApiClient(
            getString(R.string.api_key),
            getString(R.string.metric),
            getString(R.string.base_url)
        )

        weatherApiClient.getForecastWeather(currentCity) { response ->
            if (response != null && response.cod == 200) {
                view.findViewById<TextView>(R.id.hourly_city_name).text = currentCity
                val recyclerView = view.findViewById<RecyclerView>(R.id.hourly_weather_list)

                val weatherList = response.list
                val adapter = context?.let { HourlyWeatherAdapter(weatherList, it) }
                recyclerView.adapter = adapter

                view.setBackgroundColor(resources.getColor(R.color.night))


            } else {
                Toast.makeText(context, "Server Failure", Toast.LENGTH_SHORT).show();
            }

        }
    }


}