package com.samsung.accessory.manager.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;

import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.msg.MsgHelper;

import java.io.PrintWriter;

public final class WirelessChargerConnectivity extends Connectivity {
    public final MsgHelper mMsgHelper;
    public final AnonymousClass1 mReadTriggerReceiver;
    public int msgState;
    public final IntentFilter readTriggerIntentFilter;

    public WirelessChargerConnectivity(Context context) {
        super(context);
        this.msgState = -1;
        this.readTriggerIntentFilter =
                new IntentFilter("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT");
        this.mReadTriggerReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.accessory.manager.connectivity.WirelessChargerConnectivity.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("com.samsung.server.BatteryService.action.SEC_BATTERY_EVENT"
                                .equals(intent.getAction())) {
                            StringBuilder sb = new StringBuilder("onReceive: ");
                            sb.append(intent.getAction());
                            sb.append(", misc_event: ");
                            sb.append(intent.getIntExtra("misc_event", 0));
                            sb.append(", msg state: ");
                            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                                    sb,
                                    WirelessChargerConnectivity.this.msgState,
                                    "SAccessoryManager_WirelessChargerConnectivity");
                            if ((intent.getIntExtra("misc_event", 0) & 1024) == 1024
                                    && WirelessChargerConnectivity.this.msgState == 1) {
                                Log.d(
                                        "SAccessoryManager_WirelessChargerConnectivity",
                                        "read bit set");
                                WirelessChargerConnectivity.this.msgState = 2;
                            } else if ((intent.getIntExtra("misc_event", 0) & 1024) != 1024) {
                                Log.d(
                                        "SAccessoryManager_WirelessChargerConnectivity",
                                        "read bit clear");
                                WirelessChargerConnectivity.this.msgState = 1;
                            }
                        }
                    }
                };
        this.mMsgHelper = new MsgHelper();
    }

    public static String convertSessionState(int i) {
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
    public final void close() {}

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void connect() {
        AuthenticationSession.AnonymousClass1 anonymousClass1 = this.mStateChangedCallback;
        if (anonymousClass1 != null) {
            anonymousClass1.onConnectionStateChanged();
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean disable() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void disconnect() {}

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void dump(PrintWriter printWriter) {}

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean enable() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean isEnabled() {
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean isEnabledInternally() {
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean openNode() {
        int wirelesscharger_open = this.mMsgHelper.wirelesscharger_open();
        Log.d("SAccessoryManager_WirelessChargerConnectivity", "register receiver");
        this.mContext.registerReceiver(this.mReadTriggerReceiver, this.readTriggerIntentFilter);
        Log.d(
                "SAccessoryManager_WirelessChargerConnectivity",
                "open batt_misc ret = " + wirelesscharger_open);
        if (wirelesscharger_open >= 0) {
            return true;
        }
        Log.e("SAccessoryManager_WirelessChargerConnectivity", "open fail");
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final byte[] sendStartAuth(AuthenticationResult authenticationResult) {
        return null;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean sendStopAuth() {
        Log.d("SAccessoryManager_WirelessChargerConnectivity", "close batt_misc");
        this.mMsgHelper.ccic_close();
        Log.d("SAccessoryManager_WirelessChargerConnectivity", "unregister receiver");
        this.mContext.unregisterReceiver(this.mReadTriggerReceiver);
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void sendStopUsbAuth() {}

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        String str;
        try {
            Thread.sleep(15L);
        } catch (InterruptedException unused) {
        }
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                new StringBuilder("data write msgState: "),
                this.msgState,
                "SAccessoryManager_WirelessChargerConnectivity");
        MsgHelper msgHelper = this.mMsgHelper;
        byte[] bArr2 = {-2};
        byte[] bArr3 = {-1};
        if (msgHelper.ioctl_longDataWrite_batt(bArr) != bArr.length) {
            Log.e("SAccessoryManager_WirelessChargerConnectivity", "command write fail");
            return bArr3;
        }
        Log.i(
                "SAccessoryManager_WirelessChargerConnectivity",
                "getSessionState: " + convertSessionState(this.mSessionState));
        do {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused2) {
            }
            if (this.msgState == 1) {
                break;
            }
        } while (this.mSessionState == 3);
        Log.i(
                "SAccessoryManager_WirelessChargerConnectivity",
                "getSessionState: " + convertSessionState(this.mSessionState));
        if (this.mSessionState == 5) {
            return bArr2;
        }
        try {
            Thread.sleep(50L);
        } catch (InterruptedException unused3) {
        }
        UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                new StringBuilder("bit clear, msgState: "),
                this.msgState,
                "SAccessoryManager_WirelessChargerConnectivity");
        do {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused4) {
            }
            if (this.msgState != 1) {
                break;
            }
        } while (this.mSessionState == 3);
        Log.i(
                "SAccessoryManager_WirelessChargerConnectivity",
                "getSessionState: " + convertSessionState(this.mSessionState));
        if (this.mSessionState == 5) {
            return bArr2;
        }
        byte[] ioctl_longDataRead_batt = msgHelper.ioctl_longDataRead_batt();
        StringBuilder sb = new StringBuilder("api: ");
        sb.append(authenticationResult.apiState);
        sb.append(", receive: ");
        if (ioctl_longDataRead_batt != null) {
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0;
                    i < ioctl_longDataRead_batt.length;
                    i =
                            MARsFreezeStateRecord$$ExternalSyntheticOutline0.m(
                                    "%02x",
                                    new Object[] {Byte.valueOf(ioctl_longDataRead_batt[i])},
                                    sb2,
                                    i,
                                    1)) {}
            str = sb2.toString();
        } else {
            str = "null";
        }
        VpnManagerService$$ExternalSyntheticOutline0.m(
                sb, str, "SAccessoryManager_WirelessChargerConnectivity");
        int length = ioctl_longDataRead_batt.length;
        byte[] bArr4 = new byte[length + 1];
        for (int i2 = 0; i2 < ioctl_longDataRead_batt.length; i2++) {
            bArr4[i2] = ioctl_longDataRead_batt[i2];
        }
        bArr4[length] = 0;
        return bArr4;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void setStateChangedCallback(
            AuthenticationSession.AnonymousClass1 anonymousClass1) {
        this.mStateChangedCallback = anonymousClass1;
    }
}
