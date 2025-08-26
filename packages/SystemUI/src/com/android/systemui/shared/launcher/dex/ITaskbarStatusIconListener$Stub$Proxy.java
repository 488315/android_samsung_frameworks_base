package com.android.systemui.shared.launcher.dex;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes3.dex */
public class ITaskbarStatusIconListener$Stub$Proxy implements IInterface {
    public final IBinder mRemote;

    public ITaskbarStatusIconListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void setAirplaneMode(boolean z, int i) {
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        try {
            parcelObtain.writeInterfaceToken("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
            parcelObtain.writeBoolean(z);
            parcelObtain.writeInt(i);
            this.mRemote.transact(3, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }
}
