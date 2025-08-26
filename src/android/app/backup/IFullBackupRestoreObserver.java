package android.app.backup;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IFullBackupRestoreObserver extends IInterface {

    public static class Default implements IFullBackupRestoreObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onBackupPackage(String str) throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onEndBackup() throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onEndRestore() throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onRestorePackage(String str) throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onStartBackup() throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onStartRestore() throws RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onTimeout() throws RemoteException {
        }
    }

    void onBackupPackage(String str) throws RemoteException;

    void onEndBackup() throws RemoteException;

    void onEndRestore() throws RemoteException;

    void onRestorePackage(String str) throws RemoteException;

    void onStartBackup() throws RemoteException;

    void onStartRestore() throws RemoteException;

    void onTimeout() throws RemoteException;

    public static abstract class Stub extends Binder implements IFullBackupRestoreObserver {
        public static final String DESCRIPTOR = "android.app.backup.IFullBackupRestoreObserver";
        static final int TRANSACTION_onBackupPackage = 2;
        static final int TRANSACTION_onEndBackup = 3;
        static final int TRANSACTION_onEndRestore = 6;
        static final int TRANSACTION_onRestorePackage = 5;
        static final int TRANSACTION_onStartBackup = 1;
        static final int TRANSACTION_onStartRestore = 4;
        static final int TRANSACTION_onTimeout = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFullBackupRestoreObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFullBackupRestoreObserver)) {
                return (IFullBackupRestoreObserver) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStartBackup";
                case 2:
                    return "onBackupPackage";
                case 3:
                    return "onEndBackup";
                case 4:
                    return "onStartRestore";
                case 5:
                    return "onRestorePackage";
                case 6:
                    return "onEndRestore";
                case 7:
                    return "onTimeout";
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
                    onStartBackup();
                    return true;
                case 2:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBackupPackage(string);
                    return true;
                case 3:
                    onEndBackup();
                    return true;
                case 4:
                    onStartRestore();
                    return true;
                case 5:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onRestorePackage(string2);
                    return true;
                case 6:
                    onEndRestore();
                    return true;
                case 7:
                    onTimeout();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFullBackupRestoreObserver {
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

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onStartBackup() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onBackupPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onEndBackup() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onStartRestore() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onRestorePackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onEndRestore() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onTimeout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
