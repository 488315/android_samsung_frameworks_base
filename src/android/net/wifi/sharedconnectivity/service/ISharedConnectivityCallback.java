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
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface ISharedConnectivityCallback extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback";

    public static class Default implements ISharedConnectivityCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworkConnectionStatusChanged(HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onHotspotNetworksUpdated(List<HotspotNetwork> list) throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworkConnectionStatusChanged(KnownNetworkConnectionStatus knownNetworkConnectionStatus) throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onKnownNetworksUpdated(List<KnownNetwork> list) throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onServiceConnected() throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onServiceDisconnected() throws RemoteException {
        }

        @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
        public void onSharedConnectivitySettingsChanged(SharedConnectivitySettingsState sharedConnectivitySettingsState) throws RemoteException {
        }
    }

    void onHotspotNetworkConnectionStatusChanged(HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) throws RemoteException;

    void onHotspotNetworksUpdated(List<HotspotNetwork> list) throws RemoteException;

    void onKnownNetworkConnectionStatusChanged(KnownNetworkConnectionStatus knownNetworkConnectionStatus) throws RemoteException;

    void onKnownNetworksUpdated(List<KnownNetwork> list) throws RemoteException;

    void onServiceConnected() throws RemoteException;

    void onServiceDisconnected() throws RemoteException;

    void onSharedConnectivitySettingsChanged(SharedConnectivitySettingsState sharedConnectivitySettingsState) throws RemoteException;

    public static abstract class Stub extends Binder implements ISharedConnectivityCallback {
        static final int TRANSACTION_onHotspotNetworkConnectionStatusChanged = 2;
        static final int TRANSACTION_onHotspotNetworksUpdated = 1;
        static final int TRANSACTION_onKnownNetworkConnectionStatusChanged = 4;
        static final int TRANSACTION_onKnownNetworksUpdated = 3;
        static final int TRANSACTION_onServiceConnected = 6;
        static final int TRANSACTION_onServiceDisconnected = 7;
        static final int TRANSACTION_onSharedConnectivitySettingsChanged = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, ISharedConnectivityCallback.DESCRIPTOR);
        }

        public static ISharedConnectivityCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISharedConnectivityCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISharedConnectivityCallback)) {
                return (ISharedConnectivityCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                case 7:
                    return "onServiceDisconnected";
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
                parcel.enforceInterface(ISharedConnectivityCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISharedConnectivityCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(HotspotNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHotspotNetworksUpdated(arrayListCreateTypedArrayList);
                    return true;
                case 2:
                    HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus = (HotspotNetworkConnectionStatus) parcel.readTypedObject(HotspotNetworkConnectionStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onHotspotNetworkConnectionStatusChanged(hotspotNetworkConnectionStatus);
                    return true;
                case 3:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(KnownNetwork.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKnownNetworksUpdated(arrayListCreateTypedArrayList2);
                    return true;
                case 4:
                    KnownNetworkConnectionStatus knownNetworkConnectionStatus = (KnownNetworkConnectionStatus) parcel.readTypedObject(KnownNetworkConnectionStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    onKnownNetworkConnectionStatusChanged(knownNetworkConnectionStatus);
                    return true;
                case 5:
                    SharedConnectivitySettingsState sharedConnectivitySettingsState = (SharedConnectivitySettingsState) parcel.readTypedObject(SharedConnectivitySettingsState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSharedConnectivitySettingsChanged(sharedConnectivitySettingsState);
                    return true;
                case 6:
                    onServiceConnected();
                    return true;
                case 7:
                    onServiceDisconnected();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISharedConnectivityCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISharedConnectivityCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onHotspotNetworksUpdated(List<HotspotNetwork> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onHotspotNetworkConnectionStatusChanged(HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(hotspotNetworkConnectionStatus, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onKnownNetworksUpdated(List<KnownNetwork> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onKnownNetworkConnectionStatusChanged(KnownNetworkConnectionStatus knownNetworkConnectionStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(knownNetworkConnectionStatus, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onSharedConnectivitySettingsChanged(SharedConnectivitySettingsState sharedConnectivitySettingsState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(sharedConnectivitySettingsState, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onServiceConnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.sharedconnectivity.service.ISharedConnectivityCallback
            public void onServiceDisconnected() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISharedConnectivityCallback.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
