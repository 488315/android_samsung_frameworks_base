package com.android.systemui.shared.system.smartspace;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ISysuiUnlockAnimationController$Stub extends Binder implements IInterface {
    public ISysuiUnlockAnimationController$Stub() {
        attachInterface(this, "com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        ILauncherUnlockAnimationController iLauncherUnlockAnimationController$Stub$Proxy;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController");
            return true;
        }
        if (i == 1) {
            IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder == null) {
                iLauncherUnlockAnimationController$Stub$Proxy = null;
            } else {
                IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
                iLauncherUnlockAnimationController$Stub$Proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof ILauncherUnlockAnimationController)) ? new ILauncherUnlockAnimationController$Stub$Proxy(readStrongBinder) : (ILauncherUnlockAnimationController) queryLocalInterface;
            }
            parcel.enforceNoDataAvail();
            ((KeyguardUnlockAnimationController) this).launcherUnlockController = iLauncherUnlockAnimationController$Stub$Proxy;
        } else {
            if (i != 2) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceNoDataAvail();
        }
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
