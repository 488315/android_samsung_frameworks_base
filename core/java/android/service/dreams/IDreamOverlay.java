package android.service.dreams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.dreams.IDreamOverlayClientCallback;

/* loaded from: classes3.dex */
public interface IDreamOverlay extends IInterface {
    public static final String DESCRIPTOR = "android.service.dreams.IDreamOverlay";

    void getClient(IDreamOverlayClientCallback iDreamOverlayClientCallback) throws RemoteException;

    public static class Default implements IDreamOverlay {
        @Override // android.service.dreams.IDreamOverlay
        public void getClient(IDreamOverlayClientCallback callback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDreamOverlay {
        static final int TRANSACTION_getClient = 1;

        public Stub() {
            attachInterface(this, IDreamOverlay.DESCRIPTOR);
        }

        public static IDreamOverlay asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDreamOverlay.DESCRIPTOR);
            if (iin != null && (iin instanceof IDreamOverlay)) {
                return (IDreamOverlay) iin;
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
                    return "getClient";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IDreamOverlay.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDreamOverlay.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IDreamOverlayClientCallback _arg0 = IDreamOverlayClientCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getClient(_arg0);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDreamOverlay {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDreamOverlay.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlay
            public void getClient(IDreamOverlayClientCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDreamOverlay.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
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
