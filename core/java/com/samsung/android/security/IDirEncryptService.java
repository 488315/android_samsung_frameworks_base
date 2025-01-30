package com.samsung.android.security;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.IVoldTaskListener;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.samsung.android.security.IDirEncryptServiceListener;

/* loaded from: classes5.dex */
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

        private static class Proxy implements IDirEncryptService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void registerListener(IDirEncryptServiceListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void unregisterListener(IDirEncryptServiceListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(listener != null ? listener.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int setStorageCardEncryptionPolicy(int encType, int fullEnc, int excludeMediaFiles) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(encType);
                    _data.writeInt(fullEnc);
                    _data.writeInt(excludeMediaFiles);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int setSdCardEncryptionPolicy(int isPolicy, int status, String uuid) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(isPolicy);
                    _data.writeInt(status);
                    _data.writeString(uuid);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int isStorageCardEncryptionPoliciesApplied() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public boolean isSdCardEncryped() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readInt() != 0;
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int setPassword(String password) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                int _pos = 0;
                int length = password == null ? 0 : password.length();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _pos = _data.dataPosition();
                    _data.writeString(password);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.recycle();
                    _data.setDataPosition(_pos);
                    _data.writeByteArray(new byte[length]);
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.setDataPosition(_pos);
                    _data.writeByteArray(new byte[length]);
                    _data.recycle();
                    throw th;
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int getCurrentStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int getLastError() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int getAdditionalSpaceRequired() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public int encryptStorage(String path) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void unmountSDCardByAdmin() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void setNeedToCreateKey(boolean in) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte((byte) (in ? 1 : 0));
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public void setMountSDcardToHelper(boolean in) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByte((byte) (in ? 1 : 0));
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.security.IDirEncryptService
            public IVoldTaskListener getListener() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    IVoldTaskListener _result = IVoldTaskListener.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public static IDirEncryptService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IDirEncryptService)) {
                return (IDirEncryptService) iin;
            }
            return new Proxy(obj);
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
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
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }
}
