package android.os;

/* loaded from: classes3.dex */
public interface IClientCallback extends IInterface {
    public static final String DESCRIPTOR = "android.os.IClientCallback";

    public static class Default implements IClientCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IClientCallback
        public void onClients(IBinder iBinder, boolean z) throws RemoteException {
        }
    }

    void onClients(IBinder iBinder, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IClientCallback {
        static final int TRANSACTION_onClients = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IClientCallback.DESCRIPTOR);
        }

        public static IClientCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IClientCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IClientCallback)) {
                return (IClientCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onClients";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IClientCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IClientCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onClients(readStrongBinder, readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IClientCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IClientCallback.DESCRIPTOR;
            }

            @Override // android.os.IClientCallback
            public void onClients(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IClientCallback.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
