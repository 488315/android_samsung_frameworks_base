package com.android.server.notification;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.provider.Settings;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.service.notification.ScheduleCalendar;
import android.service.notification.ZenModeConfig;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.TimeUtils;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public final class ScheduleConditionProvider extends SystemConditionProviderService {
    public AlarmManager mAlarmManager;
    public boolean mConnected;
    public long mNextAlarmTime;
    public boolean mRegistered;
    public static final ComponentName COMPONENT =
            new ComponentName("android", ScheduleConditionProvider.class.getName());
    public static final String SIMPLE_NAME = "ScheduleConditionProvider";
    public static final String ACTION_EVALUATE =
            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                    "ScheduleConditionProvider", ".EVALUATE");
    public final ScheduleConditionProvider mContext = this;
    public final ArrayMap mSubscriptions = new ArrayMap();
    public final ArraySet mSnoozedForAlarm = new ArraySet();
    public boolean mScheduleEnabled = false;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.notification.ScheduleConditionProvider.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ComponentName componentName = ScheduleConditionProvider.COMPONENT;
                    Slog.d("ConditionProviders.SCP", "onReceive " + intent.getAction());
                    if ("android.intent.action.TIMEZONE_CHANGED".equals(intent.getAction())) {
                        synchronized (ScheduleConditionProvider.this.mSubscriptions) {
                            try {
                                Iterator it =
                                        ScheduleConditionProvider.this
                                                .mSubscriptions
                                                .keySet()
                                                .iterator();
                                while (it.hasNext()) {
                                    ScheduleCalendar scheduleCalendar =
                                            (ScheduleCalendar)
                                                    ScheduleConditionProvider.this.mSubscriptions
                                                            .get((Uri) it.next());
                                    if (scheduleCalendar != null) {
                                        scheduleCalendar.setTimeZone(
                                                Calendar.getInstance().getTimeZone());
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                    ScheduleConditionProvider.this.evaluateSubscriptions$1();
                }
            };

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.notification.ScheduleConditionProvider$1] */
    public ScheduleConditionProvider() {
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("new "), SIMPLE_NAME, "()", "ConditionProviders.SCP");
    }

    public static Condition createCondition(String str, Uri uri, int i) {
        Slog.d(
                "ConditionProviders.SCP",
                "notifyCondition " + uri + " " + Condition.stateToString(i) + " reason=" + str);
        return new Condition(uri, "...", "...", "...", 0, i, 2);
    }

    public void addSnoozed(Uri uri) {
        synchronized (this.mSnoozedForAlarm) {
            this.mSnoozedForAlarm.add(uri);
            Settings.Secure.putStringForUser(
                    this.mContext.getContentResolver(),
                    "snoozed_schedule_condition_provider",
                    TextUtils.join(";", this.mSnoozedForAlarm),
                    ActivityManager.getCurrentUser());
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
        printWriter.print("    ");
        printWriter.print(SIMPLE_NAME);
        printWriter.println(":");
        printWriter.print("      mConnected=");
        printWriter.println(this.mConnected);
        printWriter.print("      mRegistered=");
        printWriter.println(this.mRegistered);
        printWriter.println("      mSubscriptions=");
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (this.mSubscriptions) {
            try {
                for (Uri uri : this.mSubscriptions.keySet()) {
                    printWriter.print("        ");
                    ScheduleCalendar scheduleCalendar =
                            (ScheduleCalendar) this.mSubscriptions.get(uri);
                    printWriter.print(
                            (scheduleCalendar == null
                                            || !scheduleCalendar.isInSchedule(currentTimeMillis))
                                    ? "  "
                                    : "* ");
                    printWriter.println(uri);
                    printWriter.print("            ");
                    printWriter.println(
                            ((ScheduleCalendar) this.mSubscriptions.get(uri)).toString());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        printWriter.println(
                "      snoozed due to alarm: " + TextUtils.join(";", this.mSnoozedForAlarm));
        SystemConditionProviderService.dumpUpcomingTime(
                printWriter, this.mNextAlarmTime, currentTimeMillis);
    }

    public Condition evaluateSubscriptionLocked(
            Uri uri, ScheduleCalendar scheduleCalendar, long j, long j2) {
        Condition createCondition;
        Condition condition;
        long nextChangeTime;
        long j3;
        boolean contains;
        Slog.d(
                "ConditionProviders.SCP",
                String.format(
                        "evaluateSubscriptionLocked cal=%s, now=%s, nextUserAlarmTime=%s",
                        scheduleCalendar,
                        SystemConditionProviderService.ts(j),
                        SystemConditionProviderService.ts(j2)));
        if (scheduleCalendar == null) {
            Condition createCondition2 = createCondition("!invalidId", uri, 3);
            removeSnoozed(uri);
            return createCondition2;
        }
        if (scheduleCalendar.isInSchedule(j)) {
            synchronized (this.mSnoozedForAlarm) {
                contains = this.mSnoozedForAlarm.contains(uri);
            }
            if (contains) {
                condition = createCondition("snoozed", uri, 0);
            } else if (scheduleCalendar.shouldExitForAlarm(j)) {
                createCondition = createCondition("alarmCanceled", uri, 0);
                addSnoozed(uri);
            } else {
                condition = createCondition("meetsSchedule", uri, 1);
            }
            scheduleCalendar.maybeSetNextAlarm(j, j2);
            nextChangeTime = scheduleCalendar.getNextChangeTime(j);
            if (nextChangeTime > 0 && nextChangeTime > j) {
                j3 = this.mNextAlarmTime;
                if (j3 != 0 || nextChangeTime < j3) {
                    this.mNextAlarmTime = nextChangeTime;
                }
            }
            return condition;
        }
        createCondition = createCondition("!meetsSchedule", uri, 0);
        removeSnoozed(uri);
        condition = createCondition;
        scheduleCalendar.maybeSetNextAlarm(j, j2);
        nextChangeTime = scheduleCalendar.getNextChangeTime(j);
        if (nextChangeTime > 0) {
            j3 = this.mNextAlarmTime;
            if (j3 != 0) {}
            this.mNextAlarmTime = nextChangeTime;
        }
        return condition;
    }

    public final void evaluateSubscriptions$1() {
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        }
        long currentTimeMillis = System.currentTimeMillis();
        this.mNextAlarmTime = 0L;
        AlarmManager.AlarmClockInfo nextAlarmClock =
                this.mAlarmManager.getNextAlarmClock(ActivityManager.getCurrentUser());
        long triggerTime = nextAlarmClock != null ? nextAlarmClock.getTriggerTime() : 0L;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSubscriptions) {
            try {
                setRegistered$1(!this.mSubscriptions.isEmpty());
                for (Uri uri : this.mSubscriptions.keySet()) {
                    Condition evaluateSubscriptionLocked =
                            evaluateSubscriptionLocked(
                                    uri,
                                    (ScheduleCalendar) this.mSubscriptions.get(uri),
                                    currentTimeMillis,
                                    triggerTime);
                    if (evaluateSubscriptionLocked != null) {
                        arrayList.add(evaluateSubscriptionLocked);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        notifyConditions((Condition[]) arrayList.toArray(new Condition[arrayList.size()]));
        long j = this.mNextAlarmTime;
        AlarmManager alarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        PendingIntent pendingIntent = getPendingIntent(j);
        alarmManager.cancel(pendingIntent);
        if (this.mScheduleEnabled) {
            if (j <= currentTimeMillis) {
                Slog.d("ConditionProviders.SCP", "Not scheduling evaluate");
                return;
            }
            String ts = SystemConditionProviderService.ts(j);
            StringBuilder sb = new StringBuilder();
            TimeUtils.formatDuration(j - currentTimeMillis, sb);
            BootReceiver$$ExternalSyntheticOutline0.m(
                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                            "Scheduling evaluate for ", ts, ", in ", sb.toString(), ", now="),
                    SystemConditionProviderService.ts(currentTimeMillis),
                    "ConditionProviders.SCP");
            alarmManager.setExact(0, j, pendingIntent);
        }
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final ComponentName getComponent() {
        return COMPONENT;
    }

    public PendingIntent getPendingIntent(long j) {
        return PendingIntent.getBroadcast(
                this.mContext,
                1,
                new Intent(ACTION_EVALUATE)
                        .setPackage("android")
                        .addFlags(268435456)
                        .putExtra("time", j),
                201326592);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final boolean isScheduleEnabled() {
        return this.mScheduleEnabled;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final boolean isValidConditionId(Uri uri) {
        return ZenModeConfig.isValidScheduleConditionId(uri);
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void onBootComplete() {}

    @Override // android.service.notification.ConditionProviderService
    public final void onConnected() {
        Slog.d("ConditionProviders.SCP", "onConnected");
        this.mConnected = true;
        synchronized (this.mSnoozedForAlarm) {
            try {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String stringForUser =
                            Settings.Secure.getStringForUser(
                                    this.mContext.getContentResolver(),
                                    "snoozed_schedule_condition_provider",
                                    ActivityManager.getCurrentUser());
                    if (stringForUser != null) {
                        String[] split = stringForUser.split(";");
                        for (int i = 0; i < split.length; i++) {
                            String str = split[i];
                            if (str != null) {
                                str = str.trim();
                            }
                            if (!TextUtils.isEmpty(str)) {
                                this.mSnoozedForAlarm.add(Uri.parse(str));
                            }
                        }
                    }
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        Slog.d("ConditionProviders.SCP", "onDestroy");
        this.mConnected = false;
    }

    @Override // com.android.server.notification.SystemConditionProviderService
    public final void onScheduleEnabled(boolean z) {
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "onScheduleEnabled : ", "ConditionProviders.SCP", z);
        this.mScheduleEnabled = z;
    }

    @Override // android.service.notification.ConditionProviderService
    public final void onSubscribe(Uri uri) {
        Slog.d("ConditionProviders.SCP", "onSubscribe " + uri);
        if (!ZenModeConfig.isValidScheduleConditionId(uri)) {
            notifyCondition(createCondition("invalidId", uri, 3));
            return;
        }
        synchronized (this.mSubscriptions) {
            this.mSubscriptions.put(uri, ZenModeConfig.toScheduleCalendar(uri));
        }
        evaluateSubscriptions$1();
    }

    @Override // android.service.notification.ConditionProviderService
    public final void onUnsubscribe(Uri uri) {
        Slog.d("ConditionProviders.SCP", "onUnsubscribe " + uri);
        synchronized (this.mSubscriptions) {
            this.mSubscriptions.remove(uri);
        }
        removeSnoozed(uri);
        evaluateSubscriptions$1();
    }

    public final void removeSnoozed(Uri uri) {
        synchronized (this.mSnoozedForAlarm) {
            this.mSnoozedForAlarm.remove(uri);
            Settings.Secure.putStringForUser(
                    this.mContext.getContentResolver(),
                    "snoozed_schedule_condition_provider",
                    TextUtils.join(";", this.mSnoozedForAlarm),
                    ActivityManager.getCurrentUser());
        }
    }

    public final void setRegistered$1(boolean z) {
        if (this.mRegistered == z) {
            return;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "setRegistered ", "ConditionProviders.SCP", z);
        this.mRegistered = z;
        if (!z) {
            unregisterReceiver(this.mReceiver);
            return;
        }
        IntentFilter m =
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.TIME_SET", "android.intent.action.TIMEZONE_CHANGED");
        m.addAction(ACTION_EVALUATE);
        m.addAction("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
        registerReceiver(this.mReceiver, m, 2);
    }
}
