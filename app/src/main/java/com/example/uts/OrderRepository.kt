package com.example.uts

object OrderRepository {
    val menuList: List<MenuItemModel> = listOf(
        MenuItemModel(1, "Caffe Latte", "Kopi", 28000, "Espresso dengan susu steamed lembut", R.drawable.ic_coffee),
        MenuItemModel(2, "Matcha Frappe", "Frappe", 32000, "Green tea blended dengan es krim", R.drawable.ic_frappe),
        MenuItemModel(3, "Signature Tuna Bowl", "Makanan", 45000, "Tuna segar dengan nasi dan sayuran", R.drawable.ic_fastfood)
    )
}
