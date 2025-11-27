package com.example.evaluacion2_iot

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RecuperarActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.et_recovery_email)
        val btnEnviar = findViewById<Button>(R.id.btn_send_instructions)
        val tvVolver = findViewById<TextView>(R.id.tv_volver_login_recuperar)

        btnEnviar.setOnClickListener {
            val email = etEmail.text.toString()

            if (email.isNotEmpty()) {
                // Enviar correo de recuperación real con Firebase
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            mostrarAlerta("Correo Enviado", "Se han enviado las instrucciones para restablecer tu contraseña a $email.") {
                                finish() // Volver al login
                            }
                        } else {
                            mostrarAlerta("Error", "No se pudo enviar el correo. Verifica que el usuario exista.", null)
                        }
                    }
            } else {
                Toast.makeText(this, "Ingresa tu correo", Toast.LENGTH_SHORT).show()
            }
        }

        tvVolver.setOnClickListener {
            finish()
        }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String, onAccept: (() -> Unit)?) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Aceptar") { _, _ ->
                onAccept?.invoke()
            }
            .show()
    }
}