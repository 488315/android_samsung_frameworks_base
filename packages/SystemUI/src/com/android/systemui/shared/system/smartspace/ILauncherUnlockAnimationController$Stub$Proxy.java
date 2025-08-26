package com.android.systemui.shared.system.smartspace;

import android.graphics.Rect;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

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
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        try {
            parcelObtain.writeInterfaceToken("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
            parcelObtain.writeBoolean(true);
            parcelObtain.writeLong(j);
            parcelObtain.writeLong(j2);
            this.mRemote.transact(3, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }

    public final void prepareForUnlock(Rect rect) {
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
            parcelObtain.writeBoolean(false);
            parcelObtain.writeTypedObject(rect, 0);
            parcelObtain.writeInt(0);
            this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    public final void setUnlockAmount(boolean z) {
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        try {
            parcelObtain.writeInterfaceToken("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
            parcelObtain.writeFloat(1.0f);
            parcelObtain.writeBoolean(z);
            this.mRemote.transact(2, parcelObtain, null, 1);
        } finally {
            parcelObtain.recycle();
        }
    }
}
