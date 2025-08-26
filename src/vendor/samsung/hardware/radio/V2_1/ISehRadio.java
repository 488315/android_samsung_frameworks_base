package vendor.samsung.hardware.radio.V2_1;

import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.GsmSmsMessage;
import android.hardware.radio.V1_0.ImsSmsMessage;
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
import vendor.samsung.hardware.radio.V2_0.SehAdnRecord;
import vendor.samsung.hardware.radio.V2_0.SehAllowDataParam;
import vendor.samsung.hardware.radio.V2_0.SehCsgInfo;
import vendor.samsung.hardware.radio.V2_0.SehDial;
import vendor.samsung.hardware.radio.V2_0.SehEncodedUssd;
import vendor.samsung.hardware.radio.V2_0.SehImsCall;
import vendor.samsung.hardware.radio.V2_0.SehPreferredNetworkInfo;
import vendor.samsung.hardware.radio.V2_0.SehSimMsgArgs;

/* loaded from: classes6.dex */
public interface ISehRadio extends vendor.samsung.hardware.radio.V2_0.ISehRadio {
    public static final String kInterfaceName = "vendor.samsung.hardware.radio@2.1::ISehRadio";

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getNrIconType(int i) throws RemoteException;

    void getNrMode(int i) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    void setNrMode(int i, int i2) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static ISehRadio asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof ISehRadio)) {
            return (ISehRadio) iHwInterfaceQueryLocalInterface;
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

    static ISehRadio castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static ISehRadio getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static ISehRadio getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static ISehRadio getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static ISehRadio getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements ISehRadio {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of vendor.samsung.hardware.radio@2.1::ISehRadio]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setResponseFunctions(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse iSehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication iSehRadioIndication) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeStrongBinder(iSehRadioResponse == null ? null : iSehRadioResponse.asBinder());
            hwParcel.writeStrongBinder(iSehRadioIndication != null ? iSehRadioIndication.asBinder() : null);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getIccCardStatus(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void supplyNetworkDepersonalization(int i, String str, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void dial(int i, SehDial sehDial) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            sehDial.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getCurrentCalls(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getImsRegistrationState(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getAvailableNetworks(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setImsCallList(int i, ArrayList<SehImsCall> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            SehImsCall.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getPreferredNetworkList(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setPreferredNetworkList(int i, SehPreferredNetworkInfo sehPreferredNetworkInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            sehPreferredNetworkInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendEncodedUssd(int i, SehEncodedUssd sehEncodedUssd) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            sehEncodedUssd.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getDisable2g(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setDisable2g(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
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

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getCnap(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getPhonebookStorageInfo(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
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

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getUsimPhonebookCapability(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setSimOnOff(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setSimInitEvent(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getSimLockInfo(int i, int i2, int i3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void supplyIccPersonalization(int i, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void changeIccPersonalization(int i, String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendCdmaSmsExpectMore(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getPhonebookEntry(int i, int i2, int i3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void accessPhonebookEntry(int i, int i2, int i3, int i4, SehAdnRecord sehAdnRecord, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            hwParcel.writeInt32(i4);
            sehAdnRecord.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getCellBroadcastConfig(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void emergencySearch(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void emergencyControl(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getAtr(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendSms(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            gsmSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendSMSExpectMore(int i, GsmSmsMessage gsmSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            gsmSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendCdmaSms(int i, CdmaSmsMessage cdmaSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            cdmaSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(31, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendImsSms(int i, ImsSmsMessage imsSmsMessage) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            imsSmsMessage.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(32, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getStoredMsgCountFromSim(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(33, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void readSmsFromSim(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
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

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void writeSmsToSim(int i, SehSimMsgArgs sehSimMsgArgs) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            sehSimMsgArgs.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(35, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getCsgList(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(36, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void selectCsgManual(int i, SehCsgInfo sehCsgInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            sehCsgInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(37, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setDataAllowed(int i, boolean z, SehAllowDataParam sehAllowDataParam) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            sehAllowDataParam.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(38, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setMobileDataSetting(int i, boolean z, boolean z2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(39, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendRequestRaw(int i, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(40, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendRequestStrings(int i, ArrayList<String> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeStringVector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(41, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio
        public void setNrMode(int i, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(42, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio
        public void getNrMode(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(43, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio
        public void getNrIconType(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadio.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(44, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements ISehRadio {
        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(ISehRadio.kInterfaceName, vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return ISehRadio.kInterfaceName;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{6, -89, 104, 126, 2, 15, -126, 8, -26, MidiConstants.STATUS_SONG_POSITION, 9, -9, -97, -62, -60, -5, 105, -21, 18, 14, -127, -26, -83, -6, -21, 26, -69, -110, -84, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -83, -113}, new byte[]{104, 75, -90, 79, 123, -108, 12, 62, 76, MidiConstants.STATUS_SONG_SELECT, 109, -52, 94, 108, 17, Byte.MIN_VALUE, -70, -93, 91, -108, -34, -100, MidiConstants.STATUS_CHANNEL_PRESSURE, -63, -28, -120, 93, 15, 32, -46, 125, 107}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio, vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (ISehRadio.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    setResponseFunctions(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.asInterface(hwParcel.readStrongBinder()), vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getIccCardStatus(hwParcel.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    supplyNetworkDepersonalization(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readInt32());
                    return;
                case 4:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int32 = hwParcel.readInt32();
                    SehDial sehDial = new SehDial();
                    sehDial.readFromParcel(hwParcel);
                    dial(int32, sehDial);
                    return;
                case 5:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getCurrentCalls(hwParcel.readInt32());
                    return;
                case 6:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getImsRegistrationState(hwParcel.readInt32());
                    return;
                case 7:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getAvailableNetworks(hwParcel.readInt32());
                    return;
                case 8:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    setImsCallList(hwParcel.readInt32(), SehImsCall.readVectorFromParcel(hwParcel));
                    return;
                case 9:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getPreferredNetworkList(hwParcel.readInt32());
                    return;
                case 10:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int322 = hwParcel.readInt32();
                    SehPreferredNetworkInfo sehPreferredNetworkInfo = new SehPreferredNetworkInfo();
                    sehPreferredNetworkInfo.readFromParcel(hwParcel);
                    setPreferredNetworkList(int322, sehPreferredNetworkInfo);
                    return;
                case 11:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int323 = hwParcel.readInt32();
                    SehEncodedUssd sehEncodedUssd = new SehEncodedUssd();
                    sehEncodedUssd.readFromParcel(hwParcel);
                    sendEncodedUssd(int323, sehEncodedUssd);
                    return;
                case 12:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getDisable2g(hwParcel.readInt32());
                    return;
                case 13:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    setDisable2g(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 14:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getCnap(hwParcel.readInt32());
                    return;
                case 15:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getPhonebookStorageInfo(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 16:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getUsimPhonebookCapability(hwParcel.readInt32());
                    return;
                case 17:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    setSimOnOff(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 18:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    setSimInitEvent(hwParcel.readInt32());
                    return;
                case 19:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getSimLockInfo(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 20:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    supplyIccPersonalization(hwParcel.readInt32(), hwParcel.readString());
                    return;
                case 21:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    changeIccPersonalization(hwParcel.readInt32(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 22:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int324 = hwParcel.readInt32();
                    CdmaSmsMessage cdmaSmsMessage = new CdmaSmsMessage();
                    cdmaSmsMessage.readFromParcel(hwParcel);
                    sendCdmaSmsExpectMore(int324, cdmaSmsMessage);
                    return;
                case 23:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getPhonebookEntry(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 24:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int325 = hwParcel.readInt32();
                    int int326 = hwParcel.readInt32();
                    int int327 = hwParcel.readInt32();
                    int int328 = hwParcel.readInt32();
                    SehAdnRecord sehAdnRecord = new SehAdnRecord();
                    sehAdnRecord.readFromParcel(hwParcel);
                    accessPhonebookEntry(int325, int326, int327, int328, sehAdnRecord, hwParcel.readString());
                    return;
                case 25:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getCellBroadcastConfig(hwParcel.readInt32());
                    return;
                case 26:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    emergencySearch(hwParcel.readInt32());
                    return;
                case 27:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    emergencyControl(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 28:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getAtr(hwParcel.readInt32());
                    return;
                case 29:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int329 = hwParcel.readInt32();
                    GsmSmsMessage gsmSmsMessage = new GsmSmsMessage();
                    gsmSmsMessage.readFromParcel(hwParcel);
                    sendSms(int329, gsmSmsMessage);
                    return;
                case 30:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int3210 = hwParcel.readInt32();
                    GsmSmsMessage gsmSmsMessage2 = new GsmSmsMessage();
                    gsmSmsMessage2.readFromParcel(hwParcel);
                    sendSMSExpectMore(int3210, gsmSmsMessage2);
                    return;
                case 31:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int3211 = hwParcel.readInt32();
                    CdmaSmsMessage cdmaSmsMessage2 = new CdmaSmsMessage();
                    cdmaSmsMessage2.readFromParcel(hwParcel);
                    sendCdmaSms(int3211, cdmaSmsMessage2);
                    return;
                case 32:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int3212 = hwParcel.readInt32();
                    ImsSmsMessage imsSmsMessage = new ImsSmsMessage();
                    imsSmsMessage.readFromParcel(hwParcel);
                    sendImsSms(int3212, imsSmsMessage);
                    return;
                case 33:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getStoredMsgCountFromSim(hwParcel.readInt32());
                    return;
                case 34:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    readSmsFromSim(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 35:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int3213 = hwParcel.readInt32();
                    SehSimMsgArgs sehSimMsgArgs = new SehSimMsgArgs();
                    sehSimMsgArgs.readFromParcel(hwParcel);
                    writeSmsToSim(int3213, sehSimMsgArgs);
                    return;
                case 36:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    getCsgList(hwParcel.readInt32());
                    return;
                case 37:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int3214 = hwParcel.readInt32();
                    SehCsgInfo sehCsgInfo = new SehCsgInfo();
                    sehCsgInfo.readFromParcel(hwParcel);
                    selectCsgManual(int3214, sehCsgInfo);
                    return;
                case 38:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int int3215 = hwParcel.readInt32();
                    boolean bool = hwParcel.readBool();
                    SehAllowDataParam sehAllowDataParam = new SehAllowDataParam();
                    sehAllowDataParam.readFromParcel(hwParcel);
                    setDataAllowed(int3215, bool, sehAllowDataParam);
                    return;
                case 39:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    setMobileDataSetting(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readBool());
                    return;
                case 40:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    sendRequestRaw(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 41:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    sendRequestStrings(hwParcel.readInt32(), hwParcel.readStringVector());
                    return;
                case 42:
                    hwParcel.enforceInterface(ISehRadio.kInterfaceName);
                    setNrMode(hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 43:
                    hwParcel.enforceInterface(ISehRadio.kInterfaceName);
                    getNrMode(hwParcel.readInt32());
                    return;
                case 44:
                    hwParcel.enforceInterface(ISehRadio.kInterfaceName);
                    getNrIconType(hwParcel.readInt32());
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
