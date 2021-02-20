package fr.titouan.jeuxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static java.security.AccessController.getContext;

public class jeuxActivity extends AppCompatActivity {
    RelativeLayout ecran;
    LinearLayout perso;
    Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game= new Game(this);
        setContentView(game);
        if(game.gameOver == true){
            Intent intent = new Intent(getApplicationContext(),gameOverActivity.class);
            intent.getIntExtra("score",game.score);
            startActivity(intent);
        }

        /*ecran = findViewById(R.id.ecran);
        ecran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perso = findViewById(R.id.perso);

            }
        });*/

    }
}
