package com.android.internal.policy;

import android.os.RemoteException;

/* loaded from: classes5.dex */
public class KeyguardDismissCallback extends IKeyguardDismissCallback.Stub {
    @Override // com.android.internal.policy.IKeyguardDismissCallback
    public void onDismissError() throws RemoteException {}

    @Override // com.android.internal.policy.IKeyguardDismissCallback
    public void onDismissSucceeded() throws RemoteException {}

    @Override // com.android.internal.policy.IKeyguardDismissCallback
    public void onDismissCancelled() throws RemoteException {}
}
