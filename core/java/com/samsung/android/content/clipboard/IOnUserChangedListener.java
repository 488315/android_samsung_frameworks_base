package com.samsung.android.content.clipboard;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IOnUserChangedListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.content.clipboard.IOnUserChangedListener";

    void onUserChanged(int i) throws RemoteException;

    public static class Default implements IOnUserChangedListener {
        @Override // com.samsung.android.content.clipboard.IOnUserChangedListener
        public void onUserChanged(int userId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IOnUserChangedListener {
        static final int TRANSACTION_onUserChanged = 1;

        public Stub() {
            attachInterface(this, IOnUserChangedListener.DESCRIPTOR);
        }

        public static IOnUserChangedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOnUserChangedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IOnUserChangedListener)) {
                return (IOnUserChangedListener) iin;
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
                    return "onUserChanged";
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
                data.enforceInterface(IOnUserChangedListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOnUserChangedListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onUserChanged(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOnUserChangedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOnUserChangedListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.content.clipboard.IOnUserChangedListener
            public void onUserChanged(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IOnUserChangedListener.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
