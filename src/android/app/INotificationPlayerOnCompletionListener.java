package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface INotificationPlayerOnCompletionListener extends IInterface {
    public static final String DESCRIPTOR = "android.app.INotificationPlayerOnCompletionListener";

    public static class Default implements INotificationPlayerOnCompletionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.INotificationPlayerOnCompletionListener
        public void onCompletion() throws RemoteException {
        }
    }

    void onCompletion() throws RemoteException;

    public static abstract class Stub extends Binder implements INotificationPlayerOnCompletionListener {
        static final int TRANSACTION_onCompletion = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, INotificationPlayerOnCompletionListener.DESCRIPTOR);
        }

        public static INotificationPlayerOnCompletionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INotificationPlayerOnCompletionListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INotificationPlayerOnCompletionListener)) {
                return (INotificationPlayerOnCompletionListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCompletion";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(INotificationPlayerOnCompletionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(INotificationPlayerOnCompletionListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onCompletion();
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements INotificationPlayerOnCompletionListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return INotificationPlayerOnCompletionListener.DESCRIPTOR;
            }

            @Override // android.app.INotificationPlayerOnCompletionListener
            public void onCompletion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INotificationPlayerOnCompletionListener.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
