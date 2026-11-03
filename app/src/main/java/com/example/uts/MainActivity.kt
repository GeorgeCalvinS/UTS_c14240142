package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var lvMenu: ListView
    private lateinit var chipContainer: LinearLayout
    private var allMenuItems = OrderRepository.menuList
    private var selectedCategory = "Semua"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lvMenu = findViewById(R.id.lvMenu)
        chipContainer = findViewById(R.id.chipContainer)

        setupCategoryChips()
        displayMenu(allMenuItems)

        lvMenu.setOnItemClickListener { _, _, position, _ ->
            val adapter = lvMenu.adapter as MenuAdapter
            val item = adapter.getItem(position)
            if (item != null) {
                val intent = Intent(this, OrderActivity::class.java).apply {
                    putExtra(OrderActivity.EXTRA_MENU_NAME, item.name)
                    putExtra(OrderActivity.EXTRA_MENU_PRICE, item.price)
                }
                startActivity(intent)
            }
        }
    }

    private fun setupCategoryChips() {
        val categories = listOf("Semua", "Kopi", "Frappe", "Makanan")
        chipContainer.removeAllViews()

        for (category in categories) {
            val chip = TextView(this).apply {
                text = category
                textSize = 14f
                setPadding(36, 16, 36, 16)
                setBackgroundResource(
                    if (category == selectedCategory) R.drawable.bg_header_gradient
                    else R.drawable.bg_badge_category
                )
                setTextColor(
                    if (category == selectedCategory) getColor(R.color.white)
                    else getColor(R.color.cafe_text_body)
                )
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.marginEnd = 12
                layoutParams = params

                setOnClickListener {
                    selectedCategory = category
                    setupCategoryChips()
                    val filtered = if (category == "Semua") allMenuItems
                    else allMenuItems.filter { it.category == category }
                    displayMenu(filtered)
                }
            }
            chipContainer.addView(chip)
        }
    }

    private fun displayMenu(items: List<MenuItemModel>) {
        val adapter = MenuAdapter(this, items)
        lvMenu.adapter = adapter
    }
}
