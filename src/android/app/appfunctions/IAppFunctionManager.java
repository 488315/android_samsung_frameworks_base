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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAppFunctionManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAppFunctionManager)) {
                return (IAppFunctionManager) iInterfaceQueryLocalInterface;
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
                IExecuteAppFunctionCallback iExecuteAppFunctionCallbackAsInterface = IExecuteAppFunctionCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                ICancellationSignal iCancellationSignalExecuteAppFunction = executeAppFunction(executeAppFunctionAidlRequest, iExecuteAppFunctionCallbackAsInterface);
                parcel2.writeNoException();
                parcel2.writeStrongInterface(iCancellationSignalExecuteAppFunction);
            } else if (i == 2) {
                String string = parcel.readString();
                String string2 = parcel.readString();
                UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                int i3 = parcel.readInt();
                IAppFunctionEnabledCallback iAppFunctionEnabledCallbackAsInterface = IAppFunctionEnabledCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setAppFunctionEnabled(string, string2, userHandle, i3, iAppFunctionEnabledCallbackAsInterface);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppFunctionManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(executeAppFunctionAidlRequest, 0);
                    parcelObtain.writeStrongInterface(iExecuteAppFunctionCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ICancellationSignal.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.appfunctions.IAppFunctionManager
            public void setAppFunctionEnabled(String str, String str2, UserHandle userHandle, int i, IAppFunctionEnabledCallback iAppFunctionEnabledCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAppFunctionManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iAppFunctionEnabledCallback);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
