package com.android.server.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.util.Slog;

public final class SamsungAlarmManagerService {
    public static final Intent ALARM_CLOCK_CHANGED_INTENT =
            new Intent("com.samsung.android.action.ALARM_CLOCK_CHANGED").addFlags(536870912);
    public final Context mContext;
    public final SamsungAlarmHandler mHandler = new SamsungAlarmHandler();

    public final class SamsungAlarmHandler extends Handler {
        public SamsungAlarmHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            SamsungAlarmManagerService.this.mContext.sendBroadcastAsUser(
                    SamsungAlarmManagerService.ALARM_CLOCK_CHANGED_INTENT, UserHandle.ALL);
        }
    }

    public SamsungAlarmManagerService(Context context) {
        this.mContext = context;
        Slog.v("SamsungAlarmManager", "SamsungAlarmManagerService created.");
    }
}
