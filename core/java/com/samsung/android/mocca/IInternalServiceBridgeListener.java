package com.samsung.android.mocca;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IInternalServiceBridgeListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.mocca.IInternalServiceBridgeListener";

    void onUpdated(String str, Bundle bundle) throws RemoteException;

    public static class Default implements IInternalServiceBridgeListener {
        @Override // com.samsung.android.mocca.IInternalServiceBridgeListener
        public void onUpdated(String type, Bundle value) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInternalServiceBridgeListener {
        static final int TRANSACTION_onUpdated = 1;

        public Stub() {
            attachInterface(this, IInternalServiceBridgeListener.DESCRIPTOR);
        }

        public static IInternalServiceBridgeListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInternalServiceBridgeListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IInternalServiceBridgeListener)) {
                return (IInternalServiceBridgeListener) iin;
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
                    return "onUpdated";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IInternalServiceBridgeListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInternalServiceBridgeListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    Bundle _arg1 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onUpdated(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInternalServiceBridgeListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInternalServiceBridgeListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.mocca.IInternalServiceBridgeListener
            public void onUpdated(String type, Bundle value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInternalServiceBridgeListener.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeTypedObject(value, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
