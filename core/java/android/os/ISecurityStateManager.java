package android.os;

public interface ISecurityStateManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISecurityStateManager";

    Bundle getGlobalSecurityState() throws RemoteException;

    public static class Default implements ISecurityStateManager {
        @Override // android.os.ISecurityStateManager
        public Bundle getGlobalSecurityState() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISecurityStateManager {
        static final int TRANSACTION_getGlobalSecurityState = 1;

        public Stub() {
            attachInterface(this, ISecurityStateManager.DESCRIPTOR);
        }

        public static ISecurityStateManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISecurityStateManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISecurityStateManager)) {
                return (ISecurityStateManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "getGlobalSecurityState";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISecurityStateManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISecurityStateManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Bundle _result = getGlobalSecurityState();
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISecurityStateManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISecurityStateManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
