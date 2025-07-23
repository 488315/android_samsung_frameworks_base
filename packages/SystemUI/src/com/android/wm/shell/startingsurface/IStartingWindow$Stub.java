package com.android.wm.shell.startingsurface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.startingsurface.StartingWindowController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class IStartingWindow$Stub extends Binder implements IInterface {
    public IStartingWindow$Stub() {
        attachInterface(this, "com.android.wm.shell.startingsurface.IStartingWindow");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        final IStartingWindowListener$Stub$Proxy iStartingWindowListener$Stub$Proxy;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.startingsurface.IStartingWindow");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.startingsurface.IStartingWindow");
            return true;
        }
        if (i != 44) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder == null) {
            iStartingWindowListener$Stub$Proxy = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.startingsurface.IStartingWindowListener");
            iStartingWindowListener$Stub$Proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IStartingWindowListener$Stub$Proxy)) ? new IStartingWindowListener$Stub$Proxy(readStrongBinder) : (IStartingWindowListener$Stub$Proxy) queryLocalInterface;
        }
        parcel.enforceNoDataAvail();
        final StartingWindowController.IStartingWindowImpl iStartingWindowImpl = (StartingWindowController.IStartingWindowImpl) this;
        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(iStartingWindowImpl.mController, "setStartingWindowListener", new Consumer() { // from class: com.android.wm.shell.startingsurface.StartingWindowController$IStartingWindowImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StartingWindowController.IStartingWindowImpl iStartingWindowImpl2 = StartingWindowController.IStartingWindowImpl.this;
                IStartingWindowListener$Stub$Proxy iStartingWindowListener$Stub$Proxy2 = iStartingWindowListener$Stub$Proxy;
                if (iStartingWindowListener$Stub$Proxy2 != null) {
                    iStartingWindowImpl2.mListener.register(iStartingWindowListener$Stub$Proxy2);
                } else {
                    iStartingWindowImpl2.mListener.unregister();
                }
            }
        }, false);
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
