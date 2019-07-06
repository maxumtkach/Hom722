package com.example.hom7_2_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    private EditText callEditText;
    private EditText smsEditText;
    private Button callButton;
    private Button smsButton;
    private String phoneNo;
    private String phoneSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneNo = callEditText.getText().toString();

                callByNumber();

            }
        });

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phoneSms = smsEditText.getText().toString();
                smsByNumber();
            }
        });
    }

    private void callByNumber() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не получено
            // Делаем запрос на добавление разрешения звонка
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            // Разрешение уже получено
            Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo));
            // Звоним
            startActivity(intentCall);
        }
    }

    private void smsByNumber() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не получено
            // Делаем запрос на добавление разрешения sms
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        } else {
            //  Разрешение уже получено
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, phoneSms, null, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
// Проверяем результат запроса на право позвонить
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// Разрешение получено, осуществляем звонок
                    callByNumber();
                } else {
                    finish();
                }
            }
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
// Проверяем результат запроса на право позвонить
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// Разрешение получено, осуществляем звонок
                    smsByNumber();
                } else {
                    finish();
                }
            }
        }
    }

    private void initView() {
        callEditText = findViewById(R.id.tel_text);
        smsEditText = findViewById(R.id.sms_text);
        callButton = findViewById(R.id.btn_tel);
        smsButton = findViewById(R.id.btn_send);
    }
}
