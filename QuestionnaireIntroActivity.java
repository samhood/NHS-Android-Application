package com.example.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class QuestionnaireIntroActivity extends AppCompatActivity {

    private EditText edit;
    private ListView listView;
    private Button logout;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_intro);

        logout = findViewById(R.id.questionnaireIntroLogoutBtn);
        Button btn = (Button)findViewById(R.id.startBtn);

        // handles the sign out request and takes the user back to the first page
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(QuestionnaireIntroActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuestionnaireIntroActivity.this, LaunchActivity.class));
            }
        });

        // with this on click listener the questionnaire Activity will be started if the user clicks the start button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionnaireIntroActivity.this, QuestionnaireActivity.class));
            }
        });
    }
}