package android.app.prediction;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPredictionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.prediction.IPredictionCallback";

    void onResult(ParceledListSlice parceledListSlice) throws RemoteException;

    public static class Default implements IPredictionCallback {
        @Override // android.app.prediction.IPredictionCallback
        public void onResult(ParceledListSlice result) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IPredictionCallback {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, IPredictionCallback.DESCRIPTOR);
        }

        public static IPredictionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPredictionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IPredictionCallback)) {
                return (IPredictionCallback) iin;
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
                    return "onResult";
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
                data.enforceInterface(IPredictionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IPredictionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ParceledListSlice _arg0 =
                            (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
                    data.enforceNoDataAvail();
                    onResult(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPredictionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPredictionCallback.DESCRIPTOR;
            }

            @Override // android.app.prediction.IPredictionCallback
            public void onResult(ParceledListSlice result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPredictionCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
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
