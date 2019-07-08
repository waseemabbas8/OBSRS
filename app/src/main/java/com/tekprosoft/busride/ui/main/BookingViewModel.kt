package com.tekprosoft.busride.ui.main

import androidx.lifecycle.ViewModel
import com.tekprosoft.busride.api.RetrofitLiveData
import com.tekprosoft.busride.api.WebService
import okhttp3.MediaType
import okhttp3.RequestBody

class BookingViewModel : ViewModel() {
    private val apiService = WebService.getBusTicketApi()

    fun confirmBooking(name: String, phone: String, email: String, seatId: String) = RetrofitLiveData(apiService.submitUserData(
        RequestBody.create(MediaType.parse("text/plain"), seatId),
        RequestBody.create(MediaType.parse("text/plain"), email),
        RequestBody.create(MediaType.parse("text/plain"), phone),
        RequestBody.create(MediaType.parse("text/plain"), name)
    ))
}
