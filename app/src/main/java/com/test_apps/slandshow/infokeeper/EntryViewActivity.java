package com.test_apps.slandshow.infokeeper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class EntryViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private SimpleCursorAdapter adapter;
    private DataBaseHandler dataBaseHandler;
    private Button btnDeletaAllEntries, btnAdd;
    private ArrayList<UserEntry> arrayList;
    public static final String TAG = "ID_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entryview);

        listView = (ListView) findViewById(R.id.entryListView);
        dataBaseHandler = new DataBaseHandler(this);
      //  btnDeletaAllEntries = (Button) findViewById(R.id.btnDeleteAllEntry);
       // btnAdd = (Button) findViewById(R.id.btnAddNewEntrInDB);
        //btnDeletaAllEntries.setOnClickListener(this);
       // btnAdd.setOnClickListener(this);
        init();

        // Update onClick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update array with entries
                updateEntryArray();

                Intent intent = new Intent(getApplicationContext(), EntryDestroyer.class);
                intent.putExtra(TAG + "MAIL", arrayList.get(position).getMail()); // Put mail
                intent.putExtra(TAG + "PASS", arrayList.get(position).getPass()); // Put password
                intent.putExtra(TAG + "SITE", arrayList.get(position).getSite()); // Put site name
                intent.putExtra(TAG + "ID", position + ""); // Put id
                arrayList.get(position).setId(position); // Костыль, да

                startActivity(intent);
            }
        });

        // Add another entry in DB
      /*  btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EntryManager.class);
                startActivity(intent);
            }
        });*/
    }

    private void updateEntryArray() {
        SQLiteDatabase db = dataBaseHandler.getReadableDatabase();
        Cursor cursor = dataBaseHandler.getReadableDatabase().query(
                DataBaseHandler.TABLE_NAME, new String[]{DataBaseHandler.KEY_ID,
                        DataBaseHandler.TABLE_MAIL,
                        DataBaseHandler.TABLE_PASS,
                        DataBaseHandler.TABLE_SITE_NAME},
                null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String mail = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHandler.TABLE_MAIL));
                String pass = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHandler.TABLE_PASS));
                String siteName = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHandler.TABLE_SITE_NAME));
                arrayList.add(new UserEntry(mail, pass, siteName));
            } while (cursor.moveToNext());
        }
    }

    // Init list view and adapter
    private void init() {
        try {
            Cursor cursor = dataBaseHandler.getAllProducts();

            arrayList = new ArrayList<>();

            if (cursor == null) {
                Toast.makeText(getApplicationContext(),
                        "Problem with cursor init", Toast.LENGTH_SHORT).show();
                return;
            } else if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(),
                        "No entries in database", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] collumns = new String[]{
                    DataBaseHandler.TABLE_MAIL,
                    DataBaseHandler.TABLE_PASS,
                    DataBaseHandler.TABLE_SITE_NAME
            };

            int[] boundsTo = new int[]{
                    R.id.entrDestrMail,
                    R.id.entrDestrPass,
                    R.id.entrSite
            };

            adapter = new SimpleCursorAdapter(this,
                    R.layout.entrylist_item,
                    cursor,
                    collumns,
                    boundsTo
            );
            listView.setAdapter(adapter);
        } catch (Exception exe) {
            Toast.makeText(getApplicationContext(), "Something gone wrong...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        // Delete all entries
        Cursor cursor = dataBaseHandler.getReadableDatabase().query(
                DataBaseHandler.TABLE_NAME, new String[]{DataBaseHandler.KEY_ID,
                        DataBaseHandler.TABLE_MAIL,
                        DataBaseHandler.TABLE_PASS,
                        DataBaseHandler.TABLE_SITE_NAME},
                null, null, null, null, null
        );
        // Если фальшифка езжи
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "There are already no entries in data base", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            dataBaseHandler.deleteAllEntries();
            adapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), "All data successful deleted", Toast.LENGTH_SHORT).show();
            // Go to main activity
            Intent i = new Intent(EntryViewActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Something gone wrong...", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearDatabase() {
        // Delete all entries
        Cursor cursor = dataBaseHandler.getReadableDatabase().query(
                DataBaseHandler.TABLE_NAME, new String[]{DataBaseHandler.KEY_ID,
                        DataBaseHandler.TABLE_MAIL,
                        DataBaseHandler.TABLE_PASS,
                        DataBaseHandler.TABLE_SITE_NAME},
                null, null, null, null, null
        );
        // Если фальшифка езжи
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "There are already no entries in data base", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            dataBaseHandler.deleteAllEntries();
            adapter.notifyDataSetChanged();

            Toast.makeText(getApplicationContext(), "All data successful deleted", Toast.LENGTH_SHORT).show();
            // Go to main activity
            Intent i = new Intent(EntryViewActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Something gone wrong...", Toast.LENGTH_SHORT).show();
        }
    }

    private void setHandler(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
    }

    // View ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewer, menu);
        return true;
    }

    // Add ActionBar Handler
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        // Add new Entry in DB
        if (id == R.id.id_profile_add) {
            Intent intent = new Intent(getApplicationContext(), EntryManager.class);
            startActivity(intent);
            return true;
        }
        // Clear DB
        if (id == R.id.id_delete_all) {
            clearDatabase();
            return true;
        }
        return true;
    }
}
