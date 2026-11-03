package com.example.uts

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderListActivity : AppCompatActivity() {

    private lateinit var btnBackFromList: ImageButton
    private lateinit var lvOrders: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.orderListRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnBackFromList = findViewById(R.id.btnBackFromList)
        lvOrders = findViewById(R.id.lvOrders)

        btnBackFromList.setOnClickListener {
            finish()
        }
    }
}
