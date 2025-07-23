package android.service.persistentdata;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPersistentDataBlockService extends IInterface {

    public static class Default implements IPersistentDataBlockService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean deactivateFactoryResetProtection(byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public int getDataBlockSize() throws RemoteException {
            return 0;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public int getFlashLockState() throws RemoteException {
            return 0;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public long getMaximumDataBlockSize() throws RemoteException {
            return 0L;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean getOemUnlockEnabled() throws RemoteException {
            return false;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public String getPersistentDataPackageName() throws RemoteException {
            return null;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean hasFrpCredentialHandle() throws RemoteException {
            return false;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean isFactoryResetProtectionActive() throws RemoteException {
            return false;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public byte[] read() throws RemoteException {
            return null;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean setFactoryResetProtectionSecret(byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public void setOemUnlockEnabled(boolean z) throws RemoteException {
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public void wipe() throws RemoteException {
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public int write(byte[] bArr) throws RemoteException {
            return 0;
        }
    }

    boolean deactivateFactoryResetProtection(byte[] bArr) throws RemoteException;

    int getDataBlockSize() throws RemoteException;

    int getFlashLockState() throws RemoteException;

    long getMaximumDataBlockSize() throws RemoteException;

    boolean getOemUnlockEnabled() throws RemoteException;

    String getPersistentDataPackageName() throws RemoteException;

    boolean hasFrpCredentialHandle() throws RemoteException;

    boolean isFactoryResetProtectionActive() throws RemoteException;

    byte[] read() throws RemoteException;

    boolean setFactoryResetProtectionSecret(byte[] bArr) throws RemoteException;

    void setOemUnlockEnabled(boolean z) throws RemoteException;

    void wipe() throws RemoteException;

    int write(byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IPersistentDataBlockService {
        public static final String DESCRIPTOR = "android.service.persistentdata.IPersistentDataBlockService";
        static final int TRANSACTION_deactivateFactoryResetProtection = 12;
        static final int TRANSACTION_getDataBlockSize = 4;
        static final int TRANSACTION_getFlashLockState = 8;
        static final int TRANSACTION_getMaximumDataBlockSize = 5;
        static final int TRANSACTION_getOemUnlockEnabled = 7;
        static final int TRANSACTION_getPersistentDataPackageName = 10;
        static final int TRANSACTION_hasFrpCredentialHandle = 9;
        static final int TRANSACTION_isFactoryResetProtectionActive = 11;
        static final int TRANSACTION_read = 2;
        static final int TRANSACTION_setFactoryResetProtectionSecret = 13;
        static final int TRANSACTION_setOemUnlockEnabled = 6;
        static final int TRANSACTION_wipe = 3;
        static final int TRANSACTION_write = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPersistentDataBlockService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPersistentDataBlockService)) {
                return (IPersistentDataBlockService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "write";
                case 2:
                    return "read";
                case 3:
                    return "wipe";
                case 4:
                    return "getDataBlockSize";
                case 5:
                    return "getMaximumDataBlockSize";
                case 6:
                    return "setOemUnlockEnabled";
                case 7:
                    return "getOemUnlockEnabled";
                case 8:
                    return "getFlashLockState";
                case 9:
                    return "hasFrpCredentialHandle";
                case 10:
                    return "getPersistentDataPackageName";
                case 11:
                    return "isFactoryResetProtectionActive";
                case 12:
                    return "deactivateFactoryResetProtection";
                case 13:
                    return "setFactoryResetProtectionSecret";
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
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int write = write(createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(write);
                    return true;
                case 2:
                    byte[] read = read();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(read);
                    return true;
                case 3:
                    wipe();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int dataBlockSize = getDataBlockSize();
                    parcel2.writeNoException();
                    parcel2.writeInt(dataBlockSize);
                    return true;
                case 5:
                    long maximumDataBlockSize = getMaximumDataBlockSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(maximumDataBlockSize);
                    return true;
                case 6:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOemUnlockEnabled(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean oemUnlockEnabled = getOemUnlockEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(oemUnlockEnabled);
                    return true;
                case 8:
                    int flashLockState = getFlashLockState();
                    parcel2.writeNoException();
                    parcel2.writeInt(flashLockState);
                    return true;
                case 9:
                    boolean hasFrpCredentialHandle = hasFrpCredentialHandle();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasFrpCredentialHandle);
                    return true;
                case 10:
                    String persistentDataPackageName = getPersistentDataPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(persistentDataPackageName);
                    return true;
                case 11:
                    boolean isFactoryResetProtectionActive = isFactoryResetProtectionActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFactoryResetProtectionActive);
                    return true;
                case 12:
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean deactivateFactoryResetProtection = deactivateFactoryResetProtection(createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deactivateFactoryResetProtection);
                    return true;
                case 13:
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean factoryResetProtectionSecret = setFactoryResetProtectionSecret(createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(factoryResetProtectionSecret);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IPersistentDataBlockService {
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

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public int write(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public byte[] read() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public void wipe() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public int getDataBlockSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public long getMaximumDataBlockSize() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public void setOemUnlockEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean getOemUnlockEnabled() throws RemoteException {
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

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public int getFlashLockState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean hasFrpCredentialHandle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public String getPersistentDataPackageName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean isFactoryResetProtectionActive() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean deactivateFactoryResetProtection(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean setFactoryResetProtectionSecret(byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
