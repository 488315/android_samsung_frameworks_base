package com.samsung.android.knox.ex.peripheral;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IInfoListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ex.peripheral.IInfoListener";

    long getHashCode() throws RemoteException;

    void onFail(int i, String str) throws RemoteException;

    void onReceive(Bundle bundle) throws RemoteException;

    void onSuccess() throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IInfoListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
        public long getHashCode() throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
        public void onSuccess() throws RemoteException {
        }

        @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
        public void onReceive(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
        public void onFail(int i, String str) throws RemoteException {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IInfoListener {
        public static final int TRANSACTION_getHashCode = 1;
        public static final int TRANSACTION_onFail = 3;
        public static final int TRANSACTION_onReceive = 4;
        public static final int TRANSACTION_onSuccess = 2;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IInfoListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
            public long getHashCode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IInfoListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IInfoListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
            public void onFail(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IInfoListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
            public void onReceive(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IInfoListener.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ex.peripheral.IInfoListener
            public void onSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IInfoListener.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IInfoListener.DESCRIPTOR);
        }

        public static IInfoListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInfoListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IInfoListener)) ? new Proxy(iBinder) : (IInfoListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInfoListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInfoListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long hashCode = getHashCode();
                parcel2.writeNoException();
                parcel2.writeLong(hashCode);
            } else if (i == 2) {
                onSuccess();
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onFail(readInt, readString);
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onReceive(bundle);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
