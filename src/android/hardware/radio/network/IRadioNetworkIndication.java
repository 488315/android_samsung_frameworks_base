package android.hardware.radio.network;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioNetworkIndication extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$network$IRadioNetworkIndication".replace('$', '.');
    public static final String HASH = "5867b4f5be491ec815fafea8a3f268b0295427df";
    public static final int VERSION = 4;

    void barringInfoChanged(int i, CellIdentity cellIdentity, BarringInfo[] barringInfoArr) throws RemoteException;

    @Deprecated
    void cdmaPrlChanged(int i, int i2) throws RemoteException;

    void cellInfoList(int i, CellInfo[] cellInfoArr) throws RemoteException;

    void cellularIdentifierDisclosed(int i, CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException;

    void currentLinkCapacityEstimate(int i, LinkCapacityEstimate linkCapacityEstimate) throws RemoteException;

    void currentPhysicalChannelConfigs(int i, PhysicalChannelConfig[] physicalChannelConfigArr) throws RemoteException;

    void currentSignalStrength(int i, SignalStrength signalStrength) throws RemoteException;

    void emergencyNetworkScanResult(int i, EmergencyRegResult emergencyRegResult) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void imsNetworkStateChanged(int i) throws RemoteException;

    void networkScanResult(int i, NetworkScanResult networkScanResult) throws RemoteException;

    void networkStateChanged(int i) throws RemoteException;

    void nitzTimeReceived(int i, String str, long j, long j2) throws RemoteException;

    void registrationFailed(int i, CellIdentity cellIdentity, String str, int i2, int i3, int i4) throws RemoteException;

    void restrictedStateChanged(int i, int i2) throws RemoteException;

    void securityAlgorithmsUpdated(int i, SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException;

    void suppSvcNotify(int i, SuppSvcNotification suppSvcNotification) throws RemoteException;

    void voiceRadioTechChanged(int i, int i2) throws RemoteException;

    public static class Default implements IRadioNetworkIndication {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void barringInfoChanged(int i, CellIdentity cellIdentity, BarringInfo[] barringInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void cdmaPrlChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void cellInfoList(int i, CellInfo[] cellInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void cellularIdentifierDisclosed(int i, CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void currentLinkCapacityEstimate(int i, LinkCapacityEstimate linkCapacityEstimate) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void currentPhysicalChannelConfigs(int i, PhysicalChannelConfig[] physicalChannelConfigArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void currentSignalStrength(int i, SignalStrength signalStrength) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void emergencyNetworkScanResult(int i, EmergencyRegResult emergencyRegResult) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void imsNetworkStateChanged(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void networkScanResult(int i, NetworkScanResult networkScanResult) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void networkStateChanged(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void nitzTimeReceived(int i, String str, long j, long j2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void registrationFailed(int i, CellIdentity cellIdentity, String str, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void restrictedStateChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void securityAlgorithmsUpdated(int i, SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void suppSvcNotify(int i, SuppSvcNotification suppSvcNotification) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void voiceRadioTechChanged(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioNetworkIndication {
        static final int TRANSACTION_barringInfoChanged = 1;
        static final int TRANSACTION_cdmaPrlChanged = 2;
        static final int TRANSACTION_cellInfoList = 3;
        static final int TRANSACTION_cellularIdentifierDisclosed = 16;
        static final int TRANSACTION_currentLinkCapacityEstimate = 4;
        static final int TRANSACTION_currentPhysicalChannelConfigs = 5;
        static final int TRANSACTION_currentSignalStrength = 6;
        static final int TRANSACTION_emergencyNetworkScanResult = 15;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_imsNetworkStateChanged = 7;
        static final int TRANSACTION_networkScanResult = 8;
        static final int TRANSACTION_networkStateChanged = 9;
        static final int TRANSACTION_nitzTimeReceived = 10;
        static final int TRANSACTION_registrationFailed = 11;
        static final int TRANSACTION_restrictedStateChanged = 12;
        static final int TRANSACTION_securityAlgorithmsUpdated = 17;
        static final int TRANSACTION_suppSvcNotify = 13;
        static final int TRANSACTION_voiceRadioTechChanged = 14;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioNetworkIndication asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioNetworkIndication)) {
                return (IRadioNetworkIndication) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
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
                    int i3 = parcel.readInt();
                    CellIdentity cellIdentity = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    BarringInfo[] barringInfoArr = (BarringInfo[]) parcel.createTypedArray(BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    barringInfoChanged(i3, cellIdentity, barringInfoArr);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaPrlChanged(i4, i5);
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    CellInfo[] cellInfoArr = (CellInfo[]) parcel.createTypedArray(CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cellInfoList(i6, cellInfoArr);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    LinkCapacityEstimate linkCapacityEstimate = (LinkCapacityEstimate) parcel.readTypedObject(LinkCapacityEstimate.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentLinkCapacityEstimate(i7, linkCapacityEstimate);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    PhysicalChannelConfig[] physicalChannelConfigArr = (PhysicalChannelConfig[]) parcel.createTypedArray(PhysicalChannelConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentPhysicalChannelConfigs(i8, physicalChannelConfigArr);
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    SignalStrength signalStrength = (SignalStrength) parcel.readTypedObject(SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentSignalStrength(i9, signalStrength);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    imsNetworkStateChanged(i10);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    NetworkScanResult networkScanResult = (NetworkScanResult) parcel.readTypedObject(NetworkScanResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    networkScanResult(i11, networkScanResult);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    networkStateChanged(i12);
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    String string = parcel.readString();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    nitzTimeReceived(i13, string, j, j2);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    CellIdentity cellIdentity2 = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    String string2 = parcel.readString();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registrationFailed(i14, cellIdentity2, string2, i15, i16, i17);
                    return true;
                case 12:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restrictedStateChanged(i18, i19);
                    return true;
                case 13:
                    int i20 = parcel.readInt();
                    SuppSvcNotification suppSvcNotification = (SuppSvcNotification) parcel.readTypedObject(SuppSvcNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    suppSvcNotify(i20, suppSvcNotification);
                    return true;
                case 14:
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    voiceRadioTechChanged(i21, i22);
                    return true;
                case 15:
                    int i23 = parcel.readInt();
                    EmergencyRegResult emergencyRegResult = (EmergencyRegResult) parcel.readTypedObject(EmergencyRegResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    emergencyNetworkScanResult(i23, emergencyRegResult);
                    return true;
                case 16:
                    int i24 = parcel.readInt();
                    CellularIdentifierDisclosure cellularIdentifierDisclosure = (CellularIdentifierDisclosure) parcel.readTypedObject(CellularIdentifierDisclosure.CREATOR);
                    parcel.enforceNoDataAvail();
                    cellularIdentifierDisclosed(i24, cellularIdentifierDisclosure);
                    return true;
                case 17:
                    int i25 = parcel.readInt();
                    SecurityAlgorithmUpdate securityAlgorithmUpdate = (SecurityAlgorithmUpdate) parcel.readTypedObject(SecurityAlgorithmUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    securityAlgorithmsUpdated(i25, securityAlgorithmUpdate);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioNetworkIndication {
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

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void barringInfoChanged(int i, CellIdentity cellIdentity, BarringInfo[] barringInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cellIdentity, 0);
                    parcelObtain.writeTypedArray(barringInfoArr, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method barringInfoChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cdmaPrlChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cdmaPrlChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cellInfoList(int i, CellInfo[] cellInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(cellInfoArr, 0);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cellInfoList is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentLinkCapacityEstimate(int i, LinkCapacityEstimate linkCapacityEstimate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(linkCapacityEstimate, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method currentLinkCapacityEstimate is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentPhysicalChannelConfigs(int i, PhysicalChannelConfig[] physicalChannelConfigArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(physicalChannelConfigArr, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method currentPhysicalChannelConfigs is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentSignalStrength(int i, SignalStrength signalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(signalStrength, 0);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method currentSignalStrength is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void imsNetworkStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method imsNetworkStateChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void networkScanResult(int i, NetworkScanResult networkScanResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(networkScanResult, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method networkScanResult is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void networkStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method networkStateChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void nitzTimeReceived(int i, String str, long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nitzTimeReceived is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void registrationFailed(int i, CellIdentity cellIdentity, String str, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cellIdentity, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method registrationFailed is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void restrictedStateChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method restrictedStateChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void suppSvcNotify(int i, SuppSvcNotification suppSvcNotification) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(suppSvcNotification, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method suppSvcNotify is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void voiceRadioTechChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method voiceRadioTechChanged is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void emergencyNetworkScanResult(int i, EmergencyRegResult emergencyRegResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(emergencyRegResult, 0);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencyNetworkScanResult is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cellularIdentifierDisclosed(int i, CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(cellularIdentifierDisclosure, 0);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cellularIdentifierDisclosed is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void securityAlgorithmsUpdated(int i, SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(securityAlgorithmUpdate, 0);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method securityAlgorithmsUpdated is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
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

            @Override // android.hardware.radio.network.IRadioNetworkIndication
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
}
