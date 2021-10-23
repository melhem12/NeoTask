package com.melhem.neotask.service

import com.melhem.neotask.Models.History
import com.melhem.neotask.Models.Options
import com.melhem.neotask.Models.Portfolio
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("portfolios")
    fun getAllPortFolios() : Call<List<Portfolio>>


    @GET("options")
    fun getAllOptions() : Call<List<Options>>
    @GET("historical")
    fun getAllHistorical() : Call<List<History>>


    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://neo-mobile.herokuapp.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }


}