package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ITransientNotificationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.ITransientNotificationCallback";

    public static class Default implements ITransientNotificationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.ITransientNotificationCallback
        public void onToastHidden() throws RemoteException {
        }

        @Override // android.app.ITransientNotificationCallback
        public void onToastShown() throws RemoteException {
        }
    }

    void onToastHidden() throws RemoteException;

    void onToastShown() throws RemoteException;

    public static abstract class Stub extends Binder implements ITransientNotificationCallback {
        static final int TRANSACTION_onToastHidden = 2;
        static final int TRANSACTION_onToastShown = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ITransientNotificationCallback.DESCRIPTOR);
        }

        public static ITransientNotificationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITransientNotificationCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITransientNotificationCallback)) {
                return (ITransientNotificationCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onToastShown";
            }
            if (i != 2) {
                return null;
            }
            return "onToastHidden";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITransientNotificationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITransientNotificationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onToastShown();
            } else if (i == 2) {
                onToastHidden();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITransientNotificationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITransientNotificationCallback.DESCRIPTOR;
            }

            @Override // android.app.ITransientNotificationCallback
            public void onToastShown() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITransientNotificationCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.ITransientNotificationCallback
            public void onToastHidden() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITransientNotificationCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
