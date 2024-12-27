package android.hardware.broadcastradio.V2_0;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DabTableEntry {
    public int frequency;
    public String label;

    public static final ArrayList readVectorFromParcel(HwParcel hwParcel) {
        ArrayList arrayList = new ArrayList();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        int i = 0;
        int i2 = 0;
        while (i2 < int32) {
            DabTableEntry dabTableEntry = new DabTableEntry();
            dabTableEntry.label = new String();
            dabTableEntry.frequency = i;
            long j = i2 * 24;
            dabTableEntry.label = readEmbeddedBuffer.getString(j);
            hwParcel.readEmbeddedBuffer(r3.getBytes().length + 1, readEmbeddedBuffer.handle(), j, false);
            dabTableEntry.frequency = readEmbeddedBuffer.getInt32(j + 16);
            arrayList.add(dabTableEntry);
            i2++;
            i = 0;
        }
        return arrayList;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != DabTableEntry.class) {
            return false;
        }
        DabTableEntry dabTableEntry = (DabTableEntry) obj;
        return HidlSupport.deepEquals(this.label, dabTableEntry.label) && this.frequency == dabTableEntry.frequency;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.label)), AudioConfig$$ExternalSyntheticOutline0.m(this.frequency));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{.label = ");
        sb.append(this.label);
        sb.append(", .frequency = ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.frequency, sb, "}");
    }
}
