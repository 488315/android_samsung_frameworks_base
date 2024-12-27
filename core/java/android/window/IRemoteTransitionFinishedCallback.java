package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

public interface IRemoteTransitionFinishedCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IRemoteTransitionFinishedCallback";

    void onTransitionFinished(
            WindowContainerTransaction windowContainerTransaction,
            SurfaceControl.Transaction transaction)
            throws RemoteException;

    public static class Default implements IRemoteTransitionFinishedCallback {
        @Override // android.window.IRemoteTransitionFinishedCallback
        public void onTransitionFinished(
                WindowContainerTransaction wct, SurfaceControl.Transaction sct)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRemoteTransitionFinishedCallback {
        static final int TRANSACTION_onTransitionFinished = 1;

        public Stub() {
            attachInterface(this, IRemoteTransitionFinishedCallback.DESCRIPTOR);
        }

        public static IRemoteTransitionFinishedCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IRemoteTransitionFinishedCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IRemoteTransitionFinishedCallback)) {
                return (IRemoteTransitionFinishedCallback) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "onTransitionFinished";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IRemoteTransitionFinishedCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemoteTransitionFinishedCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    WindowContainerTransaction _arg0 =
                            (WindowContainerTransaction)
                                    data.readTypedObject(WindowContainerTransaction.CREATOR);
                    SurfaceControl.Transaction _arg1 =
                            (SurfaceControl.Transaction)
                                    data.readTypedObject(SurfaceControl.Transaction.CREATOR);
                    data.enforceNoDataAvail();
                    onTransitionFinished(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRemoteTransitionFinishedCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteTransitionFinishedCallback.DESCRIPTOR;
            }

            @Override // android.window.IRemoteTransitionFinishedCallback
            public void onTransitionFinished(
                    WindowContainerTransaction wct, SurfaceControl.Transaction sct)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IRemoteTransitionFinishedCallback.DESCRIPTOR);
                    _data.writeTypedObject(wct, 0);
                    _data.writeTypedObject(sct, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
