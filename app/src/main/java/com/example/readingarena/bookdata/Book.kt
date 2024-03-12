package com.example.readingarena.bookdata

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)