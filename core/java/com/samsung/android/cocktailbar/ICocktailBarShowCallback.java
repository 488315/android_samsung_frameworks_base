package com.samsung.android.cocktailbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ICocktailBarShowCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.cocktailbar.ICocktailBarShowCallback";

    void onShown(IBinder iBinder) throws RemoteException;

    public static class Default implements ICocktailBarShowCallback {
        @Override // com.samsung.android.cocktailbar.ICocktailBarShowCallback
        public void onShown(IBinder windowToken) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICocktailBarShowCallback {
        static final int TRANSACTION_onShown = 1;

        public Stub() {
            attachInterface(this, ICocktailBarShowCallback.DESCRIPTOR);
        }

        public static ICocktailBarShowCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICocktailBarShowCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ICocktailBarShowCallback)) {
                return (ICocktailBarShowCallback) iin;
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
                    return "onShown";
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
                data.enforceInterface(ICocktailBarShowCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICocktailBarShowCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    onShown(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICocktailBarShowCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICocktailBarShowCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarShowCallback
            public void onShown(IBinder windowToken) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICocktailBarShowCallback.DESCRIPTOR);
                    _data.writeStrongBinder(windowToken);
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
