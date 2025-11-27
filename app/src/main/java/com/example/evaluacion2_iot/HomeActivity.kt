package com.example.evaluacion2_iot

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.evaluacion2_iot.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: NoticiaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        configurarRecyclerView()
        cargarNoticias()

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.fabAgregar.setOnClickListener {
            startActivity(Intent(this, AgregarNoticiaActivity::class.java))
        }
    }

    private fun configurarRecyclerView() {
        binding.rvNoticias.layoutManager = LinearLayoutManager(this)
        adapter = NoticiaAdapter(
            noticias = listOf(),
            onClick = { noticia ->
                // Abrir detalle enviando TODO
                val intent = Intent(this, VerNoticiaActivity::class.java)
                intent.putExtra("id", noticia.id) // 👈 ID fundamental para editar
                intent.putExtra("titulo", noticia.titulo)
                intent.putExtra("bajada", noticia.bajada) // 👈 Bajada fundamental para editar
                intent.putExtra("contenido", noticia.contenido)
                intent.putExtra("autor", noticia.autor)
                intent.putExtra("fecha", noticia.fecha)
                intent.putExtra("imageUrl", noticia.imageUrl)
                startActivity(intent)
            },
            onLongClick = { noticia -> confirmarBorrado(noticia) }
        )
        binding.rvNoticias.adapter = adapter
    }

    private fun cargarNoticias() {
        db.collection("noticias").addSnapshotListener { snapshots, e ->
            if (e != null) return@addSnapshotListener
            val lista = mutableListOf<Noticia>()
            for (doc in snapshots!!) {
                val noticia = doc.toObject(Noticia::class.java)
                noticia.id = doc.id
                lista.add(noticia)
            }
            adapter.actualizarLista(lista)
        }
    }

    private fun confirmarBorrado(noticia: Noticia) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar")
            .setMessage("¿Borrar '${noticia.titulo}'?")
            .setPositiveButton("Sí") { _, _ ->
                db.collection("noticias").document(noticia.id).delete()
            }
            .setNegativeButton("No", null)
            .show()
    }
}