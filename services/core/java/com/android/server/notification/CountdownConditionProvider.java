package com.android.server.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.service.notification.ZenModeConfig;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CountdownConditionProvider extends SystemConditionProviderService {
    public boolean mConnected;
    public boolean mIsAlarm;
    public long mTime;
    public static final boolean DEBUG = Log.isLoggable("ConditionProviders", 3);
    public static final ComponentName COMPONENT =
            new ComponentName("android", CountdownConditionProvider.class.getName());
    public static final String ACTION = CountdownConditionProvider.class.getName();
    public final CountdownConditionProvider mContext = this;
    public final Receiver mReceiver = new Receiver();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (CountdownConditionProvider.ACTION.equals(intent.getAction())) {
                Uri uri = (Uri) intent.getParcelableExtra("condition_id", Uri.class);
                boolean isValidCountdownToAlarmConditionId =
                        ZenModeConfig.isValidCountdownToAlarmConditionId(uri);
                long tryParseCountdownConditionId = ZenModeConfig.tryParseCountdownConditionId(uri);
                if (CountdownConditionProvider.DEBUG) {
                    Slog.d("ConditionProviders.CCP", "Countdown condition fired: " + uri);
                }
                if (tryParseCountdownConditionId > 0) {
                    CountdownConditionProvider.this.notifyCondition(
                            new Condition(
                                    ZenModeConfig.toCountdownConditionId(
                                            tryParseCountdownConditionId,
                                            isValidCountdownToAlarmConditionId),
                                    "",
                                    "",
                                    "",
                                    0,
                                    0,
                                    1));
                }
            }
        }
    }

    public CountdownConditionProvider() {
        if (DEBUG) {
            Slog.d("ConditionProviders.CCP", "new CountdownConditionProvider()");
        }
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final IConditionProvider asInterface() {
        return onBind(null);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void attachBase(Context context) {
        attachBaseContext(context);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void dump(PrintWriter printWriter) {
        printWriter.println("    CountdownConditionProvider:");
        printWriter.print("      mConnected=");
        printWriter.println(this.mConnected);
        printWriter.print("      mTime=");
        printWriter.println(this.mTime);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final ComponentName getComponent() {
        return COMPONENT;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final boolean isScheduleEnabled() {
        return false;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final boolean isValidConditionId(Uri uri) {
        return ZenModeConfig.isValidCountdownConditionId(uri);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void onBootComplete() {}

    @Override // android.service.notification.ConditionProviderService
    public final void onConnected() {
        if (DEBUG) {
            Slog.d("ConditionProviders.CCP", "onConnected");
        }
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter(ACTION), 2);
        this.mConnected = true;
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            Slog.d("ConditionProviders.CCP", "onDestroy");
        }
        if (this.mConnected) {
            this.mContext.unregisterReceiver(this.mReceiver);
        }
        this.mConnected = false;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void onScheduleEnabled(boolean z) {
        if (DEBUG) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "onScheduleEnabled : ", "ConditionProviders.CCP", z);
        }
    }

    @Override // android.service.notification.ConditionProviderService
    public final void onSubscribe(Uri uri) {
        boolean z = DEBUG;
        if (z) {
            Slog.d("ConditionProviders.CCP", "onSubscribe " + uri);
        }
        this.mTime = ZenModeConfig.tryParseCountdownConditionId(uri);
        this.mIsAlarm = ZenModeConfig.isValidCountdownToAlarmConditionId(uri);
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        String str = ACTION;
        PendingIntent broadcast =
                PendingIntent.getBroadcast(
                        this.mContext,
                        100,
                        new Intent(str)
                                .setPackage("android")
                                .putExtra("condition_id", uri)
                                .setFlags(1073741824),
                        201326592);
        alarmManager.cancel(broadcast);
        if (this.mTime > 0) {
            long currentTimeMillis = System.currentTimeMillis();
            CharSequence relativeTimeSpanString =
                    DateUtils.getRelativeTimeSpanString(this.mTime, currentTimeMillis, 60000L);
            long j = this.mTime;
            if (j <= currentTimeMillis) {
                notifyCondition(
                        new Condition(
                                ZenModeConfig.toCountdownConditionId(j, this.mIsAlarm),
                                "",
                                "",
                                "",
                                0,
                                0,
                                1));
            } else {
                alarmManager.setExact(0, j, broadcast);
            }
            if (z) {
                long j2 = this.mTime;
                Slog.d(
                        "ConditionProviders.CCP",
                        String.format(
                                "%s %s for %s, %s in the future (%s), now=%s",
                                j2 <= currentTimeMillis ? "Not scheduling" : "Scheduling",
                                str,
                                SystemConditionProviderService.ts(j2),
                                Long.valueOf(this.mTime - currentTimeMillis),
                                relativeTimeSpanString,
                                SystemConditionProviderService.ts(currentTimeMillis)));
            }
        }
    }

    @Override // android.service.notification.ConditionProviderService
    public final void onUnsubscribe(Uri uri) {}
}
