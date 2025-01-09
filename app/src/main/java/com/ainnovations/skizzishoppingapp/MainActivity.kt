package com.ainnovations.skizzishoppingapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ainnovations.skizzishoppingapp.adapters.CategoryCarouselAdapter
import com.ainnovations.skizzishoppingapp.adapters.PromotionAdapter
import com.ainnovations.skizzishoppingapp.data.Promotion
import com.ainnovations.skizzishoppingapp.ui.theme.SkizziShoppingAppTheme
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var auth: FirebaseAuth
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Setup the toolbar
        toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbar)

        // Optionally set the title programmatically
        supportActionBar?.title = "ShoppingApp"

        // Check if user is logged in to update UI
        updateToolbarMenu()

        // Setup carousel and promotions (dummy data)
        setupCarousel()
        setupPromotionsRecyclerView()

        // Initialize the listener
        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            // Called whenever the user logs in or out
            val user = firebaseAuth.currentUser
            invalidateOptionsMenu()  // Forces the toolbar to refresh
        }
    }

    private fun updateToolbarMenu() {
        // Invalidate to trigger onCreateOptionsMenu again
        invalidateOptionsMenu()
    }

    // This is called whenever the toolbar menu is created or invalidated
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // If logged in, display user name or "Profile"
            menu?.findItem(R.id.action_login)?.title = user.email ?: "Profile"
            // Make the cart visible
            menu?.findItem(R.id.action_cart)?.isVisible = true
        } else {
            // If not logged in, show "Login"
            menu?.findItem(R.id.action_login)?.title = "Login"
            // Hide the cart
            menu?.findItem(R.id.action_cart)?.isVisible = false
        }
        return true
    }

    // Handle toolbar item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                val user = FirebaseAuth.getInstance().currentUser
                if (user == null) {
                    // If user is NOT logged in, open ProfileActivity to login
                    startActivity(Intent(this, ProfileActivity::class.java))
                } else {
                    // If user IS logged in, open a dedicated Profile screen
                    // Option A: Re-use ProfileActivity, but show different fragment
                    // Option B: Create a new UserProfileActivity
                    startActivity(Intent(this, UserProfileActivity::class.java))
                }
                true
            }
            R.id.action_cart -> {
                // Open cart
                startActivity(Intent(this, CartActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupCarousel() {
        val carouselRecyclerView = findViewById<RecyclerView>(R.id.carouselRecyclerView)
        carouselRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val categories = listOf("T-Shirts", "Skirts", "Pants", "Dresses")
        val carouselAdapter = CategoryCarouselAdapter(categories) { category ->
            // onClick -> open Category page for that category
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("category", category)
            startActivity(intent)
        }
        carouselRecyclerView.adapter = carouselAdapter
    }

    private fun setupPromotionsRecyclerView() {
        // Similarly for promotions (vertical list)
        val promotionRecyclerView = findViewById<RecyclerView>(R.id.promotionRecyclerView)
        promotionRecyclerView.layoutManager = LinearLayoutManager(this)

        val dummyPromotions = listOf(
            Promotion("50% OFF T-Shirts", R.drawable.produs_1),
            Promotion("Buy 1 Get 1: Skirts", R.drawable.produs3),
            Promotion("Summer Sales: Pants 30% off", R.drawable.produs4)
        )

        val promotionAdapter = PromotionAdapter(dummyPromotions)
        promotionRecyclerView.adapter = promotionAdapter
    }
    override fun onResume() {
        super.onResume()
        // Force the toolbar menu to be recreated
        invalidateOptionsMenu()
    }
    override fun onStart() {
        super.onStart()
        // Attach the listener
        auth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()
        // Remove the listener (avoid memory leaks)
        auth.removeAuthStateListener(authListener)
    }

}