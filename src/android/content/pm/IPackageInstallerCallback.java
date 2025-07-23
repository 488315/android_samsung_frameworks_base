package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPackageInstallerCallback extends IInterface {

    public static class Default implements IPackageInstallerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionActiveChanged(int i, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionBadgingChanged(int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionCreated(int i) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionFinished(int i, boolean z) throws RemoteException {
        }

        @Override // android.content.pm.IPackageInstallerCallback
        public void onSessionProgressChanged(int i, float f) throws RemoteException {
        }
    }

    void onSessionActiveChanged(int i, boolean z) throws RemoteException;

    void onSessionBadgingChanged(int i) throws RemoteException;

    void onSessionCreated(int i) throws RemoteException;

    void onSessionFinished(int i, boolean z) throws RemoteException;

    void onSessionProgressChanged(int i, float f) throws RemoteException;

    public static abstract class Stub extends Binder implements IPackageInstallerCallback {
        public static final String DESCRIPTOR = "android.content.pm.IPackageInstallerCallback";
        static final int TRANSACTION_onSessionActiveChanged = 3;
        static final int TRANSACTION_onSessionBadgingChanged = 2;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionFinished = 5;
        static final int TRANSACTION_onSessionProgressChanged = 4;

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

        public static IPackageInstallerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPackageInstallerCallback)) {
                return (IPackageInstallerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSessionCreated";
            }
            if (i == 2) {
                return "onSessionBadgingChanged";
            }
            if (i == 3) {
                return "onSessionActiveChanged";
            }
            if (i == 4) {
                return "onSessionProgressChanged";
            }
            if (i != 5) {
                return null;
            }
            return "onSessionFinished";
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
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSessionCreated(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSessionBadgingChanged(readInt2);
            } else if (i == 3) {
                int readInt3 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSessionActiveChanged(readInt3, readBoolean);
            } else if (i == 4) {
                int readInt4 = parcel.readInt();
                float readFloat = parcel.readFloat();
                parcel.enforceNoDataAvail();
                onSessionProgressChanged(readInt4, readFloat);
            } else if (i == 5) {
                int readInt5 = parcel.readInt();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onSessionFinished(readInt5, readBoolean2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPackageInstallerCallback {
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

            @Override // android.content.pm.IPackageInstallerCallback
            public void onSessionCreated(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerCallback
            public void onSessionBadgingChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerCallback
            public void onSessionActiveChanged(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerCallback
            public void onSessionProgressChanged(int i, float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IPackageInstallerCallback
            public void onSessionFinished(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
