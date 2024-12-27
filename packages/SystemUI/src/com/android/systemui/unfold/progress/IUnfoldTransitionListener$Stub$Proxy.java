package com.android.systemui.unfold.progress;

import android.os.IBinder;
import android.os.IInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class IUnfoldTransitionListener$Stub$Proxy implements IInterface {
    public final IBinder mRemote;

    public IUnfoldTransitionListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }
}
