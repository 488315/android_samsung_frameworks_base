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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRCPInterface.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRCPInterface)) {
                return (IRCPInterface) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt2 = parcel.readInt();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    SemIRCPCallback asInterface = SemIRCPCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    long copyFiles = copyFiles(readInt, createStringArrayList, readInt2, createStringArrayList2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeLong(copyFiles);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    int readInt4 = parcel.readInt();
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    SemIRCPCallback asInterface2 = SemIRCPCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    long moveFiles = moveFiles(readInt3, createStringArrayList3, readInt4, createStringArrayList4, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeLong(moveFiles);
                    return true;
                case 3:
                    int readInt5 = parcel.readInt();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long moveUnlimitedFilesForApp = moveUnlimitedFilesForApp(readInt5, uri, readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeLong(moveUnlimitedFilesForApp);
                    return true;
                case 4:
                    int readInt8 = parcel.readInt();
                    String readString = parcel.readString();
                    int readInt9 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int copyFile = copyFile(readInt8, readString, readInt9, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(copyFile);
                    return true;
                case 5:
                    int readInt10 = parcel.readInt();
                    String readString3 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int moveFile = moveFile(readInt10, readString3, readInt11, readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(moveFile);
                    return true;
                case 6:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String errorMessage = getErrorMessage(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeString(errorMessage);
                    return true;
                case 7:
                    String readString5 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isFileExist = isFileExist(readString5, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFileExist);
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> files = getFiles(readString6, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeStringList(files);
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle fileInfo = getFileInfo(readString7, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(fileInfo, 1);
                    return true;
                case 10:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cancel(readLong);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt16 = parcel.readInt();
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList6 = parcel.createStringArrayList();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long moveFilesForAppEx = moveFilesForAppEx(readInt16, createStringArrayList5, createStringArrayList6, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeLong(moveFilesForAppEx);
                    return true;
                case 12:
                    int readInt18 = parcel.readInt();
                    ArrayList<String> createStringArrayList7 = parcel.createStringArrayList();
                    int readInt19 = parcel.readInt();
                    ArrayList<String> createStringArrayList8 = parcel.createStringArrayList();
                    SemIRCPCallback asInterface3 = SemIRCPCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long copyFiles2 = copyFiles2(readInt18, createStringArrayList7, readInt19, createStringArrayList8, asInterface3, readString8);
                    parcel2.writeNoException();
                    parcel2.writeLong(copyFiles2);
                    return true;
                case 13:
                    int readInt20 = parcel.readInt();
                    ArrayList<String> createStringArrayList9 = parcel.createStringArrayList();
                    int readInt21 = parcel.readInt();
                    ArrayList<String> createStringArrayList10 = parcel.createStringArrayList();
                    SemIRCPCallback asInterface4 = SemIRCPCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long moveFiles2 = moveFiles2(readInt20, createStringArrayList9, readInt21, createStringArrayList10, asInterface4, readString9);
                    parcel2.writeNoException();
                    parcel2.writeLong(moveFiles2);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    obtain.writeStringList(list2);
                    obtain.writeStrongInterface(semIRCPCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveFiles(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    obtain.writeStringList(list2);
                    obtain.writeStrongInterface(semIRCPCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveUnlimitedFilesForApp(int i, Uri uri, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public int copyFile(int i, String str, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public int moveFile(int i, String str, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public String getErrorMessage(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public boolean isFileExist(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public List<String> getFiles(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
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

            @Override // android.content.IRCPInterface
            public Bundle getFileInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public void cancel(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveFilesForAppEx(int i, List<String> list, List<String> list2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long copyFiles2(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    obtain.writeStringList(list2);
                    obtain.writeStrongInterface(semIRCPCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IRCPInterface
            public long moveFiles2(int i, List<String> list, int i2, List<String> list2, SemIRCPCallback semIRCPCallback, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRCPInterface.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    obtain.writeStringList(list2);
                    obtain.writeStrongInterface(semIRCPCallback);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
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
