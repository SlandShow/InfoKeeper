package com.test_apps.slandshow.infokeeper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EntryDestroyer extends AppCompatActivity {

    private String mail, pass, site;
    private TextView mailTxt, passTxt, siteTxt;
    private long id;
    private Button btnDeleteEntry;
    private DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_destroyer);

        final Intent intent = getIntent();
        mail = intent.getStringExtra(EntryViewActivity.TAG + "MAIL");
        pass = intent.getStringExtra(EntryViewActivity.TAG + "PASS");
        site = intent.getStringExtra(EntryViewActivity.TAG + "SITE");
        id = Long.parseLong(intent.getStringExtra(EntryViewActivity.TAG + "ID"));

        mailTxt = (TextView) findViewById(R.id.entrDestrMail);
        passTxt = (TextView) findViewById(R.id.entrDestrPass);
        siteTxt = (TextView) findViewById(R.id.entrDestrSite);

        mailTxt.setText(mail);
        passTxt.setText(pass);
        siteTxt.setText(site);

        btnDeleteEntry = (Button) findViewById(R.id.btnDeleteCurrentEntry);
        dataBaseHandler = new DataBaseHandler(this);

        Toast.makeText(this, "You can delete this entry (ID: " + id + ")", Toast.LENGTH_SHORT).show();

        btnDeleteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Clear entry from database
                    SQLiteDatabase db = dataBaseHandler.getWritableDatabase();
                    db.execSQL("DELETE FROM " + DataBaseHandler.TABLE_NAME + " WHERE " + DataBaseHandler.TABLE_PASS + " = " + "'" + pass + "'" + " AND "
                            + DataBaseHandler.TABLE_SITE_NAME + " = " + "'" + site + "'"
                            + " AND " + DataBaseHandler.TABLE_MAIL + " = " + "'" + mail + "'");

                    Toast.makeText(getApplicationContext(), "Current entry deleted from database!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EntryDestroyer.this, EntryViewActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                } catch (Exception exc) {
                    Toast.makeText(getApplicationContext(), "Something gone wrong...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Add back button on ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    // View ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewer_destroyer, menu);
        return true;
    }

    // Add ActionBar Handler
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        Intent intent = new Intent(EntryDestroyer.this, EntryViewActivity.class);
        startActivity(intent);
        return true;
    }
}
