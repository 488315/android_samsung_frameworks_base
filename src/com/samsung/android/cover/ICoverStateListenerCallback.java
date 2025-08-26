package com.samsung.android.cover;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICoverStateListenerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ICoverStateListenerCallback";

    public static class Default implements ICoverStateListenerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cover.ICoverStateListenerCallback
        public String getListenerInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cover.ICoverStateListenerCallback
        public void onCoverAttachStateChanged(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverStateListenerCallback
        public void onCoverSwitchStateChanged(boolean z) throws RemoteException {
        }
    }

    String getListenerInfo() throws RemoteException;

    void onCoverAttachStateChanged(boolean z) throws RemoteException;

    void onCoverSwitchStateChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ICoverStateListenerCallback {
        static final int TRANSACTION_getListenerInfo = 3;
        static final int TRANSACTION_onCoverAttachStateChanged = 2;
        static final int TRANSACTION_onCoverSwitchStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ICoverStateListenerCallback.DESCRIPTOR);
        }

        public static ICoverStateListenerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICoverStateListenerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICoverStateListenerCallback)) {
                return (ICoverStateListenerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCoverSwitchStateChanged";
            }
            if (i == 2) {
                return "onCoverAttachStateChanged";
            }
            if (i != 3) {
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
                parcel.enforceInterface(ICoverStateListenerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICoverStateListenerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onCoverSwitchStateChanged(z);
            } else if (i == 2) {
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onCoverAttachStateChanged(z2);
            } else if (i == 3) {
                String listenerInfo = getListenerInfo();
                parcel2.writeNoException();
                parcel2.writeString(listenerInfo);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICoverStateListenerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICoverStateListenerCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ICoverStateListenerCallback
            public void onCoverSwitchStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICoverStateListenerCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverStateListenerCallback
            public void onCoverAttachStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICoverStateListenerCallback.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverStateListenerCallback
            public String getListenerInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICoverStateListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
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
