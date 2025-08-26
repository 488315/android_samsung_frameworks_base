package android.hardware.location;

import android.hardware.location.ISignificantPlaceProviderManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISignificantPlaceProvider extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.location.ISignificantPlaceProvider";

    public static class Default implements ISignificantPlaceProvider {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.ISignificantPlaceProvider
        public void onSignificantPlaceCheck() throws RemoteException {
        }

        @Override // android.hardware.location.ISignificantPlaceProvider
        public void setSignificantPlaceProviderManager(ISignificantPlaceProviderManager iSignificantPlaceProviderManager) throws RemoteException {
        }
    }

    void onSignificantPlaceCheck() throws RemoteException;

    void setSignificantPlaceProviderManager(ISignificantPlaceProviderManager iSignificantPlaceProviderManager) throws RemoteException;

    public static abstract class Stub extends Binder implements ISignificantPlaceProvider {
        static final int TRANSACTION_onSignificantPlaceCheck = 2;
        static final int TRANSACTION_setSignificantPlaceProviderManager = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISignificantPlaceProvider.DESCRIPTOR);
        }

        public static ISignificantPlaceProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISignificantPlaceProvider.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISignificantPlaceProvider)) {
                return (ISignificantPlaceProvider) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setSignificantPlaceProviderManager";
            }
            if (i != 2) {
                return null;
            }
            return "onSignificantPlaceCheck";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISignificantPlaceProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISignificantPlaceProvider.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ISignificantPlaceProviderManager iSignificantPlaceProviderManagerAsInterface = ISignificantPlaceProviderManager.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setSignificantPlaceProviderManager(iSignificantPlaceProviderManagerAsInterface);
            } else if (i == 2) {
                onSignificantPlaceCheck();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISignificantPlaceProvider {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISignificantPlaceProvider.DESCRIPTOR;
            }

            @Override // android.hardware.location.ISignificantPlaceProvider
            public void setSignificantPlaceProviderManager(ISignificantPlaceProviderManager iSignificantPlaceProviderManager) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISignificantPlaceProvider.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSignificantPlaceProviderManager);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.location.ISignificantPlaceProvider
            public void onSignificantPlaceCheck() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISignificantPlaceProvider.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
