package com.samsung.android.bio.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemFingerprintRequestCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback";

    public static class Default implements ISemFingerprintRequestCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
        public void onResult(int i) throws RemoteException {
        }
    }

    void onResult(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemFingerprintRequestCallback {
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemFingerprintRequestCallback.DESCRIPTOR);
        }

        public static ISemFingerprintRequestCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemFingerprintRequestCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemFingerprintRequestCallback)) {
                return (ISemFingerprintRequestCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onResult";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemFingerprintRequestCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemFingerprintRequestCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onResult(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemFingerprintRequestCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemFingerprintRequestCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintRequestCallback
            public void onResult(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemFingerprintRequestCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
