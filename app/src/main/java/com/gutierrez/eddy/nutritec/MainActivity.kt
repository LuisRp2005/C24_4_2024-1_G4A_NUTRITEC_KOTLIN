package com.gutierrez.eddy.nutritec

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.gutierrez.eddy.nutritec.nav_fragment.ChatGPTFragment
import com.gutierrez.eddy.nutritec.nav_fragment.HomeFragment
import com.gutierrez.eddy.nutritec.nav_fragment.ProfileFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Mostrar el fragmento Home al iniciar la actividad, solo si no hay instancia guardada
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navFragment, HomeFragment()) // Mostrar HomeFragment al inicio
                .commit()
            title = "Home" // Establecer título inicial
            navigationView.setCheckedItem(R.id.nav_home) // Marcar como seleccionado el item Home en el NavigationView
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val selectedFragment: Fragment = when (item.itemId) {
            R.id.nav_home -> HomeFragment()
            R.id.nav_profile -> ProfileFragment()
            R.id.nav_chatgpt -> ChatGPTFragment() // Mostrar ChatGPTFragment al seleccionar "ChatGPT"
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return false
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.navFragment, selectedFragment)
            .commit()
        title = item.title // Establecer título de la actividad
        drawerLayout.closeDrawer(GravityCompat.START) // Cerrar el drawer después de hacer clic en un ítem
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
