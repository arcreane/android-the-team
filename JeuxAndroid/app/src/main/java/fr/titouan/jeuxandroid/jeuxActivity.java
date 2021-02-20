package fr.titouan.jeuxandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



public class jeuxActivity extends AppCompatActivity {
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

    }
}
