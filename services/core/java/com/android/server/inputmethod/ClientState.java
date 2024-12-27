package com.android.server.inputmethod;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.IBinder;
import android.util.SparseArray;
import android.view.inputmethod.InputBinding;

import com.android.internal.inputmethod.IRemoteInputConnection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ClientState {
    public final SparseArray mAccessibilitySessions = new SparseArray();
    public final InputBinding mBinding;
    public final IInputMethodClientInvoker mClient;
    public final IBinder.DeathRecipient mClientDeathRecipient;
    public InputMethodManagerService.SessionState mCurSession;
    public final IRemoteInputConnection mFallbackInputConnection;
    public final int mPid;
    public final int mSelfReportedDisplayId;
    public boolean mSessionRequested;
    public boolean mSessionRequestedForAccessibility;
    public final int mUid;

    public ClientState(
            IInputMethodClientInvoker iInputMethodClientInvoker,
            IRemoteInputConnection iRemoteInputConnection,
            int i,
            int i2,
            int i3,
            ClientController$$ExternalSyntheticLambda0 clientController$$ExternalSyntheticLambda0) {
        this.mClient = iInputMethodClientInvoker;
        this.mFallbackInputConnection = iRemoteInputConnection;
        this.mUid = i;
        this.mPid = i2;
        this.mSelfReportedDisplayId = i3;
        this.mBinding = new InputBinding(null, iRemoteInputConnection.asBinder(), i, i2);
        this.mClientDeathRecipient = clientController$$ExternalSyntheticLambda0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ClientState{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" mUid=");
        sb.append(this.mUid);
        sb.append(" mPid=");
        sb.append(this.mPid);
        sb.append(" mSelfReportedDisplayId=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mSelfReportedDisplayId, sb, "}");
    }
}
