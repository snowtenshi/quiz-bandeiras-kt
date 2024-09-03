package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class ResultActivity : AppCompatActivity() {

    private lateinit var btnPlayAgain: Button
    private lateinit var btnHome: Button
    private lateinit var tvPlayerName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        btnPlayAgain = findViewById(R.id.btn_play_again)
        btnHome = findViewById(R.id.btn_main_screen)
        tvPlayerName = findViewById(R.id.tv_player_name)

        btnPlayAgain.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val score = intent.getIntExtra("Pontuação", 0)
        val totalQuestions = intent.getIntExtra("Total_Perguntas", 0)
        val playerName = intent.getStringExtra("Player Name").orEmpty()

        val tvResult = findViewById<TextView>(R.id.tv_score)
        tvResult.text = "Sua pontuação: $score de $totalQuestions"

        tvPlayerName.text = "Jogador: $playerName"

    }
}