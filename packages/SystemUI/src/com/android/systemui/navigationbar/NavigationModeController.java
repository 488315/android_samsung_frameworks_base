package com.android.systemui.navigationbar;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.IOverlayManager;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.navigationbar.util.NavigationModeUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.feature.SemCscFeature;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executor;

public final class NavigationModeController implements Dumpable {
    public final Context mContext;
    public Context mCurrentUserContext;
    public boolean mDeviceProvisioned;
    public final AnonymousClass1 mDeviceProvisionedCallback;
    public final DeviceProvisionedControllerImpl mDeviceProvisionedController;
    public final NavBarStore mNavBarStore;
    public final IOverlayManager mOverlayManager;
    public final AnonymousClass3 mReceiver;
    public final Executor mUiBgExecutor;
    public final UserTracker mUserTracker;
    public final UserTracker.Callback mUserTrackerCallback;
    public final ArrayList mListeners = new ArrayList();
    public final ArrayList mOverlayHistoryList = new ArrayList();

    public interface ModeChangedListener {
        void onNavigationModeChanged(int i);
    }

    public enum ModeOverlayReason {
        UPDATE_INTERACTION_MODE_AS_OWNER_USER,
        UPDATE_INTERACTION_MODE_DEVICE_PROVISIONED_COMPLETE,
        /* JADX INFO: Fake field, exist only in values array */
        UPDATE_INTERACTION_MODE_BY_SPLUGIN,
        UPDATE_INTERACTION_MODE_SIMPLIFIED_GESTURE,
        UPDATE_INTERACTION_MODE_GESTURE_BY_DEFAULT
    }

    public NavigationModeController(Context context, ConfigurationController configurationController, UserTracker userTracker, Executor executor, Executor executor2, DumpManager dumpManager, NavBarStore navBarStore, DeviceProvisionedController deviceProvisionedController) {
        DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.navigationbar.NavigationModeController.1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onDeviceProvisionedChanged() {
                if (BasicRune.NAVBAR_SETUP_WIZARD) {
                    NavigationModeController navigationModeController = NavigationModeController.this;
                    boolean z = navigationModeController.mDeviceProvisionedController.deviceProvisioned.get();
                    if (navigationModeController.mDeviceProvisioned != z) {
                        navigationModeController.mDeviceProvisioned = z;
                        String overlayPackage = NavigationModeUtil.getOverlayPackage(navigationModeController.mCurrentUserContext);
                        int i = NavigationModeController.isGestureDefault() ? 2 : 0;
                        String str = NavigationModeController.isGestureDefault() ? QuickStepContract.NAV_BAR_MODE_GESTURAL_OVERLAY : QuickStepContract.NAV_BAR_MODE_3BUTTON_OVERLAY;
                        if (NavigationModeController.getCurrentInteractionMode(navigationModeController.mContext) == i && !str.equals(overlayPackage)) {
                            Log.i("NavigationModeController", "onDeviceProvisionedChanged set to targetPackage by current settings: ".concat(overlayPackage));
                            navigationModeController.setModeOverlay(overlayPackage, -2, ModeOverlayReason.UPDATE_INTERACTION_MODE_DEVICE_PROVISIONED_COMPLETE);
                        }
                        ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnDeviceProvisionedChanged(navigationModeController.mDeviceProvisioned));
                        if (BasicRune.NAVBAR_TASKBAR) {
                            ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable());
                        }
                        ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnNavBarStyleChanged());
                    }
                }
            }
        };
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.navigationbar.NavigationModeController.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onUserChanged: ", "NavigationModeController");
                NavigationModeController navigationModeController = NavigationModeController.this;
                navigationModeController.updateCurrentInteractionMode(true);
                if (BasicRune.NAVBAR_ENABLED) {
                    ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnNavBarStyleChanged());
                    ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnUserSwitched());
                }
            }
        };
        this.mUserTrackerCallback = callback;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.NavigationModeController.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                Log.d("NavigationModeController", "ACTION_OVERLAY_CHANGED");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        };
        this.mContext = context;
        this.mCurrentUserContext = context;
        this.mUserTracker = userTracker;
        ((UserTrackerImpl) userTracker).addCallback(callback, executor);
        this.mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        this.mUiBgExecutor = executor2;
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "NavigationModeController", this);
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) deviceProvisionedController;
        deviceProvisionedControllerImpl.addCallback(deviceProvisionedListener);
        if (!BasicRune.NAVBAR_ENABLED) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.OVERLAY_CHANGED");
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("android", 0);
            context.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, null);
        }
        if (BasicRune.NAVBAR_SETUP_WIZARD) {
            this.mDeviceProvisionedController = deviceProvisionedControllerImpl;
            this.mNavBarStore = navBarStore;
        }
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.navigationbar.NavigationModeController.4
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                Log.d("NavigationModeController", "onOverlayChanged");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        });
        updateCurrentInteractionMode(false);
    }

    public static int getCurrentInteractionMode(Context context) {
        int integer = context.getResources().getInteger(R.integer.config_pinnerWebviewPinBytes);
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(integer, "getCurrentInteractionMode: mode=", " contextUser=");
        m.append(context.getUserId());
        Log.d("NavigationModeController", m.toString());
        return integer;
    }

    public static boolean isGestureDefault() {
        return "CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO")) && SystemProperties.getInt("ro.product.first_api_level", 0) >= 35;
    }

    public final int addListener(ModeChangedListener modeChangedListener) {
        this.mListeners.add(modeChangedListener);
        return getCurrentInteractionMode(this.mCurrentUserContext);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        StringBuilder m = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NavigationModeController:", "  mode=");
        m.append(getCurrentInteractionMode(this.mCurrentUserContext));
        printWriter.println(m.toString());
        try {
            str = String.join(", ", this.mOverlayManager.getDefaultOverlayPackages());
        } catch (RemoteException unused) {
            str = "failed_to_fetch";
        }
        UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "  defaultOverlays=", str);
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
            int size = this.mOverlayHistoryList.size();
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m("  mOverlayHistoryList.size=", size, printWriter);
            for (int i = 0; i < size; i++) {
                printWriter.println("    [" + i + "] " + ((String) this.mOverlayHistoryList.get(i)));
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
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (BasicRune.NAVBAR_ADDITIONAL_LOG && userId != this.mContext.getUserId()) {
            Log.d("NavigationModeController", "getCurrentUserContext: contextUser=" + this.mContext.getUserId() + " currentUser=" + userId);
        }
        if (this.mContext.getUserId() == userId) {
            return this.mContext;
        }
        try {
            Context context = this.mContext;
            return context.createPackageContextAsUser(context.getPackageName(), 0, UserHandle.of(userId));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("NavigationModeController", "Failed to create package context", e);
            return null;
        }
    }

    public final void removeListener(ModeChangedListener modeChangedListener) {
        this.mListeners.remove(modeChangedListener);
    }

    public final void setModeOverlay(final String str, final int i, ModeOverlayReason modeOverlayReason) {
        if (BasicRune.NAVBAR_GESTURE) {
            StringBuilder sb = new StringBuilder();
            sb.append(" UserId=" + i);
            sb.append(" OverlayPkg=".concat(str));
            sb.append(" OverlayReason=" + modeOverlayReason);
            ArrayList arrayList = this.mOverlayHistoryList;
            StringBuilder sb2 = new StringBuilder();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            sb2.append(String.format("%02d:%02d:%02d.%03d", Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14))));
            sb2.append((Object) sb);
            arrayList.add(sb2.toString());
            while (this.mOverlayHistoryList.size() > 30) {
                this.mOverlayHistoryList.remove(0);
            }
        }
        try {
            this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    NavigationModeController navigationModeController = NavigationModeController.this;
                    String str2 = str;
                    int i2 = i;
                    navigationModeController.getClass();
                    try {
                        if (BasicRune.NAVBAR_ENABLED_HARD_KEY && QuickStepContract.NAV_BAR_MODE_3BUTTON_OVERLAY.equals(str2)) {
                            navigationModeController.mOverlayManager.setEnabled(NavigationModeUtil.getGestureOverlayPackageName(navigationModeController.mContext), false, i2);
                        } else {
                            navigationModeController.mOverlayManager.setEnabledExclusiveInCategory(str2, i2);
                        }
                        Log.d("NavigationModeController", "setModeOverlay: overlayPackage=" + str2 + " userId=" + i2);
                    } catch (RemoteException unused) {
                        Log.e("NavigationModeController", "Failed to enable overlay " + str2 + " for user " + i2);
                    }
                }
            });
        } catch (Exception e) {
            Log.d("NavigationModeController", "Failed to setModeOverlay: ");
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00e7 A[Catch: Exception -> 0x0083, TryCatch #1 {Exception -> 0x0083, blocks: (B:12:0x003a, B:14:0x007e, B:15:0x0086, B:17:0x0091, B:21:0x00a5, B:24:0x00b8, B:26:0x00d4, B:28:0x00da, B:30:0x00e7, B:32:0x00ef, B:34:0x00f7, B:36:0x0133, B:38:0x00ff, B:41:0x0127), top: B:11:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCurrentInteractionMode(boolean r11) {
        /*
            Method dump skipped, instructions count: 447
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.NavigationModeController.updateCurrentInteractionMode(boolean):void");
    }
}
