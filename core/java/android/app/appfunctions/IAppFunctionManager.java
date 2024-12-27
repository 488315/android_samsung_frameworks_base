package android.app.appfunctions;

import android.app.appfunctions.IAppFunctionEnabledCallback;
import android.app.appfunctions.IExecuteAppFunctionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;

/* loaded from: classes.dex */
public interface IAppFunctionManager extends IInterface {
    public static final String DESCRIPTOR = "android.app.appfunctions.IAppFunctionManager";

    ICancellationSignal executeAppFunction(ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest, IExecuteAppFunctionCallback iExecuteAppFunctionCallback) throws RemoteException;

    void setAppFunctionEnabled(String str, String str2, UserHandle userHandle, int i, IAppFunctionEnabledCallback iAppFunctionEnabledCallback) throws RemoteException;

    public static class Default implements IAppFunctionManager {
        @Override // android.app.appfunctions.IAppFunctionManager
        public ICancellationSignal executeAppFunction(ExecuteAppFunctionAidlRequest request, IExecuteAppFunctionCallback callback) throws RemoteException {
            return null;
        }

        @Override // android.app.appfunctions.IAppFunctionManager
        public void setAppFunctionEnabled(String callingPackage, String functionIdentifier, UserHandle userHandle, int enabledState, IAppFunctionEnabledCallback callback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAppFunctionManager {
        static final int TRANSACTION_executeAppFunction = 1;
        static final int TRANSACTION_setAppFunctionEnabled = 2;

        public Stub() {
            attachInterface(this, IAppFunctionManager.DESCRIPTOR);
        }

        public static IAppFunctionManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IAppFunctionManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IAppFunctionManager)) {
                return (IAppFunctionManager) iin;
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
                case 2:
                    return "setAppFunctionEnabled";
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
                data.enforceInterface(IAppFunctionManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IAppFunctionManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ExecuteAppFunctionAidlRequest _arg0 = (ExecuteAppFunctionAidlRequest) data.readTypedObject(ExecuteAppFunctionAidlRequest.CREATOR);
                    IExecuteAppFunctionCallback _arg1 = IExecuteAppFunctionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    ICancellationSignal _result = executeAppFunction(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    UserHandle _arg2 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    int _arg3 = data.readInt();
                    IAppFunctionEnabledCallback _arg4 = IAppFunctionEnabledCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setAppFunctionEnabled(_arg02, _arg12, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAppFunctionManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppFunctionManager.DESCRIPTOR;
            }

            @Override // android.app.appfunctions.IAppFunctionManager
            public ICancellationSignal executeAppFunction(ExecuteAppFunctionAidlRequest request, IExecuteAppFunctionCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAppFunctionManager.DESCRIPTOR);
                    _data.writeTypedObject(request, 0);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ICancellationSignal _result = ICancellationSignal.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.appfunctions.IAppFunctionManager
            public void setAppFunctionEnabled(String callingPackage, String functionIdentifier, UserHandle userHandle, int enabledState, IAppFunctionEnabledCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IAppFunctionManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(functionIdentifier);
                    _data.writeTypedObject(userHandle, 0);
                    _data.writeInt(enabledState);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
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
