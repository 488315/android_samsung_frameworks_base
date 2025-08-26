package android.hardware.radio.V1_6;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class UrspRule {
    public byte precedence = 0;
    public ArrayList<TrafficDescriptor> trafficDescriptors = new ArrayList<>();
    public ArrayList<RouteSelectionDescriptor> routeSelectionDescriptor = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != UrspRule.class) {
            return false;
        }
        UrspRule urspRule = (UrspRule) obj;
        return this.precedence == urspRule.precedence && HidlSupport.deepEquals(this.trafficDescriptors, urspRule.trafficDescriptors) && HidlSupport.deepEquals(this.routeSelectionDescriptor, urspRule.routeSelectionDescriptor);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.precedence))), Integer.valueOf(HidlSupport.deepHashCode(this.trafficDescriptors)), Integer.valueOf(HidlSupport.deepHashCode(this.routeSelectionDescriptor)));
    }

    public final String toString() {
        return "{.precedence = " + ((int) this.precedence) + ", .trafficDescriptors = " + this.trafficDescriptors + ", .routeSelectionDescriptor = " + this.routeSelectionDescriptor + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<UrspRule> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<UrspRule> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            UrspRule urspRule = new UrspRule();
            urspRule.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 40);
            arrayList.add(urspRule);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.precedence = hwBlob.getInt8(j);
        int int32 = hwBlob.getInt32(16 + j);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, hwBlob.handle(), j + 8, true);
        this.trafficDescriptors.clear();
        for (int i = 0; i < int32; i++) {
            TrafficDescriptor trafficDescriptor = new TrafficDescriptor();
            trafficDescriptor.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 48);
            this.trafficDescriptors.add(trafficDescriptor);
        }
        int int322 = hwBlob.getInt32(j + 32);
        HwBlob embeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 48, hwBlob.handle(), j + 24, true);
        this.routeSelectionDescriptor.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            RouteSelectionDescriptor routeSelectionDescriptor = new RouteSelectionDescriptor();
            routeSelectionDescriptor.readEmbeddedFromParcel(hwParcel, embeddedBuffer2, i2 * 48);
            this.routeSelectionDescriptor.add(routeSelectionDescriptor);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<UrspRule> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt8(j, this.precedence);
        int size = this.trafficDescriptors.size();
        long j2 = 8 + j;
        hwBlob.putInt32(16 + j, size);
        hwBlob.putBool(20 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            this.trafficDescriptors.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(j2, hwBlob2);
        int size2 = this.routeSelectionDescriptor.size();
        long j3 = 24 + j;
        hwBlob.putInt32(32 + j, size2);
        hwBlob.putBool(j + 36, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 48);
        for (int i2 = 0; i2 < size2; i2++) {
            this.routeSelectionDescriptor.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 48);
        }
        hwBlob.putBlob(j3, hwBlob3);
    }
}
