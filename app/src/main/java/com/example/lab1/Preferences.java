package com.example.lab1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class Preferences extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);

        final CheckBoxPreference boldPref = (CheckBoxPreference) findPreference("TitleBold");
        final CheckBoxPreference italicPref = (CheckBoxPreference) findPreference("TitleItalic");
        final CheckBoxPreference normalPref = (CheckBoxPreference) findPreference("TitleNormal");

        boldPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boldPref.setChecked(true);
                italicPref.setChecked(false);
                normalPref.setChecked(false);
                return false;
            }
        });

        italicPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boldPref.setChecked(false);
                italicPref.setChecked(true);
                normalPref.setChecked(false);
                return false;
            }
        });

        normalPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boldPref.setChecked(false);
                italicPref.setChecked(false);
                normalPref.setChecked(true);
                return false;
            }
        });

    }


}
