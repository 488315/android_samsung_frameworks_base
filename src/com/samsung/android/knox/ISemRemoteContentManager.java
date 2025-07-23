package com.samsung.android.knox;

import android.content.IRCPInterface;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemRemoteContentManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.ISemRemoteContentManager";

    public static class Default implements ISemRemoteContentManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public void cancelCopyChunks(long j) throws RemoteException {
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public int copyChunks(int i, String str, int i2, String str2, long j, int i3, long j2, boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public int copyFile(int i, String str, int i2, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public int copyFileInternal(int i, String str, int i2, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public boolean deleteFile(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public Bundle exchangeData(String str, int i, Bundle bundle) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public Bundle getFileInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public List<String> getFiles(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public IRCPInterface getRCPInterface() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public boolean isFileExist(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public int moveFile(int i, String str, int i2, String str2) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public long moveUnlimitedFiles(int i, Uri uri, int i2, int i3) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.ISemRemoteContentManager
        public void registerRCPInterface(IRCPInterface iRCPInterface, int i) throws RemoteException {
        }
    }

    void cancelCopyChunks(long j) throws RemoteException;

    int copyChunks(int i, String str, int i2, String str2, long j, int i3, long j2, boolean z) throws RemoteException;

    int copyFile(int i, String str, int i2, String str2) throws RemoteException;

    int copyFileInternal(int i, String str, int i2, String str2) throws RemoteException;

    boolean deleteFile(String str, int i) throws RemoteException;

    Bundle exchangeData(String str, int i, Bundle bundle) throws RemoteException;

    Bundle getFileInfo(String str, int i) throws RemoteException;

    List<String> getFiles(String str, int i) throws RemoteException;

    IRCPInterface getRCPInterface() throws RemoteException;

    boolean isFileExist(String str, int i) throws RemoteException;

    int moveFile(int i, String str, int i2, String str2) throws RemoteException;

    long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2) throws RemoteException;

    long moveUnlimitedFiles(int i, Uri uri, int i2, int i3) throws RemoteException;

    void registerRCPInterface(IRCPInterface iRCPInterface, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemRemoteContentManager {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, ISemRemoteContentManager.DESCRIPTOR);
        }

        public static ISemRemoteContentManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemRemoteContentManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemRemoteContentManager)) {
                return (ISemRemoteContentManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemRemoteContentManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemRemoteContentManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IRCPInterface asInterface = IRCPInterface.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerRCPInterface(asInterface, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IRCPInterface rCPInterface = getRCPInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(rCPInterface);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt3 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int moveFile = moveFile(readInt2, readString, readInt3, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(moveFile);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    String readString3 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int copyFileInternal = copyFileInternal(readInt4, readString3, readInt5, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(copyFileInternal);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    String readString5 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int copyFile = copyFile(readInt6, readString5, readInt7, readString6);
                    parcel2.writeNoException();
                    parcel2.writeInt(copyFile);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long moveUnlimitedFiles = moveUnlimitedFiles(readInt8, uri, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeLong(moveUnlimitedFiles);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isFileExist = isFileExist(readString7, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFileExist);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> files = getFiles(readString8, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(files);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean deleteFile = deleteFile(readString9, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteFile);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle fileInfo = getFileInfo(readString10, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fileInfo, 1);
                    return true;
                case 11:
                    int readInt15 = parcel.readInt();
                    String readString11 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    String readString12 = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt17 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int copyChunks = copyChunks(readInt15, readString11, readInt16, readString12, readLong, readInt17, readLong2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(copyChunks);
                    return true;
                case 12:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelCopyChunks(readLong3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString13 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle exchangeData = exchangeData(readString13, readInt18, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(exchangeData, 1);
                    return true;
                case 14:
                    int readInt19 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long moveFilesForAppEx = moveFilesForAppEx(readInt19, createStringArrayList, createStringArrayList2, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeLong(moveFilesForAppEx);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemRemoteContentManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemRemoteContentManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public void registerRCPInterface(IRCPInterface iRCPInterface, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iRCPInterface);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public IRCPInterface getRCPInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return IRCPInterface.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int moveFile(int i, String str, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int copyFileInternal(int i, String str, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int copyFile(int i, String str, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public long moveUnlimitedFiles(int i, Uri uri, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public boolean isFileExist(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public List<String> getFiles(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public boolean deleteFile(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public Bundle getFileInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int copyChunks(int i, String str, int i2, String str2, long j, int i3, long j2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeInt(i3);
                    obtain.writeLong(j2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public void cancelCopyChunks(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public Bundle exchangeData(String str, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
