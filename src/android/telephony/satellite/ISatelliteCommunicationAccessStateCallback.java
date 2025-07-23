package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteCommunicationAccessStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteCommunicationAccessStateCallback";

    public static class Default implements ISatelliteCommunicationAccessStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.ISatelliteCommunicationAccessStateCallback
        public void onAccessAllowedStateChanged(boolean z) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteCommunicationAccessStateCallback
        public void onAccessConfigurationChanged(SatelliteAccessConfiguration satelliteAccessConfiguration) throws RemoteException {
        }
    }

    void onAccessAllowedStateChanged(boolean z) throws RemoteException;

    void onAccessConfigurationChanged(SatelliteAccessConfiguration satelliteAccessConfiguration) throws RemoteException;

    public static abstract class Stub extends Binder implements ISatelliteCommunicationAccessStateCallback {
        static final int TRANSACTION_onAccessAllowedStateChanged = 1;
        static final int TRANSACTION_onAccessConfigurationChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISatelliteCommunicationAccessStateCallback.DESCRIPTOR);
        }

        public static ISatelliteCommunicationAccessStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISatelliteCommunicationAccessStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISatelliteCommunicationAccessStateCallback)) {
                return (ISatelliteCommunicationAccessStateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onAccessAllowedStateChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onAccessConfigurationChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISatelliteCommunicationAccessStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISatelliteCommunicationAccessStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onAccessAllowedStateChanged(readBoolean);
            } else if (i == 2) {
                SatelliteAccessConfiguration satelliteAccessConfiguration = (SatelliteAccessConfiguration) parcel.readTypedObject(SatelliteAccessConfiguration.CREATOR);
                parcel.enforceNoDataAvail();
                onAccessConfigurationChanged(satelliteAccessConfiguration);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISatelliteCommunicationAccessStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteCommunicationAccessStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteCommunicationAccessStateCallback
            public void onAccessAllowedStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatelliteCommunicationAccessStateCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteCommunicationAccessStateCallback
            public void onAccessConfigurationChanged(SatelliteAccessConfiguration satelliteAccessConfiguration) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatelliteCommunicationAccessStateCallback.DESCRIPTOR);
                    obtain.writeTypedObject(satelliteAccessConfiguration, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
