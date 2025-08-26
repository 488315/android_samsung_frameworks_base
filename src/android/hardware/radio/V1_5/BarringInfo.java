package android.hardware.radio.V1_5;

import android.internal.hidl.safe_union.V1_0.Monostate;
import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.security.keystore.KeyProperties;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.lock.LsConstants;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class BarringInfo {
    public int serviceType = 0;
    public int barringType = 0;
    public BarringTypeSpecificInfo barringTypeSpecificInfo = new BarringTypeSpecificInfo();

    public static final class ServiceType {
        public static final int CS_FALLBACK = 5;
        public static final int CS_SERVICE = 0;
        public static final int CS_VOICE = 2;
        public static final int EMERGENCY = 8;
        public static final int MMTEL_VIDEO = 7;
        public static final int MMTEL_VOICE = 6;
        public static final int MO_DATA = 4;
        public static final int MO_SIGNALLING = 3;
        public static final int OPERATOR_1 = 1001;
        public static final int OPERATOR_10 = 1010;
        public static final int OPERATOR_11 = 1011;
        public static final int OPERATOR_12 = 1012;
        public static final int OPERATOR_13 = 1013;
        public static final int OPERATOR_14 = 1014;
        public static final int OPERATOR_15 = 1015;
        public static final int OPERATOR_16 = 1016;
        public static final int OPERATOR_17 = 1017;
        public static final int OPERATOR_18 = 1018;
        public static final int OPERATOR_19 = 1019;
        public static final int OPERATOR_2 = 1002;
        public static final int OPERATOR_20 = 1020;
        public static final int OPERATOR_21 = 1021;
        public static final int OPERATOR_22 = 1022;
        public static final int OPERATOR_23 = 1023;
        public static final int OPERATOR_24 = 1024;
        public static final int OPERATOR_25 = 1025;
        public static final int OPERATOR_26 = 1026;
        public static final int OPERATOR_27 = 1027;
        public static final int OPERATOR_28 = 1028;
        public static final int OPERATOR_29 = 1029;
        public static final int OPERATOR_3 = 1003;
        public static final int OPERATOR_30 = 1030;
        public static final int OPERATOR_31 = 1031;
        public static final int OPERATOR_32 = 1032;
        public static final int OPERATOR_4 = 1004;
        public static final int OPERATOR_5 = 1005;
        public static final int OPERATOR_6 = 1006;
        public static final int OPERATOR_7 = 1007;
        public static final int OPERATOR_8 = 1008;
        public static final int OPERATOR_9 = 1009;
        public static final int PS_SERVICE = 1;
        public static final int SMS = 9;

        public static final String toString(int i) {
            if (i == 0) {
                return "CS_SERVICE";
            }
            if (i == 1) {
                return "PS_SERVICE";
            }
            if (i == 2) {
                return "CS_VOICE";
            }
            if (i == 3) {
                return "MO_SIGNALLING";
            }
            if (i == 4) {
                return "MO_DATA";
            }
            if (i == 5) {
                return "CS_FALLBACK";
            }
            if (i == 6) {
                return "MMTEL_VOICE";
            }
            if (i == 7) {
                return "MMTEL_VIDEO";
            }
            if (i == 8) {
                return "EMERGENCY";
            }
            if (i == 9) {
                return "SMS";
            }
            if (i == 1001) {
                return "OPERATOR_1";
            }
            if (i == 1002) {
                return "OPERATOR_2";
            }
            if (i == 1003) {
                return "OPERATOR_3";
            }
            if (i == 1004) {
                return "OPERATOR_4";
            }
            if (i == 1005) {
                return "OPERATOR_5";
            }
            if (i == 1006) {
                return "OPERATOR_6";
            }
            if (i == 1007) {
                return "OPERATOR_7";
            }
            if (i == 1008) {
                return "OPERATOR_8";
            }
            if (i == 1009) {
                return "OPERATOR_9";
            }
            if (i == 1010) {
                return "OPERATOR_10";
            }
            if (i == 1011) {
                return "OPERATOR_11";
            }
            if (i == 1012) {
                return "OPERATOR_12";
            }
            if (i == 1013) {
                return "OPERATOR_13";
            }
            if (i == 1014) {
                return "OPERATOR_14";
            }
            if (i == 1015) {
                return "OPERATOR_15";
            }
            if (i == 1016) {
                return "OPERATOR_16";
            }
            if (i == 1017) {
                return "OPERATOR_17";
            }
            if (i == 1018) {
                return "OPERATOR_18";
            }
            if (i == 1019) {
                return "OPERATOR_19";
            }
            if (i == 1020) {
                return "OPERATOR_20";
            }
            if (i == 1021) {
                return "OPERATOR_21";
            }
            if (i == 1022) {
                return "OPERATOR_22";
            }
            if (i == 1023) {
                return "OPERATOR_23";
            }
            if (i == 1024) {
                return "OPERATOR_24";
            }
            if (i == 1025) {
                return "OPERATOR_25";
            }
            if (i == 1026) {
                return "OPERATOR_26";
            }
            if (i == 1027) {
                return "OPERATOR_27";
            }
            if (i == 1028) {
                return "OPERATOR_28";
            }
            if (i == 1029) {
                return "OPERATOR_29";
            }
            if (i == 1030) {
                return "OPERATOR_30";
            }
            if (i == 1031) {
                return "OPERATOR_31";
            }
            if (i == 1032) {
                return "OPERATOR_32";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("CS_SERVICE");
            int i2 = 1;
            if ((i & 1) == 1) {
                arrayList.add("PS_SERVICE");
            } else {
                i2 = 0;
            }
            if ((i & 2) == 2) {
                arrayList.add("CS_VOICE");
                i2 |= 2;
            }
            if ((i & 3) == 3) {
                arrayList.add("MO_SIGNALLING");
                i2 = 3;
            }
            if ((i & 4) == 4) {
                arrayList.add("MO_DATA");
                i2 |= 4;
            }
            if ((i & 5) == 5) {
                arrayList.add("CS_FALLBACK");
                i2 |= 5;
            }
            if ((i & 6) == 6) {
                arrayList.add("MMTEL_VOICE");
                i2 |= 6;
            }
            if ((i & 7) == 7) {
                arrayList.add("MMTEL_VIDEO");
                i2 = 7;
            }
            if ((i & 8) == 8) {
                arrayList.add("EMERGENCY");
                i2 |= 8;
            }
            if ((i & 9) == 9) {
                arrayList.add("SMS");
                i2 |= 9;
            }
            if ((i & 1001) == 1001) {
                arrayList.add("OPERATOR_1");
                i2 |= 1001;
            }
            if ((i & 1002) == 1002) {
                arrayList.add("OPERATOR_2");
                i2 |= 1002;
            }
            if ((i & 1003) == 1003) {
                arrayList.add("OPERATOR_3");
                i2 |= 1003;
            }
            if ((i & 1004) == 1004) {
                arrayList.add("OPERATOR_4");
                i2 |= 1004;
            }
            if ((i & 1005) == 1005) {
                arrayList.add("OPERATOR_5");
                i2 |= 1005;
            }
            if ((i & 1006) == 1006) {
                arrayList.add("OPERATOR_6");
                i2 |= 1006;
            }
            if ((i & 1007) == 1007) {
                arrayList.add("OPERATOR_7");
                i2 = 1007;
            }
            if ((i & 1008) == 1008) {
                arrayList.add("OPERATOR_8");
                i2 |= 1008;
            }
            if ((i & 1009) == 1009) {
                arrayList.add("OPERATOR_9");
                i2 |= 1009;
            }
            if ((i & 1010) == 1010) {
                arrayList.add("OPERATOR_10");
                i2 |= 1010;
            }
            if ((i & 1011) == 1011) {
                arrayList.add("OPERATOR_11");
                i2 |= 1011;
            }
            if ((i & 1012) == 1012) {
                arrayList.add("OPERATOR_12");
                i2 |= 1012;
            }
            if ((i & 1013) == 1013) {
                arrayList.add("OPERATOR_13");
                i2 |= 1013;
            }
            if ((i & 1014) == 1014) {
                arrayList.add("OPERATOR_14");
                i2 |= 1014;
            }
            if ((i & 1015) == 1015) {
                arrayList.add("OPERATOR_15");
                i2 |= 1015;
            }
            if ((i & 1016) == 1016) {
                arrayList.add("OPERATOR_16");
                i2 |= 1016;
            }
            if ((i & 1017) == 1017) {
                arrayList.add("OPERATOR_17");
                i2 |= 1017;
            }
            if ((i & 1018) == 1018) {
                arrayList.add("OPERATOR_18");
                i2 |= 1018;
            }
            if ((i & 1019) == 1019) {
                arrayList.add("OPERATOR_19");
                i2 |= 1019;
            }
            if ((i & 1020) == 1020) {
                arrayList.add("OPERATOR_20");
                i2 |= 1020;
            }
            if ((i & 1021) == 1021) {
                arrayList.add("OPERATOR_21");
                i2 |= 1021;
            }
            if ((i & 1022) == 1022) {
                arrayList.add("OPERATOR_22");
                i2 |= 1022;
            }
            if ((i & 1023) == 1023) {
                arrayList.add("OPERATOR_23");
                i2 = 1023;
            }
            if ((i & 1024) == 1024) {
                arrayList.add("OPERATOR_24");
                i2 |= 1024;
            }
            if ((i & 1025) == 1025) {
                arrayList.add("OPERATOR_25");
                i2 |= 1025;
            }
            if ((i & 1026) == 1026) {
                arrayList.add("OPERATOR_26");
                i2 |= 1026;
            }
            if ((i & 1027) == 1027) {
                arrayList.add("OPERATOR_27");
                i2 |= 1027;
            }
            if ((i & 1028) == 1028) {
                arrayList.add("OPERATOR_28");
                i2 |= 1028;
            }
            if ((i & 1029) == 1029) {
                arrayList.add("OPERATOR_29");
                i2 |= 1029;
            }
            if ((i & 1030) == 1030) {
                arrayList.add("OPERATOR_30");
                i2 |= 1030;
            }
            if ((i & 1031) == 1031) {
                arrayList.add("OPERATOR_31");
                i2 |= 1031;
            }
            if ((i & 1032) == 1032) {
                arrayList.add("OPERATOR_32");
                i2 |= 1032;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class BarringType {
        public static final int CONDITIONAL = 1;
        public static final int NONE = 0;
        public static final int UNCONDITIONAL = 2;

        public static final String toString(int i) {
            if (i == 0) {
                return KeyProperties.DIGEST_NONE;
            }
            if (i == 1) {
                return "CONDITIONAL";
            }
            if (i == 2) {
                return "UNCONDITIONAL";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(KeyProperties.DIGEST_NONE);
            int i2 = 1;
            if ((i & 1) == 1) {
                arrayList.add("CONDITIONAL");
            } else {
                i2 = 0;
            }
            if ((i & 2) == 2) {
                arrayList.add("UNCONDITIONAL");
                i2 |= 2;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class BarringTypeSpecificInfo {
        private byte hidl_d = 0;
        private Object hidl_o;

        public static final class Conditional {
            public int factor = 0;
            public int timeSeconds = 0;
            public boolean isBarred = false;

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || obj.getClass() != Conditional.class) {
                    return false;
                }
                Conditional conditional = (Conditional) obj;
                return this.factor == conditional.factor && this.timeSeconds == conditional.timeSeconds && this.isBarred == conditional.isBarred;
            }

            public final int hashCode() {
                return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.factor))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.timeSeconds))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.isBarred))));
            }

            public final String toString() {
                return "{.factor = " + this.factor + ", .timeSeconds = " + this.timeSeconds + ", .isBarred = " + this.isBarred + "}";
            }

            public final void readFromParcel(HwParcel hwParcel) {
                readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
            }

            public static final ArrayList<Conditional> readVectorFromParcel(HwParcel hwParcel) {
                ArrayList<Conditional> arrayList = new ArrayList<>();
                HwBlob buffer = hwParcel.readBuffer(16L);
                int int32 = buffer.getInt32(8L);
                HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, buffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    Conditional conditional = new Conditional();
                    conditional.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 12);
                    arrayList.add(conditional);
                }
                return arrayList;
            }

            public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
                this.factor = hwBlob.getInt32(j);
                this.timeSeconds = hwBlob.getInt32(4 + j);
                this.isBarred = hwBlob.getBool(j + 8);
            }

            public final void writeToParcel(HwParcel hwParcel) {
                HwBlob hwBlob = new HwBlob(12);
                writeEmbeddedToBlob(hwBlob, 0L);
                hwParcel.writeBuffer(hwBlob);
            }

            public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Conditional> arrayList) {
                HwBlob hwBlob = new HwBlob(16);
                int size = arrayList.size();
                hwBlob.putInt32(8L, size);
                hwBlob.putBool(12L, false);
                HwBlob hwBlob2 = new HwBlob(size * 12);
                for (int i = 0; i < size; i++) {
                    arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
                }
                hwBlob.putBlob(0L, hwBlob2);
                hwParcel.writeBuffer(hwBlob);
            }

            public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
                hwBlob.putInt32(j, this.factor);
                hwBlob.putInt32(4 + j, this.timeSeconds);
                hwBlob.putBool(j + 8, this.isBarred);
            }
        }

        public BarringTypeSpecificInfo() {
            this.hidl_o = null;
            this.hidl_o = new Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte conditional = 1;
            public static final byte noinit = 0;

            public static final String getName(byte b) {
                if (b == 0) {
                    return "noinit";
                }
                if (b == 1) {
                    return "conditional";
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

        public void conditional(Conditional conditional) {
            this.hidl_d = (byte) 1;
            this.hidl_o = conditional;
        }

        public Conditional conditional() {
            if (this.hidl_d != 1) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Conditional.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (Conditional) this.hidl_o;
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != BarringTypeSpecificInfo.class) {
                return false;
            }
            BarringTypeSpecificInfo barringTypeSpecificInfo = (BarringTypeSpecificInfo) obj;
            return this.hidl_d == barringTypeSpecificInfo.hidl_d && HidlSupport.deepEquals(this.hidl_o, barringTypeSpecificInfo.hidl_o);
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
                sb.append(".conditional = ");
                sb.append(conditional());
            } else {
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
        }

        public static final ArrayList<BarringTypeSpecificInfo> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<BarringTypeSpecificInfo> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                BarringTypeSpecificInfo barringTypeSpecificInfo = new BarringTypeSpecificInfo();
                barringTypeSpecificInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 16);
                arrayList.add(barringTypeSpecificInfo);
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
            } else if (int8 == 1) {
                Conditional conditional = new Conditional();
                this.hidl_o = conditional;
                conditional.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
            } else {
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(16);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<BarringTypeSpecificInfo> arrayList) {
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
            hwBlob.putInt8(j, this.hidl_d);
            byte b = this.hidl_d;
            if (b == 0) {
                noinit().writeEmbeddedToBlob(hwBlob, j + 4);
            } else {
                if (b == 1) {
                    conditional().writeEmbeddedToBlob(hwBlob, j + 4);
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
        if (obj == null || obj.getClass() != BarringInfo.class) {
            return false;
        }
        BarringInfo barringInfo = (BarringInfo) obj;
        return this.serviceType == barringInfo.serviceType && this.barringType == barringInfo.barringType && HidlSupport.deepEquals(this.barringTypeSpecificInfo, barringInfo.barringTypeSpecificInfo);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.serviceType))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.barringType))), Integer.valueOf(HidlSupport.deepHashCode(this.barringTypeSpecificInfo)));
    }

    public final String toString() {
        return "{.serviceType = " + ServiceType.toString(this.serviceType) + ", .barringType = " + BarringType.toString(this.barringType) + ", .barringTypeSpecificInfo = " + this.barringTypeSpecificInfo + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final ArrayList<BarringInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<BarringInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            BarringInfo barringInfo = new BarringInfo();
            barringInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 24);
            arrayList.add(barringInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.serviceType = hwBlob.getInt32(j);
        this.barringType = hwBlob.getInt32(4 + j);
        this.barringTypeSpecificInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<BarringInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.serviceType);
        hwBlob.putInt32(4 + j, this.barringType);
        this.barringTypeSpecificInfo.writeEmbeddedToBlob(hwBlob, j + 8);
    }
}
