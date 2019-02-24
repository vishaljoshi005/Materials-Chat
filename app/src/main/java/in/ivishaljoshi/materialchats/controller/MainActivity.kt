package `in`.ivishaljoshi.materialchats.controller

import `in`.ivishaljoshi.materialchats.R
import `in`.ivishaljoshi.materialchats.services.AuthService
import `in`.ivishaljoshi.materialchats.services.UserDataService
import `in`.ivishaljoshi.materialchats.utilities.BROADCAST_USER_DATA_CHANGE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        messageChannelName.visibility = View.INVISIBLE
        sendMessageBtn.visibility = View.INVISIBLE



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReceiver,
            IntentFilter(BROADCAST_USER_DATA_CHANGE))


    }

    private val userDataChangeReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if(AuthService.isLoggedIn){
                Log.d("qwe", UserDataService.name)
                Log.d("qwe", UserDataService.email)
                Log.d("qwe", UserDataService.avatarName)
                Log.d("qwe", "Made it inside the broadcast bro")

                Log.d("qwe", "Wrap")

                sendMessageBtn.visibility = View.VISIBLE
                messageChannelName.visibility = View.VISIBLE
                nav_drawer_header_include.userEmailNavHeader.text = UserDataService.email
                nav_drawer_header_include.userNameNavHeader.text = UserDataService.name
                userEmailNavHeader.text = UserDataService.email
                val resourseid= resources.getIdentifier(UserDataService.avatarName,"drawable",packageName)
                nav_drawer_header_include.userImageNavHeader.setImageResource(resourseid)
                nav_drawer_header_include.userImageNavHeader.setBackgroundColor(UserDataService.returnAvatarColor(UserDataService.avatarColor))
                nav_drawer_header_include.loginBtnNavHeader.text="Logout"

            }

        }
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun loginBtnNavClicked(view: View){

        if(AuthService.isLoggedIn){
            //logout
            UserDataService.logout()
            nav_drawer_header_include.userEmailNavHeader.text = ""
            nav_drawer_header_include.userNameNavHeader.text = "Login"
            nav_drawer_header_include.userImageNavHeader.setImageResource(R.drawable.profiledefault)
            nav_drawer_header_include.userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
            nav_drawer_header_include.loginBtnNavHeader.text = "Login"

            messageChannelName.visibility = View.INVISIBLE
            sendMessageBtn.visibility = View.INVISIBLE
        }else{
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }

    }

    fun addChannelClicked(view:View){

    }

    fun sendMessageBtnClicked (view:View){

    }

}
