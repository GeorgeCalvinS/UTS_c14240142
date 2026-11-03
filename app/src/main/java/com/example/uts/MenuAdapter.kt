package com.example.uts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MenuAdapter(
    context: Context,
    private val menuItems: List<MenuItemModel>
) : ArrayAdapter<MenuItemModel>(context, R.layout.item_menu, menuItems) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        val item = menuItems[position]

        val iconView = view.findViewById<ImageView>(R.id.imgMenuIcon)
        val nameView = view.findViewById<TextView>(R.id.tvMenuName)
        val categoryView = view.findViewById<TextView>(R.id.tvMenuCategory)
        val descView = view.findViewById<TextView>(R.id.tvMenuDescription)
        val priceView = view.findViewById<TextView>(R.id.tvMenuPrice)

        iconView.setImageResource(item.iconResId)
        nameView.text = item.name
        categoryView.text = item.category
        descView.text = item.description
        priceView.text = CurrencyHelper.formatRupiah(item.price)

        return view
    }
}
