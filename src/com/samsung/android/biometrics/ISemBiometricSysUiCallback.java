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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemBiometricSysUiCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemBiometricSysUiCallback)) {
                return (ISemBiometricSysUiCallback) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTouchEvent(i3, i4);
            } else if (i == 2) {
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onError(i5, i6, i7);
            } else if (i == 3) {
                int i8 = parcel.readInt();
                int i9 = parcel.readInt();
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onSysUiDismissed(i8, i9, bArrCreateByteArray);
            } else if (i == 4) {
                int i10 = parcel.readInt();
                int i11 = parcel.readInt();
                int i12 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onEvent(i10, i11, i12);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onError(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onSysUiDismissed(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onEvent(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
