package com.thanhhvt.mylauncher.ui.fragments.home.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thanhhvt.mylauncher.R
import com.thanhhvt.mylauncher.models.appInfo.AppInfo

//class HomeAppAdapter(apps: List<AppInfo>) :
//    RecyclerView.Adapter<HomeAppAdapter.ViewHolder>() {
//    private var appLists = apps
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.home_app_list_item_view, parent, false)
//        return ViewHolder(view)
//    }
//
//    fun updateData(newAppList: List<AppInfo>) {
//        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
//            override fun getOldListSize(): Int {
//                return appLists.size
//            }
//
//            override fun getNewListSize(): Int {
//                return newAppList.size
//            }
//
//            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                return appLists[oldItemPosition].appName == newAppList[newItemPosition].appName
//            }
//
//            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                return appLists[oldItemPosition].appName == newAppList[newItemPosition].appName
//            }
//        })
//        appLists = newAppList
//        diffResult.dispatchUpdatesTo(this)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val app = appLists[position]
//        holder.appName.text = app.appName
//
//        Glide.with(holder.itemView.context)
//            .load(app.icon)
//            .into(holder.appIcon)
//
//        holder.itemView.setOnClickListener {
//            holder.itemView.context.startActivity(app.intent)
//        }
//    }
//
//    override fun getItemCount(): Int = appLists.size
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val appName: TextView = itemView.findViewById(R.id.appName)
//        val appIcon: ImageView = itemView.findViewById(R.id.appIcon)
//    }
//}

class HomeAppAdapter(apps: List<AppInfo>) : RecyclerView.Adapter<HomeAppAdapter.ViewHolder>() {
    private var appLists = apps

    fun updateData(newAppList: List<AppInfo>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return appLists.size
            }

            override fun getNewListSize(): Int {
                return newAppList.size
            }

            override fun areItemsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                return appLists[oldItemPosition].appName == newAppList[newItemPosition].appName
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                return appLists[oldItemPosition].appName == newAppList[newItemPosition].appName
            }
        })
        appLists = newAppList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_app_list_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = appLists[position]
        holder.appName.text = app.appName
        holder.itemView.tag = position
        Glide.with(holder.itemView.context)
            .load(app.icon)
            .into(holder.appIcon)
        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity(app.intent)
        }
    }

    override fun getItemCount(): Int = appLists.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: TextView = itemView.findViewById(R.id.appName)
        val appIcon: ImageView = itemView.findViewById(R.id.appIcon)
    }
}
