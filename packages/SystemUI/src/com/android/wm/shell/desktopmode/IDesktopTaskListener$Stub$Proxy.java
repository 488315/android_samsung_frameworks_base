package com.android.wm.shell.desktopmode;

import android.os.IBinder;
import android.os.Parcel;

/* loaded from: classes3.dex */
public class IDesktopTaskListener$Stub$Proxy implements IDesktopTaskListener {
    public final IBinder mRemote;

    public IDesktopTaskListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void onDesktopDisabledFlagsChangedOnDefaultDisplay(int i) {
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        try {
            parcelObtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
            parcelObtain.writeInt(i);
            this.mRemote.transact(12, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }
}
