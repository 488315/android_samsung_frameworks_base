package com.samsung.android.knox.dar.ddar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDualDarAuthProgressCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback";

    public static class Default implements IDualDarAuthProgressCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
        public void onInnerLayerUnlockFailed() throws RemoteException {
        }

        @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
        public void onInnerLayerUnlocked() throws RemoteException {
        }
    }

    void onInnerLayerUnlockFailed() throws RemoteException;

    void onInnerLayerUnlocked() throws RemoteException;

    public static abstract class Stub extends Binder implements IDualDarAuthProgressCallback {
        static final int TRANSACTION_onInnerLayerUnlockFailed = 2;
        static final int TRANSACTION_onInnerLayerUnlocked = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IDualDarAuthProgressCallback.DESCRIPTOR);
        }

        public static IDualDarAuthProgressCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDualDarAuthProgressCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDualDarAuthProgressCallback)) {
                return (IDualDarAuthProgressCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onInnerLayerUnlocked";
            }
            if (i != 2) {
                return null;
            }
            return "onInnerLayerUnlockFailed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDualDarAuthProgressCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDualDarAuthProgressCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onInnerLayerUnlocked();
            } else if (i == 2) {
                onInnerLayerUnlockFailed();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDualDarAuthProgressCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDualDarAuthProgressCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
            public void onInnerLayerUnlocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDualDarAuthProgressCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.dar.ddar.IDualDarAuthProgressCallback
            public void onInnerLayerUnlockFailed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDualDarAuthProgressCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
