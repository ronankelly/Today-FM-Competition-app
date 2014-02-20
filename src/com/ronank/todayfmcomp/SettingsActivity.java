package com.ronank.todayfmcomp;

import android.app.Activity;
import android.os.Bundle;

/**
 * Settings Activity for this app
 * 
 * @author rkelly
 *
 */
public class SettingsActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();
    }

}
