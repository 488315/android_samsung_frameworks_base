package com.samsung.android.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemBiometricSysUiDisplayBrightnessCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback";

    public static class Default implements ISemBiometricSysUiDisplayBrightnessCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback
        public void onBrightnessChanged(float f) throws RemoteException {
        }
    }

    void onBrightnessChanged(float f) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemBiometricSysUiDisplayBrightnessCallback {
        static final int TRANSACTION_onBrightnessChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
        }

        public static ISemBiometricSysUiDisplayBrightnessCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemBiometricSysUiDisplayBrightnessCallback)) {
                return (ISemBiometricSysUiDisplayBrightnessCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onBrightnessChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                float readFloat = parcel.readFloat();
                parcel.enforceNoDataAvail();
                onBrightnessChanged(readFloat);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemBiometricSysUiDisplayBrightnessCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback
            public void onBrightnessChanged(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
