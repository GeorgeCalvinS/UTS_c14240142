package com.example.uts

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
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

    private var menuName: String = ""
    private var unitPrice: Int = 0
    private var currentQty: Int = 1

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
    }

    companion object {
        const val EXTRA_MENU_NAME = "extra_menu_name"
        const val EXTRA_MENU_PRICE = "extra_menu_price"
    }
}
