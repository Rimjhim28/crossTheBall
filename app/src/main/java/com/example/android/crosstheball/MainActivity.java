package com.example.android.crosstheball;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnTrue,btnFalse,btnPause,btnResume;
    ImageView ball;
    Animation translateBall;
    int counter = 0,points = 0;
    TextView quesText;
    TextView txtPoints;
    TextView timer;
    CountDownTimer countDownTimer;
    long millisLeft,millisBeforPause = 40000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ball = (ImageView) findViewById(R.id.ball);
        quesText = (TextView) findViewById(R.id.question_text);
        translateBall = AnimationUtils.loadAnimation(this,R.anim.translate_ball);
        translateBall.setDuration(3000);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        btnFalse = (Button) findViewById(R.id.btnFalse);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnResume = (Button) findViewById(R.id.btnResume);
        txtPoints = (TextView) findViewById(R.id.points);
        quesText.setText(Utils.game[counter][0]);
         millisLeft = 40000;
        timer = (TextView) findViewById(R.id.timer);
        setUpCounter(findViewById(R.id.btnResume));

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                millisBeforPause = millisLeft;
                btnTrue.setEnabled(false);
                btnFalse.setEnabled(false);
                countDownTimer.cancel();
            }
        });
    }

    public void answerChosen(View view) {

        if (view == findViewById(R.id.btnTrue)) {
            if (Utils.game[counter][1] == "T")
                answerCorrect();
            else
                answerWrong();
        }
        else if (view == findViewById(R.id.btnFalse)) {
                if (Utils.game[counter][1] == "F")
                    answerCorrect();
                else
                    answerWrong();
        }
    }

    public void answerCorrect(){
        showBlock();
        points = points + 1;
        counter = counter + 1;
        txtPoints.setText(Integer.toString(points));
        nextQuestion();
    }

    public void answerWrong(){
        counter = counter + 1;
        nextQuestion();
    }

    public void nextQuestion(){
        if( points == 5){
            quesText.setText("YOU WON!!");
            ball.startAnimation(translateBall);
            btnFalse.setEnabled(false);
            btnTrue.setEnabled(false);
            btnPause.setEnabled(false);
            btnTrue.setEnabled(false);
            countDownTimer.cancel();
        }
        else if(counter == 8 && points != 5){
            quesText.setText("YOU LOST!!");
            btnFalse.setEnabled(false);
            btnTrue.setEnabled(false);
        }
        else{
            final AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
            final AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
            fadeOut.setFillAfter(true);
            fadeIn.setDuration(400);
            fadeOut.setDuration(400);
            fadeIn.setFillAfter(true);
            fadeIn.setStartOffset(200);
            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    quesText.setText(Utils.game[counter][0]);
                    quesText.startAnimation(fadeIn);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            quesText.startAnimation(fadeOut);
        }
    }
    public void showBlock(){
        final AlphaAnimation fadeIn = new AlphaAnimation(0.0f,1.0f);
        fadeIn.setFillAfter(true);
        fadeIn.setDuration(800);
        fadeIn.setStartOffset(200);
        (findViewById(Utils.brick[points])).setAlpha(1.0f);
        (findViewById(Utils.brick[points])).startAnimation(fadeIn);
    }
      public void setUpCounter(View view){
           btnTrue.setEnabled(true);
           btnFalse.setEnabled(true);
        countDownTimer = new CountDownTimer(millisBeforPause,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                long secLeft = millisLeft/1000;
                timer.setText(""+secLeft);
            }

            @Override
            public void onFinish() {
                btnTrue.setEnabled(false);
                btnFalse.setEnabled(false);
                timer.setText("TIME UP");
            }
        };
        countDownTimer.start();
    }
}
