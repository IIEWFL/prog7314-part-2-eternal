package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.Property

class PropertyAdapter(private val propertyList: List<Property>) :
    RecyclerView.Adapter<PropertyAdapter.PropertyViewHolder>() {

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val btnSave: Button = itemView.findViewById(R.id.btnSave)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_property, parent, false)
        return PropertyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = propertyList[position]
        holder.tvTitle.text = property.title
        holder.tvLocation.text = property.location
        holder.tvPrice.text = "R${property.price}"

        // ✅ Save property button logic
        holder.btnSave.setOnClickListener {
            val sharedPrefs = it.context.getSharedPreferences("SavedProperties", Context.MODE_PRIVATE)
            val savedSet = sharedPrefs.getStringSet("saved", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

            val entry = "${property.id}|${property.title}|${property.price}|${property.bedrooms}|${property.bathrooms}|${property.type}|${property.location}"

            // Prevent duplicates
            if (!savedSet.contains(entry)) {
                savedSet.add(entry)
                sharedPrefs.edit().putStringSet("saved", savedSet).apply()
                Toast.makeText(it.context, "Property saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(it.context, "Already saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = propertyList.size
}

/**

 * Android Developers, 2024. *Displaying Lists with RecyclerView*. [online]
 * Available at: <https://developer.android.com/develop/ui/views/layout/recyclerview>

 * Android Developers, 2024. *Data Storage with SharedPreferences*. [online]
 * Available at: <https://developer.android.com/training/data-storage/shared-preferences>

 * Android Developers, 2024. *Working with ViewHolder and Adapters in Android*. [online]
 * Available at: <https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.ViewHolder>

 * Google Developers, 2024. *Creating Custom Adapters in Android Apps*. [online]
 * Available at: <https://developers.google.com/android/guides/ui-lists>

 * Google Developers, 2024. *Managing UI Events and Click Listeners in Android*. [online]
 * Available at: <https://developer.android.com/guide/topics/ui/ui-events>

 */