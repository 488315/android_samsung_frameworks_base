package com.android.systemui.statusbar.policy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.TetheringManager;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.UserManager;
import android.util.Log;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import com.android.systemui.CvRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.coverlauncher.utils.badge.NotificationListener$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.util.DeviceType;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class HotspotControllerImpl implements HotspotController, WifiManager.SoftApCallback {
    public static final boolean DEBUG = Log.isLoggable("HotspotController", 3);
    public static final IntentFilter mHotspotControllerIntentFilter;
    public final Context mContext;
    public final boolean mIsTetheringSupportedConfig;
    public final Handler mMainHandler;
    public volatile int mNumConnectedDevices;
    public SemWifiManager mSemWifiManager;
    public final C33971 mTetheringCallback;
    public final UserTracker mUserTracker;
    public boolean mWaitingForTerminalState;
    public final WifiManager mWifiManager;
    public final ArrayList mCallbacks = new ArrayList();
    public final HotspotControllerReceiver mHotspotControllerReceiver = new HotspotControllerReceiver(this, 0);
    public int mHotspotState = 11;
    public volatile boolean mIsTetheringSupported = true;
    public volatile boolean mHasTetherableWifiRegexs = true;
    public boolean isReceiverRegistered = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HotspotControllerReceiver extends BroadcastReceiver {
        public /* synthetic */ HotspotControllerReceiver(HotspotControllerImpl hotspotControllerImpl, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.android.net.wifi.WIFI_AP_STA_STATE_CHANGED".equals(intent.getAction())) {
                HotspotControllerImpl.this.mNumConnectedDevices = intent.getIntExtra("STA_COUNT", 0);
                TooltipPopup$$ExternalSyntheticOutline0.m13m(new StringBuilder("onReceive :ACTION_WIFI_AP_STA_STATE_CHANGED numsta:"), HotspotControllerImpl.this.mNumConnectedDevices, "HotspotController");
                HotspotControllerImpl.this.fireUpdateDevicesCallback();
            }
        }

        private HotspotControllerReceiver() {
        }
    }

    /* renamed from: -$$Nest$mfireHotspotAvailabilityChanged, reason: not valid java name */
    public static void m1725$$Nest$mfireHotspotAvailabilityChanged(HotspotControllerImpl hotspotControllerImpl) {
        ArrayList arrayList;
        synchronized (hotspotControllerImpl.mCallbacks) {
            arrayList = new ArrayList(hotspotControllerImpl.mCallbacks);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((HotspotController.Callback) it.next()).onHotspotAvailabilityChanged(hotspotControllerImpl.isHotspotSupported());
        }
    }

    static {
        IntentFilter intentFilter = new IntentFilter("com.samsung.android.net.wifi.WIFI_AP_STA_STATE_CHANGED");
        mHotspotControllerIntentFilter = intentFilter;
        if (CvRune.HOTSPOT_USE_CHAMELEON) {
            intentFilter.addAction("com.samsung.CSC_CHAMELEON_UPDATE_SETTINGS");
        }
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [android.net.TetheringManager$TetheringEventCallback, com.android.systemui.statusbar.policy.HotspotControllerImpl$1] */
    public HotspotControllerImpl(Context context, UserTracker userTracker, Handler handler, Handler handler2, DumpManager dumpManager) {
        ?? r0 = new TetheringManager.TetheringEventCallback() { // from class: com.android.systemui.statusbar.policy.HotspotControllerImpl.1
            public final void onTetherableInterfaceRegexpsChanged(TetheringManager.TetheringInterfaceRegexps tetheringInterfaceRegexps) {
                boolean z = tetheringInterfaceRegexps.getTetherableWifiRegexs().size() != 0;
                if (HotspotControllerImpl.this.mHasTetherableWifiRegexs != z) {
                    HotspotControllerImpl.this.mHasTetherableWifiRegexs = z;
                    NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("mHasTetherableWifiRegexs:"), HotspotControllerImpl.this.mHasTetherableWifiRegexs, "HotspotController");
                    HotspotControllerImpl.m1725$$Nest$mfireHotspotAvailabilityChanged(HotspotControllerImpl.this);
                }
            }

            public final void onTetheringSupported(boolean z) {
                if (HotspotControllerImpl.this.mIsTetheringSupported != z) {
                    HotspotControllerImpl.this.mIsTetheringSupported = z;
                    NotificationListener$$ExternalSyntheticOutline0.m123m(new StringBuilder("mIsTetheringSupported:"), HotspotControllerImpl.this.mIsTetheringSupported, "HotspotController");
                    HotspotControllerImpl.m1725$$Nest$mfireHotspotAvailabilityChanged(HotspotControllerImpl.this);
                }
            }
        };
        this.mTetheringCallback = r0;
        this.mContext = context;
        this.mUserTracker = userTracker;
        TetheringManager tetheringManager = (TetheringManager) context.getSystemService(TetheringManager.class);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mMainHandler = handler;
        boolean z = context.getResources().getBoolean(R.bool.config_show_wifi_tethering);
        this.mIsTetheringSupportedConfig = z;
        if (z) {
            tetheringManager.registerTetheringEventCallback(new HandlerExecutor(handler2), (TetheringManager.TetheringEventCallback) r0);
        }
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "HotspotControllerImpl", this);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        final HotspotController.Callback callback = (HotspotController.Callback) obj;
        synchronized (this.mCallbacks) {
            if (callback != null) {
                if (!this.mCallbacks.contains(callback)) {
                    if (DEBUG) {
                        Log.d("HotspotController", "addCallback " + callback);
                    }
                    this.mCallbacks.add(callback);
                    if (this.mWifiManager != null) {
                        if (this.mCallbacks.size() == 1) {
                            this.mWifiManager.registerSoftApCallback(new HandlerExecutor(this.mMainHandler), this);
                        } else {
                            this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.HotspotControllerImpl$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    HotspotControllerImpl hotspotControllerImpl = HotspotControllerImpl.this;
                                    callback.onHotspotChanged(hotspotControllerImpl.getNumConnectedDevices(), hotspotControllerImpl.isHotspotEnabled());
                                }
                            });
                        }
                    }
                    if (!this.mCallbacks.isEmpty()) {
                        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(mHotspotControllerIntentFilter, this.mHotspotControllerReceiver);
                        this.isReceiverRegistered = true;
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        String str;
        printWriter.println("HotspotController state:");
        printWriter.print("  available=");
        printWriter.println(isHotspotSupported());
        printWriter.print("  mHotspotState=");
        switch (this.mHotspotState) {
            case 10:
                str = "DISABLING";
                break;
            case 11:
                str = "DISABLED";
                break;
            case 12:
                str = "ENABLING";
                break;
            case 13:
                str = "ENABLED";
                break;
            case 14:
                str = "FAILED";
                break;
            default:
                str = null;
                break;
        }
        printWriter.println(str);
        printWriter.print("  mNumConnectedDevices=");
        printWriter.println(this.mNumConnectedDevices);
        printWriter.print("  mWaitingForTerminalState=");
        printWriter.println(this.mWaitingForTerminalState);
    }

    public final void fireUpdateDevicesCallback() {
        synchronized (this.mCallbacks) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((HotspotController.Callback) it.next()).onUpdateConnectedDevices();
            }
        }
    }

    public final int getNumConnectedDevices() {
        if (isHotspotEnabled()) {
            SemWifiManager semWifiManager = this.mSemWifiManager;
            if (semWifiManager != null) {
                this.mNumConnectedDevices = semWifiManager.getWifiApConnectedStationCount();
            }
        } else {
            this.mNumConnectedDevices = 0;
        }
        return this.mNumConnectedDevices;
    }

    public final boolean isHotspotEnabled() {
        return this.mHotspotState == 13;
    }

    public final boolean isHotspotSupported() {
        return this.mIsTetheringSupportedConfig && this.mIsTetheringSupported && this.mHasTetherableWifiRegexs && UserManager.get(this.mContext).isUserAdmin(((UserTrackerImpl) this.mUserTracker).getUserId()) && !DeviceType.isWiFiOnlyDevice();
    }

    public final void onStateChanged(int i, int i2) {
        SemWifiManager semWifiManager;
        ArrayList arrayList;
        this.mHotspotState = i;
        if (!isHotspotEnabled()) {
            this.mNumConnectedDevices = 0;
        } else if (isHotspotEnabled() && (semWifiManager = this.mSemWifiManager) != null) {
            this.mNumConnectedDevices = semWifiManager.getWifiApConnectedStationCount();
        }
        StringBuilder sb = new StringBuilder("HotspotState :");
        sb.append(this.mHotspotState);
        sb.append("numsta:");
        TooltipPopup$$ExternalSyntheticOutline0.m13m(sb, this.mNumConnectedDevices, "HotspotController");
        if (this.mWaitingForTerminalState) {
            if (this.mSemWifiManager == null) {
                this.mSemWifiManager = (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            }
            switch (this.mHotspotState) {
                case 10:
                case 12:
                    this.mWaitingForTerminalState = true;
                    break;
                case 14:
                    this.mSemWifiManager.setWifiApEnabled((SoftApConfiguration) null, false);
                case 11:
                case 13:
                    this.mWaitingForTerminalState = false;
                    break;
            }
        }
        switch (this.mHotspotState) {
            case 10:
            case 12:
                synchronized (this.mCallbacks) {
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((HotspotController.Callback) it.next()).onHotspotPrepared();
                    }
                }
                return;
            case 11:
            case 13:
            case 14:
                synchronized (this.mCallbacks) {
                    arrayList = new ArrayList(this.mCallbacks);
                }
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    ((HotspotController.Callback) it2.next()).onHotspotChanged(getNumConnectedDevices(), isHotspotEnabled());
                }
                fireUpdateDevicesCallback();
                return;
            default:
                return;
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        WifiManager wifiManager;
        HotspotController.Callback callback = (HotspotController.Callback) obj;
        if (callback == null) {
            return;
        }
        if (DEBUG) {
            Log.d("HotspotController", "removeCallback " + callback);
        }
        synchronized (this.mCallbacks) {
            this.mCallbacks.remove(callback);
            if (this.mCallbacks.isEmpty() && (wifiManager = this.mWifiManager) != null) {
                wifiManager.unregisterSoftApCallback(this);
            }
            if (this.isReceiverRegistered) {
                ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this.mHotspotControllerReceiver);
            }
            this.isReceiverRegistered = false;
        }
    }

    public final void onConnectedClientsChanged(List list) {
    }
}
