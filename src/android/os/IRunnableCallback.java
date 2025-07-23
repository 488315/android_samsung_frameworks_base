package android.os;

/* loaded from: classes3.dex */
public interface IRunnableCallback extends IInterface {
    public static final String DESCRIPTOR = "android.os.IRunnableCallback";

    public static class Default implements IRunnableCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IRunnableCallback
        public Bundle run(Bundle bundle) throws RemoteException {
            return null;
        }
    }

    Bundle run(Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements IRunnableCallback {
        static final int TRANSACTION_run = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRunnableCallback.DESCRIPTOR);
        }

        public static IRunnableCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRunnableCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRunnableCallback)) {
                return (IRunnableCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "run";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRunnableCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRunnableCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                Bundle run = run(bundle);
                parcel2.writeNoException();
                parcel2.writeTypedObject(run, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRunnableCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRunnableCallback.DESCRIPTOR;
            }

            @Override // android.os.IRunnableCallback
            public Bundle run(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRunnableCallback.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
