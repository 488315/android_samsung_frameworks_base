package android.security.maintenance;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.system.keystore2.KeyDescriptor;

/* loaded from: classes3.dex */
public interface IKeystoreMaintenance extends IInterface {
    public static final String DESCRIPTOR = "android.security.maintenance.IKeystoreMaintenance";

    public static class Default implements IKeystoreMaintenance {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void clearNamespace(int i, long j) throws RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void deleteAllKeys() throws RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void earlyBootEnded() throws RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public long[] getAppUidsAffectedBySid(int i, long j) throws RemoteException {
            return null;
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public byte[] getRotValue() throws RemoteException {
            return null;
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void initUserSuperKeys(int i, byte[] bArr, boolean z) throws RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void migrateKeyNamespace(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2) throws RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserAdded(int i) throws RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserLskfRemoved(int i) throws RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserRemoved(int i) throws RemoteException {
        }
    }

    void clearNamespace(int i, long j) throws RemoteException;

    void deleteAllKeys() throws RemoteException;

    void earlyBootEnded() throws RemoteException;

    long[] getAppUidsAffectedBySid(int i, long j) throws RemoteException;

    byte[] getRotValue() throws RemoteException;

    void initUserSuperKeys(int i, byte[] bArr, boolean z) throws RemoteException;

    void migrateKeyNamespace(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2) throws RemoteException;

    void onUserAdded(int i) throws RemoteException;

    void onUserLskfRemoved(int i) throws RemoteException;

    void onUserRemoved(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IKeystoreMaintenance {
        static final int TRANSACTION_clearNamespace = 5;
        static final int TRANSACTION_deleteAllKeys = 8;
        static final int TRANSACTION_earlyBootEnded = 6;
        static final int TRANSACTION_getAppUidsAffectedBySid = 9;
        static final int TRANSACTION_getRotValue = 10;
        static final int TRANSACTION_initUserSuperKeys = 2;
        static final int TRANSACTION_migrateKeyNamespace = 7;
        static final int TRANSACTION_onUserAdded = 1;
        static final int TRANSACTION_onUserLskfRemoved = 4;
        static final int TRANSACTION_onUserRemoved = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, IKeystoreMaintenance.DESCRIPTOR);
        }

        public static IKeystoreMaintenance asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKeystoreMaintenance.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeystoreMaintenance)) {
                return (IKeystoreMaintenance) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUserAdded";
                case 2:
                    return "initUserSuperKeys";
                case 3:
                    return "onUserRemoved";
                case 4:
                    return "onUserLskfRemoved";
                case 5:
                    return "clearNamespace";
                case 6:
                    return "earlyBootEnded";
                case 7:
                    return "migrateKeyNamespace";
                case 8:
                    return "deleteAllKeys";
                case 9:
                    return "getAppUidsAffectedBySid";
                case 10:
                    return "getRotValue";
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
                parcel.enforceInterface(IKeystoreMaintenance.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKeystoreMaintenance.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserAdded(readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    initUserSuperKeys(readInt2, createByteArray, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserRemoved(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserLskfRemoved(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    clearNamespace(readInt5, readLong);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    earlyBootEnded();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    KeyDescriptor keyDescriptor = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    KeyDescriptor keyDescriptor2 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    migrateKeyNamespace(keyDescriptor, keyDescriptor2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    deleteAllKeys();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long[] appUidsAffectedBySid = getAppUidsAffectedBySid(readInt6, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(appUidsAffectedBySid);
                    return true;
                case 10:
                    byte[] rotValue = getRotValue();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(rotValue);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IKeystoreMaintenance {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeystoreMaintenance.DESCRIPTOR;
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserAdded(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void initUserSuperKeys(int i, byte[] bArr, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserRemoved(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserLskfRemoved(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void clearNamespace(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void earlyBootEnded() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void migrateKeyNamespace(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeTypedObject(keyDescriptor2, 0);
                    this.mRemote.transact(7, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void deleteAllKeys() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public long[] getAppUidsAffectedBySid(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public byte[] getRotValue() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                obtain.markSensitive();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKeystoreMaintenance.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
