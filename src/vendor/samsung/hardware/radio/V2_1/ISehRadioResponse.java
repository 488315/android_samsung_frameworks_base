package vendor.samsung.hardware.radio.V2_1;

import android.hardware.radio.V1_0.RadioResponseInfo;
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
import vendor.samsung.hardware.radio.V2_0.SehCall;
import vendor.samsung.hardware.radio.V2_0.SehCbConfigArgs;
import vendor.samsung.hardware.radio.V2_0.SehCsgInfo;
import vendor.samsung.hardware.radio.V2_0.SehOperatorInfo;
import vendor.samsung.hardware.radio.V2_0.SehPhonebookInfo;
import vendor.samsung.hardware.radio.V2_0.SehPreferredNetworkInfo;
import vendor.samsung.hardware.radio.V2_0.SehSendSmsResult;
import vendor.samsung.hardware.radio.V2_0.SehSimLockInfo;
import vendor.samsung.hardware.radio.V2_0.SehSimMsgArgs;
import vendor.samsung.hardware.radio.V2_0.SehSimPhonebookResponse;
import vendor.samsung.hardware.radio.V2_0.SehStoredMsgCount;

/* loaded from: classes6.dex */
public interface ISehRadioResponse extends vendor.samsung.hardware.radio.V2_0.ISehRadioResponse {
    public static final String kInterfaceName = "vendor.samsung.hardware.radio@2.1::ISehRadioResponse";

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getIccCardStatusResponse_2_1(RadioResponseInfo radioResponseInfo, SehCardStatus sehCardStatus) throws RemoteException;

    void getNrIconTypeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    void getNrModeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    void setNrModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static ISehRadioResponse asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof ISehRadioResponse)) {
            return (ISehRadioResponse) iHwInterfaceQueryLocalInterface;
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

    static ISehRadioResponse castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static ISehRadioResponse getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static ISehRadioResponse getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static ISehRadioResponse getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static ISehRadioResponse getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements ISehRadioResponse {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of vendor.samsung.hardware.radio@2.1::ISehRadioResponse]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getIccCardStatusResponse(RadioResponseInfo radioResponseInfo, vendor.samsung.hardware.radio.V2_0.SehCardStatus sehCardStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehCardStatus.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void supplyNetworkDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
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

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void dialResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getCurrentCallsResponse(RadioResponseInfo radioResponseInfo, ArrayList<SehCall> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            SehCall.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getImsRegistrationStateResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getAvailableNetworksResponse(RadioResponseInfo radioResponseInfo, ArrayList<SehOperatorInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            SehOperatorInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void setImsCallListResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getPreferredNetworkListResponse(RadioResponseInfo radioResponseInfo, ArrayList<SehPreferredNetworkInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            SehPreferredNetworkInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void setPreferredNetworkListResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void sendEncodedUssdResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getDisable2gResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void setDisable2gResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getCnapResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getPhonebookStorageInfoResponse(RadioResponseInfo radioResponseInfo, SehPhonebookInfo sehPhonebookInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehPhonebookInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getUsimPhonebookCapabilityResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void setSimOnOffResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void setSimInitEventResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getSimLockInfoResponse(RadioResponseInfo radioResponseInfo, SehSimLockInfo sehSimLockInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehSimLockInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void supplyIccPersonalizationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void changeIccPersonalizationResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getPhonebookEntryResponse(RadioResponseInfo radioResponseInfo, SehSimPhonebookResponse sehSimPhonebookResponse) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehSimPhonebookResponse.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void accessPhonebookEntryResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getCellBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, SehCbConfigArgs sehCbConfigArgs) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehCbConfigArgs.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void emergencySearchResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void emergencyControlResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getAtrResponse(RadioResponseInfo radioResponseInfo, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void sendCdmaSmsExpectMoreResponse(RadioResponseInfo radioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehSendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void sendSmsResponse(RadioResponseInfo radioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehSendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void sendSMSExpectMoreResponse(RadioResponseInfo radioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehSendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void sendCdmaSmsResponse(RadioResponseInfo radioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehSendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void sendImsSmsResponse(RadioResponseInfo radioResponseInfo, SehSendSmsResult sehSendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehSendSmsResult.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(31, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getStoredMsgCountFromSimResponse(RadioResponseInfo radioResponseInfo, SehStoredMsgCount sehStoredMsgCount) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehStoredMsgCount.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(32, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void readSmsFromSimResponse(RadioResponseInfo radioResponseInfo, SehSimMsgArgs sehSimMsgArgs) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehSimMsgArgs.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(33, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void writeSmsToSimResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(34, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void setDataAllowedResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(35, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void getCsgListResponse(RadioResponseInfo radioResponseInfo, ArrayList<SehCsgInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            SehCsgInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(36, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void selectCsgManualResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(37, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void setMobileDataSettingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(38, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void sendRequestRawResponse(RadioResponseInfo radioResponseInfo, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(39, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioResponse
        public void sendRequestStringsResponse(RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeStringVector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(40, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse
        public void setNrModeResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(41, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse
        public void getNrModeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(42, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse
        public void getNrIconTypeResponse(RadioResponseInfo radioResponseInfo, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(43, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse
        public void getIccCardStatusResponse_2_1(RadioResponseInfo radioResponseInfo, SehCardStatus sehCardStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISehRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sehCardStatus.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(44, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements ISehRadioResponse {
        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(ISehRadioResponse.kInterfaceName, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return ISehRadioResponse.kInterfaceName;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{82, -116, 16, -3, -40, 72, MidiConstants.STATUS_SONG_SELECT, -31, -2, 55, -90, -118, -3, 0, 87, 85, SprAttributeBase.TYPE_ANIMATOR_SET, MidiConstants.STATUS_SONG_SELECT, 80, -107, 1, 106, 38, 78, -73, -49, 20, -123, 101, -108, -69, -117}, new byte[]{-109, -98, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, -18, -101, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -71, MidiConstants.STATUS_CHANNEL_PRESSURE, 68, MidiConstants.STATUS_SONG_SELECT, 66, 95, 109, -67, -27, -119, 107, -59, SprAttributeBase.TYPE_ANIMATOR_SET, 21, 81, 38, 85, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 37, 122, 14, 6, -34, 1, MidiConstants.STATUS_PITCH_BEND, -110}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioResponse, vendor.samsung.hardware.radio.V2_0.ISehRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (ISehRadioResponse.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel(hwParcel);
                    vendor.samsung.hardware.radio.V2_0.SehCardStatus sehCardStatus = new vendor.samsung.hardware.radio.V2_0.SehCardStatus();
                    sehCardStatus.readFromParcel(hwParcel);
                    getIccCardStatusResponse(radioResponseInfo, sehCardStatus);
                    return;
                case 2:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo2 = new RadioResponseInfo();
                    radioResponseInfo2.readFromParcel(hwParcel);
                    supplyNetworkDepersonalizationResponse(radioResponseInfo2, hwParcel.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo3 = new RadioResponseInfo();
                    radioResponseInfo3.readFromParcel(hwParcel);
                    dialResponse(radioResponseInfo3);
                    return;
                case 4:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo4 = new RadioResponseInfo();
                    radioResponseInfo4.readFromParcel(hwParcel);
                    getCurrentCallsResponse(radioResponseInfo4, SehCall.readVectorFromParcel(hwParcel));
                    return;
                case 5:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo5 = new RadioResponseInfo();
                    radioResponseInfo5.readFromParcel(hwParcel);
                    getImsRegistrationStateResponse(radioResponseInfo5, hwParcel.readInt32Vector());
                    return;
                case 6:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo6 = new RadioResponseInfo();
                    radioResponseInfo6.readFromParcel(hwParcel);
                    getAvailableNetworksResponse(radioResponseInfo6, SehOperatorInfo.readVectorFromParcel(hwParcel));
                    return;
                case 7:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo7 = new RadioResponseInfo();
                    radioResponseInfo7.readFromParcel(hwParcel);
                    setImsCallListResponse(radioResponseInfo7);
                    return;
                case 8:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo8 = new RadioResponseInfo();
                    radioResponseInfo8.readFromParcel(hwParcel);
                    getPreferredNetworkListResponse(radioResponseInfo8, SehPreferredNetworkInfo.readVectorFromParcel(hwParcel));
                    return;
                case 9:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo9 = new RadioResponseInfo();
                    radioResponseInfo9.readFromParcel(hwParcel);
                    setPreferredNetworkListResponse(radioResponseInfo9);
                    return;
                case 10:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo10 = new RadioResponseInfo();
                    radioResponseInfo10.readFromParcel(hwParcel);
                    sendEncodedUssdResponse(radioResponseInfo10);
                    return;
                case 11:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo11 = new RadioResponseInfo();
                    radioResponseInfo11.readFromParcel(hwParcel);
                    getDisable2gResponse(radioResponseInfo11, hwParcel.readInt32());
                    return;
                case 12:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo12 = new RadioResponseInfo();
                    radioResponseInfo12.readFromParcel(hwParcel);
                    setDisable2gResponse(radioResponseInfo12);
                    return;
                case 13:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo13 = new RadioResponseInfo();
                    radioResponseInfo13.readFromParcel(hwParcel);
                    getCnapResponse(radioResponseInfo13, hwParcel.readInt32());
                    return;
                case 14:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo14 = new RadioResponseInfo();
                    radioResponseInfo14.readFromParcel(hwParcel);
                    SehPhonebookInfo sehPhonebookInfo = new SehPhonebookInfo();
                    sehPhonebookInfo.readFromParcel(hwParcel);
                    getPhonebookStorageInfoResponse(radioResponseInfo14, sehPhonebookInfo);
                    return;
                case 15:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo15 = new RadioResponseInfo();
                    radioResponseInfo15.readFromParcel(hwParcel);
                    getUsimPhonebookCapabilityResponse(radioResponseInfo15, hwParcel.readInt32Vector());
                    return;
                case 16:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo16 = new RadioResponseInfo();
                    radioResponseInfo16.readFromParcel(hwParcel);
                    setSimOnOffResponse(radioResponseInfo16);
                    return;
                case 17:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo17 = new RadioResponseInfo();
                    radioResponseInfo17.readFromParcel(hwParcel);
                    setSimInitEventResponse(radioResponseInfo17);
                    return;
                case 18:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo18 = new RadioResponseInfo();
                    radioResponseInfo18.readFromParcel(hwParcel);
                    SehSimLockInfo sehSimLockInfo = new SehSimLockInfo();
                    sehSimLockInfo.readFromParcel(hwParcel);
                    getSimLockInfoResponse(radioResponseInfo18, sehSimLockInfo);
                    return;
                case 19:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo19 = new RadioResponseInfo();
                    radioResponseInfo19.readFromParcel(hwParcel);
                    supplyIccPersonalizationResponse(radioResponseInfo19);
                    return;
                case 20:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo20 = new RadioResponseInfo();
                    radioResponseInfo20.readFromParcel(hwParcel);
                    changeIccPersonalizationResponse(radioResponseInfo20);
                    return;
                case 21:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo21 = new RadioResponseInfo();
                    radioResponseInfo21.readFromParcel(hwParcel);
                    SehSimPhonebookResponse sehSimPhonebookResponse = new SehSimPhonebookResponse();
                    sehSimPhonebookResponse.readFromParcel(hwParcel);
                    getPhonebookEntryResponse(radioResponseInfo21, sehSimPhonebookResponse);
                    return;
                case 22:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo22 = new RadioResponseInfo();
                    radioResponseInfo22.readFromParcel(hwParcel);
                    accessPhonebookEntryResponse(radioResponseInfo22, hwParcel.readInt32());
                    return;
                case 23:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo23 = new RadioResponseInfo();
                    radioResponseInfo23.readFromParcel(hwParcel);
                    SehCbConfigArgs sehCbConfigArgs = new SehCbConfigArgs();
                    sehCbConfigArgs.readFromParcel(hwParcel);
                    getCellBroadcastConfigResponse(radioResponseInfo23, sehCbConfigArgs);
                    return;
                case 24:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo24 = new RadioResponseInfo();
                    radioResponseInfo24.readFromParcel(hwParcel);
                    emergencySearchResponse(radioResponseInfo24, hwParcel.readInt32());
                    return;
                case 25:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo25 = new RadioResponseInfo();
                    radioResponseInfo25.readFromParcel(hwParcel);
                    emergencyControlResponse(radioResponseInfo25);
                    return;
                case 26:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo26 = new RadioResponseInfo();
                    radioResponseInfo26.readFromParcel(hwParcel);
                    getAtrResponse(radioResponseInfo26, hwParcel.readString());
                    return;
                case 27:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo27 = new RadioResponseInfo();
                    radioResponseInfo27.readFromParcel(hwParcel);
                    SehSendSmsResult sehSendSmsResult = new SehSendSmsResult();
                    sehSendSmsResult.readFromParcel(hwParcel);
                    sendCdmaSmsExpectMoreResponse(radioResponseInfo27, sehSendSmsResult);
                    return;
                case 28:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo28 = new RadioResponseInfo();
                    radioResponseInfo28.readFromParcel(hwParcel);
                    SehSendSmsResult sehSendSmsResult2 = new SehSendSmsResult();
                    sehSendSmsResult2.readFromParcel(hwParcel);
                    sendSmsResponse(radioResponseInfo28, sehSendSmsResult2);
                    return;
                case 29:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo29 = new RadioResponseInfo();
                    radioResponseInfo29.readFromParcel(hwParcel);
                    SehSendSmsResult sehSendSmsResult3 = new SehSendSmsResult();
                    sehSendSmsResult3.readFromParcel(hwParcel);
                    sendSMSExpectMoreResponse(radioResponseInfo29, sehSendSmsResult3);
                    return;
                case 30:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo30 = new RadioResponseInfo();
                    radioResponseInfo30.readFromParcel(hwParcel);
                    SehSendSmsResult sehSendSmsResult4 = new SehSendSmsResult();
                    sehSendSmsResult4.readFromParcel(hwParcel);
                    sendCdmaSmsResponse(radioResponseInfo30, sehSendSmsResult4);
                    return;
                case 31:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo31 = new RadioResponseInfo();
                    radioResponseInfo31.readFromParcel(hwParcel);
                    SehSendSmsResult sehSendSmsResult5 = new SehSendSmsResult();
                    sehSendSmsResult5.readFromParcel(hwParcel);
                    sendImsSmsResponse(radioResponseInfo31, sehSendSmsResult5);
                    return;
                case 32:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo32 = new RadioResponseInfo();
                    radioResponseInfo32.readFromParcel(hwParcel);
                    SehStoredMsgCount sehStoredMsgCount = new SehStoredMsgCount();
                    sehStoredMsgCount.readFromParcel(hwParcel);
                    getStoredMsgCountFromSimResponse(radioResponseInfo32, sehStoredMsgCount);
                    return;
                case 33:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo33 = new RadioResponseInfo();
                    radioResponseInfo33.readFromParcel(hwParcel);
                    SehSimMsgArgs sehSimMsgArgs = new SehSimMsgArgs();
                    sehSimMsgArgs.readFromParcel(hwParcel);
                    readSmsFromSimResponse(radioResponseInfo33, sehSimMsgArgs);
                    return;
                case 34:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo34 = new RadioResponseInfo();
                    radioResponseInfo34.readFromParcel(hwParcel);
                    writeSmsToSimResponse(radioResponseInfo34, hwParcel.readInt32());
                    return;
                case 35:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo35 = new RadioResponseInfo();
                    radioResponseInfo35.readFromParcel(hwParcel);
                    setDataAllowedResponse(radioResponseInfo35);
                    return;
                case 36:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo36 = new RadioResponseInfo();
                    radioResponseInfo36.readFromParcel(hwParcel);
                    getCsgListResponse(radioResponseInfo36, SehCsgInfo.readVectorFromParcel(hwParcel));
                    return;
                case 37:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo37 = new RadioResponseInfo();
                    radioResponseInfo37.readFromParcel(hwParcel);
                    selectCsgManualResponse(radioResponseInfo37);
                    return;
                case 38:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo38 = new RadioResponseInfo();
                    radioResponseInfo38.readFromParcel(hwParcel);
                    setMobileDataSettingResponse(radioResponseInfo38);
                    return;
                case 39:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo39 = new RadioResponseInfo();
                    radioResponseInfo39.readFromParcel(hwParcel);
                    sendRequestRawResponse(radioResponseInfo39, hwParcel.readInt8Vector());
                    return;
                case 40:
                    hwParcel.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo40 = new RadioResponseInfo();
                    radioResponseInfo40.readFromParcel(hwParcel);
                    sendRequestStringsResponse(radioResponseInfo40, hwParcel.readStringVector());
                    return;
                case 41:
                    hwParcel.enforceInterface(ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo41 = new RadioResponseInfo();
                    radioResponseInfo41.readFromParcel(hwParcel);
                    setNrModeResponse(radioResponseInfo41);
                    return;
                case 42:
                    hwParcel.enforceInterface(ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo42 = new RadioResponseInfo();
                    radioResponseInfo42.readFromParcel(hwParcel);
                    getNrModeResponse(radioResponseInfo42, hwParcel.readInt32());
                    return;
                case 43:
                    hwParcel.enforceInterface(ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo43 = new RadioResponseInfo();
                    radioResponseInfo43.readFromParcel(hwParcel);
                    getNrIconTypeResponse(radioResponseInfo43, hwParcel.readInt32());
                    return;
                case 44:
                    hwParcel.enforceInterface(ISehRadioResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo44 = new RadioResponseInfo();
                    radioResponseInfo44.readFromParcel(hwParcel);
                    SehCardStatus sehCardStatus2 = new SehCardStatus();
                    sehCardStatus2.readFromParcel(hwParcel);
                    getIccCardStatusResponse_2_1(radioResponseInfo44, sehCardStatus2);
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
