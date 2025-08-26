package com.samsung.android.cocktailbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICocktailBarStateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cocktailbar.ICocktailBarStateCallback";

    public static class Default implements ICocktailBarStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarStateCallback
        public void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo) throws RemoteException {
        }
    }

    void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements ICocktailBarStateCallback {
        static final int TRANSACTION_onCocktailBarStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ICocktailBarStateCallback.DESCRIPTOR);
        }

        public static ICocktailBarStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICocktailBarStateCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICocktailBarStateCallback)) {
                return (ICocktailBarStateCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCocktailBarStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICocktailBarStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICocktailBarStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CocktailBarStateInfo cocktailBarStateInfo = (CocktailBarStateInfo) parcel.readTypedObject(CocktailBarStateInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onCocktailBarStateChanged(cocktailBarStateInfo);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICocktailBarStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICocktailBarStateCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarStateCallback
            public void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarStateCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(cocktailBarStateInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
