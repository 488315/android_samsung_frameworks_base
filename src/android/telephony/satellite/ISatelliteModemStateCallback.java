package android.telephony.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteModemStateCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.ISatelliteModemStateCallback";

    public static class Default implements ISatelliteModemStateCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onEmergencyModeChanged(boolean z) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onRegistrationFailure(int i) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onSatelliteModemStateChanged(int i) throws RemoteException {
        }

        @Override // android.telephony.satellite.ISatelliteModemStateCallback
        public void onTerrestrialNetworkAvailableChanged(boolean z) throws RemoteException {
        }
    }

    void onEmergencyModeChanged(boolean z) throws RemoteException;

    void onRegistrationFailure(int i) throws RemoteException;

    void onSatelliteModemStateChanged(int i) throws RemoteException;

    void onTerrestrialNetworkAvailableChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISatelliteModemStateCallback {
        static final int TRANSACTION_onEmergencyModeChanged = 2;
        static final int TRANSACTION_onRegistrationFailure = 3;
        static final int TRANSACTION_onSatelliteModemStateChanged = 1;
        static final int TRANSACTION_onTerrestrialNetworkAvailableChanged = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ISatelliteModemStateCallback.DESCRIPTOR);
        }

        public static ISatelliteModemStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISatelliteModemStateCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISatelliteModemStateCallback)) {
                return (ISatelliteModemStateCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSatelliteModemStateChanged";
            }
            if (i == 2) {
                return "onEmergencyModeChanged";
            }
            if (i == 3) {
                return "onRegistrationFailure";
            }
            if (i != 4) {
                return null;
            }
            return "onTerrestrialNetworkAvailableChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISatelliteModemStateCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISatelliteModemStateCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSatelliteModemStateChanged(readInt);
            } else if (i == 2) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onEmergencyModeChanged(readBoolean);
            } else if (i == 3) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onRegistrationFailure(readInt2);
            } else if (i == 4) {
                boolean readBoolean2 = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onTerrestrialNetworkAvailableChanged(readBoolean2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISatelliteModemStateCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteModemStateCallback.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.ISatelliteModemStateCallback
            public void onSatelliteModemStateChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatelliteModemStateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteModemStateCallback
            public void onEmergencyModeChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatelliteModemStateCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteModemStateCallback
            public void onRegistrationFailure(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatelliteModemStateCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.ISatelliteModemStateCallback
            public void onTerrestrialNetworkAvailableChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatelliteModemStateCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
