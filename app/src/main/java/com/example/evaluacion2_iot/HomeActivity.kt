package com.example.evaluacion2_iot // ⚠️ Verifica tu package name

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.evaluacion2_iot.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // 🧪 PRUEBA: Mensaje para confirmar que llegaste al Home
        Toast.makeText(this, "¡Bienvenido al Home!", Toast.LENGTH_LONG).show()

        // Configurar la lista (RecyclerView)
        // Por ahora estará vacía hasta que hagamos el adaptador en el siguiente paso
        binding.rvNoticias.layoutManager = LinearLayoutManager(this)

        // --- BOTÓN: CERRAR SESIÓN ---
        binding.btnLogout.setOnClickListener {
            auth.signOut() // Cerrar sesión en Firebase

            // Volver al Login y borrar historial para no volver atrás
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // --- BOTÓN FLOTANTE (+): AGREGAR NOTICIA ---
        binding.fabAgregar.setOnClickListener {
            // Aquí iremos a la pantalla de crear noticia.
            // Por ahora mostramos un mensaje hasta que creemos esa pantalla.
            Toast.makeText(this, "Ir a Agregar Noticia", Toast.LENGTH_SHORT).show()

            // CUANDO TENGAS LA ACTIVIDAD CREADA, DESCOMENTA ESTO:
            // startActivity(Intent(this, AgregarNoticiaActivity::class.java))
        }
    }
}