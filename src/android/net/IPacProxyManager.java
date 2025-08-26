package android.net;

import android.net.IPacProxyInstalledListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IPacProxyManager extends IInterface {
    public static final String DESCRIPTOR = "android.net.IPacProxyManager";

    public static class Default implements IPacProxyManager {
        @Override // android.net.IPacProxyManager
        public void addListener(IPacProxyInstalledListener iPacProxyInstalledListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.IPacProxyManager
        public void removeListener(IPacProxyInstalledListener iPacProxyInstalledListener) throws RemoteException {
        }

        @Override // android.net.IPacProxyManager
        public void setCurrentProxyScriptUrl(ProxyInfo proxyInfo) throws RemoteException {
        }
    }

    void addListener(IPacProxyInstalledListener iPacProxyInstalledListener) throws RemoteException;

    void removeListener(IPacProxyInstalledListener iPacProxyInstalledListener) throws RemoteException;

    void setCurrentProxyScriptUrl(ProxyInfo proxyInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements IPacProxyManager {
        static final int TRANSACTION_addListener = 1;
        static final int TRANSACTION_removeListener = 2;
        static final int TRANSACTION_setCurrentProxyScriptUrl = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IPacProxyManager.DESCRIPTOR);
        }

        public static IPacProxyManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPacProxyManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IPacProxyManager)) {
                return (IPacProxyManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addListener";
            }
            if (i == 2) {
                return "removeListener";
            }
            if (i != 3) {
                return null;
            }
            return "setCurrentProxyScriptUrl";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPacProxyManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPacProxyManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IPacProxyInstalledListener iPacProxyInstalledListenerAsInterface = IPacProxyInstalledListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                addListener(iPacProxyInstalledListenerAsInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                IPacProxyInstalledListener iPacProxyInstalledListenerAsInterface2 = IPacProxyInstalledListener.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                removeListener(iPacProxyInstalledListenerAsInterface2);
                parcel2.writeNoException();
            } else if (i == 3) {
                ProxyInfo proxyInfo = (ProxyInfo) parcel.readTypedObject(ProxyInfo.CREATOR);
                parcel.enforceNoDataAvail();
                setCurrentProxyScriptUrl(proxyInfo);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IPacProxyManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPacProxyManager.DESCRIPTOR;
            }

            @Override // android.net.IPacProxyManager
            public void addListener(IPacProxyInstalledListener iPacProxyInstalledListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPacProxyManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPacProxyInstalledListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IPacProxyManager
            public void removeListener(IPacProxyInstalledListener iPacProxyInstalledListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPacProxyManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iPacProxyInstalledListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.net.IPacProxyManager
            public void setCurrentProxyScriptUrl(ProxyInfo proxyInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPacProxyManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(proxyInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
