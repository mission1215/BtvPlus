package com.skb.bourbon_utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private const val PREFERENCES_KEY = "app_preferences"

// Context 확장 함수 (delegate 활용)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_KEY)

// 다양한 데이터 유형을 저장하는 일반 함수
suspend fun <T> Context.saveValue(key: String, value: T) {
    withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }
}

// 문자열 값 가져오기
suspend fun Context.getStringValue(key: String, defaultValue: String): String {
    val preferences = dataStore.data.first()
    return preferences[stringPreferencesKey(key)] ?: defaultValue
}

// 정수 값 가져오기
suspend fun Context.getIntValue(key: String, defaultValue: Int): Int {
    val preferences = dataStore.data.first()
    return preferences[intPreferencesKey(key)] ?: defaultValue
}

// 불리언 값 가져오기
suspend fun Context.getBooleanValue(key: String, defaultValue: Boolean): Boolean {
    val preferences = dataStore.data.first()
    return preferences[booleanPreferencesKey(key)] ?: defaultValue
}

// 클래스 저장하기 (JSON 직렬화 사용)
suspend fun <T : Any> Context.saveClass(key: String, value: T) {
    val serializedValue = value.toJson() // 확장 함수 사용
    saveValue(key, serializedValue)
}

// 클래스 가져오기 (JSON 역직렬화 사용)
suspend inline fun <reified T : Any> Context.getClass(key: String, defaultValue: T): T {
    val serializedValue = getStringValue(key, "")
    return if (serializedValue.isEmpty()) {
        defaultValue
    } else {
        serializedValue.fromJson() // 확장 함수 사용
    }
}
