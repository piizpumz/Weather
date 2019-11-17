package com.test.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import com.squareup.picasso.Picasso
import com.test.weather.WeatherModel.WeatherModel
import com.test.weather.backend.BackendManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class PageOne : Fragment() {

    lateinit var tvTemp: TextView
    lateinit var tvHumi: TextView
    lateinit var imgIcon: ImageView
    lateinit var btn_search: Button
    lateinit var etCity: EditText
    var mWeatherModel: WeatherModel? = null
    lateinit var singleToggle: SingleSelectToggleGroup
    var tempType = "c"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.page_one_view, container, false)
        bindView(view)
        setActionClick()



        return view
    }


    fun bindView(view: View) {
        singleToggle = view.findViewById(R.id.group_choices)
        tvTemp = view.findViewById(R.id.tv_temperature)
        tvHumi = view.findViewById(R.id.tv_humidity)
        imgIcon = view.findViewById(R.id.img_icon)
        etCity = view.findViewById(R.id.et_city_name)
        btn_search = view.findViewById(R.id.btn_search)

    }


    fun setActionClick() {
        btn_search.setOnClickListener(View.OnClickListener {
            val cityname = etCity.text.toString().trim()
            if (cityname.isEmpty()) {
                Toast.makeText(context, "please enter city name", Toast.LENGTH_SHORT).show()
            } else {
                loadData(cityname)
            }
        })


        singleToggle.setOnCheckedChangeListener(object :
            SingleSelectToggleGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: SingleSelectToggleGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.choice_a -> {
                        tempType = "c"
                        if (mWeatherModel != null) {
                            updateUI()
                        }
                    }
                    R.id.choice_b -> {
                        tempType = "f"
                        if (mWeatherModel != null) {
                            updateUI()
                        }
                    }
                }
            }
        })
    }


    fun loadData(str: String) {
        val url = "https://api.openweathermap.org"
        val appId = "75608730722a773063ee371b37b855b7"
        val call = BackendManager(url).getService()!!.getCurrentWeather(str, appId)
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    var weatherModel: WeatherModel =
                        Gson().fromJson(response.body().toString(), WeatherModel::class.java)
                    mWeatherModel = weatherModel
                    updateUI()

                } else {
                    Toast.makeText(context, "please enter city name", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }


    fun updateUI() {
        var temp = ""
        if (tempType == "c") {
            temp = DecimalFormat("##.#").format(mWeatherModel!!.main.temp - 273)
            tvTemp.setText("$temp °C")
        } else {
            temp = DecimalFormat("##.#").format(((mWeatherModel!!.main.temp * 9) / 5) - 459.67)
            tvTemp.setText("$temp °F")
        }

        val humi = mWeatherModel!!.main.humidity
        val icon = "https://openweathermap.org/img/wn/${mWeatherModel!!.weather.get(0).icon}@2x.png"
        Picasso.get().load(icon).into(imgIcon)
        tvHumi.setText("$humi %")


    }


}