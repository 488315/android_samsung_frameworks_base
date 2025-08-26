package com.android.wm.shell.splitscreen;

import android.os.IBinder;
import android.os.IInterface;

/* loaded from: classes3.dex */
public class ISplitScreenListener$Stub$Proxy implements IInterface {
    public final IBinder mRemote;

    public ISplitScreenListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }
}
