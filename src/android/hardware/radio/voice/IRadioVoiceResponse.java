package android.hardware.radio.voice;

import android.hardware.radio.RadioResponseInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioVoiceResponse extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$voice$IRadioVoiceResponse".replace('$', '.');
    public static final String HASH = "576f05d082e9269bcf773b0c9b9112d507ab4b9a";
    public static final int VERSION = 4;

    void acceptCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void acknowledgeRequest(int i) throws RemoteException;

    void cancelPendingUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void conferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void dialResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void emergencyDialResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void exitEmergencyCallbackModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void explicitCallTransferResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void getCallForwardStatusResponse(RadioResponseInfo radioResponseInfo, CallForwardInfo[] callForwardInfoArr) throws RemoteException;

    void getCallWaitingResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException;

    void getClipResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getClirResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException;

    void getCurrentCallsResponse(RadioResponseInfo radioResponseInfo, Call[] callArr) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getLastCallFailCauseResponse(RadioResponseInfo radioResponseInfo, LastCallFailCauseInfo lastCallFailCauseInfo) throws RemoteException;

    void getMuteResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void getPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void getTtyModeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void hangupConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void hangupForegroundResumeBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void hangupWaitingOrBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void isVoNrEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    void rejectCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendBurstDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Deprecated
    void sendCdmaFeatureCodeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void separateConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCallForwardResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setCallWaitingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setClirResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setMuteResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setTtyModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setVoNrEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void startDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void stopDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    public static class Default implements IRadioVoiceResponse {
        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void acceptCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void acknowledgeRequest(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void cancelPendingUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void conferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void dialResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void emergencyDialResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void exitEmergencyCallbackModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void explicitCallTransferResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getCallForwardStatusResponse(RadioResponseInfo radioResponseInfo, CallForwardInfo[] callForwardInfoArr) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getCallWaitingResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getClipResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getClirResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getCurrentCallsResponse(RadioResponseInfo radioResponseInfo, Call[] callArr) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getLastCallFailCauseResponse(RadioResponseInfo radioResponseInfo, LastCallFailCauseInfo lastCallFailCauseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getMuteResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void getTtyModeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void hangupConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void hangupForegroundResumeBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void hangupWaitingOrBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void isVoNrEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void rejectCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void sendBurstDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void sendCdmaFeatureCodeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void sendDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void sendUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void separateConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setCallForwardResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setCallWaitingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setClirResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setMuteResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setTtyModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void setVoNrEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void startDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void stopDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoiceResponse
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioVoiceResponse {
        static final int TRANSACTION_acceptCallResponse = 1;
        static final int TRANSACTION_acknowledgeRequest = 2;
        static final int TRANSACTION_cancelPendingUssdResponse = 3;
        static final int TRANSACTION_conferenceResponse = 4;
        static final int TRANSACTION_dialResponse = 5;
        static final int TRANSACTION_emergencyDialResponse = 6;
        static final int TRANSACTION_exitEmergencyCallbackModeResponse = 7;
        static final int TRANSACTION_explicitCallTransferResponse = 8;
        static final int TRANSACTION_getCallForwardStatusResponse = 9;
        static final int TRANSACTION_getCallWaitingResponse = 10;
        static final int TRANSACTION_getClipResponse = 11;
        static final int TRANSACTION_getClirResponse = 12;
        static final int TRANSACTION_getCurrentCallsResponse = 13;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getLastCallFailCauseResponse = 14;
        static final int TRANSACTION_getMuteResponse = 15;
        static final int TRANSACTION_getPreferredVoicePrivacyResponse = 16;
        static final int TRANSACTION_getTtyModeResponse = 17;
        static final int TRANSACTION_handleStkCallSetupRequestFromSimResponse = 18;
        static final int TRANSACTION_hangupConnectionResponse = 19;
        static final int TRANSACTION_hangupForegroundResumeBackgroundResponse = 20;
        static final int TRANSACTION_hangupWaitingOrBackgroundResponse = 21;
        static final int TRANSACTION_isVoNrEnabledResponse = 22;
        static final int TRANSACTION_rejectCallResponse = 23;
        static final int TRANSACTION_sendBurstDtmfResponse = 24;
        static final int TRANSACTION_sendCdmaFeatureCodeResponse = 25;
        static final int TRANSACTION_sendDtmfResponse = 26;
        static final int TRANSACTION_sendUssdResponse = 27;
        static final int TRANSACTION_separateConnectionResponse = 28;
        static final int TRANSACTION_setCallForwardResponse = 29;
        static final int TRANSACTION_setCallWaitingResponse = 30;
        static final int TRANSACTION_setClirResponse = 31;
        static final int TRANSACTION_setMuteResponse = 32;
        static final int TRANSACTION_setPreferredVoicePrivacyResponse = 33;
        static final int TRANSACTION_setTtyModeResponse = 34;
        static final int TRANSACTION_setVoNrEnabledResponse = 35;
        static final int TRANSACTION_startDtmfResponse = 36;
        static final int TRANSACTION_stopDtmfResponse = 37;
        static final int TRANSACTION_switchWaitingOrHoldingAndActiveResponse = 38;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioVoiceResponse asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioVoiceResponse)) {
                return (IRadioVoiceResponse) iInterfaceQueryLocalInterface;
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
                    RadioResponseInfo radioResponseInfo = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    acceptCallResponse(radioResponseInfo);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeRequest(i3);
                    return true;
                case 3:
                    RadioResponseInfo radioResponseInfo2 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelPendingUssdResponse(radioResponseInfo2);
                    return true;
                case 4:
                    RadioResponseInfo radioResponseInfo3 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    conferenceResponse(radioResponseInfo3);
                    return true;
                case 5:
                    RadioResponseInfo radioResponseInfo4 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    dialResponse(radioResponseInfo4);
                    return true;
                case 6:
                    RadioResponseInfo radioResponseInfo5 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    emergencyDialResponse(radioResponseInfo5);
                    return true;
                case 7:
                    RadioResponseInfo radioResponseInfo6 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    exitEmergencyCallbackModeResponse(radioResponseInfo6);
                    return true;
                case 8:
                    RadioResponseInfo radioResponseInfo7 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    explicitCallTransferResponse(radioResponseInfo7);
                    return true;
                case 9:
                    RadioResponseInfo radioResponseInfo8 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    CallForwardInfo[] callForwardInfoArr = (CallForwardInfo[]) parcel.createTypedArray(CallForwardInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCallForwardStatusResponse(radioResponseInfo8, callForwardInfoArr);
                    return true;
                case 10:
                    RadioResponseInfo radioResponseInfo9 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCallWaitingResponse(radioResponseInfo9, z, i4);
                    return true;
                case 11:
                    RadioResponseInfo radioResponseInfo10 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getClipResponse(radioResponseInfo10, i5);
                    return true;
                case 12:
                    RadioResponseInfo radioResponseInfo11 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getClirResponse(radioResponseInfo11, i6, i7);
                    return true;
                case 13:
                    RadioResponseInfo radioResponseInfo12 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    Call[] callArr = (Call[]) parcel.createTypedArray(Call.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCurrentCallsResponse(radioResponseInfo12, callArr);
                    return true;
                case 14:
                    RadioResponseInfo radioResponseInfo13 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    LastCallFailCauseInfo lastCallFailCauseInfo = (LastCallFailCauseInfo) parcel.readTypedObject(LastCallFailCauseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getLastCallFailCauseResponse(radioResponseInfo13, lastCallFailCauseInfo);
                    return true;
                case 15:
                    RadioResponseInfo radioResponseInfo14 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getMuteResponse(radioResponseInfo14, z2);
                    return true;
                case 16:
                    RadioResponseInfo radioResponseInfo15 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    getPreferredVoicePrivacyResponse(radioResponseInfo15, z3);
                    return true;
                case 17:
                    RadioResponseInfo radioResponseInfo16 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getTtyModeResponse(radioResponseInfo16, i8);
                    return true;
                case 18:
                    RadioResponseInfo radioResponseInfo17 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleStkCallSetupRequestFromSimResponse(radioResponseInfo17);
                    return true;
                case 19:
                    RadioResponseInfo radioResponseInfo18 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    hangupConnectionResponse(radioResponseInfo18);
                    return true;
                case 20:
                    RadioResponseInfo radioResponseInfo19 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    hangupForegroundResumeBackgroundResponse(radioResponseInfo19);
                    return true;
                case 21:
                    RadioResponseInfo radioResponseInfo20 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    hangupWaitingOrBackgroundResponse(radioResponseInfo20);
                    return true;
                case 22:
                    RadioResponseInfo radioResponseInfo21 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    isVoNrEnabledResponse(radioResponseInfo21, z4);
                    return true;
                case 23:
                    RadioResponseInfo radioResponseInfo22 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    rejectCallResponse(radioResponseInfo22);
                    return true;
                case 24:
                    RadioResponseInfo radioResponseInfo23 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendBurstDtmfResponse(radioResponseInfo23);
                    return true;
                case 25:
                    RadioResponseInfo radioResponseInfo24 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCdmaFeatureCodeResponse(radioResponseInfo24);
                    return true;
                case 26:
                    RadioResponseInfo radioResponseInfo25 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendDtmfResponse(radioResponseInfo25);
                    return true;
                case 27:
                    RadioResponseInfo radioResponseInfo26 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendUssdResponse(radioResponseInfo26);
                    return true;
                case 28:
                    RadioResponseInfo radioResponseInfo27 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    separateConnectionResponse(radioResponseInfo27);
                    return true;
                case 29:
                    RadioResponseInfo radioResponseInfo28 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallForwardResponse(radioResponseInfo28);
                    return true;
                case 30:
                    RadioResponseInfo radioResponseInfo29 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallWaitingResponse(radioResponseInfo29);
                    return true;
                case 31:
                    RadioResponseInfo radioResponseInfo30 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setClirResponse(radioResponseInfo30);
                    return true;
                case 32:
                    RadioResponseInfo radioResponseInfo31 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMuteResponse(radioResponseInfo31);
                    return true;
                case 33:
                    RadioResponseInfo radioResponseInfo32 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreferredVoicePrivacyResponse(radioResponseInfo32);
                    return true;
                case 34:
                    RadioResponseInfo radioResponseInfo33 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setTtyModeResponse(radioResponseInfo33);
                    return true;
                case 35:
                    RadioResponseInfo radioResponseInfo34 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVoNrEnabledResponse(radioResponseInfo34);
                    return true;
                case 36:
                    RadioResponseInfo radioResponseInfo35 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    startDtmfResponse(radioResponseInfo35);
                    return true;
                case 37:
                    RadioResponseInfo radioResponseInfo36 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopDtmfResponse(radioResponseInfo36);
                    return true;
                case 38:
                    RadioResponseInfo radioResponseInfo37 = (RadioResponseInfo) parcel.readTypedObject(RadioResponseInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    switchWaitingOrHoldingAndActiveResponse(radioResponseInfo37);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioVoiceResponse {
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

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void acceptCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acceptCallResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void acknowledgeRequest(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acknowledgeRequest is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void cancelPendingUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cancelPendingUssdResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void conferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method conferenceResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void dialResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method dialResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void emergencyDialResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencyDialResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void exitEmergencyCallbackModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method exitEmergencyCallbackModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void explicitCallTransferResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method explicitCallTransferResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getCallForwardStatusResponse(RadioResponseInfo radioResponseInfo, CallForwardInfo[] callForwardInfoArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedArray(callForwardInfoArr, 0);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallForwardStatusResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getCallWaitingResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallWaitingResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getClipResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getClipResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getClirResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getClirResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getCurrentCallsResponse(RadioResponseInfo radioResponseInfo, Call[] callArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedArray(callArr, 0);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCurrentCallsResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getLastCallFailCauseResponse(RadioResponseInfo radioResponseInfo, LastCallFailCauseInfo lastCallFailCauseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeTypedObject(lastCallFailCauseInfo, 0);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getLastCallFailCauseResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getMuteResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getMuteResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPreferredVoicePrivacyResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void getTtyModeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getTtyModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method handleStkCallSetupRequestFromSimResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void hangupConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangupConnectionResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void hangupForegroundResumeBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangupForegroundResumeBackgroundResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void hangupWaitingOrBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(21, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangupWaitingOrBackgroundResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void isVoNrEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isVoNrEnabledResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void rejectCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(23, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method rejectCallResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void sendBurstDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(24, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendBurstDtmfResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void sendCdmaFeatureCodeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(25, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaFeatureCodeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void sendDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(26, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendDtmfResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void sendUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(27, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendUssdResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void separateConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(28, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method separateConnectionResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setCallForwardResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(29, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCallForwardResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setCallWaitingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(30, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCallWaitingResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setClirResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(31, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setClirResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setMuteResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(32, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setMuteResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(33, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPreferredVoicePrivacyResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setTtyModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(34, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setTtyModeResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void setVoNrEnabledResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(35, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setVoNrEnabledResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void startDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(36, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startDtmfResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void stopDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(37, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopDtmfResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
            public void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(radioResponseInfo, 0);
                    if (this.mRemote.transact(38, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method switchWaitingOrHoldingAndActiveResponse is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
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

            @Override // android.hardware.radio.voice.IRadioVoiceResponse
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
