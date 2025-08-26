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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISatellite.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISatellite)) {
                return (ISatellite) iInterfaceQueryLocalInterface;
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
                    ISatelliteListener iSatelliteListenerAsInterface = ISatelliteListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSatelliteListener(iSatelliteListenerAsInterface);
                    return true;
                case 2:
                    boolean z = parcel.readBoolean();
                    int i3 = parcel.readInt();
                    IIntegerConsumer iIntegerConsumerAsInterface = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteListeningEnabled(z, i3, iIntegerConsumerAsInterface);
                    return true;
                case 3:
                    boolean z2 = parcel.readBoolean();
                    IIntegerConsumer iIntegerConsumerAsInterface2 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableTerrestrialNetworkScanWhileSatelliteModeIsOn(z2, iIntegerConsumerAsInterface2);
                    return true;
                case 4:
                    SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes = (SatelliteModemEnableRequestAttributes) parcel.readTypedObject(SatelliteModemEnableRequestAttributes.CREATOR);
                    IIntegerConsumer iIntegerConsumerAsInterface3 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteEnabled(satelliteModemEnableRequestAttributes, iIntegerConsumerAsInterface3);
                    return true;
                case 5:
                    IIntegerConsumer iIntegerConsumerAsInterface4 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IBooleanConsumer iBooleanConsumerAsInterface = IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabled(iIntegerConsumerAsInterface4, iBooleanConsumerAsInterface);
                    return true;
                case 6:
                    IIntegerConsumer iIntegerConsumerAsInterface5 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IBooleanConsumer iBooleanConsumerAsInterface2 = IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteSupported(iIntegerConsumerAsInterface5, iBooleanConsumerAsInterface2);
                    return true;
                case 7:
                    IIntegerConsumer iIntegerConsumerAsInterface6 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumerAsInterface = ISatelliteCapabilitiesConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteCapabilities(iIntegerConsumerAsInterface6, iSatelliteCapabilitiesConsumerAsInterface);
                    return true;
                case 8:
                    IIntegerConsumer iIntegerConsumerAsInterface7 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSendingSatellitePointingInfo(iIntegerConsumerAsInterface7);
                    return true;
                case 9:
                    IIntegerConsumer iIntegerConsumerAsInterface8 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopSendingSatellitePointingInfo(iIntegerConsumerAsInterface8);
                    return true;
                case 10:
                    IIntegerConsumer iIntegerConsumerAsInterface9 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    pollPendingSatelliteDatagrams(iIntegerConsumerAsInterface9);
                    return true;
                case 11:
                    SatelliteDatagram satelliteDatagram = (SatelliteDatagram) parcel.readTypedObject(SatelliteDatagram.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    IIntegerConsumer iIntegerConsumerAsInterface10 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendSatelliteDatagram(satelliteDatagram, z3, iIntegerConsumerAsInterface10);
                    return true;
                case 12:
                    IIntegerConsumer iIntegerConsumerAsInterface11 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IIntegerConsumer iIntegerConsumerAsInterface12 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSatelliteModemState(iIntegerConsumerAsInterface11, iIntegerConsumerAsInterface12);
                    return true;
                case 13:
                    IIntegerConsumer iIntegerConsumerAsInterface13 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IIntegerConsumer iIntegerConsumerAsInterface14 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestTimeForNextSatelliteVisibility(iIntegerConsumerAsInterface13, iIntegerConsumerAsInterface14);
                    return true;
                case 14:
                    int i4 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    IIntegerConsumer iIntegerConsumerAsInterface15 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSatellitePlmn(i4, arrayListCreateStringArrayList, arrayListCreateStringArrayList2, iIntegerConsumerAsInterface15);
                    return true;
                case 15:
                    int i5 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    IIntegerConsumer iIntegerConsumerAsInterface16 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSatelliteEnabledForCarrier(i5, z4, iIntegerConsumerAsInterface16);
                    return true;
                case 16:
                    int i6 = parcel.readInt();
                    IIntegerConsumer iIntegerConsumerAsInterface17 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    IBooleanConsumer iBooleanConsumerAsInterface3 = IBooleanConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestIsSatelliteEnabledForCarrier(i6, iIntegerConsumerAsInterface17, iBooleanConsumerAsInterface3);
                    return true;
                case 17:
                    IIntegerConsumer iIntegerConsumerAsInterface18 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    INtnSignalStrengthConsumer iNtnSignalStrengthConsumerAsInterface = INtnSignalStrengthConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestSignalStrength(iIntegerConsumerAsInterface18, iNtnSignalStrengthConsumerAsInterface);
                    return true;
                case 18:
                    IIntegerConsumer iIntegerConsumerAsInterface19 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSendingNtnSignalStrength(iIntegerConsumerAsInterface19);
                    return true;
                case 19:
                    IIntegerConsumer iIntegerConsumerAsInterface20 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    stopSendingNtnSignalStrength(iIntegerConsumerAsInterface20);
                    return true;
                case 20:
                    IIntegerConsumer iIntegerConsumerAsInterface21 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    abortSendingSatelliteDatagrams(iIntegerConsumerAsInterface21);
                    return true;
                case 21:
                    String string = parcel.readString();
                    IIntegerConsumer iIntegerConsumerAsInterface22 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateSatelliteSubscription(string, iIntegerConsumerAsInterface22);
                    return true;
                case 22:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SystemSelectionSpecifier.CREATOR);
                    IIntegerConsumer iIntegerConsumerAsInterface23 = IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    updateSystemSelectionChannels(arrayListCreateTypedArrayList, iIntegerConsumerAsInterface23);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSatelliteListener);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteListeningEnabled(boolean z, int i, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void enableTerrestrialNetworkScanWhileSatelliteModeIsOn(boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteEnabled(SatelliteModemEnableRequestAttributes satelliteModemEnableRequestAttributes, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeTypedObject(satelliteModemEnableRequestAttributes, 0);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteEnabled(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteSupported(IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteCapabilities(IIntegerConsumer iIntegerConsumer, ISatelliteCapabilitiesConsumer iSatelliteCapabilitiesConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeStrongInterface(iSatelliteCapabilitiesConsumer);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void startSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void stopSendingSatellitePointingInfo(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void pollPendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void sendSatelliteDatagram(SatelliteDatagram satelliteDatagram, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeTypedObject(satelliteDatagram, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSatelliteModemState(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeStrongInterface(iIntegerConsumer2);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestTimeForNextSatelliteVisibility(IIntegerConsumer iIntegerConsumer, IIntegerConsumer iIntegerConsumer2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeStrongInterface(iIntegerConsumer2);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void setSatellitePlmn(int i, List<String> list, List<String> list2, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeStringList(list2);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void setSatelliteEnabledForCarrier(int i, boolean z, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestIsSatelliteEnabledForCarrier(int i, IIntegerConsumer iIntegerConsumer, IBooleanConsumer iBooleanConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeStrongInterface(iBooleanConsumer);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void requestSignalStrength(IIntegerConsumer iIntegerConsumer, INtnSignalStrengthConsumer iNtnSignalStrengthConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    parcelObtain.writeStrongInterface(iNtnSignalStrengthConsumer);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void startSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void stopSendingNtnSignalStrength(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void abortSendingSatelliteDatagrams(IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void updateSatelliteSubscription(String str, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.satellite.stub.ISatellite
            public void updateSystemSelectionChannels(List<SystemSelectionSpecifier> list, IIntegerConsumer iIntegerConsumer) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISatellite.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
