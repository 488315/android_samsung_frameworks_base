package com.samsung.android.camera.iris;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIrisServiceLockoutResetCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback";

    public static class Default implements IIrisServiceLockoutResetCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback
        public void onLockoutReset(long j) throws RemoteException {
        }
    }

    void onLockoutReset(long j) throws RemoteException;

    public static abstract class Stub extends Binder implements IIrisServiceLockoutResetCallback {
        static final int TRANSACTION_onLockoutReset = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IIrisServiceLockoutResetCallback.DESCRIPTOR);
        }

        public static IIrisServiceLockoutResetCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIrisServiceLockoutResetCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIrisServiceLockoutResetCallback)) {
                return (IIrisServiceLockoutResetCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onLockoutReset";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIrisServiceLockoutResetCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIrisServiceLockoutResetCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                long readLong = parcel.readLong();
                parcel.enforceNoDataAvail();
                onLockoutReset(readLong);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IIrisServiceLockoutResetCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIrisServiceLockoutResetCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.camera.iris.IIrisServiceLockoutResetCallback
            public void onLockoutReset(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIrisServiceLockoutResetCallback.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
