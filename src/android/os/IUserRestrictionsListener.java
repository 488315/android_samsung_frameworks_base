package android.os;

/* loaded from: classes3.dex */
public interface IUserRestrictionsListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.IUserRestrictionsListener";

    public static class Default implements IUserRestrictionsListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IUserRestrictionsListener
        public void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2) throws RemoteException {
        }
    }

    void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2) throws RemoteException;

    public static abstract class Stub extends Binder implements IUserRestrictionsListener {
        static final int TRANSACTION_onUserRestrictionsChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IUserRestrictionsListener.DESCRIPTOR);
        }

        public static IUserRestrictionsListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUserRestrictionsListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUserRestrictionsListener)) {
                return (IUserRestrictionsListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onUserRestrictionsChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUserRestrictionsListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUserRestrictionsListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                parcel.enforceNoDataAvail();
                onUserRestrictionsChanged(readInt, bundle, bundle2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IUserRestrictionsListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUserRestrictionsListener.DESCRIPTOR;
            }

            @Override // android.os.IUserRestrictionsListener
            public void onUserRestrictionsChanged(int i, Bundle bundle, Bundle bundle2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUserRestrictionsListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(bundle2, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
