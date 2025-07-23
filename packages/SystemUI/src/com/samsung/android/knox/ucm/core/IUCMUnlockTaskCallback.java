package com.samsung.android.knox.ucm.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IUCMUnlockTaskCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ucm.core.IUCMUnlockTaskCallback";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IUCMUnlockTaskCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ucm.core.IUCMUnlockTaskCallback
        public int postAuthentication() throws RemoteException {
            return 0;
        }
    }

    int postAuthentication() throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IUCMUnlockTaskCallback {
        public static final int TRANSACTION_postAuthentication = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IUCMUnlockTaskCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUCMUnlockTaskCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ucm.core.IUCMUnlockTaskCallback
            public int postAuthentication() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IUCMUnlockTaskCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IUCMUnlockTaskCallback.DESCRIPTOR);
        }

        public static IUCMUnlockTaskCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUCMUnlockTaskCallback.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IUCMUnlockTaskCallback)) ? new Proxy(iBinder) : (IUCMUnlockTaskCallback) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUCMUnlockTaskCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUCMUnlockTaskCallback.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int postAuthentication = postAuthentication();
            parcel2.writeNoException();
            parcel2.writeInt(postAuthentication);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
