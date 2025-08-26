package android.app.appfunctions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IExecuteAppFunctionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.appfunctions.IExecuteAppFunctionCallback";

    public static class Default implements IExecuteAppFunctionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.appfunctions.IExecuteAppFunctionCallback
        public void onError(AppFunctionException appFunctionException) throws RemoteException {
        }

        @Override // android.app.appfunctions.IExecuteAppFunctionCallback
        public void onSuccess(ExecuteAppFunctionResponse executeAppFunctionResponse) throws RemoteException {
        }
    }

    void onError(AppFunctionException appFunctionException) throws RemoteException;

    void onSuccess(ExecuteAppFunctionResponse executeAppFunctionResponse) throws RemoteException;

    public static abstract class Stub extends Binder implements IExecuteAppFunctionCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IExecuteAppFunctionCallback.DESCRIPTOR);
        }

        public static IExecuteAppFunctionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IExecuteAppFunctionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IExecuteAppFunctionCallback)) {
                return (IExecuteAppFunctionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i != 2) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IExecuteAppFunctionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IExecuteAppFunctionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ExecuteAppFunctionResponse executeAppFunctionResponse = (ExecuteAppFunctionResponse) parcel.readTypedObject(ExecuteAppFunctionResponse.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(executeAppFunctionResponse);
            } else if (i == 2) {
                AppFunctionException appFunctionException = (AppFunctionException) parcel.readTypedObject(AppFunctionException.CREATOR);
                parcel.enforceNoDataAvail();
                onError(appFunctionException);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IExecuteAppFunctionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IExecuteAppFunctionCallback.DESCRIPTOR;
            }

            @Override // android.app.appfunctions.IExecuteAppFunctionCallback
            public void onSuccess(ExecuteAppFunctionResponse executeAppFunctionResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IExecuteAppFunctionCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(executeAppFunctionResponse, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.appfunctions.IExecuteAppFunctionCallback
            public void onError(AppFunctionException appFunctionException) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IExecuteAppFunctionCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(appFunctionException, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
