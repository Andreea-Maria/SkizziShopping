package com.ainnovations.skizzishoppingapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class UserProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var welcomeTv: TextView
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        auth = FirebaseAuth.getInstance()

        welcomeTv = findViewById(R.id.tvWelcomeUser)
        logoutBtn = findViewById(R.id.btnLogout)

        val currentUser = auth.currentUser
        if (currentUser == null) {
            // If, for some reason, we have no logged-in user,
            // just finish() or redirect them to the login screen
            finish()
            return
        }

        // Show the user's email or name
        welcomeTv.text = "Welcome, ${currentUser.email}"

        // Logout button
        logoutBtn.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
            finish() // close user profile, returning to MainActivity
        }
    }
}
