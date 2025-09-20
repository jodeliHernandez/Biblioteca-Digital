package com.JojanserCorporan.bibliotecadigital.Fragmentos_Admin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.JojanserCorporan.bibliotecadigital.Elegir_rol
import com.JojanserCorporan.bibliotecadigital.R
import com.JojanserCorporan.bibliotecadigital.databinding.FragmentAdminCuentaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Context

class Fragment_admin_cuenta : Fragment() {

    private lateinit var binding: FragmentAdminCuentaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var Mcontext: android.content.Context

    override fun onAttach(context: android.content.Context) {
        Mcontext = context
        super.onAttach(context)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

           binding = FragmentAdminCuentaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.CerrarSesionAdmin.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(context, Elegir_rol::class.java))
            activity?.finishAffinity()
        }
    }


}