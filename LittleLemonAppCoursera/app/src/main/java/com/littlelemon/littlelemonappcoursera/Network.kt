package com.littlelemon.littlelemonappcoursera

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetworkdata(
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val category: String,
    @SerialName("image")
    val imgUrl: String
)
