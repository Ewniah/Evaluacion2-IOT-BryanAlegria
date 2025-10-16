package com.example.evaluacion2_iot

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RecuperarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar)

        val btnSendInstructions = findViewById<Button>(R.id.btn_send_instructions)

        btnSendInstructions.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Recuperar Clave")
                .setMessage("Se han enviado las instrucciones a tu correo (simulación).")
                .setPositiveButton("Aceptar") { _, _ ->
                    finish() // Cierra esta pantalla y vuelve al login
                }
                .show()
        }
    }
}