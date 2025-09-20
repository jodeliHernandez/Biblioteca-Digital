package com.JojanserCorporan.bibliotecadigital.Fragmentos_Admin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.JojanserCorporan.bibliotecadigital.Administrador.AdaptadorCategoria
import com.JojanserCorporan.bibliotecadigital.Administrador.Agregar_Categoria
import com.JojanserCorporan.bibliotecadigital.Administrador.Agregar_pdf
import com.JojanserCorporan.bibliotecadigital.Administrador.ModeloCategoria
import com.JojanserCorporan.bibliotecadigital.R
import com.JojanserCorporan.bibliotecadigital.databinding.FragmentAdminCuentaBinding
import com.JojanserCorporan.bibliotecadigital.databinding.FragmentAdminDashboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Context
import java.util.ArrayList


class Fragment_admin_dashboard : Fragment() {

    private lateinit var binding: FragmentAdminDashboardBinding
    private lateinit var mContext: android.content.Context
    private lateinit var categoriaArrayList : ArrayList<ModeloCategoria>
    private lateinit var adaptadorCategoria: AdaptadorCategoria



    override fun onAttach(context: android.content.Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ListarCategorias()

        binding.BuscarCategoria.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(
                categoria: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
               try {
                   adaptadorCategoria.filter.filter(categoria)

               }catch (e: Exception){

               }
            }
        } )

        binding.BtnAgregarCategoria.setOnClickListener {
            startActivity(Intent(mContext, Agregar_Categoria::class.java))

        }
        binding.AgregarPdf.setOnClickListener {
            startActivity(Intent(mContext, Agregar_pdf::class.java))
        }

    }



    private fun ListarCategorias(){

        categoriaArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Categorias").orderByChild("categoria")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoriaArrayList.clear()
                for (ds in snapshot.children ){
                    val modelo = ds.getValue(ModeloCategoria::class.java)
                    categoriaArrayList.add(modelo!!)

                }
                adaptadorCategoria = AdaptadorCategoria(mContext, categoriaArrayList)
                binding.categoriaRv.adapter = adaptadorCategoria
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })






    }




}