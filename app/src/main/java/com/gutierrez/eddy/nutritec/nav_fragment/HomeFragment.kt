package com.gutierrez.eddy.nutritec.nav_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.bottom_fragment.ComidaFragment
import com.gutierrez.eddy.nutritec.bottom_fragment.Comida_DesignadaFragment
import com.gutierrez.eddy.nutritec.bottom_fragment.EjercicioFragment
import com.gutierrez.eddy.nutritec.bottom_fragment.Ejercicio_DesignadoFragment

class HomeFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_Comida -> {
                    replaceFragment(ComidaFragment())
                    activity?.title = "Comida"
                }
                R.id.bottom_Ejercicio -> {
                    replaceFragment(EjercicioFragment())
                    activity?.title = "Ejercicio"
                }
                R.id.bottom_Ejecicio_Designado -> {
                    replaceFragment(Ejercicio_DesignadoFragment())
                    activity?.title = "Ejercicio Designado"
                }
                R.id.bottom_Comida_Designada -> {
                    replaceFragment(Comida_DesignadaFragment())
                    activity?.title = "Comida Designada"
                }
            }
            true
        }
        replaceFragment(ComidaFragment())
        activity?.title = "Comida"
        bottomNavigationView.selectedItemId = R.id.bottom_Comida

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment, fragment)
            .commit()
    }
}
