package android.system.keystore2;

import android.hardware.security.keymint.KeyParameter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IKeystoreSecurityLevel extends IInterface {
    public static final String DESCRIPTOR = "android$system$keystore2$IKeystoreSecurityLevel".replace('$', '.');
    public static final String HASH = "98d815116c190250e9e5a1d9182cea8126fd0e97";
    public static final int KEY_FLAG_AUTH_BOUND_WITHOUT_CRYPTOGRAPHIC_LSKF_BINDING = 1;
    public static final int VERSION = 5;

    EphemeralStorageKeyResponse convertStorageKeyToEphemeral(KeyDescriptor keyDescriptor) throws RemoteException;

    CreateOperationResponse createOperation(KeyDescriptor keyDescriptor, KeyParameter[] keyParameterArr, boolean z) throws RemoteException;

    void deleteKey(KeyDescriptor keyDescriptor) throws RemoteException;

    KeyMetadata generateKey(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, KeyParameter[] keyParameterArr, int i, byte[] bArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    KeyMetadata importKey(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, KeyParameter[] keyParameterArr, int i, byte[] bArr) throws RemoteException;

    KeyMetadata importWrappedKey(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, byte[] bArr, KeyParameter[] keyParameterArr, AuthenticatorSpec[] authenticatorSpecArr) throws RemoteException;

    public static class Default implements IKeystoreSecurityLevel {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public EphemeralStorageKeyResponse convertStorageKeyToEphemeral(KeyDescriptor keyDescriptor) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public CreateOperationResponse createOperation(KeyDescriptor keyDescriptor, KeyParameter[] keyParameterArr, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public void deleteKey(KeyDescriptor keyDescriptor) throws RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public KeyMetadata generateKey(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, KeyParameter[] keyParameterArr, int i, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public KeyMetadata importKey(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, KeyParameter[] keyParameterArr, int i, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public KeyMetadata importWrappedKey(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, byte[] bArr, KeyParameter[] keyParameterArr, AuthenticatorSpec[] authenticatorSpecArr) throws RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreSecurityLevel
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IKeystoreSecurityLevel {
        static final int TRANSACTION_convertStorageKeyToEphemeral = 5;
        static final int TRANSACTION_createOperation = 1;
        static final int TRANSACTION_deleteKey = 6;
        static final int TRANSACTION_generateKey = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_importKey = 3;
        static final int TRANSACTION_importWrappedKey = 4;

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

        public static IKeystoreSecurityLevel asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeystoreSecurityLevel)) {
                return (IKeystoreSecurityLevel) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createOperation";
                case 2:
                    return "generateKey";
                case 3:
                    return "importKey";
                case 4:
                    return "importWrappedKey";
                case 5:
                    return "convertStorageKeyToEphemeral";
                case 6:
                    return "deleteKey";
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
                    KeyDescriptor keyDescriptor = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    KeyParameter[] keyParameterArr = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    CreateOperationResponse createOperationResponseCreateOperation = createOperation(keyDescriptor, keyParameterArr, z);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createOperationResponseCreateOperation, 1);
                    return true;
                case 2:
                    KeyDescriptor keyDescriptor2 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    KeyDescriptor keyDescriptor3 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    KeyParameter[] keyParameterArr2 = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    int i3 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    KeyMetadata keyMetadataGenerateKey = generateKey(keyDescriptor2, keyDescriptor3, keyParameterArr2, i3, bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyMetadataGenerateKey, 1);
                    return true;
                case 3:
                    KeyDescriptor keyDescriptor4 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    KeyDescriptor keyDescriptor5 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    KeyParameter[] keyParameterArr3 = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    int i4 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    KeyMetadata keyMetadataImportKey = importKey(keyDescriptor4, keyDescriptor5, keyParameterArr3, i4, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyMetadataImportKey, 1);
                    return true;
                case 4:
                    KeyDescriptor keyDescriptor6 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    KeyDescriptor keyDescriptor7 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    KeyParameter[] keyParameterArr4 = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    AuthenticatorSpec[] authenticatorSpecArr = (AuthenticatorSpec[]) parcel.createTypedArray(AuthenticatorSpec.CREATOR);
                    parcel.enforceNoDataAvail();
                    KeyMetadata keyMetadataImportWrappedKey = importWrappedKey(keyDescriptor6, keyDescriptor7, bArrCreateByteArray3, keyParameterArr4, authenticatorSpecArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyMetadataImportWrappedKey, 1);
                    return true;
                case 5:
                    KeyDescriptor keyDescriptor8 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    EphemeralStorageKeyResponse ephemeralStorageKeyResponseConvertStorageKeyToEphemeral = convertStorageKeyToEphemeral(keyDescriptor8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ephemeralStorageKeyResponseConvertStorageKeyToEphemeral, 1);
                    return true;
                case 6:
                    KeyDescriptor keyDescriptor9 = (KeyDescriptor) parcel.readTypedObject(KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteKey(keyDescriptor9);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IKeystoreSecurityLevel {
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

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public CreateOperationResponse createOperation(KeyDescriptor keyDescriptor, KeyParameter[] keyParameterArr, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    parcelObtain.writeBoolean(z);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method createOperation is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (CreateOperationResponse) parcelObtain2.readTypedObject(CreateOperationResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public KeyMetadata generateKey(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, KeyParameter[] keyParameterArr, int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    parcelObtain.writeTypedObject(keyDescriptor2, 0);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method generateKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyMetadata) parcelObtain2.readTypedObject(KeyMetadata.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public KeyMetadata importKey(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, KeyParameter[] keyParameterArr, int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    parcelObtain.writeTypedObject(keyDescriptor2, 0);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method importKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyMetadata) parcelObtain2.readTypedObject(KeyMetadata.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public KeyMetadata importWrappedKey(KeyDescriptor keyDescriptor, KeyDescriptor keyDescriptor2, byte[] bArr, KeyParameter[] keyParameterArr, AuthenticatorSpec[] authenticatorSpecArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    parcelObtain.writeTypedObject(keyDescriptor2, 0);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    parcelObtain.writeTypedArray(authenticatorSpecArr, 0);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method importWrappedKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyMetadata) parcelObtain2.readTypedObject(KeyMetadata.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public EphemeralStorageKeyResponse convertStorageKeyToEphemeral(KeyDescriptor keyDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method convertStorageKeyToEphemeral is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (EphemeralStorageKeyResponse) parcelObtain2.readTypedObject(EphemeralStorageKeyResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
            public void deleteKey(KeyDescriptor keyDescriptor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method deleteKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreSecurityLevel
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

            @Override // android.system.keystore2.IKeystoreSecurityLevel
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
