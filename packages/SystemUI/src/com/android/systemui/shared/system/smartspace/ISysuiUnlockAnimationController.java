package com.android.systemui.shared.system.smartspace;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ISysuiUnlockAnimationController extends IInterface {
    void onLauncherSmartspaceStateUpdated(SmartspaceState smartspaceState);

    void setLauncherUnlockController(String str, ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy);

    void unlockAnimationReady();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ISysuiUnlockAnimationController {
        public Stub() {
            attachInterface(this, "com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController");
                return true;
            }
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    unlockAnimationReady();
                    return true;
                }
                SmartspaceState smartspaceState = (SmartspaceState) parcel.readTypedObject(SmartspaceState.CREATOR);
                parcel.enforceNoDataAvail();
                onLauncherSmartspaceStateUpdated(smartspaceState);
                return true;
            }
            String readString = parcel.readString();
            IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder == null) {
                iLauncherUnlockAnimationController$Stub$Proxy = null;
            } else {
                IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
                iLauncherUnlockAnimationController$Stub$Proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof ILauncherUnlockAnimationController$Stub$Proxy)) ? new ILauncherUnlockAnimationController$Stub$Proxy(readStrongBinder) : (ILauncherUnlockAnimationController$Stub$Proxy) queryLocalInterface;
            }
            parcel.enforceNoDataAvail();
            setLauncherUnlockController(readString, iLauncherUnlockAnimationController$Stub$Proxy);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
