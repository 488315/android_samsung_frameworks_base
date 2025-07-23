package android.app.trust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IStrongAuthTracker extends IInterface {

    public static class Default implements IStrongAuthTracker {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.trust.IStrongAuthTracker
        public void onIsNonStrongBiometricAllowedChanged(boolean z, int i) throws RemoteException {
        }

        @Override // android.app.trust.IStrongAuthTracker
        public void onStrongAuthRequiredChanged(int i, int i2) throws RemoteException {
        }
    }

    void onIsNonStrongBiometricAllowedChanged(boolean z, int i) throws RemoteException;

    void onStrongAuthRequiredChanged(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IStrongAuthTracker {
        public static final String DESCRIPTOR = "android.app.trust.IStrongAuthTracker";
        static final int TRANSACTION_onIsNonStrongBiometricAllowedChanged = 2;
        static final int TRANSACTION_onStrongAuthRequiredChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IStrongAuthTracker asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IStrongAuthTracker)) {
                return (IStrongAuthTracker) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStrongAuthRequiredChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onIsNonStrongBiometricAllowedChanged";
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
            if (i == 1) {
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onStrongAuthRequiredChanged(readInt, readInt2);
            } else if (i == 2) {
                boolean readBoolean = parcel.readBoolean();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onIsNonStrongBiometricAllowedChanged(readBoolean, readInt3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IStrongAuthTracker {
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

            @Override // android.app.trust.IStrongAuthTracker
            public void onStrongAuthRequiredChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.IStrongAuthTracker
            public void onIsNonStrongBiometricAllowedChanged(boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
