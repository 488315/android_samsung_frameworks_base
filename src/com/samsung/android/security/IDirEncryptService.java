package com.samsung.android.security;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IVoldTaskListener;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.security.IDirEncryptServiceListener;

/* loaded from: classes6.dex */
public interface IDirEncryptService extends IInterface {
    int encryptStorage(String str) throws RemoteException;

    int getAdditionalSpaceRequired() throws RemoteException;

    int getCurrentStatus() throws RemoteException;

    int getLastError() throws RemoteException;

    IVoldTaskListener getListener() throws RemoteException;

    boolean isSdCardEncryped() throws RemoteException;

    int isStorageCardEncryptionPoliciesApplied() throws RemoteException;

    void registerListener(IDirEncryptServiceListener iDirEncryptServiceListener) throws RemoteException;

    void setMountSDcardToHelper(boolean z) throws RemoteException;

    void setNeedToCreateKey(boolean z) throws RemoteException;

    int setPassword(String str) throws RemoteException;

    int setSdCardEncryptionPolicy(int i, int i2, String str) throws RemoteException;

    int setStorageCardEncryptionPolicy(int i, int i2, int i3) throws RemoteException;

    void unmountSDCardByAdmin() throws RemoteException;

    void unregisterListener(IDirEncryptServiceListener iDirEncryptServiceListener) throws RemoteException;

    public static abstract class Stub extends Binder implements IDirEncryptService {
        private static final String DESCRIPTOR = "IDirEncryptService";
        static final int TRANSACTION_encryptStorage = 9;
        static final int TRANSACTION_getAdditionalSpaceRequired = 8;
        static final int TRANSACTION_getCurrentStatus = 6;
        static final int TRANSACTION_getLastError = 7;
        static final int TRANSACTION_getListener = 43;
        static final int TRANSACTION_isSdCardEncryped = 12;
        static final int TRANSACTION_isStorageCardEncryptionPoliciesApplied = 4;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_setMountSDcardToHelper = 42;
        static final int TRANSACTION_setNeedToCreateKey = 41;
        static final int TRANSACTION_setPassword = 5;
        static final int TRANSACTION_setSdCardEncryptionPolicy = 11;
        static final int TRANSACTION_setStorageCardEncryptionPolicy = 3;
        static final int TRANSACTION_unmountSDCardByAdmin = 10;
        static final int TRANSACTION_unregisterListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        private static class Proxy implements IDirEncryptService {
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

            @Override // com.samsung.android.security.IDirEncryptService
            public void registerListener(IDirEncryptServiceListener iDirEncryptServiceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iDirEncryptServiceListener != null ? iDirEncryptServiceListener.asBinder() : null);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void unregisterListener(IDirEncryptServiceListener iDirEncryptServiceListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iDirEncryptServiceListener != null ? iDirEncryptServiceListener.asBinder() : null);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int setStorageCardEncryptionPolicy(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int setSdCardEncryptionPolicy(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int isStorageCardEncryptionPoliciesApplied() throws RemoteException {
                Parcel obtain = Parcel.obtain();
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

            @Override // com.samsung.android.security.IDirEncryptService
            public boolean isSdCardEncryped() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int setPassword(String str) throws RemoteException {
                int dataPosition;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                int i = 0;
                int length = str == null ? 0 : str.length();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    dataPosition = obtain.dataPosition();
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    obtain2.recycle();
                    obtain.setDataPosition(dataPosition);
                    obtain.writeByteArray(new byte[length]);
                    obtain.recycle();
                    return readInt;
                } catch (Throwable th2) {
                    th = th2;
                    i = dataPosition;
                    obtain2.recycle();
                    obtain.setDataPosition(i);
                    obtain.writeByteArray(new byte[length]);
                    obtain.recycle();
                    throw th;
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int getCurrentStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int getLastError() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int getAdditionalSpaceRequired() throws RemoteException {
                Parcel obtain = Parcel.obtain();
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

            @Override // com.samsung.android.security.IDirEncryptService
            public int encryptStorage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void unmountSDCardByAdmin() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void setNeedToCreateKey(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(z ? (byte) 1 : (byte) 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void setMountSDcardToHelper(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByte(z ? (byte) 1 : (byte) 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public IVoldTaskListener getListener() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return IVoldTaskListener.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IDirEncryptService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDirEncryptService)) {
                return (IDirEncryptService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    IDirEncryptServiceListener asInterface = IDirEncryptServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    if (asInterface == null) {
                        return false;
                    }
                    registerListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    IDirEncryptServiceListener asInterface2 = IDirEncryptServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    if (asInterface2 == null) {
                        return false;
                    }
                    unregisterListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    int storageCardEncryptionPolicy = setStorageCardEncryptionPolicy(parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(storageCardEncryptionPolicy);
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    int isStorageCardEncryptionPoliciesApplied = isStorageCardEncryptionPoliciesApplied();
                    parcel2.writeNoException();
                    parcel2.writeInt(isStorageCardEncryptionPoliciesApplied);
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    int password = setPassword(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(password);
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    int currentStatus = getCurrentStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(currentStatus);
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    int lastError = getLastError();
                    parcel2.writeNoException();
                    parcel2.writeInt(lastError);
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    int additionalSpaceRequired = getAdditionalSpaceRequired();
                    parcel2.writeNoException();
                    parcel2.writeInt(additionalSpaceRequired);
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    int encryptStorage = encryptStorage(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(encryptStorage);
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    unmountSDCardByAdmin();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    int sdCardEncryptionPolicy = setSdCardEncryptionPolicy(parcel.readInt(), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(sdCardEncryptionPolicy);
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    boolean isSdCardEncryped = isSdCardEncryped();
                    parcel2.writeNoException();
                    parcel2.writeInt(isSdCardEncryped ? 1 : 0);
                    return true;
                default:
                    switch (i) {
                        case 41:
                            parcel.enforceInterface(DESCRIPTOR);
                            setNeedToCreateKey(parcel.readByte() == 1);
                            parcel2.writeNoException();
                            return true;
                        case 42:
                            parcel.enforceInterface(DESCRIPTOR);
                            setMountSDcardToHelper(parcel.readByte() == 1);
                            parcel2.writeNoException();
                            return true;
                        case 43:
                            parcel.enforceInterface(DESCRIPTOR);
                            IVoldTaskListener listener = getListener();
                            parcel2.writeNoException();
                            parcel2.writeStrongBinder(listener != null ? listener.asBinder() : null);
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }
    }
}
