package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes5.dex */
public interface IUnhandledDragCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IUnhandledDragCallback";

    public static class Default implements IUnhandledDragCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IUnhandledDragCallback
        public void notifyUnhandledDropComplete(boolean z) throws RemoteException {
        }
    }

    void notifyUnhandledDropComplete(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IUnhandledDragCallback {
        static final int TRANSACTION_notifyUnhandledDropComplete = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IUnhandledDragCallback.DESCRIPTOR);
        }

        public static IUnhandledDragCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IUnhandledDragCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IUnhandledDragCallback)) {
                return (IUnhandledDragCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "notifyUnhandledDropComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IUnhandledDragCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IUnhandledDragCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                notifyUnhandledDropComplete(readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IUnhandledDragCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnhandledDragCallback.DESCRIPTOR;
            }

            @Override // android.window.IUnhandledDragCallback
            public void notifyUnhandledDropComplete(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IUnhandledDragCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
