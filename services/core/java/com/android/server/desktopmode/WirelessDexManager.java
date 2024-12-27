package com.android.server.desktopmode;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplayParameter;
import android.hardware.display.SemWifiDisplayParameterListener;
import android.hardware.display.SemWifiDisplayStatus;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.UiThread;
import com.android.server.wm.WindowManagerService;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WirelessDexManager {
    public final Context mContext;
    public final AnonymousClass3 mDesktopPointerEventListener;
    public final DisplayManager mDisplayManager;
    public final Handler mHandler;
    public final InputManager mInputManager;
    public long mMotionEventStartTime;
    public final AnonymousClass2 mPointerIconChangedListener;
    public final ContentResolver mResolver;
    public final WirelessDexManager$$ExternalSyntheticLambda0 mRunnableDisableLowLatencyMode;
    public final WirelessDexManager$$ExternalSyntheticLambda0 mRunnableEnableLowLatencyMode;
    public final AnonymousClass1 mSemWifiDisplayParameterListener;
    public final IStateManager mStateManager;
    public String mTvTizenVersion;
    public final UiManager mUiManager;
    public final WindowManagerService mWindowManager;
    public boolean mIsLowLatencyMode = false;
    public boolean mIsPointerEventListener = false;
    public boolean mIsPointerIconListener = false;
    public boolean mIsWirelessDexEntered = false;
    public int mReducedLatency = -1;
    public int mPointerIconSync = -1;
    public int mDesktopDisplayId = -1;
    public int mFrequencyValue = 0;
    public String mConnectedDeviceName = "";

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Receiver extends BroadcastReceiver {
        public Receiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean z = DesktopModeFeature.DEBUG;
            if (z) {
                Log.d("[DMS]WirelessDexManager", "onReceive(), action=" + action);
            }
            StateManager.InternalState state =
                    ((StateManager) WirelessDexManager.this.mStateManager).getState();
            if ("android.intent.action.HDMI_PLUGGED".equals(action)) {
                if (intent.getBooleanExtra(
                        LauncherConfigurationInternal.KEY_STATE_BOOLEAN, false)) {
                    if (state.isDexOnPcOrWirelessDexConnected()) {
                        if (z) {
                            Log.d(
                                    "[DMS]WirelessDexManager",
                                    "Disconnect Wireless DeX / DFP when HDMI plugged");
                        }
                        ((StateManager) WirelessDexManager.this.mStateManager)
                                .notifyDisplayDisconnectionRequest(1001);
                        ((StateManager) WirelessDexManager.this.mStateManager)
                                .notifyDisplayDisconnectionRequest(1000);
                    }
                    WirelessDexManager.this.disconnect();
                    return;
                }
                return;
            }
            if ("com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE".equals(action)) {
                int intExtra =
                        intent.getIntExtra(LauncherConfigurationInternal.KEY_STATE_BOOLEAN, -1);
                boolean booleanExtra = intent.getBooleanExtra("by_user", true);
                Log.d(
                        "[DMS]WirelessDexManager",
                        "WifiDisplay Connection state="
                                + intExtra
                                + ", disconnectedByUser="
                                + booleanExtra
                                + ", isWirelessDex="
                                + state.isWirelessDexConnected()
                                + ", mIsWirelessDexEntered="
                                + WirelessDexManager.this.mIsWirelessDexEntered);
                if (intExtra != 0 || booleanExtra) {
                    return;
                }
                WirelessDexManager wirelessDexManager = WirelessDexManager.this;
                if (TextUtils.isEmpty(wirelessDexManager.mConnectedDeviceName)) {
                    return;
                }
                Log.d(
                        "[DMS]WirelessDexManager",
                        "["
                                + wirelessDexManager.mConnectedDeviceName
                                + "] has been disconnected by something other than the user"
                                + " request.");
                Context context2 = wirelessDexManager.mContext;
                String format =
                        String.format(
                                context2.getString(R.string.harmful_app_warning_open_anyway),
                                wirelessDexManager.mConnectedDeviceName);
                List list = ToastManager.sToasts;
                if (format.isEmpty()) {
                    return;
                }
                UiThread.getHandler()
                        .post(
                                new ToastManager$$ExternalSyntheticLambda0(
                                        1, context2, format, true));
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class StateListener extends StateManager.StateListener {
        public StateListener() {}

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onDualModeStartLoadingScreen(boolean z) {
            if (z) {
                return;
            }
            WirelessDexManager wirelessDexManager = WirelessDexManager.this;
            if (wirelessDexManager.mIsWirelessDexEntered) {
                if (wirelessDexManager.mIsPointerEventListener) {
                    wirelessDexManager.mWindowManager.unregisterPointerEventListener(
                            wirelessDexManager.mDesktopPointerEventListener,
                            wirelessDexManager.mDesktopDisplayId);
                    wirelessDexManager.mIsPointerEventListener = false;
                }
                if (wirelessDexManager.mIsPointerIconListener) {
                    wirelessDexManager.mInputManager.semUnregisterOnPointerIconChangedListener(
                            wirelessDexManager.mPointerIconChangedListener);
                    wirelessDexManager.mIsPointerIconListener = false;
                }
                wirelessDexManager.mIsWirelessDexEntered = false;
                wirelessDexManager.mIsLowLatencyMode = false;
                wirelessDexManager.mConnectedDeviceName = "";
                wirelessDexManager.mUiManager.removeNotification(204);
                if (DesktopModeFeature.DEBUG) {
                    Log.d("[DMS]WirelessDexManager", "onDualModeStartLoadingScreen() finish");
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0070, code lost:

           if (r0.contains(r3) != false) goto L34;
        */
        @Override // com.android.server.desktopmode.StateManager.StateListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onDualModeStopLoadingScreen(boolean r7) {
            /*
                r6 = this;
                if (r7 == 0) goto Lce
                com.android.server.desktopmode.WirelessDexManager r6 = com.android.server.desktopmode.WirelessDexManager.this
                com.android.server.desktopmode.IStateManager r7 = r6.mStateManager
                com.android.server.desktopmode.StateManager r7 = (com.android.server.desktopmode.StateManager) r7
                com.android.server.desktopmode.StateManager$InternalState r7 = r7.getState()
                boolean r0 = r7.isWirelessDexConnected()
                r6.mIsWirelessDexEntered = r0
                int r1 = r7.mDesktopDisplayId
                r6.mDesktopDisplayId = r1
                if (r0 == 0) goto Lce
                java.lang.String r0 = r6.mTvTizenVersion
                boolean r0 = android.text.TextUtils.isEmpty(r0)
                r1 = 1
                if (r0 != 0) goto L2c
                r6.mIsPointerEventListener = r1
                int r0 = r6.mDesktopDisplayId
                com.android.server.wm.WindowManagerService r2 = r6.mWindowManager
                com.android.server.desktopmode.WirelessDexManager$3 r3 = r6.mDesktopPointerEventListener
                r2.registerPointerEventListener(r3, r0)
            L2c:
                android.content.ContentResolver r0 = r6.mResolver
                int r2 = com.android.server.desktopmode.DesktopModeSettings.sCurrentUserId
                java.lang.String r3 = "wireless_dex_qos_notification_deleted"
                r4 = 0
                java.lang.String r0 = com.android.server.desktopmode.DesktopModeSettings.getSettingsAsUser(r0, r3, r4, r2)
                boolean r2 = com.samsung.android.desktopmode.DesktopModeFeature.DEBUG
                if (r2 == 0) goto L4f
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "Address List : "
                r2.<init>(r3)
                r2.append(r0)
                java.lang.String r2 = r2.toString()
                java.lang.String r3 = "[DMS]WirelessDexManager"
                com.android.server.desktopmode.Log.d(r3, r2)
            L4f:
                java.lang.String r2 = ""
                if (r0 != 0) goto L54
                goto L73
            L54:
                android.hardware.display.DisplayManager r3 = r6.mDisplayManager
                android.hardware.display.WifiDisplayStatus r3 = r3.getWifiDisplayStatus()
                android.hardware.display.WifiDisplay r3 = r3.getActiveDisplay()
                if (r3 == 0) goto L65
                java.lang.String r3 = r3.getDeviceAddress()
                goto L66
            L65:
                r3 = r2
            L66:
                boolean r5 = r2.equals(r3)
                if (r5 != 0) goto L73
                boolean r0 = r0.contains(r3)
                if (r0 == 0) goto L73
                goto La1
            L73:
                java.lang.String r0 = r6.mTvTizenVersion
                boolean r0 = android.text.TextUtils.isEmpty(r0)
                com.android.server.desktopmode.UiManager r3 = r6.mUiManager
                if (r0 != 0) goto L9a
                java.lang.String r0 = r6.mTvTizenVersion
                java.lang.String r5 = "TIZEN-WFD-SINK"
                boolean r0 = r0.equals(r5)
                if (r0 == 0) goto L88
                goto L9a
            L88:
                int r0 = r6.mFrequencyValue
                r5 = 5160(0x1428, float:7.23E-42)
                if (r0 < r5) goto L92
                r5 = 5865(0x16e9, float:8.219E-42)
                if (r0 <= r5) goto La1
            L92:
                int r7 = r7.mDesktopDisplayId
                r0 = 11
                r3.showDialog(r7, r0, r4)
                goto La1
            L9a:
                int r7 = r7.mDesktopDisplayId
                r0 = 12
                r3.showDialog(r7, r0, r4)
            La1:
                int r7 = r6.mPointerIconSync
                if (r7 != r1) goto Lae
                r6.mIsPointerIconListener = r1
                android.hardware.input.InputManager r7 = r6.mInputManager
                com.android.server.desktopmode.WirelessDexManager$2 r0 = r6.mPointerIconChangedListener
                r7.semRegisterOnPointerIconChangedListener(r0, r4)
            Lae:
                android.content.ContentResolver r7 = r6.mResolver
                int r0 = r6.mPointerIconSync
                if (r0 == r1) goto Lb5
                goto Lb6
            Lb5:
                r1 = 0
            Lb6:
                java.lang.String r0 = "uibc_finger_enabled"
                com.android.server.desktopmode.DesktopModeSettings.setSettings(r7, r0, r1)
                android.hardware.display.DisplayManager r7 = r6.mDisplayManager
                android.hardware.display.WifiDisplayStatus r7 = r7.getWifiDisplayStatus()
                android.hardware.display.WifiDisplay r7 = r7.getActiveDisplay()
                if (r7 == 0) goto Lcc
                java.lang.String r2 = r7.getDeviceName()
            Lcc:
                r6.mConnectedDeviceName = r2
            Lce:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.desktopmode.WirelessDexManager.StateListener.onDualModeStopLoadingScreen(boolean):void");
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onExternalDisplayConnectionChanged(
                StateManager.InternalState internalState) {
            if (internalState.isWirelessDexConnected()) {
                return;
            }
            WirelessDexManager wirelessDexManager = WirelessDexManager.this;
            wirelessDexManager.mReducedLatency = -1;
            wirelessDexManager.mPointerIconSync = -1;
            wirelessDexManager.mFrequencyValue = 0;
            wirelessDexManager.mTvTizenVersion = null;
            DesktopModeSettings.setSettings(
                    wirelessDexManager.mResolver, "uibc_finger_enabled", false);
        }

        @Override // com.android.server.desktopmode.StateManager.StateListener
        public final void onUserChanged(StateManager.InternalState internalState) {
            WirelessDexManager wirelessDexManager = WirelessDexManager.this;
            DesktopModeSettings.setSettings(wirelessDexManager.mResolver, "wireless_dex", 2);
            DesktopModeSettings.setSettings(
                    wirelessDexManager.mResolver, "wireless_dex_scan_device", false);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.desktopmode.WirelessDexManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.desktopmode.WirelessDexManager$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.desktopmode.WirelessDexManager$2] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.desktopmode.WirelessDexManager$3] */
    public WirelessDexManager(
            Context context,
            IStateManager iStateManager,
            UiManager uiManager,
            DisplayManager displayManager,
            InputManager inputManager,
            ServiceThread serviceThread,
            WindowManagerService windowManagerService) {
        final int i = 0;
        this.mRunnableEnableLowLatencyMode =
                new Runnable(
                        this) { // from class:
                                // com.android.server.desktopmode.WirelessDexManager$$ExternalSyntheticLambda0
                    public final /* synthetic */ WirelessDexManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        WirelessDexManager wirelessDexManager = this.f$0;
                        switch (i2) {
                            case 0:
                                if (!wirelessDexManager.mIsLowLatencyMode) {
                                    wirelessDexManager.mIsLowLatencyMode = true;
                                    wirelessDexManager.setWifiDisplayParameters(
                                            "wfd_sec_low_latency_mode", "on");
                                    break;
                                }
                                break;
                            default:
                                if (wirelessDexManager.mIsLowLatencyMode) {
                                    wirelessDexManager.mIsLowLatencyMode = false;
                                    wirelessDexManager.setWifiDisplayParameters(
                                            "wfd_sec_low_latency_mode", "off");
                                    break;
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mRunnableDisableLowLatencyMode =
                new Runnable(
                        this) { // from class:
                                // com.android.server.desktopmode.WirelessDexManager$$ExternalSyntheticLambda0
                    public final /* synthetic */ WirelessDexManager f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        WirelessDexManager wirelessDexManager = this.f$0;
                        switch (i22) {
                            case 0:
                                if (!wirelessDexManager.mIsLowLatencyMode) {
                                    wirelessDexManager.mIsLowLatencyMode = true;
                                    wirelessDexManager.setWifiDisplayParameters(
                                            "wfd_sec_low_latency_mode", "on");
                                    break;
                                }
                                break;
                            default:
                                if (wirelessDexManager.mIsLowLatencyMode) {
                                    wirelessDexManager.mIsLowLatencyMode = false;
                                    wirelessDexManager.setWifiDisplayParameters(
                                            "wfd_sec_low_latency_mode", "off");
                                    break;
                                }
                                break;
                        }
                    }
                };
        SemWifiDisplayParameterListener semWifiDisplayParameterListener =
                new SemWifiDisplayParameterListener() { // from class:
                                                        // com.android.server.desktopmode.WirelessDexManager.1
                    public final void onParametersChanged(List list) {
                        if (list != null) {
                            Iterator it = list.iterator();
                            while (it.hasNext()) {
                                SemWifiDisplayParameter semWifiDisplayParameter =
                                        (SemWifiDisplayParameter) it.next();
                                String key = semWifiDisplayParameter.getKey();
                                String value = semWifiDisplayParameter.getValue();
                                boolean z = DesktopModeFeature.DEBUG;
                                if (z) {
                                    Log.d(
                                            "[DMS]WirelessDexManager",
                                            XmlUtils$$ExternalSyntheticOutline0.m(
                                                    "onParametersChanged (Key : ",
                                                    key,
                                                    ", Value : ",
                                                    value,
                                                    ")"));
                                }
                                if ("wfd_sec_dex_support".equals(key)) {
                                    if ("yes".equals(value)) {
                                        WirelessDexManager.this.mReducedLatency = 1;
                                    } else {
                                        WirelessDexManager.this.mReducedLatency = 0;
                                    }
                                } else if ("wfd_sec_dex_mouse_support".equals(key)) {
                                    if ("yes".equals(value)) {
                                        WirelessDexManager.this.mPointerIconSync = 1;
                                    } else {
                                        WirelessDexManager.this.mPointerIconSync = 0;
                                    }
                                } else if ("notify".equals(key) && "weak_network".equals(value)) {
                                    WirelessDexManager wirelessDexManager = WirelessDexManager.this;
                                    StateManager.InternalState state =
                                            ((StateManager) wirelessDexManager.mStateManager)
                                                    .getState();
                                    if (state.isWirelessDexConnected()
                                            && state.mDesktopModeState.compareTo(4, 0)) {
                                        ToastManager.showToast(
                                                Utils.getDisplayContext(
                                                        wirelessDexManager.mContext,
                                                        state.mDesktopDisplayId),
                                                String.format(
                                                        wirelessDexManager.mContext.getString(
                                                                R.string.imProtocolCustom),
                                                        wirelessDexManager.mContext.getString(
                                                                17042662)),
                                                1);
                                    }
                                } else if ("frequency".equals(key)) {
                                    try {
                                        WirelessDexManager.this.mFrequencyValue =
                                                Integer.parseInt(value);
                                    } catch (NumberFormatException e) {
                                        Log.e(
                                                "[DMS]WirelessDexManager",
                                                "Wrong number format=" + value,
                                                e);
                                    }
                                } else if ("tizenVer".equals(key)) {
                                    if (z) {
                                        Log.d(
                                                "[DMS]WirelessDexManager",
                                                "TV Tizen version : " + value);
                                    }
                                    WirelessDexManager.this.mTvTizenVersion = value;
                                }
                            }
                        }
                    }
                };
        this.mPointerIconChangedListener =
                new InputManager
                        .SemOnPointerIconChangedListener() { // from class:
                                                             // com.android.server.desktopmode.WirelessDexManager.2
                    public final void onPointerIconChanged(
                            int i3, Bitmap bitmap, float f, float f2) {
                        WirelessDexManager wirelessDexManager = WirelessDexManager.this;
                        wirelessDexManager.getClass();
                        if (i3 != 1) {
                            switch (i3) {
                                case FrameworkStatsLog
                                        .KEYSTORE2_KEY_OPERATION_WITH_PURPOSE_AND_MODES_INFO /* 10122 */:
                                    i3 = 1014;
                                    break;
                                case FrameworkStatsLog
                                        .KEYSTORE2_KEY_OPERATION_WITH_GENERAL_INFO /* 10123 */:
                                    i3 = 1015;
                                    break;
                                case FrameworkStatsLog.RKP_ERROR_STATS /* 10124 */:
                                    i3 = 1017;
                                    break;
                                case FrameworkStatsLog.KEYSTORE2_CRASH_STATS /* 10125 */:
                                    i3 = 1016;
                                    break;
                            }
                            wirelessDexManager.setWifiDisplayParameters(
                                    "wfd_sec_uibc_mouse_cursor_idx", String.valueOf(i3));
                        }
                        i3 = 1000;
                        wirelessDexManager.setWifiDisplayParameters(
                                "wfd_sec_uibc_mouse_cursor_idx", String.valueOf(i3));
                    }
                };
        this.mDesktopPointerEventListener =
                new WindowManagerPolicyConstants
                        .PointerEventListener() { // from class:
                                                  // com.android.server.desktopmode.WirelessDexManager.3
                    public final void onPointerEvent(MotionEvent motionEvent) {
                        int action = motionEvent.getAction();
                        if (SystemClock.uptimeMillis()
                                        - WirelessDexManager.this.mMotionEventStartTime
                                > 1000) {
                            if (action == 2 || action == 7 || action == 0 || action == 1) {
                                if (motionEvent.isFromSource(8194)
                                        || motionEvent.isFromSource(16386)) {
                                    WirelessDexManager.this.mMotionEventStartTime =
                                            SystemClock.uptimeMillis();
                                    WirelessDexManager wirelessDexManager = WirelessDexManager.this;
                                    if (!wirelessDexManager.mIsLowLatencyMode) {
                                        wirelessDexManager.mHandler.post(
                                                wirelessDexManager.mRunnableEnableLowLatencyMode);
                                    }
                                    WirelessDexManager wirelessDexManager2 =
                                            WirelessDexManager.this;
                                    wirelessDexManager2.mHandler.removeCallbacks(
                                            wirelessDexManager2.mRunnableDisableLowLatencyMode);
                                    WirelessDexManager wirelessDexManager3 =
                                            WirelessDexManager.this;
                                    wirelessDexManager3.mHandler.postDelayed(
                                            wirelessDexManager3.mRunnableDisableLowLatencyMode,
                                            5000L);
                                }
                            }
                        }
                    }
                };
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mDisplayManager = displayManager;
        displayManager.semRegisterWifiDisplayParameterListener(
                semWifiDisplayParameterListener, null);
        this.mInputManager = inputManager;
        this.mWindowManager = windowManagerService;
        this.mStateManager = iStateManager;
        ((StateManager) iStateManager).registerListener(new StateListener());
        this.mHandler = new Handler(serviceThread.getLooper());
        this.mUiManager = uiManager;
        context.registerReceiverAsUser(
                new Receiver(),
                UserHandle.ALL,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.HDMI_PLUGGED",
                        "com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE"),
                null,
                null,
                2);
    }

    public final void disconnect() {
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]WirelessDexManager", "disconnect()");
        }
        SemWifiDisplayStatus semGetWifiDisplayStatus =
                this.mDisplayManager.semGetWifiDisplayStatus();
        if (semGetWifiDisplayStatus == null || semGetWifiDisplayStatus.getActiveDisplay() == null) {
            return;
        }
        if (semGetWifiDisplayStatus.getConnectedState() == 2
                || semGetWifiDisplayStatus.getConnectedState() == 3) {
            this.mDisplayManager.disconnectWifiDisplay();
        }
    }

    public final void setWifiDisplayParameters(String str, String str2) {
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]WirelessDexManager",
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "setWifiDisplayParameters(key=", str, ", value=", str2, ")"));
        }
        this.mDisplayManager.semRequestWifiDisplayParameter(
                "setparams", new SemWifiDisplayParameter(str, str2));
    }
}
