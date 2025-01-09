package com.ainnovations.skizzishoppingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var fullNameEt: EditText
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var registerBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()

        fullNameEt = view.findViewById(R.id.etFullNameRegister)
        emailEt = view.findViewById(R.id.etEmailRegister)
        passwordEt = view.findViewById(R.id.etPasswordRegister)
        registerBtn = view.findViewById(R.id.btnRegister)

        registerBtn.setOnClickListener {
            val fullName = fullNameEt.text.toString().trim()
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            registerUser(fullName, email, password)
        }

        return view
    }

    private fun registerUser(fullName: String, email: String, password: String) {
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(),
                "Please fill all fields",
                Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // You can also store fullName in Firebase Database or Firestore if you want
                    Toast.makeText(requireContext(),
                        "Registration Successful",
                        Toast.LENGTH_SHORT).show()
                    (activity as? ProfileActivity)?.showLoginFragment()
                } else {
                    Toast.makeText(requireContext(),
                        "Registration Failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
