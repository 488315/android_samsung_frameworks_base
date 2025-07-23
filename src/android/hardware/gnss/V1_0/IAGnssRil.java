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
import android.security.keystore.KeyProperties;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface IAGnssRil extends IBase {
    public static final String kInterfaceName = "android.hardware.gnss@1.0::IAGnssRil";

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

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    void setCallback(IAGnssRilCallback iAGnssRilCallback) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    void setRefLocation(AGnssRefLocation aGnssRefLocation) throws RemoteException;

    boolean setSetId(byte b, String str) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    boolean updateNetworkAvailability(boolean z, String str) throws RemoteException;

    boolean updateNetworkState(boolean z, byte b, boolean z2) throws RemoteException;

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

    public static final class SetIDType {
        public static final byte IMSI = 1;
        public static final byte MSISDM = 2;
        public static final byte NONE = 0;

        public static final String toString(byte b) {
            if (b == 0) {
                return KeyProperties.DIGEST_NONE;
            }
            if (b == 1) {
                return "IMSI";
            }
            if (b == 2) {
                return "MSISDM";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add(KeyProperties.DIGEST_NONE);
            if ((b & 1) == 1) {
                arrayList.add("IMSI");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("MSISDM");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class NetworkType {
        public static final byte DUN = 4;
        public static final byte HIPRI = 5;
        public static final byte MMS = 2;
        public static final byte MOBILE = 0;
        public static final byte SUPL = 3;
        public static final byte WIFI = 1;
        public static final byte WIMAX = 6;

        public static final String toString(byte b) {
            if (b == 0) {
                return "MOBILE";
            }
            if (b == 1) {
                return "WIFI";
            }
            if (b == 2) {
                return "MMS";
            }
            if (b == 3) {
                return "SUPL";
            }
            if (b == 4) {
                return "DUN";
            }
            if (b == 5) {
                return "HIPRI";
            }
            if (b == 6) {
                return "WIMAX";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("MOBILE");
            if ((b & 1) == 1) {
                arrayList.add("WIFI");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("MMS");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 3) == 3) {
                arrayList.add("SUPL");
                b2 = (byte) (b2 | 3);
            }
            if ((b & 4) == 4) {
                arrayList.add("DUN");
                b2 = (byte) (b2 | 4);
            }
            if ((b & 5) == 5) {
                arrayList.add("HIPRI");
                b2 = (byte) (b2 | 5);
            }
            if ((b & 6) == 6) {
                arrayList.add("WIMAX");
                b2 = (byte) (b2 | 6);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class AGnssRefLocationType {
        public static final byte GSM_CELLID = 1;
        public static final byte LTE_CELLID = 4;
        public static final byte UMTS_CELLID = 2;

        public static final String toString(byte b) {
            if (b == 1) {
                return "GSM_CELLID";
            }
            if (b == 2) {
                return "UMTS_CELLID";
            }
            if (b == 4) {
                return "LTE_CELLID";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            if ((b & 1) == 1) {
                arrayList.add("GSM_CELLID");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("UMTS_CELLID");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 4) == 4) {
                arrayList.add("LTE_CELLID");
                b2 = (byte) (b2 | 4);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class AGnssRefLocationCellID {
        public byte type = 0;
        public short mcc = 0;
        public short mnc = 0;
        public short lac = 0;
        public int cid = 0;
        public short tac = 0;
        public short pcid = 0;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != AGnssRefLocationCellID.class) {
                return false;
            }
            AGnssRefLocationCellID aGnssRefLocationCellID = (AGnssRefLocationCellID) obj;
            return this.type == aGnssRefLocationCellID.type && this.mcc == aGnssRefLocationCellID.mcc && this.mnc == aGnssRefLocationCellID.mnc && this.lac == aGnssRefLocationCellID.lac && this.cid == aGnssRefLocationCellID.cid && this.tac == aGnssRefLocationCellID.tac && this.pcid == aGnssRefLocationCellID.pcid;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.mcc))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.mnc))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.lac))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cid))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.tac))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.pcid))));
        }

        public final String toString() {
            return "{.type = " + AGnssRefLocationType.toString(this.type) + ", .mcc = " + ((int) this.mcc) + ", .mnc = " + ((int) this.mnc) + ", .lac = " + ((int) this.lac) + ", .cid = " + this.cid + ", .tac = " + ((int) this.tac) + ", .pcid = " + ((int) this.pcid) + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
        }

        public static final ArrayList<AGnssRefLocationCellID> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<AGnssRefLocationCellID> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                AGnssRefLocationCellID aGnssRefLocationCellID = new AGnssRefLocationCellID();
                aGnssRefLocationCellID.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
                arrayList.add(aGnssRefLocationCellID);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.type = hwBlob.getInt8(j);
            this.mcc = hwBlob.getInt16(2 + j);
            this.mnc = hwBlob.getInt16(4 + j);
            this.lac = hwBlob.getInt16(6 + j);
            this.cid = hwBlob.getInt32(8 + j);
            this.tac = hwBlob.getInt16(12 + j);
            this.pcid = hwBlob.getInt16(j + 14);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(16);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<AGnssRefLocationCellID> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 16);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt8(j, this.type);
            hwBlob.putInt16(2 + j, this.mcc);
            hwBlob.putInt16(4 + j, this.mnc);
            hwBlob.putInt16(6 + j, this.lac);
            hwBlob.putInt32(8 + j, this.cid);
            hwBlob.putInt16(12 + j, this.tac);
            hwBlob.putInt16(j + 14, this.pcid);
        }
    }

    public static final class AGnssRefLocation {
        public byte type = 0;
        public AGnssRefLocationCellID cellID = new AGnssRefLocationCellID();

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != AGnssRefLocation.class) {
                return false;
            }
            AGnssRefLocation aGnssRefLocation = (AGnssRefLocation) obj;
            return this.type == aGnssRefLocation.type && HidlSupport.deepEquals(this.cellID, aGnssRefLocation.cellID);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(this.cellID)));
        }

        public final String toString() {
            return "{.type = " + AGnssRefLocationType.toString(this.type) + ", .cellID = " + this.cellID + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
        }

        public static final ArrayList<AGnssRefLocation> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<AGnssRefLocation> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                AGnssRefLocation aGnssRefLocation = new AGnssRefLocation();
                aGnssRefLocation.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
                arrayList.add(aGnssRefLocation);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.type = hwBlob.getInt8(j);
            this.cellID.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(20);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<AGnssRefLocation> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 20);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt8(j, this.type);
            this.cellID.writeEmbeddedToBlob(hwBlob, j + 4);
        }
    }

    public static final class Proxy implements IAGnssRil {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@1.0::IAGnssRil]@Proxy";
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
            hwParcel.writeInterfaceToken(IAGnssRil.kInterfaceName);
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
        public void setRefLocation(AGnssRefLocation aGnssRefLocation) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IAGnssRil.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IAGnssRil.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IAGnssRil.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IAGnssRil.kInterfaceName);
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IAGnssRil {
        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IAGnssRil.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IAGnssRil.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-47, 110, 106, 53, -101, -26, -106, 62, -89, 83, -41, 19, -114, -124, -20, MidiConstants.STATUS_SONG_POSITION, -71, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, 82, 9, 121, 56, -109, -116, 77, 54, -41, -92, 126, -94, -30, -82}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IAGnssRil, android.internal.hidl.base.V1_0.IBase
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
            if (i == 1) {
                hwParcel.enforceInterface(IAGnssRil.kInterfaceName);
                setCallback(IAGnssRilCallback.asInterface(hwParcel.readStrongBinder()));
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            }
            if (i == 2) {
                hwParcel.enforceInterface(IAGnssRil.kInterfaceName);
                AGnssRefLocation aGnssRefLocation = new AGnssRefLocation();
                aGnssRefLocation.readFromParcel(hwParcel);
                setRefLocation(aGnssRefLocation);
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            }
            if (i == 3) {
                hwParcel.enforceInterface(IAGnssRil.kInterfaceName);
                boolean setId = setSetId(hwParcel.readInt8(), hwParcel.readString());
                hwParcel2.writeStatus(0);
                hwParcel2.writeBool(setId);
                hwParcel2.send();
                return;
            }
            if (i == 4) {
                hwParcel.enforceInterface(IAGnssRil.kInterfaceName);
                boolean updateNetworkState = updateNetworkState(hwParcel.readBool(), hwParcel.readInt8(), hwParcel.readBool());
                hwParcel2.writeStatus(0);
                hwParcel2.writeBool(updateNetworkState);
                hwParcel2.send();
                return;
            }
            if (i == 5) {
                hwParcel.enforceInterface(IAGnssRil.kInterfaceName);
                boolean updateNetworkAvailability = updateNetworkAvailability(hwParcel.readBool(), hwParcel.readString());
                hwParcel2.writeStatus(0);
                hwParcel2.writeBool(updateNetworkAvailability);
                hwParcel2.send();
                return;
            }
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
