package android.service.settings.preferences;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ISetValueCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.settings.preferences.ISetValueCallback";

    public static class Default implements ISetValueCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.settings.preferences.ISetValueCallback
        public void onFailure() throws RemoteException {
        }

        @Override // android.service.settings.preferences.ISetValueCallback
        public void onSuccess(SetValueResult setValueResult) throws RemoteException {
        }
    }

    void onFailure() throws RemoteException;

    void onSuccess(SetValueResult setValueResult) throws RemoteException;

    public static abstract class Stub extends Binder implements ISetValueCallback {
        static final int TRANSACTION_onFailure = 2;
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
            attachInterface(this, ISetValueCallback.DESCRIPTOR);
        }

        public static ISetValueCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISetValueCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISetValueCallback)) {
                return (ISetValueCallback) queryLocalInterface;
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
            return "onFailure";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISetValueCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISetValueCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SetValueResult setValueResult = (SetValueResult) parcel.readTypedObject(SetValueResult.CREATOR);
                parcel.enforceNoDataAvail();
                onSuccess(setValueResult);
            } else if (i == 2) {
                onFailure();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISetValueCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISetValueCallback.DESCRIPTOR;
            }

            @Override // android.service.settings.preferences.ISetValueCallback
            public void onSuccess(SetValueResult setValueResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISetValueCallback.DESCRIPTOR);
                    obtain.writeTypedObject(setValueResult, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.settings.preferences.ISetValueCallback
            public void onFailure() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISetValueCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
