package com.example.evaluacion2_iot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide // Importante

class NoticiaAdapter(
    private var noticias: List<Noticia>,
    private val onClick: (Noticia) -> Unit,
    private val onLongClick: (Noticia) -> Unit
) : RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>() {

    class NoticiaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.tv_titulo_noticia)
        val fecha: TextView = view.findViewById(R.id.tv_fecha_noticia)
        val bajada: TextView = view.findViewById(R.id.tv_bajada_noticia)
        val imagen: ImageView = view.findViewById(R.id.iv_noticia_imagen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = noticias[position]

        holder.titulo.text = noticia.titulo
        holder.fecha.text = "${noticia.fecha} • ${noticia.autor}"
        holder.bajada.text = noticia.bajada

        // Cargar imagen con Glide
        if (noticia.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(noticia.imageUrl)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.imagen)
        } else {
            holder.imagen.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.itemView.setOnClickListener { onClick(noticia) }
        holder.itemView.setOnLongClickListener {
            onLongClick(noticia)
            true
        }
    }

    override fun getItemCount() = noticias.size

    fun actualizarLista(nuevaLista: List<Noticia>) {
        noticias = nuevaLista
        notifyDataSetChanged()
    }
}