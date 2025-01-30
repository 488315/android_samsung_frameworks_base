package com.android.internal.telephony;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ILongConsumer extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ILongConsumer";

    void accept(long j) throws RemoteException;

    public static class Default implements ILongConsumer {
        @Override // com.android.internal.telephony.ILongConsumer
        public void accept(long result) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ILongConsumer {
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

        @Override // android.p009os.IInterface
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ILongConsumer.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ILongConsumer.DESCRIPTOR);
                    return true;
                default:
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
        }

        private static class Proxy implements ILongConsumer {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
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

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
