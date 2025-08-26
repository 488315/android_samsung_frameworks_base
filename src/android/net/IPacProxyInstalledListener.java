package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPacProxyInstalledListener extends IInterface {
    public static final String DESCRIPTOR = "android.net.IPacProxyInstalledListener";

    public static class Default implements IPacProxyInstalledListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.IPacProxyInstalledListener
        public void onPacProxyInstalled(Network network, ProxyInfo proxyInfo) throws RemoteException {
        }
    }

    void onPacProxyInstalled(Network network, ProxyInfo proxyInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IPacProxyInstalledListener {
        static final int TRANSACTION_onPacProxyInstalled = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IPacProxyInstalledListener.DESCRIPTOR);
        }

        public static IPacProxyInstalledListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPacProxyInstalledListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPacProxyInstalledListener)) {
                return (IPacProxyInstalledListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onPacProxyInstalled";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPacProxyInstalledListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPacProxyInstalledListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Network network = (Network) parcel.readTypedObject(Network.CREATOR);
                ProxyInfo proxyInfo = (ProxyInfo) parcel.readTypedObject(ProxyInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onPacProxyInstalled(network, proxyInfo);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IPacProxyInstalledListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPacProxyInstalledListener.DESCRIPTOR;
            }

            @Override // android.net.IPacProxyInstalledListener
            public void onPacProxyInstalled(Network network, ProxyInfo proxyInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IPacProxyInstalledListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(network, 0);
                    parcelObtain.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
