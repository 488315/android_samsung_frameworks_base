package com.samsung.android.core.pm.containerservice;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IContainerService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.core.pm.containerservice.IContainerService";

    public static class Default implements IContainerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.core.pm.containerservice.IContainerService
        public String copyPackageToContainer(String str, String str2, String str3, boolean z, String str4) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.core.pm.containerservice.IContainerService
        public void doForceGC() throws RemoteException {
        }
    }

    String copyPackageToContainer(String str, String str2, String str3, boolean z, String str4) throws RemoteException;

    void doForceGC() throws RemoteException;

    public static abstract class Stub extends Binder implements IContainerService {
        static final int TRANSACTION_copyPackageToContainer = 1;
        static final int TRANSACTION_doForceGC = 2;
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
            attachInterface(this, IContainerService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IContainerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IContainerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContainerService)) {
                return (IContainerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "copyPackageToContainer";
            }
            if (i != 2) {
                return null;
            }
            return "doForceGC";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IContainerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IContainerService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                String readString3 = parcel.readString();
                boolean readBoolean = parcel.readBoolean();
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                String copyPackageToContainer = copyPackageToContainer(readString, readString2, readString3, readBoolean, readString4);
                parcel2.writeNoException();
                parcel2.writeString(copyPackageToContainer);
            } else if (i == 2) {
                doForceGC();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IContainerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContainerService.DESCRIPTOR;
            }

            @Override // com.samsung.android.core.pm.containerservice.IContainerService
            public String copyPackageToContainer(String str, String str2, String str3, boolean z, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IContainerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    obtain.writeString(str4);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.core.pm.containerservice.IContainerService
            public void doForceGC() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IContainerService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void copyPackageToContainer_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.COPY_PROTECTED_DATA, getCallingPid(), getCallingUid());
        }

        protected void doForceGC_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.COPY_PROTECTED_DATA, getCallingPid(), getCallingUid());
        }
    }
}
