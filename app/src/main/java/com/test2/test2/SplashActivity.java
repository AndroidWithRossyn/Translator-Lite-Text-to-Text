package com.test2.test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;

public class SplashActivity extends AppCompatActivity {

    private TextView btnStart,msg;
    private Boolean isConn;
    SpinKitView spinKit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btnStart = findViewById(R.id.btnStart);
        msg = findViewById(R.id.msg);
        spinKit = findViewById(R.id.spinKit);
        spinKit.setVisibility(View.VISIBLE);
        Sprite doubleBounce = new WanderingCubes();
        spinKit.setIndeterminateDrawable(doubleBounce);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {


                        msg.setText("Are you ready?");
                        isConn = true;
                    btnStart.setVisibility(View.VISIBLE);

                        spinKit.setVisibility(View.GONE);
                        Sprite doubleBounce = new WanderingCubes();
                        spinKit.setIndeterminateDrawable(doubleBounce);
                }else{
                    isConn = false;
                    msg.setText("Check your Connection and Try Again");
                    btnStart.setVisibility(View.GONE);

                    spinKit.setVisibility(View.GONE);
                    Sprite doubleBounce = new WanderingCubes();
                    spinKit.setIndeterminateDrawable(doubleBounce);
                }
            }
        },2000);
    }
}