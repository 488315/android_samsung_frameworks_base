package com.android.server.power;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.wm.WindowManagerService;
import com.android.server.power.AbuseWakeLockDetector;
import com.android.server.power.PowerManagerService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class AbuseWakeLockDetector {
    public static final String[] SEC_APP_PREFIX = {"com.sec.", "com.samsung."};
    public final ActivityManager mAm;
    public final Callback mCallback;
    public final Context mContext;
    public boolean mIsAbuseWakeLockFound;
    public final Object mLock;
    public final PackageManager mPm;
    public final WindowManagerService mWms;

    public interface Callback {
        void onAbuseWakeLockAdded(List list);

        void onAbuseWakeLockRemoved();
    }

    public AbuseWakeLockDetector(Object obj, Context context, Callback callback, WindowManagerService windowManagerService) {
        this.mContext = context;
        this.mLock = obj;
        this.mPm = context.getPackageManager();
        this.mAm = (ActivityManager) context.getSystemService("activity");
        this.mWms = windowManagerService;
        this.mCallback = callback;
    }

    public void handleAbuseWakelockWhenUserActivityChanged(ArrayList arrayList) {
        synchronized (this.mLock) {
            if (!this.mIsAbuseWakeLockFound && !arrayList.isEmpty()) {
                List list = (List) arrayList.stream().filter(new Predicate() { // from class: com.android.server.power.AbuseWakeLockDetector$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean isScreenLock;
                        isScreenLock = AbuseWakeLockDetector.isScreenLock((PowerManagerService.WakeLock) obj);
                        return isScreenLock;
                    }
                }).map(new Function() { // from class: com.android.server.power.AbuseWakeLockDetector$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        AbuseWakeLockDetector.WakeLockInfoPair lambda$handleAbuseWakelockWhenUserActivityChanged$0;
                        lambda$handleAbuseWakelockWhenUserActivityChanged$0 = AbuseWakeLockDetector.lambda$handleAbuseWakelockWhenUserActivityChanged$0((PowerManagerService.WakeLock) obj);
                        return lambda$handleAbuseWakelockWhenUserActivityChanged$0;
                    }
                }).collect(Collectors.toList());
                final ArrayList visibleWinSurfacePkgList = this.mWms.getVisibleWinSurfacePkgList();
                if (visibleWinSurfacePkgList == null || visibleWinSurfacePkgList.isEmpty()) {
                    return;
                }
                List list2 = (List) list.stream().filter(new Predicate() { // from class: com.android.server.power.AbuseWakeLockDetector$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$handleAbuseWakelockWhenUserActivityChanged$1;
                        lambda$handleAbuseWakelockWhenUserActivityChanged$1 = AbuseWakeLockDetector.this.lambda$handleAbuseWakelockWhenUserActivityChanged$1(visibleWinSurfacePkgList, (AbuseWakeLockDetector.WakeLockInfoPair) obj);
                        return lambda$handleAbuseWakelockWhenUserActivityChanged$1;
                    }
                }).map(new Function() { // from class: com.android.server.power.AbuseWakeLockDetector$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        String str;
                        str = ((AbuseWakeLockDetector.WakeLockInfoPair) obj).packageName;
                        return str;
                    }
                }).distinct().collect(Collectors.toList());
                synchronized (this.mLock) {
                    boolean z = !list2.isEmpty();
                    this.mIsAbuseWakeLockFound = z;
                    if (z) {
                        this.mCallback.onAbuseWakeLockAdded(list2);
                    }
                }
            }
        }
    }

    public static /* synthetic */ WakeLockInfoPair lambda$handleAbuseWakelockWhenUserActivityChanged$0(PowerManagerService.WakeLock wakeLock) {
        return new WakeLockInfoPair(wakeLock.mOwnerUid, wakeLock.mPackageName);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$handleAbuseWakelockWhenUserActivityChanged$1(ArrayList arrayList, WakeLockInfoPair wakeLockInfoPair) {
        return checkAppIsRunningBackground(wakeLockInfoPair.uid, wakeLockInfoPair.packageName, arrayList);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x006b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean checkAppIsRunningBackground(int i, String str, ArrayList arrayList) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean isApp = UserHandle.isApp(i);
        try {
            z2 = (this.mPm.getPackageInfo(str, 0).applicationInfo.flags & 1) != 0;
            try {
                z3 = isSecApp(str);
                try {
                    z4 = this.mAm.getPackageImportance(str) > 100;
                    try {
                        z = this.mPm.checkPermission("android.permission.DEVICE_POWER", str) == 0;
                    } catch (PackageManager.NameNotFoundException unused) {
                        z = false;
                    } catch (Exception unused2) {
                        z = false;
                    }
                } catch (PackageManager.NameNotFoundException unused3) {
                    z = false;
                    z4 = false;
                } catch (Exception unused4) {
                    z = false;
                    z4 = false;
                }
            } catch (PackageManager.NameNotFoundException unused5) {
                z = false;
                z3 = false;
                z4 = z3;
                Slog.m74e("AbuseWakeLockDetector", "Process abuse wakelock; Failed to find " + str);
                z5 = true;
                return (!z4 || !isApp || z2 || z3 || z5 || z) ? false : true;
            } catch (Exception unused6) {
                z = false;
                z3 = false;
                z4 = z3;
                z5 = true;
                if (!z4) {
                }
            }
        } catch (PackageManager.NameNotFoundException unused7) {
            z = false;
            z2 = false;
            z3 = false;
        } catch (Exception unused8) {
            z = false;
            z2 = false;
            z3 = false;
        }
        try {
            z5 = arrayList.contains(str);
        } catch (PackageManager.NameNotFoundException unused9) {
            Slog.m74e("AbuseWakeLockDetector", "Process abuse wakelock; Failed to find " + str);
            z5 = true;
            if (!z4) {
            }
        } catch (Exception unused10) {
            z5 = true;
            if (!z4) {
            }
        }
        return (!z4 || !isApp || z2 || z3 || z5 || z) ? false : true;
    }

    public void onUserActivity() {
        if (this.mIsAbuseWakeLockFound) {
            clearAbuseWakeLockLocked();
        }
    }

    public final void clearAbuseWakeLockLocked() {
        this.mIsAbuseWakeLockFound = false;
        this.mCallback.onAbuseWakeLockRemoved();
    }

    public final boolean isSecApp(String str) {
        for (String str2 : SEC_APP_PREFIX) {
            if (str.startsWith(str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isScreenLock(PowerManagerService.WakeLock wakeLock) {
        int i = wakeLock.mFlags & GnssNative.GNSS_AIDING_TYPE_ALL;
        return i == 6 || i == 10 || i == 26;
    }

    public class WakeLockInfoPair {
        public final String packageName;
        public final int uid;

        public WakeLockInfoPair(int i, String str) {
            this.uid = i;
            this.packageName = str;
        }
    }
}
