package com.android.systemui.navigationbar;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import com.android.keyguard.FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavigationModeController implements Dumpable {
    public final Context mContext;
    public Context mCurrentUserContext;
    public boolean mDeviceProvisioned;
    public final C18601 mDeviceProvisionedCallback;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final NavBarStore mNavBarStore;
    public final IOverlayManager mOverlayManager;
    public final C18612 mReceiver;
    public final Executor mUiBgExecutor;
    public final ArrayList mListeners = new ArrayList();
    public final ArrayList mOverlayHistoryList = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ModeChangedListener {
        void onNavigationModeChanged(int i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ModeOverlayReason {
        UPDATE_INTERACTION_MODE_AS_OWNER_USER,
        UPDATE_INTERACTION_MODE_DEVICE_PROVISIONED_COMPLETE,
        /* JADX INFO: Fake field, exist only in values array */
        UPDATE_INTERACTION_MODE_BY_SPLUGIN
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.navigationbar.NavigationModeController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.NavigationModeController$2] */
    public NavigationModeController(Context context, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, Executor executor, DumpManager dumpManager, NavBarStore navBarStore) {
        ?? r0 = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.navigationbar.NavigationModeController.1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onDeviceProvisionedChanged() {
                boolean z = BasicRune.NAVBAR_ENABLED;
                NavigationModeController navigationModeController = NavigationModeController.this;
                if (z) {
                    boolean isDeviceProvisioned = ((DeviceProvisionedControllerImpl) navigationModeController.mDeviceProvisionedController).isDeviceProvisioned();
                    if (navigationModeController.mDeviceProvisioned != isDeviceProvisioned) {
                        navigationModeController.mDeviceProvisioned = isDeviceProvisioned;
                        Context context2 = navigationModeController.mCurrentUserContext;
                        NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
                        String gestureOverlayPackageName = Settings.Global.getInt(context2.getContentResolver(), "navigation_bar_gesture_while_hidden", 0) == 0 ? QuickStepContract.NAV_BAR_MODE_3BUTTON_OVERLAY : NavigationModeUtil.getGestureOverlayPackageName(context2);
                        if (NavigationModeController.getCurrentInteractionMode(navigationModeController.mContext) == 0 && !QuickStepContract.NAV_BAR_MODE_3BUTTON_OVERLAY.equals(gestureOverlayPackageName)) {
                            try {
                                navigationModeController.setModeOverlay(-2, ModeOverlayReason.UPDATE_INTERACTION_MODE_DEVICE_PROVISIONED_COMPLETE, gestureOverlayPackageName);
                            } catch (Exception e) {
                                Log.d("NavigationModeController", "Failed to setModeOverlay: ");
                                e.printStackTrace();
                            }
                        }
                    }
                    EventTypeFactory.EventType.OnDeviceProvisionedChanged onDeviceProvisionedChanged = new EventTypeFactory.EventType.OnDeviceProvisionedChanged(navigationModeController.mDeviceProvisioned);
                    NavBarStore navBarStore2 = navigationModeController.mNavBarStore;
                    ((NavBarStoreImpl) navBarStore2).handleEvent(navigationModeController, onDeviceProvisionedChanged);
                    ((NavBarStoreImpl) navBarStore2).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnNavBarStyleChanged());
                }
                if (BasicRune.NAVBAR_SUPPORT_TASKBAR) {
                    ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable());
                }
            }

            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onUserSwitched() {
                StringBuilder sb = new StringBuilder("onUserSwitched: ");
                ActivityManagerWrapper.sInstance.getClass();
                sb.append(ActivityManagerWrapper.getCurrentUserId());
                Log.d("NavigationModeController", sb.toString());
                NavigationModeController navigationModeController = NavigationModeController.this;
                navigationModeController.updateCurrentInteractionMode(true);
                if (BasicRune.NAVBAR_ENABLED) {
                    EventTypeFactory.EventType.OnNavBarStyleChanged onNavBarStyleChanged = new EventTypeFactory.EventType.OnNavBarStyleChanged();
                    NavBarStore navBarStore2 = navigationModeController.mNavBarStore;
                    ((NavBarStoreImpl) navBarStore2).handleEvent(navigationModeController, onNavBarStyleChanged);
                    ((NavBarStoreImpl) navBarStore2).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnUserSwitched());
                }
            }
        };
        this.mDeviceProvisionedCallback = r0;
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.NavigationModeController.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Log.d("NavigationModeController", "ACTION_OVERLAY_CHANGED");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        };
        this.mReceiver = r2;
        this.mContext = context;
        this.mCurrentUserContext = context;
        this.mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        this.mUiBgExecutor = executor;
        boolean z = BasicRune.NAVBAR_ENABLED;
        if (z) {
            this.mDeviceProvisionedController = deviceProvisionedController;
            this.mNavBarStore = navBarStore;
        }
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "NavigationModeController", this);
        ((DeviceProvisionedControllerImpl) deviceProvisionedController).addCallback(r0);
        if (!z) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.OVERLAY_CHANGED");
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("android", 0);
            context.registerReceiverAsUser(r2, UserHandle.ALL, intentFilter, null, null);
        }
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.navigationbar.NavigationModeController.3
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                Log.d("NavigationModeController", "onOverlayChanged");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        });
        updateCurrentInteractionMode(false);
    }

    public static int getCurrentInteractionMode(Context context) {
        int integer = context.getResources().getInteger(R.integer.config_notificationsBatteryFullARGB);
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("getCurrentInteractionMode: mode=", integer, " contextUser=");
        m1m.append(context.getUserId());
        Log.d("NavigationModeController", m1m.toString());
        return integer;
    }

    public final int addListener(ModeChangedListener modeChangedListener) {
        this.mListeners.add(modeChangedListener);
        return getCurrentInteractionMode(this.mCurrentUserContext);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "NavigationModeController:", "  mode=");
        m75m.append(getCurrentInteractionMode(this.mCurrentUserContext));
        printWriter.println(m75m.toString());
        try {
            str = String.join(", ", this.mOverlayManager.getDefaultOverlayPackages());
        } catch (RemoteException unused) {
            str = "failed_to_fetch";
        }
        FaceWakeUpTriggersConfig$$ExternalSyntheticOutline0.m60m("  defaultOverlays=", str, printWriter);
        if (BasicRune.NAVBAR_ENABLED) {
            printWriter.println("    contextUser=" + this.mCurrentUserContext.getUserId());
            printWriter.println("    assetPaths=");
            for (ApkAssets apkAssets : this.mCurrentUserContext.getResources().getAssets().getApkAssets()) {
                printWriter.println("      " + apkAssets.getDebugName());
            }
        } else {
            dumpAssetPaths(this.mCurrentUserContext);
        }
        if (BasicRune.NAVBAR_GESTURE) {
            ArrayList arrayList = this.mOverlayHistoryList;
            int size = arrayList.size();
            SideFpsController$$ExternalSyntheticOutline0.m105m("  mOverlayHistoryList.size=", size, printWriter);
            for (int i = 0; i < size; i++) {
                printWriter.println("    [" + i + "] " + ((String) arrayList.get(i)));
            }
        }
    }

    public final void dumpAssetPaths(Context context) {
        Log.d("NavigationModeController", "  contextUser=" + this.mCurrentUserContext.getUserId());
        Log.d("NavigationModeController", "  assetPaths=");
        for (ApkAssets apkAssets : context.getResources().getAssets().getApkAssets()) {
            Log.d("NavigationModeController", "    " + apkAssets.getDebugName());
        }
    }

    public final Context getCurrentUserContext() {
        ActivityManagerWrapper.sInstance.getClass();
        int currentUserId = ActivityManagerWrapper.getCurrentUserId();
        StringBuilder sb = new StringBuilder("getCurrentUserContext: contextUser=");
        Context context = this.mContext;
        sb.append(context.getUserId());
        sb.append(" currentUser=");
        sb.append(currentUserId);
        Log.d("NavigationModeController", sb.toString());
        if (context.getUserId() == currentUserId) {
            return context;
        }
        try {
            return context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.of(currentUserId));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("NavigationModeController", "Failed to create package context", e);
            return null;
        }
    }

    public final void removeListener(ModeChangedListener modeChangedListener) {
        this.mListeners.remove(modeChangedListener);
    }

    public final void setModeOverlay(int i, ModeOverlayReason modeOverlayReason, String str) {
        if (BasicRune.NAVBAR_GESTURE) {
            StringBuilder sb = new StringBuilder();
            sb.append(" UserId=" + i);
            sb.append(" OverlayPkg=".concat(str));
            sb.append(" OverlayReason=" + modeOverlayReason);
            StringBuilder sb2 = new StringBuilder();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            sb2.append(String.format("%02d:%02d:%02d.%03d", Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14))));
            sb2.append(sb.toString());
            String sb3 = sb2.toString();
            ArrayList arrayList = this.mOverlayHistoryList;
            arrayList.add(sb3);
            while (arrayList.size() > 30) {
                arrayList.remove(0);
            }
        }
        try {
            this.mUiBgExecutor.execute(new NavigationModeController$$ExternalSyntheticLambda2(this, str, i));
        } catch (Exception e) {
            Log.d("NavigationModeController", "Failed to setModeOverlay: ");
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x011f A[Catch: Exception -> 0x0126, TRY_LEAVE, TryCatch #0 {Exception -> 0x0126, blocks: (B:28:0x0081, B:30:0x00b3, B:32:0x00ce, B:35:0x0106, B:37:0x010e, B:39:0x0112, B:41:0x011f, B:43:0x00d6, B:46:0x00df, B:47:0x00ee, B:49:0x00f2, B:50:0x00e7), top: B:27:0x0081 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateCurrentInteractionMode(boolean z) {
        boolean z2;
        boolean z3 = BasicRune.NAVBAR_ENABLED;
        Context context = this.mContext;
        if (z3 && context.getUserId() != 0) {
            Log.d("NavigationModeController", "Skip updateCurrentInteractionMode for userId=" + context.getUserId());
            return;
        }
        this.mCurrentUserContext = getCurrentUserContext();
        String str = QuickStepContract.NAV_BAR_MODE_3BUTTON_OVERLAY;
        final int i = 0;
        if (z3 && context.getUserId() != this.mCurrentUserContext.getUserId()) {
            Log.d("NavigationModeController", "updateCurrentInteractionMode() : Overlay guest's package as owner's package");
            NavigationModeUtil navigationModeUtil = NavigationModeUtil.INSTANCE;
            try {
                setModeOverlay(this.mCurrentUserContext.getUserId(), ModeOverlayReason.UPDATE_INTERACTION_MODE_AS_OWNER_USER, Settings.Global.getInt(context.getContentResolver(), "navigation_bar_gesture_while_hidden", 0) == 0 ? QuickStepContract.NAV_BAR_MODE_3BUTTON_OVERLAY : NavigationModeUtil.getGestureOverlayPackageName(context));
            } catch (Exception unused) {
                Log.e("NavigationModeController", "unexpected error while running updateCurrentInteractionMode()");
            }
        }
        final int currentInteractionMode = BasicRune.NAVBAR_ENABLED ? getCurrentInteractionMode(context) : getCurrentInteractionMode(this.mCurrentUserContext);
        boolean z4 = BasicRune.NAVBAR_SIMPLIFIED_GESTURE;
        Executor executor = this.mUiBgExecutor;
        if (z4 && currentInteractionMode != 0) {
            try {
                final int i2 = 1;
                OverlayInfo overlayInfo = (OverlayInfo) this.mOverlayManager.getOverlayInfosForTarget("android", this.mCurrentUserContext.getUserId()).stream().filter(new Predicate() { // from class: com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i) {
                            case 0:
                                return "com.android.internal.navigation_bar_mode".equals(((OverlayInfo) obj).getCategory());
                            default:
                                return ((OverlayInfo) obj).isEnabled();
                        }
                    }
                }).filter(new Predicate() { // from class: com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i2) {
                            case 0:
                                return "com.android.internal.navigation_bar_mode".equals(((OverlayInfo) obj).getCategory());
                            default:
                                return ((OverlayInfo) obj).isEnabled();
                        }
                    }
                }).findFirst().orElse(null);
                if (overlayInfo != null) {
                    String packageName = overlayInfo.getPackageName();
                    int userId = this.mCurrentUserContext.getUserId();
                    ContentResolver contentResolver = context.getContentResolver();
                    int i3 = Settings.Global.getInt(contentResolver, "navigationbar_splugin_flags", 0);
                    if ("com.samsung.internal.systemui.navbar.sec_gestural".equals(packageName) || "com.samsung.internal.systemui.navbar.sec_gestural_no_hint".equals(packageName)) {
                        if ("com.samsung.internal.systemui.navbar.sec_gestural".equals(packageName)) {
                            Settings.Global.putInt(context.getContentResolver(), "sem_bottom_gesture_restored", 1);
                        } else {
                            Settings.Global.putInt(context.getContentResolver(), "sem_bottom_gesture_restored", 0);
                        }
                        if ((i3 & 4) == 0) {
                            executor.execute(new NavigationModeController$$ExternalSyntheticLambda2(this, str, userId));
                            Settings.Global.putInt(contentResolver, "navigation_bar_gesture_while_hidden", 0);
                            Settings.Global.putInt(contentResolver, "navigation_bar_gesture_detail_type", 1);
                            z2 = true;
                            if ("com.samsung.internal.systemui.navbar.gestural_no_hint".equals(packageName) && (i3 & 4) == 0) {
                                executor.execute(new NavigationModeController$$ExternalSyntheticLambda2(this, QuickStepContract.NAV_BAR_MODE_GESTURAL_OVERLAY, userId));
                                z2 = true;
                            }
                            if (z2) {
                                Settings.Global.putInt(contentResolver, "navigation_bar_gesture_hint", 1);
                            }
                        }
                    }
                    z2 = false;
                    if ("com.samsung.internal.systemui.navbar.gestural_no_hint".equals(packageName)) {
                        executor.execute(new NavigationModeController$$ExternalSyntheticLambda2(this, QuickStepContract.NAV_BAR_MODE_GESTURAL_OVERLAY, userId));
                        z2 = true;
                    }
                    if (z2) {
                    }
                }
            } catch (Exception e) {
                Log.d("NavigationModeController", "Failed to migrate navigation bar overlay package:");
                e.printStackTrace();
            }
        }
        executor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Settings.Secure.putString(NavigationModeController.this.mCurrentUserContext.getContentResolver(), "navigation_mode", String.valueOf(currentInteractionMode));
            }
        });
        Log.d("NavigationModeController", "updateCurrentInteractionMode: mode=" + currentInteractionMode);
        dumpAssetPaths(this.mCurrentUserContext);
        if (!z) {
            return;
        }
        while (true) {
            ArrayList arrayList = this.mListeners;
            if (i >= arrayList.size()) {
                return;
            }
            ((ModeChangedListener) arrayList.get(i)).onNavigationModeChanged(currentInteractionMode);
            i++;
        }
    }
}
