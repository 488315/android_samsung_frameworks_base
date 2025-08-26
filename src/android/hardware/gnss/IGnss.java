package android.hardware.gnss;

import android.hardware.gnss.IAGnss;
import android.hardware.gnss.IAGnssRil;
import android.hardware.gnss.IGnssAntennaInfo;
import android.hardware.gnss.IGnssBatching;
import android.hardware.gnss.IGnssCallback;
import android.hardware.gnss.IGnssConfiguration;
import android.hardware.gnss.IGnssDebug;
import android.hardware.gnss.IGnssGeofence;
import android.hardware.gnss.IGnssMeasurementInterface;
import android.hardware.gnss.IGnssNavigationMessageInterface;
import android.hardware.gnss.IGnssPowerIndication;
import android.hardware.gnss.IGnssPsds;
import android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsInterface;
import android.hardware.gnss.visibility_control.IGnssVisibilityControl;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnss extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$gnss$IGnss".replace('$', '.');
    public static final int ERROR_ALREADY_INIT = 2;
    public static final int ERROR_GENERIC = 3;
    public static final int ERROR_INVALID_ARGUMENT = 1;
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    public @interface GnssAidingData {
        public static final int ALL = 65535;
        public static final int ALMANAC = 2;
        public static final int CELLDB_INFO = 32768;
        public static final int EPHEMERIS = 1;
        public static final int HEALTH = 64;
        public static final int IONO = 16;
        public static final int POSITION = 4;
        public static final int RTI = 1024;
        public static final int SADATA = 512;
        public static final int SVDIR = 128;
        public static final int SVSTEER = 256;
        public static final int TIME = 8;
        public static final int UTC = 32;
    }

    public @interface GnssPositionMode {
        public static final int MS_ASSISTED = 2;
        public static final int MS_BASED = 1;
        public static final int STANDALONE = 0;
    }

    public @interface GnssPositionRecurrence {
        public static final int RECURRENCE_PERIODIC = 0;
        public static final int RECURRENCE_SINGLE = 1;
    }

    void close() throws RemoteException;

    void deleteAidingData(int i) throws RemoteException;

    IAGnss getExtensionAGnss() throws RemoteException;

    IAGnssRil getExtensionAGnssRil() throws RemoteException;

    IGnssAntennaInfo getExtensionGnssAntennaInfo() throws RemoteException;

    IGnssBatching getExtensionGnssBatching() throws RemoteException;

    IGnssConfiguration getExtensionGnssConfiguration() throws RemoteException;

    IGnssDebug getExtensionGnssDebug() throws RemoteException;

    IGnssGeofence getExtensionGnssGeofence() throws RemoteException;

    IGnssMeasurementInterface getExtensionGnssMeasurement() throws RemoteException;

    IGnssNavigationMessageInterface getExtensionGnssNavigationMessage() throws RemoteException;

    IGnssPowerIndication getExtensionGnssPowerIndication() throws RemoteException;

    IGnssVisibilityControl getExtensionGnssVisibilityControl() throws RemoteException;

    IMeasurementCorrectionsInterface getExtensionMeasurementCorrections() throws RemoteException;

    IGnssPsds getExtensionPsds() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void injectBestLocation(GnssLocation gnssLocation) throws RemoteException;

    void injectLocation(GnssLocation gnssLocation) throws RemoteException;

    void injectTime(long j, long j2, int i) throws RemoteException;

    void setCallback(IGnssCallback iGnssCallback) throws RemoteException;

    void setPositionMode(PositionModeOptions positionModeOptions) throws RemoteException;

    void start() throws RemoteException;

    void startNmea() throws RemoteException;

    void startSvStatus() throws RemoteException;

    void stop() throws RemoteException;

    void stopNmea() throws RemoteException;

    void stopSvStatus() throws RemoteException;

    public static class Default implements IGnss {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public void close() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void deleteAidingData(int i) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public IAGnss getExtensionAGnss() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IAGnssRil getExtensionAGnssRil() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssAntennaInfo getExtensionGnssAntennaInfo() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssBatching getExtensionGnssBatching() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssConfiguration getExtensionGnssConfiguration() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssDebug getExtensionGnssDebug() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssGeofence getExtensionGnssGeofence() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssMeasurementInterface getExtensionGnssMeasurement() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssNavigationMessageInterface getExtensionGnssNavigationMessage() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssPowerIndication getExtensionGnssPowerIndication() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssVisibilityControl getExtensionGnssVisibilityControl() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IMeasurementCorrectionsInterface getExtensionMeasurementCorrections() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public IGnssPsds getExtensionPsds() throws RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnss
        public void injectBestLocation(GnssLocation gnssLocation) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void injectLocation(GnssLocation gnssLocation) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void injectTime(long j, long j2, int i) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void setCallback(IGnssCallback iGnssCallback) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void setPositionMode(PositionModeOptions positionModeOptions) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void start() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void startNmea() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void startSvStatus() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void stop() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void stopNmea() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void stopSvStatus() throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnss {
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_deleteAidingData = 19;
        static final int TRANSACTION_getExtensionAGnss = 10;
        static final int TRANSACTION_getExtensionAGnssRil = 11;
        static final int TRANSACTION_getExtensionGnssAntennaInfo = 21;
        static final int TRANSACTION_getExtensionGnssBatching = 7;
        static final int TRANSACTION_getExtensionGnssConfiguration = 4;
        static final int TRANSACTION_getExtensionGnssDebug = 12;
        static final int TRANSACTION_getExtensionGnssGeofence = 8;
        static final int TRANSACTION_getExtensionGnssMeasurement = 5;
        static final int TRANSACTION_getExtensionGnssNavigationMessage = 9;
        static final int TRANSACTION_getExtensionGnssPowerIndication = 6;
        static final int TRANSACTION_getExtensionGnssVisibilityControl = 13;
        static final int TRANSACTION_getExtensionMeasurementCorrections = 22;
        static final int TRANSACTION_getExtensionPsds = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_injectBestLocation = 18;
        static final int TRANSACTION_injectLocation = 17;
        static final int TRANSACTION_injectTime = 16;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setPositionMode = 20;
        static final int TRANSACTION_start = 14;
        static final int TRANSACTION_startNmea = 25;
        static final int TRANSACTION_startSvStatus = 23;
        static final int TRANSACTION_stop = 15;
        static final int TRANSACTION_stopNmea = 26;
        static final int TRANSACTION_stopSvStatus = 24;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IGnss asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGnss)) {
                return (IGnss) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCallback";
                case 2:
                    return "close";
                case 3:
                    return "getExtensionPsds";
                case 4:
                    return "getExtensionGnssConfiguration";
                case 5:
                    return "getExtensionGnssMeasurement";
                case 6:
                    return "getExtensionGnssPowerIndication";
                case 7:
                    return "getExtensionGnssBatching";
                case 8:
                    return "getExtensionGnssGeofence";
                case 9:
                    return "getExtensionGnssNavigationMessage";
                case 10:
                    return "getExtensionAGnss";
                case 11:
                    return "getExtensionAGnssRil";
                case 12:
                    return "getExtensionGnssDebug";
                case 13:
                    return "getExtensionGnssVisibilityControl";
                case 14:
                    return "start";
                case 15:
                    return "stop";
                case 16:
                    return "injectTime";
                case 17:
                    return "injectLocation";
                case 18:
                    return "injectBestLocation";
                case 19:
                    return "deleteAidingData";
                case 20:
                    return "setPositionMode";
                case 21:
                    return "getExtensionGnssAntennaInfo";
                case 22:
                    return "getExtensionMeasurementCorrections";
                case 23:
                    return "startSvStatus";
                case 24:
                    return "stopSvStatus";
                case 25:
                    return "startNmea";
                case 26:
                    return "stopNmea";
                default:
                    switch (i) {
                        case 16777214:
                            return "getInterfaceHash";
                        case 16777215:
                            return "getInterfaceVersion";
                        default:
                            return null;
                    }
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    IGnssCallback iGnssCallbackAsInterface = IGnssCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(iGnssCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IGnssPsds extensionPsds = getExtensionPsds();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionPsds);
                    return true;
                case 4:
                    IGnssConfiguration extensionGnssConfiguration = getExtensionGnssConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssConfiguration);
                    return true;
                case 5:
                    IGnssMeasurementInterface extensionGnssMeasurement = getExtensionGnssMeasurement();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssMeasurement);
                    return true;
                case 6:
                    IGnssPowerIndication extensionGnssPowerIndication = getExtensionGnssPowerIndication();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssPowerIndication);
                    return true;
                case 7:
                    IGnssBatching extensionGnssBatching = getExtensionGnssBatching();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssBatching);
                    return true;
                case 8:
                    IGnssGeofence extensionGnssGeofence = getExtensionGnssGeofence();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssGeofence);
                    return true;
                case 9:
                    IGnssNavigationMessageInterface extensionGnssNavigationMessage = getExtensionGnssNavigationMessage();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssNavigationMessage);
                    return true;
                case 10:
                    IAGnss extensionAGnss = getExtensionAGnss();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionAGnss);
                    return true;
                case 11:
                    IAGnssRil extensionAGnssRil = getExtensionAGnssRil();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionAGnssRil);
                    return true;
                case 12:
                    IGnssDebug extensionGnssDebug = getExtensionGnssDebug();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssDebug);
                    return true;
                case 13:
                    IGnssVisibilityControl extensionGnssVisibilityControl = getExtensionGnssVisibilityControl();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssVisibilityControl);
                    return true;
                case 14:
                    start();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    injectTime(j, j2, i3);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    GnssLocation gnssLocation = (GnssLocation) parcel.readTypedObject(GnssLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectLocation(gnssLocation);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    GnssLocation gnssLocation2 = (GnssLocation) parcel.readTypedObject(GnssLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectBestLocation(gnssLocation2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteAidingData(i4);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    PositionModeOptions positionModeOptions = (PositionModeOptions) parcel.readTypedObject(PositionModeOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPositionMode(positionModeOptions);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IGnssAntennaInfo extensionGnssAntennaInfo = getExtensionGnssAntennaInfo();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssAntennaInfo);
                    return true;
                case 22:
                    IMeasurementCorrectionsInterface extensionMeasurementCorrections = getExtensionMeasurementCorrections();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionMeasurementCorrections);
                    return true;
                case 23:
                    startSvStatus();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    stopSvStatus();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    startNmea();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    stopNmea();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IGnss {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.gnss.IGnss
            public void setCallback(IGnssCallback iGnssCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGnssCallback);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setCallback is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method close is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssPsds getExtensionPsds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionPsds is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssPsds.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssConfiguration getExtensionGnssConfiguration() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionGnssConfiguration is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssConfiguration.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssMeasurementInterface getExtensionGnssMeasurement() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionGnssMeasurement is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssMeasurementInterface.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssPowerIndication getExtensionGnssPowerIndication() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionGnssPowerIndication is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssPowerIndication.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssBatching getExtensionGnssBatching() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionGnssBatching is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssBatching.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssGeofence getExtensionGnssGeofence() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionGnssGeofence is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssGeofence.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssNavigationMessageInterface getExtensionGnssNavigationMessage() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionGnssNavigationMessage is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssNavigationMessageInterface.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IAGnss getExtensionAGnss() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionAGnss is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IAGnss.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IAGnssRil getExtensionAGnssRil() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionAGnssRil is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IAGnssRil.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssDebug getExtensionGnssDebug() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionGnssDebug is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssDebug.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssVisibilityControl getExtensionGnssVisibilityControl() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionGnssVisibilityControl is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssVisibilityControl.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void start() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method start is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method stop is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void injectTime(long j, long j2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(16, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method injectTime is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void injectLocation(GnssLocation gnssLocation) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(gnssLocation, 0);
                    if (!this.mRemote.transact(17, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method injectLocation is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void injectBestLocation(GnssLocation gnssLocation) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(gnssLocation, 0);
                    if (!this.mRemote.transact(18, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method injectBestLocation is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void deleteAidingData(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (!this.mRemote.transact(19, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method deleteAidingData is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void setPositionMode(PositionModeOptions positionModeOptions) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(positionModeOptions, 0);
                    if (!this.mRemote.transact(20, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setPositionMode is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IGnssAntennaInfo getExtensionGnssAntennaInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(21, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionGnssAntennaInfo is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IGnssAntennaInfo.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public IMeasurementCorrectionsInterface getExtensionMeasurementCorrections() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method getExtensionMeasurementCorrections is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return IMeasurementCorrectionsInterface.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void startSvStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(23, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method startSvStatus is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void stopSvStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(24, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method stopSvStatus is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void startNmea() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(25, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method startNmea is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void stopNmea() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method stopNmea is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.gnss.IGnss
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }

    public static class PositionModeOptions implements Parcelable {
        public static final Parcelable.Creator<PositionModeOptions> CREATOR = new Parcelable.Creator<PositionModeOptions>() { // from class: android.hardware.gnss.IGnss.PositionModeOptions.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PositionModeOptions createFromParcel(Parcel parcel) {
                PositionModeOptions positionModeOptions = new PositionModeOptions();
                positionModeOptions.readFromParcel(parcel);
                return positionModeOptions;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public PositionModeOptions[] newArray(int i) {
                return new PositionModeOptions[i];
            }
        };
        public int mode;
        public int recurrence;
        public int minIntervalMs = 0;
        public int preferredAccuracyMeters = 0;
        public int preferredTimeMs = 0;
        public boolean lowPowerMode = false;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.mode);
            parcel.writeInt(this.recurrence);
            parcel.writeInt(this.minIntervalMs);
            parcel.writeInt(this.preferredAccuracyMeters);
            parcel.writeInt(this.preferredTimeMs);
            parcel.writeBoolean(this.lowPowerMode);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mode = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.recurrence = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.minIntervalMs = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.preferredAccuracyMeters = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.preferredTimeMs = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.lowPowerMode = parcel.readBoolean();
                                        if (iDataPosition > Integer.MAX_VALUE - i) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }
}
