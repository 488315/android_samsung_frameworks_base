package com.samsung.accessory.manager.connectivity;

import android.os.RemoteException;
import android.util.Log;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;
import com.samsung.android.nfc.adapter.ISamsungNfcAdapter;
import com.samsung.android.nfc.adapter.SamsungNfcAdapter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NfcConnectivity extends Connectivity {
    public AtomicBoolean mEnableRequest;

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
        this.mEnableRequest.set(false);
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void dump(PrintWriter printWriter) {
        BatteryService$$ExternalSyntheticOutline0.m(printWriter, " Current NfcConnectivity state:", "  mState = -1", "  mPrevState = -1");
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean enable() {
        return false;
    }

    public final SamsungNfcAdapter getSamsungNfcAdapter() {
        SamsungNfcAdapter samsungNfcAdapter = null;
        try {
            samsungNfcAdapter = SamsungNfcAdapter.getDefaultAdapter(this.mContext);
            if (samsungNfcAdapter == null) {
                Log.e("SAccessoryManager_NfcConnectivity", "SamsungNfcAdapter.getDefaultAdapter returns null");
                samsungNfcAdapter = SamsungNfcAdapter.getDefaultAdapter(this.mContext);
                if (samsungNfcAdapter == null) {
                    Log.e("SAccessoryManager_NfcConnectivity", "retry, SamsungNfcAdapter.getDefaultAdapter returns null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return samsungNfcAdapter;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean isEnabled() {
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean openNode() {
        return true;
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final byte[] sendStartAuth(AuthenticationResult authenticationResult) {
        try {
            authenticationResult.apiState = 1;
            if (getSamsungNfcAdapter() != null) {
                return SamsungNfcAdapter.startCoverAuth();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final boolean sendStopAuth() {
        try {
            if (getSamsungNfcAdapter() != null) {
                return SamsungNfcAdapter.stopCoverAuth();
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final void sendStopUsbAuth() {
    }

    @Override // com.samsung.accessory.manager.connectivity.Connectivity
    public final byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        try {
            authenticationResult.apiState = 2;
            if (getSamsungNfcAdapter() == null) {
                return null;
            }
            try {
                return ((ISamsungNfcAdapter.Stub.Proxy) SamsungNfcAdapter.sService).transceiveAuthData(bArr);
            } catch (RemoteException e) {
                Log.e("SamsungNfcAdapter", "Failed to transmit authentication data");
                SamsungNfcAdapter.attemptDeadServiceRecovery(e);
                throw new IOException("Failed to transmit authentication data");
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
