package android.net;

import android.net.networkstack.aidl.NetworkMonitorParameters;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface INetworkMonitor extends IInterface {
    public static final String DESCRIPTOR = "android$net$INetworkMonitor".replace('$', '.');
    public static final String HASH = "4d26968d0f6cb11c9bb669a3f8ebc7a1c39f9391";
    public static final int NETWORK_TEST_RESULT_INVALID = 1;
    public static final int NETWORK_TEST_RESULT_PARTIAL_CONNECTIVITY = 2;
    public static final int NETWORK_TEST_RESULT_VALID = 0;
    public static final int NETWORK_VALIDATION_PROBE_DNS = 4;
    public static final int NETWORK_VALIDATION_PROBE_FALLBACK = 32;
    public static final int NETWORK_VALIDATION_PROBE_HTTP = 8;
    public static final int NETWORK_VALIDATION_PROBE_HTTPS = 16;
    public static final int NETWORK_VALIDATION_PROBE_PRIVDNS = 64;
    public static final int NETWORK_VALIDATION_RESULT_PARTIAL = 2;
    public static final int NETWORK_VALIDATION_RESULT_SKIPPED = 4;
    public static final int NETWORK_VALIDATION_RESULT_VALID = 1;
    public static final int VERSION = 18;

    public class Default implements INetworkMonitor {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.INetworkMonitor
        public void forceReevaluation(int i) {
        }

        @Override // android.net.INetworkMonitor
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.INetworkMonitor
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.INetworkMonitor
        public void launchCaptivePortalApp() {
        }

        @Override // android.net.INetworkMonitor
        public void notifyCaptivePortalAppFinished(int i) {
        }

        @Override // android.net.INetworkMonitor
        public void notifyDnsResponse(int i) {
        }

        @Override // android.net.INetworkMonitor
        public void notifyLinkPropertiesChanged(LinkProperties linkProperties) {
        }

        @Override // android.net.INetworkMonitor
        public void notifyNetworkCapabilitiesChanged(NetworkCapabilities networkCapabilities) {
        }

        @Override // android.net.INetworkMonitor
        public void notifyNetworkConnected(LinkProperties linkProperties, NetworkCapabilities networkCapabilities) {
        }

        @Override // android.net.INetworkMonitor
        public void notifyNetworkConnectedParcel(NetworkMonitorParameters networkMonitorParameters) {
        }

        @Override // android.net.INetworkMonitor
        public void notifyNetworkDisconnected() {
        }

        @Override // android.net.INetworkMonitor
        public void notifyPrivateDnsChanged(PrivateDnsConfigParcel privateDnsConfigParcel) {
        }

        @Override // android.net.INetworkMonitor
        public void setAcceptPartialConnectivity() {
        }

        @Override // android.net.INetworkMonitor
        public void start() {
        }
    }

    void forceReevaluation(int i);

    String getInterfaceHash();

    int getInterfaceVersion();

    void launchCaptivePortalApp();

    void notifyCaptivePortalAppFinished(int i);

    void notifyDnsResponse(int i);

    void notifyLinkPropertiesChanged(LinkProperties linkProperties);

    void notifyNetworkCapabilitiesChanged(NetworkCapabilities networkCapabilities);

    void notifyNetworkConnected(LinkProperties linkProperties, NetworkCapabilities networkCapabilities);

    void notifyNetworkConnectedParcel(NetworkMonitorParameters networkMonitorParameters);

    void notifyNetworkDisconnected();

    void notifyPrivateDnsChanged(PrivateDnsConfigParcel privateDnsConfigParcel);

    void setAcceptPartialConnectivity();

    void start();

    public abstract class Stub extends Binder implements INetworkMonitor {
        static final int TRANSACTION_forceReevaluation = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_launchCaptivePortalApp = 2;
        static final int TRANSACTION_notifyCaptivePortalAppFinished = 3;
        static final int TRANSACTION_notifyDnsResponse = 7;
        static final int TRANSACTION_notifyLinkPropertiesChanged = 10;
        static final int TRANSACTION_notifyNetworkCapabilitiesChanged = 11;
        static final int TRANSACTION_notifyNetworkConnected = 8;
        static final int TRANSACTION_notifyNetworkConnectedParcel = 12;
        static final int TRANSACTION_notifyNetworkDisconnected = 9;
        static final int TRANSACTION_notifyPrivateDnsChanged = 6;
        static final int TRANSACTION_setAcceptPartialConnectivity = 4;
        static final int TRANSACTION_start = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, INetworkMonitor.DESCRIPTOR);
        }

        public static INetworkMonitor asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(INetworkMonitor.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INetworkMonitor)) {
                return (INetworkMonitor) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = INetworkMonitor.DESCRIPTOR;
            if (i >= 1 && i <= TRANSACTION_getInterfaceVersion) {
                parcel.enforceInterface(str);
            }
            switch (i) {
                case TRANSACTION_getInterfaceHash /* 16777214 */:
                    parcel2.writeNoException();
                    parcel2.writeString(getInterfaceHash());
                    return true;
                case TRANSACTION_getInterfaceVersion /* 16777215 */:
                    parcel2.writeNoException();
                    parcel2.writeInt(getInterfaceVersion());
                    return true;
                case 1598968902:
                    parcel2.writeString(str);
                    return true;
                default:
                    switch (i) {
                        case 1:
                            start();
                            return true;
                        case 2:
                            launchCaptivePortalApp();
                            return true;
                        case 3:
                            notifyCaptivePortalAppFinished(parcel.readInt());
                            return true;
                        case 4:
                            setAcceptPartialConnectivity();
                            return true;
                        case 5:
                            forceReevaluation(parcel.readInt());
                            return true;
                        case 6:
                            notifyPrivateDnsChanged((PrivateDnsConfigParcel) parcel.readTypedObject(PrivateDnsConfigParcel.CREATOR));
                            return true;
                        case 7:
                            notifyDnsResponse(parcel.readInt());
                            return true;
                        case 8:
                            notifyNetworkConnected((LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR), (NetworkCapabilities) parcel.readTypedObject(NetworkCapabilities.CREATOR));
                            return true;
                        case 9:
                            notifyNetworkDisconnected();
                            return true;
                        case 10:
                            notifyLinkPropertiesChanged((LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR));
                            return true;
                        case 11:
                            notifyNetworkCapabilitiesChanged((NetworkCapabilities) parcel.readTypedObject(NetworkCapabilities.CREATOR));
                            return true;
                        case 12:
                            notifyNetworkConnectedParcel((NetworkMonitorParameters) parcel.readTypedObject(NetworkMonitorParameters.CREATOR));
                            return true;
                        default:
                            return super.onTransact(i, parcel, parcel2, i2);
                    }
            }
        }

        public class Proxy implements INetworkMonitor {
            public IBinder mRemote;
            public int mCachedVersion = -1;
            public String mCachedHash = "-1";

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.INetworkMonitor
            public void start() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method start is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void launchCaptivePortalApp() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method launchCaptivePortalApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyCaptivePortalAppFinished(int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyCaptivePortalAppFinished is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void setAcceptPartialConnectivity() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setAcceptPartialConnectivity is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void forceReevaluation(int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method forceReevaluation is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyPrivateDnsChanged(PrivateDnsConfigParcel privateDnsConfigParcel) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(privateDnsConfigParcel, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyPrivateDnsChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyDnsResponse(int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyDnsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyNetworkConnected(LinkProperties linkProperties, NetworkCapabilities networkCapabilities) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    obtain.writeTypedObject(networkCapabilities, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyNetworkConnected is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyNetworkDisconnected() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyNetworkDisconnected is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyLinkPropertiesChanged(LinkProperties linkProperties) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyLinkPropertiesChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyNetworkCapabilitiesChanged(NetworkCapabilities networkCapabilities) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(networkCapabilities, 0);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyNetworkCapabilitiesChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public void notifyNetworkConnectedParcel(NetworkMonitorParameters networkMonitorParameters) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                    obtain.writeTypedObject(networkMonitorParameters, 0);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method notifyNetworkConnectedParcel is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkMonitor
            public int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceVersion, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.INetworkMonitor
            public synchronized String getInterfaceHash() {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(INetworkMonitor.DESCRIPTOR);
                        this.mRemote.transact(Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
