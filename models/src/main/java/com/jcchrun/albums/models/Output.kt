package com.jcchrun.albums.models

sealed class Output<out T> {
    data class Success<out T>(
        val result: T
    ) : Output<T>()

    data class Error(val errorCode: Int, val errorResponse: String, val exception: Exception?) :
        Output<Nothing>() {

        companion object {
            val ERROR_CODE_NO_NETWORK = 1
            val ERROR_CODE_UNKNOWN = 2
        }

        override fun toString() =
            "code: $errorCode | response: $errorResponse | exception: $exception"
    }
}