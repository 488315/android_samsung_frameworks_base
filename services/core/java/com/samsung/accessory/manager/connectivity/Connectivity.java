package com.samsung.accessory.manager.connectivity;

import android.content.Context;

import com.samsung.accessory.manager.authentication.AuthenticationResult;
import com.samsung.accessory.manager.authentication.AuthenticationSession;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class Connectivity {
    public static final byte[] NOT_SUPPORT = {0, 0, 0};
    public final Context mContext;
    public boolean mEnabledInternal = false;
    public int mSessionState = -1;
    public AuthenticationSession.AnonymousClass1 mStateChangedCallback;

    public Connectivity(Context context) {
        this.mContext = context;
    }

    public abstract void close();

    public abstract void connect();

    public abstract boolean disable();

    public abstract void disconnect();

    public abstract void dump(PrintWriter printWriter);

    public abstract boolean enable();

    public abstract boolean isEnabled();

    public boolean isEnabledInternally() {
        return this.mEnabledInternal;
    }

    public abstract boolean openNode();

    public abstract byte[] sendStartAuth(AuthenticationResult authenticationResult);

    public abstract boolean sendStopAuth();

    public abstract void sendStopUsbAuth();

    public abstract byte[] sendSynchronously(
            byte[] bArr, AuthenticationResult authenticationResult);

    public void setStateChangedCallback(AuthenticationSession.AnonymousClass1 anonymousClass1) {
        this.mStateChangedCallback = anonymousClass1;
    }
}
