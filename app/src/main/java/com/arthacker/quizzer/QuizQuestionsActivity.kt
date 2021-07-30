package com.arthacker.quizzer
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.arthacker.quizzer.databinding.ActivityQuizQuestionsBinding
import com.google.android.material.snackbar.Snackbar

class QuizQuestionsActivity: AppCompatActivity(),View.OnClickListener {
    private var mCurrentPosition:Int =1
    private var mQuestionList: ArrayList<Question>?=null
    private var mSelectedOptionPosition:Int= 0
    private var mCurrentScore:Int =0
    private var username:String?= null
    private val listOfOptions= ArrayList<TextView>()
    private lateinit var binding: ActivityQuizQuestionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username= intent.getStringExtra(Constants.USER_NAME)
         mQuestionList= Constants.getQuestions()
        Log.e("papa", mQuestionList!!.size.toString())
        setQuestion()
        binding.apply {
            tvOptionOne.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionTwo.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionThree.setOnClickListener(this@QuizQuestionsActivity)
            tvOptionFour.setOnClickListener(this@QuizQuestionsActivity)
            btnSubmit.setOnClickListener(this@QuizQuestionsActivity)
            listOfOptions.add(tvOptionOne)
            listOfOptions.add(tvOptionTwo)
            listOfOptions.add(tvOptionThree)
            listOfOptions.add(tvOptionFour)
        }

    }
    private fun  setQuestion(){
        binding.correctAnim.visibility= View.INVISIBLE
        if(mCurrentPosition==mQuestionList?.size){
            binding.btnSubmit.text=getString(R.string.finish)
        }
        else{
            binding.btnSubmit.text= getString(R.string.submit)
        }
        defaultOptionsView()
        val question=mQuestionList!!.get(mCurrentPosition-1)
        binding.apply {
            progressBar.progress= mCurrentPosition
            tvProgress.text= "$mCurrentPosition"+"/"+progressBar.max
            tvQuestion.text= question.question
            tvOptionOne.text= question.optionOne
            tvOptionTwo.text= question.optionTwo
            tvOptionThree.text= question.optionThree
            tvOptionFour.text= question.optionFour
            ivImage.setImageResource(question.image)
        }
    }

    private fun defaultOptionsView(){
        val options= ArrayList<TextView>()
        binding.apply {
            options.add(0,tvOptionOne)
            options.add(1,tvOptionTwo)
            options.add(2,tvOptionThree)
            options.add(3,tvOptionFour)

            for (option in listOfOptions){
                option.isClickable=true
                option.isFocusable=true
            }

            for (option in options){
                option.setTextColor(Color.parseColor("#7A8089"))
                option.typeface= Typeface.DEFAULT
                option.background= ContextCompat.getDrawable(this@QuizQuestionsActivity,R.drawable.default_option_border_bg)
            }
        }

    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.tv_option_one->{selectedOptionView(binding.tvOptionOne,1)}
            R.id.tv_option_two->{selectedOptionView(binding.tvOptionTwo,2)}
            R.id.tv_option_three->{selectedOptionView(binding.tvOptionThree,3)}
            R.id.tv_option_four->{selectedOptionView(binding.tvOptionFour,4)}

           ///////////////////////////////////////////////////////////////////////////////////////
            R.id.btn_submit->{

                for (option in listOfOptions){
                    option.isClickable=false
                    option.isFocusable=false
                }

                if (mSelectedOptionPosition==0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition<=mQuestionList!!.size->{
                            setQuestion()
                        }
                        else->{
                           // Snackbar.make(binding.quizLayout, "Quiz Completed Successfully", Snackbar.LENGTH_LONG).show()
                           val intent= Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,username)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCurrentScore)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList?.size)
                            startActivity(intent)
                        }
                    }
                }
                else{

                    val question= mQuestionList?.get(mCurrentPosition-1)
                    if (question!!.correctAnswer!=mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
                        answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
                        binding.correctAnim.visibility=View.INVISIBLE
                    }
                    else{
                        binding.correctAnim.visibility=View.VISIBLE
                        binding.correctAnim.playAnimation()
                        answerView(question.correctAnswer,R.drawable.correct_option_border_bg)
                        mCurrentScore++;
                    }
                    mSelectedOptionPosition=0

                    if (mCurrentPosition==mQuestionList?.size){
                        binding.btnSubmit.text=getString(R.string.finish)
                    }
                    else{
                        binding.btnSubmit.text=getString(R.string.next)
                    }

                }


            }

            /////////////////////////////////////////////////////////////////////////////////////////////////////
        }

    }

    private fun answerView(answer:Int, drawableView:Int){
        when(answer){
            1->{
                binding.tvOptionOne.background= ContextCompat.getDrawable(applicationContext, drawableView)
                binding.tvOptionOne.setTypeface(binding.tvOptionOne.typeface, Typeface.BOLD)

            }
            2->{
                binding.tvOptionTwo.background= ContextCompat.getDrawable(applicationContext, drawableView)
                binding.tvOptionTwo.setTypeface(binding.tvOptionTwo.typeface, Typeface.BOLD)
            }
            3->{
                binding.tvOptionThree.background= ContextCompat.getDrawable(applicationContext, drawableView)
                binding.tvOptionThree.setTypeface(binding.tvOptionThree.typeface, Typeface.BOLD)
            }
            4->{
                binding.tvOptionFour.background= ContextCompat.getDrawable(applicationContext, drawableView)
                binding.tvOptionFour.setTypeface(binding.tvOptionFour.typeface, Typeface.BOLD)
            }

        }
    }
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()
        mSelectedOptionPosition= selectedOptionNum
       tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.background= ContextCompat.getDrawable(this@QuizQuestionsActivity,R.drawable.selected_option_border_bg)
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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