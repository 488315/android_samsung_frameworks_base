package android.os;

/* loaded from: classes3.dex */
public interface IUpdateLock extends IInterface {

    public static class Default implements IUpdateLock {
        @Override // android.os.IUpdateLock
        public void acquireUpdateLock(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IUpdateLock
        public void releaseUpdateLock(IBinder iBinder) throws RemoteException {
        }
    }

    void acquireUpdateLock(IBinder iBinder, String str) throws RemoteException;

    void releaseUpdateLock(IBinder iBinder) throws RemoteException;

    public static abstract class Stub extends Binder implements IUpdateLock {
        public static final String DESCRIPTOR = "android.os.IUpdateLock";
        static final int TRANSACTION_acquireUpdateLock = 1;
        static final int TRANSACTION_releaseUpdateLock = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IUpdateLock asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUpdateLock)) {
                return (IUpdateLock) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "acquireUpdateLock";
            }
            if (i != 2) {
                return null;
            }
            return "releaseUpdateLock";
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
                IBinder readStrongBinder = parcel.readStrongBinder();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                acquireUpdateLock(readStrongBinder, readString);
                parcel2.writeNoException();
            } else if (i == 2) {
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                releaseUpdateLock(readStrongBinder2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IUpdateLock {
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

            @Override // android.os.IUpdateLock
            public void acquireUpdateLock(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IUpdateLock
            public void releaseUpdateLock(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
