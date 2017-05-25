package com.example.nickng.taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    Button btnAdd, btnCancel;
    EditText etName, etDesc, etRemind;
    DBHelper db;
    Intent i;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        etName = (EditText)findViewById(R.id.etName);
        etDesc = (EditText)findViewById(R.id.etDesc);
        etRemind = (EditText)findViewById(R.id.etRemind);
        db = new DBHelper(AddActivity.this);

        i = getIntent();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                int sec = Integer.parseInt(etRemind.getText().toString());

                db.insertTask(name, desc);
                Toast.makeText(AddActivity.this, "Added Task", Toast.LENGTH_LONG);
                db.close();
                setResult(RESULT_OK, i);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, sec);

                Intent intent = new Intent(AddActivity.this, TaskBroadcastReceiver.class);
                intent.putExtra("name", name);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                finish();
            }
        });
    }
}
