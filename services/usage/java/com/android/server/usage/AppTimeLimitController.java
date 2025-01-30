package com.android.server.usage;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.usage.UsageStatsManagerInternal;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class AppTimeLimitController {
    public static final Integer ONE = new Integer(1);
    public AlarmManager mAlarmManager;
    public final Context mContext;
    public final MyHandler mHandler;
    public TimeLimitCallbackListener mListener;
    public final Lock mLock = new Lock();
    public final SparseArray mUsers = new SparseArray();
    public final SparseArray mObserverApps = new SparseArray();

    public class Lock {
        public Lock() {
        }
    }

    public interface TimeLimitCallbackListener {
        void onLimitReached(int i, int i2, long j, long j2, PendingIntent pendingIntent);

        void onSessionEnd(int i, int i2, long j, PendingIntent pendingIntent);
    }

    public long getAppUsageLimitObserverPerUidLimit() {
        return 1000L;
    }

    public long getAppUsageObserverPerUidLimit() {
        return 1000L;
    }

    public long getMinTimeLimit() {
        return 60000L;
    }

    public long getUsageSessionObserverPerUidLimit() {
        return 1000L;
    }

    public class UserData {
        public final ArrayMap currentlyActive;
        public final ArrayMap observedMap;
        public int userId;

        public UserData(int i) {
            this.currentlyActive = new ArrayMap();
            this.observedMap = new ArrayMap();
            this.userId = i;
        }

        public boolean isActive(String[] strArr) {
            for (String str : strArr) {
                if (this.currentlyActive.containsKey(str)) {
                    return true;
                }
            }
            return false;
        }

        public void addUsageGroup(UsageGroup usageGroup) {
            int length = usageGroup.mObserved.length;
            for (int i = 0; i < length; i++) {
                ArrayList arrayList = (ArrayList) this.observedMap.get(usageGroup.mObserved[i]);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    this.observedMap.put(usageGroup.mObserved[i], arrayList);
                }
                arrayList.add(usageGroup);
            }
        }

        public void removeUsageGroup(UsageGroup usageGroup) {
            int length = usageGroup.mObserved.length;
            for (int i = 0; i < length; i++) {
                String str = usageGroup.mObserved[i];
                ArrayList arrayList = (ArrayList) this.observedMap.get(str);
                if (arrayList != null) {
                    arrayList.remove(usageGroup);
                    if (arrayList.isEmpty()) {
                        this.observedMap.remove(str);
                    }
                }
            }
        }

        public void dump(PrintWriter printWriter) {
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

    public class ObserverAppData {
        public SparseArray appUsageGroups;
        public SparseArray appUsageLimitGroups;
        public SparseArray sessionUsageGroups;
        public int uid;

        public ObserverAppData(int i) {
            this.appUsageGroups = new SparseArray();
            this.sessionUsageGroups = new SparseArray();
            this.appUsageLimitGroups = new SparseArray();
            this.uid = i;
        }

        public void removeAppUsageGroup(int i) {
            this.appUsageGroups.remove(i);
        }

        public void removeSessionUsageGroup(int i) {
            this.sessionUsageGroups.remove(i);
        }

        public void removeAppUsageLimitGroup(int i) {
            this.appUsageLimitGroups.remove(i);
        }

        public void dump(PrintWriter printWriter) {
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

    public abstract class UsageGroup {
        public int mActives;
        public long mLastKnownUsageTimeMs;
        public long mLastUsageEndTimeMs;
        public PendingIntent mLimitReachedCallback;
        public String[] mObserved;
        public WeakReference mObserverAppRef;
        public int mObserverId;
        public long mTimeLimitMs;
        public long mUsageTimeMs;
        public WeakReference mUserRef;

        public UsageGroup(UserData userData, ObserverAppData observerAppData, int i, String[] strArr, long j, PendingIntent pendingIntent) {
            this.mUserRef = new WeakReference(userData);
            this.mObserverAppRef = new WeakReference(observerAppData);
            this.mObserverId = i;
            this.mObserved = strArr;
            this.mTimeLimitMs = j;
            this.mLimitReachedCallback = pendingIntent;
        }

        public void remove() {
            UserData userData = (UserData) this.mUserRef.get();
            if (userData != null) {
                userData.removeUsageGroup(this);
            }
            this.mLimitReachedCallback = null;
        }

        public void noteUsageStart(long j) {
            noteUsageStart(j, j);
        }

        public void noteUsageStart(long j, long j2) {
            int i = this.mActives;
            int i2 = i + 1;
            this.mActives = i2;
            if (i == 0) {
                long j3 = this.mLastUsageEndTimeMs;
                if (j3 > j) {
                    j = j3;
                }
                this.mLastKnownUsageTimeMs = j;
                long j4 = ((this.mTimeLimitMs - this.mUsageTimeMs) - j2) + j;
                if (j4 > 0) {
                    Slog.d("AppTimeLimitController", "Posting time out for " + this.mObserverId + " for " + j4 + "ms ,mTimeLimitMs : " + this.mTimeLimitMs + " ,mUsageTimeMs : " + this.mUsageTimeMs + " ,currentTimeMs : " + j2 + " ,startTimeMs : " + j);
                    AppTimeLimitController.this.postCheckTimeoutLocked(this, j4);
                    return;
                }
                return;
            }
            String[] strArr = this.mObserved;
            if (i2 > strArr.length) {
                this.mActives = strArr.length;
                UserData userData = (UserData) this.mUserRef.get();
                if (userData == null) {
                    return;
                }
                Slog.e("AppTimeLimitController", "Too many noted usage starts! Observed entities: " + Arrays.toString(this.mObserved) + "   Active Entities: " + Arrays.toString(userData.currentlyActive.keySet().toArray()));
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
            long j4 = j2 + (j - this.mLastKnownUsageTimeMs);
            this.mUsageTimeMs = j4;
            this.mLastUsageEndTimeMs = j;
            if (z && j4 >= j3) {
                AppTimeLimitController.this.postInformLimitReachedListenerLocked(this);
            }
            Slog.d("AppTimeLimitController", "Posting time out for " + this.mObserverId + " for  ,mTimeLimitMs : " + this.mTimeLimitMs + " ,mUsageTimeMs : " + this.mUsageTimeMs + " ,mLastUsageEndTimeMs : " + this.mLastUsageEndTimeMs);
            AppTimeLimitController.this.cancelCheckTimeoutLocked(this);
        }

        public void checkTimeout(long j) {
            UserData userData = (UserData) this.mUserRef.get();
            if (userData == null) {
                return;
            }
            long j2 = this.mTimeLimitMs - this.mUsageTimeMs;
            if (j2 > 0 && userData.isActive(this.mObserved)) {
                long j3 = j - this.mLastKnownUsageTimeMs;
                if (j2 <= j3) {
                    this.mUsageTimeMs += j3;
                    this.mLastKnownUsageTimeMs = j;
                    AppTimeLimitController.this.postInformLimitReachedListenerLocked(this);
                    return;
                }
                AppTimeLimitController.this.postCheckTimeoutLocked(this, j2 - j3);
            }
        }

        public void onLimitReached() {
            UserData userData = (UserData) this.mUserRef.get();
            if (userData == null || AppTimeLimitController.this.mListener == null) {
                return;
            }
            AppTimeLimitController.this.mListener.onLimitReached(this.mObserverId, userData.userId, this.mTimeLimitMs, this.mUsageTimeMs, this.mLimitReachedCallback);
        }

        void dump(PrintWriter printWriter) {
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
    }

    public class AppUsageGroup extends UsageGroup {
        public AppUsageGroup(UserData userData, ObserverAppData observerAppData, int i, String[] strArr, long j, PendingIntent pendingIntent) {
            super(userData, observerAppData, i, strArr, j, pendingIntent);
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public void remove() {
            super.remove();
            ObserverAppData observerAppData = (ObserverAppData) this.mObserverAppRef.get();
            if (observerAppData != null) {
                observerAppData.removeAppUsageGroup(this.mObserverId);
            }
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public void onLimitReached() {
            super.onLimitReached();
            remove();
        }
    }

    public class SessionUsageGroup extends UsageGroup implements AlarmManager.OnAlarmListener {
        public long mNewSessionThresholdMs;
        public PendingIntent mSessionEndCallback;

        public SessionUsageGroup(UserData userData, ObserverAppData observerAppData, int i, String[] strArr, long j, PendingIntent pendingIntent, long j2, PendingIntent pendingIntent2) {
            super(userData, observerAppData, i, strArr, j, pendingIntent);
            this.mNewSessionThresholdMs = j2;
            this.mSessionEndCallback = pendingIntent2;
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public void remove() {
            super.remove();
            ObserverAppData observerAppData = (ObserverAppData) this.mObserverAppRef.get();
            if (observerAppData != null) {
                observerAppData.removeSessionUsageGroup(this.mObserverId);
            }
            this.mSessionEndCallback = null;
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public void noteUsageStart(long j, long j2) {
            if (this.mActives == 0) {
                if (j - this.mLastUsageEndTimeMs > this.mNewSessionThresholdMs) {
                    this.mUsageTimeMs = 0L;
                }
                AppTimeLimitController.this.getAlarmManager().cancel(this);
            }
            super.noteUsageStart(j, j2);
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public void noteUsageStop(long j) {
            super.noteUsageStop(j);
            if (this.mActives != 0 || this.mUsageTimeMs < this.mTimeLimitMs) {
                return;
            }
            AppTimeLimitController.this.getAlarmManager().setExact(3, this.mNewSessionThresholdMs + AppTimeLimitController.this.getElapsedRealtime(), "AppTimeLimitController", this, AppTimeLimitController.this.mHandler);
        }

        public void onSessionEnd() {
            UserData userData = (UserData) this.mUserRef.get();
            if (userData == null || AppTimeLimitController.this.mListener == null) {
                return;
            }
            AppTimeLimitController.this.mListener.onSessionEnd(this.mObserverId, userData.userId, this.mUsageTimeMs, this.mSessionEndCallback);
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public void onAlarm() {
            synchronized (AppTimeLimitController.this.mLock) {
                onSessionEnd();
            }
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public void dump(PrintWriter printWriter) {
            super.dump(printWriter);
            printWriter.print(" lastUsageEndTime=");
            printWriter.print(this.mLastUsageEndTimeMs);
            printWriter.print(" newSessionThreshold=");
            printWriter.print(this.mNewSessionThresholdMs);
        }
    }

    public class AppUsageLimitGroup extends UsageGroup {
        public AppUsageLimitGroup(UserData userData, ObserverAppData observerAppData, int i, String[] strArr, long j, long j2, PendingIntent pendingIntent) {
            super(userData, observerAppData, i, strArr, j, pendingIntent);
            this.mUsageTimeMs = j2;
        }

        @Override // com.android.server.usage.AppTimeLimitController.UsageGroup
        public void remove() {
            super.remove();
            ObserverAppData observerAppData = (ObserverAppData) this.mObserverAppRef.get();
            if (observerAppData != null) {
                observerAppData.removeAppUsageLimitGroup(this.mObserverId);
            }
        }

        public long getTotaUsageLimit() {
            return this.mTimeLimitMs;
        }

        public long getUsageRemaining() {
            long j;
            long j2;
            if (this.mActives > 0) {
                j = this.mTimeLimitMs - this.mUsageTimeMs;
                j2 = AppTimeLimitController.this.getElapsedRealtime() - this.mLastKnownUsageTimeMs;
            } else {
                j = this.mTimeLimitMs;
                j2 = this.mUsageTimeMs;
            }
            return j - j2;
        }
    }

    public class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                synchronized (AppTimeLimitController.this.mLock) {
                    ((UsageGroup) message.obj).checkTimeout(AppTimeLimitController.this.getElapsedRealtime());
                }
            } else {
                if (i == 2) {
                    synchronized (AppTimeLimitController.this.mLock) {
                        ((UsageGroup) message.obj).onLimitReached();
                    }
                    return;
                }
                super.handleMessage(message);
            }
        }
    }

    public AppTimeLimitController(Context context, TimeLimitCallbackListener timeLimitCallbackListener, Looper looper) {
        this.mContext = context;
        this.mHandler = new MyHandler(looper);
        this.mListener = timeLimitCallbackListener;
    }

    public AlarmManager getAlarmManager() {
        if (this.mAlarmManager == null) {
            this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(AlarmManager.class);
        }
        return this.mAlarmManager;
    }

    public long getElapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public AppUsageGroup getAppUsageGroup(int i, int i2) {
        AppUsageGroup appUsageGroup;
        synchronized (this.mLock) {
            appUsageGroup = (AppUsageGroup) getOrCreateObserverAppDataLocked(i).appUsageGroups.get(i2);
        }
        return appUsageGroup;
    }

    public SessionUsageGroup getSessionUsageGroup(int i, int i2) {
        SessionUsageGroup sessionUsageGroup;
        synchronized (this.mLock) {
            sessionUsageGroup = (SessionUsageGroup) getOrCreateObserverAppDataLocked(i).sessionUsageGroups.get(i2);
        }
        return sessionUsageGroup;
    }

    public AppUsageLimitGroup getAppUsageLimitGroup(int i, int i2) {
        AppUsageLimitGroup appUsageLimitGroup;
        synchronized (this.mLock) {
            appUsageLimitGroup = (AppUsageLimitGroup) getOrCreateObserverAppDataLocked(i).appUsageLimitGroups.get(i2);
        }
        return appUsageLimitGroup;
    }

    public UsageStatsManagerInternal.AppUsageLimitData getAppUsageLimit(String str, UserHandle userHandle) {
        synchronized (this.mLock) {
            UserData orCreateUserDataLocked = getOrCreateUserDataLocked(userHandle.getIdentifier());
            if (orCreateUserDataLocked == null) {
                return null;
            }
            ArrayList arrayList = (ArrayList) orCreateUserDataLocked.observedMap.get(str);
            if (arrayList != null && !arrayList.isEmpty()) {
                ArraySet arraySet = new ArraySet();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i) instanceof AppUsageLimitGroup) {
                        AppUsageLimitGroup appUsageLimitGroup = (AppUsageLimitGroup) arrayList.get(i);
                        int i2 = 0;
                        while (true) {
                            String[] strArr = appUsageLimitGroup.mObserved;
                            if (i2 >= strArr.length) {
                                break;
                            }
                            if (strArr[i2].equals(str)) {
                                arraySet.add(appUsageLimitGroup);
                                break;
                            }
                            i2++;
                        }
                    }
                }
                if (arraySet.isEmpty()) {
                    return null;
                }
                AppUsageLimitGroup appUsageLimitGroup2 = (AppUsageLimitGroup) arraySet.valueAt(0);
                for (int i3 = 1; i3 < arraySet.size(); i3++) {
                    AppUsageLimitGroup appUsageLimitGroup3 = (AppUsageLimitGroup) arraySet.valueAt(i3);
                    if (appUsageLimitGroup3.getUsageRemaining() < appUsageLimitGroup2.getUsageRemaining()) {
                        appUsageLimitGroup2 = appUsageLimitGroup3;
                    }
                }
                return new UsageStatsManagerInternal.AppUsageLimitData(appUsageLimitGroup2.getTotaUsageLimit(), appUsageLimitGroup2.getUsageRemaining());
            }
            return null;
        }
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

    public final ObserverAppData getOrCreateObserverAppDataLocked(int i) {
        ObserverAppData observerAppData = (ObserverAppData) this.mObserverApps.get(i);
        if (observerAppData != null) {
            return observerAppData;
        }
        ObserverAppData observerAppData2 = new ObserverAppData(i);
        this.mObserverApps.put(i, observerAppData2);
        return observerAppData2;
    }

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            this.mUsers.remove(i);
        }
    }

    public final void noteActiveLocked(UserData userData, UsageGroup usageGroup, long j) {
        int length = usageGroup.mObserved.length;
        for (int i = 0; i < length; i++) {
            if (userData.currentlyActive.containsKey(usageGroup.mObserved[i])) {
                usageGroup.noteUsageStart(j);
            }
        }
    }

    public void addAppUsageObserver(int i, int i2, String[] strArr, long j, PendingIntent pendingIntent, int i3) {
        if (j < getMinTimeLimit()) {
            throw new IllegalArgumentException("Time limit must be >= " + getMinTimeLimit());
        }
        synchronized (this.mLock) {
            UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i3);
            ObserverAppData orCreateObserverAppDataLocked = getOrCreateObserverAppDataLocked(i);
            AppUsageGroup appUsageGroup = (AppUsageGroup) orCreateObserverAppDataLocked.appUsageGroups.get(i2);
            if (appUsageGroup != null) {
                appUsageGroup.remove();
            }
            if (orCreateObserverAppDataLocked.appUsageGroups.size() >= getAppUsageObserverPerUidLimit()) {
                throw new IllegalStateException("Too many app usage observers added by uid " + i);
            }
            AppUsageGroup appUsageGroup2 = new AppUsageGroup(orCreateUserDataLocked, orCreateObserverAppDataLocked, i2, strArr, j, pendingIntent);
            orCreateObserverAppDataLocked.appUsageGroups.append(i2, appUsageGroup2);
            orCreateUserDataLocked.addUsageGroup(appUsageGroup2);
            noteActiveLocked(orCreateUserDataLocked, appUsageGroup2, getElapsedRealtime());
        }
    }

    public void removeAppUsageObserver(int i, int i2, int i3) {
        synchronized (this.mLock) {
            AppUsageGroup appUsageGroup = (AppUsageGroup) getOrCreateObserverAppDataLocked(i).appUsageGroups.get(i2);
            if (appUsageGroup != null) {
                appUsageGroup.remove();
            }
        }
    }

    public void addUsageSessionObserver(int i, int i2, String[] strArr, long j, long j2, PendingIntent pendingIntent, PendingIntent pendingIntent2, int i3) {
        if (j < getMinTimeLimit()) {
            throw new IllegalArgumentException("Time limit must be >= " + getMinTimeLimit());
        }
        synchronized (this.mLock) {
            UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i3);
            ObserverAppData orCreateObserverAppDataLocked = getOrCreateObserverAppDataLocked(i);
            SessionUsageGroup sessionUsageGroup = (SessionUsageGroup) orCreateObserverAppDataLocked.sessionUsageGroups.get(i2);
            if (sessionUsageGroup != null) {
                sessionUsageGroup.remove();
            }
            if (orCreateObserverAppDataLocked.sessionUsageGroups.size() >= getUsageSessionObserverPerUidLimit()) {
                throw new IllegalStateException("Too many app usage observers added by uid " + i);
            }
            SessionUsageGroup sessionUsageGroup2 = new SessionUsageGroup(orCreateUserDataLocked, orCreateObserverAppDataLocked, i2, strArr, j, pendingIntent, j2, pendingIntent2);
            orCreateObserverAppDataLocked.sessionUsageGroups.append(i2, sessionUsageGroup2);
            orCreateUserDataLocked.addUsageGroup(sessionUsageGroup2);
            noteActiveLocked(orCreateUserDataLocked, sessionUsageGroup2, getElapsedRealtime());
        }
    }

    public void removeUsageSessionObserver(int i, int i2, int i3) {
        synchronized (this.mLock) {
            SessionUsageGroup sessionUsageGroup = (SessionUsageGroup) getOrCreateObserverAppDataLocked(i).sessionUsageGroups.get(i2);
            if (sessionUsageGroup != null) {
                sessionUsageGroup.remove();
            }
        }
    }

    public void addAppUsageLimitObserver(int i, int i2, String[] strArr, long j, long j2, PendingIntent pendingIntent, int i3) {
        if (j < getMinTimeLimit()) {
            throw new IllegalArgumentException("Time limit must be >= " + getMinTimeLimit());
        }
        synchronized (this.mLock) {
            UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i3);
            ObserverAppData orCreateObserverAppDataLocked = getOrCreateObserverAppDataLocked(i);
            AppUsageLimitGroup appUsageLimitGroup = (AppUsageLimitGroup) orCreateObserverAppDataLocked.appUsageLimitGroups.get(i2);
            if (appUsageLimitGroup != null) {
                appUsageLimitGroup.remove();
            }
            if (orCreateObserverAppDataLocked.appUsageLimitGroups.size() >= getAppUsageLimitObserverPerUidLimit()) {
                throw new IllegalStateException("Too many app usage observers added by uid " + i);
            }
            AppUsageLimitGroup appUsageLimitGroup2 = new AppUsageLimitGroup(orCreateUserDataLocked, orCreateObserverAppDataLocked, i2, strArr, j, j2, j2 >= j ? null : pendingIntent);
            orCreateObserverAppDataLocked.appUsageLimitGroups.append(i2, appUsageLimitGroup2);
            orCreateUserDataLocked.addUsageGroup(appUsageLimitGroup2);
            noteActiveLocked(orCreateUserDataLocked, appUsageLimitGroup2, getElapsedRealtime());
        }
    }

    public void removeAppUsageLimitObserver(int i, int i2, int i3) {
        synchronized (this.mLock) {
            AppUsageLimitGroup appUsageLimitGroup = (AppUsageLimitGroup) getOrCreateObserverAppDataLocked(i).appUsageLimitGroups.get(i2);
            if (appUsageLimitGroup != null) {
                appUsageLimitGroup.remove();
            }
        }
    }

    public void noteUsageStart(String str, int i, long j) {
        Integer num;
        synchronized (this.mLock) {
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
        }
    }

    public void noteUsageStart(String str, int i) {
        noteUsageStart(str, i, 0L);
    }

    public void noteUsageStop(String str, int i) {
        synchronized (this.mLock) {
            UserData orCreateUserDataLocked = getOrCreateUserDataLocked(i);
            int indexOfKey = orCreateUserDataLocked.currentlyActive.indexOfKey(str);
            if (indexOfKey < 0) {
                throw new IllegalArgumentException("Unable to stop usage for " + str + ", not in use");
            }
            if (!((Integer) orCreateUserDataLocked.currentlyActive.valueAt(indexOfKey)).equals(ONE)) {
                orCreateUserDataLocked.currentlyActive.setValueAt(indexOfKey, Integer.valueOf(r2.intValue() - 1));
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
        }
    }

    public final void postInformLimitReachedListenerLocked(UsageGroup usageGroup) {
        MyHandler myHandler = this.mHandler;
        myHandler.sendMessage(myHandler.obtainMessage(2, usageGroup));
    }

    public final void postCheckTimeoutLocked(UsageGroup usageGroup, long j) {
        MyHandler myHandler = this.mHandler;
        myHandler.sendMessageDelayed(myHandler.obtainMessage(1, usageGroup), j);
    }

    public final void cancelCheckTimeoutLocked(UsageGroup usageGroup) {
        this.mHandler.removeMessages(1, usageGroup);
    }

    public void dump(String[] strArr, PrintWriter printWriter) {
        if (strArr != null) {
            for (String str : strArr) {
                if ("actives".equals(str)) {
                    synchronized (this.mLock) {
                        int size = this.mUsers.size();
                        for (int i = 0; i < size; i++) {
                            ArrayMap arrayMap = ((UserData) this.mUsers.valueAt(i)).currentlyActive;
                            int size2 = arrayMap.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                printWriter.println((String) arrayMap.keyAt(i2));
                            }
                        }
                    }
                    return;
                }
            }
        }
        synchronized (this.mLock) {
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
        }
    }
}
