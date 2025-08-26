package android.hardware.radio.V1_2;

import android.hardware.radio.V1_0.ActivityStatsInfo;
import android.hardware.radio.V1_0.CallForwardInfo;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.IccIoResult;
import android.hardware.radio.V1_0.LastCallFailCauseInfo;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.LceStatusInfo;
import android.hardware.radio.V1_0.NeighboringCell;
import android.hardware.radio.V1_0.OperatorInfo;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.V1_0.SendSmsResult;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_1.KeepaliveStatus;
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
public interface IRadioResponse extends android.hardware.radio.V1_1.IRadioResponse {
    public static final String kInterfaceName = "android.hardware.radio@1.2::IRadioResponse";

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void getCellInfoListResponse_1_2(RadioResponseInfo radioResponseInfo, ArrayList<CellInfo> arrayList) throws RemoteException;

    void getCurrentCallsResponse_1_2(RadioResponseInfo radioResponseInfo, ArrayList<Call> arrayList) throws RemoteException;

    void getDataRegistrationStateResponse_1_2(RadioResponseInfo radioResponseInfo, DataRegStateResult dataRegStateResult) throws RemoteException;

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getIccCardStatusResponse_1_2(RadioResponseInfo radioResponseInfo, CardStatus cardStatus) throws RemoteException;

    void getSignalStrengthResponse_1_2(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) throws RemoteException;

    void getVoiceRegistrationStateResponse_1_2(RadioResponseInfo radioResponseInfo, VoiceRegStateResult voiceRegStateResult) throws RemoteException;

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    void setLinkCapacityReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    void setSignalStrengthReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Override // android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.radio@1.2::IRadioResponse]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getIccCardStatusResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.CardStatus cardStatus) throws RemoteException {
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
        public void supplyIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void supplyIccPukForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void supplyIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void supplyIccPuk2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void changeIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void changeIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void supplyNetworkDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void getCurrentCallsResponse(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_0.Call> arrayList) throws RemoteException {
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
        public void dialResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getIMSIForAppResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
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
        public void hangupConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void hangupWaitingOrBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void hangupForegroundResumeBackgroundResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void conferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void rejectCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getLastCallFailCauseResponse(RadioResponseInfo radioResponseInfo, LastCallFailCauseInfo lastCallFailCauseInfo) throws RemoteException {
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
        public void getSignalStrengthResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SignalStrength signalStrength) throws RemoteException {
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
        public void getVoiceRegistrationStateResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.VoiceRegStateResult voiceRegStateResult) throws RemoteException {
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
        public void getDataRegistrationStateResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.DataRegStateResult dataRegStateResult) throws RemoteException {
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
        public void getOperatorResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3) throws RemoteException {
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
        public void setRadioPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void sendDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void sendSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
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
        public void sendSMSExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
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
        public void setupDataCallResponse(RadioResponseInfo radioResponseInfo, SetupDataCallResult setupDataCallResult) throws RemoteException {
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
        public void iccIOForAppResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
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
        public void sendUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void cancelPendingUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getClirResponse(RadioResponseInfo radioResponseInfo, int i, int i2) throws RemoteException {
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
        public void setClirResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getCallForwardStatusResponse(RadioResponseInfo radioResponseInfo, ArrayList<CallForwardInfo> arrayList) throws RemoteException {
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
        public void setCallForwardResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getCallWaitingResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException {
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
        public void setCallWaitingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void acceptCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void deactivateDataCallResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void setFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void setBarringPasswordResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getNetworkSelectionModeResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
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
        public void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setNetworkSelectionModeManualResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getAvailableNetworksResponse(RadioResponseInfo radioResponseInfo, ArrayList<OperatorInfo> arrayList) throws RemoteException {
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
        public void startDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void stopDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getBasebandVersionResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
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
        public void separateConnectionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setMuteResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getMuteResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
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
        public void getClipResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void getDataCallListResponse(RadioResponseInfo radioResponseInfo, ArrayList<SetupDataCallResult> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            SetupDataCallResult.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(54, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setSuppServiceNotificationsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void writeSmsToSimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void deleteSmsOnSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setBandModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getAvailableBandModesResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException {
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
        public void sendEnvelopeResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
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
        public void sendTerminalResponseToSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void explicitCallTransferResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setPreferredNetworkTypeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getPreferredNetworkTypeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void getNeighboringCidsResponse(RadioResponseInfo radioResponseInfo, ArrayList<NeighboringCell> arrayList) throws RemoteException {
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
        public void setLocationUpdatesResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void setTTYModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getTTYModeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void setPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo, boolean z) throws RemoteException {
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
        public void sendCDMAFeatureCodeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void sendBurstDtmfResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void sendCdmaSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
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
        public void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) throws RemoteException {
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
        public void setGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setGsmBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) throws RemoteException {
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
        public void setCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setCdmaBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getCDMASubscriptionResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4, String str5) throws RemoteException {
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
        public void writeSmsToRuimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void deleteSmsOnRuimResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getDeviceIdentityResponse(RadioResponseInfo radioResponseInfo, String str, String str2, String str3, String str4) throws RemoteException {
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
        public void exitEmergencyCallbackModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getSmscAddressResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
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
        public void setSmscAddressResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void reportSmsMemoryStatusResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void reportStkServiceIsRunningResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void requestIsimAuthenticationResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
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
        public void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void sendEnvelopeWithStatusResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
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
        public void getVoiceRadioTechnologyResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void getCellInfoListResponse(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_0.CellInfo> arrayList) throws RemoteException {
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
        public void setCellInfoListRateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setInitialAttachApnResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getImsRegistrationStateResponse(RadioResponseInfo radioResponseInfo, boolean z, int i) throws RemoteException {
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
        public void sendImsSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) throws RemoteException {
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
        public void iccTransmitApduBasicChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
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
        public void iccOpenLogicalChannelResponse(RadioResponseInfo radioResponseInfo, int i, ArrayList<Byte> arrayList) throws RemoteException {
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
        public void iccCloseLogicalChannelResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void iccTransmitApduLogicalChannelResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
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
        public void nvReadItemResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
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
        public void nvWriteItemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void nvWriteCdmaPrlResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void nvResetConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setUiccSubscriptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setDataAllowedResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getHardwareConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<HardwareConfig> arrayList) throws RemoteException {
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
        public void requestIccSimAuthenticationResponse(RadioResponseInfo radioResponseInfo, IccIoResult iccIoResult) throws RemoteException {
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
        public void setDataProfileResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void requestShutdownResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
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
        public void setRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, RadioCapability radioCapability) throws RemoteException {
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
        public void startLceServiceResponse(RadioResponseInfo radioResponseInfo, LceStatusInfo lceStatusInfo) throws RemoteException {
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
        public void stopLceServiceResponse(RadioResponseInfo radioResponseInfo, LceStatusInfo lceStatusInfo) throws RemoteException {
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
        public void pullLceDataResponse(RadioResponseInfo radioResponseInfo, LceDataInfo lceDataInfo) throws RemoteException {
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
        public void getModemActivityInfoResponse(RadioResponseInfo radioResponseInfo, ActivityStatsInfo activityStatsInfo) throws RemoteException {
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
        public void setAllowedCarriersResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
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
        public void getAllowedCarriersResponse(RadioResponseInfo radioResponseInfo, boolean z, CarrierRestrictions carrierRestrictions) throws RemoteException {
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
        public void sendDeviceStateResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setIndicationFilterResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setSimCardPowerResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setCarrierInfoForImsiEncryptionResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void setSimCardPowerResponse_1_1(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void startNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void stopNetworkScanResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void startKeepaliveResponse(RadioResponseInfo radioResponseInfo, KeepaliveStatus keepaliveStatus) throws RemoteException {
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
        public void stopKeepaliveResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
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
        public void getCellInfoListResponse_1_2(RadioResponseInfo radioResponseInfo, ArrayList<CellInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            CellInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(136, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getIccCardStatusResponse_1_2(RadioResponseInfo radioResponseInfo, CardStatus cardStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
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
        public void setSignalStrengthReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
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
        public void setLinkCapacityReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
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
        public void getCurrentCallsResponse_1_2(RadioResponseInfo radioResponseInfo, ArrayList<Call> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            Call.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(140, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getSignalStrengthResponse_1_2(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
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
        public void getVoiceRegistrationStateResponse_1_2(RadioResponseInfo radioResponseInfo, VoiceRegStateResult voiceRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
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
        public void getDataRegistrationStateResponse_1_2(RadioResponseInfo radioResponseInfo, DataRegStateResult dataRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IRadioResponse {
        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IRadioResponse.kInterfaceName, android.hardware.radio.V1_1.IRadioResponse.kInterfaceName, android.hardware.radio.V1_0.IRadioResponse.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IRadioResponse.kInterfaceName;
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-38, -116, 106, -23, -111, -58, -92, -78, -124, -52, 110, 68, 83, 50, MidiConstants.STATUS_PITCH_BEND, 100, -30, -114, -24, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -108, -126, -19, 90, -1, -7, -47, 89, -20, 102, -108, -73}, new byte[]{0, 54, 107, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, -120, -7, -20, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, 88, 1, 73, 114, -109, -126, SprAttributeBase.TYPE_SHADOW, -56, 65, 61, 74, -77, 3, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -114, 55, -65, 58, -35, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, -114, 107, -126, -102}, new byte[]{-68, 60, -116, 35, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -123, -4, -93, -121, -99, -57, 75, 73, 11, -98, 91, -63, 6, 50, 88, 71, 13, 59, 76, 18, -9, -89, 75, MidiConstants.STATUS_SONG_POSITION, 21, -53, -67}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
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
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.CardStatus cardStatus = new android.hardware.radio.V1_0.CardStatus();
                    cardStatus.readFromParcel(hwParcel);
                    getIccCardStatusResponse(radioResponseInfo, cardStatus);
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo2 = new RadioResponseInfo();
                    radioResponseInfo2.readFromParcel(hwParcel);
                    supplyIccPinForAppResponse(radioResponseInfo2, hwParcel.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo3 = new RadioResponseInfo();
                    radioResponseInfo3.readFromParcel(hwParcel);
                    supplyIccPukForAppResponse(radioResponseInfo3, hwParcel.readInt32());
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo4 = new RadioResponseInfo();
                    radioResponseInfo4.readFromParcel(hwParcel);
                    supplyIccPin2ForAppResponse(radioResponseInfo4, hwParcel.readInt32());
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo5 = new RadioResponseInfo();
                    radioResponseInfo5.readFromParcel(hwParcel);
                    supplyIccPuk2ForAppResponse(radioResponseInfo5, hwParcel.readInt32());
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo6 = new RadioResponseInfo();
                    radioResponseInfo6.readFromParcel(hwParcel);
                    changeIccPinForAppResponse(radioResponseInfo6, hwParcel.readInt32());
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo7 = new RadioResponseInfo();
                    radioResponseInfo7.readFromParcel(hwParcel);
                    changeIccPin2ForAppResponse(radioResponseInfo7, hwParcel.readInt32());
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo8 = new RadioResponseInfo();
                    radioResponseInfo8.readFromParcel(hwParcel);
                    supplyNetworkDepersonalizationResponse(radioResponseInfo8, hwParcel.readInt32());
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo9 = new RadioResponseInfo();
                    radioResponseInfo9.readFromParcel(hwParcel);
                    getCurrentCallsResponse(radioResponseInfo9, android.hardware.radio.V1_0.Call.readVectorFromParcel(hwParcel));
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo10 = new RadioResponseInfo();
                    radioResponseInfo10.readFromParcel(hwParcel);
                    dialResponse(radioResponseInfo10);
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo11 = new RadioResponseInfo();
                    radioResponseInfo11.readFromParcel(hwParcel);
                    getIMSIForAppResponse(radioResponseInfo11, hwParcel.readString());
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo12 = new RadioResponseInfo();
                    radioResponseInfo12.readFromParcel(hwParcel);
                    hangupConnectionResponse(radioResponseInfo12);
                    return;
                case 13:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo13 = new RadioResponseInfo();
                    radioResponseInfo13.readFromParcel(hwParcel);
                    hangupWaitingOrBackgroundResponse(radioResponseInfo13);
                    return;
                case 14:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo14 = new RadioResponseInfo();
                    radioResponseInfo14.readFromParcel(hwParcel);
                    hangupForegroundResumeBackgroundResponse(radioResponseInfo14);
                    return;
                case 15:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo15 = new RadioResponseInfo();
                    radioResponseInfo15.readFromParcel(hwParcel);
                    switchWaitingOrHoldingAndActiveResponse(radioResponseInfo15);
                    return;
                case 16:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo16 = new RadioResponseInfo();
                    radioResponseInfo16.readFromParcel(hwParcel);
                    conferenceResponse(radioResponseInfo16);
                    return;
                case 17:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo17 = new RadioResponseInfo();
                    radioResponseInfo17.readFromParcel(hwParcel);
                    rejectCallResponse(radioResponseInfo17);
                    return;
                case 18:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo18 = new RadioResponseInfo();
                    radioResponseInfo18.readFromParcel(hwParcel);
                    LastCallFailCauseInfo lastCallFailCauseInfo = new LastCallFailCauseInfo();
                    lastCallFailCauseInfo.readFromParcel(hwParcel);
                    getLastCallFailCauseResponse(radioResponseInfo18, lastCallFailCauseInfo);
                    return;
                case 19:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo19 = new RadioResponseInfo();
                    radioResponseInfo19.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.SignalStrength signalStrength = new android.hardware.radio.V1_0.SignalStrength();
                    signalStrength.readFromParcel(hwParcel);
                    getSignalStrengthResponse(radioResponseInfo19, signalStrength);
                    return;
                case 20:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo20 = new RadioResponseInfo();
                    radioResponseInfo20.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.VoiceRegStateResult voiceRegStateResult = new android.hardware.radio.V1_0.VoiceRegStateResult();
                    voiceRegStateResult.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse(radioResponseInfo20, voiceRegStateResult);
                    return;
                case 21:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo21 = new RadioResponseInfo();
                    radioResponseInfo21.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.DataRegStateResult dataRegStateResult = new android.hardware.radio.V1_0.DataRegStateResult();
                    dataRegStateResult.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse(radioResponseInfo21, dataRegStateResult);
                    return;
                case 22:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo22 = new RadioResponseInfo();
                    radioResponseInfo22.readFromParcel(hwParcel);
                    getOperatorResponse(radioResponseInfo22, hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 23:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo23 = new RadioResponseInfo();
                    radioResponseInfo23.readFromParcel(hwParcel);
                    setRadioPowerResponse(radioResponseInfo23);
                    return;
                case 24:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo24 = new RadioResponseInfo();
                    radioResponseInfo24.readFromParcel(hwParcel);
                    sendDtmfResponse(radioResponseInfo24);
                    return;
                case 25:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo25 = new RadioResponseInfo();
                    radioResponseInfo25.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult = new SendSmsResult();
                    sendSmsResult.readFromParcel(hwParcel);
                    sendSmsResponse(radioResponseInfo25, sendSmsResult);
                    return;
                case 26:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo26 = new RadioResponseInfo();
                    radioResponseInfo26.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult2 = new SendSmsResult();
                    sendSmsResult2.readFromParcel(hwParcel);
                    sendSMSExpectMoreResponse(radioResponseInfo26, sendSmsResult2);
                    return;
                case 27:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo27 = new RadioResponseInfo();
                    radioResponseInfo27.readFromParcel(hwParcel);
                    SetupDataCallResult setupDataCallResult = new SetupDataCallResult();
                    setupDataCallResult.readFromParcel(hwParcel);
                    setupDataCallResponse(radioResponseInfo27, setupDataCallResult);
                    return;
                case 28:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo28 = new RadioResponseInfo();
                    radioResponseInfo28.readFromParcel(hwParcel);
                    IccIoResult iccIoResult = new IccIoResult();
                    iccIoResult.readFromParcel(hwParcel);
                    iccIOForAppResponse(radioResponseInfo28, iccIoResult);
                    return;
                case 29:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo29 = new RadioResponseInfo();
                    radioResponseInfo29.readFromParcel(hwParcel);
                    sendUssdResponse(radioResponseInfo29);
                    return;
                case 30:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo30 = new RadioResponseInfo();
                    radioResponseInfo30.readFromParcel(hwParcel);
                    cancelPendingUssdResponse(radioResponseInfo30);
                    return;
                case 31:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo31 = new RadioResponseInfo();
                    radioResponseInfo31.readFromParcel(hwParcel);
                    getClirResponse(radioResponseInfo31, hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 32:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo32 = new RadioResponseInfo();
                    radioResponseInfo32.readFromParcel(hwParcel);
                    setClirResponse(radioResponseInfo32);
                    return;
                case 33:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo33 = new RadioResponseInfo();
                    radioResponseInfo33.readFromParcel(hwParcel);
                    getCallForwardStatusResponse(radioResponseInfo33, CallForwardInfo.readVectorFromParcel(hwParcel));
                    return;
                case 34:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo34 = new RadioResponseInfo();
                    radioResponseInfo34.readFromParcel(hwParcel);
                    setCallForwardResponse(radioResponseInfo34);
                    return;
                case 35:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo35 = new RadioResponseInfo();
                    radioResponseInfo35.readFromParcel(hwParcel);
                    getCallWaitingResponse(radioResponseInfo35, hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 36:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo36 = new RadioResponseInfo();
                    radioResponseInfo36.readFromParcel(hwParcel);
                    setCallWaitingResponse(radioResponseInfo36);
                    return;
                case 37:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo37 = new RadioResponseInfo();
                    radioResponseInfo37.readFromParcel(hwParcel);
                    acknowledgeLastIncomingGsmSmsResponse(radioResponseInfo37);
                    return;
                case 38:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo38 = new RadioResponseInfo();
                    radioResponseInfo38.readFromParcel(hwParcel);
                    acceptCallResponse(radioResponseInfo38);
                    return;
                case 39:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo39 = new RadioResponseInfo();
                    radioResponseInfo39.readFromParcel(hwParcel);
                    deactivateDataCallResponse(radioResponseInfo39);
                    return;
                case 40:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo40 = new RadioResponseInfo();
                    radioResponseInfo40.readFromParcel(hwParcel);
                    getFacilityLockForAppResponse(radioResponseInfo40, hwParcel.readInt32());
                    return;
                case 41:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo41 = new RadioResponseInfo();
                    radioResponseInfo41.readFromParcel(hwParcel);
                    setFacilityLockForAppResponse(radioResponseInfo41, hwParcel.readInt32());
                    return;
                case 42:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo42 = new RadioResponseInfo();
                    radioResponseInfo42.readFromParcel(hwParcel);
                    setBarringPasswordResponse(radioResponseInfo42);
                    return;
                case 43:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo43 = new RadioResponseInfo();
                    radioResponseInfo43.readFromParcel(hwParcel);
                    getNetworkSelectionModeResponse(radioResponseInfo43, hwParcel.readBool());
                    return;
                case 44:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo44 = new RadioResponseInfo();
                    radioResponseInfo44.readFromParcel(hwParcel);
                    setNetworkSelectionModeAutomaticResponse(radioResponseInfo44);
                    return;
                case 45:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo45 = new RadioResponseInfo();
                    radioResponseInfo45.readFromParcel(hwParcel);
                    setNetworkSelectionModeManualResponse(radioResponseInfo45);
                    return;
                case 46:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo46 = new RadioResponseInfo();
                    radioResponseInfo46.readFromParcel(hwParcel);
                    getAvailableNetworksResponse(radioResponseInfo46, OperatorInfo.readVectorFromParcel(hwParcel));
                    return;
                case 47:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo47 = new RadioResponseInfo();
                    radioResponseInfo47.readFromParcel(hwParcel);
                    startDtmfResponse(radioResponseInfo47);
                    return;
                case 48:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo48 = new RadioResponseInfo();
                    radioResponseInfo48.readFromParcel(hwParcel);
                    stopDtmfResponse(radioResponseInfo48);
                    return;
                case 49:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo49 = new RadioResponseInfo();
                    radioResponseInfo49.readFromParcel(hwParcel);
                    getBasebandVersionResponse(radioResponseInfo49, hwParcel.readString());
                    return;
                case 50:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo50 = new RadioResponseInfo();
                    radioResponseInfo50.readFromParcel(hwParcel);
                    separateConnectionResponse(radioResponseInfo50);
                    return;
                case 51:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo51 = new RadioResponseInfo();
                    radioResponseInfo51.readFromParcel(hwParcel);
                    setMuteResponse(radioResponseInfo51);
                    return;
                case 52:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo52 = new RadioResponseInfo();
                    radioResponseInfo52.readFromParcel(hwParcel);
                    getMuteResponse(radioResponseInfo52, hwParcel.readBool());
                    return;
                case 53:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo53 = new RadioResponseInfo();
                    radioResponseInfo53.readFromParcel(hwParcel);
                    getClipResponse(radioResponseInfo53, hwParcel.readInt32());
                    return;
                case 54:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo54 = new RadioResponseInfo();
                    radioResponseInfo54.readFromParcel(hwParcel);
                    getDataCallListResponse(radioResponseInfo54, SetupDataCallResult.readVectorFromParcel(hwParcel));
                    return;
                case 55:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo55 = new RadioResponseInfo();
                    radioResponseInfo55.readFromParcel(hwParcel);
                    setSuppServiceNotificationsResponse(radioResponseInfo55);
                    return;
                case 56:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo56 = new RadioResponseInfo();
                    radioResponseInfo56.readFromParcel(hwParcel);
                    writeSmsToSimResponse(radioResponseInfo56, hwParcel.readInt32());
                    return;
                case 57:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo57 = new RadioResponseInfo();
                    radioResponseInfo57.readFromParcel(hwParcel);
                    deleteSmsOnSimResponse(radioResponseInfo57);
                    return;
                case 58:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo58 = new RadioResponseInfo();
                    radioResponseInfo58.readFromParcel(hwParcel);
                    setBandModeResponse(radioResponseInfo58);
                    return;
                case 59:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo59 = new RadioResponseInfo();
                    radioResponseInfo59.readFromParcel(hwParcel);
                    getAvailableBandModesResponse(radioResponseInfo59, hwParcel.readInt32Vector());
                    return;
                case 60:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo60 = new RadioResponseInfo();
                    radioResponseInfo60.readFromParcel(hwParcel);
                    sendEnvelopeResponse(radioResponseInfo60, hwParcel.readString());
                    return;
                case 61:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo61 = new RadioResponseInfo();
                    radioResponseInfo61.readFromParcel(hwParcel);
                    sendTerminalResponseToSimResponse(radioResponseInfo61);
                    return;
                case 62:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo62 = new RadioResponseInfo();
                    radioResponseInfo62.readFromParcel(hwParcel);
                    handleStkCallSetupRequestFromSimResponse(radioResponseInfo62);
                    return;
                case 63:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo63 = new RadioResponseInfo();
                    radioResponseInfo63.readFromParcel(hwParcel);
                    explicitCallTransferResponse(radioResponseInfo63);
                    return;
                case 64:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo64 = new RadioResponseInfo();
                    radioResponseInfo64.readFromParcel(hwParcel);
                    setPreferredNetworkTypeResponse(radioResponseInfo64);
                    return;
                case 65:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo65 = new RadioResponseInfo();
                    radioResponseInfo65.readFromParcel(hwParcel);
                    getPreferredNetworkTypeResponse(radioResponseInfo65, hwParcel.readInt32());
                    return;
                case 66:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo66 = new RadioResponseInfo();
                    radioResponseInfo66.readFromParcel(hwParcel);
                    getNeighboringCidsResponse(radioResponseInfo66, NeighboringCell.readVectorFromParcel(hwParcel));
                    return;
                case 67:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo67 = new RadioResponseInfo();
                    radioResponseInfo67.readFromParcel(hwParcel);
                    setLocationUpdatesResponse(radioResponseInfo67);
                    return;
                case 68:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo68 = new RadioResponseInfo();
                    radioResponseInfo68.readFromParcel(hwParcel);
                    setCdmaSubscriptionSourceResponse(radioResponseInfo68);
                    return;
                case 69:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo69 = new RadioResponseInfo();
                    radioResponseInfo69.readFromParcel(hwParcel);
                    setCdmaRoamingPreferenceResponse(radioResponseInfo69);
                    return;
                case 70:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo70 = new RadioResponseInfo();
                    radioResponseInfo70.readFromParcel(hwParcel);
                    getCdmaRoamingPreferenceResponse(radioResponseInfo70, hwParcel.readInt32());
                    return;
                case 71:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo71 = new RadioResponseInfo();
                    radioResponseInfo71.readFromParcel(hwParcel);
                    setTTYModeResponse(radioResponseInfo71);
                    return;
                case 72:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo72 = new RadioResponseInfo();
                    radioResponseInfo72.readFromParcel(hwParcel);
                    getTTYModeResponse(radioResponseInfo72, hwParcel.readInt32());
                    return;
                case 73:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo73 = new RadioResponseInfo();
                    radioResponseInfo73.readFromParcel(hwParcel);
                    setPreferredVoicePrivacyResponse(radioResponseInfo73);
                    return;
                case 74:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo74 = new RadioResponseInfo();
                    radioResponseInfo74.readFromParcel(hwParcel);
                    getPreferredVoicePrivacyResponse(radioResponseInfo74, hwParcel.readBool());
                    return;
                case 75:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo75 = new RadioResponseInfo();
                    radioResponseInfo75.readFromParcel(hwParcel);
                    sendCDMAFeatureCodeResponse(radioResponseInfo75);
                    return;
                case 76:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo76 = new RadioResponseInfo();
                    radioResponseInfo76.readFromParcel(hwParcel);
                    sendBurstDtmfResponse(radioResponseInfo76);
                    return;
                case 77:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo77 = new RadioResponseInfo();
                    radioResponseInfo77.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult3 = new SendSmsResult();
                    sendSmsResult3.readFromParcel(hwParcel);
                    sendCdmaSmsResponse(radioResponseInfo77, sendSmsResult3);
                    return;
                case 78:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo78 = new RadioResponseInfo();
                    radioResponseInfo78.readFromParcel(hwParcel);
                    acknowledgeLastIncomingCdmaSmsResponse(radioResponseInfo78);
                    return;
                case 79:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo79 = new RadioResponseInfo();
                    radioResponseInfo79.readFromParcel(hwParcel);
                    getGsmBroadcastConfigResponse(radioResponseInfo79, GsmBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 80:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo80 = new RadioResponseInfo();
                    radioResponseInfo80.readFromParcel(hwParcel);
                    setGsmBroadcastConfigResponse(radioResponseInfo80);
                    return;
                case 81:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo81 = new RadioResponseInfo();
                    radioResponseInfo81.readFromParcel(hwParcel);
                    setGsmBroadcastActivationResponse(radioResponseInfo81);
                    return;
                case 82:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo82 = new RadioResponseInfo();
                    radioResponseInfo82.readFromParcel(hwParcel);
                    getCdmaBroadcastConfigResponse(radioResponseInfo82, CdmaBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 83:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo83 = new RadioResponseInfo();
                    radioResponseInfo83.readFromParcel(hwParcel);
                    setCdmaBroadcastConfigResponse(radioResponseInfo83);
                    return;
                case 84:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo84 = new RadioResponseInfo();
                    radioResponseInfo84.readFromParcel(hwParcel);
                    setCdmaBroadcastActivationResponse(radioResponseInfo84);
                    return;
                case 85:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo85 = new RadioResponseInfo();
                    radioResponseInfo85.readFromParcel(hwParcel);
                    getCDMASubscriptionResponse(radioResponseInfo85, hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 86:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo86 = new RadioResponseInfo();
                    radioResponseInfo86.readFromParcel(hwParcel);
                    writeSmsToRuimResponse(radioResponseInfo86, hwParcel.readInt32());
                    return;
                case 87:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo87 = new RadioResponseInfo();
                    radioResponseInfo87.readFromParcel(hwParcel);
                    deleteSmsOnRuimResponse(radioResponseInfo87);
                    return;
                case 88:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo88 = new RadioResponseInfo();
                    radioResponseInfo88.readFromParcel(hwParcel);
                    getDeviceIdentityResponse(radioResponseInfo88, hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 89:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo89 = new RadioResponseInfo();
                    radioResponseInfo89.readFromParcel(hwParcel);
                    exitEmergencyCallbackModeResponse(radioResponseInfo89);
                    return;
                case 90:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo90 = new RadioResponseInfo();
                    radioResponseInfo90.readFromParcel(hwParcel);
                    getSmscAddressResponse(radioResponseInfo90, hwParcel.readString());
                    return;
                case 91:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo91 = new RadioResponseInfo();
                    radioResponseInfo91.readFromParcel(hwParcel);
                    setSmscAddressResponse(radioResponseInfo91);
                    return;
                case 92:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo92 = new RadioResponseInfo();
                    radioResponseInfo92.readFromParcel(hwParcel);
                    reportSmsMemoryStatusResponse(radioResponseInfo92);
                    return;
                case 93:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo93 = new RadioResponseInfo();
                    radioResponseInfo93.readFromParcel(hwParcel);
                    reportStkServiceIsRunningResponse(radioResponseInfo93);
                    return;
                case 94:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo94 = new RadioResponseInfo();
                    radioResponseInfo94.readFromParcel(hwParcel);
                    getCdmaSubscriptionSourceResponse(radioResponseInfo94, hwParcel.readInt32());
                    return;
                case 95:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo95 = new RadioResponseInfo();
                    radioResponseInfo95.readFromParcel(hwParcel);
                    requestIsimAuthenticationResponse(radioResponseInfo95, hwParcel.readString());
                    return;
                case 96:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo96 = new RadioResponseInfo();
                    radioResponseInfo96.readFromParcel(hwParcel);
                    acknowledgeIncomingGsmSmsWithPduResponse(radioResponseInfo96);
                    return;
                case 97:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo97 = new RadioResponseInfo();
                    radioResponseInfo97.readFromParcel(hwParcel);
                    IccIoResult iccIoResult2 = new IccIoResult();
                    iccIoResult2.readFromParcel(hwParcel);
                    sendEnvelopeWithStatusResponse(radioResponseInfo97, iccIoResult2);
                    return;
                case 98:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo98 = new RadioResponseInfo();
                    radioResponseInfo98.readFromParcel(hwParcel);
                    getVoiceRadioTechnologyResponse(radioResponseInfo98, hwParcel.readInt32());
                    return;
                case 99:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo99 = new RadioResponseInfo();
                    radioResponseInfo99.readFromParcel(hwParcel);
                    getCellInfoListResponse(radioResponseInfo99, android.hardware.radio.V1_0.CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 100:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo100 = new RadioResponseInfo();
                    radioResponseInfo100.readFromParcel(hwParcel);
                    setCellInfoListRateResponse(radioResponseInfo100);
                    return;
                case 101:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo101 = new RadioResponseInfo();
                    radioResponseInfo101.readFromParcel(hwParcel);
                    setInitialAttachApnResponse(radioResponseInfo101);
                    return;
                case 102:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo102 = new RadioResponseInfo();
                    radioResponseInfo102.readFromParcel(hwParcel);
                    getImsRegistrationStateResponse(radioResponseInfo102, hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 103:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo103 = new RadioResponseInfo();
                    radioResponseInfo103.readFromParcel(hwParcel);
                    SendSmsResult sendSmsResult4 = new SendSmsResult();
                    sendSmsResult4.readFromParcel(hwParcel);
                    sendImsSmsResponse(radioResponseInfo103, sendSmsResult4);
                    return;
                case 104:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo104 = new RadioResponseInfo();
                    radioResponseInfo104.readFromParcel(hwParcel);
                    IccIoResult iccIoResult3 = new IccIoResult();
                    iccIoResult3.readFromParcel(hwParcel);
                    iccTransmitApduBasicChannelResponse(radioResponseInfo104, iccIoResult3);
                    return;
                case 105:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo105 = new RadioResponseInfo();
                    radioResponseInfo105.readFromParcel(hwParcel);
                    iccOpenLogicalChannelResponse(radioResponseInfo105, hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 106:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo106 = new RadioResponseInfo();
                    radioResponseInfo106.readFromParcel(hwParcel);
                    iccCloseLogicalChannelResponse(radioResponseInfo106);
                    return;
                case 107:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo107 = new RadioResponseInfo();
                    radioResponseInfo107.readFromParcel(hwParcel);
                    IccIoResult iccIoResult4 = new IccIoResult();
                    iccIoResult4.readFromParcel(hwParcel);
                    iccTransmitApduLogicalChannelResponse(radioResponseInfo107, iccIoResult4);
                    return;
                case 108:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo108 = new RadioResponseInfo();
                    radioResponseInfo108.readFromParcel(hwParcel);
                    nvReadItemResponse(radioResponseInfo108, hwParcel.readString());
                    return;
                case 109:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo109 = new RadioResponseInfo();
                    radioResponseInfo109.readFromParcel(hwParcel);
                    nvWriteItemResponse(radioResponseInfo109);
                    return;
                case 110:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo110 = new RadioResponseInfo();
                    radioResponseInfo110.readFromParcel(hwParcel);
                    nvWriteCdmaPrlResponse(radioResponseInfo110);
                    return;
                case 111:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo111 = new RadioResponseInfo();
                    radioResponseInfo111.readFromParcel(hwParcel);
                    nvResetConfigResponse(radioResponseInfo111);
                    return;
                case 112:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo112 = new RadioResponseInfo();
                    radioResponseInfo112.readFromParcel(hwParcel);
                    setUiccSubscriptionResponse(radioResponseInfo112);
                    return;
                case 113:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo113 = new RadioResponseInfo();
                    radioResponseInfo113.readFromParcel(hwParcel);
                    setDataAllowedResponse(radioResponseInfo113);
                    return;
                case 114:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo114 = new RadioResponseInfo();
                    radioResponseInfo114.readFromParcel(hwParcel);
                    getHardwareConfigResponse(radioResponseInfo114, HardwareConfig.readVectorFromParcel(hwParcel));
                    return;
                case 115:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo115 = new RadioResponseInfo();
                    radioResponseInfo115.readFromParcel(hwParcel);
                    IccIoResult iccIoResult5 = new IccIoResult();
                    iccIoResult5.readFromParcel(hwParcel);
                    requestIccSimAuthenticationResponse(radioResponseInfo115, iccIoResult5);
                    return;
                case 116:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo116 = new RadioResponseInfo();
                    radioResponseInfo116.readFromParcel(hwParcel);
                    setDataProfileResponse(radioResponseInfo116);
                    return;
                case 117:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo117 = new RadioResponseInfo();
                    radioResponseInfo117.readFromParcel(hwParcel);
                    requestShutdownResponse(radioResponseInfo117);
                    return;
                case 118:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo118 = new RadioResponseInfo();
                    radioResponseInfo118.readFromParcel(hwParcel);
                    RadioCapability radioCapability = new RadioCapability();
                    radioCapability.readFromParcel(hwParcel);
                    getRadioCapabilityResponse(radioResponseInfo118, radioCapability);
                    return;
                case 119:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo119 = new RadioResponseInfo();
                    radioResponseInfo119.readFromParcel(hwParcel);
                    RadioCapability radioCapability2 = new RadioCapability();
                    radioCapability2.readFromParcel(hwParcel);
                    setRadioCapabilityResponse(radioResponseInfo119, radioCapability2);
                    return;
                case 120:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo120 = new RadioResponseInfo();
                    radioResponseInfo120.readFromParcel(hwParcel);
                    LceStatusInfo lceStatusInfo = new LceStatusInfo();
                    lceStatusInfo.readFromParcel(hwParcel);
                    startLceServiceResponse(radioResponseInfo120, lceStatusInfo);
                    return;
                case 121:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo121 = new RadioResponseInfo();
                    radioResponseInfo121.readFromParcel(hwParcel);
                    LceStatusInfo lceStatusInfo2 = new LceStatusInfo();
                    lceStatusInfo2.readFromParcel(hwParcel);
                    stopLceServiceResponse(radioResponseInfo121, lceStatusInfo2);
                    return;
                case 122:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo122 = new RadioResponseInfo();
                    radioResponseInfo122.readFromParcel(hwParcel);
                    LceDataInfo lceDataInfo = new LceDataInfo();
                    lceDataInfo.readFromParcel(hwParcel);
                    pullLceDataResponse(radioResponseInfo122, lceDataInfo);
                    return;
                case 123:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo123 = new RadioResponseInfo();
                    radioResponseInfo123.readFromParcel(hwParcel);
                    ActivityStatsInfo activityStatsInfo = new ActivityStatsInfo();
                    activityStatsInfo.readFromParcel(hwParcel);
                    getModemActivityInfoResponse(radioResponseInfo123, activityStatsInfo);
                    return;
                case 124:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo124 = new RadioResponseInfo();
                    radioResponseInfo124.readFromParcel(hwParcel);
                    setAllowedCarriersResponse(radioResponseInfo124, hwParcel.readInt32());
                    return;
                case 125:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo125 = new RadioResponseInfo();
                    radioResponseInfo125.readFromParcel(hwParcel);
                    boolean bool = hwParcel.readBool();
                    CarrierRestrictions carrierRestrictions = new CarrierRestrictions();
                    carrierRestrictions.readFromParcel(hwParcel);
                    getAllowedCarriersResponse(radioResponseInfo125, bool, carrierRestrictions);
                    return;
                case 126:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo126 = new RadioResponseInfo();
                    radioResponseInfo126.readFromParcel(hwParcel);
                    sendDeviceStateResponse(radioResponseInfo126);
                    return;
                case 127:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo127 = new RadioResponseInfo();
                    radioResponseInfo127.readFromParcel(hwParcel);
                    setIndicationFilterResponse(radioResponseInfo127);
                    return;
                case 128:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo128 = new RadioResponseInfo();
                    radioResponseInfo128.readFromParcel(hwParcel);
                    setSimCardPowerResponse(radioResponseInfo128);
                    return;
                case 129:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    acknowledgeRequest(hwParcel.readInt32());
                    return;
                case 130:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo129 = new RadioResponseInfo();
                    radioResponseInfo129.readFromParcel(hwParcel);
                    setCarrierInfoForImsiEncryptionResponse(radioResponseInfo129);
                    return;
                case 131:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo130 = new RadioResponseInfo();
                    radioResponseInfo130.readFromParcel(hwParcel);
                    setSimCardPowerResponse_1_1(radioResponseInfo130);
                    return;
                case 132:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo131 = new RadioResponseInfo();
                    radioResponseInfo131.readFromParcel(hwParcel);
                    startNetworkScanResponse(radioResponseInfo131);
                    return;
                case 133:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo132 = new RadioResponseInfo();
                    radioResponseInfo132.readFromParcel(hwParcel);
                    stopNetworkScanResponse(radioResponseInfo132);
                    return;
                case 134:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo133 = new RadioResponseInfo();
                    radioResponseInfo133.readFromParcel(hwParcel);
                    KeepaliveStatus keepaliveStatus = new KeepaliveStatus();
                    keepaliveStatus.readFromParcel(hwParcel);
                    startKeepaliveResponse(radioResponseInfo133, keepaliveStatus);
                    return;
                case 135:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo134 = new RadioResponseInfo();
                    radioResponseInfo134.readFromParcel(hwParcel);
                    stopKeepaliveResponse(radioResponseInfo134);
                    return;
                case 136:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo135 = new RadioResponseInfo();
                    radioResponseInfo135.readFromParcel(hwParcel);
                    getCellInfoListResponse_1_2(radioResponseInfo135, CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 137:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo136 = new RadioResponseInfo();
                    radioResponseInfo136.readFromParcel(hwParcel);
                    CardStatus cardStatus2 = new CardStatus();
                    cardStatus2.readFromParcel(hwParcel);
                    getIccCardStatusResponse_1_2(radioResponseInfo136, cardStatus2);
                    return;
                case 138:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo137 = new RadioResponseInfo();
                    radioResponseInfo137.readFromParcel(hwParcel);
                    setSignalStrengthReportingCriteriaResponse(radioResponseInfo137);
                    return;
                case 139:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo138 = new RadioResponseInfo();
                    radioResponseInfo138.readFromParcel(hwParcel);
                    setLinkCapacityReportingCriteriaResponse(radioResponseInfo138);
                    return;
                case 140:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo139 = new RadioResponseInfo();
                    radioResponseInfo139.readFromParcel(hwParcel);
                    getCurrentCallsResponse_1_2(radioResponseInfo139, Call.readVectorFromParcel(hwParcel));
                    return;
                case 141:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo140 = new RadioResponseInfo();
                    radioResponseInfo140.readFromParcel(hwParcel);
                    SignalStrength signalStrength2 = new SignalStrength();
                    signalStrength2.readFromParcel(hwParcel);
                    getSignalStrengthResponse_1_2(radioResponseInfo140, signalStrength2);
                    return;
                case 142:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo141 = new RadioResponseInfo();
                    radioResponseInfo141.readFromParcel(hwParcel);
                    VoiceRegStateResult voiceRegStateResult2 = new VoiceRegStateResult();
                    voiceRegStateResult2.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse_1_2(radioResponseInfo141, voiceRegStateResult2);
                    return;
                case 143:
                    hwParcel.enforceInterface(IRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo142 = new RadioResponseInfo();
                    radioResponseInfo142.readFromParcel(hwParcel);
                    DataRegStateResult dataRegStateResult2 = new DataRegStateResult();
                    dataRegStateResult2.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse_1_2(radioResponseInfo142, dataRegStateResult2);
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
