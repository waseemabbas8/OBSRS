package com.tekprosoft.busride.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bus_id")
    val busId: String,
    @SerializedName("bus_number")
    val busNumber: String,
    @SerializedName("bus_type")
    val busType: Any,
    val company: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("departure_date")
    val departureDate: String,
    @SerializedName("departure_time")
    val departureTime: String,
    val destination: String,
    val from: String,
    val id: String,
    @SerializedName("Is_Reserved")
    val isReserved: String,
    @SerializedName("seat_number")
    val seatNumber: String,
    @SerializedName("ticket_price")
    val ticketPrice: String,
    @SerializedName("total_journey_time")
    val totalJourneyTime: String,
    @SerializedName("total_seats")
    val totalSeats: String,
    @SerializedName("updated_at")
    val updatedAt: String
)