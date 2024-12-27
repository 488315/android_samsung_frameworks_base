package vendor.samsung.hardware.radio.V2_2;

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
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;

import vendor.samsung.hardware.radio.V2_0.SehAdnRecord;
import vendor.samsung.hardware.radio.V2_0.SehAllowDataParam;
import vendor.samsung.hardware.radio.V2_0.SehCsgInfo;
import vendor.samsung.hardware.radio.V2_0.SehDial;
import vendor.samsung.hardware.radio.V2_0.SehEncodedUssd;
import vendor.samsung.hardware.radio.V2_0.SehImsCall;
import vendor.samsung.hardware.radio.V2_0.SehPreferredNetworkInfo;
import vendor.samsung.hardware.radio.V2_0.SehSimMsgArgs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes6.dex */
public interface ISehRadio extends vendor.samsung.hardware.radio.V2_1.ISehRadio {
    public static final String kInterfaceName = "vendor.samsung.hardware.radio@2.2::ISehRadio";

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio,
              // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void getVendorSpecificConfiguration(int i) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    void setNrMode_2_2(int i, int i2, boolean z) throws RemoteException;

    void setVendorSpecificConfiguration(int i, ArrayList<SehVendorConfiguration> arrayList)
            throws RemoteException;

    @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio,
              // vendor.samsung.hardware.radio.V2_0.ISehRadio, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static ISehRadio asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof ISehRadio)) {
            return (ISehRadio) iface;
        }
        ISehRadio proxy = new Proxy(binder);
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

    static ISehRadio castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static ISehRadio getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static ISehRadio getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static ISehRadio getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static ISehRadio getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements ISehRadio {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(remote);
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of vendor.samsung.hardware.radio@2.2::ISehRadio]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setResponseFunctions(
                vendor.samsung.hardware.radio.V2_0.ISehRadioResponse radioResponse,
                vendor.samsung.hardware.radio.V2_0.ISehRadioIndication radioIndication)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeStrongBinder(
                    radioResponse == null ? null : radioResponse.asBinder());
            _hidl_request.writeStrongBinder(
                    radioIndication != null ? radioIndication.asBinder() : null);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getIccCardStatus(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void supplyNetworkDepersonalization(int serial, String netPin, int subState)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(netPin);
            _hidl_request.writeInt32(subState);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void dial(int serial, SehDial dialInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            dialInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getCurrentCalls(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getImsRegistrationState(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getAvailableNetworks(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setImsCallList(int serial, ArrayList<SehImsCall> imsCalls)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            SehImsCall.writeVectorToParcel(_hidl_request, imsCalls);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getPreferredNetworkList(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setPreferredNetworkList(int serial, SehPreferredNetworkInfo info)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            info.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendEncodedUssd(int serial, SehEncodedUssd encodedUssd) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            encodedUssd.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getDisable2g(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setDisable2g(int serial, int on) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(on);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getCnap(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getPhonebookStorageInfo(int serial, int fileId) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(fileId);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getUsimPhonebookCapability(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(16, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setSimOnOff(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(17, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setSimInitEvent(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(18, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getSimLockInfo(int serial, int numOfLockType, int lockType)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(numOfLockType);
            _hidl_request.writeInt32(lockType);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(19, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void supplyIccPersonalization(int serial, String pin) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(pin);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(20, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void changeIccPersonalization(int serial, String oldPass, String newPass)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeString(oldPass);
            _hidl_request.writeString(newPass);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(21, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendCdmaSmsExpectMore(int serial, CdmaSmsMessage sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(22, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getPhonebookEntry(int serial, int fileId, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(fileId);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(23, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void accessPhonebookEntry(
                int serial, int command, int fileId, int index, SehAdnRecord adnRecord, String pin2)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(command);
            _hidl_request.writeInt32(fileId);
            _hidl_request.writeInt32(index);
            adnRecord.writeToParcel(_hidl_request);
            _hidl_request.writeString(pin2);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(24, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getCellBroadcastConfig(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(25, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void emergencySearch(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(26, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void emergencyControl(int serial, int command) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(command);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(27, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getAtr(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(28, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendSms(int serial, GsmSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(29, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendSMSExpectMore(int serial, GsmSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(30, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendCdmaSms(int serial, CdmaSmsMessage sms) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            sms.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(31, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendImsSms(int serial, ImsSmsMessage message) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            message.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(32, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getStoredMsgCountFromSim(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(33, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void readSmsFromSim(int serial, int index) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(index);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(34, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void writeSmsToSim(int serial, SehSimMsgArgs arg) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            arg.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(35, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void getCsgList(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(36, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void selectCsgManual(int serial, SehCsgInfo csgInfo) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            csgInfo.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(37, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setDataAllowed(int serial, boolean allow, SehAllowDataParam param)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(allow);
            param.writeToParcel(_hidl_request);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(38, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void setMobileDataSetting(int serial, boolean enabled, boolean roamingEnabled)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeBool(enabled);
            _hidl_request.writeBool(roamingEnabled);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(39, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendRequestRaw(int serial, ArrayList<Byte> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt8Vector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(40, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_0.ISehRadio
        public void sendRequestStrings(int serial, ArrayList<String> data) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeStringVector(data);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(41, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio
        public void setNrMode(int serial, int mode) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_1.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(42, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio
        public void getNrMode(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_1.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(43, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_1.ISehRadio
        public void getNrIconType(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.radio.V2_1.ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(44, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio
        public void setNrMode_2_2(int serial, int mode, boolean force) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            _hidl_request.writeBool(force);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(45, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio
        public void getVendorSpecificConfiguration(int serial) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(46, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio
        public void setVendorSpecificConfiguration(
                int serial, ArrayList<SehVendorConfiguration> configurations)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehRadio.kInterfaceName);
            _hidl_request.writeInt32(serial);
            SehVendorConfiguration.writeVectorToParcel(_hidl_request, configurations);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(47, _hidl_request, _hidl_reply, 1);
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
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
                HwBlob childBlob =
                        _hidl_reply.readEmbeddedBuffer(
                                _hidl_vec_size * 32, _hidl_blob.handle(), 0L, true);
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie)
                throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    public abstract static class Stub extends HwBinder implements ISehRadio {
        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(
                    Arrays.asList(
                            ISehRadio.kInterfaceName,
                            vendor.samsung.hardware.radio.V2_1.ISehRadio.kInterfaceName,
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName,
                            IBase.kInterfaceName));
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) {}

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return ISehRadio.kInterfaceName;
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(
                    Arrays.asList(
                            new byte[] {
                                -78,
                                -18,
                                91,
                                1,
                                -127,
                                -51,
                                8,
                                -37,
                                32,
                                0,
                                6,
                                Byte.MAX_VALUE,
                                -63,
                                -58,
                                -40,
                                7,
                                2,
                                106,
                                89,
                                -73,
                                -36,
                                -29,
                                76,
                                110,
                                -26,
                                -110,
                                -81,
                                SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT,
                                -121,
                                104,
                                19,
                                SprAttributeBase.TYPE_DURATION
                            },
                            new byte[] {
                                6,
                                -89,
                                104,
                                126,
                                2,
                                15,
                                -126,
                                8,
                                -26,
                                MidiConstants.STATUS_SONG_POSITION,
                                9,
                                -9,
                                -97,
                                -62,
                                -60,
                                -5,
                                105,
                                -21,
                                18,
                                14,
                                -127,
                                -26,
                                -83,
                                -6,
                                -21,
                                26,
                                -69,
                                -110,
                                -84,
                                SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60,
                                -83,
                                -113
                            },
                            new byte[] {
                                104,
                                75,
                                -90,
                                79,
                                123,
                                -108,
                                12,
                                62,
                                76,
                                MidiConstants.STATUS_SONG_SELECT,
                                109,
                                -52,
                                94,
                                108,
                                17,
                                Byte.MIN_VALUE,
                                -70,
                                -93,
                                91,
                                -108,
                                -34,
                                -100,
                                MidiConstants.STATUS_CHANNEL_PRESSURE,
                                -63,
                                -28,
                                -120,
                                93,
                                15,
                                32,
                                -46,
                                125,
                                107
                            },
                            new byte[] {
                                -20,
                                Byte.MAX_VALUE,
                                -41,
                                -98,
                                MidiConstants.STATUS_CHANNEL_PRESSURE,
                                SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60,
                                -6,
                                -123,
                                -68,
                                73,
                                -108,
                                38,
                                -83,
                                -82,
                                62,
                                -66,
                                35,
                                -17,
                                5,
                                SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT,
                                MidiConstants.STATUS_SONG_SELECT,
                                -51,
                                105,
                                87,
                                19,
                                -109,
                                SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT,
                                -72,
                                59,
                                24,
                                -54,
                                76
                            }));
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {}

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas,
                  // android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public final void ping() {}

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0L;
            info.arch = 0;
            return info;
        }

        @Override // vendor.samsung.hardware.radio.V2_2.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_1.ISehRadio,
                  // vendor.samsung.hardware.radio.V2_0.ISehRadio,
                  // android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas,
                  // android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String descriptor) {
            if (ISehRadio.kInterfaceName.equals(descriptor)) {
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
        public void onTransact(
                int _hidl_code, HwParcel _hidl_request, HwParcel _hidl_reply, int _hidl_flags)
                throws RemoteException {
            switch (_hidl_code) {
                case 1:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    vendor.samsung.hardware.radio.V2_0.ISehRadioResponse radioResponse =
                            vendor.samsung.hardware.radio.V2_0.ISehRadioResponse.asInterface(
                                    _hidl_request.readStrongBinder());
                    vendor.samsung.hardware.radio.V2_0.ISehRadioIndication radioIndication =
                            vendor.samsung.hardware.radio.V2_0.ISehRadioIndication.asInterface(
                                    _hidl_request.readStrongBinder());
                    setResponseFunctions(radioResponse, radioIndication);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 2:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial = _hidl_request.readInt32();
                    getIccCardStatus(serial);
                    return;
                case 3:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial2 = _hidl_request.readInt32();
                    String netPin = _hidl_request.readString();
                    int subState = _hidl_request.readInt32();
                    supplyNetworkDepersonalization(serial2, netPin, subState);
                    return;
                case 4:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial3 = _hidl_request.readInt32();
                    SehDial dialInfo = new SehDial();
                    dialInfo.readFromParcel(_hidl_request);
                    dial(serial3, dialInfo);
                    return;
                case 5:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial4 = _hidl_request.readInt32();
                    getCurrentCalls(serial4);
                    return;
                case 6:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial5 = _hidl_request.readInt32();
                    getImsRegistrationState(serial5);
                    return;
                case 7:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial6 = _hidl_request.readInt32();
                    getAvailableNetworks(serial6);
                    return;
                case 8:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial7 = _hidl_request.readInt32();
                    ArrayList<SehImsCall> imsCalls = SehImsCall.readVectorFromParcel(_hidl_request);
                    setImsCallList(serial7, imsCalls);
                    return;
                case 9:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial8 = _hidl_request.readInt32();
                    getPreferredNetworkList(serial8);
                    return;
                case 10:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial9 = _hidl_request.readInt32();
                    SehPreferredNetworkInfo info = new SehPreferredNetworkInfo();
                    info.readFromParcel(_hidl_request);
                    setPreferredNetworkList(serial9, info);
                    return;
                case 11:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial10 = _hidl_request.readInt32();
                    SehEncodedUssd encodedUssd = new SehEncodedUssd();
                    encodedUssd.readFromParcel(_hidl_request);
                    sendEncodedUssd(serial10, encodedUssd);
                    return;
                case 12:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial11 = _hidl_request.readInt32();
                    getDisable2g(serial11);
                    return;
                case 13:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial12 = _hidl_request.readInt32();
                    int on = _hidl_request.readInt32();
                    setDisable2g(serial12, on);
                    return;
                case 14:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial13 = _hidl_request.readInt32();
                    getCnap(serial13);
                    return;
                case 15:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial14 = _hidl_request.readInt32();
                    int fileId = _hidl_request.readInt32();
                    getPhonebookStorageInfo(serial14, fileId);
                    return;
                case 16:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial15 = _hidl_request.readInt32();
                    getUsimPhonebookCapability(serial15);
                    return;
                case 17:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial16 = _hidl_request.readInt32();
                    int mode = _hidl_request.readInt32();
                    setSimOnOff(serial16, mode);
                    return;
                case 18:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial17 = _hidl_request.readInt32();
                    setSimInitEvent(serial17);
                    return;
                case 19:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial18 = _hidl_request.readInt32();
                    int numOfLockType = _hidl_request.readInt32();
                    int lockType = _hidl_request.readInt32();
                    getSimLockInfo(serial18, numOfLockType, lockType);
                    return;
                case 20:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial19 = _hidl_request.readInt32();
                    String pin = _hidl_request.readString();
                    supplyIccPersonalization(serial19, pin);
                    return;
                case 21:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial20 = _hidl_request.readInt32();
                    String oldPass = _hidl_request.readString();
                    String newPass = _hidl_request.readString();
                    changeIccPersonalization(serial20, oldPass, newPass);
                    return;
                case 22:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial21 = _hidl_request.readInt32();
                    CdmaSmsMessage sms = new CdmaSmsMessage();
                    sms.readFromParcel(_hidl_request);
                    sendCdmaSmsExpectMore(serial21, sms);
                    return;
                case 23:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial22 = _hidl_request.readInt32();
                    int fileId2 = _hidl_request.readInt32();
                    int index = _hidl_request.readInt32();
                    getPhonebookEntry(serial22, fileId2, index);
                    return;
                case 24:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial23 = _hidl_request.readInt32();
                    int command = _hidl_request.readInt32();
                    int fileId3 = _hidl_request.readInt32();
                    int index2 = _hidl_request.readInt32();
                    SehAdnRecord adnRecord = new SehAdnRecord();
                    adnRecord.readFromParcel(_hidl_request);
                    String pin2 = _hidl_request.readString();
                    accessPhonebookEntry(serial23, command, fileId3, index2, adnRecord, pin2);
                    return;
                case 25:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial24 = _hidl_request.readInt32();
                    getCellBroadcastConfig(serial24);
                    return;
                case 26:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial25 = _hidl_request.readInt32();
                    emergencySearch(serial25);
                    return;
                case 27:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial26 = _hidl_request.readInt32();
                    int command2 = _hidl_request.readInt32();
                    emergencyControl(serial26, command2);
                    return;
                case 28:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial27 = _hidl_request.readInt32();
                    getAtr(serial27);
                    return;
                case 29:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial28 = _hidl_request.readInt32();
                    GsmSmsMessage message = new GsmSmsMessage();
                    message.readFromParcel(_hidl_request);
                    sendSms(serial28, message);
                    return;
                case 30:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial29 = _hidl_request.readInt32();
                    GsmSmsMessage message2 = new GsmSmsMessage();
                    message2.readFromParcel(_hidl_request);
                    sendSMSExpectMore(serial29, message2);
                    return;
                case 31:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial30 = _hidl_request.readInt32();
                    CdmaSmsMessage sms2 = new CdmaSmsMessage();
                    sms2.readFromParcel(_hidl_request);
                    sendCdmaSms(serial30, sms2);
                    return;
                case 32:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial31 = _hidl_request.readInt32();
                    ImsSmsMessage message3 = new ImsSmsMessage();
                    message3.readFromParcel(_hidl_request);
                    sendImsSms(serial31, message3);
                    return;
                case 33:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial32 = _hidl_request.readInt32();
                    getStoredMsgCountFromSim(serial32);
                    return;
                case 34:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial33 = _hidl_request.readInt32();
                    int index3 = _hidl_request.readInt32();
                    readSmsFromSim(serial33, index3);
                    return;
                case 35:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial34 = _hidl_request.readInt32();
                    SehSimMsgArgs arg = new SehSimMsgArgs();
                    arg.readFromParcel(_hidl_request);
                    writeSmsToSim(serial34, arg);
                    return;
                case 36:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial35 = _hidl_request.readInt32();
                    getCsgList(serial35);
                    return;
                case 37:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial36 = _hidl_request.readInt32();
                    SehCsgInfo csgInfo = new SehCsgInfo();
                    csgInfo.readFromParcel(_hidl_request);
                    selectCsgManual(serial36, csgInfo);
                    return;
                case 38:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial37 = _hidl_request.readInt32();
                    boolean allow = _hidl_request.readBool();
                    SehAllowDataParam param = new SehAllowDataParam();
                    param.readFromParcel(_hidl_request);
                    setDataAllowed(serial37, allow, param);
                    return;
                case 39:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial38 = _hidl_request.readInt32();
                    boolean enabled = _hidl_request.readBool();
                    boolean roamingEnabled = _hidl_request.readBool();
                    setMobileDataSetting(serial38, enabled, roamingEnabled);
                    return;
                case 40:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial39 = _hidl_request.readInt32();
                    ArrayList<Byte> data = _hidl_request.readInt8Vector();
                    sendRequestRaw(serial39, data);
                    return;
                case 41:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_0.ISehRadio.kInterfaceName);
                    int serial40 = _hidl_request.readInt32();
                    ArrayList<String> data2 = _hidl_request.readStringVector();
                    sendRequestStrings(serial40, data2);
                    return;
                case 42:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_1.ISehRadio.kInterfaceName);
                    int serial41 = _hidl_request.readInt32();
                    int mode2 = _hidl_request.readInt32();
                    setNrMode(serial41, mode2);
                    return;
                case 43:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_1.ISehRadio.kInterfaceName);
                    int serial42 = _hidl_request.readInt32();
                    getNrMode(serial42);
                    return;
                case 44:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.radio.V2_1.ISehRadio.kInterfaceName);
                    int serial43 = _hidl_request.readInt32();
                    getNrIconType(serial43);
                    return;
                case 45:
                    _hidl_request.enforceInterface(ISehRadio.kInterfaceName);
                    int serial44 = _hidl_request.readInt32();
                    int mode3 = _hidl_request.readInt32();
                    boolean force = _hidl_request.readBool();
                    setNrMode_2_2(serial44, mode3, force);
                    return;
                case 46:
                    _hidl_request.enforceInterface(ISehRadio.kInterfaceName);
                    int serial45 = _hidl_request.readInt32();
                    getVendorSpecificConfiguration(serial45);
                    return;
                case 47:
                    _hidl_request.enforceInterface(ISehRadio.kInterfaceName);
                    int serial46 = _hidl_request.readInt32();
                    ArrayList<SehVendorConfiguration> configurations =
                            SehVendorConfiguration.readVectorFromParcel(_hidl_request);
                    setVendorSpecificConfiguration(serial46, configurations);
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
                            throw new IllegalArgumentException(
                                    "Array element is not of the expected length");
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
