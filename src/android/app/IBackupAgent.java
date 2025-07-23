package android.app;

import android.app.backup.BackupRestoreEventLogger;
import android.app.backup.IBackupCallback;
import android.app.backup.IBackupManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.android.internal.infra.AndroidFuture;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface IBackupAgent extends IInterface {

    public static class Default implements IBackupAgent {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IBackupAgent
        public void clearBackupRestoreEventLogger() throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doBackup(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, long j, IBackupCallback iBackupCallback, int i) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doDisableDataExtractionRules(boolean z) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doFullBackup(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doFullBackupPath(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2, String[] strArr) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doMeasureFullBackup(long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doQuotaExceeded(long j, long j2, IBackupCallback iBackupCallback) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doRestore(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFinished(int i, IBackupManager iBackupManager) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doRestoreWithExcludedKeys(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager, List<String> list) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void fail(String str) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void getLoggerResults(AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> androidFuture) throws RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void getOperationType(AndroidFuture<Integer> androidFuture) throws RemoteException {
        }
    }

    void clearBackupRestoreEventLogger() throws RemoteException;

    void doBackup(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, long j, IBackupCallback iBackupCallback, int i) throws RemoteException;

    void doDisableDataExtractionRules(boolean z) throws RemoteException;

    void doFullBackup(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException;

    void doFullBackupPath(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2, String[] strArr) throws RemoteException;

    void doMeasureFullBackup(long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException;

    void doQuotaExceeded(long j, long j2, IBackupCallback iBackupCallback) throws RemoteException;

    void doRestore(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager) throws RemoteException;

    void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws RemoteException;

    void doRestoreFinished(int i, IBackupManager iBackupManager) throws RemoteException;

    void doRestoreWithExcludedKeys(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager, List<String> list) throws RemoteException;

    void fail(String str) throws RemoteException;

    void getLoggerResults(AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> androidFuture) throws RemoteException;

    void getOperationType(AndroidFuture<Integer> androidFuture) throws RemoteException;

    public static abstract class Stub extends Binder implements IBackupAgent {
        public static final String DESCRIPTOR = "android.app.IBackupAgent";
        static final int TRANSACTION_clearBackupRestoreEventLogger = 14;
        static final int TRANSACTION_doBackup = 1;
        static final int TRANSACTION_doDisableDataExtractionRules = 6;
        static final int TRANSACTION_doFullBackup = 4;
        static final int TRANSACTION_doFullBackupPath = 5;
        static final int TRANSACTION_doMeasureFullBackup = 7;
        static final int TRANSACTION_doQuotaExceeded = 8;
        static final int TRANSACTION_doRestore = 2;
        static final int TRANSACTION_doRestoreFile = 9;
        static final int TRANSACTION_doRestoreFinished = 10;
        static final int TRANSACTION_doRestoreWithExcludedKeys = 3;
        static final int TRANSACTION_fail = 11;
        static final int TRANSACTION_getLoggerResults = 12;
        static final int TRANSACTION_getOperationType = 13;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBackupAgent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBackupAgent)) {
                return (IBackupAgent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "doBackup";
                case 2:
                    return "doRestore";
                case 3:
                    return "doRestoreWithExcludedKeys";
                case 4:
                    return "doFullBackup";
                case 5:
                    return "doFullBackupPath";
                case 6:
                    return "doDisableDataExtractionRules";
                case 7:
                    return "doMeasureFullBackup";
                case 8:
                    return "doQuotaExceeded";
                case 9:
                    return "doRestoreFile";
                case 10:
                    return "doRestoreFinished";
                case 11:
                    return "fail";
                case 12:
                    return "getLoggerResults";
                case 13:
                    return "getOperationType";
                case 14:
                    return "clearBackupRestoreEventLogger";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long readLong = parcel.readLong();
                    IBackupCallback asInterface = IBackupCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doBackup(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, readLong, asInterface, readInt);
                    return true;
                case 2:
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long readLong2 = parcel.readLong();
                    ParcelFileDescriptor parcelFileDescriptor5 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int readInt2 = parcel.readInt();
                    IBackupManager asInterface2 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doRestore(parcelFileDescriptor4, readLong2, parcelFileDescriptor5, readInt2, asInterface2);
                    return true;
                case 3:
                    ParcelFileDescriptor parcelFileDescriptor6 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long readLong3 = parcel.readLong();
                    ParcelFileDescriptor parcelFileDescriptor7 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int readInt3 = parcel.readInt();
                    IBackupManager asInterface3 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    doRestoreWithExcludedKeys(parcelFileDescriptor6, readLong3, parcelFileDescriptor7, readInt3, asInterface3, createStringArrayList);
                    return true;
                case 4:
                    ParcelFileDescriptor parcelFileDescriptor8 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long readLong4 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    IBackupManager asInterface4 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doFullBackup(parcelFileDescriptor8, readLong4, readInt4, asInterface4, readInt5);
                    return true;
                case 5:
                    ParcelFileDescriptor parcelFileDescriptor9 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long readLong5 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    IBackupManager asInterface5 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    doFullBackupPath(parcelFileDescriptor9, readLong5, readInt6, asInterface5, readInt7, createStringArray);
                    return true;
                case 6:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    doDisableDataExtractionRules(readBoolean);
                    return true;
                case 7:
                    long readLong6 = parcel.readLong();
                    int readInt8 = parcel.readInt();
                    IBackupManager asInterface6 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doMeasureFullBackup(readLong6, readInt8, asInterface6, readInt9);
                    return true;
                case 8:
                    long readLong7 = parcel.readLong();
                    long readLong8 = parcel.readLong();
                    IBackupCallback asInterface7 = IBackupCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doQuotaExceeded(readLong7, readLong8, asInterface7);
                    return true;
                case 9:
                    ParcelFileDescriptor parcelFileDescriptor10 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long readLong9 = parcel.readLong();
                    int readInt10 = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    long readLong10 = parcel.readLong();
                    long readLong11 = parcel.readLong();
                    int readInt11 = parcel.readInt();
                    IBackupManager asInterface8 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doRestoreFile(parcelFileDescriptor10, readLong9, readInt10, readString, readString2, readLong10, readLong11, readInt11, asInterface8);
                    return true;
                case 10:
                    int readInt12 = parcel.readInt();
                    IBackupManager asInterface9 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doRestoreFinished(readInt12, asInterface9);
                    return true;
                case 11:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    fail(readString3);
                    return true;
                case 12:
                    AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> androidFuture = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getLoggerResults(androidFuture);
                    return true;
                case 13:
                    AndroidFuture<Integer> androidFuture2 = (AndroidFuture) parcel.readTypedObject(AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getOperationType(androidFuture2);
                    return true;
                case 14:
                    clearBackupRestoreEventLogger();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IBackupAgent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IBackupAgent
            public void doBackup(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, long j, IBackupCallback iBackupCallback, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeTypedObject(parcelFileDescriptor3, 0);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iBackupCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestore(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreWithExcludedKeys(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    obtain.writeStringList(list);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doFullBackup(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doFullBackupPath(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doDisableDataExtractionRules(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doMeasureFullBackup(long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doQuotaExceeded(long j, long j2, IBackupCallback iBackupCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeStrongInterface(iBackupCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreFinished(int i, IBackupManager iBackupManager) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void fail(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void getLoggerResults(AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void getOperationType(AndroidFuture<Integer> androidFuture) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void clearBackupRestoreEventLogger() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
