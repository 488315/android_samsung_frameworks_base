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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioSim)) {
                return (IRadioSim) iInterfaceQueryLocalInterface;
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
                    parcel.enforceNoDataAvail();
                    areUiccApplicationsEnabled(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    changeIccPin2ForApp(i4, string, string2, string3);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    changeIccPinForApp(i5, string4, string5, string6);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableUiccApplications(i6, z);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getAllowedCarriers(i7);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaSubscription(i8);
                    return true;
                case 7:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCdmaSubscriptionSource(i9);
                    return true;
                case 8:
                    int i10 = parcel.readInt();
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    int i11 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getFacilityLockForApp(i10, string7, string8, i11, string9);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getIccCardStatus(i12);
                    return true;
                case 10:
                    int i13 = parcel.readInt();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    getImsiForApp(i13, string10);
                    return true;
                case 11:
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimPhonebookCapacity(i14);
                    return true;
                case 12:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getSimPhonebookRecords(i15);
                    return true;
                case 13:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    iccCloseLogicalChannel(i16, i17);
                    return true;
                case 14:
                    int i18 = parcel.readInt();
                    IccIo iccIo = (IccIo) parcel.readTypedObject(IccIo.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccIoForApp(i18, iccIo);
                    return true;
                case 15:
                    int i19 = parcel.readInt();
                    String string11 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    iccOpenLogicalChannel(i19, string11, i20);
                    return true;
                case 16:
                    int i21 = parcel.readInt();
                    SimApdu simApdu = (SimApdu) parcel.readTypedObject(SimApdu.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccTransmitApduBasicChannel(i21, simApdu);
                    return true;
                case 17:
                    int i22 = parcel.readInt();
                    SimApdu simApdu2 = (SimApdu) parcel.readTypedObject(SimApdu.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccTransmitApduLogicalChannel(i22, simApdu2);
                    return true;
                case 18:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportStkServiceIsRunning(i23);
                    return true;
                case 19:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestIccSimAuthentication(i24, i25, string12, string13);
                    return true;
                case 20:
                    responseAcknowledgement();
                    return true;
                case 21:
                    int i26 = parcel.readInt();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendEnvelope(i26, string14);
                    return true;
                case 22:
                    int i27 = parcel.readInt();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendEnvelopeWithStatus(i27, string15);
                    return true;
                case 23:
                    int i28 = parcel.readInt();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendTerminalResponseToSim(i28, string16);
                    return true;
                case 24:
                    int i29 = parcel.readInt();
                    CarrierRestrictions carrierRestrictions = (CarrierRestrictions) parcel.readTypedObject(CarrierRestrictions.CREATOR);
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAllowedCarriers(i29, carrierRestrictions, i30);
                    return true;
                case 25:
                    int i31 = parcel.readInt();
                    ImsiEncryptionInfo imsiEncryptionInfo = (ImsiEncryptionInfo) parcel.readTypedObject(ImsiEncryptionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCarrierInfoForImsiEncryption(i31, imsiEncryptionInfo);
                    return true;
                case 26:
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCdmaSubscriptionSource(i32, i33);
                    return true;
                case 27:
                    int i34 = parcel.readInt();
                    String string17 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    String string18 = parcel.readString();
                    int i35 = parcel.readInt();
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setFacilityLockForApp(i34, string17, z2, string18, i35, string19);
                    return true;
                case 28:
                    IRadioSimResponse iRadioSimResponseAsInterface = IRadioSimResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioSimIndication iRadioSimIndicationAsInterface = IRadioSimIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iRadioSimResponseAsInterface, iRadioSimIndicationAsInterface);
                    return true;
                case 29:
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSimCardPower(i36, i37);
                    return true;
                case 30:
                    int i38 = parcel.readInt();
                    SelectUiccSub selectUiccSub = (SelectUiccSub) parcel.readTypedObject(SelectUiccSub.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiccSubscription(i38, selectUiccSub);
                    return true;
                case 31:
                    int i39 = parcel.readInt();
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPin2ForApp(i39, string20, string21);
                    return true;
                case 32:
                    int i40 = parcel.readInt();
                    String string22 = parcel.readString();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPinForApp(i40, string22, string23);
                    return true;
                case 33:
                    int i41 = parcel.readInt();
                    String string24 = parcel.readString();
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPuk2ForApp(i41, string24, string25, string26);
                    return true;
                case 34:
                    int i42 = parcel.readInt();
                    String string27 = parcel.readString();
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplyIccPukForApp(i42, string27, string28, string29);
                    return true;
                case 35:
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    supplySimDepersonalization(i43, i44, string30);
                    return true;
                case 36:
                    int i45 = parcel.readInt();
                    PhonebookRecordInfo phonebookRecordInfo = (PhonebookRecordInfo) parcel.readTypedObject(PhonebookRecordInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateSimPhonebookRecords(i45, phonebookRecordInfo);
                    return true;
                case 37:
                    int i46 = parcel.readInt();
                    SessionInfo sessionInfo = (SessionInfo) parcel.readTypedObject(SessionInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    iccCloseLogicalChannelWithSessionInfo(i46, sessionInfo);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method areUiccApplicationsEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void changeIccPin2ForApp(int i, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method changeIccPin2ForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void changeIccPinForApp(int i, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method changeIccPinForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void enableUiccApplications(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method enableUiccApplications is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getAllowedCarriers(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getAllowedCarriers is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getCdmaSubscription(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaSubscription is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getCdmaSubscriptionSource(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCdmaSubscriptionSource is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getFacilityLockForApp(int i, String str, String str2, int i2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getFacilityLockForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getIccCardStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getIccCardStatus is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getImsiForApp(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getImsiForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getSimPhonebookCapacity(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimPhonebookCapacity is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void getSimPhonebookRecords(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getSimPhonebookRecords is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccCloseLogicalChannel(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccCloseLogicalChannel is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccIoForApp(int i, IccIo iccIo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(iccIo, 0);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccIoForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccOpenLogicalChannel(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccOpenLogicalChannel is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccTransmitApduBasicChannel(int i, SimApdu simApdu) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(simApdu, 0);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccTransmitApduBasicChannel is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccTransmitApduLogicalChannel(int i, SimApdu simApdu) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(simApdu, 0);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccTransmitApduLogicalChannel is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void reportStkServiceIsRunning(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method reportStkServiceIsRunning is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void requestIccSimAuthentication(int i, int i2, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method requestIccSimAuthentication is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void responseAcknowledgement() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void sendEnvelope(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(21, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendEnvelope is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void sendEnvelopeWithStatus(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendEnvelopeWithStatus is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void sendTerminalResponseToSim(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(23, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendTerminalResponseToSim is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setAllowedCarriers(int i, CarrierRestrictions carrierRestrictions, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(carrierRestrictions, 0);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(24, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setAllowedCarriers is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setCarrierInfoForImsiEncryption(int i, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(imsiEncryptionInfo, 0);
                    if (this.mRemote.transact(25, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCarrierInfoForImsiEncryption is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setCdmaSubscriptionSource(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(26, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCdmaSubscriptionSource is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setFacilityLockForApp(int i, String str, boolean z, String str2, int i2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    if (this.mRemote.transact(27, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setFacilityLockForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setResponseFunctions(IRadioSimResponse iRadioSimResponse, IRadioSimIndication iRadioSimIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRadioSimResponse);
                    parcelObtain.writeStrongInterface(iRadioSimIndication);
                    if (this.mRemote.transact(28, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setSimCardPower(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(29, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setSimCardPower is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void setUiccSubscription(int i, SelectUiccSub selectUiccSub) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(selectUiccSub, 0);
                    if (this.mRemote.transact(30, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setUiccSubscription is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplyIccPin2ForApp(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (this.mRemote.transact(31, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPin2ForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplyIccPinForApp(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    if (this.mRemote.transact(32, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPinForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplyIccPuk2ForApp(int i, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    if (this.mRemote.transact(33, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPuk2ForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplyIccPukForApp(int i, String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    if (this.mRemote.transact(34, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplyIccPukForApp is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void supplySimDepersonalization(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(35, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method supplySimDepersonalization is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void updateSimPhonebookRecords(int i, PhonebookRecordInfo phonebookRecordInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(phonebookRecordInfo, 0);
                    if (this.mRemote.transact(36, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method updateSimPhonebookRecords is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
            public void iccCloseLogicalChannelWithSessionInfo(int i, SessionInfo sessionInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(sessionInfo, 0);
                    if (this.mRemote.transact(37, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method iccCloseLogicalChannelWithSessionInfo is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.sim.IRadioSim
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

            @Override // android.hardware.radio.sim.IRadioSim
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
