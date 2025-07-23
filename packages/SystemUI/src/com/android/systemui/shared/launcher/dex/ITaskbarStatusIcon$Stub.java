package com.android.systemui.shared.launcher.dex;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.TaskbarIndicatorController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder != null) {
                IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
                iTaskbarStatusIconListener$Stub$Proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof ITaskbarStatusIconListener$Stub$Proxy)) ? new ITaskbarStatusIconListener$Stub$Proxy(readStrongBinder) : (ITaskbarStatusIconListener$Stub$Proxy) queryLocalInterface;
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
        IBinder readStrongBinder2 = parcel.readStrongBinder();
        if (readStrongBinder2 != null) {
            IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
            if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof ITaskbarStatusIconListener$Stub$Proxy)) {
                new ITaskbarStatusIconListener$Stub$Proxy(readStrongBinder2);
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
