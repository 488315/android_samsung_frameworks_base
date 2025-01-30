package com.samsung.accessory.manager.connectivity;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.util.Log;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.connectivity.Connectivity;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class NfcConnectivity extends Connectivity {
    public static final String TAG = "SAccessoryManager_" + NfcConnectivity.class.getSimpleName();
    public AtomicBoolean mEnableRequest;
    public int mPrevState;
    public int mState;

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean isEnabled() {
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean openNode(AuthenticationResult authenticationResult) {
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void sendStopUsbAuth() {
    }

    public NfcConnectivity(Context context) {
        super(context);
        this.mState = -1;
        this.mPrevState = -1;
        this.mEnableRequest = new AtomicBoolean(false);
    }

    public final NfcAdapter getNfcAdapter() {
        NfcAdapter nfcAdapter = null;
        try {
            nfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
            if (nfcAdapter == null) {
                String str = TAG;
                Log.e(str, "NfcAdapter.getDefaultAdapter returns null");
                nfcAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
                if (nfcAdapter == null) {
                    Log.e(str, "retry, NfcAdapter.getDefaultAdapter returns null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nfcAdapter;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean enable() {
        Log.d(TAG, "enable");
        if (!enableInternal(true)) {
            return false;
        }
        this.mEnableRequest.set(true);
        this.mEnabledInternal = true;
        return true;
    }

    public final boolean enableInternal(boolean z) {
        boolean semEnableReaderMode;
        int nfcState = getNfcState();
        if (nfcState == 5) {
            NfcAdapter nfcAdapter = getNfcAdapter();
            semEnableReaderMode = nfcAdapter != null ? nfcAdapter.semEnableReaderMode() : true;
        } else {
            Log.e(TAG, "enableInternal : can't enable currentState = " + nfcState);
            semEnableReaderMode = false;
        }
        if (semEnableReaderMode && z) {
            this.mPrevState = nfcState;
        }
        return semEnableReaderMode;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean disable() {
        Log.d(TAG, "disable");
        this.mEnableRequest.set(false);
        return disableInternal();
    }

    public final boolean disableInternal() {
        int nfcState = getNfcState();
        if (this.mPrevState == nfcState) {
            return false;
        }
        Log.e(TAG, "disableInternal : can't disable currentState = " + nfcState);
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean connect(String str) {
        Connectivity.StateChangedCallback stateChangedCallback = this.mStateChangedCallback;
        if (stateChangedCallback != null) {
            stateChangedCallback.onConnectionStateChanged(1);
        }
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean disconnect() {
        this.mEnableRequest.set(false);
        return false;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public byte[] sendStartAuth(AuthenticationResult authenticationResult) {
        try {
            authenticationResult.setApiState(1);
            NfcAdapter nfcAdapter = getNfcAdapter();
            if (nfcAdapter != null) {
                return nfcAdapter.startCoverAuth();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public boolean sendStopAuth() {
        try {
            NfcAdapter nfcAdapter = getNfcAdapter();
            if (nfcAdapter != null) {
                return nfcAdapter.stopCoverAuth();
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        try {
            authenticationResult.setApiState(2);
            NfcAdapter nfcAdapter = getNfcAdapter();
            if (nfcAdapter != null) {
                return nfcAdapter.transceiveAuthData(bArr);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void close() {
        Log.d(TAG, "close()");
    }

    public final int getNfcState() {
        NfcAdapter nfcAdapter;
        for (int i = 0; i < 10; i++) {
            try {
                nfcAdapter = this.getNfcAdapter();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(200L);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
            if (nfcAdapter != null) {
                return nfcAdapter.semGetAdapterState();
            }
            continue;
        }
        return -1;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(" Current NfcConnectivity state:");
        printWriter.println("  mState = " + this.mState);
        printWriter.println("  mPrevState = " + this.mPrevState);
    }
}
