package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

private CardView cardView ;
private Button falseButton;
private Button trueButton,startButton;
private  SliderLayout sliderLayout;
private TextView questionTxtView,scoreTotal,wrongAnswers,questionNo;
private ImageView nextQuestion,previousQuestion,restartGame;
private int currentQuestionIndex=0;
private int score=0,wrong=0;
LinearLayout layout,layoutNext,layoutScore;
private QuestionBank[] qBanks= new QuestionBank[]//add question  from string resources
        {
                new QuestionBank(R.string.question_one, true),
                new QuestionBank(R.string.question_two, false),
                new QuestionBank(R.string.question_three, true),
                new QuestionBank(R.string.question_four, true),
                new QuestionBank(R.string.question_five, true),
                new QuestionBank(R.string.question_six, true),
                new QuestionBank(R.string.question_seven, false),
                new QuestionBank(R.string.question_eight, false),
                new QuestionBank(R.string.question_nine, true),
                new QuestionBank(R.string.question_ten, false),
                new QuestionBank(R.string.question_eleven,false),
                new QuestionBank(R.string.question_twelve,false),
                new QuestionBank(R.string.question_thirteen,false),
                new QuestionBank(R.string.question_fourteen,true),
                new QuestionBank(R.string.question_fifteen,false)
        };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        sliderLayout = findViewById(R.id.imageslider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(5);
        setSliderViews();
        layout= findViewById(R.id.truefalse);
        layoutNext= findViewById(R.id.next_previous);
        layoutScore=findViewById(R.id.layout_score);
        cardView= findViewById(R.id.cardView);
        falseButton= findViewById(R.id.false_button);
        trueButton= findViewById(R.id.true_button);
        questionTxtView= findViewById(R.id.answer_textView);
        startButton = findViewById(R.id.start_game);
        previousQuestion= findViewById(R.id.back);
        wrongAnswers= findViewById(R.id.wrong);
        scoreTotal= findViewById(R.id.score);

        restartGame= findViewById(R.id.restart);
        nextQuestion= findViewById(R.id.next);
        falseButton.setOnClickListener( this);
        trueButton.setOnClickListener( this);
        nextQuestion.setOnClickListener(this);
        previousQuestion.setOnClickListener(this);
        restartGame.setOnClickListener(this);
        startButton.setOnClickListener(this);

        questionTxtView.setText(qBanks[currentQuestionIndex].getAnswerResId());


       cardView.setVisibility(View.INVISIBLE);
       startButton.setVisibility(View.VISIBLE);
       layout.setVisibility(View.INVISIBLE);
       layoutNext.setVisibility(View.INVISIBLE);
       layoutScore.setVisibility(View.INVISIBLE);
       sliderLayout.setVisibility(View.INVISIBLE);

    }



    private void setSliderViews()
    {
        for (int i = 0; i <= 9; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.rob);
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.problem);
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.firebase);
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.stud);
                    break;
                case 4:
                    sliderView.setImageDrawable(R.drawable.javaaaa);
                    break;

                case 5:
                    sliderView.setImageDrawable(R.drawable.sdk1);
                    break;
                case 6:
                    sliderView.setImageDrawable(R.drawable.design);
                    break;
                case 7:
                    sliderView.setImageDrawable(R.drawable.code);
                    break;
                case 8:
                    sliderView.setImageDrawable(R.drawable.creative);
                    break;
                case 9:
                    sliderView.setImageDrawable(R.drawable.earth);
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.FIT_CENTER);

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onClick(View v)
    {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.click);
        switch (v.getId())
        {
            case R.id.false_button:

                checkAnswer(false);
                falseButton.setClickable(false);
                trueButton.setClickable(false);
                mp.start();
                break;
            case R.id.true_button:

                checkAnswer(true);
                trueButton.setClickable(false);
                falseButton.setClickable(false);
                mp.start();
                break;
            case R.id.next:
                currentQuestionIndex=(currentQuestionIndex+1)%qBanks.length;//safe to go
                mp.start();
                updateQuestion();
                trueButton.setClickable(true);
                falseButton.setClickable(true);
                break;
            case R.id.back:
                if(currentQuestionIndex>0)
                {
                    currentQuestionIndex = (currentQuestionIndex-1) % qBanks.length;//safe to go
                    mp.start();
                    updateQuestion();

                }
                break;
            case R.id.start_game:
                startButton.setVisibility(View.INVISIBLE);
                cardView.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
                layoutNext.setVisibility(View.VISIBLE);
                layoutScore.setVisibility(View.VISIBLE);
                sliderLayout.setVisibility(View.VISIBLE);
                mp.start();
                break;
            case R.id.restart:
                if(score==0&&wrong==0)
                {
                    Toast.makeText(this, "Firstly! Answer some questions", Toast.LENGTH_SHORT).show();
                    mp.start();
                }
                else
                {
                    mp.start();// start sound on click
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            MainActivity.this);
                    builder.setCancelable(false);
                    builder.setMessage("Are You Really Want To Restart?");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    score=0;
                                    wrong=0;
                                    currentQuestionIndex=0;
                                    questionTxtView.setText(qBanks[currentQuestionIndex].getAnswerResId());
                                    trueButton.setClickable(true);
                                    falseButton.setClickable(true);
                                    scoreTotal.setText("Score("+score+")");
                                    wrongAnswers.setText("Wrong("+wrong+")");
                                   // questionNo.setText("Q"+String.valueOf(currentQuestionIndex+1)+".");
                                    nextQuestion.setVisibility(View.VISIBLE);

                                }
                            });
                    builder.show();
                }

                    }
                }
//


private void updateQuestion()
{
    questionTxtView.setText(qBanks[currentQuestionIndex].getAnswerResId());
    questionTxtView.setTextSize(19);
  //  questionNo.setText("Q"+String.valueOf(currentQuestionIndex+1)+".");
    if(currentQuestionIndex+1==qBanks.length)
    {
        nextQuestion.setVisibility(View.INVISIBLE);
    }
    else {
        nextQuestion.setVisibility(View.VISIBLE);
    }
}
public void checkAnswer(boolean userAnswer)
{
    boolean answerIsTrue=qBanks[currentQuestionIndex].isAnswerTrue();
    int toastMessageId;
    if(userAnswer==answerIsTrue)
    {
        toastMessageId=R.string.correct_answer;
        score++;
        scoreTotal.setText("Score("+score+")");
        if(currentQuestionIndex+1==qBanks.length)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Your Score: "+score);
            builder.setMessage("Want to Play Again?");
            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which)
                        {
                                finish();
                        }
                    });
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog,
                                            int which)
                        {
                            score=0;
                            wrong=0;
                            currentQuestionIndex=0;
                            questionTxtView.setText(qBanks[currentQuestionIndex].getAnswerResId());
                            trueButton.setClickable(true);
                            falseButton.setClickable(true);
                            scoreTotal.setText("Score("+score+")");
                            wrongAnswers.setText("Wrong("+wrong+")");
                           // questionNo.setText("Q"+String.valueOf(currentQuestionIndex+1)+".");
                            nextQuestion.setVisibility(View.VISIBLE);

                        }
                    });
            builder.show();
        }

    }
    else
    {
        toastMessageId=R.string.wrong_answer;

        wrong++;
        scoreTotal.setText("Score("+score+")");
        wrongAnswers.setText("Wrong("+wrong+")");
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        assert vibe != null;
        vibe.vibrate(100);
        if(currentQuestionIndex+1==qBanks.length)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    MainActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Your Score: "+score);
            builder.setMessage("Want to Play Again?");
            builder.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which)
                        {
                            finish();
                        }
                    });
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog,
                                            int which)
                        {
                            score=0;
                            wrong=0;
                            currentQuestionIndex=0;
                            questionTxtView.setText(qBanks[currentQuestionIndex].getAnswerResId());
                            trueButton.setClickable(true);
                            falseButton.setClickable(true);
                            scoreTotal.setText("Score("+score+")");
                            wrongAnswers.setText("Wrong("+wrong+")");

                            nextQuestion.setVisibility(View.VISIBLE);

                        }
                    });
            builder.show();
        }

    }
//    Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT).show();
}
}
