package com.example.android.crosstheball;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button btnTrue,btnFalse;
    ImageView ball;
    Animation translateBall;
    int counter = 0,points = 0;
    TextView quesText;

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
        quesText.setText(Utils.game[counter][0]);
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
        ((ImageView) findViewById(Utils.brick[points])).setVisibility(View.VISIBLE);
        points = points + 1;
        counter = counter + 1;
        nextQuestion();
    }

    public void answerWrong(){
        counter = counter + 1;
        nextQuestion();
    }

    public void nextQuestion(){
        if( points == 5){
            Toast.makeText(this,"You Won",Toast.LENGTH_SHORT).show();
            ball.startAnimation(translateBall);
        }
        else if(counter == 8 && points != 5){
            Toast.makeText(this,"You Lost",Toast.LENGTH_SHORT).show();
        }
        else{
            quesText.setText(Utils.game[counter][0]);
        }
    }
}
