package com.samsung.android.service.vaultkeeper;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IVaultKeeperService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.service.vaultkeeper.IVaultKeeperService";

    public static class Default implements IVaultKeeperService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int checkDataWritable(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int destroy(String str, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public byte[] encryptMessage(String str, byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int generateHotpCode(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int initialize(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public boolean isInitialized(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public boolean migrationStorage(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public byte[] read(String str, int i, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public byte[] sensitiveBox(String str, int i, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public boolean verifyCertificate(String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int write(String str, int i, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
            return 0;
        }
    }

    int checkDataWritable(String str) throws RemoteException;

    int destroy(String str, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException;

    byte[] encryptMessage(String str, byte[] bArr) throws RemoteException;

    int generateHotpCode(String str) throws RemoteException;

    int initialize(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws RemoteException;

    boolean isInitialized(String str) throws RemoteException;

    boolean migrationStorage(String str) throws RemoteException;

    byte[] read(String str, int i, int[] iArr) throws RemoteException;

    byte[] sensitiveBox(String str, int i, int[] iArr) throws RemoteException;

    boolean verifyCertificate(String str, byte[] bArr) throws RemoteException;

    int write(String str, int i, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException;

    public static abstract class Stub extends Binder implements IVaultKeeperService {
        static final int TRANSACTION_checkDataWritable = 10;
        static final int TRANSACTION_destroy = 3;
        static final int TRANSACTION_encryptMessage = 7;
        static final int TRANSACTION_generateHotpCode = 11;
        static final int TRANSACTION_initialize = 2;
        static final int TRANSACTION_isInitialized = 1;
        static final int TRANSACTION_migrationStorage = 8;
        static final int TRANSACTION_read = 4;
        static final int TRANSACTION_sensitiveBox = 6;
        static final int TRANSACTION_verifyCertificate = 9;
        static final int TRANSACTION_write = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, IVaultKeeperService.DESCRIPTOR);
        }

        public static IVaultKeeperService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVaultKeeperService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVaultKeeperService)) {
                return (IVaultKeeperService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isInitialized";
                case 2:
                    return "initialize";
                case 3:
                    return "destroy";
                case 4:
                    return "read";
                case 5:
                    return "write";
                case 6:
                    return "sensitiveBox";
                case 7:
                    return "encryptMessage";
                case 8:
                    return "migrationStorage";
                case 9:
                    return "verifyCertificate";
                case 10:
                    return "checkDataWritable";
                case 11:
                    return "generateHotpCode";
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
            int[] iArr;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVaultKeeperService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVaultKeeperService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsInitialized = isInitialized(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInitialized);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iInitialize = initialize(string2, bArrCreateByteArray, bArrCreateByteArray2, bArrCreateByteArray3, bArrCreateByteArray4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iInitialize);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    byte[] bArrCreateByteArray5 = parcel.createByteArray();
                    byte[] bArrCreateByteArray6 = parcel.createByteArray();
                    byte[] bArrCreateByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iDestroy = destroy(string3, bArrCreateByteArray5, bArrCreateByteArray6, bArrCreateByteArray7);
                    parcel2.writeNoException();
                    parcel2.writeInt(iDestroy);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    if (i4 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i4);
                    }
                    iArr = i4 >= 0 ? new int[i4] : null;
                    parcel.enforceNoDataAvail();
                    byte[] bArr = read(string4, i3, iArr);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArr);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    int i5 = parcel.readInt();
                    byte[] bArrCreateByteArray8 = parcel.createByteArray();
                    byte[] bArrCreateByteArray9 = parcel.createByteArray();
                    byte[] bArrCreateByteArray10 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int iWrite = write(string5, i5, bArrCreateByteArray8, bArrCreateByteArray9, bArrCreateByteArray10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iWrite);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    if (i7 > 1000000) {
                        throw new BadParcelableException("Array too large: " + i7);
                    }
                    iArr = i7 >= 0 ? new int[i7] : null;
                    parcel.enforceNoDataAvail();
                    byte[] bArrSensitiveBox = sensitiveBox(string6, i6, iArr);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrSensitiveBox);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    byte[] bArrCreateByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrEncryptMessage = encryptMessage(string7, bArrCreateByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrEncryptMessage);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zMigrationStorage = migrationStorage(string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMigrationStorage);
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    byte[] bArrCreateByteArray12 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean zVerifyCertificate = verifyCertificate(string9, bArrCreateByteArray12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zVerifyCertificate);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckDataWritable = checkDataWritable(string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckDataWritable);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iGenerateHotpCode = generateHotpCode(string11);
                    parcel2.writeNoException();
                    parcel2.writeInt(iGenerateHotpCode);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVaultKeeperService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVaultKeeperService.DESCRIPTOR;
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public boolean isInitialized(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int initialize(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    parcelObtain.writeByteArray(bArr4);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int destroy(String str, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public byte[] read(String str, int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(iArr.length);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    byte[] bArrCreateByteArray = parcelObtain2.createByteArray();
                    parcelObtain2.readIntArray(iArr);
                    return bArrCreateByteArray;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int write(String str, int i, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeByteArray(bArr2);
                    parcelObtain.writeByteArray(bArr3);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public byte[] sensitiveBox(String str, int i, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(iArr.length);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    byte[] bArrCreateByteArray = parcelObtain2.createByteArray();
                    parcelObtain2.readIntArray(iArr);
                    return bArrCreateByteArray;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public byte[] encryptMessage(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public boolean migrationStorage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public boolean verifyCertificate(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int checkDataWritable(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int generateHotpCode(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
