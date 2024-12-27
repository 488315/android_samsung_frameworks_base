package com.samsung.accessory.manager.authentication;

import android.content.Context;

import com.samsung.accessory.manager.connectivity.Connectivity;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Authenticator {
    public Connectivity mConnectivity;
    public Context mContext;
    public int mType;

    public final void sendStopAuth() {
        this.mConnectivity.sendStopAuth();
    }

    public final byte[] sendSynchronously(byte[] bArr, AuthenticationResult authenticationResult) {
        if (authenticationResult.apiState != 11) {
            authenticationResult.apiState = 2;
        }
        return this.mConnectivity.sendSynchronously(bArr, authenticationResult);
    }

    public final void setSessionState(int i) {
        Connectivity connectivity = this.mConnectivity;
        if (connectivity != null) {
            connectivity.mSessionState = i;
        }
    }
}
