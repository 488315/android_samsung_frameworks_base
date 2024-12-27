package android.security.maintenance;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.system.keystore2.KeyDescriptor;

public interface IKeystoreMaintenance extends IInterface {
    public static final String DESCRIPTOR = "android.security.maintenance.IKeystoreMaintenance";

    void clearNamespace(int i, long j) throws RemoteException;

    void deleteAllKeys() throws RemoteException;

    void earlyBootEnded() throws RemoteException;

    long[] getAppUidsAffectedBySid(int i, long j) throws RemoteException;

    byte[] getRotValue() throws RemoteException;

    void initUserSuperKeys(int i, byte[] bArr, boolean z) throws RemoteException;

    void migrateKeyNamespace(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2)
            throws RemoteException;

    void onUserAdded(int i) throws RemoteException;

    void onUserLskfRemoved(int i) throws RemoteException;

    void onUserPasswordChanged(int i, byte[] bArr) throws RemoteException;

    void onUserRemoved(int i) throws RemoteException;

    public static class Default implements IKeystoreMaintenance {
        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserAdded(int userId) throws RemoteException {}

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void initUserSuperKeys(int userId, byte[] password, boolean allowExisting)
                throws RemoteException {}

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserRemoved(int userId) throws RemoteException {}

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserLskfRemoved(int userId) throws RemoteException {}

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserPasswordChanged(int userId, byte[] password) throws RemoteException {}

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void clearNamespace(int domain, long nspace) throws RemoteException {}

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void earlyBootEnded() throws RemoteException {}

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void migrateKeyNamespace(KeyDescriptor source, KeyDescriptor destination)
                throws RemoteException {}

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void deleteAllKeys() throws RemoteException {}

        @Override // android.security.maintenance.IKeystoreMaintenance
        public long[] getAppUidsAffectedBySid(int userId, long sid) throws RemoteException {
            return null;
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public byte[] getRotValue() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKeystoreMaintenance {
        static final int TRANSACTION_clearNamespace = 6;
        static final int TRANSACTION_deleteAllKeys = 9;
        static final int TRANSACTION_earlyBootEnded = 7;
        static final int TRANSACTION_getAppUidsAffectedBySid = 10;
        static final int TRANSACTION_getRotValue = 11;
        static final int TRANSACTION_initUserSuperKeys = 2;
        static final int TRANSACTION_migrateKeyNamespace = 8;
        static final int TRANSACTION_onUserAdded = 1;
        static final int TRANSACTION_onUserLskfRemoved = 4;
        static final int TRANSACTION_onUserPasswordChanged = 5;
        static final int TRANSACTION_onUserRemoved = 3;

        public Stub() {
            attachInterface(this, IKeystoreMaintenance.DESCRIPTOR);
        }

        public static IKeystoreMaintenance asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKeystoreMaintenance.DESCRIPTOR);
            if (iin != null && (iin instanceof IKeystoreMaintenance)) {
                return (IKeystoreMaintenance) iin;
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
                    return "onUserAdded";
                case 2:
                    return "initUserSuperKeys";
                case 3:
                    return "onUserRemoved";
                case 4:
                    return "onUserLskfRemoved";
                case 5:
                    return "onUserPasswordChanged";
                case 6:
                    return "clearNamespace";
                case 7:
                    return "earlyBootEnded";
                case 8:
                    return "migrateKeyNamespace";
                case 9:
                    return "deleteAllKeys";
                case 10:
                    return "getAppUidsAffectedBySid";
                case 11:
                    return "getRotValue";
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
                data.enforceInterface(IKeystoreMaintenance.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKeystoreMaintenance.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    onUserAdded(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    byte[] _arg1 = data.createByteArray();
                    boolean _arg2 = data.readBoolean();
                    data.enforceNoDataAvail();
                    initUserSuperKeys(_arg02, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    onUserRemoved(_arg03);
                    reply.writeNoException();
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    data.enforceNoDataAvail();
                    onUserLskfRemoved(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    byte[] _arg12 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onUserPasswordChanged(_arg05, _arg12);
                    reply.writeNoException();
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    long _arg13 = data.readLong();
                    data.enforceNoDataAvail();
                    clearNamespace(_arg06, _arg13);
                    reply.writeNoException();
                    return true;
                case 7:
                    earlyBootEnded();
                    reply.writeNoException();
                    return true;
                case 8:
                    KeyDescriptor _arg07 =
                            (KeyDescriptor) data.readTypedObject(KeyDescriptor.CREATOR);
                    KeyDescriptor _arg14 =
                            (KeyDescriptor) data.readTypedObject(KeyDescriptor.CREATOR);
                    data.enforceNoDataAvail();
                    migrateKeyNamespace(_arg07, _arg14);
                    reply.writeNoException();
                    return true;
                case 9:
                    deleteAllKeys();
                    reply.writeNoException();
                    return true;
                case 10:
                    int _arg08 = data.readInt();
                    long _arg15 = data.readLong();
                    data.enforceNoDataAvail();
                    long[] _result = getAppUidsAffectedBySid(_arg08, _arg15);
                    reply.writeNoException();
                    reply.writeLongArray(_result);
                    return true;
                case 11:
                    byte[] _result2 = getRotValue();
                    reply.writeNoException();
                    reply.writeByteArray(_result2);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeystoreMaintenance {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeystoreMaintenance.DESCRIPTOR;
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserAdded(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void initUserSuperKeys(int userId, byte[] password, boolean allowExisting)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeByteArray(password);
                    _data.writeBoolean(allowExisting);
                    this.mRemote.transact(2, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserRemoved(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserLskfRemoved(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserPasswordChanged(int userId, byte[] password) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeByteArray(password);
                    this.mRemote.transact(5, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void clearNamespace(int domain, long nspace) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    _data.writeInt(domain);
                    _data.writeLong(nspace);
                    this.mRemote.transact(6, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void earlyBootEnded() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void migrateKeyNamespace(KeyDescriptor source, KeyDescriptor destination)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    _data.writeTypedObject(source, 0);
                    _data.writeTypedObject(destination, 0);
                    this.mRemote.transact(8, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void deleteAllKeys() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 32);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public long[] getAppUidsAffectedBySid(int userId, long sid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeLong(sid);
                    this.mRemote.transact(10, _data, _reply, 32);
                    _reply.readException();
                    long[] _result = _reply.createLongArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public byte[] getRotValue() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                _data.markSensitive();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 32);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
