package com.samsung.android.cocktailbar;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ICocktailBarShowCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cocktailbar.ICocktailBarShowCallback";

    void onShown(IBinder iBinder) throws RemoteException;

    public static class Default implements ICocktailBarShowCallback {
        @Override // com.samsung.android.cocktailbar.ICocktailBarShowCallback
        public void onShown(IBinder windowToken) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICocktailBarShowCallback {
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

        @Override // android.p009os.IInterface
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICocktailBarShowCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ICocktailBarShowCallback.DESCRIPTOR);
                    return true;
                default:
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
        }

        private static class Proxy implements ICocktailBarShowCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
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

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
