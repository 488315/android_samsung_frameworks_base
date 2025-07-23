package android.app.appfunctions;

import android.app.appfunctions.ICancellationCallback;
import android.app.appfunctions.IExecuteAppFunctionCallback;
import android.content.pm.SigningInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAppFunctionService extends IInterface {
    public static final String DESCRIPTOR = "android.app.appfunctions.IAppFunctionService";

    public static class Default implements IAppFunctionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.appfunctions.IAppFunctionService
        public void executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, SigningInfo signingInfo, ICancellationCallback iCancellationCallback, IExecuteAppFunctionCallback iExecuteAppFunctionCallback) throws RemoteException {
        }
    }

    void executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, SigningInfo signingInfo, ICancellationCallback iCancellationCallback, IExecuteAppFunctionCallback iExecuteAppFunctionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppFunctionService {
        static final int TRANSACTION_executeAppFunction = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAppFunctionService.DESCRIPTOR);
        }

        public static IAppFunctionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAppFunctionService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppFunctionService)) {
                return (IAppFunctionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "executeAppFunction";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAppFunctionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppFunctionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ExecuteAppFunctionRequest executeAppFunctionRequest = (ExecuteAppFunctionRequest) parcel.readTypedObject(ExecuteAppFunctionRequest.CREATOR);
                String readString = parcel.readString();
                SigningInfo signingInfo = (SigningInfo) parcel.readTypedObject(SigningInfo.CREATOR);
                ICancellationCallback asInterface = ICancellationCallback.Stub.asInterface(parcel.readStrongBinder());
                IExecuteAppFunctionCallback asInterface2 = IExecuteAppFunctionCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                executeAppFunction(executeAppFunctionRequest, readString, signingInfo, asInterface, asInterface2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAppFunctionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAppFunctionService.DESCRIPTOR;
            }

            @Override // android.app.appfunctions.IAppFunctionService
            public void executeAppFunction(ExecuteAppFunctionRequest executeAppFunctionRequest, String str, SigningInfo signingInfo, ICancellationCallback iCancellationCallback, IExecuteAppFunctionCallback iExecuteAppFunctionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAppFunctionService.DESCRIPTOR);
                    obtain.writeTypedObject(executeAppFunctionRequest, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(signingInfo, 0);
                    obtain.writeStrongInterface(iCancellationCallback);
                    obtain.writeStrongInterface(iExecuteAppFunctionCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
