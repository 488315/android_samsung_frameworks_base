package android.net.wifi.nl80211;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface IPnoScanEvent extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IPnoScanEvent";

    void OnPnoNetworkFound() throws RemoteException;

    void OnPnoScanFailed() throws RemoteException;

    public static class Default implements IPnoScanEvent {
        @Override // android.net.wifi.nl80211.IPnoScanEvent
        public void OnPnoNetworkFound() throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IPnoScanEvent
        public void OnPnoScanFailed() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPnoScanEvent {
        static final int TRANSACTION_OnPnoNetworkFound = 1;
        static final int TRANSACTION_OnPnoScanFailed = 2;

        public Stub() {
            attachInterface(this, IPnoScanEvent.DESCRIPTOR);
        }

        public static IPnoScanEvent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IPnoScanEvent.DESCRIPTOR);
            if (iin != null && (iin instanceof IPnoScanEvent)) {
                return (IPnoScanEvent) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "OnPnoNetworkFound";
                case 2:
                    return "OnPnoScanFailed";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IPnoScanEvent.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(IPnoScanEvent.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            OnPnoNetworkFound();
                            return true;
                        case 2:
                            OnPnoScanFailed();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IPnoScanEvent {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPnoScanEvent.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IPnoScanEvent
            public void OnPnoNetworkFound() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPnoScanEvent.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IPnoScanEvent
            public void OnPnoScanFailed() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IPnoScanEvent.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
