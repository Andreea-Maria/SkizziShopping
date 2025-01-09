package com.ainnovations.skizzishoppingapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ainnovations.skizzishoppingapp.adapters.CartAdapter
import com.ainnovations.skizzishoppingapp.viewModels.CartViewModel

class CartActivity : AppCompatActivity() {

    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var emptyCartTv: TextView
    private lateinit var totalPriceTv: TextView
    private lateinit var checkoutBtn: Button

    private lateinit var cartAdapter: CartAdapter
    private val cartViewModel = CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        cartRecyclerView = findViewById(R.id.cartRecyclerView)
        emptyCartTv = findViewById(R.id.tvEmptyCart)
        totalPriceTv = findViewById(R.id.tvTotalPrice)
        checkoutBtn = findViewById(R.id.btnCheckout)

        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter { product ->
            // On delete item
            cartViewModel.removeProduct(product)
            updateUI()
        }
        cartRecyclerView.adapter = cartAdapter

        // Checkout button does nothing except showing total
        checkoutBtn.setOnClickListener {
            Toast.makeText(this,
                "Checkout clicked. Total: ${cartViewModel.getTotalPrice()}",
                Toast.LENGTH_SHORT).show()
        }

        updateUI()
    }

    private fun updateUI() {
        val cartItems = cartViewModel.getCartItems()
        if (cartItems.isEmpty()) {
            emptyCartTv.visibility = View.VISIBLE
            cartRecyclerView.visibility = View.GONE
        } else {
            emptyCartTv.visibility = View.GONE
            cartRecyclerView.visibility = View.VISIBLE
            cartAdapter.submitList(cartItems)
        }
        totalPriceTv.text = "Total: $${cartViewModel.getTotalPrice()}"
    }
}
