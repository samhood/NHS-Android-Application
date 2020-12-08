package com.example.firebasedemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class QuestionnaireFinishedActivity extends AppCompatActivity {

    private Button logout;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_finished);

        Intent i = getIntent();

        final String questions = i.getStringExtra("total");
        final String correct = i.getStringExtra("correct");
        final String wrong = i.getStringExtra("incorrect");
        logout = findViewById(R.id.questionnaireFinishedLogoutBtn);

        play();

        Button btn = (Button)findViewById(R.id.AdminBtn);

        // Upon clicking the "results" button this method will again send the "correct" and "wrong" integer values.
        // This time to the results page to be displayed, the results page is then started.
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(QuestionnaireFinishedActivity.this, ResultActivity.class);
                i.putExtra("total",String.valueOf(questions));
                i.putExtra("correct", String.valueOf(correct));
                i.putExtra("incorrect", String.valueOf(wrong));
                startActivity(i);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(QuestionnaireFinishedActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuestionnaireFinishedActivity.this, LaunchActivity.class));
            }
        });

    }

    // this method plays the cheering mp3 audio.
    public void play(){
        if (player == null){
            player = MediaPlayer.create(this,R.raw.cheering);
        }
        player.start();
    }
}