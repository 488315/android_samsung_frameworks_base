package android.telephony.satellite.stub;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.IBooleanConsumer;
import android.telephony.IIntegerConsumer;
import android.telephony.satellite.stub.INtnSignalStrengthConsumer;
import android.telephony.satellite.stub.ISatelliteCapabilitiesConsumer;
import android.telephony.satellite.stub.ISatelliteListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ISatellite extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.satellite.stub.ISatellite";

    public static class Default implements ISatellite {
        @Override // android.telephony.satellite.stub.ISatellite
        public void abortSendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void pollPendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabled(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteEnabledForCarrier(int i, IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestIsSatelliteSupported(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteCapabilities(IIntegerConsumer iIntegerConsumer, ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteEnabled(SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteListeningEnabled(boolean z, int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSatelliteModemState(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestSignalStrength(IIntegerConsumer iIntegerConsumer, INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void requestTimeForNextSatelliteVisibility(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void sendSatelliteDatagram(SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteEnabledForCarrier(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatelliteListener(ISatelliteListener iSatelliteListener) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void setSatellitePlmn(int i, List<String> list, List<String> list2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void startSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void stopSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void updateSatelliteSubscription(String str, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }

        @Override // android.telephony.satellite.stub.ISatellite
        public void updateSystemSelectionChannels(List<SystemSelectionSpecifier> list, IIntegerConsumer iIntegerConsumer) throws RemoteException {
        }
    }

    void abortSendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void pollPendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void requestIsSatelliteEnabled(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException;

    void requestIsSatelliteEnabledForCarrier(int i, IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException;

    void requestIsSatelliteSupported(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException;

    void requestSatelliteCapabilities(IIntegerConsumer iIntegerConsumer, ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) throws RemoteException;

    void requestSatelliteEnabled(SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void requestSatelliteListeningEnabled(boolean z, int i, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void requestSatelliteModemState(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException;

    void requestSignalStrength(IIntegerConsumer iIntegerConsumer, INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) throws RemoteException;

    void requestTimeForNextSatelliteVisibility(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException;

    void sendSatelliteDatagram(SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void setSatelliteEnabledForCarrier(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void setSatelliteListener(ISatelliteListener iSatelliteListener) throws RemoteException;

    void setSatellitePlmn(int i, List<String> list, List<String> list2, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void startSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void startSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void stopSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void stopSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void updateSatelliteSubscription(String str, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    void updateSystemSelectionChannels(List<SystemSelectionSpecifier> list, IIntegerConsumer iIntegerConsumer) throws RemoteException;

    public static abstract class Stub extends Binder implements ISatellite {
        static final int TRANSACTION_abortSendingSatelliteDatagrams = 20;
        static final int TRANSACTION_enableTerrestrialNetworkScanWhileSatelliteModeIsOn = 3;
        static final int TRANSACTION_pollPendingSatelliteDatagrams = 10;
        static final int TRANSACTION_requestIsSatelliteEnabled = 5;
        static final int TRANSACTION_requestIsSatelliteEnabledForCarrier = 16;
        static final int TRANSACTION_requestIsSatelliteSupported = 6;
        static final int TRANSACTION_requestSatelliteCapabilities = 7;
        static final int TRANSACTION_requestSatelliteEnabled = 4;
        static final int TRANSACTION_requestSatelliteListeningEnabled = 2;
        static final int TRANSACTION_requestSatelliteModemState = 12;
        static final int TRANSACTION_requestSignalStrength = 17;
        static final int TRANSACTION_requestTimeForNextSatelliteVisibility = 13;
        static final int TRANSACTION_sendSatelliteDatagram = 11;
        static final int TRANSACTION_setSatelliteEnabledForCarrier = 15;
        static final int TRANSACTION_setSatelliteListener = 1;
        static final int TRANSACTION_setSatellitePlmn = 14;
        static final int TRANSACTION_startSendingNtnSignalStrength = 18;
        static final int TRANSACTION_startSendingSatellitePointingInfo = 8;
        static final int TRANSACTION_stopSendingNtnSignalStrength = 19;
        static final int TRANSACTION_stopSendingSatellitePointingInfo = 9;
        static final int TRANSACTION_updateSatelliteSubscription = 21;
        static final int TRANSACTION_updateSystemSelectionChannels = 22;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }

        public Stub() {
            attachInterface(this, ISatellite.DESCRIPTOR);
        }

        public static ISatellite asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISatellite.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISatellite)) {
                return (ISatellite) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setSatelliteListener";
                case 2:
                    return "requestSatelliteListeningEnabled";
                case 3:
                    return "enableTerrestrialNetworkScanWhileSatelliteModeIsOn";
                case 4:
                    return "requestSatelliteEnabled";
                case 5:
                    return "requestIsSatelliteEnabled";
                case 6:
                    return "requestIsSatelliteSupported";
                case 7:
                    return "requestSatelliteCapabilities";
                case 8:
                    return "startSendingSatellitePointingInfo";
                case 9:
                    return "stopSendingSatellitePointingInfo";
                case 10:
                    return "pollPendingSatelliteDatagrams";
                case 11:
                    return "sendSatelliteDatagram";
                case 12:
                    return "requestSatelliteModemState";
                case 13:
                    return "requestTimeForNextSatelliteVisibility";
                case 14:
                    return "setSatellitePlmn";
                case 15:
                    return "setSatelliteEnabledForCarrier";
                case 16:
                    return "requestIsSatelliteEnabledForCarrier";
                case 17:
                    return "requestSignalStrength";
                case 18:
                    return "startSendingNtnSignalStrength";
                case 19:
                    return "stopSendingNtnSignalStrength";
                case 20:
                    return "abortSendingSatelliteDatagrams";
                case 21:
                    return "updateSatelliteSubscription";
                case 22:
                    return "updateSystemSelectionChannels";
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
                parcel.enforceInterface(ISatellite.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISatellite.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ISatelliteListener asInterface = ISatelliteListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSatelliteListener(asInterface);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    IIntegerConsumer asInterface2 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteListeningEnabled(readBoolean, readInt, asInterface2);
                    return true;
                case 3:
                    boolean readBoolean2 = parcel.readBoolean();
                    IIntegerConsumer asInterface3 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableTerrestrialNetworkScanWhileSatelliteModeIsOn(readBoolean2, asInterface3);
                    return true;
                case 4:
                    SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes = (SatelliteModemEnableRequestAttributes) parcel.readTypedObject(SatelliteModemEnableRequestAttributes.CREATOR);
                    IIntegerConsumer asInterface4 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteEnabled(satelliteModemEnableRequestAttributes, asInterface4);
                    return true;
                case 5:
                    IIntegerConsumer asInterface5 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IBooleanConsumer asInterface6 = IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabled(asInterface5, asInterface6);
                    return true;
                case 6:
                    IIntegerConsumer asInterface7 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IBooleanConsumer asInterface8 = IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteSupported(asInterface7, asInterface8);
                    return true;
                case 7:
                    IIntegerConsumer asInterface9 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    ISatelliteCapabilitiesConsumer asInterface10 = ISatelliteCapabilitiesConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteCapabilities(asInterface9, asInterface10);
                    return true;
                case 8:
                    IIntegerConsumer asInterface11 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSendingSatellitePointingInfo(asInterface11);
                    return true;
                case 9:
                    IIntegerConsumer asInterface12 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopSendingSatellitePointingInfo(asInterface12);
                    return true;
                case 10:
                    IIntegerConsumer asInterface13 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    pollPendingSatelliteDatagrams(asInterface13);
                    return true;
                case 11:
                    SatelliteDatagram satelliteDatagram = (SatelliteDatagram) parcel.readTypedObject(SatelliteDatagram.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    IIntegerConsumer asInterface14 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendSatelliteDatagram(satelliteDatagram, readBoolean3, asInterface14);
                    return true;
                case 12:
                    IIntegerConsumer asInterface15 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IIntegerConsumer asInterface16 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteModemState(asInterface15, asInterface16);
                    return true;
                case 13:
                    IIntegerConsumer asInterface17 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IIntegerConsumer asInterface18 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestTimeForNextSatelliteVisibility(asInterface17, asInterface18);
                    return true;
                case 14:
                    int readInt2 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    IIntegerConsumer asInterface19 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSatellitePlmn(readInt2, createStringArrayList, createStringArrayList2, asInterface19);
                    return true;
                case 15:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    IIntegerConsumer asInterface20 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSatelliteEnabledForCarrier(readInt3, readBoolean4, asInterface20);
                    return true;
                case 16:
                    int readInt4 = parcel.readInt();
                    IIntegerConsumer asInterface21 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IBooleanConsumer asInterface22 = IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabledForCarrier(readInt4, asInterface21, asInterface22);
                    return true;
                case 17:
                    IIntegerConsumer asInterface23 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    INtnSignalStrengthConsumer asInterface24 = INtnSignalStrengthConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSignalStrength(asInterface23, asInterface24);
                    return true;
                case 18:
                    IIntegerConsumer asInterface25 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSendingNtnSignalStrength(asInterface25);
                    return true;
                case 19:
                    IIntegerConsumer asInterface26 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopSendingNtnSignalStrength(asInterface26);
                    return true;
                case 20:
                    IIntegerConsumer asInterface27 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    abortSendingSatelliteDatagrams(asInterface27);
                    return true;
                case 21:
                    String readString = parcel.readString();
                    IIntegerConsumer asInterface28 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateSatelliteSubscription(readString, asInterface28);
                    return true;
                case 22:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(SystemSelectionSpecifier.CREATOR);
                    IIntegerConsumer asInterface29 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateSystemSelectionChannels(createTypedArrayList, asInterface29);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISatellite {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISatellite.DESCRIPTOR;
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void setSatelliteListener(ISatelliteListener iSatelliteListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iSatelliteListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteListeningEnabled(boolean z, int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteEnabled(SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeTypedObject(satelliteModemEnableRequestAttributes, 0);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteEnabled(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteSupported(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteCapabilities(IIntegerConsumer iIntegerConsumer, ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iSatelliteCapabilitiesConsumer);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void startSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void stopSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void pollPendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void sendSatelliteDatagram(SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeTypedObject(satelliteDatagram, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteModemState(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iIntegerConsumer2);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestTimeForNextSatelliteVisibility(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iIntegerConsumer2);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void setSatellitePlmn(int i, List<String> list, List<String> list2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeStringList(list2);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void setSatelliteEnabledForCarrier(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteEnabledForCarrier(int i, IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSignalStrength(IIntegerConsumer iIntegerConsumer, INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    obtain.writeStrongInterface(iNtnSignalStrengthConsumer);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void startSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void stopSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void abortSendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void updateSatelliteSubscription(String str, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void updateSystemSelectionChannels(List<SystemSelectionSpecifier> list, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
