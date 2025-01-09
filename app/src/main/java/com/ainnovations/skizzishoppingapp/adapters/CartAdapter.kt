package com.ainnovations.skizzishoppingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ainnovations.skizzishoppingapp.R
import com.ainnovations.skizzishoppingapp.data.Product

class CartAdapter(
    private val onDeleteClick: (Product) -> Unit
) : ListAdapter<Product, CartAdapter.CartViewHolder>(
    object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.ivCartImage)
        private val name: TextView = itemView.findViewById(R.id.tvCartProductName)
        private val price: TextView = itemView.findViewById(R.id.tvCartProductPrice)
        private val deleteBtn: Button = itemView.findViewById(R.id.btnDeleteItem)

        fun bind(product: Product) {
            image.setImageResource(product.imageRes)
            name.text = product.name
            price.text = "$${product.price}"
            deleteBtn.setOnClickListener {
                onDeleteClick(product)
            }
        }
    }
}
