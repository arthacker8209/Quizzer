package com.arthacker.quizzer

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arthacker.quizzer.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var bgMusic : MediaPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username= intent.getStringExtra(Constants.USER_NAME)
        val score= intent.getIntExtra(Constants.CORRECT_ANSWERS,0)
        val total= intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)

        binding.apply {
           // btnFinish.setBackgroundColor(Color.WHITE)
            btnFinish.setTextColor(Color.WHITE)
            tvName.text= username
            tvScore.text="Your Score is ${score} out of ${total}"
            btnFinish.setOnClickListener {
                pauseBgMusic()

                startActivity(Intent(this@ResultActivity, MainActivity::class.java))
                finish()
            }

        }
        if (score>= total*(70/100)){
            binding.anim.playAnimation()
        playMusic()
        }

    }
    private fun playMusic(){
        bgMusic= MediaPlayer.create(this, R.raw.clap)
        bgMusic?.setOnPreparedListener{
            bgMusic?.start()
        }
        bgMusic?.setOnCompletionListener {
            bgMusic?.stop()
        }

    }

   private fun pauseBgMusic(){
        bgMusic?.stop()
    }

    fun resumeBgMusic(){
        bgMusic?.start()
    }
}