package com.ainnovations.skizzishoppingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ainnovations.skizzishoppingapp.R
import com.ainnovations.skizzishoppingapp.data.Promotion

class PromotionAdapter(
    private val promotions: List<Promotion>
) : RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_promotion, parent, false)
        return PromotionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
        val promotion = promotions[position]
        holder.bind(promotion)
    }

    override fun getItemCount(): Int = promotions.size

    inner class PromotionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTv: TextView = itemView.findViewById(R.id.tvPromotionTitle)
        private val imageView: ImageView = itemView.findViewById(R.id.ivPromotionImage)

        fun bind(promotion: Promotion) {
            titleTv.text = promotion.title
            imageView.setImageResource(promotion.imageRes)
        }
    }
}
