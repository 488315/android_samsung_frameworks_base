package android.os;

/* loaded from: classes3.dex */
public interface ISemHcmManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISemHcmManager";

    public static class Default implements ISemHcmManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ISemHcmManager
        public boolean getHcmEnable() throws RemoteException {
            return false;
        }
    }

    boolean getHcmEnable() throws RemoteException;

    public static abstract class Stub extends Binder implements ISemHcmManager {
        static final int TRANSACTION_getHcmEnable = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemHcmManager.DESCRIPTOR);
        }

        public static ISemHcmManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemHcmManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemHcmManager)) {
                return (ISemHcmManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getHcmEnable";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemHcmManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemHcmManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean hcmEnable = getHcmEnable();
                parcel2.writeNoException();
                parcel2.writeBoolean(hcmEnable);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemHcmManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemHcmManager.DESCRIPTOR;
            }

            @Override // android.os.ISemHcmManager
            public boolean getHcmEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHcmManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
