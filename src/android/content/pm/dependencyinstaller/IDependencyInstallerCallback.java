package android.content.pm.dependencyinstaller;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IDependencyInstallerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.dependencyinstaller.IDependencyInstallerCallback";

    public static class Default implements IDependencyInstallerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.dependencyinstaller.IDependencyInstallerCallback
        public void onAllDependenciesResolved(int[] iArr) throws RemoteException {
        }

        @Override // android.content.pm.dependencyinstaller.IDependencyInstallerCallback
        public void onFailureToResolveAllDependencies() throws RemoteException {
        }
    }

    void onAllDependenciesResolved(int[] iArr) throws RemoteException;

    void onFailureToResolveAllDependencies() throws RemoteException;

    public static abstract class Stub extends Binder implements IDependencyInstallerCallback {
        static final int TRANSACTION_onAllDependenciesResolved = 1;
        static final int TRANSACTION_onFailureToResolveAllDependencies = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IDependencyInstallerCallback.DESCRIPTOR);
        }

        public static IDependencyInstallerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDependencyInstallerCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDependencyInstallerCallback)) {
                return (IDependencyInstallerCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAllDependenciesResolved";
            }
            if (i != 2) {
                return null;
            }
            return "onFailureToResolveAllDependencies";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDependencyInstallerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDependencyInstallerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int[] iArrCreateIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onAllDependenciesResolved(iArrCreateIntArray);
                parcel2.writeNoException();
            } else if (i == 2) {
                onFailureToResolveAllDependencies();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IDependencyInstallerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDependencyInstallerCallback.DESCRIPTOR;
            }

            @Override // android.content.pm.dependencyinstaller.IDependencyInstallerCallback
            public void onAllDependenciesResolved(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDependencyInstallerCallback.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.dependencyinstaller.IDependencyInstallerCallback
            public void onFailureToResolveAllDependencies() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDependencyInstallerCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
