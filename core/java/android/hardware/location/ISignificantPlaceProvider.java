package android.hardware.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface ISignificantPlaceProvider extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.location.ISignificantPlaceProvider";

    void onSignificantPlaceCheck() throws RemoteException;

    void setSignificantPlaceProviderManager(
            ISignificantPlaceProviderManager iSignificantPlaceProviderManager)
            throws RemoteException;

    public static class Default implements ISignificantPlaceProvider {
        @Override // android.hardware.location.ISignificantPlaceProvider
        public void setSignificantPlaceProviderManager(ISignificantPlaceProviderManager manager)
                throws RemoteException {}

        @Override // android.hardware.location.ISignificantPlaceProvider
        public void onSignificantPlaceCheck() throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISignificantPlaceProvider {
        static final int TRANSACTION_onSignificantPlaceCheck = 2;
        static final int TRANSACTION_setSignificantPlaceProviderManager = 1;

        public Stub() {
            attachInterface(this, ISignificantPlaceProvider.DESCRIPTOR);
        }

        public static ISignificantPlaceProvider asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISignificantPlaceProvider.DESCRIPTOR);
            if (iin != null && (iin instanceof ISignificantPlaceProvider)) {
                return (ISignificantPlaceProvider) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "setSignificantPlaceProviderManager";
                case 2:
                    return "onSignificantPlaceCheck";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISignificantPlaceProvider.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISignificantPlaceProvider.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    ISignificantPlaceProviderManager _arg0 =
                            ISignificantPlaceProviderManager.Stub.asInterface(
                                    data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setSignificantPlaceProviderManager(_arg0);
                    return true;
                case 2:
                    onSignificantPlaceCheck();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISignificantPlaceProvider {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISignificantPlaceProvider.DESCRIPTOR;
            }

            @Override // android.hardware.location.ISignificantPlaceProvider
            public void setSignificantPlaceProviderManager(ISignificantPlaceProviderManager manager)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISignificantPlaceProvider.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.location.ISignificantPlaceProvider
            public void onSignificantPlaceCheck() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISignificantPlaceProvider.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
