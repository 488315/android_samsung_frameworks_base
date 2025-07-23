package vendor.samsung.hardware.radio.satellite;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import vendor.samsung.hardware.radio.SehRadioResponseInfo;

/* loaded from: classes6.dex */
public interface ISehRadioSatelliteResponse extends IInterface {
    public static final String DESCRIPTOR = "vendor$samsung$hardware$radio$satellite$ISehRadioSatelliteResponse".replace('$', '.');
    public static final String HASH = "90863b100bf8b0ec3c45dec007d73ce7f04d8850";
    public static final int VERSION = 1;

    void answerResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void cleanupNetworkInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void dialResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void disableIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void enableIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void getAbsoluteRfChannelNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void getCallEndReasonResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatCallEndReason sehSatCallEndReason) throws RemoteException;

    void getCallStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatCall[] sehSatCallArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void getIotRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatIotRegState sehSatIotRegState) throws RemoteException;

    void getRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatRegStateResult sehSatRegStateResult) throws RemoteException;

    void getSatelliteIdResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void getSerialNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException;

    void getSignalStrengthResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSignalStrength sehSatSignalStrength) throws RemoteException;

    void getTxPowerResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException;

    void hangupResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void sendIccSimAuthenticationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void sendLocationDataResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void sendLocationUserPermitResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void sendRawAtCommandResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException;

    void sendSMSExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult) throws RemoteException;

    void sendSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult) throws RemoteException;

    void setCombinedConfigModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setDsiConfigResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setGpsInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setImeiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setImsiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setNetworkQueryModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setPowerResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setSignalStrengthReportResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setSignalThresholdReportResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void setSmscAddressResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void startDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void startNetworkSearchResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    void stopDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException;

    public static class Default implements ISehRadioSatelliteResponse {
        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void answerResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void cleanupNetworkInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void dialResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void disableIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void enableIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getAbsoluteRfChannelNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getCallEndReasonResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatCallEndReason sehSatCallEndReason) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getCallStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatCall[] sehSatCallArr) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getIotRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatIotRegState sehSatIotRegState) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatRegStateResult sehSatRegStateResult) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getSatelliteIdResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getSerialNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getSignalStrengthResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSignalStrength sehSatSignalStrength) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void getTxPowerResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void hangupResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendIccSimAuthenticationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendLocationDataResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendLocationUserPermitResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendRawAtCommandResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendSMSExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void sendSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setCombinedConfigModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setDsiConfigResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setGpsInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setImeiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setImsiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setNetworkQueryModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setPowerResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setSignalStrengthReportResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setSignalThresholdReportResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void setSmscAddressResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void startDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void startNetworkSearchResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public void stopDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
        }

        @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements ISehRadioSatelliteResponse {
        static final int TRANSACTION_answerResponse = 1;
        static final int TRANSACTION_cleanupNetworkInfoResponse = 30;
        static final int TRANSACTION_dialResponse = 2;
        static final int TRANSACTION_disableIotModeResponse = 32;
        static final int TRANSACTION_enableIotModeResponse = 31;
        static final int TRANSACTION_getAbsoluteRfChannelNumberResponse = 19;
        static final int TRANSACTION_getCallEndReasonResponse = 3;
        static final int TRANSACTION_getCallStateResponse = 4;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getIotModeResponse = 33;
        static final int TRANSACTION_getIotRegistrationStateResponse = 34;
        static final int TRANSACTION_getRegistrationStateResponse = 8;
        static final int TRANSACTION_getSatelliteIdResponse = 26;
        static final int TRANSACTION_getSerialNumberResponse = 18;
        static final int TRANSACTION_getSignalStrengthResponse = 9;
        static final int TRANSACTION_getTxPowerResponse = 20;
        static final int TRANSACTION_hangupResponse = 5;
        static final int TRANSACTION_sendIccSimAuthenticationResponse = 16;
        static final int TRANSACTION_sendLocationDataResponse = 25;
        static final int TRANSACTION_sendLocationUserPermitResponse = 24;
        static final int TRANSACTION_sendRawAtCommandResponse = 28;
        static final int TRANSACTION_sendSMSExpectMoreResponse = 22;
        static final int TRANSACTION_sendSmsResponse = 21;
        static final int TRANSACTION_setCombinedConfigModeResponse = 35;
        static final int TRANSACTION_setDsiConfigResponse = 27;
        static final int TRANSACTION_setGpsInfoResponse = 14;
        static final int TRANSACTION_setImeiResponse = 17;
        static final int TRANSACTION_setImsiResponse = 15;
        static final int TRANSACTION_setNetworkQueryModeResponse = 10;
        static final int TRANSACTION_setPowerResponse = 13;
        static final int TRANSACTION_setSignalStrengthReportResponse = 11;
        static final int TRANSACTION_setSignalThresholdReportResponse = 12;
        static final int TRANSACTION_setSmscAddressResponse = 23;
        static final int TRANSACTION_startDtmfResponse = 6;
        static final int TRANSACTION_startNetworkSearchResponse = 29;
        static final int TRANSACTION_stopDtmfResponse = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static ISehRadioSatelliteResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISehRadioSatelliteResponse)) {
                return (ISehRadioSatelliteResponse) queryLocalInterface;
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
                    answerResponse(sehRadioResponseInfo);
                    return true;
                case 2:
                    SehRadioResponseInfo sehRadioResponseInfo2 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    dialResponse(sehRadioResponseInfo2);
                    return true;
                case 3:
                    SehRadioResponseInfo sehRadioResponseInfo3 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatCallEndReason sehSatCallEndReason = (SehSatCallEndReason) parcel.readTypedObject(SehSatCallEndReason.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCallEndReasonResponse(sehRadioResponseInfo3, sehSatCallEndReason);
                    return true;
                case 4:
                    SehRadioResponseInfo sehRadioResponseInfo4 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatCall[] sehSatCallArr = (SehSatCall[]) parcel.createTypedArray(SehSatCall.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCallStateResponse(sehRadioResponseInfo4, sehSatCallArr);
                    return true;
                case 5:
                    SehRadioResponseInfo sehRadioResponseInfo5 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    hangupResponse(sehRadioResponseInfo5);
                    return true;
                case 6:
                    SehRadioResponseInfo sehRadioResponseInfo6 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startDtmfResponse(sehRadioResponseInfo6);
                    return true;
                case 7:
                    SehRadioResponseInfo sehRadioResponseInfo7 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopDtmfResponse(sehRadioResponseInfo7);
                    return true;
                case 8:
                    SehRadioResponseInfo sehRadioResponseInfo8 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatRegStateResult sehSatRegStateResult = (SehSatRegStateResult) parcel.readTypedObject(SehSatRegStateResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    getRegistrationStateResponse(sehRadioResponseInfo8, sehSatRegStateResult);
                    return true;
                case 9:
                    SehRadioResponseInfo sehRadioResponseInfo9 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatSignalStrength sehSatSignalStrength = (SehSatSignalStrength) parcel.readTypedObject(SehSatSignalStrength.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSignalStrengthResponse(sehRadioResponseInfo9, sehSatSignalStrength);
                    return true;
                case 10:
                    SehRadioResponseInfo sehRadioResponseInfo10 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setNetworkQueryModeResponse(sehRadioResponseInfo10);
                    return true;
                case 11:
                    SehRadioResponseInfo sehRadioResponseInfo11 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSignalStrengthReportResponse(sehRadioResponseInfo11);
                    return true;
                case 12:
                    SehRadioResponseInfo sehRadioResponseInfo12 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSignalThresholdReportResponse(sehRadioResponseInfo12);
                    return true;
                case 13:
                    SehRadioResponseInfo sehRadioResponseInfo13 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPowerResponse(sehRadioResponseInfo13);
                    return true;
                case 14:
                    SehRadioResponseInfo sehRadioResponseInfo14 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setGpsInfoResponse(sehRadioResponseInfo14);
                    return true;
                case 15:
                    SehRadioResponseInfo sehRadioResponseInfo15 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setImsiResponse(sehRadioResponseInfo15);
                    return true;
                case 16:
                    SehRadioResponseInfo sehRadioResponseInfo16 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendIccSimAuthenticationResponse(sehRadioResponseInfo16);
                    return true;
                case 17:
                    SehRadioResponseInfo sehRadioResponseInfo17 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setImeiResponse(sehRadioResponseInfo17);
                    return true;
                case 18:
                    SehRadioResponseInfo sehRadioResponseInfo18 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getSerialNumberResponse(sehRadioResponseInfo18, readString);
                    return true;
                case 19:
                    SehRadioResponseInfo sehRadioResponseInfo19 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAbsoluteRfChannelNumberResponse(sehRadioResponseInfo19, readInt);
                    return true;
                case 20:
                    SehRadioResponseInfo sehRadioResponseInfo20 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getTxPowerResponse(sehRadioResponseInfo20, readInt2);
                    return true;
                case 21:
                    SehRadioResponseInfo sehRadioResponseInfo21 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatSendSmsResult sehSatSendSmsResult = (SehSatSendSmsResult) parcel.readTypedObject(SehSatSendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSmsResponse(sehRadioResponseInfo21, sehSatSendSmsResult);
                    return true;
                case 22:
                    SehRadioResponseInfo sehRadioResponseInfo22 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatSendSmsResult sehSatSendSmsResult2 = (SehSatSendSmsResult) parcel.readTypedObject(SehSatSendSmsResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSMSExpectMoreResponse(sehRadioResponseInfo22, sehSatSendSmsResult2);
                    return true;
                case 23:
                    SehRadioResponseInfo sehRadioResponseInfo23 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSmscAddressResponse(sehRadioResponseInfo23);
                    return true;
                case 24:
                    SehRadioResponseInfo sehRadioResponseInfo24 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendLocationUserPermitResponse(sehRadioResponseInfo24);
                    return true;
                case 25:
                    SehRadioResponseInfo sehRadioResponseInfo25 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendLocationDataResponse(sehRadioResponseInfo25);
                    return true;
                case 26:
                    SehRadioResponseInfo sehRadioResponseInfo26 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSatelliteIdResponse(sehRadioResponseInfo26, readInt3);
                    return true;
                case 27:
                    SehRadioResponseInfo sehRadioResponseInfo27 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDsiConfigResponse(sehRadioResponseInfo27);
                    return true;
                case 28:
                    SehRadioResponseInfo sehRadioResponseInfo28 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRawAtCommandResponse(sehRadioResponseInfo28, readString2);
                    return true;
                case 29:
                    SehRadioResponseInfo sehRadioResponseInfo29 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startNetworkSearchResponse(sehRadioResponseInfo29);
                    return true;
                case 30:
                    SehRadioResponseInfo sehRadioResponseInfo30 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cleanupNetworkInfoResponse(sehRadioResponseInfo30);
                    return true;
                case 31:
                    SehRadioResponseInfo sehRadioResponseInfo31 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    enableIotModeResponse(sehRadioResponseInfo31);
                    return true;
                case 32:
                    SehRadioResponseInfo sehRadioResponseInfo32 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    disableIotModeResponse(sehRadioResponseInfo32);
                    return true;
                case 33:
                    SehRadioResponseInfo sehRadioResponseInfo33 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getIotModeResponse(sehRadioResponseInfo33, readInt4);
                    return true;
                case 34:
                    SehRadioResponseInfo sehRadioResponseInfo34 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    SehSatIotRegState sehSatIotRegState = (SehSatIotRegState) parcel.readTypedObject(SehSatIotRegState.CREATOR);
                    parcel.enforceNoDataAvail();
                    getIotRegistrationStateResponse(sehRadioResponseInfo34, sehSatIotRegState);
                    return true;
                case 35:
                    SehRadioResponseInfo sehRadioResponseInfo35 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCombinedConfigModeResponse(sehRadioResponseInfo35);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISehRadioSatelliteResponse {
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void answerResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method answerResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void dialResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method dialResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getCallEndReasonResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatCallEndReason sehSatCallEndReason) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSatCallEndReason, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallEndReasonResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getCallStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatCall[] sehSatCallArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedArray(sehSatCallArr, 0);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void hangupResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangupResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void startDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startDtmfResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void stopDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopDtmfResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatRegStateResult sehSatRegStateResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSatRegStateResult, 0);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getSignalStrengthResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSignalStrength sehSatSignalStrength) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSatSignalStrength, 0);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSignalStrengthResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setNetworkQueryModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNetworkQueryModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setSignalStrengthReportResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalStrengthReportResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setSignalThresholdReportResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalThresholdReportResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setPowerResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPowerResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setGpsInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setGpsInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setImsiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImsiResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendIccSimAuthenticationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendIccSimAuthenticationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setImeiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(17, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImeiResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getSerialNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeString(str);
                    if (this.mRemote.transact(18, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSerialNumberResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getAbsoluteRfChannelNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(19, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAbsoluteRfChannelNumberResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getTxPowerResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(20, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getTxPowerResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSatSendSmsResult, 0);
                    if (this.mRemote.transact(21, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSmsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendSMSExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSatSendSmsResult, 0);
                    if (this.mRemote.transact(22, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSMSExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setSmscAddressResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(23, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSmscAddressResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendLocationUserPermitResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(24, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendLocationUserPermitResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendLocationDataResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(25, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendLocationDataResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getSatelliteIdResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(26, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSatelliteIdResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setDsiConfigResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(27, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDsiConfigResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendRawAtCommandResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeString(str);
                    if (this.mRemote.transact(28, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRawAtCommandResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void startNetworkSearchResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(29, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startNetworkSearchResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void cleanupNetworkInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(30, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cleanupNetworkInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void enableIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(31, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableIotModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void disableIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(32, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method disableIotModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(33, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIotModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getIotRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatIotRegState sehSatIotRegState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    obtain.writeTypedObject(sehSatIotRegState, 0);
                    if (this.mRemote.transact(34, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIotRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setCombinedConfigModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(35, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCombinedConfigModeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
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
