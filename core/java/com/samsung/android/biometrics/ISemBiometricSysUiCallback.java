package com.samsung.android.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemBiometricSysUiCallback extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.biometrics.ISemBiometricSysUiCallback";

    void onError(int i, int i2, int i3) throws RemoteException;

    void onEvent(int i, int i2, int i3) throws RemoteException;

    void onSysUiDismissed(int i, int i2, byte[] bArr) throws RemoteException;

    void onTouchEvent(int i, int i2) throws RemoteException;

    public static class Default implements ISemBiometricSysUiCallback {
        @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
        public void onTouchEvent(int sessionId, int event) throws RemoteException {}

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
        public void onError(int sessionId, int error, int code) throws RemoteException {}

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
        public void onSysUiDismissed(int sessionId, int reason, byte[] credentialAttestation)
                throws RemoteException {}

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
        public void onEvent(int sessionId, int event, int code) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemBiometricSysUiCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onEvent = 4;
        static final int TRANSACTION_onSysUiDismissed = 3;
        static final int TRANSACTION_onTouchEvent = 1;

        public Stub() {
            attachInterface(this, ISemBiometricSysUiCallback.DESCRIPTOR);
        }

        public static ISemBiometricSysUiCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemBiometricSysUiCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemBiometricSysUiCallback)) {
                return (ISemBiometricSysUiCallback) iin;
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
                    return "onTouchEvent";
                case 2:
                    return "onError";
                case 3:
                    return "onSysUiDismissed";
                case 4:
                    return "onEvent";
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
                data.enforceInterface(ISemBiometricSysUiCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemBiometricSysUiCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onTouchEvent(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    onError(_arg02, _arg12, _arg2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg13 = data.readInt();
                    byte[] _arg22 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onSysUiDismissed(_arg03, _arg13, _arg22);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    int _arg14 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    onEvent(_arg04, _arg14, _arg23);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemBiometricSysUiCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemBiometricSysUiCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onTouchEvent(int sessionId, int event) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(event);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onError(int sessionId, int error, int code) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(error);
                    _data.writeInt(code);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onSysUiDismissed(int sessionId, int reason, byte[] credentialAttestation)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(reason);
                    _data.writeByteArray(credentialAttestation);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiCallback
            public void onEvent(int sessionId, int event, int code) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiCallback.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(event);
                    _data.writeInt(code);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
