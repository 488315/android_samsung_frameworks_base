package android.security.identity;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.identity.IWritableCredential;

/* loaded from: classes3.dex */
public interface ICredential extends IInterface {
    public static final String DESCRIPTOR = "android.security.identity.ICredential";
    public static final int STATUS_NOT_IN_REQUEST_MESSAGE = 3;
    public static final int STATUS_NOT_REQUESTED = 2;
    public static final int STATUS_NO_ACCESS_CONTROL_PROFILES = 6;
    public static final int STATUS_NO_SUCH_ENTRY = 1;
    public static final int STATUS_OK = 0;
    public static final int STATUS_READER_AUTHENTICATION_FAILED = 5;
    public static final int STATUS_USER_AUTHENTICATION_FAILED = 4;

    public static class Default implements ICredential {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.identity.ICredential
        public byte[] createEphemeralKeyPair() throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public byte[] deleteCredential() throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public byte[] deleteWithChallenge(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public AuthKeyParcel[] getAuthKeysNeedingCertification() throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public long[] getAuthenticationDataExpirations() throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public int[] getAuthenticationDataUsageCount() throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public byte[] getCredentialKeyCertificateChain() throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public GetEntriesResultParcel getEntries(byte[] bArr, RequestNamespaceParcel[] requestNamespaceParcelArr, byte[] bArr2, byte[] bArr3, boolean z, boolean z2, boolean z3) throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public byte[] proveOwnership(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.security.identity.ICredential
        public long selectAuthKey(boolean z, boolean z2, boolean z3) throws RemoteException {
            return 0L;
        }

        @Override // android.security.identity.ICredential
        public void setAvailableAuthenticationKeys(int i, int i2, long j) throws RemoteException {
        }

        @Override // android.security.identity.ICredential
        public void setReaderEphemeralPublicKey(byte[] bArr) throws RemoteException {
        }

        @Override // android.security.identity.ICredential
        public void storeStaticAuthenticationData(AuthKeyParcel authKeyParcel, byte[] bArr) throws RemoteException {
        }

        @Override // android.security.identity.ICredential
        public void storeStaticAuthenticationDataWithExpiration(AuthKeyParcel authKeyParcel, long j, byte[] bArr) throws RemoteException {
        }

        @Override // android.security.identity.ICredential
        public IWritableCredential update() throws RemoteException {
            return null;
        }
    }

    byte[] createEphemeralKeyPair() throws RemoteException;

    byte[] deleteCredential() throws RemoteException;

    byte[] deleteWithChallenge(byte[] bArr) throws RemoteException;

    AuthKeyParcel[] getAuthKeysNeedingCertification() throws RemoteException;

    long[] getAuthenticationDataExpirations() throws RemoteException;

    int[] getAuthenticationDataUsageCount() throws RemoteException;

    byte[] getCredentialKeyCertificateChain() throws RemoteException;

    GetEntriesResultParcel getEntries(byte[] bArr, RequestNamespaceParcel[] requestNamespaceParcelArr, byte[] bArr2, byte[] bArr3, boolean z, boolean z2, boolean z3) throws RemoteException;

    byte[] proveOwnership(byte[] bArr) throws RemoteException;

    long selectAuthKey(boolean z, boolean z2, boolean z3) throws RemoteException;

    void setAvailableAuthenticationKeys(int i, int i2, long j) throws RemoteException;

    void setReaderEphemeralPublicKey(byte[] bArr) throws RemoteException;

    void storeStaticAuthenticationData(AuthKeyParcel authKeyParcel, byte[] bArr) throws RemoteException;

    void storeStaticAuthenticationDataWithExpiration(AuthKeyParcel authKeyParcel, long j, byte[] bArr) throws RemoteException;

    IWritableCredential update() throws RemoteException;

    public static abstract class Stub extends Binder implements ICredential {
        static final int TRANSACTION_createEphemeralKeyPair = 1;
        static final int TRANSACTION_deleteCredential = 3;
        static final int TRANSACTION_deleteWithChallenge = 4;
        static final int TRANSACTION_getAuthKeysNeedingCertification = 10;
        static final int TRANSACTION_getAuthenticationDataExpirations = 14;
        static final int TRANSACTION_getAuthenticationDataUsageCount = 13;
        static final int TRANSACTION_getCredentialKeyCertificateChain = 6;
        static final int TRANSACTION_getEntries = 8;
        static final int TRANSACTION_proveOwnership = 5;
        static final int TRANSACTION_selectAuthKey = 7;
        static final int TRANSACTION_setAvailableAuthenticationKeys = 9;
        static final int TRANSACTION_setReaderEphemeralPublicKey = 2;
        static final int TRANSACTION_storeStaticAuthenticationData = 11;
        static final int TRANSACTION_storeStaticAuthenticationDataWithExpiration = 12;
        static final int TRANSACTION_update = 15;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }

        public Stub() {
            attachInterface(this, ICredential.DESCRIPTOR);
        }

        public static ICredential asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICredential.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICredential)) {
                return (ICredential) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createEphemeralKeyPair";
                case 2:
                    return "setReaderEphemeralPublicKey";
                case 3:
                    return "deleteCredential";
                case 4:
                    return "deleteWithChallenge";
                case 5:
                    return "proveOwnership";
                case 6:
                    return "getCredentialKeyCertificateChain";
                case 7:
                    return "selectAuthKey";
                case 8:
                    return "getEntries";
                case 9:
                    return "setAvailableAuthenticationKeys";
                case 10:
                    return "getAuthKeysNeedingCertification";
                case 11:
                    return "storeStaticAuthenticationData";
                case 12:
                    return "storeStaticAuthenticationDataWithExpiration";
                case 13:
                    return "getAuthenticationDataUsageCount";
                case 14:
                    return "getAuthenticationDataExpirations";
                case 15:
                    return "update";
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
                parcel.enforceInterface(ICredential.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICredential.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte[] bArrCreateEphemeralKeyPair = createEphemeralKeyPair();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrCreateEphemeralKeyPair);
                    return true;
                case 2:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setReaderEphemeralPublicKey(bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    byte[] bArrDeleteCredential = deleteCredential();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrDeleteCredential);
                    return true;
                case 4:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrDeleteWithChallenge = deleteWithChallenge(bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrDeleteWithChallenge);
                    return true;
                case 5:
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrProveOwnership = proveOwnership(bArrCreateByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrProveOwnership);
                    return true;
                case 6:
                    byte[] credentialKeyCertificateChain = getCredentialKeyCertificateChain();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(credentialKeyCertificateChain);
                    return true;
                case 7:
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    long jSelectAuthKey = selectAuthKey(z, z2, z3);
                    parcel2.writeNoException();
                    parcel2.writeLong(jSelectAuthKey);
                    return true;
                case 8:
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    RequestNamespaceParcel[] requestNamespaceParcelArr = (RequestNamespaceParcel[]) parcel.createTypedArray(RequestNamespaceParcel.CREATOR);
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    GetEntriesResultParcel entries = getEntries(bArrCreateByteArray4, requestNamespaceParcelArr, bArrCreateByteArray5, bArrCreateByteArray6, z4, z5, z6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(entries, 1);
                    return true;
                case 9:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAvailableAuthenticationKeys(i3, i4, j);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    AuthKeyParcel[] authKeysNeedingCertification = getAuthKeysNeedingCertification();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(authKeysNeedingCertification, 1);
                    return true;
                case 11:
                    AuthKeyParcel authKeyParcel = (AuthKeyParcel) parcel.readTypedObject(AuthKeyParcel.CREATOR);
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    storeStaticAuthenticationData(authKeyParcel, bArrCreateByteArray7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    AuthKeyParcel authKeyParcel2 = (AuthKeyParcel) parcel.readTypedObject(AuthKeyParcel.CREATOR);
                    long j2 = parcel.readLong();
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    storeStaticAuthenticationDataWithExpiration(authKeyParcel2, j2, bArrCreateByteArray8);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] authenticationDataUsageCount = getAuthenticationDataUsageCount();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(authenticationDataUsageCount);
                    return true;
                case 14:
                    long[] authenticationDataExpirations = getAuthenticationDataExpirations();
                    parcel2.writeNoException();
                    parcel2.writeLongArray(authenticationDataExpirations);
                    return true;
                case 15:
                    IWritableCredential iWritableCredentialUpdate = update();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iWritableCredentialUpdate);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICredential {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICredential.DESCRIPTOR;
            }

            @Override // android.security.identity.ICredential
            public byte[] createEphemeralKeyPair() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public void setReaderEphemeralPublicKey(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public byte[] deleteCredential() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public byte[] deleteWithChallenge(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public byte[] proveOwnership(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public byte[] getCredentialKeyCertificateChain() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public long selectAuthKey(boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public GetEntriesResultParcel getEntries(byte[] bArr, RequestNamespaceParcel[] requestNamespaceParcelArr, byte[] bArr2, byte[] bArr3, boolean z, boolean z2, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedArray(requestNamespaceParcelArr, 0);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GetEntriesResultParcel) parcelObtain2.readTypedObject(GetEntriesResultParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public void setAvailableAuthenticationKeys(int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public AuthKeyParcel[] getAuthKeysNeedingCertification() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AuthKeyParcel[]) parcelObtain2.createTypedArray(AuthKeyParcel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public void storeStaticAuthenticationData(AuthKeyParcel authKeyParcel, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    parcelObtain.writeTypedObject(authKeyParcel, 0);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public void storeStaticAuthenticationDataWithExpiration(AuthKeyParcel authKeyParcel, long j, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    parcelObtain.writeTypedObject(authKeyParcel, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public int[] getAuthenticationDataUsageCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public long[] getAuthenticationDataExpirations() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.identity.ICredential
            public IWritableCredential update() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICredential.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IWritableCredential.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
