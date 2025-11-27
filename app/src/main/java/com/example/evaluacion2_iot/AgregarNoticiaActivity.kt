package com.example.evaluacion2_iot

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AgregarNoticiaActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_noticia)

        db = FirebaseFirestore.getInstance()

        // Referencias
        val etTitulo = findViewById<EditText>(R.id.et_titulo)
        val etBajada = findViewById<EditText>(R.id.et_bajada)
        val etUrl = findViewById<EditText>(R.id.et_url_imagen)
        val etAutor = findViewById<EditText>(R.id.et_autor)
        val etFecha = findViewById<EditText>(R.id.et_fecha)
        val etContenido = findViewById<EditText>(R.id.et_contenido)
        val btnGuardar = findViewById<Button>(R.id.btn_guardar_noticia)
        val tvTituloPantalla = findViewById<TextView>(0) // Opcional: busca el TextView del título si quieres cambiar el texto "Nueva Noticia"

        // 1. Verificar si venimos a EDITAR (¿Llegó un ID?)
        val idDocumento = intent.getStringExtra("id")

        if (idDocumento != null) {
            // MODO EDICIÓN: Rellenar campos
            btnGuardar.text = "Actualizar Noticia"
            etTitulo.setText(intent.getStringExtra("titulo"))
            etBajada.setText(intent.getStringExtra("bajada"))
            etUrl.setText(intent.getStringExtra("imageUrl"))
            etAutor.setText(intent.getStringExtra("autor"))
            etFecha.setText(intent.getStringExtra("fecha"))
            etContenido.setText(intent.getStringExtra("contenido"))
        }

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val bajada = etBajada.text.toString()
            val url = etUrl.text.toString()
            val autor = etAutor.text.toString()
            val fecha = etFecha.text.toString()
            val contenido = etContenido.text.toString()

            if (titulo.isEmpty() || bajada.isEmpty()) {
                Toast.makeText(this, "Faltan datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnGuardar.isEnabled = false

            val mapa = hashMapOf(
                "titulo" to titulo, "bajada" to bajada, "imageUrl" to url,
                "autor" to autor, "fecha" to fecha, "contenido" to contenido
            )

            if (idDocumento != null) {
                // 2. ACTUALIZAR (Usamos .set para sobrescribir)
                db.collection("noticias").document(idDocumento).set(mapa)
                    .addOnSuccessListener {
                        Toast.makeText(this, "¡Noticia Actualizada!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        btnGuardar.isEnabled = true
                        Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                    }
            } else {
                // 3. CREAR NUEVA (Usamos .add para crear ID nuevo)
                db.collection("noticias").add(mapa)
                    .addOnSuccessListener {
                        Toast.makeText(this, "¡Publicada!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        btnGuardar.isEnabled = true
                        Toast.makeText(this, "Error al publicar", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}