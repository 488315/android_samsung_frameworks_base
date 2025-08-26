package android.telephony.satellite.stub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISatelliteListener extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.stub.ISatelliteListener";

    public static class Default implements ISatelliteListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onPendingDatagrams() throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onRegistrationFailure(int i) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatelliteCapabilitiesChanged(SatelliteCapabilities satelliteCapabilities) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatelliteDatagramReceived(SatelliteDatagram satelliteDatagram, int i) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatelliteModemStateChanged(int i) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatellitePositionChanged(PointingInfo pointingInfo) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onSatelliteSupportedStateChanged(boolean z) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatelliteListener
        public void onTerrestrialNetworkAvailableChanged(boolean z) throws RemoteException {
        }
    }

    void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException;

    void onPendingDatagrams() throws RemoteException;

    void onRegistrationFailure(int i) throws RemoteException;

    void onSatelliteCapabilitiesChanged(SatelliteCapabilities satelliteCapabilities) throws RemoteException;

    void onSatelliteDatagramReceived(SatelliteDatagram satelliteDatagram, int i) throws RemoteException;

    void onSatelliteModemStateChanged(int i) throws RemoteException;

    void onSatellitePositionChanged(PointingInfo pointingInfo) throws RemoteException;

    void onSatelliteSupportedStateChanged(boolean z) throws RemoteException;

    void onTerrestrialNetworkAvailableChanged(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISatelliteListener {
        static final int TRANSACTION_onNtnSignalStrengthChanged = 5;
        static final int TRANSACTION_onPendingDatagrams = 2;
        static final int TRANSACTION_onRegistrationFailure = 8;
        static final int TRANSACTION_onSatelliteCapabilitiesChanged = 6;
        static final int TRANSACTION_onSatelliteDatagramReceived = 1;
        static final int TRANSACTION_onSatelliteModemStateChanged = 4;
        static final int TRANSACTION_onSatellitePositionChanged = 3;
        static final int TRANSACTION_onSatelliteSupportedStateChanged = 7;
        static final int TRANSACTION_onTerrestrialNetworkAvailableChanged = 9;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ISatelliteListener.DESCRIPTOR);
        }

        public static ISatelliteListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISatelliteListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISatelliteListener)) {
                return (ISatelliteListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSatelliteDatagramReceived";
                case 2:
                    return "onPendingDatagrams";
                case 3:
                    return "onSatellitePositionChanged";
                case 4:
                    return "onSatelliteModemStateChanged";
                case 5:
                    return "onNtnSignalStrengthChanged";
                case 6:
                    return "onSatelliteCapabilitiesChanged";
                case 7:
                    return "onSatelliteSupportedStateChanged";
                case 8:
                    return "onRegistrationFailure";
                case 9:
                    return "onTerrestrialNetworkAvailableChanged";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISatelliteListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISatelliteListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    SatelliteDatagram satelliteDatagram = (SatelliteDatagram) parcel.readTypedObject(SatelliteDatagram.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSatelliteDatagramReceived(satelliteDatagram, i3);
                    return true;
                case 2:
                    onPendingDatagrams();
                    return true;
                case 3:
                    PointingInfo pointingInfo = (PointingInfo) parcel.readTypedObject(PointingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSatellitePositionChanged(pointingInfo);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSatelliteModemStateChanged(i4);
                    return true;
                case 5:
                    NtnSignalStrength ntnSignalStrength = (NtnSignalStrength) parcel.readTypedObject(NtnSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    onNtnSignalStrengthChanged(ntnSignalStrength);
                    return true;
                case 6:
                    SatelliteCapabilities satelliteCapabilities = (SatelliteCapabilities) parcel.readTypedObject(SatelliteCapabilities.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSatelliteCapabilitiesChanged(satelliteCapabilities);
                    return true;
                case 7:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSatelliteSupportedStateChanged(z);
                    return true;
                case 8:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRegistrationFailure(i5);
                    return true;
                case 9:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTerrestrialNetworkAvailableChanged(z2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISatelliteListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatelliteListener.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatelliteDatagramReceived(SatelliteDatagram satelliteDatagram, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(satelliteDatagram, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onPendingDatagrams() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteListener.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatellitePositionChanged(PointingInfo pointingInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pointingInfo, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatelliteModemStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(ntnSignalStrength, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatelliteCapabilitiesChanged(SatelliteCapabilities satelliteCapabilities) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteListener.DESCRIPTOR);
                    parcelObtain.writeTypedObject(satelliteCapabilities, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onSatelliteSupportedStateChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onRegistrationFailure(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatelliteListener
            public void onTerrestrialNetworkAvailableChanged(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatelliteListener.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
