package com.test.weather.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.weather.WeatherModel.X
import com.test.weather.R
import java.text.DecimalFormat
import java.util.*

class ForecastAdapter(context: Context, list: List<X>) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    val mList = list
    val mContext = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.single_item_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var list = mList.get(position)

        holder.tvDateTime.setText("${getDate(list.dt.toLong())}  ")
        val icon = "https://openweathermap.org/img/wn/${list.weather.get(0).icon}@2x.png"
        Picasso.get().load(icon).into(holder.imgIcon)
        val temp_max = DecimalFormat("##.#").format(list.temp.max - 273)
        val temp_min = DecimalFormat("##.#").format(list.temp.min - 273)
        holder.tvTemp.setText("^$temp_max°C  v$temp_min°C  ${list.weather.get(0).description}")
        holder.tvWind.setText("${list.speed} m/s")
        holder.tvClouds.setText("clouds: ${list.clouds} %")


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDateTime: TextView
        val imgIcon: ImageView
        val tvTemp: TextView
        val tvWind: TextView
        val tvClouds: TextView

        init {
            tvDateTime = itemView.findViewById(R.id.tv_datetime)
            imgIcon = itemView.findViewById(R.id.img_icon)
            tvTemp = itemView.findViewById(R.id.tv_temp)
            tvWind = itemView.findViewById(R.id.tv_wind)
            tvClouds = itemView.findViewById(R.id.tv_clouds)
        }

    }

    private fun getDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.setTimeInMillis(time * 1000)
        return DateFormat.format("dd-MM-yyyy", cal).toString()
    }
}