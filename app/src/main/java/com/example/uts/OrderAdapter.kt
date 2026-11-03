package com.example.uts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class OrderAdapter(
    context: Context,
    private val orders: List<OrderModel>
) : ArrayAdapter<OrderModel>(context, R.layout.item_order, orders) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_order, parent, false)
        val item = orders[position]

        val tvCustomerName = view.findViewById<TextView>(R.id.tvOrderCustomerName)
        val tvMenuName = view.findViewById<TextView>(R.id.tvOrderMenuName)
        val tvMemberBadge = view.findViewById<TextView>(R.id.tvMemberBadge)
        val tvUnitPrice = view.findViewById<TextView>(R.id.tvOrderUnitPrice)
        val tvQuantity = view.findViewById<TextView>(R.id.tvOrderQuantity)
        val tvDiscount = view.findViewById<TextView>(R.id.tvOrderDiscount)
        val tvGrandTotal = view.findViewById<TextView>(R.id.tvOrderGrandTotal)

        tvCustomerName.text = item.customerName
        tvMenuName.text = item.menuName
        tvMemberBadge.visibility = if (item.isMember) View.VISIBLE else View.GONE
        tvUnitPrice.text = CurrencyHelper.formatRupiah(item.unitPrice)
        tvQuantity.text = "${item.quantity} Porsi"
        tvDiscount.text = if (item.discountAmount > 0) "- ${CurrencyHelper.formatRupiah(item.discountAmount)}" else "Rp0"
        tvGrandTotal.text = CurrencyHelper.formatRupiah(item.grandTotal)

        return view
    }
}
