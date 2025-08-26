package android.hardware.biometrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ITestSessionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.biometrics.ITestSessionCallback";

    public static class Default implements ITestSessionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.biometrics.ITestSessionCallback
        public void onCleanupFinished(int i) throws RemoteException {
        }

        @Override // android.hardware.biometrics.ITestSessionCallback
        public void onCleanupStarted(int i) throws RemoteException {
        }
    }

    void onCleanupFinished(int i) throws RemoteException;

    void onCleanupStarted(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ITestSessionCallback {
        static final int TRANSACTION_onCleanupFinished = 2;
        static final int TRANSACTION_onCleanupStarted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ITestSessionCallback.DESCRIPTOR);
        }

        public static ITestSessionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITestSessionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITestSessionCallback)) {
                return (ITestSessionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCleanupStarted";
            }
            if (i != 2) {
                return null;
            }
            return "onCleanupFinished";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITestSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITestSessionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCleanupStarted(i3);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCleanupFinished(i4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITestSessionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITestSessionCallback.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.ITestSessionCallback
            public void onCleanupStarted(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITestSessionCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.biometrics.ITestSessionCallback
            public void onCleanupFinished(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITestSessionCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
