package com.JojanserCorporan.bibliotecadigital

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.JojanserCorporan.bibliotecadigital.Fragmentos_Admin.Fragment_admin_cuenta
import com.JojanserCorporan.bibliotecadigital.Fragmentos_Admin.Fragment_admin_dashboard
import com.JojanserCorporan.bibliotecadigital.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        ComprobarSesion()

        VerFragmentoDashboard()

        binding.BottomNvAdmin.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Menu_panel -> {
                    VerFragmentoDashboard()
                    true
                }
                R.id.Menu_cuenta -> {
                    VerFragmentoCuenta()
                    true
                }
                else -> false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun VerFragmentoDashboard() {
        binding.TituloRLAdmin.text = "Dashboard"
        val fragment = Fragment_admin_dashboard()
        supportFragmentManager.beginTransaction()
            .replace(binding.FramentsAdmin.id, fragment, "Fragment dashboard")
            .commit()
    }

    private fun VerFragmentoCuenta() {
        binding.TituloRLAdmin.text = "Mi cuenta"
        val fragment = Fragment_admin_cuenta()
        supportFragmentManager.beginTransaction()
            .replace(binding.FramentsAdmin.id, fragment, "Fragment mi cuenta")
            .commit()
    }

    private fun ComprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, Elegir_rol ::class.java))
            finishAffinity()
        }
        else{
            /*Toast.makeText(applicationContext, "Bienvenido(a) ${firebaseUser.email}", Toast.LENGTH_SHORT).show()*/
        }
    }
}