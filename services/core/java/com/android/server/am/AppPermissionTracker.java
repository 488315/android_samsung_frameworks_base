package com.android.server.am;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseArray;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.am.BaseAppStateTracker;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppPermissionTracker extends BaseAppStateTracker implements PackageManager.OnPermissionsChangedListener {
    public final SparseArray mAppOpsCallbacks;
    public final MyHandler mHandler;
    public volatile boolean mLockedBootCompleted;
    public final SparseArray mUidGrantedPermissionsInMonitor;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppPermissionPolicy extends BaseAppStatePolicy {
        public static final String[] DEFAULT_BG_PERMISSIONS_IN_MONITOR = {"android.permission.ACCESS_FINE_LOCATION", "android:fine_location", "android.permission.CAMERA", "android:camera", "android.permission.RECORD_AUDIO", "android:record_audio"};
        public volatile Pair[] mBgPermissionsInMonitor;

        public static Pair[] parsePermissionConfig(String[] strArr) {
            Pair[] pairArr = new Pair[strArr.length / 2];
            int i = 0;
            int i2 = 0;
            while (i < strArr.length) {
                try {
                    int i3 = i + 1;
                    pairArr[i2] = Pair.create(TextUtils.isEmpty(strArr[i]) ? null : strArr[i], Integer.valueOf(TextUtils.isEmpty(strArr[i3]) ? -1 : AppOpsManager.strOpToOp(strArr[i3])));
                } catch (Exception unused) {
                }
                i += 2;
                i2++;
            }
            return pairArr;
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.println("APP PERMISSION TRACKER POLICY SETTINGS:");
            String str2 = "  " + str;
            super.dump(printWriter, str2);
            printWriter.print(str2);
            printWriter.print("bg_permission_in_monitor");
            printWriter.print('=');
            printWriter.print('[');
            for (int i = 0; i < this.mBgPermissionsInMonitor.length; i++) {
                if (i > 0) {
                    printWriter.print(',');
                }
                Pair pair = this.mBgPermissionsInMonitor[i];
                Object obj = pair.first;
                if (obj != null) {
                    printWriter.print((String) obj);
                }
                printWriter.print(',');
                if (((Integer) pair.second).intValue() != -1) {
                    printWriter.print(AppOpsManager.opToPublicName(((Integer) pair.second).intValue()));
                }
            }
            printWriter.println(']');
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onPropertiesChanged(String str) {
            if (str.equals("bg_permission_in_monitor")) {
                updateBgPermissionsInMonitor();
            } else if (this.mKeyTrackerEnabled.equals(str)) {
                updateTrackerEnabled();
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onSystemReady() {
            updateTrackerEnabled();
            updateBgPermissionsInMonitor();
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onTrackerEnabled(boolean z) {
            ((AppPermissionTracker) this.mTracker).onPermissionTrackerEnabled(z);
        }

        public final void updateBgPermissionsInMonitor() {
            String string = DeviceConfig.getString("activity_manager", "bg_permission_in_monitor", (String) null);
            Pair[] parsePermissionConfig = parsePermissionConfig(string != null ? string.split(",") : DEFAULT_BG_PERMISSIONS_IN_MONITOR);
            if (Arrays.equals(this.mBgPermissionsInMonitor, parsePermissionConfig)) {
                return;
            }
            this.mBgPermissionsInMonitor = parsePermissionConfig;
            if (this.mTrackerEnabled) {
                onTrackerEnabled(false);
                onTrackerEnabled(true);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyAppOpsCallback extends IAppOpsCallback.Stub {
        public MyAppOpsCallback() {
        }

        public final void opChanged(int i, int i2, String str, String str2) {
            AppPermissionTracker.this.mHandler.obtainMessage(3, i, i2, str).sendToTarget();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyHandler extends Handler {
        public AppPermissionTracker mTracker;

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            int i2 = 0;
            if (i == 0) {
                AppPermissionTracker appPermissionTracker = this.mTracker;
                appPermissionTracker.getClass();
                ArrayList arrayList = new ArrayList();
                for (Pair pair : ((AppPermissionPolicy) appPermissionTracker.mInjector.mAppStatePolicy).mBgPermissionsInMonitor) {
                    if (((Integer) pair.second).intValue() != -1) {
                        arrayList.add((Integer) pair.second);
                    }
                }
                Integer[] numArr = (Integer[]) arrayList.toArray(new Integer[arrayList.size()]);
                synchronized (appPermissionTracker.mAppOpsCallbacks) {
                    appPermissionTracker.stopWatchingMode();
                    IAppOpsService iAppOpsService = appPermissionTracker.mInjector.mIAppOpsService;
                    try {
                        int length = numArr.length;
                        while (i2 < length) {
                            int intValue = numArr[i2].intValue();
                            MyAppOpsCallback myAppOpsCallback = appPermissionTracker.new MyAppOpsCallback();
                            appPermissionTracker.mAppOpsCallbacks.put(intValue, myAppOpsCallback);
                            iAppOpsService.startWatchingModeWithFlags(intValue, (String) null, 1, myAppOpsCallback);
                            i2++;
                        }
                    } catch (RemoteException unused) {
                    }
                }
                AppPermissionTracker.m176$$Nest$mhandlePermissionsInit(this.mTracker);
                return;
            }
            if (i == 1) {
                AppPermissionTracker appPermissionTracker2 = this.mTracker;
                synchronized (appPermissionTracker2.mLock) {
                    try {
                        SparseArray sparseArray = appPermissionTracker2.mUidGrantedPermissionsInMonitor;
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        int size = sparseArray.size();
                        for (int i3 = 0; i3 < size; i3++) {
                            int keyAt = sparseArray.keyAt(i3);
                            if (((ArraySet) sparseArray.valueAt(i3)).size() > 0) {
                                appPermissionTracker2.notifyListenersOnStateChange(keyAt, 16, elapsedRealtime, "", false);
                            }
                        }
                        sparseArray.clear();
                    } finally {
                    }
                }
                this.mTracker.stopWatchingMode();
                return;
            }
            if (i == 2) {
                AppPermissionTracker appPermissionTracker3 = this.mTracker;
                int i4 = message.arg1;
                Pair[] pairArr = ((AppPermissionPolicy) appPermissionTracker3.mInjector.mAppStatePolicy).mBgPermissionsInMonitor;
                if (pairArr == null || pairArr.length <= 0) {
                    return;
                }
                appPermissionTracker3.mInjector.getClass();
                UidGrantedPermissionState[] uidGrantedPermissionStateArr = new UidGrantedPermissionState[pairArr.length];
                while (i2 < pairArr.length) {
                    Pair pair2 = pairArr[i2];
                    uidGrantedPermissionStateArr[i2] = new UidGrantedPermissionState(appPermissionTracker3, i4, (String) pair2.first, ((Integer) pair2.second).intValue());
                    i2++;
                }
                synchronized (appPermissionTracker3.mLock) {
                    appPermissionTracker3.handlePermissionsChangedLocked(i4, uidGrantedPermissionStateArr);
                }
                return;
            }
            if (i != 3) {
                return;
            }
            AppPermissionTracker appPermissionTracker4 = this.mTracker;
            int i5 = message.arg1;
            int i6 = message.arg2;
            Pair[] pairArr2 = ((AppPermissionPolicy) appPermissionTracker4.mInjector.mAppStatePolicy).mBgPermissionsInMonitor;
            if (pairArr2 == null || pairArr2.length <= 0) {
                return;
            }
            while (i2 < pairArr2.length) {
                Pair pair3 = pairArr2[i2];
                if (((Integer) pair3.second).intValue() == i5) {
                    UidGrantedPermissionState uidGrantedPermissionState = new UidGrantedPermissionState(appPermissionTracker4, i6, (String) pair3.first, i5);
                    synchronized (appPermissionTracker4.mLock) {
                        appPermissionTracker4.handlePermissionsChangedLocked(i6, new UidGrantedPermissionState[]{uidGrantedPermissionState});
                    }
                    return;
                }
                i2++;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidGrantedPermissionState {
        public final int mAppOp;
        public final boolean mAppOpAllowed;
        public final String mPermission;
        public final boolean mPermissionGranted;
        public final int mUid;

        public UidGrantedPermissionState(AppPermissionTracker appPermissionTracker, int i, String str, int i2) {
            this.mUid = i;
            this.mPermission = str;
            this.mAppOp = i2;
            if (TextUtils.isEmpty(str)) {
                this.mPermissionGranted = true;
            } else {
                this.mPermissionGranted = appPermissionTracker.mInjector.mContext.checkPermission(str, -1, i) == 0;
            }
            if (i2 == -1) {
                this.mAppOpAllowed = true;
                return;
            }
            String[] packagesForUid = appPermissionTracker.mInjector.mPackageManager.getPackagesForUid(i);
            if (packagesForUid != null) {
                IAppOpsService iAppOpsService = appPermissionTracker.mInjector.mIAppOpsService;
                for (String str2 : packagesForUid) {
                    if (iAppOpsService.checkOperation(i2, i, str2) == 0) {
                        this.mAppOpAllowed = true;
                        return;
                    }
                    continue;
                }
            }
            this.mAppOpAllowed = false;
        }

        public final boolean equals(Object obj) {
            if (obj == null || !(obj instanceof UidGrantedPermissionState)) {
                return false;
            }
            UidGrantedPermissionState uidGrantedPermissionState = (UidGrantedPermissionState) obj;
            return this.mUid == uidGrantedPermissionState.mUid && this.mAppOp == uidGrantedPermissionState.mAppOp && Objects.equals(this.mPermission, uidGrantedPermissionState.mPermission);
        }

        public final int hashCode() {
            int hashCode = (Integer.hashCode(this.mAppOp) + (Integer.hashCode(this.mUid) * 31)) * 31;
            String str = this.mPermission;
            return hashCode + (str == null ? 0 : str.hashCode());
        }

        public final String toString() {
            String str = "UidGrantedPermissionState{" + System.identityHashCode(this) + " " + UserHandle.formatUid(this.mUid) + ": ";
            String str2 = this.mPermission;
            boolean isEmpty = TextUtils.isEmpty(str2);
            if (!isEmpty) {
                str = str + str2 + "=" + this.mPermissionGranted;
            }
            int i = this.mAppOp;
            if (i != -1) {
                if (!isEmpty) {
                    str = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, ",");
                }
                StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
                m.append(AppOpsManager.opToPublicName(i));
                m.append("=");
                m.append(this.mAppOpAllowed);
                str = m.toString();
            }
            return ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "}");
        }
    }

    /* renamed from: -$$Nest$mhandlePermissionsInit, reason: not valid java name */
    public static void m176$$Nest$mhandlePermissionsInit(AppPermissionTracker appPermissionTracker) {
        ApplicationInfo applicationInfo;
        int i;
        int i2;
        int i3;
        int[] userIds = appPermissionTracker.mInjector.mUserManagerInternal.getUserIds();
        BaseAppStateTracker.Injector injector = appPermissionTracker.mInjector;
        PackageManagerInternal packageManagerInternal = injector.mPackageManagerInternal;
        Pair[] pairArr = ((AppPermissionPolicy) injector.mAppStatePolicy).mBgPermissionsInMonitor;
        SparseArray sparseArray = appPermissionTracker.mUidGrantedPermissionsInMonitor;
        for (int i4 : userIds) {
            List installedApplications = packageManagerInternal.getInstalledApplications(i4, 1000, 0L);
            if (installedApplications != null) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                int size = installedApplications.size();
                int i5 = 0;
                while (i5 < size) {
                    ApplicationInfo applicationInfo2 = (ApplicationInfo) installedApplications.get(i5);
                    int length = pairArr.length;
                    int i6 = 0;
                    while (i6 < length) {
                        Pair pair = pairArr[i6];
                        int i7 = i6;
                        UidGrantedPermissionState uidGrantedPermissionState = new UidGrantedPermissionState(appPermissionTracker, applicationInfo2.uid, (String) pair.first, ((Integer) pair.second).intValue());
                        if (uidGrantedPermissionState.mPermissionGranted && uidGrantedPermissionState.mAppOpAllowed) {
                            synchronized (appPermissionTracker.mLock) {
                                try {
                                    ArraySet arraySet = (ArraySet) sparseArray.get(applicationInfo2.uid);
                                    if (arraySet == null) {
                                        ArraySet arraySet2 = new ArraySet();
                                        sparseArray.put(applicationInfo2.uid, arraySet2);
                                        applicationInfo = applicationInfo2;
                                        i = length;
                                        i2 = i5;
                                        i3 = size;
                                        appPermissionTracker.notifyListenersOnStateChange(applicationInfo2.uid, 16, elapsedRealtime, "", true);
                                        arraySet = arraySet2;
                                        uidGrantedPermissionState = uidGrantedPermissionState;
                                    } else {
                                        applicationInfo = applicationInfo2;
                                        i = length;
                                        i2 = i5;
                                        i3 = size;
                                    }
                                    arraySet.add(uidGrantedPermissionState);
                                } finally {
                                }
                            }
                        } else {
                            applicationInfo = applicationInfo2;
                            i = length;
                            i2 = i5;
                            i3 = size;
                        }
                        i6 = i7 + 1;
                        size = i3;
                        applicationInfo2 = applicationInfo;
                        length = i;
                        i5 = i2;
                    }
                    i5++;
                }
            }
        }
    }

    public AppPermissionTracker(Context context, AppRestrictionController appRestrictionController) {
        super(context, appRestrictionController);
        this.mAppOpsCallbacks = new SparseArray();
        this.mUidGrantedPermissionsInMonitor = new SparseArray();
        this.mLockedBootCompleted = false;
        MyHandler myHandler = new MyHandler(this.mBgHandler.getLooper());
        myHandler.mTracker = this;
        this.mHandler = myHandler;
        BaseAppStateTracker.Injector injector = this.mInjector;
        AppPermissionPolicy appPermissionPolicy = new AppPermissionPolicy(injector, this, "bg_permission_monitor_enabled", true);
        appPermissionPolicy.mBgPermissionsInMonitor = AppPermissionPolicy.parsePermissionConfig(AppPermissionPolicy.DEFAULT_BG_PERMISSIONS_IN_MONITOR);
        injector.mAppStatePolicy = appPermissionPolicy;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void dump(PrintWriter printWriter, String str) {
        Pair[] pairArr;
        String str2;
        String str3;
        Pair[] pairArr2;
        String str4;
        String str5;
        printWriter.print(str);
        printWriter.println("APP PERMISSIONS TRACKER:");
        Pair[] pairArr3 = ((AppPermissionPolicy) this.mInjector.mAppStatePolicy).mBgPermissionsInMonitor;
        String m = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("  ", str);
        String m2 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("  ", m);
        int length = pairArr3.length;
        int i = 0;
        while (i < length) {
            Pair pair = pairArr3[i];
            printWriter.print(m);
            boolean isEmpty = TextUtils.isEmpty((CharSequence) pair.first);
            if (!isEmpty) {
                printWriter.print((String) pair.first);
            }
            if (((Integer) pair.second).intValue() != -1) {
                if (!isEmpty) {
                    printWriter.print('+');
                }
                printWriter.print(AppOpsManager.opToPublicName(((Integer) pair.second).intValue()));
            }
            printWriter.println(':');
            synchronized (this.mLock) {
                try {
                    SparseArray sparseArray = this.mUidGrantedPermissionsInMonitor;
                    printWriter.print(m2);
                    printWriter.print('[');
                    int size = sparseArray.size();
                    int i2 = 0;
                    boolean z = false;
                    while (i2 < size) {
                        ArraySet arraySet = (ArraySet) sparseArray.valueAt(i2);
                        int size2 = arraySet.size() - 1;
                        while (true) {
                            if (size2 < 0) {
                                pairArr2 = pairArr3;
                                str4 = m;
                                str5 = m2;
                                break;
                            }
                            pairArr2 = pairArr3;
                            UidGrantedPermissionState uidGrantedPermissionState = (UidGrantedPermissionState) arraySet.valueAt(size2);
                            str4 = m;
                            str5 = m2;
                            if (uidGrantedPermissionState.mAppOp == ((Integer) pair.second).intValue() && TextUtils.equals(uidGrantedPermissionState.mPermission, (CharSequence) pair.first)) {
                                if (z) {
                                    printWriter.print(',');
                                }
                                printWriter.print(UserHandle.formatUid(uidGrantedPermissionState.mUid));
                                z = true;
                            } else {
                                size2--;
                                m = str4;
                                pairArr3 = pairArr2;
                                m2 = str5;
                            }
                        }
                        i2++;
                        m = str4;
                        pairArr3 = pairArr2;
                        m2 = str5;
                    }
                    pairArr = pairArr3;
                    str2 = m;
                    str3 = m2;
                    printWriter.println(']');
                } catch (Throwable th) {
                    throw th;
                }
            }
            i++;
            m = str2;
            pairArr3 = pairArr;
            m2 = str3;
        }
        this.mInjector.mAppStatePolicy.dump(printWriter, "  " + str);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final int getType() {
        return 5;
    }

    public final void handlePermissionsChangedLocked(int i, UidGrantedPermissionState[] uidGrantedPermissionStateArr) {
        int indexOfKey = this.mUidGrantedPermissionsInMonitor.indexOfKey(i);
        ArraySet arraySet = indexOfKey >= 0 ? (ArraySet) this.mUidGrantedPermissionsInMonitor.valueAt(indexOfKey) : null;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        for (int i2 = 0; i2 < uidGrantedPermissionStateArr.length; i2++) {
            UidGrantedPermissionState uidGrantedPermissionState = uidGrantedPermissionStateArr[i2];
            boolean z = true;
            boolean z2 = uidGrantedPermissionState.mPermissionGranted && uidGrantedPermissionState.mAppOpAllowed;
            if (z2) {
                if (arraySet == null) {
                    arraySet = new ArraySet();
                    this.mUidGrantedPermissionsInMonitor.put(i, arraySet);
                } else {
                    z = false;
                }
                arraySet.add(uidGrantedPermissionStateArr[i2]);
            } else if (arraySet == null || arraySet.isEmpty() || !arraySet.remove(uidGrantedPermissionStateArr[i2]) || !arraySet.isEmpty()) {
                z = false;
            } else {
                this.mUidGrantedPermissionsInMonitor.removeAt(indexOfKey);
            }
            if (z) {
                notifyListenersOnStateChange(i, 16, elapsedRealtime, "", z2);
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onLockedBootCompleted() {
        this.mLockedBootCompleted = true;
        onPermissionTrackerEnabled(((AppPermissionPolicy) this.mInjector.mAppStatePolicy).mTrackerEnabled);
    }

    public final void onPermissionTrackerEnabled(boolean z) {
        if (this.mLockedBootCompleted) {
            PermissionManager permissionManager = this.mInjector.mPermissionManager;
            if (z) {
                permissionManager.addOnPermissionsChangeListener(this);
                this.mHandler.obtainMessage(0).sendToTarget();
            } else {
                permissionManager.removeOnPermissionsChangeListener(this);
                this.mHandler.obtainMessage(1).sendToTarget();
            }
        }
    }

    public final void onPermissionsChanged(int i) {
        this.mHandler.obtainMessage(2, i, 0).sendToTarget();
    }

    public final void stopWatchingMode() {
        synchronized (this.mAppOpsCallbacks) {
            IAppOpsService iAppOpsService = this.mInjector.mIAppOpsService;
            for (int size = this.mAppOpsCallbacks.size() - 1; size >= 0; size--) {
                try {
                    iAppOpsService.stopWatchingMode((IAppOpsCallback) this.mAppOpsCallbacks.valueAt(size));
                } catch (RemoteException unused) {
                }
            }
            this.mAppOpsCallbacks.clear();
        }
    }
}
