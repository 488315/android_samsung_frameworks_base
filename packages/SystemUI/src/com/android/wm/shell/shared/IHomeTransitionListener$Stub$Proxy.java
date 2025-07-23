package com.android.wm.shell.shared;

import android.os.IBinder;
import android.os.IInterface;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
