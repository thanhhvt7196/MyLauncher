package com.thanhhvt.mylauncher.utils.extensions

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.thanhhvt.mylauncher.BuildConfig
import com.thanhhvt.mylauncher.ui.activities.FakeHomeActivity

fun Context.getDefaultLauncherPackage(): String {
    val intent = Intent()
    intent.action = Intent.ACTION_MAIN
    intent.addCategory(Intent.CATEGORY_HOME)
    val result = packageManager.resolveActivity(intent, 0)
    return result?.activityInfo?.packageName ?: "android"
}

fun Context.isDefaultLauncher(): Boolean {
    return BuildConfig.APPLICATION_ID == getDefaultLauncherPackage()
}

fun Context.resetDefaultLauncher() {
    try {
        val componentName = ComponentName(this, FakeHomeActivity::class.java)
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        val selector = Intent(Intent.ACTION_MAIN)
        selector.addCategory(Intent.CATEGORY_HOME)
        startActivity(selector)
        packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    } catch (e: Exception) {
        e.printStackTrace()
    }
}