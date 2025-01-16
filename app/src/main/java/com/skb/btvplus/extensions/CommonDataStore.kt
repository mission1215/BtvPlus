package com.skb.btvplus.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private val PREFERENCES_KEY = "app_preferences"

object PreferenceKey {
    // TODO: add key
}

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
            }
        }
    }
}

suspend fun Context.getStringValue(key: String, defaultValue: String): String {
    val preferences = dataStore.data.first()
    return preferences[stringPreferencesKey(key)] ?: defaultValue
}

suspend fun Context.getIntValue(key: String, defaultValue: Int): Int {
    val preferences = dataStore.data.first()
    return preferences[intPreferencesKey(key)] ?: defaultValue
}

suspend fun Context.getBooleanValue(key: String, defaultValue: Boolean): Boolean {
    val preferences = dataStore.data.first()
    return preferences[booleanPreferencesKey(key)] ?: defaultValue
}

suspend fun <T : Any> Context.saveClass(key: String, value: T) {
    val serializedValue = JsonSerializer.toString(value)
    saveValue(key, serializedValue)
}

suspend fun <T : Any> Context.getClass(key: String, defaultValue: T): T {
    val serializedValue = getStringValue(key, "")
    return if (serializedValue.isNullOrEmpty()) {
        defaultValue
    } else {
        JsonSerializer.fromJson(serializedValue, defaultValue::class.java)
    }
}

object JsonSerializer {
    fun toString(value: Any): String {
        return Gson().toJson(value) //Gson 라이브러리 사용 예시
    }

    fun <T : Any> fromJson(serializedValue: String, clazz: Class<T>): T {
        return Gson().fromJson(serializedValue, clazz) //Gson 라이브러리 사용 예시
    }
}