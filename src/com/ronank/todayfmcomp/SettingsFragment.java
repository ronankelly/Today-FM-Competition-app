package com.ronank.todayfmcomp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;

/**
 * Settings Fragment
 * 
 * @author rkelly
 *
 */
public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener 
{
    private CheckBoxPreference locationCheckBox;
    private EditTextPreference location;
    
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        
        locationCheckBox = (CheckBoxPreference) findPreference(getString(R.string.location_checkbox_key));
        location = (EditTextPreference) findPreference(getString(R.string.location_key));

        location.setEnabled(locationCheckBox.isChecked());
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        // Toggle if the location preference is enabled based on the checkbox
        location.setEnabled(locationCheckBox.isChecked());
    }
}
