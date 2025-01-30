package android.app.backup;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface IMemorySaverBackupRestoreObserver extends IInterface {
    public static final String DESCRIPTOR = "android.app.backup.IMemorySaverBackupRestoreObserver";

    void onBackupCompleted(String str, boolean z) throws RemoteException;

    void onRestoreCompleted(String str, boolean z) throws RemoteException;

    void onRestoreStart(String str) throws RemoteException;

    void onStartBackup(String str) throws RemoteException;

    void onTimeout() throws RemoteException;

    public static class Default implements IMemorySaverBackupRestoreObserver {
        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onStartBackup(String packageName) throws RemoteException {
        }

        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onBackupCompleted(String packageName, boolean success) throws RemoteException {
        }

        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onRestoreStart(String name) throws RemoteException {
        }

        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onRestoreCompleted(String packageName, boolean success) throws RemoteException {
        }

        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onTimeout() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMemorySaverBackupRestoreObserver {
        static final int TRANSACTION_onBackupCompleted = 2;
        static final int TRANSACTION_onRestoreCompleted = 4;
        static final int TRANSACTION_onRestoreStart = 3;
        static final int TRANSACTION_onStartBackup = 1;
        static final int TRANSACTION_onTimeout = 5;

        public Stub() {
            attachInterface(this, IMemorySaverBackupRestoreObserver.DESCRIPTOR);
        }

        public static IMemorySaverBackupRestoreObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof IMemorySaverBackupRestoreObserver)) {
                return (IMemorySaverBackupRestoreObserver) iin;
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
                    return "onStartBackup";
                case 2:
                    return "onBackupCompleted";
                case 3:
                    return "onRestoreStart";
                case 4:
                    return "onRestoreCompleted";
                case 5:
                    return "onTimeout";
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
                data.enforceInterface(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            String _arg0 = data.readString();
                            data.enforceNoDataAvail();
                            onStartBackup(_arg0);
                            return true;
                        case 2:
                            String _arg02 = data.readString();
                            boolean _arg1 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onBackupCompleted(_arg02, _arg1);
                            return true;
                        case 3:
                            String _arg03 = data.readString();
                            data.enforceNoDataAvail();
                            onRestoreStart(_arg03);
                            return true;
                        case 4:
                            String _arg04 = data.readString();
                            boolean _arg12 = data.readBoolean();
                            data.enforceNoDataAvail();
                            onRestoreCompleted(_arg04, _arg12);
                            return true;
                        case 5:
                            onTimeout();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IMemorySaverBackupRestoreObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMemorySaverBackupRestoreObserver.DESCRIPTOR;
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onStartBackup(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onBackupCompleted(String packageName, boolean success) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(success);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onRestoreStart(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onRestoreCompleted(String packageName, boolean success) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeBoolean(success);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onTimeout() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
