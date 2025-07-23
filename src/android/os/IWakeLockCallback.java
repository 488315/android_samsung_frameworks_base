package android.os;

/* loaded from: classes3.dex */
public interface IWakeLockCallback extends IInterface {
    public static final String DESCRIPTOR = "android.os.IWakeLockCallback";

    public static class Default implements IWakeLockCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IWakeLockCallback
        public void onStateChanged(boolean z) throws RemoteException {
        }
    }

    void onStateChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IWakeLockCallback {
        static final int TRANSACTION_onStateChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWakeLockCallback.DESCRIPTOR);
        }

        public static IWakeLockCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWakeLockCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWakeLockCallback)) {
                return (IWakeLockCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWakeLockCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWakeLockCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onStateChanged(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWakeLockCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWakeLockCallback.DESCRIPTOR;
            }

            @Override // android.os.IWakeLockCallback
            public void onStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWakeLockCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
