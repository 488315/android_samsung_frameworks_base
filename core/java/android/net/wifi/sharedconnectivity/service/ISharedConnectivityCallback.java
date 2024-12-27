package android.net.wifi.sharedconnectivity.service;

import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;
import android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

public interface ISharedConnectivityCallback extends IInterface {
    public static final String DESCRIPTOR =
            "android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback";

    void onHotspotNetworkConnectionStatusChanged(
            HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) throws RemoteException;

    void onHotspotNetworksUpdated(List<HotspotNetwork> list) throws RemoteException;

    void onKnownNetworkConnectionStatusChanged(
            KnownNetworkConnectionStatus knownNetworkConnectionStatus) throws RemoteException;

    void onKnownNetworksUpdated(List<KnownNetwork> list) throws RemoteException;

    void onServiceConnected() throws RemoteException;

    void onSharedConnectivitySettingsChanged(
            SharedConnectivitySettingsState sharedConnectivitySettingsState) throws RemoteException;

    public static class Default implements ISharedConnectivityCallback {
        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworksUpdated(List<HotspotNetwork> networks)
                throws RemoteException {}

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworkConnectionStatusChanged(HotspotNetworkConnectionStatus status)
                throws RemoteException {}

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworksUpdated(List<KnownNetwork> networks) throws RemoteException {}

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworkConnectionStatusChanged(KnownNetworkConnectionStatus status)
                throws RemoteException {}

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onSharedConnectivitySettingsChanged(SharedConnectivitySettingsState state)
                throws RemoteException {}

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onServiceConnected() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISharedConnectivityCallback {
        static final int TRANSACTION_onHotspotNetworkConnectionStatusChanged = 2;
        static final int TRANSACTION_onHotspotNetworksUpdated = 1;
        static final int TRANSACTION_onKnownNetworkConnectionStatusChanged = 4;
        static final int TRANSACTION_onKnownNetworksUpdated = 3;
        static final int TRANSACTION_onServiceConnected = 6;
        static final int TRANSACTION_onSharedConnectivitySettingsChanged = 5;

        public Stub() {
            attachInterface(this, ISharedConnectivityCallback.DESCRIPTOR);
        }

        public static ISharedConnectivityCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISharedConnectivityCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ISharedConnectivityCallback)) {
                return (ISharedConnectivityCallback) iin;
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
                    return "onHotspotNetworksUpdated";
                case 2:
                    return "onHotspotNetworkConnectionStatusChanged";
                case 3:
                    return "onKnownNetworksUpdated";
                case 4:
                    return "onKnownNetworkConnectionStatusChanged";
                case 5:
                    return "onSharedConnectivitySettingsChanged";
                case 6:
                    return "onServiceConnected";
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
                data.enforceInterface(ISharedConnectivityCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISharedConnectivityCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    List<HotspotNetwork> _arg0 = data.createTypedArrayList(HotspotNetwork.CREATOR);
                    data.enforceNoDataAvail();
                    onHotspotNetworksUpdated(_arg0);
                    return true;
                case 2:
                    HotspotNetworkConnectionStatus _arg02 =
                            (HotspotNetworkConnectionStatus)
                                    data.readTypedObject(HotspotNetworkConnectionStatus.CREATOR);
                    data.enforceNoDataAvail();
                    onHotspotNetworkConnectionStatusChanged(_arg02);
                    return true;
                case 3:
                    List<KnownNetwork> _arg03 = data.createTypedArrayList(KnownNetwork.CREATOR);
                    data.enforceNoDataAvail();
                    onKnownNetworksUpdated(_arg03);
                    return true;
                case 4:
                    KnownNetworkConnectionStatus _arg04 =
                            (KnownNetworkConnectionStatus)
                                    data.readTypedObject(KnownNetworkConnectionStatus.CREATOR);
                    data.enforceNoDataAvail();
                    onKnownNetworkConnectionStatusChanged(_arg04);
                    return true;
                case 5:
                    SharedConnectivitySettingsState _arg05 =
                            (SharedConnectivitySettingsState)
                                    data.readTypedObject(SharedConnectivitySettingsState.CREATOR);
                    data.enforceNoDataAvail();
                    onSharedConnectivitySettingsChanged(_arg05);
                    return true;
                case 6:
                    onServiceConnected();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISharedConnectivityCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISharedConnectivityCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onHotspotNetworksUpdated(List<HotspotNetwork> networks)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    _data.writeTypedList(networks, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onHotspotNetworkConnectionStatusChanged(
                    HotspotNetworkConnectionStatus status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    _data.writeTypedObject(status, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onKnownNetworksUpdated(List<KnownNetwork> networks) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    _data.writeTypedList(networks, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onKnownNetworkConnectionStatusChanged(KnownNetworkConnectionStatus status)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    _data.writeTypedObject(status, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onSharedConnectivitySettingsChanged(SharedConnectivitySettingsState state)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    _data.writeTypedObject(state, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onServiceConnected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
