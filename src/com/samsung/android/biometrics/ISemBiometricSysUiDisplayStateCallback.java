package com.samsung.android.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemBiometricSysUiDisplayStateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback";

    public static class Default implements ISemBiometricSysUiDisplayStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback
        public void onFinish(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback
        public void onStart(int i, int i2, int i3) throws RemoteException {
        }
    }

    void onFinish(int i, int i2, int i3) throws RemoteException;

    void onStart(int i, int i2, int i3) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemBiometricSysUiDisplayStateCallback {
        static final int TRANSACTION_onFinish = 2;
        static final int TRANSACTION_onStart = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
        }

        public static ISemBiometricSysUiDisplayStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemBiometricSysUiDisplayStateCallback)) {
                return (ISemBiometricSysUiDisplayStateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStart";
            }
            if (i != 2) {
                return null;
            }
            return "onFinish";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStart(readInt, readInt2, readInt3);
            } else if (i == 2) {
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFinish(readInt4, readInt5, readInt6);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemBiometricSysUiDisplayStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback
            public void onStart(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback
            public void onFinish(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
