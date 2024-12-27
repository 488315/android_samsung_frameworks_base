package android.net.wifi.nl80211;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IInterfaceEventCallback extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IInterfaceEventCallback";

    void OnApInterfaceReady(IApInterface iApInterface) throws RemoteException;

    void OnApTorndownEvent(IApInterface iApInterface) throws RemoteException;

    void OnClientInterfaceReady(IClientInterface iClientInterface) throws RemoteException;

    void OnClientTorndownEvent(IClientInterface iClientInterface) throws RemoteException;

    public static class Default implements IInterfaceEventCallback {
        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnClientInterfaceReady(IClientInterface network_interface)
                throws RemoteException {}

        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnApInterfaceReady(IApInterface network_interface) throws RemoteException {}

        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnClientTorndownEvent(IClientInterface network_interface)
                throws RemoteException {}

        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnApTorndownEvent(IApInterface network_interface) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IInterfaceEventCallback {
        static final int TRANSACTION_OnApInterfaceReady = 2;
        static final int TRANSACTION_OnApTorndownEvent = 4;
        static final int TRANSACTION_OnClientInterfaceReady = 1;
        static final int TRANSACTION_OnClientTorndownEvent = 3;

        public Stub() {
            attachInterface(this, IInterfaceEventCallback.DESCRIPTOR);
        }

        public static IInterfaceEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInterfaceEventCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IInterfaceEventCallback)) {
                return (IInterfaceEventCallback) iin;
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
                    return "OnClientInterfaceReady";
                case 2:
                    return "OnApInterfaceReady";
                case 3:
                    return "OnClientTorndownEvent";
                case 4:
                    return "OnApTorndownEvent";
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
                data.enforceInterface(IInterfaceEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInterfaceEventCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IClientInterface _arg0 =
                            IClientInterface.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    OnClientInterfaceReady(_arg0);
                    return true;
                case 2:
                    IApInterface _arg02 = IApInterface.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    OnApInterfaceReady(_arg02);
                    return true;
                case 3:
                    IClientInterface _arg03 =
                            IClientInterface.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    OnClientTorndownEvent(_arg03);
                    return true;
                case 4:
                    IApInterface _arg04 = IApInterface.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    OnApTorndownEvent(_arg04);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInterfaceEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInterfaceEventCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnClientInterfaceReady(IClientInterface network_interface)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInterfaceEventCallback.DESCRIPTOR);
                    _data.writeStrongInterface(network_interface);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnApInterfaceReady(IApInterface network_interface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInterfaceEventCallback.DESCRIPTOR);
                    _data.writeStrongInterface(network_interface);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnClientTorndownEvent(IClientInterface network_interface)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInterfaceEventCallback.DESCRIPTOR);
                    _data.writeStrongInterface(network_interface);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnApTorndownEvent(IApInterface network_interface) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInterfaceEventCallback.DESCRIPTOR);
                    _data.writeStrongInterface(network_interface);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
