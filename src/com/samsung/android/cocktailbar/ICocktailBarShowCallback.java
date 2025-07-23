package com.samsung.android.cocktailbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICocktailBarShowCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cocktailbar.ICocktailBarShowCallback";

    public static class Default implements ICocktailBarShowCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarShowCallback
        public void onShown(IBinder iBinder) throws RemoteException {
        }
    }

    void onShown(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements ICocktailBarShowCallback {
        static final int TRANSACTION_onShown = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ICocktailBarShowCallback.DESCRIPTOR);
        }

        public static ICocktailBarShowCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICocktailBarShowCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICocktailBarShowCallback)) {
                return (ICocktailBarShowCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onShown";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICocktailBarShowCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICocktailBarShowCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onShown(readStrongBinder);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICocktailBarShowCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICocktailBarShowCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarShowCallback
            public void onShown(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICocktailBarShowCallback.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
