package com.samsung.android.bio.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface ISemFingerprintAodController extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.bio.fingerprint.ISemFingerprintAodController";

    void hideAodScreen() throws RemoteException;

    void turnOffDozeHlpmMode() throws RemoteException;

    void turnOffDozeMode() throws RemoteException;

    void turnOnDozeHlpmMode() throws RemoteException;

    void turnOnDozeMode() throws RemoteException;

    public static class Default implements ISemFingerprintAodController {
        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOnDozeMode() throws RemoteException {
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOffDozeMode() throws RemoteException {
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOnDozeHlpmMode() throws RemoteException {
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOffDozeHlpmMode() throws RemoteException {
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void hideAodScreen() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemFingerprintAodController {
        static final int TRANSACTION_hideAodScreen = 5;
        static final int TRANSACTION_turnOffDozeHlpmMode = 4;
        static final int TRANSACTION_turnOffDozeMode = 2;
        static final int TRANSACTION_turnOnDozeHlpmMode = 3;
        static final int TRANSACTION_turnOnDozeMode = 1;

        public Stub() {
            attachInterface(this, ISemFingerprintAodController.DESCRIPTOR);
        }

        public static ISemFingerprintAodController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemFingerprintAodController.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemFingerprintAodController)) {
                return (ISemFingerprintAodController) iin;
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
                    return "turnOnDozeMode";
                case 2:
                    return "turnOffDozeMode";
                case 3:
                    return "turnOnDozeHlpmMode";
                case 4:
                    return "turnOffDozeHlpmMode";
                case 5:
                    return "hideAodScreen";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemFingerprintAodController.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemFingerprintAodController.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    turnOnDozeMode();
                    return true;
                case 2:
                    turnOffDozeMode();
                    return true;
                case 3:
                    turnOnDozeHlpmMode();
                    return true;
                case 4:
                    turnOffDozeHlpmMode();
                    return true;
                case 5:
                    hideAodScreen();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemFingerprintAodController {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemFingerprintAodController.DESCRIPTOR;
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
            public void turnOnDozeMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
            public void turnOffDozeMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
            public void turnOnDozeHlpmMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
            public void turnOffDozeHlpmMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
            public void hideAodScreen() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
