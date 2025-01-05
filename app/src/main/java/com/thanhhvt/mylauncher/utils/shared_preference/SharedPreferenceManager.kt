package com.utijoy.ezremotetv.utils.shared_preference

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceKeys {
    const val APP_INFO_LIST = "app_info_list"
}

object SharedPreferenceManager {
    private const val PREFERENCE_NAME = "MyLauncherPreference"

    fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(context: Context, key: String, value: String) {
        getSharedPreference(context).edit().putString(key, value).apply()
    }

    fun getString(context: Context, key: String, defaultValue: String? = null): String? {
        return getSharedPreference(context).getString(key, defaultValue)
    }

    fun remove(context: Context, key: String) {
        getSharedPreference(context).edit().remove(key).apply()
    }

    fun saveBoolean(context: Context, key: String, value: Boolean) {
        getSharedPreference(context).edit().putBoolean(key, value).apply()
    }

    fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        return getSharedPreference(context).getBoolean(key, defaultValue)
    }

    fun saveInt(context: Context, key: String, value: Int) {
        getSharedPreference(context).edit().putInt(key, value).apply()
    }

    fun getInt(context: Context, key: String, defaultValue: Int = 0): Int {
        return getSharedPreference(context).getInt(key, defaultValue)
    }

    fun saveFloat(context: Context, key: String, value: Float) {
        getSharedPreference(context).edit().putFloat(key, value).apply()
    }

    fun getFloat(context: Context, key: String, defaultValue: Float = 0f): Float {
        return getSharedPreference(context).getFloat(key, defaultValue)
    }
}
