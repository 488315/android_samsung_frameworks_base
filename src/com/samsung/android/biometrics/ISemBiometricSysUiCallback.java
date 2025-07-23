package com.samsung.android.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemBiometricSysUiCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.biometrics.ISemBiometricSysUiCallback";

    public static class Default implements ISemBiometricSysUiCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
        public void onError(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
        public void onEvent(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
        public void onSysUiDismissed(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
        public void onTouchEvent(int i, int i2) throws RemoteException {
        }
    }

    void onError(int i, int i2, int i3) throws RemoteException;

    void onEvent(int i, int i2, int i3) throws RemoteException;

    void onSysUiDismissed(int i, int i2, byte[] bArr) throws RemoteException;

    void onTouchEvent(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemBiometricSysUiCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onEvent = 4;
        static final int TRANSACTION_onSysUiDismissed = 3;
        static final int TRANSACTION_onTouchEvent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ISemBiometricSysUiCallback.DESCRIPTOR);
        }

        public static ISemBiometricSysUiCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemBiometricSysUiCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemBiometricSysUiCallback)) {
                return (ISemBiometricSysUiCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onTouchEvent";
            }
            if (i == 2) {
                return "onError";
            }
            if (i == 3) {
                return "onSysUiDismissed";
            }
            if (i != 4) {
                return null;
            }
            return "onEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemBiometricSysUiCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemBiometricSysUiCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTouchEvent(readInt, readInt2);
            } else if (i == 2) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(readInt3, readInt4, readInt5);
            } else if (i == 3) {
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onSysUiDismissed(readInt6, readInt7, createByteArray);
            } else if (i == 4) {
                int readInt8 = parcel.readInt();
                int readInt9 = parcel.readInt();
                int readInt10 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEvent(readInt8, readInt9, readInt10);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemBiometricSysUiCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemBiometricSysUiCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onTouchEvent(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onError(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onSysUiDismissed(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onEvent(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
