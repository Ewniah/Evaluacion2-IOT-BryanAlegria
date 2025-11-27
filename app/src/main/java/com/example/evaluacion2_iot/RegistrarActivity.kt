package com.example.evaluacion2_iot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class RegistrarActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val etEmail = findViewById<EditText>(R.id.et_register_email)
        val etPass = findViewById<EditText>(R.id.et_register_password)
        val btnRegister = findViewById<Button>(R.id.btn_register)
        val tvVolver = findViewById<TextView>(R.id.tv_volver_login)

        btnRegister.setOnClickListener {
            Log.d("Registro", "Botón presionado") // 🔍 LOG 1

            val email = etEmail.text.toString()
            val pass = etPass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // 1. Feedback visual: Desactivar botón para que el usuario sepa que algo pasa
                btnRegister.isEnabled = false
                btnRegister.text = "Registrando..."

                Log.d("Registro", "Intentando crear usuario con: $email") // 🔍 LOG 2

                // 2. Crear usuario en Authentication
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Registro", "¡Usuario creado en Auth!") // 🔍 LOG 3
                            val userId = auth.currentUser?.uid

                            val usuarioMap = hashMapOf(
                                "id" to userId,
                                "email" to email,
                                "fechaRegistro" to Date().toString(),
                                "rol" to "usuario"
                            )

                            // 3. Guardar en Firestore
                            if (userId != null) {
                                db.collection("usuarios").document(userId).set(usuarioMap)
                                    .addOnSuccessListener {
                                        Log.d("Registro", "Datos guardados en Firestore. Redirigiendo...") // 🔍 LOG 4
                                        Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                                        irAHome()
                                    }
                                    .addOnFailureListener { e ->
                                        // Si falla Firestore, igual entramos porque el usuario YA se creó
                                        Log.e("Registro", "Error en Firestore: ${e.message}") // 🔍 LOG ERROR
                                        Toast.makeText(this, "Cuenta creada (sin datos extra)", Toast.LENGTH_SHORT).show()
                                        irAHome()
                                    }
                            }
                        } else {
                            // Si falla, reactivamos el botón
                            btnRegister.isEnabled = true
                            btnRegister.text = "Registrarme"
                            Log.e("Registro", "Error en Auth: ${task.exception?.message}") // 🔍 LOG ERROR
                            mostrarAlerta("Error", "No se pudo registrar: ${task.exception?.message}")
                        }
                    }
            } else {
                mostrarAlerta("Atención", "Por favor completa todos los campos.")
            }
        }

        tvVolver.setOnClickListener {
            finish()
        }
    }

    private fun irAHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}