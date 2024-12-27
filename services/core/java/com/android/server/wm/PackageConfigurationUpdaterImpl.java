package com.android.server.wm;

import android.os.Binder;
import android.os.LocaleList;
import android.util.ArraySet;
import android.util.Slog;

import java.util.HashMap;
import java.util.Optional;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageConfigurationUpdaterImpl {
    public final ActivityTaskManagerService mAtm;
    public int mGrammaticalGender;
    public LocaleList mLocales;
    public Integer mNightMode;
    public String mPackageName;
    public final Optional mPid;
    public int mUserId;

    public PackageConfigurationUpdaterImpl(
            int i, ActivityTaskManagerService activityTaskManagerService) {
        this.mPid = Optional.of(Integer.valueOf(i));
        this.mAtm = activityTaskManagerService;
    }

    public PackageConfigurationUpdaterImpl(
            int i, ActivityTaskManagerService activityTaskManagerService, String str) {
        this.mPackageName = str;
        this.mUserId = i;
        this.mAtm = activityTaskManagerService;
        this.mPid = Optional.empty();
    }

    public final boolean commit() {
        int packageUid;
        synchronized (this) {
            WindowManagerGlobalLock windowManagerGlobalLock = this.mAtm.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        if (this.mPid.isPresent()) {
                            WindowProcessController process =
                                    this.mAtm.mProcessMap.getProcess(
                                            ((Integer) this.mPid.get()).intValue());
                            if (process == null) {
                                Slog.w(
                                        "PackageConfigurationUpdaterImpl",
                                        "commit: Override application configuration failed: cannot"
                                            + " find pid "
                                                + this.mPid);
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                            packageUid = process.mUid;
                            this.mUserId = process.mUserId;
                            this.mPackageName = process.mInfo.packageName;
                        } else {
                            packageUid =
                                    this.mAtm
                                            .getPackageManagerInternalLocked()
                                            .getPackageUid(
                                                    this.mPackageName, 131072L, this.mUserId);
                            if (packageUid < 0) {
                                Slog.w(
                                        "PackageConfigurationUpdaterImpl",
                                        "commit: update of application configuration failed: userId"
                                            + " or packageName not valid "
                                                + this.mUserId);
                                WindowManagerService.resetPriorityAfterLockedSection();
                                return false;
                            }
                        }
                        updateConfig(packageUid, this.mPackageName);
                        boolean updateFromImpl =
                                this.mAtm.mPackageConfigPersister.updateFromImpl(
                                        this.mPackageName, this.mUserId, this);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return updateFromImpl;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
    }

    public final void setNightMode(int i) {
        synchronized (this) {
            this.mNightMode = Integer.valueOf(i);
        }
    }

    public final void updateConfig(int i, String str) {
        ActivityTaskManagerService activityTaskManagerService = this.mAtm;
        ArraySet arraySet =
                (ArraySet)
                        ((HashMap) activityTaskManagerService.mProcessMap.mUidMap)
                                .get(Integer.valueOf(i));
        if (arraySet == null || arraySet.isEmpty()) {
            return;
        }
        LocaleList combineLocalesIfOverlayExists =
                LocaleOverlayHelper.combineLocalesIfOverlayExists(
                        this.mLocales,
                        activityTaskManagerService.getGlobalConfiguration().getLocales());
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            WindowProcessController windowProcessController =
                    (WindowProcessController) arraySet.valueAt(size);
            if (windowProcessController.mInfo.packageName.equals(str)) {
                windowProcessController.applyAppSpecificConfig(
                        this.mNightMode,
                        combineLocalesIfOverlayExists,
                        Integer.valueOf(this.mGrammaticalGender));
            }
            Integer num = this.mNightMode;
            int i2 = this.mGrammaticalGender;
            for (int size2 = windowProcessController.mActivities.size() - 1; size2 >= 0; size2--) {
                ActivityRecord activityRecord =
                        (ActivityRecord) windowProcessController.mActivities.get(size2);
                if (str.equals(activityRecord.packageName)
                        && activityRecord.applyAppSpecificConfig(
                                num, combineLocalesIfOverlayExists, Integer.valueOf(i2))
                        && activityRecord.isVisibleRequested()) {
                    activityRecord.ensureActivityConfiguration(false);
                }
            }
        }
    }
}
