package com.example.evaluacion2_iot // ⚠️ ¡Revisa tu package name!

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegistrarActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        // 1. Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Referencias a los campos
        val etEmail = findViewById<EditText>(R.id.et_register_email)
        val etPass = findViewById<EditText>(R.id.et_register_password)
        val btnRegister = findViewById<Button>(R.id.btn_register)
        val tvVolver = findViewById<TextView>(R.id.tv_volver_login)

        // 2. Lógica de Registro Real
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Crear usuario en Firebase
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Registro exitoso
                            mostrarAlerta("Éxito", "Cuenta creada correctamente. Ahora puedes iniciar sesión.") {
                                finish() // Cierra la pantalla y vuelve al Login
                            }
                        } else {
                            // Hubo un error (ej: contraseña corta, correo inválido)
                            mostrarAlerta("Error", "No se pudo registrar: ${task.exception?.message}", null)
                        }
                    }
            } else {
                mostrarAlerta("Atención", "Por favor completa todos los campos.", null)
            }
        }

        // Botón volver
        tvVolver.setOnClickListener {
            finish()
        }
    }

    // Función auxiliar para mostrar alertas limpias
    private fun mostrarAlerta(titulo: String, mensaje: String, onAccept: (() -> Unit)?) {
        val builder = AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Aceptar") { _, _ ->
                onAccept?.invoke()
            }
        builder.show()
    }
}