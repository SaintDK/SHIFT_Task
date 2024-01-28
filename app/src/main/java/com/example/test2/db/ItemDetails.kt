package com.example.shift_tt.db

data class ItemDetails(
    val id: String,
    val phoneNumber: String
) {
    override fun toString(): String {
        return "[$id] $phoneNumber"
    }
}