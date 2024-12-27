package android.os;

/* loaded from: classes3.dex */
public interface ISemHcmManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISemHcmManager";

    boolean getHcmEnable() throws RemoteException;

    public static class Default implements ISemHcmManager {
        @Override // android.os.ISemHcmManager
        public boolean getHcmEnable() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISemHcmManager {
        static final int TRANSACTION_getHcmEnable = 1;

        public Stub() {
            attachInterface(this, ISemHcmManager.DESCRIPTOR);
        }

        public static ISemHcmManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISemHcmManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISemHcmManager)) {
                return (ISemHcmManager) iin;
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
                    return "getHcmEnable";
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
                data.enforceInterface(ISemHcmManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISemHcmManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _result = getHcmEnable();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISemHcmManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISemHcmManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
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
