package android.net.wifi.nl80211;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWificondEventCallback extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IWificondEventCallback";

    void OnRegDomainChanged(String str) throws RemoteException;

    public static class Default implements IWificondEventCallback {
        @Override // android.net.wifi.nl80211.IWificondEventCallback
        public void OnRegDomainChanged(String countryCode) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWificondEventCallback {
        static final int TRANSACTION_OnRegDomainChanged = 1;

        public Stub() {
            attachInterface(this, IWificondEventCallback.DESCRIPTOR);
        }

        public static IWificondEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IWificondEventCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IWificondEventCallback)) {
                return (IWificondEventCallback) iin;
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
                    return "OnRegDomainChanged";
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
                data.enforceInterface(IWificondEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IWificondEventCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    OnRegDomainChanged(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWificondEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWificondEventCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IWificondEventCallback
            public void OnRegDomainChanged(String countryCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IWificondEventCallback.DESCRIPTOR);
                    _data.writeString(countryCode);
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
