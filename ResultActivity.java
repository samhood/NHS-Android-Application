package com.example.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ResultActivity extends AppCompatActivity {

    TextView totalQuestions, correctAnswers, incorrectAnswers;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        totalQuestions = (TextView) findViewById(R.id.totalQuestionsValue);
        correctAnswers = (TextView) findViewById(R.id.correctAnswerValue);
        incorrectAnswers = (TextView) findViewById(R.id.incorrectAnswerValue);
        logout = findViewById(R.id.resultsLogoutBtn);

        Intent i = getIntent();

        String questions = "4";
        String correct = i.getStringExtra("correct");
        String wrong = i.getStringExtra("incorrect");

// the lines of code below will display the number of correct and incorrect
        totalQuestions.setText(questions);
        correctAnswers.setText(correct);
        incorrectAnswers.setText(wrong);

        Button btn1 = (Button) findViewById(R.id.newTestBtn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultActivity.this, QuestionnaireIntroActivity.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ResultActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ResultActivity.this, LaunchActivity.class));
            }
        });

    }

}