package com.test_apps.slandshow.infokeeper;

import android.content.Intent;
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

    private Button btnPassGenerator, btnAddPass;
    private TextView txtPassGenerator;
    private SeekBar passSeekBar;
    private EditText password;
    private ArrayList<String> chars;
    private int passLength;
    private int capitalLetterLvlStart, capitalLetterLvlEnd;
    private int cursiveLetterLvlStart, cursiveLetterLvlEnd;
    private int numbersLvlStart, numbersLvlEnd;
    private CheckBox capital, cursive, number;
    private boolean b1, b2, b3, generate = true;
    public static final String TAG = "PASS_GENERATED";
    public static String pass;
    public static boolean IS_LOADED = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        passLength = 0;
        chars = new ArrayList<>();
        btnPassGenerator = (Button) findViewById(R.id.btnpassg);
        passSeekBar = (SeekBar) findViewById(R.id.seekBarPass);
        password = (EditText) findViewById(R.id.editTextPass);
        txtPassGenerator = (TextView) findViewById(R.id.textViewPasswordGenerator);
        capital = (CheckBox) findViewById(R.id.checkBox1);
        cursive = (CheckBox) findViewById(R.id.checkBox2);
        number = (CheckBox) findViewById(R.id.checkBox3);
        btnAddPass = (Button) findViewById(R.id.buttonAddp);

        passSeekBar.setProgress(0);

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

        capital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1 = true;
                if (b1 && b2 && b3) {
                    Toast.makeText(getApplicationContext(), "You can't generate password!", Toast.LENGTH_SHORT).show();
                    generate = false;
                    b2 = false;
                    b3 = false;
                }
            }
        });

        cursive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b2 = true;
                if (b1 && b2 && b3) {
                    Toast.makeText(getApplicationContext(), "You can't generate password!", Toast.LENGTH_SHORT).show();
                    generate = false;
                    b1 = false;
                    b3 = false;
                }
            }
        });

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b3 = true;
                if (b1 && b2 && b3) {
                    Toast.makeText(getApplicationContext(), "You can't generate password!", Toast.LENGTH_SHORT).show();
                    generate = false;
                    b1 = false;
                    b2 = false;
                }

            }
        });

        btnAddPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntryManager.flag = true;
                pass = String.valueOf(password.getText());
                Intent intent = new Intent(PasswordGenerator.this, EntryManager.class);
                intent.putExtra(TAG, password.getText());
                startActivity(intent);
            }
        });

        btnPassGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (passLength == 0) {
                        passLength = passSeekBar.getProgress();
                        if (passLength == 0) {
                            Toast.makeText(getApplicationContext(), "Please, set password length", Toast.LENGTH_SHORT).show();
                        }
                    } else if (passLength == 1) {
                        Toast.makeText(getApplicationContext(), "Re-set password length", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (generate)
                        password.setText(choseRandom(passLength, 0, chars.size()));
                    else if (!generate)
                        Toast.makeText(getApplicationContext(), "Change check box values!", Toast.LENGTH_SHORT).show();
                } catch (Exception exe) {
                    Toast.makeText(getApplicationContext(), "Trying to calculate...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void onGeneratePass(View view) {
        try {
            if (passLength == 0) {
                passLength = passSeekBar.getProgress();
                if (passLength == 0) {
                    Toast.makeText(getApplicationContext(), "Please, set password length", Toast.LENGTH_SHORT).show();
                }
            } else if (passLength == 1) {
                Toast.makeText(getApplicationContext(), "Re-set password length", Toast.LENGTH_SHORT).show();
                return;
            } else if (generate)
                password.setText(choseRandom(passLength, 0, chars.size()));
            else if (!generate)
                Toast.makeText(getApplicationContext(), "Change check box values!", Toast.LENGTH_SHORT).show();
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
            result += String.valueOf(chars.get(random.nextInt(end - start))); // Generate random number from 'start' to 'end'
        return result;
    }
}
