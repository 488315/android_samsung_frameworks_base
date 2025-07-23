package android.sec.enterprise.proxy;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IProxyCredentialsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.sec.enterprise.proxy.IProxyCredentialsCallback";

    public static class Default implements IProxyCredentialsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.sec.enterprise.proxy.IProxyCredentialsCallback
        public void onAuthenticationResult(int i) throws RemoteException {
        }
    }

    void onAuthenticationResult(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IProxyCredentialsCallback {
        static final int TRANSACTION_onAuthenticationResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IProxyCredentialsCallback.DESCRIPTOR);
        }

        public static IProxyCredentialsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProxyCredentialsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProxyCredentialsCallback)) {
                return (IProxyCredentialsCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onAuthenticationResult";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProxyCredentialsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProxyCredentialsCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onAuthenticationResult(readInt);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IProxyCredentialsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProxyCredentialsCallback.DESCRIPTOR;
            }

            @Override // android.sec.enterprise.proxy.IProxyCredentialsCallback
            public void onAuthenticationResult(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProxyCredentialsCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
