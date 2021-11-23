package com.example.quiz_assignment;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class Question implements Parcelable {
    private final int text;
    private final boolean answer;
    private int colour;

    public Question(int p_question, boolean p_answer) {
        Random random = new Random();

        text = p_question;
        answer = p_answer;

        //generate random colour
        //don't want white - text will blend in with background
        do {
            colour = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        } while (colour == Color.rgb(255, 255, 255));
    }

    //returns the text (the question)
    public int getText() {
        return text;
    }

    //returns the answer (the answer to the question)
    public boolean getAnswer() {
        return answer;
    }

    //returns the colour (the colour for the question)
    public int getColour() {
        return colour;
    }

    protected Question(Parcel in) {
        text = in.readInt();
        answer = in.readByte() != 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(text);
        parcel.writeByte((byte) (answer ? 1 : 0));
    }
}