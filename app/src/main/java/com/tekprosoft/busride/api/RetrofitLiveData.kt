package com.tekprosoft.busride.api

import android.util.Log
import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitLiveData<T>(private val call: Call<T>, private var retry: Int = 3) : LiveData<ResponseBody<T>>(), Callback<T> {
    override fun onActive() {
        if (!call.isCanceled && !call.isExecuted) call.enqueue(this)
    }

    override fun onFailure(call: Call<T>?, t: Throwable) {
        Log.e("Response Error", "retry $retry", t)

        retry -= 1
        if (retry > 0) {
            call?.clone()?.enqueue(this)
        } else {
            val err = ResponseError(0, t.localizedMessage ?: "")
            postValue(ResponseBody(null, err))
        }
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        try {
            if (response != null) {
                if (response.isSuccessful) {
                    postValue(ResponseBody(response.body(), null))
                } else {
                    val err = ResponseError(response.code(), response.errorBody()?.string() ?: "")
                    Log.w("Response Error", err.message)
                    postValue(ResponseBody(null, err))
                }
            }
        } catch (e: Exception) {
            val err = ResponseError(0, e.localizedMessage ?: "")
            postValue(ResponseBody(null, err))
        }
    }

    fun cancel() {
        if (!call.isCanceled) {
            call.cancel()
        }
    }
}