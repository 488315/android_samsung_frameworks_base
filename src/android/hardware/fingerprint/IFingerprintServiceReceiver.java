package android.hardware.fingerprint;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IFingerprintServiceReceiver extends IInterface {

    public static class Default implements IFingerprintServiceReceiver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAcquired(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationFailed() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onAuthenticationSucceeded(Fingerprint fingerprint, int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onChallengeGenerated(int i, int i2, long j) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onEnrollResult(Fingerprint fingerprint, int i) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onError(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onFingerprintDetected(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onRemoved(Fingerprint fingerprint, int i) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsOverlayShown() throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerDown(int i) throws RemoteException {
        }

        @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
        public void onUdfpsPointerUp(int i) throws RemoteException {
        }
    }

    void onAcquired(int i, int i2) throws RemoteException;

    void onAuthenticationFailed() throws RemoteException;

    void onAuthenticationSucceeded(Fingerprint fingerprint, int i, boolean z) throws RemoteException;

    void onChallengeGenerated(int i, int i2, long j) throws RemoteException;

    void onEnrollResult(Fingerprint fingerprint, int i) throws RemoteException;

    void onError(int i, int i2) throws RemoteException;

    void onFingerprintDetected(int i, int i2, boolean z) throws RemoteException;

    void onRemoved(Fingerprint fingerprint, int i) throws RemoteException;

    void onUdfpsOverlayShown() throws RemoteException;

    void onUdfpsPointerDown(int i) throws RemoteException;

    void onUdfpsPointerUp(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IFingerprintServiceReceiver {
        public static final String DESCRIPTOR = "android.hardware.fingerprint.IFingerprintServiceReceiver";
        static final int TRANSACTION_onAcquired = 2;
        static final int TRANSACTION_onAuthenticationFailed = 5;
        static final int TRANSACTION_onAuthenticationSucceeded = 3;
        static final int TRANSACTION_onChallengeGenerated = 8;
        static final int TRANSACTION_onEnrollResult = 1;
        static final int TRANSACTION_onError = 6;
        static final int TRANSACTION_onFingerprintDetected = 4;
        static final int TRANSACTION_onRemoved = 7;
        static final int TRANSACTION_onUdfpsOverlayShown = 11;
        static final int TRANSACTION_onUdfpsPointerDown = 9;
        static final int TRANSACTION_onUdfpsPointerUp = 10;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFingerprintServiceReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFingerprintServiceReceiver)) {
                return (IFingerprintServiceReceiver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onEnrollResult";
                case 2:
                    return "onAcquired";
                case 3:
                    return "onAuthenticationSucceeded";
                case 4:
                    return "onFingerprintDetected";
                case 5:
                    return "onAuthenticationFailed";
                case 6:
                    return "onError";
                case 7:
                    return "onRemoved";
                case 8:
                    return "onChallengeGenerated";
                case 9:
                    return "onUdfpsPointerDown";
                case 10:
                    return "onUdfpsPointerUp";
                case 11:
                    return "onUdfpsOverlayShown";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    Fingerprint fingerprint = (Fingerprint) parcel.readTypedObject(Fingerprint.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onEnrollResult(fingerprint, readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAcquired(readInt2, readInt3);
                    return true;
                case 3:
                    Fingerprint fingerprint2 = (Fingerprint) parcel.readTypedObject(Fingerprint.CREATOR);
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(fingerprint2, readInt4, readBoolean);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onFingerprintDetected(readInt5, readInt6, readBoolean2);
                    return true;
                case 5:
                    onAuthenticationFailed();
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt7, readInt8);
                    return true;
                case 7:
                    Fingerprint fingerprint3 = (Fingerprint) parcel.readTypedObject(Fingerprint.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoved(fingerprint3, readInt9);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onChallengeGenerated(readInt10, readInt11, readLong);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUdfpsPointerDown(readInt12);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUdfpsPointerUp(readInt13);
                    return true;
                case 11:
                    onUdfpsOverlayShown();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFingerprintServiceReceiver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onEnrollResult(Fingerprint fingerprint, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fingerprint, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onAcquired(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onAuthenticationSucceeded(Fingerprint fingerprint, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fingerprint, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onFingerprintDetected(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onAuthenticationFailed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onError(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onRemoved(Fingerprint fingerprint, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(fingerprint, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onChallengeGenerated(int i, int i2, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onUdfpsPointerDown(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onUdfpsPointerUp(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.fingerprint.IFingerprintServiceReceiver
            public void onUdfpsOverlayShown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
