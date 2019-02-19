package `in`.ivishaljoshi.materialchats.controller

import `in`.ivishaljoshi.materialchats.R
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor= "[0.5,0.5,0.5,1]" /* This is because is saved in fire base like this. mac have rgb values between 0 and 1 */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
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

    }
}
