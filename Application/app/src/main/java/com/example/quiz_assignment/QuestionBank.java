package com.example.quiz_assignment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionBank implements Parcelable {
    private ArrayList<Question> listOfQuestions = new ArrayList<>();

    public QuestionBank() {
        listOfQuestions.add(new Question(R.string.question1, false));
        listOfQuestions.add(new Question(R.string.question2, true));
        listOfQuestions.add(new Question(R.string.question3, true));
        listOfQuestions.add(new Question(R.string.question4, false));
        listOfQuestions.add(new Question(R.string.question5, false));

        shuffleQuestionBank();
    }

    protected QuestionBank(Parcel in) {
        listOfQuestions = in.createTypedArrayList(Question.CREATOR);
    }

    public static final Creator<QuestionBank> CREATOR = new Creator<QuestionBank>() {
        @Override
        public QuestionBank createFromParcel(Parcel in) {
            return new QuestionBank(in);
        }

        @Override
        public QuestionBank[] newArray(int size) {
            return new QuestionBank[size];
        }
    };

    // shuffle the questions in the question bank
    public void shuffleQuestionBank() {
        Collections.shuffle(listOfQuestions);
    }

    //returns the Question at 'index' from the Question Bank
    public Question getQuestionAtIndex(int index) {
        return listOfQuestions.get(index);
    }

    public int getNumberOfQuestions(){
        return listOfQuestions.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(listOfQuestions);
    }
}