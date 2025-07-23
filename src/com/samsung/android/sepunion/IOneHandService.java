package com.samsung.android.sepunion;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IOneHandService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.IOneHandService";

    public static class Default implements IOneHandService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sepunion.IOneHandService
        public void clickTile(String str) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IOneHandService
        public void registerListener(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IOneHandService
        public void unRegisterListener(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IOneHandService
        public void writeSetting(String str, String str2, int i) throws RemoteException {
        }
    }

    void clickTile(String str) throws RemoteException;

    void registerListener(String str, IBinder iBinder) throws RemoteException;

    void unRegisterListener(String str, IBinder iBinder) throws RemoteException;

    void writeSetting(String str, String str2, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IOneHandService {
        static final int TRANSACTION_clickTile = 3;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_unRegisterListener = 2;
        static final int TRANSACTION_writeSetting = 4;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IOneHandService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IOneHandService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOneHandService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOneHandService)) {
                return (IOneHandService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerListener";
            }
            if (i == 2) {
                return "unRegisterListener";
            }
            if (i == 3) {
                return "clickTile";
            }
            if (i != 4) {
                return null;
            }
            return "writeSetting";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOneHandService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOneHandService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                registerListener(readString, readStrongBinder);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                unRegisterListener(readString2, readStrongBinder2);
            } else if (i == 3) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                clickTile(readString3);
            } else if (i == 4) {
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                writeSetting(readString4, readString5, readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IOneHandService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOneHandService.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IOneHandService
            public void registerListener(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOneHandService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IOneHandService
            public void unRegisterListener(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOneHandService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IOneHandService
            public void clickTile(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOneHandService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IOneHandService
            public void writeSetting(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOneHandService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        protected void registerListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void unRegisterListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void clickTile_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }

        protected void writeSetting_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.WRITE_SECURE_SETTINGS, getCallingPid(), getCallingUid());
        }
    }
}
