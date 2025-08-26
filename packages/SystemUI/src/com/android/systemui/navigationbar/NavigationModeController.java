package com.android.systemui.navigationbar;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.content.pm.PackageManager;
import android.content.res.ApkAssets;
import android.content.res.Resources;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavigationModeController;
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
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.feature.SemCscFeature;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes2.dex */
public class NavigationModeController implements Dumpable {
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
        UPDATE_INTERACTION_MODE_GESTURE_BY_DEFAULT,
        UPDATE_INTERACTION_MODE_FAILED_BY_EXCEPTION
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.navigationbar.NavigationModeController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.BroadcastReceiver, com.android.systemui.navigationbar.NavigationModeController$3] */
    public NavigationModeController(Context context, ConfigurationController configurationController, UserTracker userTracker, Executor executor, Executor executor2, DumpManager dumpManager, NavBarStore navBarStore, DeviceProvisionedController deviceProvisionedController) throws Resources.NotFoundException {
        ?? r0 = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.navigationbar.NavigationModeController.1
            @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
            public final void onDeviceProvisionedChanged() {
                if (BasicRune.NAVBAR_SETUP_WIZARD) {
                    NavigationModeController navigationModeController = NavigationModeController.this;
                    boolean z = navigationModeController.mDeviceProvisionedController.deviceProvisioned.get();
                    if (navigationModeController.mDeviceProvisioned != z) {
                        navigationModeController.mDeviceProvisioned = z;
                        String overlayPackage = NavigationModeUtil.getOverlayPackage(navigationModeController.mCurrentUserContext);
                        int i = NavigationModeController.isGestureDefault() ? 2 : 0;
                        String str = NavigationModeController.isGestureDefault() ? "com.android.internal.systemui.navbar.gestural" : "com.android.internal.systemui.navbar.threebutton";
                        if (NavigationModeController.getCurrentInteractionMode(navigationModeController.mContext) == i && !str.equals(overlayPackage)) {
                            Log.i("NavigationModeController", "onDeviceProvisionedChanged set to targetPackage by current settings: ".concat(overlayPackage));
                            navigationModeController.setModeOverlay(-2, ModeOverlayReason.UPDATE_INTERACTION_MODE_DEVICE_PROVISIONED_COMPLETE, overlayPackage);
                        }
                        ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnDeviceProvisionedChanged(navigationModeController.mDeviceProvisioned));
                        ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnNavBarStyleChanged(Settings.Secure.getIntForUser(navigationModeController.mContext.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) == 1));
                        if (BasicRune.NAVBAR_TASKBAR) {
                            ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnUpdateTaskbarAvailable());
                        }
                    }
                }
            }
        };
        this.mDeviceProvisionedCallback = r0;
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.navigationbar.NavigationModeController.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) throws Resources.NotFoundException {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onUserChanged: ", "NavigationModeController");
                NavigationModeController navigationModeController = NavigationModeController.this;
                navigationModeController.updateCurrentInteractionMode(true);
                if (BasicRune.NAVBAR_ENABLED) {
                    ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnNavBarStyleChanged(Settings.Secure.getIntForUser(navigationModeController.mContext.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0, -2) == 1));
                    ((NavBarStoreImpl) navigationModeController.mNavBarStore).handleEvent(navigationModeController, new EventTypeFactory.EventType.OnUserSwitched());
                }
            }
        };
        this.mUserTrackerCallback = callback;
        ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.navigationbar.NavigationModeController.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) throws Resources.NotFoundException {
                Log.d("NavigationModeController", "ACTION_OVERLAY_CHANGED");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        };
        this.mReceiver = r3;
        this.mContext = context;
        this.mCurrentUserContext = context;
        this.mUserTracker = userTracker;
        ((UserTrackerImpl) userTracker).addCallback(callback, executor);
        this.mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        this.mUiBgExecutor = executor2;
        String simpleName = getClass().getSimpleName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, simpleName, this);
        DeviceProvisionedControllerImpl deviceProvisionedControllerImpl = (DeviceProvisionedControllerImpl) deviceProvisionedController;
        deviceProvisionedControllerImpl.addCallback(r0);
        if (!BasicRune.NAVBAR_ENABLED) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.OVERLAY_CHANGED");
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("android", 0);
            context.registerReceiverAsUser(r3, UserHandle.ALL, intentFilter, null, null);
        }
        if (BasicRune.NAVBAR_SETUP_WIZARD) {
            this.mDeviceProvisionedController = deviceProvisionedControllerImpl;
            this.mNavBarStore = navBarStore;
        }
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.navigationbar.NavigationModeController.4
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() throws Resources.NotFoundException {
                Log.d("NavigationModeController", "onOverlayChanged");
                NavigationModeController.this.updateCurrentInteractionMode(true);
            }
        });
        updateCurrentInteractionMode(false);
    }

    public static int getCurrentInteractionMode(Context context) throws Resources.NotFoundException {
        int integer = context.getResources().getInteger(R.integer.config_screenTimeoutOverride);
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(integer, "getCurrentInteractionMode: mode=", " contextUser=");
        sbM.append(context.getUserId());
        Log.d("NavigationModeController", sbM.toString());
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
        String strJoin;
        StringBuilder sbM = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "NavigationModeController:", "  mode=");
        sbM.append(getCurrentInteractionMode(this.mCurrentUserContext));
        printWriter.println(sbM.toString());
        try {
            strJoin = String.join(", ", this.mOverlayManager.getDefaultOverlayPackages());
        } catch (RemoteException unused) {
            strJoin = "failed_to_fetch";
        }
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "  defaultOverlays=", strJoin);
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
            DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  mOverlayHistoryList.size=", size, printWriter);
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

    public final void makeOverlayHistory(int i, ModeOverlayReason modeOverlayReason, String str) {
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

    public final void removeListener(ModeChangedListener modeChangedListener) {
        this.mListeners.remove(modeChangedListener);
    }

    public final void setModeOverlay(final int i, ModeOverlayReason modeOverlayReason, final String str) {
        if (BasicRune.NAVBAR_GESTURE) {
            makeOverlayHistory(i, modeOverlayReason, str);
        }
        try {
            this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    NavigationModeController navigationModeController = this.f$0;
                    String str2 = str;
                    int i2 = i;
                    navigationModeController.getClass();
                    try {
                        if (BasicRune.NAVBAR_ENABLED_HARD_KEY && "com.android.internal.systemui.navbar.threebutton".equals(str2)) {
                            navigationModeController.mOverlayManager.setEnabled(NavigationModeUtil.getGestureOverlayPackageName(navigationModeController.mContext), false, i2);
                        } else {
                            navigationModeController.mOverlayManager.setEnabledExclusiveInCategory(str2, i2);
                        }
                        Log.d("NavigationModeController", "setModeOverlay: overlayPackage=" + str2 + " userId=" + i2);
                    } catch (Exception e) {
                        Log.e("NavigationModeController", "Failed to enable overlay " + str2 + " for user " + i2);
                        EmergencyButton$$ExternalSyntheticOutline0.m("caused by: ", e, "NavigationModeController");
                        navigationModeController.makeOverlayHistory(i2, NavigationModeController.ModeOverlayReason.UPDATE_INTERACTION_MODE_FAILED_BY_EXCEPTION, str2);
                    }
                }
            });
        } catch (Exception e) {
            Log.d("NavigationModeController", "Failed to setModeOverlay: ");
            e.printStackTrace();
        }
    }

    public final void updateCurrentInteractionMode(boolean z) throws Resources.NotFoundException {
        boolean z2 = BasicRune.NAVBAR_ENABLED;
        if (z2 && this.mContext.getUserId() != 0) {
            Log.d("NavigationModeController", "Skip updateCurrentInteractionMode for userId=" + this.mContext.getUserId());
            return;
        }
        Trace.beginSection("NMC#updateCurrentInteractionMode");
        this.mCurrentUserContext = getCurrentUserContext();
        if (z2) {
            try {
                final int i = 0;
                Stream streamFilter = this.mOverlayManager.getOverlayInfosForTarget("android", this.mContext.getUserId()).stream().filter(new Predicate() { // from class: com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        OverlayInfo overlayInfo = (OverlayInfo) obj;
                        switch (i) {
                            case 0:
                                return "com.android.internal.navigation_bar_mode".equals(overlayInfo.getCategory());
                            default:
                                return overlayInfo.isEnabled();
                        }
                    }
                });
                final int i2 = 1;
                OverlayInfo overlayInfo = (OverlayInfo) streamFilter.filter(new Predicate() { // from class: com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        OverlayInfo overlayInfo2 = (OverlayInfo) obj;
                        switch (i2) {
                            case 0:
                                return "com.android.internal.navigation_bar_mode".equals(overlayInfo2.getCategory());
                            default:
                                return overlayInfo2.isEnabled();
                        }
                    }
                }).findFirst().orElse(null);
                Log.d("NavigationModeController", "migrateNavigationBarIfNecessary currentOverlayInfo=" + overlayInfo);
                String packageName = overlayInfo != null ? overlayInfo.getPackageName() : "";
                int userId = this.mContext.getUserId();
                boolean z3 = (BasicRune.NAVBAR_SIMPLIFIED_GESTURE && (Settings.Global.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_NAVIGATIONBAR_SPLUGIN_FLAGS, 0) & 4) == 0) ? false : true;
                boolean zIsGestureDefault = isGestureDefault();
                StringBuilder sb = new StringBuilder("migrateNavBar: currentPackageName=");
                sb.append(packageName.isEmpty() ? "empty" : packageName);
                sb.append(", isSupportLegacyGestureOptions=");
                sb.append(z3);
                sb.append(", isChinaModel=");
                sb.append(zIsGestureDefault);
                Log.d("NavigationModeController", sb.toString());
                if (zIsGestureDefault && packageName.isEmpty()) {
                    setModeOverlay(userId, ModeOverlayReason.UPDATE_INTERACTION_MODE_GESTURE_BY_DEFAULT, NavigationModeUtil.getOverlayPackage(this.mContext));
                }
                if (!z3) {
                    if ("com.samsung.internal.systemui.navbar.sec_gestural".equals(packageName) || "com.samsung.internal.systemui.navbar.sec_gestural_no_hint".equals(packageName) || "com.samsung.internal.systemui.navbar.gestural_no_hint".equals(packageName)) {
                        Settings.Global.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, zIsGestureDefault ? 1 : 0);
                        Settings.Global.putInt(this.mContext.getContentResolver(), "navigation_bar_gesture_detail_type", 1);
                        Settings.Global.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_NAVIGATIONBAR_GESTURE_HINT, 1);
                        String str = zIsGestureDefault ? "com.android.internal.systemui.navbar.gestural" : "com.android.internal.systemui.navbar.threebutton";
                        setModeOverlay(userId, ModeOverlayReason.UPDATE_INTERACTION_MODE_SIMPLIFIED_GESTURE, str);
                        Log.d("NavigationModeController", "migrateNavBar: targetPackage=".concat(str));
                    }
                    Settings.Global.putInt(this.mContext.getContentResolver(), "sem_bottom_gesture_restored", 0);
                }
            } catch (Exception e) {
                Log.d("NavigationModeController", "Failed to migrate navigation bar overlay package:");
                e.printStackTrace();
            }
            if (this.mContext.getUserId() != this.mCurrentUserContext.getUserId()) {
                Log.d("NavigationModeController", "updateCurrentInteractionMode() : Overlay guest's package as owner's package");
                try {
                    setModeOverlay(this.mCurrentUserContext.getUserId(), ModeOverlayReason.UPDATE_INTERACTION_MODE_AS_OWNER_USER, NavigationModeUtil.getOverlayPackage(this.mContext));
                } catch (Exception unused) {
                    Log.e("NavigationModeController", "unexpected error while running updateCurrentInteractionMode()");
                }
            }
        }
        final int currentInteractionMode = getCurrentInteractionMode(BasicRune.NAVBAR_ENABLED ? this.mContext : this.mCurrentUserContext);
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationModeController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Settings.Secure.putString(this.f$0.mCurrentUserContext.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_MODE, String.valueOf(currentInteractionMode));
            }
        });
        Log.d("NavigationModeController", "updateCurrentInteractionMode: mode=" + currentInteractionMode);
        dumpAssetPaths(this.mCurrentUserContext);
        if (z) {
            for (int i3 = 0; i3 < this.mListeners.size(); i3++) {
                ((ModeChangedListener) this.mListeners.get(i3)).onNavigationModeChanged(currentInteractionMode);
            }
        }
        Trace.endSection();
    }
}
