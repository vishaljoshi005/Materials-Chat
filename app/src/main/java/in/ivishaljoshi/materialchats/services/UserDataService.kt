package `in`.ivishaljoshi.materialchats.services

import android.graphics.Color
import android.util.Log
import java.util.*


object UserDataService {
    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var name = ""
    var email = ""

    fun logout(){
        id = ""
        avatarColor = ""
        avatarName = ""
        name = ""
        email = ""
        AuthService.authToken = ""
        AuthService.userEmail = ""
        AuthService.isLoggedIn = false
    }

    fun returnAvatarColor (component: String):Int{

        Log.d("qwe",component)
        val strippedColor = component.replace("[","")
            .replace("]","")
            .replace(",","")
        Log.d("qwe",strippedColor)

        var r:Int = 0
        var g:Int = 0
        var b:Int = 0

        val scanner = Scanner(strippedColor)
            if(scanner.hasNextDouble()){
//                Log.d("qwe","before Wrap")
//                Log.d("qwe","${(scanner.nextDouble()*255).toInt()}" )
//                Log.d("qwe","after Wrap")
//                Log.d("qwe","${scanner.nextDouble()}" )
//                Log.d("qwe","${scanner.nextDouble()}" )
//                Log.d("qwe","${scanner.nextDouble()}" )

                Log.d("qwe","before toInt() Wrap")
                r = (scanner.nextDouble()*255).toInt()
                Log.d("qwe","after toInt() Wrap")
                g = (scanner.nextDouble() * 255).toInt()
                b = (scanner.nextDouble() * 255).toInt()

                Log.d("qwe","$r" )
                Log.d("qwe","$g" )
                Log.d("qwe","$b" )
            }
        return Color.rgb(r,g,b)
    }
}