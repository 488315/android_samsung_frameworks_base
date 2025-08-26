package android.net.wifi.nl80211;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IApInterfaceEventCallback extends IInterface {
    public static final int BANDWIDTH_160 = 6;
    public static final int BANDWIDTH_20 = 2;
    public static final int BANDWIDTH_20_NOHT = 1;
    public static final int BANDWIDTH_320 = 7;
    public static final int BANDWIDTH_40 = 3;
    public static final int BANDWIDTH_80 = 4;
    public static final int BANDWIDTH_80P80 = 5;
    public static final int BANDWIDTH_INVALID = 0;
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IApInterfaceEventCallback";

    public static class Default implements IApInterfaceEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
        public void onConnectedClientsChanged(NativeWifiClient nativeWifiClient, boolean z) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
        public void onSoftApChannelSwitched(int i, int i2) throws RemoteException {
        }
    }

    void onConnectedClientsChanged(NativeWifiClient nativeWifiClient, boolean z) throws RemoteException;

    void onSoftApChannelSwitched(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IApInterfaceEventCallback {
        static final int TRANSACTION_onConnectedClientsChanged = 1;
        static final int TRANSACTION_onSoftApChannelSwitched = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IApInterfaceEventCallback.DESCRIPTOR);
        }

        public static IApInterfaceEventCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IApInterfaceEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IApInterfaceEventCallback)) {
                return (IApInterfaceEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onConnectedClientsChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onSoftApChannelSwitched";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IApInterfaceEventCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IApInterfaceEventCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                NativeWifiClient nativeWifiClient = (NativeWifiClient) parcel.readTypedObject(NativeWifiClient.CREATOR);
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onConnectedClientsChanged(nativeWifiClient, z);
            } else if (i == 2) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSoftApChannelSwitched(i3, i4);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IApInterfaceEventCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IApInterfaceEventCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
            public void onConnectedClientsChanged(NativeWifiClient nativeWifiClient, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IApInterfaceEventCallback.DESCRIPTOR);
                    parcelObtain.writeTypedObject(nativeWifiClient, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
            public void onSoftApChannelSwitched(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IApInterfaceEventCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
