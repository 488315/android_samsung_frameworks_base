package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IBooleanListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.inputmethod.IBooleanListener";

    void onResult(boolean z) throws RemoteException;

    public static class Default implements IBooleanListener {
        @Override // com.android.internal.inputmethod.IBooleanListener
        public void onResult(boolean value) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IBooleanListener {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, IBooleanListener.DESCRIPTOR);
        }

        public static IBooleanListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBooleanListener.DESCRIPTOR);
            if (iin != null && (iin instanceof IBooleanListener)) {
                return (IBooleanListener) iin;
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
                    return "onResult";
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
                data.enforceInterface(IBooleanListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBooleanListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onResult(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBooleanListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBooleanListener.DESCRIPTOR;
            }

            @Override // com.android.internal.inputmethod.IBooleanListener
            public void onResult(boolean value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBooleanListener.DESCRIPTOR);
                    _data.writeBoolean(value);
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
