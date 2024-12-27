package com.android.server.sepunion;

import android.app.ActivityManager;
import android.app.IProcessObserver;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.hardware.SensorPrivacyManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.am.FreecessController$$ExternalSyntheticOutline0;
import com.android.server.sepunion.SmartManagerService.RunningProcessObserver;

import com.samsung.android.app.usage.IUsageStatsWatcher;
import com.samsung.android.sepunion.ISmartManagerService;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SmartManagerService extends ISmartManagerService.Stub
        implements AbsSemSystemService {
    public final Context mContext;
    public static final Uri PAYMENT_SWITCH_URI = Settings.Secure.getUriFor("payment_safety_switch");
    public static final Uri PAYMENT_APP_URI =
            Uri.parse("content://com.samsung.android.sm/ProtectedApps");
    public static final Uri PAYMENT_APP_CHECK_URI =
            Uri.parse("content://com.samsung.android.sm.payment");
    public static final Uri IMPORT_COMPONENT_LIST_URI =
            Uri.parse("content://com.samsung.android.sm.payment/importantComponentList");
    public static final String[] PROJECTION = {"package_name"};
    public static final String[] ARGS = {"1"};
    public static final Uri SM_PROVIDER_URI = Uri.parse("content://com.samsung.android.sm.dcapi");
    public final HashSet mProtectedAppSet = new HashSet();
    public final ConcurrentHashMap mCheckedAppMap = new ConcurrentHashMap();
    public final ConcurrentHashMap mForegroundActivitiesPidMap = new ConcurrentHashMap();
    public volatile boolean mProtectedAppLoaded = false;
    public final ConcurrentHashMap mImportantAppLastCheckTimeMap = new ConcurrentHashMap();
    public final HashSet mImportantAppSet = new HashSet();
    public final ArrayList mImportantComponentList = new ArrayList();
    public final AnonymousClass1 mUserActionReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.sepunion.SmartManagerService.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                        Uri uri = SmartManagerService.PAYMENT_SWITCH_URI;
                        Log.i("SmartManagerService", "onUserUnlocked");
                        SmartManagerService.this.mHandler.sendEmptyMessage(70);
                        return;
                    }
                    int intExtra =
                            "com.samsung.knox.securefolder.SETUP_COMPLETE".equals(action)
                                    ? intent.getIntExtra("userid", -1)
                                    : intent.getIntExtra("android.intent.extra.user_handle", -1);
                    Message obtain = Message.obtain();
                    obtain.what = 60;
                    Bundle bundle = new Bundle();
                    bundle.putString("action", action);
                    bundle.putInt("userid", intExtra);
                    obtain.setData(bundle);
                    SmartManagerService.this.mHandler.sendMessage(obtain);
                }
            };
    public final AnonymousClass2 mPkgChangedIntentReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.sepunion.SmartManagerService.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("android.intent.action.PACKAGE_ADDED".equals(action)
                            || "android.intent.action.PACKAGE_REMOVED".equals(action)) {
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", 0);
                        boolean booleanExtra =
                                intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                        Uri data = intent.getData();
                        if (booleanExtra || data == null) {
                            return;
                        }
                        Message obtain = Message.obtain();
                        obtain.what = 50;
                        Bundle m =
                                FreecessController$$ExternalSyntheticOutline0.m(
                                        intExtra, "action", action, "uid");
                        m.putString("pkg_name", data.getSchemeSpecificPart());
                        obtain.setData(m);
                        SmartManagerService.this.mHandler.sendMessage(obtain);
                    }
                }
            };
    public final AnonymousClass3 mUsageStatusWatcher =
            new IUsageStatsWatcher
                    .Stub() { // from class: com.android.server.sepunion.SmartManagerService.3
                public final void notePauseComponent(
                        ComponentName componentName, Intent intent, int i, int i2) {}

                public final void noteResumeComponent(
                        ComponentName componentName, Intent intent, int i, int i2) {
                    if (componentName != null) {
                        SmartManagerService smartManagerService = SmartManagerService.this;
                        String packageName = componentName.getPackageName();
                        String className = componentName.getClassName();
                        smartManagerService.mHandler.removeMessages(40);
                        Message obtain = Message.obtain();
                        obtain.what = 40;
                        Bundle bundle = new Bundle();
                        bundle.putString("pkg_name", packageName);
                        bundle.putString("class_name", className);
                        obtain.setData(bundle);
                        smartManagerService.mHandler.sendMessage(obtain);
                    }
                }

                public final void noteStopComponent(
                        ComponentName componentName, Intent intent, int i, int i2) {}
            };
    public final AnonymousClass4 mLocationChangeReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.server.sepunion.SmartManagerService.4
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    SmartManagerService.m870$$Nest$monPermissionChanged(
                            SmartManagerService.this, 3);
                }
            };
    public final AnonymousClass5 mPrivacyChangedListener =
            new SensorPrivacyManager
                    .OnSensorPrivacyChangedListener() { // from class:
                                                        // com.android.server.sepunion.SmartManagerService.5
                public final void onSensorPrivacyChanged(int i, boolean z) {
                    if (i == 2 || i == 1) {
                        SmartManagerService.m870$$Nest$monPermissionChanged(
                                SmartManagerService.this, i);
                    }
                }
            };
    public final BgWorkerHandler mHandler =
            new BgWorkerHandler(
                    KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("SmartManagerService")
                            .getLooper());

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BgWorkerHandler extends Handler {
        public BgWorkerHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Uri uri = SmartManagerService.PAYMENT_SWITCH_URI;
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    new StringBuilder("msg : "), message.what, "SmartManagerService");
            int i = message.what;
            SmartManagerService smartManagerService = SmartManagerService.this;
            if (i == 10) {
                smartManagerService.loadProtectedAppSet();
                smartManagerService.loadImportantAppComponentList();
                smartManagerService.registerWatcherForImportantComponentList();
                return;
            }
            if (i == 20) {
                Bundle data = message.getData();
                smartManagerService.getClass();
                if (data == null) {
                    return;
                }
                int i2 = data.getInt("pid", 0);
                ActivityManager activityManager =
                        (ActivityManager) smartManagerService.mContext.getSystemService("activity");
                if (activityManager == null) {
                    return;
                }
                String packageFromAppProcesses = activityManager.getPackageFromAppProcesses(i2);
                if (smartManagerService.isProtectedApp(packageFromAppProcesses)
                        && !smartManagerService.mImportantAppSet.contains(
                                packageFromAppProcesses)) {
                    data.putString("pkg_name", packageFromAppProcesses);
                    try {
                        smartManagerService
                                .mContext
                                .getContentResolver()
                                .call(
                                        SmartManagerService.PAYMENT_APP_CHECK_URI,
                                        "startCheck",
                                        (String) null,
                                        data);
                        return;
                    } catch (SQLiteException | IllegalArgumentException e) {
                        Log.e(
                                "SmartManagerService",
                                "SmartManager app doesn't support payment policy, please check",
                                e);
                        return;
                    }
                }
                return;
            }
            if (i == 40) {
                Bundle data2 = message.getData();
                smartManagerService.getClass();
                try {
                    String string = data2.getString("pkg_name");
                    long longValue =
                            ((Long)
                                            smartManagerService.mImportantAppLastCheckTimeMap
                                                    .getOrDefault(string, -1L))
                                    .longValue();
                    long currentTimeMillis = System.currentTimeMillis();
                    if (Math.abs(longValue - currentTimeMillis) < 3000) {
                        Log.e("SmartManagerService", "avoid repeat check in 3 seconds");
                    } else {
                        smartManagerService.mImportantAppLastCheckTimeMap.put(
                                string, Long.valueOf(currentTimeMillis));
                        if (smartManagerService.isProtectedApp(string)) {
                            smartManagerService
                                    .mContext
                                    .getContentResolver()
                                    .call(
                                            SmartManagerService.PAYMENT_APP_CHECK_URI,
                                            "startCheck",
                                            (String) null,
                                            data2);
                        }
                    }
                    return;
                } catch (SQLiteException | IllegalArgumentException e2) {
                    Log.e(
                            "SmartManagerService",
                            "SmartManager app doesn't support payment policy, please check",
                            e2);
                    return;
                }
            }
            if (i == 50) {
                Bundle data3 = message.getData();
                smartManagerService.getClass();
                try {
                    smartManagerService
                            .mContext
                            .getContentResolver()
                            .call(
                                    SmartManagerService.PAYMENT_APP_CHECK_URI,
                                    "onPkgChanged",
                                    (String) null,
                                    data3);
                    return;
                } catch (SQLiteException | IllegalArgumentException e3) {
                    Log.e(
                            "SmartManagerService",
                            "SmartManager app doesn't support payment policy, please check",
                            e3);
                    return;
                }
            }
            if (i == 60) {
                Bundle data4 = message.getData();
                smartManagerService.getClass();
                try {
                    smartManagerService
                            .mContext
                            .getContentResolver()
                            .call(
                                    SmartManagerService.PAYMENT_APP_CHECK_URI,
                                    "onUserAction",
                                    (String) null,
                                    data4);
                    return;
                } catch (SQLiteException | IllegalArgumentException e4) {
                    Log.e(
                            "SmartManagerService",
                            "SmartManager app doesn't support payment policy, please check",
                            e4);
                    return;
                }
            }
            if (i != 70) {
                if (i != 100) {
                    return;
                }
                Bundle data5 = message.getData();
                smartManagerService.getClass();
                try {
                    smartManagerService
                            .mContext
                            .getContentResolver()
                            .call(
                                    SmartManagerService.SM_PROVIDER_URI,
                                    "updatePrivacyLockingState",
                                    (String) null,
                                    data5);
                    return;
                } catch (IllegalArgumentException e5) {
                    Log.e("SmartManagerService", "notify to smart manager has exception ", e5);
                    return;
                }
            }
            smartManagerService.getClass();
            try {
                ProtectedAppChangedObserver protectedAppChangedObserver =
                        smartManagerService
                        .new ProtectedAppChangedObserver(smartManagerService.mHandler);
                smartManagerService
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(
                                SmartManagerService.PAYMENT_SWITCH_URI,
                                true,
                                protectedAppChangedObserver);
                smartManagerService
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(
                                SmartManagerService.PAYMENT_APP_URI,
                                true,
                                protectedAppChangedObserver);
            } catch (Exception e6) {
                Log.e("SmartManagerService", "registerObserver cause exception", e6);
            }
            smartManagerService.loadProtectedAppSet();
            smartManagerService.loadImportantAppComponentList();
            smartManagerService.registerWatcherForImportantComponentList();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProtectedAppChangedObserver extends ContentObserver {
        public ProtectedAppChangedObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            SmartManagerService.this.mHandler.removeMessages(10);
            SmartManagerService.this.mHandler.sendEmptyMessage(10);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RunningProcessObserver extends IProcessObserver.Stub {
        public RunningProcessObserver() {}

        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            if (z) {
                if (((Boolean)
                                SmartManagerService.this.mCheckedAppMap.getOrDefault(
                                        Integer.valueOf(i2), Boolean.FALSE))
                        .booleanValue()) {
                    Uri uri = SmartManagerService.PAYMENT_SWITCH_URI;
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                            i2, "already checked ", "SmartManagerService");
                    return;
                }
                ConcurrentHashMap concurrentHashMap =
                        SmartManagerService.this.mForegroundActivitiesPidMap;
                Integer valueOf = Integer.valueOf(i);
                Boolean bool = Boolean.TRUE;
                concurrentHashMap.put(valueOf, bool);
                SmartManagerService.this.mCheckedAppMap.put(Integer.valueOf(i2), bool);
                SmartManagerService smartManagerService = SmartManagerService.this;
                smartManagerService.mHandler.removeMessages(20);
                Message obtain = Message.obtain();
                obtain.what = 20;
                Bundle bundle = new Bundle();
                bundle.putInt("pid", i);
                bundle.putInt("uid", i2);
                obtain.setData(bundle);
                smartManagerService.mHandler.sendMessage(obtain);
            }
        }

        public final void onForegroundServicesChanged(int i, int i2, int i3) {}

        public final void onProcessDied(int i, int i2) {
            if (((Boolean)
                            SmartManagerService.this.mForegroundActivitiesPidMap.getOrDefault(
                                    Integer.valueOf(i), Boolean.FALSE))
                    .booleanValue()) {
                SmartManagerService.this.mForegroundActivitiesPidMap.remove(Integer.valueOf(i));
                SmartManagerService.this.mCheckedAppMap.remove(Integer.valueOf(i2));
            }
        }

        public final void onProcessStarted(int i, int i2, int i3, String str, String str2) {}
    }

    /* renamed from: -$$Nest$monPermissionChanged, reason: not valid java name */
    public static void m870$$Nest$monPermissionChanged(
            SmartManagerService smartManagerService, int i) {
        smartManagerService.mHandler.removeMessages(100);
        Message obtain = Message.obtain();
        obtain.what = 100;
        Bundle bundle = new Bundle();
        bundle.putInt("permission_type", i);
        obtain.setData(bundle);
        smartManagerService.mHandler.sendMessageDelayed(obtain, 100L);
    }

    /* JADX WARN: Type inference failed for: r0v10, types: [com.android.server.sepunion.SmartManagerService$4] */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.server.sepunion.SmartManagerService$5] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.server.sepunion.SmartManagerService$1] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.server.sepunion.SmartManagerService$2] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.server.sepunion.SmartManagerService$3] */
    public SmartManagerService(Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void dump(
            FileDescriptor fileDescriptor, final PrintWriter printWriter, String[] strArr) {
        printWriter.println("##### SmartManagerService  #####");
        printWriter.println(" Current Payment App:");
        synchronized (this.mProtectedAppSet) {
            this.mProtectedAppSet.forEach(
                    new Consumer() { // from class:
                                     // com.android.server.sepunion.SmartManagerService$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            Uri uri = SmartManagerService.PAYMENT_SWITCH_URI;
                            printWriter.println((String) obj);
                        }
                    });
        }
    }

    public final AbsSemSystemService getSemSystemService(String str) {
        return null;
    }

    public final boolean isProtectedApp(String str) {
        if (!this.mProtectedAppLoaded) {
            loadProtectedAppSet();
        }
        synchronized (this.mProtectedAppSet) {
            try {
                if (this.mProtectedAppSet.contains(str)) {
                    return true;
                }
                Log.d("SmartManagerService", str + " not target");
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void loadImportantAppComponentList() {
        this.mImportantAppSet.clear();
        this.mImportantComponentList.clear();
        try {
            Cursor query =
                    this.mContext
                            .getContentResolver()
                            .query(IMPORT_COMPONENT_LIST_URI, null, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        do {
                            String string = query.getString(0);
                            this.mImportantAppSet.add(string);
                            this.mImportantComponentList.add(
                                    new ComponentName(string, query.getString(1)));
                        } while (query.moveToNext());
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            Log.e("SmartManagerService", "getImportantComponentList", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x006c A[Catch: all -> 0x0024, SQLiteException | IllegalArgumentException | NullPointerException -> 0x0070, SQLiteException | IllegalArgumentException | NullPointerException -> 0x0070, SQLiteException | IllegalArgumentException | NullPointerException -> 0x0070, TRY_ENTER, TRY_LEAVE, TryCatch #3 {SQLiteException | IllegalArgumentException | NullPointerException -> 0x0070, blocks: (B:11:0x0026, B:15:0x006c, B:15:0x006c, B:15:0x006c, B:30:0x007c, B:30:0x007c, B:30:0x007c, B:35:0x0079, B:35:0x0079, B:35:0x0079), top: B:10:0x0026, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadProtectedAppSet() {
        /*
            r10 = this;
            java.util.HashSet r0 = r10.mProtectedAppSet
            monitor-enter(r0)
            java.util.HashSet r1 = r10.mProtectedAppSet     // Catch: java.lang.Throwable -> L24
            r1.clear()     // Catch: java.lang.Throwable -> L24
            r1 = 1
            r10.mProtectedAppLoaded = r1     // Catch: java.lang.Throwable -> L24
            android.content.Context r1 = r10.mContext     // Catch: java.lang.Throwable -> L24
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch: java.lang.Throwable -> L24
            java.lang.String r2 = "payment_safety_switch"
            r3 = 0
            int r1 = android.provider.Settings.Secure.getInt(r1, r2, r3)     // Catch: java.lang.Throwable -> L24
            if (r1 != 0) goto L26
            java.lang.String r10 = "SmartManagerService"
            java.lang.String r1 = "Payment switch is off"
            android.util.Log.i(r10, r1)     // Catch: java.lang.Throwable -> L24
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L24
            return
        L24:
            r10 = move-exception
            goto L86
        L26:
            android.content.Context r1 = r10.mContext     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L70
            android.content.ContentResolver r4 = r1.getContentResolver()     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L70
            android.net.Uri r5 = com.android.server.sepunion.SmartManagerService.PAYMENT_APP_URI     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L70
            java.lang.String[] r6 = com.android.server.sepunion.SmartManagerService.PROJECTION     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L70
            java.lang.String r7 = "mode=?"
            java.lang.String[] r8 = com.android.server.sepunion.SmartManagerService.ARGS     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L70
            r9 = 0
            android.database.Cursor r1 = r4.query(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L70
            if (r1 == 0) goto L63
            boolean r2 = r1.isClosed()     // Catch: java.lang.Throwable -> L61
            if (r2 != 0) goto L63
            int r2 = r1.getCount()     // Catch: java.lang.Throwable -> L61
            if (r2 <= 0) goto L63
        L48:
            boolean r2 = r1.moveToNext()     // Catch: java.lang.Throwable -> L61
            if (r2 == 0) goto L6a
            java.lang.String r2 = "SmartManagerService"
            java.lang.String r4 = r1.getString(r3)     // Catch: java.lang.Throwable -> L61
            android.util.Log.d(r2, r4)     // Catch: java.lang.Throwable -> L61
            java.util.HashSet r2 = r10.mProtectedAppSet     // Catch: java.lang.Throwable -> L61
            java.lang.String r4 = r1.getString(r3)     // Catch: java.lang.Throwable -> L61
            r2.add(r4)     // Catch: java.lang.Throwable -> L61
            goto L48
        L61:
            r10 = move-exception
            goto L72
        L63:
            java.lang.String r10 = "SmartManagerService"
            java.lang.String r2 = "Protected App is empty"
            android.util.Log.i(r10, r2)     // Catch: java.lang.Throwable -> L61
        L6a:
            if (r1 == 0) goto L84
            r1.close()     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L70 java.lang.Throwable -> L70 java.lang.Throwable -> L70
            goto L84
        L70:
            r10 = move-exception
            goto L7d
        L72:
            if (r1 == 0) goto L7c
            r1.close()     // Catch: java.lang.Throwable -> L78
            goto L7c
        L78:
            r1 = move-exception
            r10.addSuppressed(r1)     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L70 java.lang.Throwable -> L70 java.lang.Throwable -> L70
        L7c:
            throw r10     // Catch: java.lang.Throwable -> L24 java.lang.Throwable -> L70 java.lang.Throwable -> L70 java.lang.Throwable -> L70
        L7d:
            java.lang.String r1 = "SmartManagerService"
            java.lang.String r2 = "SmartManager app doesn't support payment app list, please check"
            android.util.Log.e(r1, r2, r10)     // Catch: java.lang.Throwable -> L24
        L84:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L24
            return
        L86:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L24
            throw r10
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.sepunion.SmartManagerService.loadProtectedAppSet():void");
    }

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            Log.i("SmartManagerService", "onBootPhase");
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.sepunion.SmartManagerService$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SmartManagerService smartManagerService = SmartManagerService.this;
                            Uri uri = SmartManagerService.PAYMENT_SWITCH_URI;
                            smartManagerService.getClass();
                            try {
                                IntentFilter intentFilter = new IntentFilter();
                                intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                                intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                                intentFilter.addDataScheme("package");
                                smartManagerService.mContext.registerReceiverAsUser(
                                        smartManagerService.mPkgChangedIntentReceiver,
                                        UserHandle.ALL,
                                        intentFilter,
                                        null,
                                        smartManagerService.mHandler);
                                IntentFilter intentFilter2 = new IntentFilter();
                                intentFilter2.addAction("android.intent.action.USER_ADDED");
                                intentFilter2.addAction(
                                        "com.samsung.knox.securefolder.SETUP_COMPLETE");
                                intentFilter2.addAction("android.intent.action.USER_STOPPED");
                                intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
                                smartManagerService.mContext.registerReceiver(
                                        smartManagerService.mUserActionReceiver,
                                        intentFilter2,
                                        null,
                                        smartManagerService.mHandler);
                                smartManagerService.mContext.registerReceiver(
                                        smartManagerService.mLocationChangeReceiver,
                                        new IntentFilter("android.location.MODE_CHANGED"),
                                        null,
                                        smartManagerService.mHandler);
                            } catch (NullPointerException e) {
                                Log.e("SmartManagerService", "registerReceiver cause exception", e);
                            }
                            try {
                                ActivityManager.getService()
                                        .registerProcessObserver(
                                                smartManagerService.new RunningProcessObserver());
                            } catch (RemoteException e2) {
                                Log.e(
                                        "SmartManagerService",
                                        "registerRunningProcessObserver cause exception",
                                        e2);
                            }
                            try {
                                SensorPrivacyManager sensorPrivacyManager =
                                        (SensorPrivacyManager)
                                                smartManagerService.mContext.getSystemService(
                                                        SensorPrivacyManager.class);
                                sensorPrivacyManager.addSensorPrivacyListener(
                                        2, smartManagerService.mPrivacyChangedListener);
                                sensorPrivacyManager.addSensorPrivacyListener(
                                        1, smartManagerService.mPrivacyChangedListener);
                            } catch (IllegalArgumentException e3) {
                                Log.e("SmartManagerService", "register listener exception ", e3);
                            }
                        }
                    });
        }
    }

    public final void onCleanupUser(int i) {}

    @Override // com.android.server.sepunion.AbsSemSystemService
    public final void onCreate(Bundle bundle) {}

    public final void onDestroy() {}

    public final void onStart() {}

    public final void onStartUser(int i) {}

    public final void onStopUser(int i) {}

    public final void onSwitchUser(int i) {}

    public final void onUnlockUser(int i) {}

    public final void registerWatcherForImportantComponentList() {
        try {
            UsageStatsManager usageStatsManager =
                    (UsageStatsManager) this.mContext.getSystemService("usagestats");
            usageStatsManager.unregisterUsageStatsWatcher(this.mUsageStatusWatcher);
            if (this.mImportantComponentList.isEmpty()) {
                Log.e("SmartManagerService", "IMPORTANT_COMPONENT_LIST is empty");
            } else {
                usageStatsManager.registerUsageStatsWatcher(
                        this.mUsageStatusWatcher, this.mImportantComponentList);
            }
        } catch (Exception e) {
            Log.e(
                    "SmartManagerService",
                    "registerWatcherForImportantComponents cause exception",
                    e);
        }
    }
}
