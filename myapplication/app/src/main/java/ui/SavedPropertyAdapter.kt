package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Property

class SavedPropertyAdapter(private val savedList: List<Property>) :
    RecyclerView.Adapter<SavedPropertyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvSavedTitle)
        val tvDetails: TextView = itemView.findViewById(R.id.tvSavedDetails)
        val tvLocation: TextView = itemView.findViewById(R.id.tvSavedLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_property, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property = savedList[position]
        holder.tvTitle.text = property.title
        holder.tvDetails.text = "R${property.price} · ${property.bedrooms}BR · ${property.bathrooms}Bath · ${property.type}"
        holder.tvLocation.text = property.location
    }

    override fun getItemCount(): Int = savedList.size
}


/**

 * Android Developers, 2024. *Displaying Lists with RecyclerView*. [online]
 * Available at: <https://developer.android.com/develop/ui/views/layout/recyclerview>

 * Android Developers, 2024. *ViewHolder Pattern and Adapter Optimization in Android*. [online]
 * Available at: <https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.ViewHolder>

 * Google Developers, 2024. *Creating and Binding RecyclerView Adapters*. [online]
 * Available at: <https://developers.google.com/android/guides/ui-lists>

 * Google Developers, 2024. *Managing Data Binding Between UI Components and Data Models*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui/declaring-layout>

 */