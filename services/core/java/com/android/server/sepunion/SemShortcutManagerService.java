package com.android.server.sepunion;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.UserInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.util.Slog;

import com.android.internal.content.PackageMonitor;
import com.android.internal.util.Preconditions;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.wm.ActivityTaskManagerInternal;

import com.samsung.android.app.ISemExecuteManager;
import com.samsung.android.app.SemExecutableInfo;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemShortcutManagerService extends ISemExecuteManager.Stub
        implements AbsSemSystemService {
    public static final Object mLock = new Object();
    public final Context mContext;
    public final ActivityTaskManagerInternal mLocalActivityTaskManager;
    public final AnonymousClass2 mScanHandler;
    public final AnonymousClass1 mShortcutChangeListener;
    public final ShortcutServiceInternal mShortcutServiceInternal;
    public final UserManager mUm;
    public final HashMap mSemExecutableInfos = new HashMap();
    public final HashMap mShortcutChangedCallbackMap = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MyPackageMonitor extends PackageMonitor {
        public MyPackageMonitor() {}

        public final void onPackageModified(String str) {
            Log.i("SemExecuteManagerService", "onPackageModified");
            if (hasMessages(998)) {
                removeMessages(998);
            }
            sendEmptyMessageDelayed(998, 1000L);
        }

        public final void onSomePackagesChanged() {
            Log.i("SemExecuteManagerService", "onSomePackagesChanged");
            if (hasMessages(998)) {
                removeMessages(998);
            }
            sendEmptyMessageDelayed(998, 1000L);
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.sepunion.SemShortcutManagerService$2] */
    public SemShortcutManagerService(Context context) {
        ShortcutServiceInternal.ShortcutChangeListener shortcutChangeListener =
                new ShortcutServiceInternal
                        .ShortcutChangeListener() { // from class:
                                                    // com.android.server.sepunion.SemShortcutManagerService.1
                    public final void onShortcutChanged(String str, int i) {
                        synchronized (SemShortcutManagerService.mLock) {
                            Log.i("SemExecuteManagerService", "onShortcutChanged: " + str + i);
                            ArrayList arrayList =
                                    (ArrayList)
                                            SemShortcutManagerService.this
                                                    .mShortcutChangedCallbackMap.get(
                                                    Integer.valueOf(i));
                            if (arrayList != null) {
                                int size = arrayList.size();
                                Log.i(
                                        "SemExecuteManagerService",
                                        "onShortcutChanged: size=" + size);
                                for (int i2 = 0; i2 < size; i2++) {
                                    SemShortcutManagerService.this.getClass();
                                    Intent intent = new Intent();
                                    intent.putExtra(
                                            "com.samsung.android.shortcut.PACKAGE_NAME", str);
                                    intent.putExtra("com.samsung.android.shortcut.USER_ID", i);
                                    PendingIntent pendingIntent = (PendingIntent) arrayList.get(i2);
                                    Log.i(
                                            "SemExecuteManagerService",
                                            "onShortcutChanged: send callback "
                                                    + pendingIntent.getCreatorPackage()
                                                    + pendingIntent.getCreatorUid());
                                    try {
                                        pendingIntent.send(
                                                SemShortcutManagerService.this.mContext, 0, intent);
                                    } catch (PendingIntent.CanceledException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                };
        this.mScanHandler =
                new Handler(
                        Looper
                                .getMainLooper()) { // from class:
                                                    // com.android.server.sepunion.SemShortcutManagerService.2
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        if (message.what == 998) {
                            Log.i(
                                    "SemExecuteManagerService",
                                    "handleMessage: MSG_START_SCAN received. Start scanning.");
                            new Thread() { // from class:
                                           // com.android.server.sepunion.SemShortcutManagerService.2.1
                                @Override // java.lang.Thread, java.lang.Runnable
                                public final void run() {
                                    Process.setThreadPriority(10);
                                    SemShortcutManagerService semShortcutManagerService =
                                            SemShortcutManagerService.this;
                                    Object obj = SemShortcutManagerService.mLock;
                                    semShortcutManagerService.updateSemExecutableInfo();
                                }
                            }.start();
                        } else {
                            Log.e(
                                    "SemExecuteManagerService",
                                    "handleMessage: default case msg.what = " + message.what);
                        }
                    }
                };
        this.mContext = context;
        new MyPackageMonitor().register(context, (Looper) null, new UserHandle(0), true);
        this.mUm = (UserManager) context.getSystemService("user");
        this.mLocalActivityTaskManager =
                (ActivityTaskManagerInternal)
                        Preconditions.checkNotNull(
                                (ActivityTaskManagerInternal)
                                        LocalServices.getService(
                                                ActivityTaskManagerInternal.class));
        ShortcutServiceInternal shortcutServiceInternal =
                (ShortcutServiceInternal)
                        Preconditions.checkNotNull(
                                (ShortcutServiceInternal)
                                        LocalServices.getService(ShortcutServiceInternal.class));
        this.mShortcutServiceInternal = shortcutServiceInternal;
        shortcutServiceInternal.addListener(shortcutChangeListener);
        Log.i(
                "SemExecuteManagerService",
                "----- SemShortcutManagerService: start updateSemExecutableInfo() on "
                        + System.currentTimeMillis());
        updateSemExecutableInfo();
        Log.i(
                "SemExecuteManagerService",
                "----- SemShortcutManagerService: end updateSemExecutableInfo() on "
                        + System.currentTimeMillis());
    }

    public final boolean canAccessProfile(int i, String str, String str2) {
        int i2;
        int userId = UserHandle.getUserId(injectBinderCallingUid());
        if (i == userId) {
            return true;
        }
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            UserInfo userInfo = this.mUm.getUserInfo(userId);
            if (!userInfo.isManagedProfile()) {
                UserInfo userInfo2 = this.mUm.getUserInfo(i);
                if (userInfo2 != null
                        && (i2 = userInfo2.profileGroupId) != -10000
                        && i2 == userInfo.profileGroupId) {
                    return true;
                }
                throw new SecurityException(str2 + " for unrelated profile " + i);
            }
            Slog.w(
                    "SemExecuteManagerService",
                    str2 + " by " + str + " for another profile " + i + " from " + userId);
            injectRestoreCallingIdentity(injectClearCallingIdentity);
            return false;
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.DUMP", "SemExecuteManagerService");
        printWriter.println(
                "\n"
                    + "##### SEM SHORTCUT MANAGER SERVICE #####\n"
                    + "##### (dumpsys sepunion execute) #####\n");
    }

    public final void enforeCallingPermission(String str) {
        int callingUid = Binder.getCallingUid();
        if (this.mContext.getPackageManager().checkSignatures(1000, callingUid) == 0) {
            return;
        }
        Log.i(
                "SemExecuteManagerService",
                AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                        callingUid,
                        "Permission denied: ",
                        str,
                        " uid=",
                        ", you need system uid of to be signed with the system certificate."));
        throw new SecurityException(
                AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                        callingUid,
                        "Permission denied: ",
                        str,
                        " uid=",
                        ", you need system uid of to be signed with the system certificate."));
    }

    public final ApplicationInfo getApplicationInfo(
            String str, String str2, int i, UserHandle userHandle) throws RemoteException {
        if (!canAccessProfile(userHandle.getIdentifier(), str, "Cannot check package")
                || !isUserEnabled(userHandle.getIdentifier())) {
            return null;
        }
        int injectBinderCallingUid = injectBinderCallingUid();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class))
                    .getApplicationInfo(
                            injectBinderCallingUid, userHandle.getIdentifier(), i, str2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final SemExecutableInfo getExecutableInfo(String str) {
        SemExecutableInfo semExecutableInfo;
        synchronized (this) {
            semExecutableInfo = (SemExecutableInfo) this.mSemExecutableInfos.get(str);
        }
        return semExecutableInfo;
    }

    public final List getExecutableInfos() {
        ArrayList arrayList;
        synchronized (this) {
            arrayList = new ArrayList(this.mSemExecutableInfos.values());
        }
        return arrayList;
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    public final ParcelFileDescriptor getShortcutIconFd(
            String str, String str2, String str3, String str4, int i) {
        enforeCallingPermission("getShortcutIconFd");
        if (canAccessProfile(i, str, "Cannot access shortcuts") && isUserEnabled(i)) {
            return this.mShortcutServiceInternal.getShortcutIconFd(
                    UserHandle.getUserId(injectBinderCallingUid()), str2, str3, str4, i);
        }
        return null;
    }

    public final ParceledListSlice getShortcuts(
            String str,
            String str2,
            long j,
            String str3,
            List list,
            ComponentName componentName,
            int i,
            UserHandle userHandle) {
        Log.i("SemExecuteManagerService", "getShortcuts: " + str);
        enforeCallingPermission("getShortcuts");
        if (!canAccessProfile(userHandle.getIdentifier(), str, "Cannot get shortcuts")
                || !isUserEnabled(userHandle.getIdentifier())) {
            return new ParceledListSlice(Collections.EMPTY_LIST);
        }
        if (list == null || str3 != null) {
            return new ParceledListSlice(
                    this.mShortcutServiceInternal.getShortcuts(
                            UserHandle.getUserId(injectBinderCallingUid()),
                            str2,
                            j,
                            str3,
                            list,
                            (List) null,
                            componentName,
                            i,
                            userHandle.getIdentifier(),
                            Binder.getCallingPid(),
                            Binder.getCallingUid()));
        }
        throw new IllegalArgumentException(
                "To query by shortcut ID, package name must also be set");
    }

    public final boolean hasShortcutHostPermission(String str) {
        enforeCallingPermission("hasShortcutHostPermission");
        return true;
    }

    public int injectBinderCallingUid() {
        return ISemExecuteManager.Stub.getCallingUid();
    }

    public long injectClearCallingIdentity() {
        return Binder.clearCallingIdentity();
    }

    public void injectRestoreCallingIdentity(long j) {
        Binder.restoreCallingIdentity(j);
    }

    public final boolean isUserEnabled(int i) {
        boolean z;
        long injectClearCallingIdentity = injectClearCallingIdentity();
        try {
            UserInfo userInfo = this.mUm.getUserInfo(i);
            if (userInfo != null) {
                if (userInfo.isEnabled()) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            injectRestoreCallingIdentity(injectClearCallingIdentity);
        }
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            new Thread() { // from class: com.android.server.sepunion.SemShortcutManagerService.3
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    Process.setThreadPriority(10);
                    SemShortcutManagerService semShortcutManagerService =
                            SemShortcutManagerService.this;
                    Object obj = SemShortcutManagerService.mLock;
                    semShortcutManagerService.updateSemExecutableInfo();
                }
            }.start();
        }
    }

    public final void onCleanupUser(int i) {
        updateSemExecutableInfo();
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {}

    public final void onDestroy() {}

    public final void onStart() {}

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}

    public final void registerChangedCallback(
            String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
        synchronized (mLock) {
            try {
                ArrayList arrayList =
                        (ArrayList)
                                this.mShortcutChangedCallbackMap.get(
                                        Integer.valueOf(userHandle.getIdentifier()));
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(pendingIntent);
                this.mShortcutChangedCallbackMap.put(
                        Integer.valueOf(userHandle.getIdentifier()), arrayList);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean startShortcut(
            String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i) {
        enforeCallingPermission("startShortcut");
        if (!canAccessProfile(i, str, "Cannot start activity")) {
            return false;
        }
        if (!isUserEnabled(i)) {
            throw new IllegalStateException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "Cannot start a shortcut for disabled profile "));
        }
        if (!this.mShortcutServiceInternal.isPinnedByCaller(
                UserHandle.getUserId(injectBinderCallingUid()), str2, str3, str4, i)) {
            enforeCallingPermission("startShortcut");
        }
        Intent[] createShortcutIntents =
                this.mShortcutServiceInternal.createShortcutIntents(
                        UserHandle.getUserId(injectBinderCallingUid()),
                        str2,
                        str3,
                        str4,
                        i,
                        Binder.getCallingPid(),
                        Binder.getCallingUid());
        if (createShortcutIntents == null || createShortcutIntents.length == 0) {
            return false;
        }
        createShortcutIntents[0].addFlags(268435456);
        createShortcutIntents[0].setSourceBounds(rect);
        try {
            int startActivitiesAsPackage =
                    this.mLocalActivityTaskManager.startActivitiesAsPackage(
                            str3, null, i, createShortcutIntents, bundle);
            if (ActivityManager.isStartResultSuccessful(startActivitiesAsPackage)) {
                return true;
            }
            Log.e(
                    "SemExecuteManagerService",
                    "Couldn't start activity, code=" + startActivitiesAsPackage);
            return false;
        } catch (SecurityException e) {
            Slog.d("SemExecuteManagerService", "SecurityException while launching intent", e);
            return false;
        }
    }

    public final void unRegisterChangedCallback(
            String str, PendingIntent pendingIntent, UserHandle userHandle) throws RemoteException {
        synchronized (mLock) {
            try {
                ArrayList arrayList =
                        (ArrayList)
                                this.mShortcutChangedCallbackMap.get(
                                        Integer.valueOf(userHandle.getIdentifier()));
                if (arrayList != null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        if (((PendingIntent) it.next()).equals(pendingIntent)) {
                            it.remove();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final synchronized void updateSemExecutableInfo() {
        synchronized (this.mSemExecutableInfos) {
            try {
                List<SemExecutableInfo> scanExecutableInfos =
                        SemExecutableInfo.scanExecutableInfos(this.mContext);
                boolean z = this.mSemExecutableInfos.size() != scanExecutableInfos.size();
                for (SemExecutableInfo semExecutableInfo : scanExecutableInfos) {
                    SemExecutableInfo semExecutableInfo2 =
                            (SemExecutableInfo)
                                    this.mSemExecutableInfos.get(semExecutableInfo.getId());
                    if (semExecutableInfo2 != null
                            && semExecutableInfo2.equals(semExecutableInfo)) {}
                    z = true;
                }
                if (z) {
                    this.mSemExecutableInfos.clear();
                    for (SemExecutableInfo semExecutableInfo3 : scanExecutableInfos) {
                        this.mSemExecutableInfos.put(
                                semExecutableInfo3.getId(), semExecutableInfo3);
                    }
                }
            } finally {
            }
        }
    }

    public void verifyCallingPackage(String str) {
        int i;
        try {
            i =
                    AppGlobals.getPackageManager()
                            .getPackageUid(
                                    str,
                                    794624L,
                                    UserHandle.getUserId(ISemExecuteManager.Stub.getCallingUid()));
        } catch (RemoteException unused) {
            i = -1;
        }
        if (i < 0) {
            StorageManagerService$$ExternalSyntheticOutline0.m(
                    "Package not found: ", str, "SemExecuteManagerService");
        }
        if (i != injectBinderCallingUid()) {
            throw new SecurityException("Calling package name mismatch");
        }
    }
}
