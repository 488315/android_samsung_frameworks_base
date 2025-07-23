package android.net.wifi.nl80211;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IWificondEventCallback extends IInterface {
    public static final String DESCRIPTOR = "android.net.wifi.nl80211.IWificondEventCallback";

    public static class Default implements IWificondEventCallback {
        @Override // android.net.wifi.nl80211.IWificondEventCallback
        public void OnRegDomainChanged(String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    void OnRegDomainChanged(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IWificondEventCallback {
        static final int TRANSACTION_OnRegDomainChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IWificondEventCallback.DESCRIPTOR);
        }

        public static IWificondEventCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IWificondEventCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IWificondEventCallback)) {
                return (IWificondEventCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "OnRegDomainChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IWificondEventCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IWificondEventCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                OnRegDomainChanged(readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IWificondEventCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IWificondEventCallback.DESCRIPTOR;
            }

            @Override // android.net.wifi.nl80211.IWificondEventCallback
            public void OnRegDomainChanged(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IWificondEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
