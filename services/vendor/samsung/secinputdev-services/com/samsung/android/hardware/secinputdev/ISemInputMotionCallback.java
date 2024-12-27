package com.samsung.android.hardware.secinputdev;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISemInputMotionCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.hardware.secinputdev.ISemInputMotionCallback";

    void onEventChanged(int i) throws RemoteException;

    public static class Default implements ISemInputMotionCallback {
        @Override // com.samsung.android.hardware.secinputdev.ISemInputMotionCallback
        public void onEventChanged(int value) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemInputMotionCallback {
        static final int TRANSACTION_onEventChanged = 1;

        public Stub() {
            attachInterface(this, ISemInputMotionCallback.DESCRIPTOR);
        }

        public static ISemInputMotionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemInputMotionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemInputMotionCallback)) {
                return (ISemInputMotionCallback) iin;
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
                    return "onEventChanged";
                default:
                    return null;
            }
        }

        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemInputMotionCallback.DESCRIPTOR);
            }
            switch (code) {
                case 1598968902:
                    reply.writeString(ISemInputMotionCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            data.enforceNoDataAvail();
                            onEventChanged(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemInputMotionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInputMotionCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.hardware.secinputdev.ISemInputMotionCallback
            public void onEventChanged(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemInputMotionCallback.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        public int getMaxTransactionId() {
            return 0;
        }
    }
}
