package com.skb.bourbon_network.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Ui state
 *
 * @param T
 * @constructor Create empty Ui state
 */
sealed class UiState<out T> {
    object Loading : UiState<Nothing>() // 로딩 상태
    data class Success<out T>(val data: T) : UiState<T>() // 성공 상태
    data class Error(val message: String) : UiState<Nothing>() // 에러 상태
}

/**
 * Execute api call sync
 *
 * @param T
 * @param apiCall
 * @receiver
 * @return
 */
suspend fun <T> executeApiCallSync(apiCall: suspend () -> Response<T>): UiState<T> {
    return try {
        val response = apiCall() // API 호출
        response.handleResponse() // 응답 처리 (확장 함수 활용)
    } catch (e: Exception) {
        UiState.Error(e.message ?: "Unknown Error") // 에러 상태 반환
    }
}

/**
 * Execute api call flow
 *
 * @param T
 * @param apiCall
 * @receiver
 * @return
 */
fun <T> executeApiCallFlow(apiCall: suspend () -> Response<T>): Flow<UiState<T>> {
    return flow {
        emit(UiState.Loading) // 로딩 상태 발행
        val response = apiCall() // API 호출
        emit(response.handleResponse()) // 응답 처리 (확장 함수 활용)
    }.catch { e ->
        emit(UiState.Error(e.message ?: "Unknown Error")) // 에러 상태 발행
    }.flowOn(Dispatchers.IO)
}

/**
 * Handle response
 *
 * @param T
 * @return
 */
inline fun <T> Response<T>.handleResponse(): UiState<T> {
    return if (this.isSuccessful) {
        this.body()?.let { body ->
            UiState.Success(body)
        } ?: UiState.Error("Response body is null")
    } else {
        UiState.Error("API Error: ${this.code()} - ${this.message()}")
    }
}

