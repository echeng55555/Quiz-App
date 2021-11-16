package com.example.quiz_assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {
    Question newQuestion;
    TextView question;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.question_fragment, container, false);

        //get the Question object passed from MainActivity
        //set the fragment TextView to the question
        //set the fragment background colour to the colour
        if (this.getArguments() != null) {
            newQuestion = (Question) getArguments().getParcelable("question");

            question = v.findViewById(R.id.questionText);
            question.setText(newQuestion.getText());
            question.setBackgroundColor(newQuestion.getColour());
        }
        return v;
    }
}
