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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMemorySaverBackupRestoreObserver)) {
                return (IMemorySaverBackupRestoreObserver) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onStartBackup(string);
            } else if (i == 2) {
                String string2 = parcel.readString();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onBackupCompleted(string2, z);
            } else if (i == 3) {
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                onRestoreStart(string3);
            } else if (i == 4) {
                String string4 = parcel.readString();
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onRestoreCompleted(string4, z2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onBackupCompleted(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onRestoreStart(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onRestoreCompleted(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IMemorySaverBackupRestoreObserver
            public void onTimeout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IMemorySaverBackupRestoreObserver.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
