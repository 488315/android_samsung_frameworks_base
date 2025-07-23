package android.internal.hidl.manager.V1_0;

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
public interface IServiceManager extends IBase {
    public static final String kInterfaceName = "android.hidl.manager@1.0::IServiceManager";

    boolean add(String str, IBase iBase) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    ArrayList<InstanceDebugInfo> debugDump() throws RemoteException;

    IBase get(String str, String str2) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    byte getTransport(String str, String str2) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    ArrayList<String> list() throws RemoteException;

    ArrayList<String> listByInterface(String str) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    boolean registerForNotifications(String str, String str2, IServiceNotification iServiceNotification) throws RemoteException;

    void registerPassthroughClient(String str, String str2) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IServiceManager asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IServiceManager)) {
            return (IServiceManager) queryLocalInterface;
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

    static IServiceManager castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IServiceManager getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IServiceManager getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IServiceManager getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IServiceManager getService() throws RemoteException {
        return getService("default");
    }

    public static final class Transport {
        public static final byte EMPTY = 0;
        public static final byte HWBINDER = 1;
        public static final byte PASSTHROUGH = 2;

        public static final String toString(byte b) {
            if (b == 0) {
                return "EMPTY";
            }
            if (b == 1) {
                return "HWBINDER";
            }
            if (b == 2) {
                return "PASSTHROUGH";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("EMPTY");
            if ((b & 1) == 1) {
                arrayList.add("HWBINDER");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("PASSTHROUGH");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class PidConstant {
        public static final int NO_PID = -1;

        public static final String toString(int i) {
            if (i == -1) {
                return "NO_PID";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            int i2 = -1;
            if (i == -1) {
                arrayList.add("NO_PID");
            } else {
                i2 = 0;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class InstanceDebugInfo {
        public String interfaceName = new String();
        public String instanceName = new String();
        public int pid = 0;
        public ArrayList<Integer> clientPids = new ArrayList<>();
        public int arch = 0;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != InstanceDebugInfo.class) {
                return false;
            }
            InstanceDebugInfo instanceDebugInfo = (InstanceDebugInfo) obj;
            return HidlSupport.deepEquals(this.interfaceName, instanceDebugInfo.interfaceName) && HidlSupport.deepEquals(this.instanceName, instanceDebugInfo.instanceName) && this.pid == instanceDebugInfo.pid && HidlSupport.deepEquals(this.clientPids, instanceDebugInfo.clientPids) && this.arch == instanceDebugInfo.arch;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.interfaceName)), Integer.valueOf(HidlSupport.deepHashCode(this.instanceName)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.pid))), Integer.valueOf(HidlSupport.deepHashCode(this.clientPids)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.arch))));
        }

        public final String toString() {
            return "{.interfaceName = " + this.interfaceName + ", .instanceName = " + this.instanceName + ", .pid = " + this.pid + ", .clientPids = " + this.clientPids + ", .arch = " + DebugInfo.Architecture.toString(this.arch) + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
        }

        public static final ArrayList<InstanceDebugInfo> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<InstanceDebugInfo> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                InstanceDebugInfo instanceDebugInfo = new InstanceDebugInfo();
                instanceDebugInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
                arrayList.add(instanceDebugInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.interfaceName = hwBlob.getString(j);
            hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
            long j2 = j + 16;
            this.instanceName = hwBlob.getString(j2);
            hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
            this.pid = hwBlob.getInt32(j + 32);
            int int32 = hwBlob.getInt32(j + 48);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j + 40, true);
            this.clientPids.clear();
            for (int i = 0; i < int32; i++) {
                this.clientPids.add(Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
            }
            this.arch = hwBlob.getInt32(j + 56);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(64);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<InstanceDebugInfo> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 64);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putString(j, this.interfaceName);
            hwBlob.putString(16 + j, this.instanceName);
            hwBlob.putInt32(32 + j, this.pid);
            int size = this.clientPids.size();
            long j2 = 40 + j;
            hwBlob.putInt32(48 + j, size);
            hwBlob.putBool(52 + j, false);
            HwBlob hwBlob2 = new HwBlob(size * 4);
            for (int i = 0; i < size; i++) {
                hwBlob2.putInt32(i * 4, this.clientPids.get(i).intValue());
            }
            hwBlob.putBlob(j2, hwBlob2);
            hwBlob.putInt32(j + 56, this.arch);
        }
    }

    public static final class Proxy implements IServiceManager {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hidl.manager@1.0::IServiceManager]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public IBase get(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IBase.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public boolean add(String str, IBase iBase) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public byte getTransport(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt8();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public ArrayList<String> list() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public ArrayList<String> listByInterface(String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public boolean registerForNotifications(String str, String str2, IServiceNotification iServiceNotification) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeStrongBinder(iServiceNotification == null ? null : iServiceNotification.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public ArrayList<InstanceDebugInfo> debugDump() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return InstanceDebugInfo.readVectorFromParcel(hwParcel2);
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public void registerPassthroughClient(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IServiceManager {
        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IServiceManager.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IServiceManager.kInterfaceName;
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-123, 57, 79, -118, 13, 21, -25, -5, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -28, 92, 82, -47, -5, -117, -113, -45, -63, 60, 51, 62, 99, -57, -116, 74, -95, -1, -122, -124, 12, -10, -36}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IServiceManager.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    IBase iBase = get(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(iBase == null ? null : iBase.asBinder());
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    boolean add = add(hwParcel.readString(), IBase.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(add);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    byte transport = getTransport(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt8(transport);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    ArrayList<String> list = list();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(list);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    ArrayList<String> listByInterface = listByInterface(hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(listByInterface);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    boolean registerForNotifications = registerForNotifications(hwParcel.readString(), hwParcel.readString(), IServiceNotification.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(registerForNotifications);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    ArrayList<InstanceDebugInfo> debugDump = debugDump();
                    hwParcel2.writeStatus(0);
                    InstanceDebugInfo.writeVectorToParcel(hwParcel2, debugDump);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    registerPassthroughClient(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
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
