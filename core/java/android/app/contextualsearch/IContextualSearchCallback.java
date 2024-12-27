package android.app.contextualsearch;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelableException;
import android.os.RemoteException;

public interface IContextualSearchCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.app.contextualsearch.IContextualSearchCallback";

    void onError(ParcelableException parcelableException) throws RemoteException;

    void onResult(ContextualSearchState contextualSearchState) throws RemoteException;

    public static class Default implements IContextualSearchCallback {
        @Override // android.app.contextualsearch.IContextualSearchCallback
        public void onResult(ContextualSearchState state) throws RemoteException {}

        @Override // android.app.contextualsearch.IContextualSearchCallback
        public void onError(ParcelableException error) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IContextualSearchCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, IContextualSearchCallback.DESCRIPTOR);
        }

        public static IContextualSearchCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IContextualSearchCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IContextualSearchCallback)) {
                return (IContextualSearchCallback) iin;
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
                case 2:
                    return "onError";
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
                data.enforceInterface(IContextualSearchCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IContextualSearchCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ContextualSearchState _arg0 =
                            (ContextualSearchState)
                                    data.readTypedObject(ContextualSearchState.CREATOR);
                    data.enforceNoDataAvail();
                    onResult(_arg0);
                    return true;
                case 2:
                    ParcelableException _arg02 =
                            (ParcelableException) data.readTypedObject(ParcelableException.CREATOR);
                    data.enforceNoDataAvail();
                    onError(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IContextualSearchCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IContextualSearchCallback.DESCRIPTOR;
            }

            @Override // android.app.contextualsearch.IContextualSearchCallback
            public void onResult(ContextualSearchState state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IContextualSearchCallback.DESCRIPTOR);
                    _data.writeTypedObject(state, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.contextualsearch.IContextualSearchCallback
            public void onError(ParcelableException error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IContextualSearchCallback.DESCRIPTOR);
                    _data.writeTypedObject(error, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
