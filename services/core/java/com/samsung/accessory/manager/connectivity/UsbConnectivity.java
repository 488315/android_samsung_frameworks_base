package com.samsung.accessory.manager.connectivity;

import android.content.Context;
import android.util.Log;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.msg.MsgHelper;
import com.samsung.accessory.manager.connectivity.Connectivity;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class UsbConnectivity extends Connectivity {
    public static final String TAG = "SAccessoryManager_" + UsbConnectivity.class.getSimpleName();
    public Connectivity.StateChangedHandler mAdapterStateChangedHandler;
    public MsgHelper mMsgHelper;

    public class AdapterStateChangedHandler implements Connectivity.StateChangedHandler {
        public AdapterStateChangedHandler() {
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void close() {
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
    public boolean openNode(AuthenticationResult authenticationResult) {
        return true;
    }

    public UsbConnectivity(Context context) {
        super(context);
        this.mMsgHelper = new MsgHelper();
        this.mAdapterStateChangedHandler = new AdapterStateChangedHandler();
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void setStateChangedCallback(Connectivity.StateChangedCallback stateChangedCallback) {
        super.setStateChangedCallback(stateChangedCallback);
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean sendStopAuth() {
        Log.d(TAG, "close ccic_misc");
        this.mMsgHelper.ccic_close();
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public byte[] sendStartAuth(AuthenticationResult authenticationResult) {
        byte[] bArr = {-1};
        int ccic_open = this.mMsgHelper.ccic_open();
        String str = TAG;
        Log.d(str, "open ccic_misc ret = " + ccic_open);
        if (ccic_open < 0) {
            Log.e(str, "open fail");
            return bArr;
        }
        int ioctl_longDataWrite = this.mMsgHelper.ioctl_longDataWrite(new byte[]{0, 90, 0, 0, 0});
        if (ioctl_longDataWrite != 5) {
            Log.e(str, "ReqS fail. invalid return value: " + ioctl_longDataWrite);
            return bArr;
        }
        byte[] ioctl_longDataRead = this.mMsgHelper.ioctl_longDataRead();
        Log.d(str, "atqs: " + byteArrayToString(ioctl_longDataRead));
        return includeHeader(ioctl_longDataRead);
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void sendStopUsbAuth() {
        boolean z = false;
        for (int i = 5; i >= 0 && !z; i--) {
            int ccic_open = this.mMsgHelper.ccic_open();
            String str = TAG;
            Log.d(str, "open ccic_misc ret = " + ccic_open);
            if (ccic_open < 0) {
                Log.e(str, "open fail for end comd");
            } else {
                int ioctl_longDataWrite = this.mMsgHelper.ioctl_longDataWrite(new byte[]{0, -34, -83, 0, 0});
                if (ioctl_longDataWrite != 5) {
                    Log.e(str, "End cmd fail: " + ioctl_longDataWrite);
                } else {
                    Log.d(str, "close ccic_misc");
                    this.mMsgHelper.ccic_close();
                    z = true;
                }
            }
        }
    }

    public final byte[] includeHeader(byte[] bArr) {
        byte[] bArr2 = new byte[16];
        int i = 0;
        if (bArr.length == 15) {
            bArr2[0] = 0;
            while (i < 15) {
                int i2 = i + 1;
                bArr2[i2] = bArr[i];
                i = i2;
            }
        } else {
            bArr2[0] = -2;
        }
        return bArr2;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        try {
            Thread.sleep(15L);
        } catch (InterruptedException unused) {
        }
        byte[] bArr2 = {-2};
        byte[] bArr3 = {-1};
        if (this.mMsgHelper.ioctl_longDataWrite(bArr) != bArr.length) {
            Log.e(TAG, "command write fail");
            return bArr3;
        }
        if (authenticationResult.getApiState() == 11) {
            return bArr2;
        }
        byte[] ioctl_longDataRead = this.mMsgHelper.ioctl_longDataRead();
        Log.d(TAG, "api: " + authenticationResult.getApiState() + ", receive: " + byteArrayToString(ioctl_longDataRead));
        return addOneByte(ioctl_longDataRead, ioctl_longDataRead.length);
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
