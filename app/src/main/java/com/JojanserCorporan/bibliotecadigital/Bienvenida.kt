package com.JojanserCorporan.bibliotecadigital

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Bienvenida : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bienvenida)
        firebaseAuth = FirebaseAuth.getInstance()


        VerBienvenida()
    }
    fun VerBienvenida(){
        object : CountDownTimer(2000, 1000){
            override fun onFinish() {
               ComprobarSesion()
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }.start()
    }

    private fun ComprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this, Elegir_rol ::class.java))
            finishAffinity()
        }
        else{
            val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
            reference.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val rol = snapshot.child("rol").value
                        if(rol == "admin"){
                            startActivity(Intent(this@Bienvenida, MainActivity::class.java))
                            finishAffinity()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
        }
    }

}