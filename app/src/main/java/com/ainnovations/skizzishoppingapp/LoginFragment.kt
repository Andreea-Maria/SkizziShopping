package com.ainnovations.skizzishoppingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var forgotPasswordTv: TextView
    private lateinit var goToRegisterBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        emailEt = view.findViewById(R.id.etEmailLogin)
        passwordEt = view.findViewById(R.id.etPasswordLogin)
        loginBtn = view.findViewById(R.id.btnLogin)
        forgotPasswordTv = view.findViewById(R.id.tvForgotPassword)
        goToRegisterBtn = view.findViewById(R.id.btnGoToRegister)

        // Handle login button click
        loginBtn.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()
            loginUser(email, password)
        }

        // Handle forgot password click
        forgotPasswordTv.setOnClickListener {
            val email = emailEt.text.toString().trim()
            if (email.isNotEmpty()) {
                resetPassword(email)
            } else {
                Toast.makeText(requireContext(),
                    "Please enter your email first",
                    Toast.LENGTH_SHORT).show()
            }
        }

        // Switch to Register
        goToRegisterBtn.setOnClickListener {
            (activity as? ProfileActivity)?.showRegisterFragment()
        }

        return view
    }

    private fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(),
                "Fields cannot be empty",
                Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(),
                        "Login Successful",
                        Toast.LENGTH_SHORT).show()
                    // Close ProfileActivity and return to MainActivity
                    activity?.finish()
                } else {
                    Toast.makeText(requireContext(),
                        "Login Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(),
                        "Reset email sent",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(),
                        "Error: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
