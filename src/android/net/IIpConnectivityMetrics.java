package android.net;

import android.net.INetdEventCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IIpConnectivityMetrics extends IInterface {

    public static class Default implements IIpConnectivityMetrics {
        @Override // android.net.IIpConnectivityMetrics
        public boolean addNetdEventCallback(int i, INetdEventCallback iNetdEventCallback) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.IIpConnectivityMetrics
        public void logDefaultNetworkEvent(Network network, int i, boolean z, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, Network network2, int i2, LinkProperties linkProperties2, NetworkCapabilities networkCapabilities2) throws RemoteException {
        }

        @Override // android.net.IIpConnectivityMetrics
        public void logDefaultNetworkValidity(boolean z) throws RemoteException {
        }

        @Override // android.net.IIpConnectivityMetrics
        public int logEvent(ConnectivityMetricsEvent connectivityMetricsEvent) throws RemoteException {
            return 0;
        }

        @Override // android.net.IIpConnectivityMetrics
        public boolean removeNetdEventCallback(int i) throws RemoteException {
            return false;
        }
    }

    boolean addNetdEventCallback(int i, INetdEventCallback iNetdEventCallback) throws RemoteException;

    void logDefaultNetworkEvent(Network network, int i, boolean z, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, Network network2, int i2, LinkProperties linkProperties2, NetworkCapabilities networkCapabilities2) throws RemoteException;

    void logDefaultNetworkValidity(boolean z) throws RemoteException;

    int logEvent(ConnectivityMetricsEvent connectivityMetricsEvent) throws RemoteException;

    boolean removeNetdEventCallback(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IIpConnectivityMetrics {
        public static final String DESCRIPTOR = "android.net.IIpConnectivityMetrics";
        static final int TRANSACTION_addNetdEventCallback = 4;
        static final int TRANSACTION_logDefaultNetworkEvent = 3;
        static final int TRANSACTION_logDefaultNetworkValidity = 2;
        static final int TRANSACTION_logEvent = 1;
        static final int TRANSACTION_removeNetdEventCallback = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IIpConnectivityMetrics asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIpConnectivityMetrics)) {
                return (IIpConnectivityMetrics) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "logEvent";
            }
            if (i == 2) {
                return "logDefaultNetworkValidity";
            }
            if (i == 3) {
                return "logDefaultNetworkEvent";
            }
            if (i == 4) {
                return "addNetdEventCallback";
            }
            if (i != 5) {
                return null;
            }
            return "removeNetdEventCallback";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ConnectivityMetricsEvent connectivityMetricsEvent = (ConnectivityMetricsEvent) parcel.readTypedObject(ConnectivityMetricsEvent.CREATOR);
                parcel.enforceNoDataAvail();
                int iLogEvent = logEvent(connectivityMetricsEvent);
                parcel2.writeNoException();
                parcel2.writeInt(iLogEvent);
            } else if (i == 2) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                logDefaultNetworkValidity(z);
                parcel2.writeNoException();
            } else if (i == 3) {
                Network network = (Network) parcel.readTypedObject(Network.CREATOR);
                int i3 = parcel.readInt();
                boolean z2 = parcel.readBoolean();
                LinkProperties linkProperties = (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR);
                NetworkCapabilities networkCapabilities = (NetworkCapabilities) parcel.readTypedObject(NetworkCapabilities.CREATOR);
                Network network2 = (Network) parcel.readTypedObject(Network.CREATOR);
                int i4 = parcel.readInt();
                LinkProperties linkProperties2 = (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR);
                NetworkCapabilities networkCapabilities2 = (NetworkCapabilities) parcel.readTypedObject(NetworkCapabilities.CREATOR);
                parcel.enforceNoDataAvail();
                logDefaultNetworkEvent(network, i3, z2, linkProperties, networkCapabilities, network2, i4, linkProperties2, networkCapabilities2);
                parcel2.writeNoException();
            } else if (i == 4) {
                int i5 = parcel.readInt();
                INetdEventCallback iNetdEventCallbackAsInterface = INetdEventCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                boolean zAddNetdEventCallback = addNetdEventCallback(i5, iNetdEventCallbackAsInterface);
                parcel2.writeNoException();
                parcel2.writeBoolean(zAddNetdEventCallback);
            } else if (i == 5) {
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zRemoveNetdEventCallback = removeNetdEventCallback(i6);
                parcel2.writeNoException();
                parcel2.writeBoolean(zRemoveNetdEventCallback);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIpConnectivityMetrics {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.net.IIpConnectivityMetrics
            public int logEvent(ConnectivityMetricsEvent connectivityMetricsEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(connectivityMetricsEvent, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public void logDefaultNetworkValidity(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public void logDefaultNetworkEvent(Network network, int i, boolean z, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, Network network2, int i2, LinkProperties linkProperties2, NetworkCapabilities networkCapabilities2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(network, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(linkProperties, 0);
                    parcelObtain.writeTypedObject(networkCapabilities, 0);
                    parcelObtain.writeTypedObject(network2, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(linkProperties2, 0);
                    parcelObtain.writeTypedObject(networkCapabilities2, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public boolean addNetdEventCallback(int i, INetdEventCallback iNetdEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iNetdEventCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public boolean removeNetdEventCallback(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
