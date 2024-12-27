package android.content;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.SemIRCPCallback;
import java.util.List;

/* loaded from: classes.dex */
public interface IRCPInterface extends IInterface {
    public static final String DESCRIPTOR = "android.content.IRCPInterface";

    void cancel(long j) throws RemoteException;

    int copyFile(int i, String str, int i2, String str2) throws RemoteException;

    long copyFiles(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback) throws RemoteException;

    long copyFiles2(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback, String str) throws RemoteException;

    String getErrorMessage(int i) throws RemoteException;

    Bundle getFileInfo(String str, int i) throws RemoteException;

    List<String> getFiles(String str, int i) throws RemoteException;

    boolean isFileExist(String str, int i) throws RemoteException;

    int moveFile(int i, String str, int i2, String str2) throws RemoteException;

    long moveFiles(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback) throws RemoteException;

    long moveFiles2(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback, String str) throws RemoteException;

    long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2) throws RemoteException;

    long moveUnlimitedFilesForApp(int i, Uri uri, int i2, int i3) throws RemoteException;

    public static class Default implements IRCPInterface {
        @Override // android.content.IRCPInterface
        public long copyFiles(int srcContainerId, List<String> srcFilePaths, int destContainerId, List<String> destFilePaths, SemIRCPCallback callback) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public long moveFiles(int srcContainerId, List<String> srcFilePaths, int destContainerId, List<String> destFilePaths, SemIRCPCallback callback) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public long moveUnlimitedFilesForApp(int requestApp, Uri uri, int fileCount, int containerId) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public int copyFile(int srcContainerId, String srcFilePath, int destContainerId, String destFilePath) throws RemoteException {
            return 0;
        }

        @Override // android.content.IRCPInterface
        public int moveFile(int srcContainerId, String srcFilePath, int destContainerId, String destFilePath) throws RemoteException {
            return 0;
        }

        @Override // android.content.IRCPInterface
        public String getErrorMessage(int errorId) throws RemoteException {
            return null;
        }

        @Override // android.content.IRCPInterface
        public boolean isFileExist(String path, int containerId) throws RemoteException {
            return false;
        }

        @Override // android.content.IRCPInterface
        public List<String> getFiles(String path, int containerId) throws RemoteException {
            return null;
        }

        @Override // android.content.IRCPInterface
        public Bundle getFileInfo(String path, int containerId) throws RemoteException {
            return null;
        }

        @Override // android.content.IRCPInterface
        public void cancel(long threadId) throws RemoteException {
        }

        @Override // android.content.IRCPInterface
        public long moveFilesForAppEx(int requestApp, List<String> srcFilePaths, List<String> destFilePaths, int containerId) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public long copyFiles2(int srcContainerId, List<String> srcFilePaths, int destContainerId, List<String> destFilePaths, SemIRCPCallback callback, String mSourceClassName) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public long moveFiles2(int srcContainerId, List<String> srcFilePaths, int destContainerId, List<String> destFilePaths, SemIRCPCallback callback, String mSourceClassName) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRCPInterface {
        static final int TRANSACTION_cancel = 10;
        static final int TRANSACTION_copyFile = 4;
        static final int TRANSACTION_copyFiles = 1;
        static final int TRANSACTION_copyFiles2 = 12;
        static final int TRANSACTION_getErrorMessage = 6;
        static final int TRANSACTION_getFileInfo = 9;
        static final int TRANSACTION_getFiles = 8;
        static final int TRANSACTION_isFileExist = 7;
        static final int TRANSACTION_moveFile = 5;
        static final int TRANSACTION_moveFiles = 2;
        static final int TRANSACTION_moveFiles2 = 13;
        static final int TRANSACTION_moveFilesForAppEx = 11;
        static final int TRANSACTION_moveUnlimitedFilesForApp = 3;

        public Stub() {
            attachInterface(this, IRCPInterface.DESCRIPTOR);
        }

        public static IRCPInterface asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRCPInterface.DESCRIPTOR);
            if (iin != null && (iin instanceof IRCPInterface)) {
                return (IRCPInterface) iin;
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
                    return "copyFiles";
                case 2:
                    return "moveFiles";
                case 3:
                    return "moveUnlimitedFilesForApp";
                case 4:
                    return "copyFile";
                case 5:
                    return "moveFile";
                case 6:
                    return "getErrorMessage";
                case 7:
                    return "isFileExist";
                case 8:
                    return "getFiles";
                case 9:
                    return "getFileInfo";
                case 10:
                    return "cancel";
                case 11:
                    return "moveFilesForAppEx";
                case 12:
                    return "copyFiles2";
                case 13:
                    return "moveFiles2";
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
                data.enforceInterface(IRCPInterface.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRCPInterface.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    List<String> _arg1 = data.createStringArrayList();
                    int _arg2 = data.readInt();
                    List<String> _arg3 = data.createStringArrayList();
                    SemIRCPCallback _arg4 = SemIRCPCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    long _result = copyFiles(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeLong(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    List<String> _arg12 = data.createStringArrayList();
                    int _arg22 = data.readInt();
                    List<String> _arg32 = data.createStringArrayList();
                    SemIRCPCallback _arg42 = SemIRCPCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    long _result2 = moveFiles(_arg02, _arg12, _arg22, _arg32, _arg42);
                    reply.writeNoException();
                    reply.writeLong(_result2);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    Uri _arg13 = (Uri) data.readTypedObject(Uri.CREATOR);
                    int _arg23 = data.readInt();
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result3 = moveUnlimitedFilesForApp(_arg03, _arg13, _arg23, _arg33);
                    reply.writeNoException();
                    reply.writeLong(_result3);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    String _arg14 = data.readString();
                    int _arg24 = data.readInt();
                    String _arg34 = data.readString();
                    data.enforceNoDataAvail();
                    int _result4 = copyFile(_arg04, _arg14, _arg24, _arg34);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    String _arg15 = data.readString();
                    int _arg25 = data.readInt();
                    String _arg35 = data.readString();
                    data.enforceNoDataAvail();
                    int _result5 = moveFile(_arg05, _arg15, _arg25, _arg35);
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result6 = getErrorMessage(_arg06);
                    reply.writeNoException();
                    reply.writeString(_result6);
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result7 = isFileExist(_arg07, _arg16);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    List<String> _result8 = getFiles(_arg08, _arg17);
                    reply.writeNoException();
                    reply.writeStringList(_result8);
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result9 = getFileInfo(_arg09, _arg18);
                    reply.writeNoException();
                    reply.writeTypedObject(_result9, 1);
                    return true;
                case 10:
                    long _arg010 = data.readLong();
                    data.enforceNoDataAvail();
                    cancel(_arg010);
                    reply.writeNoException();
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    List<String> _arg19 = data.createStringArrayList();
                    List<String> _arg26 = data.createStringArrayList();
                    int _arg36 = data.readInt();
                    data.enforceNoDataAvail();
                    long _result10 = moveFilesForAppEx(_arg011, _arg19, _arg26, _arg36);
                    reply.writeNoException();
                    reply.writeLong(_result10);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    List<String> _arg110 = data.createStringArrayList();
                    int _arg27 = data.readInt();
                    List<String> _arg37 = data.createStringArrayList();
                    SemIRCPCallback _arg43 = SemIRCPCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg5 = data.readString();
                    data.enforceNoDataAvail();
                    long _result11 = copyFiles2(_arg012, _arg110, _arg27, _arg37, _arg43, _arg5);
                    reply.writeNoException();
                    reply.writeLong(_result11);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    List<String> _arg111 = data.createStringArrayList();
                    int _arg28 = data.readInt();
                    List<String> _arg38 = data.createStringArrayList();
                    SemIRCPCallback _arg44 = SemIRCPCallback.Stub.asInterface(data.readStrongBinder());
                    String _arg52 = data.readString();
                    data.enforceNoDataAvail();
                    long _result12 = moveFiles2(_arg013, _arg111, _arg28, _arg38, _arg44, _arg52);
                    reply.writeNoException();
                    reply.writeLong(_result12);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRCPInterface {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRCPInterface.DESCRIPTOR;
            }

            @Override // android.content.IRCPInterface
            public long copyFiles(int srcContainerId, List<String> srcFilePaths, int destContainerId, List<String> destFilePaths, SemIRCPCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeStringList(srcFilePaths);
                    _data.writeInt(destContainerId);
                    _data.writeStringList(destFilePaths);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveFiles(int srcContainerId, List<String> srcFilePaths, int destContainerId, List<String> destFilePaths, SemIRCPCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeStringList(srcFilePaths);
                    _data.writeInt(destContainerId);
                    _data.writeStringList(destFilePaths);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveUnlimitedFilesForApp(int requestApp, Uri uri, int fileCount, int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(requestApp);
                    _data.writeTypedObject(uri, 0);
                    _data.writeInt(fileCount);
                    _data.writeInt(containerId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public int copyFile(int srcContainerId, String srcFilePath, int destContainerId, String destFilePath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public int moveFile(int srcContainerId, String srcFilePath, int destContainerId, String destFilePath) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public String getErrorMessage(int errorId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(errorId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public boolean isFileExist(String path, int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public List<String> getFiles(String path, int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public Bundle getFileInfo(String path, int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeString(path);
                    _data.writeInt(containerId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public void cancel(long threadId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeLong(threadId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveFilesForAppEx(int requestApp, List<String> srcFilePaths, List<String> destFilePaths, int containerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(requestApp);
                    _data.writeStringList(srcFilePaths);
                    _data.writeStringList(destFilePaths);
                    _data.writeInt(containerId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long copyFiles2(int srcContainerId, List<String> srcFilePaths, int destContainerId, List<String> destFilePaths, SemIRCPCallback callback, String mSourceClassName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeStringList(srcFilePaths);
                    _data.writeInt(destContainerId);
                    _data.writeStringList(destFilePaths);
                    _data.writeStrongInterface(callback);
                    _data.writeString(mSourceClassName);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveFiles2(int srcContainerId, List<String> srcFilePaths, int destContainerId, List<String> destFilePaths, SemIRCPCallback callback, String mSourceClassName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeStringList(srcFilePaths);
                    _data.writeInt(destContainerId);
                    _data.writeStringList(destFilePaths);
                    _data.writeStrongInterface(callback);
                    _data.writeString(mSourceClassName);
                    this.mRemote.transact(13, _data, _reply, 0);
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
            return 12;
        }
    }
}
