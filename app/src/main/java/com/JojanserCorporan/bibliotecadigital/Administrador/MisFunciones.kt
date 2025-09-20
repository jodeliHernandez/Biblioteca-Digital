package com.JojanserCorporan.bibliotecadigital.Administrador

import android.app.Application
import android.content.Context
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata

import java.util.Calendar
import java.util.Locale
import android.text.format.DateFormat
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.joanzapata.pdfview.PDFView
import java.io.File


class MisFunciones : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    companion object {

        fun formatoTiempo(tiempo: Long): String {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = tiempo
            return android.text.format.DateFormat.format("dd/MM/yyyy", cal).toString()
        }

        fun CargarTamanioPdf(pdfUrl: String, pdfTitulo: String, tamanio: TextView) {
            val ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
            ref.metadata
                .addOnSuccessListener { metadata ->
                    val bytes = metadata.sizeBytes.toDouble()
                    val KB = bytes / 1024
                    val MB = KB / 1024

                    if (MB > 1) {
                        tamanio.text = "${String.format("%.2f", MB)} MB"
                    } else if (KB >= 1) {
                        tamanio.text = "${String.format("%.2f", KB)} KB"
                    } else {
                        tamanio.text = "${String.format("%.2f", bytes)} Bytes"
                    }
                }
                .addOnFailureListener { e ->

                }
        }




         fun CargarPdfUrl(
             paginaTv: android.widget.TextView?,
             pdfUrl: String,
             pdfTitulo: String,
             pdfView: com.joanzapata.pdfview.PDFView,
             progressBar: ProgressBar,
             context: Context
         ) {
             val ref = FirebaseStorage.getInstance().getReferenceFromUrl(pdfUrl)
             ref.getBytes(Constantes.Maximo_bytes_pdf)
                 .addOnSuccessListener { bytes ->
                     // Guardar bytes en archivo temporal
                     val tempFile = File.createTempFile("temp_pdf", ".pdf", context.cacheDir)
                     tempFile.writeBytes(bytes)

                     // Cargar PDF desde archivo temporal
                     pdfView.fromFile(tempFile)

                     // Ocultar progressBar y actualizar paginaTv
                     progressBar.visibility = View.INVISIBLE
                     paginaTv?.text = "1" // Página inicial
                 }
                 .addOnFailureListener { e ->
                     progressBar.visibility = View.INVISIBLE
                 }

         }

        fun CargarCategoria(categoriaId: String, categoriaTv: TextView) {
            val ref = FirebaseDatabase.getInstance().getReference("Categorias")
            ref.child(categoriaId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val categoria = "${snapshot.child("categoria").value}"
                        categoriaTv.text = categoria
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
        }






    }

}