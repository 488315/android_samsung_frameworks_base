package com.android.systemui.statusbar.connectivity;

import android.content.Intent;
import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.tiles.detail.WifiDetailAdapter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.util.Utils;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda13;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AccessPointControllerImpl implements AccessPointController, WifiPickerTracker.WifiPickerTrackerCallback, LifecycleOwner {
    public static final boolean DEBUG = Log.isLoggable("AccessPointController", 3);
    public static final int[][] ICONS_WIFI;
    public static final int[][] ICONS_WIFI5;
    public static final int[][] ICONS_WIFI6;
    public static final int[][] ICONS_WIFI6E;
    public static final int[][] ICONS_WIFI7;
    public int mCurrentUser;
    public final Executor mMainExecutor;
    public final SemWifiManager mSemWifiManager;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public WifiPickerTracker mWifiPickerTracker;
    public final WifiPickerTrackerFactory mWifiPickerTrackerFactory;
    public final ArrayList mCallbacks = new ArrayList();
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);
    public final ArrayList mWifiApBleCallbacks = new ArrayList();
    public final AnonymousClass1 mSemWifiApSmartCallback = new SemWifiManager.SemWifiApSmartCallback() { // from class: com.android.systemui.statusbar.connectivity.AccessPointControllerImpl.1
        public final void onStateChanged(int i, String str) {
            SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "WifiApSmartCallback`s onStateChanged() : mhsMac -> ", str, " state -> ", "AccessPointController.AutoHotspot");
            Iterator it = AccessPointControllerImpl.this.mWifiApBleCallbacks.iterator();
            while (it.hasNext()) {
                WifiDetailAdapter wifiDetailAdapter = (WifiDetailAdapter) ((AccessPointController.WifiApBleStateChangeCallback) it.next());
                wifiDetailAdapter.getClass();
                if (Utils.SPF_SupportMobileApEnhanced || Utils.SPF_SupportMobileApEnhancedLite || Utils.SPF_SupportMobileApEnhancedWifiOnlyLite) {
                    SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "onWifiApBleStateChanged() : mhsMac -> ", str, " state -> ", "WifiDetailAdapter.AutoHotspot");
                    if (str != null && i <= 0) {
                        Log.d("WifiDetailAdapter.AutoHotspot", "onWifiApBleStateChanged() - Triggering updateHotspotItems for connection time out with mhsMac-> ".concat(str));
                        wifiDetailAdapter.updateHotspotItems();
                    }
                }
            }
        }
    };
    public final AnonymousClass2 mConnectCallback = new WifiEntry.ConnectCallback() { // from class: com.android.systemui.statusbar.connectivity.AccessPointControllerImpl.2
        @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
        public final void onConnectResult(int i) {
            if (i == 0) {
                if (AccessPointControllerImpl.DEBUG) {
                    Log.d("AccessPointController", "connect success");
                }
            } else if (AccessPointControllerImpl.DEBUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "connect failure reason=", "AccessPointController");
            }
        }
    };

    static {
        int[] iArr = WifiIcons.WIFI_FULL_ICONS;
        ICONS_WIFI = new int[][]{new int[]{R.drawable.sec_ic_wifi_signal_0, R.drawable.sec_ic_wifi_signal_lock_0}, new int[]{R.drawable.sec_ic_wifi_signal_1, R.drawable.sec_ic_wifi_signal_lock_1}, new int[]{R.drawable.sec_ic_wifi_signal_2, R.drawable.sec_ic_wifi_signal_lock_2}, new int[]{R.drawable.sec_ic_wifi_signal_3, R.drawable.sec_ic_wifi_signal_lock_3}, new int[]{R.drawable.sec_ic_wifi_signal_4, R.drawable.sec_ic_wifi_signal_lock_4}};
        ICONS_WIFI5 = new int[][]{new int[]{R.drawable.sec_ic_wifi_signal_wifi5_0, R.drawable.sec_ic_wifi_signal_lock_wifi5_0}, new int[]{R.drawable.sec_ic_wifi_signal_wifi5_1, R.drawable.sec_ic_wifi_signal_lock_wifi5_1}, new int[]{R.drawable.sec_ic_wifi_signal_wifi5_2, R.drawable.sec_ic_wifi_signal_lock_wifi5_2}, new int[]{R.drawable.sec_ic_wifi_signal_wifi5_3, R.drawable.sec_ic_wifi_signal_lock_wifi5_3}, new int[]{R.drawable.sec_ic_wifi_signal_wifi5_4, R.drawable.sec_ic_wifi_signal_lock_wifi5_4}};
        ICONS_WIFI6 = new int[][]{new int[]{R.drawable.sec_ic_wifi_signal_wifi6_0, R.drawable.sec_ic_wifi_signal_lock_wifi6_0}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6_1, R.drawable.sec_ic_wifi_signal_lock_wifi6_1}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6_2, R.drawable.sec_ic_wifi_signal_lock_wifi6_2}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6_3, R.drawable.sec_ic_wifi_signal_lock_wifi6_3}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6_4, R.drawable.sec_ic_wifi_signal_lock_wifi6_4}};
        ICONS_WIFI6E = new int[][]{new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_0, R.drawable.sec_ic_wifi_signal_lock_wifi6e_0}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_1, R.drawable.sec_ic_wifi_signal_lock_wifi6e_1}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_2, R.drawable.sec_ic_wifi_signal_lock_wifi6e_2}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_3, R.drawable.sec_ic_wifi_signal_lock_wifi6e_3}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_4, R.drawable.sec_ic_wifi_signal_lock_wifi6e_4}};
        ICONS_WIFI7 = new int[][]{new int[]{R.drawable.sec_ic_wifi7_signal_0, R.drawable.sec_ic_wifi7_lock_signal_0}, new int[]{R.drawable.sec_ic_wifi7_signal_1, R.drawable.sec_ic_wifi7_lock_signal_1}, new int[]{R.drawable.sec_ic_wifi7_signal_2, R.drawable.sec_ic_wifi7_lock_signal_2}, new int[]{R.drawable.sec_ic_wifi7_signal_3, R.drawable.sec_ic_wifi7_lock_signal_3}, new int[]{R.drawable.sec_ic_wifi7_signal_4, R.drawable.sec_ic_wifi7_lock_signal_4}};
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.connectivity.AccessPointControllerImpl$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.connectivity.AccessPointControllerImpl$2] */
    public AccessPointControllerImpl(UserManager userManager, UserTracker userTracker, Executor executor, WifiPickerTrackerFactory wifiPickerTrackerFactory) {
        this.mUserManager = userManager;
        this.mUserTracker = userTracker;
        this.mCurrentUser = ((UserTrackerImpl) userTracker).getUserId();
        this.mMainExecutor = executor;
        this.mWifiPickerTrackerFactory = wifiPickerTrackerFactory;
        executor.execute(new AccessPointControllerImpl$$ExternalSyntheticLambda0(this, 3));
        this.mSemWifiManager = (SemWifiManager) wifiPickerTrackerFactory.context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    public static boolean isOpenNetwork(WifiEntry wifiEntry) {
        return wifiEntry.getSecurity() == 0 || wifiEntry.getSecurity() == 4;
    }

    public final void addAccessPointCallback(AccessPointController.AccessPointCallback accessPointCallback) {
        if (accessPointCallback == null || this.mCallbacks.contains(accessPointCallback)) {
            return;
        }
        if (DEBUG) {
            Log.d("AccessPointController", "addCallback " + accessPointCallback);
        }
        this.mCallbacks.add(accessPointCallback);
        if (this.mCallbacks.size() == 1) {
            this.mMainExecutor.execute(new AccessPointControllerImpl$$ExternalSyntheticLambda0(this, 0));
        }
    }

    public final boolean canConfigMobileData() {
        return !this.mUserManager.hasUserRestriction("no_config_mobile_networks", UserHandle.of(this.mCurrentUser)) && ((UserTrackerImpl) this.mUserTracker).getUserInfo().isAdmin();
    }

    public final boolean canConfigWifi() {
        return (this.mWifiPickerTrackerFactory.wifiManager == null || this.mUserManager.hasUserRestriction("no_config_wifi", UserHandle.of(this.mCurrentUser)) || this.mUserManager.hasUserRestriction("no_change_wifi_state", UserHandle.of(this.mCurrentUser))) ? false : true;
    }

    public final void finalize() {
        this.mMainExecutor.execute(new AccessPointControllerImpl$$ExternalSyntheticLambda0(this, 2));
        super.finalize();
    }

    public final void fireAccessPointsCallback(List list) {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((AccessPointController.AccessPointCallback) it.next()).onAccessPointsChanged(list);
        }
    }

    public final int getInstantHotspotIcon(HotspotNetworkEntry hotspotNetworkEntry) {
        char c;
        int connectionStrength;
        int intValue;
        synchronized (hotspotNetworkEntry) {
            HotspotNetwork hotspotNetwork = hotspotNetworkEntry.mHotspotNetworkData;
            c = 0;
            connectionStrength = hotspotNetwork == null ? 0 : hotspotNetwork.getNetworkProviderInfo().getConnectionStrength();
        }
        ArrayList arrayList = new ArrayList();
        if (hotspotNetworkEntry.mHotspotNetworkData != null) {
            arrayList = new ArrayList(hotspotNetworkEntry.mHotspotNetworkData.getHotspotSecurityTypes());
        }
        if (arrayList.size() != 0 && (arrayList.size() != 1 ? arrayList.size() != 2 || !arrayList.contains(0) || !arrayList.contains(6) : (intValue = ((Integer) arrayList.get(0)).intValue()) != 0 && intValue != 6)) {
            c = 1;
        }
        return ICONS_WIFI[connectionStrength][c];
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.mLifecycle;
    }

    public final MergedCarrierEntry getMergedCarrierEntry() {
        WifiPickerTracker wifiPickerTracker = this.mWifiPickerTracker;
        if (wifiPickerTracker != null) {
            return wifiPickerTracker.getMergedCarrierEntry();
        }
        fireAccessPointsCallback(Collections.emptyList());
        return null;
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onScanRequested() {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((AccessPointController.AccessPointCallback) it.next()).onWifiScan(true);
        }
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onWifiEntriesChanged() {
        scanForAccessPoints();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        scanForAccessPoints();
    }

    public final void removeAccessPointCallback(AccessPointController.AccessPointCallback accessPointCallback) {
        if (accessPointCallback == null) {
            return;
        }
        if (DEBUG) {
            Log.d("AccessPointController", "removeCallback " + accessPointCallback);
        }
        this.mCallbacks.remove(accessPointCallback);
        if (this.mCallbacks.isEmpty()) {
            this.mMainExecutor.execute(new AccessPointControllerImpl$$ExternalSyntheticLambda0(this, 1));
        }
    }

    public final void scanForAccessPoints() {
        WifiPickerTracker wifiPickerTracker = this.mWifiPickerTracker;
        if (wifiPickerTracker == null) {
            fireAccessPointsCallback(Collections.emptyList());
            return;
        }
        List list = (List) new ArrayList(wifiPickerTracker.mWifiEntries).stream().filter(new WifiPickerTracker$$ExternalSyntheticLambda13(wifiPickerTracker, 2)).collect(Collectors.toList());
        WifiEntry wifiEntry = this.mWifiPickerTracker.mConnectedWifiEntry;
        if (wifiEntry != null) {
            list.add(0, wifiEntry);
        }
        fireAccessPointsCallback(list);
    }

    public final void startSettings(WifiEntry wifiEntry) {
        if (wifiEntry == null) {
            return;
        }
        Intent intent = new Intent("android.settings.WIFI_SETTINGS");
        intent.putExtra("wifi_start_connect_ssid", wifiEntry.getTitle());
        intent.putExtra("wifi_start_connect_security", wifiEntry.getSecurity());
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((AccessPointController.AccessPointCallback) it.next()).onSettingsActivityTriggered(intent);
        }
    }

    @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
    public final void onWifiEntriesChanged(int i) {
        scanForAccessPoints();
        if (i == 1) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((AccessPointController.AccessPointCallback) it.next()).onWifiScan(false);
            }
        }
    }
}
