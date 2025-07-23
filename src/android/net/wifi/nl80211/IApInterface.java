package android.net.wifi.nl80211;

import android.net.wifi.nl80211.IApInterfaceEventCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IApInterface extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IApInterface";
    public static final int ENCRYPTION_TYPE_NONE = 0;
    public static final int ENCRYPTION_TYPE_WPA = 1;
    public static final int ENCRYPTION_TYPE_WPA2 = 2;

    public static class Default implements IApInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.wifi.nl80211.IApInterface
        public String getInterfaceName() throws RemoteException {
            return null;
        }

        @Override // android.net.wifi.nl80211.IApInterface
        public boolean registerCallback(IApInterfaceEventCallback iApInterfaceEventCallback) throws RemoteException {
            return false;
        }
    }

    String getInterfaceName() throws RemoteException;

    boolean registerCallback(IApInterfaceEventCallback iApInterfaceEventCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IApInterface {
        static final int TRANSACTION_getInterfaceName = 2;
        static final int TRANSACTION_registerCallback = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IApInterface.DESCRIPTOR);
        }

        public static IApInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IApInterface.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IApInterface)) {
                return (IApInterface) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "registerCallback";
            }
            if (i != 2) {
                return null;
            }
            return "getInterfaceName";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IApInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IApInterface.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IApInterfaceEventCallback asInterface = IApInterfaceEventCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                boolean registerCallback = registerCallback(asInterface);
                parcel2.writeNoException();
                parcel2.writeBoolean(registerCallback);
            } else if (i == 2) {
                String interfaceName = getInterfaceName();
                parcel2.writeNoException();
                parcel2.writeString(interfaceName);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IApInterface {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IApInterface.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IApInterface
            public boolean registerCallback(IApInterfaceEventCallback iApInterfaceEventCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApInterface.DESCRIPTOR);
                    obtain.writeStrongInterface(iApInterfaceEventCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.net.wifi.nl80211.IApInterface
            public String getInterfaceName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IApInterface.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
