package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.telephony.SubscriptionManager;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.R;
import com.android.systemui.p016qs.tiles.WifiTile;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AccessPointControllerImpl implements AccessPointController, WifiPickerTracker.WifiPickerTrackerCallback, LifecycleOwner {
    public static final boolean DEBUG = Log.isLoggable("AccessPointController", 3);
    public static final int[][] ICONS_GIGA;
    public static final int[][] ICONS_WIFI;
    public static final int[][] ICONS_WIFI6;
    public static final int[][] ICONS_WIFI6E;
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
    public final C26161 mSemWifiApSmartCallback = new SemWifiManager.SemWifiApSmartCallback() { // from class: com.android.systemui.statusbar.connectivity.AccessPointControllerImpl.1
        public final void onStateChanged(int i, String str) {
            Log.d("AccessPointController.AutoHotspot", "WifiApSmartCallback`s onStateChanged() : mhsMac -> " + str + " state -> " + i);
            Iterator it = AccessPointControllerImpl.this.mWifiApBleCallbacks.iterator();
            while (it.hasNext()) {
                WifiTile.WifiDetailAdapter wifiDetailAdapter = (WifiTile.WifiDetailAdapter) ((AccessPointController.WifiApBleStateChangeCallback) it.next());
                wifiDetailAdapter.getClass();
                StringBuilder sb = new StringBuilder();
                Intent intent = WifiTile.WIFI_SETTINGS;
                WifiTile wifiTile = WifiTile.this;
                Log.d(AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, wifiTile.TAG, ".AutoHotspot"), "onWifiApBleStateChanged() : mhsMac -> " + str + " state -> " + i);
                if (str != null && i <= 0) {
                    Log.d(AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder(), wifiTile.TAG, ".AutoHotspot"), "onWifiApBleStateChanged() - Triggering updateHotspotItems for connection time out with mhsMac-> ".concat(str));
                    wifiDetailAdapter.updateHotspotItems();
                }
            }
        }
    };
    public final C26172 mConnectCallback = new WifiEntry.ConnectCallback() { // from class: com.android.systemui.statusbar.connectivity.AccessPointControllerImpl.2
        @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
        public final void onConnectResult(int i) {
            if (i == 0) {
                if (AccessPointControllerImpl.DEBUG) {
                    Log.d("AccessPointController", "connect success");
                }
            } else if (AccessPointControllerImpl.DEBUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("connect failure reason=", i, "AccessPointController");
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiPickerTrackerFactory {
        public final SimpleClockC26181 mClock = new SimpleClock(this, ZoneOffset.UTC) { // from class: com.android.systemui.statusbar.connectivity.AccessPointControllerImpl.WifiPickerTrackerFactory.1
            public final long millis() {
                return SystemClock.elapsedRealtime();
            }
        };
        public final ConnectivityManager mConnectivityManager;
        public final Context mContext;
        public final Handler mMainHandler;
        public final WifiManager mWifiManager;
        public final Handler mWorkerHandler;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.statusbar.connectivity.AccessPointControllerImpl$WifiPickerTrackerFactory$1] */
        public WifiPickerTrackerFactory(Context context, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Handler handler2) {
            this.mContext = context;
            this.mWifiManager = wifiManager;
            this.mConnectivityManager = connectivityManager;
            this.mMainHandler = handler;
            this.mWorkerHandler = handler2;
        }
    }

    static {
        int[][] iArr = WifiIcons.QS_WIFI_SIGNAL_STRENGTH;
        ICONS_WIFI = new int[][]{new int[]{R.drawable.sec_ic_wifi_signal_0, R.drawable.sec_ic_wifi_signal_lock_0}, new int[]{R.drawable.sec_ic_wifi_signal_1, R.drawable.sec_ic_wifi_signal_lock_1}, new int[]{R.drawable.sec_ic_wifi_signal_2, R.drawable.sec_ic_wifi_signal_lock_2}, new int[]{R.drawable.sec_ic_wifi_signal_3, R.drawable.sec_ic_wifi_signal_lock_3}, new int[]{R.drawable.sec_ic_wifi_signal_4, R.drawable.sec_ic_wifi_signal_lock_4}};
        ICONS_WIFI6 = new int[][]{new int[]{R.drawable.sec_ic_wifi_signal_wifi6_0, R.drawable.sec_ic_wifi_signal_lock_wifi6_0}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6_1, R.drawable.sec_ic_wifi_signal_lock_wifi6_1}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6_2, R.drawable.sec_ic_wifi_signal_lock_wifi6_2}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6_3, R.drawable.sec_ic_wifi_signal_lock_wifi6_3}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6_4, R.drawable.sec_ic_wifi_signal_lock_wifi6_4}};
        ICONS_WIFI6E = new int[][]{new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_0, R.drawable.sec_ic_wifi_signal_lock_wifi6e_0}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_1, R.drawable.sec_ic_wifi_signal_lock_wifi6e_1}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_2, R.drawable.sec_ic_wifi_signal_lock_wifi6e_2}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_3, R.drawable.sec_ic_wifi_signal_lock_wifi6e_3}, new int[]{R.drawable.sec_ic_wifi_signal_wifi6e_4, R.drawable.sec_ic_wifi_signal_lock_wifi6e_4}};
        ICONS_GIGA = new int[][]{new int[]{R.drawable.sec_ic_wifi_signal_shift_0, R.drawable.sec_ic_wifi_signal_lock_shift_0}, new int[]{R.drawable.sec_ic_wifi_signal_shift_1, R.drawable.sec_ic_wifi_signal_lock_shift_1}, new int[]{R.drawable.sec_ic_wifi_signal_shift_2, R.drawable.sec_ic_wifi_signal_lock_shift_2}, new int[]{R.drawable.sec_ic_wifi_signal_shift_3, R.drawable.sec_ic_wifi_signal_lock_shift_3}, new int[]{R.drawable.sec_ic_wifi_signal_shift_4, R.drawable.sec_ic_wifi_signal_lock_shift_4}};
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.statusbar.connectivity.AccessPointControllerImpl$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.connectivity.AccessPointControllerImpl$2] */
    public AccessPointControllerImpl(UserManager userManager, UserTracker userTracker, Executor executor, WifiPickerTrackerFactory wifiPickerTrackerFactory) {
        this.mUserManager = userManager;
        this.mUserTracker = userTracker;
        this.mCurrentUser = ((UserTrackerImpl) userTracker).getUserId();
        this.mMainExecutor = executor;
        this.mWifiPickerTrackerFactory = wifiPickerTrackerFactory;
        executor.execute(new AccessPointControllerImpl$$ExternalSyntheticLambda0(this, 2));
        this.mSemWifiManager = (SemWifiManager) wifiPickerTrackerFactory.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    public static boolean isOpenNetwork(WifiEntry wifiEntry) {
        return wifiEntry.getSecurity() == 0 || wifiEntry.getSecurity() == 4;
    }

    public final void addAccessPointCallback(AccessPointController.AccessPointCallback accessPointCallback) {
        if (accessPointCallback != null) {
            ArrayList arrayList = this.mCallbacks;
            if (arrayList.contains(accessPointCallback)) {
                return;
            }
            if (DEBUG) {
                Log.d("AccessPointController", "addCallback " + accessPointCallback);
            }
            arrayList.add(accessPointCallback);
            if (arrayList.size() == 1) {
                this.mMainExecutor.execute(new AccessPointControllerImpl$$ExternalSyntheticLambda0(this, 3));
            }
        }
    }

    public final boolean canConfigWifi() {
        if (this.mWifiPickerTrackerFactory.mWifiManager != null) {
            return !this.mUserManager.hasUserRestriction("no_config_wifi", new UserHandle(this.mCurrentUser));
        }
        return false;
    }

    public final void finalize() {
        this.mMainExecutor.execute(new AccessPointControllerImpl$$ExternalSyntheticLambda0(this, 0));
        super.finalize();
    }

    public final void fireAccessPointsCallback(List list) {
        Iterator it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((AccessPointController.AccessPointCallback) it.next()).onAccessPointsChanged(list);
        }
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final LifecycleRegistry getLifecycle() {
        return this.mLifecycle;
    }

    public final MergedCarrierEntry getMergedCarrierEntry() {
        int defaultDataSubscriptionId;
        WifiPickerTracker wifiPickerTracker = this.mWifiPickerTracker;
        if (wifiPickerTracker == null) {
            fireAccessPointsCallback(Collections.emptyList());
            return null;
        }
        if (!wifiPickerTracker.mIsInitialized && wifiPickerTracker.mMergedCarrierEntry == null && (defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId()) != -1) {
            wifiPickerTracker.mMergedCarrierEntry = new MergedCarrierEntry(wifiPickerTracker.mWorkerHandler, wifiPickerTracker.mWifiManager, false, wifiPickerTracker.mContext, defaultDataSubscriptionId);
        }
        return wifiPickerTracker.mMergedCarrierEntry;
    }

    public final void removeAccessPointCallback(AccessPointController.AccessPointCallback accessPointCallback) {
        if (accessPointCallback == null) {
            return;
        }
        if (DEBUG) {
            Log.d("AccessPointController", "removeCallback " + accessPointCallback);
        }
        ArrayList arrayList = this.mCallbacks;
        arrayList.remove(accessPointCallback);
        if (arrayList.isEmpty()) {
            this.mMainExecutor.execute(new AccessPointControllerImpl$$ExternalSyntheticLambda0(this, 1));
        }
    }

    public final void scanForAccessPoints() {
        WifiPickerTracker wifiPickerTracker = this.mWifiPickerTracker;
        if (wifiPickerTracker == null) {
            fireAccessPointsCallback(Collections.emptyList());
            return;
        }
        List wifiEntries = wifiPickerTracker.getWifiEntries();
        WifiEntry connectedWifiEntry = this.mWifiPickerTracker.getConnectedWifiEntry();
        if (connectedWifiEntry != null) {
            wifiEntries.add(0, connectedWifiEntry);
        }
        fireAccessPointsCallback(wifiEntries);
    }

    public final void startSettings(WifiEntry wifiEntry) {
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
}
