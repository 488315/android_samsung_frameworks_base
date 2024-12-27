package android.net.ip;

import android.net.Layer2InformationParcelable;
import android.net.NattKeepalivePacketDataParcelable;
import android.net.ProvisioningConfigurationParcelable;
import android.net.ProxyInfo;
import android.net.TcpKeepalivePacketDataParcelable;
import android.net.apf.ApfCapabilities;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IIpClient extends IInterface {
    public static final String DESCRIPTOR = "android$net$ip$IIpClient".replace('$', '.');
    public static final String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int HOSTNAME_SETTING_DO_NOT_SEND = 2;
    public static final int HOSTNAME_SETTING_SEND = 1;
    public static final int HOSTNAME_SETTING_UNSET = 0;
    public static final int PROV_IPV4_DHCP = 2;
    public static final int PROV_IPV4_DISABLED = 0;
    public static final int PROV_IPV4_STATIC = 1;
    public static final int PROV_IPV6_DISABLED = 0;
    public static final int PROV_IPV6_LINKLOCAL = 2;
    public static final int PROV_IPV6_SLAAC = 1;
    public static final int VERSION = 21;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements IIpClient {
        @Override // android.net.ip.IIpClient
        public void addKeepalivePacketFilter(
                int i, TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable)
                throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void addNattKeepalivePacketFilter(
                int i, NattKeepalivePacketDataParcelable nattKeepalivePacketDataParcelable)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.ip.IIpClient
        public void completedPreDhcpAction() throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void confirmConfiguration() throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.ip.IIpClient
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.ip.IIpClient
        public void notifyPreconnectionComplete(boolean z) throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void readPacketFilterComplete(byte[] bArr) throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void removeKeepalivePacketFilter(int i) throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void setHttpProxy(ProxyInfo proxyInfo) throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void setL2KeyAndGroupHint(String str, String str2) throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void setMulticastFilter(boolean z) throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void setTcpBufferSizes(String str) throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void shutdown() throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void startProvisioning(
                ProvisioningConfigurationParcelable provisioningConfigurationParcelable)
                throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void stop() throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void updateApfCapabilities(ApfCapabilities apfCapabilities) throws RemoteException {}

        @Override // android.net.ip.IIpClient
        public void updateLayer2Information(Layer2InformationParcelable layer2InformationParcelable)
                throws RemoteException {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IIpClient {
        static final int TRANSACTION_addKeepalivePacketFilter = 10;
        static final int TRANSACTION_addNattKeepalivePacketFilter = 13;
        static final int TRANSACTION_completedPreDhcpAction = 1;
        static final int TRANSACTION_confirmConfiguration = 2;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_notifyPreconnectionComplete = 14;
        static final int TRANSACTION_readPacketFilterComplete = 3;
        static final int TRANSACTION_removeKeepalivePacketFilter = 11;
        static final int TRANSACTION_setHttpProxy = 8;
        static final int TRANSACTION_setL2KeyAndGroupHint = 12;
        static final int TRANSACTION_setMulticastFilter = 9;
        static final int TRANSACTION_setTcpBufferSizes = 7;
        static final int TRANSACTION_shutdown = 4;
        static final int TRANSACTION_startProvisioning = 5;
        static final int TRANSACTION_stop = 6;
        static final int TRANSACTION_updateApfCapabilities = 16;
        static final int TRANSACTION_updateLayer2Information = 15;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements IIpClient {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.net.ip.IIpClient
            public final void addKeepalivePacketFilter(
                    int i, TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(tcpKeepalivePacketDataParcelable, 0);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method addKeepalivePacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void addNattKeepalivePacketFilter(
                    int i, NattKeepalivePacketDataParcelable nattKeepalivePacketDataParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(nattKeepalivePacketDataParcelable, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method addNattKeepalivePacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.ip.IIpClient
            public final void completedPreDhcpAction() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method completedPreDhcpAction is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void confirmConfiguration() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method confirmConfiguration is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                            this.mRemote.transact(
                                    Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }

            @Override // android.net.ip.IIpClient
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                        this.mRemote.transact(
                                Stub.TRANSACTION_getInterfaceVersion, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.ip.IIpClient
            public final void notifyPreconnectionComplete(boolean z) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method notifyPreconnectionComplete is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void readPacketFilterComplete(byte[] bArr) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method readPacketFilterComplete is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void removeKeepalivePacketFilter(int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method removeKeepalivePacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void setHttpProxy(ProxyInfo proxyInfo) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeTypedObject(proxyInfo, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setHttpProxy is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void setL2KeyAndGroupHint(String str, String str2) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setL2KeyAndGroupHint is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void setMulticastFilter(boolean z) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setMulticastFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void setTcpBufferSizes(String str) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeString(str);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setTcpBufferSizes is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void shutdown() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method shutdown is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void startProvisioning(
                    ProvisioningConfigurationParcelable provisioningConfigurationParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeTypedObject(provisioningConfigurationParcelable, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startProvisioning is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void stop() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stop is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void updateApfCapabilities(ApfCapabilities apfCapabilities) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeTypedObject(apfCapabilities, 0);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateApfCapabilities is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClient
            public final void updateLayer2Information(
                    Layer2InformationParcelable layer2InformationParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClient.DESCRIPTOR);
                    obtain.writeTypedObject(layer2InformationParcelable, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method updateLayer2Information is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IIpClient.DESCRIPTOR);
        }

        public static IIpClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIpClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIpClient)) {
                return (IIpClient) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mCachedVersion = -1;
            proxy.mCachedHash = "-1";
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            String str = IIpClient.DESCRIPTOR;
            if (i >= 1 && i <= TRANSACTION_getInterfaceVersion) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == TRANSACTION_getInterfaceVersion) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    completedPreDhcpAction();
                    return true;
                case 2:
                    confirmConfiguration();
                    return true;
                case 3:
                    readPacketFilterComplete(parcel.createByteArray());
                    return true;
                case 4:
                    shutdown();
                    return true;
                case 5:
                    startProvisioning(
                            (ProvisioningConfigurationParcelable)
                                    parcel.readTypedObject(
                                            ProvisioningConfigurationParcelable.CREATOR));
                    return true;
                case 6:
                    stop();
                    return true;
                case 7:
                    setTcpBufferSizes(parcel.readString());
                    return true;
                case 8:
                    setHttpProxy((ProxyInfo) parcel.readTypedObject(ProxyInfo.CREATOR));
                    return true;
                case 9:
                    setMulticastFilter(parcel.readBoolean());
                    return true;
                case 10:
                    addKeepalivePacketFilter(
                            parcel.readInt(),
                            (TcpKeepalivePacketDataParcelable)
                                    parcel.readTypedObject(
                                            TcpKeepalivePacketDataParcelable.CREATOR));
                    return true;
                case 11:
                    removeKeepalivePacketFilter(parcel.readInt());
                    return true;
                case 12:
                    setL2KeyAndGroupHint(parcel.readString(), parcel.readString());
                    return true;
                case 13:
                    addNattKeepalivePacketFilter(
                            parcel.readInt(),
                            (NattKeepalivePacketDataParcelable)
                                    parcel.readTypedObject(
                                            NattKeepalivePacketDataParcelable.CREATOR));
                    return true;
                case 14:
                    notifyPreconnectionComplete(parcel.readBoolean());
                    return true;
                case 15:
                    updateLayer2Information(
                            (Layer2InformationParcelable)
                                    parcel.readTypedObject(Layer2InformationParcelable.CREATOR));
                    return true;
                case 16:
                    updateApfCapabilities(
                            (ApfCapabilities) parcel.readTypedObject(ApfCapabilities.CREATOR));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void addKeepalivePacketFilter(
            int i, TcpKeepalivePacketDataParcelable tcpKeepalivePacketDataParcelable)
            throws RemoteException;

    void addNattKeepalivePacketFilter(
            int i, NattKeepalivePacketDataParcelable nattKeepalivePacketDataParcelable)
            throws RemoteException;

    void completedPreDhcpAction() throws RemoteException;

    void confirmConfiguration() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void notifyPreconnectionComplete(boolean z) throws RemoteException;

    void readPacketFilterComplete(byte[] bArr) throws RemoteException;

    void removeKeepalivePacketFilter(int i) throws RemoteException;

    void setHttpProxy(ProxyInfo proxyInfo) throws RemoteException;

    void setL2KeyAndGroupHint(String str, String str2) throws RemoteException;

    void setMulticastFilter(boolean z) throws RemoteException;

    void setTcpBufferSizes(String str) throws RemoteException;

    void shutdown() throws RemoteException;

    void startProvisioning(ProvisioningConfigurationParcelable provisioningConfigurationParcelable)
            throws RemoteException;

    void stop() throws RemoteException;

    void updateApfCapabilities(ApfCapabilities apfCapabilities) throws RemoteException;

    void updateLayer2Information(Layer2InformationParcelable layer2InformationParcelable)
            throws RemoteException;
}
