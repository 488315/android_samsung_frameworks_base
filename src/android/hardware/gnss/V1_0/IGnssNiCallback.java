package android.hardware.gnss.V1_0;

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
public interface IGnssNiCallback extends IBase {
    public static final String kInterfaceName = "android.hardware.gnss@1.0::IGnssNiCallback";

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

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    void niNotifyCb(GnssNiNotification gnssNiNotification) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IGnssNiCallback asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof IGnssNiCallback)) {
            return (IGnssNiCallback) iHwInterfaceQueryLocalInterface;
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

    static IGnssNiCallback castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IGnssNiCallback getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IGnssNiCallback getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IGnssNiCallback getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IGnssNiCallback getService() throws RemoteException {
        return getService("default");
    }

    public static final class GnssNiType {
        public static final byte EMERGENCY_SUPL = 4;
        public static final byte UMTS_CTRL_PLANE = 3;
        public static final byte UMTS_SUPL = 2;
        public static final byte VOICE = 1;

        public static final String toString(byte b) {
            if (b == 1) {
                return "VOICE";
            }
            if (b == 2) {
                return "UMTS_SUPL";
            }
            if (b == 3) {
                return "UMTS_CTRL_PLANE";
            }
            if (b == 4) {
                return "EMERGENCY_SUPL";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            if ((b & 1) == 1) {
                arrayList.add("VOICE");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("UMTS_SUPL");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 3) == 3) {
                arrayList.add("UMTS_CTRL_PLANE");
                b2 = (byte) (b2 | 3);
            }
            if ((b & 4) == 4) {
                arrayList.add("EMERGENCY_SUPL");
                b2 = (byte) (b2 | 4);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssNiNotifyFlags {
        public static final int NEED_NOTIFY = 1;
        public static final int NEED_VERIFY = 2;
        public static final int PRIVACY_OVERRIDE = 4;

        public static final String toString(int i) {
            if (i == 1) {
                return "NEED_NOTIFY";
            }
            if (i == 2) {
                return "NEED_VERIFY";
            }
            if (i == 4) {
                return "PRIVACY_OVERRIDE";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            int i2 = 1;
            if ((i & 1) == 1) {
                arrayList.add("NEED_NOTIFY");
            } else {
                i2 = 0;
            }
            if ((i & 2) == 2) {
                arrayList.add("NEED_VERIFY");
                i2 |= 2;
            }
            if ((i & 4) == 4) {
                arrayList.add("PRIVACY_OVERRIDE");
                i2 |= 4;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssUserResponseType {
        public static final byte RESPONSE_ACCEPT = 1;
        public static final byte RESPONSE_DENY = 2;
        public static final byte RESPONSE_NORESP = 3;

        public static final String toString(byte b) {
            if (b == 1) {
                return "RESPONSE_ACCEPT";
            }
            if (b == 2) {
                return "RESPONSE_DENY";
            }
            if (b == 3) {
                return "RESPONSE_NORESP";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            if ((b & 1) == 1) {
                arrayList.add("RESPONSE_ACCEPT");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("RESPONSE_DENY");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 3) == 3) {
                arrayList.add("RESPONSE_NORESP");
                b2 = (byte) (b2 | 3);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssNiEncodingType {
        public static final int ENC_NONE = 0;
        public static final int ENC_SUPL_GSM_DEFAULT = 1;
        public static final int ENC_SUPL_UCS2 = 3;
        public static final int ENC_SUPL_UTF8 = 2;
        public static final int ENC_UNKNOWN = -1;

        public static final String toString(int i) {
            if (i == 0) {
                return "ENC_NONE";
            }
            if (i == 1) {
                return "ENC_SUPL_GSM_DEFAULT";
            }
            if (i == 2) {
                return "ENC_SUPL_UTF8";
            }
            if (i == 3) {
                return "ENC_SUPL_UCS2";
            }
            if (i == -1) {
                return "ENC_UNKNOWN";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("ENC_NONE");
            int i2 = 1;
            if ((i & 1) == 1) {
                arrayList.add("ENC_SUPL_GSM_DEFAULT");
            } else {
                i2 = 0;
            }
            if ((i & 2) == 2) {
                arrayList.add("ENC_SUPL_UTF8");
                i2 |= 2;
            }
            if ((i & 3) == 3) {
                arrayList.add("ENC_SUPL_UCS2");
                i2 = 3;
            }
            if (i == -1) {
                arrayList.add("ENC_UNKNOWN");
                i2 = -1;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssNiNotification {
        public int notifyFlags;
        public int notificationId = 0;
        public byte niType = 0;
        public int timeoutSec = 0;
        public byte defaultResponse = 0;
        public String requestorId = new String();
        public String notificationMessage = new String();
        public int requestorIdEncoding = 0;
        public int notificationIdEncoding = 0;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssNiNotification.class) {
                return false;
            }
            GnssNiNotification gnssNiNotification = (GnssNiNotification) obj;
            return this.notificationId == gnssNiNotification.notificationId && this.niType == gnssNiNotification.niType && HidlSupport.deepEquals(Integer.valueOf(this.notifyFlags), Integer.valueOf(gnssNiNotification.notifyFlags)) && this.timeoutSec == gnssNiNotification.timeoutSec && this.defaultResponse == gnssNiNotification.defaultResponse && HidlSupport.deepEquals(this.requestorId, gnssNiNotification.requestorId) && HidlSupport.deepEquals(this.notificationMessage, gnssNiNotification.notificationMessage) && this.requestorIdEncoding == gnssNiNotification.requestorIdEncoding && this.notificationIdEncoding == gnssNiNotification.notificationIdEncoding;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.notificationId))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.niType))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.notifyFlags))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.timeoutSec))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.defaultResponse))), Integer.valueOf(HidlSupport.deepHashCode(this.requestorId)), Integer.valueOf(HidlSupport.deepHashCode(this.notificationMessage)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.requestorIdEncoding))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.notificationIdEncoding))));
        }

        public final String toString() {
            return "{.notificationId = " + this.notificationId + ", .niType = " + GnssNiType.toString(this.niType) + ", .notifyFlags = " + GnssNiNotifyFlags.dumpBitfield(this.notifyFlags) + ", .timeoutSec = " + this.timeoutSec + ", .defaultResponse = " + GnssUserResponseType.toString(this.defaultResponse) + ", .requestorId = " + this.requestorId + ", .notificationMessage = " + this.notificationMessage + ", .requestorIdEncoding = " + GnssNiEncodingType.toString(this.requestorIdEncoding) + ", .notificationIdEncoding = " + GnssNiEncodingType.toString(this.notificationIdEncoding) + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
        }

        public static final ArrayList<GnssNiNotification> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssNiNotification> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssNiNotification gnssNiNotification = new GnssNiNotification();
                gnssNiNotification.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 64);
                arrayList.add(gnssNiNotification);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.notificationId = hwBlob.getInt32(j);
            this.niType = hwBlob.getInt8(j + 4);
            this.notifyFlags = hwBlob.getInt32(j + 8);
            this.timeoutSec = hwBlob.getInt32(j + 12);
            this.defaultResponse = hwBlob.getInt8(j + 16);
            long j2 = j + 24;
            this.requestorId = hwBlob.getString(j2);
            hwParcel.readEmbeddedBuffer(r2.getBytes().length + 1, hwBlob.handle(), j2, false);
            long j3 = j + 40;
            this.notificationMessage = hwBlob.getString(j3);
            hwParcel.readEmbeddedBuffer(r4.getBytes().length + 1, hwBlob.handle(), j3, false);
            this.requestorIdEncoding = hwBlob.getInt32(j + 56);
            this.notificationIdEncoding = hwBlob.getInt32(j + 60);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(64);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssNiNotification> arrayList) {
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
            hwBlob.putInt32(j, this.notificationId);
            hwBlob.putInt8(4 + j, this.niType);
            hwBlob.putInt32(8 + j, this.notifyFlags);
            hwBlob.putInt32(12 + j, this.timeoutSec);
            hwBlob.putInt8(16 + j, this.defaultResponse);
            hwBlob.putString(24 + j, this.requestorId);
            hwBlob.putString(40 + j, this.notificationMessage);
            hwBlob.putInt32(56 + j, this.requestorIdEncoding);
            hwBlob.putInt32(j + 60, this.notificationIdEncoding);
        }
    }

    public static final class Proxy implements IGnssNiCallback {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@1.0::IGnssNiCallback]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback
        public void niNotifyCb(GnssNiNotification gnssNiNotification) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssNiCallback.kInterfaceName);
            gnssNiNotification.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnssNiCallback {
        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnssNiCallback.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnssNiCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-57, -127, -73, -79, 37, -10, -117, -27, -37, -118, -116, 61, 65, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 82, 106, -51, -67, -9, 125, -52, 89, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, 76, 14, -41, 11, -116, -28, -2, 108, 73}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IGnssNiCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IGnssNiCallback.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(IGnssNiCallback.kInterfaceName);
                    GnssNiNotification gnssNiNotification = new GnssNiNotification();
                    gnssNiNotification.readFromParcel(hwParcel);
                    niNotifyCb(gnssNiNotification);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
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
