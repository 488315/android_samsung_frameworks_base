package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IBiometricEnabledOnKeyguardCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback";

    public static class Default implements IBiometricEnabledOnKeyguardCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback
        public void onChanged(boolean z, int i, int i2) throws RemoteException {
        }
    }

    void onChanged(boolean z, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IBiometricEnabledOnKeyguardCallback {
        static final int TRANSACTION_onChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
        }

        public static IBiometricEnabledOnKeyguardCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBiometricEnabledOnKeyguardCallback)) {
                return (IBiometricEnabledOnKeyguardCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onChanged(readBoolean, readInt, readInt2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IBiometricEnabledOnKeyguardCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricEnabledOnKeyguardCallback.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback
            public void onChanged(boolean z, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IBiometricEnabledOnKeyguardCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
