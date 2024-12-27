package com.android.internal.widget;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IWeakEscrowTokenRemovedListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.widget.IWeakEscrowTokenRemovedListener";

    void onWeakEscrowTokenRemoved(long j, int i) throws RemoteException;

    public static class Default implements IWeakEscrowTokenRemovedListener {
        @Override // com.android.internal.widget.IWeakEscrowTokenRemovedListener
        public void onWeakEscrowTokenRemoved(long handle, int userId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWeakEscrowTokenRemovedListener {
        static final int TRANSACTION_onWeakEscrowTokenRemoved = 1;

        public Stub() {
            attachInterface(this, IWeakEscrowTokenRemovedListener.DESCRIPTOR);
        }

        public static IWeakEscrowTokenRemovedListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWeakEscrowTokenRemovedListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IWeakEscrowTokenRemovedListener)) {
                return (IWeakEscrowTokenRemovedListener) iin;
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
                    return "onWeakEscrowTokenRemoved";
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
                data.enforceInterface(IWeakEscrowTokenRemovedListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWeakEscrowTokenRemovedListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onWeakEscrowTokenRemoved(_arg0, _arg1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWeakEscrowTokenRemovedListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWeakEscrowTokenRemovedListener.DESCRIPTOR;
            }

            @Override // com.android.internal.widget.IWeakEscrowTokenRemovedListener
            public void onWeakEscrowTokenRemoved(long handle, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWeakEscrowTokenRemovedListener.DESCRIPTOR);
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
