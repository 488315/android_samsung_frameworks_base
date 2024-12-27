package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRequestFinishCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.IRequestFinishCallback";

    void requestFinish() throws RemoteException;

    public static class Default implements IRequestFinishCallback {
        @Override // android.app.IRequestFinishCallback
        public void requestFinish() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRequestFinishCallback {
        static final int TRANSACTION_requestFinish = 1;

        public Stub() {
            attachInterface(this, IRequestFinishCallback.DESCRIPTOR);
        }

        public static IRequestFinishCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRequestFinishCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRequestFinishCallback)) {
                return (IRequestFinishCallback) iin;
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
                    return "requestFinish";
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
                data.enforceInterface(IRequestFinishCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRequestFinishCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    requestFinish();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRequestFinishCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRequestFinishCallback.DESCRIPTOR;
            }

            @Override // android.app.IRequestFinishCallback
            public void requestFinish() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IRequestFinishCallback.DESCRIPTOR);
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
