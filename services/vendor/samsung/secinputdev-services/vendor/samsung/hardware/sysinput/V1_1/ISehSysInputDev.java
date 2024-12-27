package vendor.samsung.hardware.sysinput.V1_1;

import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;

import vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public interface ISehSysInputDev extends vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev {
    public static final String kInterfaceName =
            "vendor.samsung.hardware.sysinput@1.1::ISehSysInputDev";

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    IHwBinder asBinder();

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    int runSpenCmdNoRead(String str) throws RemoteException;

    int runTspCmdNoRead(int i, String str) throws RemoteException;

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    int setSpenEnable(int i, boolean z) throws RemoteException;

    int setTspEnable(int i, int i2, boolean z) throws RemoteException;

    @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev, android.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static ISehSysInputDev asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof ISehSysInputDev)) {
            return (ISehSysInputDev) iface;
        }
        ISehSysInputDev proxy = new Proxy(binder);
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

    static ISehSysInputDev castFrom(IHwInterface iface) {
        if (iface == null) {
            return null;
        }
        return asInterface(iface.asBinder());
    }

    static ISehSysInputDev getService(String serviceName, boolean retry) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName, retry));
    }

    static ISehSysInputDev getService(boolean retry) throws RemoteException {
        return getService("default", retry);
    }

    @Deprecated
    static ISehSysInputDev getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    @Deprecated
    static ISehSysInputDev getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements ISehSysInputDev {
        private IHwBinder mRemote;

        public Proxy(IHwBinder remote) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(remote);
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException e) {
                return "[class or subclass of"
                           + " vendor.samsung.hardware.sysinput@1.1::ISehSysInputDev]@Proxy";
            }
        }

        public final boolean equals(Object other) {
            return HidlSupport.interfacesEqual(this, other);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void getKeyCodePressed(
                int keycode, ISehSysInputDev.getKeyCodePressedCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(keycode);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void runTspCmd(int devid, String cmdname, ISehSysInputDev.runTspCmdCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(devid);
            _hidl_request.writeString(cmdname);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(2, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void getTspScrubPosition(
                int devid, ISehSysInputDev.getTspScrubPositionCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(devid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(3, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void getTspSupportFeature(
                int devid, ISehSysInputDev.getTspSupportFeatureCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(devid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(4, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void getTspCommandList(int devid, ISehSysInputDev.getTspCommandListCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(devid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(5, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void getTspAodActiveArea(
                int devid, ISehSysInputDev.getTspAodActiveAreaCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(devid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(6, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void getTspFodInformation(
                int devid, ISehSysInputDev.getTspFodInformationCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(devid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(7, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void getTspFodPosition(int devid, ISehSysInputDev.getTspFodPositionCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(devid);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(8, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void runSpenCmd(String cmdname, ISehSysInputDev.runSpenCmdCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            _hidl_request.writeString(cmdname);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(9, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void getSpenPosition(ISehSysInputDev.getSpenPositionCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(10, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev
        public void getSpenCommandList(ISehSysInputDev.getSpenCommandListCallback _hidl_cb)
                throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(
                    vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(11, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                String _hidl_out_outbuf = _hidl_reply.readString();
                _hidl_cb.onValues(_hidl_out_retval, _hidl_out_outbuf);
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev
        public int runTspCmdNoRead(int devid, String cmdname) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(devid);
            _hidl_request.writeString(cmdname);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(12, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                return _hidl_out_retval;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev
        public int runSpenCmdNoRead(String cmdname) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehSysInputDev.kInterfaceName);
            _hidl_request.writeString(cmdname);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(13, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                return _hidl_out_retval;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev
        public int setTspEnable(int devid, int enable, boolean isBefore) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(devid);
            _hidl_request.writeInt32(enable);
            _hidl_request.writeBool(isBefore);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(14, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                return _hidl_out_retval;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev
        public int setSpenEnable(int enable, boolean isBefore) throws RemoteException {
            HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(ISehSysInputDev.kInterfaceName);
            _hidl_request.writeInt32(enable);
            _hidl_request.writeBool(isBefore);
            HwParcel _hidl_reply = new HwParcel();
            try {
                this.mRemote.transact(15, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                int _hidl_out_retval = _hidl_reply.readInt32();
                return _hidl_out_retval;
            } finally {
                _hidl_reply.release();
            }
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie)
                throws RemoteException {
            return this.mRemote.linkToDeath(recipient, cookie);
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
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

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(recipient);
        }
    }

    public abstract static class Stub extends HwBinder implements ISehSysInputDev {
        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public IHwBinder asBinder() {
            return this;
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(
                    Arrays.asList(
                            ISehSysInputDev.kInterfaceName,
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName,
                            IBase.kInterfaceName));
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public void debug(NativeHandle fd, ArrayList<String> options) {}

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return ISehSysInputDev.kInterfaceName;
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(
                    Arrays.asList(
                            new byte[] {
                                -24, -17, -51, 22, 37, -23, -16, -27, 75, -12, 89, -83, 33, 15, 50,
                                -7, 84, -14, 120, -113, 4, -102, 120, -21, -112, 4, -115, -55, 84,
                                -7, -110, -106
                            },
                            new byte[] {
                                32, -76, -74, -122, -121, 82, 48, -41, -117, -110, -72, 102, -80,
                                -67, 80, 53, -70, 114, -66, 101, -122, -86, -56, 8, 114, 82, 124,
                                -119, 14, 77, 98, -115
                            },
                            new byte[] {
                                -20,
                                Byte.MAX_VALUE,
                                -41,
                                -98,
                                -48,
                                45,
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
                                36,
                                -13,
                                -51,
                                105,
                                87,
                                19,
                                -109,
                                36,
                                -72,
                                59,
                                24,
                                -54,
                                76
                            }));
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {}

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public final void ping() {}

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0L;
            info.arch = 0;
            return info;
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev,
                  // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev,
                  // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        public IHwInterface queryLocalInterface(String descriptor) {
            if (ISehSysInputDev.kInterfaceName.equals(descriptor)) {
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

        public void onTransact(
                int _hidl_code, HwParcel _hidl_request, final HwParcel _hidl_reply, int _hidl_flags)
                throws RemoteException {
            switch (_hidl_code) {
                case 1:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    int keycode = _hidl_request.readInt32();
                    getKeyCodePressed(
                            keycode,
                            new ISehSysInputDev
                                    .getKeyCodePressedCallback() { // from class:
                                                                   // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.1
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getKeyCodePressedCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 2:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    int devid = _hidl_request.readInt32();
                    String cmdname = _hidl_request.readString();
                    runTspCmd(
                            devid,
                            cmdname,
                            new ISehSysInputDev
                                    .runTspCmdCallback() { // from class:
                                                           // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.2
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.runTspCmdCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 3:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    int devid2 = _hidl_request.readInt32();
                    getTspScrubPosition(
                            devid2,
                            new ISehSysInputDev
                                    .getTspScrubPositionCallback() { // from class:
                                                                     // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.3
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspScrubPositionCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 4:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    int devid3 = _hidl_request.readInt32();
                    getTspSupportFeature(
                            devid3,
                            new ISehSysInputDev
                                    .getTspSupportFeatureCallback() { // from class:
                                                                      // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.4
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspSupportFeatureCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 5:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    int devid4 = _hidl_request.readInt32();
                    getTspCommandList(
                            devid4,
                            new ISehSysInputDev
                                    .getTspCommandListCallback() { // from class:
                                                                   // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.5
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspCommandListCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 6:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    int devid5 = _hidl_request.readInt32();
                    getTspAodActiveArea(
                            devid5,
                            new ISehSysInputDev
                                    .getTspAodActiveAreaCallback() { // from class:
                                                                     // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.6
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspAodActiveAreaCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 7:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    int devid6 = _hidl_request.readInt32();
                    getTspFodInformation(
                            devid6,
                            new ISehSysInputDev
                                    .getTspFodInformationCallback() { // from class:
                                                                      // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.7
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspFodInformationCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 8:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    int devid7 = _hidl_request.readInt32();
                    getTspFodPosition(
                            devid7,
                            new ISehSysInputDev
                                    .getTspFodPositionCallback() { // from class:
                                                                   // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.8
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getTspFodPositionCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 9:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    String cmdname2 = _hidl_request.readString();
                    runSpenCmd(
                            cmdname2,
                            new ISehSysInputDev
                                    .runSpenCmdCallback() { // from class:
                                                            // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.9
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.runSpenCmdCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 10:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    getSpenPosition(
                            new ISehSysInputDev
                                    .getSpenPositionCallback() { // from class:
                                                                 // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.10
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getSpenPositionCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 11:
                    _hidl_request.enforceInterface(
                            vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.kInterfaceName);
                    getSpenCommandList(
                            new ISehSysInputDev
                                    .getSpenCommandListCallback() { // from class:
                                                                    // vendor.samsung.hardware.sysinput.V1_1.ISehSysInputDev.Stub.11
                                @Override // vendor.samsung.hardware.sysinput.V1_0.ISehSysInputDev.getSpenCommandListCallback
                                public void onValues(int retval, String outbuf) {
                                    _hidl_reply.writeStatus(0);
                                    _hidl_reply.writeInt32(retval);
                                    _hidl_reply.writeString(outbuf);
                                    _hidl_reply.send();
                                }
                            });
                    return;
                case 12:
                    _hidl_request.enforceInterface(ISehSysInputDev.kInterfaceName);
                    int devid8 = _hidl_request.readInt32();
                    String cmdname3 = _hidl_request.readString();
                    int _hidl_out_retval = runTspCmdNoRead(devid8, cmdname3);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeInt32(_hidl_out_retval);
                    _hidl_reply.send();
                    return;
                case 13:
                    _hidl_request.enforceInterface(ISehSysInputDev.kInterfaceName);
                    String cmdname4 = _hidl_request.readString();
                    int _hidl_out_retval2 = runSpenCmdNoRead(cmdname4);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeInt32(_hidl_out_retval2);
                    _hidl_reply.send();
                    return;
                case 14:
                    _hidl_request.enforceInterface(ISehSysInputDev.kInterfaceName);
                    int devid9 = _hidl_request.readInt32();
                    int enable = _hidl_request.readInt32();
                    boolean isBefore = _hidl_request.readBool();
                    int _hidl_out_retval3 = setTspEnable(devid9, enable, isBefore);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeInt32(_hidl_out_retval3);
                    _hidl_reply.send();
                    return;
                case 15:
                    _hidl_request.enforceInterface(ISehSysInputDev.kInterfaceName);
                    int enable2 = _hidl_request.readInt32();
                    boolean isBefore2 = _hidl_request.readBool();
                    int _hidl_out_retval4 = setSpenEnable(enable2, isBefore2);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeInt32(_hidl_out_retval4);
                    _hidl_reply.send();
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
