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
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iDirEncryptServiceListener != null ? iDirEncryptServiceListener.asBinder() : null);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void unregisterListener(IDirEncryptServiceListener iDirEncryptServiceListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iDirEncryptServiceListener != null ? iDirEncryptServiceListener.asBinder() : null);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int setStorageCardEncryptionPolicy(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int setSdCardEncryptionPolicy(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int isStorageCardEncryptionPoliciesApplied() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public boolean isSdCardEncryped() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int setPassword(String str) throws Throwable {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                int i = 0;
                int length = str == null ? 0 : str.length();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    int iDataPosition = parcelObtain.dataPosition();
                    try {
                        parcelObtain.writeString(str);
                        this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        int i2 = parcelObtain2.readInt();
                        parcelObtain2.recycle();
                        parcelObtain.setDataPosition(iDataPosition);
                        parcelObtain.writeByteArray(new byte[length]);
                        parcelObtain.recycle();
                        return i2;
                    } catch (Throwable th) {
                        th = th;
                        i = iDataPosition;
                        parcelObtain2.recycle();
                        parcelObtain.setDataPosition(i);
                        parcelObtain.writeByteArray(new byte[length]);
                        parcelObtain.recycle();
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int getCurrentStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int getLastError() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int getAdditionalSpaceRequired() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int encryptStorage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void unmountSDCardByAdmin() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void setNeedToCreateKey(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByte(z ? (byte) 1 : (byte) 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void setMountSDcardToHelper(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByte(z ? (byte) 1 : (byte) 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public IVoldTaskListener getListener() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IVoldTaskListener.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static IDirEncryptService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDirEncryptService)) {
                return (IDirEncryptService) iInterfaceQueryLocalInterface;
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
                    IDirEncryptServiceListener iDirEncryptServiceListenerAsInterface = IDirEncryptServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    if (iDirEncryptServiceListenerAsInterface == null) {
                        return false;
                    }
                    registerListener(iDirEncryptServiceListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    IDirEncryptServiceListener iDirEncryptServiceListenerAsInterface2 = IDirEncryptServiceListener.Stub.asInterface(parcel.readStrongBinder());
                    if (iDirEncryptServiceListenerAsInterface2 == null) {
                        return false;
                    }
                    unregisterListener(iDirEncryptServiceListenerAsInterface2);
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
                    int iIsStorageCardEncryptionPoliciesApplied = isStorageCardEncryptionPoliciesApplied();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsStorageCardEncryptionPoliciesApplied);
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
                    int iEncryptStorage = encryptStorage(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(iEncryptStorage);
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
                    boolean zIsSdCardEncryped = isSdCardEncryped();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsSdCardEncryped ? 1 : 0);
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
