package com.android.systemui.unfold.progress;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class IUnfoldAnimation$Stub extends Binder implements IInterface {
    public IUnfoldAnimation$Stub() {
        attachInterface(this, "com.android.systemui.unfold.progress.IUnfoldAnimation");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        IUnfoldTransitionListener iUnfoldTransitionListener$Stub$Proxy;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.systemui.unfold.progress.IUnfoldAnimation");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.systemui.unfold.progress.IUnfoldAnimation");
            return true;
        }
        if (i != 1) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder == null) {
            iUnfoldTransitionListener$Stub$Proxy = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.unfold.progress.IUnfoldTransitionListener");
            iUnfoldTransitionListener$Stub$Proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IUnfoldTransitionListener)) ? new IUnfoldTransitionListener$Stub$Proxy(readStrongBinder) : (IUnfoldTransitionListener) queryLocalInterface;
        }
        parcel.enforceNoDataAvail();
        ((UnfoldTransitionProgressForwarder) this).remoteListener = iUnfoldTransitionListener$Stub$Proxy;
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
