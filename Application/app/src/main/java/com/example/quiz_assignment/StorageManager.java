package com.example.quiz_assignment;

import android.app.Activity;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class StorageManager {
    String filename = "quiz.txt";

    public void saveScoreToFile(Activity activity, int score){
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_APPEND);
            //each score attempt is delimited by a comma
            fileOutputStream.write((score + ",").getBytes());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally{
            try{
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }

        }
    }

    public ArrayList<Integer> getAverage(Activity activity){
        FileInputStream fileInputStream = null;
        int read;
        ArrayList<Integer> scores = new ArrayList<>(1);

        try{
            fileInputStream = activity.openFileInput(filename);
            while((read = fileInputStream.read()) != -1){
                if (read != ','){
                    scores.add(Character.getNumericValue(read));
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return scores;
    }

    public void resetScoresInFile(Activity activity){
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally{
            try{
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
