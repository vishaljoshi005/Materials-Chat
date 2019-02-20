package `in`.ivishaljoshi.materialchats.controller

import `in`.ivishaljoshi.materialchats.R
import `in`.ivishaljoshi.materialchats.services.AuthService
import `in`.ivishaljoshi.materialchats.utilities.BROADCAST_USER_DATA_CHANGE
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {


    var userAvatar = "profileDefault"
    var avatarColor= "[0.5,0.5,0.5,1]" /* This is because is saved in fire base like this. mac have rgb values between 0 and 1 */

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility= View.INVISIBLE
    }

    fun generateAvatarImage(view:View){
        val random = Random()
        val avatar = random.nextInt(28) /* These values are upper bound values and doesn't included in generating */
        val color = random.nextInt(2)

        if(color==0){
            userAvatar = "light$avatar"
        }else{
            userAvatar = "dark$avatar"
        }
        val resourseId = resources.getIdentifier(userAvatar,"drawable",packageName)

        createAvatarImageView.setImageResource(resourseId)
    }

    fun backgroundBtnClicked(view:View){
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        createAvatarImageView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble()/255
        val savedG = g.toDouble()/255
        val savedB = b.toDouble()/255

        avatarColor = "[$savedR, $savedG, $savedB, 1 ]"
    }
    fun createUserBtnClicked(view:View){
        enableSpinner(true)

        val userName = createUserNameText.text.toString()
        val email=createUserEmailText.text.toString()
        val password=createUserPasswordText.text.toString()

        if(userName.isNotEmpty()&& email.isNotEmpty()&&password.isNotEmpty()){

            AuthService.registerUser(this,email,password){ registerSuccess->
                if(registerSuccess) {
                    Log.d("qwe","registerSuccess")
                    AuthService.loginUser(this,email, password){loginSuccess->
                        if (loginSuccess){
                            Log.d("qwe","loginSuccess")
                            AuthService.createUser(this,userName,email, userAvatar, avatarColor){ createSuccess ->
                                if(createSuccess){
                                    val userDataChangeIntent = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChangeIntent)


                                    Log.d("qwe","createSuccess")
                                    enableSpinner(false)
                                    finish()
                                }else{
                                    errorToast()
                                }

                            }
                        }else{
                            errorToast()
                        }
                    }
                }else{
                    errorToast()
                }
            }

        } else{
            Toast.makeText(this,"Please fill in all the details", Toast.LENGTH_LONG).show()
        }








    }

    fun errorToast(){
        Toast.makeText(this, "Something Went Wrong. Please Try Again Later", Toast.LENGTH_LONG).show()
    }

    fun enableSpinner(enable:Boolean){
        if(enable) {
            createSpinner.visibility = View.VISIBLE
        }else{
            createSpinner.visibility = View.INVISIBLE
        }
        createUserBtn.isEnabled= !enable
        createAvatarImageView.isEnabled = !enable
        backgroundColorBtn.isEnabled = !enable
    }
}
