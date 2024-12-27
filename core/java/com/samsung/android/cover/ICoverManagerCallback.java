package com.samsung.android.cover;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICoverManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ICoverManagerCallback";

    void coverCallback(CoverState coverState) throws RemoteException;

    String getListenerInfo() throws RemoteException;

    public static class Default implements ICoverManagerCallback {
        @Override // com.samsung.android.cover.ICoverManagerCallback
        public void coverCallback(CoverState state) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManagerCallback
        public String getListenerInfo() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICoverManagerCallback {
        static final int TRANSACTION_coverCallback = 1;
        static final int TRANSACTION_getListenerInfo = 2;

        public Stub() {
            attachInterface(this, ICoverManagerCallback.DESCRIPTOR);
        }

        public static ICoverManagerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICoverManagerCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ICoverManagerCallback)) {
                return (ICoverManagerCallback) iin;
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
                    return "coverCallback";
                case 2:
                    return "getListenerInfo";
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
                data.enforceInterface(ICoverManagerCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICoverManagerCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    CoverState _arg0 = (CoverState) data.readTypedObject(CoverState.CREATOR);
                    data.enforceNoDataAvail();
                    coverCallback(_arg0);
                    return true;
                case 2:
                    String _result = getListenerInfo();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICoverManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICoverManagerCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ICoverManagerCallback
            public void coverCallback(CoverState state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICoverManagerCallback.DESCRIPTOR);
                    _data.writeTypedObject(state, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManagerCallback
            public String getListenerInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICoverManagerCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
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
