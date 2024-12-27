package com.android.server.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FrameworkStatsLog;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SnoozeHelper {
    public static final boolean DEBUG = Log.isLoggable("SnoozeHelper", 3);
    public static final String REPOST_ACTION = "SnoozeHelper.EVALUATE";
    public AlarmManager mAm;
    public final AnonymousClass1 mBroadcastReceiver;
    public final NotificationManagerService$$ExternalSyntheticLambda5 mCallback;
    public final Context mContext;
    public final ManagedServices.UserProfiles mUserProfiles;
    public final ArrayMap mSnoozedNotifications = new ArrayMap();
    public final ArrayMap mPersistedSnoozedNotifications = new ArrayMap();
    public final ArrayMap mPersistedSnoozedNotificationsWithContext = new ArrayMap();
    public final Object mLock = new Object();

    public SnoozeHelper(
            Context context,
            NotificationManagerService$$ExternalSyntheticLambda5
                    notificationManagerService$$ExternalSyntheticLambda5,
            ManagedServices.UserProfiles userProfiles) {
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.notification.SnoozeHelper.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if (SnoozeHelper.DEBUG) {
                            Slog.d("SnoozeHelper", "Reposting notification");
                        }
                        if (SnoozeHelper.REPOST_ACTION.equals(intent.getAction())) {
                            SnoozeHelper snoozeHelper = SnoozeHelper.this;
                            String stringExtra = intent.getStringExtra("key");
                            intent.getIntExtra("userId", 0);
                            snoozeHelper.repost(stringExtra, false);
                        }
                    }
                };
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter(REPOST_ACTION);
        intentFilter.addDataScheme("repost");
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
        this.mAm = (AlarmManager) context.getSystemService("alarm");
        this.mCallback = notificationManagerService$$ExternalSyntheticLambda5;
        this.mUserProfiles = userProfiles;
    }

    public static String getTrimmedString(String str) {
        return (str == null || str.length() <= 1000) ? str : str.substring(0, 1000);
    }

    public final void cancel(int i, String str) {
        synchronized (this.mLock) {
            try {
                int size = this.mSnoozedNotifications.size();
                for (int i2 = 0; i2 < size; i2++) {
                    NotificationRecord notificationRecord =
                            (NotificationRecord) this.mSnoozedNotifications.valueAt(i2);
                    if (notificationRecord.sbn.getPackageName().equals(str)
                            && notificationRecord.sbn.getUserId() == i) {
                        notificationRecord.isCanceled = true;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void cancel(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mSnoozedNotifications.size() == 0) {
                    return;
                }
                IntArray intArray = new IntArray();
                intArray.add(i);
                if (z) {
                    intArray = this.mUserProfiles.getCurrentProfileIds();
                }
                for (NotificationRecord notificationRecord : this.mSnoozedNotifications.values()) {
                    if (intArray.binarySearch(notificationRecord.sbn.getUserId()) >= 0) {
                        notificationRecord.isCanceled = true;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean cancel(int i, int i2, String str, String str2) {
        synchronized (this.mLock) {
            try {
                for (Map.Entry entry : this.mSnoozedNotifications.entrySet()) {
                    StatusBarNotification statusBarNotification =
                            ((NotificationRecord) entry.getValue()).sbn;
                    if (statusBarNotification.getPackageName().equals(str)
                            && statusBarNotification.getUserId() == i
                            && Objects.equals(statusBarNotification.getTag(), str2)
                            && statusBarNotification.getId() == i2) {
                        ((NotificationRecord) entry.getValue()).isCanceled = true;
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final PendingIntent createPendingIntent(String str) {
        return PendingIntent.getBroadcast(
                this.mContext,
                1,
                new Intent(REPOST_ACTION)
                        .setPackage("android")
                        .setData(new Uri.Builder().scheme("repost").appendPath(str).build())
                        .addFlags(268435456)
                        .putExtra("key", str),
                201326592);
    }

    public final void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.println("\n  Snoozed notifications:");
                for (String str : this.mSnoozedNotifications.keySet()) {
                    printWriter.print("    ");
                    printWriter.println("key: " + str);
                }
                printWriter.println("\n Pending snoozed notifications");
                for (String str2 : this.mPersistedSnoozedNotifications.keySet()) {
                    printWriter.print("    ");
                    printWriter.println(
                            "key: "
                                    + str2
                                    + " until: "
                                    + this.mPersistedSnoozedNotifications.get(str2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getSnoozeContextForUnpostedNotification(String str) {
        String str2;
        synchronized (this.mLock) {
            str2 =
                    (String)
                            this.mPersistedSnoozedNotificationsWithContext.get(
                                    getTrimmedString(str));
        }
        return str2;
    }

    public final Long getSnoozeTimeForUnpostedNotification(String str) {
        Long l;
        synchronized (this.mLock) {
            l = (Long) this.mPersistedSnoozedNotifications.get(getTrimmedString(str));
        }
        if (l == null) {
            return 0L;
        }
        return l;
    }

    public final Collection getSnoozed(int i, String str) {
        ArrayList arrayList;
        synchronized (this.mLock) {
            try {
                arrayList = new ArrayList();
                for (NotificationRecord notificationRecord : this.mSnoozedNotifications.values()) {
                    if (notificationRecord.sbn.getUserId() == i
                            && notificationRecord.sbn.getPackageName().equals(str)) {
                        arrayList.add(notificationRecord);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final List getSnoozed() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList();
            arrayList.addAll(this.mSnoozedNotifications.values());
        }
        return arrayList;
    }

    public final boolean isSnoozed(String str) {
        boolean containsKey;
        synchronized (this.mLock) {
            containsKey = this.mSnoozedNotifications.containsKey(str);
        }
        return containsKey;
    }

    public final void repost(String str, boolean z) {
        NotificationRecord notificationRecord;
        String trimmedString = getTrimmedString(str);
        synchronized (this.mLock) {
            this.mPersistedSnoozedNotifications.remove(trimmedString);
            this.mPersistedSnoozedNotificationsWithContext.remove(trimmedString);
            notificationRecord = (NotificationRecord) this.mSnoozedNotifications.remove(str);
        }
        if (notificationRecord == null || notificationRecord.isCanceled) {
            return;
        }
        this.mAm.cancel(createPendingIntent(notificationRecord.sbn.getKey()));
        MetricsLogger.action(
                notificationRecord
                        .getLogMaker()
                        .setCategory(
                                FrameworkStatsLog.SENSITIVE_NOTIFICATION_APP_PROTECTION_SESSION)
                        .setType(1));
        this.mCallback.repost(notificationRecord.sbn.getUserId(), notificationRecord, z);
    }

    public final void repostAll(IntArray intArray) {
        synchronized (this.mLock) {
            try {
                Iterator it = ((ArrayList) getSnoozed()).iterator();
                while (it.hasNext()) {
                    NotificationRecord notificationRecord = (NotificationRecord) it.next();
                    if (intArray.binarySearch(notificationRecord.sbn.getUserId()) >= 0) {
                        String key = notificationRecord.sbn.getKey();
                        notificationRecord.sbn.getUserId();
                        repost(key, false);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void repostGroupSummary(int i, String str, String str2) {
        String str3;
        synchronized (this.mLock) {
            try {
                int size = this.mSnoozedNotifications.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        str3 = null;
                        break;
                    }
                    NotificationRecord notificationRecord =
                            (NotificationRecord) this.mSnoozedNotifications.valueAt(i2);
                    if (notificationRecord.sbn.getPackageName().equals(str)
                            && notificationRecord.sbn.getUserId() == i
                            && notificationRecord.sbn.isGroup()
                            && notificationRecord.sbn.getNotification().isGroupSummary()
                            && str2.equals(notificationRecord.sbn.getGroupKey())) {
                        str3 = notificationRecord.sbn.getKey();
                        break;
                    }
                    i2++;
                }
                if (str3 != null) {
                    NotificationRecord notificationRecord2 =
                            (NotificationRecord) this.mSnoozedNotifications.remove(str3);
                    String trimmedString = getTrimmedString(str3);
                    this.mPersistedSnoozedNotificationsWithContext.remove(trimmedString);
                    this.mPersistedSnoozedNotifications.remove(trimmedString);
                    if (notificationRecord2 != null && !notificationRecord2.isCanceled) {
                        MetricsLogger.action(
                                notificationRecord2
                                        .getLogMaker()
                                        .setCategory(
                                                FrameworkStatsLog
                                                        .SENSITIVE_NOTIFICATION_APP_PROTECTION_SESSION)
                                        .setType(1));
                        this.mCallback.repost(
                                notificationRecord2.sbn.getUserId(), notificationRecord2, false);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setAlarmManager(AlarmManager alarmManager) {
        this.mAm = alarmManager;
    }

    public final void snooze(NotificationRecord notificationRecord) {
        if (DEBUG) {
            Slog.d("SnoozeHelper", "Snoozing " + notificationRecord.sbn.getKey());
        }
        synchronized (this.mLock) {
            this.mSnoozedNotifications.put(notificationRecord.sbn.getKey(), notificationRecord);
        }
    }

    public final void update(NotificationRecord notificationRecord) {
        synchronized (this.mLock) {
            try {
                if (this.mSnoozedNotifications.containsKey(notificationRecord.sbn.getKey())) {
                    this.mSnoozedNotifications.put(
                            notificationRecord.sbn.getKey(), notificationRecord);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
