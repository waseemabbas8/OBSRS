package com.tekprosoft.busride.api

import com.tekprosoft.busride.model.BusSearchResponse
import com.tekprosoft.busride.model.SeatsResponse
import com.tekprosoft.busride.model.UserInfoSubmitResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface BusTicketApi {

    @GET("GetBusData")
    fun searchBuses(@Query("departure_date") departureDate: String,
                    @Query("from") origin: String,
                    @Query("destination") destination: String) : Call<BusSearchResponse>

    @GET("GetSeatData")
    fun getSeatData(@Query("bus_id") busId: String): Call<SeatsResponse>

    @Multipart
    @POST("UserInformation")
    fun submitUserData(@Part("seat_id") seatId: RequestBody,
                       @Part("name") name: RequestBody,
                       @Part("number") number: RequestBody,
                       @Part("email") email: RequestBody) : Call<UserInfoSubmitResponse>

}