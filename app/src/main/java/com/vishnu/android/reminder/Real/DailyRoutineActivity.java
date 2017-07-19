package com.vishnu.android.reminder.Real;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vishnu.android.reminder.R;

import java.io.File;
import java.security.Permission;

public class DailyRoutineActivity extends AppCompatActivity {
    private ImageButton mImgBtnAddRoutine;
    private final int MY_REQUEST_READ_CONTACTS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_routine);
        mImgBtnAddRoutine       =   (ImageButton)   findViewById(R.id.img_button_add_routine);
        getPermissionExample();
        mImgBtnAddRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   =   new Intent(DailyRoutineActivity.this, NewDailyRoutine.class);
                startActivity(intent);
            }
        });
    }

    public void getPermissionExample(){
        if (ContextCompat.checkSelfPermission(DailyRoutineActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            /*if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(this, "App need to get permission of access external memory card", Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, MY_REQUEST_READ_CONTACTS);
            }*/
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, MY_REQUEST_READ_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults){
        switch (requestCode){
            case MY_REQUEST_READ_CONTACTS:
                if (grantResults.length >   0){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        craeteFolder();
                    }
                    else {
                        getPermissionExample();
                    }
                }
        }
    }

    public void craeteFolder(){
        String folder   =   "ReminderFolder";
        File file       =   new File(Environment.getExternalStorageDirectory(), folder);
        if (!file.exists()){
            if (file.mkdir()){
                Toast.makeText(this, "created", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
