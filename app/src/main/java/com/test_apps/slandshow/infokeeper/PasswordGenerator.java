package com.test_apps.slandshow.infokeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

// ...

public class PasswordGenerator extends AppCompatActivity {

    private Button btnPassGenerator;
    private TextView txtPassGenerator;
    private SeekBar passSeekBar;
    private EditText password;
    private ArrayList<String> chars;
    private int passLength;
    private int capitalLetterLvlStart, capitalLetterLvlEnd;
    private int cursiveLetterLvlStart, cursiveLetterLvlEnd;
    private int numbersLvlStart, numbersLvlEnd;
    private CheckBox capital, cursive, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        passLength = 0;
        chars = new ArrayList<>();
        btnPassGenerator = (Button) findViewById(R.id.btnGeneratePass);
        passSeekBar = (SeekBar) findViewById(R.id.seekBarPass);
        password = (EditText) findViewById(R.id.editTextPass);
        txtPassGenerator = (TextView) findViewById(R.id.textViewPasswordGenerator);

        // SeekBar listener
        passSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtPassGenerator.setText(seekBar.getProgress() + "");
                passLength = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() <= 1) {
                    Toast.makeText(getApplicationContext(), "Password can`t be so short. Please, re-set password length", Toast.LENGTH_SHORT).show();
                }
                txtPassGenerator.setText(seekBar.getProgress() + "");
                passLength = seekBar.getProgress();
                init();
            }
        });

    }

    protected void onGeneratePass(View view) {
        try {
            if (passLength == 0) {
                passLength = passSeekBar.getProgress();
                if (passLength == 0) {
                    Toast.makeText(getApplicationContext(), "Please, set password length", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else if (passLength == 1) {
                Toast.makeText(getApplicationContext(), "Re-set password length", Toast.LENGTH_SHORT).show();
                return;
            }

            password.setText(choseRandom(passLength, 0, chars.size()));
        } catch (Exception exe) {
            Toast.makeText(getApplicationContext(), "Trying to calculate...", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        if (chars.size() == 0) {
            // Заглавные
            for (char i = 'A'; i < 'Z'; i++)
                chars.add(String.valueOf(i));
            capitalLetterLvlStart = 0;
            capitalLetterLvlEnd = chars.size(); // 26
            // Прописные
            for (char i = 'a'; i < 'z'; i++)
                chars.add(String.valueOf(i));
            cursiveLetterLvlStart = chars.size() - capitalLetterLvlEnd; // 26
            cursiveLetterLvlEnd = chars.size();
            // Цифры
            for (int i = 0; i <= 9; i++)
                chars.add(String.valueOf(i));
            numbersLvlStart = chars.size() - cursiveLetterLvlEnd;
            numbersLvlEnd = chars.size();
        }
    }

    private String choseRandom(int size, int start, int end) {
        String result = "";
        Random random = new Random();
        for (int i = 0; i < size; i++)
            result += String.valueOf(chars.get(random.nextInt(end - start) + 1)); // Generate random number from 'start' to 'end'
        return result;
    }
}
