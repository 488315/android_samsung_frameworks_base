package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWeakEscrowTokenActivatedListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.widget.IWeakEscrowTokenActivatedListener";

    void onWeakEscrowTokenActivated(long j, int i) throws RemoteException;

    public static class Default implements IWeakEscrowTokenActivatedListener {
        @Override // com.android.internal.widget.IWeakEscrowTokenActivatedListener
        public void onWeakEscrowTokenActivated(long handle, int userId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWeakEscrowTokenActivatedListener {
        static final int TRANSACTION_onWeakEscrowTokenActivated = 1;

        public Stub() {
            attachInterface(this, IWeakEscrowTokenActivatedListener.DESCRIPTOR);
        }

        public static IWeakEscrowTokenActivatedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWeakEscrowTokenActivatedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IWeakEscrowTokenActivatedListener)) {
                return (IWeakEscrowTokenActivatedListener) iin;
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
                    return "onWeakEscrowTokenActivated";
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
                data.enforceInterface(IWeakEscrowTokenActivatedListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWeakEscrowTokenActivatedListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onWeakEscrowTokenActivated(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWeakEscrowTokenActivatedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWeakEscrowTokenActivatedListener.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IWeakEscrowTokenActivatedListener
            public void onWeakEscrowTokenActivated(long handle, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWeakEscrowTokenActivatedListener.DESCRIPTOR);
                    _data.writeLong(handle);
                    _data.writeInt(userId);
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
