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

    void clickTile(String str) throws RemoteException;

    void registerListener(String str, IBinder iBinder) throws RemoteException;

    void unRegisterListener(String str, IBinder iBinder) throws RemoteException;

    void writeSetting(String str, String str2, int i) throws RemoteException;

    public static class Default implements IOneHandService {
        @Override // com.samsung.android.sepunion.IOneHandService
        public void registerListener(String packageName, IBinder cb) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IOneHandService
        public void unRegisterListener(String packageName, IBinder cb) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IOneHandService
        public void clickTile(String componentName) throws RemoteException {
        }

        @Override // com.samsung.android.sepunion.IOneHandService
        public void writeSetting(String type, String key, int value) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IOneHandService {
        static final int TRANSACTION_clickTile = 3;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_unRegisterListener = 2;
        static final int TRANSACTION_writeSetting = 4;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IOneHandService.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IOneHandService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IOneHandService.DESCRIPTOR);
            if (iin != null && (iin instanceof IOneHandService)) {
                return (IOneHandService) iin;
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
                    return "registerListener";
                case 2:
                    return "unRegisterListener";
                case 3:
                    return "clickTile";
                case 4:
                    return "writeSetting";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IOneHandService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IOneHandService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    IBinder _arg1 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    registerListener(_arg0, _arg1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    IBinder _arg12 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    unRegisterListener(_arg02, _arg12);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    clickTile(_arg03);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    String _arg13 = data.readString();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    writeSetting(_arg04, _arg13, _arg2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IOneHandService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOneHandService.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.IOneHandService
            public void registerListener(String packageName, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOneHandService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IOneHandService
            public void unRegisterListener(String packageName, IBinder cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOneHandService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStrongBinder(cb);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IOneHandService
            public void clickTile(String componentName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOneHandService.DESCRIPTOR);
                    _data.writeString(componentName);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.sepunion.IOneHandService
            public void writeSetting(String type, String key, int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IOneHandService.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeString(key);
                    _data.writeInt(value);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
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

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
