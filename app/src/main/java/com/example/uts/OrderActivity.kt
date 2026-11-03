package com.example.uts

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderActivity : AppCompatActivity() {
    //1b

    private lateinit var tvSelectedMenuInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.orderRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvSelectedMenuInfo = findViewById(R.id.tvSelectedMenuInfo)

        val menuName = intent.getStringExtra(EXTRA_MENU_NAME) ?: ""
        val menuPrice = intent.getIntExtra(EXTRA_MENU_PRICE, 0)

        tvSelectedMenuInfo.text = "$menuName - ${CurrencyHelper.formatRupiah(menuPrice)}"
    }

    companion object {
        const val EXTRA_MENU_NAME = "extra_menu_name"
        const val EXTRA_MENU_PRICE = "extra_menu_price"
    }
}
