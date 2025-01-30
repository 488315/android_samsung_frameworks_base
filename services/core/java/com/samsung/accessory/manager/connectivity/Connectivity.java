package com.samsung.accessory.manager.connectivity;

import android.content.Context;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public abstract class Connectivity {
    public Context mContext;
    public boolean mEnabledInternal = false;
    public int mSessionState = -1;
    public StateChangedCallback mStateChangedCallback;
    public static final String TAG = "SAccessoryManager_" + Connectivity.class.getSimpleName();
    public static int NFC_MAX_CONNECTION = 1;
    public static int BT_MAX_CONNECTION = 8;
    public static int USB_MAX_CONNECTION = 1;
    public static int WIRELESSCHARGER_MAX_CONNECTION = 1;
    public static final byte[] NOT_SUPPORT = {0, 0, 0};

    public interface StateChangedCallback {
        void onConnectionStateChanged(int i);

        void onStateChanged(int i);
    }

    public interface StateChangedHandler {
    }

    public abstract void close();

    public abstract boolean connect(String str);

    public abstract boolean disable();

    public abstract boolean disconnect();

    public abstract void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract boolean enable();

    public abstract boolean isEnabled();

    public abstract boolean openNode(AuthenticationResult authenticationResult);

    public abstract byte[] sendStartAuth(AuthenticationResult authenticationResult);

    public abstract boolean sendStopAuth();

    public abstract void sendStopUsbAuth();

    public abstract byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult);

    public void setStateChangedCallback(StateChangedCallback stateChangedCallback) {
        this.mStateChangedCallback = stateChangedCallback;
    }

    public void setSessionState(int i) {
        this.mSessionState = i;
    }

    public int getSessionState() {
        return this.mSessionState;
    }

    public static int getMaxConnection(int i) {
        if (i == 1) {
            return NFC_MAX_CONNECTION;
        }
        if (i == 2) {
            return BT_MAX_CONNECTION;
        }
        if (i == 3) {
            return USB_MAX_CONNECTION;
        }
        if (i == 4) {
            return WIRELESSCHARGER_MAX_CONNECTION;
        }
        return -1;
    }

    public Connectivity(Context context) {
        this.mContext = context;
    }

    public boolean isEnabledInternally() {
        return this.mEnabledInternal;
    }
}
