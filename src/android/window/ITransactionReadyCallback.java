package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes5.dex */
public interface ITransactionReadyCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITransactionReadyCallback";

    public static class Default implements ITransactionReadyCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ITransactionReadyCallback
        public void onTransactionReady(SurfaceControl.Transaction transaction) throws RemoteException {
        }
    }

    void onTransactionReady(SurfaceControl.Transaction transaction) throws RemoteException;

    public static abstract class Stub extends Binder implements ITransactionReadyCallback {
        static final int TRANSACTION_onTransactionReady = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ITransactionReadyCallback.DESCRIPTOR);
        }

        public static ITransactionReadyCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ITransactionReadyCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITransactionReadyCallback)) {
                return (ITransactionReadyCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onTransactionReady";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITransactionReadyCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITransactionReadyCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                parcel.enforceNoDataAvail();
                onTransactionReady(transaction);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITransactionReadyCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITransactionReadyCallback.DESCRIPTOR;
            }

            @Override // android.window.ITransactionReadyCallback
            public void onTransactionReady(SurfaceControl.Transaction transaction) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ITransactionReadyCallback.DESCRIPTOR);
                    obtain.writeTypedObject(transaction, 0);
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
