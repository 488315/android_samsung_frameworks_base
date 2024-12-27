package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBooleanConsumer extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IBooleanConsumer";

    void accept(boolean z) throws RemoteException;

    public static class Default implements IBooleanConsumer {
        @Override // com.android.internal.telephony.IBooleanConsumer
        public void accept(boolean result) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IBooleanConsumer {
        static final int TRANSACTION_accept = 1;

        public Stub() {
            attachInterface(this, IBooleanConsumer.DESCRIPTOR);
        }

        public static IBooleanConsumer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBooleanConsumer.DESCRIPTOR);
            if (iin != null && (iin instanceof IBooleanConsumer)) {
                return (IBooleanConsumer) iin;
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
                data.enforceInterface(IBooleanConsumer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBooleanConsumer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    accept(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBooleanConsumer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBooleanConsumer.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IBooleanConsumer
            public void accept(boolean result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBooleanConsumer.DESCRIPTOR);
                    _data.writeBoolean(result);
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
