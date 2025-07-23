package com.android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICredentialStatusCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.net.ICredentialStatusCallback";

    public static class Default implements ICredentialStatusCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.net.ICredentialStatusCallback
        public void onFail(int i) throws RemoteException {
        }

        @Override // com.android.net.ICredentialStatusCallback
        public void onSuccess() throws RemoteException {
        }
    }

    void onFail(int i) throws RemoteException;

    void onSuccess() throws RemoteException;

    public static abstract class Stub extends Binder implements ICredentialStatusCallback {
        static final int TRANSACTION_onFail = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ICredentialStatusCallback.DESCRIPTOR);
        }

        public static ICredentialStatusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICredentialStatusCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICredentialStatusCallback)) {
                return (ICredentialStatusCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i != 2) {
                return null;
            }
            return "onFail";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICredentialStatusCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICredentialStatusCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSuccess();
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFail(readInt);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICredentialStatusCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICredentialStatusCallback.DESCRIPTOR;
            }

            @Override // com.android.net.ICredentialStatusCallback
            public void onSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialStatusCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.ICredentialStatusCallback
            public void onFail(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICredentialStatusCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
