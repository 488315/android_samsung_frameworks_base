package com.samsung.android.service.vaultkeeper;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IVaultKeeperService extends IInterface {
    public static final String DESCRIPTOR =
            "com.samsung.android.service.vaultkeeper.IVaultKeeperService";

    int checkDataWritable(String str) throws RemoteException;

    int destroy(String str, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException;

    byte[] encryptMessage(String str, byte[] bArr) throws RemoteException;

    int generateHotpCode(String str) throws RemoteException;

    int initialize(String str, byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4)
            throws RemoteException;

    boolean isInitialized(String str) throws RemoteException;

    boolean migrationStorage(String str) throws RemoteException;

    byte[] read(String str, int i, int[] iArr) throws RemoteException;

    byte[] sensitiveBox(String str, int i, int[] iArr) throws RemoteException;

    boolean verifyCertificate(String str, byte[] bArr) throws RemoteException;

    int write(String str, int i, byte[] bArr, byte[] bArr2, byte[] bArr3) throws RemoteException;

    public static class Default implements IVaultKeeperService {
        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public boolean isInitialized(String vaultName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int initialize(
                String vaultName, byte[] key, byte[] initUnsheltered, byte[] cert, byte[] sig)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int destroy(String vaultName, byte[] hmac, byte[] cert, byte[] sig)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public byte[] read(String vaultName, int type, int[] errorCode) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int write(String vaultName, int type, byte[] data, byte[] cert, byte[] sig)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public byte[] sensitiveBox(String vaultName, int type, int[] errorCode)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public byte[] encryptMessage(String vaultName, byte[] msg) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public boolean migrationStorage(String vaultName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public boolean verifyCertificate(String vaultName, byte[] cert) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int checkDataWritable(String vaultName) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
        public int generateHotpCode(String vaultName) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IVaultKeeperService {
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

        public Stub() {
            attachInterface(this, IVaultKeeperService.DESCRIPTOR);
        }

        public static IVaultKeeperService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IVaultKeeperService.DESCRIPTOR);
            if (iin != null && (iin instanceof IVaultKeeperService)) {
                return (IVaultKeeperService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            int[] _arg2;
            int[] _arg22;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IVaultKeeperService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IVaultKeeperService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result = isInitialized(_arg0);
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    byte[] _arg1 = data.createByteArray();
                    byte[] _arg23 = data.createByteArray();
                    byte[] _arg3 = data.createByteArray();
                    byte[] _arg4 = data.createByteArray();
                    data.enforceNoDataAvail();
                    int _result2 = initialize(_arg02, _arg1, _arg23, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    byte[] _arg12 = data.createByteArray();
                    byte[] _arg24 = data.createByteArray();
                    byte[] _arg32 = data.createByteArray();
                    data.enforceNoDataAvail();
                    int _result3 = destroy(_arg03, _arg12, _arg24, _arg32);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg13 = data.readInt();
                    int _arg2_length = data.readInt();
                    if (_arg2_length < 0) {
                        _arg2 = null;
                    } else {
                        _arg2 = new int[_arg2_length];
                    }
                    data.enforceNoDataAvail();
                    byte[] _result4 = read(_arg04, _arg13, _arg2);
                    reply.writeNoException();
                    reply.writeByteArray(_result4);
                    reply.writeIntArray(_arg2);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    int _arg14 = data.readInt();
                    byte[] _arg25 = data.createByteArray();
                    byte[] _arg33 = data.createByteArray();
                    byte[] _arg42 = data.createByteArray();
                    data.enforceNoDataAvail();
                    int _result5 = write(_arg05, _arg14, _arg25, _arg33, _arg42);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    int _arg15 = data.readInt();
                    int _arg2_length2 = data.readInt();
                    if (_arg2_length2 < 0) {
                        _arg22 = null;
                    } else {
                        _arg22 = new int[_arg2_length2];
                    }
                    data.enforceNoDataAvail();
                    byte[] _result6 = sensitiveBox(_arg06, _arg15, _arg22);
                    reply.writeNoException();
                    reply.writeByteArray(_result6);
                    reply.writeIntArray(_arg22);
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    byte[] _arg16 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result7 = encryptMessage(_arg07, _arg16);
                    reply.writeNoException();
                    reply.writeByteArray(_result7);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result8 = migrationStorage(_arg08);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    byte[] _arg17 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result9 = verifyCertificate(_arg09, _arg17);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    data.enforceNoDataAvail();
                    int _result10 = checkDataWritable(_arg010);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 11:
                    String _arg011 = data.readString();
                    data.enforceNoDataAvail();
                    int _result11 = generateHotpCode(_arg011);
                    reply.writeNoException();
                    reply.writeInt(_result11);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IVaultKeeperService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVaultKeeperService.DESCRIPTOR;
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public boolean isInitialized(String vaultName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int initialize(
                    String vaultName, byte[] key, byte[] initUnsheltered, byte[] cert, byte[] sig)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    _data.writeByteArray(key);
                    _data.writeByteArray(initUnsheltered);
                    _data.writeByteArray(cert);
                    _data.writeByteArray(sig);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int destroy(String vaultName, byte[] hmac, byte[] cert, byte[] sig)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    _data.writeByteArray(hmac);
                    _data.writeByteArray(cert);
                    _data.writeByteArray(sig);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public byte[] read(String vaultName, int type, int[] errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    _data.writeInt(type);
                    _data.writeInt(errorCode.length);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.readIntArray(errorCode);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int write(String vaultName, int type, byte[] data, byte[] cert, byte[] sig)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    _data.writeInt(type);
                    _data.writeByteArray(data);
                    _data.writeByteArray(cert);
                    _data.writeByteArray(sig);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public byte[] sensitiveBox(String vaultName, int type, int[] errorCode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    _data.writeInt(type);
                    _data.writeInt(errorCode.length);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    _reply.readIntArray(errorCode);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public byte[] encryptMessage(String vaultName, byte[] msg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    _data.writeByteArray(msg);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public boolean migrationStorage(String vaultName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public boolean verifyCertificate(String vaultName, byte[] cert) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    _data.writeByteArray(cert);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int checkDataWritable(String vaultName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.service.vaultkeeper.IVaultKeeperService
            public int generateHotpCode(String vaultName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IVaultKeeperService.DESCRIPTOR);
                    _data.writeString(vaultName);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
