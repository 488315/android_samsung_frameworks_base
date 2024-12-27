package com.samsung.android.contextengine;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemContextEngineManager extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.contextengine.ISemContextEngineManager";

    void setDefault() throws RemoteException;

    public static class Default implements ISemContextEngineManager {
        @Override // com.samsung.android.contextengine.ISemContextEngineManager
        public void setDefault() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemContextEngineManager {
        static final int TRANSACTION_setDefault = 1;

        public Stub() {
            attachInterface(this, ISemContextEngineManager.DESCRIPTOR);
        }

        public static ISemContextEngineManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemContextEngineManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemContextEngineManager)) {
                return (ISemContextEngineManager) iin;
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
                    return "setDefault";
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
                data.enforceInterface(ISemContextEngineManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemContextEngineManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    setDefault();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemContextEngineManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemContextEngineManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.contextengine.ISemContextEngineManager
            public void setDefault() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemContextEngineManager.DESCRIPTOR);
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
