package `in`.ivishaljoshi.materialchats.services

import `in`.ivishaljoshi.materialchats.utilities.URL_CREATE_USER
import `in`.ivishaljoshi.materialchats.utilities.URL_LOGIN
import `in`.ivishaljoshi.materialchats.utilities.URL_REGISTER
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

object AuthService {

    var userEmail=""
    var authToken= ""
    var isLoggedIn=false

    fun registerUser(context: Context, email:String, password:String, complete:(Boolean)-> Unit){
        val jsonBody = JSONObject()
        jsonBody.put("email",email)
        jsonBody.put("password",password)
        val requestBody = jsonBody.toString()
        
        val registerRequest = object :StringRequest(Method.POST, URL_REGISTER, Response.Listener {response ->
            complete(true)
            Log.d("Resposne", response)
        }, Response.ErrorListener { error ->
            Log.d("ERROR", error.toString())
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray{
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(registerRequest)
    }

    fun loginUser(context:Context, email: String,password: String, complete: (Boolean) -> Unit){
        val jsonBody = JSONObject()
        jsonBody.put("email",email)
        jsonBody.put("password",password)
        val requestBody = jsonBody.toString()

        val loginRequest = object: JsonObjectRequest(Method.POST, URL_LOGIN, null , Response.Listener { response->

            //Json response may not have the key we asking for best to put in try catch

            try {
                authToken = response.getString("token")
                userEmail = response.getString("user")
                isLoggedIn = true
                Log.d("qwe", response.toString())
                complete(true)
            }catch (e:JSONException){
                Log.d("qwe", "jSON PARSING ERROR ${e.localizedMessage}")
                complete(false)
            }

        },
            Response.ErrorListener {error ->
                Log.d("qwe", error.localizedMessage)
                complete(false)
            }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(loginRequest)
    }

    fun createUser(context: Context, name:String, email: String, avatarName:String, avatarColor: String,complete: (Boolean) -> Unit){
        val jsonBody = JSONObject()
        jsonBody.put("name",name)
        jsonBody.put("email",email)
        jsonBody.put("avatarName", avatarName)
        jsonBody.put("avatarColor",avatarColor)
        val requestBody = jsonBody.toString()

        //nameDiff
        val createUserRequest = object: JsonObjectRequest(Method.POST, URL_CREATE_USER, null, Response.Listener { resposne->
            try {
                UserDataService.name = resposne.getString("name")
                UserDataService.id = resposne.getString("_id")
                UserDataService.email = resposne.getString("email")
                UserDataService.avatarColor = resposne.getString("avatarColor")
                UserDataService.avatarName= resposne.getString("avatarName")

                complete(true)


            }catch (e:JSONException){
                Log.d("qwe", "jSON PARSING ERROR ${e.localizedMessage}")
                complete(false)
            }
        }, Response.ErrorListener {error ->
            Log.d("qwe", error.localizedMessage)
            complete(false)

        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String,String>()
                header.put("Authorization", "Bearer $authToken")
                return header
            }
        }
        Volley.newRequestQueue(context).add(createUserRequest)
    }

}