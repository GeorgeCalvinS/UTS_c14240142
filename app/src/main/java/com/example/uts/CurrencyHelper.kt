package com.example.uts

import java.text.NumberFormat
import java.util.Locale

object CurrencyHelper {
    private val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID")).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }

    fun formatRupiah(amount: Int): String {
        return formatter.format(amount)
    }
}
