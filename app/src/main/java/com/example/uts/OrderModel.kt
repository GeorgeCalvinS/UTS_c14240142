package com.example.uts

data class OrderModel(
    val id: Int,
    val customerName: String,
    val menuName: String,
    val unitPrice: Int,
    val quantity: Int,
    val isMember: Boolean,
    val discountAmount: Int,
    val grandTotal: Int,
    val timestamp: Long = System.currentTimeMillis()
)
