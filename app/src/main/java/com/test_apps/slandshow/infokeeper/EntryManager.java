package com.test_apps.slandshow.infokeeper;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class EntryManager extends AppCompatActivity {

    private Button btnAdd;
    private DataBaseHandler dataBaseHandler;
    private EditText mail, pass, site;
    public static String m, p, s;
    private UserEntry userEntry;
    public static boolean flag = false;
    public static boolean LOAD = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_manager);

        // Change app orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAdd = (Button) findViewById(R.id.btnAddEntr);
        mail = (EditText) findViewById(R.id.userMailInp);
        pass = (EditText) findViewById(R.id.userPassInp);
        site = (EditText) findViewById(R.id.userSiteInp);

        dataBaseHandler = new DataBaseHandler(this); // Arguments: this, null, null, 1

      /*  final Intent intent = getIntent();
        String str = intent.getStringExtra(PasswordGenerator.TAG);
        if (str != null || str != "") {
            pass.setText(PasswordGenerator.pass);
            mail.setText("");
            site.setText("");
            Toast.makeText(getApplicationContext(), PasswordGenerator.pass, Toast.LENGTH_SHORT).show();
        }*/

        if (flag) {
            flag = false;
            pass.setText(PasswordGenerator.pass);
            mail.setText("");
            site.setText("");
            Toast.makeText(getApplicationContext(), PasswordGenerator.pass, Toast.LENGTH_SHORT).show();
        }

        if (LOAD) {
            LOAD = false;
            pass.setText(p);
            site.setText(s);
            mail.setText(m);
        }

        // Listeners
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = EntryManager.this.mail.getText().toString();
                String pass = EntryManager.this.pass.getText().toString();
                String site = EntryManager.this.site.getText().toString();

                if (mail.equals("") || pass.equals("") || site.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please, add entries", Toast.LENGTH_SHORT).show();
                    return;
                }

                userEntry = new UserEntry(mail, pass, site); // This object with entries will be used in Intent extra data method
                userEntry.setDataBaseHandler(dataBaseHandler);
                // Clear input fields
                EntryManager.this.mail.setText("");
                EntryManager.this.pass.setText("");
                EntryManager.this.site.setText("");

                // Add data in SQLite
                try {
                    dataBaseHandler.addData(mail, pass, site);
                    Toast.makeText(getApplicationContext(), "All data successful added " + ("\ud83d\ude01"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Problem with adding data in database!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Add back button on ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    protected void onAddEntry(View view) {
        String mail = this.mail.getText().toString();
        String pass = this.pass.getText().toString();
        String site = this.site.getText().toString();

        userEntry = new UserEntry(mail, pass, site); // This object with entries will be used in Intent extra data method
        userEntry.setDataBaseHandler(dataBaseHandler);
        // Clear input fields
        this.mail.setText("");
        this.pass.setText("");
        this.site.setText("");

        // Add data in SQLite
        try {
            dataBaseHandler.addData(mail, pass, site);
            Toast.makeText(getApplicationContext(), "All data successful added " + ("\ud83d\ude01"), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Problem with adding data in database!", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onViewEntry(View view) {
        // If we load EntryViewActivity by this button
        // We must use Serializable object before sending to another activity!
        // Создаём экземпляр класса Intent, который в Android реализует переход от одной
        // Активности к другой. Так же передаём ей данные (строки) через `putExtra`
        Intent intent = new Intent(getApplicationContext(), EntryViewActivity.class);
        intent.putExtra("TAG", "Test String");
        // Переходим из текущей активности в активность `EntryViewActivity`
        startActivity(intent);
    }


    // View ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewer_manager, menu);
        return true;
    }

    // Add ActionBar Handler
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.id_add_to_db) {
            String mail = EntryManager.this.mail.getText().toString();
            String pass = EntryManager.this.pass.getText().toString();
            String site = EntryManager.this.site.getText().toString();

            if (mail.equals("") || pass.equals("") || site.equals("")) {
                Toast.makeText(getApplicationContext(), "Please, add entries", Toast.LENGTH_SHORT).show();
                return true;
            }

            userEntry = new UserEntry(mail, pass, site); // This object with entries will be used in Intent extra data method
            userEntry.setDataBaseHandler(dataBaseHandler);
            // Clear input fields
            EntryManager.this.mail.setText("");
            EntryManager.this.pass.setText("");
            EntryManager.this.site.setText("");

            // Add data in SQLite
            try {
                dataBaseHandler.addData(mail, pass, site);
                Toast.makeText(getApplicationContext(), "All data successful added " + ("\ud83d\ude01"), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Problem with adding data in database!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.id_clear_all) {
            // Clear input fields
            EntryManager.this.mail.setText("");
            EntryManager.this.pass.setText("");
            EntryManager.this.site.setText("");
        } else if (id == R.id.id_view_all) {
            // If we load EntryViewActivity by this button
            // We must use Serializable object before sending to another activity!
            // Создаём экземпляр класса Intent, который в Android реализует переход от одной
            // Активности к другой. Так же передаём ей данные (строки) через `putExtra`
            Intent intent = new Intent(getApplicationContext(), EntryViewActivity.class);
            intent.putExtra("TAG", "Test String");
            // Переходим из текущей активности в активность `EntryViewActivity`
            startActivity(intent);
        } else {
            // Back to previous Activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
}
