package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IScreenCaptureObserver extends IInterface {
    public static final String DESCRIPTOR = "android.app.IScreenCaptureObserver";

    void onScreenCaptured() throws RemoteException;

    public static class Default implements IScreenCaptureObserver {
        @Override // android.app.IScreenCaptureObserver
        public void onScreenCaptured() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IScreenCaptureObserver {
        static final int TRANSACTION_onScreenCaptured = 1;

        public Stub() {
            attachInterface(this, IScreenCaptureObserver.DESCRIPTOR);
        }

        public static IScreenCaptureObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IScreenCaptureObserver.DESCRIPTOR);
            if (iin != null && (iin instanceof IScreenCaptureObserver)) {
                return (IScreenCaptureObserver) iin;
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
                    return "onScreenCaptured";
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
                data.enforceInterface(IScreenCaptureObserver.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IScreenCaptureObserver.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onScreenCaptured();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IScreenCaptureObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IScreenCaptureObserver.DESCRIPTOR;
            }

            @Override // android.app.IScreenCaptureObserver
            public void onScreenCaptured() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IScreenCaptureObserver.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
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
