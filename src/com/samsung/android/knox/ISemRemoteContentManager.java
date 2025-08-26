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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemRemoteContentManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemRemoteContentManager)) {
                return (ISemRemoteContentManager) iInterfaceQueryLocalInterface;
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
                    IRCPInterface iRCPInterfaceAsInterface = IRCPInterface.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerRCPInterface(iRCPInterfaceAsInterface, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IRCPInterface rCPInterface = getRCPInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(rCPInterface);
                    return true;
                case 3:
                    int i4 = parcel.readInt();
                    String string = parcel.readString();
                    int i5 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iMoveFile = moveFile(i4, string, i5, string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMoveFile);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    String string3 = parcel.readString();
                    int i7 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCopyFileInternal = copyFileInternal(i6, string3, i7, string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCopyFileInternal);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    String string5 = parcel.readString();
                    int i9 = parcel.readInt();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCopyFile = copyFile(i8, string5, i9, string6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCopyFile);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long jMoveUnlimitedFiles = moveUnlimitedFiles(i10, uri, i11, i12);
                    parcel2.writeNoException();
                    parcel2.writeLong(jMoveUnlimitedFiles);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsFileExist = isFileExist(string7, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFileExist);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> files = getFiles(string8, i14);
                    parcel2.writeNoException();
                    parcel2.writeStringList(files);
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDeleteFile = deleteFile(string9, i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDeleteFile);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle fileInfo = getFileInfo(string10, i16);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fileInfo, 1);
                    return true;
                case 11:
                    int i17 = parcel.readInt();
                    String string11 = parcel.readString();
                    int i18 = parcel.readInt();
                    String string12 = parcel.readString();
                    long j = parcel.readLong();
                    int i19 = parcel.readInt();
                    long j2 = parcel.readLong();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iCopyChunks = copyChunks(i17, string11, i18, string12, j, i19, j2, z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCopyChunks);
                    return true;
                case 12:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancelCopyChunks(j3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string13 = parcel.readString();
                    int i20 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    Bundle bundleExchangeData = exchangeData(string13, i20, bundle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleExchangeData, 1);
                    return true;
                case 14:
                    int i21 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long jMoveFilesForAppEx = moveFilesForAppEx(i21, arrayListCreateStringArrayList, arrayListCreateStringArrayList2, i22);
                    parcel2.writeNoException();
                    parcel2.writeLong(jMoveFilesForAppEx);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRCPInterface);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public IRCPInterface getRCPInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IRCPInterface.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int moveFile(int i, String str, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int copyFileInternal(int i, String str, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int copyFile(int i, String str, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public long moveUnlimitedFiles(int i, Uri uri, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public boolean isFileExist(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public List<String> getFiles(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public boolean deleteFile(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public Bundle getFileInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public int copyChunks(int i, String str, int i2, String str2, long j, int i3, long j2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public void cancelCopyChunks(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public Bundle exchangeData(String str, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.ISemRemoteContentManager
            public long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemRemoteContentManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
