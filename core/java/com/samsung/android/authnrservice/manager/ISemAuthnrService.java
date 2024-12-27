package com.samsung.android.authnrservice.manager;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes5.dex */
public interface ISemAuthnrService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.authnrservice.manager.ISemAuthnrService";

    boolean deleteFile(String str) throws RemoteException;

    byte[] getDrkKeyHandle() throws RemoteException;

    List<String> getFiles(String str, String str2) throws RemoteException;

    List<String> getMatchedFilePaths(String str, String str2) throws RemoteException;

    int getVersion() throws RemoteException;

    byte[] getWrappedObject(byte[] bArr) throws RemoteException;

    boolean initialize(ParcelFileDescriptor parcelFileDescriptor, long j, long j2) throws RemoteException;

    boolean initializeDrk() throws RemoteException;

    boolean initializePreloadedTa(int i) throws RemoteException;

    boolean initializeWithPreloadedTa() throws RemoteException;

    byte[] process(byte[] bArr) throws RemoteException;

    byte[] processPreloadedTa(int i, byte[] bArr) throws RemoteException;

    byte[] processWithPreloadedTa(byte[] bArr, String str) throws RemoteException;

    String readFile(String str) throws RemoteException;

    boolean setChallenge(byte[] bArr) throws RemoteException;

    boolean terminate() throws RemoteException;

    boolean terminateDrk() throws RemoteException;

    boolean terminatePreloadedTa(int i) throws RemoteException;

    boolean terminateWithPreloadedTa() throws RemoteException;

    boolean writeFile(byte[] bArr, String str) throws RemoteException;

    public static class Default implements ISemAuthnrService {
        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public int getVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean initialize(ParcelFileDescriptor fd, long offset, long len) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean terminate() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] process(byte[] blobInput) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean setChallenge(byte[] challenge) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] getWrappedObject(byte[] challenge) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean initializeDrk() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean terminateDrk() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] getDrkKeyHandle() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean writeFile(byte[] data, String path) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean deleteFile(String path) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public List<String> getFiles(String path, String filter) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean initializeWithPreloadedTa() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean terminateWithPreloadedTa() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] processWithPreloadedTa(byte[] data, String appId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public String readFile(String path) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public List<String> getMatchedFilePaths(String path, String filter) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean initializePreloadedTa(int trustedAppType) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public boolean terminatePreloadedTa(int trustedAppType) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
        public byte[] processPreloadedTa(int trustedAppType, byte[] data) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ISemAuthnrService {
        static final int TRANSACTION_deleteFile = 11;
        static final int TRANSACTION_getDrkKeyHandle = 9;
        static final int TRANSACTION_getFiles = 12;
        static final int TRANSACTION_getMatchedFilePaths = 17;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_getWrappedObject = 6;
        static final int TRANSACTION_initialize = 2;
        static final int TRANSACTION_initializeDrk = 7;
        static final int TRANSACTION_initializePreloadedTa = 18;
        static final int TRANSACTION_initializeWithPreloadedTa = 13;
        static final int TRANSACTION_process = 4;
        static final int TRANSACTION_processPreloadedTa = 20;
        static final int TRANSACTION_processWithPreloadedTa = 15;
        static final int TRANSACTION_readFile = 16;
        static final int TRANSACTION_setChallenge = 5;
        static final int TRANSACTION_terminate = 3;
        static final int TRANSACTION_terminateDrk = 8;
        static final int TRANSACTION_terminatePreloadedTa = 19;
        static final int TRANSACTION_terminateWithPreloadedTa = 14;
        static final int TRANSACTION_writeFile = 10;

        public Stub() {
            attachInterface(this, ISemAuthnrService.DESCRIPTOR);
        }

        public static ISemAuthnrService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemAuthnrService.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemAuthnrService)) {
                return (ISemAuthnrService) iin;
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
                    return "getVersion";
                case 2:
                    return "initialize";
                case 3:
                    return "terminate";
                case 4:
                    return "process";
                case 5:
                    return "setChallenge";
                case 6:
                    return "getWrappedObject";
                case 7:
                    return "initializeDrk";
                case 8:
                    return "terminateDrk";
                case 9:
                    return "getDrkKeyHandle";
                case 10:
                    return "writeFile";
                case 11:
                    return "deleteFile";
                case 12:
                    return "getFiles";
                case 13:
                    return "initializeWithPreloadedTa";
                case 14:
                    return "terminateWithPreloadedTa";
                case 15:
                    return "processWithPreloadedTa";
                case 16:
                    return "readFile";
                case 17:
                    return "getMatchedFilePaths";
                case 18:
                    return "initializePreloadedTa";
                case 19:
                    return "terminatePreloadedTa";
                case 20:
                    return "processPreloadedTa";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemAuthnrService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemAuthnrService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _result = getVersion();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    ParcelFileDescriptor _arg0 = (ParcelFileDescriptor) data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg1 = data.readLong();
                    long _arg2 = data.readLong();
                    data.enforceNoDataAvail();
                    boolean _result2 = initialize(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 3:
                    boolean _result3 = terminate();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 4:
                    byte[] _arg02 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result4 = process(_arg02);
                    reply.writeNoException();
                    reply.writeByteArray(_result4);
                    return true;
                case 5:
                    byte[] _arg03 = data.createByteArray();
                    data.enforceNoDataAvail();
                    boolean _result5 = setChallenge(_arg03);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 6:
                    byte[] _arg04 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result6 = getWrappedObject(_arg04);
                    reply.writeNoException();
                    reply.writeByteArray(_result6);
                    return true;
                case 7:
                    boolean _result7 = initializeDrk();
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 8:
                    boolean _result8 = terminateDrk();
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 9:
                    byte[] _result9 = getDrkKeyHandle();
                    reply.writeNoException();
                    reply.writeByteArray(_result9);
                    return true;
                case 10:
                    byte[] _arg05 = data.createByteArray();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result10 = writeFile(_arg05, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 11:
                    String _arg06 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result11 = deleteFile(_arg06);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 12:
                    String _arg07 = data.readString();
                    String _arg13 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result12 = getFiles(_arg07, _arg13);
                    reply.writeNoException();
                    reply.writeStringList(_result12);
                    return true;
                case 13:
                    boolean _result13 = initializeWithPreloadedTa();
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 14:
                    boolean _result14 = terminateWithPreloadedTa();
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 15:
                    byte[] _arg08 = data.createByteArray();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    byte[] _result15 = processWithPreloadedTa(_arg08, _arg14);
                    reply.writeNoException();
                    reply.writeByteArray(_result15);
                    return true;
                case 16:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    String _result16 = readFile(_arg09);
                    reply.writeNoException();
                    reply.writeString(_result16);
                    return true;
                case 17:
                    String _arg010 = data.readString();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    List<String> _result17 = getMatchedFilePaths(_arg010, _arg15);
                    reply.writeNoException();
                    reply.writeStringList(_result17);
                    return true;
                case 18:
                    int _arg011 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result18 = initializePreloadedTa(_arg011);
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 19:
                    int _arg012 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result19 = terminatePreloadedTa(_arg012);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 20:
                    int _arg013 = data.readInt();
                    byte[] _arg16 = data.createByteArray();
                    data.enforceNoDataAvail();
                    byte[] _result20 = processPreloadedTa(_arg013, _arg16);
                    reply.writeNoException();
                    reply.writeByteArray(_result20);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemAuthnrService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemAuthnrService.DESCRIPTOR;
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public int getVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean initialize(ParcelFileDescriptor fd, long offset, long len) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeTypedObject(fd, 0);
                    _data.writeLong(offset);
                    _data.writeLong(len);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean terminate() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] process(byte[] blobInput) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeByteArray(blobInput);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean setChallenge(byte[] challenge) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeByteArray(challenge);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] getWrappedObject(byte[] challenge) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeByteArray(challenge);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean initializeDrk() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean terminateDrk() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] getDrkKeyHandle() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean writeFile(byte[] data, String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeByteArray(data);
                    _data.writeString(path);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean deleteFile(String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public List<String> getFiles(String path, String filter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeString(filter);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean initializeWithPreloadedTa() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean terminateWithPreloadedTa() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] processWithPreloadedTa(byte[] data, String appId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeByteArray(data);
                    _data.writeString(appId);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public String readFile(String path) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeString(path);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public List<String> getMatchedFilePaths(String path, String filter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeString(filter);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean initializePreloadedTa(int trustedAppType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeInt(trustedAppType);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public boolean terminatePreloadedTa(int trustedAppType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeInt(trustedAppType);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.authnrservice.manager.ISemAuthnrService
            public byte[] processPreloadedTa(int trustedAppType, byte[] data) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemAuthnrService.DESCRIPTOR);
                    _data.writeInt(trustedAppType);
                    _data.writeByteArray(data);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }
    }
}
