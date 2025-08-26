package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class SliceInfo {
    public byte sst = 0;
    public int sliceDifferentiator = 0;
    public byte mappedHplmnSst = 0;
    public int mappedHplmnSD = 0;
    public byte status = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != SliceInfo.class) {
            return false;
        }
        SliceInfo sliceInfo = (SliceInfo) obj;
        return this.sst == sliceInfo.sst && this.sliceDifferentiator == sliceInfo.sliceDifferentiator && this.mappedHplmnSst == sliceInfo.mappedHplmnSst && this.mappedHplmnSD == sliceInfo.mappedHplmnSD && this.status == sliceInfo.status;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.sst))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.sliceDifferentiator))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.mappedHplmnSst))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.mappedHplmnSD))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.status))));
    }

    public final String toString() {
        return "{.sst = " + SliceServiceType.toString(this.sst) + ", .sliceDifferentiator = " + this.sliceDifferentiator + ", .mappedHplmnSst = " + SliceServiceType.toString(this.mappedHplmnSst) + ", .mappedHplmnSD = " + this.mappedHplmnSD + ", .status = " + SliceStatus.toString(this.status) + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final ArrayList<SliceInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SliceInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            SliceInfo sliceInfo = new SliceInfo();
            sliceInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 20);
            arrayList.add(sliceInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.sst = hwBlob.getInt8(j);
        this.sliceDifferentiator = hwBlob.getInt32(4 + j);
        this.mappedHplmnSst = hwBlob.getInt8(8 + j);
        this.mappedHplmnSD = hwBlob.getInt32(12 + j);
        this.status = hwBlob.getInt8(j + 16);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SliceInfo> arrayList) {
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
        hwBlob.putInt8(j, this.sst);
        hwBlob.putInt32(4 + j, this.sliceDifferentiator);
        hwBlob.putInt8(8 + j, this.mappedHplmnSst);
        hwBlob.putInt32(12 + j, this.mappedHplmnSD);
        hwBlob.putInt8(j + 16, this.status);
    }
}
