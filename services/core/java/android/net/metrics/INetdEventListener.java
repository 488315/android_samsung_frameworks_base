package android.net.metrics;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface INetdEventListener extends IInterface {
    public static final String DESCRIPTOR =
            "android$net$metrics$INetdEventListener".replace('$', '.');
    public static final int DNS_REPORTED_IP_ADDRESSES_LIMIT = 10;
    public static final int EVENT_GETADDRINFO = 1;
    public static final int EVENT_GETHOSTBYADDR = 3;
    public static final int EVENT_GETHOSTBYNAME = 2;
    public static final int EVENT_RES_NSEND = 4;
    public static final String HASH = "8e27594d285ca7c567d87e8cf74766c27647e02b";
    public static final int REPORTING_LEVEL_FULL = 2;
    public static final int REPORTING_LEVEL_METRICS = 1;
    public static final int REPORTING_LEVEL_NONE = 0;
    public static final int VERSION = 1;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class Default implements INetdEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.metrics.INetdEventListener
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.metrics.INetdEventListener
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.metrics.INetdEventListener
        public void onConnectEvent(int i, int i2, int i3, String str, int i4, int i5)
                throws RemoteException {}

        @Override // android.net.metrics.INetdEventListener
        public void onDnsEvent(
                int i, int i2, int i3, int i4, String str, String[] strArr, int i5, int i6)
                throws RemoteException {}

        @Override // android.net.metrics.INetdEventListener
        public void onNat64PrefixEvent(int i, boolean z, String str, int i2)
                throws RemoteException {}

        @Override // android.net.metrics.INetdEventListener
        public void onPrivateDnsValidationEvent(int i, String str, String str2, boolean z)
                throws RemoteException {}

        @Override // android.net.metrics.INetdEventListener
        public void onTcpSocketStatsEvent(
                int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5)
                throws RemoteException {}

        @Override // android.net.metrics.INetdEventListener
        public void onWakeupEvent(
                String str,
                int i,
                int i2,
                int i3,
                byte[] bArr,
                String str2,
                String str3,
                int i4,
                int i5,
                long j)
                throws RemoteException {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends Binder implements INetdEventListener {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onConnectEvent = 3;
        static final int TRANSACTION_onDnsEvent = 1;
        static final int TRANSACTION_onNat64PrefixEvent = 6;
        static final int TRANSACTION_onPrivateDnsValidationEvent = 2;
        static final int TRANSACTION_onTcpSocketStatsEvent = 5;
        static final int TRANSACTION_onWakeupEvent = 4;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Proxy implements INetdEventListener {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.metrics.INetdEventListener
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(INetdEventListener.DESCRIPTOR);
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

            @Override // android.net.metrics.INetdEventListener
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(INetdEventListener.DESCRIPTOR);
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

            @Override // android.net.metrics.INetdEventListener
            public final void onConnectEvent(int i, int i2, int i3, String str, int i4, int i5) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetdEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onConnectEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.metrics.INetdEventListener
            public final void onDnsEvent(
                    int i, int i2, int i3, int i4, String str, String[] strArr, int i5, int i6) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetdEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onDnsEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.metrics.INetdEventListener
            public final void onNat64PrefixEvent(int i, boolean z, String str, int i2) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetdEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onNat64PrefixEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.metrics.INetdEventListener
            public final void onPrivateDnsValidationEvent(
                    int i, String str, String str2, boolean z) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetdEventListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method onPrivateDnsValidationEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.metrics.INetdEventListener
            public final void onTcpSocketStatsEvent(
                    int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetdEventListener.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeIntArray(iArr2);
                    obtain.writeIntArray(iArr3);
                    obtain.writeIntArray(iArr4);
                    obtain.writeIntArray(iArr5);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onTcpSocketStatsEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.metrics.INetdEventListener
            public final void onWakeupEvent(
                    String str,
                    int i,
                    int i2,
                    int i3,
                    byte[] bArr,
                    String str2,
                    String str3,
                    int i4,
                    int i5,
                    long j) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(INetdEventListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeLong(j);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onWakeupEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, INetdEventListener.DESCRIPTOR);
        }

        public static INetdEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(INetdEventListener.DESCRIPTOR);
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof INetdEventListener)) {
                return (INetdEventListener) queryLocalInterface;
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
            String str = INetdEventListener.DESCRIPTOR;
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
                    onDnsEvent(
                            parcel.readInt(),
                            parcel.readInt(),
                            parcel.readInt(),
                            parcel.readInt(),
                            parcel.readString(),
                            parcel.createStringArray(),
                            parcel.readInt(),
                            parcel.readInt());
                    return true;
                case 2:
                    onPrivateDnsValidationEvent(
                            parcel.readInt(),
                            parcel.readString(),
                            parcel.readString(),
                            parcel.readBoolean());
                    return true;
                case 3:
                    onConnectEvent(
                            parcel.readInt(),
                            parcel.readInt(),
                            parcel.readInt(),
                            parcel.readString(),
                            parcel.readInt(),
                            parcel.readInt());
                    return true;
                case 4:
                    onWakeupEvent(
                            parcel.readString(),
                            parcel.readInt(),
                            parcel.readInt(),
                            parcel.readInt(),
                            parcel.createByteArray(),
                            parcel.readString(),
                            parcel.readString(),
                            parcel.readInt(),
                            parcel.readInt(),
                            parcel.readLong());
                    return true;
                case 5:
                    onTcpSocketStatsEvent(
                            parcel.createIntArray(),
                            parcel.createIntArray(),
                            parcel.createIntArray(),
                            parcel.createIntArray(),
                            parcel.createIntArray());
                    return true;
                case 6:
                    onNat64PrefixEvent(
                            parcel.readInt(),
                            parcel.readBoolean(),
                            parcel.readString(),
                            parcel.readInt());
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onConnectEvent(int i, int i2, int i3, String str, int i4, int i5) throws RemoteException;

    void onDnsEvent(int i, int i2, int i3, int i4, String str, String[] strArr, int i5, int i6)
            throws RemoteException;

    void onNat64PrefixEvent(int i, boolean z, String str, int i2) throws RemoteException;

    void onPrivateDnsValidationEvent(int i, String str, String str2, boolean z)
            throws RemoteException;

    void onTcpSocketStatsEvent(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5)
            throws RemoteException;

    void onWakeupEvent(
            String str,
            int i,
            int i2,
            int i3,
            byte[] bArr,
            String str2,
            String str3,
            int i4,
            int i5,
            long j)
            throws RemoteException;
}
