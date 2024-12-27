package android.hardware.radio.network;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IRadioNetworkIndication extends IInterface {
    public static final String DESCRIPTOR =
            "android$hardware$radio$network$IRadioNetworkIndication".replace('$', '.');
    public static final String HASH = "c45c122528c07c449ea08f6eacaace17bb7abc38";
    public static final int VERSION = 3;

    void barringInfoChanged(int i, CellIdentity cellIdentity, BarringInfo[] barringInfoArr)
            throws RemoteException;

    void cdmaPrlChanged(int i, int i2) throws RemoteException;

    void cellInfoList(int i, CellInfo[] cellInfoArr) throws RemoteException;

    void cellularIdentifierDisclosed(
            int i, CellularIdentifierDisclosure cellularIdentifierDisclosure)
            throws RemoteException;

    void currentLinkCapacityEstimate(int i, LinkCapacityEstimate linkCapacityEstimate)
            throws RemoteException;

    void currentPhysicalChannelConfigs(int i, PhysicalChannelConfig[] physicalChannelConfigArr)
            throws RemoteException;

    void currentSignalStrength(int i, SignalStrength signalStrength) throws RemoteException;

    void emergencyNetworkScanResult(int i, EmergencyRegResult emergencyRegResult)
            throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void imsNetworkStateChanged(int i) throws RemoteException;

    void networkScanResult(int i, NetworkScanResult networkScanResult) throws RemoteException;

    void networkStateChanged(int i) throws RemoteException;

    void nitzTimeReceived(int i, String str, long j, long j2) throws RemoteException;

    void registrationFailed(int i, CellIdentity cellIdentity, String str, int i2, int i3, int i4)
            throws RemoteException;

    void restrictedStateChanged(int i, int i2) throws RemoteException;

    void securityAlgorithmsUpdated(int i, SecurityAlgorithmUpdate securityAlgorithmUpdate)
            throws RemoteException;

    void suppSvcNotify(int i, SuppSvcNotification suppSvcNotification) throws RemoteException;

    void voiceRadioTechChanged(int i, int i2) throws RemoteException;

    public static class Default implements IRadioNetworkIndication {
        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void barringInfoChanged(
                int type, CellIdentity cellIdentity, BarringInfo[] barringInfos)
                throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void cdmaPrlChanged(int type, int version) throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void cellInfoList(int type, CellInfo[] records) throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void currentLinkCapacityEstimate(int type, LinkCapacityEstimate lce)
                throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void currentPhysicalChannelConfigs(int type, PhysicalChannelConfig[] configs)
                throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void currentSignalStrength(int type, SignalStrength signalStrength)
                throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void imsNetworkStateChanged(int type) throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void networkScanResult(int type, NetworkScanResult result) throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void networkStateChanged(int type) throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void nitzTimeReceived(int type, String nitzTime, long receivedTimeMs, long ageMs)
                throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void registrationFailed(
                int type,
                CellIdentity cellIdentity,
                String chosenPlmn,
                int domain,
                int causeCode,
                int additionalCauseCode)
                throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void restrictedStateChanged(int type, int state) throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void suppSvcNotify(int type, SuppSvcNotification suppSvc) throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void voiceRadioTechChanged(int type, int rat) throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void emergencyNetworkScanResult(int type, EmergencyRegResult result)
                throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void cellularIdentifierDisclosed(int type, CellularIdentifierDisclosure disclosure)
                throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public void securityAlgorithmsUpdated(
                int type, SecurityAlgorithmUpdate securityAlgorithmUpdate) throws RemoteException {}

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.network.IRadioNetworkIndication
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IRadioNetworkIndication {
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

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioNetworkIndication asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IRadioNetworkIndication)) {
                return (IRadioNetworkIndication) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            if (code == 1598968902) {
                reply.writeString(descriptor);
                return true;
            }
            if (code == 16777215) {
                reply.writeNoException();
                reply.writeInt(getInterfaceVersion());
                return true;
            }
            if (code == 16777214) {
                reply.writeNoException();
                reply.writeString(getInterfaceHash());
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    CellIdentity _arg1 = (CellIdentity) data.readTypedObject(CellIdentity.CREATOR);
                    BarringInfo[] _arg2 =
                            (BarringInfo[]) data.createTypedArray(BarringInfo.CREATOR);
                    data.enforceNoDataAvail();
                    barringInfoChanged(_arg0, _arg1, _arg2);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    cdmaPrlChanged(_arg02, _arg12);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    CellInfo[] _arg13 = (CellInfo[]) data.createTypedArray(CellInfo.CREATOR);
                    data.enforceNoDataAvail();
                    cellInfoList(_arg03, _arg13);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    LinkCapacityEstimate _arg14 =
                            (LinkCapacityEstimate)
                                    data.readTypedObject(LinkCapacityEstimate.CREATOR);
                    data.enforceNoDataAvail();
                    currentLinkCapacityEstimate(_arg04, _arg14);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    PhysicalChannelConfig[] _arg15 =
                            (PhysicalChannelConfig[])
                                    data.createTypedArray(PhysicalChannelConfig.CREATOR);
                    data.enforceNoDataAvail();
                    currentPhysicalChannelConfigs(_arg05, _arg15);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    SignalStrength _arg16 =
                            (SignalStrength) data.readTypedObject(SignalStrength.CREATOR);
                    data.enforceNoDataAvail();
                    currentSignalStrength(_arg06, _arg16);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    imsNetworkStateChanged(_arg07);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    NetworkScanResult _arg17 =
                            (NetworkScanResult) data.readTypedObject(NetworkScanResult.CREATOR);
                    data.enforceNoDataAvail();
                    networkScanResult(_arg08, _arg17);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    networkStateChanged(_arg09);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    String _arg18 = data.readString();
                    long _arg22 = data.readLong();
                    long _arg3 = data.readLong();
                    data.enforceNoDataAvail();
                    nitzTimeReceived(_arg010, _arg18, _arg22, _arg3);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    CellIdentity _arg19 = (CellIdentity) data.readTypedObject(CellIdentity.CREATOR);
                    String _arg23 = data.readString();
                    int _arg32 = data.readInt();
                    int _arg4 = data.readInt();
                    int _arg5 = data.readInt();
                    data.enforceNoDataAvail();
                    registrationFailed(_arg011, _arg19, _arg23, _arg32, _arg4, _arg5);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    restrictedStateChanged(_arg012, _arg110);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    SuppSvcNotification _arg111 =
                            (SuppSvcNotification) data.readTypedObject(SuppSvcNotification.CREATOR);
                    data.enforceNoDataAvail();
                    suppSvcNotify(_arg013, _arg111);
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    int _arg112 = data.readInt();
                    data.enforceNoDataAvail();
                    voiceRadioTechChanged(_arg014, _arg112);
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    EmergencyRegResult _arg113 =
                            (EmergencyRegResult) data.readTypedObject(EmergencyRegResult.CREATOR);
                    data.enforceNoDataAvail();
                    emergencyNetworkScanResult(_arg015, _arg113);
                    return true;
                case 16:
                    int _arg016 = data.readInt();
                    CellularIdentifierDisclosure _arg114 =
                            (CellularIdentifierDisclosure)
                                    data.readTypedObject(CellularIdentifierDisclosure.CREATOR);
                    data.enforceNoDataAvail();
                    cellularIdentifierDisclosed(_arg016, _arg114);
                    return true;
                case 17:
                    int _arg017 = data.readInt();
                    SecurityAlgorithmUpdate _arg115 =
                            (SecurityAlgorithmUpdate)
                                    data.readTypedObject(SecurityAlgorithmUpdate.CREATOR);
                    data.enforceNoDataAvail();
                    securityAlgorithmsUpdated(_arg017, _arg115);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IRadioNetworkIndication {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void barringInfoChanged(
                    int type, CellIdentity cellIdentity, BarringInfo[] barringInfos)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(cellIdentity, 0);
                    _data.writeTypedArray(barringInfos, 0);
                    boolean _status = this.mRemote.transact(1, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method barringInfoChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cdmaPrlChanged(int type, int version) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(version);
                    boolean _status = this.mRemote.transact(2, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method cdmaPrlChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cellInfoList(int type, CellInfo[] records) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedArray(records, 0);
                    boolean _status = this.mRemote.transact(3, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method cellInfoList is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentLinkCapacityEstimate(int type, LinkCapacityEstimate lce)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(lce, 0);
                    boolean _status = this.mRemote.transact(4, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method currentLinkCapacityEstimate is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentPhysicalChannelConfigs(int type, PhysicalChannelConfig[] configs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedArray(configs, 0);
                    boolean _status = this.mRemote.transact(5, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method currentPhysicalChannelConfigs is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void currentSignalStrength(int type, SignalStrength signalStrength)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(signalStrength, 0);
                    boolean _status = this.mRemote.transact(6, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method currentSignalStrength is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void imsNetworkStateChanged(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(7, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method imsNetworkStateChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void networkScanResult(int type, NetworkScanResult result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(result, 0);
                    boolean _status = this.mRemote.transact(8, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method networkScanResult is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void networkStateChanged(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    boolean _status = this.mRemote.transact(9, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method networkStateChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void nitzTimeReceived(int type, String nitzTime, long receivedTimeMs, long ageMs)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(nitzTime);
                    _data.writeLong(receivedTimeMs);
                    _data.writeLong(ageMs);
                    boolean _status = this.mRemote.transact(10, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method nitzTimeReceived is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void registrationFailed(
                    int type,
                    CellIdentity cellIdentity,
                    String chosenPlmn,
                    int domain,
                    int causeCode,
                    int additionalCauseCode)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(cellIdentity, 0);
                    _data.writeString(chosenPlmn);
                    _data.writeInt(domain);
                    _data.writeInt(causeCode);
                    _data.writeInt(additionalCauseCode);
                    boolean _status = this.mRemote.transact(11, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method registrationFailed is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void restrictedStateChanged(int type, int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(state);
                    boolean _status = this.mRemote.transact(12, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method restrictedStateChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void suppSvcNotify(int type, SuppSvcNotification suppSvc)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(suppSvc, 0);
                    boolean _status = this.mRemote.transact(13, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method suppSvcNotify is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void voiceRadioTechChanged(int type, int rat) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(rat);
                    boolean _status = this.mRemote.transact(14, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException("Method voiceRadioTechChanged is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void emergencyNetworkScanResult(int type, EmergencyRegResult result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(result, 0);
                    boolean _status = this.mRemote.transact(15, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method emergencyNetworkScanResult is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void cellularIdentifierDisclosed(
                    int type, CellularIdentifierDisclosure disclosure) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(disclosure, 0);
                    boolean _status = this.mRemote.transact(16, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method cellularIdentifierDisclosed is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public void securityAlgorithmsUpdated(
                    int type, SecurityAlgorithmUpdate securityAlgorithmUpdate)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeTypedObject(securityAlgorithmUpdate, 0);
                    boolean _status = this.mRemote.transact(17, _data, null, 1);
                    if (!_status) {
                        throw new RemoteException(
                                "Method securityAlgorithmsUpdated is unimplemented.");
                    }
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, data, reply, 0);
                        reply.readException();
                        this.mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.radio.network.IRadioNetworkIndication
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel data = Parcel.obtain(asBinder());
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, data, reply, 0);
                        reply.readException();
                        this.mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } catch (Throwable th) {
                        reply.recycle();
                        data.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
