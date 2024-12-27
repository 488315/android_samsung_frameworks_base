package com.samsung.android.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISemBiometricSysUiDisplayBrightnessCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback";

    void onBrightnessChanged(float f) throws RemoteException;

    public static class Default implements ISemBiometricSysUiDisplayBrightnessCallback {
        @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback
        public void onBrightnessChanged(float v) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder
            implements ISemBiometricSysUiDisplayBrightnessCallback {
        static final int TRANSACTION_onBrightnessChanged = 1;

        public Stub() {
            attachInterface(this, ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
        }

        public static ISemBiometricSysUiDisplayBrightnessCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin =
                    obj.queryLocalInterface(ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemBiometricSysUiDisplayBrightnessCallback)) {
                return (ISemBiometricSysUiDisplayBrightnessCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onBrightnessChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    float _arg0 = data.readFloat();
                    data.enforceNoDataAvail();
                    onBrightnessChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemBiometricSysUiDisplayBrightnessCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayBrightnessCallback
            public void onBrightnessChanged(float v) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(
                            ISemBiometricSysUiDisplayBrightnessCallback.DESCRIPTOR);
                    _data.writeFloat(v);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
