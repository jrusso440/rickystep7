package com.csc2636.quizapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.Toast
import com.csc2636.quizapp.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val answered = booleanArrayOf(false, false, false, false, false, false)
    var score:Int = 0
    private var numberAnswered = 0
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }
        binding.falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            /*if (answered[currentIndex] == true)
            {
                Toast.makeText(this, "true", Toast.LENGTH_SHORT)
                    .show()
            }
            else
            {
                Toast.makeText(this, "false", Toast.LENGTH_SHORT)
                    .show()
            }*/
            if (answered[currentIndex] == false)
            {
                binding.trueButton.isEnabled = true
                binding.falseButton.isEnabled = true
            }
            else
            {
                binding.trueButton.isEnabled = false
                binding.falseButton.isEnabled = false
            }
            binding.cheatButton.setOnClickListener {
                // Start CheatActivity
                val answerIsTrue = quizViewModel.currentQuestionAnswer
                val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
                startActivity(intent)
            }
            updateQuestion()
        }
        binding.previousButton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            /*if (answered[currentIndex] == true)
            {
                Toast.makeText(this, "true", Toast.LENGTH_SHORT)
                    .show()
            }
            else
            {
                Toast.makeText(this, "false", Toast.LENGTH_SHORT)
                    .show()
            }*/
            if (answered[currentIndex] == false)
            {
                binding.trueButton.isEnabled = true
                binding.falseButton.isEnabled = true
            }
            else
            {
                binding.trueButton.isEnabled = false
                binding.falseButton.isEnabled = false
            }
            updateQuestion()
        }
        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
        /*binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true*/
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        numberAnswered++
        answered[currentIndex] = true
        if (userAnswer == correctAnswer)
        {
            score++
        }
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
        if (numberAnswered == 6)
        {
            val percent = (score / 6) * 100
            val messageResId1 = "Your score is " + percent + "%"
            Toast.makeText(this, messageResId1, Toast.LENGTH_SHORT)
                .show()
        }
    }


}