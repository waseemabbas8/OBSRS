package com.tekprosoft.busride.model


import com.google.gson.annotations.SerializedName

data class SeatsResponse(
    val data: List<Seat>,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Status")
    val status: String
)