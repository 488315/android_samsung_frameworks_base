package com.android.systemui.shared.system.smartspace;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes3.dex */
public interface ISysuiUnlockAnimationController extends IInterface {
    void onLauncherSmartspaceStateUpdated(SmartspaceState smartspaceState);

    void setLauncherUnlockController(String str, ILauncherUnlockAnimationController$Stub$Proxy iLauncherUnlockAnimationController$Stub$Proxy);

    void unlockAnimationReady();

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
            String string = parcel.readString();
            IBinder strongBinder = parcel.readStrongBinder();
            if (strongBinder == null) {
                iLauncherUnlockAnimationController$Stub$Proxy = null;
            } else {
                IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController");
                iLauncherUnlockAnimationController$Stub$Proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ILauncherUnlockAnimationController$Stub$Proxy)) ? new ILauncherUnlockAnimationController$Stub$Proxy(strongBinder) : (ILauncherUnlockAnimationController$Stub$Proxy) iInterfaceQueryLocalInterface;
            }
            parcel.enforceNoDataAvail();
            setLauncherUnlockController(string, iLauncherUnlockAnimationController$Stub$Proxy);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
