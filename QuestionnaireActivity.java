package com.example.firebasedemo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebasedemo.Model.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuestionnaireActivity extends AppCompatActivity {

    MediaPlayer player;
    public CountDownTimer CountDownTimer;
    private Button logout2;
    Button b1,b2,b3,b4;
    TextView t1_question,timerTxt;

    int total = 0;
    int correct = 0;
    int wrong = 0;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaire_activity);
        b1 = (Button) findViewById(R.id.topLeftButton);
        b2 = (Button)findViewById(R.id.topRightButton);
        b3 = (Button) findViewById(R.id.bottomLeftButton);
        b4 = (Button) findViewById(R.id.bottomRightButton);

        t1_question = (TextView) findViewById(R.id.questionsTxt);
        timerTxt = (TextView) findViewById(R.id.timerTxt);

        logout2 = findViewById(R.id.questionnaireLogoutBtn);

        play();


        updateQuestion();
        reverseTimer(120,timerTxt);

        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountDownTimer.cancel();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(QuestionnaireActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuestionnaireActivity.this, LaunchActivity.class));
                stopPlayer();;
            }
        });

    }

// this method plays the pirate themed audio mp3 file
    public void play(){
        if (player == null){
            player = MediaPlayer.create(this,R.raw.piratemusic);
        }
        player.start();
    }

    // this method stops the pirate themed audio mp3 file
    private void stopPlayer(){
        if (player != null){
            player.release();
            player = null;
        }
    }
// this method updates the question, and question options on the screen. It will interact with the
// database to check if the user has clicked the correct answer. Once the user has answered all 4 questions
// the test will be finished and the next activity will load. the integer value of correct and
// wrong answer variables will be passed to the next activity
    private void updateQuestion() {
        total++;
        if (total> 4){

            stopPlayer();
            Intent i = new Intent(QuestionnaireActivity.this, QuestionnaireFinishedActivity.class);
            i.putExtra("total",String.valueOf(total));
            i.putExtra("correct", String.valueOf(correct));
            i.putExtra("incorrect", String.valueOf(wrong));
            startActivity(i);

        } else {
            reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final Question question = snapshot.getValue(Question.class);

                    t1_question.setText(question.getQuestion());
                    b1.setText(question.getOption1());
                    b2.setText(question.getOption2());
                    b3.setText(question.getOption3());
                    b4.setText(question.getOption4());

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (b1.getText().toString().equals(question.getAnswer())) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;
                                        updateQuestion();

                                    };
                                }, 1500);
                            } else {

                                wrong = wrong+1;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestion();
                                    }
                                }, 1500);

                            }
                        }
                    });


                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (b2.getText().toString().equals(question.getAnswer())) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        correct++;
                                        updateQuestion();

                                    };
                                }, 1500);
                            } else {

                                wrong = wrong+1;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestion();
                                    }
                                }, 1500);

                            }
                        }
                    });


                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (b3.getText().toString().equals(question.getAnswer())) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;

                                        updateQuestion();
                                    };
                                }, 1500);
                            } else {

                                wrong = wrong+1;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestion();
                                    }
                                }, 1500);

                            }
                        }

                    });



                    b4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (b4.getText().toString().equals(question.getAnswer())) {

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        correct++;

                                        updateQuestion();
                                    };
                                }, 1500);
                            } else {

                                wrong = wrong+1;
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        updateQuestion();
                                    }
                                }, 1500);

                            }
                        }

                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

// this method is for the countdown timer on the test. once the timer is at 0 the message
// "completed will appear on the screen". Then the next activity will load
    public void reverseTimer(int seconds, final TextView tv){

     CountDownTimer =  new CountDownTimer(seconds* 1000+1000, 1000){

            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished / 1000;
                int minutes = seconds / 60;
                seconds = seconds % 60;
                tv.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
               total = getTotal();

                if (total < 4) {

                stopPlayer();
                tv.setText("Completed");
                Intent myIntent = new Intent(QuestionnaireActivity.this, QuestionnaireFinishedActivity.class);
                myIntent.putExtra("total", String.valueOf(total));
                myIntent.putExtra("correct", String.valueOf(correct));
                myIntent.putExtra("incorrect", String.valueOf(wrong));
                startActivity(myIntent);
            }
            }
        }.start();

    }

    public int getTotal() {
        return total;
    }
    public int getCorrect() {
        return correct;
    }
    public int getWrong() {
        return wrong;
    }

}