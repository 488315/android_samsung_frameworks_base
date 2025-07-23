package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IBiometricStateListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricStateListener";

    public static class Default implements IBiometricStateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricStateListener
        public void onBiometricAction(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricStateListener
        public void onEnrollmentsChanged(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricStateListener
        public void onStateChanged(int i) throws RemoteException {
        }
    }

    void onBiometricAction(int i) throws RemoteException;

    void onEnrollmentsChanged(int i, int i2, boolean z) throws RemoteException;

    void onStateChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IBiometricStateListener {
        static final int TRANSACTION_onBiometricAction = 2;
        static final int TRANSACTION_onEnrollmentsChanged = 3;
        static final int TRANSACTION_onStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IBiometricStateListener.DESCRIPTOR);
        }

        public static IBiometricStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBiometricStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBiometricStateListener)) {
                return (IBiometricStateListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStateChanged";
            }
            if (i == 2) {
                return "onBiometricAction";
            }
            if (i != 3) {
                return null;
            }
            return "onEnrollmentsChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBiometricStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBiometricStateListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStateChanged(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onBiometricAction(readInt2);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onEnrollmentsChanged(readInt3, readInt4, readBoolean);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBiometricStateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricStateListener.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricStateListener
            public void onStateChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricStateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricStateListener
            public void onBiometricAction(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricStateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricStateListener
            public void onEnrollmentsChanged(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricStateListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
