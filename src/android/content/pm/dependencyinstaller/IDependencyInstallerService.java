package android.content.pm.dependencyinstaller;

import android.content.pm.SharedLibraryInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IDependencyInstallerService extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.dependencyinstaller.IDependencyInstallerService";

    public static class Default implements IDependencyInstallerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.dependencyinstaller.IDependencyInstallerService
        public void onDependenciesRequired(List<SharedLibraryInfo> list, DependencyInstallerCallback dependencyInstallerCallback) throws RemoteException {
        }
    }

    void onDependenciesRequired(List<SharedLibraryInfo> list, DependencyInstallerCallback dependencyInstallerCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IDependencyInstallerService {
        static final int TRANSACTION_onDependenciesRequired = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IDependencyInstallerService.DESCRIPTOR);
        }

        public static IDependencyInstallerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDependencyInstallerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDependencyInstallerService)) {
                return (IDependencyInstallerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onDependenciesRequired";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDependencyInstallerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDependencyInstallerService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(SharedLibraryInfo.CREATOR);
                DependencyInstallerCallback dependencyInstallerCallback = (DependencyInstallerCallback) parcel.readTypedObject(DependencyInstallerCallback.CREATOR);
                parcel.enforceNoDataAvail();
                onDependenciesRequired(createTypedArrayList, dependencyInstallerCallback);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDependencyInstallerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDependencyInstallerService.DESCRIPTOR;
            }

            @Override // android.content.pm.dependencyinstaller.IDependencyInstallerService
            public void onDependenciesRequired(List<SharedLibraryInfo> list, DependencyInstallerCallback dependencyInstallerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IDependencyInstallerService.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(dependencyInstallerCallback, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
