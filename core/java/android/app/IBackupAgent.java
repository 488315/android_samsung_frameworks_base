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

import java.util.List;

/* loaded from: classes.dex */
public interface IBackupAgent extends IInterface {
    void clearBackupRestoreEventLogger() throws RemoteException;

    void doBackup(
            ParcelFileDescriptor parcelFileDescriptor,
            ParcelFileDescriptor parcelFileDescriptor2,
            ParcelFileDescriptor parcelFileDescriptor3,
            long j,
            IBackupCallback iBackupCallback,
            int i)
            throws RemoteException;

    void doDisableDataExtractionRules(boolean z) throws RemoteException;

    void doFullBackup(
            ParcelFileDescriptor parcelFileDescriptor,
            long j,
            int i,
            IBackupManager iBackupManager,
            int i2)
            throws RemoteException;

    void doFullBackupPath(
            ParcelFileDescriptor parcelFileDescriptor,
            long j,
            int i,
            IBackupManager iBackupManager,
            int i2,
            String[] strArr)
            throws RemoteException;

    void doMeasureFullBackup(long j, int i, IBackupManager iBackupManager, int i2)
            throws RemoteException;

    void doQuotaExceeded(long j, long j2, IBackupCallback iBackupCallback) throws RemoteException;

    void doRestore(
            ParcelFileDescriptor parcelFileDescriptor,
            long j,
            ParcelFileDescriptor parcelFileDescriptor2,
            int i,
            IBackupManager iBackupManager)
            throws RemoteException;

    void doRestoreFile(
            ParcelFileDescriptor parcelFileDescriptor,
            long j,
            int i,
            String str,
            String str2,
            long j2,
            long j3,
            int i2,
            IBackupManager iBackupManager)
            throws RemoteException;

    void doRestoreFinished(int i, IBackupManager iBackupManager) throws RemoteException;

    void doRestoreWithExcludedKeys(
            ParcelFileDescriptor parcelFileDescriptor,
            long j,
            ParcelFileDescriptor parcelFileDescriptor2,
            int i,
            IBackupManager iBackupManager,
            List<String> list)
            throws RemoteException;

    void fail(String str) throws RemoteException;

    void getLoggerResults(
            AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> androidFuture)
            throws RemoteException;

    void getOperationType(AndroidFuture<Integer> androidFuture) throws RemoteException;

    public static class Default implements IBackupAgent {
        @Override // android.app.IBackupAgent
        public void doBackup(
                ParcelFileDescriptor oldState,
                ParcelFileDescriptor data,
                ParcelFileDescriptor newState,
                long quotaBytes,
                IBackupCallback callbackBinder,
                int transportFlags)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void doRestore(
                ParcelFileDescriptor data,
                long appVersionCode,
                ParcelFileDescriptor newState,
                int token,
                IBackupManager callbackBinder)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void doRestoreWithExcludedKeys(
                ParcelFileDescriptor data,
                long appVersionCode,
                ParcelFileDescriptor newState,
                int token,
                IBackupManager callbackBinder,
                List<String> excludedKeys)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void doFullBackup(
                ParcelFileDescriptor data,
                long quotaBytes,
                int token,
                IBackupManager callbackBinder,
                int transportFlags)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void doFullBackupPath(
                ParcelFileDescriptor data,
                long quotaBytes,
                int token,
                IBackupManager callbackBinder,
                int transportFlags,
                String[] path)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void doDisableDataExtractionRules(boolean disable) throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void doMeasureFullBackup(
                long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void doQuotaExceeded(
                long backupDataBytes, long quotaBytes, IBackupCallback callbackBinder)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void doRestoreFile(
                ParcelFileDescriptor data,
                long size,
                int type,
                String domain,
                String path,
                long mode,
                long mtime,
                int token,
                IBackupManager callbackBinder)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void doRestoreFinished(int token, IBackupManager callbackBinder)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void fail(String message) throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void getLoggerResults(
                AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> resultsFuture)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void getOperationType(AndroidFuture<Integer> operationTypeFuture)
                throws RemoteException {}

        @Override // android.app.IBackupAgent
        public void clearBackupRestoreEventLogger() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IBackupAgent {
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

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBackupAgent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IBackupAgent)) {
                return (IBackupAgent) iin;
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParcelFileDescriptor _arg0 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor _arg1 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor _arg2 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg3 = data.readLong();
                    IBackupCallback _arg4 =
                            IBackupCallback.Stub.asInterface(data.readStrongBinder());
                    int _arg5 = data.readInt();
                    data.enforceNoDataAvail();
                    doBackup(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    return true;
                case 2:
                    ParcelFileDescriptor _arg02 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg12 = data.readLong();
                    ParcelFileDescriptor _arg22 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int _arg32 = data.readInt();
                    IBackupManager _arg42 =
                            IBackupManager.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    doRestore(_arg02, _arg12, _arg22, _arg32, _arg42);
                    return true;
                case 3:
                    ParcelFileDescriptor _arg03 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg13 = data.readLong();
                    ParcelFileDescriptor _arg23 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    int _arg33 = data.readInt();
                    IBackupManager _arg43 =
                            IBackupManager.Stub.asInterface(data.readStrongBinder());
                    List<String> _arg52 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    doRestoreWithExcludedKeys(_arg03, _arg13, _arg23, _arg33, _arg43, _arg52);
                    return true;
                case 4:
                    ParcelFileDescriptor _arg04 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg14 = data.readLong();
                    int _arg24 = data.readInt();
                    IBackupManager _arg34 =
                            IBackupManager.Stub.asInterface(data.readStrongBinder());
                    int _arg44 = data.readInt();
                    data.enforceNoDataAvail();
                    doFullBackup(_arg04, _arg14, _arg24, _arg34, _arg44);
                    return true;
                case 5:
                    ParcelFileDescriptor _arg05 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg15 = data.readLong();
                    int _arg25 = data.readInt();
                    IBackupManager _arg35 =
                            IBackupManager.Stub.asInterface(data.readStrongBinder());
                    int _arg45 = data.readInt();
                    String[] _arg53 = data.createStringArray();
                    data.enforceNoDataAvail();
                    doFullBackupPath(_arg05, _arg15, _arg25, _arg35, _arg45, _arg53);
                    return true;
                case 6:
                    boolean _arg06 = data.readBoolean();
                    data.enforceNoDataAvail();
                    doDisableDataExtractionRules(_arg06);
                    return true;
                case 7:
                    long _arg07 = data.readLong();
                    int _arg16 = data.readInt();
                    IBackupManager _arg26 =
                            IBackupManager.Stub.asInterface(data.readStrongBinder());
                    int _arg36 = data.readInt();
                    data.enforceNoDataAvail();
                    doMeasureFullBackup(_arg07, _arg16, _arg26, _arg36);
                    return true;
                case 8:
                    long _arg08 = data.readLong();
                    long _arg17 = data.readLong();
                    IBackupCallback _arg27 =
                            IBackupCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    doQuotaExceeded(_arg08, _arg17, _arg27);
                    return true;
                case 9:
                    ParcelFileDescriptor _arg09 =
                            (ParcelFileDescriptor)
                                    data.readTypedObject(ParcelFileDescriptor.CREATOR);
                    long _arg18 = data.readLong();
                    int _arg28 = data.readInt();
                    String _arg37 = data.readString();
                    String _arg46 = data.readString();
                    long _arg54 = data.readLong();
                    long _arg6 = data.readLong();
                    int _arg7 = data.readInt();
                    IBackupManager _arg8 = IBackupManager.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    doRestoreFile(
                            _arg09, _arg18, _arg28, _arg37, _arg46, _arg54, _arg6, _arg7, _arg8);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    IBackupManager _arg19 =
                            IBackupManager.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    doRestoreFinished(_arg010, _arg19);
                    return true;
                case 11:
                    String _arg011 = data.readString();
                    data.enforceNoDataAvail();
                    fail(_arg011);
                    return true;
                case 12:
                    AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> _arg012 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    getLoggerResults(_arg012);
                    return true;
                case 13:
                    AndroidFuture<Integer> _arg013 =
                            (AndroidFuture) data.readTypedObject(AndroidFuture.CREATOR);
                    data.enforceNoDataAvail();
                    getOperationType(_arg013);
                    return true;
                case 14:
                    clearBackupRestoreEventLogger();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBackupAgent {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.IBackupAgent
            public void doBackup(
                    ParcelFileDescriptor oldState,
                    ParcelFileDescriptor data,
                    ParcelFileDescriptor newState,
                    long quotaBytes,
                    IBackupCallback callbackBinder,
                    int transportFlags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(oldState, 0);
                    _data.writeTypedObject(data, 0);
                    _data.writeTypedObject(newState, 0);
                    _data.writeLong(quotaBytes);
                    _data.writeStrongInterface(callbackBinder);
                    _data.writeInt(transportFlags);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestore(
                    ParcelFileDescriptor data,
                    long appVersionCode,
                    ParcelFileDescriptor newState,
                    int token,
                    IBackupManager callbackBinder)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    _data.writeLong(appVersionCode);
                    _data.writeTypedObject(newState, 0);
                    _data.writeInt(token);
                    _data.writeStrongInterface(callbackBinder);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreWithExcludedKeys(
                    ParcelFileDescriptor data,
                    long appVersionCode,
                    ParcelFileDescriptor newState,
                    int token,
                    IBackupManager callbackBinder,
                    List<String> excludedKeys)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    _data.writeLong(appVersionCode);
                    _data.writeTypedObject(newState, 0);
                    _data.writeInt(token);
                    _data.writeStrongInterface(callbackBinder);
                    _data.writeStringList(excludedKeys);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doFullBackup(
                    ParcelFileDescriptor data,
                    long quotaBytes,
                    int token,
                    IBackupManager callbackBinder,
                    int transportFlags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    _data.writeLong(quotaBytes);
                    _data.writeInt(token);
                    _data.writeStrongInterface(callbackBinder);
                    _data.writeInt(transportFlags);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doFullBackupPath(
                    ParcelFileDescriptor data,
                    long quotaBytes,
                    int token,
                    IBackupManager callbackBinder,
                    int transportFlags,
                    String[] path)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    _data.writeLong(quotaBytes);
                    _data.writeInt(token);
                    _data.writeStrongInterface(callbackBinder);
                    _data.writeInt(transportFlags);
                    _data.writeStringArray(path);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doDisableDataExtractionRules(boolean disable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(disable);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doMeasureFullBackup(
                    long quotaBytes, int token, IBackupManager callbackBinder, int transportFlags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(quotaBytes);
                    _data.writeInt(token);
                    _data.writeStrongInterface(callbackBinder);
                    _data.writeInt(transportFlags);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doQuotaExceeded(
                    long backupDataBytes, long quotaBytes, IBackupCallback callbackBinder)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(backupDataBytes);
                    _data.writeLong(quotaBytes);
                    _data.writeStrongInterface(callbackBinder);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreFile(
                    ParcelFileDescriptor data,
                    long size,
                    int type,
                    String domain,
                    String path,
                    long mode,
                    long mtime,
                    int token,
                    IBackupManager callbackBinder)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    _data.writeLong(size);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeInt(type);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(domain);
                    try {
                        _data.writeString(path);
                    } catch (Throwable th3) {
                        th = th3;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeLong(mode);
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeLong(mtime);
                    } catch (Throwable th5) {
                        th = th5;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(token);
                    try {
                        _data.writeStrongInterface(callbackBinder);
                    } catch (Throwable th7) {
                        th = th7;
                    }
                    try {
                        this.mRemote.transact(9, _data, null, 1);
                        _data.recycle();
                    } catch (Throwable th8) {
                        th = th8;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th9) {
                    th = th9;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreFinished(int token, IBackupManager callbackBinder)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(token);
                    _data.writeStrongInterface(callbackBinder);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void fail(String message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(message);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void getLoggerResults(
                    AndroidFuture<List<BackupRestoreEventLogger.DataTypeResult>> resultsFuture)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(resultsFuture, 0);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void getOperationType(AndroidFuture<Integer> operationTypeFuture)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(operationTypeFuture, 0);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void clearBackupRestoreEventLogger() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
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
