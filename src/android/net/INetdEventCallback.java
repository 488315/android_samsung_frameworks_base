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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INetdEventCallback)) {
                return (INetdEventCallback) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                String string = parcel.readString();
                String[] strArrCreateStringArray = parcel.createStringArray();
                int i6 = parcel.readInt();
                long j = parcel.readLong();
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDnsEvent(i3, i4, i5, string, strArrCreateStringArray, i6, j, i7);
            } else if (i == 2) {
                int i8 = parcel.readInt();
                boolean z = parcel.readBoolean();
                String string2 = parcel.readString();
                int i9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onNat64PrefixEvent(i8, z, string2, i9);
            } else if (i == 3) {
                int i10 = parcel.readInt();
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                boolean z2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onPrivateDnsValidationEvent(i10, string3, string4, z2);
            } else if (i == 4) {
                String string5 = parcel.readString();
                int i11 = parcel.readInt();
                long j2 = parcel.readLong();
                int i12 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onConnectEvent(string5, i11, j2, i12);
            } else if (i == 5) {
                int i13 = parcel.readInt();
                int i14 = parcel.readInt();
                int i15 = parcel.readInt();
                int i16 = parcel.readInt();
                int i17 = parcel.readInt();
                int i18 = parcel.readInt();
                int i19 = parcel.readInt();
                int i20 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onNetworkMetricsUpdated(i13, i14, i15, i16, i17, i18, i19, i20);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onNat64PrefixEvent(int i, boolean z, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onPrivateDnsValidationEvent(int i, String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onConnectEvent(String str, int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.INetdEventCallback
            public void onNetworkMetricsUpdated(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    parcelObtain.writeInt(i6);
                    parcelObtain.writeInt(i7);
                    parcelObtain.writeInt(i8);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
