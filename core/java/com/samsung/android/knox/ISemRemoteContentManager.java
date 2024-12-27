package com.samsung.android.knox;

import android.content.IRCPInterface;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/* loaded from: classes6.dex */
public interface ISemRemoteContentManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ISemRemoteContentManager";

    void cancelCopyChunks(long j) throws RemoteException;

    int copyChunks(int i, String str, int i2, String str2, long j, int i3, long j2, boolean z)
            throws RemoteException;

    int copyFile(int i, String str, int i2, String str2) throws RemoteException;

    int copyFileInternal(int i, String str, int i2, String str2) throws RemoteException;

    boolean deleteFile(String str, int i) throws RemoteException;

    Bundle exchangeData(String str, int i, Bundle bundle) throws RemoteException;

    Bundle getFileInfo(String str, int i) throws RemoteException;

    List<String> getFiles(String str, int i) throws RemoteException;

    IRCPInterface getRCPInterface() throws RemoteException;

    boolean isFileExist(String str, int i) throws RemoteException;

    int moveFile(int i, String str, int i2, String str2) throws RemoteException;

    long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2)
            throws RemoteException;

    long moveUnlimitedFiles(int i, Uri uri, int i2, int i3) throws RemoteException;

    void registerRCPInterface(IRCPInterface iRCPInterface, int i) throws RemoteException;

    public static class Default implements ISemRemoteContentManager {
        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public void registerRCPInterface(IRCPInterface rcpInterface, int userId)
                throws RemoteException {}

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public IRCPInterface getRCPInterface() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public int moveFile(
                int srcContainerId, String srcFilePath, int destContainerId, String destFilePath)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public int copyFileInternal(
                int srcContainerId, String srcFilePath, int destContainerId, String destFilePath)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public int copyFile(
                int srcContainerId, String srcFilePath, int destContainerId, String destFilePath)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public long moveUnlimitedFiles(int requestApp, Uri uri, int fileCount, int containerId)
                throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public boolean isFileExist(String path, int containerId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public List<String> getFiles(String path, int containerId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public boolean deleteFile(String path, int containerId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public Bundle getFileInfo(String path, int containerId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public int copyChunks(
                int srcContainerId,
                String srcFilePath,
                int destContainerId,
                String destFilePath,
                long offset,
                int length,
                long sessionId,
                boolean deleteSrc)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public void cancelCopyChunks(long sessionId) throws RemoteException {}

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public Bundle exchangeData(String pkgName, int userId, Bundle bundle)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public long moveFilesForAppEx(
                int requestApp,
                List<String> srcFilePaths,
                List<String> destFilePaths,
                int containerId)
                throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemRemoteContentManager {
        static final int TRANSACTION_cancelCopyChunks = 12;
        static final int TRANSACTION_copyChunks = 11;
        static final int TRANSACTION_copyFile = 5;
        static final int TRANSACTION_copyFileInternal = 4;
        static final int TRANSACTION_deleteFile = 9;
        static final int TRANSACTION_exchangeData = 13;
        static final int TRANSACTION_getFileInfo = 10;
        static final int TRANSACTION_getFiles = 8;
        static final int TRANSACTION_getRCPInterface = 2;
        static final int TRANSACTION_isFileExist = 7;
        static final int TRANSACTION_moveFile = 3;
        static final int TRANSACTION_moveFilesForAppEx = 14;
        static final int TRANSACTION_moveUnlimitedFiles = 6;
        static final int TRANSACTION_registerRCPInterface = 1;

        public Stub() {
            attachInterface(this, ISemRemoteContentManager.DESCRIPTOR);
        }

        public static ISemRemoteContentManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemRemoteContentManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemRemoteContentManager)) {
                return (ISemRemoteContentManager) iin;
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
                    return "registerRCPInterface";
                case 2:
                    return "getRCPInterface";
                case 3:
                    return "moveFile";
                case 4:
                    return "copyFileInternal";
                case 5:
                    return "copyFile";
                case 6:
                    return "moveUnlimitedFiles";
                case 7:
                    return "isFileExist";
                case 8:
                    return "getFiles";
                case 9:
                    return "deleteFile";
                case 10:
                    return "getFileInfo";
                case 11:
                    return "copyChunks";
                case 12:
                    return "cancelCopyChunks";
                case 13:
                    return "exchangeData";
                case 14:
                    return "moveFilesForAppEx";
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
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISemRemoteContentManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemRemoteContentManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IRCPInterface _arg0 = IRCPInterface.Stub.asInterface(data.readStrongBinder());
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    registerRCPInterface(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    IRCPInterface _result = getRCPInterface();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                case 3:
                    int _arg02 = data.readInt();
                    String _arg12 = data.readString();
                    int _arg2 = data.readInt();
                    String _arg3 = data.readString();
                    data.enforceNoDataAvail();
                    int _result2 = moveFile(_arg02, _arg12, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    String _arg13 = data.readString();
                    int _arg22 = data.readInt();
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    int _result3 = copyFileInternal(_arg03, _arg13, _arg22, _arg32);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 5:
                    int _arg04 = data.readInt();
                    String _arg14 = data.readString();
                    int _arg23 = data.readInt();
                    String _arg33 = data.readString();
                    data.enforceNoDataAvail();
                    int _result4 = copyFile(_arg04, _arg14, _arg23, _arg33);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 6:
                    int _arg05 = data.readInt();
                    Uri _arg15 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg24 = data.readInt();
                    int _arg34 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result5 = moveUnlimitedFiles(_arg05, _arg15, _arg24, _arg34);
                    reply.writeNoException();
                    reply.writeLong(_result5);
                    return true;
                case 7:
                    String _arg06 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result6 = isFileExist(_arg06, _arg16);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 8:
                    String _arg07 = data.readString();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result7 = getFiles(_arg07, _arg17);
                    reply.writeNoException();
                    reply.writeStringList(_result7);
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = deleteFile(_arg08, _arg18);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 10:
                    String _arg09 = data.readString();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result9 = getFileInfo(_arg09, _arg19);
                    reply.writeNoException();
                    reply.writeTypedObject(_result9, 1);
                    return true;
                case 11:
                    int _arg010 = data.readInt();
                    String _arg110 = data.readString();
                    int _arg25 = data.readInt();
                    String _arg35 = data.readString();
                    long _arg4 = data.readLong();
                    int _arg5 = data.readInt();
                    long _arg6 = data.readLong();
                    boolean _arg7 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result10 =
                            copyChunks(
                                    _arg010, _arg110, _arg25, _arg35, _arg4, _arg5, _arg6, _arg7);
                    reply.writeNoException();
                    reply.writeInt(_result10);
                    return true;
                case 12:
                    long _arg011 = data.readLong();
                    data.enforceNoDataAvail();
                    cancelCopyChunks(_arg011);
                    reply.writeNoException();
                    return true;
                case 13:
                    String _arg012 = data.readString();
                    int _arg111 = data.readInt();
                    Bundle _arg26 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    Bundle _result11 = exchangeData(_arg012, _arg111, _arg26);
                    reply.writeNoException();
                    reply.writeTypedObject(_result11, 1);
                    return true;
                case 14:
                    int _arg013 = data.readInt();
                    List<String> _arg112 = data.createStringArrayList();
                    List<String> _arg27 = data.createStringArrayList();
                    int _arg36 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result12 = moveFilesForAppEx(_arg013, _arg112, _arg27, _arg36);
                    reply.writeNoException();
                    reply.writeLong(_result12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemRemoteContentManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemRemoteContentManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public void registerRCPInterface(IRCPInterface rcpInterface, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeStrongInterface(rcpInterface);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public IRCPInterface getRCPInterface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    IRCPInterface _result =
                            IRCPInterface.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int moveFile(
                    int srcContainerId,
                    String srcFilePath,
                    int destContainerId,
                    String destFilePath)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeString(srcFilePath);
                    _data.writeInt(destContainerId);
                    _data.writeString(destFilePath);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int copyFileInternal(
                    int srcContainerId,
                    String srcFilePath,
                    int destContainerId,
                    String destFilePath)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeString(srcFilePath);
                    _data.writeInt(destContainerId);
                    _data.writeString(destFilePath);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int copyFile(
                    int srcContainerId,
                    String srcFilePath,
                    int destContainerId,
                    String destFilePath)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeString(srcFilePath);
                    _data.writeInt(destContainerId);
                    _data.writeString(destFilePath);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public long moveUnlimitedFiles(int requestApp, Uri uri, int fileCount, int containerId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeInt(requestApp);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(fileCount);
                    _data.writeInt(containerId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public boolean isFileExist(String path, int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeInt(containerId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public List<String> getFiles(String path, int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeInt(containerId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public boolean deleteFile(String path, int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeInt(containerId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public Bundle getFileInfo(String path, int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeInt(containerId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int copyChunks(
                    int srcContainerId,
                    String srcFilePath,
                    int destContainerId,
                    String destFilePath,
                    long offset,
                    int length,
                    long sessionId,
                    boolean deleteSrc)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeString(srcFilePath);
                    _data.writeInt(destContainerId);
                    _data.writeString(destFilePath);
                    _data.writeLong(offset);
                    _data.writeInt(length);
                    _data.writeLong(sessionId);
                    _data.writeBoolean(deleteSrc);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public void cancelCopyChunks(long sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeLong(sessionId);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public Bundle exchangeData(String pkgName, int userId, Bundle bundle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeInt(userId);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public long moveFilesForAppEx(
                    int requestApp,
                    List<String> srcFilePaths,
                    List<String> destFilePaths,
                    int containerId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    _data.writeInt(requestApp);
                    _data.writeStringList(srcFilePaths);
                    _data.writeStringList(destFilePaths);
                    _data.writeInt(containerId);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
