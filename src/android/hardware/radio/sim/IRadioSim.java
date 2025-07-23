package android.hardware.radio.sim;

import android.hardware.radio.sim.IRadioSimIndication;
import android.hardware.radio.sim.IRadioSimResponse;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioSim extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$sim$IRadioSim".replace('$', '.');
    public static final String HASH = "fc1a19a4f86a58981158cc8d956763c9d8ace630";
    public static final int VERSION = 4;

    void areUiccApplicationsEnabled(int i) throws RemoteException;

    void changeIccPin2ForApp(int i, String str, String str2, String str3) throws RemoteException;

    void changeIccPinForApp(int i, String str, String str2, String str3) throws RemoteException;

    void enableUiccApplications(int i, boolean z) throws RemoteException;

    void getAllowedCarriers(int i) throws RemoteException;

    @Deprecated
    void getCdmaSubscription(int i) throws RemoteException;

    @Deprecated
    void getCdmaSubscriptionSource(int i) throws RemoteException;

    void getFacilityLockForApp(int i, String str, String str2, int i2, String str3) throws RemoteException;

    void getIccCardStatus(int i) throws RemoteException;

    void getImsiForApp(int i, String str) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getSimPhonebookCapacity(int i) throws RemoteException;

    void getSimPhonebookRecords(int i) throws RemoteException;

    @Deprecated
    void iccCloseLogicalChannel(int i, int i2) throws RemoteException;

    void iccCloseLogicalChannelWithSessionInfo(int i, SessionInfo sessionInfo) throws RemoteException;

    void iccIoForApp(int i, IccIo iccIo) throws RemoteException;

    void iccOpenLogicalChannel(int i, String str, int i2) throws RemoteException;

    void iccTransmitApduBasicChannel(int i, SimApdu simApdu) throws RemoteException;

    void iccTransmitApduLogicalChannel(int i, SimApdu simApdu) throws RemoteException;

    void reportStkServiceIsRunning(int i) throws RemoteException;

    void requestIccSimAuthentication(int i, int i2, String str, String str2) throws RemoteException;

    void responseAcknowledgement() throws RemoteException;

    void sendEnvelope(int i, String str) throws RemoteException;

    void sendEnvelopeWithStatus(int i, String str) throws RemoteException;

    void sendTerminalResponseToSim(int i, String str) throws RemoteException;

    void setAllowedCarriers(int i, CarrierRestrictions carrierRestrictions, int i2) throws RemoteException;

    void setCarrierInfoForImsiEncryption(int i, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException;

    @Deprecated
    void setCdmaSubscriptionSource(int i, int i2) throws RemoteException;

    void setFacilityLockForApp(int i, String str, boolean z, String str2, int i2, String str3) throws RemoteException;

    void setResponseFunctions(IRadioSimResponse iRadioSimResponse, IRadioSimIndication iRadioSimIndication) throws RemoteException;

    void setSimCardPower(int i, int i2) throws RemoteException;

    @Deprecated
    void setUiccSubscription(int i, SelectUiccSub selectUiccSub) throws RemoteException;

    void supplyIccPin2ForApp(int i, String str, String str2) throws RemoteException;

    void supplyIccPinForApp(int i, String str, String str2) throws RemoteException;

    void supplyIccPuk2ForApp(int i, String str, String str2, String str3) throws RemoteException;

    void supplyIccPukForApp(int i, String str, String str2, String str3) throws RemoteException;

    void supplySimDepersonalization(int i, int i2, String str) throws RemoteException;

    void updateSimPhonebookRecords(int i, PhonebookRecordInfo phonebookRecordInfo) throws RemoteException;

    public static class Default implements IRadioSim {
        @Override // android.hardware.radio.sim.IRadioSim
        public void areUiccApplicationsEnabled(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void changeIccPin2ForApp(int i, String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void changeIccPinForApp(int i, String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void enableUiccApplications(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void getAllowedCarriers(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void getCdmaSubscription(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void getCdmaSubscriptionSource(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void getFacilityLockForApp(int i, String str, String str2, int i2, String str3) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void getIccCardStatus(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void getImsiForApp(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void getSimPhonebookCapacity(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void getSimPhonebookRecords(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void iccCloseLogicalChannel(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void iccCloseLogicalChannelWithSessionInfo(int i, SessionInfo sessionInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void iccIoForApp(int i, IccIo iccIo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void iccOpenLogicalChannel(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void iccTransmitApduBasicChannel(int i, SimApdu simApdu) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void iccTransmitApduLogicalChannel(int i, SimApdu simApdu) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void reportStkServiceIsRunning(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void requestIccSimAuthentication(int i, int i2, String str, String str2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void responseAcknowledgement() throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void sendEnvelope(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void sendEnvelopeWithStatus(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void sendTerminalResponseToSim(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void setAllowedCarriers(int i, CarrierRestrictions carrierRestrictions, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void setCarrierInfoForImsiEncryption(int i, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void setCdmaSubscriptionSource(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void setFacilityLockForApp(int i, String str, boolean z, String str2, int i2, String str3) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void setResponseFunctions(IRadioSimResponse iRadioSimResponse, IRadioSimIndication iRadioSimIndication) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void setSimCardPower(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void setUiccSubscription(int i, SelectUiccSub selectUiccSub) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void supplyIccPin2ForApp(int i, String str, String str2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void supplyIccPinForApp(int i, String str, String str2) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void supplyIccPuk2ForApp(int i, String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void supplyIccPukForApp(int i, String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void supplySimDepersonalization(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public void updateSimPhonebookRecords(int i, PhonebookRecordInfo phonebookRecordInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.sim.IRadioSim
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioSim {
        static final int TRANSACTION_areUiccApplicationsEnabled = 1;
        static final int TRANSACTION_changeIccPin2ForApp = 2;
        static final int TRANSACTION_changeIccPinForApp = 3;
        static final int TRANSACTION_enableUiccApplications = 4;
        static final int TRANSACTION_getAllowedCarriers = 5;
        static final int TRANSACTION_getCdmaSubscription = 6;
        static final int TRANSACTION_getCdmaSubscriptionSource = 7;
        static final int TRANSACTION_getFacilityLockForApp = 8;
        static final int TRANSACTION_getIccCardStatus = 9;
        static final int TRANSACTION_getImsiForApp = 10;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getSimPhonebookCapacity = 11;
        static final int TRANSACTION_getSimPhonebookRecords = 12;
        static final int TRANSACTION_iccCloseLogicalChannel = 13;
        static final int TRANSACTION_iccCloseLogicalChannelWithSessionInfo = 37;
        static final int TRANSACTION_iccIoForApp = 14;
        static final int TRANSACTION_iccOpenLogicalChannel = 15;
        static final int TRANSACTION_iccTransmitApduBasicChannel = 16;
        static final int TRANSACTION_iccTransmitApduLogicalChannel = 17;
        static final int TRANSACTION_reportStkServiceIsRunning = 18;
        static final int TRANSACTION_requestIccSimAuthentication = 19;
        static final int TRANSACTION_responseAcknowledgement = 20;
        static final int TRANSACTION_sendEnvelope = 21;
        static final int TRANSACTION_sendEnvelopeWithStatus = 22;
        static final int TRANSACTION_sendTerminalResponseToSim = 23;
        static final int TRANSACTION_setAllowedCarriers = 24;
        static final int TRANSACTION_setCarrierInfoForImsiEncryption = 25;
        static final int TRANSACTION_setCdmaSubscriptionSource = 26;
        static final int TRANSACTION_setFacilityLockForApp = 27;
        static final int TRANSACTION_setResponseFunctions = 28;
        static final int TRANSACTION_setSimCardPower = 29;
        static final int TRANSACTION_setUiccSubscription = 30;
        static final int TRANSACTION_supplyIccPin2ForApp = 31;
        static final int TRANSACTION_supplyIccPinForApp = 32;
        static final int TRANSACTION_supplyIccPuk2ForApp = 33;
        static final int TRANSACTION_supplyIccPukForApp = 34;
        static final int TRANSACTION_supplySimDepersonalization = 35;
        static final int TRANSACTION_updateSimPhonebookRecords = 36;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioSim asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioSim)) {
                return (IRadioSim) queryLocalInterface;
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
                    areUiccApplicationsEnabled(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    changeIccPin2ForApp(readInt2, readString, readString2, readString3);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    changeIccPinForApp(readInt3, readString4, readString5, readString6);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableUiccApplications(readInt4, readBoolean);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAllowedCarriers(readInt5);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaSubscription(readInt6);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaSubscriptionSource(readInt7);
                    return true;
                case 8:
                    int readInt8 = parcel.readInt();
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getFacilityLockForApp(readInt8, readString7, readString8, readInt9, readString9);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getIccCardStatus(readInt10);
                    return true;
                case 10:
                    int readInt11 = parcel.readInt();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getImsiForApp(readInt11, readString10);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimPhonebookCapacity(readInt12);
                    return true;
                case 12:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimPhonebookRecords(readInt13);
                    return true;
                case 13:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    iccCloseLogicalChannel(readInt14, readInt15);
                    return true;
                case 14:
                    int readInt16 = parcel.readInt();
                    IccIo iccIo = (IccIo) parcel.readTypedObject(IccIo.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccIoForApp(readInt16, iccIo);
                    return true;
                case 15:
                    int readInt17 = parcel.readInt();
                    String readString11 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    iccOpenLogicalChannel(readInt17, readString11, readInt18);
                    return true;
                case 16:
                    int readInt19 = parcel.readInt();
                    SimApdu simApdu = (SimApdu) parcel.readTypedObject(SimApdu.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccTransmitApduBasicChannel(readInt19, simApdu);
                    return true;
                case 17:
                    int readInt20 = parcel.readInt();
                    SimApdu simApdu2 = (SimApdu) parcel.readTypedObject(SimApdu.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccTransmitApduLogicalChannel(readInt20, simApdu2);
                    return true;
                case 18:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportStkServiceIsRunning(readInt21);
                    return true;
                case 19:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestIccSimAuthentication(readInt22, readInt23, readString12, readString13);
                    return true;
                case 20:
                    responseAcknowledgement();
                    return true;
                case 21:
                    int readInt24 = parcel.readInt();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendEnvelope(readInt24, readString14);
                    return true;
                case 22:
                    int readInt25 = parcel.readInt();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendEnvelopeWithStatus(readInt25, readString15);
                    return true;
                case 23:
                    int readInt26 = parcel.readInt();
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendTerminalResponseToSim(readInt26, readString16);
                    return true;
                case 24:
                    int readInt27 = parcel.readInt();
                    CarrierRestrictions carrierRestrictions = (CarrierRestrictions) parcel.readTypedObject(CarrierRestrictions.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAllowedCarriers(readInt27, carrierRestrictions, readInt28);
                    return true;
                case 25:
                    int readInt29 = parcel.readInt();
                    ImsiEncryptionInfo imsiEncryptionInfo = (ImsiEncryptionInfo) parcel.readTypedObject(ImsiEncryptionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCarrierInfoForImsiEncryption(readInt29, imsiEncryptionInfo);
                    return true;
                case 26:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCdmaSubscriptionSource(readInt30, readInt31);
                    return true;
                case 27:
                    int readInt32 = parcel.readInt();
                    String readString17 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    String readString18 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setFacilityLockForApp(readInt32, readString17, readBoolean2, readString18, readInt33, readString19);
                    return true;
                case 28:
                    IRadioSimResponse asInterface = IRadioSimResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioSimIndication asInterface2 = IRadioSimIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(asInterface, asInterface2);
                    return true;
                case 29:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSimCardPower(readInt34, readInt35);
                    return true;
                case 30:
                    int readInt36 = parcel.readInt();
                    SelectUiccSub selectUiccSub = (SelectUiccSub) parcel.readTypedObject(SelectUiccSub.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiccSubscription(readInt36, selectUiccSub);
                    return true;
                case 31:
                    int readInt37 = parcel.readInt();
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPin2ForApp(readInt37, readString20, readString21);
                    return true;
                case 32:
                    int readInt38 = parcel.readInt();
                    String readString22 = parcel.readString();
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPinForApp(readInt38, readString22, readString23);
                    return true;
                case 33:
                    int readInt39 = parcel.readInt();
                    String readString24 = parcel.readString();
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPuk2ForApp(readInt39, readString24, readString25, readString26);
                    return true;
                case 34:
                    int readInt40 = parcel.readInt();
                    String readString27 = parcel.readString();
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPukForApp(readInt40, readString27, readString28, readString29);
                    return true;
                case 35:
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplySimDepersonalization(readInt41, readInt42, readString30);
                    return true;
                case 36:
                    int readInt43 = parcel.readInt();
                    PhonebookRecordInfo phonebookRecordInfo = (PhonebookRecordInfo) parcel.readTypedObject(PhonebookRecordInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSimPhonebookRecords(readInt43, phonebookRecordInfo);
                    return true;
                case 37:
                    int readInt44 = parcel.readInt();
                    SessionInfo sessionInfo = (SessionInfo) parcel.readTypedObject(SessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccCloseLogicalChannelWithSessionInfo(readInt44, sessionInfo);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioSim {
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

            @Override // android.hardware.radio.sim.IRadioSim
            public void areUiccApplicationsEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method areUiccApplicationsEnabled is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void changeIccPin2ForApp(int i, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method changeIccPin2ForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void changeIccPinForApp(int i, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method changeIccPinForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void enableUiccApplications(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (this.mRemote.transact(4, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableUiccApplications is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getAllowedCarriers(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(5, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAllowedCarriers is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getCdmaSubscription(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(6, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaSubscription is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getCdmaSubscriptionSource(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(7, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaSubscriptionSource is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getFacilityLockForApp(int i, String str, String str2, int i2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    if (this.mRemote.transact(8, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getFacilityLockForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getIccCardStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(9, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIccCardStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getImsiForApp(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(10, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImsiForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getSimPhonebookCapacity(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(11, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimPhonebookCapacity is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getSimPhonebookRecords(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(12, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimPhonebookRecords is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccCloseLogicalChannel(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(13, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccCloseLogicalChannel is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccIoForApp(int i, IccIo iccIo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(iccIo, 0);
                    if (this.mRemote.transact(14, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccIoForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccOpenLogicalChannel(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(15, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccOpenLogicalChannel is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccTransmitApduBasicChannel(int i, SimApdu simApdu) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(simApdu, 0);
                    if (this.mRemote.transact(16, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccTransmitApduBasicChannel is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccTransmitApduLogicalChannel(int i, SimApdu simApdu) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(simApdu, 0);
                    if (this.mRemote.transact(17, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccTransmitApduLogicalChannel is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void reportStkServiceIsRunning(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (this.mRemote.transact(18, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method reportStkServiceIsRunning is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void requestIccSimAuthentication(int i, int i2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (this.mRemote.transact(19, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIccSimAuthentication is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void responseAcknowledgement() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(20, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void sendEnvelope(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(21, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendEnvelope is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void sendEnvelopeWithStatus(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(22, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendEnvelopeWithStatus is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void sendTerminalResponseToSim(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (this.mRemote.transact(23, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendTerminalResponseToSim is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setAllowedCarriers(int i, CarrierRestrictions carrierRestrictions, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(carrierRestrictions, 0);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(24, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setAllowedCarriers is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setCarrierInfoForImsiEncryption(int i, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsiEncryptionInfo, 0);
                    if (this.mRemote.transact(25, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCarrierInfoForImsiEncryption is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setCdmaSubscriptionSource(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(26, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCdmaSubscriptionSource is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setFacilityLockForApp(int i, String str, boolean z, String str2, int i2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    if (this.mRemote.transact(27, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setFacilityLockForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setResponseFunctions(IRadioSimResponse iRadioSimResponse, IRadioSimIndication iRadioSimIndication) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iRadioSimResponse);
                    obtain.writeStrongInterface(iRadioSimIndication);
                    if (this.mRemote.transact(28, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setSimCardPower(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (this.mRemote.transact(29, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimCardPower is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setUiccSubscription(int i, SelectUiccSub selectUiccSub) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(selectUiccSub, 0);
                    if (this.mRemote.transact(30, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setUiccSubscription is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplyIccPin2ForApp(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (this.mRemote.transact(31, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPin2ForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplyIccPinForApp(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (this.mRemote.transact(32, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPinForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplyIccPuk2ForApp(int i, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (this.mRemote.transact(33, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPuk2ForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplyIccPukForApp(int i, String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    if (this.mRemote.transact(34, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPukForApp is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplySimDepersonalization(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    if (this.mRemote.transact(35, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplySimDepersonalization is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void updateSimPhonebookRecords(int i, PhonebookRecordInfo phonebookRecordInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(phonebookRecordInfo, 0);
                    if (this.mRemote.transact(36, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateSimPhonebookRecords is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccCloseLogicalChannelWithSessionInfo(int i, SessionInfo sessionInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(sessionInfo, 0);
                    if (this.mRemote.transact(37, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccCloseLogicalChannelWithSessionInfo is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
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

            @Override // android.hardware.radio.sim.IRadioSim
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
