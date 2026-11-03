package com.example.uts

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderListActivity : AppCompatActivity() {

    private lateinit var btnBackFromList: ImageButton
    private lateinit var lvOrders: ListView
    private lateinit var tvEmptyOrders: TextView

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
        tvEmptyOrders = findViewById(R.id.tvEmptyOrders)

        btnBackFromList.setOnClickListener {
            finish()
        }

        val orders = OrderRepository.orderList
        if (orders.isEmpty()) {
            tvEmptyOrders.visibility = View.VISIBLE
            lvOrders.visibility = View.GONE
        } else {
            tvEmptyOrders.visibility = View.GONE
            lvOrders.visibility = View.VISIBLE
            val adapter = OrderAdapter(this, orders)
            lvOrders.adapter = adapter
        }
    }
}
