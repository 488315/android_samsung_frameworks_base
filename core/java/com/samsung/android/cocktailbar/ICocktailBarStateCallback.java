package com.samsung.android.cocktailbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ICocktailBarStateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cocktailbar.ICocktailBarStateCallback";

    void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo) throws RemoteException;

    public static class Default implements ICocktailBarStateCallback {
        @Override // com.samsung.android.cocktailbar.ICocktailBarStateCallback
        public void onCocktailBarStateChanged(CocktailBarStateInfo stateInfo) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICocktailBarStateCallback {
        static final int TRANSACTION_onCocktailBarStateChanged = 1;

        public Stub() {
            attachInterface(this, ICocktailBarStateCallback.DESCRIPTOR);
        }

        public static ICocktailBarStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICocktailBarStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ICocktailBarStateCallback)) {
                return (ICocktailBarStateCallback) iin;
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
                    return "onCocktailBarStateChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICocktailBarStateCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICocktailBarStateCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    CocktailBarStateInfo _arg0 = (CocktailBarStateInfo) data.readTypedObject(CocktailBarStateInfo.CREATOR);
                    data.enforceNoDataAvail();
                    onCocktailBarStateChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICocktailBarStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICocktailBarStateCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarStateCallback
            public void onCocktailBarStateChanged(CocktailBarStateInfo stateInfo) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICocktailBarStateCallback.DESCRIPTOR);
                    _data.writeTypedObject(stateInfo, 0);
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
