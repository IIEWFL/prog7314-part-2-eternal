package com.example.myapplication.network

import com.example.myapplication.model.Property
import retrofit2.Call
import retrofit2.http.*

interface PropertyApi {

    // ✅ Get all properties (with filters)
    @GET("properties")
    fun getProperties(
        @Query("minPrice") minPrice: Int,
        @Query("maxPrice") maxPrice: Int,
        @Query("bedrooms") bedrooms: Int?,
        @Query("bathrooms") bathrooms: Int?,
        @Query("type") type: String?
    ): Call<List<Property>>

    // ✅ Add a new property
    @POST("properties")
    fun addProperty(
        @Body property: Property
    ): Call<Property>
}
/**

 * Android Developers, 2024. *HTTP Networking on Android Overview*. [online]
 * Available at: <https://developer.android.com/training/basics/network-ops/connecting>

 * Android Developers, 2024. *Working with RESTful APIs in Android*. [online]
 * Available at: <https://developer.android.com/training/volley/simple>

 * Google Developers, 2024. *Making HTTP Requests Using Retrofit and REST Principles*. [online]
 * Available at: <https://developers.google.com/android/guides/http>

 * Square, 2024. *Retrofit: A Type-Safe HTTP Client for Android and Java*. [online]
 * Available at: <https://square.github.io/retrofit/>

 * Square, 2024. *Retrofit Annotations Reference Guide*. [online]
 * Available at: <https://square.github.io/retrofit/#annotations>

 */