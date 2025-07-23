package android.security.advancedprotection;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.security.advancedprotection.IAdvancedProtectionCallback;
import java.util.List;

/* loaded from: classes3.dex */
public interface IAdvancedProtectionService extends IInterface {
    public static final String DESCRIPTOR = "android.security.advancedprotection.IAdvancedProtectionService";

    public static class Default implements IAdvancedProtectionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.advancedprotection.IAdvancedProtectionService
        public List<AdvancedProtectionFeature> getAdvancedProtectionFeatures() throws RemoteException {
            return null;
        }

        @Override // android.security.advancedprotection.IAdvancedProtectionService
        public boolean isAdvancedProtectionEnabled() throws RemoteException {
            return false;
        }

        @Override // android.security.advancedprotection.IAdvancedProtectionService
        public void logDialogShown(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.security.advancedprotection.IAdvancedProtectionService
        public void registerAdvancedProtectionCallback(IAdvancedProtectionCallback iAdvancedProtectionCallback) throws RemoteException {
        }

        @Override // android.security.advancedprotection.IAdvancedProtectionService
        public void setAdvancedProtectionEnabled(boolean z) throws RemoteException {
        }

        @Override // android.security.advancedprotection.IAdvancedProtectionService
        public void unregisterAdvancedProtectionCallback(IAdvancedProtectionCallback iAdvancedProtectionCallback) throws RemoteException {
        }
    }

    List<AdvancedProtectionFeature> getAdvancedProtectionFeatures() throws RemoteException;

    boolean isAdvancedProtectionEnabled() throws RemoteException;

    void logDialogShown(int i, int i2, boolean z) throws RemoteException;

    void registerAdvancedProtectionCallback(IAdvancedProtectionCallback iAdvancedProtectionCallback) throws RemoteException;

    void setAdvancedProtectionEnabled(boolean z) throws RemoteException;

    void unregisterAdvancedProtectionCallback(IAdvancedProtectionCallback iAdvancedProtectionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAdvancedProtectionService {
        static final int TRANSACTION_getAdvancedProtectionFeatures = 5;
        static final int TRANSACTION_isAdvancedProtectionEnabled = 1;
        static final int TRANSACTION_logDialogShown = 6;
        static final int TRANSACTION_registerAdvancedProtectionCallback = 2;
        static final int TRANSACTION_setAdvancedProtectionEnabled = 4;
        static final int TRANSACTION_unregisterAdvancedProtectionCallback = 3;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IAdvancedProtectionService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IAdvancedProtectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAdvancedProtectionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAdvancedProtectionService)) {
                return (IAdvancedProtectionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isAdvancedProtectionEnabled";
                case 2:
                    return "registerAdvancedProtectionCallback";
                case 3:
                    return "unregisterAdvancedProtectionCallback";
                case 4:
                    return "setAdvancedProtectionEnabled";
                case 5:
                    return "getAdvancedProtectionFeatures";
                case 6:
                    return "logDialogShown";
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
                parcel.enforceInterface(IAdvancedProtectionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAdvancedProtectionService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean isAdvancedProtectionEnabled = isAdvancedProtectionEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdvancedProtectionEnabled);
                    return true;
                case 2:
                    IAdvancedProtectionCallback asInterface = IAdvancedProtectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerAdvancedProtectionCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IAdvancedProtectionCallback asInterface2 = IAdvancedProtectionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterAdvancedProtectionCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAdvancedProtectionEnabled(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    List<AdvancedProtectionFeature> advancedProtectionFeatures = getAdvancedProtectionFeatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(advancedProtectionFeatures, 1);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    logDialogShown(readInt, readInt2, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAdvancedProtectionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAdvancedProtectionService.DESCRIPTOR;
            }

            @Override // android.security.advancedprotection.IAdvancedProtectionService
            public boolean isAdvancedProtectionEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedProtectionService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.advancedprotection.IAdvancedProtectionService
            public void registerAdvancedProtectionCallback(IAdvancedProtectionCallback iAdvancedProtectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedProtectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAdvancedProtectionCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.advancedprotection.IAdvancedProtectionService
            public void unregisterAdvancedProtectionCallback(IAdvancedProtectionCallback iAdvancedProtectionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedProtectionService.DESCRIPTOR);
                    obtain.writeStrongInterface(iAdvancedProtectionCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.advancedprotection.IAdvancedProtectionService
            public void setAdvancedProtectionEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedProtectionService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.advancedprotection.IAdvancedProtectionService
            public List<AdvancedProtectionFeature> getAdvancedProtectionFeatures() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedProtectionService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AdvancedProtectionFeature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.advancedprotection.IAdvancedProtectionService
            public void logDialogShown(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAdvancedProtectionService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void isAdvancedProtectionEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.QUERY_ADVANCED_PROTECTION_MODE, getCallingPid(), getCallingUid());
        }

        protected void registerAdvancedProtectionCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.QUERY_ADVANCED_PROTECTION_MODE, getCallingPid(), getCallingUid());
        }

        protected void unregisterAdvancedProtectionCallback_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.QUERY_ADVANCED_PROTECTION_MODE, getCallingPid(), getCallingUid());
        }

        protected void setAdvancedProtectionEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ADVANCED_PROTECTION_MODE, getCallingPid(), getCallingUid());
        }

        protected void getAdvancedProtectionFeatures_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ADVANCED_PROTECTION_MODE, getCallingPid(), getCallingUid());
        }

        protected void logDialogShown_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_ADVANCED_PROTECTION_MODE, getCallingPid(), getCallingUid());
        }
    }
}
