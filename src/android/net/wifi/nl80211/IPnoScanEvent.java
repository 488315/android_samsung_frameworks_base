package android.net.wifi.nl80211;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPnoScanEvent extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IPnoScanEvent";

    public static class Default implements IPnoScanEvent {
        @Override // android.net.wifi.nl80211.IPnoScanEvent
        public void OnPnoNetworkFound() throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IPnoScanEvent
        public void OnPnoScanFailed() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void OnPnoNetworkFound() throws RemoteException;

    void OnPnoScanFailed() throws RemoteException;

    public static abstract class Stub extends Binder implements IPnoScanEvent {
        static final int TRANSACTION_OnPnoNetworkFound = 1;
        static final int TRANSACTION_OnPnoScanFailed = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IPnoScanEvent.DESCRIPTOR);
        }

        public static IPnoScanEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPnoScanEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IPnoScanEvent)) {
                return (IPnoScanEvent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "OnPnoNetworkFound";
            }
            if (i != 2) {
                return null;
            }
            return "OnPnoScanFailed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPnoScanEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPnoScanEvent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                OnPnoNetworkFound();
            } else if (i == 2) {
                OnPnoScanFailed();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPnoScanEvent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPnoScanEvent.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IPnoScanEvent
            public void OnPnoNetworkFound() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPnoScanEvent.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IPnoScanEvent
            public void OnPnoScanFailed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IPnoScanEvent.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
