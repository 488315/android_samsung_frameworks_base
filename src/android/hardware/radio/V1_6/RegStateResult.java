package android.hardware.radio.V1_6;

import android.hardware.radio.V1_0.RegState;
import android.hardware.radio.V1_4.RadioTechnology;
import android.hardware.radio.V1_5.CellIdentity;
import android.hardware.radio.V1_5.RegStateResult;
import android.hardware.radio.V1_5.RegistrationFailCause;
import android.internal.hidl.safe_union.V1_0.Monostate;
import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.lock.LsConstants;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RegStateResult {
    public int regState = 0;
    public int rat = 0;
    public int reasonForDenial = 0;
    public CellIdentity cellIdentity = new CellIdentity();
    public String registeredPlmn = new String();
    public AccessTechnologySpecificInfo accessTechnologySpecificInfo = new AccessTechnologySpecificInfo();

    public static final class AccessTechnologySpecificInfo {
        private byte hidl_d = 0;
        private Object hidl_o;

        public AccessTechnologySpecificInfo() {
            this.hidl_o = null;
            this.hidl_o = new Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte cdmaInfo = 1;
            public static final byte eutranInfo = 2;
            public static final byte geranDtmSupported = 4;
            public static final byte ngranNrVopsInfo = 3;
            public static final byte noinit = 0;

            public static final String getName(byte b) {
                if (b == 0) {
                    return "noinit";
                }
                if (b == 1) {
                    return "cdmaInfo";
                }
                if (b == 2) {
                    return "eutranInfo";
                }
                if (b == 3) {
                    return "ngranNrVopsInfo";
                }
                if (b == 4) {
                    return "geranDtmSupported";
                }
                return LsConstants.TAG_UNKNOWN;
            }

            private hidl_discriminator() {
            }
        }

        public void noinit(Monostate monostate) {
            this.hidl_d = (byte) 0;
            this.hidl_o = monostate;
        }

        public Monostate noinit() {
            if (this.hidl_d != 0) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Monostate.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (Monostate) this.hidl_o;
        }

        public void cdmaInfo(RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo cdma2000RegistrationInfo) {
            this.hidl_d = (byte) 1;
            this.hidl_o = cdma2000RegistrationInfo;
        }

        public RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo cdmaInfo() {
            if (this.hidl_d != 1) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo) this.hidl_o;
        }

        public void eutranInfo(RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo eutranRegistrationInfo) {
            this.hidl_d = (byte) 2;
            this.hidl_o = eutranRegistrationInfo;
        }

        public RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo eutranInfo() {
            if (this.hidl_d != 2) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo) this.hidl_o;
        }

        public void ngranNrVopsInfo(NrVopsInfo nrVopsInfo) {
            this.hidl_d = (byte) 3;
            this.hidl_o = nrVopsInfo;
        }

        public NrVopsInfo ngranNrVopsInfo() {
            if (this.hidl_d != 3) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !NrVopsInfo.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (NrVopsInfo) this.hidl_o;
        }

        public void geranDtmSupported(boolean z) {
            this.hidl_d = (byte) 4;
            this.hidl_o = Boolean.valueOf(z);
        }

        public boolean geranDtmSupported() {
            if (this.hidl_d != 4) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Boolean.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return ((Boolean) this.hidl_o).booleanValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != AccessTechnologySpecificInfo.class) {
                return false;
            }
            AccessTechnologySpecificInfo accessTechnologySpecificInfo = (AccessTechnologySpecificInfo) obj;
            return this.hidl_d == accessTechnologySpecificInfo.hidl_d && HidlSupport.deepEquals(this.hidl_o, accessTechnologySpecificInfo.hidl_o);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            byte b = this.hidl_d;
            if (b == 0) {
                sb.append(".noinit = ");
                sb.append(noinit());
            } else if (b == 1) {
                sb.append(".cdmaInfo = ");
                sb.append(cdmaInfo());
            } else if (b == 2) {
                sb.append(".eutranInfo = ");
                sb.append(eutranInfo());
            } else if (b == 3) {
                sb.append(".ngranNrVopsInfo = ");
                sb.append(ngranNrVopsInfo());
            } else if (b == 4) {
                sb.append(".geranDtmSupported = ");
                sb.append(geranDtmSupported());
            } else {
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
        }

        public static final ArrayList<AccessTechnologySpecificInfo> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<AccessTechnologySpecificInfo> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                AccessTechnologySpecificInfo accessTechnologySpecificInfo = new AccessTechnologySpecificInfo();
                accessTechnologySpecificInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 20);
                arrayList.add(accessTechnologySpecificInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            byte int8 = hwBlob.getInt8(j);
            this.hidl_d = int8;
            if (int8 == 0) {
                Monostate monostate = new Monostate();
                this.hidl_o = monostate;
                monostate.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
                return;
            }
            if (int8 == 1) {
                RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo cdma2000RegistrationInfo = new RegStateResult.AccessTechnologySpecificInfo.Cdma2000RegistrationInfo();
                this.hidl_o = cdma2000RegistrationInfo;
                cdma2000RegistrationInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
                return;
            }
            if (int8 == 2) {
                RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo eutranRegistrationInfo = new RegStateResult.AccessTechnologySpecificInfo.EutranRegistrationInfo();
                this.hidl_o = eutranRegistrationInfo;
                eutranRegistrationInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
            } else if (int8 == 3) {
                NrVopsInfo nrVopsInfo = new NrVopsInfo();
                this.hidl_o = nrVopsInfo;
                nrVopsInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
            } else if (int8 == 4) {
                this.hidl_o = false;
                this.hidl_o = Boolean.valueOf(hwBlob.getBool(j + 4));
            } else {
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(20);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<AccessTechnologySpecificInfo> arrayList) {
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
            hwBlob.putInt8(j, this.hidl_d);
            byte b = this.hidl_d;
            if (b == 0) {
                noinit().writeEmbeddedToBlob(hwBlob, j + 4);
                return;
            }
            if (b == 1) {
                cdmaInfo().writeEmbeddedToBlob(hwBlob, j + 4);
                return;
            }
            if (b == 2) {
                eutranInfo().writeEmbeddedToBlob(hwBlob, j + 4);
                return;
            }
            if (b == 3) {
                ngranNrVopsInfo().writeEmbeddedToBlob(hwBlob, j + 4);
            } else {
                if (b == 4) {
                    hwBlob.putBool(j + 4, geranDtmSupported());
                    return;
                }
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != RegStateResult.class) {
            return false;
        }
        RegStateResult regStateResult = (RegStateResult) obj;
        return this.regState == regStateResult.regState && this.rat == regStateResult.rat && this.reasonForDenial == regStateResult.reasonForDenial && HidlSupport.deepEquals(this.cellIdentity, regStateResult.cellIdentity) && HidlSupport.deepEquals(this.registeredPlmn, regStateResult.registeredPlmn) && HidlSupport.deepEquals(this.accessTechnologySpecificInfo, regStateResult.accessTechnologySpecificInfo);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.regState))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.reasonForDenial))), Integer.valueOf(HidlSupport.deepHashCode(this.cellIdentity)), Integer.valueOf(HidlSupport.deepHashCode(this.registeredPlmn)), Integer.valueOf(HidlSupport.deepHashCode(this.accessTechnologySpecificInfo)));
    }

    public final String toString() {
        return "{.regState = " + RegState.toString(this.regState) + ", .rat = " + RadioTechnology.toString(this.rat) + ", .reasonForDenial = " + RegistrationFailCause.toString(this.reasonForDenial) + ", .cellIdentity = " + this.cellIdentity + ", .registeredPlmn = " + this.registeredPlmn + ", .accessTechnologySpecificInfo = " + this.accessTechnologySpecificInfo + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(224L), 0L);
    }

    public static final ArrayList<RegStateResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RegStateResult> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 224, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            RegStateResult regStateResult = new RegStateResult();
            regStateResult.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 224);
            arrayList.add(regStateResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.regState = hwBlob.getInt32(j);
        this.rat = hwBlob.getInt32(4 + j);
        this.reasonForDenial = hwBlob.getInt32(8 + j);
        this.cellIdentity.readEmbeddedFromParcel(hwParcel, hwBlob, 16 + j);
        long j2 = j + 184;
        this.registeredPlmn = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, hwBlob.handle(), j2, false);
        this.accessTechnologySpecificInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 200);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(224);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RegStateResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 224);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 224);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.regState);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putInt32(8 + j, this.reasonForDenial);
        this.cellIdentity.writeEmbeddedToBlob(hwBlob, 16 + j);
        hwBlob.putString(184 + j, this.registeredPlmn);
        this.accessTechnologySpecificInfo.writeEmbeddedToBlob(hwBlob, j + 200);
    }
}
