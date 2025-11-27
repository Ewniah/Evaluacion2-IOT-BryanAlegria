package com.example.evaluacion2_iot

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton

class VerNoticiaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_noticia)

        // 1. Recibir TODOS los datos
        val id = intent.getStringExtra("id")
        val titulo = intent.getStringExtra("titulo")
        val bajada = intent.getStringExtra("bajada") // Importante para editar
        val contenido = intent.getStringExtra("contenido")
        val autor = intent.getStringExtra("autor")
        val fecha = intent.getStringExtra("fecha")
        val imageUrl = intent.getStringExtra("imageUrl")

        // 2. Mostrar datos en pantalla
        findViewById<TextView>(R.id.tv_detalle_titulo).text = titulo
        findViewById<TextView>(R.id.tv_detalle_fecha_autor).text = "$fecha - $autor"
        findViewById<TextView>(R.id.tv_detalle_contenido).text = contenido

        val ivImagen = findViewById<ImageView>(R.id.iv_detalle_imagen)
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this).load(imageUrl).into(ivImagen)
        }

        // Botón Volver
        findViewById<Button>(R.id.btn_volver).setOnClickListener { finish() }

        // 3. BOTÓN EDITAR: Reenvía los datos al formulario
        findViewById<FloatingActionButton>(R.id.fab_editar).setOnClickListener {
            val intent = Intent(this, AgregarNoticiaActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("titulo", titulo)
            intent.putExtra("bajada", bajada)
            intent.putExtra("contenido", contenido)
            intent.putExtra("autor", autor)
            intent.putExtra("fecha", fecha)
            intent.putExtra("imageUrl", imageUrl)
            startActivity(intent)
            finish() // Cerramos para que se actualice al volver
        }
    }
}