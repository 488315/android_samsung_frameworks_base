package com.samsung.android.server.pm.mm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.pm.UserManagerService;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MaintenanceModeManager {
    public static final File LOG_DIR = new File("/data/log/repairdump");
    public final Context mContext;
    public final Handler mHandler;
    public CountDownLatch mLatch;
    public TextView mOverlayView;
    public WindowManager.LayoutParams mOverlayViewParams;
    public AnonymousClass2 mReceiver;
    public final UserManagerService mUms;
    public WindowManager mWm;
    public final Set mRemainingPkgs = new ArraySet();
    public final AtomicBoolean mIsBeingCreated = new AtomicBoolean(false);
    public final ArrayList mLifecycleListeners = new ArrayList();
    public final AnonymousClass1 mOverlayReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("onReceive: ", action, "MaintenanceMode");
            if (action != null) {
                if (action.equals("com.samsung.android.intent.action.HIDE_MAINTENANCE_MODE_MARK")) {
                    MaintenanceModeManager.this.setOverlayVisibility(false);
                } else if (action.equals("com.samsung.android.intent.action.SHOW_MAINTENANCE_MODE_MARK")) {
                    MaintenanceModeManager.this.setOverlayVisibility(true);
                }
            }
        }
    };
    public final MaintenanceModeManager$$ExternalSyntheticLambda3 mExitRunnable = new MaintenanceModeManager$$ExternalSyntheticLambda3(0, this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ATCommandReceiver {
        public final AnonymousClass1 mReceiver = new AnonymousClass1();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.samsung.android.server.pm.mm.MaintenanceModeManager$ATCommandReceiver$1, reason: invalid class name */
        public final class AnonymousClass1 extends BroadcastReceiver {
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if ("com.samsung.intent.action.BCS_REQUEST".equals(intent.getAction()) && "AT+SVCIFPGM=1,3".equalsIgnoreCase(intent.getStringExtra("command"))) {
                    ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
                    UserInfo currentUser = activityManagerInternal != null ? activityManagerInternal.getCurrentUser() : null;
                    String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1((currentUser == null || currentUser.id != 77) ? "AT+SVCIFPGM=1,3\r\n+SVCIFPGM:1,USERMODE,NONE" : ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("AT+SVCIFPGM=1,3\r\n+SVCIFPGM:1,REPAIRMODE,", new SimpleDateFormat("yyyyMMddHHmm").format(new Date(currentUser.creationTime))), "\r\n");
                    Intent intent2 = new Intent("com.samsung.intent.action.BCS_RESPONSE");
                    intent2.putExtra("response", m$1);
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("response: ", m$1, "MaintenanceMode");
                    context.sendBroadcastAsUser(intent2, UserHandle.SYSTEM);
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.server.pm.mm.MaintenanceModeManager$1] */
    public MaintenanceModeManager(Context context, Handler handler, UserManagerService userManagerService) {
        this.mContext = context;
        this.mHandler = handler;
        this.mUms = userManagerService;
    }

    public static boolean containsAdbFunction() {
        String str = SystemProperties.get("persist.sys.usb.config", "");
        int indexOf = str.indexOf("adb");
        if (indexOf < 0) {
            return false;
        }
        if (indexOf > 0 && str.charAt(indexOf - 1) != ',') {
            return false;
        }
        int i = indexOf + 3;
        return i >= str.length() || str.charAt(i) == ',';
    }

    public static boolean isInMaintenanceMode() {
        return UserManagerService.getInstance().getUsers(false, false, false).stream().anyMatch(new MaintenanceModeManager$$ExternalSyntheticLambda0(0));
    }

    public static boolean isInMaintenanceModeFromProperty() {
        return SystemProperties.getBoolean("persist.sys.is_in_maintenance_mode", false);
    }

    public final void changeUsbDebuggingOption(boolean z) {
        SystemProperties.set("persist.sys.auto_confirm", z ? "1" : "0");
        Settings.Global.putInt(this.mContext.getContentResolver(), "adb_enabled", z ? 1 : 0);
    }

    public final void checkPendingAdbProcessing(final int i, final long j) {
        if (!(i == 1 ? containsAdbFunction() : !containsAdbFunction())) {
            if (!(j - SystemClock.elapsedRealtime() <= 0)) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        MaintenanceModeManager.this.checkPendingAdbProcessing(i, j);
                    }
                }, 200L);
                return;
            }
        }
        reboot(i == 1 ? "Enable" : "Disable");
    }

    public final void finishUserCreation() {
        notifyOtherPackages("com.samsung.android.intent.action.NOTIFY_PREPROCESSING_MAINTENANCE_MODE", "com.samsung.android.intent.action.RESPONSE_PREPROCESSING_MAINTENANCE_MODE");
        waitForOtherPackages();
        SystemProperties.set("persist.sys.is_in_maintenance_mode", Boolean.toString(true));
        changeUsbDebuggingOption(true);
        checkPendingAdbProcessing(1, SystemClock.elapsedRealtime() + 15000);
    }

    public final void logDebugInfoAsync(String str) {
        this.mHandler.post(new MaintenanceModeManager$$ExternalSyntheticLambda3(4, str));
    }

    /* JADX WARN: Type inference failed for: r3v7, types: [com.samsung.android.server.pm.mm.MaintenanceModeManager$2] */
    public final void notifyOtherPackages(String str, final String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        try {
            Intent intent = new Intent(str);
            ArraySet arraySet = new ArraySet();
            PackageManager packageManager = this.mContext.getPackageManager();
            Iterator<ResolveInfo> it = packageManager.queryBroadcastReceivers(intent, 0).iterator();
            while (it.hasNext()) {
                arraySet.add(it.next().getComponentInfo().packageName);
            }
            arraySet.addAll(Arrays.asList(((ActivityManager) this.mContext.getSystemService("activity")).queryRegisteredReceiverPackages(intent)));
            ArrayList arrayList = new ArrayList();
            Iterator it2 = arraySet.iterator();
            while (it2.hasNext()) {
                String str3 = (String) it2.next();
                if (UserHandle.isSameApp(packageManager.getPackageUid(str3, 0), 1000) || packageManager.checkPermission("com.samsung.android.permission.ACCESS_MAINTENANCE_MODE", str3) == 0) {
                    arrayList.add(str3);
                } else {
                    Log.i("MaintenanceMode", str3 + " doesn't have permission for receiving this broadcast");
                }
            }
            ((ArraySet) this.mRemainingPkgs).clear();
            ((ArraySet) this.mRemainingPkgs).addAll(arrayList);
            this.mLatch = new CountDownLatch(1);
            this.mReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent2) {
                    CountDownLatch countDownLatch;
                    if (str2.equals(intent2.getAction())) {
                        String stringExtra = intent2.getStringExtra("android.intent.extra.PACKAGE_NAME");
                        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("onReceive: ", stringExtra, "MaintenanceMode");
                        if (stringExtra != null) {
                            ((ArraySet) MaintenanceModeManager.this.mRemainingPkgs).remove(stringExtra);
                        }
                        if (!((ArraySet) MaintenanceModeManager.this.mRemainingPkgs).isEmpty() || (countDownLatch = MaintenanceModeManager.this.mLatch) == null) {
                            return;
                        }
                        countDownLatch.countDown();
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(str2);
            this.mContext.registerReceiverForAllUsers(this.mReceiver, intentFilter, "com.samsung.android.permission.ACCESS_MAINTENANCE_MODE", this.mHandler, 2);
            boolean userConsentAboutCreatingLog = MaintenanceModeUtils.getUserConsentAboutCreatingLog();
            if ("com.samsung.android.intent.action.NOTIFY_PREPROCESSING_MAINTENANCE_MODE".equals(str)) {
                logDebugInfoAsync("User consent about creating log: " + userConsentAboutCreatingLog);
            }
            intent.putExtra("user_consent_about_creating_log", userConsentAboutCreatingLog);
            intent.setFlags(32);
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                String str4 = (String) it3.next();
                intent.setPackage(str4);
                Log.i("MaintenanceMode", "sendBroadcast: " + str4);
                this.mContext.sendBroadcast(intent);
            }
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to notify: " + e.toString());
        }
        MaintenanceModeUtils.setUserConsentAboutCreatingLog(false);
    }

    public final void reboot(String str) {
        ((PowerManager) this.mContext.getSystemService("power")).reboot(str.concat(" MaintenanceMode"));
    }

    public final void setOverlayVisibility(boolean z) {
        try {
            if (z) {
                this.mWm.addView(this.mOverlayView, this.mOverlayViewParams);
            } else {
                this.mWm.removeView(this.mOverlayView);
            }
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to set overlay visibility: " + e.toString());
        }
    }

    public final void waitForOtherPackages() {
        try {
            CountDownLatch countDownLatch = this.mLatch;
            if (countDownLatch != null) {
                if (countDownLatch.await(9000L, TimeUnit.MILLISECONDS)) {
                    Log.i("MaintenanceMode", "Latch wake");
                } else {
                    Log.i("MaintenanceMode", "Latch timed out " + ((ArraySet) this.mRemainingPkgs).toString());
                }
            }
            AnonymousClass2 anonymousClass2 = this.mReceiver;
            if (anonymousClass2 != null) {
                this.mContext.unregisterReceiver(anonymousClass2);
            }
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to wait: " + e.toString());
        }
    }
}
