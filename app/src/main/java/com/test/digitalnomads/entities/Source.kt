package com.test.digitalnomads.entities

import androidx.room.PrimaryKey

data class Source(
    @PrimaryKey
    val id: String?,
    val name: String
)