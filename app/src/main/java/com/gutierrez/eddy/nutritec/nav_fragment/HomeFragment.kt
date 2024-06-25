package com.gutierrez.eddy.nutritec.nav_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.bottom_fragment.ComidaFragment
import com.gutierrez.eddy.nutritec.bottom_fragment.Comida_DesignadaFragment
import com.gutierrez.eddy.nutritec.bottom_fragment.EjercicioFragment
import com.gutierrez.eddy.nutritec.bottom_fragment.Ejercicio_DesignadoFragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_Comida -> {
                    replaceFragment(ComidaFragment())
                    requireActivity().title = "Comida"
                }
                R.id.bottom_Ejercicio -> {
                    replaceFragment(EjercicioFragment())
                    requireActivity().title = "Ejercicio"
                }
                R.id.bottom_Comida_Designada -> {
                    replaceFragment(Comida_DesignadaFragment())
                    requireActivity().title = "Comida Designada"
                }
                R.id.bottom_Ejecicio_Designado -> {
                    replaceFragment(Ejercicio_DesignadoFragment())
                    requireActivity().title = "Ejercicio Designado"
                }
            }
            true
        }

        // Establecer el fragmento por defecto al iniciar HomeFragment
        if (savedInstanceState == null) {
            replaceFragment(ComidaFragment()) // Fragmento por defecto al iniciar
            requireActivity().title = "Comida"
            bottomNavigationView.selectedItemId = R.id.bottom_Comida
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.bottomFragment, fragment)
            .commit()
    }
}
