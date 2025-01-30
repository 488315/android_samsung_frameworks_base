package com.android.server.desktopmode;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.SparseBooleanArray;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ServiceThread;
import com.android.server.desktopmode.StateManager;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.lib.dexcontrol.ISDCDeviceController;
import com.samsung.android.lib.dexcontrol.NotSupportDexFeatureException;
import com.samsung.android.lib.dexcontrol.SDCDeviceController;

/* loaded from: classes2.dex */
public class DockManager {
    public static final String TAG = "[DMS]" + DockManager.class.getSimpleName();
    public final Context mContext;
    public SparseBooleanArray mDockControlLibError;
    public DockHandler mHandler;
    public DockMsgState mLastDockControlLibMsgState;
    public final ContentResolver mResolver;
    public final StateManager.StateListener mStateListener;
    public final IStateManager mStateManager;
    public String mDockVersion = null;
    public String mDockVersionExtra = null;
    public String mLastDockVersion = null;
    public ISDCDeviceController mISDCDeviceController = null;
    public ISDCDeviceController.ControlResponseListener mControlResponseListener = null;
    public int mDockChargerPower = 0;
    public int mDockChargerSupport = 0;

    public DockManager(Context context, ServiceThread serviceThread, IStateManager iStateManager) {
        this.mHandler = null;
        StateManager.StateListener stateListener = new StateManager.StateListener() { // from class: com.android.server.desktopmode.DockManager.1
            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onExternalDisplayConnectionChanged(State state) {
                if (state.isHdmiConnected()) {
                    if (state.getDockState().isRawDockUsbpdIdSupported()) {
                        DockManager.this.updateDockVersionToSettings(true);
                    }
                } else {
                    DockManager.this.updateDockVersionToSettings(false);
                }
                DockManager.this.requestUpdateDockLibStatus("onDisplayChanged");
            }

            @Override // com.android.server.desktopmode.StateManager.StateListener
            public void onDockStateChanged(State state) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(DockManager.TAG, "onDockStateChanged n=" + state.getDockState() + ", p=" + state.getPreviousDockState());
                }
                if (state.getDockState().isUndocked() && (state.getPreviousDockState().isDexStation() || state.getPreviousDockState().isDexPad())) {
                    DockManager.this.initialize();
                }
                DockManager.this.requestUpdateDockLibStatus("onDockStateChanged");
            }
        };
        this.mStateListener = stateListener;
        this.mContext = context;
        this.mResolver = context.getContentResolver();
        this.mStateManager = iStateManager;
        iStateManager.registerListener(stateListener);
        this.mHandler = new DockHandler(serviceThread.getLooper());
        this.mLastDockControlLibMsgState = new DockMsgState(200, 210, FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_ASTRO, 230, 240);
        this.mDockControlLibError = new SparseBooleanArray();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        context.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.desktopmode.DockManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                State state = DockManager.this.mStateManager.getState();
                String action = intent.getAction();
                if ("android.intent.action.ACTION_POWER_CONNECTED".equals(action) || "android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
                    if (DesktopModeFeature.DEBUG) {
                        Log.m7d(DockManager.TAG, "onReceive(), action=" + action);
                    }
                    if (DockManager.isDexPadConnected(state)) {
                        DockManager.this.requestConnectedPowerChargerInfoUpdate();
                    }
                }
            }
        }, UserHandle.ALL, intentFilter, null, null);
    }

    public final void requestUpdateDockLibStatus(String str) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "requestUpdateDockLibStatus, reason - " + str);
        }
        this.mHandler.removeMessages(150);
        DockHandler dockHandler = this.mHandler;
        dockHandler.sendMessage(dockHandler.obtainMessage(150));
    }

    public final void updateDockLibStatus(State state) {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "updateDockLibStatus(), isDesktopDockConnected=" + state.getDockState().isDexStation() + ", isDexPadConnected=" + state.getDockState().isDexPad() + ", mDockState=" + state.getDockState().getType() + ", mPrevDockState=" + state.getPreviousDockState().getType() + ", mDockChargerPower=" + this.mDockChargerPower + ", mDockChargerSupport=" + this.mDockChargerSupport);
        }
        if (state.getDockState().getType() == 10001) {
            requestCreateDockControlLib(40992);
            return;
        }
        if (state.getDockState().isDexPad()) {
            requestCreateDockControlLib(41001);
            return;
        }
        if (state.getDockState().isUndocked() && state.getPreviousDockState().isDexStation()) {
            requestDestroyDockControlLib(40992);
        } else if (state.getDockState().isUndocked() && state.getPreviousDockState().isDexPad()) {
            requestDestroyDockControlLib(41001);
        }
    }

    public final void updateDockVersionToSettings(boolean z) {
        if (z) {
            this.mDockVersion = Utils.readFile("/sys/class/dp_sec/dex_ver", "") + "." + Utils.readFile("/sys/class/sec/ccic/acc_device_version", "");
            if (this.mDockVersionExtra != null) {
                this.mDockVersion += "." + this.mDockVersionExtra;
            }
            this.mLastDockVersion = this.mDockVersion;
        } else {
            this.mDockVersion = null;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "updateDockVersionToSettings(), mDockVersion = " + this.mDockVersion);
        }
        DesktopModeSettings.setSettings(this.mResolver, "dock_version", this.mDockVersion);
    }

    public final void createDockControlLib(int i) {
        if (i == 40992 || i == 41001) {
            if (this.mControlResponseListener == null) {
                this.mControlResponseListener = new ISDCDeviceController.ControlResponseListener() { // from class: com.android.server.desktopmode.DockManager.3
                    @Override // com.samsung.android.lib.dexcontrol.ISDCDeviceController.ControlResponseListener
                    public void onChargingModeUpdated(boolean z) {
                    }

                    @Override // com.samsung.android.lib.dexcontrol.ISDCDeviceController.ControlResponseListener
                    public void onConnectedPowerChargerInfoUpdated(int i2, int i3, int i4) {
                        DockManager.this.mHandler.removeMessages(211);
                        DockManager.this.mHandler.obtainMessage(211, i3, i4).sendToTarget();
                    }

                    @Override // com.samsung.android.lib.dexcontrol.ISDCDeviceController.ControlResponseListener
                    public void onDSVersionUpdated(String str) {
                        DockManager.this.mHandler.removeMessages(231);
                        DockManager.this.mHandler.obtainMessage(231, str).sendToTarget();
                    }

                    @Override // com.samsung.android.lib.dexcontrol.ISDCDeviceController.ControlResponseListener
                    public void onError(int i2) {
                        DockManager.this.mHandler.removeMessages(221);
                        DockManager.this.mHandler.obtainMessage(221, Integer.valueOf(i2)).sendToTarget();
                    }
                };
            }
            if (this.mISDCDeviceController == null) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(TAG, "createDockControlLib dockType=" + i);
                }
                this.mLastDockControlLibMsgState.setLast(100, 201, null);
                this.mISDCDeviceController = new SDCDeviceController(this.mContext, i, this.mControlResponseListener);
                if (i == 41001) {
                    requestConnectedPowerChargerInfoUpdate();
                    return;
                }
                return;
            }
            return;
        }
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "createDockControlLib is not supported");
        }
    }

    public final void destroyDockControlLib(int i) {
        if (this.mControlResponseListener != null) {
            this.mControlResponseListener = null;
        }
        if (this.mISDCDeviceController != null) {
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(TAG, "destroyDockControlLib");
            }
            this.mLastDockControlLibMsgState.setLast(100, 203, null);
            this.mISDCDeviceController.destroy();
            this.mISDCDeviceController = null;
            if (i == 41001) {
                this.mStateManager.setDockLowChargerState(-1);
            }
        }
    }

    public final void requestCreateDockControlLib(int i) {
        this.mHandler.removeMessages(201);
        DockHandler dockHandler = this.mHandler;
        dockHandler.sendMessage(dockHandler.obtainMessage(201, Integer.valueOf(i)));
    }

    public final void requestDestroyDockControlLib(int i) {
        this.mHandler.removeMessages(203);
        DockHandler dockHandler = this.mHandler;
        dockHandler.sendMessage(dockHandler.obtainMessage(203, Integer.valueOf(i)));
    }

    public final void requestConnectedPowerChargerInfoUpdate() {
        this.mHandler.removeMessages(202);
        this.mHandler.obtainMessage(202).sendToTarget();
    }

    public static String dockControlErrorToString(int i) {
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
                return "Unknown=" + i;
        }
    }

    public static boolean isDexPadConnected(State state) {
        return state.getDockState().isDexPad();
    }

    public final void initialize() {
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "DockManager initialize()");
        }
        this.mDockVersion = null;
        this.mDockVersionExtra = null;
        this.mDockChargerPower = 0;
        this.mDockChargerSupport = 0;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current " + DockManager.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mDockVersion=" + this.mDockVersion);
        indentingPrintWriter.println("mDockVersionExtra=" + this.mDockVersionExtra);
        indentingPrintWriter.println("mDockChargerPower=" + this.mDockChargerPower);
        indentingPrintWriter.println("mDockChargerSupport=" + this.mDockChargerSupport);
        indentingPrintWriter.println("Last " + DockManager.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mLastDockVersion=" + this.mLastDockVersion);
        for (int i = 0; i < this.mLastDockControlLibMsgState.mMsgState.size(); i++) {
            indentingPrintWriter.println("mLastDockControlLibMsgState=" + this.mLastDockControlLibMsgState.toString(i));
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final class DockHandler extends Handler {
        public DockHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            State state = DockManager.this.mStateManager.getState();
            int i = message.what;
            if (i == 150) {
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(DockManager.TAG, "MSG_DOCK_REQUEST_UPDATE_DOCK_LIB_STATUS");
                }
                DockManager.this.updateDockLibStatus(state);
                return;
            }
            int i2 = 0;
            if (i == 211) {
                int i3 = message.arg1;
                int i4 = message.arg2;
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(DockManager.TAG, "MSG_DOCK_RESPONSE_POWER_INFO onConnectedPowerChargerInfoUpdated, power=" + i3 + " previous power=" + DockManager.this.mDockChargerPower + " support=" + i4);
                }
                DockManager.this.mLastDockControlLibMsgState.setLast(101, message.what, i3 + "," + i4);
                if (i3 == 0 || i3 != DockManager.this.mDockChargerPower) {
                    if (i3 < 15) {
                        if (DockManager.this.mDockChargerPower == 0 || DockManager.this.mDockChargerPower >= 15) {
                            if (DesktopModeFeature.DEBUG) {
                                Log.m7d(DockManager.TAG, "updateDockLowChargerPower true");
                            }
                            DockManager.this.mStateManager.setDockLowChargerState(1);
                            i2 = 1;
                        }
                    } else if (DockManager.this.mDockChargerPower < 15) {
                        if (DesktopModeFeature.DEBUG) {
                            Log.m7d(DockManager.TAG, "updateDockLowChargerPower false");
                        }
                        DockManager.this.mStateManager.setDockLowChargerState(2);
                        i2 = 1;
                    }
                }
                if (i2 != 0) {
                    DockManager.this.mDockChargerPower = i3;
                    DockManager.this.mDockChargerSupport = i4;
                    DockManager.this.requestUpdateDockLibStatus("dockLowChargerPowerUpdated");
                    return;
                }
                return;
            }
            if (i == 221) {
                int intValue = ((Integer) message.obj).intValue();
                if (DesktopModeFeature.DEBUG) {
                    Log.m7d(DockManager.TAG, "MSG_DOCK_ERROR onError, error=" + DockManager.dockControlErrorToString(intValue));
                }
                DockManager.this.mLastDockControlLibMsgState.setLast(102, message.what, DockManager.dockControlErrorToString(intValue));
                DockManager.this.mDockControlLibError.put(intValue, true);
                StringBuilder sb = new StringBuilder();
                while (i2 < DockManager.this.mDockControlLibError.size()) {
                    sb.append(DockManager.dockControlErrorToString(DockManager.this.mDockControlLibError.keyAt(i2)));
                    sb.append(" = ");
                    sb.append(DockManager.this.mDockControlLibError.valueAt(i2));
                    sb.append(",");
                    i2++;
                }
                DockManager.this.mLastDockControlLibMsgState.setLast(104, FrameworkStatsLog.f434xe7bba16c, sb.toString());
                return;
            }
            if (i != 231) {
                switch (i) {
                    case 201:
                        int intValue2 = ((Integer) message.obj).intValue();
                        if (DesktopModeFeature.DEBUG) {
                            Log.m7d(DockManager.TAG, "MSG_DOCK_REQUEST_CREATE dockType=" + intValue2);
                        }
                        DockManager.this.createDockControlLib(intValue2);
                        break;
                    case 202:
                        if (DesktopModeFeature.DEBUG) {
                            Log.m7d(DockManager.TAG, "MSG_DOCK_REQUEST_POWER_INFO requestConnectedPowerChargerInfoUpdate");
                        }
                        if (DockManager.this.mISDCDeviceController != null) {
                            try {
                                DockManager.this.mLastDockControlLibMsgState.setLast(100, message.what, null);
                                DockManager.this.mISDCDeviceController.requestConnectedPowerChargerInfoUpdate();
                                break;
                            } catch (NotSupportDexFeatureException e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        break;
                    case 203:
                        int intValue3 = ((Integer) message.obj).intValue();
                        if (DesktopModeFeature.DEBUG) {
                            Log.m7d(DockManager.TAG, "MSG_DOCK_REQUEST_DESTROY dockType=" + intValue3);
                        }
                        DockManager.this.destroyDockControlLib(intValue3);
                        break;
                }
                return;
            }
            String str = (String) message.obj;
            if (DesktopModeFeature.DEBUG) {
                Log.m7d(DockManager.TAG, "MSG_DOCK_VERSION_DSVERSION_UPDATED onDSVersionUpdated, version=" + str);
            }
            DockManager.this.mLastDockControlLibMsgState.setLast(103, message.what, str);
            if (str != null) {
                DockManager.this.mDockVersionExtra = str;
            } else {
                DockManager.this.mDockVersionExtra = "####";
            }
            DockManager.this.updateDockVersionToSettings(true);
        }
    }

    public class DockMsgState {
        public ArrayMap mMsgState;

        public DockMsgState(int i, int i2, int i3, int i4, int i5) {
            ArrayMap arrayMap = new ArrayMap();
            this.mMsgState = arrayMap;
            if (i != 0) {
                arrayMap.put(100, msgStateToString(i));
            }
            if (i2 != 0) {
                this.mMsgState.put(101, msgStateToString(i2));
            }
            if (i3 != 0) {
                this.mMsgState.put(102, msgStateToString(i3));
            }
            if (i4 != 0) {
                this.mMsgState.put(103, msgStateToString(i4));
            }
            if (i5 != 0) {
                this.mMsgState.put(104, msgStateToString(i5));
            }
        }

        public void setLast(int i, int i2, String str) {
            this.mMsgState.put(Integer.valueOf(i), msgStateToString(i2) + "," + str);
        }

        public String toString(int i) {
            return msgTypeToString(((Integer) this.mMsgState.keyAt(i)).intValue()) + ", " + ((String) this.mMsgState.valueAt(i));
        }

        public final String msgTypeToString(int i) {
            switch (i) {
                case 100:
                    return "MSG_TYPE_REQUEST";
                case 101:
                    return "MSG_TYPE_RESPONSE";
                case 102:
                    return "MSG_TYPE_ERROR";
                case 103:
                    return "MSG_TYPE_VERSION";
                case 104:
                    return "MSG_TYPE_EXTRA";
                default:
                    return "Unknown=" + i;
            }
        }

        public final String msgStateToString(int i) {
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
                    return "Unknown=" + i;
            }
        }
    }
}
