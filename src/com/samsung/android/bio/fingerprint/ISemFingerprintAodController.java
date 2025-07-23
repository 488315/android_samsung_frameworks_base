package com.samsung.android.bio.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemFingerprintAodController extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.bio.fingerprint.ISemFingerprintAodController";

    public static class Default implements ISemFingerprintAodController {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void hideAodScreen() throws RemoteException {
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOffDozeHlpmMode() throws RemoteException {
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOffDozeMode() throws RemoteException {
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOnDozeHlpmMode() throws RemoteException {
        }

        @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
        public void turnOnDozeMode() throws RemoteException {
        }
    }

    void hideAodScreen() throws RemoteException;

    void turnOffDozeHlpmMode() throws RemoteException;

    void turnOffDozeMode() throws RemoteException;

    void turnOnDozeHlpmMode() throws RemoteException;

    void turnOnDozeMode() throws RemoteException;

    public static abstract class Stub extends Binder implements ISemFingerprintAodController {
        static final int TRANSACTION_hideAodScreen = 5;
        static final int TRANSACTION_turnOffDozeHlpmMode = 4;
        static final int TRANSACTION_turnOffDozeMode = 2;
        static final int TRANSACTION_turnOnDozeHlpmMode = 3;
        static final int TRANSACTION_turnOnDozeMode = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISemFingerprintAodController.DESCRIPTOR);
        }

        public static ISemFingerprintAodController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemFingerprintAodController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemFingerprintAodController)) {
                return (ISemFingerprintAodController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "turnOnDozeMode";
            }
            if (i == 2) {
                return "turnOffDozeMode";
            }
            if (i == 3) {
                return "turnOnDozeHlpmMode";
            }
            if (i == 4) {
                return "turnOffDozeHlpmMode";
            }
            if (i != 5) {
                return null;
            }
            return "hideAodScreen";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemFingerprintAodController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemFingerprintAodController.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                turnOnDozeMode();
            } else if (i == 2) {
                turnOffDozeMode();
            } else if (i == 3) {
                turnOnDozeHlpmMode();
            } else if (i == 4) {
                turnOffDozeHlpmMode();
            } else if (i == 5) {
                hideAodScreen();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemFingerprintAodController {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
            public void turnOffDozeMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
            public void turnOnDozeHlpmMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
            public void turnOffDozeHlpmMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.bio.fingerprint.ISemFingerprintAodController
            public void hideAodScreen() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISemFingerprintAodController.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
