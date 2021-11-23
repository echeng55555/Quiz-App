package com.example.quiz_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FragmentManager fm;
    Button selectedAnswer;
    int noAttempts = 0; //represents # quiz attempts
    int noCorrect = 0; //represents # questions answered correctly
    int noQuestions = 0; // represents # questions asked
    int total = 0; // represent total # questions in quiz
    AlertDialog.Builder builder;
    QuestionBank newQuestionBank;
    StorageManager storageManager;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            newQuestionBank = savedInstanceState.getParcelable("currentQuestionBank");
            noQuestions = savedInstanceState.getInt("currentQuestionCount");
            noCorrect = savedInstanceState.getInt("currentQuestionsCorrect");
            noAttempts = savedInstanceState.getInt("currentAttempts");
        } else {
            //GOAL: set initial fragment question
            //generate new question bank
            newQuestionBank = new QuestionBank();
        }

        builder = new AlertDialog.Builder(this);
        fm = getSupportFragmentManager();
        storageManager = ((myApp) getApplication()).getStorageManager();
        bar = findViewById(R.id.progressBar);

        //set total to total number of questions in QuestionBank
        total = newQuestionBank.getNumberOfQuestions();

        //pass a Question to fragment to set the initial TextView
        Bundle bundle = new Bundle();
        bundle.putParcelable("question", newQuestionBank.getQuestionAtIndex(noQuestions));
        fm.beginTransaction().replace(R.id.questionFrameLayout, QuestionFragment.class, bundle).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.getTheAverage: {
                int totalCorrect = 0, totalAttempts = 0;
                ArrayList<Integer> scores = storageManager.getAverage(MainActivity.this);
                for (int score : scores) {
                    totalCorrect += score;
                    ++totalAttempts;
                }

                //create dialog
                builder.create();
                builder.setTitle("");
                builder.setMessage(getString(R.string.getTheAverage1) + " " + totalCorrect + " " + getString(R.string.getTheAverage2) + " " + totalAttempts + " " + getString(R.string.getTheAverage3));
                builder.setNegativeButton("OK", null);
                builder.setPositiveButton("", null);
                builder.setCancelable(false);
                builder.show();

                break;
            }
            case R.id.selectTheNumberOfQuestion: {
                break;
            }
            case R.id.resetTheSavedResult: {
                storageManager.resetScoresInFile(MainActivity.this);
                break;
            }
        }

        return true;
    }

    public void resetQuiz() {
        //reset for new attempt
        //shuffle questions and change colours
        noCorrect = 0;
        noQuestions = 0;
        newQuestionBank = new QuestionBank();

        //reset progress bar
        if (noQuestions == 0) {
            bar.incrementProgressBy(-100);
        }

        //generate new question
        //pass next question in questionBank to fragment to change the question
        Bundle bundle = new Bundle();
        bundle.putParcelable("question", newQuestionBank.getQuestionAtIndex(noQuestions));
        fm.beginTransaction().replace(R.id.questionFrameLayout, QuestionFragment.class, bundle).commit();
    }

    public void showResult() {
        builder.create();
        builder.setTitle(R.string.resultTitle);
        builder.setMessage(getString(R.string.resultDialog1) + " " + noCorrect + " " + getString(R.string.resultDialog2) + " " + total);
        builder.setNegativeButton(R.string.ignore, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                resetQuiz(); //reset for next attempt
            }
        });

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                storageManager.saveScoreToFile(MainActivity.this, noCorrect);
                resetQuiz(); //reset for next attempt
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    public void buttonClicked(View view) {
        int id = view.getId();
        selectedAnswer = findViewById(id);

        //check if answer is correct
        //display appropriate Toast message
        boolean select = Boolean.parseBoolean(selectedAnswer.getText().toString());
        if (newQuestionBank.getQuestionAtIndex(noQuestions++).getAnswer() == select) {
            Toast.makeText(getApplicationContext(), R.string.correct, Toast.LENGTH_SHORT).show();
            noCorrect++;

        } else {
            Toast.makeText(getApplicationContext(), R.string.incorrect, Toast.LENGTH_SHORT).show();
        }

        bar.incrementProgressBy((int) (((double) 1 / (double) total) * 100));

        if (noQuestions == total) {
            //increment number of attempts now b/c quiz has been completed
            ++noAttempts;

            //due to decimal difference, last question should have progress bar full
            bar.incrementProgressBy(100);

            //display result
            showResult();
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable("question", newQuestionBank.getQuestionAtIndex(noQuestions));
            fm.beginTransaction().replace(R.id.questionFrameLayout, QuestionFragment.class, bundle).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("currentQuestionBank", newQuestionBank);
        outState.putInt("currentQuestionCount", noQuestions);
        outState.putInt("currentQuestionsCorrect", noCorrect);
        outState.putInt("currentAttempts", noAttempts);
    }
}