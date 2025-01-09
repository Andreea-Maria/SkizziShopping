package com.ainnovations.skizzishoppingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Default: Show LoginFragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.profileContainer, LoginFragment())
                .commit()
        }
    }

    // Helper method to switch to RegisterFragment
    fun showRegisterFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.profileContainer, RegisterFragment())
            .addToBackStack(null)
            .commit()
    }

    // Helper method to switch back to LoginFragment
    fun showLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.profileContainer, LoginFragment())
            .commit()
    }
}
