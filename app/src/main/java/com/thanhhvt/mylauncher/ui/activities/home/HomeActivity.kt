package com.thanhhvt.mylauncher.ui.activities.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.thanhhvt.mylauncher.databinding.ActivityHomeBinding
import com.thanhhvt.mylauncher.utils.extensions.getDefaultLauncherPackage
import com.thanhhvt.mylauncher.utils.extensions.isDefaultLauncher

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)

        enableEdgeToEdge(
            SystemBarStyle.dark(Color.TRANSPARENT),
            SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        Log.d("default launcher", isDefaultLauncher().toString())
        Log.d("default launcher", getDefaultLauncherPackage())
    }
}
