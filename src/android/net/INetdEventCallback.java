package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface INetdEventCallback extends IInterface {
    public static final int CALLBACK_CALLER_CONNECTIVITY_SERVICE = 0;
    public static final int CALLBACK_CALLER_DEVICE_POLICY = 1;
    public static final int CALLBACK_CALLER_ECHOLOCATE_SERVICE = 4;
    public static final int CALLBACK_CALLER_INDIVIDUAL_APPS = 3;
    public static final int CALLBACK_CALLER_INTELLIGENT_NETWORK_TRAFFIC_CONTROLLER = 5;
    public static final int CALLBACK_CALLER_METRICS = 6;
    public static final int CALLBACK_CALLER_NETWORK_WATCHLIST = 2;

    public static class Default implements INetdEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.INetdEventCallback
        public void onConnectEvent(String str, int i, long j, int i2) throws RemoteException {
        }

        @Override // android.net.INetdEventCallback
        public void onDnsEvent(int i, int i2, int i3, String str, String[] strArr, int i4, long j, int i5) throws RemoteException {
        }

        @Override // android.net.INetdEventCallback
        public void onNat64PrefixEvent(int i, boolean z, String str, int i2) throws RemoteException {
        }

        @Override // android.net.INetdEventCallback
        public void onNetworkMetricsUpdated(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws RemoteException {
        }

        @Override // android.net.INetdEventCallback
        public void onPrivateDnsValidationEvent(int i, String str, String str2, boolean z) throws RemoteException {
        }
    }

    void onConnectEvent(String str, int i, long j, int i2) throws RemoteException;

    void onDnsEvent(int i, int i2, int i3, String str, String[] strArr, int i4, long j, int i5) throws RemoteException;

    void onNat64PrefixEvent(int i, boolean z, String str, int i2) throws RemoteException;

    void onNetworkMetricsUpdated(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws RemoteException;

    void onPrivateDnsValidationEvent(int i, String str, String str2, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements INetdEventCallback {
        public static final String DESCRIPTOR = "android.net.INetdEventCallback";
        static final int TRANSACTION_onConnectEvent = 4;
        static final int TRANSACTION_onDnsEvent = 1;
        static final int TRANSACTION_onNat64PrefixEvent = 2;
        static final int TRANSACTION_onNetworkMetricsUpdated = 5;
        static final int TRANSACTION_onPrivateDnsValidationEvent = 3;

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

        public static INetdEventCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INetdEventCallback)) {
                return (INetdEventCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onDnsEvent";
            }
            if (i == 2) {
                return "onNat64PrefixEvent";
            }
            if (i == 3) {
                return "onPrivateDnsValidationEvent";
            }
            if (i == 4) {
                return "onConnectEvent";
            }
            if (i != 5) {
                return null;
            }
            return "onNetworkMetricsUpdated";
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
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                String readString = parcel.readString();
                String[] createStringArray = parcel.createStringArray();
                int readInt4 = parcel.readInt();
                long readLong = parcel.readLong();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDnsEvent(readInt, readInt2, readInt3, readString, createStringArray, readInt4, readLong, readInt5);
            } else if (i == 2) {
                int readInt6 = parcel.readInt();
                boolean readBoolean = parcel.readBoolean();
                String readString2 = parcel.readString();
                int readInt7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onNat64PrefixEvent(readInt6, readBoolean, readString2, readInt7);
            } else if (i == 3) {
                int readInt8 = parcel.readInt();
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onPrivateDnsValidationEvent(readInt8, readString3, readString4, readBoolean2);
            } else if (i == 4) {
                String readString5 = parcel.readString();
                int readInt9 = parcel.readInt();
                long readLong2 = parcel.readLong();
                int readInt10 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onConnectEvent(readString5, readInt9, readLong2, readInt10);
            } else if (i == 5) {
                int readInt11 = parcel.readInt();
                int readInt12 = parcel.readInt();
                int readInt13 = parcel.readInt();
                int readInt14 = parcel.readInt();
                int readInt15 = parcel.readInt();
                int readInt16 = parcel.readInt();
                int readInt17 = parcel.readInt();
                int readInt18 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onNetworkMetricsUpdated(readInt11, readInt12, readInt13, readInt14, readInt15, readInt16, readInt17, readInt18);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements INetdEventCallback {
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

            @Override // android.net.INetdEventCallback
            public void onDnsEvent(int i, int i2, int i3, String str, String[] strArr, int i4, long j, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i4);
                    obtain.writeLong(j);
                    obtain.writeInt(i5);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onNat64PrefixEvent(int i, boolean z, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onPrivateDnsValidationEvent(int i, String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onConnectEvent(String str, int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onNetworkMetricsUpdated(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    obtain.writeInt(i6);
                    obtain.writeInt(i7);
                    obtain.writeInt(i8);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
