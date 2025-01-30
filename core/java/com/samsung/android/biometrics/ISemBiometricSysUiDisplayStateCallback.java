package com.samsung.android.biometrics;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemBiometricSysUiDisplayStateCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback";

    void onFinish(int i, int i2, int i3) throws RemoteException;

    void onStart(int i, int i2, int i3) throws RemoteException;

    public static class Default implements ISemBiometricSysUiDisplayStateCallback {
        @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback
        public void onStart(int stateLogical, int statePhysical, int displayType) throws RemoteException {
        }

        @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback
        public void onFinish(int stateLogical, int statePhysical, int displayType) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemBiometricSysUiDisplayStateCallback {
        static final int TRANSACTION_onFinish = 2;
        static final int TRANSACTION_onStart = 1;

        public Stub() {
            attachInterface(this, ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
        }

        public static ISemBiometricSysUiDisplayStateCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemBiometricSysUiDisplayStateCallback)) {
                return (ISemBiometricSysUiDisplayStateCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onStart";
                case 2:
                    return "onFinish";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            int _arg0 = data.readInt();
                            int _arg1 = data.readInt();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            onStart(_arg0, _arg1, _arg2);
                            return true;
                        case 2:
                            int _arg02 = data.readInt();
                            int _arg12 = data.readInt();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            onFinish(_arg02, _arg12, _arg22);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ISemBiometricSysUiDisplayStateCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback
            public void onStart(int stateLogical, int statePhysical, int displayType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
                    _data.writeInt(stateLogical);
                    _data.writeInt(statePhysical);
                    _data.writeInt(displayType);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.biometrics.ISemBiometricSysUiDisplayStateCallback
            public void onFinish(int stateLogical, int statePhysical, int displayType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemBiometricSysUiDisplayStateCallback.DESCRIPTOR);
                    _data.writeInt(stateLogical);
                    _data.writeInt(statePhysical);
                    _data.writeInt(displayType);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
