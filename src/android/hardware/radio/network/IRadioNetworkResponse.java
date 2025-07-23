package android.hardware.radio.network;

import android.hardware.radio.RadioResponseInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioNetworkResponse extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$network$IRadioNetworkResponse".replace('$', '.');
    public static final String HASH = "5867b4f5be491ec815fafea8a3f268b0295427df";
    public static final int VERSION = 4;

    void acknowledgeRequest(int i) throws RemoteException;

    void cancelEmergencyNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void exitEmergencyModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void getAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    @Deprecated
    void getAvailableBandModesResponse(RadioResponseInfo radioResponseInfo, int[] iArr) throws RemoteException;

    void getAvailableNetworksResponse(RadioResponseInfo radioResponseInfo, OperatorInfo[] operatorInfoArr) throws RemoteException;

    void getBarringInfoResponse(RadioResponseInfo radioResponseInfo, CellIdentity cellIdentity, BarringInfo[] barringInfoArr) throws RemoteException;

    @Deprecated
    void getCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getCellInfoListResponse(RadioResponseInfo radioResponseInfo, CellInfo[] cellInfoArr) throws RemoteException;

    void getDataRegistrationStateResponse(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException;

    @Deprecated
    void getImsRegistrationStateResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getNetworkSelectionModeResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void getOperatorResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3) throws RemoteException;

    void getSignalStrengthResponse(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) throws RemoteException;

    void getSystemSelectionChannelsResponse(RadioResponseInfo radioResponseInfo, RadioAccessSpecifier[] radioAccessSpecifierArr) throws RemoteException;

    void getUsageSettingResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getVoiceRadioTechnologyResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getVoiceRegistrationStateResponse(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException;

    void isCellularIdentifierTransparencyEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void isN1ModeEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void isNrDualConnectivityEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void isNullCipherAndIntegrityEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void isSatelliteEnabledForCarrierResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void isSecurityAlgorithmsUpdatedEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void setAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void setBandModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setBarringPasswordResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void setCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCellInfoListRateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCellularIdentifierTransparencyEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setEmergencyModeResponse(RadioResponseInfo radioResponseInfo, EmergencyRegResult emergencyRegResult) throws RemoteException;

    void setIndicationFilterResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setLinkCapacityReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void setLocationUpdatesResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setN1ModeEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNetworkSelectionModeManualResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNrDualConnectivityStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setNullCipherAndIntegrityEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSatelliteEnabledForCarrierResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSatellitePlmnResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSecurityAlgorithmsUpdatedEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSignalStrengthReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void setSuppServiceNotificationsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSystemSelectionChannelsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setUsageSettingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void startNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void stopNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void supplyNetworkDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void triggerEmergencyNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    public static class Default implements IRadioNetworkResponse {
        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void acknowledgeRequest(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void cancelEmergencyNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void exitEmergencyModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getAvailableBandModesResponse(RadioResponseInfo radioResponseInfo, int[] iArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getAvailableNetworksResponse(RadioResponseInfo radioResponseInfo, OperatorInfo[] operatorInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getBarringInfoResponse(RadioResponseInfo radioResponseInfo, CellIdentity cellIdentity, BarringInfo[] barringInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getCellInfoListResponse(RadioResponseInfo radioResponseInfo, CellInfo[] cellInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getDataRegistrationStateResponse(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getImsRegistrationStateResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getNetworkSelectionModeResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getOperatorResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getSignalStrengthResponse(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getSystemSelectionChannelsResponse(RadioResponseInfo radioResponseInfo, RadioAccessSpecifier[] radioAccessSpecifierArr) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getUsageSettingResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getVoiceRadioTechnologyResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void getVoiceRegistrationStateResponse(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isCellularIdentifierTransparencyEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isN1ModeEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isNrDualConnectivityEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isNullCipherAndIntegrityEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isSatelliteEnabledForCarrierResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void isSecurityAlgorithmsUpdatedEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setBandModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setBarringPasswordResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setCellInfoListRateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setCellularIdentifierTransparencyEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setEmergencyModeResponse(RadioResponseInfo radioResponseInfo, EmergencyRegResult emergencyRegResult) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setIndicationFilterResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setLinkCapacityReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setLocationUpdatesResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setN1ModeEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setNetworkSelectionModeManualResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setNrDualConnectivityStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setNullCipherAndIntegrityEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSatelliteEnabledForCarrierResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSatellitePlmnResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSecurityAlgorithmsUpdatedEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSignalStrengthReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSuppServiceNotificationsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setSystemSelectionChannelsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void setUsageSettingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void startNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void stopNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void supplyNetworkDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public void triggerEmergencyNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.network.IRadioNetworkResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioNetworkResponse {
        static final int TRANSACTION_acknowledgeRequest = 1;
        static final int TRANSACTION_cancelEmergencyNetworkScanResponse = 39;
        static final int TRANSACTION_exitEmergencyModeResponse = 38;
        static final int TRANSACTION_getAllowedNetworkTypesBitmapResponse = 2;
        static final int TRANSACTION_getAvailableBandModesResponse = 3;
        static final int TRANSACTION_getAvailableNetworksResponse = 4;
        static final int TRANSACTION_getBarringInfoResponse = 5;
        static final int TRANSACTION_getCdmaRoamingPreferenceResponse = 6;
        static final int TRANSACTION_getCellInfoListResponse = 7;
        static final int TRANSACTION_getDataRegistrationStateResponse = 8;
        static final int TRANSACTION_getImsRegistrationStateResponse = 9;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getNetworkSelectionModeResponse = 10;
        static final int TRANSACTION_getOperatorResponse = 11;
        static final int TRANSACTION_getSignalStrengthResponse = 12;
        static final int TRANSACTION_getSystemSelectionChannelsResponse = 13;
        static final int TRANSACTION_getUsageSettingResponse = 35;
        static final int TRANSACTION_getVoiceRadioTechnologyResponse = 14;
        static final int TRANSACTION_getVoiceRegistrationStateResponse = 15;
        static final int TRANSACTION_isCellularIdentifierTransparencyEnabledResponse = 44;
        static final int TRANSACTION_isN1ModeEnabledResponse = 42;
        static final int TRANSACTION_isNrDualConnectivityEnabledResponse = 16;
        static final int TRANSACTION_isNullCipherAndIntegrityEnabledResponse = 41;
        static final int TRANSACTION_isSatelliteEnabledForCarrierResponse = 50;
        static final int TRANSACTION_isSecurityAlgorithmsUpdatedEnabledResponse = 47;
        static final int TRANSACTION_setAllowedNetworkTypesBitmapResponse = 17;
        static final int TRANSACTION_setBandModeResponse = 18;
        static final int TRANSACTION_setBarringPasswordResponse = 19;
        static final int TRANSACTION_setCdmaRoamingPreferenceResponse = 20;
        static final int TRANSACTION_setCellInfoListRateResponse = 21;
        static final int TRANSACTION_setCellularIdentifierTransparencyEnabledResponse = 45;
        static final int TRANSACTION_setEmergencyModeResponse = 36;
        static final int TRANSACTION_setIndicationFilterResponse = 22;
        static final int TRANSACTION_setLinkCapacityReportingCriteriaResponse = 23;
        static final int TRANSACTION_setLocationUpdatesResponse = 24;
        static final int TRANSACTION_setN1ModeEnabledResponse = 43;
        static final int TRANSACTION_setNetworkSelectionModeAutomaticResponse = 25;
        static final int TRANSACTION_setNetworkSelectionModeManualResponse = 26;
        static final int TRANSACTION_setNrDualConnectivityStateResponse = 27;
        static final int TRANSACTION_setNullCipherAndIntegrityEnabledResponse = 40;
        static final int TRANSACTION_setSatelliteEnabledForCarrierResponse = 49;
        static final int TRANSACTION_setSatellitePlmnResponse = 48;
        static final int TRANSACTION_setSecurityAlgorithmsUpdatedEnabledResponse = 46;
        static final int TRANSACTION_setSignalStrengthReportingCriteriaResponse = 28;
        static final int TRANSACTION_setSuppServiceNotificationsResponse = 29;
        static final int TRANSACTION_setSystemSelectionChannelsResponse = 30;
        static final int TRANSACTION_setUsageSettingResponse = 34;
        static final int TRANSACTION_startNetworkScanResponse = 31;
        static final int TRANSACTION_stopNetworkScanResponse = 32;
        static final int TRANSACTION_supplyNetworkDepersonalizationResponse = 33;
        static final int TRANSACTION_triggerEmergencyNetworkScanResponse = 37;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioNetworkResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioNetworkResponse)) {
                return (IRadioNetworkResponse) queryLocalInterface;
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
                    parcel.enforceNoDataAvail();
                    acknowledgeRequest(readInt);
                    return true;
                case 2:
                    RadioResponseInfo radioResponseInfo = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAllowedNetworkTypesBitmapResponse(radioResponseInfo, readInt2);
                    return true;
                case 3:
                    RadioResponseInfo radioResponseInfo2 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    getAvailableBandModesResponse(radioResponseInfo2, createIntArray);
                    return true;
                case 4:
                    RadioResponseInfo radioResponseInfo3 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    OperatorInfo[] operatorInfoArr = (OperatorInfo[]) parcel.createTypedArray(OperatorInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAvailableNetworksResponse(radioResponseInfo3, operatorInfoArr);
                    return true;
                case 5:
                    RadioResponseInfo radioResponseInfo4 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    CellIdentity cellIdentity = (CellIdentity) parcel.readTypedObject(CellIdentity.CREATOR);
                    BarringInfo[] barringInfoArr = (BarringInfo[]) parcel.createTypedArray(BarringInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getBarringInfoResponse(radioResponseInfo4, cellIdentity, barringInfoArr);
                    return true;
                case 6:
                    RadioResponseInfo radioResponseInfo5 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaRoamingPreferenceResponse(radioResponseInfo5, readInt3);
                    return true;
                case 7:
                    RadioResponseInfo radioResponseInfo6 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    CellInfo[] cellInfoArr = (CellInfo[]) parcel.createTypedArray(CellInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCellInfoListResponse(radioResponseInfo6, cellInfoArr);
                    return true;
                case 8:
                    RadioResponseInfo radioResponseInfo7 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    RegStateResult regStateResult = (RegStateResult) parcel.readTypedObject(RegStateResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    getDataRegistrationStateResponse(radioResponseInfo7, regStateResult);
                    return true;
                case 9:
                    RadioResponseInfo radioResponseInfo8 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getImsRegistrationStateResponse(radioResponseInfo8, readBoolean, readInt4);
                    return true;
                case 10:
                    RadioResponseInfo radioResponseInfo9 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getNetworkSelectionModeResponse(radioResponseInfo9, readBoolean2);
                    return true;
                case 11:
                    RadioResponseInfo radioResponseInfo10 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getOperatorResponse(radioResponseInfo10, readString, readString2, readString3);
                    return true;
                case 12:
                    RadioResponseInfo radioResponseInfo11 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    SignalStrength signalStrength = (SignalStrength) parcel.readTypedObject(SignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSignalStrengthResponse(radioResponseInfo11, signalStrength);
                    return true;
                case 13:
                    RadioResponseInfo radioResponseInfo12 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    RadioAccessSpecifier[] radioAccessSpecifierArr = (RadioAccessSpecifier[]) parcel.createTypedArray(RadioAccessSpecifier.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSystemSelectionChannelsResponse(radioResponseInfo12, radioAccessSpecifierArr);
                    return true;
                case 14:
                    RadioResponseInfo radioResponseInfo13 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getVoiceRadioTechnologyResponse(radioResponseInfo13, readInt5);
                    return true;
                case 15:
                    RadioResponseInfo radioResponseInfo14 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    RegStateResult regStateResult2 = (RegStateResult) parcel.readTypedObject(RegStateResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    getVoiceRegistrationStateResponse(radioResponseInfo14, regStateResult2);
                    return true;
                case 16:
                    RadioResponseInfo radioResponseInfo15 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isNrDualConnectivityEnabledResponse(radioResponseInfo15, readBoolean3);
                    return true;
                case 17:
                    RadioResponseInfo radioResponseInfo16 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAllowedNetworkTypesBitmapResponse(radioResponseInfo16);
                    return true;
                case 18:
                    RadioResponseInfo radioResponseInfo17 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setBandModeResponse(radioResponseInfo17);
                    return true;
                case 19:
                    RadioResponseInfo radioResponseInfo18 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setBarringPasswordResponse(radioResponseInfo18);
                    return true;
                case 20:
                    RadioResponseInfo radioResponseInfo19 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaRoamingPreferenceResponse(radioResponseInfo19);
                    return true;
                case 21:
                    RadioResponseInfo radioResponseInfo20 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCellInfoListRateResponse(radioResponseInfo20);
                    return true;
                case 22:
                    RadioResponseInfo radioResponseInfo21 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIndicationFilterResponse(radioResponseInfo21);
                    return true;
                case 23:
                    RadioResponseInfo radioResponseInfo22 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLinkCapacityReportingCriteriaResponse(radioResponseInfo22);
                    return true;
                case 24:
                    RadioResponseInfo radioResponseInfo23 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setLocationUpdatesResponse(radioResponseInfo23);
                    return true;
                case 25:
                    RadioResponseInfo radioResponseInfo24 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeAutomaticResponse(radioResponseInfo24);
                    return true;
                case 26:
                    RadioResponseInfo radioResponseInfo25 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNetworkSelectionModeManualResponse(radioResponseInfo25);
                    return true;
                case 27:
                    RadioResponseInfo radioResponseInfo26 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNrDualConnectivityStateResponse(radioResponseInfo26);
                    return true;
                case 28:
                    RadioResponseInfo radioResponseInfo27 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSignalStrengthReportingCriteriaResponse(radioResponseInfo27);
                    return true;
                case 29:
                    RadioResponseInfo radioResponseInfo28 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSuppServiceNotificationsResponse(radioResponseInfo28);
                    return true;
                case 30:
                    RadioResponseInfo radioResponseInfo29 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSystemSelectionChannelsResponse(radioResponseInfo29);
                    return true;
                case 31:
                    RadioResponseInfo radioResponseInfo30 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startNetworkScanResponse(radioResponseInfo30);
                    return true;
                case 32:
                    RadioResponseInfo radioResponseInfo31 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopNetworkScanResponse(radioResponseInfo31);
                    return true;
                case 33:
                    RadioResponseInfo radioResponseInfo32 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyNetworkDepersonalizationResponse(radioResponseInfo32, readInt6);
                    return true;
                case 34:
                    RadioResponseInfo radioResponseInfo33 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUsageSettingResponse(radioResponseInfo33);
                    return true;
                case 35:
                    RadioResponseInfo radioResponseInfo34 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getUsageSettingResponse(radioResponseInfo34, readInt7);
                    return true;
                case 36:
                    RadioResponseInfo radioResponseInfo35 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    EmergencyRegResult emergencyRegResult = (EmergencyRegResult) parcel.readTypedObject(EmergencyRegResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    setEmergencyModeResponse(radioResponseInfo35, emergencyRegResult);
                    return true;
                case 37:
                    RadioResponseInfo radioResponseInfo36 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    triggerEmergencyNetworkScanResponse(radioResponseInfo36);
                    return true;
                case 38:
                    RadioResponseInfo radioResponseInfo37 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    exitEmergencyModeResponse(radioResponseInfo37);
                    return true;
                case 39:
                    RadioResponseInfo radioResponseInfo38 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelEmergencyNetworkScanResponse(radioResponseInfo38);
                    return true;
                case 40:
                    RadioResponseInfo radioResponseInfo39 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNullCipherAndIntegrityEnabledResponse(radioResponseInfo39);
                    return true;
                case 41:
                    RadioResponseInfo radioResponseInfo40 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isNullCipherAndIntegrityEnabledResponse(radioResponseInfo40, readBoolean4);
                    return true;
                case 42:
                    RadioResponseInfo radioResponseInfo41 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isN1ModeEnabledResponse(radioResponseInfo41, readBoolean5);
                    return true;
                case 43:
                    RadioResponseInfo radioResponseInfo42 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setN1ModeEnabledResponse(radioResponseInfo42);
                    return true;
                case 44:
                    RadioResponseInfo radioResponseInfo43 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isCellularIdentifierTransparencyEnabledResponse(radioResponseInfo43, readBoolean6);
                    return true;
                case 45:
                    RadioResponseInfo radioResponseInfo44 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCellularIdentifierTransparencyEnabledResponse(radioResponseInfo44);
                    return true;
                case 46:
                    RadioResponseInfo radioResponseInfo45 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSecurityAlgorithmsUpdatedEnabledResponse(radioResponseInfo45);
                    return true;
                case 47:
                    RadioResponseInfo radioResponseInfo46 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isSecurityAlgorithmsUpdatedEnabledResponse(radioResponseInfo46, readBoolean7);
                    return true;
                case 48:
                    RadioResponseInfo radioResponseInfo47 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSatellitePlmnResponse(radioResponseInfo47);
                    return true;
                case 49:
                    RadioResponseInfo radioResponseInfo48 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSatelliteEnabledForCarrierResponse(radioResponseInfo48);
                    return true;
                case 50:
                    RadioResponseInfo radioResponseInfo49 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isSatelliteEnabledForCarrierResponse(radioResponseInfo49, readBoolean8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioNetworkResponse {
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

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void acknowledgeRequest(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAllowedNetworkTypesBitmapResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getAvailableBandModesResponse(RadioResponseInfo radioResponseInfo, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeIntArray(iArr);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAvailableBandModesResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getAvailableNetworksResponse(RadioResponseInfo radioResponseInfo, OperatorInfo[] operatorInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(operatorInfoArr, 0);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAvailableNetworksResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getBarringInfoResponse(RadioResponseInfo radioResponseInfo, CellIdentity cellIdentity, BarringInfo[] barringInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(cellIdentity, 0);
                    obtain.writeTypedArray(barringInfoArr, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getBarringInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaRoamingPreferenceResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getCellInfoListResponse(RadioResponseInfo radioResponseInfo, CellInfo[] cellInfoArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(cellInfoArr, 0);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCellInfoListResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getDataRegistrationStateResponse(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(regStateResult, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getDataRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getImsRegistrationStateResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImsRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getNetworkSelectionModeResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getNetworkSelectionModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getOperatorResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getOperatorResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getSignalStrengthResponse(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(signalStrength, 0);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSignalStrengthResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getSystemSelectionChannelsResponse(RadioResponseInfo radioResponseInfo, RadioAccessSpecifier[] radioAccessSpecifierArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedArray(radioAccessSpecifierArr, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSystemSelectionChannelsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getVoiceRadioTechnologyResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getVoiceRadioTechnologyResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getVoiceRegistrationStateResponse(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(regStateResult, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getVoiceRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isNrDualConnectivityEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isNrDualConnectivityEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(17, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setAllowedNetworkTypesBitmapResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setBandModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(18, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setBandModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setBarringPasswordResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(19, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setBarringPasswordResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(20, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCdmaRoamingPreferenceResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setCellInfoListRateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(21, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCellInfoListRateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setIndicationFilterResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(22, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setIndicationFilterResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setLinkCapacityReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(23, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setLinkCapacityReportingCriteriaResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setLocationUpdatesResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(24, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setLocationUpdatesResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(25, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNetworkSelectionModeAutomaticResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setNetworkSelectionModeManualResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(26, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNetworkSelectionModeManualResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setNrDualConnectivityStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(27, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNrDualConnectivityStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSignalStrengthReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(28, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalStrengthReportingCriteriaResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSuppServiceNotificationsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(29, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSuppServiceNotificationsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSystemSelectionChannelsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(30, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSystemSelectionChannelsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void startNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(31, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startNetworkScanResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void stopNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(32, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopNetworkScanResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void supplyNetworkDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(33, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyNetworkDepersonalizationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setUsageSettingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(34, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setUsageSettingResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void getUsageSettingResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(35, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getUsageSettingResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setEmergencyModeResponse(RadioResponseInfo radioResponseInfo, EmergencyRegResult emergencyRegResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(emergencyRegResult, 0);
                    if (this.mRemote.transact(36, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setEmergencyModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void triggerEmergencyNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(37, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method triggerEmergencyNetworkScanResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void exitEmergencyModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(38, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method exitEmergencyModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void cancelEmergencyNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(39, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cancelEmergencyNetworkScanResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setNullCipherAndIntegrityEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(40, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNullCipherAndIntegrityEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isNullCipherAndIntegrityEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(41, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isNullCipherAndIntegrityEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isN1ModeEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(42, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isN1ModeEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setN1ModeEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(43, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setN1ModeEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isCellularIdentifierTransparencyEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(44, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isCellularIdentifierTransparencyEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setCellularIdentifierTransparencyEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(45, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCellularIdentifierTransparencyEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSecurityAlgorithmsUpdatedEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(46, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSecurityAlgorithmsUpdatedEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isSecurityAlgorithmsUpdatedEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(47, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isSecurityAlgorithmsUpdatedEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSatellitePlmnResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(48, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSatellitePlmnResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void setSatelliteEnabledForCarrierResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(49, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSatelliteEnabledForCarrierResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
            public void isSatelliteEnabledForCarrierResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(50, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isSatelliteEnabledForCarrierResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.network.IRadioNetworkResponse
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

            @Override // android.hardware.radio.network.IRadioNetworkResponse
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
