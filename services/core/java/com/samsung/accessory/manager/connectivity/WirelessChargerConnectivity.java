package com.samsung.accessory.manager.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.msg.MsgHelper;
import com.samsung.accessory.manager.connectivity.Connectivity;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class WirelessChargerConnectivity extends Connectivity {
    public static final String TAG = "SAccessoryManager_" + WirelessChargerConnectivity.class.getSimpleName();
    public Connectivity.StateChangedHandler mAdapterStateChangedHandler;
    public MsgHelper mMsgHelper;
    public final BroadcastReceiver mReadTriggerReceiver;
    public int msgState;
    public IntentFilter readTriggerIntentFilter;

    public class AdapterStateChangedHandler implements Connectivity.StateChangedHandler {
        public AdapterStateChangedHandler() {
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void close() {
    }

    public final String convertSessionState(int i) {
        switch (i) {
            case 1:
                return "SESSION_STATE_READY";
            case 2:
                return "SESSION_STATE_STARTING";
            case 3:
                return "SESSION_STATE_STARTED";
            case 4:
                return "SESSION_STATE_STOPPING";
            case 5:
                return "SESSION_STATE_STOPPED";
            case 6:
                return "SESSION_STATE_ERROR";
            case 7:
                return "SESSION_NONE";
            default:
                return null;
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean disable() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean disconnect() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean enable() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean isEnabled() {
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean isEnabledInternally() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public byte[] sendStartAuth(AuthenticationResult authenticationResult) {
        return null;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void sendStopUsbAuth() {
    }

    public WirelessChargerConnectivity(Context context) {
        super(context);
        this.msgState = -1;
        this.readTriggerIntentFilter = new IntentFilter("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT");
        this.mReadTriggerReceiver = new BroadcastReceiver() { // from class: com.samsung.accessory.manager.connectivity.WirelessChargerConnectivity.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT".equals(intent.getAction())) {
                    Log.i(WirelessChargerConnectivity.TAG, "onReceive: " + intent.getAction() + ", misc_event: " + intent.getIntExtra("misc_event", 0) + ", msg state: " + WirelessChargerConnectivity.this.msgState);
                    if ((intent.getIntExtra("misc_event", 0) & 1024) == 1024 && WirelessChargerConnectivity.this.msgState == 1) {
                        Log.d(WirelessChargerConnectivity.TAG, "read bit set");
                        WirelessChargerConnectivity.this.msgState = 2;
                    } else if ((intent.getIntExtra("misc_event", 0) & 1024) != 1024) {
                        Log.d(WirelessChargerConnectivity.TAG, "read bit clear");
                        WirelessChargerConnectivity.this.msgState = 1;
                    }
                }
            }
        };
        this.mMsgHelper = new MsgHelper();
        this.mAdapterStateChangedHandler = new AdapterStateChangedHandler();
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void setStateChangedCallback(Connectivity.StateChangedCallback stateChangedCallback) {
        super.setStateChangedCallback(stateChangedCallback);
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean sendStopAuth() {
        String str = TAG;
        Log.d(str, "close batt_misc");
        this.mMsgHelper.ccic_close();
        Log.d(str, "unregister receiver");
        this.mContext.unregisterReceiver(this.mReadTriggerReceiver);
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean openNode(AuthenticationResult authenticationResult) {
        int wirelesscharger_open = this.mMsgHelper.wirelesscharger_open();
        String str = TAG;
        Log.d(str, "register receiver");
        this.mContext.registerReceiver(this.mReadTriggerReceiver, this.readTriggerIntentFilter);
        Log.d(str, "open batt_misc ret = " + wirelesscharger_open);
        if (wirelesscharger_open >= 0) {
            return true;
        }
        Log.e(str, "open fail");
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        try {
            Thread.sleep(15L);
        } catch (InterruptedException unused) {
        }
        String str = TAG;
        Log.i(str, "data write msgState: " + this.msgState);
        byte[] bArr2 = {-2};
        byte[] bArr3 = {-1};
        if (this.mMsgHelper.ioctl_longDataWrite_batt(bArr) != bArr.length) {
            Log.e(str, "command write fail");
            return bArr3;
        }
        Log.i(str, "getSessionState: " + convertSessionState(getSessionState()));
        do {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused2) {
            }
            if (this.msgState == 1) {
                break;
            }
        } while (getSessionState() == 3);
        Log.i(TAG, "getSessionState: " + convertSessionState(getSessionState()));
        if (getSessionState() == 5) {
            return bArr2;
        }
        try {
            Thread.sleep(50L);
        } catch (InterruptedException unused3) {
        }
        Log.i(TAG, "bit clear, msgState: " + this.msgState);
        do {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused4) {
            }
            if (this.msgState != 1) {
                break;
            }
        } while (getSessionState() == 3);
        String str2 = TAG;
        Log.i(str2, "getSessionState: " + convertSessionState(getSessionState()));
        if (getSessionState() == 5) {
            return bArr2;
        }
        byte[] ioctl_longDataRead_batt = this.mMsgHelper.ioctl_longDataRead_batt();
        Log.d(str2, "api: " + authenticationResult.getApiState() + ", receive: " + byteArrayToString(ioctl_longDataRead_batt));
        return addOneByte(ioctl_longDataRead_batt, ioctl_longDataRead_batt.length);
    }

    public final byte[] addOneByte(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i + 1];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr2[i2] = bArr[i2];
        }
        bArr2[i] = 0;
        return bArr2;
    }

    public String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b)));
        }
        return sb.toString();
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean connect(String str) {
        Connectivity.StateChangedCallback stateChangedCallback = this.mStateChangedCallback;
        if (stateChangedCallback != null) {
            stateChangedCallback.onConnectionStateChanged(1);
        }
        return true;
    }
}
