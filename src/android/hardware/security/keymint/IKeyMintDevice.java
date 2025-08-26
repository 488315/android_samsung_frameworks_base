package android.hardware.security.keymint;

import android.hardware.security.secureclock.TimeStampToken;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IKeyMintDevice extends IInterface {
    public static final int AUTH_TOKEN_MAC_LENGTH = 32;
    public static final String DESCRIPTOR = "android$hardware$security$keymint$IKeyMintDevice".replace('$', '.');
    public static final String HASH = "a05c8079586139db45b0762a528cdd9745ad15ce";
    public static final int VERSION = 4;

    void addRngEntropy(byte[] bArr) throws RemoteException;

    BeginResult begin(int i, byte[] bArr, KeyParameter[] keyParameterArr, HardwareAuthToken hardwareAuthToken) throws RemoteException;

    byte[] convertStorageKeyToEphemeral(byte[] bArr) throws RemoteException;

    void deleteAllKeys() throws RemoteException;

    void deleteKey(byte[] bArr) throws RemoteException;

    void destroyAttestationIds() throws RemoteException;

    @Deprecated
    void deviceLocked(boolean z, TimeStampToken timeStampToken) throws RemoteException;

    void earlyBootEnded() throws RemoteException;

    KeyCreationResult generateKey(KeyParameter[] keyParameterArr, AttestationKey attestationKey) throws RemoteException;

    KeyMintHardwareInfo getHardwareInfo() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    KeyCharacteristics[] getKeyCharacteristics(byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException;

    byte[] getRootOfTrust(byte[] bArr) throws RemoteException;

    byte[] getRootOfTrustChallenge() throws RemoteException;

    KeyCreationResult importKey(KeyParameter[] keyParameterArr, int i, byte[] bArr, AttestationKey attestationKey) throws RemoteException;

    KeyCreationResult importWrappedKey(byte[] bArr, byte[] bArr2, byte[] bArr3, KeyParameter[] keyParameterArr, long j, long j2) throws RemoteException;

    void sendRootOfTrust(byte[] bArr) throws RemoteException;

    void setAdditionalAttestationInfo(KeyParameter[] keyParameterArr) throws RemoteException;

    byte[] upgradeKey(byte[] bArr, KeyParameter[] keyParameterArr) throws RemoteException;

    public static class Default implements IKeyMintDevice {
        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void addRngEntropy(byte[] bArr) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public BeginResult begin(int i, byte[] bArr, KeyParameter[] keyParameterArr, HardwareAuthToken hardwareAuthToken) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public byte[] convertStorageKeyToEphemeral(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void deleteAllKeys() throws RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void deleteKey(byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void destroyAttestationIds() throws RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void deviceLocked(boolean z, TimeStampToken timeStampToken) throws RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void earlyBootEnded() throws RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public KeyCreationResult generateKey(KeyParameter[] keyParameterArr, AttestationKey attestationKey) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public KeyMintHardwareInfo getHardwareInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public KeyCharacteristics[] getKeyCharacteristics(byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public byte[] getRootOfTrust(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public byte[] getRootOfTrustChallenge() throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public KeyCreationResult importKey(KeyParameter[] keyParameterArr, int i, byte[] bArr, AttestationKey attestationKey) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public KeyCreationResult importWrappedKey(byte[] bArr, byte[] bArr2, byte[] bArr3, KeyParameter[] keyParameterArr, long j, long j2) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void sendRootOfTrust(byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public void setAdditionalAttestationInfo(KeyParameter[] keyParameterArr) throws RemoteException {
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public byte[] upgradeKey(byte[] bArr, KeyParameter[] keyParameterArr) throws RemoteException {
            return null;
        }

        @Override // android.hardware.security.keymint.IKeyMintDevice
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IKeyMintDevice {
        static final int TRANSACTION_addRngEntropy = 2;
        static final int TRANSACTION_begin = 10;
        static final int TRANSACTION_convertStorageKeyToEphemeral = 13;
        static final int TRANSACTION_deleteAllKeys = 8;
        static final int TRANSACTION_deleteKey = 7;
        static final int TRANSACTION_destroyAttestationIds = 9;
        static final int TRANSACTION_deviceLocked = 11;
        static final int TRANSACTION_earlyBootEnded = 12;
        static final int TRANSACTION_generateKey = 3;
        static final int TRANSACTION_getHardwareInfo = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getKeyCharacteristics = 14;
        static final int TRANSACTION_getRootOfTrust = 16;
        static final int TRANSACTION_getRootOfTrustChallenge = 15;
        static final int TRANSACTION_importKey = 4;
        static final int TRANSACTION_importWrappedKey = 5;
        static final int TRANSACTION_sendRootOfTrust = 17;
        static final int TRANSACTION_setAdditionalAttestationInfo = 18;
        static final int TRANSACTION_upgradeKey = 6;

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

        public static IKeyMintDevice asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IKeyMintDevice)) {
                return (IKeyMintDevice) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getHardwareInfo";
                case 2:
                    return "addRngEntropy";
                case 3:
                    return "generateKey";
                case 4:
                    return "importKey";
                case 5:
                    return "importWrappedKey";
                case 6:
                    return "upgradeKey";
                case 7:
                    return "deleteKey";
                case 8:
                    return "deleteAllKeys";
                case 9:
                    return "destroyAttestationIds";
                case 10:
                    return "begin";
                case 11:
                    return "deviceLocked";
                case 12:
                    return "earlyBootEnded";
                case 13:
                    return "convertStorageKeyToEphemeral";
                case 14:
                    return "getKeyCharacteristics";
                case 15:
                    return "getRootOfTrustChallenge";
                case 16:
                    return "getRootOfTrust";
                case 17:
                    return "sendRootOfTrust";
                case 18:
                    return "setAdditionalAttestationInfo";
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
                    KeyMintHardwareInfo hardwareInfo = getHardwareInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hardwareInfo, 1);
                    return true;
                case 2:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    addRngEntropy(bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    KeyParameter[] keyParameterArr = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    AttestationKey attestationKey = (AttestationKey) parcel.readTypedObject(AttestationKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    KeyCreationResult keyCreationResultGenerateKey = generateKey(keyParameterArr, attestationKey);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCreationResultGenerateKey, 1);
                    return true;
                case 4:
                    KeyParameter[] keyParameterArr2 = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    int i3 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    AttestationKey attestationKey2 = (AttestationKey) parcel.readTypedObject(AttestationKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    KeyCreationResult keyCreationResultImportKey = importKey(keyParameterArr2, i3, bArrCreateByteArray2, attestationKey2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCreationResultImportKey, 1);
                    return true;
                case 5:
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    KeyParameter[] keyParameterArr3 = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    KeyCreationResult keyCreationResultImportWrappedKey = importWrappedKey(bArrCreateByteArray3, bArrCreateByteArray4, bArrCreateByteArray5, keyParameterArr3, j, j2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyCreationResultImportWrappedKey, 1);
                    return true;
                case 6:
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    KeyParameter[] keyParameterArr4 = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    parcel.enforceNoDataAvail();
                    byte[] bArrUpgradeKey = upgradeKey(bArrCreateByteArray6, keyParameterArr4);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrUpgradeKey);
                    return true;
                case 7:
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    deleteKey(bArrCreateByteArray7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    deleteAllKeys();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    destroyAttestationIds();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i4 = parcel.readInt();
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    KeyParameter[] keyParameterArr5 = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    HardwareAuthToken hardwareAuthToken = (HardwareAuthToken) parcel.readTypedObject(HardwareAuthToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    BeginResult beginResultBegin = begin(i4, bArrCreateByteArray8, keyParameterArr5, hardwareAuthToken);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(beginResultBegin, 1);
                    return true;
                case 11:
                    boolean z = parcel.readBoolean();
                    TimeStampToken timeStampToken = (TimeStampToken) parcel.readTypedObject(TimeStampToken.CREATOR);
                    parcel.enforceNoDataAvail();
                    deviceLocked(z, timeStampToken);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    earlyBootEnded();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrConvertStorageKeyToEphemeral = convertStorageKeyToEphemeral(bArrCreateByteArray9);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrConvertStorageKeyToEphemeral);
                    return true;
                case 14:
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    byte[] bArrCreateByteArray12 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    KeyCharacteristics[] keyCharacteristics = getKeyCharacteristics(bArrCreateByteArray10, bArrCreateByteArray11, bArrCreateByteArray12);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(keyCharacteristics, 1);
                    return true;
                case 15:
                    byte[] rootOfTrustChallenge = getRootOfTrustChallenge();
                    parcel2.writeNoException();
                    parcel2.writeFixedArray(rootOfTrustChallenge, 1, 16);
                    return true;
                case 16:
                    byte[] bArr = (byte[]) parcel.createFixedArray(byte[].class, 16);
                    parcel.enforceNoDataAvail();
                    byte[] rootOfTrust = getRootOfTrust(bArr);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(rootOfTrust);
                    return true;
                case 17:
                    byte[] bArrCreateByteArray13 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendRootOfTrust(bArrCreateByteArray13);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    KeyParameter[] keyParameterArr6 = (KeyParameter[]) parcel.createTypedArray(KeyParameter.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAdditionalAttestationInfo(keyParameterArr6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IKeyMintDevice {
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

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public KeyMintHardwareInfo getHardwareInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method getHardwareInfo is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyMintHardwareInfo) parcelObtain2.readTypedObject(KeyMintHardwareInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void addRngEntropy(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method addRngEntropy is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public KeyCreationResult generateKey(KeyParameter[] keyParameterArr, AttestationKey attestationKey) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    parcelObtain.writeTypedObject(attestationKey, 0);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method generateKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyCreationResult) parcelObtain2.readTypedObject(KeyCreationResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public KeyCreationResult importKey(KeyParameter[] keyParameterArr, int i, byte[] bArr, AttestationKey attestationKey) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedObject(attestationKey, 0);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method importKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyCreationResult) parcelObtain2.readTypedObject(KeyCreationResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public KeyCreationResult importWrappedKey(byte[] bArr, byte[] bArr2, byte[] bArr3, KeyParameter[] keyParameterArr, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method importWrappedKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyCreationResult) parcelObtain2.readTypedObject(KeyCreationResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public byte[] upgradeKey(byte[] bArr, KeyParameter[] keyParameterArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method upgradeKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void deleteKey(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method deleteKey is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void deleteAllKeys() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method deleteAllKeys is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void destroyAttestationIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method destroyAttestationIds is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public BeginResult begin(int i, byte[] bArr, KeyParameter[] keyParameterArr, HardwareAuthToken hardwareAuthToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    parcelObtain.writeTypedObject(hardwareAuthToken, 0);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method begin is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (BeginResult) parcelObtain2.readTypedObject(BeginResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void deviceLocked(boolean z, TimeStampToken timeStampToken) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(timeStampToken, 0);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method deviceLocked is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void earlyBootEnded() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method earlyBootEnded is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public byte[] convertStorageKeyToEphemeral(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method convertStorageKeyToEphemeral is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public KeyCharacteristics[] getKeyCharacteristics(byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method getKeyCharacteristics is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (KeyCharacteristics[]) parcelObtain2.createTypedArray(KeyCharacteristics.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public byte[] getRootOfTrustChallenge() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method getRootOfTrustChallenge is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return (byte[]) parcelObtain2.createFixedArray(byte[].class, 16);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public byte[] getRootOfTrust(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeFixedArray(bArr, 0, 16);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method getRootOfTrust is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void sendRootOfTrust(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method sendRootOfTrust is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
            public void setAdditionalAttestationInfo(KeyParameter[] keyParameterArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                parcelObtain.markSensitive();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedArray(keyParameterArr, 0);
                    if (!this.mRemote.transact(18, parcelObtain, parcelObtain2, 32)) {
                        throw new RemoteException("Method setAdditionalAttestationInfo is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.security.keymint.IKeyMintDevice
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

            @Override // android.hardware.security.keymint.IKeyMintDevice
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
