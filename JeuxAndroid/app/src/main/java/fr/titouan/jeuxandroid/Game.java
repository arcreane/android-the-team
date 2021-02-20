package fr.titouan.jeuxandroid;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Game extends View {

    Handler handler;
    Runnable runnable;
    private int delay=30;
    Bitmap background;
    Bitmap topTube,bottomTube;
    Display display;
    Point point;
    int screenWidth,screenHeight;
    Rect rect;
    Bitmap bird,secondBird;
    double velocity = 0 ,gravity =4;
    int xBird,yBird;
    boolean gameStart=false;
    int tubeGap = 400;
    int minTubeOffSet,maxtubeOffSet,distanceBetweenTube;
    int numberOfTube=4;
    int []xTube = new int[numberOfTube];
    int []yTopTube = new int[numberOfTube];
    Random random;
    int tubeVelocity =10;
    int score=0;
    public boolean gameOver = false;
    Paint paint;
    public Game(Context context){

        super(context);
        handler = new Handler();
        runnable =new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;
        rect = new Rect(0,0,screenWidth,screenHeight);
        bird = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        secondBird =BitmapFactory.decodeResource(getResources(),R.drawable.bird3);
        topTube = BitmapFactory.decodeResource(getResources(),R.drawable.petittubetop);
        bottomTube = BitmapFactory.decodeResource(getResources(),R.drawable.petittube);
        xBird = screenWidth/2 - bird.getWidth();
        yBird = screenHeight/2 - bird.getHeight();
        distanceBetweenTube = screenWidth * 3/4;
        minTubeOffSet = tubeGap/2;
        maxtubeOffSet = screenHeight - minTubeOffSet - tubeGap;
        random = new Random();
        paint  = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        for (int i=0 ; i<numberOfTube;i++){
            xTube[i]= screenWidth + i *distanceBetweenTube;
            yTopTube[i] = minTubeOffSet+random.nextInt(maxtubeOffSet-minTubeOffSet+1);
        }

    }

    @Override
     protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawBitmap(background,null,rect,null);
        if(gameStart) {
            if((score%20 == 0)&&(tubeVelocity<17)){
                tubeVelocity+=0.5;
            }
            if (yBird < screenHeight + bird.getHeight() || velocity < 0) {
                velocity += gravity;
                yBird += velocity;
            }
            for(int i=0 ; i<numberOfTube;i++) {
                xTube[i]-=tubeVelocity;

                if(xTube[i]<-topTube.getWidth()){
                    xTube[i] += numberOfTube*distanceBetweenTube;
                    yTopTube[i] = minTubeOffSet+random.nextInt(maxtubeOffSet-minTubeOffSet+1);
                }
                if((yBird<yTopTube[i]-100)){
                    if((xBird>xTube[i])&&(xBird+100<xTube[i]+topTube.getWidth())){
                        setGameOver();

                    }
                    if ((xBird+bird.getWidth()-100>xTube[i])&&(xBird+bird.getWidth()<xTube[i]+topTube.getWidth())){

                        setGameOver();

                    }
                }
                if((yBird+bird.getHeight()-100>yTopTube[i]+tubeGap)){
                    if((xBird>xTube[i])&&(xBird+100<xTube[i]+bottomTube.getWidth())){
                        setGameOver();
                    }
                    if ((xBird+bird.getWidth()-100>xTube[i])&&(xBird+bird.getWidth()<xTube[i]+bottomTube.getWidth())){
                        setGameOver();

                    }
                }

                chechkScore(xTube[i]);
                canvas.drawBitmap(topTube, xTube[i], yTopTube[i] - topTube.getHeight(), null);
                canvas.drawBitmap(bottomTube, xTube[i], yTopTube[i] + tubeGap, null);
            }
        }
        paint.setColor(Color.BLACK);
        paint.setTextSize(200);
        canvas.drawText(String.valueOf(score), screenWidth/2, screenHeight/6, paint);
        canvas.drawBitmap(bird,xBird,yBird,null);
        handler.postDelayed(runnable,delay);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            if(yBird > 0 && !gameOver) {
                velocity = -40;
                gameStart=true;


            }
        }
        return true;
    }
    protected  void chechkScore(int xtube){
        if((xtube > tubeGap)&&(xtube<tubeGap+10)){
            score+=1;
        }
    }
    protected void setGameOver(){
        Intent intent = new Intent(getContext(),gameOverActivity.class);
        intent.putExtra("score",this.score);
        getContext().startActivity(intent);
        ((Activity)getContext()).finish();
        gameOver=true;
        gameStart = false;
    }
}
