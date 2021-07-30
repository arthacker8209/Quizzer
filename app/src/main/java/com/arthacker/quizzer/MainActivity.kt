 package com.arthacker.quizzer

 import android.content.Intent
 import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
 import android.view.View
 import com.arthacker.quizzer.databinding.ActivityMainBinding
 import com.google.android.material.snackbar.Snackbar

 class MainActivity : AppCompatActivity() {
      private lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.apply {
            btnStart.setOnClickListener {
              if(etName.text.toString().isEmpty()){
                  Snackbar.make(binding.layout,"Name should not be empty",Snackbar.LENGTH_SHORT).show()
              }
                else{
                    val intent = Intent(applicationContext,QuizQuestionsActivity::class.java)
                  intent.putExtra(Constants.USER_NAME, etName.text.toString())
                  startActivity(intent)
                  finish()
                }
            }
        }
    }




































     ////// full screen , Immersive
     override fun onWindowFocusChanged(hasFocus: Boolean) {
         super.onWindowFocusChanged(hasFocus)
         if (hasFocus) hideSystemUI()
     }
     private fun hideSystemUI() {

         window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                 or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                 or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                 or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                 or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                 or View.SYSTEM_UI_FLAG_FULLSCREEN)
     }
     private fun showSystemUI() {
         window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                 or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                 or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
     }
}