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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioNetwork)) {
                return (ISehRadioNetwork) iInterfaceQueryLocalInterface;
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
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    emergencyControl(i3, i4);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    emergencySearch(i5);
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAvailableNetworks(i6);
                    return true;
                case 4:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCnap(i7);
                    return true;
                case 5:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCsgList(i8);
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDisable2g(i9);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNrIconType(i10);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNrMode(i11);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPreferredNetworkList(i12);
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    byte b = parcel.readByte();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getRoamingNetworkInfoViaBLE(i13, b, string);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getVendorSpecificConfiguration(i14);
                    return true;
                case 12:
                    int i15 = parcel.readInt();
                    SehCsgInfo sehCsgInfo = (SehCsgInfo) parcel.readTypedObject(SehCsgInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    selectCsgManual(i15, sehCsgInfo);
                    return true;
                case 13:
                    int i16 = parcel.readInt();
                    SehEncodedUssd sehEncodedUssd = (SehEncodedUssd) parcel.readTypedObject(SehEncodedUssd.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEncodedUssd(i16, sehEncodedUssd);
                    return true;
                case 14:
                    int i17 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendRequestRaw(i17, bArrCreateByteArray);
                    return true;
                case 15:
                    int i18 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    sendRequestStrings(i18, strArrCreateStringArray);
                    return true;
                case 16:
                    int i19 = parcel.readInt();
                    SehCurrentNetworkInfo[] sehCurrentNetworkInfoArr = (SehCurrentNetworkInfo[]) parcel.createTypedArray(SehCurrentNetworkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCurrentNetworkInfoViaBLE(i19, sehCurrentNetworkInfoArr);
                    return true;
                case 17:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDisable2g(i20, i21);
                    return true;
                case 18:
                    int i22 = parcel.readInt();
                    SehImsCall[] sehImsCallArr = (SehImsCall[]) parcel.createTypedArray(SehImsCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    setImsCallList(i22, sehImsCallArr);
                    return true;
                case 19:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNrMode(i23, i24, z);
                    return true;
                case 20:
                    int i25 = parcel.readInt();
                    SehPreferredNetworkInfo sehPreferredNetworkInfo = (SehPreferredNetworkInfo) parcel.readTypedObject(SehPreferredNetworkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredNetworkList(i25, sehPreferredNetworkInfo);
                    return true;
                case 21:
                    ISehRadioNetworkResponse iSehRadioNetworkResponseAsInterface = ISehRadioNetworkResponse.Stub.asInterface(parcel.readStrongBinder());
                    ISehRadioNetworkIndication iSehRadioNetworkIndicationAsInterface = ISehRadioNetworkIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iSehRadioNetworkResponseAsInterface, iSehRadioNetworkIndicationAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i26 = parcel.readInt();
                    SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr = (SehRoamingNetworkInfo[]) parcel.createTypedArray(SehRoamingNetworkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRoamingNetworkInfoViaBLE(i26, sehRoamingNetworkInfoArr);
                    return true;
                case 23:
                    int i27 = parcel.readInt();
                    byte b2 = parcel.readByte();
                    byte b3 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    setScanResultViaBLE(i27, b2, b3);
                    return true;
                case 24:
                    int i28 = parcel.readInt();
                    SehVendorConfiguration[] sehVendorConfigurationArr = (SehVendorConfiguration[]) parcel.createTypedArray(SehVendorConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVendorSpecificConfiguration(i28, sehVendorConfigurationArr);
                    return true;
                case 25:
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCpaiModelVersion(i29);
                    return true;
                case 26:
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    execCpaiModelUpdate(i30, i31);
                    return true;
                case 27:
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCpaiFeatureInfo(i32, i33);
                    return true;
                case 28:
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    cfrmCpaiFeatureInfo(i34, i35, i36, bArrCreateByteArray2);
                    return true;
                case 29:
                    int i37 = parcel.readInt();
                    int i38 = parcel.readInt();
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCpaiDataGathering(i37, i38, i39, i40, i41);
                    return true;
                case 30:
                    int i42 = parcel.readInt();
                    int i43 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    evtCpaiDataGathering(i42, i43, bArrCreateByteArray3);
                    return true;
                case 31:
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    int i46 = parcel.readInt();
                    byte[] bArrCreateByteArray4 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setCpaiDevAppMessage(i44, i45, i46, bArrCreateByteArray4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencyControl is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void emergencySearch(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencySearch is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getAvailableNetworks(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAvailableNetworks is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCnap(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCnap is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCsgList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCsgList is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getDisable2g(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDisable2g is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getNrIconType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNrIconType is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getNrMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNrMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getPreferredNetworkList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPreferredNetworkList is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getRoamingNetworkInfoViaBLE(int i, byte b, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRoamingNetworkInfoViaBLE is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getVendorSpecificConfiguration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getVendorSpecificConfiguration is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void selectCsgManual(int i, SehCsgInfo sehCsgInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehCsgInfo, 0);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method selectCsgManual is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void sendEncodedUssd(int i, SehEncodedUssd sehEncodedUssd) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehEncodedUssd, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendEncodedUssd is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void sendRequestRaw(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRequestRaw is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void sendRequestStrings(int i, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRequestStrings is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setCurrentNetworkInfoViaBLE(int i, SehCurrentNetworkInfo[] sehCurrentNetworkInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(sehCurrentNetworkInfoArr, 0);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCurrentNetworkInfoViaBLE is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setDisable2g(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDisable2g is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setImsCallList(int i, SehImsCall[] sehImsCallArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(sehImsCallArr, 0);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImsCallList is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setNrMode(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNrMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setPreferredNetworkList(int i, SehPreferredNetworkInfo sehPreferredNetworkInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sehPreferredNetworkInfo, 0);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPreferredNetworkList is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setResponseFunctions(ISehRadioNetworkResponse iSehRadioNetworkResponse, ISehRadioNetworkIndication iSehRadioNetworkIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSehRadioNetworkResponse);
                    parcelObtain.writeStrongInterface(iSehRadioNetworkIndication);
                    if (!this.mRemote.transact(21, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setRoamingNetworkInfoViaBLE(int i, SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(sehRoamingNetworkInfoArr, 0);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRoamingNetworkInfoViaBLE is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setScanResultViaBLE(int i, byte b, byte b2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByte(b);
                    parcelObtain.writeByte(b2);
                    if (this.mRemote.transact(23, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setScanResultViaBLE is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setVendorSpecificConfiguration(int i, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(sehVendorConfigurationArr, 0);
                    if (this.mRemote.transact(24, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setVendorSpecificConfiguration is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCpaiModelVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(25, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCpaiModelVersion is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void execCpaiModelUpdate(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(26, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method execCpaiModelUpdate is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void getCpaiFeatureInfo(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(27, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCpaiFeatureInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void cfrmCpaiFeatureInfo(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(28, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cfrmCpaiFeatureInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setCpaiDataGathering(int i, int i2, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    if (this.mRemote.transact(29, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCpaiDataGathering is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void evtCpaiDataGathering(int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(30, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method evtCpaiDataGathering is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
            public void setCpaiDevAppMessage(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(31, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCpaiDevAppMessage is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetwork
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
