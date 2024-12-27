package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IResetMemoryCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.android.internal.telephony.euicc.IResetMemoryCallback";

    void onComplete(int i) throws RemoteException;

    public static class Default implements IResetMemoryCallback {
        @Override // com.android.internal.telephony.euicc.IResetMemoryCallback
        public void onComplete(int resultCode) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IResetMemoryCallback {
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            attachInterface(this, IResetMemoryCallback.DESCRIPTOR);
        }

        public static IResetMemoryCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IResetMemoryCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IResetMemoryCallback)) {
                return (IResetMemoryCallback) iin;
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
                    return "onComplete";
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
                data.enforceInterface(IResetMemoryCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IResetMemoryCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onComplete(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IResetMemoryCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IResetMemoryCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.euicc.IResetMemoryCallback
            public void onComplete(int resultCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IResetMemoryCallback.DESCRIPTOR);
                    _data.writeInt(resultCode);
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
