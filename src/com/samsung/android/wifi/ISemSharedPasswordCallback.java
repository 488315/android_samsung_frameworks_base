package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemSharedPasswordCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemSharedPasswordCallback";

    public static class Default implements ISemSharedPasswordCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
        public void onAccepted(String str, String str2) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
        public void onAvailable(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
        public void onRejected(String str) throws RemoteException {
        }
    }

    void onAccepted(String str, String str2) throws RemoteException;

    void onAvailable(boolean z) throws RemoteException;

    void onRejected(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemSharedPasswordCallback {
        static final int TRANSACTION_onAccepted = 1;
        static final int TRANSACTION_onAvailable = 3;
        static final int TRANSACTION_onRejected = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, ISemSharedPasswordCallback.DESCRIPTOR);
        }

        public static ISemSharedPasswordCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemSharedPasswordCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemSharedPasswordCallback)) {
                return (ISemSharedPasswordCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAccepted";
            }
            if (i == 2) {
                return "onRejected";
            }
            if (i != 3) {
                return null;
            }
            return "onAvailable";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemSharedPasswordCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemSharedPasswordCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onAccepted(readString, readString2);
            } else if (i == 2) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                onRejected(readString3);
            } else if (i == 3) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onAvailable(readBoolean);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemSharedPasswordCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemSharedPasswordCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
            public void onAccepted(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemSharedPasswordCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
            public void onRejected(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemSharedPasswordCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemSharedPasswordCallback
            public void onAvailable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemSharedPasswordCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
