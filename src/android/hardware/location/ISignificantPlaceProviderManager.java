package android.hardware.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISignificantPlaceProviderManager extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.location.ISignificantPlaceProviderManager";

    public static class Default implements ISignificantPlaceProviderManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.ISignificantPlaceProviderManager
        public void setInSignificantPlace(boolean z) throws RemoteException {
        }
    }

    void setInSignificantPlace(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISignificantPlaceProviderManager {
        static final int TRANSACTION_setInSignificantPlace = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISignificantPlaceProviderManager.DESCRIPTOR);
        }

        public static ISignificantPlaceProviderManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISignificantPlaceProviderManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISignificantPlaceProviderManager)) {
                return (ISignificantPlaceProviderManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "setInSignificantPlace";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISignificantPlaceProviderManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISignificantPlaceProviderManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                setInSignificantPlace(z);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISignificantPlaceProviderManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISignificantPlaceProviderManager.DESCRIPTOR;
            }

            @Override // android.hardware.location.ISignificantPlaceProviderManager
            public void setInSignificantPlace(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISignificantPlaceProviderManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
