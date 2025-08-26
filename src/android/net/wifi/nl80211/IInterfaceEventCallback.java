package android.net.wifi.nl80211;

import android.net.wifi.nl80211.IApInterface;
import android.net.wifi.nl80211.IClientInterface;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IInterfaceEventCallback extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IInterfaceEventCallback";

    public static class Default implements IInterfaceEventCallback {
        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnApInterfaceReady(IApInterface iApInterface) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnApTorndownEvent(IApInterface iApInterface) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnClientInterfaceReady(IClientInterface iClientInterface) throws RemoteException {
        }

        @Override // android.net.wifi.nl80211.IInterfaceEventCallback
        public void OnClientTorndownEvent(IClientInterface iClientInterface) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void OnApInterfaceReady(IApInterface iApInterface) throws RemoteException;

    void OnApTorndownEvent(IApInterface iApInterface) throws RemoteException;

    void OnClientInterfaceReady(IClientInterface iClientInterface) throws RemoteException;

    void OnClientTorndownEvent(IClientInterface iClientInterface) throws RemoteException;

    public static abstract class Stub extends Binder implements IInterfaceEventCallback {
        static final int TRANSACTION_OnApInterfaceReady = 2;
        static final int TRANSACTION_OnApTorndownEvent = 4;
        static final int TRANSACTION_OnClientInterfaceReady = 1;
        static final int TRANSACTION_OnClientTorndownEvent = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, IInterfaceEventCallback.DESCRIPTOR);
        }

        public static IInterfaceEventCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInterfaceEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInterfaceEventCallback)) {
                return (IInterfaceEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "OnClientInterfaceReady";
            }
            if (i == 2) {
                return "OnApInterfaceReady";
            }
            if (i == 3) {
                return "OnClientTorndownEvent";
            }
            if (i != 4) {
                return null;
            }
            return "OnApTorndownEvent";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInterfaceEventCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInterfaceEventCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IClientInterface iClientInterfaceAsInterface = IClientInterface.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                OnClientInterfaceReady(iClientInterfaceAsInterface);
            } else if (i == 2) {
                IApInterface iApInterfaceAsInterface = IApInterface.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                OnApInterfaceReady(iApInterfaceAsInterface);
            } else if (i == 3) {
                IClientInterface iClientInterfaceAsInterface2 = IClientInterface.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                OnClientTorndownEvent(iClientInterfaceAsInterface2);
            } else if (i == 4) {
                IApInterface iApInterfaceAsInterface2 = IApInterface.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                OnApTorndownEvent(iApInterfaceAsInterface2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInterfaceEventCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInterfaceEventCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnClientInterfaceReady(IClientInterface iClientInterface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInterfaceEventCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iClientInterface);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnApInterfaceReady(IApInterface iApInterface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInterfaceEventCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApInterface);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnClientTorndownEvent(IClientInterface iClientInterface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInterfaceEventCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iClientInterface);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IInterfaceEventCallback
            public void OnApTorndownEvent(IApInterface iApInterface) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInterfaceEventCallback.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApInterface);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
