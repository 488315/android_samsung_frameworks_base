package com.samsung.android.server.pm.mm;

import android.R;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.res.Resources;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.server.locksettings.LockSettingsService;
import com.android.server.pm.UserManagerService;
import com.samsung.android.core.pm.mm.MaintenanceModeNotificationService;
import com.samsung.android.server.pm.PmLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class MaintenanceModeManager$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ MaintenanceModeManager$$ExternalSyntheticLambda3(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                MaintenanceModeManager maintenanceModeManager = (MaintenanceModeManager) obj;
                maintenanceModeManager.getClass();
                SystemProperties.set("persist.sys.is_in_maintenance_mode", Boolean.toString(false));
                maintenanceModeManager.changeUsbDebuggingOption(false);
                maintenanceModeManager.checkPendingAdbProcessing(0, SystemClock.elapsedRealtime() + 15000);
                return;
            case 1:
                final MaintenanceModeManager maintenanceModeManager2 = (MaintenanceModeManager) obj;
                maintenanceModeManager2.getClass();
                ArrayList arrayList = new ArrayList();
                synchronized (maintenanceModeManager2.mLifecycleListeners) {
                    Iterator it = maintenanceModeManager2.mLifecycleListeners.iterator();
                    while (it.hasNext()) {
                        final LockSettingsService.AnonymousClass1 anonymousClass1 = (LockSettingsService.AnonymousClass1) it.next();
                        final CompletableFuture completableFuture = new CompletableFuture();
                        arrayList.add(completableFuture);
                        try {
                            maintenanceModeManager2.logDebugInfoAsync("Start to call onPostprocessing: " + anonymousClass1);
                            Consumer consumer = new Consumer() { // from class: com.samsung.android.server.pm.mm.MaintenanceModeManager$$ExternalSyntheticLambda10
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    MaintenanceModeManager maintenanceModeManager3 = MaintenanceModeManager.this;
                                    LockSettingsService.AnonymousClass1 anonymousClass12 = anonymousClass1;
                                    CompletableFuture completableFuture2 = completableFuture;
                                    Boolean bool = (Boolean) obj2;
                                    maintenanceModeManager3.getClass();
                                    maintenanceModeManager3.logDebugInfoAsync("Received callback: " + anonymousClass12 + " - " + bool);
                                    completableFuture2.complete(bool);
                                }
                            };
                            anonymousClass1.getClass();
                            Slog.i("LockSettingsService", "removeUser for MaintenanceMode");
                            LockSettingsService lockSettingsService = LockSettingsService.this;
                            lockSettingsService.mMaintenanceModeCallback = consumer;
                            lockSettingsService.removeUserState(77);
                            maintenanceModeManager2.logDebugInfoAsync("Finish calling onPostprocessing: " + anonymousClass1);
                        } catch (Exception e) {
                            maintenanceModeManager2.logDebugInfoAsync("Got exception: " + anonymousClass1 + " - " + e.toString());
                            completableFuture.complete(Boolean.FALSE);
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    return;
                }
                try {
                    CompletableFuture.allOf((CompletableFuture[]) arrayList.toArray(new CompletableFuture[arrayList.size()])).get();
                    return;
                } catch (Exception unused) {
                    return;
                }
            case 2:
                MaintenanceModeManager maintenanceModeManager3 = (MaintenanceModeManager) obj;
                UserManagerService userManagerService = maintenanceModeManager3.mUms;
                userManagerService.setUserRestriction("no_sms", true, 77);
                userManagerService.setUserRestriction("no_outgoing_calls", false, 77);
                Settings.Secure.putIntForUser(maintenanceModeManager3.mContext.getContentResolver(), "user_setup_complete", 1, 77);
                Settings.System.putLongForUser(maintenanceModeManager3.mContext.getContentResolver(), "screen_off_timeout", 600000L, 77);
                try {
                    IPackageManager packageManager = ActivityThread.getPackageManager();
                    packageManager.setApplicationEnabledSetting("com.sec.android.app.SecSetupWizard", 2, 0, 77, "MaintenanceMode");
                    packageManager.setApplicationEnabledSetting("com.google.android.setupwizard", 2, 0, 77, "MaintenanceMode");
                    return;
                } catch (Exception e2) {
                    Log.i("MaintenanceMode", "Failed to disable SUW: " + e2.toString());
                    return;
                }
            case 3:
                MaintenanceModeManager maintenanceModeManager4 = (MaintenanceModeManager) obj;
                maintenanceModeManager4.getClass();
                try {
                    ComponentName componentName = new ComponentName("android", MaintenanceModeNotificationService.class.getName());
                    Intent intent = new Intent();
                    intent.setComponent(componentName);
                    maintenanceModeManager4.mContext.startForegroundServiceAsUser(intent, new UserHandle(77));
                } catch (Exception e3) {
                    Log.i("MaintenanceMode", "Failed to register notification: " + e3.toString());
                }
                try {
                    Resources resources = maintenanceModeManager4.mContext.getResources();
                    maintenanceModeManager4.mOverlayView = new TextView(maintenanceModeManager4.mContext);
                    int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.preference_breadcrumbs_padding_start_material);
                    maintenanceModeManager4.mOverlayView.setGravity(17);
                    maintenanceModeManager4.mOverlayView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
                    maintenanceModeManager4.mOverlayView.setBackgroundColor(maintenanceModeManager4.mContext.getColor(R.color.primary_device_default_dark));
                    maintenanceModeManager4.mOverlayView.setTextAppearance(R.style.Theme.DeviceDefault.Dialog.FixedSize);
                    maintenanceModeManager4.mOverlayView.setTextColor(maintenanceModeManager4.mContext.getColor(R.color.primary_device_default_light));
                    maintenanceModeManager4.mOverlayView.setTextSize(0, resources.getDimensionPixelSize(R.dimen.preference_child_padding_side));
                    maintenanceModeManager4.mOverlayView.setText(R.string.permlab_bindCarrierServices);
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    maintenanceModeManager4.mOverlayViewParams = layoutParams;
                    layoutParams.type = 2038;
                    layoutParams.width = -2;
                    layoutParams.height = -2;
                    layoutParams.gravity = 8388691;
                    layoutParams.format = maintenanceModeManager4.mOverlayView.getBackground().getOpacity();
                    WindowManager.LayoutParams layoutParams2 = maintenanceModeManager4.mOverlayViewParams;
                    layoutParams2.flags = 24;
                    layoutParams2.privateFlags |= 536870928;
                    maintenanceModeManager4.mWm = (WindowManager) maintenanceModeManager4.mContext.getSystemService("window");
                } catch (Exception e4) {
                    Log.i("MaintenanceMode", "Failed to make overlay: " + e4.toString());
                }
                maintenanceModeManager4.setOverlayVisibility(true);
                try {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("com.samsung.android.intent.action.HIDE_MAINTENANCE_MODE_MARK");
                    intentFilter.addAction("com.samsung.android.intent.action.SHOW_MAINTENANCE_MODE_MARK");
                    maintenanceModeManager4.mContext.registerReceiverForAllUsers(maintenanceModeManager4.mOverlayReceiver, intentFilter, null, maintenanceModeManager4.mHandler, 2);
                } catch (Exception e5) {
                    Log.i("MaintenanceMode", "Failed to register overlay receiver: " + e5.toString());
                }
                maintenanceModeManager4.changeUsbDebuggingOption(true);
                return;
            default:
                try {
                    PmLog.logDebugInfoAndLogcat((String) obj, "MaintenanceMode");
                    return;
                } catch (Exception unused2) {
                    return;
                }
        }
    }
}
