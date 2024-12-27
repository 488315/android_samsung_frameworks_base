package com.android.server.desktopmode;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

import com.att.iqi.lib.metrics.hw.HwConstants;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.mcf.McfAdapter;
import com.samsung.android.mcf.McfBleAdapter;
import com.samsung.android.mcf.ble.BleAdapterCallback;
import com.samsung.android.mcf.ble.BleScanCallback;
import com.samsung.android.mcf.ble.BleScanFilter;
import com.samsung.android.mcf.ble.BleScanSettings;
import com.samsung.android.mcf.ble.BleScanner;

import java.util.ArrayList;

public final class McfManager {
    public static final byte[] mScanFilterData = {
        66, 4, 0, HwConstants.IQ_CONFIG_POS_WIFI_ENABLED, 0, 0, 0, 0, 8
    };
    public static final byte[] mScanFilterDataMask = {-1, -1, 0, -1, 0, 0, 0, 0, 8};
    public final AnonymousClass3 mBleAdapterCallback;
    public final BleAdvertiserServiceManager mBleAdvertiserServiceManager;
    public final AnonymousClass5 mBleScanCallback;
    public BleScanner mBleScanner;
    public int mBleStartScanReason;
    public final Context mContext;
    public int mLastBleScanFailedErrorCode;
    public String mLastBleScanResult;
    public McfAdapter mMcfAdapter;
    public final AnonymousClass4 mMcfAdapterListener;
    public McfBleAdapter mMcfBleAdapter;
    public final McfHandler mMcfHandler;
    public final ContentResolver mResolver;
    public final AnonymousClass2 mStateListener;
    public final IStateManager mStateManager;
    public final PowerManager.WakeLock mWakeLock;
    public final AnonymousClass6 mWirelessDeXBleAddressSettingChangedListener;
    public String mBleScannerState = "BLE_SCANNER_STATE_MCF_ADAPTER_UNBIND";
    public long mBleAdvertiserServiceTimeout = -1;
    public boolean mIsRequestingBindMcfAdapter = false;
    public long mWakeLockTimeout = 0;
    public boolean mRegisterIntent = false;
    public final AnonymousClass1 mBroadcastReceiver =
            new BroadcastReceiver() { // from class: com.android.server.desktopmode.McfManager.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.d(
                                "[DMS]McfManager",
                                "onReceive intent.getAction():" + intent.getAction());
                    }
                    String action = intent.getAction();
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                        McfManager.this.mMcfHandler.removeMessages(701);
                        McfHandler mcfHandler = McfManager.this.mMcfHandler;
                        mcfHandler.sendMessage(mcfHandler.obtainMessage(701));
                    } else if ("com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED"
                            .equals(action)) {
                        McfManager.this.mMcfHandler.removeMessages(
                                EndpointMonitorConst.TRACE_EVENT_SCHED_PROCESS_EXIT);
                        McfHandler mcfHandler2 = McfManager.this.mMcfHandler;
                        mcfHandler2.sendMessage(
                                mcfHandler2.obtainMessage(
                                        EndpointMonitorConst.TRACE_EVENT_SCHED_PROCESS_EXIT,
                                        intent));
                    } else if ("com.samsung.android.nearbyscanning".equals(action)) {
                        McfManager.this.mMcfHandler.removeMessages(
                                EndpointMonitorConst.TRACE_EVENT_SCHED_PROCESS_FORK);
                        McfHandler mcfHandler3 = McfManager.this.mMcfHandler;
                        mcfHandler3.sendMessage(
                                mcfHandler3.obtainMessage(
                                        EndpointMonitorConst.TRACE_EVENT_SCHED_PROCESS_FORK));
                    }
                }
            };

    public final class McfHandler extends Handler {
        public McfHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i;
            boolean z;
            int i2 = message.what;
            McfManager mcfManager = McfManager.this;
            if (i2 == 201) {
                int intValue = ((Integer) message.obj).intValue();
                McfBleAdapter mcfBleAdapter = mcfManager.mMcfBleAdapter;
                if (mcfBleAdapter == null || 2 != intValue) {
                    return;
                }
                if (mcfBleAdapter.isNetworkEnabled(1)) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.d(
                                "[DMS]McfManager",
                                "handleMcfServiceStateChanged - BLE NETWORK is enabled");
                        return;
                    }
                    return;
                } else {
                    if (DesktopModeFeature.DEBUG) {
                        Log.i(
                                "[DMS]McfManager",
                                "handleMcfServiceStateChanged - BLE NETWORK is not enabled. Need to"
                                    + " check unbind");
                    }
                    return;
                }
            }
            if (i2 == 401) {
                mcfManager.mBleScannerState = "BLE_SCANNER_STATE_SCAN_RESULT";
                StateManager.InternalState state =
                        ((StateManager) mcfManager.mStateManager).getState();
                BleAdvertiserServiceManager bleAdvertiserServiceManager =
                        mcfManager.mBleAdvertiserServiceManager;
                if (!bleAdvertiserServiceManager.mBound
                        && (i = state.mDesktopModeState.enabled) != 3
                        && i != 4) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.d("[DMS]McfManager", "handleMcfAdapterScannerScanResult bindService");
                    }
                    bleAdvertiserServiceManager.bindService();
                }
                mcfManager.tryBleAdvertiserServiceUnbind(60000L);
                return;
            }
            String[] strArr = null;
            if (i2 == 301) {
                int i3 = message.arg1;
                McfAdapter mcfAdapter = (McfAdapter) message.obj;
                boolean z2 = DesktopModeFeature.DEBUG;
                if (z2) {
                    DesktopModeService$$ExternalSyntheticOutline0.m(
                            i3, "MSG_MCF_ADAPTER_SERVICE_CONNECTED retryCount=", "[DMS]McfManager");
                }
                if (mcfAdapter != null) {
                    mcfManager.mBleScannerState = "BLE_SCANNER_STATE_MCF_ADAPTER_BIND";
                    mcfManager.mMcfAdapter = mcfAdapter;
                } else {
                    mcfManager.getClass();
                }
                try {
                    if (mcfManager.mMcfAdapter == null) {
                        if (z2) {
                            Log.d(
                                    "[DMS]McfManager",
                                    "handleMcfAdapterServiceConnected mMcfAdapter null");
                            return;
                        }
                        return;
                    }
                    if (z2) {
                        Log.d("[DMS]McfManager", "handleMcfAdapterServiceConnected mMcfAdapter");
                    }
                    if (mcfManager.mMcfBleAdapter == null) {
                        if (z2) {
                            Log.d(
                                    "[DMS]McfManager",
                                    "handleMcfAdapterServiceConnected getBleAdapter");
                        }
                        mcfManager.mMcfBleAdapter =
                                mcfManager.mMcfAdapter.getBleAdapter(
                                        35, mcfManager.mBleAdapterCallback);
                    }
                    McfBleAdapter mcfBleAdapter2 = mcfManager.mMcfBleAdapter;
                    if (mcfBleAdapter2 == null) {
                        if (z2) {
                            Log.d(
                                    "[DMS]McfManager",
                                    "handleMcfAdapterServiceConnected mMcfBleAdapter null");
                            return;
                        }
                        return;
                    } else {
                        if (mcfBleAdapter2.isNetworkEnabled(1)) {
                            String settingsAsUser =
                                    DesktopModeSettings.getSettingsAsUser(
                                            mcfManager.mResolver,
                                            "ble_mac_address_list",
                                            (String) null,
                                            DesktopModeSettings.sCurrentUserId);
                            if (settingsAsUser != null) {
                                strArr = settingsAsUser.split(",");
                            } else {
                                Log.e("[DMS]McfManager", "There is no bleMacAddress");
                            }
                            mcfManager.tryBleScannerStartScan(strArr);
                            return;
                        }
                        if (i3 >= 3) {
                            mcfManager.mBleScannerState = "BLE_SCANNER_STATE_BLE_NETWORK_NOT_READY";
                            return;
                        }
                        mcfManager.mBleScannerState = "BLE_SCANNER_STATE_BLE_NETWORK_NOT_READY_YET";
                        McfHandler mcfHandler = mcfManager.mMcfHandler;
                        mcfHandler.removeMessages(301);
                        mcfHandler.sendMessageDelayed(
                                mcfHandler.obtainMessage(301, i3 + 1, 0, null), 3000L);
                        return;
                    }
                } catch (Exception e) {
                    Log.e("[DMS]McfManager", "handleMcfAdapterServiceConnected Exception:" + e);
                    return;
                }
            }
            if (i2 == 302) {
                mcfManager.unbindMcfAdapter();
                mcfManager.mBleScannerState = "BLE_SCANNER_STATE_MCF_ADAPTER_UNBIND";
                mcfManager.tryBleAdvertiserServiceUnbind(0L);
                return;
            }
            switch (i2) {
                case 101:
                    long longValue = ((Long) message.obj).longValue();
                    mcfManager.getClass();
                    if (DesktopModeFeature.DEBUG) {
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        "handleBleAdvertiserServiceUnbind timeout ",
                                        longValue,
                                        ", mBleScannerState=");
                        m.append(mcfManager.mBleScannerState);
                        Log.w("[DMS]McfManager", m.toString());
                    }
                    if (!mcfManager.mBleScannerState.equals(
                            "BLE_SCANNER_STATE_MCF_ADAPTER_UNBIND")) {
                        mcfManager.mBleScannerState = "BLE_SCANNER_STATE_SCAN_TIMEOUT";
                    }
                    mcfManager.mBleAdvertiserServiceTimeout = -1L;
                    mcfManager.mBleAdvertiserServiceManager.unbindService();
                    PowerManager.WakeLock wakeLock = mcfManager.mWakeLock;
                    if (wakeLock != null && wakeLock.isHeld()) {
                        mcfManager.mWakeLockTimeout = 0L;
                        mcfManager.mWakeLock.release();
                        break;
                    }
                    break;
                case 102:
                    String str = (String) message.obj;
                    mcfManager.getClass();
                    if (str != null) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.d(
                                    "[DMS]McfManager",
                                    "handleWirelessDexBleMacAddressChanged changed.");
                        }
                        if (mcfManager.mMcfAdapter != null) {
                            BleScanner bleScanner = mcfManager.mBleScanner;
                            if (bleScanner != null) {
                                bleScanner.stopScan(mcfManager.mBleScanCallback);
                                mcfManager.mBleScanner = null;
                                mcfManager.mBleScannerState = "BLE_SCANNER_STATE_STOP_SCAN_REQUEST";
                            }
                            mcfManager.mBleScannerState = "BLE_SCANNER_STATE_SCAN_FILTER_CHANGE";
                            McfHandler mcfHandler2 = mcfManager.mMcfHandler;
                            mcfHandler2.removeMessages(301);
                            mcfHandler2.sendMessageDelayed(
                                    mcfHandler2.obtainMessage(301, 0, 0, null), 100L);
                            break;
                        } else {
                            mcfManager.initialize(1002);
                            break;
                        }
                    } else {
                        boolean z3 = DesktopModeFeature.DEBUG;
                        if (z3) {
                            Log.d(
                                    "[DMS]McfManager",
                                    "handleWirelessDexBleMacAddressChanged deleted.");
                        }
                        if (mcfManager.mRegisterIntent) {
                            if (z3) {
                                Log.d("[DMS]McfManager", "unregisterIntent");
                            }
                            mcfManager.mRegisterIntent = false;
                            mcfManager.mContext.unregisterReceiver(mcfManager.mBroadcastReceiver);
                        }
                        mcfManager.unbindMcfAdapter();
                        mcfManager.tryBleAdvertiserServiceUnbind(0L);
                        break;
                    }
                case 103:
                    boolean booleanValue = ((Boolean) message.obj).booleanValue();
                    if (mcfManager.bleMacAddressListExists()) {
                        if (!booleanValue) {
                            BleAdvertiserServiceManager bleAdvertiserServiceManager2 =
                                    mcfManager.mBleAdvertiserServiceManager;
                            if (!bleAdvertiserServiceManager2.mBound
                                    && McfManager.isNearbyScanningOn(mcfManager.mContext)) {
                                McfBleAdapter mcfBleAdapter3 = mcfManager.mMcfBleAdapter;
                                if (mcfBleAdapter3 != null) {
                                    z = mcfBleAdapter3.isNetworkEnabled(1);
                                } else {
                                    BluetoothAdapter defaultAdapter =
                                            BluetoothAdapter.getDefaultAdapter();
                                    z = defaultAdapter != null && defaultAdapter.semIsBleEnabled();
                                }
                                if (z) {
                                    bleAdvertiserServiceManager2.bindService();
                                    mcfManager.tryBleAdvertiserServiceUnbind(45000L);
                                    break;
                                }
                            }
                        } else {
                            mcfManager.tryBleAdvertiserServiceUnbind(0L);
                            break;
                        }
                    }
                    break;
                case 104:
                    mcfManager.initialize(1005);
                    break;
                default:
                    switch (i2) {
                        case 701:
                            Context context = mcfManager.mContext;
                            if (context == null
                                    || Settings.Global.getInt(
                                                    context.getContentResolver(),
                                                    "airplane_mode_on",
                                                    0)
                                            == 0) {
                                mcfManager.initialize(1003);
                                break;
                            }
                            break;
                        case EndpointMonitorConst.TRACE_EVENT_SCHED_PROCESS_EXIT /* 702 */:
                            Intent intent = (Intent) message.obj;
                            mcfManager.getClass();
                            int intExtra =
                                    intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 10);
                            if (DesktopModeFeature.DEBUG) {
                                DesktopModeService$$ExternalSyntheticOutline0.m(
                                        intExtra,
                                        "handleSemBleStateChanged STATE:",
                                        "[DMS]McfManager");
                            }
                            if (intExtra == 15 || intExtra == 12) {
                                mcfManager.initialize(1004);
                                break;
                            }
                            break;
                        case EndpointMonitorConst.TRACE_EVENT_SCHED_PROCESS_FORK /* 703 */:
                            mcfManager.getClass();
                            if (DesktopModeFeature.DEBUG) {
                                Log.d("[DMS]McfManager", "handleNearbyScanningChanged");
                            }
                            if (!McfManager.isNearbyScanningOn(mcfManager.mContext)) {
                                mcfManager.unbindMcfAdapter();
                                mcfManager.mBleScannerState =
                                        "BLE_SCANNER_STATE_MCF_ADAPTER_UNBIND";
                                mcfManager.tryBleAdvertiserServiceUnbind(0L);
                                break;
                            } else {
                                mcfManager.initialize(1006);
                                break;
                            }
                    }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.desktopmode.McfManager$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.desktopmode.McfManager$3] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.server.desktopmode.McfManager$4] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.desktopmode.McfManager$5] */
    public McfManager(
            Context context,
            ServiceThread serviceThread,
            IStateManager iStateManager,
            SettingsHelper settingsHelper,
            BleAdvertiserServiceManager bleAdvertiserServiceManager) {
        StateManager.StateListener stateListener =
                new StateManager
                        .StateListener() { // from class:
                                           // com.android.server.desktopmode.McfManager.2
                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onBootCompleted() {
                        McfManager.this.initialize(1001);
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onDualModeStopLoadingScreen(boolean z) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.i("[DMS]McfManager", "onDualModeStopLoadingScreen enter=" + z);
                        }
                        McfManager mcfManager = McfManager.this;
                        mcfManager.mMcfHandler.removeMessages(103);
                        Boolean valueOf = Boolean.valueOf(z);
                        McfHandler mcfHandler = mcfManager.mMcfHandler;
                        mcfHandler.sendMessage(mcfHandler.obtainMessage(103, valueOf));
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onPackageStateChanged(
                            StateManager.InternalState internalState) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.i(
                                    "[DMS]McfManager",
                                    "onPackageStateChanged state=" + internalState);
                        }
                        Boolean bool =
                                (Boolean)
                                        internalState.mPackageState.get(
                                                "com.sec.android.desktopmode.uiservice");
                        if (bool == null || !bool.booleanValue()) {
                            return;
                        }
                        McfManager mcfManager = McfManager.this;
                        mcfManager.mMcfHandler.removeMessages(104);
                        McfHandler mcfHandler = mcfManager.mMcfHandler;
                        mcfHandler.sendMessage(mcfHandler.obtainMessage(104));
                    }
                };
        this.mBleAdapterCallback =
                new BleAdapterCallback() { // from class:
                                           // com.android.server.desktopmode.McfManager.3
                    public final void onMcfServiceStateChanged(int i, int i2) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.i("[DMS]McfManager", "onMcfServiceStateChanged state " + i);
                        }
                        McfManager.this.mMcfHandler.removeMessages(201);
                        McfHandler mcfHandler = McfManager.this.mMcfHandler;
                        mcfHandler.sendMessage(mcfHandler.obtainMessage(201, Integer.valueOf(i)));
                    }
                };
        this.mMcfAdapterListener =
                new McfAdapter
                        .McfAdapterListener() { // from class:
                                                // com.android.server.desktopmode.McfManager.4
                    public final void onServiceConnected(McfAdapter mcfAdapter) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.i("[DMS]McfManager", "McfAdapterListener onServiceConnected");
                        }
                        McfManager.this.mMcfHandler.removeMessages(301);
                        McfHandler mcfHandler = McfManager.this.mMcfHandler;
                        mcfHandler.sendMessageDelayed(
                                mcfHandler.obtainMessage(301, 0, 0, mcfAdapter), 100L);
                    }

                    public final void onServiceDisconnected() {
                        if (DesktopModeFeature.DEBUG) {
                            Log.i("[DMS]McfManager", "McfAdapterListener onServiceDisconnected");
                        }
                        McfManager.this.mMcfHandler.removeMessages(
                                FrameworkStatsLog
                                        .APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ALARM_MANAGER_WHILE_IDLE);
                        McfHandler mcfHandler = McfManager.this.mMcfHandler;
                        mcfHandler.sendMessage(
                                mcfHandler.obtainMessage(
                                        FrameworkStatsLog
                                                .APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ALARM_MANAGER_WHILE_IDLE));
                    }

                    public final void onServiceRemoteException() {
                        Log.e("[DMS]McfManager", "McfAdapterListener onServiceRemoteException");
                    }
                };
        this.mBleScanCallback =
                new BleScanCallback() { // from class: com.android.server.desktopmode.McfManager.5
                    public final void onScanFailed(int i) {
                        McfManager.this.mLastBleScanFailedErrorCode = i;
                        Log.e("[DMS]McfManager", "onScanFailed errorCode " + i);
                    }

                    public final void onScanResult(ScanResult scanResult) {
                        McfManager.this.mLastBleScanResult = scanResult.toString();
                        if (McfManager.this.mBleAdvertiserServiceTimeout != 60000
                                && DesktopModeFeature.DEBUG) {
                            Log.i(
                                    "[DMS]McfManager",
                                    "onScanResult result " + scanResult.toString());
                        }
                        McfManager.this.mMcfHandler.removeMessages(401);
                        McfHandler mcfHandler = McfManager.this.mMcfHandler;
                        mcfHandler.sendMessage(mcfHandler.obtainMessage(401));
                    }
                };
        SettingsHelper.OnSettingChangedListener onSettingChangedListener =
                new SettingsHelper.OnSettingChangedListener() { // from class:
                    // com.android.server.desktopmode.McfManager.6
                    @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
                    public final void onSettingChanged(String str) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.i(
                                    "[DMS]McfManager",
                                    "mWirelessDeXBleAddressSettingChangedListener onSettingChanged"
                                        + " value="
                                            + str);
                        }
                        McfManager mcfManager = McfManager.this;
                        mcfManager.mMcfHandler.removeMessages(102);
                        McfHandler mcfHandler = mcfManager.mMcfHandler;
                        mcfHandler.sendMessage(mcfHandler.obtainMessage(102, str));
                    }
                };
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mMcfHandler = new McfHandler(serviceThread.getLooper());
        this.mStateManager = iStateManager;
        ((StateManager) iStateManager).registerListener(stateListener);
        settingsHelper.registerListener(onSettingChangedListener);
        this.mBleAdvertiserServiceManager = bleAdvertiserServiceManager;
        this.mWakeLock =
                ((PowerManager) context.getSystemService("power"))
                        .newWakeLock(1, "DesktopMode:McfManager");
    }

    public static String bleStartScanReasonToString(int i) {
        switch (i) {
            case 1001:
                return "BLE_START_SCAN_REASON_BOOT_COMPLETE";
            case 1002:
                return "BLE_START_SCAN_REASON_DEX_BLE_ADDRESS_CHANGED";
            case 1003:
                return "BLE_START_SCAN_REASON_AIRPLANE_MODE_CHANGED";
            case 1004:
                return "BLE_START_SCAN_REASON_BLUETOOTH_STATUS_CHANGED";
            case 1005:
                return "BLE_START_SCAN_REASON_PACKAGE_STATE_CHANGED";
            case 1006:
                return "BLE_START_SCAN_REASON_NEARBY_SCANNING_CHANGED";
            default:
                return VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown=");
        }
    }

    public static boolean isNearbyScanningOn(Context context) {
        if (context != null) {
            try {
                return Settings.System.getInt(
                                context.getContentResolver(), "nearby_scanning_enabled")
                        == 1;
            } catch (Settings.SettingNotFoundException e) {
                if (DesktopModeFeature.DEBUG) {
                    Log.w("[DMS]McfManager", "SettingNotFoundException " + e);
                }
            }
        }
        return true;
    }

    public final boolean bleMacAddressListExists() {
        String settingsAsUser =
                DesktopModeSettings.getSettingsAsUser(
                        this.mResolver,
                        "ble_mac_address_list",
                        (String) null,
                        DesktopModeSettings.sCurrentUserId);
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]McfManager", "bleMacAddressListExists bleMacAddress=" + settingsAsUser);
        }
        return settingsAsUser != null;
    }

    public final void initialize(int i) {
        Context context;
        if (bleMacAddressListExists()) {
            if (!this.mRegisterIntent) {
                if (DesktopModeFeature.DEBUG) {
                    Log.d("[DMS]McfManager", "registerIntent");
                }
                this.mRegisterIntent = true;
                this.mContext.registerReceiver(
                        this.mBroadcastReceiver,
                        GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                "android.intent.action.AIRPLANE_MODE",
                                "com.samsung.bluetooth.adapter.action.BLE_STATE_CHANGED",
                                "com.samsung.android.nearbyscanning"),
                        2);
            }
            if (this.mMcfAdapter == null
                    && !this.mIsRequestingBindMcfAdapter
                    && isNearbyScanningOn(this.mContext)) {
                if (i != 1001
                        || (context = this.mContext) == null
                        || Settings.Global.getInt(
                                        context.getContentResolver(), "airplane_mode_on", 0)
                                == 0) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.i(
                                "[DMS]McfManager",
                                "bindMcfAdapter reason=" + bleStartScanReasonToString(i));
                    }
                    this.mBleStartScanReason = i;
                    if (McfAdapter.bindService(this.mContext, this.mMcfAdapterListener)) {
                        this.mIsRequestingBindMcfAdapter = true;
                    }
                }
            }
        }
    }

    public final void tryBleAdvertiserServiceUnbind(long j) {
        if (this.mBleAdvertiserServiceTimeout != j) {
            if (DesktopModeFeature.DEBUG) {
                Log.i("[DMS]McfManager", "tryBleAdvertiserServiceUnbind timeout changed " + j);
            }
            this.mBleAdvertiserServiceTimeout = j;
        }
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null && wakeLock.isHeld()) {
            this.mWakeLockTimeout = 0L;
            this.mWakeLock.release();
        }
        PowerManager.WakeLock wakeLock2 = this.mWakeLock;
        if (wakeLock2 != null && !wakeLock2.isHeld()) {
            long j2 = 10000 + j;
            this.mWakeLockTimeout = j2;
            this.mWakeLock.acquire(j2);
        }
        McfHandler mcfHandler = this.mMcfHandler;
        mcfHandler.removeMessages(101);
        mcfHandler.sendMessageDelayed(mcfHandler.obtainMessage(101, Long.valueOf(j)), j);
    }

    public final void tryBleScannerStartScan(String[] strArr) {
        McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
        if (mcfBleAdapter != null) {
            BleScanner bleScanner = mcfBleAdapter.getBleScanner();
            this.mBleScanner = bleScanner;
            if (bleScanner != null) {
                BleScanSettings.Builder builder = new BleScanSettings.Builder();
                builder.setTimeout(0);
                BleScanSettings build = builder.build();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                if (strArr != null) {
                    for (String str : strArr) {
                        if (BluetoothAdapter.checkBluetoothAddress(str)) {
                            if (DesktopModeFeature.DEBUG) {
                                Log.d(
                                        "[DMS]McfManager",
                                        "tryBleScannerStartScan valid splitBleMac=" + str);
                            }
                            BleScanFilter.Builder builder2 = new BleScanFilter.Builder();
                            builder2.setManufacturerData(117, mScanFilterData, mScanFilterDataMask);
                            builder2.setDeviceAddress(str);
                            BleScanFilter build2 = builder2.build();
                            arrayList.add(build2);
                            arrayList2.add(build2);
                        } else {
                            Log.e(
                                    "[DMS]McfManager",
                                    "tryBleScannerStartScan invalid splitBleMac=" + str);
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    Log.e(
                            "[DMS]McfManager",
                            "tryBleScannerStartScan BLE_SCANNER_STATE_NO_SCAN_TARGET");
                    this.mBleScannerState = "BLE_SCANNER_STATE_NO_SCAN_TARGET";
                    return;
                }
                boolean startScan =
                        this.mBleScanner.startScan(
                                arrayList, arrayList2, build, this.mBleScanCallback);
                if (DesktopModeFeature.DEBUG) {
                    Log.i(
                            "[DMS]McfManager",
                            "tryBleScannerStartScan BLE_SCANNER_STATE_START_SCAN_REQUEST "
                                    + startScan);
                }
                this.mBleScannerState = "BLE_SCANNER_STATE_START_SCAN_REQUEST";
            }
        }
    }

    public final void unbindMcfAdapter() {
        if (DesktopModeFeature.DEBUG) {
            Log.i("[DMS]McfManager", "unbindMcfAdapter");
        }
        BleScanner bleScanner = this.mBleScanner;
        if (bleScanner != null) {
            bleScanner.stopScan(this.mBleScanCallback);
            this.mBleScanner = null;
            this.mBleScannerState = "BLE_SCANNER_STATE_STOP_SCAN_REQUEST";
        }
        McfBleAdapter mcfBleAdapter = this.mMcfBleAdapter;
        if (mcfBleAdapter != null) {
            mcfBleAdapter.close();
            this.mMcfBleAdapter = null;
        }
        McfAdapter mcfAdapter = this.mMcfAdapter;
        if (mcfAdapter != null) {
            mcfAdapter.unbindService();
            this.mMcfAdapter = null;
            this.mIsRequestingBindMcfAdapter = false;
        }
    }
}
