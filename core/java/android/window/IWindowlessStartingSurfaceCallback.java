package android.window;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

public interface IWindowlessStartingSurfaceCallback extends IInterface {
    public static final String DESCRIPTOR = "android.window.IWindowlessStartingSurfaceCallback";

    void onSurfaceAdded(SurfaceControl surfaceControl) throws RemoteException;

    public static class Default implements IWindowlessStartingSurfaceCallback {
        @Override // android.window.IWindowlessStartingSurfaceCallback
        public void onSurfaceAdded(SurfaceControl addedSurface) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWindowlessStartingSurfaceCallback {
        static final int TRANSACTION_onSurfaceAdded = 1;

        public Stub() {
            attachInterface(this, IWindowlessStartingSurfaceCallback.DESCRIPTOR);
        }

        public static IWindowlessStartingSurfaceCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWindowlessStartingSurfaceCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IWindowlessStartingSurfaceCallback)) {
                return (IWindowlessStartingSurfaceCallback) iin;
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
                    return "onSurfaceAdded";
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
                data.enforceInterface(IWindowlessStartingSurfaceCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWindowlessStartingSurfaceCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    SurfaceControl _arg0 =
                            (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
                    data.enforceNoDataAvail();
                    onSurfaceAdded(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWindowlessStartingSurfaceCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWindowlessStartingSurfaceCallback.DESCRIPTOR;
            }

            @Override // android.window.IWindowlessStartingSurfaceCallback
            public void onSurfaceAdded(SurfaceControl addedSurface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IWindowlessStartingSurfaceCallback.DESCRIPTOR);
                    _data.writeTypedObject(addedSurface, 0);
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
