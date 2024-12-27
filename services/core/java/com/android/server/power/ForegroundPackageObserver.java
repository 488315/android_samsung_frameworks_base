package com.android.server.power;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.IActivityManager;
import android.app.IProcessObserver;

import com.android.server.LocalServices;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public final class ForegroundPackageObserver extends Observable {
    public final ActivityManager mActivityManager;
    public final IActivityManager mActivityManagerNative;
    public String mForegroundPackageName = "";
    public boolean mEnabled = false;
    public final AnonymousClass1 mProcessObserver =
            new IProcessObserver
                    .Stub() { // from class: com.android.server.power.ForegroundPackageObserver.1
                public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
                    synchronized (this) {
                        try {
                            if (z) {
                                ForegroundPackageObserver.this.mForegroundPidSet.add(
                                        Integer.valueOf(i));
                            } else {
                                ForegroundPackageObserver.this.mForegroundPidSet.remove(
                                        Integer.valueOf(i));
                            }
                            if (!ForegroundPackageObserver.this.mForegroundPidSet.isEmpty()) {
                                ForegroundPackageObserver foregroundPackageObserver =
                                        ForegroundPackageObserver.this;
                                String packageNameByPid =
                                        foregroundPackageObserver.mActivityManagerInternal
                                                .getPackageNameByPid(
                                                        ((Integer)
                                                                        foregroundPackageObserver
                                                                                .mForegroundPidSet
                                                                                .stream()
                                                                                .findFirst()
                                                                                .get())
                                                                .intValue());
                                if (packageNameByPid != null
                                        && !packageNameByPid.equals(
                                                ForegroundPackageObserver.this
                                                        .mForegroundPackageName)) {
                                    ForegroundPackageObserver foregroundPackageObserver2 =
                                            ForegroundPackageObserver.this;
                                    foregroundPackageObserver2.mForegroundPackageName =
                                            packageNameByPid;
                                    foregroundPackageObserver2.setChanged();
                                    ForegroundPackageObserver foregroundPackageObserver3 =
                                            ForegroundPackageObserver.this;
                                    foregroundPackageObserver3.notifyObservers(
                                            foregroundPackageObserver3.mForegroundPackageName);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                public final void onForegroundServicesChanged(int i, int i2, int i3) {}

                public final void onProcessDied(int i, int i2) {
                    onForegroundActivitiesChanged(i, i2, false);
                }

                public final void onProcessStarted(
                        int i, int i2, int i3, String str, String str2) {}
            };
    public final LinkedHashSet mForegroundPidSet = new LinkedHashSet();
    public final ActivityManagerInternal mActivityManagerInternal =
            (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);

    public ForegroundPackageObserver(
            ActivityManager activityManager, IActivityManager iActivityManager) {
        this.mActivityManager = activityManager;
        this.mActivityManagerNative = iActivityManager;
    }

    @Override // java.util.Observable
    public final synchronized void addObserver(Observer observer) {
        String str;
        try {
            Slog.d(
                    "ForegroundPackageObserver",
                    "addObserver: ".concat(observer.getClass().getName()));
            super.addObserver(observer);
            if (countObservers() > 0) {
                setEnabled(true);
            }
            if (this.mForegroundPackageName.isEmpty()) {
                List<ActivityManager.RunningTaskInfo> runningTasks =
                        this.mActivityManager.getRunningTasks(1);
                str =
                        runningTasks.isEmpty()
                                ? ""
                                : ((ActivityManager.RunningTaskInfo) runningTasks.getFirst())
                                        .topActivity.getPackageName();
            } else {
                str = this.mForegroundPackageName;
            }
            observer.update(this, str);
        } catch (Throwable th) {
            throw th;
        }
    }

    @Override // java.util.Observable
    public final synchronized void deleteObserver(Observer observer) {
        Slog.d(
                "ForegroundPackageObserver",
                "deleteObserver: ".concat(observer.getClass().getName()));
        super.deleteObserver(observer);
        if (countObservers() == 0) {
            setEnabled(false);
        }
    }

    public final void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            try {
                if (z) {
                    Slog.d("ForegroundPackageObserver", "register process observer");
                    this.mActivityManagerNative.registerProcessObserver(this.mProcessObserver);
                } else {
                    Slog.d("ForegroundPackageObserver", "unregister process observer");
                    this.mActivityManagerNative.unregisterProcessObserver(this.mProcessObserver);
                    this.mForegroundPidSet.clear();
                    this.mForegroundPackageName = "";
                }
            } catch (Exception e) {
                Slog.e(
                        "ForegroundPackageObserver",
                        "Failed to register/unregister process observer",
                        e);
            }
        }
    }
}
