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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIpConnectivityMetrics)) {
                return (IIpConnectivityMetrics) queryLocalInterface;
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
                int logEvent = logEvent(connectivityMetricsEvent);
                parcel2.writeNoException();
                parcel2.writeInt(logEvent);
            } else if (i == 2) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                logDefaultNetworkValidity(readBoolean);
                parcel2.writeNoException();
            } else if (i == 3) {
                Network network = (Network) parcel.readTypedObject(Network.CREATOR);
                int readInt = parcel.readInt();
                boolean readBoolean2 = parcel.readBoolean();
                LinkProperties linkProperties = (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR);
                NetworkCapabilities networkCapabilities = (NetworkCapabilities) parcel.readTypedObject(NetworkCapabilities.CREATOR);
                Network network2 = (Network) parcel.readTypedObject(Network.CREATOR);
                int readInt2 = parcel.readInt();
                LinkProperties linkProperties2 = (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR);
                NetworkCapabilities networkCapabilities2 = (NetworkCapabilities) parcel.readTypedObject(NetworkCapabilities.CREATOR);
                parcel.enforceNoDataAvail();
                logDefaultNetworkEvent(network, readInt, readBoolean2, linkProperties, networkCapabilities, network2, readInt2, linkProperties2, networkCapabilities2);
                parcel2.writeNoException();
            } else if (i == 4) {
                int readInt3 = parcel.readInt();
                INetdEventCallback asInterface = INetdEventCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                boolean addNetdEventCallback = addNetdEventCallback(readInt3, asInterface);
                parcel2.writeNoException();
                parcel2.writeBoolean(addNetdEventCallback);
            } else if (i == 5) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean removeNetdEventCallback = removeNetdEventCallback(readInt4);
                parcel2.writeNoException();
                parcel2.writeBoolean(removeNetdEventCallback);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(connectivityMetricsEvent, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public void logDefaultNetworkValidity(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public void logDefaultNetworkEvent(Network network, int i, boolean z, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, Network network2, int i2, LinkProperties linkProperties2, NetworkCapabilities networkCapabilities2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(network, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(linkProperties, 0);
                    obtain.writeTypedObject(networkCapabilities, 0);
                    obtain.writeTypedObject(network2, 0);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(linkProperties2, 0);
                    obtain.writeTypedObject(networkCapabilities2, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public boolean addNetdEventCallback(int i, INetdEventCallback iNetdEventCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iNetdEventCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IIpConnectivityMetrics
            public boolean removeNetdEventCallback(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
