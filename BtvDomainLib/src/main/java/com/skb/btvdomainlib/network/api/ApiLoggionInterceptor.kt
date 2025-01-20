package com.skb.btvdomainlib.network.api

import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

class ApiLoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.nanoTime()

        // Log request details
        Timber.d("Request: %s\nHeaders: %s", request.url, request.headers)

        val response = chain.proceed(request)
        val endTime = System.nanoTime()

        // Log response details
        val responseBody = response.peekBody(Long.MAX_VALUE)
        val formattedBody = formatJson(responseBody.string())

        Timber.d(
            "Response: %s\nDuration: %.1fms\nHeaders: %s\nBody: %s",
            response.request.url,
            (endTime - startTime) / 1e6,
            response.headers,
            formattedBody
        )

        return response
    }

    // Helper function to format JSON strings for readability
    private fun formatJson(json: String): String {
        return try {
            if (json.startsWith("{")) {
                JSONObject(json).toString(4) // Indent with 4 spaces
            } else if (json.startsWith("[")) {
                JSONArray(json).toString(4)
            } else {
                json // Return raw string if not JSON
            }
        } catch (e: Exception) {
            json // Return raw string if parsing fails
        }
    }
}

