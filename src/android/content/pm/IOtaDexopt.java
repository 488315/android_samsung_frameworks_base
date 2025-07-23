package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IOtaDexopt extends IInterface {

    public static class Default implements IOtaDexopt {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IOtaDexopt
        public void cleanup() throws RemoteException {
        }

        @Override // android.content.pm.IOtaDexopt
        public void dexoptNextPackage() throws RemoteException {
        }

        @Override // android.content.pm.IOtaDexopt
        public float getProgress() throws RemoteException {
            return 0.0f;
        }

        @Override // android.content.pm.IOtaDexopt
        public boolean isDone() throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IOtaDexopt
        public String nextDexoptCommand() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IOtaDexopt
        public void prepare() throws RemoteException {
        }
    }

    void cleanup() throws RemoteException;

    void dexoptNextPackage() throws RemoteException;

    float getProgress() throws RemoteException;

    boolean isDone() throws RemoteException;

    String nextDexoptCommand() throws RemoteException;

    void prepare() throws RemoteException;

    public static abstract class Stub extends Binder implements IOtaDexopt {
        public static final String DESCRIPTOR = "android.content.pm.IOtaDexopt";
        static final int TRANSACTION_cleanup = 2;
        static final int TRANSACTION_dexoptNextPackage = 5;
        static final int TRANSACTION_getProgress = 4;
        static final int TRANSACTION_isDone = 3;
        static final int TRANSACTION_nextDexoptCommand = 6;
        static final int TRANSACTION_prepare = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IOtaDexopt asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOtaDexopt)) {
                return (IOtaDexopt) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "prepare";
                case 2:
                    return "cleanup";
                case 3:
                    return "isDone";
                case 4:
                    return "getProgress";
                case 5:
                    return "dexoptNextPackage";
                case 6:
                    return "nextDexoptCommand";
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
                    prepare();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    cleanup();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean isDone = isDone();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDone);
                    return true;
                case 4:
                    float progress = getProgress();
                    parcel2.writeNoException();
                    parcel2.writeFloat(progress);
                    return true;
                case 5:
                    dexoptNextPackage();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String nextDexoptCommand = nextDexoptCommand();
                    parcel2.writeNoException();
                    parcel2.writeString(nextDexoptCommand);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IOtaDexopt {
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

            @Override // android.content.pm.IOtaDexopt
            public void prepare() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOtaDexopt
            public void cleanup() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOtaDexopt
            public boolean isDone() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOtaDexopt
            public float getProgress() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IOtaDexopt
            public void dexoptNextPackage() throws RemoteException {
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

            @Override // android.content.pm.IOtaDexopt
            public String nextDexoptCommand() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
