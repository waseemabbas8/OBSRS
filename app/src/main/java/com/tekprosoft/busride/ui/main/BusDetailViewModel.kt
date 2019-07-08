package com.tekprosoft.busride.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tekprosoft.busride.api.ResponseBody
import com.tekprosoft.busride.api.ResponseError
import com.tekprosoft.busride.api.WebService
import com.tekprosoft.busride.model.SeatsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusDetailViewModel : ViewModel() {

    private val apiService = WebService.getBusTicketApi()

    val seatsLiveData = MutableLiveData<ResponseBody<SeatsResponse>>()

    fun getSeatsData(busId: String){
        val call = apiService.getSeatData(busId)
        call.enqueue(object : Callback<SeatsResponse> {
            override fun onFailure(call: Call<SeatsResponse>, t: Throwable) {
                val err = ResponseError(0, t.localizedMessage ?: "")
                seatsLiveData.value = ResponseBody(null, err)
            }

            override fun onResponse(call: Call<SeatsResponse>, response: Response<SeatsResponse>) {
                try {
                    if (response.isSuccessful) {
                        seatsLiveData.value = ResponseBody(response.body(), null)
                    } else {
                        val err = ResponseError(response.code(), response.errorBody()?.string() ?: "")
                        Log.w("Response Error", err.message)
                        seatsLiveData.value = (ResponseBody(null, err))
                    }
                } catch (e: Exception) {
                    val err = ResponseError(0, e.localizedMessage ?: "")
                    seatsLiveData.value = ResponseBody(null, err)
                }
            }

        })
    }

}
