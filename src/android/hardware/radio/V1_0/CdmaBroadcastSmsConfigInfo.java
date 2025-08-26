package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CdmaBroadcastSmsConfigInfo {
    public int serviceCategory = 0;
    public int language = 0;
    public boolean selected = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CdmaBroadcastSmsConfigInfo.class) {
            return false;
        }
        CdmaBroadcastSmsConfigInfo cdmaBroadcastSmsConfigInfo = (CdmaBroadcastSmsConfigInfo) obj;
        return this.serviceCategory == cdmaBroadcastSmsConfigInfo.serviceCategory && this.language == cdmaBroadcastSmsConfigInfo.language && this.selected == cdmaBroadcastSmsConfigInfo.selected;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.serviceCategory))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.language))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.selected))));
    }

    public final String toString() {
        return "{.serviceCategory = " + this.serviceCategory + ", .language = " + this.language + ", .selected = " + this.selected + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final ArrayList<CdmaBroadcastSmsConfigInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaBroadcastSmsConfigInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CdmaBroadcastSmsConfigInfo cdmaBroadcastSmsConfigInfo = new CdmaBroadcastSmsConfigInfo();
            cdmaBroadcastSmsConfigInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 12);
            arrayList.add(cdmaBroadcastSmsConfigInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.serviceCategory = hwBlob.getInt32(j);
        this.language = hwBlob.getInt32(4 + j);
        this.selected = hwBlob.getBool(j + 8);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) {
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
        hwBlob.putInt32(j, this.serviceCategory);
        hwBlob.putInt32(4 + j, this.language);
        hwBlob.putBool(j + 8, this.selected);
    }
}
