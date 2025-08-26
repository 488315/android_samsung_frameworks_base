package vendor.samsung.hardware.radio.network;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.SehRadioResponseInfo;

/* loaded from: classes6.dex */
public interface ISehRadioNetworkResponse extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$network$ISehRadioNetworkResponse".replace('$', '.');
    public static final String HASH = "0fc15a1faf82e2c79f263a7fd6e8b5ffba3644f3";
    public static final int VERSION = 1;

    void cfrmCpaiFeatureInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void emergencyControlResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void emergencySearchResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void evtCpaiDataGatheringResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void execCpaiModelUpdateResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void getAvailableNetworksResponse(SehRadioResponseInfo sehRadioResponseInfo, SehOperatorInfo[] sehOperatorInfoArr) throws RemoteException;

    void getCnapResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void getCpaiFeatureInfoResponse(SehRadioResponseInfo sehRadioResponseInfo, int i, int i2, byte[] bArr) throws RemoteException;

    void getCpaiModelVersionResponse(SehRadioResponseInfo sehRadioResponseInfo, int i, int i2, byte[] bArr) throws RemoteException;

    void getCsgListResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCsgInfo[] sehCsgInfoArr) throws RemoteException;

    void getDisable2gResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getNrIconTypeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void getNrModeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void getPreferredNetworkListResponse(SehRadioResponseInfo sehRadioResponseInfo, SehPreferredNetworkInfo[] sehPreferredNetworkInfoArr) throws RemoteException;

    void getRoamingNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo, SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr) throws RemoteException;

    void getVendorSpecificConfigurationResponse(SehRadioResponseInfo sehRadioResponseInfo, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException;

    void selectCsgManualResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void sendEncodedUssdResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void sendRequestRawResponse(SehRadioResponseInfo sehRadioResponseInfo, byte[] bArr) throws RemoteException;

    void sendRequestStringsResponse(SehRadioResponseInfo sehRadioResponseInfo, String[] strArr) throws RemoteException;

    void setCpaiDataGatheringResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setCpaiDevAppMessageResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setCurrentNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setDisable2gResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setImsCallListResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setNrModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setPreferredNetworkListResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setRoamingNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setScanResultViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setVendorSpecificConfigurationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    public static class Default implements ISehRadioNetworkResponse {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void cfrmCpaiFeatureInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void emergencyControlResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void emergencySearchResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void evtCpaiDataGatheringResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void execCpaiModelUpdateResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getAvailableNetworksResponse(SehRadioResponseInfo sehRadioResponseInfo, SehOperatorInfo[] sehOperatorInfoArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getCnapResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getCpaiFeatureInfoResponse(SehRadioResponseInfo sehRadioResponseInfo, int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getCpaiModelVersionResponse(SehRadioResponseInfo sehRadioResponseInfo, int i, int i2, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getCsgListResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCsgInfo[] sehCsgInfoArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getDisable2gResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getNrIconTypeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getNrModeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getPreferredNetworkListResponse(SehRadioResponseInfo sehRadioResponseInfo, SehPreferredNetworkInfo[] sehPreferredNetworkInfoArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getRoamingNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo, SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void getVendorSpecificConfigurationResponse(SehRadioResponseInfo sehRadioResponseInfo, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void selectCsgManualResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void sendEncodedUssdResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void sendRequestRawResponse(SehRadioResponseInfo sehRadioResponseInfo, byte[] bArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void sendRequestStringsResponse(SehRadioResponseInfo sehRadioResponseInfo, String[] strArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setCpaiDataGatheringResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setCpaiDevAppMessageResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setCurrentNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setDisable2gResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setImsCallListResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setNrModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setPreferredNetworkListResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setRoamingNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setScanResultViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public void setVendorSpecificConfigurationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioNetworkResponse {
        static final int TRANSACTION_cfrmCpaiFeatureInfoResponse = 27;
        static final int TRANSACTION_emergencyControlResponse = 1;
        static final int TRANSACTION_emergencySearchResponse = 2;
        static final int TRANSACTION_evtCpaiDataGatheringResponse = 29;
        static final int TRANSACTION_execCpaiModelUpdateResponse = 25;
        static final int TRANSACTION_getAvailableNetworksResponse = 3;
        static final int TRANSACTION_getCnapResponse = 4;
        static final int TRANSACTION_getCpaiFeatureInfoResponse = 26;
        static final int TRANSACTION_getCpaiModelVersionResponse = 24;
        static final int TRANSACTION_getCsgListResponse = 5;
        static final int TRANSACTION_getDisable2gResponse = 6;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNrIconTypeResponse = 7;
        static final int TRANSACTION_getNrModeResponse = 8;
        static final int TRANSACTION_getPreferredNetworkListResponse = 9;
        static final int TRANSACTION_getRoamingNetworkInfoViaBLEResponse = 10;
        static final int TRANSACTION_getVendorSpecificConfigurationResponse = 11;
        static final int TRANSACTION_selectCsgManualResponse = 12;
        static final int TRANSACTION_sendEncodedUssdResponse = 13;
        static final int TRANSACTION_sendRequestRawResponse = 14;
        static final int TRANSACTION_sendRequestStringsResponse = 15;
        static final int TRANSACTION_setCpaiDataGatheringResponse = 28;
        static final int TRANSACTION_setCpaiDevAppMessageResponse = 30;
        static final int TRANSACTION_setCurrentNetworkInfoViaBLEResponse = 16;
        static final int TRANSACTION_setDisable2gResponse = 17;
        static final int TRANSACTION_setImsCallListResponse = 18;
        static final int TRANSACTION_setNrModeResponse = 19;
        static final int TRANSACTION_setPreferredNetworkListResponse = 20;
        static final int TRANSACTION_setRoamingNetworkInfoViaBLEResponse = 21;
        static final int TRANSACTION_setScanResultViaBLEResponse = 22;
        static final int TRANSACTION_setVendorSpecificConfigurationResponse = 23;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioNetworkResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioNetworkResponse)) {
                return (ISehRadioNetworkResponse) iInterfaceQueryLocalInterface;
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
                    SehRadioResponseInfo sehRadioResponseInfo = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    emergencyControlResponse(sehRadioResponseInfo);
                    return true;
                case 2:
                    SehRadioResponseInfo sehRadioResponseInfo2 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    emergencySearchResponse(sehRadioResponseInfo2, i3);
                    return true;
                case 3:
                    SehRadioResponseInfo sehRadioResponseInfo3 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehOperatorInfo[] sehOperatorInfoArr = (SehOperatorInfo[]) parcel.createTypedArray(SehOperatorInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAvailableNetworksResponse(sehRadioResponseInfo3, sehOperatorInfoArr);
                    return true;
                case 4:
                    SehRadioResponseInfo sehRadioResponseInfo4 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCnapResponse(sehRadioResponseInfo4, i4);
                    return true;
                case 5:
                    SehRadioResponseInfo sehRadioResponseInfo5 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehCsgInfo[] sehCsgInfoArr = (SehCsgInfo[]) parcel.createTypedArray(SehCsgInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCsgListResponse(sehRadioResponseInfo5, sehCsgInfoArr);
                    return true;
                case 6:
                    SehRadioResponseInfo sehRadioResponseInfo6 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getDisable2gResponse(sehRadioResponseInfo6, i5);
                    return true;
                case 7:
                    SehRadioResponseInfo sehRadioResponseInfo7 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNrIconTypeResponse(sehRadioResponseInfo7, i6);
                    return true;
                case 8:
                    SehRadioResponseInfo sehRadioResponseInfo8 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getNrModeResponse(sehRadioResponseInfo8, i7);
                    return true;
                case 9:
                    SehRadioResponseInfo sehRadioResponseInfo9 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehPreferredNetworkInfo[] sehPreferredNetworkInfoArr = (SehPreferredNetworkInfo[]) parcel.createTypedArray(SehPreferredNetworkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPreferredNetworkListResponse(sehRadioResponseInfo9, sehPreferredNetworkInfoArr);
                    return true;
                case 10:
                    SehRadioResponseInfo sehRadioResponseInfo10 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr = (SehRoamingNetworkInfo[]) parcel.createTypedArray(SehRoamingNetworkInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getRoamingNetworkInfoViaBLEResponse(sehRadioResponseInfo10, sehRoamingNetworkInfoArr);
                    return true;
                case 11:
                    SehRadioResponseInfo sehRadioResponseInfo11 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehVendorConfiguration[] sehVendorConfigurationArr = (SehVendorConfiguration[]) parcel.createTypedArray(SehVendorConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    getVendorSpecificConfigurationResponse(sehRadioResponseInfo11, sehVendorConfigurationArr);
                    return true;
                case 12:
                    SehRadioResponseInfo sehRadioResponseInfo12 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    selectCsgManualResponse(sehRadioResponseInfo12);
                    return true;
                case 13:
                    SehRadioResponseInfo sehRadioResponseInfo13 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEncodedUssdResponse(sehRadioResponseInfo13);
                    return true;
                case 14:
                    SehRadioResponseInfo sehRadioResponseInfo14 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendRequestRawResponse(sehRadioResponseInfo14, bArrCreateByteArray);
                    return true;
                case 15:
                    SehRadioResponseInfo sehRadioResponseInfo15 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    sendRequestStringsResponse(sehRadioResponseInfo15, strArrCreateStringArray);
                    return true;
                case 16:
                    SehRadioResponseInfo sehRadioResponseInfo16 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCurrentNetworkInfoViaBLEResponse(sehRadioResponseInfo16);
                    return true;
                case 17:
                    SehRadioResponseInfo sehRadioResponseInfo17 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisable2gResponse(sehRadioResponseInfo17);
                    return true;
                case 18:
                    SehRadioResponseInfo sehRadioResponseInfo18 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setImsCallListResponse(sehRadioResponseInfo18);
                    return true;
                case 19:
                    SehRadioResponseInfo sehRadioResponseInfo19 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNrModeResponse(sehRadioResponseInfo19);
                    return true;
                case 20:
                    SehRadioResponseInfo sehRadioResponseInfo20 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredNetworkListResponse(sehRadioResponseInfo20);
                    return true;
                case 21:
                    SehRadioResponseInfo sehRadioResponseInfo21 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRoamingNetworkInfoViaBLEResponse(sehRadioResponseInfo21);
                    return true;
                case 22:
                    SehRadioResponseInfo sehRadioResponseInfo22 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setScanResultViaBLEResponse(sehRadioResponseInfo22);
                    return true;
                case 23:
                    SehRadioResponseInfo sehRadioResponseInfo23 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVendorSpecificConfigurationResponse(sehRadioResponseInfo23);
                    return true;
                case 24:
                    SehRadioResponseInfo sehRadioResponseInfo24 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    getCpaiModelVersionResponse(sehRadioResponseInfo24, i8, i9, bArrCreateByteArray2);
                    return true;
                case 25:
                    SehRadioResponseInfo sehRadioResponseInfo25 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    execCpaiModelUpdateResponse(sehRadioResponseInfo25);
                    return true;
                case 26:
                    SehRadioResponseInfo sehRadioResponseInfo26 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    getCpaiFeatureInfoResponse(sehRadioResponseInfo26, i10, i11, bArrCreateByteArray3);
                    return true;
                case 27:
                    SehRadioResponseInfo sehRadioResponseInfo27 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cfrmCpaiFeatureInfoResponse(sehRadioResponseInfo27);
                    return true;
                case 28:
                    SehRadioResponseInfo sehRadioResponseInfo28 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCpaiDataGatheringResponse(sehRadioResponseInfo28);
                    return true;
                case 29:
                    SehRadioResponseInfo sehRadioResponseInfo29 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    evtCpaiDataGatheringResponse(sehRadioResponseInfo29);
                    return true;
                case 30:
                    SehRadioResponseInfo sehRadioResponseInfo30 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCpaiDevAppMessageResponse(sehRadioResponseInfo30);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioNetworkResponse {
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void emergencyControlResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencyControlResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void emergencySearchResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencySearchResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getAvailableNetworksResponse(SehRadioResponseInfo sehRadioResponseInfo, SehOperatorInfo[] sehOperatorInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedArray(sehOperatorInfoArr, 0);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAvailableNetworksResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getCnapResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCnapResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getCsgListResponse(SehRadioResponseInfo sehRadioResponseInfo, SehCsgInfo[] sehCsgInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedArray(sehCsgInfoArr, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCsgListResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getDisable2gResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDisable2gResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getNrIconTypeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNrIconTypeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getNrModeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNrModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getPreferredNetworkListResponse(SehRadioResponseInfo sehRadioResponseInfo, SehPreferredNetworkInfo[] sehPreferredNetworkInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedArray(sehPreferredNetworkInfoArr, 0);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPreferredNetworkListResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getRoamingNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo, SehRoamingNetworkInfo[] sehRoamingNetworkInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedArray(sehRoamingNetworkInfoArr, 0);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRoamingNetworkInfoViaBLEResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getVendorSpecificConfigurationResponse(SehRadioResponseInfo sehRadioResponseInfo, SehVendorConfiguration[] sehVendorConfigurationArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedArray(sehVendorConfigurationArr, 0);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getVendorSpecificConfigurationResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void selectCsgManualResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method selectCsgManualResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void sendEncodedUssdResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendEncodedUssdResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void sendRequestRawResponse(SehRadioResponseInfo sehRadioResponseInfo, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRequestRawResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void sendRequestStringsResponse(SehRadioResponseInfo sehRadioResponseInfo, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeStringArray(strArr);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRequestStringsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setCurrentNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCurrentNetworkInfoViaBLEResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setDisable2gResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDisable2gResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setImsCallListResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImsCallListResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setNrModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNrModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setPreferredNetworkListResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPreferredNetworkListResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setRoamingNetworkInfoViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(21, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setRoamingNetworkInfoViaBLEResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setScanResultViaBLEResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setScanResultViaBLEResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setVendorSpecificConfigurationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(23, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setVendorSpecificConfigurationResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getCpaiModelVersionResponse(SehRadioResponseInfo sehRadioResponseInfo, int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(24, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCpaiModelVersionResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void execCpaiModelUpdateResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(25, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method execCpaiModelUpdateResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void getCpaiFeatureInfoResponse(SehRadioResponseInfo sehRadioResponseInfo, int i, int i2, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeByteArray(bArr);
                    if (this.mRemote.transact(26, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCpaiFeatureInfoResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void cfrmCpaiFeatureInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(27, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cfrmCpaiFeatureInfoResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setCpaiDataGatheringResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(28, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCpaiDataGatheringResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void evtCpaiDataGatheringResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(29, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method evtCpaiDataGatheringResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
            public void setCpaiDevAppMessageResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(30, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCpaiDevAppMessageResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
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

            @Override // vendor.samsung.hardware.radio.network.ISehRadioNetworkResponse
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
