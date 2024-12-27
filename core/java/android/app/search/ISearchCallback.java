package android.app.search;

import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISearchCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.search.ISearchCallback";

    void onResult(ParceledListSlice parceledListSlice) throws RemoteException;

    public static class Default implements ISearchCallback {
        @Override // android.app.search.ISearchCallback
        public void onResult(ParceledListSlice result) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISearchCallback {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, ISearchCallback.DESCRIPTOR);
        }

        public static ISearchCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISearchCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISearchCallback)) {
                return (ISearchCallback) iin;
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
                data.enforceInterface(ISearchCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISearchCallback.DESCRIPTOR);
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

        private static class Proxy implements ISearchCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISearchCallback.DESCRIPTOR;
            }

            @Override // android.app.search.ISearchCallback
            public void onResult(ParceledListSlice result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISearchCallback.DESCRIPTOR);
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
