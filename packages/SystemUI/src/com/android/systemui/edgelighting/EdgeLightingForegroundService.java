package com.android.systemui.edgelighting;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Slog;
import com.android.systemui.R;
import com.android.systemui.plugins.subscreen.SubRoom;

public class EdgeLightingForegroundService extends Service {
    public String CHANNEL_TAG;
    public ScreenStateReceiver mScreenStateReceiver = null;

    public final class ScreenStateReceiver extends BroadcastReceiver {
        public /* synthetic */ ScreenStateReceiver(EdgeLightingForegroundService edgeLightingForegroundService, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals("android.intent.action.SCREEN_OFF") && intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                EdgeLightingForegroundService.this.stopSelf();
            }
        }

        private ScreenStateReceiver() {
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        Slog.d("EdgeLightingForegroundService", "stopForeground service");
        ScreenStateReceiver screenStateReceiver = this.mScreenStateReceiver;
        if (screenStateReceiver != null) {
            unregisterReceiver(screenStateReceiver);
            this.mScreenStateReceiver = null;
        }
        stopForeground(true);
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(SubRoom.EXTRA_VALUE_NOTIFICATION);
        if (notificationManager.getNotificationChannel("edge_lighting_chnnel_id") == null) {
            this.CHANNEL_TAG = getString(R.string.edge_lighting_label);
            notificationManager.createNotificationChannel(new NotificationChannel("edge_lighting_chnnel_id", this.CHANNEL_TAG, 1));
        }
        Notification.Builder builder = new Notification.Builder(this, "edge_lighting_chnnel_id");
        builder.setContentTitle("Edge lighting").setGroup("group_key_lighting").setSmallIcon(R.drawable.edge_screen_icon).setOngoing(true);
        Slog.d("EdgeLightingForegroundService", "startForeground service");
        startForeground(4096, builder.build());
        if (this.mScreenStateReceiver == null) {
            this.mScreenStateReceiver = new ScreenStateReceiver(this, 0);
            IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            registerReceiver(this.mScreenStateReceiver, intentFilter);
        }
        return super.onStartCommand(intent, i, i2);
    }
}
