package com.thanhhvt.mylauncher.utils.extensions

import android.content.Context

fun Number.toPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this.toFloat() * density).toInt()
}
