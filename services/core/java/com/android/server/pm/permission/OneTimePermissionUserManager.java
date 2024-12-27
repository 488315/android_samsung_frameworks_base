package com.android.server.pm.permission;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.AlarmManager;
import android.app.IActivityManager;
import android.app.IUidObserver;
import android.app.UidObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.RemoteException;
import android.permission.PermissionControllerManager;
import android.provider.DeviceConfig;
import android.util.Log;
import android.util.SparseArray;

import com.android.server.LocalServices;
import com.android.server.PermissionThread;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OneTimePermissionUserManager {
    public final AlarmManager mAlarmManager;
    public final Context mContext;
    public final Handler mHandler;
    public final PermissionControllerManager mPermissionControllerManager;
    public final Object mLock = new Object();
    public final AnonymousClass1 mUninstallListener =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.pm.permission.OneTimePermissionUserManager.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if ("android.intent.action.UID_REMOVED".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        PackageInactivityListener packageInactivityListener =
                                (PackageInactivityListener)
                                        OneTimePermissionUserManager.this.mListeners.get(intExtra);
                        if (packageInactivityListener != null) {
                            PackageInactivityListener.m784$$Nest$mcancel(packageInactivityListener);
                            OneTimePermissionUserManager.this.mListeners.remove(intExtra);
                        }
                    }
                }
            };
    public final SparseArray mListeners = new SparseArray();
    public final IActivityManager mIActivityManager = ActivityManager.getService();
    public final ActivityManagerInternal mActivityManagerInternal =
            (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageInactivityListener implements AlarmManager.OnAlarmListener {
        public final int mDeviceId;
        public boolean mIsAlarmSet;
        public boolean mIsFinished;
        public final AnonymousClass1 mObserver;
        public final String mPackageName;
        public long mRevokeAfterKilledDelay;
        public long mTimeout;
        public final int mUid;
        public long mTimerStart = -1;
        public final Object mInnerLock = new Object();
        public final Object mToken = new Object();

        /* renamed from: -$$Nest$mcancel, reason: not valid java name */
        public static void m784$$Nest$mcancel(PackageInactivityListener packageInactivityListener) {
            synchronized (packageInactivityListener.mInnerLock) {
                packageInactivityListener.mIsFinished = true;
                if (packageInactivityListener.mIsAlarmSet) {
                    OneTimePermissionUserManager.this.mAlarmManager.cancel(
                            packageInactivityListener);
                    packageInactivityListener.mIsAlarmSet = false;
                }
                try {
                    OneTimePermissionUserManager.this.mIActivityManager.unregisterUidObserver(
                            packageInactivityListener.mObserver);
                } catch (RemoteException e) {
                    Log.e("OneTimePermissionUserManager", "Unable to unregister uid observer.", e);
                }
            }
        }

        public PackageInactivityListener(int i, String str, int i2, long j, long j2) {
            IUidObserver iUidObserver =
                    new UidObserver() { // from class:
                                        // com.android.server.pm.permission.OneTimePermissionUserManager.PackageInactivityListener.1
                        public final void onUidGone(int i3, boolean z) {
                            PackageInactivityListener packageInactivityListener =
                                    PackageInactivityListener.this;
                            if (i3 == packageInactivityListener.mUid) {
                                packageInactivityListener.updateUidState(0);
                            }
                        }

                        public final void onUidStateChanged(int i3, int i4, long j3, int i5) {
                            PackageInactivityListener packageInactivityListener =
                                    PackageInactivityListener.this;
                            if (i3 == packageInactivityListener.mUid) {
                                if (i4 <= 4 || i4 == 20) {
                                    packageInactivityListener.updateUidState(2);
                                } else {
                                    packageInactivityListener.updateUidState(1);
                                }
                            }
                        }
                    };
            this.mObserver = iUidObserver;
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "Start tracking ", str, ". uid=", " timeout=");
            m.append(j);
            m.append(" killedDelay=");
            m.append(j2);
            Log.i("OneTimePermissionUserManager", m.toString());
            this.mUid = i;
            this.mPackageName = str;
            this.mDeviceId = i2;
            this.mTimeout = j;
            this.mRevokeAfterKilledDelay =
                    j2 == -1
                            ? DeviceConfig.getLong(
                                    "permissions",
                                    "one_time_permissions_killed_delay_millis",
                                    5000L)
                            : j2;
            try {
                OneTimePermissionUserManager.this.mIActivityManager.registerUidObserver(
                        iUidObserver, 3, 4, (String) null);
            } catch (RemoteException e) {
                Log.e("OneTimePermissionUserManager", "Couldn't check uid proc state", e);
                synchronized (this.mInnerLock) {
                    onPackageInactiveLocked();
                }
            }
            int uidProcessState =
                    OneTimePermissionUserManager.this.mActivityManagerInternal.getUidProcessState(
                            this.mUid);
            updateUidState(uidProcessState == 20 ? 0 : uidProcessState > 4 ? 1 : 2);
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            synchronized (this.mInnerLock) {
                try {
                    if (this.mIsAlarmSet) {
                        this.mIsAlarmSet = false;
                        onPackageInactiveLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onPackageInactiveLocked() {
            if (this.mIsFinished) {
                return;
            }
            this.mIsFinished = true;
            if (this.mIsAlarmSet) {
                OneTimePermissionUserManager.this.mAlarmManager.cancel(this);
                this.mIsAlarmSet = false;
            }
            OneTimePermissionUserManager.this.mHandler.post(
                    new OneTimePermissionUserManager$PackageInactivityListener$$ExternalSyntheticLambda0(
                            this, 0));
            try {
                OneTimePermissionUserManager.this.mIActivityManager.unregisterUidObserver(
                        this.mObserver);
            } catch (RemoteException e) {
                Log.e("OneTimePermissionUserManager", "Unable to unregister uid observer.", e);
            }
            synchronized (OneTimePermissionUserManager.this.mLock) {
                OneTimePermissionUserManager.this.mListeners.remove(this.mUid);
            }
        }

        public final void updateSessionParameters(long j, long j2) {
            synchronized (this.mInnerLock) {
                try {
                    this.mTimeout = Math.min(this.mTimeout, j);
                    long j3 = this.mRevokeAfterKilledDelay;
                    if (j2 == -1) {
                        j2 =
                                DeviceConfig.getLong(
                                        "permissions",
                                        "one_time_permissions_killed_delay_millis",
                                        5000L);
                    }
                    this.mRevokeAfterKilledDelay = Math.min(j3, j2);
                    Log.v(
                            "OneTimePermissionUserManager",
                            "Updated params for "
                                    + this.mPackageName
                                    + ", device ID "
                                    + this.mDeviceId
                                    + ". timeout="
                                    + this.mTimeout
                                    + " killedDelay="
                                    + this.mRevokeAfterKilledDelay);
                    int uidProcessState =
                            OneTimePermissionUserManager.this.mActivityManagerInternal
                                    .getUidProcessState(this.mUid);
                    updateUidState(uidProcessState == 20 ? 0 : uidProcessState > 4 ? 1 : 2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateUidState(int i) {
            Log.v(
                    "OneTimePermissionUserManager",
                    "Updating state for "
                            + this.mPackageName
                            + " ("
                            + this.mUid
                            + "). device ID="
                            + this.mDeviceId
                            + ", state="
                            + i);
            synchronized (this.mInnerLock) {
                try {
                    OneTimePermissionUserManager.this.mHandler.removeCallbacksAndMessages(
                            this.mToken);
                    if (i == 0) {
                        long j = this.mRevokeAfterKilledDelay;
                        if (j == 0) {
                            onPackageInactiveLocked();
                            return;
                        } else {
                            OneTimePermissionUserManager.this.mHandler.postDelayed(
                                    new OneTimePermissionUserManager$PackageInactivityListener$$ExternalSyntheticLambda0(
                                            this, 1),
                                    this.mToken,
                                    j);
                            return;
                        }
                    }
                    if (i == 1) {
                        if (this.mTimerStart == -1) {
                            long currentTimeMillis = System.currentTimeMillis();
                            this.mTimerStart = currentTimeMillis;
                            if (!this.mIsAlarmSet) {
                                long j2 = currentTimeMillis + this.mTimeout;
                                if (j2 > System.currentTimeMillis()) {
                                    OneTimePermissionUserManager oneTimePermissionUserManager =
                                            OneTimePermissionUserManager.this;
                                    oneTimePermissionUserManager.mAlarmManager.setExact(
                                            0,
                                            j2,
                                            "OneTimePermissionUserManager",
                                            this,
                                            oneTimePermissionUserManager.mHandler);
                                    this.mIsAlarmSet = true;
                                } else {
                                    this.mIsAlarmSet = true;
                                    onAlarm();
                                }
                            }
                        }
                    } else if (i == 2) {
                        this.mTimerStart = -1L;
                        if (this.mIsAlarmSet) {
                            OneTimePermissionUserManager.this.mAlarmManager.cancel(this);
                            this.mIsAlarmSet = false;
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.pm.permission.OneTimePermissionUserManager$1] */
    public OneTimePermissionUserManager(Context context) {
        this.mContext = context;
        this.mAlarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        this.mPermissionControllerManager =
                new PermissionControllerManager(context, PermissionThread.getHandler());
        this.mHandler = context.getMainThreadHandler();
    }

    public final void startPackageOneTimeSession(int i, long j, long j2, String str) {
        try {
            int packageUid = this.mContext.getPackageManager().getPackageUid(str, 0);
            synchronized (this.mLock) {
                try {
                    PackageInactivityListener packageInactivityListener =
                            (PackageInactivityListener) this.mListeners.get(packageUid);
                    if (packageInactivityListener != null) {
                        packageInactivityListener.updateSessionParameters(j, j2);
                    } else {
                        this.mListeners.put(
                                packageUid,
                                new PackageInactivityListener(packageUid, str, i, j, j2));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(
                    "OneTimePermissionUserManager",
                    SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0
                            .m(i, "Unknown package name ", str, ", device ID "),
                    e);
        }
    }

    public final void stopPackageOneTimeSession(String str) {
        try {
            int packageUid = this.mContext.getPackageManager().getPackageUid(str, 0);
            synchronized (this.mLock) {
                try {
                    PackageInactivityListener packageInactivityListener =
                            (PackageInactivityListener) this.mListeners.get(packageUid);
                    if (packageInactivityListener != null) {
                        this.mListeners.remove(packageUid);
                        PackageInactivityListener.m784$$Nest$mcancel(packageInactivityListener);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("OneTimePermissionUserManager", "Unknown package name " + str, e);
        }
    }
}
