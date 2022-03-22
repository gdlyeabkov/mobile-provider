package softtrack.apps.mobileprovider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public ViewPager2 activityMainContainerBody;
    public TabLayout activityMainContainerTabs;
    public Spinner activityMainContainerPhoneDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

    }

    public void initialize() {
        findViews();
        initializeWidgets();
        addHandlers();
        checkSendMessages();
    }

    public void findViews() {
        activityMainContainerBody = findViewById(R.id.activity_main_container_body);
        activityMainContainerTabs = findViewById(R.id.activity_main_container_tabs);
        activityMainContainerPhoneDropdown = findViewById(R.id.activity_main_container_phone_dropdown);
    }

    public void initializeTabs() {
        FragmentManager fm = getSupportFragmentManager();
        ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle());
        activityMainContainerBody.setAdapter(sa);
        activityMainContainerTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIndex = tab.getPosition();
                activityMainContainerBody.setCurrentItem(tabIndex);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        activityMainContainerBody.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                boolean isCommunicationPage = position == 0;
                if (isCommunicationPage) {
                    activityMainContainerTabs.selectTab(activityMainContainerTabs.getTabAt(position));
                }
            }
        });
    }

    public void initializePhoneDropdown() {
        String phoneNumber = getPhoneNumber();
        ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, new String[]{ phoneNumber });
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityMainContainerPhoneDropdown.setAdapter(adapter);
    }

    public void initializeWidgets() {
        initializeTabs();
        initializePhoneDropdown();
    }

    public void addHandlers() {

    }

    public String getPhoneNumber() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_SMS }, 1);
        }
        String phoneNumber = telephonyManager.getLine1Number();
        return phoneNumber;
    }

    public void checkSendMessages() {
        //Getting intent and PendingIntent instance
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
        //Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms = SmsManager.getDefault();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.SEND_SMS }, 2);
        } else {
            sendMessages();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        boolean isSendSmsCode = requestCode == 2;
        if (isSendSmsCode) {
            sendMessages();
        }
    }

    public void sendMessages() {
        // отправлять смс в случае если установлен пункт в настройках отправлять смс при входе с другого устройства
        // sms.sendTextMessage("", null, "message from application", pi, null);
    }
}