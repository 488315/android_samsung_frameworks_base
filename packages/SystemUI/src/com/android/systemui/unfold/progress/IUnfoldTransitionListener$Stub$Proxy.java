package com.android.systemui.unfold.progress;

import android.os.IBinder;
import android.os.IInterface;

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
