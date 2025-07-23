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
public interface IRadioIndication extends IBase {
    public static final String kInterfaceName = "android.hardware.radio@1.0::IRadioIndication";

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    void callRing(int i, boolean z, CdmaSignalInfoRecord cdmaSignalInfoRecord) throws RemoteException;

    void callStateChanged(int i) throws RemoteException;

    void cdmaCallWaiting(int i, CdmaCallWaiting cdmaCallWaiting) throws RemoteException;

    void cdmaInfoRec(int i, CdmaInformationRecords cdmaInformationRecords) throws RemoteException;

    void cdmaNewSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException;

    void cdmaOtaProvisionStatus(int i, int i2) throws RemoteException;

    void cdmaPrlChanged(int i, int i2) throws RemoteException;

    void cdmaRuimSmsStorageFull(int i) throws RemoteException;

    void cdmaSubscriptionSourceChanged(int i, int i2) throws RemoteException;

    void cellInfoList(int i, ArrayList<CellInfo> arrayList) throws RemoteException;

    void currentSignalStrength(int i, SignalStrength signalStrength) throws RemoteException;

    void dataCallListChanged(int i, ArrayList<SetupDataCallResult> arrayList) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void enterEmergencyCallbackMode(int i) throws RemoteException;

    void exitEmergencyCallbackMode(int i) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void hardwareConfigChanged(int i, ArrayList<HardwareConfig> arrayList) throws RemoteException;

    void imsNetworkStateChanged(int i) throws RemoteException;

    void indicateRingbackTone(int i, boolean z) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    void lceData(int i, LceDataInfo lceDataInfo) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void modemReset(int i, String str) throws RemoteException;

    void networkStateChanged(int i) throws RemoteException;

    void newBroadcastSms(int i, ArrayList<Byte> arrayList) throws RemoteException;

    void newSms(int i, ArrayList<Byte> arrayList) throws RemoteException;

    void newSmsOnSim(int i, int i2) throws RemoteException;

    void newSmsStatusReport(int i, ArrayList<Byte> arrayList) throws RemoteException;

    void nitzTimeReceived(int i, String str, long j) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    void onSupplementaryServiceIndication(int i, StkCcUnsolSsResult stkCcUnsolSsResult) throws RemoteException;

    void onUssd(int i, int i2, String str) throws RemoteException;

    void pcoData(int i, PcoDataInfo pcoDataInfo) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    void radioCapabilityIndication(int i, RadioCapability radioCapability) throws RemoteException;

    void radioStateChanged(int i, int i2) throws RemoteException;

    void resendIncallMute(int i) throws RemoteException;

    void restrictedStateChanged(int i, int i2) throws RemoteException;

    void rilConnected(int i) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    void simRefresh(int i, SimRefreshResult simRefreshResult) throws RemoteException;

    void simSmsStorageFull(int i) throws RemoteException;

    void simStatusChanged(int i) throws RemoteException;

    void srvccStateNotify(int i, int i2) throws RemoteException;

    void stkCallControlAlphaNotify(int i, String str) throws RemoteException;

    void stkCallSetup(int i, long j) throws RemoteException;

    void stkEventNotify(int i, String str) throws RemoteException;

    void stkProactiveCommand(int i, String str) throws RemoteException;

    void stkSessionEnd(int i) throws RemoteException;

    void subscriptionStatusChanged(int i, boolean z) throws RemoteException;

    void suppSvcNotify(int i, SuppSvcNotification suppSvcNotification) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void voiceRadioTechChanged(int i, int i2) throws RemoteException;

    static IRadioIndication asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IRadioIndication)) {
            return (IRadioIndication) queryLocalInterface;
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

    static IRadioIndication castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IRadioIndication getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IRadioIndication getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IRadioIndication getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IRadioIndication getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IRadioIndication {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.radio@1.0::IRadioIndication]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void radioStateChanged(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void callStateChanged(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void networkStateChanged(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void newSms(int i, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void newSmsStatusReport(int i, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void newSmsOnSim(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void onUssd(int i, int i2, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void nitzTimeReceived(int i, String str, long j) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeInt64(j);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void currentSignalStrength(int i, SignalStrength signalStrength) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            signalStrength.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void dataCallListChanged(int i, ArrayList<SetupDataCallResult> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            SetupDataCallResult.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void suppSvcNotify(int i, SuppSvcNotification suppSvcNotification) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            suppSvcNotification.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void stkSessionEnd(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void stkProactiveCommand(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void stkEventNotify(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void stkCallSetup(int i, long j) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt64(j);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void simSmsStorageFull(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void simRefresh(int i, SimRefreshResult simRefreshResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            simRefreshResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void callRing(int i, boolean z, CdmaSignalInfoRecord cdmaSignalInfoRecord) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            cdmaSignalInfoRecord.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void simStatusChanged(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void cdmaNewSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void newBroadcastSms(int i, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void cdmaRuimSmsStorageFull(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void restrictedStateChanged(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void enterEmergencyCallbackMode(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void cdmaCallWaiting(int i, CdmaCallWaiting cdmaCallWaiting) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaCallWaiting.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void cdmaOtaProvisionStatus(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void cdmaInfoRec(int i, CdmaInformationRecords cdmaInformationRecords) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaInformationRecords.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void indicateRingbackTone(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void resendIncallMute(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void cdmaSubscriptionSourceChanged(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void cdmaPrlChanged(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
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

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void exitEmergencyCallbackMode(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(32, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void rilConnected(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(33, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void voiceRadioTechChanged(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(34, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void cellInfoList(int i, ArrayList<CellInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            CellInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(35, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void imsNetworkStateChanged(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(36, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void subscriptionStatusChanged(int i, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(37, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void srvccStateNotify(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(38, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void hardwareConfigChanged(int i, ArrayList<HardwareConfig> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HardwareConfig.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(39, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void radioCapabilityIndication(int i, RadioCapability radioCapability) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            radioCapability.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(40, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void onSupplementaryServiceIndication(int i, StkCcUnsolSsResult stkCcUnsolSsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            stkCcUnsolSsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(41, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void stkCallControlAlphaNotify(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(42, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void lceData(int i, LceDataInfo lceDataInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            lceDataInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(43, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void pcoData(int i, PcoDataInfo pcoDataInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            pcoDataInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(44, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication
        public void modemReset(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(45, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IRadioIndication {
        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IRadioIndication.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IRadioIndication.kInterfaceName;
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-119, -41, -113, -92, -101, 9, -30, MidiConstants.STATUS_SONG_SELECT, 24, 18, -69, 99, -31, -65, -84, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, MidiConstants.STATUS_SONG_SELECT, 24, -87, 86, 20, 115, -58, MidiConstants.STATUS_CONTROL_CHANGE, -19, 105, 4, -50, 24, 55, 125, 84}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.radio.V1_0.IRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IRadioIndication.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    radioStateChanged(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 2:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    callStateChanged(hwParcel.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    networkStateChanged(hwParcel.readInt32());
                    return;
                case 4:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    newSms(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 5:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    newSmsStatusReport(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 6:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    newSmsOnSim(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 7:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    onUssd(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 8:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    nitzTimeReceived(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readInt64());
                    return;
                case 9:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt32 = hwParcel.readInt32();
                    SignalStrength signalStrength = new SignalStrength();
                    signalStrength.readFromParcel(hwParcel);
                    currentSignalStrength(readInt32, signalStrength);
                    return;
                case 10:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    dataCallListChanged(hwParcel.readInt32(), SetupDataCallResult.readVectorFromParcel(hwParcel));
                    return;
                case 11:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt322 = hwParcel.readInt32();
                    SuppSvcNotification suppSvcNotification = new SuppSvcNotification();
                    suppSvcNotification.readFromParcel(hwParcel);
                    suppSvcNotify(readInt322, suppSvcNotification);
                    return;
                case 12:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    stkSessionEnd(hwParcel.readInt32());
                    return;
                case 13:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    stkProactiveCommand(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 14:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    stkEventNotify(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 15:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    stkCallSetup(hwParcel.readInt32(), hwParcel.readInt64());
                    return;
                case 16:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    simSmsStorageFull(hwParcel.readInt32());
                    return;
                case 17:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt323 = hwParcel.readInt32();
                    SimRefreshResult simRefreshResult = new SimRefreshResult();
                    simRefreshResult.readFromParcel(hwParcel);
                    simRefresh(readInt323, simRefreshResult);
                    return;
                case 18:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt324 = hwParcel.readInt32();
                    boolean readBool = hwParcel.readBool();
                    CdmaSignalInfoRecord cdmaSignalInfoRecord = new CdmaSignalInfoRecord();
                    cdmaSignalInfoRecord.readFromParcel(hwParcel);
                    callRing(readInt324, readBool, cdmaSignalInfoRecord);
                    return;
                case 19:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    simStatusChanged(hwParcel.readInt32());
                    return;
                case 20:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt325 = hwParcel.readInt32();
                    CdmaSmsMessage cdmaSmsMessage = new CdmaSmsMessage();
                    cdmaSmsMessage.readFromParcel(hwParcel);
                    cdmaNewSms(readInt325, cdmaSmsMessage);
                    return;
                case 21:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    newBroadcastSms(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 22:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    cdmaRuimSmsStorageFull(hwParcel.readInt32());
                    return;
                case 23:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    restrictedStateChanged(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 24:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    enterEmergencyCallbackMode(hwParcel.readInt32());
                    return;
                case 25:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt326 = hwParcel.readInt32();
                    CdmaCallWaiting cdmaCallWaiting = new CdmaCallWaiting();
                    cdmaCallWaiting.readFromParcel(hwParcel);
                    cdmaCallWaiting(readInt326, cdmaCallWaiting);
                    return;
                case 26:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    cdmaOtaProvisionStatus(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 27:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt327 = hwParcel.readInt32();
                    CdmaInformationRecords cdmaInformationRecords = new CdmaInformationRecords();
                    cdmaInformationRecords.readFromParcel(hwParcel);
                    cdmaInfoRec(readInt327, cdmaInformationRecords);
                    return;
                case 28:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    indicateRingbackTone(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 29:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    resendIncallMute(hwParcel.readInt32());
                    return;
                case 30:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    cdmaSubscriptionSourceChanged(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 31:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    cdmaPrlChanged(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 32:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    exitEmergencyCallbackMode(hwParcel.readInt32());
                    return;
                case 33:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    rilConnected(hwParcel.readInt32());
                    return;
                case 34:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    voiceRadioTechChanged(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 35:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    cellInfoList(hwParcel.readInt32(), CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 36:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    imsNetworkStateChanged(hwParcel.readInt32());
                    return;
                case 37:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    subscriptionStatusChanged(hwParcel.readInt32(), hwParcel.readBool());
                    return;
                case 38:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    srvccStateNotify(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 39:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    hardwareConfigChanged(hwParcel.readInt32(), HardwareConfig.readVectorFromParcel(hwParcel));
                    return;
                case 40:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt328 = hwParcel.readInt32();
                    RadioCapability radioCapability = new RadioCapability();
                    radioCapability.readFromParcel(hwParcel);
                    radioCapabilityIndication(readInt328, radioCapability);
                    return;
                case 41:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt329 = hwParcel.readInt32();
                    StkCcUnsolSsResult stkCcUnsolSsResult = new StkCcUnsolSsResult();
                    stkCcUnsolSsResult.readFromParcel(hwParcel);
                    onSupplementaryServiceIndication(readInt329, stkCcUnsolSsResult);
                    return;
                case 42:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    stkCallControlAlphaNotify(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 43:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt3210 = hwParcel.readInt32();
                    LceDataInfo lceDataInfo = new LceDataInfo();
                    lceDataInfo.readFromParcel(hwParcel);
                    lceData(readInt3210, lceDataInfo);
                    return;
                case 44:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    int readInt3211 = hwParcel.readInt32();
                    PcoDataInfo pcoDataInfo = new PcoDataInfo();
                    pcoDataInfo.readFromParcel(hwParcel);
                    pcoData(readInt3211, pcoDataInfo);
                    return;
                case 45:
                    hwParcel.enforceInterface(IRadioIndication.kInterfaceName);
                    modemReset(hwParcel.readInt32(), hwParcel.readString());
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
