package com.samsung.android.cocktailbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISystemUiVisibilityCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.cocktailbar.ISystemUiVisibilityCallback";

    void onSystemUiVisibilityChanged(int i) throws RemoteException;

    public static class Default implements ISystemUiVisibilityCallback {
        @Override // com.samsung.android.cocktailbar.ISystemUiVisibilityCallback
        public void onSystemUiVisibilityChanged(int visibility) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISystemUiVisibilityCallback {
        static final int TRANSACTION_onSystemUiVisibilityChanged = 1;

        public Stub() {
            attachInterface(this, ISystemUiVisibilityCallback.DESCRIPTOR);
        }

        public static ISystemUiVisibilityCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISystemUiVisibilityCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISystemUiVisibilityCallback)) {
                return (ISystemUiVisibilityCallback) iin;
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
                    return "onSystemUiVisibilityChanged";
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
                data.enforceInterface(ISystemUiVisibilityCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISystemUiVisibilityCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onSystemUiVisibilityChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISystemUiVisibilityCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemUiVisibilityCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cocktailbar.ISystemUiVisibilityCallback
            public void onSystemUiVisibilityChanged(int visibility) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISystemUiVisibilityCallback.DESCRIPTOR);
                    _data.writeInt(visibility);
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
