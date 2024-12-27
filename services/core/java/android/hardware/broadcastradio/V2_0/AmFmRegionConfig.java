package android.hardware.broadcastradio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AmFmRegionConfig {
    public byte fmDeemphasis;
    public byte fmRds;
    public ArrayList ranges;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != AmFmRegionConfig.class) {
            return false;
        }
        AmFmRegionConfig amFmRegionConfig = (AmFmRegionConfig) obj;
        return HidlSupport.deepEquals(this.ranges, amFmRegionConfig.ranges)
                && HidlSupport.deepEquals(
                        Byte.valueOf(this.fmDeemphasis),
                        Byte.valueOf(amFmRegionConfig.fmDeemphasis))
                && HidlSupport.deepEquals(
                        Byte.valueOf(this.fmRds), Byte.valueOf(amFmRegionConfig.fmRds));
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.ranges)),
                Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.fmDeemphasis))),
                Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.fmRds))));
    }

    public final void readFromParcel(HwParcel hwParcel) {
        HwBlob readBuffer = hwParcel.readBuffer(24L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer =
                hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        this.ranges.clear();
        for (int i = 0; i < int32; i++) {
            AmFmBandRange amFmBandRange = new AmFmBandRange();
            amFmBandRange.lowerBound = 0;
            amFmBandRange.upperBound = 0;
            amFmBandRange.spacing = 0;
            amFmBandRange.scanSpacing = 0;
            long j = i * 16;
            amFmBandRange.lowerBound = readEmbeddedBuffer.getInt32(j);
            amFmBandRange.upperBound = readEmbeddedBuffer.getInt32(4 + j);
            amFmBandRange.spacing = readEmbeddedBuffer.getInt32(j + 8);
            amFmBandRange.scanSpacing = readEmbeddedBuffer.getInt32(j + 12);
            this.ranges.add(amFmBandRange);
        }
        this.fmDeemphasis = readBuffer.getInt8(16L);
        this.fmRds = readBuffer.getInt8(17L);
    }

    public final String toString() {
        byte b;
        StringBuilder sb = new StringBuilder("{.ranges = ");
        sb.append(this.ranges);
        sb.append(", .fmDeemphasis = ");
        byte b2 = this.fmDeemphasis;
        ArrayList arrayList = new ArrayList();
        byte b3 = 0;
        if ((b2 & 1) == 1) {
            arrayList.add("D50");
            b = (byte) 1;
        } else {
            b = 0;
        }
        if ((b2 & 2) == 2) {
            arrayList.add("D75");
            b = (byte) (b | 2);
        }
        if (b2 != b) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b2 & (~b)))));
        }
        sb.append(String.join(" | ", arrayList));
        sb.append(", .fmRds = ");
        byte b4 = this.fmRds;
        ArrayList arrayList2 = new ArrayList();
        if ((b4 & 1) == 1) {
            arrayList2.add("RDS");
            b3 = (byte) 1;
        }
        if ((b4 & 2) == 2) {
            arrayList2.add("RBDS");
            b3 = (byte) (b3 | 2);
        }
        if (b4 != b3) {
            arrayList2.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b4 & (~b3)))));
        }
        sb.append(String.join(" | ", arrayList2));
        sb.append("}");
        return sb.toString();
    }
}
