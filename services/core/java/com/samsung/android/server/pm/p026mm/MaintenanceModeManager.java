package com.samsung.android.server.pm.mm;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.SigningDetails;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.Binder;
import android.os.FileUtils;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.internal.app.IAppOpsService;
import com.android.server.LocalServices;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.UserManagerInternal;
import com.android.server.pm.UserManagerService;
import com.samsung.android.core.pm.mm.MaintenanceModeUtils;
import com.samsung.android.server.pm.PmLog;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class MaintenanceModeManager {
    public final Context mContext;
    public final Handler mHandler;
    public CountDownLatch mLatch;
    public TextView mOverlayView;
    public WindowManager.LayoutParams mOverlayViewParams;
    public BroadcastReceiver mReceiver;
    public final UserManagerService mUms;
    public WindowManager mWm;
    public static final String[] TARGET_PACKAGES_PREPROCESSING = {"com.samsung.android.rampart", "com.samsung.android.dqagent", "com.sec.android.sdhms"};
    public static final String[] TARGET_PACKAGES_POSTPROCESSING = {"com.samsung.android.rampart"};
    public static final File LOG_DIR = new File("/data/log/repairdump");
    public static final BroadcastReceiver mATCommandReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("com.samsung.intent.action.BCS_REQUEST".equals(intent.getAction()) && "AT+SVCIFPGM=1,3".equalsIgnoreCase(intent.getStringExtra(KnoxVpnFirewallHelper.CMD))) {
                MaintenanceModeManager.sendATCommandResponse(context);
            }
        }
    };
    public Set mRemainingPkgs = new ArraySet();
    public AtomicBoolean mIsBeingCreated = new AtomicBoolean(false);
    public final ArrayList mLifecycleListeners = new ArrayList();
    public final BroadcastReceiver mOverlayReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("MaintenanceMode", "onReceive: " + action);
            if (action != null) {
                if (action.equals("com.samsung.android.intent.action.HIDE_MAINTENANCE_MODE_MARK")) {
                    MaintenanceModeManager.this.setOverlayVisibility(false);
                } else if (action.equals("com.samsung.android.intent.action.SHOW_MAINTENANCE_MODE_MARK")) {
                    MaintenanceModeManager.this.setOverlayVisibility(true);
                }
            }
        }
    };
    public final Runnable mExitRunnable = new Runnable() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda3
        @Override // java.lang.Runnable
        public final void run() {
            MaintenanceModeManager.this.lambda$new$6();
        }
    };

    public MaintenanceModeManager(Context context, Handler handler, UserManagerService userManagerService) {
        this.mContext = context;
        this.mHandler = handler;
        this.mUms = userManagerService;
    }

    public boolean openUserCreationSession() {
        if (this.mIsBeingCreated.get()) {
            Log.i("MaintenanceMode", "Maintenance mode user is already being created.");
            return false;
        }
        return this.mIsBeingCreated.compareAndSet(false, true);
    }

    public void closeUserCreationSession() {
        this.mIsBeingCreated.set(false);
    }

    public boolean canCreateMaintenanceModeUser(boolean z) {
        return !z && isAllowedToManage() && !MaintenanceModeUtils.doesMaintenanceModeUserIdExist(this.mContext) && MaintenanceModeUtils.hasSystemFeature();
    }

    public boolean isAllowedToManage() {
        final int callingUid = Binder.getCallingUid();
        if (!UserHandle.isSameApp(callingUid, 1000)) {
            return false;
        }
        final int callingPid = Binder.getCallingPid();
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeManager.lambda$isAllowedToManage$0(callingPid, callingUid);
            }
        });
        return true;
    }

    public static /* synthetic */ void lambda$isAllowedToManage$0(int i, int i2) {
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ActivityManager.getService().getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    if (runningAppProcessInfo.pid == i && runningAppProcessInfo.uid == i2) {
                        PmLog.logDebugInfoAndLogcat("Requested by " + runningAppProcessInfo.processName, "MaintenanceMode");
                        return;
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    public static boolean isFeatureDisallowedByPolicy(Context context) {
        return SystemProperties.getBoolean("persist.sys.disallow_maintenance_mode", false) || isLduSkuBinary() || isShopDemo(context);
    }

    public static boolean isLduSkuBinary() {
        String str = SystemProperties.get("ril.product_code", "");
        if (str.length() < 11) {
            return false;
        }
        return str.charAt(10) == '8' || str.charAt(10) == '9';
    }

    public static boolean isShopDemo(Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "shopdemo", 0) == 1;
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to check shopdemo: " + e.toString());
            return false;
        }
    }

    public static boolean isInMaintenanceMode() {
        return UserManagerService.getInstance().getUsers(false, false, false).stream().anyMatch(new Predicate() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isMaintenanceModeUser;
                isMaintenanceModeUser = MaintenanceModeUtils.isMaintenanceModeUser((UserInfo) obj);
                return isMaintenanceModeUser;
            }
        });
    }

    public static boolean isInMaintenanceModeFromProperty() {
        return SystemProperties.getBoolean("persist.sys.is_in_maintenance_mode", false);
    }

    public static boolean isPlatformSigned(SigningDetails signingDetails, SigningDetails signingDetails2) {
        if (signingDetails == null || signingDetails2 == null) {
            return false;
        }
        return signingDetails2.hasAncestorOrSelf(signingDetails) || signingDetails.checkCapability(signingDetails2, 4);
    }

    public static boolean isMobileDoctorProcess(ApplicationInfo applicationInfo) {
        if (applicationInfo != null && "com.samsung.android.app.mobiledoctor".equals(applicationInfo.packageName)) {
            return ((PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class)).isPlatformSigned(applicationInfo.packageName);
        }
        return false;
    }

    public static int[] updateGidsForMobileDoctor(int[] iArr) {
        if (iArr == null) {
            return null;
        }
        int[] iArr2 = new int[iArr.length + 1];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        iArr2[iArr.length] = Process.getGidForName("radio");
        return iArr2;
    }

    public static void registerATCommandReceiver(Context context, Handler handler) {
        context.registerReceiver(mATCommandReceiver, new IntentFilter("com.samsung.intent.action.BCS_REQUEST"), null, handler);
    }

    public static void sendATCommandResponse(Context context) {
        String str;
        ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        if (activityManagerInternal != null && activityManagerInternal.getCurrentUserId() == 77) {
            str = "AT+SVCIFPGM=1,3\r\n+SVCIFPGM:1,REPAIRMODE";
        } else {
            str = "AT+SVCIFPGM=1,3\r\n+SVCIFPGM:1,USERMODE";
        }
        String str2 = str + "\r\n";
        Intent intent = new Intent("com.samsung.intent.action.BCS_RESPONSE");
        intent.putExtra("response", str2);
        Log.i("MaintenanceMode", "response: " + str2);
        context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
    }

    public void addLifecycleListener(UserManagerInternal.MaintenanceModeLifecycleListener maintenanceModeLifecycleListener) {
        synchronized (this.mLifecycleListeners) {
            if (!this.mLifecycleListeners.contains(maintenanceModeLifecycleListener)) {
                logDebugInfoAsync("addLifecycleListener: " + maintenanceModeLifecycleListener);
                this.mLifecycleListeners.add(maintenanceModeLifecycleListener);
            }
        }
    }

    public void finishUserCreation() {
        notifyOtherPackages("com.samsung.android.intent.action.NOTIFY_PREPROCESSING_MAINTENANCE_MODE", "com.samsung.android.intent.action.RESPONSE_PREPROCESSING_MAINTENANCE_MODE", TARGET_PACKAGES_PREPROCESSING);
        waitForOtherPackages(3000L);
        setMaintenanceModeEnabledState(true);
        changeUsbDebuggingOption(true);
        lambda$checkPendingAdbProcessing$4(1, SystemClock.elapsedRealtime() + 15000);
    }

    public void finishUserDeletion() {
        notifyOtherPackages("com.samsung.android.intent.action.NOTIFY_POSTPROCESSING_MAINTENANCE_MODE", "com.samsung.android.intent.action.RESPONSE_POSTPROCESSING_MAINTENANCE_MODE", TARGET_PACKAGES_POSTPROCESSING);
        waitForOtherPackages(3000L);
        cleanUpLogFiles();
        notifyPostprocessingDirectly();
        notifyPostprocessingAsync(60000L);
        this.mHandler.postDelayed(this.mExitRunnable, 65000L);
    }

    public void reboot(String str) {
        ((PowerManager) this.mContext.getSystemService("power")).reboot(str + " MaintenanceMode");
    }

    public void onUserStartingAsync() {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeManager.this.lambda$onUserStartingAsync$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserStartingAsync$2() {
        setUserRestrictions();
        skipSetupWizard();
    }

    public void onUserUnlockedAsync() {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeManager.this.lambda$onUserUnlockedAsync$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onUserUnlockedAsync$3() {
        startNotificationService();
        initializeOverlay();
        changeUsbDebuggingOption(true);
    }

    public final void setUserRestrictions() {
        this.mUms.setUserRestriction("no_sms", true, 77);
        this.mUms.setUserRestriction("no_outgoing_calls", false, 77);
    }

    public final void skipSetupWizard() {
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 1, 77);
        Settings.System.putLongForUser(this.mContext.getContentResolver(), "screen_off_timeout", 600000L, 77);
        try {
            IPackageManager packageManager = ActivityThread.getPackageManager();
            packageManager.setApplicationEnabledSetting("com.sec.android.app.SecSetupWizard", 2, 0, 77, "MaintenanceMode");
            packageManager.setApplicationEnabledSetting("com.google.android.setupwizard", 2, 0, 77, "MaintenanceMode");
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to disable SUW: " + e.toString());
        }
    }

    public final void startNotificationService() {
        try {
            ComponentName componentName = new ComponentName("android", "com.samsung.android.core.pm.mm.MaintenanceModeNotificationService");
            Intent intent = new Intent();
            intent.setComponent(componentName);
            this.mContext.startForegroundServiceAsUser(intent, new UserHandle(77));
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to register notification: " + e.toString());
        }
    }

    public final void initializeOverlay() {
        makeOverlay();
        setOverlayVisibility(true);
        registerOverlayReceiver();
    }

    public final void makeOverlay() {
        try {
            Resources resources = this.mContext.getResources();
            this.mOverlayView = new TextView(this.mContext);
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.messaging_image_rounding);
            this.mOverlayView.setGravity(17);
            this.mOverlayView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            this.mOverlayView.setBackgroundColor(this.mContext.getColor(R.color.overview_background));
            this.mOverlayView.setTextAppearance(R.style.TextAppearance.StatusBar.EventContent.Time);
            this.mOverlayView.setTextColor(this.mContext.getColor(R.color.overview_background_dark));
            this.mOverlayView.setTextSize(0, resources.getDimensionPixelSize(R.dimen.messaging_layout_icon_padding_start));
            this.mOverlayView.setText(R.string.permdesc_readMediaVideo);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mOverlayViewParams = layoutParams;
            layoutParams.type = 2038;
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.gravity = 8388691;
            layoutParams.format = this.mOverlayView.getBackground().getOpacity();
            WindowManager.LayoutParams layoutParams2 = this.mOverlayViewParams;
            layoutParams2.flags = 24;
            layoutParams2.privateFlags |= 536870928;
            this.mWm = (WindowManager) this.mContext.getSystemService("window");
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to make overlay: " + e.toString());
        }
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

    public final void registerOverlayReceiver() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.intent.action.HIDE_MAINTENANCE_MODE_MARK");
            intentFilter.addAction("com.samsung.android.intent.action.SHOW_MAINTENANCE_MODE_MARK");
            this.mContext.registerReceiverForAllUsers(this.mOverlayReceiver, intentFilter, null, this.mHandler);
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to register overlay receiver: " + e.toString());
        }
    }

    public final void setMaintenanceModeEnabledState(boolean z) {
        SystemProperties.set("persist.sys.is_in_maintenance_mode", Boolean.toString(z));
    }

    public final void changeUsbDebuggingOption(boolean z) {
        SystemProperties.set("persist.sys.auto_confirm", z ? "1" : "0");
        Settings.Global.putInt(this.mContext.getContentResolver(), "adb_enabled", z ? 1 : 0);
    }

    /* renamed from: checkPendingAdbProcessing, reason: merged with bridge method [inline-methods] */
    public final void lambda$checkPendingAdbProcessing$4(final int i, final long j) {
        boolean z;
        if (i == 1) {
            z = containsAdbFunction();
        } else {
            z = !containsAdbFunction();
        }
        if (z || isAdbProcessingTimeout(j)) {
            reboot(i == 1 ? "Enable" : "Disable");
        } else {
            this.mHandler.postDelayed(new Runnable() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    MaintenanceModeManager.this.lambda$checkPendingAdbProcessing$4(i, j);
                }
            }, 200L);
        }
    }

    public final boolean containsAdbFunction() {
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

    public final boolean isAdbProcessingTimeout(long j) {
        return j - SystemClock.elapsedRealtime() <= 0;
    }

    public final void logDebugInfoAsync(final String str) {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeManager.lambda$logDebugInfoAsync$5(str);
            }
        });
    }

    public static /* synthetic */ void lambda$logDebugInfoAsync$5(String str) {
        try {
            PmLog.logDebugInfoAndLogcat(str, "MaintenanceMode");
        } catch (Exception unused) {
        }
    }

    public final void notifyOtherPackages(String str, final String str2, String[] strArr) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || strArr == null) {
            return;
        }
        try {
            this.mRemainingPkgs.clear();
            this.mRemainingPkgs.addAll(Arrays.asList(strArr));
            this.mLatch = new CountDownLatch(1);
            this.mReceiver = new BroadcastReceiver() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager.3
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    if (str2.equals(intent.getAction())) {
                        String stringExtra = intent.getStringExtra("android.intent.extra.PACKAGE_NAME");
                        Log.i("MaintenanceMode", "onReceive: " + stringExtra);
                        if (stringExtra != null) {
                            MaintenanceModeManager.this.mRemainingPkgs.remove(stringExtra);
                        }
                        if (!MaintenanceModeManager.this.mRemainingPkgs.isEmpty() || MaintenanceModeManager.this.mLatch == null) {
                            return;
                        }
                        MaintenanceModeManager.this.mLatch.countDown();
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(str2);
            this.mContext.registerReceiverForAllUsers(this.mReceiver, intentFilter, "com.samsung.android.permission.ACCESS_MAINTENANCE_MODE", this.mHandler);
            for (String str3 : strArr) {
                Intent intent = new Intent(str);
                intent.setPackage(str3);
                Log.i("MaintenanceMode", "sendBroadcast: " + str3);
                this.mContext.sendBroadcast(intent);
            }
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to notify: " + e.toString());
        }
    }

    public final void waitForOtherPackages(long j) {
        try {
            CountDownLatch countDownLatch = this.mLatch;
            if (countDownLatch != null) {
                if (countDownLatch.await(j, TimeUnit.MILLISECONDS)) {
                    Log.i("MaintenanceMode", "Latch wake");
                } else {
                    Log.i("MaintenanceMode", "Latch timed out " + this.mRemainingPkgs.toString());
                }
            }
            BroadcastReceiver broadcastReceiver = this.mReceiver;
            if (broadcastReceiver != null) {
                this.mContext.unregisterReceiver(broadcastReceiver);
            }
        } catch (Exception e) {
            Log.i("MaintenanceMode", "Failed to wait: " + e.toString());
        }
    }

    public final void cleanUpLogFiles() {
        try {
            FileUtils.deleteContentsAndDir(LOG_DIR);
        } catch (Exception unused) {
        }
    }

    public final void notifyPostprocessingDirectly() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                IAppOpsService.Stub.asInterface(ServiceManager.getService("appops")).removeUser(77);
            } catch (Exception e) {
                Log.i("MaintenanceMode", "Unable to notify AppOpsService of removing user.", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6() {
        setMaintenanceModeEnabledState(false);
        changeUsbDebuggingOption(false);
        lambda$checkPendingAdbProcessing$4(0, SystemClock.elapsedRealtime() + 15000);
    }

    public final void notifyPostprocessingAsync(long j) {
        CompletableFuture.runAsync(new Runnable() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                MaintenanceModeManager.this.lambda$notifyPostprocessingAsync$8();
            }
        }).orTimeout(j, TimeUnit.MILLISECONDS).whenCompleteAsync(new BiConsumer() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MaintenanceModeManager.this.lambda$notifyPostprocessingAsync$9((Void) obj, (Throwable) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPostprocessingAsync$8() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLifecycleListeners) {
            Iterator it = this.mLifecycleListeners.iterator();
            while (it.hasNext()) {
                final UserManagerInternal.MaintenanceModeLifecycleListener maintenanceModeLifecycleListener = (UserManagerInternal.MaintenanceModeLifecycleListener) it.next();
                final CompletableFuture completableFuture = new CompletableFuture();
                arrayList.add(completableFuture);
                try {
                    logDebugInfoAsync("Start to call onPostprocessing: " + maintenanceModeLifecycleListener);
                    maintenanceModeLifecycleListener.onPostprocessing(new Consumer() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            MaintenanceModeManager.this.lambda$notifyPostprocessingAsync$7(maintenanceModeLifecycleListener, completableFuture, (Boolean) obj);
                        }
                    });
                    logDebugInfoAsync("Finish calling onPostprocessing: " + maintenanceModeLifecycleListener);
                } catch (Exception e) {
                    logDebugInfoAsync("Got exception: " + maintenanceModeLifecycleListener + " - " + e.toString());
                    completableFuture.complete(Boolean.FALSE);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        try {
            CompletableFuture.allOf((CompletableFuture[]) arrayList.toArray(new CompletableFuture[arrayList.size()])).get();
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPostprocessingAsync$7(UserManagerInternal.MaintenanceModeLifecycleListener maintenanceModeLifecycleListener, CompletableFuture completableFuture, Boolean bool) {
        logDebugInfoAsync("Received callback: " + maintenanceModeLifecycleListener + " - " + bool);
        completableFuture.complete(bool);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyPostprocessingAsync$9(Void r2, Throwable th) {
        if (th != null) {
            logDebugInfoAsync("Got exception: " + th.toString());
        }
        this.mHandler.removeCallbacks(this.mExitRunnable);
        this.mHandler.post(this.mExitRunnable);
    }
}
