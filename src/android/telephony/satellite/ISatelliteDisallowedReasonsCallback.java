package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteDisallowedReasonsCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteDisallowedReasonsCallback";

    public static class Default implements ISatelliteDisallowedReasonsCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.ISatelliteDisallowedReasonsCallback
        public void onSatelliteDisallowedReasonsChanged(int[] iArr) throws RemoteException {
        }
    }

    void onSatelliteDisallowedReasonsChanged(int[] iArr) throws RemoteException;

    public static abstract class Stub extends Binder implements ISatelliteDisallowedReasonsCallback {
        static final int TRANSACTION_onSatelliteDisallowedReasonsChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
        }

        public static ISatelliteDisallowedReasonsCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISatelliteDisallowedReasonsCallback)) {
                return (ISatelliteDisallowedReasonsCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSatelliteDisallowedReasonsChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int[] createIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onSatelliteDisallowedReasonsChanged(createIntArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISatelliteDisallowedReasonsCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteDisallowedReasonsCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteDisallowedReasonsCallback
            public void onSatelliteDisallowedReasonsChanged(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatelliteDisallowedReasonsCallback.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
