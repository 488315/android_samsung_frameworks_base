package com.samsung.accessory.manager.connectivity;

import android.util.Log;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.am.mars.MARsFreezeStateRecord$$ExternalSyntheticOutline0;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.accessory.manager.authentication.msg.MsgHelper;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbConnectivity extends Connectivity {
    public MsgHelper mMsgHelper;

    public final String byteArrayToString(byte[] bArr) {
        if (bArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < bArr.length) {
            i = MARsFreezeStateRecord$$ExternalSyntheticOutline0.m("%02x", new Object[]{Byte.valueOf(bArr[i])}, sb, i, 1);
        }
        return sb.toString();
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void close() {
    }

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
    public final void disconnect() {
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void dump(PrintWriter printWriter) {
    }

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
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final byte[] sendStartAuth(AuthenticationResult authenticationResult) {
        int i = 0;
        byte[] bArr = {-1};
        MsgHelper msgHelper = this.mMsgHelper;
        int ccic_open = msgHelper.ccic_open();
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(ccic_open, "open ccic_misc ret = ", "SAccessoryManager_UsbConnectivity");
        if (ccic_open < 0) {
            Log.e("SAccessoryManager_UsbConnectivity", "open fail");
            return bArr;
        }
        int ioctl_longDataWrite = msgHelper.ioctl_longDataWrite(new byte[]{0, 90, 0, 0, 0});
        if (ioctl_longDataWrite != 5) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(ioctl_longDataWrite, "ReqS fail. invalid return value: ", "SAccessoryManager_UsbConnectivity");
            return bArr;
        }
        byte[] ioctl_longDataRead = msgHelper.ioctl_longDataRead();
        Log.d("SAccessoryManager_UsbConnectivity", "atqs: " + byteArrayToString(ioctl_longDataRead));
        byte[] bArr2 = new byte[16];
        if (ioctl_longDataRead.length == 15) {
            bArr2[0] = 0;
            while (i < 15) {
                int i2 = i + 1;
                bArr2[i2] = ioctl_longDataRead[i];
                i = i2;
            }
        } else {
            bArr2[0] = -2;
        }
        return bArr2;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean sendStopAuth() {
        Log.d("SAccessoryManager_UsbConnectivity", "close ccic_misc");
        this.mMsgHelper.ccic_close();
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void sendStopUsbAuth() {
        boolean z = false;
        for (int i = 5; i >= 0 && !z; i--) {
            MsgHelper msgHelper = this.mMsgHelper;
            int ccic_open = msgHelper.ccic_open();
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(ccic_open, "open ccic_misc ret = ", "SAccessoryManager_UsbConnectivity");
            if (ccic_open < 0) {
                Log.e("SAccessoryManager_UsbConnectivity", "open fail for end comd");
            } else {
                int ioctl_longDataWrite = msgHelper.ioctl_longDataWrite(new byte[]{0, -34, -83, 0, 0});
                if (ioctl_longDataWrite != 5) {
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(ioctl_longDataWrite, "End cmd fail: ", "SAccessoryManager_UsbConnectivity");
                } else {
                    Log.d("SAccessoryManager_UsbConnectivity", "close ccic_misc");
                    msgHelper.ccic_close();
                    z = true;
                }
            }
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        try {
            Thread.sleep(15L);
        } catch (InterruptedException unused) {
        }
        MsgHelper msgHelper = this.mMsgHelper;
        byte[] bArr2 = {-2};
        byte[] bArr3 = {-1};
        if (msgHelper.ioctl_longDataWrite(bArr) != bArr.length) {
            Log.e("SAccessoryManager_UsbConnectivity", "command write fail");
            return bArr3;
        }
        if (authenticationResult.apiState == 11) {
            return bArr2;
        }
        byte[] ioctl_longDataRead = msgHelper.ioctl_longDataRead();
        Log.d("SAccessoryManager_UsbConnectivity", "api: " + authenticationResult.apiState + ", receive: " + byteArrayToString(ioctl_longDataRead));
        int length = ioctl_longDataRead.length;
        byte[] bArr4 = new byte[length + 1];
        for (int i = 0; i < ioctl_longDataRead.length; i++) {
            bArr4[i] = ioctl_longDataRead[i];
        }
        bArr4[length] = 0;
        return bArr4;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void setStateChangedCallback(AuthenticationSession.AnonymousClass1 anonymousClass1) {
        this.mStateChangedCallback = anonymousClass1;
    }
}
