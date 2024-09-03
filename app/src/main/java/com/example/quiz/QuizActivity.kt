package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class QuizActivity : AppCompatActivity() {

    private val imagesFlags = arrayOf(
        R.drawable.bandeira_japao,        // Japão
        R.drawable.bandeira_irlanda,      // Irlanda
        R.drawable.bandeira_uruguai,      // Uruguai
        R.drawable.bandeira_palestina,    // Palestina
        R.drawable.bandeira_paises_baixo, // Holanda
        R.drawable.bandeira_lituania,     // Lituania
        R.drawable.bandeira_libano,       // Líbano
        R.drawable.bandeira_brasil,       // Brasil
        R.drawable.bandeira_romenia,      // Romenia
        R.drawable.bandeira_filipinas     // Filipinas
    )

    private val options = arrayOf(
        arrayOf("Afeganistão", "Japão", "Índia", "Estados Unidos"),           // 1 Japão
        arrayOf("Irlanda", "Canadá", "Uganda", "Holanda"),                    // 0 Irlanda
        arrayOf("Índia", "Grécia", "Uruguai", "México"),                      // 2 Uruguai
        arrayOf("Estados Unidos", "Brasil", "Argentina", "Palestina"),        // 3 Palestina
        arrayOf("Coréia", "Lituania", "Irlanda", "Holanda"),                  // 3 Holanda
        arrayOf("Lituania", "Argélia", "Angola", "Irlanda"),                  // 0 Lituania
        arrayOf("Chile", "China", "Líbano", "Alemanha"),                      // 2 Líbano
        arrayOf("Bélgica", "Rússia", "Singapura", "Brasil"),                  // 3 Brasil
        arrayOf("Romenia", "Árabia Saudita", "Barbados", "África do Sul"),    // 0 Romenia
        arrayOf("Camarões", "Filipinas", "Congo", "Reino Unido")              // 1 Filipinas
    )

    private val correctAnswers = arrayOf(1, 0, 2, 3, 3, 0, 2, 3, 0, 1)

    private var currentQuestionIndex = 0
    private var score = 0

    private lateinit var rdOption1: RadioButton
    private lateinit var rdOption2: RadioButton
    private lateinit var rdOption3: RadioButton
    private lateinit var rdOption4: RadioButton
    private lateinit var playerName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        rdOption1 = findViewById(R.id.radioButton1)
        rdOption2 = findViewById(R.id.radioButton2)
        rdOption3 = findViewById(R.id.radioButton3)
        rdOption4 = findViewById(R.id.radioButton4)
        playerName = intent.getStringExtra("Player Name").orEmpty()

        findViewById<Button>(R.id.btn_next).setOnClickListener {
            val selectedOptionIndex = when (findViewById<RadioGroup>(R.id.radioGroup).checkedRadioButtonId) {
                R.id.radioButton1 -> 0
                R.id.radioButton2 -> 1
                R.id.radioButton3 -> 2
                R.id.radioButton4 -> 3
                else -> -1
            }

            if (selectedOptionIndex != -1) {
                checkAnswer(selectedOptionIndex)
            } else {
                Toast.makeText(this, "Por favor, selecione uma opção", Toast.LENGTH_SHORT).show()
            }
        }

        showFlag()
    }

    private fun btnCorrect(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> rdOption1.backgroundTintList = getColorStateList(android.R.color.holo_green_light)
            1 -> rdOption2.backgroundTintList = getColorStateList(android.R.color.holo_green_light)
            2 -> rdOption3.backgroundTintList = getColorStateList(android.R.color.holo_green_light)
            3 -> rdOption4.backgroundTintList = getColorStateList(android.R.color.holo_green_light)
        }
    }

    private fun btnWrong(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> rdOption1.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
            1 -> rdOption2.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
            2 -> rdOption3.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
            3 -> rdOption4.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
        }
    }

    private fun btnResetColor() {
        rdOption1.backgroundTintList = getColorStateList(R.color.pale_dogwood)
        rdOption2.backgroundTintList = getColorStateList(R.color.pale_dogwood)
        rdOption3.backgroundTintList = getColorStateList(R.color.pale_dogwood)
        rdOption4.backgroundTintList = getColorStateList(R.color.pale_dogwood)
    }

    private fun showResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("Pontuação", score)
        intent.putExtra("Total_Perguntas", imagesFlags.size)
        intent.putExtra("Player Name", playerName)
        startActivity(intent)
        finish()
    }


    private fun showFlag() {
        val questionImage = findViewById<ImageView>(R.id.question_image)
        questionImage.setImageResource(imagesFlags[currentQuestionIndex])

        val btnOption1 = findViewById<RadioButton>(R.id.radioButton1)
        val btnOption2 = findViewById<RadioButton>(R.id.radioButton2)
        val btnOption3 = findViewById<RadioButton>(R.id.radioButton3)
        val btnOption4 = findViewById<RadioButton>(R.id.radioButton4)

        btnOption1.text = options[currentQuestionIndex][0]
        btnOption2.text = options[currentQuestionIndex][1]
        btnOption3.text = options[currentQuestionIndex][2]
        btnOption4.text = options[currentQuestionIndex][3]

        btnResetColor()
        findViewById<RadioGroup>(R.id.radioGroup).clearCheck()
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            btnCorrect(selectedAnswerIndex)
        } else {
            btnWrong(selectedAnswerIndex)
            btnCorrect(correctAnswerIndex)
        }

        if (currentQuestionIndex < imagesFlags.size - 1) {
            currentQuestionIndex++
            Handler().postDelayed({ showFlag() }, 1000)
        } else {
            showResult()
        }
    }
}