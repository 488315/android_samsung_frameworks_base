package android.net.ip;

import android.net.DhcpResultsParcelable;
import android.net.Layer2PacketParcelable;
import android.net.LinkProperties;
import android.net.networkstack.aidl.ip.ReachabilityLossInfoParcelable;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface IIpClientCallbacks extends IInterface {
    public static final String DESCRIPTOR = "android$net$ip$IIpClientCallbacks".replace('$', '.');
    public static final int DTIM_MULTIPLIER_RESET = 0;
    public static final String HASH = "9bd9d687ddb816baf1faabcad0d56ac15b22c56e";
    public static final int VERSION = 21;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements IIpClientCallbacks {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.ip.IIpClientCallbacks
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.ip.IIpClientCallbacks
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.ip.IIpClientCallbacks
        public void installPacketFilter(byte[] bArr) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onIpClientCreated(IIpClient iIpClient) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onLinkPropertiesChange(LinkProperties linkProperties) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onNewDhcpResults(DhcpResultsParcelable dhcpResultsParcelable)
                throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onPostDhcpAction() throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onPreDhcpAction() throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onPreconnectionStart(List list) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onProvisioningFailure(LinkProperties linkProperties) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onProvisioningSuccess(LinkProperties linkProperties) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onQuit() throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onReachabilityFailure(
                ReachabilityLossInfoParcelable reachabilityLossInfoParcelable)
                throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void onReachabilityLost(String str) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void setFallbackMulticastFilter(boolean z) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void setMaxDtimMultiplier(int i) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void setNeighborDiscoveryOffload(boolean z) throws RemoteException {}

        @Override // android.net.ip.IIpClientCallbacks
        public void startReadPacketFilter() throws RemoteException {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IIpClientCallbacks {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_installPacketFilter = 10;
        static final int TRANSACTION_onIpClientCreated = 1;
        static final int TRANSACTION_onLinkPropertiesChange = 7;
        static final int TRANSACTION_onNewDhcpResults = 4;
        static final int TRANSACTION_onPostDhcpAction = 3;
        static final int TRANSACTION_onPreDhcpAction = 2;
        static final int TRANSACTION_onPreconnectionStart = 14;
        static final int TRANSACTION_onProvisioningFailure = 6;
        static final int TRANSACTION_onProvisioningSuccess = 5;
        static final int TRANSACTION_onQuit = 9;
        static final int TRANSACTION_onReachabilityFailure = 15;
        static final int TRANSACTION_onReachabilityLost = 8;
        static final int TRANSACTION_setFallbackMulticastFilter = 12;
        static final int TRANSACTION_setMaxDtimMultiplier = 16;
        static final int TRANSACTION_setNeighborDiscoveryOffload = 13;
        static final int TRANSACTION_startReadPacketFilter = 11;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements IIpClientCallbacks {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
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

            @Override // android.net.ip.IIpClientCallbacks
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
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

            @Override // android.net.ip.IIpClientCallbacks
            public final void installPacketFilter(byte[] bArr) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method installPacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onIpClientCreated(IIpClient iIpClient) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeStrongInterface(iIpClient);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onIpClientCreated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onLinkPropertiesChange(LinkProperties linkProperties) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method onLinkPropertiesChange is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onNewDhcpResults(DhcpResultsParcelable dhcpResultsParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(dhcpResultsParcelable, 0);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onNewDhcpResults is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onPostDhcpAction() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onPostDhcpAction is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onPreDhcpAction() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onPreDhcpAction is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onPreconnectionStart(List list) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    _Parcel.writeTypedList(obtain, list, 0);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onPreconnectionStart is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onProvisioningFailure(LinkProperties linkProperties) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onProvisioningFailure is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onProvisioningSuccess(LinkProperties linkProperties) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(linkProperties, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onProvisioningSuccess is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onQuit() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onQuit is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onReachabilityFailure(
                    ReachabilityLossInfoParcelable reachabilityLossInfoParcelable) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeTypedObject(reachabilityLossInfoParcelable, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onReachabilityFailure is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void onReachabilityLost(String str) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeString(str);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onReachabilityLost is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void setFallbackMulticastFilter(boolean z) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method setFallbackMulticastFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void setMaxDtimMultiplier(int i) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setMaxDtimMultiplier is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void setNeighborDiscoveryOffload(boolean z) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method setNeighborDiscoveryOffload is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.ip.IIpClientCallbacks
            public final void startReadPacketFilter() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIpClientCallbacks.DESCRIPTOR);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startReadPacketFilter is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IIpClientCallbacks.DESCRIPTOR);
        }

        public static IIpClientCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IIpClientCallbacks.DESCRIPTOR);
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof IIpClientCallbacks)) {
                return (IIpClientCallbacks) queryLocalInterface;
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
            String str = IIpClientCallbacks.DESCRIPTOR;
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
                    onIpClientCreated(IIpClient.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 2:
                    onPreDhcpAction();
                    return true;
                case 3:
                    onPostDhcpAction();
                    return true;
                case 4:
                    onNewDhcpResults(
                            (DhcpResultsParcelable)
                                    parcel.readTypedObject(DhcpResultsParcelable.CREATOR));
                    return true;
                case 5:
                    onProvisioningSuccess(
                            (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR));
                    return true;
                case 6:
                    onProvisioningFailure(
                            (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR));
                    return true;
                case 7:
                    onLinkPropertiesChange(
                            (LinkProperties) parcel.readTypedObject(LinkProperties.CREATOR));
                    return true;
                case 8:
                    onReachabilityLost(parcel.readString());
                    return true;
                case 9:
                    onQuit();
                    return true;
                case 10:
                    installPacketFilter(parcel.createByteArray());
                    return true;
                case 11:
                    startReadPacketFilter();
                    return true;
                case 12:
                    setFallbackMulticastFilter(parcel.readBoolean());
                    return true;
                case 13:
                    setNeighborDiscoveryOffload(parcel.readBoolean());
                    return true;
                case 14:
                    onPreconnectionStart(
                            parcel.createTypedArrayList(Layer2PacketParcelable.CREATOR));
                    return true;
                case 15:
                    onReachabilityFailure(
                            (ReachabilityLossInfoParcelable)
                                    parcel.readTypedObject(ReachabilityLossInfoParcelable.CREATOR));
                    return true;
                case 16:
                    setMaxDtimMultiplier(parcel.readInt());
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static void writeTypedList(Parcel parcel, List list, int i) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                parcel.writeTypedObject((Parcelable) list.get(i2), i);
            }
        }
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void installPacketFilter(byte[] bArr) throws RemoteException;

    void onIpClientCreated(IIpClient iIpClient) throws RemoteException;

    void onLinkPropertiesChange(LinkProperties linkProperties) throws RemoteException;

    void onNewDhcpResults(DhcpResultsParcelable dhcpResultsParcelable) throws RemoteException;

    void onPostDhcpAction() throws RemoteException;

    void onPreDhcpAction() throws RemoteException;

    void onPreconnectionStart(List list) throws RemoteException;

    void onProvisioningFailure(LinkProperties linkProperties) throws RemoteException;

    void onProvisioningSuccess(LinkProperties linkProperties) throws RemoteException;

    void onQuit() throws RemoteException;

    void onReachabilityFailure(ReachabilityLossInfoParcelable reachabilityLossInfoParcelable)
            throws RemoteException;

    void onReachabilityLost(String str) throws RemoteException;

    void setFallbackMulticastFilter(boolean z) throws RemoteException;

    void setMaxDtimMultiplier(int i) throws RemoteException;

    void setNeighborDiscoveryOffload(boolean z) throws RemoteException;

    void startReadPacketFilter() throws RemoteException;
}
