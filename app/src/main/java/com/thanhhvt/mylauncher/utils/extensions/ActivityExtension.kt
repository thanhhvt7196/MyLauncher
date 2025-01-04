package com.thanhhvt.mylauncher.utils.extensions

import android.app.Activity
import android.app.role.RoleManager
import android.content.Context


fun Activity.showLauncherSelector(requestCode: Int) {
    val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
    if (roleManager.isRoleAvailable(RoleManager.ROLE_HOME)) {
        val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_HOME)
        startActivityForResult(intent, requestCode)
    } else {
        resetDefaultLauncher()
    }
}