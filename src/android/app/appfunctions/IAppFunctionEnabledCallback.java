package android.app.appfunctions;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelableException;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IAppFunctionEnabledCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.appfunctions.IAppFunctionEnabledCallback";

    public static class Default implements IAppFunctionEnabledCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.appfunctions.IAppFunctionEnabledCallback
        public void onError(ParcelableException parcelableException) throws RemoteException {
        }

        @Override // android.app.appfunctions.IAppFunctionEnabledCallback
        public void onSuccess() throws RemoteException {
        }
    }

    void onError(ParcelableException parcelableException) throws RemoteException;

    void onSuccess() throws RemoteException;

    public static abstract class Stub extends Binder implements IAppFunctionEnabledCallback {
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
            attachInterface(this, IAppFunctionEnabledCallback.DESCRIPTOR);
        }

        public static IAppFunctionEnabledCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAppFunctionEnabledCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppFunctionEnabledCallback)) {
                return (IAppFunctionEnabledCallback) queryLocalInterface;
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
                parcel.enforceInterface(IAppFunctionEnabledCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAppFunctionEnabledCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onSuccess();
            } else if (i == 2) {
                ParcelableException parcelableException = (ParcelableException) parcel.readTypedObject(ParcelableException.CREATOR);
                parcel.enforceNoDataAvail();
                onError(parcelableException);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAppFunctionEnabledCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAppFunctionEnabledCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.appfunctions.IAppFunctionEnabledCallback
            public void onError(ParcelableException parcelableException) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAppFunctionEnabledCallback.DESCRIPTOR);
                    obtain.writeTypedObject(parcelableException, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
