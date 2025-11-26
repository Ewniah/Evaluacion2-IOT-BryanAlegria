package com.example.evaluacion2_iot // ⚠️ ¡Recuerda verificar tu package name!

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RecuperarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)

        val btnSendInstructions = findViewById<Button>(R.id.btn_send_instructions)
        // Referencia al nuevo botón de volver
        val tvVolver = findViewById<TextView>(R.id.tv_volver_login_recuperar)

        // Lógica del botón enviar (Simulación EV2)
        btnSendInstructions.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Recuperar Clave")
                .setMessage("Se han enviado las instrucciones a tu correo (simulación).")
                .setPositiveButton("Aceptar") { _, _ ->
                    finish() // Cierra y vuelve al login
                }
                .show()
        }

        tvVolver.setOnClickListener {
            finish() // Cierra esta actividad y regresa a la anterior (Login)
        }
    }
}