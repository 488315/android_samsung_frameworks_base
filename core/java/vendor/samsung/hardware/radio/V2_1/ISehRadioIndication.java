package vendor.samsung.hardware.radio.V2_1;

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
import vendor.samsung.hardware.radio.V2_0.SehConfigModemCapability;
import vendor.samsung.hardware.radio.V2_0.SehExtendedRegStateResult;
import vendor.samsung.hardware.radio.V2_0.SehPacketUsage;
import vendor.samsung.hardware.radio.V2_0.SehRrcStateInfo;
import vendor.samsung.hardware.radio.V2_0.SehSignalBar;
import vendor.samsung.hardware.radio.V2_0.SehSsReleaseComplete;

/* loaded from: classes6.dex */
public interface ISehRadioIndication extends vendor.samsung.hardware.radio.V2_0.ISehRadioIndication {
    public static final String kInterfaceName = "vendor.samsung.hardware.radio@2.1::ISehRadioIndication";

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    void nrIconTypeChanged(int i, int i2) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static ISehRadioIndication asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof ISehRadioIndication)) {
            return (ISehRadioIndication) iface;
        }
        ISehRadioIndication proxy = new Proxy(binder);
        try {
            Iterator<String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                String descriptor = it.next();
                if (descriptor.equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException e) {
        }
        return null;
    }

    static ISehRadioIndication castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static ISehRadioIndication getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static ISehRadioIndication getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static ISehRadioIndication getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static ISehRadioIndication getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements ISehRadioIndication {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(remote);
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of vendor.samsung.hardware.radio@2.1::ISehRadioIndication]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void acbInfoChanged(int type, ArrayList<Integer> acbInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32Vector(acbInfo);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void csFallback(int type, int state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void imsPreferenceChanged(int type, ArrayList<Integer> imsPref) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32Vector(imsPref);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void voiceRadioBearerHandoverStatusChanged(int type, int state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void timerStatusChangedInd(int type, ArrayList<Integer> eventNoti) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32Vector(eventNoti);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void modemCapabilityIndication(int type, ArrayList<Byte> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt8Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void needTurnOnRadioIndication(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void simPhonebookReadyIndication(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void phonebookInitCompleteIndication(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void deviceReadyNoti(int type) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void stkSmsSendResultIndication(int type, int result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(result);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void stkCallControlResultIndication(int type, String cmd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(cmd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void simSwapStateChangedIndication(int type, int state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void simCountMismatchedIndication(int type, int state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(state);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void simOnOffStateChangedNotify(int type, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void releaseCompleteMessageIndication(int type, SehSsReleaseComplete result) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            result.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void sapNotify(int type, ArrayList<Byte> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt8Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void nrBearerAllocationChanged(int type, int status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void nrNetworkTypeAdded(int type, int status) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(status);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void rrcStateChanged(int type, SehRrcStateInfo state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            state.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void configModemCapabilityChangeNoti(int type, SehConfigModemCapability configModemCapa) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            configModemCapa.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public SehApnProfile needApnProfileIndication(String select) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeString(select);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                SehApnProfile _hidl_out_apnProf = new SehApnProfile();
                _hidl_out_apnProf.readFromParcel(_hidl_reply);
                return _hidl_out_apnProf;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public int needSettingValueIndication(String key, String table) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeString(key);
            _hidl_request.writeString(table);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_value = _hidl_reply.readInt32();
                return _hidl_out_value;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void execute(int type, String cmd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeString(cmd);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void signalLevelInfoChanged(int type, SehSignalBar signalBarInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            signalBarInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void extendedRegistrationState(int type, SehExtendedRegStateResult state) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            state.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication
        public void needPacketUsage(String iface, ISehRadioIndication.needPacketUsageCallback _hidl_cb) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
            _hidl_request.writeString(iface);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_error = _hidl_reply.readInt32();
                SehPacketUsage _hidl_out_usage = new SehPacketUsage();
                _hidl_out_usage.readFromParcel(_hidl_reply);
                _hidl_cb.onValues(_hidl_out_error, _hidl_out_usage);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication
        public void nrIconTypeChanged(int type, int nrIconType) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehRadioIndication.kInterfaceName);
            _hidl_request.writeInt32(type);
            _hidl_request.writeInt32(nrIconType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public ArrayList<String> interfaceChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256067662, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ArrayList<String> _hidl_out_descriptors = _hidl_reply.readStringVector();
                return _hidl_out_descriptors;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            _hidl_request.writeNativeHandle(fd);
            _hidl_request.writeStringVector(options);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256131655, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public String interfaceDescriptor() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256136003, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                String _hidl_out_descriptor = _hidl_reply.readString();
                return _hidl_out_descriptor;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256398152, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                ArrayList<byte[]> _hidl_out_hashchain = new ArrayList<>();
                HwBlob _hidl_blob = _hidl_reply.readBuffer(16L);
                int _hidl_vec_size = _hidl_blob.getInt32(8L);
                HwBlob childBlob = _hidl_reply.readEmbeddedBuffer(_hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
                _hidl_out_hashchain.clear();
                for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                    byte[] _hidl_vec_element = new byte[32];
                    long _hidl_array_offset_1 = _hidl_index_0 * 32;
                    childBlob.copyToInt8Array(_hidl_array_offset_1, _hidl_vec_element, 32);
                    _hidl_out_hashchain.add(_hidl_vec_element);
                }
                return _hidl_out_hashchain;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256462420, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public void ping() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(256921159, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public DebugInfo getDebugInfo() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257049926, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                DebugInfo _hidl_out_info = new DebugInfo();
                _hidl_out_info.readFromParcel(_hidl_reply);
                return _hidl_out_info;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(257120595, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    public static abstract class Stub extends HwBinder implements ISehRadioIndication {
        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(ISehRadioIndication.kInterfaceName, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) {
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return ISehRadioIndication.kInterfaceName;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-92, 125, -7, -61, 7, 69, 122, -110, 75, MidiConstants.STATUS_PROGRAM_CHANGE, -106, 8, 89, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, -103, 66, -3, -52, -16, 123, 5, -47, -120, -111, -75, -60, -116, 25, -59, 4, -35, -103}, new byte[]{120, 15, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -11, -123, -62, 87, 84, -52, 99, 67, -65, 0, -34, -27, 21, 126, -4, -10, -8, 82, 107, -63, 121, 122, -84, -72, 126, -115, 55, 95, -92}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0L;
            info.arch = 0;
            return info;
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadioIndication, vendor.samsung.hardware.radio.V2_0.ISehRadioIndication, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String descriptor) {
            if (ISehRadioIndication.kInterfaceName.equals(descriptor)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String serviceName) throws RemoteException {
            registerService(serviceName);
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        @Override // android.os.HwBinder
        public void onTransact(int _hidl_code, HwParcel _hidl_request, final HwParcel _hidl_reply, int _hidl_flags) throws RemoteException {
            switch (_hidl_code) {
                case 1:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type = _hidl_request.readInt32();
                    ArrayList<Integer> acbInfo = _hidl_request.readInt32Vector();
                    acbInfoChanged(type, acbInfo);
                    return;
                case 2:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type2 = _hidl_request.readInt32();
                    int state = _hidl_request.readInt32();
                    csFallback(type2, state);
                    return;
                case 3:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type3 = _hidl_request.readInt32();
                    ArrayList<Integer> imsPref = _hidl_request.readInt32Vector();
                    imsPreferenceChanged(type3, imsPref);
                    return;
                case 4:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type4 = _hidl_request.readInt32();
                    int state2 = _hidl_request.readInt32();
                    voiceRadioBearerHandoverStatusChanged(type4, state2);
                    return;
                case 5:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type5 = _hidl_request.readInt32();
                    ArrayList<Integer> eventNoti = _hidl_request.readInt32Vector();
                    timerStatusChangedInd(type5, eventNoti);
                    return;
                case 6:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type6 = _hidl_request.readInt32();
                    ArrayList<Byte> data = _hidl_request.readInt8Vector();
                    modemCapabilityIndication(type6, data);
                    return;
                case 7:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type7 = _hidl_request.readInt32();
                    needTurnOnRadioIndication(type7);
                    return;
                case 8:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type8 = _hidl_request.readInt32();
                    simPhonebookReadyIndication(type8);
                    return;
                case 9:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type9 = _hidl_request.readInt32();
                    phonebookInitCompleteIndication(type9);
                    return;
                case 10:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type10 = _hidl_request.readInt32();
                    deviceReadyNoti(type10);
                    return;
                case 11:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type11 = _hidl_request.readInt32();
                    stkSmsSendResultIndication(type11, _hidl_request.readInt32());
                    return;
                case 12:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type12 = _hidl_request.readInt32();
                    String cmd = _hidl_request.readString();
                    stkCallControlResultIndication(type12, cmd);
                    return;
                case 13:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type13 = _hidl_request.readInt32();
                    int state3 = _hidl_request.readInt32();
                    simSwapStateChangedIndication(type13, state3);
                    return;
                case 14:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type14 = _hidl_request.readInt32();
                    int state4 = _hidl_request.readInt32();
                    simCountMismatchedIndication(type14, state4);
                    return;
                case 15:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type15 = _hidl_request.readInt32();
                    int mode = _hidl_request.readInt32();
                    simOnOffStateChangedNotify(type15, mode);
                    return;
                case 16:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type16 = _hidl_request.readInt32();
                    SehSsReleaseComplete result = new SehSsReleaseComplete();
                    result.readFromParcel(_hidl_request);
                    releaseCompleteMessageIndication(type16, result);
                    return;
                case 17:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type17 = _hidl_request.readInt32();
                    ArrayList<Byte> data2 = _hidl_request.readInt8Vector();
                    sapNotify(type17, data2);
                    return;
                case 18:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type18 = _hidl_request.readInt32();
                    int status = _hidl_request.readInt32();
                    nrBearerAllocationChanged(type18, status);
                    return;
                case 19:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type19 = _hidl_request.readInt32();
                    int status2 = _hidl_request.readInt32();
                    nrNetworkTypeAdded(type19, status2);
                    return;
                case 20:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type20 = _hidl_request.readInt32();
                    SehRrcStateInfo state5 = new SehRrcStateInfo();
                    state5.readFromParcel(_hidl_request);
                    rrcStateChanged(type20, state5);
                    return;
                case 21:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type21 = _hidl_request.readInt32();
                    SehConfigModemCapability configModemCapa = new SehConfigModemCapability();
                    configModemCapa.readFromParcel(_hidl_request);
                    configModemCapabilityChangeNoti(type21, configModemCapa);
                    return;
                case 22:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    String select = _hidl_request.readString();
                    SehApnProfile _hidl_out_apnProf = needApnProfileIndication(select);
                    _hidl_reply.writeStatus(0);
                    _hidl_out_apnProf.writeToParcel(_hidl_reply);
                    _hidl_reply.send();
                    return;
                case 23:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    String key = _hidl_request.readString();
                    String table = _hidl_request.readString();
                    int _hidl_out_value = needSettingValueIndication(key, table);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeInt32(_hidl_out_value);
                    _hidl_reply.send();
                    return;
                case 24:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type22 = _hidl_request.readInt32();
                    String cmd2 = _hidl_request.readString();
                    execute(type22, cmd2);
                    return;
                case 25:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type23 = _hidl_request.readInt32();
                    SehSignalBar signalBarInfo = new SehSignalBar();
                    signalBarInfo.readFromParcel(_hidl_request);
                    signalLevelInfoChanged(type23, signalBarInfo);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 26:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    int type24 = _hidl_request.readInt32();
                    SehExtendedRegStateResult state6 = new SehExtendedRegStateResult();
                    state6.readFromParcel(_hidl_request);
                    extendedRegistrationState(type24, state6);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 27:
                    _hidl_request.enforceInterface(vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.kInterfaceName);
                    String iface = _hidl_request.readString();
                    needPacketUsage(iface, new ISehRadioIndication.needPacketUsageCallback() { // from class: vendor.samsung.hardware.radio.V2_1.ISehRadioIndication.Stub.1
                        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.needPacketUsageCallback
                        public void onValues(int error, SehPacketUsage usage) {
                            _hidl_reply.writeStatus(0);
                            _hidl_reply.writeInt32(error);
                            usage.writeToParcel(_hidl_reply);
                            _hidl_reply.send();
                        }
                    });
                    return;
                case 28:
                    _hidl_request.enforceInterface(ISehRadioIndication.kInterfaceName);
                    int type25 = _hidl_request.readInt32();
                    int nrIconType = _hidl_request.readInt32();
                    nrIconTypeChanged(type25, nrIconType);
                    return;
                case 256067662:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ArrayList<String> _hidl_out_descriptors = interfaceChain();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeStringVector(_hidl_out_descriptors);
                    _hidl_reply.send();
                    return;
                case 256131655:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    NativeHandle fd = _hidl_request.readNativeHandle();
                    ArrayList<String> options = _hidl_request.readStringVector();
                    debug(fd, options);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 256136003:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    String _hidl_out_descriptor = interfaceDescriptor();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeString(_hidl_out_descriptor);
                    _hidl_reply.send();
                    return;
                case 256398152:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ArrayList<byte[]> _hidl_out_hashchain = getHashChain();
                    _hidl_reply.writeStatus(0);
                    HwBlob _hidl_blob = new HwBlob(16);
                    int _hidl_vec_size = _hidl_out_hashchain.size();
                    _hidl_blob.putInt32(8L, _hidl_vec_size);
                    _hidl_blob.putBool(12L, false);
                    HwBlob childBlob = new HwBlob(_hidl_vec_size * 32);
                    for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
                        long _hidl_array_offset_1 = _hidl_index_0 * 32;
                        byte[] _hidl_array_item_1 = _hidl_out_hashchain.get(_hidl_index_0);
                        if (_hidl_array_item_1 == null || _hidl_array_item_1.length != 32) {
                            throw new IllegalArgumentException("Array element is not of the expected length");
                        }
                        childBlob.putInt8Array(_hidl_array_offset_1, _hidl_array_item_1);
                    }
                    _hidl_blob.putBlob(0L, childBlob);
                    _hidl_reply.writeBuffer(_hidl_blob);
                    _hidl_reply.send();
                    return;
                case 256462420:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    setHALInstrumentation();
                    return;
                case 256660548:
                default:
                    return;
                case 256921159:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    ping();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 257049926:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    DebugInfo _hidl_out_info = getDebugInfo();
                    _hidl_reply.writeStatus(0);
                    _hidl_out_info.writeToParcel(_hidl_reply);
                    _hidl_reply.send();
                    return;
                case 257120595:
                    _hidl_request.enforceInterface(IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
            }
        }
    }
}
