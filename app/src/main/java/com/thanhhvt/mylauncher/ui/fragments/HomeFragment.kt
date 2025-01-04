package com.thanhhvt.mylauncher.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.thanhhvt.mylauncher.R
import com.thanhhvt.mylauncher.databinding.FragmentHomeBinding
import com.thanhhvt.mylauncher.utils.constants.Constants
import com.thanhhvt.mylauncher.utils.extensions.isDefaultLauncher
import com.thanhhvt.mylauncher.utils.extensions.showLauncherSelector

class HomeFragment : Fragment() {
    private var viewBinding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)
        viewBinding?.root?.fitsSystemWindows = true
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupBinding()
    }

    private fun setupUI() {
        viewBinding?.let {
            it.setDefaultLauncherButton.setOnClickListener {
                requireActivity().showLauncherSelector(Constants.REQUEST_CODE_LAUNCHER_SELECTOR)
            }
        }
    }

    private fun setupBinding() {

    }

    override fun onResume() {
        super.onResume()
        viewBinding?.let {
            it.setDefaultLauncherButton.isVisible = !it.root.context.isDefaultLauncher()
        }
    }
}