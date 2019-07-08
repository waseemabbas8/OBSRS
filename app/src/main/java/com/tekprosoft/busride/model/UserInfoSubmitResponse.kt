package com.tekprosoft.busride.model


import com.google.gson.annotations.SerializedName

data class UserInfoSubmitResponse(
    val data: List<Data>,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Status")
    val status: String
)