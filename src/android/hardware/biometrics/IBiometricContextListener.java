package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IBiometricContextListener extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.IBiometricContextListener";

    public static class Default implements IBiometricContextListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.IBiometricContextListener
        public void onDisplayStateChanged(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricContextListener
        public void onFoldChanged(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.IBiometricContextListener
        public void onHardwareIgnoreTouchesChanged(boolean z) throws RemoteException {
        }
    }

    public @interface FoldState {
        public static final int FULLY_CLOSED = 3;
        public static final int FULLY_OPENED = 2;
        public static final int HALF_OPENED = 1;
        public static final int UNKNOWN = 0;
    }

    void onDisplayStateChanged(int i) throws RemoteException;

    void onFoldChanged(int i) throws RemoteException;

    void onHardwareIgnoreTouchesChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IBiometricContextListener {
        static final int TRANSACTION_onDisplayStateChanged = 2;
        static final int TRANSACTION_onFoldChanged = 1;
        static final int TRANSACTION_onHardwareIgnoreTouchesChanged = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IBiometricContextListener.DESCRIPTOR);
        }

        public static IBiometricContextListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBiometricContextListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBiometricContextListener)) {
                return (IBiometricContextListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onFoldChanged";
            }
            if (i == 2) {
                return "onDisplayStateChanged";
            }
            if (i != 3) {
                return null;
            }
            return "onHardwareIgnoreTouchesChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBiometricContextListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBiometricContextListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFoldChanged(i3);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDisplayStateChanged(i4);
            } else if (i == 3) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onHardwareIgnoreTouchesChanged(z);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBiometricContextListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBiometricContextListener.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IBiometricContextListener
            public void onFoldChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricContextListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricContextListener
            public void onDisplayStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricContextListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.IBiometricContextListener
            public void onHardwareIgnoreTouchesChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBiometricContextListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
