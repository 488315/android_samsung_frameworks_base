package android.app.backup;

import android.app.backup.IBackupManagerMonitor;
import android.app.backup.IRestoreObserver;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IRestoreSession extends IInterface {

    public static class Default implements IRestoreSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.backup.IRestoreSession
        public void endRestoreSession() throws RemoteException {
        }

        @Override // android.app.backup.IRestoreSession
        public int getAvailableRestoreSets(IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public int restoreAll(long j, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public int restorePackage(String str, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public int restorePackages(long j, IRestoreObserver iRestoreObserver, String[] strArr, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
            return 0;
        }
    }

    void endRestoreSession() throws RemoteException;

    int getAvailableRestoreSets(IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException;

    int restoreAll(long j, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException;

    int restorePackage(String str, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException;

    int restorePackages(long j, IRestoreObserver iRestoreObserver, String[] strArr, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException;

    public static abstract class Stub extends Binder implements IRestoreSession {
        public static final String DESCRIPTOR = "android.app.backup.IRestoreSession";
        static final int TRANSACTION_endRestoreSession = 5;
        static final int TRANSACTION_getAvailableRestoreSets = 1;
        static final int TRANSACTION_restoreAll = 2;
        static final int TRANSACTION_restorePackage = 4;
        static final int TRANSACTION_restorePackages = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRestoreSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRestoreSession)) {
                return (IRestoreSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getAvailableRestoreSets";
            }
            if (i == 2) {
                return "restoreAll";
            }
            if (i == 3) {
                return "restorePackages";
            }
            if (i == 4) {
                return "restorePackage";
            }
            if (i != 5) {
                return null;
            }
            return "endRestoreSession";
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
                IRestoreObserver asInterface = IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                IBackupManagerMonitor asInterface2 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int availableRestoreSets = getAvailableRestoreSets(asInterface, asInterface2);
                parcel2.writeNoException();
                parcel2.writeInt(availableRestoreSets);
            } else if (i == 2) {
                long readLong = parcel.readLong();
                IRestoreObserver asInterface3 = IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                IBackupManagerMonitor asInterface4 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int restoreAll = restoreAll(readLong, asInterface3, asInterface4);
                parcel2.writeNoException();
                parcel2.writeInt(restoreAll);
            } else if (i == 3) {
                long readLong2 = parcel.readLong();
                IRestoreObserver asInterface5 = IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                String[] createStringArray = parcel.createStringArray();
                IBackupManagerMonitor asInterface6 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int restorePackages = restorePackages(readLong2, asInterface5, createStringArray, asInterface6);
                parcel2.writeNoException();
                parcel2.writeInt(restorePackages);
            } else if (i == 4) {
                String readString = parcel.readString();
                IRestoreObserver asInterface7 = IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                IBackupManagerMonitor asInterface8 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int restorePackage = restorePackage(readString, asInterface7, asInterface8);
                parcel2.writeNoException();
                parcel2.writeInt(restorePackage);
            } else if (i == 5) {
                endRestoreSession();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRestoreSession {
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

            @Override // android.app.backup.IRestoreSession
            public int getAvailableRestoreSets(IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRestoreObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restoreAll(long j, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iRestoreObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restorePackages(long j, IRestoreObserver iRestoreObserver, String[] strArr, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iRestoreObserver);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restorePackage(String str, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iRestoreObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public void endRestoreSession() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
