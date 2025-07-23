package com.android.net;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.sec.enterprise.proxy.IProxyCredentialsCallback;

/* loaded from: classes6.dex */
public interface IProxyCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.net.IProxyCallback";

    public static class Default implements IProxyCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.net.IProxyCallback
        public void clearProxyServerCache() throws RemoteException {
        }

        @Override // com.android.net.IProxyCallback
        public void getProxyPort(IBinder iBinder) throws RemoteException {
        }

        @Override // com.android.net.IProxyCallback
        public void onCredentialsReceived(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) throws RemoteException {
        }

        @Override // com.android.net.IProxyCallback
        public void setEnterpriseProxy(boolean z) throws RemoteException {
        }
    }

    void clearProxyServerCache() throws RemoteException;

    void getProxyPort(IBinder iBinder) throws RemoteException;

    void onCredentialsReceived(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) throws RemoteException;

    void setEnterpriseProxy(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IProxyCallback {
        static final int TRANSACTION_clearProxyServerCache = 2;
        static final int TRANSACTION_getProxyPort = 1;
        static final int TRANSACTION_onCredentialsReceived = 3;
        static final int TRANSACTION_setEnterpriseProxy = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IProxyCallback.DESCRIPTOR);
        }

        public static IProxyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProxyCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProxyCallback)) {
                return (IProxyCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getProxyPort";
            }
            if (i == 2) {
                return "clearProxyServerCache";
            }
            if (i == 3) {
                return "onCredentialsReceived";
            }
            if (i != 4) {
                return null;
            }
            return "setEnterpriseProxy";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProxyCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProxyCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                getProxyPort(readStrongBinder);
            } else if (i == 2) {
                clearProxyServerCache();
            } else if (i == 3) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                IProxyCredentialsCallback asInterface = IProxyCredentialsCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onCredentialsReceived(bundle, asInterface);
                parcel2.writeNoException();
            } else if (i == 4) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setEnterpriseProxy(readBoolean);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IProxyCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProxyCallback.DESCRIPTOR;
            }

            @Override // com.android.net.IProxyCallback
            public void getProxyPort(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IProxyCallback.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyCallback
            public void clearProxyServerCache() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IProxyCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyCallback
            public void onCredentialsReceived(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyCallback.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeStrongInterface(iProxyCredentialsCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.net.IProxyCallback
            public void setEnterpriseProxy(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IProxyCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
