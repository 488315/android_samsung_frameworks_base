package android.service.dreams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDreamOverlayCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.dreams.IDreamOverlayCallback";

    void onExitRequested() throws RemoteException;

    void onRedirectWake(boolean z) throws RemoteException;

    public static class Default implements IDreamOverlayCallback {
        @Override // android.service.dreams.IDreamOverlayCallback
        public void onExitRequested() throws RemoteException {}

        @Override // android.service.dreams.IDreamOverlayCallback
        public void onRedirectWake(boolean redirect) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDreamOverlayCallback {
        static final int TRANSACTION_onExitRequested = 1;
        static final int TRANSACTION_onRedirectWake = 2;

        public Stub() {
            attachInterface(this, IDreamOverlayCallback.DESCRIPTOR);
        }

        public static IDreamOverlayCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDreamOverlayCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IDreamOverlayCallback)) {
                return (IDreamOverlayCallback) iin;
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
                    return "onExitRequested";
                case 2:
                    return "onRedirectWake";
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
                data.enforceInterface(IDreamOverlayCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDreamOverlayCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    onExitRequested();
                    return true;
                case 2:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onRedirectWake(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDreamOverlayCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDreamOverlayCallback.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlayCallback
            public void onExitRequested() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDreamOverlayCallback.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.service.dreams.IDreamOverlayCallback
            public void onRedirectWake(boolean redirect) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IDreamOverlayCallback.DESCRIPTOR);
                    _data.writeBoolean(redirect);
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
