package com.samsung.android.continuity;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISemContinuitySimpleListener extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.continuity.ISemContinuitySimpleListener";

    void onNotify(Bundle bundle) throws RemoteException;

    public static class Default implements ISemContinuitySimpleListener {
        @Override // com.samsung.android.continuity.ISemContinuitySimpleListener
        public void onNotify(Bundle bundle) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemContinuitySimpleListener {
        static final int TRANSACTION_onNotify = 1;

        public Stub() {
            attachInterface(this, ISemContinuitySimpleListener.DESCRIPTOR);
        }

        public static ISemContinuitySimpleListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemContinuitySimpleListener.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemContinuitySimpleListener)) {
                return (ISemContinuitySimpleListener) iin;
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
                    return "onNotify";
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
                data.enforceInterface(ISemContinuitySimpleListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemContinuitySimpleListener.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Bundle _arg0 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    onNotify(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemContinuitySimpleListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemContinuitySimpleListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.continuity.ISemContinuitySimpleListener
            public void onNotify(Bundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemContinuitySimpleListener.DESCRIPTOR);
                    _data.writeTypedObject(bundle, 0);
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
