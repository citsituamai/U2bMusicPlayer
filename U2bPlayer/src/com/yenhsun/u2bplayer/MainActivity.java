
package com.yenhsun.u2bplayer;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

public class MainActivity extends Activity {

    private PlayListView mPlayListView;
    private IPlayMusicService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        bindService();
    }

    private void initComponents() {
        mPlayListView = (PlayListView) findViewById(R.id.main_playlist);
    }

    private void bindService() {
        Intent intent = new Intent(MainActivity.this, PlayMusicService.class);
        startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            mService = IPlayMusicService.Stub.asInterface(service);
            mPlayListView.setService(mService);
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };
}
