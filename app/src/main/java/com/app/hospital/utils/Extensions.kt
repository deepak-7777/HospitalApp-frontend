package com.app.hospital.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import retrofit2.Response

fun View.visible() { visibility = View.VISIBLE }
fun View.gone() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun <T> Response<T>.toNetworkResult(): NetworkResult<T> {
    return if (isSuccessful) {
        val body = body()
        if (body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error("Empty response body", code())
        }
    } else {
        val errorMsg = try {
            val errorBody = errorBody()?.string()
            val apiError = Gson().fromJson(errorBody, com.app.hospital.data.api.ApiError::class.java)
            apiError.message
        } catch (e: Exception) {
            when (code()) {
                401 -> "Unauthorized. Please login again."
                403 -> "Access denied."
                404 -> "Resource not found."
                500 -> "Server error. Please try again later."
                else -> "Something went wrong (${code()})"
            }
        }
        NetworkResult.Error(errorMsg, code())
    }
}
