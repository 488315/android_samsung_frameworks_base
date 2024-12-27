package com.android.server;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.UserManager;
import android.provider.Settings;

import com.android.server.am.ActivityManagerService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
import com.android.server.utils.TimingsTraceAndSlog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HsumBootUserInitializer {
    public final ActivityManagerService mAms;
    public final ContentResolver mContentResolver;
    public final AnonymousClass1 mDeviceProvisionedObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.android.server.HsumBootUserInitializer.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    HsumBootUserInitializer hsumBootUserInitializer = HsumBootUserInitializer.this;
                    hsumBootUserInitializer.getClass();
                    try {
                        if (Settings.Global.getInt(
                                        hsumBootUserInitializer.mContentResolver,
                                        "device_provisioned")
                                == 1) {
                            Slogf.i(
                                    "HsumBootUserInitializer",
                                    "Marking USER_SETUP_COMPLETE for system user");
                            Settings.Secure.putInt(
                                    HsumBootUserInitializer.this.mContentResolver,
                                    "user_setup_complete",
                                    1);
                            HsumBootUserInitializer hsumBootUserInitializer2 =
                                    HsumBootUserInitializer.this;
                            hsumBootUserInitializer2.mContentResolver.unregisterContentObserver(
                                    hsumBootUserInitializer2.mDeviceProvisionedObserver);
                        }
                    } catch (Exception e) {
                        Slogf.wtf(
                                "HsumBootUserInitializer",
                                "DEVICE_PROVISIONED setting not found.",
                                e);
                    }
                }
            };
    public final PackageManagerService mPms;
    public final boolean mShouldAlwaysHaveMainUser;
    public final UserManagerInternal mUmi;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.HsumBootUserInitializer$1] */
    public HsumBootUserInitializer(
            UserManagerInternal userManagerInternal,
            ActivityManagerService activityManagerService,
            PackageManagerService packageManagerService,
            ContentResolver contentResolver,
            boolean z) {
        this.mUmi = userManagerInternal;
        this.mAms = activityManagerService;
        this.mPms = packageManagerService;
        this.mContentResolver = contentResolver;
        this.mShouldAlwaysHaveMainUser = z;
    }

    public static HsumBootUserInitializer createInstance(
            ActivityManagerService activityManagerService,
            PackageManagerService packageManagerService,
            ContentResolver contentResolver,
            boolean z) {
        if (UserManager.isHeadlessSystemUserMode()) {
            return new HsumBootUserInitializer(
                    (UserManagerInternal) LocalServices.getService(UserManagerInternal.class),
                    activityManagerService,
                    packageManagerService,
                    contentResolver,
                    z);
        }
        return null;
    }

    public final void init(TimingsTraceAndSlog timingsTraceAndSlog) {
        Slogf.i("HsumBootUserInitializer", "init())");
        if (this.mShouldAlwaysHaveMainUser) {
            timingsTraceAndSlog.traceBegin("createMainUserIfNeeded");
            int mainUserId = this.mUmi.getMainUserId();
            if (mainUserId != -10000) {
                Slogf.d(
                        "HsumBootUserInitializer",
                        "Found existing MainUser, userId=%d",
                        Integer.valueOf(mainUserId));
            } else {
                Slogf.d("HsumBootUserInitializer", "Creating a new MainUser");
                try {
                    Slogf.i(
                            "HsumBootUserInitializer",
                            "Successfully created MainUser, userId=%d",
                            Integer.valueOf(
                                    this.mUmi.createUserEvenWhenDisallowed(
                                                    null,
                                                    "android.os.usertype.full.SECONDARY",
                                                    16386,
                                                    null,
                                                    null)
                                            .id));
                } catch (UserManager.CheckedUserOperationException e) {
                    Slogf.wtf(
                            "HsumBootUserInitializer",
                            "Initial bootable MainUser creation failed",
                            (Throwable) e);
                }
            }
            timingsTraceAndSlog.traceEnd();
        }
    }
}
