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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISehRadioSatelliteResponse)) {
                return (ISehRadioSatelliteResponse) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getSerialNumberResponse(sehRadioResponseInfo18, string);
                    return true;
                case 19:
                    SehRadioResponseInfo sehRadioResponseInfo19 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAbsoluteRfChannelNumberResponse(sehRadioResponseInfo19, i3);
                    return true;
                case 20:
                    SehRadioResponseInfo sehRadioResponseInfo20 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getTxPowerResponse(sehRadioResponseInfo20, i4);
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
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSatelliteIdResponse(sehRadioResponseInfo26, i5);
                    return true;
                case 27:
                    SehRadioResponseInfo sehRadioResponseInfo27 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDsiConfigResponse(sehRadioResponseInfo27);
                    return true;
                case 28:
                    SehRadioResponseInfo sehRadioResponseInfo28 = (SehRadioResponseInfo) parcel.readTypedObject(SehRadioResponseInfo.CREATOR);
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendRawAtCommandResponse(sehRadioResponseInfo28, string2);
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
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getIotModeResponse(sehRadioResponseInfo33, i6);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method answerResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void dialResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method dialResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getCallEndReasonResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatCallEndReason sehSatCallEndReason) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSatCallEndReason, 0);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallEndReasonResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getCallStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatCall[] sehSatCallArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedArray(sehSatCallArr, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallStateResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void hangupResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangupResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void startDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startDtmfResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void stopDtmfResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopDtmfResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatRegStateResult sehSatRegStateResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSatRegStateResult, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getSignalStrengthResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSignalStrength sehSatSignalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSatSignalStrength, 0);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSignalStrengthResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setNetworkQueryModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setNetworkQueryModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setSignalStrengthReportResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalStrengthReportResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setSignalThresholdReportResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSignalThresholdReportResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setPowerResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPowerResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setGpsInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setGpsInfoResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setImsiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImsiResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendIccSimAuthenticationResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendIccSimAuthenticationResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setImeiResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setImeiResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getSerialNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSerialNumberResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getAbsoluteRfChannelNumberResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAbsoluteRfChannelNumberResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getTxPowerResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getTxPowerResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendSmsResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSatSendSmsResult, 0);
                    if (this.mRemote.transact(21, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSmsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendSMSExpectMoreResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatSendSmsResult sehSatSendSmsResult) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSatSendSmsResult, 0);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendSMSExpectMoreResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setSmscAddressResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(23, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSmscAddressResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendLocationUserPermitResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(24, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendLocationUserPermitResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendLocationDataResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(25, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendLocationDataResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getSatelliteIdResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(26, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSatelliteIdResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setDsiConfigResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(27, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setDsiConfigResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void sendRawAtCommandResponse(SehRadioResponseInfo sehRadioResponseInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(28, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendRawAtCommandResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void startNetworkSearchResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(29, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startNetworkSearchResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void cleanupNetworkInfoResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(30, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cleanupNetworkInfoResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void enableIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(31, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableIotModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void disableIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(32, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method disableIotModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getIotModeResponse(SehRadioResponseInfo sehRadioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(33, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIotModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void getIotRegistrationStateResponse(SehRadioResponseInfo sehRadioResponseInfo, SehSatIotRegState sehSatIotRegState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    parcelObtain.writeTypedObject(sehSatIotRegState, 0);
                    if (this.mRemote.transact(34, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIotRegistrationStateResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
            public void setCombinedConfigModeResponse(SehRadioResponseInfo sehRadioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(sehRadioResponseInfo, 0);
                    if (this.mRemote.transact(35, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCombinedConfigModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
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

            @Override // vendor.samsung.hardware.radio.satellite.ISehRadioSatelliteResponse
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
