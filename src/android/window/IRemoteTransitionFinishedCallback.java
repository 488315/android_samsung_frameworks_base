package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes5.dex */
public interface IRemoteTransitionFinishedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IRemoteTransitionFinishedCallback";

    public static class Default implements IRemoteTransitionFinishedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.IRemoteTransitionFinishedCallback
        public void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) throws RemoteException {
        }
    }

    void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemoteTransitionFinishedCallback {
        static final int TRANSACTION_onTransitionFinished = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IRemoteTransitionFinishedCallback.DESCRIPTOR);
        }

        public static IRemoteTransitionFinishedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteTransitionFinishedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRemoteTransitionFinishedCallback)) {
                return (IRemoteTransitionFinishedCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onTransitionFinished";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteTransitionFinishedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteTransitionFinishedCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                WindowContainerTransaction windowContainerTransaction = (WindowContainerTransaction) parcel.readTypedObject(WindowContainerTransaction.CREATOR);
                SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) parcel.readTypedObject(SurfaceControl.Transaction.CREATOR);
                parcel.enforceNoDataAvail();
                onTransitionFinished(windowContainerTransaction, transaction);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IRemoteTransitionFinishedCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTransitionFinishedCallback.DESCRIPTOR;
            }

            @Override // android.window.IRemoteTransitionFinishedCallback
            public void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteTransitionFinishedCallback.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
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
