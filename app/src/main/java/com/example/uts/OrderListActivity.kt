package com.example.uts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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

            lvOrders.setOnItemClickListener { _, _, position, _ ->
                val selectedOrder = orders[position]
                showSendSmsDialog(selectedOrder)
            }
        }
    }

    private fun showSendSmsDialog(order: OrderModel) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_send_sms, null)
        val etCustomerName = dialogView.findViewById<EditText>(R.id.etSmsCustomerName)
        val etPhoneNumber = dialogView.findViewById<EditText>(R.id.etSmsPhoneNumber)

        etCustomerName.setText(order.customerName)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Kirim SMS") { _, _ ->
                val customerName = etCustomerName.text.toString().trim()
                val phoneNumber = etPhoneNumber.text.toString().trim()

                if (customerName.isEmpty()) {
                    Toast.makeText(this, "Nama pelanggan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (phoneNumber.isEmpty()) {
                    Toast.makeText(this, "Nomor HP tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val smsContent = "Struk Pesanan - ${order.menuName} - $customerName"

                Log.d("SMS_TRACKING", "SMS Berhasil disiapkan | Data: $smsContent | No HP: $phoneNumber | Menu: ${order.menuName} | Pelanggan: $customerName | Total: ${CurrencyHelper.formatRupiah(order.grandTotal)}")

                val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:$phoneNumber")
                    putExtra("sms_body", smsContent)
                }

                try {
                    startActivity(smsIntent)
                } catch (e: Exception) {
                    val fallbackIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("sms:$phoneNumber")
                        putExtra("sms_body", smsContent)
                    }
                    try {
                        startActivity(fallbackIntent)
                    } catch (ex: Exception) {
                        Toast.makeText(this, "Aplikasi SMS tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }

                Toast.makeText(this, "SMS Berhasil disiapkan untuk $customerName", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
