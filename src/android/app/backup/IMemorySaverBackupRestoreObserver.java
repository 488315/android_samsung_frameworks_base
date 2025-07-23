package android.app.backup;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IMemorySaverBackupRestoreObserver extends IInterface {
    public static final String DESCRIPTOR = "android.app.backup.IMemorySaverBackupRestoreObserver";

    public static class Default implements IMemorySaverBackupRestoreObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onBackupCompleted(String str, boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onRestoreCompleted(String str, boolean z) throws RemoteException {
        }

        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onRestoreStart(String str) throws RemoteException {
        }

        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onStartBackup(String str) throws RemoteException {
        }

        @Override // android.app.backup.IMemorySaverBackupRestoreObserver
        public void onTimeout() throws RemoteException {
        }
    }

    void onBackupCompleted(String str, boolean z) throws RemoteException;

    void onRestoreCompleted(String str, boolean z) throws RemoteException;

    void onRestoreStart(String str) throws RemoteException;

    void onStartBackup(String str) throws RemoteException;

    void onTimeout() throws RemoteException;

    public static abstract class Stub extends Binder implements IMemorySaverBackupRestoreObserver {
        static final int TRANSACTION_onBackupCompleted = 2;
        static final int TRANSACTION_onRestoreCompleted = 4;
        static final int TRANSACTION_onRestoreStart = 3;
        static final int TRANSACTION_onStartBackup = 1;
        static final int TRANSACTION_onTimeout = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, IMemorySaverBackupRestoreObserver.DESCRIPTOR);
        }

        public static IMemorySaverBackupRestoreObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMemorySaverBackupRestoreObserver)) {
                return (IMemorySaverBackupRestoreObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStartBackup";
            }
            if (i == 2) {
                return "onBackupCompleted";
            }
            if (i == 3) {
                return "onRestoreStart";
            }
            if (i == 4) {
                return "onRestoreCompleted";
            }
            if (i != 5) {
                return null;
            }
            return "onTimeout";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onStartBackup(readString);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onBackupCompleted(readString2, readBoolean);
            } else if (i == 3) {
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                onRestoreStart(readString3);
            } else if (i == 4) {
                String readString4 = parcel.readString();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onRestoreCompleted(readString4, readBoolean2);
            } else if (i == 5) {
                onTimeout();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IMemorySaverBackupRestoreObserver {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMemorySaverBackupRestoreObserver.DESCRIPTOR;
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onStartBackup(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onBackupCompleted(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onRestoreStart(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onRestoreCompleted(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onTimeout() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
