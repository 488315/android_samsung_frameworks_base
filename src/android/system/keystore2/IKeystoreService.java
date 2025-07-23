package android.system.keystore2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.system.keystore2.IKeystoreSecurityLevel;

/* loaded from: classes3.dex */
public interface IKeystoreService extends IInterface {
    public static final String DESCRIPTOR = "android$system$keystore2$IKeystoreService".replace('$', '.');
    public static final String HASH = "98d815116c190250e9e5a1d9182cea8126fd0e97";
    public static final int VERSION = 5;

    void deleteKey(KeyDescriptor keyDescriptor) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    KeyEntryResponse getKeyEntry(KeyDescriptor keyDescriptor) throws RemoteException;

    int getNumberOfEntries(int i, long j) throws RemoteException;

    IKeystoreSecurityLevel getSecurityLevel(int i) throws RemoteException;

    byte[] getSupplementaryAttestationInfo(int i) throws RemoteException;

    KeyDescriptor grant(KeyDescriptor keyDescriptor, int i, int i2) throws RemoteException;

    @Deprecated
    KeyDescriptor[] listEntries(int i, long j) throws RemoteException;

    KeyDescriptor[] listEntriesBatched(int i, long j, String str) throws RemoteException;

    void ungrant(KeyDescriptor keyDescriptor, int i) throws RemoteException;

    void updateSubcomponent(KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2) throws RemoteException;

    public static class Default implements IKeystoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public void deleteKey(KeyDescriptor keyDescriptor) throws RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.system.keystore2.IKeystoreService
        public KeyEntryResponse getKeyEntry(KeyDescriptor keyDescriptor) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public int getNumberOfEntries(int i, long j) throws RemoteException {
            return 0;
        }

        @Override // android.system.keystore2.IKeystoreService
        public IKeystoreSecurityLevel getSecurityLevel(int i) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public byte[] getSupplementaryAttestationInfo(int i) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public KeyDescriptor grant(KeyDescriptor keyDescriptor, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public KeyDescriptor[] listEntries(int i, long j) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public KeyDescriptor[] listEntriesBatched(int i, long j, String str) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public void ungrant(KeyDescriptor keyDescriptor, int i) throws RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreService
        public void updateSubcomponent(KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2) throws RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreService
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IKeystoreService {
        static final int TRANSACTION_deleteKey = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getKeyEntry = 2;
        static final int TRANSACTION_getNumberOfEntries = 8;
        static final int TRANSACTION_getSecurityLevel = 1;
        static final int TRANSACTION_getSupplementaryAttestationInfo = 10;
        static final int TRANSACTION_grant = 6;
        static final int TRANSACTION_listEntries = 4;
        static final int TRANSACTION_listEntriesBatched = 9;
        static final int TRANSACTION_ungrant = 7;
        static final int TRANSACTION_updateSubcomponent = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IKeystoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IKeystoreService)) {
                return (IKeystoreService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSecurityLevel";
                case 2:
                    return "getKeyEntry";
                case 3:
                    return "updateSubcomponent";
                case 4:
                    return "listEntries";
                case 5:
                    return "deleteKey";
                case 6:
                    return "grant";
                case 7:
                    return "ungrant";
                case 8:
                    return "getNumberOfEntries";
                case 9:
                    return "listEntriesBatched";
                case 10:
                    return "getSupplementaryAttestationInfo";
                default:
                    switch (i) {
                        case 16777214:
                            return "getInterfaceHash";
                        case 16777215:
                            return "getInterfaceVersion";
                        default:
                            return null;
                    }
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IKeystoreSecurityLevel securityLevel = getSecurityLevel(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(securityLevel);
                    return true;
                case 2:
                    KeyDescriptor keyDescriptor = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    KeyEntryResponse keyEntry = getKeyEntry(keyDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyEntry, 1);
                    return true;
                case 3:
                    KeyDescriptor keyDescriptor2 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    updateSubcomponent(keyDescriptor2, createByteArray, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    KeyDescriptor[] listEntries = listEntries(readInt2, readLong);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listEntries, 1);
                    return true;
                case 5:
                    KeyDescriptor keyDescriptor3 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteKey(keyDescriptor3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    KeyDescriptor keyDescriptor4 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    KeyDescriptor grant = grant(keyDescriptor4, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(grant, 1);
                    return true;
                case 7:
                    KeyDescriptor keyDescriptor5 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ungrant(keyDescriptor5, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int numberOfEntries = getNumberOfEntries(readInt6, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeInt(numberOfEntries);
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeyDescriptor[] listEntriesBatched = listEntriesBatched(readInt7, readLong3, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listEntriesBatched, 1);
                    return true;
                case 10:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] supplementaryAttestationInfo = getSupplementaryAttestationInfo(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(supplementaryAttestationInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IKeystoreService {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.system.keystore2.IKeystoreService
            public IKeystoreSecurityLevel getSecurityLevel(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSecurityLevel is unimplemented.");
                    }
                    obtain2.readException();
                    return IKeystoreSecurityLevel.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyEntryResponse getKeyEntry(KeyDescriptor keyDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getKeyEntry is unimplemented.");
                    }
                    obtain2.readException();
                    return (KeyEntryResponse) obtain2.readTypedObject(KeyEntryResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void updateSubcomponent(KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method updateSubcomponent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyDescriptor[] listEntries(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method listEntries is unimplemented.");
                    }
                    obtain2.readException();
                    return (KeyDescriptor[]) obtain2.createTypedArray(KeyDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void deleteKey(KeyDescriptor keyDescriptor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method deleteKey is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyDescriptor grant(KeyDescriptor keyDescriptor, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method grant is unimplemented.");
                    }
                    obtain2.readException();
                    return (KeyDescriptor) obtain2.readTypedObject(KeyDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void ungrant(KeyDescriptor keyDescriptor, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method ungrant is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public int getNumberOfEntries(int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getNumberOfEntries is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyDescriptor[] listEntriesBatched(int i, long j, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method listEntriesBatched is unimplemented.");
                    }
                    obtain2.readException();
                    return (KeyDescriptor[]) obtain2.createTypedArray(KeyDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public byte[] getSupplementaryAttestationInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSupplementaryAttestationInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.system.keystore2.IKeystoreService
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
