package com.android.wm.shell.common.pip;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class IPipAnimationListener$Stub$Proxy implements IInterface {
    public final IBinder mRemote;

    public IPipAnimationListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void onPipAnimationStarted() {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.common.pip.IPipAnimationListener");
            this.mRemote.transact(1, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void onPipResourceDimensionsChanged(int i, int i2) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.common.pip.IPipAnimationListener");
            obtain.writeInt(i);
            obtain.writeInt(i2);
            this.mRemote.transact(2, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }
}
