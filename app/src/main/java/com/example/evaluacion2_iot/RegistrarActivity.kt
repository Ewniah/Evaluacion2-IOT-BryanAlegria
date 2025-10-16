package com.example.evaluacion2_iot

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegistrarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        val btnRegister = findViewById<Button>(R.id.btn_register)

        btnRegister.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Registro de Cuenta")
                .setMessage("Cuenta registrada con éxito (simulación).")
                .setPositiveButton("Aceptar") { _, _ ->
                    finish() // Cierra esta pantalla y vuelve al login
                }
                .show()
        }
    }
}