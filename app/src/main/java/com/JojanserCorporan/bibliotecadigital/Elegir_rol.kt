package com.JojanserCorporan.bibliotecadigital

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.JojanserCorporan.bibliotecadigital.Administrador.Registrar_Admin
import com.JojanserCorporan.bibliotecadigital.databinding.ActivityElegirRolBinding

class Elegir_rol : AppCompatActivity() {

    private lateinit var binding: ActivityElegirRolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityElegirRolBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.BtnRolAdministrador.setOnClickListener {
           // Toast.makeText(applicationContext, "Rol administrador", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Elegir_rol, Registrar_Admin::class.java))

        }

        binding.BtnRolLector.setOnClickListener {
            Toast.makeText(applicationContext, "Rol lector", Toast.LENGTH_SHORT).show()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}