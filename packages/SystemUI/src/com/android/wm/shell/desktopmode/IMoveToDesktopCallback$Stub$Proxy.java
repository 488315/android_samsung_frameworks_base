package com.android.wm.shell.desktopmode;

import android.os.IBinder;

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
