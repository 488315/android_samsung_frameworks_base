package com.samsung.android.wifi.p2p;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiP2pCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.p2p.ISemWifiP2pCallback";

    public static class Default implements ISemWifiP2pCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
        public void onFailure(int i) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
        public void onSuccess() throws RemoteException {
        }
    }

    void onFailure(int i) throws RemoteException;

    void onSuccess() throws RemoteException;

    public static abstract class Stub extends Binder implements ISemWifiP2pCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISemWifiP2pCallback.DESCRIPTOR);
        }

        public static ISemWifiP2pCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemWifiP2pCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemWifiP2pCallback)) {
                return (ISemWifiP2pCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i != 2) {
                return null;
            }
            return "onFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemWifiP2pCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemWifiP2pCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSuccess();
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFailure(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemWifiP2pCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiP2pCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
            public void onSuccess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.p2p.ISemWifiP2pCallback
            public void onFailure(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiP2pCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
