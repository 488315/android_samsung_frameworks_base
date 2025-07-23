package android.os;

/* loaded from: classes3.dex */
public interface IVoldTaskListener extends IInterface {

    public static class Default implements IVoldTaskListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IVoldTaskListener
        public void onFinished(int i, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override // android.os.IVoldTaskListener
        public void onStatus(int i, PersistableBundle persistableBundle) throws RemoteException {
        }
    }

    void onFinished(int i, PersistableBundle persistableBundle) throws RemoteException;

    void onStatus(int i, PersistableBundle persistableBundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoldTaskListener {
        public static final String DESCRIPTOR = "android.os.IVoldTaskListener";
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_onStatus = 1;

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

        public static IVoldTaskListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoldTaskListener)) {
                return (IVoldTaskListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onStatus";
            }
            if (i != 2) {
                return null;
            }
            return "onFinished";
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
                PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                parcel.enforceNoDataAvail();
                onStatus(readInt, persistableBundle);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                PersistableBundle persistableBundle2 = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
                parcel.enforceNoDataAvail();
                onFinished(readInt2, persistableBundle2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IVoldTaskListener {
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

            @Override // android.os.IVoldTaskListener
            public void onStatus(int i, PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IVoldTaskListener
            public void onFinished(int i, PersistableBundle persistableBundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
