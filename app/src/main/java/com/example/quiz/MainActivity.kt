package com.example.quiz

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var btnQuit: Button
    private lateinit var playerName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btn_start)
        btnQuit = findViewById(R.id.btn_quit)
        playerName = findViewById(R.id.edt_player_name)

        playerName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btnActivated()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnStart.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("Player Name", playerName.text.toString())
            startActivity(intent)
        }

        btnQuit.setOnClickListener {
            finish()
            System.out.close()
        }
    }

    private fun btnActivated() {
        btnStart.isEnabled = playerName.text.toString().isNotBlank()
    }
}