package android.app.appfunctions;

import android.app.appfunctions.ICancellationCallback;
import android.app.appfunctions.IExecuteAppFunctionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAppFunctionService extends IInterface {
    public static final String DESCRIPTOR = "android.app.appfunctions.IAppFunctionService";

    void executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, ICancellationCallback iCancellationCallback, IExecuteAppFunctionCallback iExecuteAppFunctionCallback) throws RemoteException;

    public static class Default implements IAppFunctionService {
        @Override // android.app.appfunctions.IAppFunctionService
        public void executeAppFunction(ExecuteAppFunctionRequest request, String callingPackage, ICancellationCallback cancellationCallback, IExecuteAppFunctionCallback callback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAppFunctionService {
        static final int TRANSACTION_executeAppFunction = 1;

        public Stub() {
            attachInterface(this, IAppFunctionService.DESCRIPTOR);
        }

        public static IAppFunctionService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAppFunctionService.DESCRIPTOR);
            if (iin != null && (iin instanceof IAppFunctionService)) {
                return (IAppFunctionService) iin;
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
                    return "executeAppFunction";
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
                data.enforceInterface(IAppFunctionService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAppFunctionService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ExecuteAppFunctionRequest _arg0 = (ExecuteAppFunctionRequest) data.readTypedObject(ExecuteAppFunctionRequest.CREATOR);
                    String _arg1 = data.readString();
                    ICancellationCallback _arg2 = ICancellationCallback.Stub.asInterface(data.readStrongBinder());
                    IExecuteAppFunctionCallback _arg3 = IExecuteAppFunctionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    executeAppFunction(_arg0, _arg1, _arg2, _arg3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAppFunctionService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppFunctionService.DESCRIPTOR;
            }

            @Override // android.app.appfunctions.IAppFunctionService
            public void executeAppFunction(ExecuteAppFunctionRequest request, String callingPackage, ICancellationCallback cancellationCallback, IExecuteAppFunctionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IAppFunctionService.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeString(callingPackage);
                    _data.writeStrongInterface(cancellationCallback);
                    _data.writeStrongInterface(callback);
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
