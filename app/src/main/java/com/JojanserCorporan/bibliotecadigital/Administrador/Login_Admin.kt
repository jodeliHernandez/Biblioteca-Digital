package com.JojanserCorporan.bibliotecadigital.Administrador

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.JojanserCorporan.bibliotecadigital.MainActivity
import com.JojanserCorporan.bibliotecadigital.R
import com.JojanserCorporan.bibliotecadigital.databinding.ActivityLoginAdminBinding
import com.JojanserCorporan.bibliotecadigital.databinding.FragmentAdminCuentaBinding
import com.google.firebase.auth.FirebaseAuth

class Login_Admin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAdminBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.BtnLoginAdmin.setOnClickListener {
            ValidarInformacion()
        }


    }
    private var email = ""
    private var password = ""
    private fun Login_Admin.ValidarInformacion() {
        email = binding.EtEmailAdmin.text.toString().trim()
        password = binding.EtPasswordAdmin.text.toString().trim()

        if(email.isEmpty()){
            binding.EtEmailAdmin.error = "Ingrese su correo"
            binding.EtEmailAdmin.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            binding.EtEmailAdmin.error = "Correo invalido"
            binding.EtEmailAdmin.requestFocus()
        }
        else if (password.isEmpty()){
            binding.EtPasswordAdmin.error = "Ingrese su contraseña"
            binding.EtPasswordAdmin.requestFocus()
        }
        else{
            LoginAdmin()
        }
    }
    private fun LoginAdmin(){
        progressDialog.setMessage("iniciando Sesion")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@Login_Admin, MainActivity::class.java))
                finishAffinity()
            }

            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Nose pudo iniciar sesion debido a ${e.message}",
                    Toast.LENGTH_SHORT).show()

            }



    }


}


