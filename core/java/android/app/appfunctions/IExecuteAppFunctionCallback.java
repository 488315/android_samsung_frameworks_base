package android.app.appfunctions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IExecuteAppFunctionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.appfunctions.IExecuteAppFunctionCallback";

    void onError(AppFunctionException appFunctionException) throws RemoteException;

    void onSuccess(ExecuteAppFunctionResponse executeAppFunctionResponse) throws RemoteException;

    public static class Default implements IExecuteAppFunctionCallback {
        @Override // android.app.appfunctions.IExecuteAppFunctionCallback
        public void onSuccess(ExecuteAppFunctionResponse result) throws RemoteException {
        }

        @Override // android.app.appfunctions.IExecuteAppFunctionCallback
        public void onError(AppFunctionException exception) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IExecuteAppFunctionCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, IExecuteAppFunctionCallback.DESCRIPTOR);
        }

        public static IExecuteAppFunctionCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IExecuteAppFunctionCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IExecuteAppFunctionCallback)) {
                return (IExecuteAppFunctionCallback) iin;
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
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IExecuteAppFunctionCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IExecuteAppFunctionCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ExecuteAppFunctionResponse _arg0 = (ExecuteAppFunctionResponse) data.readTypedObject(ExecuteAppFunctionResponse.CREATOR);
                    data.enforceNoDataAvail();
                    onSuccess(_arg0);
                    return true;
                case 2:
                    AppFunctionException _arg02 = (AppFunctionException) data.readTypedObject(AppFunctionException.CREATOR);
                    data.enforceNoDataAvail();
                    onError(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IExecuteAppFunctionCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExecuteAppFunctionCallback.DESCRIPTOR;
            }

            @Override // android.app.appfunctions.IExecuteAppFunctionCallback
            public void onSuccess(ExecuteAppFunctionResponse result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IExecuteAppFunctionCallback.DESCRIPTOR);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.appfunctions.IExecuteAppFunctionCallback
            public void onError(AppFunctionException exception) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IExecuteAppFunctionCallback.DESCRIPTOR);
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
