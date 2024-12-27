package android.app.appfunctions;

import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ICancellationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.appfunctions.ICancellationCallback";

    void sendCancellationTransport(ICancellationSignal iCancellationSignal) throws RemoteException;

    public static class Default implements ICancellationCallback {
        @Override // android.app.appfunctions.ICancellationCallback
        public void sendCancellationTransport(ICancellationSignal cancellationTransport)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICancellationCallback {
        static final int TRANSACTION_sendCancellationTransport = 1;

        public Stub() {
            attachInterface(this, ICancellationCallback.DESCRIPTOR);
        }

        public static ICancellationCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICancellationCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ICancellationCallback)) {
                return (ICancellationCallback) iin;
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
                    return "sendCancellationTransport";
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
                data.enforceInterface(ICancellationCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICancellationCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ICancellationSignal _arg0 =
                            ICancellationSignal.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    sendCancellationTransport(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICancellationCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICancellationCallback.DESCRIPTOR;
            }

            @Override // android.app.appfunctions.ICancellationCallback
            public void sendCancellationTransport(ICancellationSignal cancellationTransport)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICancellationCallback.DESCRIPTOR);
                    _data.writeStrongInterface(cancellationTransport);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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
