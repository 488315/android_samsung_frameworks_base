package android.content;

import android.net.Uri;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import com.samsung.android.knox.SemIRCPCallback;
import java.util.List;

/* loaded from: classes.dex */
public interface IRCPInterface extends IInterface {
    public static final String DESCRIPTOR = "android.content.IRCPInterface";

    void cancel(long j) throws RemoteException;

    void cancelCopyChunks(long j) throws RemoteException;

    int copyChunks(int i, String str, int i2, String str2, long j, int i3, long j2, boolean z) throws RemoteException;

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

    long moveFilesForApp(int i, List<String> list, List<String> list2) throws RemoteException;

    long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2) throws RemoteException;

    long moveUnlimitedFiles(int i, int i2, Uri uri, SemIRCPCallback semIRCPCallback) throws RemoteException;

    long moveUnlimitedFiles2(int i, int i2, Uri uri, SemIRCPCallback semIRCPCallback, String str) throws RemoteException;

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
        public long moveUnlimitedFiles(int srcContainerId, int destContainerId, Uri uri, SemIRCPCallback callback) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public long moveFilesForApp(int requestApp, List<String> srcFilePaths, List<String> destFilePaths) throws RemoteException {
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
        public int copyChunks(int srcContainerId, String srcFilePath, int destContainerId, String destFilePath, long offset, int length, long sessionId, boolean deleteSrc) throws RemoteException {
            return 0;
        }

        @Override // android.content.IRCPInterface
        public void cancelCopyChunks(long sessionId) throws RemoteException {
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

        @Override // android.content.IRCPInterface
        public long moveUnlimitedFiles2(int srcContainerId, int destContainerId, Uri uri, SemIRCPCallback callback, String mSourceClassName) throws RemoteException {
            return 0L;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRCPInterface {
        static final int TRANSACTION_cancel = 14;
        static final int TRANSACTION_cancelCopyChunks = 13;
        static final int TRANSACTION_copyChunks = 12;
        static final int TRANSACTION_copyFile = 6;
        static final int TRANSACTION_copyFiles = 1;
        static final int TRANSACTION_copyFiles2 = 16;
        static final int TRANSACTION_getErrorMessage = 8;
        static final int TRANSACTION_getFileInfo = 11;
        static final int TRANSACTION_getFiles = 10;
        static final int TRANSACTION_isFileExist = 9;
        static final int TRANSACTION_moveFile = 7;
        static final int TRANSACTION_moveFiles = 2;
        static final int TRANSACTION_moveFiles2 = 17;
        static final int TRANSACTION_moveFilesForApp = 4;
        static final int TRANSACTION_moveFilesForAppEx = 15;
        static final int TRANSACTION_moveUnlimitedFiles = 3;
        static final int TRANSACTION_moveUnlimitedFiles2 = 18;
        static final int TRANSACTION_moveUnlimitedFilesForApp = 5;

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

        @Override // android.p009os.IInterface
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
                    return "moveUnlimitedFiles";
                case 4:
                    return "moveFilesForApp";
                case 5:
                    return "moveUnlimitedFilesForApp";
                case 6:
                    return "copyFile";
                case 7:
                    return "moveFile";
                case 8:
                    return "getErrorMessage";
                case 9:
                    return "isFileExist";
                case 10:
                    return "getFiles";
                case 11:
                    return "getFileInfo";
                case 12:
                    return "copyChunks";
                case 13:
                    return "cancelCopyChunks";
                case 14:
                    return "cancel";
                case 15:
                    return "moveFilesForAppEx";
                case 16:
                    return "copyFiles2";
                case 17:
                    return "moveFiles2";
                case 18:
                    return "moveUnlimitedFiles2";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IRCPInterface.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IRCPInterface.DESCRIPTOR);
                    return true;
                default:
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
                            int _arg13 = data.readInt();
                            Uri _arg23 = (Uri) data.readTypedObject(Uri.CREATOR);
                            SemIRCPCallback _arg33 = SemIRCPCallback.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            long _result3 = moveUnlimitedFiles(_arg03, _arg13, _arg23, _arg33);
                            reply.writeNoException();
                            reply.writeLong(_result3);
                            return true;
                        case 4:
                            int _arg04 = data.readInt();
                            List<String> _arg14 = data.createStringArrayList();
                            List<String> _arg24 = data.createStringArrayList();
                            data.enforceNoDataAvail();
                            long _result4 = moveFilesForApp(_arg04, _arg14, _arg24);
                            reply.writeNoException();
                            reply.writeLong(_result4);
                            return true;
                        case 5:
                            int _arg05 = data.readInt();
                            Uri _arg15 = (Uri) data.readTypedObject(Uri.CREATOR);
                            int _arg25 = data.readInt();
                            int _arg34 = data.readInt();
                            data.enforceNoDataAvail();
                            long _result5 = moveUnlimitedFilesForApp(_arg05, _arg15, _arg25, _arg34);
                            reply.writeNoException();
                            reply.writeLong(_result5);
                            return true;
                        case 6:
                            int _arg06 = data.readInt();
                            String _arg16 = data.readString();
                            int _arg26 = data.readInt();
                            String _arg35 = data.readString();
                            data.enforceNoDataAvail();
                            int _result6 = copyFile(_arg06, _arg16, _arg26, _arg35);
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            return true;
                        case 7:
                            int _arg07 = data.readInt();
                            String _arg17 = data.readString();
                            int _arg27 = data.readInt();
                            String _arg36 = data.readString();
                            data.enforceNoDataAvail();
                            int _result7 = moveFile(_arg07, _arg17, _arg27, _arg36);
                            reply.writeNoException();
                            reply.writeInt(_result7);
                            return true;
                        case 8:
                            int _arg08 = data.readInt();
                            data.enforceNoDataAvail();
                            String _result8 = getErrorMessage(_arg08);
                            reply.writeNoException();
                            reply.writeString(_result8);
                            return true;
                        case 9:
                            String _arg09 = data.readString();
                            int _arg18 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result9 = isFileExist(_arg09, _arg18);
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 10:
                            String _arg010 = data.readString();
                            int _arg19 = data.readInt();
                            data.enforceNoDataAvail();
                            List<String> _result10 = getFiles(_arg010, _arg19);
                            reply.writeNoException();
                            reply.writeStringList(_result10);
                            return true;
                        case 11:
                            String _arg011 = data.readString();
                            int _arg110 = data.readInt();
                            data.enforceNoDataAvail();
                            Bundle _result11 = getFileInfo(_arg011, _arg110);
                            reply.writeNoException();
                            reply.writeTypedObject(_result11, 1);
                            return true;
                        case 12:
                            int _arg012 = data.readInt();
                            String _arg111 = data.readString();
                            int _arg28 = data.readInt();
                            String _arg37 = data.readString();
                            long _arg43 = data.readLong();
                            int _arg5 = data.readInt();
                            long _arg6 = data.readLong();
                            boolean _arg7 = data.readBoolean();
                            data.enforceNoDataAvail();
                            int _result12 = copyChunks(_arg012, _arg111, _arg28, _arg37, _arg43, _arg5, _arg6, _arg7);
                            reply.writeNoException();
                            reply.writeInt(_result12);
                            return true;
                        case 13:
                            long _arg013 = data.readLong();
                            data.enforceNoDataAvail();
                            cancelCopyChunks(_arg013);
                            reply.writeNoException();
                            return true;
                        case 14:
                            long _arg014 = data.readLong();
                            data.enforceNoDataAvail();
                            cancel(_arg014);
                            reply.writeNoException();
                            return true;
                        case 15:
                            int _arg015 = data.readInt();
                            List<String> _arg112 = data.createStringArrayList();
                            List<String> _arg29 = data.createStringArrayList();
                            int _arg38 = data.readInt();
                            data.enforceNoDataAvail();
                            long _result13 = moveFilesForAppEx(_arg015, _arg112, _arg29, _arg38);
                            reply.writeNoException();
                            reply.writeLong(_result13);
                            return true;
                        case 16:
                            int _arg016 = data.readInt();
                            List<String> _arg113 = data.createStringArrayList();
                            int _arg210 = data.readInt();
                            List<String> _arg39 = data.createStringArrayList();
                            SemIRCPCallback _arg44 = SemIRCPCallback.Stub.asInterface(data.readStrongBinder());
                            String _arg52 = data.readString();
                            data.enforceNoDataAvail();
                            long _result14 = copyFiles2(_arg016, _arg113, _arg210, _arg39, _arg44, _arg52);
                            reply.writeNoException();
                            reply.writeLong(_result14);
                            return true;
                        case 17:
                            int _arg017 = data.readInt();
                            List<String> _arg114 = data.createStringArrayList();
                            int _arg211 = data.readInt();
                            List<String> _arg310 = data.createStringArrayList();
                            SemIRCPCallback _arg45 = SemIRCPCallback.Stub.asInterface(data.readStrongBinder());
                            String _arg53 = data.readString();
                            data.enforceNoDataAvail();
                            long _result15 = moveFiles2(_arg017, _arg114, _arg211, _arg310, _arg45, _arg53);
                            reply.writeNoException();
                            reply.writeLong(_result15);
                            return true;
                        case 18:
                            int _arg018 = data.readInt();
                            int _arg115 = data.readInt();
                            Uri _arg212 = (Uri) data.readTypedObject(Uri.CREATOR);
                            SemIRCPCallback _arg311 = SemIRCPCallback.Stub.asInterface(data.readStrongBinder());
                            String _arg46 = data.readString();
                            data.enforceNoDataAvail();
                            long _result16 = moveUnlimitedFiles2(_arg018, _arg115, _arg212, _arg311, _arg46);
                            reply.writeNoException();
                            reply.writeLong(_result16);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IRCPInterface {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
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
            public long moveUnlimitedFiles(int srcContainerId, int destContainerId, Uri uri, SemIRCPCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeInt(destContainerId);
                    _data.writeTypedObject(uri, 0);
                    _data.writeStrongInterface(callback);
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
            public long moveFilesForApp(int requestApp, List<String> srcFilePaths, List<String> destFilePaths) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(requestApp);
                    _data.writeStringList(srcFilePaths);
                    _data.writeStringList(destFilePaths);
                    this.mRemote.transact(4, _data, _reply, 0);
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
                    this.mRemote.transact(5, _data, _reply, 0);
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
                    this.mRemote.transact(6, _data, _reply, 0);
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
                    this.mRemote.transact(7, _data, _reply, 0);
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
                    this.mRemote.transact(8, _data, _reply, 0);
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
                    this.mRemote.transact(9, _data, _reply, 0);
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
                    this.mRemote.transact(10, _data, _reply, 0);
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
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public int copyChunks(int srcContainerId, String srcFilePath, int destContainerId, String destFilePath, long offset, int length, long sessionId, boolean deleteSrc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeString(srcFilePath);
                    _data.writeInt(destContainerId);
                    _data.writeString(destFilePath);
                    _data.writeLong(offset);
                    _data.writeInt(length);
                    _data.writeLong(sessionId);
                    _data.writeBoolean(deleteSrc);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public void cancelCopyChunks(long sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeLong(sessionId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
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
                    this.mRemote.transact(14, _data, _reply, 0);
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
                    this.mRemote.transact(15, _data, _reply, 0);
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
                    this.mRemote.transact(16, _data, _reply, 0);
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
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveUnlimitedFiles2(int srcContainerId, int destContainerId, Uri uri, SemIRCPCallback callback, String mSourceClassName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    _data.writeInt(srcContainerId);
                    _data.writeInt(destContainerId);
                    _data.writeTypedObject(uri, 0);
                    _data.writeStrongInterface(callback);
                    _data.writeString(mSourceClassName);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    long _result = _reply.readLong();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
