package com.samsung.accessory.manager.authentication;

import android.content.Context;
import com.samsung.accessory.manager.connectivity.BTConnectivity;
import com.samsung.accessory.manager.connectivity.Connectivity;
import com.samsung.accessory.manager.connectivity.NfcConnectivity;
import com.samsung.accessory.manager.connectivity.UsbConnectivity;
import com.samsung.accessory.manager.connectivity.WirelessChargerConnectivity;

/* loaded from: classes.dex */
public abstract class Authenticator {
    public Connectivity mConnectivity;
    public Context mContext;
    public int mType;

    public abstract boolean onAuthenticationChallenge(AuthenticationResult authenticationResult);

    public abstract void onInterrupted();

    public abstract void setInterrupt(boolean z);

    public final void setConnection(Connectivity connectivity) {
        this.mConnectivity = connectivity;
    }

    public void setStateChangedCallback(Connectivity.StateChangedCallback stateChangedCallback) {
        this.mConnectivity.setStateChangedCallback(stateChangedCallback);
    }

    public Authenticator(Context context) {
        this.mContext = context;
    }

    public void setSessionState(int i) {
        Connectivity connectivity = this.mConnectivity;
        if (connectivity != null) {
            connectivity.setSessionState(i);
        }
    }

    public void setConnection() {
        if (this.mConnectivity != null) {
            return;
        }
        int i = this.mType;
        if (i == 1) {
            setConnection(new NfcConnectivity(this.mContext));
            return;
        }
        if (i == 2) {
            setConnection(new BTConnectivity(this.mContext));
        } else if (i == 3) {
            setConnection(new UsbConnectivity(this.mContext));
        } else {
            if (i != 4) {
                return;
            }
            setConnection(new WirelessChargerConnectivity(this.mContext));
        }
    }

    public Connectivity getConnectivity() {
        return this.mConnectivity;
    }

    public byte[] sendStartAuth(AuthenticationResult authenticationResult) {
        authenticationResult.setApiState(1);
        return this.mConnectivity.sendStartAuth(authenticationResult);
    }

    public boolean sendStopAuth() {
        return this.mConnectivity.sendStopAuth();
    }

    public boolean openNode(AuthenticationResult authenticationResult) {
        return this.mConnectivity.openNode(authenticationResult);
    }

    public byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        if (authenticationResult.getApiState() != 11) {
            authenticationResult.setApiState(2);
        }
        return this.mConnectivity.sendSynchronously(bArr, authenticationResult);
    }

    public void sendEndCommand() {
        Connectivity connectivity = this.mConnectivity;
        if (connectivity != null) {
            connectivity.sendStopUsbAuth();
        }
    }

    public void connect() {
        Connectivity connectivity = this.mConnectivity;
        if (connectivity != null) {
            connectivity.connect(null);
        }
    }

    public void disconnect() {
        Connectivity connectivity = this.mConnectivity;
        if (connectivity != null) {
            connectivity.disconnect();
        }
    }

    public void close() {
        Connectivity connectivity = this.mConnectivity;
        if (connectivity != null) {
            connectivity.close();
        }
    }
}
