package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteCapabilitiesCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteCapabilitiesCallback";

    public static class Default implements ISatelliteCapabilitiesCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.ISatelliteCapabilitiesCallback
        public void onSatelliteCapabilitiesChanged(SatelliteCapabilities satelliteCapabilities) throws RemoteException {
        }
    }

    void onSatelliteCapabilitiesChanged(SatelliteCapabilities satelliteCapabilities) throws RemoteException;

    public static abstract class Stub extends Binder implements ISatelliteCapabilitiesCallback {
        static final int TRANSACTION_onSatelliteCapabilitiesChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISatelliteCapabilitiesCallback.DESCRIPTOR);
        }

        public static ISatelliteCapabilitiesCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISatelliteCapabilitiesCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISatelliteCapabilitiesCallback)) {
                return (ISatelliteCapabilitiesCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSatelliteCapabilitiesChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISatelliteCapabilitiesCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISatelliteCapabilitiesCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SatelliteCapabilities satelliteCapabilities = (SatelliteCapabilities) parcel.readTypedObject(SatelliteCapabilities.CREATOR);
                parcel.enforceNoDataAvail();
                onSatelliteCapabilitiesChanged(satelliteCapabilities);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISatelliteCapabilitiesCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteCapabilitiesCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteCapabilitiesCallback
            public void onSatelliteCapabilitiesChanged(SatelliteCapabilities satelliteCapabilities) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatelliteCapabilitiesCallback.DESCRIPTOR);
                    obtain.writeTypedObject(satelliteCapabilities, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
