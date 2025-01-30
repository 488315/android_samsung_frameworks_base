package com.samsung.android.displaysolution;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Slog;

/* loaded from: classes2.dex */
public class BigDataLoggingService {
    public final boolean DEBUG = "eng".equals(Build.TYPE);
    public final Context mContext;
    public ScrControlHandler mHandler;
    public HandlerThread mHandlerThread;
    public boolean mUseBigDataLoggingServiceConfig;

    public final void receive_boot_completed_intent() {
    }

    public final void receive_screen_off_intent() {
    }

    public final void receive_screen_on_intent() {
    }

    public final void receive_user_present_intent() {
    }

    public BigDataLoggingService(Context context) {
        this.mUseBigDataLoggingServiceConfig = false;
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("BigDataLoggingServiceThread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new ScrControlHandler(this.mHandlerThread.getLooper());
        this.mUseBigDataLoggingServiceConfig = context.getResources().getBoolean(R.bool.config_batterySaver_full_disableVibration);
        SystemProperties.set("sys.bigdatalogging.bdlon", "false");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        context.registerReceiver(new ScreenWatchingReceiver(), intentFilter);
        if (this.mUseBigDataLoggingServiceConfig) {
            SystemProperties.set("sys.bigdatalogging.bdlon", "true");
        }
    }

    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("BigDataLoggingService", "action  :  " + action);
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Slog.i("BigDataLoggingService", "ACTION_BOOT_COMPLETED");
                BigDataLoggingService.this.receive_boot_completed_intent();
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                Slog.i("BigDataLoggingService", "ACTION_SCREEN_ON");
                BigDataLoggingService.this.receive_screen_on_intent();
            } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                Slog.i("BigDataLoggingService", "ACTION_SCREEN_OFF");
                BigDataLoggingService.this.receive_screen_off_intent();
            } else if ("android.intent.action.USER_PRESENT".equals(action)) {
                Slog.i("BigDataLoggingService", "ACTION_USER_PRESENT");
                BigDataLoggingService.this.receive_user_present_intent();
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
        }

        public SettingsObserver(Handler handler) {
            super(handler);
        }
    }

    public final class ScrControlHandler extends Handler {
        public ScrControlHandler(Looper looper) {
            super(looper, null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
        }
    }
}
