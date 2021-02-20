package fr.titouan.jeuxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class gameOverActivity extends AppCompatActivity {
    TextView textScore;
    Button restartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        Intent intent = getIntent();
        int score = intent.getIntExtra("score",0);
        textScore = findViewById(R.id.score);
        String AfficheScore= "Votre score est de : "+score;
        textScore.setText(AfficheScore);
        restartButton  = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentrestart = new Intent(getApplicationContext(),jeuxActivity.class);
                startActivity(intentrestart);
            }
        });

    }
}
