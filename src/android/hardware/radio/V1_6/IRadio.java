package android.hardware.radio.V1_6;

import android.hardware.radio.V1_0.CallForwardInfo;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.CdmaSmsAck;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CdmaSmsWriteArgs;
import android.hardware.radio.V1_0.Dial;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.GsmSmsMessage;
import android.hardware.radio.V1_0.IccIo;
import android.hardware.radio.V1_0.ImsSmsMessage;
import android.hardware.radio.V1_0.NvWriteItem;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.SelectUiccSub;
import android.hardware.radio.V1_0.SimApdu;
import android.hardware.radio.V1_0.SmsWriteArgs;
import android.hardware.radio.V1_1.KeepaliveRequest;
import android.hardware.radio.V1_1.NetworkScanRequest;
import android.hardware.radio.V1_1.RadioAccessSpecifier;
import android.hardware.radio.V1_4.CarrierRestrictionsWithPriority;
import android.hardware.radio.V1_5.DataProfileInfo;
import android.hardware.radio.V1_5.LinkAddress;
import android.hardware.radio.V1_5.SignalThresholdInfo;
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
public interface IRadio extends android.hardware.radio.V1_5.IRadio {
    public static final String kInterfaceName = "android.hardware.radio@1.6::IRadio";

    void allocatePduSessionId(int i) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    void cancelHandover(int i, int i2) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void emergencyDial_1_6(int i, Dial dial, int i2, ArrayList<String> arrayList, int i3, boolean z, boolean z2) throws RemoteException;

    void getAllowedNetworkTypesBitmap(int i) throws RemoteException;

    void getCellInfoList_1_6(int i) throws RemoteException;

    void getCurrentCalls_1_6(int i) throws RemoteException;

    void getDataCallList_1_6(int i) throws RemoteException;

    void getDataRegistrationState_1_6(int i) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getSignalStrength_1_6(int i) throws RemoteException;

    void getSimPhonebookCapacity(int i) throws RemoteException;

    void getSimPhonebookRecords(int i) throws RemoteException;

    void getSlicingConfig(int i) throws RemoteException;

    void getSystemSelectionChannels(int i) throws RemoteException;

    void getVoiceRegistrationState_1_6(int i) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    void isNrDualConnectivityEnabled(int i) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    void releasePduSessionId(int i, int i2) throws RemoteException;

    void sendCdmaSmsExpectMore_1_6(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    void sendCdmaSms_1_6(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    void sendSmsExpectMore_1_6(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    void sendSms_1_6(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException;

    void setAllowedNetworkTypesBitmap(int i, int i2) throws RemoteException;

    void setCarrierInfoForImsiEncryption_1_6(int i, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException;

    void setDataThrottling(int i, byte b, long j) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    void setNrDualConnectivityState(int i, byte b) throws RemoteException;

    void setRadioPower_1_6(int i, boolean z, boolean z2, boolean z3) throws RemoteException;

    void setSimCardPower_1_6(int i, int i2) throws RemoteException;

    void setupDataCall_1_6(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, int i3, ArrayList<LinkAddress> arrayList, ArrayList<String> arrayList2, int i4, OptionalSliceInfo optionalSliceInfo, OptionalTrafficDescriptor optionalTrafficDescriptor, boolean z2) throws RemoteException;

    void startHandover(int i, int i2) throws RemoteException;

    @Override // android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void updateSimPhonebookRecords(int i, PhonebookRecordInfo phonebookRecordInfo) throws RemoteException;

    static IRadio asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof IRadio)) {
            return (IRadio) iHwInterfaceQueryLocalInterface;
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

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.radio@1.6::IRadio]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.radio.V1_0.IRadio
        public void setResponseFunctions(android.hardware.radio.V1_0.IRadioResponse iRadioResponse, android.hardware.radio.V1_0.IRadioIndication iRadioIndication) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
        public void setupDataCall(int i, int i2, android.hardware.radio.V1_0.DataProfileInfo dataProfileInfo, boolean z, boolean z2, boolean z3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
        public void setInitialAttachApn(int i, android.hardware.radio.V1_0.DataProfileInfo dataProfileInfo, boolean z, boolean z2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
        public void setDataProfile(int i, ArrayList<android.hardware.radio.V1_0.DataProfileInfo> arrayList, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            android.hardware.radio.V1_0.DataProfileInfo.writeVectorToParcel(hwParcel, arrayList);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
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
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadio.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(130, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadio
        public void setCarrierInfoForImsiEncryption(int i, android.hardware.radio.V1_1.ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            imsiEncryptionInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(131, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadio
        public void setSimCardPower_1_1(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(132, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadio
        public void startNetworkScan(int i, NetworkScanRequest networkScanRequest) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            networkScanRequest.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(133, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadio
        public void stopNetworkScan(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(134, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadio
        public void startKeepalive(int i, KeepaliveRequest keepaliveRequest) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            keepaliveRequest.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(135, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadio
        public void stopKeepalive(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(136, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadio
        public void startNetworkScan_1_2(int i, android.hardware.radio.V1_2.NetworkScanRequest networkScanRequest) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            networkScanRequest.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(137, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadio
        public void setIndicationFilter_1_2(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(138, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadio
        public void setSignalStrengthReportingCriteria(int i, int i2, int i3, ArrayList<Integer> arrayList, int i4) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            hwParcel.writeInt32Vector(arrayList);
            hwParcel.writeInt32(i4);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(139, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadio
        public void setLinkCapacityReportingCriteria(int i, int i2, int i3, int i4, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2, int i5) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            hwParcel.writeInt32(i4);
            hwParcel.writeInt32Vector(arrayList);
            hwParcel.writeInt32Vector(arrayList2);
            hwParcel.writeInt32(i5);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(140, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadio
        public void setupDataCall_1_2(int i, int i2, android.hardware.radio.V1_0.DataProfileInfo dataProfileInfo, boolean z, boolean z2, boolean z3, int i3, ArrayList<String> arrayList, ArrayList<String> arrayList2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            dataProfileInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            hwParcel.writeBool(z3);
            hwParcel.writeInt32(i3);
            hwParcel.writeStringVector(arrayList);
            hwParcel.writeStringVector(arrayList2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(141, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadio
        public void deactivateDataCall_1_2(int i, int i2, int i3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(142, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadio
        public void setSystemSelectionChannels(int i, boolean z, ArrayList<RadioAccessSpecifier> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_3.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            RadioAccessSpecifier.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(143, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadio
        public void enableModem(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_3.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(144, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadio
        public void getModemStackStatus(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_3.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(145, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void setupDataCall_1_4(int i, int i2, android.hardware.radio.V1_4.DataProfileInfo dataProfileInfo, boolean z, int i3, ArrayList<String> arrayList, ArrayList<String> arrayList2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            dataProfileInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i3);
            hwParcel.writeStringVector(arrayList);
            hwParcel.writeStringVector(arrayList2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(146, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void setInitialAttachApn_1_4(int i, android.hardware.radio.V1_4.DataProfileInfo dataProfileInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            dataProfileInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(147, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void setDataProfile_1_4(int i, ArrayList<android.hardware.radio.V1_4.DataProfileInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            android.hardware.radio.V1_4.DataProfileInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(148, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void emergencyDial(int i, Dial dial, int i2, ArrayList<String> arrayList, int i3, boolean z, boolean z2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            dial.writeToParcel(hwParcel);
            hwParcel.writeInt32(i2);
            hwParcel.writeStringVector(arrayList);
            hwParcel.writeInt32(i3);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(149, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void startNetworkScan_1_4(int i, android.hardware.radio.V1_2.NetworkScanRequest networkScanRequest) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            networkScanRequest.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(150, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void getPreferredNetworkTypeBitmap(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(151, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void setPreferredNetworkTypeBitmap(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(152, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void setAllowedCarriers_1_4(int i, CarrierRestrictionsWithPriority carrierRestrictionsWithPriority, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            carrierRestrictionsWithPriority.writeToParcel(hwParcel);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(153, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void getAllowedCarriers_1_4(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(154, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_4.IRadio
        public void getSignalStrength_1_4(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_4.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(155, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void setSignalStrengthReportingCriteria_1_5(int i, SignalThresholdInfo signalThresholdInfo, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            signalThresholdInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(156, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void setLinkCapacityReportingCriteria_1_5(int i, int i2, int i3, int i4, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2, int i5) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            hwParcel.writeInt32(i4);
            hwParcel.writeInt32Vector(arrayList);
            hwParcel.writeInt32Vector(arrayList2);
            hwParcel.writeInt32(i5);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(157, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void enableUiccApplications(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(158, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void areUiccApplicationsEnabled(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(159, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void setSystemSelectionChannels_1_5(int i, boolean z, ArrayList<android.hardware.radio.V1_5.RadioAccessSpecifier> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            android.hardware.radio.V1_5.RadioAccessSpecifier.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(160, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void startNetworkScan_1_5(int i, android.hardware.radio.V1_5.NetworkScanRequest networkScanRequest) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            networkScanRequest.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(161, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void setupDataCall_1_5(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, int i3, ArrayList<LinkAddress> arrayList, ArrayList<String> arrayList2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            dataProfileInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i3);
            LinkAddress.writeVectorToParcel(hwParcel, arrayList);
            hwParcel.writeStringVector(arrayList2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(162, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void setInitialAttachApn_1_5(int i, DataProfileInfo dataProfileInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            dataProfileInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(163, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void setDataProfile_1_5(int i, ArrayList<DataProfileInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            DataProfileInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(164, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void setRadioPower_1_5(int i, boolean z, boolean z2, boolean z3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            hwParcel.writeBool(z3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(165, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void setIndicationFilter_1_5(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(166, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void getBarringInfo(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(167, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void getVoiceRegistrationState_1_5(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(168, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void getDataRegistrationState_1_5(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(169, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void setNetworkSelectionModeManual_1_5(int i, String str, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(170, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void sendCdmaSmsExpectMore(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(171, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_5.IRadio
        public void supplySimDepersonalization(int i, int i2, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_5.IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(172, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void setRadioPower_1_6(int i, boolean z, boolean z2, boolean z3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            hwParcel.writeBool(z3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(173, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getDataCallList_1_6(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(174, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void setupDataCall_1_6(int i, int i2, DataProfileInfo dataProfileInfo, boolean z, int i3, ArrayList<LinkAddress> arrayList, ArrayList<String> arrayList2, int i4, OptionalSliceInfo optionalSliceInfo, OptionalTrafficDescriptor optionalTrafficDescriptor, boolean z2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            dataProfileInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i3);
            LinkAddress.writeVectorToParcel(hwParcel, arrayList);
            hwParcel.writeStringVector(arrayList2);
            hwParcel.writeInt32(i4);
            optionalSliceInfo.writeToParcel(hwParcel);
            optionalTrafficDescriptor.writeToParcel(hwParcel);
            hwParcel.writeBool(z2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(175, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void sendSms_1_6(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            gsmSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(176, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void sendSmsExpectMore_1_6(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            gsmSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(177, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void sendCdmaSms_1_6(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(178, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void sendCdmaSmsExpectMore_1_6(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(179, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void setSimCardPower_1_6(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(180, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void setNrDualConnectivityState(int i, byte b) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8(b);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(181, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void isNrDualConnectivityEnabled(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(182, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void allocatePduSessionId(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(183, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void releasePduSessionId(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(184, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void startHandover(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(185, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void cancelHandover(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(186, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void setAllowedNetworkTypesBitmap(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(187, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getAllowedNetworkTypesBitmap(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(188, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void setDataThrottling(int i, byte b, long j) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8(b);
            hwParcel.writeInt64(j);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(189, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void emergencyDial_1_6(int i, Dial dial, int i2, ArrayList<String> arrayList, int i3, boolean z, boolean z2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            dial.writeToParcel(hwParcel);
            hwParcel.writeInt32(i2);
            hwParcel.writeStringVector(arrayList);
            hwParcel.writeInt32(i3);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(190, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getSystemSelectionChannels(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(191, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getCellInfoList_1_6(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(192, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getVoiceRegistrationState_1_6(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(193, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getSignalStrength_1_6(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(194, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getDataRegistrationState_1_6(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(195, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getCurrentCalls_1_6(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(196, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getSlicingConfig(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(197, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void setCarrierInfoForImsiEncryption_1_6(int i, ImsiEncryptionInfo imsiEncryptionInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            imsiEncryptionInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(198, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getSimPhonebookRecords(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(199, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void getSimPhonebookCapacity(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(200, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio
        public void updateSimPhonebookRecords(int i, PhonebookRecordInfo phonebookRecordInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            phonebookRecordInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(201, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IRadio {
        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IRadio.kInterfaceName, android.hardware.radio.V1_5.IRadio.kInterfaceName, android.hardware.radio.V1_4.IRadio.kInterfaceName, android.hardware.radio.V1_3.IRadio.kInterfaceName, android.hardware.radio.V1_2.IRadio.kInterfaceName, android.hardware.radio.V1_1.IRadio.kInterfaceName, android.hardware.radio.V1_0.IRadio.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IRadio.kInterfaceName;
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{62, -120, 102, -104, 125, -28, -20, -76, -120, 7, MidiConstants.STATUS_PROGRAM_CHANGE, -99, 76, -120, -20, 56, 54, 89, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -94, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, 21, MidiConstants.STATUS_MIDI_TIME_CODE, -73, 78, -33, -117, 20, -38, 23, -124, 107}, new byte[]{-76, 84, -33, -123, 52, 65, -63, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, 110, 66, 94, -118, SprAttributeBase.TYPE_DURATION, -35, 41, -3, -94, 15, 94, 110, 57, -71, 61, 17, 3, -28, -77, 116, -107, -37, 56, -86}, new byte[]{-17, 74, -73, 65, -9, -25, 118, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, -76, 94, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -54, -125, -121, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 114, 0, 108, MidiConstants.STATUS_PITCH_BEND, 95, 87, -86, -102, -35, -59, 116, -119, 61, -46, -104, 114}, new byte[]{-95, -58, MidiConstants.STATUS_CONTROL_CHANGE, 118, 27, -53, -119, -42, -65, 21, -95, 86, -7, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 107, Byte.MIN_VALUE, MidiConstants.STATUS_NOTE_ON, -77, -87, 22, -95, 95, -22, 22, -119, -76, MidiConstants.STATUS_CONTROL_CHANGE, -63, 115, -114, 56, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80}, new byte[]{SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 90, -2, -10, -114, 62, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, MidiConstants.STATUS_MIDI_TIME_CODE, -38, -74, 62, 79, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -27, 115, 55, -17, 38, 53, -20, -127, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, 73, 8, 12, -83, -4, -23, 102, -45, 59, 82}, new byte[]{-122, -5, 7, -102, SprAttributeBase.TYPE_DURATION, 11, 35, 1, -89, 82, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -99, -5, -4, 83, -104, 58, 121, 93, 117, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, 17, -86, -68, -74, -125, 21, -95, -119, -10, -55, -94}, new byte[]{-49, -86, MidiConstants.STATUS_CONTROL_CHANGE, -28, 92, 93, 123, 53, -107, 3, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 100, -99, -94, -98, -41, 18, -23, 32, -7, 86, -63, 54, 113, -17, -45, 86, 2, -6, -127, -55, 35}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.radio.V1_6.IRadio, android.hardware.radio.V1_5.IRadio, android.hardware.radio.V1_4.IRadio, android.hardware.radio.V1_3.IRadio, android.hardware.radio.V1_2.IRadio, android.hardware.radio.V1_1.IRadio, android.hardware.radio.V1_0.IRadio, android.internal.hidl.base.V1_0.IBase
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
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setResponseFunctions(android.hardware.radio.V1_0.IRadioResponse.asInterface(hwParcel.readStrongBinder()), android.hardware.radio.V1_0.IRadioIndication.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getIccCardStatus(hwParcel.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyIccPinForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyIccPukForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyIccPin2ForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyIccPuk2ForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    changeIccPinForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    changeIccPin2ForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    supplyNetworkDepersonalization(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCurrentCalls(hwParcel.readInt32());
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int32 = hwParcel.readInt32();
                    Dial dial = new Dial();
                    dial.readFromParcel(hwParcel);
                    dial(int32, dial);
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getImsiForApp(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 13:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    hangup(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 14:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    hangupWaitingOrBackground(hwParcel.readInt32());
                    return;
                case 15:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    hangupForegroundResumeBackground(hwParcel.readInt32());
                    return;
                case 16:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    switchWaitingOrHoldingAndActive(hwParcel.readInt32());
                    return;
                case 17:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    conference(hwParcel.readInt32());
                    return;
                case 18:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    rejectCall(hwParcel.readInt32());
                    return;
                case 19:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getLastCallFailCause(hwParcel.readInt32());
                    return;
                case 20:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getSignalStrength(hwParcel.readInt32());
                    return;
                case 21:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getVoiceRegistrationState(hwParcel.readInt32());
                    return;
                case 22:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getDataRegistrationState(hwParcel.readInt32());
                    return;
                case 23:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getOperator(hwParcel.readInt32());
                    return;
                case 24:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setRadioPower(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 25:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendDtmf(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 26:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int322 = hwParcel.readInt32();
                    GsmSmsMessage gsmSmsMessage = new GsmSmsMessage();
                    gsmSmsMessage.readFromParcel(hwParcel);
                    sendSms(int322, gsmSmsMessage);
                    return;
                case 27:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int323 = hwParcel.readInt32();
                    GsmSmsMessage gsmSmsMessage2 = new GsmSmsMessage();
                    gsmSmsMessage2.readFromParcel(hwParcel);
                    sendSMSExpectMore(int323, gsmSmsMessage2);
                    return;
                case 28:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int324 = hwParcel.readInt32();
                    int int325 = hwParcel.readInt32();
                    android.hardware.radio.V1_0.DataProfileInfo dataProfileInfo = new android.hardware.radio.V1_0.DataProfileInfo();
                    dataProfileInfo.readFromParcel(hwParcel);
                    setupDataCall(int324, int325, dataProfileInfo, hwParcel.readBool(), hwParcel.readBool(), hwParcel.readBool());
                    return;
                case 29:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int326 = hwParcel.readInt32();
                    IccIo iccIo = new IccIo();
                    iccIo.readFromParcel(hwParcel);
                    iccIOForApp(int326, iccIo);
                    return;
                case 30:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendUssd(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 31:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    cancelPendingUssd(hwParcel.readInt32());
                    return;
                case 32:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getClir(hwParcel.readInt32());
                    return;
                case 33:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setClir(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 34:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int327 = hwParcel.readInt32();
                    CallForwardInfo callForwardInfo = new CallForwardInfo();
                    callForwardInfo.readFromParcel(hwParcel);
                    getCallForwardStatus(int327, callForwardInfo);
                    return;
                case 35:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int328 = hwParcel.readInt32();
                    CallForwardInfo callForwardInfo2 = new CallForwardInfo();
                    callForwardInfo2.readFromParcel(hwParcel);
                    setCallForward(int328, callForwardInfo2);
                    return;
                case 36:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCallWaiting(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 37:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCallWaiting(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 38:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    acknowledgeLastIncomingGsmSms(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 39:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    acceptCall(hwParcel.readInt32());
                    return;
                case 40:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    deactivateDataCall(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 41:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getFacilityLockForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 42:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setFacilityLockForApp(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readBool(), hwParcel.readString(), hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 43:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setBarringPassword(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 44:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getNetworkSelectionMode(hwParcel.readInt32());
                    return;
                case 45:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setNetworkSelectionModeAutomatic(hwParcel.readInt32());
                    return;
                case 46:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setNetworkSelectionModeManual(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 47:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getAvailableNetworks(hwParcel.readInt32());
                    return;
                case 48:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    startDtmf(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 49:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    stopDtmf(hwParcel.readInt32());
                    return;
                case 50:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getBasebandVersion(hwParcel.readInt32());
                    return;
                case 51:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    separateConnection(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 52:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setMute(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 53:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getMute(hwParcel.readInt32());
                    return;
                case 54:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getClip(hwParcel.readInt32());
                    return;
                case 55:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getDataCallList(hwParcel.readInt32());
                    return;
                case 56:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setSuppServiceNotifications(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 57:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int329 = hwParcel.readInt32();
                    SmsWriteArgs smsWriteArgs = new SmsWriteArgs();
                    smsWriteArgs.readFromParcel(hwParcel);
                    writeSmsToSim(int329, smsWriteArgs);
                    return;
                case 58:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    deleteSmsOnSim(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 59:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setBandMode(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 60:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getAvailableBandModes(hwParcel.readInt32());
                    return;
                case 61:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendEnvelope(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 62:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendTerminalResponseToSim(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 63:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    handleStkCallSetupRequestFromSim(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 64:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    explicitCallTransfer(hwParcel.readInt32());
                    return;
                case 65:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setPreferredNetworkType(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 66:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getPreferredNetworkType(hwParcel.readInt32());
                    return;
                case 67:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getNeighboringCids(hwParcel.readInt32());
                    return;
                case 68:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setLocationUpdates(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 69:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCdmaSubscriptionSource(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 70:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCdmaRoamingPreference(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 71:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCdmaRoamingPreference(hwParcel.readInt32());
                    return;
                case 72:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setTTYMode(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 73:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getTTYMode(hwParcel.readInt32());
                    return;
                case 74:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setPreferredVoicePrivacy(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 75:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getPreferredVoicePrivacy(hwParcel.readInt32());
                    return;
                case 76:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendCDMAFeatureCode(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 77:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendBurstDtmf(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 78:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3210 = hwParcel.readInt32();
                    CdmaSmsMessage cdmaSmsMessage = new CdmaSmsMessage();
                    cdmaSmsMessage.readFromParcel(hwParcel);
                    sendCdmaSms(int3210, cdmaSmsMessage);
                    return;
                case 79:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3211 = hwParcel.readInt32();
                    CdmaSmsAck cdmaSmsAck = new CdmaSmsAck();
                    cdmaSmsAck.readFromParcel(hwParcel);
                    acknowledgeLastIncomingCdmaSms(int3211, cdmaSmsAck);
                    return;
                case 80:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getGsmBroadcastConfig(hwParcel.readInt32());
                    return;
                case 81:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setGsmBroadcastConfig(hwParcel.readInt32(), GsmBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 82:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setGsmBroadcastActivation(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 83:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCdmaBroadcastConfig(hwParcel.readInt32());
                    return;
                case 84:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCdmaBroadcastConfig(hwParcel.readInt32(), CdmaBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 85:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCdmaBroadcastActivation(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 86:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCDMASubscription(hwParcel.readInt32());
                    return;
                case 87:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3212 = hwParcel.readInt32();
                    CdmaSmsWriteArgs cdmaSmsWriteArgs = new CdmaSmsWriteArgs();
                    cdmaSmsWriteArgs.readFromParcel(hwParcel);
                    writeSmsToRuim(int3212, cdmaSmsWriteArgs);
                    return;
                case 88:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    deleteSmsOnRuim(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 89:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getDeviceIdentity(hwParcel.readInt32());
                    return;
                case 90:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    exitEmergencyCallbackMode(hwParcel.readInt32());
                    return;
                case 91:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getSmscAddress(hwParcel.readInt32());
                    return;
                case 92:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setSmscAddress(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 93:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    reportSmsMemoryStatus(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 94:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    reportStkServiceIsRunning(hwParcel.readInt32());
                    return;
                case 95:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCdmaSubscriptionSource(hwParcel.readInt32());
                    return;
                case 96:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    requestIsimAuthentication(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 97:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    acknowledgeIncomingGsmSmsWithPdu(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readString());
                    return;
                case 98:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendEnvelopeWithStatus(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 99:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getVoiceRadioTechnology(hwParcel.readInt32());
                    return;
                case 100:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getCellInfoList(hwParcel.readInt32());
                    return;
                case 101:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setCellInfoListRate(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 102:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3213 = hwParcel.readInt32();
                    android.hardware.radio.V1_0.DataProfileInfo dataProfileInfo2 = new android.hardware.radio.V1_0.DataProfileInfo();
                    dataProfileInfo2.readFromParcel(hwParcel);
                    setInitialAttachApn(int3213, dataProfileInfo2, hwParcel.readBool(), hwParcel.readBool());
                    return;
                case 103:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getImsRegistrationState(hwParcel.readInt32());
                    return;
                case 104:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3214 = hwParcel.readInt32();
                    ImsSmsMessage imsSmsMessage = new ImsSmsMessage();
                    imsSmsMessage.readFromParcel(hwParcel);
                    sendImsSms(int3214, imsSmsMessage);
                    return;
                case 105:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3215 = hwParcel.readInt32();
                    SimApdu simApdu = new SimApdu();
                    simApdu.readFromParcel(hwParcel);
                    iccTransmitApduBasicChannel(int3215, simApdu);
                    return;
                case 106:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    iccOpenLogicalChannel(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readInt32());
                    return;
                case 107:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    iccCloseLogicalChannel(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 108:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3216 = hwParcel.readInt32();
                    SimApdu simApdu2 = new SimApdu();
                    simApdu2.readFromParcel(hwParcel);
                    iccTransmitApduLogicalChannel(int3216, simApdu2);
                    return;
                case 109:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    nvReadItem(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 110:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3217 = hwParcel.readInt32();
                    NvWriteItem nvWriteItem = new NvWriteItem();
                    nvWriteItem.readFromParcel(hwParcel);
                    nvWriteItem(int3217, nvWriteItem);
                    return;
                case 111:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    nvWriteCdmaPrl(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 112:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    nvResetConfig(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 113:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3218 = hwParcel.readInt32();
                    SelectUiccSub selectUiccSub = new SelectUiccSub();
                    selectUiccSub.readFromParcel(hwParcel);
                    setUiccSubscription(int3218, selectUiccSub);
                    return;
                case 114:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setDataAllowed(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 115:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getHardwareConfig(hwParcel.readInt32());
                    return;
                case 116:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    requestIccSimAuthentication(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 117:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setDataProfile(hwParcel.readInt32(), android.hardware.radio.V1_0.DataProfileInfo.readVectorFromParcel(hwParcel), hwParcel.readBool());
                    return;
                case 118:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    requestShutdown(hwParcel.readInt32());
                    return;
                case 119:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getRadioCapability(hwParcel.readInt32());
                    return;
                case 120:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3219 = hwParcel.readInt32();
                    RadioCapability radioCapability = new RadioCapability();
                    radioCapability.readFromParcel(hwParcel);
                    setRadioCapability(int3219, radioCapability);
                    return;
                case 121:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    startLceService(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 122:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    stopLceService(hwParcel.readInt32());
                    return;
                case 123:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    pullLceData(hwParcel.readInt32());
                    return;
                case 124:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getModemActivityInfo(hwParcel.readInt32());
                    return;
                case 125:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    int int3220 = hwParcel.readInt32();
                    boolean bool = hwParcel.readBool();
                    CarrierRestrictions carrierRestrictions = new CarrierRestrictions();
                    carrierRestrictions.readFromParcel(hwParcel);
                    setAllowedCarriers(int3220, bool, carrierRestrictions);
                    return;
                case 126:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    getAllowedCarriers(hwParcel.readInt32());
                    return;
                case 127:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    sendDeviceState(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 128:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setIndicationFilter(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 129:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    setSimCardPower(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 130:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadio.kInterfaceName);
                    responseAcknowledgement();
                    return;
                case 131:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    int int3221 = hwParcel.readInt32();
                    android.hardware.radio.V1_1.ImsiEncryptionInfo imsiEncryptionInfo = new android.hardware.radio.V1_1.ImsiEncryptionInfo();
                    imsiEncryptionInfo.readFromParcel(hwParcel);
                    setCarrierInfoForImsiEncryption(int3221, imsiEncryptionInfo);
                    return;
                case 132:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    setSimCardPower_1_1(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 133:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    int int3222 = hwParcel.readInt32();
                    NetworkScanRequest networkScanRequest = new NetworkScanRequest();
                    networkScanRequest.readFromParcel(hwParcel);
                    startNetworkScan(int3222, networkScanRequest);
                    return;
                case 134:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    stopNetworkScan(hwParcel.readInt32());
                    return;
                case 135:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    int int3223 = hwParcel.readInt32();
                    KeepaliveRequest keepaliveRequest = new KeepaliveRequest();
                    keepaliveRequest.readFromParcel(hwParcel);
                    startKeepalive(int3223, keepaliveRequest);
                    return;
                case 136:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadio.kInterfaceName);
                    stopKeepalive(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 137:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    int int3224 = hwParcel.readInt32();
                    android.hardware.radio.V1_2.NetworkScanRequest networkScanRequest2 = new android.hardware.radio.V1_2.NetworkScanRequest();
                    networkScanRequest2.readFromParcel(hwParcel);
                    startNetworkScan_1_2(int3224, networkScanRequest2);
                    return;
                case 138:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    setIndicationFilter_1_2(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 139:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    setSignalStrengthReportingCriteria(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32Vector(), hwParcel.readInt32());
                    return;
                case 140:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    setLinkCapacityReportingCriteria(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32Vector(), hwParcel.readInt32Vector(), hwParcel.readInt32());
                    return;
                case 141:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    int int3225 = hwParcel.readInt32();
                    int int3226 = hwParcel.readInt32();
                    android.hardware.radio.V1_0.DataProfileInfo dataProfileInfo3 = new android.hardware.radio.V1_0.DataProfileInfo();
                    dataProfileInfo3.readFromParcel(hwParcel);
                    setupDataCall_1_2(int3225, int3226, dataProfileInfo3, hwParcel.readBool(), hwParcel.readBool(), hwParcel.readBool(), hwParcel.readInt32(), hwParcel.readStringVector(), hwParcel.readStringVector());
                    return;
                case 142:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadio.kInterfaceName);
                    deactivateDataCall_1_2(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 143:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadio.kInterfaceName);
                    setSystemSelectionChannels(hwParcel.readInt32(), hwParcel.readBool(), RadioAccessSpecifier.readVectorFromParcel(hwParcel));
                    return;
                case 144:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadio.kInterfaceName);
                    enableModem(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 145:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadio.kInterfaceName);
                    getModemStackStatus(hwParcel.readInt32());
                    return;
                case 146:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int int3227 = hwParcel.readInt32();
                    int int3228 = hwParcel.readInt32();
                    android.hardware.radio.V1_4.DataProfileInfo dataProfileInfo4 = new android.hardware.radio.V1_4.DataProfileInfo();
                    dataProfileInfo4.readFromParcel(hwParcel);
                    setupDataCall_1_4(int3227, int3228, dataProfileInfo4, hwParcel.readBool(), hwParcel.readInt32(), hwParcel.readStringVector(), hwParcel.readStringVector());
                    return;
                case 147:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int int3229 = hwParcel.readInt32();
                    android.hardware.radio.V1_4.DataProfileInfo dataProfileInfo5 = new android.hardware.radio.V1_4.DataProfileInfo();
                    dataProfileInfo5.readFromParcel(hwParcel);
                    setInitialAttachApn_1_4(int3229, dataProfileInfo5);
                    return;
                case 148:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    setDataProfile_1_4(hwParcel.readInt32(), android.hardware.radio.V1_4.DataProfileInfo.readVectorFromParcel(hwParcel));
                    return;
                case 149:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int int3230 = hwParcel.readInt32();
                    Dial dial2 = new Dial();
                    dial2.readFromParcel(hwParcel);
                    emergencyDial(int3230, dial2, hwParcel.readInt32(), hwParcel.readStringVector(), hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readBool());
                    return;
                case 150:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int int3231 = hwParcel.readInt32();
                    android.hardware.radio.V1_2.NetworkScanRequest networkScanRequest3 = new android.hardware.radio.V1_2.NetworkScanRequest();
                    networkScanRequest3.readFromParcel(hwParcel);
                    startNetworkScan_1_4(int3231, networkScanRequest3);
                    return;
                case 151:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    getPreferredNetworkTypeBitmap(hwParcel.readInt32());
                    return;
                case 152:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    setPreferredNetworkTypeBitmap(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 153:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    int int3232 = hwParcel.readInt32();
                    CarrierRestrictionsWithPriority carrierRestrictionsWithPriority = new CarrierRestrictionsWithPriority();
                    carrierRestrictionsWithPriority.readFromParcel(hwParcel);
                    setAllowedCarriers_1_4(int3232, carrierRestrictionsWithPriority, hwParcel.readInt32());
                    return;
                case 154:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    getAllowedCarriers_1_4(hwParcel.readInt32());
                    return;
                case 155:
                    hwParcel.enforceInterface(android.hardware.radio.V1_4.IRadio.kInterfaceName);
                    getSignalStrength_1_4(hwParcel.readInt32());
                    return;
                case 156:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    int int3233 = hwParcel.readInt32();
                    SignalThresholdInfo signalThresholdInfo = new SignalThresholdInfo();
                    signalThresholdInfo.readFromParcel(hwParcel);
                    setSignalStrengthReportingCriteria_1_5(int3233, signalThresholdInfo, hwParcel.readInt32());
                    return;
                case 157:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    setLinkCapacityReportingCriteria_1_5(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32Vector(), hwParcel.readInt32Vector(), hwParcel.readInt32());
                    return;
                case 158:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    enableUiccApplications(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 159:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    areUiccApplicationsEnabled(hwParcel.readInt32());
                    return;
                case 160:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    setSystemSelectionChannels_1_5(hwParcel.readInt32(), hwParcel.readBool(), android.hardware.radio.V1_5.RadioAccessSpecifier.readVectorFromParcel(hwParcel));
                    return;
                case 161:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    int int3234 = hwParcel.readInt32();
                    android.hardware.radio.V1_5.NetworkScanRequest networkScanRequest4 = new android.hardware.radio.V1_5.NetworkScanRequest();
                    networkScanRequest4.readFromParcel(hwParcel);
                    startNetworkScan_1_5(int3234, networkScanRequest4);
                    return;
                case 162:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    int int3235 = hwParcel.readInt32();
                    int int3236 = hwParcel.readInt32();
                    DataProfileInfo dataProfileInfo6 = new DataProfileInfo();
                    dataProfileInfo6.readFromParcel(hwParcel);
                    setupDataCall_1_5(int3235, int3236, dataProfileInfo6, hwParcel.readBool(), hwParcel.readInt32(), LinkAddress.readVectorFromParcel(hwParcel), hwParcel.readStringVector());
                    return;
                case 163:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    int int3237 = hwParcel.readInt32();
                    DataProfileInfo dataProfileInfo7 = new DataProfileInfo();
                    dataProfileInfo7.readFromParcel(hwParcel);
                    setInitialAttachApn_1_5(int3237, dataProfileInfo7);
                    return;
                case 164:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    setDataProfile_1_5(hwParcel.readInt32(), DataProfileInfo.readVectorFromParcel(hwParcel));
                    return;
                case 165:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    setRadioPower_1_5(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readBool(), hwParcel.readBool());
                    return;
                case 166:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    setIndicationFilter_1_5(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 167:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    getBarringInfo(hwParcel.readInt32());
                    return;
                case 168:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    getVoiceRegistrationState_1_5(hwParcel.readInt32());
                    return;
                case 169:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    getDataRegistrationState_1_5(hwParcel.readInt32());
                    return;
                case 170:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    setNetworkSelectionModeManual_1_5(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readInt32());
                    return;
                case 171:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    int int3238 = hwParcel.readInt32();
                    CdmaSmsMessage cdmaSmsMessage2 = new CdmaSmsMessage();
                    cdmaSmsMessage2.readFromParcel(hwParcel);
                    sendCdmaSmsExpectMore(int3238, cdmaSmsMessage2);
                    return;
                case 172:
                    hwParcel.enforceInterface(android.hardware.radio.V1_5.IRadio.kInterfaceName);
                    supplySimDepersonalization(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 173:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setRadioPower_1_6(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readBool(), hwParcel.readBool());
                    return;
                case 174:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getDataCallList_1_6(hwParcel.readInt32());
                    return;
                case 175:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int int3239 = hwParcel.readInt32();
                    int int3240 = hwParcel.readInt32();
                    DataProfileInfo dataProfileInfo8 = new DataProfileInfo();
                    dataProfileInfo8.readFromParcel(hwParcel);
                    boolean bool2 = hwParcel.readBool();
                    int int3241 = hwParcel.readInt32();
                    ArrayList<LinkAddress> vectorFromParcel = LinkAddress.readVectorFromParcel(hwParcel);
                    ArrayList<String> stringVector = hwParcel.readStringVector();
                    int int3242 = hwParcel.readInt32();
                    OptionalSliceInfo optionalSliceInfo = new OptionalSliceInfo();
                    optionalSliceInfo.readFromParcel(hwParcel);
                    OptionalTrafficDescriptor optionalTrafficDescriptor = new OptionalTrafficDescriptor();
                    optionalTrafficDescriptor.readFromParcel(hwParcel);
                    setupDataCall_1_6(int3239, int3240, dataProfileInfo8, bool2, int3241, vectorFromParcel, stringVector, int3242, optionalSliceInfo, optionalTrafficDescriptor, hwParcel.readBool());
                    return;
                case 176:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int int3243 = hwParcel.readInt32();
                    GsmSmsMessage gsmSmsMessage3 = new GsmSmsMessage();
                    gsmSmsMessage3.readFromParcel(hwParcel);
                    sendSms_1_6(int3243, gsmSmsMessage3);
                    return;
                case 177:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int int3244 = hwParcel.readInt32();
                    GsmSmsMessage gsmSmsMessage4 = new GsmSmsMessage();
                    gsmSmsMessage4.readFromParcel(hwParcel);
                    sendSmsExpectMore_1_6(int3244, gsmSmsMessage4);
                    return;
                case 178:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int int3245 = hwParcel.readInt32();
                    CdmaSmsMessage cdmaSmsMessage3 = new CdmaSmsMessage();
                    cdmaSmsMessage3.readFromParcel(hwParcel);
                    sendCdmaSms_1_6(int3245, cdmaSmsMessage3);
                    return;
                case 179:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int int3246 = hwParcel.readInt32();
                    CdmaSmsMessage cdmaSmsMessage4 = new CdmaSmsMessage();
                    cdmaSmsMessage4.readFromParcel(hwParcel);
                    sendCdmaSmsExpectMore_1_6(int3246, cdmaSmsMessage4);
                    return;
                case 180:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setSimCardPower_1_6(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 181:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setNrDualConnectivityState(hwParcel.readInt32(), hwParcel.readInt8());
                    return;
                case 182:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    isNrDualConnectivityEnabled(hwParcel.readInt32());
                    return;
                case 183:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    allocatePduSessionId(hwParcel.readInt32());
                    return;
                case 184:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    releasePduSessionId(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 185:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    startHandover(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 186:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    cancelHandover(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 187:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setAllowedNetworkTypesBitmap(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 188:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getAllowedNetworkTypesBitmap(hwParcel.readInt32());
                    return;
                case 189:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    setDataThrottling(hwParcel.readInt32(), hwParcel.readInt8(), hwParcel.readInt64());
                    return;
                case 190:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int int3247 = hwParcel.readInt32();
                    Dial dial3 = new Dial();
                    dial3.readFromParcel(hwParcel);
                    emergencyDial_1_6(int3247, dial3, hwParcel.readInt32(), hwParcel.readStringVector(), hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readBool());
                    return;
                case 191:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getSystemSelectionChannels(hwParcel.readInt32());
                    return;
                case 192:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCellInfoList_1_6(hwParcel.readInt32());
                    return;
                case 193:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getVoiceRegistrationState_1_6(hwParcel.readInt32());
                    return;
                case 194:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getSignalStrength_1_6(hwParcel.readInt32());
                    return;
                case 195:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getDataRegistrationState_1_6(hwParcel.readInt32());
                    return;
                case 196:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getCurrentCalls_1_6(hwParcel.readInt32());
                    return;
                case 197:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getSlicingConfig(hwParcel.readInt32());
                    return;
                case 198:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int int3248 = hwParcel.readInt32();
                    ImsiEncryptionInfo imsiEncryptionInfo2 = new ImsiEncryptionInfo();
                    imsiEncryptionInfo2.readFromParcel(hwParcel);
                    setCarrierInfoForImsiEncryption_1_6(int3248, imsiEncryptionInfo2);
                    return;
                case 199:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getSimPhonebookRecords(hwParcel.readInt32());
                    return;
                case 200:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    getSimPhonebookCapacity(hwParcel.readInt32());
                    return;
                case 201:
                    hwParcel.enforceInterface(IRadio.kInterfaceName);
                    int int3249 = hwParcel.readInt32();
                    PhonebookRecordInfo phonebookRecordInfo = new PhonebookRecordInfo();
                    phonebookRecordInfo.readFromParcel(hwParcel);
                    updateSimPhonebookRecords(int3249, phonebookRecordInfo);
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
