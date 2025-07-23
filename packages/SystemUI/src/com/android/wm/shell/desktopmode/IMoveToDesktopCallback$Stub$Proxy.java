package com.android.wm.shell.desktopmode;

import android.os.IBinder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class IMoveToDesktopCallback$Stub$Proxy implements IMoveToDesktopCallback {
    public final IBinder mRemote;

    public IMoveToDesktopCallback$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }
}
