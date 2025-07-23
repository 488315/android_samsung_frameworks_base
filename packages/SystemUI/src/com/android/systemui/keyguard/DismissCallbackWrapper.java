package com.android.systemui.keyguard;

import android.os.RemoteException;
import com.android.internal.policy.IKeyguardDismissCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DismissCallbackWrapper {
    public final IKeyguardDismissCallback mCallback;

    public DismissCallbackWrapper(IKeyguardDismissCallback iKeyguardDismissCallback) {
        this.mCallback = iKeyguardDismissCallback;
    }

    public final void notifyDismissError() {
        try {
            this.mCallback.onDismissError();
        } catch (RemoteException e) {
            android.util.Log.i("DismissCallbackWrapper", "Failed to call callback", e);
        }
    }
}
