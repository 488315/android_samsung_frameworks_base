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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRestoreSession)) {
                return (IRestoreSession) iInterfaceQueryLocalInterface;
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
                IRestoreObserver iRestoreObserverAsInterface = IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                IBackupManagerMonitor iBackupManagerMonitorAsInterface = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int availableRestoreSets = getAvailableRestoreSets(iRestoreObserverAsInterface, iBackupManagerMonitorAsInterface);
                parcel2.writeNoException();
                parcel2.writeInt(availableRestoreSets);
            } else if (i == 2) {
                long j = parcel.readLong();
                IRestoreObserver iRestoreObserverAsInterface2 = IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                IBackupManagerMonitor iBackupManagerMonitorAsInterface2 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int iRestoreAll = restoreAll(j, iRestoreObserverAsInterface2, iBackupManagerMonitorAsInterface2);
                parcel2.writeNoException();
                parcel2.writeInt(iRestoreAll);
            } else if (i == 3) {
                long j2 = parcel.readLong();
                IRestoreObserver iRestoreObserverAsInterface3 = IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                String[] strArrCreateStringArray = parcel.createStringArray();
                IBackupManagerMonitor iBackupManagerMonitorAsInterface3 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int iRestorePackages = restorePackages(j2, iRestoreObserverAsInterface3, strArrCreateStringArray, iBackupManagerMonitorAsInterface3);
                parcel2.writeNoException();
                parcel2.writeInt(iRestorePackages);
            } else if (i == 4) {
                String string = parcel.readString();
                IRestoreObserver iRestoreObserverAsInterface4 = IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                IBackupManagerMonitor iBackupManagerMonitorAsInterface4 = IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                int iRestorePackage = restorePackage(string, iRestoreObserverAsInterface4, iBackupManagerMonitorAsInterface4);
                parcel2.writeNoException();
                parcel2.writeInt(iRestorePackage);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRestoreObserver);
                    parcelObtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restoreAll(long j, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iRestoreObserver);
                    parcelObtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restorePackages(long j, IRestoreObserver iRestoreObserver, String[] strArr, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeStrongInterface(iRestoreObserver);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restorePackage(String str, IRestoreObserver iRestoreObserver, IBackupManagerMonitor iBackupManagerMonitor) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iRestoreObserver);
                    parcelObtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public void endRestoreSession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
