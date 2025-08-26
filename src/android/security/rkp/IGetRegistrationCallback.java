package android.security.rkp;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.security.rkp.IRegistration;

/* loaded from: classes3.dex */
public interface IGetRegistrationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.security.rkp.IGetRegistrationCallback";

    public static class Default implements IGetRegistrationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.rkp.IGetRegistrationCallback
        public void onCancel() throws RemoteException {
        }

        @Override // android.security.rkp.IGetRegistrationCallback
        public void onError(String str) throws RemoteException {
        }

        @Override // android.security.rkp.IGetRegistrationCallback
        public void onSuccess(IRegistration iRegistration) throws RemoteException {
        }
    }

    void onCancel() throws RemoteException;

    void onError(String str) throws RemoteException;

    void onSuccess(IRegistration iRegistration) throws RemoteException;

    public static abstract class Stub extends Binder implements IGetRegistrationCallback {
        static final int TRANSACTION_onCancel = 2;
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IGetRegistrationCallback.DESCRIPTOR);
        }

        public static IGetRegistrationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGetRegistrationCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGetRegistrationCallback)) {
                return (IGetRegistrationCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSuccess";
            }
            if (i == 2) {
                return "onCancel";
            }
            if (i != 3) {
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
                parcel.enforceInterface(IGetRegistrationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetRegistrationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IRegistration iRegistrationAsInterface = IRegistration.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                onSuccess(iRegistrationAsInterface);
            } else if (i == 2) {
                onCancel();
            } else if (i == 3) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(string);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGetRegistrationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetRegistrationCallback.DESCRIPTOR;
            }

            @Override // android.security.rkp.IGetRegistrationCallback
            public void onSuccess(IRegistration iRegistration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetRegistrationCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRegistration);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.rkp.IGetRegistrationCallback
            public void onCancel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetRegistrationCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.security.rkp.IGetRegistrationCallback
            public void onError(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IGetRegistrationCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
