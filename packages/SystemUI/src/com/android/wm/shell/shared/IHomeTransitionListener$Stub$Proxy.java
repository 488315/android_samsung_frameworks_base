package com.android.wm.shell.shared;

import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes3.dex */
public class IHomeTransitionListener$Stub$Proxy implements IInterface {
    public final IBinder mRemote;

    public IHomeTransitionListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }
}
