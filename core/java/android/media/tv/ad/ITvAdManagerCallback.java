package android.media.tv.ad;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ITvAdManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "android.media.tv.ad.ITvAdManagerCallback";

    void onAdServiceAdded(String str) throws RemoteException;

    void onAdServiceRemoved(String str) throws RemoteException;

    void onAdServiceUpdated(String str) throws RemoteException;

    public static class Default implements ITvAdManagerCallback {
        @Override // android.media.tv.ad.ITvAdManagerCallback
        public void onAdServiceAdded(String serviceId) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManagerCallback
        public void onAdServiceRemoved(String serviceId) throws RemoteException {}

        @Override // android.media.tv.ad.ITvAdManagerCallback
        public void onAdServiceUpdated(String serviceId) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ITvAdManagerCallback {
        static final int TRANSACTION_onAdServiceAdded = 1;
        static final int TRANSACTION_onAdServiceRemoved = 2;
        static final int TRANSACTION_onAdServiceUpdated = 3;

        public Stub() {
            attachInterface(this, ITvAdManagerCallback.DESCRIPTOR);
        }

        public static ITvAdManagerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ITvAdManagerCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ITvAdManagerCallback)) {
                return (ITvAdManagerCallback) iin;
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
                    return "onAdServiceAdded";
                case 2:
                    return "onAdServiceRemoved";
                case 3:
                    return "onAdServiceUpdated";
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
                data.enforceInterface(ITvAdManagerCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ITvAdManagerCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    onAdServiceAdded(_arg0);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    onAdServiceRemoved(_arg02);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    onAdServiceUpdated(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ITvAdManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITvAdManagerCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceAdded(String serviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdManagerCallback.DESCRIPTOR);
                    _data.writeString(serviceId);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceRemoved(String serviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdManagerCallback.DESCRIPTOR);
                    _data.writeString(serviceId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.tv.ad.ITvAdManagerCallback
            public void onAdServiceUpdated(String serviceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ITvAdManagerCallback.DESCRIPTOR);
                    _data.writeString(serviceId);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
