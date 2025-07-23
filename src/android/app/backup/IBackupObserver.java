package android.app.backup;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBackupObserver extends IInterface {

    public static class Default implements IBackupObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.backup.IBackupObserver
        public void backupFinished(int i) throws RemoteException {
        }

        @Override // android.app.backup.IBackupObserver
        public void onResult(String str, int i) throws RemoteException {
        }

        @Override // android.app.backup.IBackupObserver
        public void onUpdate(String str, BackupProgress backupProgress) throws RemoteException {
        }
    }

    void backupFinished(int i) throws RemoteException;

    void onResult(String str, int i) throws RemoteException;

    void onUpdate(String str, BackupProgress backupProgress) throws RemoteException;

    public static abstract class Stub extends Binder implements IBackupObserver {
        public static final String DESCRIPTOR = "android.app.backup.IBackupObserver";
        static final int TRANSACTION_backupFinished = 3;
        static final int TRANSACTION_onResult = 2;
        static final int TRANSACTION_onUpdate = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBackupObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IBackupObserver)) {
                return (IBackupObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onUpdate";
            }
            if (i == 2) {
                return "onResult";
            }
            if (i != 3) {
                return null;
            }
            return "backupFinished";
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
            if (i == 1) {
                String readString = parcel.readString();
                BackupProgress backupProgress = (BackupProgress) parcel.readTypedObject(BackupProgress.CREATOR);
                parcel.enforceNoDataAvail();
                onUpdate(readString, backupProgress);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onResult(readString2, readInt);
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                backupFinished(readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBackupObserver {
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

            @Override // android.app.backup.IBackupObserver
            public void onUpdate(String str, BackupProgress backupProgress) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(backupProgress, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupObserver
            public void onResult(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupObserver
            public void backupFinished(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
