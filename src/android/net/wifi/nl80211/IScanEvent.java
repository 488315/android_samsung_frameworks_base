package android.net.wifi.nl80211;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IScanEvent extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IScanEvent";

    public static class Default implements IScanEvent {
        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanFailed() throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanRequestFailed(int i) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IScanEvent
        public void OnScanResultReady() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void OnScanFailed() throws RemoteException;

    void OnScanRequestFailed(int i) throws RemoteException;

    void OnScanResultReady() throws RemoteException;

    public static abstract class Stub extends Binder implements IScanEvent {
        static final int TRANSACTION_OnScanFailed = 2;
        static final int TRANSACTION_OnScanRequestFailed = 3;
        static final int TRANSACTION_OnScanResultReady = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IScanEvent.DESCRIPTOR);
        }

        public static IScanEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IScanEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IScanEvent)) {
                return (IScanEvent) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "OnScanResultReady";
            }
            if (i == 2) {
                return "OnScanFailed";
            }
            if (i != 3) {
                return null;
            }
            return "OnScanRequestFailed";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IScanEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IScanEvent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                OnScanResultReady();
            } else if (i == 2) {
                OnScanFailed();
            } else if (i == 3) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                OnScanRequestFailed(readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IScanEvent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IScanEvent.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IScanEvent
            public void OnScanResultReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IScanEvent.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IScanEvent
            public void OnScanFailed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IScanEvent.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IScanEvent
            public void OnScanRequestFailed(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IScanEvent.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
