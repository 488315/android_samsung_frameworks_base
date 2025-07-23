package android.os;

/* loaded from: classes3.dex */
public interface ISecurityStateManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISecurityStateManager";

    public static class Default implements ISecurityStateManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ISecurityStateManager
        public Bundle getGlobalSecurityState() throws RemoteException {
            return null;
        }
    }

    Bundle getGlobalSecurityState() throws RemoteException;

    public static abstract class Stub extends Binder implements ISecurityStateManager {
        static final int TRANSACTION_getGlobalSecurityState = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISecurityStateManager.DESCRIPTOR);
        }

        public static ISecurityStateManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISecurityStateManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISecurityStateManager)) {
                return (ISecurityStateManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getGlobalSecurityState";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISecurityStateManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISecurityStateManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Bundle globalSecurityState = getGlobalSecurityState();
                parcel2.writeNoException();
                parcel2.writeTypedObject(globalSecurityState, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISecurityStateManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISecurityStateManager.DESCRIPTOR;
            }

            @Override // android.os.ISecurityStateManager
            public Bundle getGlobalSecurityState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISecurityStateManager.DESCRIPTOR);
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
