package android.hardware.radio.V1_0;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface IRadio extends IBase {
    public static final String kInterfaceName = "android.hardware.radio@1.0::IRadio";

    void acceptCall(int i) throws RemoteException;

    void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, String str) throws RemoteException;

    void acknowledgeLastIncomingCdmaSms(int i, CdmaSmsAck cdmaSmsAck) throws RemoteException;

    void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    void cancelPendingUssd(int i) throws RemoteException;

    void changeIccPin2ForApp(int i, String str, String str2, String str3) throws RemoteException;

    void changeIccPinForApp(int i, String str, String str2, String str3) throws RemoteException;

    void conference(int i) throws RemoteException;

    void deactivateDataCall(int i, int i2, boolean z) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void deleteSmsOnRuim(int i, int i2) throws RemoteException;

    void deleteSmsOnSim(int i, int i2) throws RemoteException;

    void dial(int i, Dial dial) throws RemoteException;

    void exitEmergencyCallbackMode(int i) throws RemoteException;

    void explicitCallTransfer(int i) throws RemoteException;

    void getAllowedCarriers(int i) throws RemoteException;

    void getAvailableBandModes(int i) throws RemoteException;

    void getAvailableNetworks(int i) throws RemoteException;

    void getBasebandVersion(int i) throws RemoteException;

    void getCDMASubscription(int i) throws RemoteException;

    void getCallForwardStatus(int i, CallForwardInfo callForwardInfo) throws RemoteException;

    void getCallWaiting(int i, int i2) throws RemoteException;

    void getCdmaBroadcastConfig(int i) throws RemoteException;

    void getCdmaRoamingPreference(int i) throws RemoteException;

    void getCdmaSubscriptionSource(int i) throws RemoteException;

    void getCellInfoList(int i) throws RemoteException;

    void getClip(int i) throws RemoteException;

    void getClir(int i) throws RemoteException;

    void getCurrentCalls(int i) throws RemoteException;

    void getDataCallList(int i) throws RemoteException;

    void getDataRegistrationState(int i) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    void getDeviceIdentity(int i) throws RemoteException;

    void getFacilityLockForApp(int i, String str, String str2, int i2, String str3) throws RemoteException;

    void getGsmBroadcastConfig(int i) throws RemoteException;

    void getHardwareConfig(int i) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getIccCardStatus(int i) throws RemoteException;

    void getImsRegistrationState(int i) throws RemoteException;

    void getImsiForApp(int i, String str) throws RemoteException;

    void getLastCallFailCause(int i) throws RemoteException;

    void getModemActivityInfo(int i) throws RemoteException;

    void getMute(int i) throws RemoteException;

    void getNeighboringCids(int i) throws RemoteException;

    void getNetworkSelectionMode(int i) throws RemoteException;

    void getOperator(int i) throws RemoteException;

    void getPreferredNetworkType(int i) throws RemoteException;

    void getPreferredVoicePrivacy(int i) throws RemoteException;

    void getRadioCapability(int i) throws RemoteException;

    void getSignalStrength(int i) throws RemoteException;

    void getSmscAddress(int i) throws RemoteException;

    void getTTYMode(int i) throws RemoteException;

    void getVoiceRadioTechnology(int i) throws RemoteException;

    void getVoiceRegistrationState(int i) throws RemoteException;

    void handleStkCallSetupRequestFromSim(int i, boolean z) throws RemoteException;

    void hangup(int i, int i2) throws RemoteException;

    void hangupForegroundResumeBackground(int i) throws RemoteException;

    void hangupWaitingOrBackground(int i) throws RemoteException;

    void iccCloseLogicalChannel(int i, int i2) throws RemoteException;

    void iccIOForApp(int i, IccIo iccIo) throws RemoteException;

    void iccOpenLogicalChannel(int i, String str, int i2) throws RemoteException;

    void iccTransmitApduBasicChannel(int i, SimApdu simApdu) throws RemoteException;

    void iccTransmitApduLogicalChannel(int i, SimApdu simApdu) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    void nvReadItem(int i, int i2) throws RemoteException;

    void nvResetConfig(int i, int i2) throws RemoteException;

    void nvWriteCdmaPrl(int i, ArrayList<Byte> arrayList) throws RemoteException;

    void nvWriteItem(int i, NvWriteItem nvWriteItem) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    void pullLceData(int i) throws RemoteException;

    void rejectCall(int i) throws RemoteException;

    void reportSmsMemoryStatus(int i, boolean z) throws RemoteException;

    void reportStkServiceIsRunning(int i) throws RemoteException;

    void requestIccSimAuthentication(int i, int i2, String str, String str2) throws RemoteException;

    void requestIsimAuthentication(int i, String str) throws RemoteException;

    void requestShutdown(int i) throws RemoteException;

    void responseAcknowledgement() throws RemoteException;

    void sendBurstDtmf(int i, String str, int i2, int i3) throws RemoteException;

    void sendCDMAFeatureCode(int i, String str) throws RemoteException;

    void sendCdmaSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    void sendDeviceState(int i, int i2, boolean z) throws RemoteException;

    void sendDtmf(int i, String str) throws RemoteException;

    void sendEnvelope(int i, String str) throws RemoteException;

    void sendEnvelopeWithStatus(int i, String str) throws RemoteException;

    void sendImsSms(int i, ImsSmsMessage imsSmsMessage) throws RemoteException;

    void sendSMSExpectMore(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    void sendSms(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    void sendTerminalResponseToSim(int i, String str) throws RemoteException;

    void sendUssd(int i, String str) throws RemoteException;

    void separateConnection(int i, int i2) throws RemoteException;

    void setAllowedCarriers(int i, boolean z, CarrierRestrictions carrierRestrictions) throws RemoteException;

    void setBandMode(int i, int i2) throws RemoteException;

    void setBarringPassword(int i, String str, String str2, String str3) throws RemoteException;

    void setCallForward(int i, CallForwardInfo callForwardInfo) throws RemoteException;

    void setCallWaiting(int i, boolean z, int i2) throws RemoteException;

    void setCdmaBroadcastActivation(int i, boolean z) throws RemoteException;

    void setCdmaBroadcastConfig(int i, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) throws RemoteException;

    void setCdmaRoamingPreference(int i, int i2) throws RemoteException;

    void setCdmaSubscriptionSource(int i, int i2) throws RemoteException;

    void setCellInfoListRate(int i, int i2) throws RemoteException;

    void setClir(int i, int i2) throws RemoteException;

    void setDataAllowed(int i, boolean z) throws RemoteException;

    void setDataProfile(int i, ArrayList<DataProfileInfo> arrayList, boolean z) throws RemoteException;

    void setFacilityLockForApp(int i, String str, boolean z, String str2, int i2, String str3) throws RemoteException;

    void setGsmBroadcastActivation(int i, boolean z) throws RemoteException;

    void setGsmBroadcastConfig(int i, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    void setIndicationFilter(int i, int i2) throws RemoteException;

    void setInitialAttachApn(int i, DataProfileInfo dataProfileInfo, boolean z, boolean z2) throws RemoteException;

    void setLocationUpdates(int i, boolean z) throws RemoteException;

    void setMute(int i, boolean z) throws RemoteException;

    void setNetworkSelectionModeAutomatic(int i) throws RemoteException;

    void setNetworkSelectionModeManual(int i, String str) throws RemoteException;

    void setPreferredNetworkType(int i, int i2) throws RemoteException;

    void setPreferredVoicePrivacy(int i, boolean z) throws RemoteException;

    void setRadioCapability(int i, RadioCapability radioCapability) throws RemoteException;

    void setRadioPower(int i, boolean z) throws RemoteException;

    void setResponseFunctions(IRadioResponse iRadioResponse, IRadioIndication iRadioIndication) throws RemoteException;

    void setSimCardPower(int i, boolean z) throws RemoteException;

    void setSmscAddress(int i, String str) throws RemoteException;

    void setSuppServiceNotifications(int i, boolean z) throws RemoteException;

    void setTTYMode(int i, int i2) throws RemoteException;

    void setUiccSubscription(int i, SelectUiccSub selectUiccSub) throws RemoteException;

    void setupDataCall(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, boolean z2, boolean z3) throws RemoteException;

    void startDtmf(int i, String str) throws RemoteException;

    void startLceService(int i, int i2, boolean z) throws RemoteException;

    void stopDtmf(int i) throws RemoteException;

    void stopLceService(int i) throws RemoteException;

    void supplyIccPin2ForApp(int i, String str, String str2) throws RemoteException;

    void supplyIccPinForApp(int i, String str, String str2) throws RemoteException;

    void supplyIccPuk2ForApp(int i, String str, String str2, String str3) throws RemoteException;

    void supplyIccPukForApp(int i, String str, String str2, String str3) throws RemoteException;

    void supplyNetworkDepersonalization(int i, String str) throws RemoteException;

    void switchWaitingOrHoldingAndActive(int i) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void writeSmsToRuim(int i, CdmaSmsWriteArgs cdmaSmsWriteArgs) throws RemoteException;

    void writeSmsToSim(int i, SmsWriteArgs smsWriteArgs) throws RemoteException;

    static IRadio asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IRadio)) {
            return (IRadio) queryLocalInterface;
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

    static IRadio castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IRadio getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IRadio getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IRadio getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IRadio getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IRadio {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.radio@1.0::IRadio]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setResponseFunctions(IRadioResponse iRadioResponse, IRadioIndication iRadioIndication) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeStrongBinder(iRadioResponse == null ? null : iRadioResponse.asBinder());
            hwParcel.writeStrongBinder(iRadioIndication != null ? iRadioIndication.asBinder() : null);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getIccCardStatus(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void supplyIccPinForApp(int i, String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void supplyIccPukForApp(int i, String str, String str2, String str3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void supplyIccPin2ForApp(int i, String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void supplyIccPuk2ForApp(int i, String str, String str2, String str3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void changeIccPinForApp(int i, String str, String str2, String str3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void changeIccPin2ForApp(int i, String str, String str2, String str3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void supplyNetworkDepersonalization(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getCurrentCalls(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void dial(int i, Dial dial) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            dial.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getImsiForApp(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void hangup(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void hangupWaitingOrBackground(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void hangupForegroundResumeBackground(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void switchWaitingOrHoldingAndActive(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void conference(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void rejectCall(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getLastCallFailCause(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getSignalStrength(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getVoiceRegistrationState(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getDataRegistrationState(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getOperator(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setRadioPower(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendDtmf(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendSms(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            gsmSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendSMSExpectMore(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            gsmSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setupDataCall(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, boolean z2, boolean z3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            dataProfileInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            hwParcel.writeBool(z3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void iccIOForApp(int i, IccIo iccIo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            iccIo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendUssd(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void cancelPendingUssd(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(31, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getClir(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(32, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setClir(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(33, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getCallForwardStatus(int i, CallForwardInfo callForwardInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            callForwardInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(34, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setCallForward(int i, CallForwardInfo callForwardInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            callForwardInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(35, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getCallWaiting(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(36, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setCallWaiting(int i, boolean z, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(37, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void acknowledgeLastIncomingGsmSms(int i, boolean z, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(38, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void acceptCall(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(39, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void deactivateDataCall(int i, int i2, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(40, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getFacilityLockForApp(int i, String str, String str2, int i2, String str3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeInt32(i2);
            hwParcel.writeString(str3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(41, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setFacilityLockForApp(int i, String str, boolean z, String str2, int i2, String str3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeBool(z);
            hwParcel.writeString(str2);
            hwParcel.writeInt32(i2);
            hwParcel.writeString(str3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(42, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setBarringPassword(int i, String str, String str2, String str3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(43, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getNetworkSelectionMode(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(44, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setNetworkSelectionModeAutomatic(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(45, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setNetworkSelectionModeManual(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(46, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getAvailableNetworks(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(47, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void startDtmf(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(48, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void stopDtmf(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(49, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getBasebandVersion(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(50, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void separateConnection(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(51, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setMute(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(52, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getMute(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(53, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getClip(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(54, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getDataCallList(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(55, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setSuppServiceNotifications(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(56, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void writeSmsToSim(int i, SmsWriteArgs smsWriteArgs) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            smsWriteArgs.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(57, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void deleteSmsOnSim(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(58, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setBandMode(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(59, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getAvailableBandModes(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(60, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendEnvelope(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(61, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendTerminalResponseToSim(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(62, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void handleStkCallSetupRequestFromSim(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(63, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void explicitCallTransfer(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(64, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setPreferredNetworkType(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(65, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getPreferredNetworkType(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(66, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getNeighboringCids(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(67, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setLocationUpdates(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(68, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setCdmaSubscriptionSource(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(69, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setCdmaRoamingPreference(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(70, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getCdmaRoamingPreference(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(71, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setTTYMode(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(72, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getTTYMode(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(73, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setPreferredVoicePrivacy(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(74, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getPreferredVoicePrivacy(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(75, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendCDMAFeatureCode(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(76, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendBurstDtmf(int i, String str, int i2, int i3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(77, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendCdmaSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(78, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void acknowledgeLastIncomingCdmaSms(int i, CdmaSmsAck cdmaSmsAck) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaSmsAck.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(79, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getGsmBroadcastConfig(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(80, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setGsmBroadcastConfig(int i, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            GsmBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(81, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setGsmBroadcastActivation(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(82, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getCdmaBroadcastConfig(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(83, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setCdmaBroadcastConfig(int i, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            CdmaBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(84, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setCdmaBroadcastActivation(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(85, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getCDMASubscription(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(86, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void writeSmsToRuim(int i, CdmaSmsWriteArgs cdmaSmsWriteArgs) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaSmsWriteArgs.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(87, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void deleteSmsOnRuim(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(88, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getDeviceIdentity(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(89, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void exitEmergencyCallbackMode(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(90, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getSmscAddress(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(91, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setSmscAddress(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(92, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void reportSmsMemoryStatus(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(93, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void reportStkServiceIsRunning(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(94, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getCdmaSubscriptionSource(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(95, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void requestIsimAuthentication(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(96, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void acknowledgeIncomingGsmSmsWithPdu(int i, boolean z, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(97, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendEnvelopeWithStatus(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(98, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getVoiceRadioTechnology(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(99, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getCellInfoList(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(100, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setCellInfoListRate(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(101, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setInitialAttachApn(int i, DataProfileInfo dataProfileInfo, boolean z, boolean z2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            dataProfileInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(102, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getImsRegistrationState(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(103, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendImsSms(int i, ImsSmsMessage imsSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            imsSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(104, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void iccTransmitApduBasicChannel(int i, SimApdu simApdu) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            simApdu.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(105, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void iccOpenLogicalChannel(int i, String str, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(106, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void iccCloseLogicalChannel(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(107, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void iccTransmitApduLogicalChannel(int i, SimApdu simApdu) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            simApdu.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(108, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void nvReadItem(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(109, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void nvWriteItem(int i, NvWriteItem nvWriteItem) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            nvWriteItem.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(110, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void nvWriteCdmaPrl(int i, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(111, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void nvResetConfig(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(112, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setUiccSubscription(int i, SelectUiccSub selectUiccSub) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            selectUiccSub.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(113, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setDataAllowed(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(114, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getHardwareConfig(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(115, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void requestIccSimAuthentication(int i, int i2, String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(116, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setDataProfile(int i, ArrayList<DataProfileInfo> arrayList, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            DataProfileInfo.writeVectorToParcel(hwParcel, arrayList);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(117, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void requestShutdown(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(118, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getRadioCapability(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(119, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setRadioCapability(int i, RadioCapability radioCapability) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            radioCapability.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(120, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void startLceService(int i, int i2, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(121, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void stopLceService(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(122, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void pullLceData(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(123, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void getModemActivityInfo(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(124, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setAllowedCarriers(int i, boolean z, CarrierRestrictions carrierRestrictions) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
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

        @Override // android.hardware.radio.V1_0.IRadio
        public void getAllowedCarriers(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(126, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void sendDeviceState(int i, int i2, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(127, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setIndicationFilter(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(128, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setSimCardPower(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(129, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void responseAcknowledgement() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(130, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ArrayList<byte[]> arrayList = new ArrayList<>();
                HwBlob readBuffer = hwParcel2.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IRadio {
        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IRadio.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IRadio.kInterfaceName;
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-49, -86, MidiConstants.STATUS_CONTROL_CHANGE, -28, 92, 93, 123, 53, -107, 3, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 100, -99, -94, -98, -41, 18, -23, 32, -7, 86, -63, 54, 113, -17, -45, 86, 2, -6, -127, -55, 35}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IRadio.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setResponseFunctions(IRadioResponse.asInterface(hwParcel.readStrongBinder()), IRadioIndication.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getIccCardStatus(hwParcel.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyIccPinForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 4:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyIccPukForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 5:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyIccPin2ForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 6:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyIccPuk2ForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 7:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    changeIccPinForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 8:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    changeIccPin2ForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 9:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    supplyNetworkDepersonalization(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 10:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCurrentCalls(hwParcel.readInt32());
                    return;
                case 11:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt32 = hwParcel.readInt32();
                    Dial dial = new Dial();
                    dial.readFromParcel(hwParcel);
                    dial(readInt32, dial);
                    return;
                case 12:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getImsiForApp(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 13:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    hangup(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 14:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    hangupWaitingOrBackground(hwParcel.readInt32());
                    return;
                case 15:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    hangupForegroundResumeBackground(hwParcel.readInt32());
                    return;
                case 16:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    switchWaitingOrHoldingAndActive(hwParcel.readInt32());
                    return;
                case 17:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    conference(hwParcel.readInt32());
                    return;
                case 18:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    rejectCall(hwParcel.readInt32());
                    return;
                case 19:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getLastCallFailCause(hwParcel.readInt32());
                    return;
                case 20:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getSignalStrength(hwParcel.readInt32());
                    return;
                case 21:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getVoiceRegistrationState(hwParcel.readInt32());
                    return;
                case 22:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getDataRegistrationState(hwParcel.readInt32());
                    return;
                case 23:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getOperator(hwParcel.readInt32());
                    return;
                case 24:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setRadioPower(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 25:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendDtmf(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 26:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt322 = hwParcel.readInt32();
                    GsmSmsMessage gsmSmsMessage = new GsmSmsMessage();
                    gsmSmsMessage.readFromParcel(hwParcel);
                    sendSms(readInt322, gsmSmsMessage);
                    return;
                case 27:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt323 = hwParcel.readInt32();
                    GsmSmsMessage gsmSmsMessage2 = new GsmSmsMessage();
                    gsmSmsMessage2.readFromParcel(hwParcel);
                    sendSMSExpectMore(readInt323, gsmSmsMessage2);
                    return;
                case 28:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt324 = hwParcel.readInt32();
                    int readInt325 = hwParcel.readInt32();
                    DataProfileInfo dataProfileInfo = new DataProfileInfo();
                    dataProfileInfo.readFromParcel(hwParcel);
                    setupDataCall(readInt324, readInt325, dataProfileInfo, hwParcel.readBool(), hwParcel.readBool(), hwParcel.readBool());
                    return;
                case 29:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt326 = hwParcel.readInt32();
                    IccIo iccIo = new IccIo();
                    iccIo.readFromParcel(hwParcel);
                    iccIOForApp(readInt326, iccIo);
                    return;
                case 30:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendUssd(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 31:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    cancelPendingUssd(hwParcel.readInt32());
                    return;
                case 32:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getClir(hwParcel.readInt32());
                    return;
                case 33:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setClir(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 34:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt327 = hwParcel.readInt32();
                    CallForwardInfo callForwardInfo = new CallForwardInfo();
                    callForwardInfo.readFromParcel(hwParcel);
                    getCallForwardStatus(readInt327, callForwardInfo);
                    return;
                case 35:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt328 = hwParcel.readInt32();
                    CallForwardInfo callForwardInfo2 = new CallForwardInfo();
                    callForwardInfo2.readFromParcel(hwParcel);
                    setCallForward(readInt328, callForwardInfo2);
                    return;
                case 36:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCallWaiting(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 37:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCallWaiting(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 38:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    acknowledgeLastIncomingGsmSms(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 39:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    acceptCall(hwParcel.readInt32());
                    return;
                case 40:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    deactivateDataCall(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 41:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getFacilityLockForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 42:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setFacilityLockForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readBool(), hwParcel.readString(), hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 43:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setBarringPassword(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 44:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getNetworkSelectionMode(hwParcel.readInt32());
                    return;
                case 45:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setNetworkSelectionModeAutomatic(hwParcel.readInt32());
                    return;
                case 46:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setNetworkSelectionModeManual(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 47:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getAvailableNetworks(hwParcel.readInt32());
                    return;
                case 48:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    startDtmf(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 49:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    stopDtmf(hwParcel.readInt32());
                    return;
                case 50:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getBasebandVersion(hwParcel.readInt32());
                    return;
                case 51:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    separateConnection(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 52:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setMute(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 53:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getMute(hwParcel.readInt32());
                    return;
                case 54:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getClip(hwParcel.readInt32());
                    return;
                case 55:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getDataCallList(hwParcel.readInt32());
                    return;
                case 56:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setSuppServiceNotifications(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 57:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt329 = hwParcel.readInt32();
                    SmsWriteArgs smsWriteArgs = new SmsWriteArgs();
                    smsWriteArgs.readFromParcel(hwParcel);
                    writeSmsToSim(readInt329, smsWriteArgs);
                    return;
                case 58:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    deleteSmsOnSim(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 59:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setBandMode(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 60:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getAvailableBandModes(hwParcel.readInt32());
                    return;
                case 61:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendEnvelope(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 62:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendTerminalResponseToSim(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 63:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    handleStkCallSetupRequestFromSim(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 64:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    explicitCallTransfer(hwParcel.readInt32());
                    return;
                case 65:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setPreferredNetworkType(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 66:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getPreferredNetworkType(hwParcel.readInt32());
                    return;
                case 67:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getNeighboringCids(hwParcel.readInt32());
                    return;
                case 68:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setLocationUpdates(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 69:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCdmaSubscriptionSource(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 70:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCdmaRoamingPreference(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 71:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCdmaRoamingPreference(hwParcel.readInt32());
                    return;
                case 72:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setTTYMode(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 73:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getTTYMode(hwParcel.readInt32());
                    return;
                case 74:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setPreferredVoicePrivacy(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 75:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getPreferredVoicePrivacy(hwParcel.readInt32());
                    return;
                case 76:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendCDMAFeatureCode(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 77:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendBurstDtmf(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 78:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3210 = hwParcel.readInt32();
                    CdmaSmsMessage cdmaSmsMessage = new CdmaSmsMessage();
                    cdmaSmsMessage.readFromParcel(hwParcel);
                    sendCdmaSms(readInt3210, cdmaSmsMessage);
                    return;
                case 79:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3211 = hwParcel.readInt32();
                    CdmaSmsAck cdmaSmsAck = new CdmaSmsAck();
                    cdmaSmsAck.readFromParcel(hwParcel);
                    acknowledgeLastIncomingCdmaSms(readInt3211, cdmaSmsAck);
                    return;
                case 80:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getGsmBroadcastConfig(hwParcel.readInt32());
                    return;
                case 81:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setGsmBroadcastConfig(hwParcel.readInt32(), GsmBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 82:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setGsmBroadcastActivation(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 83:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCdmaBroadcastConfig(hwParcel.readInt32());
                    return;
                case 84:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCdmaBroadcastConfig(hwParcel.readInt32(), CdmaBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 85:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCdmaBroadcastActivation(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 86:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCDMASubscription(hwParcel.readInt32());
                    return;
                case 87:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3212 = hwParcel.readInt32();
                    CdmaSmsWriteArgs cdmaSmsWriteArgs = new CdmaSmsWriteArgs();
                    cdmaSmsWriteArgs.readFromParcel(hwParcel);
                    writeSmsToRuim(readInt3212, cdmaSmsWriteArgs);
                    return;
                case 88:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    deleteSmsOnRuim(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 89:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getDeviceIdentity(hwParcel.readInt32());
                    return;
                case 90:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    exitEmergencyCallbackMode(hwParcel.readInt32());
                    return;
                case 91:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getSmscAddress(hwParcel.readInt32());
                    return;
                case 92:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setSmscAddress(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 93:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    reportSmsMemoryStatus(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 94:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    reportStkServiceIsRunning(hwParcel.readInt32());
                    return;
                case 95:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCdmaSubscriptionSource(hwParcel.readInt32());
                    return;
                case 96:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    requestIsimAuthentication(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 97:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    acknowledgeIncomingGsmSmsWithPdu(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readString());
                    return;
                case 98:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendEnvelopeWithStatus(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 99:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getVoiceRadioTechnology(hwParcel.readInt32());
                    return;
                case 100:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCellInfoList(hwParcel.readInt32());
                    return;
                case 101:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setCellInfoListRate(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 102:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3213 = hwParcel.readInt32();
                    DataProfileInfo dataProfileInfo2 = new DataProfileInfo();
                    dataProfileInfo2.readFromParcel(hwParcel);
                    setInitialAttachApn(readInt3213, dataProfileInfo2, hwParcel.readBool(), hwParcel.readBool());
                    return;
                case 103:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getImsRegistrationState(hwParcel.readInt32());
                    return;
                case 104:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3214 = hwParcel.readInt32();
                    ImsSmsMessage imsSmsMessage = new ImsSmsMessage();
                    imsSmsMessage.readFromParcel(hwParcel);
                    sendImsSms(readInt3214, imsSmsMessage);
                    return;
                case 105:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3215 = hwParcel.readInt32();
                    SimApdu simApdu = new SimApdu();
                    simApdu.readFromParcel(hwParcel);
                    iccTransmitApduBasicChannel(readInt3215, simApdu);
                    return;
                case 106:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    iccOpenLogicalChannel(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readInt32());
                    return;
                case 107:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    iccCloseLogicalChannel(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 108:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3216 = hwParcel.readInt32();
                    SimApdu simApdu2 = new SimApdu();
                    simApdu2.readFromParcel(hwParcel);
                    iccTransmitApduLogicalChannel(readInt3216, simApdu2);
                    return;
                case 109:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    nvReadItem(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 110:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3217 = hwParcel.readInt32();
                    NvWriteItem nvWriteItem = new NvWriteItem();
                    nvWriteItem.readFromParcel(hwParcel);
                    nvWriteItem(readInt3217, nvWriteItem);
                    return;
                case 111:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    nvWriteCdmaPrl(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 112:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    nvResetConfig(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 113:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3218 = hwParcel.readInt32();
                    SelectUiccSub selectUiccSub = new SelectUiccSub();
                    selectUiccSub.readFromParcel(hwParcel);
                    setUiccSubscription(readInt3218, selectUiccSub);
                    return;
                case 114:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setDataAllowed(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 115:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getHardwareConfig(hwParcel.readInt32());
                    return;
                case 116:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    requestIccSimAuthentication(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 117:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setDataProfile(hwParcel.readInt32(), DataProfileInfo.readVectorFromParcel(hwParcel), hwParcel.readBool());
                    return;
                case 118:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    requestShutdown(hwParcel.readInt32());
                    return;
                case 119:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getRadioCapability(hwParcel.readInt32());
                    return;
                case 120:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3219 = hwParcel.readInt32();
                    RadioCapability radioCapability = new RadioCapability();
                    radioCapability.readFromParcel(hwParcel);
                    setRadioCapability(readInt3219, radioCapability);
                    return;
                case 121:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    startLceService(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 122:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    stopLceService(hwParcel.readInt32());
                    return;
                case 123:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    pullLceData(hwParcel.readInt32());
                    return;
                case 124:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getModemActivityInfo(hwParcel.readInt32());
                    return;
                case 125:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int readInt3220 = hwParcel.readInt32();
                    boolean readBool = hwParcel.readBool();
                    CarrierRestrictions carrierRestrictions = new CarrierRestrictions();
                    carrierRestrictions.readFromParcel(hwParcel);
                    setAllowedCarriers(readInt3220, readBool, carrierRestrictions);
                    return;
                case 126:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getAllowedCarriers(hwParcel.readInt32());
                    return;
                case 127:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    sendDeviceState(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 128:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setIndicationFilter(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 129:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setSimCardPower(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 130:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    responseAcknowledgement();
                    return;
                default:
                    switch (i) {
                        case 256067662:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList<String> interfaceChain = interfaceChain();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeStringVector(interfaceChain);
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
                            String interfaceDescriptor = interfaceDescriptor();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeString(interfaceDescriptor);
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
