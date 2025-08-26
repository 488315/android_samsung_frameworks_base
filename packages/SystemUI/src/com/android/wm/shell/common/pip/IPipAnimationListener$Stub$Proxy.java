package com.android.wm.shell.common.pip;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

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
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        try {
            parcelObtain.writeInterfaceToken("com.android.wm.shell.common.pip.IPipAnimationListener");
            this.mRemote.transact(1, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    public final void onPipResourceDimensionsChanged(int i, int i2) {
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        try {
            parcelObtain.writeInterfaceToken("com.android.wm.shell.common.pip.IPipAnimationListener");
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.mRemote.transact(2, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }
}
