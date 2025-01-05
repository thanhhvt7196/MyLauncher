package com.thanhhvt.mylauncher.ui.fragments.home

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.thanhhvt.mylauncher.databinding.FragmentHomeBinding
import com.thanhhvt.mylauncher.models.appInfo.AppInfo
import com.thanhhvt.mylauncher.ui.activities.home.HomeActivity
import com.thanhhvt.mylauncher.ui.activities.home.HomeViewModel
import com.thanhhvt.mylauncher.ui.fragments.home.components.HomeAppAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var adapter: HomeAppAdapter? = null
    private var viewBinding: FragmentHomeBinding? = null

    private var homeViewModel: HomeViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)
        viewBinding?.root?.fitsSystemWindows = true
        (requireActivity() as? HomeActivity)?.let {
            homeViewModel = it.viewModel
        }
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupBinding()
    }

    private fun setupUI() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        viewBinding?.let { it ->
            adapter = HomeAppAdapter(homeViewModel?.listApps?.value ?: emptyList())
            it.recyclerView.isVerticalScrollBarEnabled = false
            it.recyclerView.isHorizontalScrollBarEnabled = false

            val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    val dragFlags =
                        ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    return makeMovementFlags(dragFlags, 0)
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onMoved(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    fromPos: Int,
                    target: RecyclerView.ViewHolder,
                    toPos: Int,
                    x: Int,
                    y: Int
                ) {
                    super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
                    (requireActivity() as? HomeActivity)?.viewModel?.updateAppListAfterMove(
                        fromPos,
                        toPos
                    )
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                }

                override fun isLongPressDragEnabled(): Boolean {
                    return true
                }

                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int
                ) {
                    Log.d("on selected changed", actionState.toString())
                    when (actionState) {
                        ItemTouchHelper.ACTION_STATE_DRAG -> {
                            viewHolder?.itemView?.alpha = 0.7f
                        }

                        else -> {}
                    }
                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)
                    viewHolder.itemView.alpha = 1.0f
                }
            }

            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(it.recyclerView)
            it.recyclerView.adapter = adapter
            homeViewModel?.let {
                updateRecyclerViewLayout()
            }
        }
    }

    private fun updateRecyclerViewLayout() {
        viewBinding?.let {
            val columnsPerPage = 4
            val rowsPerPage = 5
            val layoutManager = GridLayoutManager(
                requireActivity(),
                rowsPerPage,
                GridLayoutManager.HORIZONTAL,
                false
            )
            it.recyclerView.layoutManager = layoutManager
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(it.recyclerView)
        }
    }

    private fun updateAppLists(apps: List<AppInfo>) {
        adapter?.updateData(apps)
    }

    private fun setupBinding() {
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel?.listApps?.collect { allApps ->
                Log.d("list app change 2", allApps.take(5).map { it.packageName }.toString())
                updateAppLists(allApps)
            }
        }
    }

    override fun onDestroy() {
        adapter = null
        viewBinding = null
        super.onDestroy()
    }
}
