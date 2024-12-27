package com.android.server.power;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;

import com.android.server.wm.WindowManagerService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AbuseWakeLockDetector {
    public static final String[] SEC_APP_PREFIX = {"com.sec.", "com.samsung."};
    public final ActivityManager mAm;
    public final PowerManagerService.AnonymousClass1 mCallback;
    public boolean mIsAbuseWakeLockFound;
    public final Object mLock;
    public final PackageManager mPm;
    public final WindowManagerService mWms;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WakeLockInfoPair {
        public final String packageName;
        public final int uid;

        public WakeLockInfoPair(int i, String str) {
            this.uid = i;
            this.packageName = str;
        }
    }

    public AbuseWakeLockDetector(
            Object obj,
            Context context,
            PowerManagerService.AnonymousClass1 anonymousClass1,
            WindowManagerService windowManagerService) {
        this.mLock = obj;
        this.mPm = context.getPackageManager();
        this.mAm = (ActivityManager) context.getSystemService("activity");
        this.mWms = windowManagerService;
        this.mCallback = anonymousClass1;
    }

    public final void handleAbuseWakelockWhenUserActivityChanged(ArrayList arrayList) {
        synchronized (this.mLock) {
            if (!this.mIsAbuseWakeLockFound && !arrayList.isEmpty()) {
                final int i = 0;
                List list =
                        (List)
                                arrayList.stream()
                                        .filter(
                                                new AbuseWakeLockDetector$$ExternalSyntheticLambda0())
                                        .map(
                                                new Function() { // from class:
                                                                 // com.android.server.power.AbuseWakeLockDetector$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        switch (i) {
                                                            case 0:
                                                                PowerManagerService.WakeLock
                                                                        wakeLock =
                                                                                (PowerManagerService
                                                                                                .WakeLock)
                                                                                        obj;
                                                                return new AbuseWakeLockDetector
                                                                        .WakeLockInfoPair(
                                                                        wakeLock.mOwnerUid,
                                                                        wakeLock.mPackageName);
                                                            default:
                                                                return ((AbuseWakeLockDetector
                                                                                        .WakeLockInfoPair)
                                                                                obj)
                                                                        .packageName;
                                                        }
                                                    }
                                                })
                                        .collect(Collectors.toList());
                final ArrayList visibleWinSurfacePkgList = this.mWms.getVisibleWinSurfacePkgList();
                if (visibleWinSurfacePkgList == null || visibleWinSurfacePkgList.isEmpty()) {
                    return;
                }
                final int i2 = 1;
                List list2 =
                        (List)
                                list.stream()
                                        .filter(
                                                new Predicate() { // from class:
                                                    // com.android.server.power.AbuseWakeLockDetector$$ExternalSyntheticLambda2
                                                    /* JADX WARN: Removed duplicated region for block: B:28:0x0088 A[ADDED_TO_REGION] */
                                                    @Override // java.util.function.Predicate
                                                    /*
                                                        Code decompiled incorrectly, please refer to instructions dump.
                                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                                    */
                                                    public final boolean test(java.lang.Object r9) {
                                                        /*
                                                            r8 = this;
                                                            com.android.server.power.AbuseWakeLockDetector r0 = com.android.server.power.AbuseWakeLockDetector.this
                                                            java.util.ArrayList r8 = r2
                                                            com.android.server.power.AbuseWakeLockDetector$WakeLockInfoPair r9 = (com.android.server.power.AbuseWakeLockDetector.WakeLockInfoPair) r9
                                                            r0.getClass()
                                                            int r1 = r9.uid
                                                            java.lang.String r9 = r9.packageName
                                                            boolean r1 = android.os.UserHandle.isApp(r1)
                                                            r2 = 1
                                                            r3 = 0
                                                            android.content.pm.PackageManager r4 = r0.mPm     // Catch: java.lang.Exception -> L68 android.content.pm.PackageManager.NameNotFoundException -> L6c
                                                            android.content.pm.PackageInfo r4 = r4.getPackageInfo(r9, r3)     // Catch: java.lang.Exception -> L68 android.content.pm.PackageManager.NameNotFoundException -> L6c
                                                            android.content.pm.ApplicationInfo r4 = r4.applicationInfo     // Catch: java.lang.Exception -> L68 android.content.pm.PackageManager.NameNotFoundException -> L6c
                                                            int r4 = r4.flags     // Catch: java.lang.Exception -> L68 android.content.pm.PackageManager.NameNotFoundException -> L6c
                                                            r4 = r4 & r2
                                                            if (r4 == 0) goto L22
                                                            r4 = r2
                                                            goto L23
                                                        L22:
                                                            r4 = r3
                                                        L23:
                                                            java.lang.String[] r5 = com.android.server.power.AbuseWakeLockDetector.SEC_APP_PREFIX     // Catch: java.lang.Exception -> L60 android.content.pm.PackageManager.NameNotFoundException -> L64
                                                            r6 = r3
                                                        L26:
                                                            r7 = 2
                                                            if (r6 >= r7) goto L36
                                                            r7 = r5[r6]     // Catch: java.lang.Exception -> L60 android.content.pm.PackageManager.NameNotFoundException -> L64
                                                            boolean r7 = r9.startsWith(r7)     // Catch: java.lang.Exception -> L60 android.content.pm.PackageManager.NameNotFoundException -> L64
                                                            if (r7 == 0) goto L33
                                                            r5 = r2
                                                            goto L37
                                                        L33:
                                                            int r6 = r6 + 1
                                                            goto L26
                                                        L36:
                                                            r5 = r3
                                                        L37:
                                                            android.app.ActivityManager r6 = r0.mAm     // Catch: java.lang.Exception -> L5a android.content.pm.PackageManager.NameNotFoundException -> L5d
                                                            int r6 = r6.getPackageImportance(r9)     // Catch: java.lang.Exception -> L5a android.content.pm.PackageManager.NameNotFoundException -> L5d
                                                            r7 = 100
                                                            if (r6 <= r7) goto L43
                                                            r6 = r2
                                                            goto L44
                                                        L43:
                                                            r6 = r3
                                                        L44:
                                                            android.content.pm.PackageManager r0 = r0.mPm     // Catch: java.lang.Exception -> L56 android.content.pm.PackageManager.NameNotFoundException -> L58
                                                            java.lang.String r7 = "android.permission.DEVICE_POWER"
                                                            int r0 = r0.checkPermission(r7, r9)     // Catch: java.lang.Exception -> L56 android.content.pm.PackageManager.NameNotFoundException -> L58
                                                            if (r0 != 0) goto L50
                                                            r0 = r2
                                                            goto L51
                                                        L50:
                                                            r0 = r3
                                                        L51:
                                                            boolean r8 = r8.contains(r9)     // Catch: java.lang.Exception -> L70 android.content.pm.PackageManager.NameNotFoundException -> L72
                                                            goto L86
                                                        L56:
                                                            r0 = r3
                                                            goto L70
                                                        L58:
                                                            r0 = r3
                                                            goto L72
                                                        L5a:
                                                            r0 = r3
                                                            r6 = r0
                                                            goto L70
                                                        L5d:
                                                            r0 = r3
                                                            r6 = r0
                                                            goto L72
                                                        L60:
                                                            r0 = r3
                                                            r5 = r0
                                                        L62:
                                                            r6 = r5
                                                            goto L70
                                                        L64:
                                                            r0 = r3
                                                            r5 = r0
                                                        L66:
                                                            r6 = r5
                                                            goto L72
                                                        L68:
                                                            r0 = r3
                                                            r4 = r0
                                                            r5 = r4
                                                            goto L62
                                                        L6c:
                                                            r0 = r3
                                                            r4 = r0
                                                            r5 = r4
                                                            goto L66
                                                        L70:
                                                            r8 = r2
                                                            goto L86
                                                        L72:
                                                            java.lang.StringBuilder r8 = new java.lang.StringBuilder
                                                            java.lang.String r7 = "Process abuse wakelock; Failed to find "
                                                            r8.<init>(r7)
                                                            r8.append(r9)
                                                            java.lang.String r8 = r8.toString()
                                                            java.lang.String r9 = "AbuseWakeLockDetector"
                                                            com.android.server.power.Slog.e(r9, r8)
                                                            goto L70
                                                        L86:
                                                            if (r6 == 0) goto L93
                                                            if (r1 == 0) goto L93
                                                            if (r4 != 0) goto L93
                                                            if (r5 != 0) goto L93
                                                            if (r8 != 0) goto L93
                                                            if (r0 != 0) goto L93
                                                            goto L94
                                                        L93:
                                                            r2 = r3
                                                        L94:
                                                            return r2
                                                        */
                                                        throw new UnsupportedOperationException(
                                                                "Method not decompiled:"
                                                                    + " com.android.server.power.AbuseWakeLockDetector$$ExternalSyntheticLambda2.test(java.lang.Object):boolean");
                                                    }
                                                })
                                        .map(
                                                new Function() { // from class:
                                                    // com.android.server.power.AbuseWakeLockDetector$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Function
                                                    public final Object apply(Object obj) {
                                                        switch (i2) {
                                                            case 0:
                                                                PowerManagerService.WakeLock
                                                                        wakeLock =
                                                                                (PowerManagerService
                                                                                                .WakeLock)
                                                                                        obj;
                                                                return new AbuseWakeLockDetector
                                                                        .WakeLockInfoPair(
                                                                        wakeLock.mOwnerUid,
                                                                        wakeLock.mPackageName);
                                                            default:
                                                                return ((AbuseWakeLockDetector
                                                                                        .WakeLockInfoPair)
                                                                                obj)
                                                                        .packageName;
                                                        }
                                                    }
                                                })
                                        .distinct()
                                        .collect(Collectors.toList());
                synchronized (this.mLock) {
                    try {
                        boolean z = !list2.isEmpty();
                        this.mIsAbuseWakeLockFound = z;
                        if (z) {
                            this.mCallback.onAbuseWakeLockAdded(list2);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public final void onUserActivity() {
        if (this.mIsAbuseWakeLockFound) {
            this.mIsAbuseWakeLockFound = false;
            PowerManagerService.AnonymousClass1 anonymousClass1 = this.mCallback;
            anonymousClass1.getClass();
            Slog.d("PowerManagerService", "clear abuse wakelock");
            PowerManagerService powerManagerService = PowerManagerService.this;
            powerManagerService.mWakeLocks.stream()
                    .filter(new PowerManagerService$1$$ExternalSyntheticLambda0())
                    .forEach(
                            new PowerManagerService$$ExternalSyntheticLambda10(1, anonymousClass1));
            powerManagerService.mDirty |= 1;
        }
    }
}
