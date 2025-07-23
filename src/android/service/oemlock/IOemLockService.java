package android.service.oemlock;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IOemLockService extends IInterface {

    public static class Default implements IOemLockService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.oemlock.IOemLockService
        public String getLockName() throws RemoteException {
            return null;
        }

        @Override // android.service.oemlock.IOemLockService
        public boolean isDeviceOemUnlocked() throws RemoteException {
            return false;
        }

        @Override // android.service.oemlock.IOemLockService
        public boolean isOemUnlockAllowed() throws RemoteException {
            return false;
        }

        @Override // android.service.oemlock.IOemLockService
        public boolean isOemUnlockAllowedByCarrier() throws RemoteException {
            return false;
        }

        @Override // android.service.oemlock.IOemLockService
        public boolean isOemUnlockAllowedByUser() throws RemoteException {
            return false;
        }

        @Override // android.service.oemlock.IOemLockService
        public void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) throws RemoteException {
        }

        @Override // android.service.oemlock.IOemLockService
        public void setOemUnlockAllowedByUser(boolean z) throws RemoteException {
        }
    }

    String getLockName() throws RemoteException;

    boolean isDeviceOemUnlocked() throws RemoteException;

    boolean isOemUnlockAllowed() throws RemoteException;

    boolean isOemUnlockAllowedByCarrier() throws RemoteException;

    boolean isOemUnlockAllowedByUser() throws RemoteException;

    void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) throws RemoteException;

    void setOemUnlockAllowedByUser(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IOemLockService {
        public static final String DESCRIPTOR = "android.service.oemlock.IOemLockService";
        static final int TRANSACTION_getLockName = 1;
        static final int TRANSACTION_isDeviceOemUnlocked = 7;
        static final int TRANSACTION_isOemUnlockAllowed = 6;
        static final int TRANSACTION_isOemUnlockAllowedByCarrier = 3;
        static final int TRANSACTION_isOemUnlockAllowedByUser = 5;
        static final int TRANSACTION_setOemUnlockAllowedByCarrier = 2;
        static final int TRANSACTION_setOemUnlockAllowedByUser = 4;
        private final PermissionEnforcer mEnforcer;
        static final String[] PERMISSIONS_isOemUnlockAllowed = {Manifest.permission.READ_OEM_UNLOCK_STATE, Manifest.permission.OEM_UNLOCK_STATE};
        static final String[] PERMISSIONS_isDeviceOemUnlocked = {Manifest.permission.READ_OEM_UNLOCK_STATE, Manifest.permission.OEM_UNLOCK_STATE};

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
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

        public static IOemLockService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOemLockService)) {
                return (IOemLockService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getLockName";
                case 2:
                    return "setOemUnlockAllowedByCarrier";
                case 3:
                    return "isOemUnlockAllowedByCarrier";
                case 4:
                    return "setOemUnlockAllowedByUser";
                case 5:
                    return "isOemUnlockAllowedByUser";
                case 6:
                    return "isOemUnlockAllowed";
                case 7:
                    return "isDeviceOemUnlocked";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String lockName = getLockName();
                    parcel2.writeNoException();
                    parcel2.writeString(lockName);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setOemUnlockAllowedByCarrier(readBoolean, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean isOemUnlockAllowedByCarrier = isOemUnlockAllowedByCarrier();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOemUnlockAllowedByCarrier);
                    return true;
                case 4:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOemUnlockAllowedByUser(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean isOemUnlockAllowedByUser = isOemUnlockAllowedByUser();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOemUnlockAllowedByUser);
                    return true;
                case 6:
                    boolean isOemUnlockAllowed = isOemUnlockAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOemUnlockAllowed);
                    return true;
                case 7:
                    boolean isDeviceOemUnlocked = isDeviceOemUnlocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceOemUnlocked);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IOemLockService {
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

            @Override // android.service.oemlock.IOemLockService
            public String getLockName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public boolean isOemUnlockAllowedByCarrier() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public void setOemUnlockAllowedByUser(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public boolean isOemUnlockAllowedByUser() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public boolean isOemUnlockAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public boolean isDeviceOemUnlocked() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getLockName_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void setOemUnlockAllowedByCarrier_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void isOemUnlockAllowedByCarrier_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void setOemUnlockAllowedByUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void isOemUnlockAllowedByUser_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_USER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void isOemUnlockAllowed_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_isOemUnlockAllowed, getCallingPid(), getCallingUid());
        }

        protected void isDeviceOemUnlocked_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_isDeviceOemUnlocked, getCallingPid(), getCallingUid());
        }
    }
}
