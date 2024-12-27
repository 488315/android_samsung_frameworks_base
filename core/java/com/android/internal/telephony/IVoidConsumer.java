package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IVoidConsumer extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.IVoidConsumer";

    void accept() throws RemoteException;

    public static class Default implements IVoidConsumer {
        @Override // com.android.internal.telephony.IVoidConsumer
        public void accept() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVoidConsumer {
        static final int TRANSACTION_accept = 1;

        public Stub() {
            attachInterface(this, IVoidConsumer.DESCRIPTOR);
        }

        public static IVoidConsumer asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVoidConsumer.DESCRIPTOR);
            if (iin != null && (iin instanceof IVoidConsumer)) {
                return (IVoidConsumer) iin;
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
                data.enforceInterface(IVoidConsumer.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVoidConsumer.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    accept();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVoidConsumer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVoidConsumer.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.IVoidConsumer
            public void accept() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IVoidConsumer.DESCRIPTOR);
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
