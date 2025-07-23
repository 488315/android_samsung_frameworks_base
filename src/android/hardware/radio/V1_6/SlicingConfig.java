package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SlicingConfig {
    public ArrayList<UrspRule> urspRules = new ArrayList<>();
    public ArrayList<SliceInfo> sliceInfo = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SlicingConfig.class) {
            return false;
        }
        SlicingConfig slicingConfig = (SlicingConfig) obj;
        return HidlSupport.deepEquals(this.urspRules, slicingConfig.urspRules) && HidlSupport.deepEquals(this.sliceInfo, slicingConfig.sliceInfo);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.urspRules)), Integer.valueOf(HidlSupport.deepHashCode(this.sliceInfo)));
    }

    public final String toString() {
        return "{.urspRules = " + this.urspRules + ", .sliceInfo = " + this.sliceInfo + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final ArrayList<SlicingConfig> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SlicingConfig> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SlicingConfig slicingConfig = new SlicingConfig();
            slicingConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(slicingConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        int int32 = hwBlob.getInt32(8 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, hwBlob.handle(), j, true);
        this.urspRules.clear();
        for (int i = 0; i < int32; i++) {
            UrspRule urspRule = new UrspRule();
            urspRule.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            this.urspRules.add(urspRule);
        }
        long j2 = j + 16;
        int int322 = hwBlob.getInt32(j + 24);
        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 20, hwBlob.handle(), j2, true);
        this.sliceInfo.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            SliceInfo sliceInfo = new SliceInfo();
            sliceInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 20);
            this.sliceInfo.add(sliceInfo);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SlicingConfig> arrayList) {
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
        int size = this.urspRules.size();
        hwBlob.putInt32(8 + j, size);
        hwBlob.putBool(12 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            this.urspRules.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(j, hwBlob2);
        int size2 = this.sliceInfo.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size2);
        hwBlob.putBool(j + 28, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 20);
        for (int i2 = 0; i2 < size2; i2++) {
            this.sliceInfo.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 20);
        }
        hwBlob.putBlob(j2, hwBlob3);
    }
}
