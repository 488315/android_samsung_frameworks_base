package com.samsung.android.displaysolution;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Slog;

public final class BigDataLoggingService {

    public final class ScrControlHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
        }
    }

    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("BigDataLoggingService", "action  :  " + action);
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Slog.i("BigDataLoggingService", "ACTION_BOOT_COMPLETED");
                BigDataLoggingService.this.getClass();
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                Slog.i("BigDataLoggingService", "ACTION_SCREEN_ON");
                BigDataLoggingService.this.getClass();
            } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                Slog.i("BigDataLoggingService", "ACTION_SCREEN_OFF");
                BigDataLoggingService.this.getClass();
            } else if ("android.intent.action.USER_PRESENT".equals(action)) {
                Slog.i("BigDataLoggingService", "ACTION_USER_PRESENT");
                BigDataLoggingService.this.getClass();
            }
        }
    }
}
