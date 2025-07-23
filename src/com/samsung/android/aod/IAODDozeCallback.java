package com.samsung.android.aod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IAODDozeCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.aod.IAODDozeCallback";

    public static class Default implements IAODDozeCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.aod.IAODDozeCallback
        public void onAODToastRequested(AODToast aODToast) throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODDozeCallback
        public void onDozeAcquired() throws RemoteException {
        }

        @Override // com.samsung.android.aod.IAODDozeCallback
        public void onDozeReleased() throws RemoteException {
        }
    }

    void onAODToastRequested(AODToast aODToast) throws RemoteException;

    void onDozeAcquired() throws RemoteException;

    void onDozeReleased() throws RemoteException;

    public static abstract class Stub extends Binder implements IAODDozeCallback {
        static final int TRANSACTION_onAODToastRequested = 3;
        static final int TRANSACTION_onDozeAcquired = 1;
        static final int TRANSACTION_onDozeReleased = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IAODDozeCallback.DESCRIPTOR);
        }

        public static IAODDozeCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAODDozeCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAODDozeCallback)) {
                return (IAODDozeCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDozeAcquired";
            }
            if (i == 2) {
                return "onDozeReleased";
            }
            if (i != 3) {
                return null;
            }
            return "onAODToastRequested";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAODDozeCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAODDozeCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onDozeAcquired();
            } else if (i == 2) {
                onDozeReleased();
            } else if (i == 3) {
                AODToast aODToast = (AODToast) parcel.readTypedObject(AODToast.CREATOR);
                parcel.enforceNoDataAvail();
                onAODToastRequested(aODToast);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAODDozeCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAODDozeCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.aod.IAODDozeCallback
            public void onDozeAcquired() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAODDozeCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODDozeCallback
            public void onDozeReleased() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAODDozeCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.aod.IAODDozeCallback
            public void onAODToastRequested(AODToast aODToast) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAODDozeCallback.DESCRIPTOR);
                    obtain.writeTypedObject(aODToast, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
