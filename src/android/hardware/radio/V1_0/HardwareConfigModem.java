package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class HardwareConfigModem {
    public int rilModel = 0;
    public int rat = 0;
    public int maxVoice = 0;
    public int maxData = 0;
    public int maxStandby = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != HardwareConfigModem.class) {
            return false;
        }
        HardwareConfigModem hardwareConfigModem = (HardwareConfigModem) obj;
        return this.rilModel == hardwareConfigModem.rilModel && this.rat == hardwareConfigModem.rat && this.maxVoice == hardwareConfigModem.maxVoice && this.maxData == hardwareConfigModem.maxData && this.maxStandby == hardwareConfigModem.maxStandby;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rilModel))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxVoice))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxData))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.maxStandby))));
    }

    public final String toString() {
        return "{.rilModel = " + this.rilModel + ", .rat = " + this.rat + ", .maxVoice = " + this.maxVoice + ", .maxData = " + this.maxData + ", .maxStandby = " + this.maxStandby + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final ArrayList<HardwareConfigModem> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<HardwareConfigModem> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            HardwareConfigModem hardwareConfigModem = new HardwareConfigModem();
            hardwareConfigModem.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 20);
            arrayList.add(hardwareConfigModem);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.rilModel = hwBlob.getInt32(j);
        this.rat = hwBlob.getInt32(4 + j);
        this.maxVoice = hwBlob.getInt32(8 + j);
        this.maxData = hwBlob.getInt32(12 + j);
        this.maxStandby = hwBlob.getInt32(j + 16);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<HardwareConfigModem> arrayList) {
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
        hwBlob.putInt32(j, this.rilModel);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putInt32(8 + j, this.maxVoice);
        hwBlob.putInt32(12 + j, this.maxData);
        hwBlob.putInt32(j + 16, this.maxStandby);
    }
}
