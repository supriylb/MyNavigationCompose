package com.example.mynavigationcompose.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object CategoryRoute

@Serializable
data class DetailCategoryRoute(
    val name: String = "default name",
    val stock: Long = 0L,
)

@Serializable
data object ProfileRoute
