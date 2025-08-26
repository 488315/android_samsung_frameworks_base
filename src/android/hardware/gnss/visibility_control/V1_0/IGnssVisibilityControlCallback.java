package android.hardware.gnss.visibility_control.V1_0;

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
public interface IGnssVisibilityControlCallback extends IBase {
    public static final String kInterfaceName = "android.hardware.gnss.visibility_control@1.0::IGnssVisibilityControlCallback";

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    boolean isInEmergencySession() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void nfwNotifyCb(NfwNotification nfwNotification) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IGnssVisibilityControlCallback asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof IGnssVisibilityControlCallback)) {
            return (IGnssVisibilityControlCallback) iHwInterfaceQueryLocalInterface;
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

    static IGnssVisibilityControlCallback castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IGnssVisibilityControlCallback getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IGnssVisibilityControlCallback getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IGnssVisibilityControlCallback getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IGnssVisibilityControlCallback getService() throws RemoteException {
        return getService("default");
    }

    public static final class NfwProtocolStack {
        public static final byte CTRL_PLANE = 0;
        public static final byte IMS = 10;
        public static final byte OTHER_PROTOCOL_STACK = 100;
        public static final byte SIM = 11;
        public static final byte SUPL = 1;

        public static final String toString(byte b) {
            if (b == 0) {
                return "CTRL_PLANE";
            }
            if (b == 1) {
                return "SUPL";
            }
            if (b == 10) {
                return "IMS";
            }
            if (b == 11) {
                return "SIM";
            }
            if (b == 100) {
                return "OTHER_PROTOCOL_STACK";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("CTRL_PLANE");
            if ((b & 1) == 1) {
                arrayList.add("SUPL");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 10) == 10) {
                arrayList.add("IMS");
                b2 = (byte) (b2 | 10);
            }
            if ((b & 11) == 11) {
                arrayList.add("SIM");
                b2 = (byte) (b2 | 11);
            }
            if ((b & 100) == 100) {
                arrayList.add("OTHER_PROTOCOL_STACK");
                b2 = (byte) (b2 | 100);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class NfwRequestor {
        public static final byte AUTOMOBILE_CLIENT = 20;
        public static final byte CARRIER = 0;
        public static final byte GNSS_CHIPSET_VENDOR = 12;
        public static final byte MODEM_CHIPSET_VENDOR = 11;
        public static final byte OEM = 10;
        public static final byte OTHER_CHIPSET_VENDOR = 13;
        public static final byte OTHER_REQUESTOR = 100;

        public static final String toString(byte b) {
            if (b == 0) {
                return "CARRIER";
            }
            if (b == 10) {
                return "OEM";
            }
            if (b == 11) {
                return "MODEM_CHIPSET_VENDOR";
            }
            if (b == 12) {
                return "GNSS_CHIPSET_VENDOR";
            }
            if (b == 13) {
                return "OTHER_CHIPSET_VENDOR";
            }
            if (b == 20) {
                return "AUTOMOBILE_CLIENT";
            }
            if (b == 100) {
                return "OTHER_REQUESTOR";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("CARRIER");
            if ((b & 10) == 10) {
                arrayList.add("OEM");
                b2 = (byte) 10;
            } else {
                b2 = 0;
            }
            if ((b & 11) == 11) {
                arrayList.add("MODEM_CHIPSET_VENDOR");
                b2 = (byte) (b2 | 11);
            }
            if ((b & 12) == 12) {
                arrayList.add("GNSS_CHIPSET_VENDOR");
                b2 = (byte) (b2 | 12);
            }
            if ((b & 13) == 13) {
                arrayList.add("OTHER_CHIPSET_VENDOR");
                b2 = (byte) (b2 | 13);
            }
            if ((b & 20) == 20) {
                arrayList.add("AUTOMOBILE_CLIENT");
                b2 = (byte) (b2 | 20);
            }
            if ((b & 100) == 100) {
                arrayList.add("OTHER_REQUESTOR");
                b2 = (byte) (b2 | 100);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class NfwResponseType {
        public static final byte ACCEPTED_LOCATION_PROVIDED = 2;
        public static final byte ACCEPTED_NO_LOCATION_PROVIDED = 1;
        public static final byte REJECTED = 0;

        public static final String toString(byte b) {
            if (b == 0) {
                return "REJECTED";
            }
            if (b == 1) {
                return "ACCEPTED_NO_LOCATION_PROVIDED";
            }
            if (b == 2) {
                return "ACCEPTED_LOCATION_PROVIDED";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("REJECTED");
            if ((b & 1) == 1) {
                arrayList.add("ACCEPTED_NO_LOCATION_PROVIDED");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("ACCEPTED_LOCATION_PROVIDED");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class NfwNotification {
        public String proxyAppPackageName = new String();
        public byte protocolStack = 0;
        public String otherProtocolStackName = new String();
        public byte requestor = 0;
        public String requestorId = new String();
        public byte responseType = 0;
        public boolean inEmergencyMode = false;
        public boolean isCachedLocation = false;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != NfwNotification.class) {
                return false;
            }
            NfwNotification nfwNotification = (NfwNotification) obj;
            return HidlSupport.deepEquals(this.proxyAppPackageName, nfwNotification.proxyAppPackageName) && this.protocolStack == nfwNotification.protocolStack && HidlSupport.deepEquals(this.otherProtocolStackName, nfwNotification.otherProtocolStackName) && this.requestor == nfwNotification.requestor && HidlSupport.deepEquals(this.requestorId, nfwNotification.requestorId) && this.responseType == nfwNotification.responseType && this.inEmergencyMode == nfwNotification.inEmergencyMode && this.isCachedLocation == nfwNotification.isCachedLocation;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.proxyAppPackageName)), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.protocolStack))), Integer.valueOf(HidlSupport.deepHashCode(this.otherProtocolStackName)), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.requestor))), Integer.valueOf(HidlSupport.deepHashCode(this.requestorId)), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.responseType))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.inEmergencyMode))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isCachedLocation))));
        }

        public final String toString() {
            return "{.proxyAppPackageName = " + this.proxyAppPackageName + ", .protocolStack = " + NfwProtocolStack.toString(this.protocolStack) + ", .otherProtocolStackName = " + this.otherProtocolStackName + ", .requestor = " + NfwRequestor.toString(this.requestor) + ", .requestorId = " + this.requestorId + ", .responseType = " + NfwResponseType.toString(this.responseType) + ", .inEmergencyMode = " + this.inEmergencyMode + ", .isCachedLocation = " + this.isCachedLocation + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
        }

        public static final ArrayList<NfwNotification> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<NfwNotification> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                NfwNotification nfwNotification = new NfwNotification();
                nfwNotification.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 72);
                arrayList.add(nfwNotification);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.proxyAppPackageName = hwBlob.getString(j);
            hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j, false);
            this.protocolStack = hwBlob.getInt8(j + 16);
            long j2 = j + 24;
            this.otherProtocolStackName = hwBlob.getString(j2);
            hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j2, false);
            this.requestor = hwBlob.getInt8(j + 40);
            long j3 = j + 48;
            this.requestorId = hwBlob.getString(j3);
            hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
            this.responseType = hwBlob.getInt8(j + 64);
            this.inEmergencyMode = hwBlob.getBool(j + 65);
            this.isCachedLocation = hwBlob.getBool(j + 66);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(72);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NfwNotification> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 72);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putString(j, this.proxyAppPackageName);
            hwBlob.putInt8(16 + j, this.protocolStack);
            hwBlob.putString(24 + j, this.otherProtocolStackName);
            hwBlob.putInt8(40 + j, this.requestor);
            hwBlob.putString(48 + j, this.requestorId);
            hwBlob.putInt8(64 + j, this.responseType);
            hwBlob.putBool(65 + j, this.inEmergencyMode);
            hwBlob.putBool(j + 66, this.isCachedLocation);
        }
    }

    public static final class Proxy implements IGnssVisibilityControlCallback {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss.visibility_control@1.0::IGnssVisibilityControlCallback]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback
        public void nfwNotifyCb(NfwNotification nfwNotification) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssVisibilityControlCallback.kInterfaceName);
            nfwNotification.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback
        public boolean isInEmergencySession() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssVisibilityControlCallback.kInterfaceName);
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnssVisibilityControlCallback {
        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnssVisibilityControlCallback.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnssVisibilityControlCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{51, -90, -78, 12, 67, -81, 0, -3, -5, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 93, -8, -111, -68, 89, 17, MidiConstants.STATUS_PROGRAM_CHANGE, 109, -102, -111, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -71, 18, 117, -106, 73, -109, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 90, 74, 110, 109}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.visibility_control.V1_0.IGnssVisibilityControlCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IGnssVisibilityControlCallback.kInterfaceName.equals(str)) {
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
            if (i == 1) {
                hwParcel.enforceInterface(IGnssVisibilityControlCallback.kInterfaceName);
                NfwNotification nfwNotification = new NfwNotification();
                nfwNotification.readFromParcel(hwParcel);
                nfwNotifyCb(nfwNotification);
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            }
            if (i == 2) {
                hwParcel.enforceInterface(IGnssVisibilityControlCallback.kInterfaceName);
                boolean zIsInEmergencySession = isInEmergencySession();
                hwParcel2.writeStatus(0);
                hwParcel2.writeBool(zIsInEmergencySession);
                hwParcel2.send();
                return;
            }
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
