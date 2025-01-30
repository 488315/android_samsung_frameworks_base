package com.android.systemui.shared.system.smartspace;

import android.os.IBinder;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ILauncherUnlockAnimationController$Stub$Proxy implements ILauncherUnlockAnimationController {
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
