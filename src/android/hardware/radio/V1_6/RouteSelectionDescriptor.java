package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RouteSelectionDescriptor {
    public byte precedence = 0;
    public OptionalPdpProtocolType sessionType = new OptionalPdpProtocolType();
    public OptionalSscMode sscMode = new OptionalSscMode();
    public ArrayList<SliceInfo> sliceInfo = new ArrayList<>();
    public ArrayList<String> dnn = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != RouteSelectionDescriptor.class) {
            return false;
        }
        RouteSelectionDescriptor routeSelectionDescriptor = (RouteSelectionDescriptor) obj;
        return this.precedence == routeSelectionDescriptor.precedence && HidlSupport.deepEquals(this.sessionType, routeSelectionDescriptor.sessionType) && HidlSupport.deepEquals(this.sscMode, routeSelectionDescriptor.sscMode) && HidlSupport.deepEquals(this.sliceInfo, routeSelectionDescriptor.sliceInfo) && HidlSupport.deepEquals(this.dnn, routeSelectionDescriptor.dnn);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.precedence))), Integer.valueOf(HidlSupport.deepHashCode(this.sessionType)), Integer.valueOf(HidlSupport.deepHashCode(this.sscMode)), Integer.valueOf(HidlSupport.deepHashCode(this.sliceInfo)), Integer.valueOf(HidlSupport.deepHashCode(this.dnn)));
    }

    public final String toString() {
        return "{.precedence = " + ((int) this.precedence) + ", .sessionType = " + this.sessionType + ", .sscMode = " + this.sscMode + ", .sliceInfo = " + this.sliceInfo + ", .dnn = " + this.dnn + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final ArrayList<RouteSelectionDescriptor> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RouteSelectionDescriptor> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            RouteSelectionDescriptor routeSelectionDescriptor = new RouteSelectionDescriptor();
            routeSelectionDescriptor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(routeSelectionDescriptor);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.precedence = hwBlob.getInt8(j);
        this.sessionType.readEmbeddedFromParcel(hwParcel, hwBlob, 4 + j);
        this.sscMode.readEmbeddedFromParcel(hwParcel, hwBlob, 12 + j);
        int int32 = hwBlob.getInt32(24 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, hwBlob.handle(), j + 16, true);
        this.sliceInfo.clear();
        for (int i = 0; i < int32; i++) {
            SliceInfo sliceInfo = new SliceInfo();
            sliceInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            this.sliceInfo.add(sliceInfo);
        }
        int int322 = hwBlob.getInt32(j + 40);
        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j + 32, true);
        this.dnn.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            new String();
            int i3 = i2 * 16;
            String string = readEmbeddedBuffer2.getString(i3);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer2.handle(), i3, false);
            this.dnn.add(string);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RouteSelectionDescriptor> arrayList) {
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
        hwBlob.putInt8(j, this.precedence);
        this.sessionType.writeEmbeddedToBlob(hwBlob, 4 + j);
        this.sscMode.writeEmbeddedToBlob(hwBlob, 12 + j);
        int size = this.sliceInfo.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size);
        hwBlob.putBool(28 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            this.sliceInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(j2, hwBlob2);
        int size2 = this.dnn.size();
        long j3 = 32 + j;
        hwBlob.putInt32(40 + j, size2);
        hwBlob.putBool(j + 44, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.dnn.get(i2));
        }
        hwBlob.putBlob(j3, hwBlob3);
    }
}
