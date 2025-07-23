package android.hardware.radio.sim;

import android.hardware.radio.RadioResponseInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioSimResponse extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$sim$IRadioSimResponse".replace('$', '.');
    public static final String HASH = "fc1a19a4f86a58981158cc8d956763c9d8ace630";
    public static final int VERSION = 4;

    void acknowledgeRequest(int i) throws RemoteException;

    void areUiccApplicationsEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void changeIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void changeIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void enableUiccApplicationsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void getAllowedCarriersResponse(RadioResponseInfo radioResponseInfo, CarrierRestrictions carrierRestrictions, int i) throws RemoteException;

    @Deprecated
    void getCdmaSubscriptionResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4, String str5) throws RemoteException;

    @Deprecated
    void getCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getIccCardStatusResponse(RadioResponseInfo radioResponseInfo, CardStatus cardStatus) throws RemoteException;

    void getImsiForAppResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getSimPhonebookCapacityResponse(RadioResponseInfo radioResponseInfo, PhonebookCapacity phonebookCapacity) throws RemoteException;

    void getSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void iccCloseLogicalChannelResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void iccCloseLogicalChannelWithSessionInfoResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void iccIoForAppResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    void iccOpenLogicalChannelResponse(RadioResponseInfo radioResponseInfo, int i, byte[] bArr) throws RemoteException;

    void iccTransmitApduBasicChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    void iccTransmitApduLogicalChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    void reportStkServiceIsRunningResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void requestIccSimAuthenticationResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    void sendEnvelopeResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException;

    void sendEnvelopeWithStatusResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException;

    void sendTerminalResponseToSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setAllowedCarriersResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCarrierInfoForImsiEncryptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void setCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void setSimCardPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void setUiccSubscriptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void supplyIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void supplyIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void supplyIccPuk2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void supplyIccPukForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void supplySimDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException;

    void updateSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    public static class Default implements IRadioSimResponse {
        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void acknowledgeRequest(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void areUiccApplicationsEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void changeIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void changeIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void enableUiccApplicationsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getAllowedCarriersResponse(RadioResponseInfo radioResponseInfo, CarrierRestrictions carrierRestrictions, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getCdmaSubscriptionResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4, String str5) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getIccCardStatusResponse(RadioResponseInfo radioResponseInfo, CardStatus cardStatus) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getImsiForAppResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getSimPhonebookCapacityResponse(RadioResponseInfo radioResponseInfo, PhonebookCapacity phonebookCapacity) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void getSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccCloseLogicalChannelResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccCloseLogicalChannelWithSessionInfoResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccIoForAppResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccOpenLogicalChannelResponse(RadioResponseInfo radioResponseInfo, int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccTransmitApduBasicChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void iccTransmitApduLogicalChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void reportStkServiceIsRunningResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void requestIccSimAuthenticationResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void sendEnvelopeResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void sendEnvelopeWithStatusResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void sendTerminalResponseToSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setAllowedCarriersResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setCarrierInfoForImsiEncryptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setSimCardPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void setUiccSubscriptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplyIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplyIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplyIccPuk2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplyIccPukForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void supplySimDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public void updateSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSimResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioSimResponse {
        static final int TRANSACTION_acknowledgeRequest = 1;
        static final int TRANSACTION_areUiccApplicationsEnabledResponse = 2;
        static final int TRANSACTION_changeIccPin2ForAppResponse = 3;
        static final int TRANSACTION_changeIccPinForAppResponse = 4;
        static final int TRANSACTION_enableUiccApplicationsResponse = 5;
        static final int TRANSACTION_getAllowedCarriersResponse = 6;
        static final int TRANSACTION_getCdmaSubscriptionResponse = 7;
        static final int TRANSACTION_getCdmaSubscriptionSourceResponse = 8;
        static final int TRANSACTION_getFacilityLockForAppResponse = 9;
        static final int TRANSACTION_getIccCardStatusResponse = 10;
        static final int TRANSACTION_getImsiForAppResponse = 11;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSimPhonebookCapacityResponse = 12;
        static final int TRANSACTION_getSimPhonebookRecordsResponse = 13;
        static final int TRANSACTION_iccCloseLogicalChannelResponse = 14;
        static final int TRANSACTION_iccCloseLogicalChannelWithSessionInfoResponse = 36;
        static final int TRANSACTION_iccIoForAppResponse = 15;
        static final int TRANSACTION_iccOpenLogicalChannelResponse = 16;
        static final int TRANSACTION_iccTransmitApduBasicChannelResponse = 17;
        static final int TRANSACTION_iccTransmitApduLogicalChannelResponse = 18;
        static final int TRANSACTION_reportStkServiceIsRunningResponse = 19;
        static final int TRANSACTION_requestIccSimAuthenticationResponse = 20;
        static final int TRANSACTION_sendEnvelopeResponse = 21;
        static final int TRANSACTION_sendEnvelopeWithStatusResponse = 22;
        static final int TRANSACTION_sendTerminalResponseToSimResponse = 23;
        static final int TRANSACTION_setAllowedCarriersResponse = 24;
        static final int TRANSACTION_setCarrierInfoForImsiEncryptionResponse = 25;
        static final int TRANSACTION_setCdmaSubscriptionSourceResponse = 26;
        static final int TRANSACTION_setFacilityLockForAppResponse = 27;
        static final int TRANSACTION_setSimCardPowerResponse = 28;
        static final int TRANSACTION_setUiccSubscriptionResponse = 29;
        static final int TRANSACTION_supplyIccPin2ForAppResponse = 30;
        static final int TRANSACTION_supplyIccPinForAppResponse = 31;
        static final int TRANSACTION_supplyIccPuk2ForAppResponse = 32;
        static final int TRANSACTION_supplyIccPukForAppResponse = 33;
        static final int TRANSACTION_supplySimDepersonalizationResponse = 34;
        static final int TRANSACTION_updateSimPhonebookRecordsResponse = 35;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioSimResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioSimResponse)) {
                return (IRadioSimResponse) queryLocalInterface;
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    areUiccApplicationsEnabledResponse(radioResponseInfo, readBoolean);
                    return true;
                case 3:
                    RadioResponseInfo radioResponseInfo2 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeIccPin2ForAppResponse(radioResponseInfo2, readInt2);
                    return true;
                case 4:
                    RadioResponseInfo radioResponseInfo3 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeIccPinForAppResponse(radioResponseInfo3, readInt3);
                    return true;
                case 5:
                    RadioResponseInfo radioResponseInfo4 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    enableUiccApplicationsResponse(radioResponseInfo4);
                    return true;
                case 6:
                    RadioResponseInfo radioResponseInfo5 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    CarrierRestrictions carrierRestrictions = (CarrierRestrictions) parcel.readTypedObject(CarrierRestrictions.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAllowedCarriersResponse(radioResponseInfo5, carrierRestrictions, readInt4);
                    return true;
                case 7:
                    RadioResponseInfo radioResponseInfo6 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getCdmaSubscriptionResponse(radioResponseInfo6, readString, readString2, readString3, readString4, readString5);
                    return true;
                case 8:
                    RadioResponseInfo radioResponseInfo7 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaSubscriptionSourceResponse(radioResponseInfo7, readInt5);
                    return true;
                case 9:
                    RadioResponseInfo radioResponseInfo8 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getFacilityLockForAppResponse(radioResponseInfo8, readInt6);
                    return true;
                case 10:
                    RadioResponseInfo radioResponseInfo9 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    CardStatus cardStatus = (CardStatus) parcel.readTypedObject(CardStatus.CREATOR);
                    parcel.enforceNoDataAvail();
                    getIccCardStatusResponse(radioResponseInfo9, cardStatus);
                    return true;
                case 11:
                    RadioResponseInfo radioResponseInfo10 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getImsiForAppResponse(radioResponseInfo10, readString6);
                    return true;
                case 12:
                    RadioResponseInfo radioResponseInfo11 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    PhonebookCapacity phonebookCapacity = (PhonebookCapacity) parcel.readTypedObject(PhonebookCapacity.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSimPhonebookCapacityResponse(radioResponseInfo11, phonebookCapacity);
                    return true;
                case 13:
                    RadioResponseInfo radioResponseInfo12 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getSimPhonebookRecordsResponse(radioResponseInfo12);
                    return true;
                case 14:
                    RadioResponseInfo radioResponseInfo13 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccCloseLogicalChannelResponse(radioResponseInfo13);
                    return true;
                case 15:
                    RadioResponseInfo radioResponseInfo14 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    IccIoResult iccIoResult = (IccIoResult) parcel.readTypedObject(IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccIoForAppResponse(radioResponseInfo14, iccIoResult);
                    return true;
                case 16:
                    RadioResponseInfo radioResponseInfo15 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    iccOpenLogicalChannelResponse(radioResponseInfo15, readInt7, createByteArray);
                    return true;
                case 17:
                    RadioResponseInfo radioResponseInfo16 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    IccIoResult iccIoResult2 = (IccIoResult) parcel.readTypedObject(IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccTransmitApduBasicChannelResponse(radioResponseInfo16, iccIoResult2);
                    return true;
                case 18:
                    RadioResponseInfo radioResponseInfo17 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    IccIoResult iccIoResult3 = (IccIoResult) parcel.readTypedObject(IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccTransmitApduLogicalChannelResponse(radioResponseInfo17, iccIoResult3);
                    return true;
                case 19:
                    RadioResponseInfo radioResponseInfo18 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportStkServiceIsRunningResponse(radioResponseInfo18);
                    return true;
                case 20:
                    RadioResponseInfo radioResponseInfo19 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    IccIoResult iccIoResult4 = (IccIoResult) parcel.readTypedObject(IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestIccSimAuthenticationResponse(radioResponseInfo19, iccIoResult4);
                    return true;
                case 21:
                    RadioResponseInfo radioResponseInfo20 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendEnvelopeResponse(radioResponseInfo20, readString7);
                    return true;
                case 22:
                    RadioResponseInfo radioResponseInfo21 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    IccIoResult iccIoResult5 = (IccIoResult) parcel.readTypedObject(IccIoResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEnvelopeWithStatusResponse(radioResponseInfo21, iccIoResult5);
                    return true;
                case 23:
                    RadioResponseInfo radioResponseInfo22 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendTerminalResponseToSimResponse(radioResponseInfo22);
                    return true;
                case 24:
                    RadioResponseInfo radioResponseInfo23 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAllowedCarriersResponse(radioResponseInfo23);
                    return true;
                case 25:
                    RadioResponseInfo radioResponseInfo24 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCarrierInfoForImsiEncryptionResponse(radioResponseInfo24);
                    return true;
                case 26:
                    RadioResponseInfo radioResponseInfo25 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCdmaSubscriptionSourceResponse(radioResponseInfo25);
                    return true;
                case 27:
                    RadioResponseInfo radioResponseInfo26 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setFacilityLockForAppResponse(radioResponseInfo26, readInt8);
                    return true;
                case 28:
                    RadioResponseInfo radioResponseInfo27 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSimCardPowerResponse(radioResponseInfo27);
                    return true;
                case 29:
                    RadioResponseInfo radioResponseInfo28 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiccSubscriptionResponse(radioResponseInfo28);
                    return true;
                case 30:
                    RadioResponseInfo radioResponseInfo29 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyIccPin2ForAppResponse(radioResponseInfo29, readInt9);
                    return true;
                case 31:
                    RadioResponseInfo radioResponseInfo30 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyIccPinForAppResponse(radioResponseInfo30, readInt10);
                    return true;
                case 32:
                    RadioResponseInfo radioResponseInfo31 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyIccPuk2ForAppResponse(radioResponseInfo31, readInt11);
                    return true;
                case 33:
                    RadioResponseInfo radioResponseInfo32 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplyIccPukForAppResponse(radioResponseInfo32, readInt12);
                    return true;
                case 34:
                    RadioResponseInfo radioResponseInfo33 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    supplySimDepersonalizationResponse(radioResponseInfo33, readInt13, readInt14);
                    return true;
                case 35:
                    RadioResponseInfo radioResponseInfo34 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSimPhonebookRecordsResponse(radioResponseInfo34, readInt15);
                    return true;
                case 36:
                    RadioResponseInfo radioResponseInfo35 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccCloseLogicalChannelWithSessionInfoResponse(radioResponseInfo35);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioSimResponse {
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

            @Override // android.hardware.radio.sim.IRadioSimResponse
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

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void areUiccApplicationsEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method areUiccApplicationsEnabledResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void changeIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method changeIccPin2ForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void changeIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method changeIccPinForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void enableUiccApplicationsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableUiccApplicationsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getAllowedCarriersResponse(RadioResponseInfo radioResponseInfo, CarrierRestrictions carrierRestrictions, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(carrierRestrictions, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAllowedCarriersResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getCdmaSubscriptionResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4, String str5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaSubscriptionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaSubscriptionSourceResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getFacilityLockForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getIccCardStatusResponse(RadioResponseInfo radioResponseInfo, CardStatus cardStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(cardStatus, 0);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIccCardStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getImsiForAppResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImsiForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getSimPhonebookCapacityResponse(RadioResponseInfo radioResponseInfo, PhonebookCapacity phonebookCapacity) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(phonebookCapacity, 0);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimPhonebookCapacityResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void getSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimPhonebookRecordsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccCloseLogicalChannelResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccCloseLogicalChannelResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccIoForAppResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccIoForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccOpenLogicalChannelResponse(RadioResponseInfo radioResponseInfo, int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccOpenLogicalChannelResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccTransmitApduBasicChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (this.mRemote.transact(17, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccTransmitApduBasicChannelResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccTransmitApduLogicalChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (this.mRemote.transact(18, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccTransmitApduLogicalChannelResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void reportStkServiceIsRunningResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(19, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method reportStkServiceIsRunningResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void requestIccSimAuthenticationResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (this.mRemote.transact(20, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIccSimAuthenticationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void sendEnvelopeResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeString(str);
                    if (this.mRemote.transact(21, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendEnvelopeResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void sendEnvelopeWithStatusResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeTypedObject(iccIoResult, 0);
                    if (this.mRemote.transact(22, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendEnvelopeWithStatusResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void sendTerminalResponseToSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(23, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendTerminalResponseToSimResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setAllowedCarriersResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(24, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setAllowedCarriersResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setCarrierInfoForImsiEncryptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(25, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCarrierInfoForImsiEncryptionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(26, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCdmaSubscriptionSourceResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(27, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setFacilityLockForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setSimCardPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(28, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimCardPowerResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void setUiccSubscriptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(29, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setUiccSubscriptionResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplyIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(30, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPin2ForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplyIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(31, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPinForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplyIccPuk2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(32, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPuk2ForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplyIccPukForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(33, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPukForAppResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void supplySimDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(34, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplySimDepersonalizationResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void updateSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(35, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateSimPhonebookRecordsResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
            public void iccCloseLogicalChannelWithSessionInfoResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(36, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccCloseLogicalChannelWithSessionInfoResponse is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSimResponse
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

            @Override // android.hardware.radio.sim.IRadioSimResponse
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
