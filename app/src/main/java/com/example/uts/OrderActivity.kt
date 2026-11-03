package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var imgOrderMenuIcon: ImageView
    private lateinit var tvOrderMenuName: TextView
    private lateinit var tvOrderUnitPrice: TextView
    private lateinit var btnMinus: ImageButton
    private lateinit var btnPlus: ImageButton
    private lateinit var etQty: EditText
    private lateinit var rgMember: RadioGroup
    private lateinit var rbNonMember: RadioButton
    private lateinit var rbMember: RadioButton
    private lateinit var tvSubtotal: TextView
    private lateinit var tvDiscount: TextView
    private lateinit var tvGrandTotal: TextView
    private lateinit var btnConfirmOrder: Button

    private var menuName: String = ""
    private var unitPrice: Int = 0
    private var currentQty: Int = 1
    private var currentSubtotal: Int = 0
    private var currentDiscount: Int = 0
    private var currentGrandTotal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.orderRoot)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnBack = findViewById(R.id.btnBack)
        imgOrderMenuIcon = findViewById(R.id.imgOrderMenuIcon)
        tvOrderMenuName = findViewById(R.id.tvOrderMenuName)
        tvOrderUnitPrice = findViewById(R.id.tvOrderUnitPrice)
        btnMinus = findViewById(R.id.btnMinus)
        btnPlus = findViewById(R.id.btnPlus)
        etQty = findViewById(R.id.etQty)
        rgMember = findViewById(R.id.rgMember)
        rbNonMember = findViewById(R.id.rbNonMember)
        rbMember = findViewById(R.id.rbMember)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvDiscount = findViewById(R.id.tvDiscount)
        tvGrandTotal = findViewById(R.id.tvGrandTotal)
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder)

        menuName = intent.getStringExtra(EXTRA_MENU_NAME) ?: ""
        unitPrice = intent.getIntExtra(EXTRA_MENU_PRICE, 0)

        tvOrderMenuName.text = menuName
        tvOrderUnitPrice.text = "${CurrencyHelper.formatRupiah(unitPrice)} / porsi"

        when {
            menuName.contains("Matcha", ignoreCase = true) -> imgOrderMenuIcon.setImageResource(R.drawable.ic_frappe)
            menuName.contains("Tuna", ignoreCase = true) -> imgOrderMenuIcon.setImageResource(R.drawable.ic_fastfood)
            else -> imgOrderMenuIcon.setImageResource(R.drawable.ic_coffee)
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnMinus.setOnClickListener {
            if (currentQty > 1) {
                currentQty--
                etQty.setText(currentQty.toString())
            }
        }

        btnPlus.setOnClickListener {
            currentQty++
            etQty.setText(currentQty.toString())
        }

        etQty.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val inputQty = s?.toString()?.toIntOrNull() ?: 0
                currentQty = inputQty
                calculateTotal()
            }
        })

        rgMember.setOnCheckedChangeListener { _, _ ->
            calculateTotal()
        }

        btnConfirmOrder.setOnClickListener {
            if (currentQty <= 0) {
                Toast.makeText(this, "Jumlah porsi minimal 1", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            showCustomerNameDialog()
        }

        calculateTotal()
    }

    private fun calculateTotal() {
        val qty = etQty.text.toString().toIntOrNull() ?: 0
        currentSubtotal = unitPrice * qty
        val isMember = rbMember.isChecked
        currentDiscount = if (isMember) (currentSubtotal * 0.10).toInt() else 0
        currentGrandTotal = currentSubtotal - currentDiscount

        tvSubtotal.text = CurrencyHelper.formatRupiah(currentSubtotal)
        tvDiscount.text = if (currentDiscount > 0) "- ${CurrencyHelper.formatRupiah(currentDiscount)}" else "Rp0"
        tvGrandTotal.text = CurrencyHelper.formatRupiah(currentGrandTotal)
    }

    private fun showCustomerNameDialog() {
        val input = EditText(this).apply {
            hint = "Masukkan nama pemesan"
            setSingleLine(true)
        }

        val container = FrameLayout(this).apply {
            val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(60, 20, 60, 10)
            layoutParams = params
            addView(input)
        }

        AlertDialog.Builder(this)
            .setTitle("Nama Pemesan")
            .setMessage("Silakan masukkan nama pemesan:")
            .setView(container)
            .setPositiveButton("Konfirmasi") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isEmpty()) {
                    Toast.makeText(this, "Nama pemesan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                } else {
                    val newOrder = OrderModel(
                        id = OrderRepository.orderList.size + 1,
                        customerName = name,
                        menuName = menuName,
                        unitPrice = unitPrice,
                        quantity = currentQty,
                        isMember = rbMember.isChecked,
                        discountAmount = currentDiscount,
                        grandTotal = currentGrandTotal
                    )
                    OrderRepository.addOrder(newOrder)

                    val intent = Intent(this, OrderListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    companion object {
        const val EXTRA_MENU_NAME = "extra_menu_name"
        const val EXTRA_MENU_PRICE = "extra_menu_price"
    }
}
