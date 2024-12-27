package com.android.server.desktopmode;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.SparseBooleanArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.lib.dexcontrol.NotSupportDexFeatureException;
import com.samsung.android.lib.dexcontrol.SDCDeviceController;
import com.samsung.android.lib.dexcontrol.model.dexpad.DexPad;
import com.samsung.android.lib.dexcontrol.model.dexstation.DexStation;
import com.samsung.android.lib.dexcontrol.utils.SLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DockManager {
    public final Context mContext;
    public final SparseBooleanArray mDockControlLibError;
    public final DockHandler mHandler;
    public final AnonymousClass3 mLastDockControlLibMsgState;
    public final ContentResolver mResolver;
    public final AnonymousClass1 mStateListener;
    public final IStateManager mStateManager;
    public String mDockVersion = null;
    public String mDockVersionExtra = null;
    public String mLastDockVersion = null;
    public SDCDeviceController mISDCDeviceController = null;
    public AnonymousClass3 mControlResponseListener = null;
    public int mDockChargerPower = 0;
    public int mDockChargerSupport = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.desktopmode.DockManager$3, reason: invalid class name */
    public final class AnonymousClass3 {
        public Object this$0;

        public static String msgStateToString(int i) {
            if (i == 210) {
                return "MSG_DOCK_RESPONSE_UNDEFINED";
            }
            if (i == 211) {
                return "MSG_DOCK_RESPONSE_POWER_INFO";
            }
            if (i == 220) {
                return "MSG_DOCK_ERROR_UNDEFINED";
            }
            if (i == 221) {
                return "MSG_DOCK_ERROR";
            }
            if (i == 230) {
                return "MSG_DOCK_VERSION_UNDEFINED";
            }
            if (i == 231) {
                return "MSG_DOCK_VERSION_DSVERSION_UPDATED";
            }
            if (i == 240) {
                return "MSG_DOCK_EXTRA_UNDEFINED";
            }
            if (i == 241) {
                return "MSG_DOCK_EXTRA_ERROR_UPDATED";
            }
            switch (i) {
                case 200:
                    return "MSG_DOCK_REQUEST_UNDEFINED";
                case 201:
                    return "MSG_DOCK_REQUEST_CREATE";
                case 202:
                    return "MSG_DOCK_REQUEST_POWER_INFO";
                case 203:
                    return "MSG_DOCK_REQUEST_DESTROY";
                default:
                    return VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown=");
            }
        }

        public void setLast(int i, int i2, String str) {
            ((ArrayMap) this.this$0).put(Integer.valueOf(i), msgStateToString(i2) + "," + str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DockHandler extends Handler {
        public DockHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            DockManager dockManager = DockManager.this;
            StateManager.InternalState state =
                    ((StateManager) dockManager.mStateManager).getState();
            int i = message.what;
            if (i == 150) {
                boolean z = DesktopModeFeature.DEBUG;
                if (z) {
                    Log.d("[DMS]DockManager", "MSG_DOCK_REQUEST_UPDATE_DOCK_LIB_STATUS");
                }
                if (z) {
                    Log.d(
                            "[DMS]DockManager",
                            "updateDockLibStatus(), isDesktopDockConnected="
                                    + state.mDockState.isDexStation()
                                    + ", isDexPadConnected="
                                    + state.mDockState.isDexPad()
                                    + ", mDockState="
                                    + state.mDockState.mType
                                    + ", mPrevDockState="
                                    + state.mPreviousDockState.mType
                                    + ", mDockChargerPower="
                                    + dockManager.mDockChargerPower
                                    + ", mDockChargerSupport="
                                    + dockManager.mDockChargerSupport);
                }
                HardwareManager.DockState dockState = state.mDockState;
                if (dockState.mType == 10001) {
                    DockHandler dockHandler = dockManager.mHandler;
                    dockHandler.removeMessages(201);
                    dockHandler.sendMessage(dockHandler.obtainMessage(201, 40992));
                    return;
                }
                if (dockState.isDexPad()) {
                    DockHandler dockHandler2 = dockManager.mHandler;
                    dockHandler2.removeMessages(201);
                    dockHandler2.sendMessage(dockHandler2.obtainMessage(201, 41001));
                    return;
                } else if (state.mDockState.isUndocked()
                        && state.mPreviousDockState.isDexStation()) {
                    DockHandler dockHandler3 = dockManager.mHandler;
                    dockHandler3.removeMessages(203);
                    dockHandler3.sendMessage(dockHandler3.obtainMessage(203, 40992));
                    return;
                } else {
                    if (state.mDockState.isUndocked() && state.mPreviousDockState.isDexPad()) {
                        DockHandler dockHandler4 = dockManager.mHandler;
                        dockHandler4.removeMessages(203);
                        dockHandler4.sendMessage(dockHandler4.obtainMessage(203, 41001));
                        return;
                    }
                    return;
                }
            }
            IStateManager iStateManager = dockManager.mStateManager;
            if (i == 211) {
                int i2 = message.arg1;
                int i3 = message.arg2;
                boolean z2 = DesktopModeFeature.DEBUG;
                if (z2) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    i2,
                                    "MSG_DOCK_RESPONSE_POWER_INFO"
                                        + " onConnectedPowerChargerInfoUpdated, power=",
                                    " previous power=");
                    m.append(dockManager.mDockChargerPower);
                    m.append(" support=");
                    m.append(i3);
                    Log.d("[DMS]DockManager", m.toString());
                }
                dockManager.mLastDockControlLibMsgState.setLast(101, message.what, i2 + "," + i3);
                if (i2 == 0 || i2 != dockManager.mDockChargerPower) {
                    if (i2 < 15) {
                        int i4 = dockManager.mDockChargerPower;
                        if (i4 != 0 && i4 < 15) {
                            return;
                        }
                        if (z2) {
                            Log.d("[DMS]DockManager", "updateDockLowChargerPower true");
                        }
                        ((StateManager) iStateManager).setDockLowChargerState(1);
                    } else {
                        if (dockManager.mDockChargerPower >= 15) {
                            return;
                        }
                        if (z2) {
                            Log.d("[DMS]DockManager", "updateDockLowChargerPower false");
                        }
                        ((StateManager) iStateManager).setDockLowChargerState(2);
                    }
                    dockManager.mDockChargerPower = i2;
                    dockManager.mDockChargerSupport = i3;
                    DockManager.m405$$Nest$mrequestUpdateDockLibStatus(
                            dockManager, "dockLowChargerPowerUpdated");
                    return;
                }
                return;
            }
            if (i == 221) {
                int intValue = ((Integer) message.obj).intValue();
                if (DesktopModeFeature.DEBUG) {
                    Log.d(
                            "[DMS]DockManager",
                            "MSG_DOCK_ERROR onError, error="
                                    + DockManager.m407$$Nest$smdockControlErrorToString(intValue));
                }
                dockManager.mLastDockControlLibMsgState.setLast(
                        102,
                        message.what,
                        DockManager.m407$$Nest$smdockControlErrorToString(intValue));
                dockManager.mDockControlLibError.put(intValue, true);
                StringBuilder sb = new StringBuilder();
                for (int i5 = 0; i5 < dockManager.mDockControlLibError.size(); i5++) {
                    sb.append(
                            DockManager.m407$$Nest$smdockControlErrorToString(
                                    dockManager.mDockControlLibError.keyAt(i5)));
                    sb.append(" = ");
                    sb.append(dockManager.mDockControlLibError.valueAt(i5));
                    sb.append(",");
                }
                dockManager.mLastDockControlLibMsgState.setLast(
                        104,
                        FrameworkStatsLog
                                .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_HYBRID_NNHDR_MERGE_QZ,
                        sb.toString());
                return;
            }
            if (i == 231) {
                String str = (String) message.obj;
                if (DesktopModeFeature.DEBUG) {
                    Log.d(
                            "[DMS]DockManager",
                            "MSG_DOCK_VERSION_DSVERSION_UPDATED onDSVersionUpdated, version="
                                    + str);
                }
                dockManager.mLastDockControlLibMsgState.setLast(103, message.what, str);
                if (str != null) {
                    dockManager.mDockVersionExtra = str;
                } else {
                    dockManager.mDockVersionExtra = "####";
                }
                DockManager.m406$$Nest$mupdateDockVersionToSettings(dockManager, true);
                return;
            }
            switch (i) {
                case 201:
                    int intValue2 = ((Integer) message.obj).intValue();
                    boolean z3 = DesktopModeFeature.DEBUG;
                    if (z3) {
                        DesktopModeService$$ExternalSyntheticOutline0.m(
                                intValue2, "MSG_DOCK_REQUEST_CREATE dockType=", "[DMS]DockManager");
                    }
                    if (intValue2 != 40992 && intValue2 != 41001) {
                        if (z3) {
                            Log.d("[DMS]DockManager", "createDockControlLib is not supported");
                            return;
                        }
                        return;
                    }
                    if (dockManager.mControlResponseListener == null) {
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
                        anonymousClass3.this$0 = dockManager;
                        dockManager.mControlResponseListener = anonymousClass3;
                    }
                    if (dockManager.mISDCDeviceController == null) {
                        if (z3) {
                            DesktopModeService$$ExternalSyntheticOutline0.m(
                                    intValue2,
                                    "createDockControlLib dockType=",
                                    "[DMS]DockManager");
                        }
                        dockManager.mLastDockControlLibMsgState.setLast(100, 201, null);
                        Context context = dockManager.mContext;
                        AnonymousClass3 anonymousClass32 = dockManager.mControlResponseListener;
                        SDCDeviceController sDCDeviceController = new SDCDeviceController();
                        sDCDeviceController.mDexStation = null;
                        sDCDeviceController.mDexPad = null;
                        sDCDeviceController.mDexDevicePID = intValue2;
                        SLog.i(
                                "SDCDeviceController",
                                "Model : " + intValue2 + ", DexControlLib Version : ");
                        int i6 = sDCDeviceController.mDexDevicePID;
                        if (i6 == 40992) {
                            sDCDeviceController.mDexStation = new DexStation(context);
                        } else if (i6 == 41001) {
                            sDCDeviceController.mDexPad = new DexPad(context, anonymousClass32);
                        } else {
                            SLog.e(
                                    "SDCDeviceController",
                                    "SDCDeviceController unknown pid = " + intValue2);
                        }
                        dockManager.mISDCDeviceController = sDCDeviceController;
                        if (intValue2 == 41001) {
                            DockHandler dockHandler5 = dockManager.mHandler;
                            dockHandler5.removeMessages(202);
                            dockHandler5.obtainMessage(202).sendToTarget();
                            return;
                        }
                        return;
                    }
                    return;
                case 202:
                    if (DesktopModeFeature.DEBUG) {
                        Log.d(
                                "[DMS]DockManager",
                                "MSG_DOCK_REQUEST_POWER_INFO"
                                    + " requestConnectedPowerChargerInfoUpdate");
                    }
                    if (dockManager.mISDCDeviceController != null) {
                        try {
                            dockManager.mLastDockControlLibMsgState.setLast(
                                    100, message.what, null);
                            SDCDeviceController sDCDeviceController2 =
                                    dockManager.mISDCDeviceController;
                            DexPad dexPad = sDCDeviceController2.mDexPad;
                            if (dexPad == null) {
                                throw new NotSupportDexFeatureException(
                                        sDCDeviceController2.mDexDevicePID);
                            }
                            dexPad.requestConnectedPowerChargerInfoUpdate();
                            return;
                        } catch (NotSupportDexFeatureException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    return;
                case 203:
                    int intValue3 = ((Integer) message.obj).intValue();
                    boolean z4 = DesktopModeFeature.DEBUG;
                    if (z4) {
                        DesktopModeService$$ExternalSyntheticOutline0.m(
                                intValue3,
                                "MSG_DOCK_REQUEST_DESTROY dockType=",
                                "[DMS]DockManager");
                    }
                    if (dockManager.mControlResponseListener != null) {
                        dockManager.mControlResponseListener = null;
                    }
                    if (dockManager.mISDCDeviceController != null) {
                        if (z4) {
                            Log.d("[DMS]DockManager", "destroyDockControlLib");
                        }
                        dockManager.mLastDockControlLibMsgState.setLast(100, 203, null);
                        SDCDeviceController sDCDeviceController3 =
                                dockManager.mISDCDeviceController;
                        sDCDeviceController3.getClass();
                        SLog.i("SDCDeviceController", "destroy");
                        DexStation dexStation = sDCDeviceController3.mDexStation;
                        if (dexStation != null) {
                            dexStation.destroy();
                        }
                        sDCDeviceController3.mDexStation = null;
                        DexPad dexPad2 = sDCDeviceController3.mDexPad;
                        if (dexPad2 != null) {
                            dexPad2.destroy();
                        }
                        sDCDeviceController3.mDexPad = null;
                        sDCDeviceController3.mDexDevicePID = 0;
                        dockManager.mISDCDeviceController = null;
                        if (intValue3 == 41001) {
                            ((StateManager) iStateManager).setDockLowChargerState(-1);
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: -$$Nest$mrequestUpdateDockLibStatus, reason: not valid java name */
    public static void m405$$Nest$mrequestUpdateDockLibStatus(DockManager dockManager, String str) {
        dockManager.getClass();
        if (DesktopModeFeature.DEBUG) {
            Log.d("[DMS]DockManager", "requestUpdateDockLibStatus, reason - ".concat(str));
        }
        DockHandler dockHandler = dockManager.mHandler;
        dockHandler.removeMessages(150);
        dockHandler.sendMessage(dockHandler.obtainMessage(150));
    }

    /* renamed from: -$$Nest$mupdateDockVersionToSettings, reason: not valid java name */
    public static void m406$$Nest$mupdateDockVersionToSettings(DockManager dockManager, boolean z) {
        if (z) {
            dockManager.getClass();
            dockManager.mDockVersion =
                    Utils.readFile("/sys/class/dp_sec/dex_ver", "")
                            + "."
                            + Utils.readFile("/sys/class/sec/ccic/acc_device_version", "");
            if (dockManager.mDockVersionExtra != null) {
                dockManager.mDockVersion += "." + dockManager.mDockVersionExtra;
            }
            dockManager.mLastDockVersion = dockManager.mDockVersion;
        } else {
            dockManager.mDockVersion = null;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.d(
                    "[DMS]DockManager",
                    "updateDockVersionToSettings(), mDockVersion = " + dockManager.mDockVersion);
        }
        DesktopModeSettings.setSettings(
                dockManager.mResolver, "dock_version", dockManager.mDockVersion);
    }

    /* renamed from: -$$Nest$smdockControlErrorToString, reason: not valid java name */
    public static String m407$$Nest$smdockControlErrorToString(int i) {
        switch (i) {
            case -6:
                return "FAN_LEVEL_CONTROL_DEX_BUSY";
            case -5:
                return "CONNECTED_POWER_CHARGER_INFO_UPDATE_DEX_BUSY";
            case -4:
                return "SET_FAST_CHARGING_DEX_BUSY";
            case -3:
                return "CONNECTED_POWER_CHARGER_INFO_UPDATE_UVDM_FAILED";
            case -2:
                return "CHARGING_MODE_UPDATE_UVDM_FAILED";
            case -1:
                return "SET_FAST_CHARGING_UVDM_FAILED";
            default:
                return VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown=");
        }
    }

    public DockManager(Context context, ServiceThread serviceThread, IStateManager iStateManager) {
        this.mHandler = null;
        StateManager.StateListener stateListener =
                new StateManager
                        .StateListener() { // from class:
                                           // com.android.server.desktopmode.DockManager.1
                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onDockStateChanged(StateManager.InternalState internalState) {
                        boolean z = DesktopModeFeature.DEBUG;
                        if (z) {
                            Log.d(
                                    "[DMS]DockManager",
                                    "onDockStateChanged n="
                                            + internalState.mDockState
                                            + ", p="
                                            + internalState.mPreviousDockState);
                        }
                        boolean isUndocked = internalState.mDockState.isUndocked();
                        DockManager dockManager = DockManager.this;
                        if (isUndocked
                                && (internalState.mPreviousDockState.isDexStation()
                                        || internalState.mPreviousDockState.isDexPad())) {
                            dockManager.getClass();
                            if (z) {
                                Log.d("[DMS]DockManager", "DockManager initialize()");
                            }
                            dockManager.mDockVersion = null;
                            dockManager.mDockVersionExtra = null;
                            dockManager.mDockChargerPower = 0;
                            dockManager.mDockChargerSupport = 0;
                        }
                        DockManager.m405$$Nest$mrequestUpdateDockLibStatus(
                                dockManager, "onDockStateChanged");
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onExternalDisplayConnectionChanged(
                            StateManager.InternalState internalState) {
                        boolean isHdmiConnected = internalState.isHdmiConnected();
                        DockManager dockManager = DockManager.this;
                        if (!isHdmiConnected) {
                            DockManager.m406$$Nest$mupdateDockVersionToSettings(dockManager, false);
                        } else if (HardwareManager.resolveDockType(
                                        internalState.mDockState.mRawDockUsbpdIds)
                                != -1) {
                            DockManager.m406$$Nest$mupdateDockVersionToSettings(dockManager, true);
                        }
                        DockManager.m405$$Nest$mrequestUpdateDockLibStatus(
                                dockManager, "onDisplayChanged");
                    }
                };
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mStateManager = iStateManager;
        ((StateManager) iStateManager).registerListener(stateListener);
        this.mHandler = new DockHandler(serviceThread.getLooper());
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        ArrayMap arrayMap = new ArrayMap();
        anonymousClass3.this$0 = arrayMap;
        arrayMap.put(100, AnonymousClass3.msgStateToString(200));
        arrayMap.put(101, "MSG_DOCK_RESPONSE_UNDEFINED");
        arrayMap.put(102, "MSG_DOCK_ERROR_UNDEFINED");
        arrayMap.put(103, "MSG_DOCK_VERSION_UNDEFINED");
        arrayMap.put(104, "MSG_DOCK_EXTRA_UNDEFINED");
        this.mLastDockControlLibMsgState = anonymousClass3;
        this.mDockControlLibError = new SparseBooleanArray();
        context.registerReceiverAsUser(
                new BroadcastReceiver() { // from class:
                                          // com.android.server.desktopmode.DockManager.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        StateManager.InternalState state =
                                ((StateManager) DockManager.this.mStateManager).getState();
                        String action = intent.getAction();
                        if ("android.intent.action.ACTION_POWER_CONNECTED".equals(action)
                                || "android.intent.action.ACTION_POWER_DISCONNECTED"
                                        .equals(action)) {
                            if (DesktopModeFeature.DEBUG) {
                                Log.d("[DMS]DockManager", "onReceive(), action=" + action);
                            }
                            if (state.mDockState.isDexPad()) {
                                DockHandler dockHandler = DockManager.this.mHandler;
                                dockHandler.removeMessages(202);
                                dockHandler.obtainMessage(202).sendToTarget();
                            }
                        }
                    }
                },
                UserHandle.ALL,
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "android.intent.action.ACTION_POWER_CONNECTED",
                        "android.intent.action.ACTION_POWER_DISCONNECTED"),
                null,
                null,
                2);
    }
}
