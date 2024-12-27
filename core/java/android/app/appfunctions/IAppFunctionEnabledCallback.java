package android.app.appfunctions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelableException;
import android.os.RemoteException;

public interface IAppFunctionEnabledCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.appfunctions.IAppFunctionEnabledCallback";

    void onError(ParcelableException parcelableException) throws RemoteException;

    void onSuccess() throws RemoteException;

    public static class Default implements IAppFunctionEnabledCallback {
        @Override // android.app.appfunctions.IAppFunctionEnabledCallback
        public void onSuccess() throws RemoteException {}

        @Override // android.app.appfunctions.IAppFunctionEnabledCallback
        public void onError(ParcelableException exception) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IAppFunctionEnabledCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, IAppFunctionEnabledCallback.DESCRIPTOR);
        }

        public static IAppFunctionEnabledCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAppFunctionEnabledCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IAppFunctionEnabledCallback)) {
                return (IAppFunctionEnabledCallback) iin;
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
                    return "onSuccess";
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
                data.enforceInterface(IAppFunctionEnabledCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAppFunctionEnabledCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onSuccess();
                    return true;
                case 2:
                    ParcelableException _arg0 =
                            (ParcelableException) data.readTypedObject(ParcelableException.CREATOR);
                    data.enforceNoDataAvail();
                    onError(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAppFunctionEnabledCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppFunctionEnabledCallback.DESCRIPTOR;
            }

            @Override // android.app.appfunctions.IAppFunctionEnabledCallback
            public void onSuccess() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAppFunctionEnabledCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.appfunctions.IAppFunctionEnabledCallback
            public void onError(ParcelableException exception) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAppFunctionEnabledCallback.DESCRIPTOR);
                    _data.writeTypedObject(exception, 0);
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
