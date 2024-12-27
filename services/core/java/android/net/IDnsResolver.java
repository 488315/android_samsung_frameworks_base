package android.net;

import android.net.metrics.INetdEventListener;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface IDnsResolver extends IInterface {
    public static final String DESCRIPTOR = "android$net$IDnsResolver".replace('$', '.');
    public static final int DNS_RESOLVER_LOG_DEBUG = 1;
    public static final int DNS_RESOLVER_LOG_ERROR = 4;
    public static final int DNS_RESOLVER_LOG_INFO = 2;
    public static final int DNS_RESOLVER_LOG_VERBOSE = 0;
    public static final int DNS_RESOLVER_LOG_WARNING = 3;
    public static final String HASH = "882638dc86e8afd0924ecf7c28db6cce572f7e7d";
    public static final int RESOLVER_PARAMS_BASE_TIMEOUT_MSEC = 4;
    public static final int RESOLVER_PARAMS_COUNT = 6;
    public static final int RESOLVER_PARAMS_MAX_SAMPLES = 3;
    public static final int RESOLVER_PARAMS_MIN_SAMPLES = 2;
    public static final int RESOLVER_PARAMS_RETRY_COUNT = 5;
    public static final int RESOLVER_PARAMS_SAMPLE_VALIDITY = 0;
    public static final int RESOLVER_PARAMS_SUCCESS_THRESHOLD = 1;
    public static final int RESOLVER_STATS_COUNT = 7;
    public static final int RESOLVER_STATS_ERRORS = 1;
    public static final int RESOLVER_STATS_INTERNAL_ERRORS = 3;
    public static final int RESOLVER_STATS_LAST_SAMPLE_TIME = 5;
    public static final int RESOLVER_STATS_RTT_AVG = 4;
    public static final int RESOLVER_STATS_SUCCESSES = 0;
    public static final int RESOLVER_STATS_TIMEOUTS = 2;
    public static final int RESOLVER_STATS_USABLE = 6;
    public static final int TC_MODE_DEFAULT = 0;
    public static final int TC_MODE_UDP_TCP = 1;
    public static final int TRANSPORT_BLUETOOTH = 2;
    public static final int TRANSPORT_CELLULAR = 0;
    public static final int TRANSPORT_ETHERNET = 3;
    public static final int TRANSPORT_LOWPAN = 6;
    public static final int TRANSPORT_TEST = 7;
    public static final int TRANSPORT_UNKNOWN = -1;
    public static final int TRANSPORT_USB = 8;
    public static final int TRANSPORT_VPN = 4;
    public static final int TRANSPORT_WIFI = 1;
    public static final int TRANSPORT_WIFI_AWARE = 5;
    public static final int VERSION = 9;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements IDnsResolver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.IDnsResolver
        public void createNetworkCache(int i) throws RemoteException {}

        @Override // android.net.IDnsResolver
        public void destroyNetworkCache(int i) throws RemoteException {}

        @Override // android.net.IDnsResolver
        public void flushNetworkCache(int i) throws RemoteException {}

        @Override // android.net.IDnsResolver
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.IDnsResolver
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.IDnsResolver
        public String getPrefix64(int i) throws RemoteException {
            return null;
        }

        @Override // android.net.IDnsResolver
        public void getResolverInfo(
                int i,
                String[] strArr,
                String[] strArr2,
                String[] strArr3,
                int[] iArr,
                int[] iArr2,
                int[] iArr3)
                throws RemoteException {}

        @Override // android.net.IDnsResolver
        public boolean isAlive() throws RemoteException {
            return false;
        }

        @Override // android.net.IDnsResolver
        public void registerEventListener(INetdEventListener iNetdEventListener)
                throws RemoteException {}

        @Override // android.net.IDnsResolver
        public void registerUnsolicitedEventListener(
                IDnsResolverUnsolicitedEventListener iDnsResolverUnsolicitedEventListener)
                throws RemoteException {}

        @Override // android.net.IDnsResolver
        public void setLogSeverity(int i) throws RemoteException {}

        @Override // android.net.IDnsResolver
        public void setPrefix64(int i, String str) throws RemoteException {}

        @Override // android.net.IDnsResolver
        public void setResolverConfiguration(ResolverParamsParcel resolverParamsParcel)
                throws RemoteException {}

        @Override // android.net.IDnsResolver
        public void setResolverOptions(int i, ResolverOptionsParcel resolverOptionsParcel)
                throws RemoteException {}

        @Override // android.net.IDnsResolver
        public void startPrefix64Discovery(int i) throws RemoteException {}

        @Override // android.net.IDnsResolver
        public void stopPrefix64Discovery(int i) throws RemoteException {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements IDnsResolver {
        static final int TRANSACTION_createNetworkCache = 8;
        static final int TRANSACTION_destroyNetworkCache = 9;
        static final int TRANSACTION_flushNetworkCache = 11;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPrefix64 = 7;
        static final int TRANSACTION_getResolverInfo = 4;
        static final int TRANSACTION_isAlive = 1;
        static final int TRANSACTION_registerEventListener = 2;
        static final int TRANSACTION_registerUnsolicitedEventListener = 13;
        static final int TRANSACTION_setLogSeverity = 10;
        static final int TRANSACTION_setPrefix64 = 12;
        static final int TRANSACTION_setResolverConfiguration = 3;
        static final int TRANSACTION_setResolverOptions = 14;
        static final int TRANSACTION_startPrefix64Discovery = 5;
        static final int TRANSACTION_stopPrefix64Discovery = 6;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements IDnsResolver {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.IDnsResolver
            public final void createNetworkCache(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method createNetworkCache is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void destroyNetworkCache(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method destroyNetworkCache is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void flushNetworkCache(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method flushNetworkCache is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
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

            @Override // android.net.IDnsResolver
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
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

            @Override // android.net.IDnsResolver
            public final String getPrefix64(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getPrefix64 is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void getResolverInfo(
                    int i,
                    String[] strArr,
                    String[] strArr2,
                    String[] strArr3,
                    int[] iArr,
                    int[] iArr2,
                    int[] iArr3) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(strArr.length);
                    obtain.writeInt(strArr2.length);
                    obtain.writeInt(strArr3.length);
                    obtain.writeInt(iArr.length);
                    obtain.writeInt(iArr2.length);
                    obtain.writeInt(iArr3.length);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getResolverInfo is unimplemented.");
                    }
                    obtain2.readException();
                    obtain2.readStringArray(strArr);
                    obtain2.readStringArray(strArr2);
                    obtain2.readStringArray(strArr3);
                    obtain2.readIntArray(iArr);
                    obtain2.readIntArray(iArr2);
                    obtain2.readIntArray(iArr3);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final boolean isAlive() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method isAlive is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void registerEventListener(INetdEventListener iNetdEventListener) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeStrongInterface(iNetdEventListener);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method registerEventListener is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void registerUnsolicitedEventListener(
                    IDnsResolverUnsolicitedEventListener iDnsResolverUnsolicitedEventListener) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeStrongInterface(iDnsResolverUnsolicitedEventListener);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new RemoteException(
                                "Method registerUnsolicitedEventListener is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void setLogSeverity(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setLogSeverity is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void setPrefix64(int i, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setPrefix64 is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void setResolverConfiguration(ResolverParamsParcel resolverParamsParcel) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeTypedObject(resolverParamsParcel, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException(
                                "Method setResolverConfiguration is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void setResolverOptions(
                    int i, ResolverOptionsParcel resolverOptionsParcel) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resolverOptionsParcel, 0);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setResolverOptions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void startPrefix64Discovery(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException(
                                "Method startPrefix64Discovery is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.IDnsResolver
            public final void stopPrefix64Discovery(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolver.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method stopPrefix64Discovery is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDnsResolver.DESCRIPTOR);
        }

        public static IDnsResolver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDnsResolver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDnsResolver)) {
                return (IDnsResolver) queryLocalInterface;
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
            String str = IDnsResolver.DESCRIPTOR;
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
                    boolean isAlive = isAlive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlive);
                    return true;
                case 2:
                    registerEventListener(
                            INetdEventListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 3:
                    setResolverConfiguration(
                            (ResolverParamsParcel)
                                    parcel.readTypedObject(ResolverParamsParcel.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String[] strArr = readInt2 < 0 ? null : new String[readInt2];
                    int readInt3 = parcel.readInt();
                    String[] strArr2 = readInt3 < 0 ? null : new String[readInt3];
                    int readInt4 = parcel.readInt();
                    String[] strArr3 = readInt4 < 0 ? null : new String[readInt4];
                    int readInt5 = parcel.readInt();
                    int[] iArr = readInt5 < 0 ? null : new int[readInt5];
                    int readInt6 = parcel.readInt();
                    int[] iArr2 = readInt6 < 0 ? null : new int[readInt6];
                    int readInt7 = parcel.readInt();
                    int[] iArr3 = readInt7 >= 0 ? new int[readInt7] : null;
                    getResolverInfo(readInt, strArr, strArr2, strArr3, iArr, iArr2, iArr3);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArr);
                    parcel2.writeStringArray(strArr2);
                    parcel2.writeStringArray(strArr3);
                    parcel2.writeIntArray(iArr);
                    parcel2.writeIntArray(iArr2);
                    parcel2.writeIntArray(iArr3);
                    return true;
                case 5:
                    startPrefix64Discovery(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 6:
                    stopPrefix64Discovery(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String prefix64 = getPrefix64(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeString(prefix64);
                    return true;
                case 8:
                    createNetworkCache(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 9:
                    destroyNetworkCache(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 10:
                    setLogSeverity(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 11:
                    flushNetworkCache(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 12:
                    setPrefix64(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 13:
                    registerUnsolicitedEventListener(
                            IDnsResolverUnsolicitedEventListener.Stub.asInterface(
                                    parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 14:
                    setResolverOptions(
                            parcel.readInt(),
                            (ResolverOptionsParcel)
                                    parcel.readTypedObject(ResolverOptionsParcel.CREATOR));
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void createNetworkCache(int i) throws RemoteException;

    void destroyNetworkCache(int i) throws RemoteException;

    void flushNetworkCache(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    String getPrefix64(int i) throws RemoteException;

    void getResolverInfo(
            int i,
            String[] strArr,
            String[] strArr2,
            String[] strArr3,
            int[] iArr,
            int[] iArr2,
            int[] iArr3)
            throws RemoteException;

    boolean isAlive() throws RemoteException;

    void registerEventListener(INetdEventListener iNetdEventListener) throws RemoteException;

    void registerUnsolicitedEventListener(
            IDnsResolverUnsolicitedEventListener iDnsResolverUnsolicitedEventListener)
            throws RemoteException;

    void setLogSeverity(int i) throws RemoteException;

    void setPrefix64(int i, String str) throws RemoteException;

    void setResolverConfiguration(ResolverParamsParcel resolverParamsParcel) throws RemoteException;

    void setResolverOptions(int i, ResolverOptionsParcel resolverOptionsParcel)
            throws RemoteException;

    void startPrefix64Discovery(int i) throws RemoteException;

    void stopPrefix64Discovery(int i) throws RemoteException;
}
