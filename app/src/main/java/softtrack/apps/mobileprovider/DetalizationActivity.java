package softtrack.apps.mobileprovider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.CallLog;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetalizationActivity extends AppCompatActivity {

    public String data = "";
    public LinearLayout activityDetalizationContainerBody;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalization);

        try {
            initialize();
        } catch (RemoteException e) {
            Log.d("debug", "ошибка получения мобильных данных");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initialize() throws RemoteException {
        initializeConstants();
        findViews();
        initializeViews();
        initializeActionBar();
    }

    public void initializeConstants() {
        Intent myIntent = getIntent();
        Bundle extras = myIntent.getExtras();
        boolean isExtrasExists =  extras != null;
        if (isExtrasExists) {
            data = extras.getString("data");
        }
    }

    public void findViews() {
        activityDetalizationContainerBody = findViewById(R.id.activity_detalization_container_body);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initializeViews() throws RemoteException {
        boolean isGetCalls = data.equalsIgnoreCase("calls");
        boolean isGetMessages = data.equalsIgnoreCase("messages");
        boolean isGetDataUsage = data.equalsIgnoreCase("datausage");
        if (isGetCalls) {
            outputCalls();
        } else if (isGetMessages) {
            outputMessages();
        } else if (isGetDataUsage) {
            outputDataUsage();
        }
    }

    public void initializeActionBar() {
        boolean isGetCalls = data.equalsIgnoreCase("calls");
        boolean isGetMessages = data.equalsIgnoreCase("messages");
        boolean isGetDataUsage = data.equalsIgnoreCase("datausage");
        String actionBarTitle = "";
        if (isGetCalls) {
            actionBarTitle = "Звонки";
        } else if (isGetMessages) {
            actionBarTitle = "Сообщения";
        } else if (isGetDataUsage) {
            actionBarTitle = "Интернет";
        }
        setTitle(actionBarTitle);
    }

    public void outputCalls() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG }, 1);
        }
        Uri allCalls = Uri.parse("content://call_log/calls");
        Cursor c = managedQuery(allCalls, null, null, null, null);
        c.moveToFirst();
        Log.d("debug", "c.getCount(): " + String.valueOf(c.getCount()));
        for (int i = 0; i < c.getCount(); i++) {
            @SuppressLint("Range") String num = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));// for  number
            @SuppressLint("Range") String name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));// for name
            @SuppressLint("Range") String duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));// for duration
            @SuppressLint("Range") int type = Integer.parseInt(c.getString(c.getColumnIndex(CallLog.Calls.TYPE)));// for call type, Incoming or out going.
            @SuppressLint("Range") long dateInSeconds = Long.parseLong(c.getString(c.getColumnIndex(CallLog.Calls.DATE)));
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy в HH:mm");
            String dateString = formatter.format(new Date(dateInSeconds));

            int rawType = R.drawable.income;
            int rawTypeEffect = Color.rgb(0, 150, 0);
            String footerLabelMsg = "";
            boolean isIncomingCall = type == 1;
            boolean isOutgointCall = type == 2;
            if (isIncomingCall) {
                rawType = R.drawable.income;
                rawTypeEffect = Color.rgb(0, 150, 0);
                footerLabelMsg = "Исходящие звонки Домашнего\nрегиона";
            } else if (isOutgointCall) {
                rawType = R.drawable.outcome;
                rawTypeEffect = Color.rgb(255, 0, 0);
                footerLabelMsg = "Входящие звонки Домашнего\nрегиона";
            }
            LinearLayout item = new LinearLayout(DetalizationActivity.this);
            item.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout itemAside = new LinearLayout(DetalizationActivity.this);
            itemAside.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams itemAsideLayoutParams = new LinearLayout.LayoutParams(450, ViewGroup.LayoutParams.MATCH_PARENT);
            itemAside.setLayoutParams(itemAsideLayoutParams);
            LinearLayout itemAsideHeader = new LinearLayout(DetalizationActivity.this);
            itemAsideHeader.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams itemAsideHeaderLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 35);
            itemAsideHeader.setLayoutParams(itemAsideHeaderLayoutParams);
            TextView itemNumber = new TextView(DetalizationActivity.this);
            itemNumber.setText(name);
            itemAsideHeader.addView(itemNumber);
            ImageView itemDirection = new ImageView(DetalizationActivity.this);
            LinearLayout.LayoutParams itemDirectionLayoutParams = new LinearLayout.LayoutParams(35, 35);
            itemDirectionLayoutParams.setMargins(50, 0, 0, 0);
            itemDirection.setLayoutParams(itemDirectionLayoutParams);
            itemDirection.setImageResource(rawType);
            itemDirection.setColorFilter(rawTypeEffect);
            itemAsideHeader.addView(itemDirection);
            itemAside.addView(itemAsideHeader);
            TextView itemAsideFooterLabel = new TextView(DetalizationActivity.this);
            itemAsideFooterLabel.setText(footerLabelMsg);
            itemAside.addView(itemAsideFooterLabel);
            LinearLayout itemArticle = new LinearLayout(DetalizationActivity.this);
            itemArticle.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams itemArticleLayoutParams = new LinearLayout.LayoutParams(450, ViewGroup.LayoutParams.MATCH_PARENT);
            itemArticle.setLayoutParams(itemArticleLayoutParams);
            item.addView(itemAside);
            TextView costLabel = new TextView(DetalizationActivity.this);
            costLabel.setText("1,5 ₽");
            itemArticle.addView(costLabel);
            TextView dateLabel = new TextView(DetalizationActivity.this);
            dateLabel.setText(dateString);
            itemArticle.addView(dateLabel);
            item.addView(itemArticle);
            activityDetalizationContainerBody.addView(item);
            c.moveToNext();
        }
    }

    public void outputMessages() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_SMS }, 1);
        }
        Uri allMessages = Uri.parse("content://sms/inbox");
        Cursor c = managedQuery(allMessages, null, null, null, null);
        c.moveToFirst();
        Log.d("debug", "c.getCount(): " + String.valueOf(c.getCount()));
        for (int i = 0; i < c.getCount(); i++) {
            @SuppressLint("Range") String name = c.getString(c.getColumnIndex(Telephony.Sms.Inbox.ADDRESS));
            @SuppressLint("Range") int type = Integer.parseInt(c.getString(c.getColumnIndex(Telephony.Sms.Inbox.TYPE)));
            @SuppressLint("Range") long dateInSeconds = Long.parseLong(c.getString(c.getColumnIndex(Telephony.Sms.Inbox.DATE)));
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy в HH:mm");
            String dateString = formatter.format(new Date(dateInSeconds));

            int rawType = R.drawable.income;
            int rawTypeEffect = Color.rgb(0, 150, 0);
            String footerLabelMsg = "";
            boolean isIncomingCall = type == 1;
            boolean isOutgointCall = type == 2;
            if (isIncomingCall) {
                rawType = R.drawable.income;
                rawTypeEffect = Color.rgb(0, 150, 0);
                footerLabelMsg = "Исходящие SMS/MMS Домашнего\nрегиона";
            } else if (isOutgointCall) {
                rawType = R.drawable.outcome;
                rawTypeEffect = Color.rgb(255, 0, 0);
                footerLabelMsg = "Входящие SMS/MMS Домашнего\nрегиона";
            }
            LinearLayout item = new LinearLayout(DetalizationActivity.this);
            item.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout itemAside = new LinearLayout(DetalizationActivity.this);
            itemAside.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams itemAsideLayoutParams = new LinearLayout.LayoutParams(450, ViewGroup.LayoutParams.MATCH_PARENT);
            itemAside.setLayoutParams(itemAsideLayoutParams);
            LinearLayout itemAsideHeader = new LinearLayout(DetalizationActivity.this);
            itemAsideHeader.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams itemAsideHeaderLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 35);
            itemAsideHeader.setLayoutParams(itemAsideHeaderLayoutParams);
            TextView itemNumber = new TextView(DetalizationActivity.this);
            itemNumber.setText(name);
            itemAsideHeader.addView(itemNumber);
            ImageView itemDirection = new ImageView(DetalizationActivity.this);
            LinearLayout.LayoutParams itemDirectionLayoutParams = new LinearLayout.LayoutParams(35, 35);
            itemDirectionLayoutParams.setMargins(50, 0, 0, 0);
            itemDirection.setLayoutParams(itemDirectionLayoutParams);
            itemDirection.setImageResource(rawType);
            itemDirection.setColorFilter(rawTypeEffect);
            itemAsideHeader.addView(itemDirection);
            itemAside.addView(itemAsideHeader);
            TextView itemAsideFooterLabel = new TextView(DetalizationActivity.this);
            itemAsideFooterLabel.setText(footerLabelMsg);
            itemAside.addView(itemAsideFooterLabel);
            LinearLayout itemArticle = new LinearLayout(DetalizationActivity.this);
            itemArticle.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams itemArticleLayoutParams = new LinearLayout.LayoutParams(450, ViewGroup.LayoutParams.MATCH_PARENT);
            itemArticle.setLayoutParams(itemArticleLayoutParams);
            item.addView(itemAside);
            TextView costLabel = new TextView(DetalizationActivity.this);
            costLabel.setText("1,5 ₽");
            itemArticle.addView(costLabel);
            TextView dateLabel = new TextView(DetalizationActivity.this);
            dateLabel.setText(dateString);
            itemArticle.addView(dateLabel);
            item.addView(itemArticle);
            activityDetalizationContainerBody.addView(item);
            c.moveToNext();
        }
    }

    public String getSubscriberId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            TelephonyManager telephonyManager = (TelephonyManager) (DetalizationActivity.this.getApplicationContext()).getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getSubscriberId();
        } else {
            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void outputDataUsage() throws RemoteException {
        String subscriberId = getSubscriberId();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            NetworkStatsManager networkStatsManager = getSystemService(NetworkStatsManager.class);
            TelephonyManager tele = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(DetalizationActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_PHONE_STATE }, 1);
            }
            NetworkStats stats = networkStatsManager.querySummary(ConnectivityManager.TYPE_MOBILE, getSubscriberId(), 0, System.currentTimeMillis());
            boolean isNetworkActivityDetected = stats != null;
            if (isNetworkActivityDetected) {
                do {
                    NetworkStats.Bucket bucket = new NetworkStats.Bucket();
                    stats.getNextBucket(bucket);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy в HH:mm");
                    String dateString = formatter.format(new Date(bucket.getEndTimeStamp()));
                    int rawType = R.drawable.income;
                    int rawTypeEffect = Color.rgb(0, 150, 0);
                    String footerLabelMsg = "";
                    LinearLayout item = new LinearLayout(DetalizationActivity.this);
                    item.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout itemAside = new LinearLayout(DetalizationActivity.this);
                    itemAside.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams itemAsideLayoutParams = new LinearLayout.LayoutParams(450, ViewGroup.LayoutParams.MATCH_PARENT);
                    itemAside.setLayoutParams(itemAsideLayoutParams);
                    LinearLayout itemAsideHeader = new LinearLayout(DetalizationActivity.this);
                    itemAsideHeader.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams itemAsideHeaderLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 35);
                    itemAsideHeader.setLayoutParams(itemAsideHeaderLayoutParams);
                    TextView itemNumber = new TextView(DetalizationActivity.this);
                    itemNumber.setText("Мобильный интернет");
                    itemAsideHeader.addView(itemNumber);
                    ImageView itemDirection = new ImageView(DetalizationActivity.this);
                    LinearLayout.LayoutParams itemDirectionLayoutParams = new LinearLayout.LayoutParams(35, 35);
                    itemDirectionLayoutParams.setMargins(50, 0, 0, 0);
                    itemDirection.setLayoutParams(itemDirectionLayoutParams);
                    itemDirection.setImageResource(rawType);
                    itemDirection.setColorFilter(rawTypeEffect);
                    itemAsideHeader.addView(itemDirection);
                    itemAside.addView(itemAsideHeader);
                    TextView itemAsideFooterLabel = new TextView(DetalizationActivity.this);
                    itemAsideFooterLabel.setText("Пакет интернета");
                    itemAside.addView(itemAsideFooterLabel);
                    LinearLayout itemArticle = new LinearLayout(DetalizationActivity.this);
                    itemArticle.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams itemArticleLayoutParams = new LinearLayout.LayoutParams(450, ViewGroup.LayoutParams.MATCH_PARENT);
                    itemArticle.setLayoutParams(itemArticleLayoutParams);
                    item.addView(itemAside);
                    TextView costLabel = new TextView(DetalizationActivity.this);
                    costLabel.setText("0,49 ₽");
                    itemArticle.addView(costLabel);
                    TextView dateLabel = new TextView(DetalizationActivity.this);
                    dateLabel.setText(dateString);
                    itemArticle.addView(dateLabel);
                    item.addView(itemArticle);
                    activityDetalizationContainerBody.addView(item);
                } while (stats.hasNextBucket());
            }
        }
    }

}
