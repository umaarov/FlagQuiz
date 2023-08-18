package uz.umarov.flagquiz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import uz.umarov.flagquiz.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizQuestionsBinding

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mUserName: String? = null
    private var mCorrectAnswers: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        mQuestionsList = Constants.getQuestions()

        setQuestion()
        defaultOptionsView()
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {


//        Log.i("QuestionsList size is", "${mQuestionsList!!.size}")
//        for (i in mQuestionsList!!) {
//            Log.e("Questions: ", i.question)
//        }
        defaultOptionsView()

        val question: Question = mQuestionsList!![mCurrentPosition - 1]

        binding.imageView.setImageResource(question.image)
        binding.progressBar.progress = mCurrentPosition
        binding.textProgressBar.text = "$mCurrentPosition/${binding.progressBar.max}"
        binding.questionText.text = question.question


        binding.apply {
            optionOne.text = question.optionOne
            optionTwo.text = question.optionTwo
            optionThree.text = question.optionThree
            optionFour.text = question.optionFour
        }
        if (mCurrentPosition == mQuestionsList!!.size) {
            binding.btnSubmit.text = "FINISH"
        } else {
            binding.btnSubmit.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()

        binding.apply {

            optionOne.let {
                options.add(0, it)
            }

            optionTwo.let {
                options.add(1, it)
            }

            optionThree.let {
                options.add(2, it)
            }

            optionFour.let {
                options.add(3, it)
            }

        }
        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }


    private fun selectedOptionView(textView: TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        textView.setTextColor(Color.parseColor("#005bbb"))
        textView.setTypeface(textView.typeface, Typeface.BOLD)
        textView.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )

    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.optionOne -> {
                binding.optionOne.let {
                    selectedOptionView(it, 1)
                }
            }

            R.id.optionTwo -> {
                binding.optionTwo.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.optionThree -> {
                binding.optionThree.let {
                    selectedOptionView(it, 3)
                }
            }

            R.id.optionFour -> {
                binding.optionFour.let {
                    selectedOptionView(it, 4)
                }
            }

            R.id.btnSubmit -> {
                if (mSelectedOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }

                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                            startActivity(intent)
                            finish()

                            Toast.makeText(this, "Congrats! You made it", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(
                            mSelectedOptionPosition, R.drawable.wrong_option_border_bg
                        )
                    } else {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        binding.btnSubmit.text = "FINISH"
                    } else {
                        binding.btnSubmit.text = "Go to next question"
                    }

                    mSelectedOptionPosition = 0

                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                binding.optionOne.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            2 -> {
                binding.optionTwo.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            3 -> {
                binding.optionThree.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

            4 -> {
                binding.optionFour.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
}