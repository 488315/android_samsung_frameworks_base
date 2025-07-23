package com.android.systemui.shared.system.smartspace;

import android.graphics.Rect;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ILauncherUnlockAnimationController$Stub$Proxy implements IInterface {
    public final IBinder mRemote;

    public ILauncherUnlockAnimationController$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void playUnlockAnimation(long j, long j2) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
            obtain.writeBoolean(true);
            obtain.writeLong(j);
            obtain.writeLong(j2);
            this.mRemote.transact(3, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void prepareForUnlock(Rect rect) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
            obtain.writeBoolean(false);
            obtain.writeTypedObject(rect, 0);
            obtain.writeInt(0);
            this.mRemote.transact(1, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public final void setUnlockAmount(boolean z) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
            obtain.writeFloat(1.0f);
            obtain.writeBoolean(z);
            this.mRemote.transact(2, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }
}
