package com.test_apps.slandshow.infokeeper;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EntryManager extends AppCompatActivity {

    private Button btnAdd;
    private Button btnView;
    private Button btnClear;
    private DataBaseHandler dataBaseHandler;
    private EditText mail, pass, site;
    private UserEntry userEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_manager);

        btnAdd = (Button) findViewById(R.id.btnAddEntr);
        btnView = (Button) findViewById(R.id.btnViewDB);
        btnClear = (Button) findViewById(R.id.btnClear);
        mail = (EditText) findViewById(R.id.userMailInp);
        pass = (EditText) findViewById(R.id.userPassInp);
        site = (EditText) findViewById(R.id.userSiteInp);

        dataBaseHandler = new DataBaseHandler(this); // Arguments: this, null, null, 1

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


    protected void onClearEntry(View view) {
        // Clear input fields
        this.mail.setText("");
        this.pass.setText("");
        this.site.setText("");
    }
}
