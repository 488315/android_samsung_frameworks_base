package android.os;

import android.Manifest;
import android.app.ActivityThread;

/* loaded from: classes3.dex */
public interface ISystemUpdateManager extends IInterface {

    public static class Default implements ISystemUpdateManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ISystemUpdateManager
        public Bundle retrieveSystemUpdateInfo() throws RemoteException {
            return null;
        }

        @Override // android.os.ISystemUpdateManager
        public void updateSystemUpdateInfo(PersistableBundle persistableBundle) throws RemoteException {
        }
    }

    Bundle retrieveSystemUpdateInfo() throws RemoteException;

    void updateSystemUpdateInfo(PersistableBundle persistableBundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ISystemUpdateManager {
        public static final String DESCRIPTOR = "android.os.ISystemUpdateManager";
        static final int TRANSACTION_retrieveSystemUpdateInfo = 1;
        static final int TRANSACTION_updateSystemUpdateInfo = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static ISystemUpdateManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISystemUpdateManager)) {
                return (ISystemUpdateManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "retrieveSystemUpdateInfo";
            }
            if (i != 2) {
                return null;
            }
            return "updateSystemUpdateInfo";
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
                Bundle retrieveSystemUpdateInfo = retrieveSystemUpdateInfo();
                parcel2.writeNoException();
                parcel2.writeTypedObject(retrieveSystemUpdateInfo, 1);
            } else if (i == 2) {
                PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                parcel.enforceNoDataAvail();
                updateSystemUpdateInfo(persistableBundle);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISystemUpdateManager {
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

            @Override // android.os.ISystemUpdateManager
            public Bundle retrieveSystemUpdateInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISystemUpdateManager
            public void updateSystemUpdateInfo(PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void updateSystemUpdateInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.RECOVERY, getCallingPid(), getCallingUid());
        }
    }
}
