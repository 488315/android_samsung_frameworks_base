package com.samsung.android.hardware.display;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IRefreshRateToken extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.hardware.display.IRefreshRateToken";

    public static class Default implements IRefreshRateToken {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.hardware.display.IRefreshRateToken
        public void release() throws RemoteException {
        }
    }

    void release() throws RemoteException;

    public static abstract class Stub extends Binder implements IRefreshRateToken {
        static final int TRANSACTION_release = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRefreshRateToken.DESCRIPTOR);
        }

        public static IRefreshRateToken asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRefreshRateToken.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRefreshRateToken)) {
                return (IRefreshRateToken) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "release";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRefreshRateToken.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRefreshRateToken.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                release();
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRefreshRateToken {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRefreshRateToken.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.display.IRefreshRateToken
            public void release() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRefreshRateToken.DESCRIPTOR);
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
