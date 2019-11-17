package com.test.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.test.weather.WeatherModel.WeatherModel
import com.test.weather.adapter.ForecastAdapter
import com.test.weather.backend.BackendManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PageTwo : Fragment(){
    lateinit var mRecyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.page_two_view, container, false)
        bindView(view)
        loadData("")

        return view


    }

    fun bindView(view: View) {
        mRecyclerView = view.findViewById(R.id.recycle_view)

    }

    fun loadData(str: String) {
        val url = "https://samples.openweathermap.org"
        val appId = "75608730722a773063ee371b37b855b7"

        val call = BackendManager(url).getService()!!.getForecastWeather(str, appId)
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    var weatherModel: WeatherModel =
                        Gson().fromJson(response.body(), WeatherModel::class.java)
                    setAdapter(weatherModel)


                } else {
                    Toast.makeText(context, "please enter city name", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    fun setAdapter(weatherModel: WeatherModel) {
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = ForecastAdapter(context!!, weatherModel.list)

    }


}