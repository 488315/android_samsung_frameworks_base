package com.android.server.usage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.usage.UsageStatsService;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppTimeLimitController {
    public static final Integer ONE = new Integer(1);
    public AlarmManager mAlarmManager;
    public final Context mContext;
    public final MyHandler mHandler;
    public final UsageStatsService.AnonymousClass2 mListener;
    public final Lock mLock = new Lock();
    public final SparseArray mUsers = new SparseArray();
    public final SparseArray mObserverApps = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppUsageGroup extends UsageGroup {
        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public final void onLimitReached() {
            super.onLimitReached();
            remove();
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public final void remove() {
            super.remove();
            ObserverAppData observerAppData = (ObserverAppData) this.mObserverAppRef.get();
            if (observerAppData != null) {
                observerAppData.appUsageGroups.remove(this.mObserverId);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppUsageLimitGroup extends UsageGroup {
        public AppUsageLimitGroup(UserData userData, ObserverAppData observerAppData, int i, String[] strArr, long j, long j2, PendingIntent pendingIntent) {
            super(userData, observerAppData, i, strArr, j, pendingIntent);
            this.mUsageTimeMs = j2;
        }

        public final long getUsageRemaining() {
            long j;
            int i = this.mActives;
            long j2 = this.mTimeLimitMs;
            if (i > 0) {
                j2 -= this.mUsageTimeMs;
                j = AppTimeLimitController.this.getElapsedRealtime() - this.mLastKnownUsageTimeMs;
            } else {
                j = this.mUsageTimeMs;
            }
            return j2 - j;
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public final void remove() {
            super.remove();
            ObserverAppData observerAppData = (ObserverAppData) this.mObserverAppRef.get();
            if (observerAppData != null) {
                observerAppData.appUsageLimitGroups.remove(this.mObserverId);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lock {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                synchronized (AppTimeLimitController.this.mLock) {
                    ((UsageGroup) message.obj).checkTimeout(AppTimeLimitController.this.getElapsedRealtime());
                }
            } else {
                if (i != 2) {
                    super.handleMessage(message);
                    return;
                }
                synchronized (AppTimeLimitController.this.mLock) {
                    ((UsageGroup) message.obj).onLimitReached();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ObserverAppData {
        public SparseArray appUsageGroups;
        public SparseArray appUsageLimitGroups;
        public SparseArray sessionUsageGroups;
        public int uid;

        public final void dump(PrintWriter printWriter) {
            printWriter.print(" uid=");
            printWriter.println(this.uid);
            printWriter.println("    App Usage Groups:");
            int size = this.appUsageGroups.size();
            for (int i = 0; i < size; i++) {
                ((AppUsageGroup) this.appUsageGroups.valueAt(i)).dump(printWriter);
                printWriter.println();
            }
            printWriter.println("    Session Usage Groups:");
            int size2 = this.sessionUsageGroups.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((SessionUsageGroup) this.sessionUsageGroups.valueAt(i2)).dump(printWriter);
                printWriter.println();
            }
            printWriter.println("    App Usage Limit Groups:");
            int size3 = this.appUsageLimitGroups.size();
            for (int i3 = 0; i3 < size3; i3++) {
                ((AppUsageLimitGroup) this.appUsageLimitGroups.valueAt(i3)).dump(printWriter);
                printWriter.println();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SessionUsageGroup extends UsageGroup implements AlarmManager.OnAlarmListener {
        public final long mNewSessionThresholdMs;
        public PendingIntent mSessionEndCallback;

        public SessionUsageGroup(UserData userData, ObserverAppData observerAppData, int i, String[] strArr, long j, PendingIntent pendingIntent, long j2, PendingIntent pendingIntent2) {
            super(userData, observerAppData, i, strArr, j, pendingIntent);
            this.mNewSessionThresholdMs = j2;
            this.mSessionEndCallback = pendingIntent2;
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public final void dump(PrintWriter printWriter) {
            super.dump(printWriter);
            printWriter.print(" lastUsageEndTime=");
            printWriter.print(this.mLastUsageEndTimeMs);
            printWriter.print(" newSessionThreshold=");
            printWriter.print(this.mNewSessionThresholdMs);
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public final void noteUsageStart(long j, long j2) {
            if (this.mActives == 0) {
                if (j - this.mLastUsageEndTimeMs > this.mNewSessionThresholdMs) {
                    this.mUsageTimeMs = 0L;
                }
                AppTimeLimitController.this.getAlarmManager().cancel(this);
            }
            super.noteUsageStart(j, j2);
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public final void noteUsageStop(long j) {
            super.noteUsageStop(j);
            if (this.mActives != 0 || this.mUsageTimeMs < this.mTimeLimitMs) {
                return;
            }
            AppTimeLimitController.this.getAlarmManager().setExact(3, AppTimeLimitController.this.getElapsedRealtime() + this.mNewSessionThresholdMs, "AppTimeLimitController", this, AppTimeLimitController.this.mHandler);
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            synchronized (AppTimeLimitController.this.mLock) {
                onSessionEnd();
            }
        }

        public final void onSessionEnd() {
            UsageStatsService.AnonymousClass2 anonymousClass2;
            if (((UserData) this.mUserRef.get()) == null || (anonymousClass2 = AppTimeLimitController.this.mListener) == null) {
                return;
            }
            long j = this.mUsageTimeMs;
            PendingIntent pendingIntent = this.mSessionEndCallback;
            anonymousClass2.getClass();
            if (pendingIntent == null) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("android.app.usage.extra.OBSERVER_ID", this.mObserverId);
            intent.putExtra("android.app.usage.extra.TIME_USED", j);
            try {
                pendingIntent.send(UsageStatsService.this.getContext(), 0, intent);
            } catch (PendingIntent.CanceledException unused) {
                Slog.w("UsageStatsService", "Couldn't deliver callback: " + pendingIntent);
            }
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public final void remove() {
            super.remove();
            ObserverAppData observerAppData = (ObserverAppData) this.mObserverAppRef.get();
            if (observerAppData != null) {
                observerAppData.sessionUsageGroups.remove(this.mObserverId);
            }
            this.mSessionEndCallback = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class UsageGroup {
        public int mActives;
        public long mLastKnownUsageTimeMs;
        public long mLastUsageEndTimeMs;
        public PendingIntent mLimitReachedCallback;
        public final String[] mObserved;
        public final WeakReference mObserverAppRef;
        public final int mObserverId;
        public final long mTimeLimitMs;
        public long mUsageTimeMs;
        public final WeakReference mUserRef;

        public UsageGroup(UserData userData, ObserverAppData observerAppData, int i, String[] strArr, long j, PendingIntent pendingIntent) {
            this.mUserRef = new WeakReference(userData);
            this.mObserverAppRef = new WeakReference(observerAppData);
            this.mObserverId = i;
            this.mObserved = strArr;
            this.mTimeLimitMs = j;
            this.mLimitReachedCallback = pendingIntent;
        }

        public final void checkTimeout(long j) {
            UserData userData = (UserData) this.mUserRef.get();
            if (userData == null) {
                return;
            }
            long j2 = this.mTimeLimitMs - this.mUsageTimeMs;
            if (j2 <= 0) {
                return;
            }
            for (String str : this.mObserved) {
                if (userData.currentlyActive.containsKey(str)) {
                    long j3 = j - this.mLastKnownUsageTimeMs;
                    AppTimeLimitController appTimeLimitController = AppTimeLimitController.this;
                    if (j2 > j3) {
                        MyHandler myHandler = appTimeLimitController.mHandler;
                        myHandler.sendMessageDelayed(myHandler.obtainMessage(1, this), j2 - j3);
                        return;
                    } else {
                        this.mUsageTimeMs += j3;
                        this.mLastKnownUsageTimeMs = j;
                        MyHandler myHandler2 = appTimeLimitController.mHandler;
                        myHandler2.sendMessage(myHandler2.obtainMessage(2, this));
                        return;
                    }
                }
            }
        }

        public void dump(PrintWriter printWriter) {
            printWriter.print("        Group id=");
            printWriter.print(this.mObserverId);
            printWriter.print(" timeLimit=");
            printWriter.print(this.mTimeLimitMs);
            printWriter.print(" used=");
            printWriter.print(this.mUsageTimeMs);
            printWriter.print(" lastKnownUsage=");
            printWriter.print(this.mLastKnownUsageTimeMs);
            printWriter.print(" mActives=");
            printWriter.print(this.mActives);
            printWriter.print(" observed=");
            printWriter.print(Arrays.toString(this.mObserved));
        }

        public void noteUsageStart(long j, long j2) {
            int i = this.mActives;
            int i2 = i + 1;
            this.mActives = i2;
            if (i != 0) {
                String[] strArr = this.mObserved;
                if (i2 > strArr.length) {
                    this.mActives = strArr.length;
                    UserData userData = (UserData) this.mUserRef.get();
                    if (userData == null) {
                        return;
                    }
                    Slog.e("AppTimeLimitController", "Too many noted usage starts! Observed entities: " + Arrays.toString(strArr) + "   Active Entities: " + Arrays.toString(userData.currentlyActive.keySet().toArray()));
                    return;
                }
                return;
            }
            long j3 = this.mLastUsageEndTimeMs;
            if (j3 > j) {
                j = j3;
            }
            this.mLastKnownUsageTimeMs = j;
            long j4 = this.mUsageTimeMs;
            long j5 = this.mTimeLimitMs;
            long j6 = ((j5 - j4) - j2) + j;
            if (j6 > 0) {
                StringBuilder sb = new StringBuilder("Posting time out for ");
                sb.append(this.mObserverId);
                sb.append(" for ");
                sb.append(j6);
                BootReceiver$$ExternalSyntheticOutline0.m(sb, "ms ,mTimeLimitMs : ", j5, " ,mUsageTimeMs : ");
                sb.append(this.mUsageTimeMs);
                BootReceiver$$ExternalSyntheticOutline0.m(sb, " ,currentTimeMs : ", j2, " ,startTimeMs : ");
                BatteryService$$ExternalSyntheticOutline0.m(sb, j, "AppTimeLimitController");
                MyHandler myHandler = AppTimeLimitController.this.mHandler;
                myHandler.sendMessageDelayed(myHandler.obtainMessage(1, this), j6);
            }
        }

        public void noteUsageStop(long j) {
            int i = this.mActives - 1;
            this.mActives = i;
            if (i != 0) {
                if (i < 0) {
                    this.mActives = 0;
                    UserData userData = (UserData) this.mUserRef.get();
                    if (userData == null) {
                        return;
                    }
                    Slog.e("AppTimeLimitController", "Too many noted usage stops! Observed entities: " + Arrays.toString(this.mObserved) + "   Active Entities: " + Arrays.toString(userData.currentlyActive.keySet().toArray()));
                    return;
                }
                return;
            }
            long j2 = this.mUsageTimeMs;
            long j3 = this.mTimeLimitMs;
            boolean z = j2 < j3;
            long j4 = (j - this.mLastKnownUsageTimeMs) + j2;
            this.mUsageTimeMs = j4;
            this.mLastUsageEndTimeMs = j;
            AppTimeLimitController appTimeLimitController = AppTimeLimitController.this;
            if (z && j4 >= j3) {
                MyHandler myHandler = appTimeLimitController.mHandler;
                myHandler.sendMessage(myHandler.obtainMessage(2, this));
            }
            StringBuilder sb = new StringBuilder("Posting time out for ");
            sb.append(this.mObserverId);
            sb.append(" for  ,mTimeLimitMs : ");
            sb.append(j3);
            sb.append(" ,mUsageTimeMs : ");
            sb.append(this.mUsageTimeMs);
            sb.append(" ,mLastUsageEndTimeMs : ");
            BatteryService$$ExternalSyntheticOutline0.m(sb, this.mLastUsageEndTimeMs, "AppTimeLimitController");
            appTimeLimitController.mHandler.removeMessages(1, this);
        }

        public void onLimitReached() {
            UsageStatsService.AnonymousClass2 anonymousClass2;
            if (((UserData) this.mUserRef.get()) == null || (anonymousClass2 = AppTimeLimitController.this.mListener) == null) {
                return;
            }
            long j = this.mUsageTimeMs;
            PendingIntent pendingIntent = this.mLimitReachedCallback;
            anonymousClass2.getClass();
            if (pendingIntent == null) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("android.app.usage.extra.OBSERVER_ID", this.mObserverId);
            intent.putExtra("android.app.usage.extra.TIME_LIMIT", this.mTimeLimitMs);
            intent.putExtra("android.app.usage.extra.TIME_USED", j);
            try {
                pendingIntent.send(UsageStatsService.this.getContext(), 0, intent);
            } catch (PendingIntent.CanceledException unused) {
                Slog.w("UsageStatsService", "Couldn't deliver callback: " + pendingIntent);
            }
        }

        public void remove() {
            UserData userData = (UserData) this.mUserRef.get();
            if (userData != null) {
                for (String str : this.mObserved) {
                    ArrayList arrayList = (ArrayList) userData.observedMap.get(str);
                    if (arrayList != null) {
                        arrayList.remove(this);
                        if (arrayList.isEmpty()) {
                            userData.observedMap.remove(str);
                        }
                    }
                }
            }
            this.mLimitReachedCallback = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UserData {
        public final ArrayMap currentlyActive = new ArrayMap();
        public final ArrayMap observedMap = new ArrayMap();
        public final int userId;

        public UserData(int i) {
            this.userId = i;
        }

        public final void addUsageGroup(UsageGroup usageGroup) {
            int length = usageGroup.mObserved.length;
            for (int i = 0; i < length; i++) {
                ArrayMap arrayMap = this.observedMap;
                String[] strArr = usageGroup.mObserved;
                ArrayList arrayList = (ArrayList) arrayMap.get(strArr[i]);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    this.observedMap.put(strArr[i], arrayList);
                }
                arrayList.add(usageGroup);
            }
        }

        public final void dump(PrintWriter printWriter) {
            printWriter.print(" userId=");
            printWriter.println(this.userId);
            printWriter.print(" Currently Active:");
            int size = this.currentlyActive.size();
            for (int i = 0; i < size; i++) {
                printWriter.print((String) this.currentlyActive.keyAt(i));
                printWriter.print(", ");
            }
            printWriter.println();
            printWriter.print(" Observed Entities:");
            int size2 = this.observedMap.size();
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print((String) this.observedMap.keyAt(i2));
                printWriter.print(", ");
            }
            printWriter.println();
        }
    }

    public AppTimeLimitController(Context context, UsageStatsService.AnonymousClass2 anonymousClass2, Looper looper) {
        this.mContext = context;
        this.mHandler = new MyHandler(looper);
        this.mListener = anonymousClass2;
    }

    public static void noteActiveLocked(UserData userData, UsageGroup usageGroup, long j) {
        int length = usageGroup.mObserved.length;
        for (int i = 0; i < length; i++) {
            if (userData.currentlyActive.containsKey(usageGroup.mObserved[i])) {
                usageGroup.noteUsageStart(j, j);
            }
        }
    }

    public final void dump(PrintWriter printWriter, String[] strArr) {
        if (strArr != null) {
            for (String str : strArr) {
                if ("actives".equals(str)) {
                    synchronized (this.mLock) {
                        try {
                            int size = this.mUsers.size();
                            for (int i = 0; i < size; i++) {
                                ArrayMap arrayMap = ((UserData) this.mUsers.valueAt(i)).currentlyActive;
                                int size2 = arrayMap.size();
                                for (int i2 = 0; i2 < size2; i2++) {
                                    printWriter.println((String) arrayMap.keyAt(i2));
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                }
            }
        }
        synchronized (this.mLock) {
            try {
                printWriter.println("\n  App Time Limits");
                int size3 = this.mUsers.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    printWriter.print("   User ");
                    ((UserData) this.mUsers.valueAt(i3)).dump(printWriter);
                }
                printWriter.println();
                int size4 = this.mObserverApps.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    printWriter.print("   Observer App ");
                    ((ObserverAppData) this.mObserverApps.valueAt(i4)).dump(printWriter);
                }
            } finally {
            }
        }
    }

    public AlarmManager getAlarmManager() {
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        }
        return this.mAlarmManager;
    }

    public AppUsageGroup getAppUsageGroup(int i, int i2) {
        AppUsageGroup appUsageGroup;
        synchronized (this.mLock) {
            appUsageGroup = (AppUsageGroup) getOrCreateObserverAppDataLocked(i).appUsageGroups.get(i2);
        }
        return appUsageGroup;
    }

    public AppUsageLimitGroup getAppUsageLimitGroup(int i, int i2) {
        AppUsageLimitGroup appUsageLimitGroup;
        synchronized (this.mLock) {
            appUsageLimitGroup = (AppUsageLimitGroup) getOrCreateObserverAppDataLocked(i).appUsageLimitGroups.get(i2);
        }
        return appUsageLimitGroup;
    }

    public long getAppUsageLimitObserverPerUidLimit() {
        return 1000L;
    }

    public long getAppUsageObserverPerUidLimit() {
        return 1000L;
    }

    public long getElapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public long getMinTimeLimit() {
        return 60000L;
    }

    public final ObserverAppData getOrCreateObserverAppDataLocked(int i) {
        ObserverAppData observerAppData = (ObserverAppData) this.mObserverApps.get(i);
        if (observerAppData != null) {
            return observerAppData;
        }
        ObserverAppData observerAppData2 = new ObserverAppData();
        observerAppData2.appUsageGroups = new SparseArray();
        observerAppData2.sessionUsageGroups = new SparseArray();
        observerAppData2.appUsageLimitGroups = new SparseArray();
        observerAppData2.uid = i;
        this.mObserverApps.put(i, observerAppData2);
        return observerAppData2;
    }

    public final UserData getOrCreateUserDataLocked(int i) {
        UserData userData = (UserData) this.mUsers.get(i);
        if (userData != null) {
            return userData;
        }
        UserData userData2 = new UserData(i);
        this.mUsers.put(i, userData2);
        return userData2;
    }

    public SessionUsageGroup getSessionUsageGroup(int i, int i2) {
        SessionUsageGroup sessionUsageGroup;
        synchronized (this.mLock) {
            sessionUsageGroup = (SessionUsageGroup) getOrCreateObserverAppDataLocked(i).sessionUsageGroups.get(i2);
        }
        return sessionUsageGroup;
    }

    public long getUsageSessionObserverPerUidLimit() {
        return 1000L;
    }

    public final void noteUsageStart(int i, String str, long j) {
        Integer num;
        synchronized (this.mLock) {
            try {
                UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i);
                int indexOfKey = orCreateUserDataLocked.currentlyActive.indexOfKey(str);
                if (indexOfKey >= 0 && (num = (Integer) orCreateUserDataLocked.currentlyActive.valueAt(indexOfKey)) != null) {
                    orCreateUserDataLocked.currentlyActive.setValueAt(indexOfKey, Integer.valueOf(num.intValue() + 1));
                    return;
                }
                long elapsedRealtime = getElapsedRealtime();
                orCreateUserDataLocked.currentlyActive.put(str, ONE);
                ArrayList arrayList = (ArrayList) orCreateUserDataLocked.observedMap.get(str);
                if (arrayList == null) {
                    return;
                }
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((UsageGroup) arrayList.get(i2)).noteUsageStart(elapsedRealtime - j, elapsedRealtime);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void noteUsageStop(int i, String str) {
        synchronized (this.mLock) {
            try {
                UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i);
                int indexOfKey = orCreateUserDataLocked.currentlyActive.indexOfKey(str);
                if (indexOfKey < 0) {
                    throw new IllegalArgumentException("Unable to stop usage for " + str + ", not in use");
                }
                if (!((Integer) orCreateUserDataLocked.currentlyActive.valueAt(indexOfKey)).equals(ONE)) {
                    orCreateUserDataLocked.currentlyActive.setValueAt(indexOfKey, Integer.valueOf(r0.intValue() - 1));
                    return;
                }
                orCreateUserDataLocked.currentlyActive.removeAt(indexOfKey);
                long elapsedRealtime = getElapsedRealtime();
                ArrayList arrayList = (ArrayList) orCreateUserDataLocked.observedMap.get(str);
                if (arrayList == null) {
                    return;
                }
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((UsageGroup) arrayList.get(i2)).noteUsageStop(elapsedRealtime);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
