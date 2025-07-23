package vendor.samsung.hardware.radio.network;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.network.ISehRadioNetworkIndication;
import vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse;

/* loaded from: classes6.dex */
public interface ISehRadioNetwork extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$network$ISehRadioNetwork".replace('$', '.');
    public static final String HASH = "0fc15a1faf82e2c79f263a7fd6e8b5ffba3644f3";
    public static final int VERSION = 1;

    void cfrmCpaiFeatureInfo(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void emergencyControl(int i, int i2) throws RemoteException;

    void emergencySearch(int i) throws RemoteException;

    void evtCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException;

    void execCpaiModelUpdate(int i, int i2) throws RemoteException;

    void getAvailableNetworks(int i) throws RemoteException;

    void getCnap(int i) throws RemoteException;

    void getCpaiFeatureInfo(int i, int i2) throws RemoteException;

    void getCpaiModelVersion(int i) throws RemoteException;

    void getCsgList(int i) throws RemoteException;

    void getDisable2g(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getNrIconType(int i) throws RemoteException;

    void getNrMode(int i) throws RemoteException;

    void getPreferredNetworkList(int i) throws RemoteException;

    void getRoamingNetworkInfoViaBLE(int i, byte b, String str) throws RemoteException;

    void getVendorSpecificConfiguration(int i) throws RemoteException;

    void selectCsgManual(int i, SehCsgInfo sehCsgInfo) throws RemoteException;

    void sendEncodedUssd(int i, SehEncodedUssd sehEncodedUssd) throws RemoteException;

    void sendRequestRaw(int i, byte[] bArr) throws RemoteException;

    void sendRequestStrings(int i, String[] strArr) throws RemoteException;

    void setCpaiDataGathering(int i, int i2, int i3, int i4, int i5) throws RemoteException;

    void setCpaiDevAppMessage(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void setCurrentNetworkInfoViaBLE(int i, SehCurrentNetworkInfo[] sehCurrentNetworkInfoArr) throws RemoteException;

    void setDisable2g(int i, int i2) throws RemoteException;

    void setImsCallList(int i, SehImsCall[] sehImsCallArr) throws RemoteException;

    void setNrMode(int i, int i2, boolean z) throws RemoteException;

    void setPreferredNetworkList(int i, SehPreferredNetworkInfo sehPreferredNetworkInfo) throws RemoteException;

    void setResponseFunctions(ISehRadioNetworkResponse iSehRadioNetworkResponse, ISehRadioNetworkIndication iSehRadioNetworkIndication) throws RemoteException;

    void setRoamingNetworkInfoViaBLE(int i, SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr) throws RemoteException;

    void setScanResultViaBLE(int i, byte b, byte b2) throws RemoteException;

    void setVendorSpecificConfiguration(int i, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException;

    public static class Default implements ISehRadioNetwork {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void cfrmCpaiFeatureInfo(int i, int i2, int i3, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void emergencyControl(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void emergencySearch(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void evtCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void execCpaiModelUpdate(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getAvailableNetworks(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getCnap(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getCpaiFeatureInfo(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getCpaiModelVersion(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getCsgList(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getDisable2g(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getNrIconType(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getNrMode(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getPreferredNetworkList(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getRoamingNetworkInfoViaBLE(int i, byte b, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void getVendorSpecificConfiguration(int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void selectCsgManual(int i, SehCsgInfo sehCsgInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void sendEncodedUssd(int i, SehEncodedUssd sehEncodedUssd) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void sendRequestRaw(int i, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void sendRequestStrings(int i, String[] strArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setCpaiDataGathering(int i, int i2, int i3, int i4, int i5) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setCpaiDevAppMessage(int i, int i2, int i3, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setCurrentNetworkInfoViaBLE(int i, SehCurrentNetworkInfo[] sehCurrentNetworkInfoArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setDisable2g(int i, int i2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setImsCallList(int i, SehImsCall[] sehImsCallArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setNrMode(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setPreferredNetworkList(int i, SehPreferredNetworkInfo sehPreferredNetworkInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setResponseFunctions(ISehRadioNetworkResponse iSehRadioNetworkResponse, ISehRadioNetworkIndication iSehRadioNetworkIndication) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setRoamingNetworkInfoViaBLE(int i, SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setScanResultViaBLE(int i, byte b, byte b2) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public void setVendorSpecificConfiguration(int i, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioNetwork {
        static final int TRANSACTION_cfrmCpaiFeatureInfo = 28;
        static final int TRANSACTION_emergencyControl = 1;
        static final int TRANSACTION_emergencySearch = 2;
        static final int TRANSACTION_evtCpaiDataGathering = 30;
        static final int TRANSACTION_execCpaiModelUpdate = 26;
        static final int TRANSACTION_getAvailableNetworks = 3;
        static final int TRANSACTION_getCnap = 4;
        static final int TRANSACTION_getCpaiFeatureInfo = 27;
        static final int TRANSACTION_getCpaiModelVersion = 25;
        static final int TRANSACTION_getCsgList = 5;
        static final int TRANSACTION_getDisable2g = 6;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNrIconType = 7;
        static final int TRANSACTION_getNrMode = 8;
        static final int TRANSACTION_getPreferredNetworkList = 9;
        static final int TRANSACTION_getRoamingNetworkInfoViaBLE = 10;
        static final int TRANSACTION_getVendorSpecificConfiguration = 11;
        static final int TRANSACTION_selectCsgManual = 12;
        static final int TRANSACTION_sendEncodedUssd = 13;
        static final int TRANSACTION_sendRequestRaw = 14;
        static final int TRANSACTION_sendRequestStrings = 15;
        static final int TRANSACTION_setCpaiDataGathering = 29;
        static final int TRANSACTION_setCpaiDevAppMessage = 31;
        static final int TRANSACTION_setCurrentNetworkInfoViaBLE = 16;
        static final int TRANSACTION_setDisable2g = 17;
        static final int TRANSACTION_setImsCallList = 18;
        static final int TRANSACTION_setNrMode = 19;
        static final int TRANSACTION_setPreferredNetworkList = 20;
        static final int TRANSACTION_setResponseFunctions = 21;
        static final int TRANSACTION_setRoamingNetworkInfoViaBLE = 22;
        static final int TRANSACTION_setScanResultViaBLE = 23;
        static final int TRANSACTION_setVendorSpecificConfiguration = 24;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioNetwork asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioNetwork)) {
                return (ISehRadioNetwork) queryLocalInterface;
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
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    emergencyControl(readInt, readInt2);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    emergencySearch(readInt3);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAvailableNetworks(readInt4);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCnap(readInt5);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCsgList(readInt6);
                    return true;
                case 6:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDisable2g(readInt7);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNrIconType(readInt8);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNrMode(readInt9);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPreferredNetworkList(readInt10);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getRoamingNetworkInfoViaBLE(readInt11, readByte, readString);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getVendorSpecificConfiguration(readInt12);
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    SehCsgInfo sehCsgInfo = (SehCsgInfo) parcel.readTypedObject(SehCsgInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    selectCsgManual(readInt13, sehCsgInfo);
                    return true;
                case 13:
                    int readInt14 = parcel.readInt();
                    SehEncodedUssd sehEncodedUssd = (SehEncodedUssd) parcel.readTypedObject(SehEncodedUssd.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEncodedUssd(readInt14, sehEncodedUssd);
                    return true;
                case 14:
                    int readInt15 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendRequestRaw(readInt15, createByteArray);
                    return true;
                case 15:
                    int readInt16 = parcel.readInt();
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    sendRequestStrings(readInt16, createStringArray);
                    return true;
                case 16:
                    int readInt17 = parcel.readInt();
                    SehCurrentNetworkInfo[] sehCurrentNetworkInfoArr = (SehCurrentNetworkInfo[]) parcel.createTypedArray(SehCurrentNetworkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCurrentNetworkInfoViaBLE(readInt17, sehCurrentNetworkInfoArr);
                    return true;
                case 17:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisable2g(readInt18, readInt19);
                    return true;
                case 18:
                    int readInt20 = parcel.readInt();
                    SehImsCall[] sehImsCallArr = (SehImsCall[]) parcel.createTypedArray(SehImsCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    setImsCallList(readInt20, sehImsCallArr);
                    return true;
                case 19:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNrMode(readInt21, readInt22, readBoolean);
                    return true;
                case 20:
                    int readInt23 = parcel.readInt();
                    SehPreferredNetworkInfo sehPreferredNetworkInfo = (SehPreferredNetworkInfo) parcel.readTypedObject(SehPreferredNetworkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredNetworkList(readInt23, sehPreferredNetworkInfo);
                    return true;
                case 21:
                    ISehRadioNetworkResponse asInterface = ISehRadioNetworkResponse.Stub.asInterface(parcel.readStrongBinder());
                    ISehRadioNetworkIndication asInterface2 = ISehRadioNetworkIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt24 = parcel.readInt();
                    SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr = (SehRoamingNetworkInfo[]) parcel.createTypedArray(SehRoamingNetworkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRoamingNetworkInfoViaBLE(readInt24, sehRoamingNetworkInfoArr);
                    return true;
                case 23:
                    int readInt25 = parcel.readInt();
                    byte readByte2 = parcel.readByte();
                    byte readByte3 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setScanResultViaBLE(readInt25, readByte2, readByte3);
                    return true;
                case 24:
                    int readInt26 = parcel.readInt();
                    SehVendorConfiguration[] sehVendorConfigurationArr = (SehVendorConfiguration[]) parcel.createTypedArray(SehVendorConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVendorSpecificConfiguration(readInt26, sehVendorConfigurationArr);
                    return true;
                case 25:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCpaiModelVersion(readInt27);
                    return true;
                case 26:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    execCpaiModelUpdate(readInt28, readInt29);
                    return true;
                case 27:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCpaiFeatureInfo(readInt30, readInt31);
                    return true;
                case 28:
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    cfrmCpaiFeatureInfo(readInt32, readInt33, readInt34, createByteArray2);
                    return true;
                case 29:
                    int readInt35 = parcel.readInt();
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCpaiDataGathering(readInt35, readInt36, readInt37, readInt38, readInt39);
                    return true;
                case 30:
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    evtCpaiDataGathering(readInt40, readInt41, createByteArray3);
                    return true;
                case 31:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    byte[] createByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setCpaiDevAppMessage(readInt42, readInt43, readInt44, createByteArray4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioNetwork {
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void emergencyControl(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencyControl is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void emergencySearch(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencySearch is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getAvailableNetworks(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAvailableNetworks is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCnap(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCnap is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCsgList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCsgList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getDisable2g(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDisable2g is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getNrIconType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNrIconType is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getNrMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNrMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getPreferredNetworkList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPreferredNetworkList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getRoamingNetworkInfoViaBLE(int i, byte b, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    obtain.writeString(str);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRoamingNetworkInfoViaBLE is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getVendorSpecificConfiguration(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getVendorSpecificConfiguration is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void selectCsgManual(int i, SehCsgInfo sehCsgInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehCsgInfo, 0);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method selectCsgManual is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void sendEncodedUssd(int i, SehEncodedUssd sehEncodedUssd) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehEncodedUssd, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendEncodedUssd is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void sendRequestRaw(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRequestRaw is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void sendRequestStrings(int i, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRequestStrings is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setCurrentNetworkInfoViaBLE(int i, SehCurrentNetworkInfo[] sehCurrentNetworkInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(sehCurrentNetworkInfoArr, 0);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCurrentNetworkInfoViaBLE is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setDisable2g(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(17, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDisable2g is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setImsCallList(int i, SehImsCall[] sehImsCallArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(sehImsCallArr, 0);
                    if (this.mRemote.transact(18, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImsCallList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setNrMode(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(19, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNrMode is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setPreferredNetworkList(int i, SehPreferredNetworkInfo sehPreferredNetworkInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sehPreferredNetworkInfo, 0);
                    if (this.mRemote.transact(20, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPreferredNetworkList is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setResponseFunctions(ISehRadioNetworkResponse iSehRadioNetworkResponse, ISehRadioNetworkIndication iSehRadioNetworkIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iSehRadioNetworkResponse);
                    obtain.writeStrongInterface(iSehRadioNetworkIndication);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setRoamingNetworkInfoViaBLE(int i, SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(sehRoamingNetworkInfoArr, 0);
                    if (this.mRemote.transact(22, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRoamingNetworkInfoViaBLE is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setScanResultViaBLE(int i, byte b, byte b2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    obtain.writeByte(b2);
                    if (this.mRemote.transact(23, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setScanResultViaBLE is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setVendorSpecificConfiguration(int i, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(sehVendorConfigurationArr, 0);
                    if (this.mRemote.transact(24, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setVendorSpecificConfiguration is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCpaiModelVersion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(25, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCpaiModelVersion is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void execCpaiModelUpdate(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(26, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method execCpaiModelUpdate is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCpaiFeatureInfo(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(27, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCpaiFeatureInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void cfrmCpaiFeatureInfo(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(28, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cfrmCpaiFeatureInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setCpaiDataGathering(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    if (this.mRemote.transact(29, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCpaiDataGathering is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void evtCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(30, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method evtCpaiDataGathering is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setCpaiDevAppMessage(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(31, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCpaiDevAppMessage is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
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
