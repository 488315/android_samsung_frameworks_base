package android.hardware.radio.V1_6;

import android.hardware.radio.V1_0.ActivityStatsInfo;
import android.hardware.radio.V1_0.CallForwardInfo;
import android.hardware.radio.V1_0.CardStatus;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.DataRegStateResult;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.IccIoResult;
import android.hardware.radio.V1_0.LastCallFailCauseInfo;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.LceStatusInfo;
import android.hardware.radio.V1_0.NeighboringCell;
import android.hardware.radio.V1_0.OperatorInfo;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.SendSmsResult;
import android.hardware.radio.V1_0.VoiceRegStateResult;
import android.hardware.radio.V1_1.KeepaliveStatus;
import android.hardware.radio.V1_4.CarrierRestrictionsWithPriority;
import android.hardware.radio.V1_5.BarringInfo;
import android.hardware.radio.V1_5.CellIdentity;
import android.hardware.radio.V1_5.RadioAccessSpecifier;
import android.internal.hidl.base.V1_0.DebugInfo;
import android.internal.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface IRadioResponse extends android.hardware.radio.V1_5.IRadioResponse {
    public static final String kInterfaceName = "android.hardware.radio@1.6::IRadioResponse";

    void allocatePduSessionIdResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    void cancelHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void getAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getCellInfoListResponse_1_6(RadioResponseInfo radioResponseInfo, ArrayList<CellInfo> arrayList) throws RemoteException;

    void getCurrentCallsResponse_1_6(RadioResponseInfo radioResponseInfo, ArrayList<Call> arrayList) throws RemoteException;

    void getDataCallListResponse_1_6(RadioResponseInfo radioResponseInfo, ArrayList<SetupDataCallResult> arrayList) throws RemoteException;

    void getDataRegistrationStateResponse_1_6(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getSignalStrengthResponse_1_6(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) throws RemoteException;

    void getSimPhonebookCapacityResponse(RadioResponseInfo radioResponseInfo, PhonebookCapacity phonebookCapacity) throws RemoteException;

    void getSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void getSlicingConfigResponse(RadioResponseInfo radioResponseInfo, SlicingConfig slicingConfig) throws RemoteException;

    void getSystemSelectionChannelsResponse(RadioResponseInfo radioResponseInfo, ArrayList<RadioAccessSpecifier> arrayList) throws RemoteException;

    void getVoiceRegistrationStateResponse_1_6(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    void isNrDualConnectivityEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    void releasePduSessionIdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void sendCdmaSmsExpectMoreResponse_1_6(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendCdmaSmsResponse_1_6(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendSmsExpectMoreResponse_1_6(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void sendSmsResponse_1_6(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException;

    void setAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setDataThrottlingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    void setNrDualConnectivityStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setRadioPowerResponse_1_6(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSimCardPowerResponse_1_6(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setupDataCallResponse_1_6(RadioResponseInfo radioResponseInfo, SetupDataCallResult setupDataCallResult) throws RemoteException;

    void startHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void updateSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    static IRadioResponse asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof IRadioResponse)) {
            return (IRadioResponse) iHwInterfaceQueryLocalInterface;
        }
        Proxy proxy = new Proxy(iHwBinder);
        try {
            Iterator<String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    static IRadioResponse castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IRadioResponse getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IRadioResponse getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IRadioResponse getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IRadioResponse getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IRadioResponse {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.radio@1.6::IRadioResponse]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getIccCardStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, CardStatus cardStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            cardStatus.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyIccPinForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyIccPukForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyIccPin2ForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyIccPuk2ForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void changeIccPinForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void changeIccPin2ForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyNetworkDepersonalizationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCurrentCallsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_0.Call> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.Call.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void dialResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getIMSIForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void hangupConnectionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void hangupWaitingOrBackgroundResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void hangupForegroundResumeBackgroundResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void switchWaitingOrHoldingAndActiveResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void conferenceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void rejectCallResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getLastCallFailCauseResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, LastCallFailCauseInfo lastCallFailCauseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            lastCallFailCauseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getSignalStrengthResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SignalStrength signalStrength) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            signalStrength.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getVoiceRegistrationStateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, VoiceRegStateResult voiceRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            voiceRegStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getDataRegistrationStateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, DataRegStateResult dataRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            dataRegStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getOperatorResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, String str, String str2, String str3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setRadioPowerResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendSMSExpectMoreResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setupDataCallResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SetupDataCallResult setupDataCallResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            setupDataCallResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccIOForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendUssdResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void cancelPendingUssdResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getClirResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(31, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setClirResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(32, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCallForwardStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<CallForwardInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            CallForwardInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(33, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCallForwardResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(34, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCallWaitingResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(35, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCallWaitingResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(36, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acknowledgeLastIncomingGsmSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(37, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acceptCallResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(38, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void deactivateDataCallResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(39, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getFacilityLockForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(40, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setFacilityLockForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(41, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setBarringPasswordResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(42, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getNetworkSelectionModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(43, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setNetworkSelectionModeAutomaticResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(44, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setNetworkSelectionModeManualResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(45, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getAvailableNetworksResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<OperatorInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            OperatorInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(46, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void startDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(47, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void stopDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(48, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getBasebandVersionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(49, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void separateConnectionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(50, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setMuteResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(51, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getMuteResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(52, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getClipResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(53, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getDataCallListResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_0.SetupDataCallResult> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.SetupDataCallResult.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(54, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setSuppServiceNotificationsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(55, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void writeSmsToSimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(56, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void deleteSmsOnSimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(57, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setBandModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(58, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getAvailableBandModesResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(59, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendEnvelopeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(60, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendTerminalResponseToSimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(61, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void handleStkCallSetupRequestFromSimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(62, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void explicitCallTransferResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(63, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setPreferredNetworkTypeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(64, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getPreferredNetworkTypeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(65, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getNeighboringCidsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<NeighboringCell> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            NeighboringCell.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(66, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setLocationUpdatesResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(67, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCdmaSubscriptionSourceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(68, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCdmaRoamingPreferenceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(69, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCdmaRoamingPreferenceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(70, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setTTYModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(71, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getTTYModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(72, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setPreferredVoicePrivacyResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(73, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getPreferredVoicePrivacyResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(74, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendCDMAFeatureCodeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(75, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendBurstDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(76, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendCdmaSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(77, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acknowledgeLastIncomingCdmaSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(78, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getGsmBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            GsmBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(79, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setGsmBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(80, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setGsmBroadcastActivationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(81, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCdmaBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            CdmaBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(82, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCdmaBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(83, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCdmaBroadcastActivationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(84, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCDMASubscriptionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4, String str5) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            hwParcel.writeString(str4);
            hwParcel.writeString(str5);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(85, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void writeSmsToRuimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(86, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void deleteSmsOnRuimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(87, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getDeviceIdentityResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            hwParcel.writeString(str4);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(88, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void exitEmergencyCallbackModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(89, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getSmscAddressResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(90, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setSmscAddressResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(91, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void reportSmsMemoryStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(92, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void reportStkServiceIsRunningResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(93, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCdmaSubscriptionSourceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(94, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void requestIsimAuthenticationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(95, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acknowledgeIncomingGsmSmsWithPduResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(96, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendEnvelopeWithStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(97, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getVoiceRadioTechnologyResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(98, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCellInfoListResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_0.CellInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.CellInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(99, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCellInfoListRateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(100, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setInitialAttachApnResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(101, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getImsRegistrationStateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(102, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendImsSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(103, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccTransmitApduBasicChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(104, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccOpenLogicalChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(105, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccCloseLogicalChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(106, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccTransmitApduLogicalChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(107, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void nvReadItemResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(108, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void nvWriteItemResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(109, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void nvWriteCdmaPrlResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(110, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void nvResetConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(111, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setUiccSubscriptionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(112, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setDataAllowedResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(113, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getHardwareConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<HardwareConfig> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HardwareConfig.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(114, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void requestIccSimAuthenticationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(115, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setDataProfileResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(116, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void requestShutdownResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(117, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getRadioCapabilityResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            radioCapability.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(118, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setRadioCapabilityResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            radioCapability.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(119, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void startLceServiceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, LceStatusInfo lceStatusInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            lceStatusInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(120, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void stopLceServiceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, LceStatusInfo lceStatusInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            lceStatusInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(121, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void pullLceDataResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, LceDataInfo lceDataInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            lceDataInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(122, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getModemActivityInfoResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ActivityStatsInfo activityStatsInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            activityStatsInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(123, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setAllowedCarriersResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(124, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getAllowedCarriersResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z, CarrierRestrictions carrierRestrictions) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            carrierRestrictions.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(125, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendDeviceStateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(126, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setIndicationFilterResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(127, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setSimCardPowerResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(128, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acknowledgeRequest(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(129, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void setCarrierInfoForImsiEncryptionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(130, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void setSimCardPowerResponse_1_1(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(131, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void startNetworkScanResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(132, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void stopNetworkScanResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(133, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void startKeepaliveResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, KeepaliveStatus keepaliveStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            keepaliveStatus.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(134, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void stopKeepaliveResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(135, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getCellInfoListResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_2.CellInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_2.CellInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(136, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getIccCardStatusResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.CardStatus cardStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            cardStatus.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(137, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void setSignalStrengthReportingCriteriaResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(138, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void setLinkCapacityReportingCriteriaResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(139, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getCurrentCallsResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_2.Call> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_2.Call.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(140, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getSignalStrengthResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.SignalStrength signalStrength) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            signalStrength.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(141, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getVoiceRegistrationStateResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.VoiceRegStateResult voiceRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            voiceRegStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(142, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getDataRegistrationStateResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.DataRegStateResult dataRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            dataRegStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(143, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse
        public void setSystemSelectionChannelsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(144, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse
        public void enableModemResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(145, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse
        public void getModemStackStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(146, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void emergencyDialResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(147, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void startNetworkScanResponse_1_4(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(148, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void getCellInfoListResponse_1_4(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_4.CellInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_4.CellInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(149, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void getDataRegistrationStateResponse_1_4(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_4.DataRegStateResult dataRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            dataRegStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(150, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void getIccCardStatusResponse_1_4(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_4.CardStatus cardStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            cardStatus.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(151, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void getPreferredNetworkTypeBitmapResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(152, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void setPreferredNetworkTypeBitmapResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(153, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void getDataCallListResponse_1_4(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_4.SetupDataCallResult> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_4.SetupDataCallResult.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(154, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void setupDataCallResponse_1_4(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_4.SetupDataCallResult setupDataCallResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            setupDataCallResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(155, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void setAllowedCarriersResponse_1_4(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(156, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void getAllowedCarriersResponse_1_4(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, CarrierRestrictionsWithPriority carrierRestrictionsWithPriority, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            carrierRestrictionsWithPriority.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(157, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadioResponse
        public void getSignalStrengthResponse_1_4(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_4.SignalStrength signalStrength) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            signalStrength.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(158, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void setSignalStrengthReportingCriteriaResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(159, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void setLinkCapacityReportingCriteriaResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(160, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void enableUiccApplicationsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(161, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void areUiccApplicationsEnabledResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(162, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void setSystemSelectionChannelsResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(163, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void startNetworkScanResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(164, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void setupDataCallResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_5.SetupDataCallResult setupDataCallResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            setupDataCallResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(165, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void getDataCallListResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_5.SetupDataCallResult> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_5.SetupDataCallResult.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(166, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void setInitialAttachApnResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(167, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void setDataProfileResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(168, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void setRadioPowerResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(169, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void setIndicationFilterResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(170, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void getBarringInfoResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, CellIdentity cellIdentity, ArrayList<BarringInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            cellIdentity.writeToParcel(hwParcel);
            BarringInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(171, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void getVoiceRegistrationStateResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_5.RegStateResult regStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            regStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(172, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void getDataRegistrationStateResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_5.RegStateResult regStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            regStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(173, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void getCellInfoListResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_5.CellInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_5.CellInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(174, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void setNetworkSelectionModeManualResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(175, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void sendCdmaSmsExpectMoreResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(176, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void supplySimDepersonalizationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(177, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadioResponse
        public void getIccCardStatusResponse_1_5(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_5.CardStatus cardStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            cardStatus.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(178, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void setRadioPowerResponse_1_6(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(179, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void setupDataCallResponse_1_6(RadioResponseInfo radioResponseInfo, SetupDataCallResult setupDataCallResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            setupDataCallResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(180, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getDataCallListResponse_1_6(RadioResponseInfo radioResponseInfo, ArrayList<SetupDataCallResult> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            SetupDataCallResult.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(181, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void sendSmsResponse_1_6(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(182, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void sendSmsExpectMoreResponse_1_6(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(183, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void sendCdmaSmsResponse_1_6(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(184, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void sendCdmaSmsExpectMoreResponse_1_6(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(185, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void setSimCardPowerResponse_1_6(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(186, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void setNrDualConnectivityStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(187, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void isNrDualConnectivityEnabledResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(188, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void allocatePduSessionIdResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(189, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void releasePduSessionIdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(190, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void startHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(191, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void cancelHandoverResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(192, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void setAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(193, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getAllowedNetworkTypesBitmapResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(194, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void setDataThrottlingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(195, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getSystemSelectionChannelsResponse(RadioResponseInfo radioResponseInfo, ArrayList<RadioAccessSpecifier> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            RadioAccessSpecifier.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(196, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getCellInfoListResponse_1_6(RadioResponseInfo radioResponseInfo, ArrayList<CellInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            CellInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(197, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getSignalStrengthResponse_1_6(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            signalStrength.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(198, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getVoiceRegistrationStateResponse_1_6(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            regStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(199, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getDataRegistrationStateResponse_1_6(RadioResponseInfo radioResponseInfo, RegStateResult regStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            regStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(200, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getCurrentCallsResponse_1_6(RadioResponseInfo radioResponseInfo, ArrayList<Call> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            Call.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(201, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getSlicingConfigResponse(RadioResponseInfo radioResponseInfo, SlicingConfig slicingConfig) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            slicingConfig.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(202, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(203, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void getSimPhonebookCapacityResponse(RadioResponseInfo radioResponseInfo, PhonebookCapacity phonebookCapacity) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            phonebookCapacity.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(204, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse
        public void updateSimPhonebookRecordsResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(205, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public String interfaceDescriptor() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ArrayList<byte[]> arrayList = new ArrayList<>();
                HwBlob buffer = hwParcel2.readBuffer(16L);
                int int32 = buffer.getInt32(8L);
                HwBlob embeddedBuffer = hwParcel2.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    embeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void ping() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public DebugInfo getDebugInfo() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                DebugInfo debugInfo = new DebugInfo();
                debugInfo.readFromParcel(hwParcel2);
                return debugInfo;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IRadioResponse {
        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IRadioResponse.kInterfaceName, android.hardware.radio.V1_5.IRadioResponse.kInterfaceName, android.hardware.radio.V1_4.IRadioResponse.kInterfaceName, android.hardware.radio.V1_3.IRadioResponse.kInterfaceName, android.hardware.radio.V1_2.IRadioResponse.kInterfaceName, android.hardware.radio.V1_1.IRadioResponse.kInterfaceName, android.hardware.radio.V1_0.IRadioResponse.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IRadioResponse.kInterfaceName;
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{68, 67, -11, -59, -9, -119, -41, 122, -76, -111, -86, -44, -49, 70, 115, -47, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, -69, -92, 80, 20, -42, -69, 72, 22, -120, -116, -70, -61, 122, -88, -63}, new byte[]{-72, 9, 25, 57, SprAttributeBase.TYPE_SHADOW, -87, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, -90, 55, -92, MidiConstants.STATUS_CONTROL_CHANGE, 24, 71, 103, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, 86, 1, -45, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 62, MidiConstants.STATUS_SONG_SELECT, -43, -103, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, -5, -57, -88, -47, 74, 20, -16, 21}, new byte[]{12, MidiConstants.STATUS_PROGRAM_CHANGE, -35, -121, -58, 52, -86, -45, 109, 125, MidiConstants.STATUS_SONG_POSITION, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 40, 50, -125, -98, -9, -34, -41, 25, 9, -37, -51, -31, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, -3, -42, -99, MidiConstants.STATUS_PROGRAM_CHANGE, -36, 82, -72}, new byte[]{-11, -5, -28, MidiConstants.STATUS_SONG_POSITION, -118, -98, 52, 107, -29, SprAttributeBase.TYPE_DURATION, 99, -20, -92, -26, -56, 100, 17, 74, 26, 111, -74, 72, -124, -37, 3, -3, -40, 37, 121, 26, -39, -72}, new byte[]{-38, -116, 106, -23, -111, -58, -92, -78, -124, -52, 110, 68, 83, 50, MidiConstants.STATUS_PITCH_BEND, 100, -30, -114, -24, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -108, -126, -19, 90, -1, -7, -47, 89, -20, 102, -108, -73}, new byte[]{0, 54, 107, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, -120, -7, -20, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, 88, 1, 73, 114, -109, -126, SprAttributeBase.TYPE_SHADOW, -56, 65, 61, 74, -77, 3, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -114, 55, -65, 58, -35, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, -114, 107, -126, -102}, new byte[]{-68, 60, -116, 35, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -123, -4, -93, -121, -99, -57, 75, 73, 11, -98, 91, -63, 6, 50, 88, 71, 13, 59, 76, 18, -9, -89, 75, MidiConstants.STATUS_SONG_POSITION, 21, -53, -67}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.radio.V1_6.IRadioResponse, android.hardware.radio.V1_5.IRadioResponse, android.hardware.radio.V1_4.IRadioResponse, android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IRadioResponse.kInterfaceName.equals(str)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String str) throws RemoteException {
            registerService(str);
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        @Override // android.os.HwBinder
        public void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo.readFromParcel(hwParcel);
                    CardStatus cardStatus = new CardStatus();
                    cardStatus.readFromParcel(hwParcel);
                    getIccCardStatusResponse(radioResponseInfo, cardStatus);
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo2 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo2.readFromParcel(hwParcel);
                    supplyIccPinForAppResponse(radioResponseInfo2, hwParcel.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo3 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo3.readFromParcel(hwParcel);
                    supplyIccPukForAppResponse(radioResponseInfo3, hwParcel.readInt32());
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo4 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo4.readFromParcel(hwParcel);
                    supplyIccPin2ForAppResponse(radioResponseInfo4, hwParcel.readInt32());
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo5 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo5.readFromParcel(hwParcel);
                    supplyIccPuk2ForAppResponse(radioResponseInfo5, hwParcel.readInt32());
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo6 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo6.readFromParcel(hwParcel);
                    changeIccPinForAppResponse(radioResponseInfo6, hwParcel.readInt32());
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo7 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo7.readFromParcel(hwParcel);
                    changeIccPin2ForAppResponse(radioResponseInfo7, hwParcel.readInt32());
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo8 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo8.readFromParcel(hwParcel);
                    supplyNetworkDepersonalizationResponse(radioResponseInfo8, hwParcel.readInt32());
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo9 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo9.readFromParcel(hwParcel);
                    getCurrentCallsResponse(radioResponseInfo9, android.hardware.radio.V1_0.Call.readVectorFromParcel(hwParcel));
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo10 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo10.readFromParcel(hwParcel);
                    dialResponse(radioResponseInfo10);
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo11 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo11.readFromParcel(hwParcel);
                    getIMSIForAppResponse(radioResponseInfo11, hwParcel.readString());
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo12 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo12.readFromParcel(hwParcel);
                    hangupConnectionResponse(radioResponseInfo12);
                    return;
                case 13:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo13 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo13.readFromParcel(hwParcel);
                    hangupWaitingOrBackgroundResponse(radioResponseInfo13);
                    return;
                case 14:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo14 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo14.readFromParcel(hwParcel);
                    hangupForegroundResumeBackgroundResponse(radioResponseInfo14);
                    return;
                case 15:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo15 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo15.readFromParcel(hwParcel);
                    switchWaitingOrHoldingAndActiveResponse(radioResponseInfo15);
                    return;
                case 16:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo16 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo16.readFromParcel(hwParcel);
                    conferenceResponse(radioResponseInfo16);
                    return;
                case 17:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo17 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo17.readFromParcel(hwParcel);
                    rejectCallResponse(radioResponseInfo17);
                    return;
                case 18:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo18 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo18.readFromParcel(hwParcel);
                    LastCallFailCauseInfo lastCallFailCauseInfo = new LastCallFailCauseInfo();
                    lastCallFailCauseInfo.readFromParcel(hwParcel);
                    getLastCallFailCauseResponse(radioResponseInfo18, lastCallFailCauseInfo);
                    return;
                case 19:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo19 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo19.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.SignalStrength signalStrength = new android.hardware.radio.V1_0.SignalStrength();
                    signalStrength.readFromParcel(hwParcel);
                    getSignalStrengthResponse(radioResponseInfo19, signalStrength);
                    return;
                case 20:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo20 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo20.readFromParcel(hwParcel);
                    VoiceRegStateResult voiceRegStateResult = new VoiceRegStateResult();
                    voiceRegStateResult.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse(radioResponseInfo20, voiceRegStateResult);
                    return;
                case 21:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo21 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo21.readFromParcel(hwParcel);
                    DataRegStateResult dataRegStateResult = new DataRegStateResult();
                    dataRegStateResult.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse(radioResponseInfo21, dataRegStateResult);
                    return;
                case 22:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo22 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo22.readFromParcel(hwParcel);
                    getOperatorResponse(radioResponseInfo22, hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 23:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo23 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo23.readFromParcel(hwParcel);
                    setRadioPowerResponse(radioResponseInfo23);
                    return;
                case 24:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo24 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo24.readFromParcel(hwParcel);
                    sendDtmfResponse(radioResponseInfo24);
                    return;
                case 25:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo25 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo25.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult = new SendSmsResult();
                    sendSmsResult.readFromParcel(hwParcel);
                    sendSmsResponse(radioResponseInfo25, sendSmsResult);
                    return;
                case 26:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo26 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo26.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult2 = new SendSmsResult();
                    sendSmsResult2.readFromParcel(hwParcel);
                    sendSMSExpectMoreResponse(radioResponseInfo26, sendSmsResult2);
                    return;
                case 27:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo27 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo27.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.SetupDataCallResult setupDataCallResult = new android.hardware.radio.V1_0.SetupDataCallResult();
                    setupDataCallResult.readFromParcel(hwParcel);
                    setupDataCallResponse(radioResponseInfo27, setupDataCallResult);
                    return;
                case 28:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo28 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo28.readFromParcel(hwParcel);
                    IccIoResult iccIoResult = new IccIoResult();
                    iccIoResult.readFromParcel(hwParcel);
                    iccIOForAppResponse(radioResponseInfo28, iccIoResult);
                    return;
                case 29:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo29 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo29.readFromParcel(hwParcel);
                    sendUssdResponse(radioResponseInfo29);
                    return;
                case 30:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo30 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo30.readFromParcel(hwParcel);
                    cancelPendingUssdResponse(radioResponseInfo30);
                    return;
                case 31:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo31 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo31.readFromParcel(hwParcel);
                    getClirResponse(radioResponseInfo31, hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 32:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo32 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo32.readFromParcel(hwParcel);
                    setClirResponse(radioResponseInfo32);
                    return;
                case 33:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo33 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo33.readFromParcel(hwParcel);
                    getCallForwardStatusResponse(radioResponseInfo33, CallForwardInfo.readVectorFromParcel(hwParcel));
                    return;
                case 34:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo34 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo34.readFromParcel(hwParcel);
                    setCallForwardResponse(radioResponseInfo34);
                    return;
                case 35:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo35 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo35.readFromParcel(hwParcel);
                    getCallWaitingResponse(radioResponseInfo35, hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 36:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo36 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo36.readFromParcel(hwParcel);
                    setCallWaitingResponse(radioResponseInfo36);
                    return;
                case 37:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo37 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo37.readFromParcel(hwParcel);
                    acknowledgeLastIncomingGsmSmsResponse(radioResponseInfo37);
                    return;
                case 38:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo38 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo38.readFromParcel(hwParcel);
                    acceptCallResponse(radioResponseInfo38);
                    return;
                case 39:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo39 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo39.readFromParcel(hwParcel);
                    deactivateDataCallResponse(radioResponseInfo39);
                    return;
                case 40:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo40 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo40.readFromParcel(hwParcel);
                    getFacilityLockForAppResponse(radioResponseInfo40, hwParcel.readInt32());
                    return;
                case 41:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo41 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo41.readFromParcel(hwParcel);
                    setFacilityLockForAppResponse(radioResponseInfo41, hwParcel.readInt32());
                    return;
                case 42:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo42 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo42.readFromParcel(hwParcel);
                    setBarringPasswordResponse(radioResponseInfo42);
                    return;
                case 43:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo43 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo43.readFromParcel(hwParcel);
                    getNetworkSelectionModeResponse(radioResponseInfo43, hwParcel.readBool());
                    return;
                case 44:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo44 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo44.readFromParcel(hwParcel);
                    setNetworkSelectionModeAutomaticResponse(radioResponseInfo44);
                    return;
                case 45:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo45 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo45.readFromParcel(hwParcel);
                    setNetworkSelectionModeManualResponse(radioResponseInfo45);
                    return;
                case 46:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo46 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo46.readFromParcel(hwParcel);
                    getAvailableNetworksResponse(radioResponseInfo46, OperatorInfo.readVectorFromParcel(hwParcel));
                    return;
                case 47:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo47 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo47.readFromParcel(hwParcel);
                    startDtmfResponse(radioResponseInfo47);
                    return;
                case 48:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo48 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo48.readFromParcel(hwParcel);
                    stopDtmfResponse(radioResponseInfo48);
                    return;
                case 49:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo49 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo49.readFromParcel(hwParcel);
                    getBasebandVersionResponse(radioResponseInfo49, hwParcel.readString());
                    return;
                case 50:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo50 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo50.readFromParcel(hwParcel);
                    separateConnectionResponse(radioResponseInfo50);
                    return;
                case 51:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo51 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo51.readFromParcel(hwParcel);
                    setMuteResponse(radioResponseInfo51);
                    return;
                case 52:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo52 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo52.readFromParcel(hwParcel);
                    getMuteResponse(radioResponseInfo52, hwParcel.readBool());
                    return;
                case 53:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo53 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo53.readFromParcel(hwParcel);
                    getClipResponse(radioResponseInfo53, hwParcel.readInt32());
                    return;
                case 54:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo54 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo54.readFromParcel(hwParcel);
                    getDataCallListResponse(radioResponseInfo54, android.hardware.radio.V1_0.SetupDataCallResult.readVectorFromParcel(hwParcel));
                    return;
                case 55:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo55 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo55.readFromParcel(hwParcel);
                    setSuppServiceNotificationsResponse(radioResponseInfo55);
                    return;
                case 56:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo56 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo56.readFromParcel(hwParcel);
                    writeSmsToSimResponse(radioResponseInfo56, hwParcel.readInt32());
                    return;
                case 57:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo57 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo57.readFromParcel(hwParcel);
                    deleteSmsOnSimResponse(radioResponseInfo57);
                    return;
                case 58:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo58 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo58.readFromParcel(hwParcel);
                    setBandModeResponse(radioResponseInfo58);
                    return;
                case 59:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo59 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo59.readFromParcel(hwParcel);
                    getAvailableBandModesResponse(radioResponseInfo59, hwParcel.readInt32Vector());
                    return;
                case 60:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo60 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo60.readFromParcel(hwParcel);
                    sendEnvelopeResponse(radioResponseInfo60, hwParcel.readString());
                    return;
                case 61:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo61 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo61.readFromParcel(hwParcel);
                    sendTerminalResponseToSimResponse(radioResponseInfo61);
                    return;
                case 62:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo62 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo62.readFromParcel(hwParcel);
                    handleStkCallSetupRequestFromSimResponse(radioResponseInfo62);
                    return;
                case 63:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo63 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo63.readFromParcel(hwParcel);
                    explicitCallTransferResponse(radioResponseInfo63);
                    return;
                case 64:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo64 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo64.readFromParcel(hwParcel);
                    setPreferredNetworkTypeResponse(radioResponseInfo64);
                    return;
                case 65:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo65 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo65.readFromParcel(hwParcel);
                    getPreferredNetworkTypeResponse(radioResponseInfo65, hwParcel.readInt32());
                    return;
                case 66:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo66 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo66.readFromParcel(hwParcel);
                    getNeighboringCidsResponse(radioResponseInfo66, NeighboringCell.readVectorFromParcel(hwParcel));
                    return;
                case 67:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo67 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo67.readFromParcel(hwParcel);
                    setLocationUpdatesResponse(radioResponseInfo67);
                    return;
                case 68:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo68 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo68.readFromParcel(hwParcel);
                    setCdmaSubscriptionSourceResponse(radioResponseInfo68);
                    return;
                case 69:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo69 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo69.readFromParcel(hwParcel);
                    setCdmaRoamingPreferenceResponse(radioResponseInfo69);
                    return;
                case 70:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo70 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo70.readFromParcel(hwParcel);
                    getCdmaRoamingPreferenceResponse(radioResponseInfo70, hwParcel.readInt32());
                    return;
                case 71:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo71 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo71.readFromParcel(hwParcel);
                    setTTYModeResponse(radioResponseInfo71);
                    return;
                case 72:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo72 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo72.readFromParcel(hwParcel);
                    getTTYModeResponse(radioResponseInfo72, hwParcel.readInt32());
                    return;
                case 73:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo73 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo73.readFromParcel(hwParcel);
                    setPreferredVoicePrivacyResponse(radioResponseInfo73);
                    return;
                case 74:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo74 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo74.readFromParcel(hwParcel);
                    getPreferredVoicePrivacyResponse(radioResponseInfo74, hwParcel.readBool());
                    return;
                case 75:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo75 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo75.readFromParcel(hwParcel);
                    sendCDMAFeatureCodeResponse(radioResponseInfo75);
                    return;
                case 76:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo76 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo76.readFromParcel(hwParcel);
                    sendBurstDtmfResponse(radioResponseInfo76);
                    return;
                case 77:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo77 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo77.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult3 = new SendSmsResult();
                    sendSmsResult3.readFromParcel(hwParcel);
                    sendCdmaSmsResponse(radioResponseInfo77, sendSmsResult3);
                    return;
                case 78:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo78 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo78.readFromParcel(hwParcel);
                    acknowledgeLastIncomingCdmaSmsResponse(radioResponseInfo78);
                    return;
                case 79:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo79 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo79.readFromParcel(hwParcel);
                    getGsmBroadcastConfigResponse(radioResponseInfo79, GsmBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 80:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo80 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo80.readFromParcel(hwParcel);
                    setGsmBroadcastConfigResponse(radioResponseInfo80);
                    return;
                case 81:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo81 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo81.readFromParcel(hwParcel);
                    setGsmBroadcastActivationResponse(radioResponseInfo81);
                    return;
                case 82:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo82 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo82.readFromParcel(hwParcel);
                    getCdmaBroadcastConfigResponse(radioResponseInfo82, CdmaBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 83:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo83 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo83.readFromParcel(hwParcel);
                    setCdmaBroadcastConfigResponse(radioResponseInfo83);
                    return;
                case 84:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo84 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo84.readFromParcel(hwParcel);
                    setCdmaBroadcastActivationResponse(radioResponseInfo84);
                    return;
                case 85:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo85 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo85.readFromParcel(hwParcel);
                    getCDMASubscriptionResponse(radioResponseInfo85, hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 86:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo86 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo86.readFromParcel(hwParcel);
                    writeSmsToRuimResponse(radioResponseInfo86, hwParcel.readInt32());
                    return;
                case 87:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo87 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo87.readFromParcel(hwParcel);
                    deleteSmsOnRuimResponse(radioResponseInfo87);
                    return;
                case 88:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo88 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo88.readFromParcel(hwParcel);
                    getDeviceIdentityResponse(radioResponseInfo88, hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 89:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo89 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo89.readFromParcel(hwParcel);
                    exitEmergencyCallbackModeResponse(radioResponseInfo89);
                    return;
                case 90:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo90 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo90.readFromParcel(hwParcel);
                    getSmscAddressResponse(radioResponseInfo90, hwParcel.readString());
                    return;
                case 91:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo91 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo91.readFromParcel(hwParcel);
                    setSmscAddressResponse(radioResponseInfo91);
                    return;
                case 92:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo92 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo92.readFromParcel(hwParcel);
                    reportSmsMemoryStatusResponse(radioResponseInfo92);
                    return;
                case 93:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo93 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo93.readFromParcel(hwParcel);
                    reportStkServiceIsRunningResponse(radioResponseInfo93);
                    return;
                case 94:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo94 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo94.readFromParcel(hwParcel);
                    getCdmaSubscriptionSourceResponse(radioResponseInfo94, hwParcel.readInt32());
                    return;
                case 95:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo95 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo95.readFromParcel(hwParcel);
                    requestIsimAuthenticationResponse(radioResponseInfo95, hwParcel.readString());
                    return;
                case 96:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo96 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo96.readFromParcel(hwParcel);
                    acknowledgeIncomingGsmSmsWithPduResponse(radioResponseInfo96);
                    return;
                case 97:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo97 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo97.readFromParcel(hwParcel);
                    IccIoResult iccIoResult2 = new IccIoResult();
                    iccIoResult2.readFromParcel(hwParcel);
                    sendEnvelopeWithStatusResponse(radioResponseInfo97, iccIoResult2);
                    return;
                case 98:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo98 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo98.readFromParcel(hwParcel);
                    getVoiceRadioTechnologyResponse(radioResponseInfo98, hwParcel.readInt32());
                    return;
                case 99:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo99 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo99.readFromParcel(hwParcel);
                    getCellInfoListResponse(radioResponseInfo99, android.hardware.radio.V1_0.CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 100:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo100 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo100.readFromParcel(hwParcel);
                    setCellInfoListRateResponse(radioResponseInfo100);
                    return;
                case 101:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo101 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo101.readFromParcel(hwParcel);
                    setInitialAttachApnResponse(radioResponseInfo101);
                    return;
                case 102:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo102 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo102.readFromParcel(hwParcel);
                    getImsRegistrationStateResponse(radioResponseInfo102, hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 103:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo103 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo103.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult4 = new SendSmsResult();
                    sendSmsResult4.readFromParcel(hwParcel);
                    sendImsSmsResponse(radioResponseInfo103, sendSmsResult4);
                    return;
                case 104:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo104 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo104.readFromParcel(hwParcel);
                    IccIoResult iccIoResult3 = new IccIoResult();
                    iccIoResult3.readFromParcel(hwParcel);
                    iccTransmitApduBasicChannelResponse(radioResponseInfo104, iccIoResult3);
                    return;
                case 105:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo105 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo105.readFromParcel(hwParcel);
                    iccOpenLogicalChannelResponse(radioResponseInfo105, hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 106:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo106 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo106.readFromParcel(hwParcel);
                    iccCloseLogicalChannelResponse(radioResponseInfo106);
                    return;
                case 107:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo107 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo107.readFromParcel(hwParcel);
                    IccIoResult iccIoResult4 = new IccIoResult();
                    iccIoResult4.readFromParcel(hwParcel);
                    iccTransmitApduLogicalChannelResponse(radioResponseInfo107, iccIoResult4);
                    return;
                case 108:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo108 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo108.readFromParcel(hwParcel);
                    nvReadItemResponse(radioResponseInfo108, hwParcel.readString());
                    return;
                case 109:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo109 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo109.readFromParcel(hwParcel);
                    nvWriteItemResponse(radioResponseInfo109);
                    return;
                case 110:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo110 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo110.readFromParcel(hwParcel);
                    nvWriteCdmaPrlResponse(radioResponseInfo110);
                    return;
                case 111:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo111 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo111.readFromParcel(hwParcel);
                    nvResetConfigResponse(radioResponseInfo111);
                    return;
                case 112:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo112 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo112.readFromParcel(hwParcel);
                    setUiccSubscriptionResponse(radioResponseInfo112);
                    return;
                case 113:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo113 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo113.readFromParcel(hwParcel);
                    setDataAllowedResponse(radioResponseInfo113);
                    return;
                case 114:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo114 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo114.readFromParcel(hwParcel);
                    getHardwareConfigResponse(radioResponseInfo114, HardwareConfig.readVectorFromParcel(hwParcel));
                    return;
                case 115:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo115 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo115.readFromParcel(hwParcel);
                    IccIoResult iccIoResult5 = new IccIoResult();
                    iccIoResult5.readFromParcel(hwParcel);
                    requestIccSimAuthenticationResponse(radioResponseInfo115, iccIoResult5);
                    return;
                case 116:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo116 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo116.readFromParcel(hwParcel);
                    setDataProfileResponse(radioResponseInfo116);
                    return;
                case 117:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo117 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo117.readFromParcel(hwParcel);
                    requestShutdownResponse(radioResponseInfo117);
                    return;
                case 118:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo118 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo118.readFromParcel(hwParcel);
                    RadioCapability radioCapability = new RadioCapability();
                    radioCapability.readFromParcel(hwParcel);
                    getRadioCapabilityResponse(radioResponseInfo118, radioCapability);
                    return;
                case 119:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo119 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo119.readFromParcel(hwParcel);
                    RadioCapability radioCapability2 = new RadioCapability();
                    radioCapability2.readFromParcel(hwParcel);
                    setRadioCapabilityResponse(radioResponseInfo119, radioCapability2);
                    return;
                case 120:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo120 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo120.readFromParcel(hwParcel);
                    LceStatusInfo lceStatusInfo = new LceStatusInfo();
                    lceStatusInfo.readFromParcel(hwParcel);
                    startLceServiceResponse(radioResponseInfo120, lceStatusInfo);
                    return;
                case 121:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo121 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo121.readFromParcel(hwParcel);
                    LceStatusInfo lceStatusInfo2 = new LceStatusInfo();
                    lceStatusInfo2.readFromParcel(hwParcel);
                    stopLceServiceResponse(radioResponseInfo121, lceStatusInfo2);
                    return;
                case 122:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo122 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo122.readFromParcel(hwParcel);
                    LceDataInfo lceDataInfo = new LceDataInfo();
                    lceDataInfo.readFromParcel(hwParcel);
                    pullLceDataResponse(radioResponseInfo122, lceDataInfo);
                    return;
                case 123:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo123 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo123.readFromParcel(hwParcel);
                    ActivityStatsInfo activityStatsInfo = new ActivityStatsInfo();
                    activityStatsInfo.readFromParcel(hwParcel);
                    getModemActivityInfoResponse(radioResponseInfo123, activityStatsInfo);
                    return;
                case 124:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo124 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo124.readFromParcel(hwParcel);
                    setAllowedCarriersResponse(radioResponseInfo124, hwParcel.readInt32());
                    return;
                case 125:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo125 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo125.readFromParcel(hwParcel);
                    boolean bool = hwParcel.readBool();
                    CarrierRestrictions carrierRestrictions = new CarrierRestrictions();
                    carrierRestrictions.readFromParcel(hwParcel);
                    getAllowedCarriersResponse(radioResponseInfo125, bool, carrierRestrictions);
                    return;
                case 126:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo126 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo126.readFromParcel(hwParcel);
                    sendDeviceStateResponse(radioResponseInfo126);
                    return;
                case 127:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo127 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo127.readFromParcel(hwParcel);
                    setIndicationFilterResponse(radioResponseInfo127);
                    return;
                case 128:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo128 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo128.readFromParcel(hwParcel);
                    setSimCardPowerResponse(radioResponseInfo128);
                    return;
                case 129:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    acknowledgeRequest(hwParcel.readInt32());
                    return;
                case 130:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo129 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo129.readFromParcel(hwParcel);
                    setCarrierInfoForImsiEncryptionResponse(radioResponseInfo129);
                    return;
                case 131:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo130 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo130.readFromParcel(hwParcel);
                    setSimCardPowerResponse_1_1(radioResponseInfo130);
                    return;
                case 132:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo131 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo131.readFromParcel(hwParcel);
                    startNetworkScanResponse(radioResponseInfo131);
                    return;
                case 133:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo132 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo132.readFromParcel(hwParcel);
                    stopNetworkScanResponse(radioResponseInfo132);
                    return;
                case 134:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo133 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo133.readFromParcel(hwParcel);
                    KeepaliveStatus keepaliveStatus = new KeepaliveStatus();
                    keepaliveStatus.readFromParcel(hwParcel);
                    startKeepaliveResponse(radioResponseInfo133, keepaliveStatus);
                    return;
                case 135:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo134 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo134.readFromParcel(hwParcel);
                    stopKeepaliveResponse(radioResponseInfo134);
                    return;
                case 136:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo135 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo135.readFromParcel(hwParcel);
                    getCellInfoListResponse_1_2(radioResponseInfo135, android.hardware.radio.V1_2.CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 137:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo136 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo136.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.CardStatus cardStatus2 = new android.hardware.radio.V1_2.CardStatus();
                    cardStatus2.readFromParcel(hwParcel);
                    getIccCardStatusResponse_1_2(radioResponseInfo136, cardStatus2);
                    return;
                case 138:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo137 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo137.readFromParcel(hwParcel);
                    setSignalStrengthReportingCriteriaResponse(radioResponseInfo137);
                    return;
                case 139:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo138 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo138.readFromParcel(hwParcel);
                    setLinkCapacityReportingCriteriaResponse(radioResponseInfo138);
                    return;
                case 140:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo139 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo139.readFromParcel(hwParcel);
                    getCurrentCallsResponse_1_2(radioResponseInfo139, android.hardware.radio.V1_2.Call.readVectorFromParcel(hwParcel));
                    return;
                case 141:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo140 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo140.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.SignalStrength signalStrength2 = new android.hardware.radio.V1_2.SignalStrength();
                    signalStrength2.readFromParcel(hwParcel);
                    getSignalStrengthResponse_1_2(radioResponseInfo140, signalStrength2);
                    return;
                case 142:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo141 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo141.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.VoiceRegStateResult voiceRegStateResult2 = new android.hardware.radio.V1_2.VoiceRegStateResult();
                    voiceRegStateResult2.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse_1_2(radioResponseInfo141, voiceRegStateResult2);
                    return;
                case 143:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo142 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo142.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.DataRegStateResult dataRegStateResult2 = new android.hardware.radio.V1_2.DataRegStateResult();
                    dataRegStateResult2.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse_1_2(radioResponseInfo142, dataRegStateResult2);
                    return;
                case 144:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo143 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo143.readFromParcel(hwParcel);
                    setSystemSelectionChannelsResponse(radioResponseInfo143);
                    return;
                case 145:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo144 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo144.readFromParcel(hwParcel);
                    enableModemResponse(radioResponseInfo144);
                    return;
                case 146:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo145 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo145.readFromParcel(hwParcel);
                    getModemStackStatusResponse(radioResponseInfo145, hwParcel.readBool());
                    return;
                case 147:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo146 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo146.readFromParcel(hwParcel);
                    emergencyDialResponse(radioResponseInfo146);
                    return;
                case 148:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo147 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo147.readFromParcel(hwParcel);
                    startNetworkScanResponse_1_4(radioResponseInfo147);
                    return;
                case 149:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo148 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo148.readFromParcel(hwParcel);
                    getCellInfoListResponse_1_4(radioResponseInfo148, android.hardware.radio.V1_4.CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 150:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo149 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo149.readFromParcel(hwParcel);
                    android.hardware.radio.V1_4.DataRegStateResult dataRegStateResult3 = new android.hardware.radio.V1_4.DataRegStateResult();
                    dataRegStateResult3.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse_1_4(radioResponseInfo149, dataRegStateResult3);
                    return;
                case 151:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo150 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo150.readFromParcel(hwParcel);
                    android.hardware.radio.V1_4.CardStatus cardStatus3 = new android.hardware.radio.V1_4.CardStatus();
                    cardStatus3.readFromParcel(hwParcel);
                    getIccCardStatusResponse_1_4(radioResponseInfo150, cardStatus3);
                    return;
                case 152:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo151 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo151.readFromParcel(hwParcel);
                    getPreferredNetworkTypeBitmapResponse(radioResponseInfo151, hwParcel.readInt32());
                    return;
                case 153:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo152 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo152.readFromParcel(hwParcel);
                    setPreferredNetworkTypeBitmapResponse(radioResponseInfo152);
                    return;
                case 154:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo153 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo153.readFromParcel(hwParcel);
                    getDataCallListResponse_1_4(radioResponseInfo153, android.hardware.radio.V1_4.SetupDataCallResult.readVectorFromParcel(hwParcel));
                    return;
                case 155:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo154 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo154.readFromParcel(hwParcel);
                    android.hardware.radio.V1_4.SetupDataCallResult setupDataCallResult2 = new android.hardware.radio.V1_4.SetupDataCallResult();
                    setupDataCallResult2.readFromParcel(hwParcel);
                    setupDataCallResponse_1_4(radioResponseInfo154, setupDataCallResult2);
                    return;
                case 156:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo155 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo155.readFromParcel(hwParcel);
                    setAllowedCarriersResponse_1_4(radioResponseInfo155);
                    return;
                case 157:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo156 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo156.readFromParcel(hwParcel);
                    CarrierRestrictionsWithPriority carrierRestrictionsWithPriority = new CarrierRestrictionsWithPriority();
                    carrierRestrictionsWithPriority.readFromParcel(hwParcel);
                    getAllowedCarriersResponse_1_4(radioResponseInfo156, carrierRestrictionsWithPriority, hwParcel.readInt32());
                    return;
                case 158:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo157 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo157.readFromParcel(hwParcel);
                    android.hardware.radio.V1_4.SignalStrength signalStrength3 = new android.hardware.radio.V1_4.SignalStrength();
                    signalStrength3.readFromParcel(hwParcel);
                    getSignalStrengthResponse_1_4(radioResponseInfo157, signalStrength3);
                    return;
                case 159:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo158 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo158.readFromParcel(hwParcel);
                    setSignalStrengthReportingCriteriaResponse_1_5(radioResponseInfo158);
                    return;
                case 160:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo159 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo159.readFromParcel(hwParcel);
                    setLinkCapacityReportingCriteriaResponse_1_5(radioResponseInfo159);
                    return;
                case 161:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo160 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo160.readFromParcel(hwParcel);
                    enableUiccApplicationsResponse(radioResponseInfo160);
                    return;
                case 162:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo161 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo161.readFromParcel(hwParcel);
                    areUiccApplicationsEnabledResponse(radioResponseInfo161, hwParcel.readBool());
                    return;
                case 163:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo162 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo162.readFromParcel(hwParcel);
                    setSystemSelectionChannelsResponse_1_5(radioResponseInfo162);
                    return;
                case 164:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo163 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo163.readFromParcel(hwParcel);
                    startNetworkScanResponse_1_5(radioResponseInfo163);
                    return;
                case 165:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo164 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo164.readFromParcel(hwParcel);
                    android.hardware.radio.V1_5.SetupDataCallResult setupDataCallResult3 = new android.hardware.radio.V1_5.SetupDataCallResult();
                    setupDataCallResult3.readFromParcel(hwParcel);
                    setupDataCallResponse_1_5(radioResponseInfo164, setupDataCallResult3);
                    return;
                case 166:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo165 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo165.readFromParcel(hwParcel);
                    getDataCallListResponse_1_5(radioResponseInfo165, android.hardware.radio.V1_5.SetupDataCallResult.readVectorFromParcel(hwParcel));
                    return;
                case 167:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo166 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo166.readFromParcel(hwParcel);
                    setInitialAttachApnResponse_1_5(radioResponseInfo166);
                    return;
                case 168:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo167 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo167.readFromParcel(hwParcel);
                    setDataProfileResponse_1_5(radioResponseInfo167);
                    return;
                case 169:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo168 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo168.readFromParcel(hwParcel);
                    setRadioPowerResponse_1_5(radioResponseInfo168);
                    return;
                case 170:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo169 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo169.readFromParcel(hwParcel);
                    setIndicationFilterResponse_1_5(radioResponseInfo169);
                    return;
                case 171:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo170 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo170.readFromParcel(hwParcel);
                    CellIdentity cellIdentity = new CellIdentity();
                    cellIdentity.readFromParcel(hwParcel);
                    getBarringInfoResponse(radioResponseInfo170, cellIdentity, BarringInfo.readVectorFromParcel(hwParcel));
                    return;
                case 172:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo171 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo171.readFromParcel(hwParcel);
                    android.hardware.radio.V1_5.RegStateResult regStateResult = new android.hardware.radio.V1_5.RegStateResult();
                    regStateResult.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse_1_5(radioResponseInfo171, regStateResult);
                    return;
                case 173:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo172 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo172.readFromParcel(hwParcel);
                    android.hardware.radio.V1_5.RegStateResult regStateResult2 = new android.hardware.radio.V1_5.RegStateResult();
                    regStateResult2.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse_1_5(radioResponseInfo172, regStateResult2);
                    return;
                case 174:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo173 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo173.readFromParcel(hwParcel);
                    getCellInfoListResponse_1_5(radioResponseInfo173, android.hardware.radio.V1_5.CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 175:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo174 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo174.readFromParcel(hwParcel);
                    setNetworkSelectionModeManualResponse_1_5(radioResponseInfo174);
                    return;
                case 176:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo175 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo175.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult5 = new SendSmsResult();
                    sendSmsResult5.readFromParcel(hwParcel);
                    sendCdmaSmsExpectMoreResponse(radioResponseInfo175, sendSmsResult5);
                    return;
                case 177:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo176 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo176.readFromParcel(hwParcel);
                    supplySimDepersonalizationResponse(radioResponseInfo176, hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 178:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo177 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo177.readFromParcel(hwParcel);
                    android.hardware.radio.V1_5.CardStatus cardStatus4 = new android.hardware.radio.V1_5.CardStatus();
                    cardStatus4.readFromParcel(hwParcel);
                    getIccCardStatusResponse_1_5(radioResponseInfo177, cardStatus4);
                    return;
                case 179:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo178 = new RadioResponseInfo();
                    radioResponseInfo178.readFromParcel(hwParcel);
                    setRadioPowerResponse_1_6(radioResponseInfo178);
                    return;
                case 180:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo179 = new RadioResponseInfo();
                    radioResponseInfo179.readFromParcel(hwParcel);
                    SetupDataCallResult setupDataCallResult4 = new SetupDataCallResult();
                    setupDataCallResult4.readFromParcel(hwParcel);
                    setupDataCallResponse_1_6(radioResponseInfo179, setupDataCallResult4);
                    return;
                case 181:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo180 = new RadioResponseInfo();
                    radioResponseInfo180.readFromParcel(hwParcel);
                    getDataCallListResponse_1_6(radioResponseInfo180, SetupDataCallResult.readVectorFromParcel(hwParcel));
                    return;
                case 182:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo181 = new RadioResponseInfo();
                    radioResponseInfo181.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult6 = new SendSmsResult();
                    sendSmsResult6.readFromParcel(hwParcel);
                    sendSmsResponse_1_6(radioResponseInfo181, sendSmsResult6);
                    return;
                case 183:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo182 = new RadioResponseInfo();
                    radioResponseInfo182.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult7 = new SendSmsResult();
                    sendSmsResult7.readFromParcel(hwParcel);
                    sendSmsExpectMoreResponse_1_6(radioResponseInfo182, sendSmsResult7);
                    return;
                case 184:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo183 = new RadioResponseInfo();
                    radioResponseInfo183.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult8 = new SendSmsResult();
                    sendSmsResult8.readFromParcel(hwParcel);
                    sendCdmaSmsResponse_1_6(radioResponseInfo183, sendSmsResult8);
                    return;
                case 185:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo184 = new RadioResponseInfo();
                    radioResponseInfo184.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult9 = new SendSmsResult();
                    sendSmsResult9.readFromParcel(hwParcel);
                    sendCdmaSmsExpectMoreResponse_1_6(radioResponseInfo184, sendSmsResult9);
                    return;
                case 186:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo185 = new RadioResponseInfo();
                    radioResponseInfo185.readFromParcel(hwParcel);
                    setSimCardPowerResponse_1_6(radioResponseInfo185);
                    return;
                case 187:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo186 = new RadioResponseInfo();
                    radioResponseInfo186.readFromParcel(hwParcel);
                    setNrDualConnectivityStateResponse(radioResponseInfo186);
                    return;
                case 188:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo187 = new RadioResponseInfo();
                    radioResponseInfo187.readFromParcel(hwParcel);
                    isNrDualConnectivityEnabledResponse(radioResponseInfo187, hwParcel.readBool());
                    return;
                case 189:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo188 = new RadioResponseInfo();
                    radioResponseInfo188.readFromParcel(hwParcel);
                    allocatePduSessionIdResponse(radioResponseInfo188, hwParcel.readInt32());
                    return;
                case 190:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo189 = new RadioResponseInfo();
                    radioResponseInfo189.readFromParcel(hwParcel);
                    releasePduSessionIdResponse(radioResponseInfo189);
                    return;
                case 191:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo190 = new RadioResponseInfo();
                    radioResponseInfo190.readFromParcel(hwParcel);
                    startHandoverResponse(radioResponseInfo190);
                    return;
                case 192:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo191 = new RadioResponseInfo();
                    radioResponseInfo191.readFromParcel(hwParcel);
                    cancelHandoverResponse(radioResponseInfo191);
                    return;
                case 193:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo192 = new RadioResponseInfo();
                    radioResponseInfo192.readFromParcel(hwParcel);
                    setAllowedNetworkTypesBitmapResponse(radioResponseInfo192);
                    return;
                case 194:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo193 = new RadioResponseInfo();
                    radioResponseInfo193.readFromParcel(hwParcel);
                    getAllowedNetworkTypesBitmapResponse(radioResponseInfo193, hwParcel.readInt32());
                    return;
                case 195:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo194 = new RadioResponseInfo();
                    radioResponseInfo194.readFromParcel(hwParcel);
                    setDataThrottlingResponse(radioResponseInfo194);
                    return;
                case 196:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo195 = new RadioResponseInfo();
                    radioResponseInfo195.readFromParcel(hwParcel);
                    getSystemSelectionChannelsResponse(radioResponseInfo195, RadioAccessSpecifier.readVectorFromParcel(hwParcel));
                    return;
                case 197:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo196 = new RadioResponseInfo();
                    radioResponseInfo196.readFromParcel(hwParcel);
                    getCellInfoListResponse_1_6(radioResponseInfo196, CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 198:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo197 = new RadioResponseInfo();
                    radioResponseInfo197.readFromParcel(hwParcel);
                    SignalStrength signalStrength4 = new SignalStrength();
                    signalStrength4.readFromParcel(hwParcel);
                    getSignalStrengthResponse_1_6(radioResponseInfo197, signalStrength4);
                    return;
                case 199:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo198 = new RadioResponseInfo();
                    radioResponseInfo198.readFromParcel(hwParcel);
                    RegStateResult regStateResult3 = new RegStateResult();
                    regStateResult3.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse_1_6(radioResponseInfo198, regStateResult3);
                    return;
                case 200:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo199 = new RadioResponseInfo();
                    radioResponseInfo199.readFromParcel(hwParcel);
                    RegStateResult regStateResult4 = new RegStateResult();
                    regStateResult4.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse_1_6(radioResponseInfo199, regStateResult4);
                    return;
                case 201:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo200 = new RadioResponseInfo();
                    radioResponseInfo200.readFromParcel(hwParcel);
                    getCurrentCallsResponse_1_6(radioResponseInfo200, Call.readVectorFromParcel(hwParcel));
                    return;
                case 202:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo201 = new RadioResponseInfo();
                    radioResponseInfo201.readFromParcel(hwParcel);
                    SlicingConfig slicingConfig = new SlicingConfig();
                    slicingConfig.readFromParcel(hwParcel);
                    getSlicingConfigResponse(radioResponseInfo201, slicingConfig);
                    return;
                case 203:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo202 = new RadioResponseInfo();
                    radioResponseInfo202.readFromParcel(hwParcel);
                    getSimPhonebookRecordsResponse(radioResponseInfo202);
                    return;
                case 204:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo203 = new RadioResponseInfo();
                    radioResponseInfo203.readFromParcel(hwParcel);
                    PhonebookCapacity phonebookCapacity = new PhonebookCapacity();
                    phonebookCapacity.readFromParcel(hwParcel);
                    getSimPhonebookCapacityResponse(radioResponseInfo203, phonebookCapacity);
                    return;
                case 205:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo204 = new RadioResponseInfo();
                    radioResponseInfo204.readFromParcel(hwParcel);
                    updateSimPhonebookRecordsResponse(radioResponseInfo204, hwParcel.readInt32());
                    return;
                default:
                    switch (i) {
                        case 256067662:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList<String> arrayListInterfaceChain = interfaceChain();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeStringVector(arrayListInterfaceChain);
                            hwParcel2.send();
                            return;
                        case 256131655:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                            hwParcel2.writeStatus(0);
                            hwParcel2.send();
                            return;
                        case 256136003:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            String strInterfaceDescriptor = interfaceDescriptor();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeString(strInterfaceDescriptor);
                            hwParcel2.send();
                            return;
                        case 256398152:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList<byte[]> hashChain = getHashChain();
                            hwParcel2.writeStatus(0);
                            HwBlob hwBlob = new HwBlob(16);
                            int size = hashChain.size();
                            hwBlob.putInt32(8L, size);
                            hwBlob.putBool(12L, false);
                            HwBlob hwBlob2 = new HwBlob(size * 32);
                            for (int i3 = 0; i3 < size; i3++) {
                                long j = i3 * 32;
                                byte[] bArr = hashChain.get(i3);
                                if (bArr == null || bArr.length != 32) {
                                    throw new IllegalArgumentException("Array element is not of the expected length");
                                }
                                hwBlob2.putInt8Array(j, bArr);
                            }
                            hwBlob.putBlob(0L, hwBlob2);
                            hwParcel2.writeBuffer(hwBlob);
                            hwParcel2.send();
                            return;
                        case 256462420:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            setHALInstrumentation();
                            return;
                        case 256921159:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ping();
                            hwParcel2.writeStatus(0);
                            hwParcel2.send();
                            return;
                        case 257049926:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            DebugInfo debugInfo = getDebugInfo();
                            hwParcel2.writeStatus(0);
                            debugInfo.writeToParcel(hwParcel2);
                            hwParcel2.send();
                            return;
                        case 257120595:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            notifySyspropsChanged();
                            return;
                        default:
                            return;
                    }
            }
        }
    }
}
