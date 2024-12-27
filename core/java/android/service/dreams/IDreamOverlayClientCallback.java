package android.service.dreams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDreamOverlayClientCallback extends IInterface {
    public static final String DESCRIPTOR = "android.service.dreams.IDreamOverlayClientCallback";

    void onDreamOverlayClient(IDreamOverlayClient iDreamOverlayClient) throws RemoteException;

    public static class Default implements IDreamOverlayClientCallback {
        @Override // android.service.dreams.IDreamOverlayClientCallback
        public void onDreamOverlayClient(IDreamOverlayClient client) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDreamOverlayClientCallback {
        static final int TRANSACTION_onDreamOverlayClient = 1;

        public Stub() {
            attachInterface(this, IDreamOverlayClientCallback.DESCRIPTOR);
        }

        public static IDreamOverlayClientCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDreamOverlayClientCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IDreamOverlayClientCallback)) {
                return (IDreamOverlayClientCallback) iin;
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
                    return "onDreamOverlayClient";
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
                data.enforceInterface(IDreamOverlayClientCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDreamOverlayClientCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IDreamOverlayClient _arg0 =
                            IDreamOverlayClient.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    onDreamOverlayClient(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDreamOverlayClientCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDreamOverlayClientCallback.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlayClientCallback
            public void onDreamOverlayClient(IDreamOverlayClient client) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDreamOverlayClientCallback.DESCRIPTOR);
                    _data.writeStrongInterface(client);
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
