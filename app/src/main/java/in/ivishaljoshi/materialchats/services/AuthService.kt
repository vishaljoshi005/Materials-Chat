package `in`.ivishaljoshi.materialchats.services

import `in`.ivishaljoshi.materialchats.utilities.URL_REGISTER
import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object AuthService {

    fun registerUser(context: Context, email:String, password:String, complete:(Boolean)-> Unit){
        val jsonBody = JSONObject()
        jsonBody.put("email",email)
        jsonBody.put("password",password)
        val requestBody = jsonBody.toString()
        
        val registerRequest = object :StringRequest(Method.POST, URL_REGISTER, Response.Listener {response ->
            Log.d("Resposne", response)
            complete(true)
        }, Response.ErrorListener { error ->
            Log.d("ERROR", error.toString())
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset-utf8"
            }

            override fun getBody(): ByteArray{
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(registerRequest)
    }
}