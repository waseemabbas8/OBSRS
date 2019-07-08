package com.tekprosoft.busride.api

data class ResponseBody<T>(
    var body: T?,
    var error: ResponseError?
)

data class ResponseError(
    var statusCode: Int,
    var message: String
)