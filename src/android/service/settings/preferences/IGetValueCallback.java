package android.service.settings.preferences;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IGetValueCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.settings.preferences.IGetValueCallback";

    public static class Default implements IGetValueCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.settings.preferences.IGetValueCallback
        public void onFailure() throws RemoteException {
        }

        @Override // android.service.settings.preferences.IGetValueCallback
        public void onSuccess(GetValueResult getValueResult) throws RemoteException {
        }
    }

    void onFailure() throws RemoteException;

    void onSuccess(GetValueResult getValueResult) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetValueCallback {
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IGetValueCallback.DESCRIPTOR);
        }

        public static IGetValueCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGetValueCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGetValueCallback)) {
                return (IGetValueCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 2) {
                return "onSuccess";
            }
            if (i != 3) {
                return null;
            }
            return "onFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGetValueCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetValueCallback.DESCRIPTOR);
                return true;
            }
            if (i == 2) {
                GetValueResult getValueResult = (GetValueResult) parcel.readTypedObject(GetValueResult.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(getValueResult);
            } else if (i == 3) {
                onFailure();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGetValueCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetValueCallback.DESCRIPTOR;
            }

            @Override // android.service.settings.preferences.IGetValueCallback
            public void onSuccess(GetValueResult getValueResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGetValueCallback.DESCRIPTOR);
                    obtain.writeTypedObject(getValueResult, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.settings.preferences.IGetValueCallback
            public void onFailure() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGetValueCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
