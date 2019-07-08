package com.tekprosoft.busride.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebService {
    private const val BASE_URL = "http://busticketlo.gearhostpreview.com/Apis/"

    fun getBusTicketApi(): BusTicketApi {
        val mGson = GsonBuilder ().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .build()
        return  retrofit.create(BusTicketApi::class.java)
    }
}