package com.android.net;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.sec.enterprise.proxy.IProxyCredentialsCallback;

/* loaded from: classes5.dex */
public interface IProxyCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.net.IProxyCallback";

    void clearProxyServerCache() throws RemoteException;

    void getProxyPort(IBinder iBinder) throws RemoteException;

    void onCredentialsReceived(Bundle bundle, IProxyCredentialsCallback iProxyCredentialsCallback) throws RemoteException;

    void setEnterpriseProxy(boolean z) throws RemoteException;

    public static class Default implements IProxyCallback {
        @Override // com.android.net.IProxyCallback
        public void getProxyPort(IBinder callback) throws RemoteException {
        }

        @Override // com.android.net.IProxyCallback
        public void clearProxyServerCache() throws RemoteException {
        }

        @Override // com.android.net.IProxyCallback
        public void onCredentialsReceived(Bundle response, IProxyCredentialsCallback callback) throws RemoteException {
        }

        @Override // com.android.net.IProxyCallback
        public void setEnterpriseProxy(boolean value) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IProxyCallback {
        static final int TRANSACTION_clearProxyServerCache = 2;
        static final int TRANSACTION_getProxyPort = 1;
        static final int TRANSACTION_onCredentialsReceived = 3;
        static final int TRANSACTION_setEnterpriseProxy = 4;

        public Stub() {
            attachInterface(this, IProxyCallback.DESCRIPTOR);
        }

        public static IProxyCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IProxyCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IProxyCallback)) {
                return (IProxyCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getProxyPort";
                case 2:
                    return "clearProxyServerCache";
                case 3:
                    return "onCredentialsReceived";
                case 4:
                    return "setEnterpriseProxy";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IProxyCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IProxyCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    getProxyPort(_arg0);
                    return true;
                case 2:
                    clearProxyServerCache();
                    return true;
                case 3:
                    Bundle _arg02 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    IProxyCredentialsCallback _arg1 = IProxyCredentialsCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onCredentialsReceived(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 4:
                    boolean _arg03 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setEnterpriseProxy(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IProxyCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProxyCallback.DESCRIPTOR;
            }

            @Override // com.android.net.IProxyCallback
            public void getProxyPort(IBinder callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IProxyCallback.DESCRIPTOR);
                    _data.writeStrongBinder(callback);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyCallback
            public void clearProxyServerCache() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IProxyCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyCallback
            public void onCredentialsReceived(Bundle response, IProxyCredentialsCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IProxyCallback.DESCRIPTOR);
                    _data.writeTypedObject(response, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.net.IProxyCallback
            public void setEnterpriseProxy(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IProxyCallback.DESCRIPTOR);
                    _data.writeBoolean(value);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
