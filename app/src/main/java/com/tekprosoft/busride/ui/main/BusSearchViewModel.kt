package com.tekprosoft.busride.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tekprosoft.busride.api.BusTicketApi
import com.tekprosoft.busride.api.ResponseBody
import com.tekprosoft.busride.api.ResponseError
import com.tekprosoft.busride.api.WebService
import com.tekprosoft.busride.model.BusSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusSearchViewModel : ViewModel() {
    private val apiService: BusTicketApi = WebService.getBusTicketApi()


    val mBusSearchLiveData = MutableLiveData<ResponseBody<BusSearchResponse>>()

    fun searchBuses(departureDate: String, origin: String, dest: String) {
        val call = apiService.searchBuses(departureDate, origin, dest)
        call.enqueue(object: Callback<BusSearchResponse>{
            override fun onFailure(call: Call<BusSearchResponse>, t: Throwable) {
                val err = ResponseError(0, t.localizedMessage ?: "")
                mBusSearchLiveData.value = ResponseBody(null, err)
            }

            override fun onResponse(call: Call<BusSearchResponse>, response: Response<BusSearchResponse>) {
                try {
                    if (response.isSuccessful) {
                        mBusSearchLiveData.value = ResponseBody(response.body(), null)
                    } else {
                        val err = ResponseError(response.code(), response.errorBody()?.string() ?: "")
                        Log.w("Response Error", err.message)
                        mBusSearchLiveData.value = (ResponseBody(null, err))
                    }
                } catch (e: Exception) {
                    val err = ResponseError(0, e.localizedMessage ?: "")
                    mBusSearchLiveData.value = ResponseBody(null, err)
                }
            }

        })
    }
}
