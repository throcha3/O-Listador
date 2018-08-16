package br.com.thiagopgr.olistador.views

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import br.com.thiagopgr.olistador.R
import br.com.thiagopgr.olistador.business.ClassificationBusiness
import br.com.thiagopgr.olistador.business.ThingBusiness
import br.com.thiagopgr.olistador.business.TypeBusiness
import br.com.thiagopgr.olistador.entities.ThingEntity
import br.com.thiagopgr.olistador.repositories.caches.ClassificationCacheConstants
import br.com.thiagopgr.olistador.repositories.caches.TypeCacheConstants
import br.com.thiagopgr.olistador.repositories.helpers.OListadorDatabaseHelper
import br.com.thiagopgr.olistador.util.exception.ValidationException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mOListadorDatabaseHelper : OListadorDatabaseHelper
    private val mClassificationBusiness: ClassificationBusiness = ClassificationBusiness()
    private val mTypeBusiness: TypeBusiness = TypeBusiness()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
*/
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        /*for (i in 1..50){
            executa()
        }*/
        //Toast.makeText(this, "cabou", Toast.LENGTH_LONG).show()
        loadCaches()
        startDefaultFragment()
    }

    private fun startDefaultFragment() {
        // Inicializa fragment
        val fragment: Fragment = ThingsListFragment.newInstance("","")

        // Insere fragment substituindo qualquer existente
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.frameContent, fragment).commit()
    }

    private fun executa(){
        val thing = ThingEntity(0,
                "One Piece",
                "Anime Massa novo que to assistindo aqui",
                1,
                2,
                1,
                830,
                "data",
                "01/08/2018",
                "data",
                10,
                "data",
                43,
                2
                )
        try {
            val mB: ThingBusiness = ThingBusiness(this)
            var insert = mB.insert(thing)
           // Toast.makeText(this, "Deu bom", Toast.LENGTH_SHORT).show()
        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Algo de errado não está certo!!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as? SearchView


        return true
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_search -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                startActivity(Intent(this, ThingFormActivity::class.java))
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadCaches(){
        TypeCacheConstants.setCache(mTypeBusiness.getList())
        ClassificationCacheConstants.setCache(mClassificationBusiness.getList())
    }
}
