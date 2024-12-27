package android.os;

/* loaded from: classes3.dex */
public interface IRunnableCallback extends IInterface {
    public static final String DESCRIPTOR = "android.os.IRunnableCallback";

    Bundle run(Bundle bundle) throws RemoteException;

    public static class Default implements IRunnableCallback {
        @Override // android.os.IRunnableCallback
        public Bundle run(Bundle arg1) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRunnableCallback {
        static final int TRANSACTION_run = 1;

        public Stub() {
            attachInterface(this, IRunnableCallback.DESCRIPTOR);
        }

        public static IRunnableCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRunnableCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRunnableCallback)) {
                return (IRunnableCallback) iin;
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
                    return "run";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IRunnableCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRunnableCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    Bundle _arg0 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    Bundle _result = run(_arg0);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRunnableCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRunnableCallback.DESCRIPTOR;
            }

            @Override // android.os.IRunnableCallback
            public Bundle run(Bundle arg1) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRunnableCallback.DESCRIPTOR);
                    _data.writeTypedObject(arg1, 0);
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
