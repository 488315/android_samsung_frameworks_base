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

    public static class Default implements IAppFunctionManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.appfunctions.IAppFunctionManager
        public ICancellationSignal executeAppFunction(ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest, IExecuteAppFunctionCallback iExecuteAppFunctionCallback) throws RemoteException {
            return null;
        }

        @Override // android.app.appfunctions.IAppFunctionManager
        public void setAppFunctionEnabled(String str, String str2, UserHandle userHandle, int i, IAppFunctionEnabledCallback iAppFunctionEnabledCallback) throws RemoteException {
        }
    }

    ICancellationSignal executeAppFunction(ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest, IExecuteAppFunctionCallback iExecuteAppFunctionCallback) throws RemoteException;

    void setAppFunctionEnabled(String str, String str2, UserHandle userHandle, int i, IAppFunctionEnabledCallback iAppFunctionEnabledCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppFunctionManager {
        static final int TRANSACTION_executeAppFunction = 1;
        static final int TRANSACTION_setAppFunctionEnabled = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IAppFunctionManager.DESCRIPTOR);
        }

        public static IAppFunctionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAppFunctionManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppFunctionManager)) {
                return (IAppFunctionManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "executeAppFunction";
            }
            if (i != 2) {
                return null;
            }
            return "setAppFunctionEnabled";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppFunctionManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppFunctionManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest = (ExecuteAppFunctionAidlRequest) parcel.readTypedObject(ExecuteAppFunctionAidlRequest.CREATOR);
                IExecuteAppFunctionCallback asInterface = IExecuteAppFunctionCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ICancellationSignal executeAppFunction = executeAppFunction(executeAppFunctionAidlRequest, asInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(executeAppFunction);
            } else if (i == 2) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                int readInt = parcel.readInt();
                IAppFunctionEnabledCallback asInterface2 = IAppFunctionEnabledCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setAppFunctionEnabled(readString, readString2, userHandle, readInt, asInterface2);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAppFunctionManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppFunctionManager.DESCRIPTOR;
            }

            @Override // android.app.appfunctions.IAppFunctionManager
            public ICancellationSignal executeAppFunction(ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest, IExecuteAppFunctionCallback iExecuteAppFunctionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppFunctionManager.DESCRIPTOR);
                    obtain.writeTypedObject(executeAppFunctionAidlRequest, 0);
                    obtain.writeStrongInterface(iExecuteAppFunctionCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return ICancellationSignal.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.appfunctions.IAppFunctionManager
            public void setAppFunctionEnabled(String str, String str2, UserHandle userHandle, int i, IAppFunctionEnabledCallback iAppFunctionEnabledCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IAppFunctionManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iAppFunctionEnabledCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
