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
import com.JojanserCorporan.bibliotecadigital.databinding.ActivityRegistrarAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.intellij.lang.annotations.Pattern

class Registrar_Admin : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrarAdminBinding

    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarAdminBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor, espere")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.BtnRegistrarAdmin.setOnClickListener {
            ValidarInformacion()
        }

        binding.TxtTengoCuenta.setOnClickListener {
            startActivity(Intent(this@Registrar_Admin, Login_Admin::class.java))
        }


    }
    var nombres = ""
    var email = ""
    var password = ""
    var r_password = ""
    private fun Registrar_Admin.ValidarInformacion() {
        nombres = binding.EtNombresAdmin.text.toString().trim()
        email = binding.EtEmailAdmin.text.toString().trim()
        password = binding.EtPasswordAdmin.text.toString().trim()
        r_password = binding.EtRPasswordAdmin.text.toString().trim()

        if (nombres.isEmpty()){
            binding.EtNombresAdmin.error = "Ingrese su nombre"
            binding.EtNombresAdmin.requestFocus()
        }
        else if (email.isEmpty()){
            binding.EtEmailAdmin.error = "Ingrese su correo"
            binding.EtEmailAdmin.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtEmailAdmin.error ="Email no es Valido"
            binding.EtEmailAdmin.requestFocus()
        }
        else if(password.isEmpty()){
            binding.EtPasswordAdmin.error = "Ingrese la contraseña"
            binding.EtPasswordAdmin.requestFocus()
        }
        else if(password.length <7){
            binding.EtPasswordAdmin.error = "La contraseña debe de tener 8 caracteres o mas"
            binding.EtPasswordAdmin.requestFocus()
        }
        else if(r_password.isEmpty()){
            binding.EtRPasswordAdmin.error = "Repita la contraseña"
            binding.EtRPasswordAdmin.requestFocus()
        }
        else if(password != r_password){
            binding.EtRPasswordAdmin.error = "Las Contraseñas no coinciden"
        }
        else {
            crearCuentaAdmin(email, password)
        }

    }

    private fun crearCuentaAdmin(email: String, password: String) {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                AgregarInfoDB()

            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Ha fallado la creacion de la cuenta debido a ${e.message}",
                    Toast.LENGTH_SHORT )
                    .show()
            }


    }

    private fun AgregarInfoDB() {
        progressDialog.setMessage("Guardando información...")
        val tiempo = System.currentTimeMillis()
        val uid = firebaseAuth.uid


        val datos_admin : HashMap<String, Any?> = HashMap()
        datos_admin["uid"] = uid
        datos_admin["nombres"] = nombres
        datos_admin["email"] = email
        datos_admin["rol"] = "admin"
        datos_admin["tiempo_registro"] = tiempo
        datos_admin["imagen"] = ""

        val reference = FirebaseDatabase.getInstance().getReference("usuarios")
        reference.child(uid!!)
            .setValue(datos_admin)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Cuenta Creada correctamente",
                    Toast.LENGTH_SHORT )
                    .show()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()



            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se pudo guardar la informacion debido a ${e.message}",
                    Toast.LENGTH_SHORT )
                    .show()

            }



    }













}



