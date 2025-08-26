package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISelectedNbIotSatelliteSubscriptionCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISelectedNbIotSatelliteSubscriptionCallback";

    public static class Default implements ISelectedNbIotSatelliteSubscriptionCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.ISelectedNbIotSatelliteSubscriptionCallback
        public void onSelectedNbIotSatelliteSubscriptionChanged(int i) throws RemoteException {
        }
    }

    void onSelectedNbIotSatelliteSubscriptionChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISelectedNbIotSatelliteSubscriptionCallback {
        static final int TRANSACTION_onSelectedNbIotSatelliteSubscriptionChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISelectedNbIotSatelliteSubscriptionCallback.DESCRIPTOR);
        }

        public static ISelectedNbIotSatelliteSubscriptionCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISelectedNbIotSatelliteSubscriptionCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISelectedNbIotSatelliteSubscriptionCallback)) {
                return (ISelectedNbIotSatelliteSubscriptionCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSelectedNbIotSatelliteSubscriptionChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISelectedNbIotSatelliteSubscriptionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISelectedNbIotSatelliteSubscriptionCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSelectedNbIotSatelliteSubscriptionChanged(i3);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISelectedNbIotSatelliteSubscriptionCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISelectedNbIotSatelliteSubscriptionCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISelectedNbIotSatelliteSubscriptionCallback
            public void onSelectedNbIotSatelliteSubscriptionChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISelectedNbIotSatelliteSubscriptionCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
