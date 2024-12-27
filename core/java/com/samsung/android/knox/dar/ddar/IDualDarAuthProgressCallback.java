package com.samsung.android.knox.dar.ddar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDualDarAuthProgressCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback";

    void onInnerLayerUnlockFailed() throws RemoteException;

    void onInnerLayerUnlocked() throws RemoteException;

    public static class Default implements IDualDarAuthProgressCallback {
        @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
        public void onInnerLayerUnlocked() throws RemoteException {}

        @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
        public void onInnerLayerUnlockFailed() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDualDarAuthProgressCallback {
        static final int TRANSACTION_onInnerLayerUnlockFailed = 2;
        static final int TRANSACTION_onInnerLayerUnlocked = 1;

        public Stub() {
            attachInterface(this, IDualDarAuthProgressCallback.DESCRIPTOR);
        }

        public static IDualDarAuthProgressCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDualDarAuthProgressCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IDualDarAuthProgressCallback)) {
                return (IDualDarAuthProgressCallback) iin;
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
                    return "onInnerLayerUnlocked";
                case 2:
                    return "onInnerLayerUnlockFailed";
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
                data.enforceInterface(IDualDarAuthProgressCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDualDarAuthProgressCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onInnerLayerUnlocked();
                    return true;
                case 2:
                    onInnerLayerUnlockFailed();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDualDarAuthProgressCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDualDarAuthProgressCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
            public void onInnerLayerUnlocked() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDualDarAuthProgressCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
            public void onInnerLayerUnlockFailed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDualDarAuthProgressCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
