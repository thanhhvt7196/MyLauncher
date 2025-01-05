package com.thanhhvt.mylauncher.ui.activities.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thanhhvt.mylauncher.models.appInfo.AppInfo
import com.thanhhvt.mylauncher.utils.extensions.getAllInstalledApp
import com.utijoy.ezremotetv.utils.shared_preference.SharedPreferenceKeys
import com.utijoy.ezremotetv.utils.shared_preference.SharedPreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val appContext by lazy { application.applicationContext }
    val listApps: MutableStateFlow<List<AppInfo>>

    init {
        listApps = MutableStateFlow(getInitialAppList())
//        listApps.value = appContext.getAllInstalledApp(false)
        setupBinding()
        Log.d("list app change 3", listApps.value.take(5).map { it.packageName }.toString())
    }

    private fun getInitialAppList(): List<AppInfo> {
        val allInstalledApp = appContext.getAllInstalledApp()
        try {
            val listAppsString =
                SharedPreferenceManager.getString(appContext, SharedPreferenceKeys.APP_INFO_LIST)
            val type = object : TypeToken<Array<String>>() {}.type
            val cachedAppList: Array<String> = Gson().fromJson(listAppsString, type)
            val cachedOrderMap = cachedAppList.withIndex().associate { it.value to it.index }
            return allInstalledApp.sortedBy { cachedOrderMap[it.packageName] ?: Int.MAX_VALUE }
        } catch (e: Exception) {
            Log.e("parse error", e.message.toString())
            SharedPreferenceManager.remove(appContext, SharedPreferenceKeys.APP_INFO_LIST)
            e.printStackTrace()
            return allInstalledApp
        }
    }

    private fun setupBinding() {
        viewModelScope.launch {
            listApps.collect { allApps ->
                SharedPreferenceManager.saveString(
                    appContext,
                    SharedPreferenceKeys.APP_INFO_LIST,
                    Gson().toJson(allApps.map { it.packageName })
                )
            }
        }
    }

    fun updateAppListAfterMove(fromPosition: Int, toPosition: Int) {
        val currentList = listApps.value.toMutableList() ?: return
        val item = currentList.removeAt(fromPosition)
        currentList.add(toPosition, item)
        listApps.value = currentList
        Log.d("list app change", listApps.value.take(5).map { it.packageName }.toString())
    }
}
