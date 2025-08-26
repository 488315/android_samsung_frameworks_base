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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBackupAgent)) {
                return (IBackupAgent) iInterfaceQueryLocalInterface;
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
                    long j = parcel.readLong();
                    IBackupCallback iBackupCallbackAsInterface = IBackupCallback.Stub.asInterface(parcel.readStrongBinder());
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doBackup(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, j, iBackupCallbackAsInterface, i3);
                    return true;
                case 2:
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long j2 = parcel.readLong();
                    ParcelFileDescriptor parcelFileDescriptor5 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int i4 = parcel.readInt();
                    IBackupManager iBackupManagerAsInterface = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doRestore(parcelFileDescriptor4, j2, parcelFileDescriptor5, i4, iBackupManagerAsInterface);
                    return true;
                case 3:
                    ParcelFileDescriptor parcelFileDescriptor6 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long j3 = parcel.readLong();
                    ParcelFileDescriptor parcelFileDescriptor7 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int i5 = parcel.readInt();
                    IBackupManager iBackupManagerAsInterface2 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    doRestoreWithExcludedKeys(parcelFileDescriptor6, j3, parcelFileDescriptor7, i5, iBackupManagerAsInterface2, arrayListCreateStringArrayList);
                    return true;
                case 4:
                    ParcelFileDescriptor parcelFileDescriptor8 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long j4 = parcel.readLong();
                    int i6 = parcel.readInt();
                    IBackupManager iBackupManagerAsInterface3 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doFullBackup(parcelFileDescriptor8, j4, i6, iBackupManagerAsInterface3, i7);
                    return true;
                case 5:
                    ParcelFileDescriptor parcelFileDescriptor9 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long j5 = parcel.readLong();
                    int i8 = parcel.readInt();
                    IBackupManager iBackupManagerAsInterface4 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    int i9 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    doFullBackupPath(parcelFileDescriptor9, j5, i8, iBackupManagerAsInterface4, i9, strArrCreateStringArray);
                    return true;
                case 6:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    doDisableDataExtractionRules(z);
                    return true;
                case 7:
                    long j6 = parcel.readLong();
                    int i10 = parcel.readInt();
                    IBackupManager iBackupManagerAsInterface5 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doMeasureFullBackup(j6, i10, iBackupManagerAsInterface5, i11);
                    return true;
                case 8:
                    long j7 = parcel.readLong();
                    long j8 = parcel.readLong();
                    IBackupCallback iBackupCallbackAsInterface2 = IBackupCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doQuotaExceeded(j7, j8, iBackupCallbackAsInterface2);
                    return true;
                case 9:
                    ParcelFileDescriptor parcelFileDescriptor10 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long j9 = parcel.readLong();
                    int i12 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    long j10 = parcel.readLong();
                    long j11 = parcel.readLong();
                    int i13 = parcel.readInt();
                    IBackupManager iBackupManagerAsInterface6 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doRestoreFile(parcelFileDescriptor10, j9, i12, string, string2, j10, j11, i13, iBackupManagerAsInterface6);
                    return true;
                case 10:
                    int i14 = parcel.readInt();
                    IBackupManager iBackupManagerAsInterface7 = IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doRestoreFinished(i14, iBackupManagerAsInterface7);
                    return true;
                case 11:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    fail(string3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor3, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iBackupCallback);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestore(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreWithExcludedKeys(ParcelFileDescriptor parcelFileDescriptor, long j, ParcelFileDescriptor parcelFileDescriptor2, int i, IBackupManager iBackupManager, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBackupManager);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doFullBackup(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBackupManager);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doFullBackupPath(ParcelFileDescriptor parcelFileDescriptor, long j, int i, IBackupManager iBackupManager, int i2, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBackupManager);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doDisableDataExtractionRules(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doMeasureFullBackup(long j, int i, IBackupManager iBackupManager, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBackupManager);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doQuotaExceeded(long j, long j2, IBackupCallback iBackupCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeStrongInterface(iBackupCallback);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long j, int i, String str, String str2, long j2, long j3, int i2, IBackupManager iBackupManager) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeLong(j3);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreFinished(int i, IBackupManager iBackupManager) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void fail(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void getLoggerResults(AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void getOperationType(AndroidFuture<Integer> androidFuture) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void clearBackupRestoreEventLogger() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
