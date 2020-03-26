package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Fragment fragment =new Preferences();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_layout,fragment)
                .commit();
    }
}
