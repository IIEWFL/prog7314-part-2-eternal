/**
 * ApiService.kt
 *
 * This interface defines the REST API endpoints used for communication between
 * the Android application and a remote backend server. It uses Retrofit for
 * HTTP requests to fetch and send property data.
 *
 * (Square, 2024; Android Developers, 2024; Google Developers, 2024)
 */

package com.example.myapplication.network

import com.example.myapplication.model.Property
import retrofit2.Call
import retrofit2.http.*   // ✅ Retrofit annotations for REST API definitions (Square, 2024)

interface ApiService {

    /**
     * Retrieves a list of properties based on filters such as minimum price,
     * maximum price, number of bedrooms, bathrooms, and property type.
     * Uses a Retrofit GET request with query parameters.
     *
     * (Square, 2024; Google Developers, 2024)
     */
    @GET("properties")
    fun getProperties(
        @Query("minPrice") minPrice: Int,
        @Query("maxPrice") maxPrice: Int,
        @Query("bedrooms") bedrooms: Int?,
        @Query("bathrooms") bathrooms: Int?,
        @Query("type") type: String?
    ): Call<List<Property>>

    /**
     * Sends a POST request to the backend to add a new property entry.
     * The property data is sent as a JSON object in the request body.
     *
     * (Square, 2024; Android Developers, 2024)
     */
    @POST("properties")
    fun addProperty(@Body property: Property): Call<Property>
}

/**

 * Android Developers, 2024. *HTTP Networking on Android Overview*. [online]
 * Available at: <https://developer.android.com/training/volley/simple>

 * Android Developers, 2024. *Working with RESTful APIs in Android*. [online]
 * Available at: <https://developer.android.com/training/basics/network-ops/connecting>

 * Google Developers, 2024. *Using Retrofit for API Requests in Android Apps*. [online]
 * Available at: <https://developers.google.com/android/guides/http>

 * Square, 2024. *Retrofit: Type-Safe HTTP Client for Android and Java*. [online]
 * Available at: <https://square.github.io/retrofit/>

 * Square, 2024. *Retrofit Annotations and Interface Definition Reference*. [online]
 * Available at: <https://square.github.io/retrofit/#annotations>

 */
