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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioNetworkIndication)) {
                return (IRadioNetworkIndication) queryLocalInterface;
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
                    int readInt = parcel.readInt();
                    CellIdentity cellIdentity = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    BarringInfo[] barringInfoArr = (BarringInfo[]) parcel.createTypedArray(BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    barringInfoChanged(readInt, cellIdentity, barringInfoArr);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cdmaPrlChanged(readInt2, readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    CellInfo[] cellInfoArr = (CellInfo[]) parcel.createTypedArray(CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cellInfoList(readInt4, cellInfoArr);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    LinkCapacityEstimate linkCapacityEstimate = (LinkCapacityEstimate) parcel.readTypedObject(LinkCapacityEstimate.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentLinkCapacityEstimate(readInt5, linkCapacityEstimate);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    PhysicalChannelConfig[] physicalChannelConfigArr = (PhysicalChannelConfig[]) parcel.createTypedArray(PhysicalChannelConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentPhysicalChannelConfigs(readInt6, physicalChannelConfigArr);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    SignalStrength signalStrength = (SignalStrength) parcel.readTypedObject(SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentSignalStrength(readInt7, signalStrength);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    imsNetworkStateChanged(readInt8);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    NetworkScanResult networkScanResult = (NetworkScanResult) parcel.readTypedObject(NetworkScanResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    networkScanResult(readInt9, networkScanResult);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    networkStateChanged(readInt10);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    nitzTimeReceived(readInt11, readString, readLong, readLong2);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    CellIdentity cellIdentity2 = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    String readString2 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registrationFailed(readInt12, cellIdentity2, readString2, readInt13, readInt14, readInt15);
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restrictedStateChanged(readInt16, readInt17);
                    return true;
                case 13:
                    int readInt18 = parcel.readInt();
                    SuppSvcNotification suppSvcNotification = (SuppSvcNotification) parcel.readTypedObject(SuppSvcNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    suppSvcNotify(readInt18, suppSvcNotification);
                    return true;
                case 14:
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    voiceRadioTechChanged(readInt19, readInt20);
                    return true;
                case 15:
                    int readInt21 = parcel.readInt();
                    EmergencyRegResult emergencyRegResult = (EmergencyRegResult) parcel.readTypedObject(EmergencyRegResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    emergencyNetworkScanResult(readInt21, emergencyRegResult);
                    return true;
                case 16:
                    int readInt22 = parcel.readInt();
                    CellularIdentifierDisclosure cellularIdentifierDisclosure = (CellularIdentifierDisclosure) parcel.readTypedObject(CellularIdentifierDisclosure.CREATOR);
                    parcel.enforceNoDataAvail();
                    cellularIdentifierDisclosed(readInt22, cellularIdentifierDisclosure);
                    return true;
                case 17:
                    int readInt23 = parcel.readInt();
                    SecurityAlgorithmUpdate securityAlgorithmUpdate = (SecurityAlgorithmUpdate) parcel.readTypedObject(SecurityAlgorithmUpdate.CREATOR);
                    parcel.enforceNoDataAvail();
                    securityAlgorithmsUpdated(readInt23, securityAlgorithmUpdate);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cellIdentity, 0);
                    obtain.writeTypedArray(barringInfoArr, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method barringInfoChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cdmaPrlChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cdmaPrlChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cellInfoList(int i, CellInfo[] cellInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(cellInfoArr, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cellInfoList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentLinkCapacityEstimate(int i, LinkCapacityEstimate linkCapacityEstimate) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(linkCapacityEstimate, 0);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method currentLinkCapacityEstimate is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentPhysicalChannelConfigs(int i, PhysicalChannelConfig[] physicalChannelConfigArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(physicalChannelConfigArr, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method currentPhysicalChannelConfigs is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentSignalStrength(int i, SignalStrength signalStrength) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(signalStrength, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method currentSignalStrength is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void imsNetworkStateChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method imsNetworkStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void networkScanResult(int i, NetworkScanResult networkScanResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(networkScanResult, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method networkScanResult is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void networkStateChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method networkStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void nitzTimeReceived(int i, String str, long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method nitzTimeReceived is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void registrationFailed(int i, CellIdentity cellIdentity, String str, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cellIdentity, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method registrationFailed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void restrictedStateChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method restrictedStateChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void suppSvcNotify(int i, SuppSvcNotification suppSvcNotification) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(suppSvcNotification, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method suppSvcNotify is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void voiceRadioTechChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method voiceRadioTechChanged is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void emergencyNetworkScanResult(int i, EmergencyRegResult emergencyRegResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(emergencyRegResult, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencyNetworkScanResult is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cellularIdentifierDisclosed(int i, CellularIdentifierDisclosure cellularIdentifierDisclosure) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(cellularIdentifierDisclosure, 0);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cellularIdentifierDisclosed is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void securityAlgorithmsUpdated(int i, SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(securityAlgorithmUpdate, 0);
                    if (this.mRemote.transact(17, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method securityAlgorithmsUpdated is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
