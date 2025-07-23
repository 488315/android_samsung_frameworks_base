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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVaultKeeperService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVaultKeeperService)) {
                return (IVaultKeeperService) queryLocalInterface;
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isInitialized = isInitialized(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInitialized);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    byte[] createByteArray3 = parcel.createByteArray();
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int initialize = initialize(readString2, createByteArray, createByteArray2, createByteArray3, createByteArray4);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialize);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    byte[] createByteArray5 = parcel.createByteArray();
                    byte[] createByteArray6 = parcel.createByteArray();
                    byte[] createByteArray7 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int destroy = destroy(readString3, createByteArray5, createByteArray6, createByteArray7);
                    parcel2.writeNoException();
                    parcel2.writeInt(destroy);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    if (readInt2 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt2);
                    }
                    iArr = readInt2 >= 0 ? new int[readInt2] : null;
                    parcel.enforceNoDataAvail();
                    byte[] read = read(readString4, readInt, iArr);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(read);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    byte[] createByteArray8 = parcel.createByteArray();
                    byte[] createByteArray9 = parcel.createByteArray();
                    byte[] createByteArray10 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int write = write(readString5, readInt3, createByteArray8, createByteArray9, createByteArray10);
                    parcel2.writeNoException();
                    parcel2.writeInt(write);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    if (readInt5 > 1000000) {
                        throw new BadParcelableException("Array too large: " + readInt5);
                    }
                    iArr = readInt5 >= 0 ? new int[readInt5] : null;
                    parcel.enforceNoDataAvail();
                    byte[] sensitiveBox = sensitiveBox(readString6, readInt4, iArr);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sensitiveBox);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    byte[] createByteArray11 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] encryptMessage = encryptMessage(readString7, createByteArray11);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(encryptMessage);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean migrationStorage = migrationStorage(readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(migrationStorage);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    byte[] createByteArray12 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean verifyCertificate = verifyCertificate(readString9, createByteArray12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(verifyCertificate);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkDataWritable = checkDataWritable(readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkDataWritable);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int generateHotpCode = generateHotpCode(readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(generateHotpCode);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int initialize(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    obtain.writeByteArray(bArr4);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int destroy(String str, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public byte[] read(String str, int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    byte[] createByteArray = obtain2.createByteArray();
                    obtain2.readIntArray(iArr);
                    return createByteArray;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int write(String str, int i, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeByteArray(bArr3);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public byte[] sensitiveBox(String str, int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    byte[] createByteArray = obtain2.createByteArray();
                    obtain2.readIntArray(iArr);
                    return createByteArray;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public byte[] encryptMessage(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public boolean migrationStorage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public boolean verifyCertificate(String str, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int checkDataWritable(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int generateHotpCode(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
