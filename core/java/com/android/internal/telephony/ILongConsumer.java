package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ILongConsumer extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ILongConsumer";

    void accept(long j) throws RemoteException;

    public static class Default implements ILongConsumer {
        @Override // com.android.internal.telephony.ILongConsumer
        public void accept(long result) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ILongConsumer {
        static final int TRANSACTION_accept = 1;

        public Stub() {
            attachInterface(this, ILongConsumer.DESCRIPTOR);
        }

        public static ILongConsumer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ILongConsumer.DESCRIPTOR);
            if (iin != null && (iin instanceof ILongConsumer)) {
                return (ILongConsumer) iin;
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
                    return "accept";
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
                data.enforceInterface(ILongConsumer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ILongConsumer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    long _arg0 = data.readLong();
                    data.enforceNoDataAvail();
                    accept(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ILongConsumer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILongConsumer.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ILongConsumer
            public void accept(long result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ILongConsumer.DESCRIPTOR);
                    _data.writeLong(result);
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
