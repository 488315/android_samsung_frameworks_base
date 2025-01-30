package com.android.systemui.unfold.progress;

import android.os.IBinder;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class IUnfoldTransitionListener$Stub$Proxy implements IUnfoldTransitionListener {
    public final IBinder mRemote;

    public IUnfoldTransitionListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void onTransitionFinished() {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
            this.mRemote.transact(4, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void onTransitionProgress(float f) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
            obtain.writeFloat(f);
            this.mRemote.transact(3, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void onTransitionStarted() {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
            this.mRemote.transact(2, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }
}
