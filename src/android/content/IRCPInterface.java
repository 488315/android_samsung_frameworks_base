package android.content;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.SemIRCPCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IRCPInterface extends IInterface {
    public static final String DESCRIPTOR = "android.content.IRCPInterface";

    public static class Default implements IRCPInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.IRCPInterface
        public void cancel(long j) throws RemoteException {
        }

        @Override // android.content.IRCPInterface
        public int copyFile(int i, String str, int i2, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.content.IRCPInterface
        public long copyFiles(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public long copyFiles2(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback, String str) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public String getErrorMessage(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.IRCPInterface
        public Bundle getFileInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.IRCPInterface
        public List<String> getFiles(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.content.IRCPInterface
        public boolean isFileExist(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.IRCPInterface
        public int moveFile(int i, String str, int i2, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.content.IRCPInterface
        public long moveFiles(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public long moveFiles2(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback, String str) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2) throws RemoteException {
            return 0L;
        }

        @Override // android.content.IRCPInterface
        public long moveUnlimitedFilesForApp(int i, Uri uri, int i2, int i3) throws RemoteException {
            return 0L;
        }
    }

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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, IRCPInterface.DESCRIPTOR);
        }

        public static IRCPInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRCPInterface.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRCPInterface)) {
                return (IRCPInterface) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRCPInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRCPInterface.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    int i4 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    SemIRCPCallback semIRCPCallbackAsInterface = SemIRCPCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    long jCopyFiles = copyFiles(i3, arrayListCreateStringArrayList, i4, arrayListCreateStringArrayList2, semIRCPCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeLong(jCopyFiles);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    int i6 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    SemIRCPCallback semIRCPCallbackAsInterface2 = SemIRCPCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    long jMoveFiles = moveFiles(i5, arrayListCreateStringArrayList3, i6, arrayListCreateStringArrayList4, semIRCPCallbackAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeLong(jMoveFiles);
                    return true;
                case 3:
                    int i7 = parcel.readInt();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long jMoveUnlimitedFilesForApp = moveUnlimitedFilesForApp(i7, uri, i8, i9);
                    parcel2.writeNoException();
                    parcel2.writeLong(jMoveUnlimitedFilesForApp);
                    return true;
                case 4:
                    int i10 = parcel.readInt();
                    String string = parcel.readString();
                    int i11 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCopyFile = copyFile(i10, string, i11, string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCopyFile);
                    return true;
                case 5:
                    int i12 = parcel.readInt();
                    String string3 = parcel.readString();
                    int i13 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iMoveFile = moveFile(i12, string3, i13, string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(iMoveFile);
                    return true;
                case 6:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String errorMessage = getErrorMessage(i14);
                    parcel2.writeNoException();
                    parcel2.writeString(errorMessage);
                    return true;
                case 7:
                    String string5 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsFileExist = isFileExist(string5, i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsFileExist);
                    return true;
                case 8:
                    String string6 = parcel.readString();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> files = getFiles(string6, i16);
                    parcel2.writeNoException();
                    parcel2.writeStringList(files);
                    return true;
                case 9:
                    String string7 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle fileInfo = getFileInfo(string7, i17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fileInfo, 1);
                    return true;
                case 10:
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancel(j);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i18 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList6 = parcel.createStringArrayList();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long jMoveFilesForAppEx = moveFilesForAppEx(i18, arrayListCreateStringArrayList5, arrayListCreateStringArrayList6, i19);
                    parcel2.writeNoException();
                    parcel2.writeLong(jMoveFilesForAppEx);
                    return true;
                case 12:
                    int i20 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList7 = parcel.createStringArrayList();
                    int i21 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList8 = parcel.createStringArrayList();
                    SemIRCPCallback semIRCPCallbackAsInterface3 = SemIRCPCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long jCopyFiles2 = copyFiles2(i20, arrayListCreateStringArrayList7, i21, arrayListCreateStringArrayList8, semIRCPCallbackAsInterface3, string8);
                    parcel2.writeNoException();
                    parcel2.writeLong(jCopyFiles2);
                    return true;
                case 13:
                    int i22 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList9 = parcel.createStringArrayList();
                    int i23 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList10 = parcel.createStringArrayList();
                    SemIRCPCallback semIRCPCallbackAsInterface4 = SemIRCPCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long jMoveFiles2 = moveFiles2(i22, arrayListCreateStringArrayList9, i23, arrayListCreateStringArrayList10, semIRCPCallbackAsInterface4, string9);
                    parcel2.writeNoException();
                    parcel2.writeLong(jMoveFiles2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRCPInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRCPInterface.DESCRIPTOR;
            }

            @Override // android.content.IRCPInterface
            public long copyFiles(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStrongInterface(semIRCPCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveFiles(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStrongInterface(semIRCPCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveUnlimitedFilesForApp(int i, Uri uri, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public int copyFile(int i, String str, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public int moveFile(int i, String str, int i2, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public String getErrorMessage(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public boolean isFileExist(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public List<String> getFiles(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public Bundle getFileInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public void cancel(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long copyFiles2(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStrongInterface(semIRCPCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveFiles2(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStrongInterface(semIRCPCallback);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
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
