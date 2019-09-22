package ch.teko.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = "MainActivity";
    BroadcastReceiver connectivityReceiver;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        connectivityReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    Log.d(LOG_TAG, intent.getAction());
                    Log.d(LOG_TAG, ConnectivityManager.CONNECTIVITY_ACTION);

                    if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                        Log.d(LOG_TAG, "if statement");
                        boolean isConnected = isNetworkInterfaceAvailable(context);
                        String conn = isConnected? "internet":"no internet";
                        textView.setText(conn);
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "onRecieve() error, " + String.valueOf(e));
                }
            }

            private boolean isNetworkInterfaceAvailable(Context context) {
                final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
                return (activeNetwork != null && activeNetwork.isConnected());
            }
        };
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityReceiver);
    }


}
