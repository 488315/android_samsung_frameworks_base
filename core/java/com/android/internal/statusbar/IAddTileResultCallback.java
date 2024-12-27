package com.android.internal.statusbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IAddTileResultCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.statusbar.IAddTileResultCallback";

    void onTileRequest(int i) throws RemoteException;

    public static class Default implements IAddTileResultCallback {
        @Override // com.android.internal.statusbar.IAddTileResultCallback
        public void onTileRequest(int userResponse) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IAddTileResultCallback {
        static final int TRANSACTION_onTileRequest = 1;

        public Stub() {
            attachInterface(this, IAddTileResultCallback.DESCRIPTOR);
        }

        public static IAddTileResultCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAddTileResultCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IAddTileResultCallback)) {
                return (IAddTileResultCallback) iin;
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
                    return "onTileRequest";
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
                data.enforceInterface(IAddTileResultCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAddTileResultCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onTileRequest(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAddTileResultCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAddTileResultCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.IAddTileResultCallback
            public void onTileRequest(int userResponse) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAddTileResultCallback.DESCRIPTOR);
                    _data.writeInt(userResponse);
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
