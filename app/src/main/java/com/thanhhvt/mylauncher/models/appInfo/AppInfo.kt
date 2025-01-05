package com.thanhhvt.mylauncher.models.appInfo

import android.content.Intent
import android.graphics.drawable.Drawable

data class AppInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable,
    val intent: Intent,
    val isSystemApp: Boolean
)
