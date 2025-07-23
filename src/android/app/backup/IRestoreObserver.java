package android.app.backup;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IRestoreObserver extends IInterface {

    public static class Default implements IRestoreObserver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.backup.IRestoreObserver
        public void onUpdate(int i, String str) throws RemoteException {
        }

        @Override // android.app.backup.IRestoreObserver
        public void restoreFinished(int i) throws RemoteException {
        }

        @Override // android.app.backup.IRestoreObserver
        public void restoreSetsAvailable(RestoreSet[] restoreSetArr) throws RemoteException {
        }

        @Override // android.app.backup.IRestoreObserver
        public void restoreStarting(int i) throws RemoteException {
        }
    }

    void onUpdate(int i, String str) throws RemoteException;

    void restoreFinished(int i) throws RemoteException;

    void restoreSetsAvailable(RestoreSet[] restoreSetArr) throws RemoteException;

    void restoreStarting(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IRestoreObserver {
        public static final String DESCRIPTOR = "android.app.backup.IRestoreObserver";
        static final int TRANSACTION_onUpdate = 3;
        static final int TRANSACTION_restoreFinished = 4;
        static final int TRANSACTION_restoreSetsAvailable = 1;
        static final int TRANSACTION_restoreStarting = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRestoreObserver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRestoreObserver)) {
                return (IRestoreObserver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "restoreSetsAvailable";
            }
            if (i == 2) {
                return "restoreStarting";
            }
            if (i == 3) {
                return "onUpdate";
            }
            if (i != 4) {
                return null;
            }
            return "restoreFinished";
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
                RestoreSet[] restoreSetArr = (RestoreSet[]) parcel.createTypedArray(RestoreSet.CREATOR);
                parcel.enforceNoDataAvail();
                restoreSetsAvailable(restoreSetArr);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                restoreStarting(readInt);
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onUpdate(readInt2, readString);
            } else if (i == 4) {
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                restoreFinished(readInt3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IRestoreObserver {
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

            @Override // android.app.backup.IRestoreObserver
            public void restoreSetsAvailable(RestoreSet[] restoreSetArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(restoreSetArr, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreObserver
            public void restoreStarting(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreObserver
            public void onUpdate(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreObserver
            public void restoreFinished(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
