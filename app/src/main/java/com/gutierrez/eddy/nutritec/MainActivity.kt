package com.gutierrez.eddy.nutritec

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat.Action
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.nav_fragment.HomeFragment
import com.gutierrez.eddy.nutritec.nav_fragment.ProfileFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val onBackPressedCallBack = object:OnBackPressedCallback(true){
            override fun handleOnBackPressed(){
                onBackPressedMethod()
            }
    }
    private fun onBackPressedMethod() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            finish()
        }
    }

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)

        val navigationView= findViewById<NavigationView>(R.id.navigationView)
        val header= navigationView.getHeaderView(0)
        val userNameTxt = header.findViewById<TextView>(R.id.userNameTxt)
        val emailTxt = header.findViewById<TextView>(R.id.emailText)
        val profileImg = header.findViewById<ImageView>(R.id.profileImg)

        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(HomeFragment())
        navigationView.setCheckedItem(R.id.nav_home)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment,fragment)
            .commit()
    }
    override fun onNavigationItemSelected(item: MenuItem):Boolean {
        when(item.itemId){
            R.id.nav_home -> {
                replaceFragment(HomeFragment())
                title = "Home"
            }
            R.id.nav_profile -> {
                replaceFragment(ProfileFragment())
                title = "Usuario"
            }
            R.id.nav_logout-> {
                Toast.makeText(this,"Logo Clciked",Toast.LENGTH_LONG).show()
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

}
