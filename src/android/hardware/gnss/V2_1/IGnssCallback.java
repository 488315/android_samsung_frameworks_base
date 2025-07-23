package android.hardware.gnss.V2_1;

import android.hardware.gnss.V1_0.GnssLocation;
import android.hardware.gnss.V1_0.IGnssCallback;
import android.hardware.gnss.V2_0.IGnssCallback;
import android.hardware.scontext.SContextConstants;
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

/* loaded from: classes2.dex */
public interface IGnssCallback extends android.hardware.gnss.V2_0.IGnssCallback {
    public static final String kInterfaceName = "android.hardware.gnss@2.1::IGnssCallback";

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void gnssSetCapabilitiesCb_2_1(int i) throws RemoteException;

    void gnssSvStatusCb_2_1(ArrayList<GnssSvInfo> arrayList) throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IGnssCallback asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssCallback)) {
            return (IGnssCallback) queryLocalInterface;
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

    static IGnssCallback castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IGnssCallback getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IGnssCallback getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IGnssCallback getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IGnssCallback getService() throws RemoteException {
        return getService("default");
    }

    public static final class Capabilities {
        public static final int ANTENNA_INFO = 2048;
        public static final int GEOFENCING = 32;
        public static final int LOW_POWER_MODE = 256;
        public static final int MEASUREMENTS = 64;
        public static final int MEASUREMENT_CORRECTIONS = 1024;
        public static final int MSA = 4;
        public static final int MSB = 2;
        public static final int NAV_MESSAGES = 128;
        public static final int ON_DEMAND_TIME = 16;
        public static final int SATELLITE_BLACKLIST = 512;
        public static final int SCHEDULING = 1;
        public static final int SINGLE_SHOT = 8;

        public static final String toString(int i) {
            if (i == 1) {
                return "SCHEDULING";
            }
            if (i == 2) {
                return "MSB";
            }
            if (i == 4) {
                return "MSA";
            }
            if (i == 8) {
                return "SINGLE_SHOT";
            }
            if (i == 16) {
                return "ON_DEMAND_TIME";
            }
            if (i == 32) {
                return "GEOFENCING";
            }
            if (i == 64) {
                return "MEASUREMENTS";
            }
            if (i == 128) {
                return "NAV_MESSAGES";
            }
            if (i == 256) {
                return "LOW_POWER_MODE";
            }
            if (i == 512) {
                return "SATELLITE_BLACKLIST";
            }
            if (i == 1024) {
                return "MEASUREMENT_CORRECTIONS";
            }
            if (i == 2048) {
                return "ANTENNA_INFO";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            int i2 = 1;
            if ((i & 1) == 1) {
                arrayList.add("SCHEDULING");
            } else {
                i2 = 0;
            }
            if ((i & 2) == 2) {
                arrayList.add("MSB");
                i2 |= 2;
            }
            if ((i & 4) == 4) {
                arrayList.add("MSA");
                i2 |= 4;
            }
            if ((i & 8) == 8) {
                arrayList.add("SINGLE_SHOT");
                i2 |= 8;
            }
            if ((i & 16) == 16) {
                arrayList.add("ON_DEMAND_TIME");
                i2 |= 16;
            }
            if ((i & 32) == 32) {
                arrayList.add("GEOFENCING");
                i2 |= 32;
            }
            if ((i & 64) == 64) {
                arrayList.add("MEASUREMENTS");
                i2 |= 64;
            }
            if ((i & 128) == 128) {
                arrayList.add("NAV_MESSAGES");
                i2 |= 128;
            }
            if ((i & 256) == 256) {
                arrayList.add("LOW_POWER_MODE");
                i2 |= 256;
            }
            if ((i & 512) == 512) {
                arrayList.add("SATELLITE_BLACKLIST");
                i2 |= 512;
            }
            if ((i & 1024) == 1024) {
                arrayList.add("MEASUREMENT_CORRECTIONS");
                i2 |= 1024;
            }
            if ((i & 2048) == 2048) {
                arrayList.add("ANTENNA_INFO");
                i2 |= 2048;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssSvInfo {
        public IGnssCallback.GnssSvInfo v2_0 = new IGnssCallback.GnssSvInfo();
        public double basebandCN0DbHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssSvInfo.class) {
                return false;
            }
            GnssSvInfo gnssSvInfo = (GnssSvInfo) obj;
            return HidlSupport.deepEquals(this.v2_0, gnssSvInfo.v2_0) && this.basebandCN0DbHz == gnssSvInfo.basebandCN0DbHz;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.v2_0)), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.basebandCN0DbHz))));
        }

        public final String toString() {
            return "{.v2_0 = " + this.v2_0 + ", .basebandCN0DbHz = " + this.basebandCN0DbHz + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
        }

        public static final ArrayList<GnssSvInfo> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssSvInfo> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssSvInfo gnssSvInfo = new GnssSvInfo();
                gnssSvInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
                arrayList.add(gnssSvInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.v2_0.readEmbeddedFromParcel(hwParcel, hwBlob, j);
            this.basebandCN0DbHz = hwBlob.getDouble(j + 32);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(40);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssSvInfo> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 40);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            this.v2_0.writeEmbeddedToBlob(hwBlob, j);
            hwBlob.putDouble(j + 32, this.basebandCN0DbHz);
        }
    }

    public static final class Proxy implements IGnssCallback {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@2.1::IGnssCallback]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssLocationCb(GnssLocation gnssLocation) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            gnssLocation.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssStatusCb(byte b) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            hwParcel.writeInt8(b);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssSvStatusCb(IGnssCallback.GnssSvStatus gnssSvStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            gnssSvStatus.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssNmeaCb(long j, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            hwParcel.writeInt64(j);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssSetCapabilitesCb(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssAcquireWakelockCb() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssReleaseWakelockCb() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssRequestTimeCb() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssSetSystemInfoCb(IGnssCallback.GnssSystemInfo gnssSystemInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
            gnssSystemInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnssCallback
        public void gnssNameCb(String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnssCallback.kInterfaceName);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnssCallback
        public void gnssRequestLocationCb(boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnssCallback.kInterfaceName);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnssCallback
        public void gnssSetCapabilitiesCb_2_0(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnssCallback.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnssCallback
        public void gnssLocationCb_2_0(android.hardware.gnss.V2_0.GnssLocation gnssLocation) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnssCallback.kInterfaceName);
            gnssLocation.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnssCallback
        public void gnssRequestLocationCb_2_0(boolean z, boolean z2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnssCallback.kInterfaceName);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnssCallback
        public void gnssSvStatusCb_2_0(ArrayList<IGnssCallback.GnssSvInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnssCallback.kInterfaceName);
            IGnssCallback.GnssSvInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback
        public void gnssSetCapabilitiesCb_2_1(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback
        public void gnssSvStatusCb_2_1(ArrayList<GnssSvInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            GnssSvInfo.writeVectorToParcel(hwParcel, arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnssCallback {
        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnssCallback.kInterfaceName, android.hardware.gnss.V2_0.IGnssCallback.kInterfaceName, android.hardware.gnss.V1_1.IGnssCallback.kInterfaceName, android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnssCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{53, 65, -40, 58, -33, -22, -63, 110, -29, -28, 93, 24, 58, 88, -33, -2, 6, 1, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, -53, 90, -91, -68, -46, -28, -10, -18, -82, 38, -97, 105, -51}, new byte[]{100, 35, 32, 55, 16, -102, 94, 95, 83, -85, 3, 119, -25, 85, -20, 73, 74, -23, 63, -53, 82, 121, -26, -18, -89, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT, -20, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 122, -58, -5, -4}, new byte[]{-118, -43, 91, -61, 91, -77, -88, 62, 101, MidiConstants.STATUS_PROGRAM_CHANGE, 24, -67, -3, -25, -82, 94, -68, 116, -97, MidiConstants.STATUS_SONG_POSITION, -65, 107, 121, 65, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -19, 11, -58, -56, -101, -105, -40}, new byte[]{-94, -5, -39, 116, Byte.MAX_VALUE, -69, -100, -21, -116, 16, MidiConstants.STATUS_NOTE_ON, -75, -94, 65, 56, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, 70, 80, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 90, -16, 101, 74, -116, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, SprAttributeBase.TYPE_DURATION, 58, -101, -11, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -4}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V2_1.IGnssCallback, android.hardware.gnss.V2_0.IGnssCallback, android.hardware.gnss.V1_1.IGnssCallback, android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IGnssCallback.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    GnssLocation gnssLocation = new GnssLocation();
                    gnssLocation.readFromParcel(hwParcel);
                    gnssLocationCb(gnssLocation);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssStatusCb(hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    IGnssCallback.GnssSvStatus gnssSvStatus = new IGnssCallback.GnssSvStatus();
                    gnssSvStatus.readFromParcel(hwParcel);
                    gnssSvStatusCb(gnssSvStatus);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssNmeaCb(hwParcel.readInt64(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssSetCapabilitesCb(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssAcquireWakelockCb();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssReleaseWakelockCb();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    gnssRequestTimeCb();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssCallback.kInterfaceName);
                    IGnssCallback.GnssSystemInfo gnssSystemInfo = new IGnssCallback.GnssSystemInfo();
                    gnssSystemInfo.readFromParcel(hwParcel);
                    gnssSetSystemInfoCb(gnssSystemInfo);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnssCallback.kInterfaceName);
                    gnssNameCb(hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnssCallback.kInterfaceName);
                    gnssRequestLocationCb(hwParcel.readBool());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnssCallback.kInterfaceName);
                    gnssSetCapabilitiesCb_2_0(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 13:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnssCallback.kInterfaceName);
                    android.hardware.gnss.V2_0.GnssLocation gnssLocation2 = new android.hardware.gnss.V2_0.GnssLocation();
                    gnssLocation2.readFromParcel(hwParcel);
                    gnssLocationCb_2_0(gnssLocation2);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 14:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnssCallback.kInterfaceName);
                    gnssRequestLocationCb_2_0(hwParcel.readBool(), hwParcel.readBool());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 15:
                    hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnssCallback.kInterfaceName);
                    gnssSvStatusCb_2_0(IGnssCallback.GnssSvInfo.readVectorFromParcel(hwParcel));
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 16:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    gnssSetCapabilitiesCb_2_1(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 17:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    gnssSvStatusCb_2_1(GnssSvInfo.readVectorFromParcel(hwParcel));
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
