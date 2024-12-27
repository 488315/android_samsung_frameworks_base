package android.app.admin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControlViewHost;

public interface IKeyguardCallback extends IInterface {
    public static final String DESCRIPTOR = "android.app.admin.IKeyguardCallback";

    void onDismiss() throws RemoteException;

    void onRemoteContentReady(SurfaceControlViewHost.SurfacePackage surfacePackage)
            throws RemoteException;

    public static class Default implements IKeyguardCallback {
        @Override // android.app.admin.IKeyguardCallback
        public void onRemoteContentReady(SurfaceControlViewHost.SurfacePackage surfacePackage)
                throws RemoteException {}

        @Override // android.app.admin.IKeyguardCallback
        public void onDismiss() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IKeyguardCallback {
        static final int TRANSACTION_onDismiss = 2;
        static final int TRANSACTION_onRemoteContentReady = 1;

        public Stub() {
            attachInterface(this, IKeyguardCallback.DESCRIPTOR);
        }

        public static IKeyguardCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IKeyguardCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IKeyguardCallback)) {
                return (IKeyguardCallback) iin;
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
                    return "onRemoteContentReady";
                case 2:
                    return "onDismiss";
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
                data.enforceInterface(IKeyguardCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IKeyguardCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SurfaceControlViewHost.SurfacePackage _arg0 =
                            (SurfaceControlViewHost.SurfacePackage)
                                    data.readTypedObject(
                                            SurfaceControlViewHost.SurfacePackage.CREATOR);
                    data.enforceNoDataAvail();
                    onRemoteContentReady(_arg0);
                    return true;
                case 2:
                    onDismiss();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IKeyguardCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IKeyguardCallback.DESCRIPTOR;
            }

            @Override // android.app.admin.IKeyguardCallback
            public void onRemoteContentReady(SurfaceControlViewHost.SurfacePackage surfacePackage)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IKeyguardCallback.DESCRIPTOR);
                    _data.writeTypedObject(surfacePackage, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.app.admin.IKeyguardCallback
            public void onDismiss() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IKeyguardCallback.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
