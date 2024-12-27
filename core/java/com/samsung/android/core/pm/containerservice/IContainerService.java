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
    public static final String DESCRIPTOR =
            "com.samsung.android.core.pm.containerservice.IContainerService";

    String copyPackageToContainer(String str, String str2, String str3, boolean z, String str4)
            throws RemoteException;

    void doForceGC() throws RemoteException;

    public static class Default implements IContainerService {
        @Override // com.samsung.android.core.pm.containerservice.IContainerService
        public String copyPackageToContainer(
                String packagePath,
                String containerId,
                String key,
                boolean isExternal,
                String abiOverride)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.core.pm.containerservice.IContainerService
        public void doForceGC() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IContainerService {
        static final int TRANSACTION_copyPackageToContainer = 1;
        static final int TRANSACTION_doForceGC = 2;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IContainerService.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IContainerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IContainerService.DESCRIPTOR);
            if (iin != null && (iin instanceof IContainerService)) {
                return (IContainerService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "copyPackageToContainer";
                case 2:
                    return "doForceGC";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IContainerService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IContainerService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    String _arg2 = data.readString();
                    boolean _arg3 = data.readBoolean();
                    String _arg4 = data.readString();
                    data.enforceNoDataAvail();
                    String _result = copyPackageToContainer(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                case 2:
                    doForceGC();
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IContainerService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContainerService.DESCRIPTOR;
            }

            @Override // com.samsung.android.core.pm.containerservice.IContainerService
            public String copyPackageToContainer(
                    String packagePath,
                    String containerId,
                    String key,
                    boolean isExternal,
                    String abiOverride)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IContainerService.DESCRIPTOR);
                    _data.writeString(packagePath);
                    _data.writeString(containerId);
                    _data.writeString(key);
                    _data.writeBoolean(isExternal);
                    _data.writeString(abiOverride);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.core.pm.containerservice.IContainerService
            public void doForceGC() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IContainerService.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void copyPackageToContainer_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.COPY_PROTECTED_DATA, getCallingPid(), getCallingUid());
        }

        protected void doForceGC_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.COPY_PROTECTED_DATA, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
