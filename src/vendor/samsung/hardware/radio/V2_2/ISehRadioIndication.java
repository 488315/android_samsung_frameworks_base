package vendor.samsung.hardware.radio.V2_2;

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
import vendor.samsung.hardware.radio.V2_0.ISehRadioIndication;
import vendor.samsung.hardware.radio.V2_0.SehApnProfile;
import vendor.samsung.hardware.radio.V2_0.SehCallDetails;
import vendor.samsung.hardware.radio.V2_0.SehConfigModemCapability;
import vendor.samsung.hardware.radio.V2_0.SehExtendedRegStateResult;
import vendor.samsung.hardware.radio.V2_0.SehPacketUsage;
import vendor.samsung.hardware.radio.V2_0.SehRrcStateInfo;
import vendor.samsung.hardware.radio.V2_0.SehSignalBar;
import vendor.samsung.hardware.radio.V2_0.SehSsReleaseComplete;

/* loaded from: classes6.dex */
public interface ISehRadioIndication extends vendor.samsung.hardware.radio.V2_1.ISehRadioIndication {
    public static final String kInterfaceName = "vendor.samsung.hardware.radio@2.2::ISehRadioIndication";

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    void callDetailsChanged(int i, ArrayList<SehCallDetails> arrayList) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void eriInfoReceived(int i, SehEriInfo sehEriInfo) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void vendorConfigurationChanged(int i, ArrayList<SehVendorConfiguration> arrayList) throws RemoteException;

    static ISehRadioIndication asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof ISehRadioIndication)) {
            return (ISehRadioIndication) iHwInterfaceQueryLocalInterface;
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

    static ISehRadioIndication castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static ISehRadioIndication getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static ISehRadioIndication getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static ISehRadioIndication getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static ISehRadioIndication getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements ISehRadioIndication {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of vendor.samsung.hardware.radio@2.2::ISehRadioIndication]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void acbInfoChanged(int i, ArrayList<Integer> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void csFallback(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void imsPreferenceChanged(int i, ArrayList<Integer> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void voiceRadioBearerHandoverStatusChanged(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void timerStatusChangedInd(int i, ArrayList<Integer> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void modemCapabilityIndication(int i, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void needTurnOnRadioIndication(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void simPhonebookReadyIndication(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void phonebookInitCompleteIndication(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void deviceReadyNoti(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void stkSmsSendResultIndication(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void stkCallControlResultIndication(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
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

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void simSwapStateChangedIndication(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
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

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void simCountMismatchedIndication(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void simOnOffStateChangedNotify(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void releaseCompleteMessageIndication(int i, SehSsReleaseComplete sehSsReleaseComplete) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            sehSsReleaseComplete.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void sapNotify(int i, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void nrBearerAllocationChanged(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void nrNetworkTypeAdded(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void rrcStateChanged(int i, SehRrcStateInfo sehRrcStateInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            sehRrcStateInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void configModemCapabilityChangeNoti(int i, SehConfigModemCapability sehConfigModemCapability) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            sehConfigModemCapability.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public SehApnProfile needApnProfileIndication(String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                SehApnProfile sehApnProfile = new SehApnProfile();
                sehApnProfile.readFromParcel(hwParcel2);
                return sehApnProfile;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public int needSettingValueIndication(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void execute(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void signalLevelInfoChanged(int i, SehSignalBar sehSignalBar) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            sehSignalBar.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void extendedRegistrationState(int i, SehExtendedRegStateResult sehExtendedRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            sehExtendedRegStateResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void needPacketUsage(String str, ISehRadioIndication.needPacketUsageCallback needpacketusagecallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                int int32 = hwParcel2.readInt32();
                SehPacketUsage sehPacketUsage = new SehPacketUsage();
                sehPacketUsage.readFromParcel(hwParcel2);
                needpacketusagecallback.onValues(int32, sehPacketUsage);
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication
        public void nrIconTypeChanged(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_1.ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication
        public void callDetailsChanged(int i, ArrayList<SehCallDetails> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            SehCallDetails.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication
        public void vendorConfigurationChanged(int i, ArrayList<SehVendorConfiguration> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            SehVendorConfiguration.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication
        public void eriInfoReceived(int i, SehEriInfo sehEriInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadioIndication.kInterfaceName);
            hwParcel.writeInt32(i);
            sehEriInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(31, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements ISehRadioIndication {
        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(ISehRadioIndication.kInterfaceName, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication.kInterfaceName, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return ISehRadioIndication.kInterfaceName;
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-47, MidiConstants.STATUS_PROGRAM_CHANGE, 122, -121, -118, 76, -79, -105, 111, -127, 67, 69, -99, 119, 4, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -72, 60, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -28, 2, -110, 26, 4, -1, -75, 118, 114, -101, 122, -65}, new byte[]{-92, 125, -7, -61, 7, 69, 122, -110, 75, MidiConstants.STATUS_PROGRAM_CHANGE, -106, 8, 89, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, -103, 66, -3, -52, -16, 123, 5, -47, -120, -111, -75, -60, -116, 25, -59, 4, -35, -103}, new byte[]{120, 15, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -11, -123, -62, 87, 84, -52, 99, 67, -65, 0, -34, -27, 21, 126, -4, -10, -8, 82, 107, -63, 121, 122, -84, -72, 126, -115, 55, 95, -92}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadioIndication, vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (ISehRadioIndication.kInterfaceName.equals(str)) {
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
        public void onTransact(int i, HwParcel hwParcel, final HwParcel hwParcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    acbInfoChanged(hwParcel.readInt32(), hwParcel.readInt32Vector());
                    return;
                case 2:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    csFallback(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    imsPreferenceChanged(hwParcel.readInt32(), hwParcel.readInt32Vector());
                    return;
                case 4:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    voiceRadioBearerHandoverStatusChanged(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 5:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    timerStatusChangedInd(hwParcel.readInt32(), hwParcel.readInt32Vector());
                    return;
                case 6:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    modemCapabilityIndication(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 7:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    needTurnOnRadioIndication(hwParcel.readInt32());
                    return;
                case 8:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    simPhonebookReadyIndication(hwParcel.readInt32());
                    return;
                case 9:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    phonebookInitCompleteIndication(hwParcel.readInt32());
                    return;
                case 10:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    deviceReadyNoti(hwParcel.readInt32());
                    return;
                case 11:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    stkSmsSendResultIndication(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 12:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    stkCallControlResultIndication(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 13:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    simSwapStateChangedIndication(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 14:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    simCountMismatchedIndication(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 15:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    simOnOffStateChangedNotify(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 16:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int int32 = hwParcel.readInt32();
                    SehSsReleaseComplete sehSsReleaseComplete = new SehSsReleaseComplete();
                    sehSsReleaseComplete.readFromParcel(hwParcel);
                    releaseCompleteMessageIndication(int32, sehSsReleaseComplete);
                    return;
                case 17:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    sapNotify(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 18:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    nrBearerAllocationChanged(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 19:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    nrNetworkTypeAdded(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 20:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int int322 = hwParcel.readInt32();
                    SehRrcStateInfo sehRrcStateInfo = new SehRrcStateInfo();
                    sehRrcStateInfo.readFromParcel(hwParcel);
                    rrcStateChanged(int322, sehRrcStateInfo);
                    return;
                case 21:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int int323 = hwParcel.readInt32();
                    SehConfigModemCapability sehConfigModemCapability = new SehConfigModemCapability();
                    sehConfigModemCapability.readFromParcel(hwParcel);
                    configModemCapabilityChangeNoti(int323, sehConfigModemCapability);
                    return;
                case 22:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    SehApnProfile sehApnProfileNeedApnProfileIndication = needApnProfileIndication(hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    sehApnProfileNeedApnProfileIndication.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 23:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int iNeedSettingValueIndication = needSettingValueIndication(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(iNeedSettingValueIndication);
                    hwParcel2.send();
                    return;
                case 24:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    execute(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 25:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int int324 = hwParcel.readInt32();
                    SehSignalBar sehSignalBar = new SehSignalBar();
                    sehSignalBar.readFromParcel(hwParcel);
                    signalLevelInfoChanged(int324, sehSignalBar);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 26:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int int325 = hwParcel.readInt32();
                    SehExtendedRegStateResult sehExtendedRegStateResult = new SehExtendedRegStateResult();
                    sehExtendedRegStateResult.readFromParcel(hwParcel);
                    extendedRegistrationState(int325, sehExtendedRegStateResult);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 27:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    needPacketUsage(hwParcel.readString(), new ISehRadioIndication.needPacketUsageCallback(this) { // from class: vendor.samsung.hardware.radio.V2_2.ISehRadioIndication.Stub.1
                        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.needPacketUsageCallback
                        public void onValues(int i3, SehPacketUsage sehPacketUsage) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            sehPacketUsage.writeToParcel(hwParcel2);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 28:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_1.ISehRadioIndication.kInterfaceName);
                    nrIconTypeChanged(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 29:
                    hwParcel.enforceInterface(ISehRadioIndication.kInterfaceName);
                    callDetailsChanged(hwParcel.readInt32(), SehCallDetails.readVectorFromParcel(hwParcel));
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 30:
                    hwParcel.enforceInterface(ISehRadioIndication.kInterfaceName);
                    vendorConfigurationChanged(hwParcel.readInt32(), SehVendorConfiguration.readVectorFromParcel(hwParcel));
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 31:
                    hwParcel.enforceInterface(ISehRadioIndication.kInterfaceName);
                    int int326 = hwParcel.readInt32();
                    SehEriInfo sehEriInfo = new SehEriInfo();
                    sehEriInfo.readFromParcel(hwParcel);
                    eriInfoReceived(int326, sehEriInfo);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
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
