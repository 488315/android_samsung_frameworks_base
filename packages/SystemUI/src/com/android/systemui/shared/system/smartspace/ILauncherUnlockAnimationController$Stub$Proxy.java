package com.android.systemui.shared.system.smartspace;

import android.graphics.Rect;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ILauncherUnlockAnimationController$Stub$Proxy implements IInterface {
    public final IBinder mRemote;

    public ILauncherUnlockAnimationController$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void playUnlockAnimation(long j, boolean z, long j2) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
            obtain.writeBoolean(z);
            obtain.writeLong(j);
            obtain.writeLong(j2);
            this.mRemote.transact(3, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }

    public final void prepareForUnlock(int i, boolean z, Rect rect) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
            obtain.writeBoolean(z);
            obtain.writeTypedObject(rect, 0);
            obtain.writeInt(i);
            this.mRemote.transact(1, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public final void setUnlockAmount(float f, boolean z) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
            obtain.writeFloat(f);
            obtain.writeBoolean(z);
            this.mRemote.transact(2, obtain, null, 1);
        } finally {
            obtain.recycle();
        }
    }
}
