package android.net.wifi.sharedconnectivity.service;

import android.net.wifi.sharedconnectivity.app.HotspotNetwork;
import android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.KnownNetwork;
import android.net.wifi.sharedconnectivity.app.KnownNetworkConnectionStatus;
import android.net.wifi.sharedconnectivity.app.SharedConnectivitySettingsState;
import android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes3.dex */
public interface ISharedConnectivityService extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.sharedconnectivity.service.ISharedConnectivityService";

    public static class Default implements ISharedConnectivityService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void connectHotspotNetwork(HotspotNetwork hotspotNetwork) throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void connectKnownNetwork(KnownNetwork knownNetwork) throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void disconnectHotspotNetwork(HotspotNetwork hotspotNetwork) throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void forgetKnownNetwork(KnownNetwork knownNetwork) throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public List<HotspotNetwork> getHotspotNetworks() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public List<KnownNetwork> getKnownNetworks() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public SharedConnectivitySettingsState getSettingsState() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void registerCallback(ISharedConnectivityCallback iSharedConnectivityCallback) throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
        public void unregisterCallback(ISharedConnectivityCallback iSharedConnectivityCallback) throws RemoteException {
        }
    }

    void connectHotspotNetwork(HotspotNetwork hotspotNetwork) throws RemoteException;

    void connectKnownNetwork(KnownNetwork knownNetwork) throws RemoteException;

    void disconnectHotspotNetwork(HotspotNetwork hotspotNetwork) throws RemoteException;

    void forgetKnownNetwork(KnownNetwork knownNetwork) throws RemoteException;

    HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() throws RemoteException;

    List<HotspotNetwork> getHotspotNetworks() throws RemoteException;

    KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() throws RemoteException;

    List<KnownNetwork> getKnownNetworks() throws RemoteException;

    SharedConnectivitySettingsState getSettingsState() throws RemoteException;

    void registerCallback(ISharedConnectivityCallback iSharedConnectivityCallback) throws RemoteException;

    void unregisterCallback(ISharedConnectivityCallback iSharedConnectivityCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISharedConnectivityService {
        static final int TRANSACTION_connectHotspotNetwork = 3;
        static final int TRANSACTION_connectKnownNetwork = 5;
        static final int TRANSACTION_disconnectHotspotNetwork = 4;
        static final int TRANSACTION_forgetKnownNetwork = 6;
        static final int TRANSACTION_getHotspotNetworkConnectionStatus = 10;
        static final int TRANSACTION_getHotspotNetworks = 7;
        static final int TRANSACTION_getKnownNetworkConnectionStatus = 11;
        static final int TRANSACTION_getKnownNetworks = 8;
        static final int TRANSACTION_getSettingsState = 9;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_unregisterCallback = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }

        public Stub() {
            attachInterface(this, ISharedConnectivityService.DESCRIPTOR);
        }

        public static ISharedConnectivityService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISharedConnectivityService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISharedConnectivityService)) {
                return (ISharedConnectivityService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerCallback";
                case 2:
                    return "unregisterCallback";
                case 3:
                    return "connectHotspotNetwork";
                case 4:
                    return "disconnectHotspotNetwork";
                case 5:
                    return "connectKnownNetwork";
                case 6:
                    return "forgetKnownNetwork";
                case 7:
                    return "getHotspotNetworks";
                case 8:
                    return "getKnownNetworks";
                case 9:
                    return "getSettingsState";
                case 10:
                    return "getHotspotNetworkConnectionStatus";
                case 11:
                    return "getKnownNetworkConnectionStatus";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISharedConnectivityService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISharedConnectivityService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ISharedConnectivityCallback asInterface = ISharedConnectivityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ISharedConnectivityCallback asInterface2 = ISharedConnectivityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    HotspotNetwork hotspotNetwork = (HotspotNetwork) parcel.readTypedObject(HotspotNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    connectHotspotNetwork(hotspotNetwork);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    HotspotNetwork hotspotNetwork2 = (HotspotNetwork) parcel.readTypedObject(HotspotNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    disconnectHotspotNetwork(hotspotNetwork2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    KnownNetwork knownNetwork = (KnownNetwork) parcel.readTypedObject(KnownNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    connectKnownNetwork(knownNetwork);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    KnownNetwork knownNetwork2 = (KnownNetwork) parcel.readTypedObject(KnownNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    forgetKnownNetwork(knownNetwork2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    List<HotspotNetwork> hotspotNetworks = getHotspotNetworks();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(hotspotNetworks, 1);
                    return true;
                case 8:
                    List<KnownNetwork> knownNetworks = getKnownNetworks();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(knownNetworks, 1);
                    return true;
                case 9:
                    SharedConnectivitySettingsState settingsState = getSettingsState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(settingsState, 1);
                    return true;
                case 10:
                    HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus = getHotspotNetworkConnectionStatus();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(hotspotNetworkConnectionStatus, 1);
                    return true;
                case 11:
                    KnownNetworkConnectionStatus knownNetworkConnectionStatus = getKnownNetworkConnectionStatus();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(knownNetworkConnectionStatus, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISharedConnectivityService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISharedConnectivityService.DESCRIPTOR;
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void registerCallback(ISharedConnectivityCallback iSharedConnectivityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSharedConnectivityCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void unregisterCallback(ISharedConnectivityCallback iSharedConnectivityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSharedConnectivityCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void connectHotspotNetwork(HotspotNetwork hotspotNetwork) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeTypedObject(hotspotNetwork, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void disconnectHotspotNetwork(HotspotNetwork hotspotNetwork) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeTypedObject(hotspotNetwork, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void connectKnownNetwork(KnownNetwork knownNetwork) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeTypedObject(knownNetwork, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public void forgetKnownNetwork(KnownNetwork knownNetwork) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    obtain.writeTypedObject(knownNetwork, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public List<HotspotNetwork> getHotspotNetworks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(HotspotNetwork.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public List<KnownNetwork> getKnownNetworks() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(KnownNetwork.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public SharedConnectivitySettingsState getSettingsState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SharedConnectivitySettingsState) obtain2.readTypedObject(SharedConnectivitySettingsState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public HotspotNetworkConnectionStatus getHotspotNetworkConnectionStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (HotspotNetworkConnectionStatus) obtain2.readTypedObject(HotspotNetworkConnectionStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityService
            public KnownNetworkConnectionStatus getKnownNetworkConnectionStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISharedConnectivityService.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (KnownNetworkConnectionStatus) obtain2.readTypedObject(KnownNetworkConnectionStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
