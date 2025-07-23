package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IBiometricServiceReceiver extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricServiceReceiver";

    public static class Default implements IBiometricServiceReceiver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAcquired(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAuthenticationFailed() throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onAuthenticationSucceeded(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onDialogDismissed(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onError(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onSemAuthenticationSucceeded(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricServiceReceiver
        public void onSystemEvent(int i) throws RemoteException {
        }
    }

    void onAcquired(int i, String str) throws RemoteException;

    void onAuthenticationFailed() throws RemoteException;

    void onAuthenticationSucceeded(int i) throws RemoteException;

    void onDialogDismissed(int i) throws RemoteException;

    void onError(int i, int i2, int i3) throws RemoteException;

    void onSemAuthenticationSucceeded(int i, int i2, byte[] bArr) throws RemoteException;

    void onSystemEvent(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IBiometricServiceReceiver {
        static final int TRANSACTION_onAcquired = 4;
        static final int TRANSACTION_onAuthenticationFailed = 2;
        static final int TRANSACTION_onAuthenticationSucceeded = 1;
        static final int TRANSACTION_onDialogDismissed = 5;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onSemAuthenticationSucceeded = 7;
        static final int TRANSACTION_onSystemEvent = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, IBiometricServiceReceiver.DESCRIPTOR);
        }

        public static IBiometricServiceReceiver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBiometricServiceReceiver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBiometricServiceReceiver)) {
                return (IBiometricServiceReceiver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAuthenticationSucceeded";
                case 2:
                    return "onAuthenticationFailed";
                case 3:
                    return "onError";
                case 4:
                    return "onAcquired";
                case 5:
                    return "onDialogDismissed";
                case 6:
                    return "onSystemEvent";
                case 7:
                    return "onSemAuthenticationSucceeded";
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
                parcel.enforceInterface(IBiometricServiceReceiver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBiometricServiceReceiver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAuthenticationSucceeded(readInt);
                    return true;
                case 2:
                    onAuthenticationFailed();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt2, readInt3, readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onAcquired(readInt5, readString);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDialogDismissed(readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSystemEvent(readInt7);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onSemAuthenticationSucceeded(readInt8, readInt9, createByteArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBiometricServiceReceiver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricServiceReceiver.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricServiceReceiver
            public void onAuthenticationSucceeded(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricServiceReceiver
            public void onAuthenticationFailed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricServiceReceiver.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricServiceReceiver
            public void onError(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricServiceReceiver
            public void onAcquired(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricServiceReceiver
            public void onDialogDismissed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricServiceReceiver
            public void onSystemEvent(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricServiceReceiver
            public void onSemAuthenticationSucceeded(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricServiceReceiver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
