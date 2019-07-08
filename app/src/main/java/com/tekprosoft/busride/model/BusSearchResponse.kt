package com.tekprosoft.busride.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BusSearchResponse(
    val data: List<Bus>,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Status")
    val status: String,

    var origin: String = "",
    var dest: String = ""
) : Serializable