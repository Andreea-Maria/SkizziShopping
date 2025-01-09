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

class ProductAdapter(
    private val onItemClick: (Product) -> Unit
) : ListAdapter<Product, ProductAdapter.ProductViewHolder>(
    object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView = itemView.findViewById(R.id.ivProductImage)
        private val name: TextView = itemView.findViewById(R.id.tvProductName)
        private val price: TextView = itemView.findViewById(R.id.tvProductPrice)
        private val addBtn: Button = itemView.findViewById(R.id.btnAddToCart)

        fun bind(product: Product) {
            name.text = product.name
            price.text = "$${product.price}"
            image.setImageResource(product.imageRes)

            addBtn.setOnClickListener {
                onItemClick(product)
            }
        }
    }
}
