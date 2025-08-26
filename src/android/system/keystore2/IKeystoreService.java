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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeystoreService)) {
                return (IKeystoreService) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IKeystoreSecurityLevel securityLevel = getSecurityLevel(i3);
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
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    updateSubcomponent(keyDescriptor2, bArrCreateByteArray, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    KeyDescriptor[] keyDescriptorArrListEntries = listEntries(i4, j);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyDescriptorArrListEntries, 1);
                    return true;
                case 5:
                    KeyDescriptor keyDescriptor3 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteKey(keyDescriptor3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    KeyDescriptor keyDescriptor4 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    KeyDescriptor keyDescriptorGrant = grant(keyDescriptor4, i5, i6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyDescriptorGrant, 1);
                    return true;
                case 7:
                    KeyDescriptor keyDescriptor5 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ungrant(keyDescriptor5, i7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i8 = parcel.readInt();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int numberOfEntries = getNumberOfEntries(i8, j2);
                    parcel2.writeNoException();
                    parcel2.writeInt(numberOfEntries);
                    return true;
                case 9:
                    int i9 = parcel.readInt();
                    long j3 = parcel.readLong();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    KeyDescriptor[] keyDescriptorArrListEntriesBatched = listEntriesBatched(i9, j3, string);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyDescriptorArrListEntriesBatched, 1);
                    return true;
                case 10:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] supplementaryAttestationInfo = getSupplementaryAttestationInfo(i10);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getSecurityLevel is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IKeystoreSecurityLevel.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyEntryResponse getKeyEntry(KeyDescriptor keyDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getKeyEntry is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyEntryResponse) parcelObtain2.readTypedObject(KeyEntryResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void updateSubcomponent(KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method updateSubcomponent is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyDescriptor[] listEntries(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method listEntries is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyDescriptor[]) parcelObtain2.createTypedArray(KeyDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void deleteKey(KeyDescriptor keyDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method deleteKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyDescriptor grant(KeyDescriptor keyDescriptor, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method grant is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyDescriptor) parcelObtain2.readTypedObject(KeyDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void ungrant(KeyDescriptor keyDescriptor, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method ungrant is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public int getNumberOfEntries(int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getNumberOfEntries is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public KeyDescriptor[] listEntriesBatched(int i, long j, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method listEntriesBatched is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyDescriptor[]) parcelObtain2.createTypedArray(KeyDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public byte[] getSupplementaryAttestationInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getSupplementaryAttestationInfo is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.system.keystore2.IKeystoreService
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
