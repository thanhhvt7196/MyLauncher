package com.thanhhvt.mylauncher.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.thanhhvt.mylauncher.R
import com.thanhhvt.mylauncher.databinding.ActivityHomeBinding
import com.thanhhvt.mylauncher.utils.extensions.getDefaultLauncherPackage
import com.thanhhvt.mylauncher.utils.extensions.isDefaultLauncher

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge(
            SystemBarStyle.dark(Color.TRANSPARENT),
            SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContentView(viewBinding.root)
    }

    override fun onResume() {
        super.onResume()
        Log.d("default launcher", isDefaultLauncher().toString())
        Log.d("default launcher", getDefaultLauncherPackage())
    }
}