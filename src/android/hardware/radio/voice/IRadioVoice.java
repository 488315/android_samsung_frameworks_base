package android.hardware.radio.voice;

import android.hardware.radio.voice.IRadioVoiceIndication;
import android.hardware.radio.voice.IRadioVoiceResponse;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRadioVoice extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$radio$voice$IRadioVoice".replace('$', '.');
    public static final String HASH = "576f05d082e9269bcf773b0c9b9112d507ab4b9a";
    public static final int VERSION = 4;

    void acceptCall(int i) throws RemoteException;

    void cancelPendingUssd(int i) throws RemoteException;

    void conference(int i) throws RemoteException;

    void dial(int i, Dial dial) throws RemoteException;

    void emergencyDial(int i, Dial dial, int i2, String[] strArr, int i3, boolean z, boolean z2) throws RemoteException;

    void exitEmergencyCallbackMode(int i) throws RemoteException;

    void explicitCallTransfer(int i) throws RemoteException;

    void getCallForwardStatus(int i, CallForwardInfo callForwardInfo) throws RemoteException;

    void getCallWaiting(int i, int i2) throws RemoteException;

    void getClip(int i) throws RemoteException;

    void getClir(int i) throws RemoteException;

    void getCurrentCalls(int i) throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void getLastCallFailCause(int i) throws RemoteException;

    void getMute(int i) throws RemoteException;

    void getPreferredVoicePrivacy(int i) throws RemoteException;

    void getTtyMode(int i) throws RemoteException;

    void handleStkCallSetupRequestFromSim(int i, boolean z) throws RemoteException;

    void hangup(int i, int i2) throws RemoteException;

    void hangupForegroundResumeBackground(int i) throws RemoteException;

    void hangupWaitingOrBackground(int i) throws RemoteException;

    void isVoNrEnabled(int i) throws RemoteException;

    void rejectCall(int i) throws RemoteException;

    void responseAcknowledgement() throws RemoteException;

    void sendBurstDtmf(int i, String str, int i2, int i3) throws RemoteException;

    @Deprecated
    void sendCdmaFeatureCode(int i, String str) throws RemoteException;

    void sendDtmf(int i, String str) throws RemoteException;

    void sendUssd(int i, String str) throws RemoteException;

    void separateConnection(int i, int i2) throws RemoteException;

    void setCallForward(int i, CallForwardInfo callForwardInfo) throws RemoteException;

    void setCallWaiting(int i, boolean z, int i2) throws RemoteException;

    void setClir(int i, int i2) throws RemoteException;

    void setMute(int i, boolean z) throws RemoteException;

    void setPreferredVoicePrivacy(int i, boolean z) throws RemoteException;

    void setResponseFunctions(IRadioVoiceResponse iRadioVoiceResponse, IRadioVoiceIndication iRadioVoiceIndication) throws RemoteException;

    void setTtyMode(int i, int i2) throws RemoteException;

    void setVoNrEnabled(int i, boolean z) throws RemoteException;

    void startDtmf(int i, String str) throws RemoteException;

    void stopDtmf(int i) throws RemoteException;

    void switchWaitingOrHoldingAndActive(int i) throws RemoteException;

    public static class Default implements IRadioVoice {
        @Override // android.hardware.radio.voice.IRadioVoice
        public void acceptCall(int i) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void cancelPendingUssd(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void conference(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void dial(int i, Dial dial) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void emergencyDial(int i, Dial dial, int i2, String[] strArr, int i3, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void exitEmergencyCallbackMode(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void explicitCallTransfer(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getCallForwardStatus(int i, CallForwardInfo callForwardInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getCallWaiting(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getClip(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getClir(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getCurrentCalls(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getLastCallFailCause(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getMute(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getPreferredVoicePrivacy(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void getTtyMode(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void handleStkCallSetupRequestFromSim(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void hangup(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void hangupForegroundResumeBackground(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void hangupWaitingOrBackground(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void isVoNrEnabled(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void rejectCall(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void responseAcknowledgement() throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void sendBurstDtmf(int i, String str, int i2, int i3) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void sendCdmaFeatureCode(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void sendDtmf(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void sendUssd(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void separateConnection(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setCallForward(int i, CallForwardInfo callForwardInfo) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setCallWaiting(int i, boolean z, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setClir(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setMute(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setPreferredVoicePrivacy(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setResponseFunctions(IRadioVoiceResponse iRadioVoiceResponse, IRadioVoiceIndication iRadioVoiceIndication) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setTtyMode(int i, int i2) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void setVoNrEnabled(int i, boolean z) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void startDtmf(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void stopDtmf(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public void switchWaitingOrHoldingAndActive(int i) throws RemoteException {
        }

        @Override // android.hardware.radio.voice.IRadioVoice
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IRadioVoice {
        static final int TRANSACTION_acceptCall = 1;
        static final int TRANSACTION_cancelPendingUssd = 2;
        static final int TRANSACTION_conference = 3;
        static final int TRANSACTION_dial = 4;
        static final int TRANSACTION_emergencyDial = 5;
        static final int TRANSACTION_exitEmergencyCallbackMode = 6;
        static final int TRANSACTION_explicitCallTransfer = 7;
        static final int TRANSACTION_getCallForwardStatus = 8;
        static final int TRANSACTION_getCallWaiting = 9;
        static final int TRANSACTION_getClip = 10;
        static final int TRANSACTION_getClir = 11;
        static final int TRANSACTION_getCurrentCalls = 12;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getLastCallFailCause = 13;
        static final int TRANSACTION_getMute = 14;
        static final int TRANSACTION_getPreferredVoicePrivacy = 15;
        static final int TRANSACTION_getTtyMode = 16;
        static final int TRANSACTION_handleStkCallSetupRequestFromSim = 17;
        static final int TRANSACTION_hangup = 18;
        static final int TRANSACTION_hangupForegroundResumeBackground = 19;
        static final int TRANSACTION_hangupWaitingOrBackground = 20;
        static final int TRANSACTION_isVoNrEnabled = 21;
        static final int TRANSACTION_rejectCall = 22;
        static final int TRANSACTION_responseAcknowledgement = 23;
        static final int TRANSACTION_sendBurstDtmf = 24;
        static final int TRANSACTION_sendCdmaFeatureCode = 25;
        static final int TRANSACTION_sendDtmf = 26;
        static final int TRANSACTION_sendUssd = 27;
        static final int TRANSACTION_separateConnection = 28;
        static final int TRANSACTION_setCallForward = 29;
        static final int TRANSACTION_setCallWaiting = 30;
        static final int TRANSACTION_setClir = 31;
        static final int TRANSACTION_setMute = 32;
        static final int TRANSACTION_setPreferredVoicePrivacy = 33;
        static final int TRANSACTION_setResponseFunctions = 34;
        static final int TRANSACTION_setTtyMode = 35;
        static final int TRANSACTION_setVoNrEnabled = 36;
        static final int TRANSACTION_startDtmf = 37;
        static final int TRANSACTION_stopDtmf = 38;
        static final int TRANSACTION_switchWaitingOrHoldingAndActive = 39;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRadioVoice asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRadioVoice)) {
                return (IRadioVoice) iInterfaceQueryLocalInterface;
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
                    acceptCall(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelPendingUssd(i4);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    conference(i5);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    Dial dial = (Dial) parcel.readTypedObject(Dial.CREATOR);
                    parcel.enforceNoDataAvail();
                    dial(i6, dial);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    Dial dial2 = (Dial) parcel.readTypedObject(Dial.CREATOR);
                    int i8 = parcel.readInt();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    int i9 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    emergencyDial(i7, dial2, i8, strArrCreateStringArray, i9, z, z2);
                    return true;
                case 6:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    exitEmergencyCallbackMode(i10);
                    return true;
                case 7:
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    explicitCallTransfer(i11);
                    return true;
                case 8:
                    int i12 = parcel.readInt();
                    CallForwardInfo callForwardInfo = (CallForwardInfo) parcel.readTypedObject(CallForwardInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCallForwardStatus(i12, callForwardInfo);
                    return true;
                case 9:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCallWaiting(i13, i14);
                    return true;
                case 10:
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getClip(i15);
                    return true;
                case 11:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getClir(i16);
                    return true;
                case 12:
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getCurrentCalls(i17);
                    return true;
                case 13:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getLastCallFailCause(i18);
                    return true;
                case 14:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getMute(i19);
                    return true;
                case 15:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getPreferredVoicePrivacy(i20);
                    return true;
                case 16:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    getTtyMode(i21);
                    return true;
                case 17:
                    int i22 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    handleStkCallSetupRequestFromSim(i22, z3);
                    return true;
                case 18:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hangup(i23, i24);
                    return true;
                case 19:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hangupForegroundResumeBackground(i25);
                    return true;
                case 20:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    hangupWaitingOrBackground(i26);
                    return true;
                case 21:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    isVoNrEnabled(i27);
                    return true;
                case 22:
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    rejectCall(i28);
                    return true;
                case 23:
                    responseAcknowledgement();
                    return true;
                case 24:
                    int i29 = parcel.readInt();
                    String string = parcel.readString();
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendBurstDtmf(i29, string, i30, i31);
                    return true;
                case 25:
                    int i32 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendCdmaFeatureCode(i32, string2);
                    return true;
                case 26:
                    int i33 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendDtmf(i33, string3);
                    return true;
                case 27:
                    int i34 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendUssd(i34, string4);
                    return true;
                case 28:
                    int i35 = parcel.readInt();
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    separateConnection(i35, i36);
                    return true;
                case 29:
                    int i37 = parcel.readInt();
                    CallForwardInfo callForwardInfo2 = (CallForwardInfo) parcel.readTypedObject(CallForwardInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallForward(i37, callForwardInfo2);
                    return true;
                case 30:
                    int i38 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCallWaiting(i38, z4, i39);
                    return true;
                case 31:
                    int i40 = parcel.readInt();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setClir(i40, i41);
                    return true;
                case 32:
                    int i42 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMute(i42, z5);
                    return true;
                case 33:
                    int i43 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPreferredVoicePrivacy(i43, z6);
                    return true;
                case 34:
                    IRadioVoiceResponse iRadioVoiceResponseAsInterface = IRadioVoiceResponse.Stub.asInterface(parcel.readStrongBinder());
                    IRadioVoiceIndication iRadioVoiceIndicationAsInterface = IRadioVoiceIndication.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setResponseFunctions(iRadioVoiceResponseAsInterface, iRadioVoiceIndicationAsInterface);
                    return true;
                case 35:
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTtyMode(i44, i45);
                    return true;
                case 36:
                    int i46 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVoNrEnabled(i46, z7);
                    return true;
                case 37:
                    int i47 = parcel.readInt();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    startDtmf(i47, string5);
                    return true;
                case 38:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopDtmf(i48);
                    return true;
                case 39:
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    switchWaitingOrHoldingAndActive(i49);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IRadioVoice {
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

            @Override // android.hardware.radio.voice.IRadioVoice
            public void acceptCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(1, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method acceptCall is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void cancelPendingUssd(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(2, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method cancelPendingUssd is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void conference(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(3, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method conference is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void dial(int i, Dial dial) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(dial, 0);
                    if (this.mRemote.transact(4, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method dial is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void emergencyDial(int i, Dial dial, int i2, String[] strArr, int i3, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(dial, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    if (this.mRemote.transact(5, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method emergencyDial is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void exitEmergencyCallbackMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(6, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method exitEmergencyCallbackMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void explicitCallTransfer(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(7, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method explicitCallTransfer is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getCallForwardStatus(int i, CallForwardInfo callForwardInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(callForwardInfo, 0);
                    if (this.mRemote.transact(8, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallForwardStatus is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getCallWaiting(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(9, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCallWaiting is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getClip(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(10, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getClip is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getClir(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(11, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getClir is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getCurrentCalls(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(12, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getCurrentCalls is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getLastCallFailCause(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(13, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getLastCallFailCause is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getMute(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(14, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getMute is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getPreferredVoicePrivacy(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(15, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getPreferredVoicePrivacy is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void getTtyMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(16, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method getTtyMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void handleStkCallSetupRequestFromSim(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(17, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method handleStkCallSetupRequestFromSim is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void hangup(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(18, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangup is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void hangupForegroundResumeBackground(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(19, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangupForegroundResumeBackground is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void hangupWaitingOrBackground(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(20, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method hangupWaitingOrBackground is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void isVoNrEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(21, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method isVoNrEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void rejectCall(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(22, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method rejectCall is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void responseAcknowledgement() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (this.mRemote.transact(23, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method responseAcknowledgement is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void sendBurstDtmf(int i, String str, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    if (this.mRemote.transact(24, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendBurstDtmf is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void sendCdmaFeatureCode(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(25, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendCdmaFeatureCode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void sendDtmf(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(26, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendDtmf is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void sendUssd(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(27, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method sendUssd is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void separateConnection(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(28, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method separateConnection is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setCallForward(int i, CallForwardInfo callForwardInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(callForwardInfo, 0);
                    if (this.mRemote.transact(29, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCallForward is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setCallWaiting(int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(30, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setCallWaiting is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setClir(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(31, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setClir is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setMute(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(32, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setMute is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setPreferredVoicePrivacy(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(33, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setPreferredVoicePrivacy is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setResponseFunctions(IRadioVoiceResponse iRadioVoiceResponse, IRadioVoiceIndication iRadioVoiceIndication) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRadioVoiceResponse);
                    parcelObtain.writeStrongInterface(iRadioVoiceIndication);
                    if (this.mRemote.transact(34, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setResponseFunctions is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setTtyMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    if (this.mRemote.transact(35, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setTtyMode is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void setVoNrEnabled(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    if (this.mRemote.transact(36, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method setVoNrEnabled is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void startDtmf(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    if (this.mRemote.transact(37, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method startDtmf is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void stopDtmf(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(38, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method stopDtmf is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
            public void switchWaitingOrHoldingAndActive(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    if (this.mRemote.transact(39, parcelObtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method switchWaitingOrHoldingAndActive is unimplemented.");
                    }
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.radio.voice.IRadioVoice
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

            @Override // android.hardware.radio.voice.IRadioVoice
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
