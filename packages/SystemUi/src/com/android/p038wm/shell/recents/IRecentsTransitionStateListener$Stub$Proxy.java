package com.android.p038wm.shell.recents;

import android.os.IBinder;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IRecentsTransitionStateListener$Stub$Proxy implements IRecentsTransitionStateListener {
    public final IBinder mRemote;

    public IRecentsTransitionStateListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void onAnimationStateChanged(boolean z) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentsTransitionStateListener");
            obtain.writeBoolean(z);
            this.mRemote.transact(1, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }
}
