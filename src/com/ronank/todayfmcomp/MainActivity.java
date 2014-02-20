package com.ronank.todayfmcomp;

import java.util.Locale;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Main Activity for this app
 * 
 * @author rkelly
 *
 */
public class MainActivity extends Activity
{
    private Button button1;
    private Button button2;
    private Button button3;
    private SharedPreferences prefs;
    
    private static final String[] NUMERIC_PREFIX = { "1", "2", "3" };
    private static final String[] ALPHA_PREFIX   = { "A", "B", "C" };
    private static final String DEFAULT_PHONE_NUMBER = "53102";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendSMS(1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendSMS(2);
            }
        });
        
        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendSMS(3);
            }
        });
    }
    
    @Override
    public void onResume()
    {
        super.onResume();

        updateButtons();
    }
    
    private void sendSMS(int buttonNumber)
    {
        try
        {
            String phoneNumber = prefs.getString(getString(R.string.phone_key), DEFAULT_PHONE_NUMBER);
            String message = generateMessage(buttonNumber);
            String toastMessage = String.format(Locale.ENGLISH, getString(R.string.toast_message_format), message, phoneNumber);
    
            SmsManager smsManager = SmsManager.getDefault();

            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
        }
        catch (InvalidMessageFormatException e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    private String generateMessage(int buttonNumber) throws InvalidMessageFormatException
    {
        String name = prefs.getString(getString(R.string.name_key), null);
        String location = prefs.getString(getString(R.string.location_key), null);
        boolean addLocation = prefs.getBoolean(getString(R.string.location_checkbox_key), false);
        
        // Check the values of the message.
        if (name == null || (addLocation && location == null))
            throw new InvalidMessageFormatException(getString(R.string.invalid_message_format));
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(getPrefix(buttonNumber));
        sb.append(" ");
        sb.append(name);
        if (addLocation)
        {
            sb.append(" ");
            sb.append(location);
        }
        
        return sb.toString();
    }
    
    private String getPrefix(int buttonNumber)
    {
        String prefixType = prefs.getString(getString(R.string.prefix_list_key), "1");
        
        if (prefixType.equalsIgnoreCase("1"))
            return  NUMERIC_PREFIX[buttonNumber - 1];
        else if (prefixType.equalsIgnoreCase("2"))
            return ALPHA_PREFIX[buttonNumber - 1];

        // Default is to just return numeric value
        return NUMERIC_PREFIX[buttonNumber - 1];
    }
    
    private void updateButtons()
    {
        String name = prefs.getString(getString(R.string.name_key), "");
        
        button1.setText(getPrefix(1) + " " + name);
        button2.setText(getPrefix(2) + " " + name);
        button3.setText(getPrefix(3) + " " + name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // As there's only one option, We always start SettingsActivity
     
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SettingsActivity.class);
        startActivity(intent); 
     
        return true;
    }


}
