package com.android.systemui.shared.launcher.dex;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
            obtain.writeBoolean(z);
            obtain.writeInt(i);
            this.mRemote.transact(3, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }
}
