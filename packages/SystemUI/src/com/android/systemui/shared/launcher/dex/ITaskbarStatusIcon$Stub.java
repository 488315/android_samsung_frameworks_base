package com.android.systemui.shared.launcher.dex;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.TaskbarIndicatorController;

/* loaded from: classes3.dex */
public abstract class ITaskbarStatusIcon$Stub extends Binder implements IInterface {
    public ITaskbarStatusIcon$Stub() {
        attachInterface(this, "com.android.systemui.shared.launcher.dex.ITaskbarStatusIcon");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.systemui.shared.launcher.dex.ITaskbarStatusIcon");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.systemui.shared.launcher.dex.ITaskbarStatusIcon");
            return true;
        }
        ITaskbarStatusIconListener$Stub$Proxy iTaskbarStatusIconListener$Stub$Proxy = null;
        if (i == 1) {
            IBinder strongBinder = parcel.readStrongBinder();
            if (strongBinder != null) {
                IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
                iTaskbarStatusIconListener$Stub$Proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ITaskbarStatusIconListener$Stub$Proxy)) ? new ITaskbarStatusIconListener$Stub$Proxy(strongBinder) : (ITaskbarStatusIconListener$Stub$Proxy) iInterfaceQueryLocalInterface;
            }
            parcel.enforceNoDataAvail();
            ((TaskbarIndicatorController) this).taskbarStatusIconListener = iTaskbarStatusIconListener$Stub$Proxy;
            return true;
        }
        if (i != 2) {
            if (i != 3) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            ((TaskbarIndicatorController) this).requestStatusIcons();
            return true;
        }
        IBinder strongBinder2 = parcel.readStrongBinder();
        if (strongBinder2 != null) {
            IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
            if (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof ITaskbarStatusIconListener$Stub$Proxy)) {
                new ITaskbarStatusIconListener$Stub$Proxy(strongBinder2);
            }
        }
        parcel.enforceNoDataAvail();
        ((TaskbarIndicatorController) this).taskbarStatusIconListener = null;
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
