package com.samsung.android.cover;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICoverManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ICoverManagerCallback";

    public static class Default implements ICoverManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cover.ICoverManagerCallback
        public void coverCallback(CoverState coverState) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverManagerCallback
        public String getListenerInfo() throws RemoteException {
            return null;
        }
    }

    void coverCallback(CoverState coverState) throws RemoteException;

    String getListenerInfo() throws RemoteException;

    public static abstract class Stub extends Binder implements ICoverManagerCallback {
        static final int TRANSACTION_coverCallback = 1;
        static final int TRANSACTION_getListenerInfo = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ICoverManagerCallback.DESCRIPTOR);
        }

        public static ICoverManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICoverManagerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICoverManagerCallback)) {
                return (ICoverManagerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "coverCallback";
            }
            if (i != 2) {
                return null;
            }
            return "getListenerInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICoverManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICoverManagerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                CoverState coverState = (CoverState) parcel.readTypedObject(CoverState.CREATOR);
                parcel.enforceNoDataAvail();
                coverCallback(coverState);
            } else if (i == 2) {
                String listenerInfo = getListenerInfo();
                parcel2.writeNoException();
                parcel2.writeString(listenerInfo);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICoverManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICoverManagerCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ICoverManagerCallback
            public void coverCallback(CoverState coverState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICoverManagerCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(coverState, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverManagerCallback
            public String getListenerInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverManagerCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
