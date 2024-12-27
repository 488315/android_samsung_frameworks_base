package android.hardware.soundtrigger.V2_3;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hidl.safe_union.V1_0.Monostate;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class OptionalModelParameterRange {
    public byte hidl_d;
    public Object hidl_o;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != OptionalModelParameterRange.class) {
            return false;
        }
        OptionalModelParameterRange optionalModelParameterRange = (OptionalModelParameterRange) obj;
        return this.hidl_d == optionalModelParameterRange.hidl_d
                && HidlSupport.deepEquals(this.hidl_o, optionalModelParameterRange.hidl_o);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)),
                Integer.valueOf(Byte.valueOf(this.hidl_d).hashCode()));
    }

    public final ModelParameterRange range() {
        if (this.hidl_d == 1) {
            Object obj = this.hidl_o;
            if (obj == null || ModelParameterRange.class.isInstance(obj)) {
                return (ModelParameterRange) this.hidl_o;
            }
            throw new Error("Union is in a corrupted state.");
        }
        Object obj2 = this.hidl_o;
        String name = obj2 != null ? obj2.getClass().getName() : "null";
        StringBuilder sb =
                new StringBuilder(
                        "Read access to inactive union components is disallowed. Discriminator"
                            + " value is ");
        sb.append((int) this.hidl_d);
        sb.append(" (corresponding to ");
        byte b = this.hidl_d;
        throw new IllegalStateException(
                OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                        sb,
                        b != 0 ? b != 1 ? "Unknown" : "range" : "noinit",
                        "), and hidl_o is of type ",
                        name,
                        "."));
    }

    public final void readFromParcel(HwParcel hwParcel) {
        HwBlob readBuffer = hwParcel.readBuffer(12L);
        byte int8 = readBuffer.getInt8(0L);
        this.hidl_d = int8;
        if (int8 == 0) {
            this.hidl_o = new Monostate();
            return;
        }
        if (int8 != 1) {
            throw new IllegalStateException(
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            this.hidl_d,
                            new StringBuilder("Unknown union discriminator (value: "),
                            ")."));
        }
        ModelParameterRange modelParameterRange = new ModelParameterRange();
        modelParameterRange.start = 0;
        modelParameterRange.end = 0;
        this.hidl_o = modelParameterRange;
        modelParameterRange.start = readBuffer.getInt32(4L);
        modelParameterRange.end = readBuffer.getInt32(8L);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        byte b = this.hidl_d;
        if (b == 0) {
            sb.append(".noinit = ");
            if (this.hidl_d != 0) {
                Object obj = this.hidl_o;
                String name = obj != null ? obj.getClass().getName() : "null";
                StringBuilder sb2 =
                        new StringBuilder(
                                "Read access to inactive union components is disallowed."
                                    + " Discriminator value is ");
                sb2.append((int) this.hidl_d);
                sb2.append(" (corresponding to ");
                byte b2 = this.hidl_d;
                throw new IllegalStateException(
                        OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                                sb2,
                                b2 != 0 ? b2 != 1 ? "Unknown" : "range" : "noinit",
                                "), and hidl_o is of type ",
                                name,
                                "."));
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Monostate.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            sb.append((Monostate) this.hidl_o);
        } else {
            if (b != 1) {
                throw new Error(
                        AmFmBandRange$$ExternalSyntheticOutline0.m(
                                this.hidl_d,
                                new StringBuilder("Unknown union discriminator (value: "),
                                ")."));
            }
            sb.append(".range = ");
            sb.append(range());
        }
        sb.append("}");
        return sb.toString();
    }
}
