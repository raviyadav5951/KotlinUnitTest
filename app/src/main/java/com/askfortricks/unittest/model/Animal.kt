package com.askfortricks.unittest.model

import com.google.gson.annotations.SerializedName

data class ApiKey(
    val message:String?,
    val key:String?
)

data class Animal(
    val name: String?,
    val taxonomy: Taxonomy?,
    val location:String?,
    val speed:Speed?,
    val diet:String?,
    @SerializedName("lifespan")
    val lifeSpan:String?,
    @SerializedName("image")
    val imageUrl:String?

)

data class Taxonomy(
    val kingdom: String?,
    val family: String?,
    val order: String
)

data class Speed(
    val metric:String?,
    val imperial:String?
)