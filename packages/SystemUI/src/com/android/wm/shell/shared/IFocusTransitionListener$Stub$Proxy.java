package com.android.wm.shell.shared;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes3.dex */
public class IFocusTransitionListener$Stub$Proxy implements IInterface {
    public final IBinder mRemote;

    public IFocusTransitionListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void onFocusedDisplayChanged(int i) {
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        try {
            parcelObtain.writeInterfaceToken("com.android.wm.shell.shared.IFocusTransitionListener");
            parcelObtain.writeInt(i);
            this.mRemote.transact(1, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }
}
