package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class PhysicalChannelConfig {
    public android.hardware.radio.V1_2.PhysicalChannelConfig base = new android.hardware.radio.V1_2.PhysicalChannelConfig();
    public int rat = 0;
    public RadioFrequencyInfo rfInfo = new RadioFrequencyInfo();
    public ArrayList<Integer> contextIds = new ArrayList<>();
    public int physicalCellId = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PhysicalChannelConfig.class) {
            return false;
        }
        PhysicalChannelConfig physicalChannelConfig = (PhysicalChannelConfig) obj;
        return HidlSupport.deepEquals(this.base, physicalChannelConfig.base) && this.rat == physicalChannelConfig.rat && HidlSupport.deepEquals(this.rfInfo, physicalChannelConfig.rfInfo) && HidlSupport.deepEquals(this.contextIds, physicalChannelConfig.contextIds) && this.physicalCellId == physicalChannelConfig.physicalCellId;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))), Integer.valueOf(HidlSupport.deepHashCode(this.rfInfo)), Integer.valueOf(HidlSupport.deepHashCode(this.contextIds)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.physicalCellId))));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .rat = " + RadioTechnology.toString(this.rat) + ", .rfInfo = " + this.rfInfo + ", .contextIds = " + this.contextIds + ", .physicalCellId = " + this.physicalCellId + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final ArrayList<PhysicalChannelConfig> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PhysicalChannelConfig> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            PhysicalChannelConfig physicalChannelConfig = new PhysicalChannelConfig();
            physicalChannelConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(physicalChannelConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.rat = hwBlob.getInt32(8 + j);
        this.rfInfo.readEmbeddedFromParcel(hwParcel, hwBlob, 12 + j);
        int int32 = hwBlob.getInt32(32 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j + 24, true);
        this.contextIds.clear();
        for (int i = 0; i < int32; i++) {
            this.contextIds.add(Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
        this.physicalCellId = hwBlob.getInt32(j + 40);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PhysicalChannelConfig> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j);
        hwBlob.putInt32(8 + j, this.rat);
        this.rfInfo.writeEmbeddedToBlob(hwBlob, 12 + j);
        int size = this.contextIds.size();
        long j2 = 24 + j;
        hwBlob.putInt32(32 + j, size);
        hwBlob.putBool(36 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.contextIds.get(i).intValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putInt32(j + 40, this.physicalCellId);
    }
}
