package com.samsung.android.wifi.stdp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IStandardPlusCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.stdp.IStandardPlusCallback";

    void onEvent(int i) throws RemoteException;

    public static class Default implements IStandardPlusCallback {
        @Override // com.samsung.android.wifi.stdp.IStandardPlusCallback
        public void onEvent(int event) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IStandardPlusCallback {
        static final int TRANSACTION_onEvent = 1;

        public Stub() {
            attachInterface(this, IStandardPlusCallback.DESCRIPTOR);
        }

        public static IStandardPlusCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IStandardPlusCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IStandardPlusCallback)) {
                return (IStandardPlusCallback) iin;
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
                    return "onEvent";
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
                data.enforceInterface(IStandardPlusCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IStandardPlusCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onEvent(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IStandardPlusCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IStandardPlusCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.stdp.IStandardPlusCallback
            public void onEvent(int event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IStandardPlusCallback.DESCRIPTOR);
                    _data.writeInt(event);
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
