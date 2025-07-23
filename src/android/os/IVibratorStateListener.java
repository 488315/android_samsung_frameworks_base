package android.os;

/* loaded from: classes3.dex */
public interface IVibratorStateListener extends IInterface {
    public static final String DESCRIPTOR = "android.os.IVibratorStateListener";

    public static class Default implements IVibratorStateListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IVibratorStateListener
        public void onVibrating(boolean z) throws RemoteException {
        }
    }

    void onVibrating(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IVibratorStateListener {
        static final int TRANSACTION_onVibrating = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IVibratorStateListener.DESCRIPTOR);
        }

        public static IVibratorStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVibratorStateListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVibratorStateListener)) {
                return (IVibratorStateListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onVibrating";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVibratorStateListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVibratorStateListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onVibrating(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IVibratorStateListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IVibratorStateListener.DESCRIPTOR;
            }

            @Override // android.os.IVibratorStateListener
            public void onVibrating(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IVibratorStateListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
