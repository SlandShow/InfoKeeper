package com.test_apps.slandshow.infokeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.balram.locker.utils.Locker;
import com.balram.locker.view.AppLocker;
import com.balram.locker.view.LockActivity;

import java.util.List;

public class MainActivity extends LockActivity {


    private Button btnAddEntry;
    private Button btnViewEntries;
    private Button btnGeneratePass;
    private Button btnChangePass, btnDisablePass;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddEntry = (Button) findViewById(R.id.btnAddEntry);
        btnViewEntries = (Button) findViewById(R.id.btnViewEntries);
        btnGeneratePass = (Button) findViewById(R.id.btnGeneratePass);
        btnChangePass = (Button) findViewById(R.id.btn_change_pass);
        btnDisablePass = (Button) findViewById(R.id.btn_off_pass);

        updateUI();
    }

    // Adding user entries
    protected void onAdd(View view) {
            Intent intent = new Intent(getApplicationContext(), EntryManager.class);
            startActivity(intent);
    }

    protected void onView(View view) {
        Intent intent = new Intent(getApplicationContext(), EntryViewActivity.class);
        startActivity(intent);
    }

    // Change password in app
    protected void onChangePass(View view) {
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra(Locker.TYPE, Locker.CHANGE_PASSWORD);
        intent.putExtra(Locker.MESSAGE, getString(R.string.enter_old_passcode));
        startActivityForResult(intent, Locker.CHANGE_PASSWORD);
    }

    // disable password in app
    protected void onDisablePass(View view) {
        int type = AppLocker.getInstance().getAppLock().isPasscodeSet() ? Locker.DISABLE_PASSLOCK : Locker.ENABLE_PASSLOCK;
        Intent intent = new Intent(this, LockActivity.class);
        intent.putExtra(Locker.TYPE, type);
        startActivityForResult(intent, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Locker.DISABLE_PASSLOCK:
                break;
            case Locker.ENABLE_PASSLOCK:
            case Locker.CHANGE_PASSWORD:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, getString(R.string.setup_passcode), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        updateUI();

    }

    private void updateUI() {
        if (AppLocker.getInstance().getAppLock().isPasscodeSet()) {
            btnDisablePass.setText(R.string.disable_passcode);
            btnChangePass.setEnabled(true);
        } else {
            btnDisablePass.setText(R.string.enable_passcode);
            btnChangePass.setEnabled(false);
        }
    }

}
