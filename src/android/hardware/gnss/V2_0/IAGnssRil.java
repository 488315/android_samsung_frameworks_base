package android.hardware.gnss.V2_0;

import android.hardware.gnss.V1_0.IAGnssRil;
import android.hardware.gnss.V1_0.IAGnssRilCallback;
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
public interface IAGnssRil extends android.hardware.gnss.V1_0.IAGnssRil {
    public static final String kInterfaceName = "android.hardware.gnss@2.0::IAGnssRil";

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    boolean updateNetworkState_2_0(NetworkAttributes networkAttributes) throws RemoteException;

    static IAGnssRil asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IAGnssRil)) {
            return (IAGnssRil) queryLocalInterface;
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

    static IAGnssRil castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IAGnssRil getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IAGnssRil getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IAGnssRil getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IAGnssRil getService() throws RemoteException {
        return getService("default");
    }

    public static final class NetworkCapability {
        public static final short NOT_METERED = 1;
        public static final short NOT_ROAMING = 2;

        public static final String toString(short s) {
            if (s == 1) {
                return "NOT_METERED";
            }
            if (s == 2) {
                return "NOT_ROAMING";
            }
            return "0x" + Integer.toHexString(Short.toUnsignedInt(s));
        }

        public static final String dumpBitfield(short s) {
            short s2;
            ArrayList arrayList = new ArrayList();
            if ((s & 1) == 1) {
                arrayList.add("NOT_METERED");
                s2 = (short) 1;
            } else {
                s2 = 0;
            }
            if ((s & 2) == 2) {
                arrayList.add("NOT_ROAMING");
                s2 = (short) (s2 | 2);
            }
            if (s != s2) {
                arrayList.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) (s & (~s2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class NetworkAttributes {
        public short capabilities;
        public long networkHandle = 0;
        public boolean isConnected = false;
        public String apn = new String();

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != NetworkAttributes.class) {
                return false;
            }
            NetworkAttributes networkAttributes = (NetworkAttributes) obj;
            return this.networkHandle == networkAttributes.networkHandle && this.isConnected == networkAttributes.isConnected && HidlSupport.deepEquals(Short.valueOf(this.capabilities), Short.valueOf(networkAttributes.capabilities)) && HidlSupport.deepEquals(this.apn, networkAttributes.apn);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.networkHandle))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isConnected))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.capabilities))), Integer.valueOf(HidlSupport.deepHashCode(this.apn)));
        }

        public final String toString() {
            return "{.networkHandle = " + this.networkHandle + ", .isConnected = " + this.isConnected + ", .capabilities = " + NetworkCapability.dumpBitfield(this.capabilities) + ", .apn = " + this.apn + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
        }

        public static final ArrayList<NetworkAttributes> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<NetworkAttributes> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                NetworkAttributes networkAttributes = new NetworkAttributes();
                networkAttributes.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
                arrayList.add(networkAttributes);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.networkHandle = hwBlob.getInt64(j);
            this.isConnected = hwBlob.getBool(8 + j);
            this.capabilities = hwBlob.getInt16(10 + j);
            long j2 = j + 16;
            this.apn = hwBlob.getString(j2);
            hwParcel.readEmbeddedBuffer(r13.getBytes().length + 1, hwBlob.handle(), j2, false);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(32);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NetworkAttributes> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 32);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt64(j, this.networkHandle);
            hwBlob.putBool(8 + j, this.isConnected);
            hwBlob.putInt16(10 + j, this.capabilities);
            hwBlob.putString(j + 16, this.apn);
        }
    }

    public static final class Proxy implements IAGnssRil {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@2.0::IAGnssRil]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public void setCallback(IAGnssRilCallback iAGnssRilCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            hwParcel.writeStrongBinder(iAGnssRilCallback == null ? null : iAGnssRilCallback.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public void setRefLocation(IAGnssRil.AGnssRefLocation aGnssRefLocation) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            aGnssRefLocation.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public boolean setSetId(byte b, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            hwParcel.writeInt8(b);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public boolean updateNetworkState(boolean z, byte b, boolean z2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            hwParcel.writeBool(z);
            hwParcel.writeInt8(b);
            hwParcel.writeBool(z2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil
        public boolean updateNetworkAvailability(boolean z, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
            hwParcel.writeBool(z);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil
        public boolean updateNetworkState_2_0(NetworkAttributes networkAttributes) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IAGnssRil.kInterfaceName);
            networkAttributes.writeToParcel(hwParcel);
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

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IAGnssRil {
        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IAGnssRil.kInterfaceName, android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IAGnssRil.kInterfaceName;
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 74, MidiConstants.STATUS_PROGRAM_CHANGE, 104, -88, -118, 114, 54, 2, Byte.MIN_VALUE, -39, 74, Byte.MAX_VALUE, 111, -41, -58, 56, 19, -63, -18, -92, -119, 26, 14, MidiConstants.STATUS_CONTROL_CHANGE, 19, -108, -45, -25, -25, 117, MidiConstants.STATUS_SONG_POSITION}, new byte[]{-47, 110, 106, 53, -101, -26, -106, 62, -89, 83, -41, 19, -114, -124, -20, MidiConstants.STATUS_SONG_POSITION, -71, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 82, 9, 121, 56, -109, -116, 77, 54, -41, -92, 126, -94, -30, -82}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V2_0.IAGnssRil, android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IAGnssRil.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    setCallback(IAGnssRilCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    IAGnssRil.AGnssRefLocation aGnssRefLocation = new IAGnssRil.AGnssRefLocation();
                    aGnssRefLocation.readFromParcel(hwParcel);
                    setRefLocation(aGnssRefLocation);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    boolean setId = setSetId(hwParcel.readInt8(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(setId);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    boolean updateNetworkState = updateNetworkState(hwParcel.readBool(), hwParcel.readInt8(), hwParcel.readBool());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(updateNetworkState);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IAGnssRil.kInterfaceName);
                    boolean updateNetworkAvailability = updateNetworkAvailability(hwParcel.readBool(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(updateNetworkAvailability);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(IAGnssRil.kInterfaceName);
                    NetworkAttributes networkAttributes = new NetworkAttributes();
                    networkAttributes.readFromParcel(hwParcel);
                    boolean updateNetworkState_2_0 = updateNetworkState_2_0(networkAttributes);
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(updateNetworkState_2_0);
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
