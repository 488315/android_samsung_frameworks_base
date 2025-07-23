package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiApSmartCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemWifiApSmartCallback";

    public static class Default implements ISemWifiApSmartCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemWifiApSmartCallback
        public void onStateChanged(int i, String str) throws RemoteException {
        }
    }

    void onStateChanged(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemWifiApSmartCallback {
        static final int TRANSACTION_onStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemWifiApSmartCallback.DESCRIPTOR);
        }

        public static ISemWifiApSmartCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemWifiApSmartCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemWifiApSmartCallback)) {
                return (ISemWifiApSmartCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemWifiApSmartCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemWifiApSmartCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onStateChanged(readInt, readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemWifiApSmartCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemWifiApSmartCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemWifiApSmartCallback
            public void onStateChanged(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemWifiApSmartCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
