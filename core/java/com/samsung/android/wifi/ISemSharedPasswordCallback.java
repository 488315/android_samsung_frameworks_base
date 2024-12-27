package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISemSharedPasswordCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemSharedPasswordCallback";

    void onAccepted(String str, String str2) throws RemoteException;

    void onAvailable(boolean z) throws RemoteException;

    void onRejected(String str) throws RemoteException;

    public static class Default implements ISemSharedPasswordCallback {
        @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
        public void onAccepted(String bssid, String password) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
        public void onRejected(String bssid) throws RemoteException {}

        @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
        public void onAvailable(boolean isPossibleToRequest) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemSharedPasswordCallback {
        static final int TRANSACTION_onAccepted = 1;
        static final int TRANSACTION_onAvailable = 3;
        static final int TRANSACTION_onRejected = 2;

        public Stub() {
            attachInterface(this, ISemSharedPasswordCallback.DESCRIPTOR);
        }

        public static ISemSharedPasswordCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemSharedPasswordCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemSharedPasswordCallback)) {
                return (ISemSharedPasswordCallback) iin;
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
                    return "onAccepted";
                case 2:
                    return "onRejected";
                case 3:
                    return "onAvailable";
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
                data.enforceInterface(ISemSharedPasswordCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemSharedPasswordCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    onAccepted(_arg0, _arg1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    onRejected(_arg02);
                    return true;
                case 3:
                    boolean _arg03 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onAvailable(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemSharedPasswordCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemSharedPasswordCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
            public void onAccepted(String bssid, String password) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemSharedPasswordCallback.DESCRIPTOR);
                    _data.writeString(bssid);
                    _data.writeString(password);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
            public void onRejected(String bssid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemSharedPasswordCallback.DESCRIPTOR);
                    _data.writeString(bssid);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
            public void onAvailable(boolean isPossibleToRequest) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemSharedPasswordCallback.DESCRIPTOR);
                    _data.writeBoolean(isPossibleToRequest);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
