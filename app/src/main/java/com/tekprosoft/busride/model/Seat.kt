package com.tekprosoft.busride.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Seat(
    @SerializedName("bus_id")
    val busId: String = "",
    @SerializedName("seat_number")
    val seatNumber: String = "",
    @SerializedName("created_at")
    val createdAt: String = "",
    val id: String = "",
    @SerializedName("Is_Reserved")
    var isReserved: String = "0",
    @SerializedName("updated_at")
    val updatedAt: String = ""
) : Serializable