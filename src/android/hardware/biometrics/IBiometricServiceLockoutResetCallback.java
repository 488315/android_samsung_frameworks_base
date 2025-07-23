package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IBiometricServiceLockoutResetCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricServiceLockoutResetCallback";

    public static class Default implements IBiometricServiceLockoutResetCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricServiceLockoutResetCallback
        public void onLockoutReset(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        }
    }

    void onLockoutReset(int i, IRemoteCallback iRemoteCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IBiometricServiceLockoutResetCallback {
        static final int TRANSACTION_onLockoutReset = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IBiometricServiceLockoutResetCallback.DESCRIPTOR);
        }

        public static IBiometricServiceLockoutResetCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBiometricServiceLockoutResetCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBiometricServiceLockoutResetCallback)) {
                return (IBiometricServiceLockoutResetCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onLockoutReset";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBiometricServiceLockoutResetCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBiometricServiceLockoutResetCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                IRemoteCallback asInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onLockoutReset(readInt, asInterface);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBiometricServiceLockoutResetCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricServiceLockoutResetCallback.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricServiceLockoutResetCallback
            public void onLockoutReset(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricServiceLockoutResetCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRemoteCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
